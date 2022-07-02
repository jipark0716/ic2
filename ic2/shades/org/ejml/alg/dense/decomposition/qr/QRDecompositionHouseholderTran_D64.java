/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QRDecompositionHouseholderTran_D64
/*     */   implements QRDecomposition<DenseMatrix64F>
/*     */ {
/*     */   protected DenseMatrix64F QR;
/*     */   protected double[] v;
/*     */   protected int numCols;
/*     */   protected int numRows;
/*     */   protected int minLength;
/*     */   protected double[] gammas;
/*     */   protected double gamma;
/*     */   protected double tau;
/*     */   protected boolean error;
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  65 */     this.numCols = numCols;
/*  66 */     this.numRows = numRows;
/*  67 */     this.minLength = Math.min(numCols, numRows);
/*  68 */     int maxLength = Math.max(numCols, numRows);
/*     */     
/*  70 */     if (this.QR == null) {
/*  71 */       this.QR = new DenseMatrix64F(numCols, numRows);
/*  72 */       this.v = new double[maxLength];
/*  73 */       this.gammas = new double[this.minLength];
/*     */     } else {
/*  75 */       this.QR.reshape(numCols, numRows, false);
/*     */     } 
/*     */     
/*  78 */     if (this.v.length < maxLength) {
/*  79 */       this.v = new double[maxLength];
/*     */     }
/*  81 */     if (this.gammas.length < this.minLength) {
/*  82 */       this.gammas = new double[this.minLength];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQR() {
/*  90 */     return this.QR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQ(DenseMatrix64F Q, boolean compact) {
/* 101 */     if (compact) {
/* 102 */       if (Q == null) {
/* 103 */         Q = CommonOps.identity(this.numRows, this.minLength);
/*     */       } else {
/* 105 */         if (Q.numRows != this.numRows || Q.numCols != this.minLength) {
/* 106 */           throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */         }
/* 108 */         CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */       }
/*     */     
/*     */     }
/* 112 */     else if (Q == null) {
/* 113 */       Q = CommonOps.identity(this.numRows);
/*     */     } else {
/* 115 */       if (Q.numRows != this.numRows || Q.numCols != this.numRows) {
/* 116 */         throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */       }
/* 118 */       CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     for (int j = this.minLength - 1; j >= 0; j--) {
/* 126 */       int diagIndex = j * this.numRows + j;
/* 127 */       double before = this.QR.data[diagIndex];
/* 128 */       this.QR.data[diagIndex] = 1.0D;
/* 129 */       QrHelperFunctions.rank1UpdateMultR(Q, this.QR.data, j * this.numRows, this.gammas[j], j, j, this.numRows, this.v);
/* 130 */       this.QR.data[diagIndex] = before;
/*     */     } 
/*     */     
/* 133 */     return Q;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyQ(DenseMatrix64F A) {
/* 142 */     if (A.numRows != this.numRows) {
/* 143 */       throw new IllegalArgumentException("A must have at least " + this.numRows + " rows.");
/*     */     }
/* 145 */     for (int j = this.minLength - 1; j >= 0; j--) {
/* 146 */       int diagIndex = j * this.numRows + j;
/* 147 */       double before = this.QR.data[diagIndex];
/* 148 */       this.QR.data[diagIndex] = 1.0D;
/* 149 */       QrHelperFunctions.rank1UpdateMultR(A, this.QR.data, j * this.numRows, this.gammas[j], 0, j, this.numRows, this.v);
/* 150 */       this.QR.data[diagIndex] = before;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyTranQ(DenseMatrix64F A) {
/* 160 */     for (int j = 0; j < this.minLength; j++) {
/* 161 */       int diagIndex = j * this.numRows + j;
/* 162 */       double before = this.QR.data[diagIndex];
/* 163 */       this.QR.data[diagIndex] = 1.0D;
/* 164 */       QrHelperFunctions.rank1UpdateMultR(A, this.QR.data, j * this.numRows, this.gammas[j], 0, j, this.numRows, this.v);
/* 165 */       this.QR.data[diagIndex] = before;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getR(DenseMatrix64F R, boolean compact) {
/* 177 */     if (R == null)
/* 178 */     { if (compact) {
/* 179 */         R = new DenseMatrix64F(this.minLength, this.numCols);
/*     */       } else {
/* 181 */         R = new DenseMatrix64F(this.numRows, this.numCols);
/*     */       }  }
/* 183 */     else { if (compact) {
/* 184 */         if (R.numCols != this.numCols || R.numRows != this.minLength) {
/* 185 */           throw new IllegalArgumentException("Unexpected dimensions");
/*     */         }
/* 187 */       } else if (R.numCols != this.numCols || R.numRows != this.numRows) {
/* 188 */         throw new IllegalArgumentException("Unexpected dimensions");
/*     */       } 
/*     */       
/* 191 */       for (int j = 0; j < R.numRows; j++) {
/* 192 */         int min = Math.min(j, R.numCols);
/* 193 */         for (int k = 0; k < min; k++) {
/* 194 */           R.unsafe_set(j, k, 0.0D);
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/* 199 */     for (int i = 0; i < R.numRows; i++) {
/* 200 */       for (int j = i; j < R.numCols; j++) {
/* 201 */         R.unsafe_set(i, j, this.QR.unsafe_get(j, i));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 206 */     return R;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F A) {
/* 223 */     setExpectedMaxSize(A.numRows, A.numCols);
/*     */     
/* 225 */     CommonOps.transpose(A, this.QR);
/*     */     
/* 227 */     this.error = false;
/*     */     
/* 229 */     for (int j = 0; j < this.minLength; j++) {
/* 230 */       householder(j);
/* 231 */       updateA(j);
/*     */     } 
/*     */     
/* 234 */     return !this.error;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void householder(int j) {
/* 259 */     int startQR = j * this.numRows;
/* 260 */     int endQR = startQR + this.numRows;
/* 261 */     startQR += j;
/*     */     
/* 263 */     double max = QrHelperFunctions.findMax(this.QR.data, startQR, this.numRows - j);
/*     */     
/* 265 */     if (max == 0.0D) {
/* 266 */       this.gamma = 0.0D;
/* 267 */       this.error = true;
/*     */     } else {
/*     */       
/* 270 */       this.tau = QrHelperFunctions.computeTauAndDivide(startQR, endQR, this.QR.data, max);
/*     */ 
/*     */       
/* 273 */       double u_0 = this.QR.data[startQR] + this.tau;
/* 274 */       QrHelperFunctions.divideElements(startQR + 1, endQR, this.QR.data, u_0);
/*     */       
/* 276 */       this.gamma = u_0 / this.tau;
/* 277 */       this.tau *= max;
/*     */       
/* 279 */       this.QR.data[startQR] = -this.tau;
/*     */     } 
/*     */     
/* 282 */     this.gammas[j] = this.gamma;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateA(int w) {
/* 315 */     double[] data = this.QR.data;
/* 316 */     int rowW = w * this.numRows + w + 1;
/* 317 */     int rowJ = rowW + this.numRows;
/* 318 */     int rowJEnd = rowJ + (this.numCols - w - 1) * this.numRows;
/* 319 */     int indexWEnd = rowW + this.numRows - w - 1;
/*     */     
/* 321 */     for (; rowJEnd != rowJ; rowJ += this.numRows) {
/*     */       
/* 323 */       double val = data[rowJ - 1];
/*     */       
/* 325 */       int indexW = rowW;
/* 326 */       int indexJ = rowJ;
/*     */       
/* 328 */       while (indexW != indexWEnd) {
/* 329 */         val += data[indexW++] * data[indexJ++];
/*     */       }
/* 331 */       val *= this.gamma;
/*     */       
/* 333 */       data[rowJ - 1] = data[rowJ - 1] - val;
/* 334 */       indexW = rowW;
/* 335 */       indexJ = rowJ;
/* 336 */       while (indexW != indexWEnd) {
/* 337 */         data[indexJ++] = data[indexJ++] - data[indexW++] * val;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public double[] getGammas() {
/* 343 */     return this.gammas;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QRDecompositionHouseholderTran_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */