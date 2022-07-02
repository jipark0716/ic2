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
/*     */ public class QRDecompositionHouseholderColumn_D64
/*     */   implements QRDecomposition<DenseMatrix64F>
/*     */ {
/*     */   protected double[][] dataQR;
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
/*  64 */     this.numCols = numCols;
/*  65 */     this.numRows = numRows;
/*  66 */     this.minLength = Math.min(numCols, numRows);
/*  67 */     int maxLength = Math.max(numCols, numRows);
/*     */     
/*  69 */     if (this.dataQR == null || this.dataQR.length < numCols || (this.dataQR[0]).length < numRows) {
/*  70 */       this.dataQR = new double[numCols][numRows];
/*  71 */       this.v = new double[maxLength];
/*  72 */       this.gammas = new double[this.minLength];
/*     */     } 
/*     */     
/*  75 */     if (this.v.length < maxLength) {
/*  76 */       this.v = new double[maxLength];
/*     */     }
/*  78 */     if (this.gammas.length < this.minLength) {
/*  79 */       this.gammas = new double[this.minLength];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getQR() {
/*  89 */     return this.dataQR;
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
/* 100 */     if (compact) {
/* 101 */       if (Q == null) {
/* 102 */         Q = CommonOps.identity(this.numRows, this.minLength);
/*     */       } else {
/* 104 */         if (Q.numRows != this.numRows || Q.numCols != this.minLength) {
/* 105 */           throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */         }
/* 107 */         CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */       }
/*     */     
/*     */     }
/* 111 */     else if (Q == null) {
/* 112 */       Q = CommonOps.identity(this.numRows);
/*     */     } else {
/* 114 */       if (Q.numRows != this.numRows || Q.numCols != this.numRows) {
/* 115 */         throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */       }
/* 117 */       CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 122 */     for (int j = this.minLength - 1; j >= 0; j--) {
/* 123 */       double[] u = this.dataQR[j];
/*     */       
/* 125 */       double vv = u[j];
/* 126 */       u[j] = 1.0D;
/* 127 */       QrHelperFunctions.rank1UpdateMultR(Q, u, this.gammas[j], j, j, this.numRows, this.v);
/* 128 */       u[j] = vv;
/*     */     } 
/*     */     
/* 131 */     return Q;
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
/* 142 */     if (R == null)
/* 143 */     { if (compact) {
/* 144 */         R = new DenseMatrix64F(this.minLength, this.numCols);
/*     */       } else {
/* 146 */         R = new DenseMatrix64F(this.numRows, this.numCols);
/*     */       }  }
/* 148 */     else { if (compact) {
/* 149 */         if (R.numCols != this.numCols || R.numRows != this.minLength) {
/* 150 */           throw new IllegalArgumentException("Unexpected dimensions: found( " + R.numRows + " " + R.numCols + " ) expected( " + this.minLength + " " + this.numCols + " )");
/*     */         }
/*     */       }
/* 153 */       else if (R.numCols != this.numCols || R.numRows != this.numRows) {
/* 154 */         throw new IllegalArgumentException("Unexpected dimensions");
/*     */       } 
/*     */       
/* 157 */       for (int i = 0; i < R.numRows; i++) {
/* 158 */         int min = Math.min(i, R.numCols);
/* 159 */         for (int k = 0; k < min; k++) {
/* 160 */           R.set(i, k, 0.0D);
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/* 165 */     for (int j = 0; j < this.numCols; j++) {
/* 166 */       double[] colR = this.dataQR[j];
/* 167 */       int l = Math.min(j, this.numRows - 1);
/* 168 */       for (int i = 0; i <= l; i++) {
/* 169 */         double val = colR[i];
/* 170 */         R.set(i, j, val);
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return R;
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
/* 191 */     setExpectedMaxSize(A.numRows, A.numCols);
/*     */     
/* 193 */     convertToColumnMajor(A);
/*     */     
/* 195 */     this.error = false;
/*     */     
/* 197 */     for (int j = 0; j < this.minLength; j++) {
/* 198 */       householder(j);
/* 199 */       updateA(j);
/*     */     } 
/*     */     
/* 202 */     return !this.error;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void convertToColumnMajor(DenseMatrix64F A) {
/* 217 */     for (int x = 0; x < this.numCols; x++) {
/* 218 */       double[] colQ = this.dataQR[x];
/* 219 */       for (int y = 0; y < this.numRows; y++) {
/* 220 */         colQ[y] = A.data[y * this.numCols + x];
/*     */       }
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
/* 242 */     double[] u = this.dataQR[j];
/*     */ 
/*     */ 
/*     */     
/* 246 */     double max = QrHelperFunctions.findMax(u, j, this.numRows - j);
/*     */     
/* 248 */     if (max == 0.0D) {
/* 249 */       this.gamma = 0.0D;
/* 250 */       this.error = true;
/*     */     } else {
/*     */       
/* 253 */       this.tau = QrHelperFunctions.computeTauAndDivide(j, this.numRows, u, max);
/*     */ 
/*     */       
/* 256 */       double u_0 = u[j] + this.tau;
/* 257 */       QrHelperFunctions.divideElements(j + 1, this.numRows, u, u_0);
/*     */       
/* 259 */       this.gamma = u_0 / this.tau;
/* 260 */       this.tau *= max;
/*     */       
/* 262 */       u[j] = -this.tau;
/*     */     } 
/*     */     
/* 265 */     this.gammas[j] = this.gamma;
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
/*     */   protected void updateA(int w) {
/* 279 */     double[] u = this.dataQR[w];
/*     */     
/* 281 */     for (int j = w + 1; j < this.numCols; j++) {
/*     */       
/* 283 */       double[] colQ = this.dataQR[j];
/* 284 */       double val = colQ[w];
/*     */       
/* 286 */       for (int k = w + 1; k < this.numRows; k++) {
/* 287 */         val += u[k] * colQ[k];
/*     */       }
/* 289 */       val *= this.gamma;
/*     */       
/* 291 */       colQ[w] = colQ[w] - val;
/* 292 */       for (int i = w + 1; i < this.numRows; i++) {
/* 293 */         colQ[i] = colQ[i] - u[i] * val;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public double[] getGammas() {
/* 299 */     return this.gammas;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QRDecompositionHouseholderColumn_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */