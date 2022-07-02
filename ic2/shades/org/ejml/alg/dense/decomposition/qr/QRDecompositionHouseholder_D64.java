/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
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
/*     */ public class QRDecompositionHouseholder_D64
/*     */   implements QRDecomposition<DenseMatrix64F>
/*     */ {
/*     */   protected DenseMatrix64F QR;
/*     */   protected double[] u;
/*     */   protected double[] v;
/*     */   protected int numCols;
/*     */   protected int numRows;
/*     */   protected int minLength;
/*     */   protected double[] dataQR;
/*     */   protected double[] gammas;
/*     */   protected double gamma;
/*     */   protected double tau;
/*     */   protected boolean error;
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  80 */     this.error = false;
/*     */     
/*  82 */     this.numCols = numCols;
/*  83 */     this.numRows = numRows;
/*  84 */     this.minLength = Math.min(numRows, numCols);
/*  85 */     int maxLength = Math.max(numRows, numCols);
/*     */     
/*  87 */     if (this.QR == null) {
/*  88 */       this.QR = new DenseMatrix64F(numRows, numCols);
/*  89 */       this.u = new double[maxLength];
/*  90 */       this.v = new double[maxLength];
/*  91 */       this.gammas = new double[this.minLength];
/*     */     } else {
/*  93 */       this.QR.reshape(numRows, numCols, false);
/*     */     } 
/*     */     
/*  96 */     this.dataQR = this.QR.data;
/*     */     
/*  98 */     if (this.u.length < maxLength) {
/*  99 */       this.u = new double[maxLength];
/* 100 */       this.v = new double[maxLength];
/*     */     } 
/*     */     
/* 103 */     if (this.gammas.length < this.minLength) {
/* 104 */       this.gammas = new double[this.minLength];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQR() {
/* 115 */     return this.QR;
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
/* 126 */     if (compact) {
/* 127 */       if (Q == null) {
/* 128 */         Q = CommonOps.identity(this.numRows, this.minLength);
/*     */       } else {
/* 130 */         if (Q.numRows != this.numRows || Q.numCols != this.minLength) {
/* 131 */           throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */         }
/* 133 */         CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */       }
/*     */     
/*     */     }
/* 137 */     else if (Q == null) {
/* 138 */       Q = CommonOps.identity(this.numRows);
/*     */     } else {
/* 140 */       if (Q.numRows != this.numRows || Q.numCols != this.numRows) {
/* 141 */         throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */       }
/* 143 */       CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 148 */     for (int j = this.minLength - 1; j >= 0; j--) {
/* 149 */       this.u[j] = 1.0D;
/* 150 */       for (int i = j + 1; i < this.numRows; i++) {
/* 151 */         this.u[i] = this.QR.get(i, j);
/*     */       }
/* 153 */       QrHelperFunctions.rank1UpdateMultR(Q, this.u, this.gammas[j], j, j, this.numRows, this.v);
/*     */     } 
/*     */     
/* 156 */     return Q;
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
/* 167 */     if (R == null)
/* 168 */     { if (compact) {
/* 169 */         R = new DenseMatrix64F(this.minLength, this.numCols);
/*     */       } else {
/* 171 */         R = new DenseMatrix64F(this.numRows, this.numCols);
/*     */       }  }
/* 173 */     else { if (compact) {
/* 174 */         if (R.numCols != this.numCols || R.numRows != this.minLength) {
/* 175 */           throw new IllegalArgumentException("Unexpected dimensions");
/*     */         }
/* 177 */       } else if (R.numCols != this.numCols || R.numRows != this.numRows) {
/* 178 */         throw new IllegalArgumentException("Unexpected dimensions");
/*     */       } 
/*     */       
/* 181 */       for (int j = 0; j < R.numRows; j++) {
/* 182 */         int min = Math.min(j, R.numCols);
/* 183 */         for (int k = 0; k < min; k++) {
/* 184 */           R.set(j, k, 0.0D);
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/* 189 */     for (int i = 0; i < this.minLength; i++) {
/* 190 */       for (int j = i; j < this.numCols; j++) {
/* 191 */         double val = this.QR.get(i, j);
/* 192 */         R.set(i, j, val);
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     return R;
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
/* 213 */     commonSetup(A);
/*     */     
/* 215 */     for (int j = 0; j < this.minLength; j++) {
/* 216 */       householder(j);
/* 217 */       updateA(j);
/*     */     } 
/*     */     
/* 220 */     return !this.error;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 225 */     return false;
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
/*     */   protected void householder(int j) {
/* 246 */     int index = j + j * this.numCols;
/* 247 */     double max = 0.0D; int i;
/* 248 */     for (i = j; i < this.numRows; i++) {
/*     */       
/* 250 */       double d = this.u[i] = this.dataQR[index];
/*     */ 
/*     */       
/* 253 */       if (d < 0.0D) d = -d; 
/* 254 */       if (max < d) {
/* 255 */         max = d;
/*     */       }
/* 257 */       index += this.numCols;
/*     */     } 
/*     */     
/* 260 */     if (max == 0.0D) {
/* 261 */       this.gamma = 0.0D;
/* 262 */       this.error = true;
/*     */     }
/*     */     else {
/*     */       
/* 266 */       this.tau = 0.0D;
/* 267 */       for (i = j; i < this.numRows; i++) {
/* 268 */         this.u[i] = this.u[i] / max;
/* 269 */         double d = this.u[i];
/* 270 */         this.tau += d * d;
/*     */       } 
/* 272 */       this.tau = Math.sqrt(this.tau);
/*     */       
/* 274 */       if (this.u[j] < 0.0D) {
/* 275 */         this.tau = -this.tau;
/*     */       }
/* 277 */       double u_0 = this.u[j] + this.tau;
/* 278 */       this.gamma = u_0 / this.tau;
/* 279 */       for (int k = j + 1; k < this.numRows; k++) {
/* 280 */         this.u[k] = this.u[k] / u_0;
/*     */       }
/* 282 */       this.u[j] = 1.0D;
/* 283 */       this.tau *= max;
/*     */     } 
/*     */     
/* 286 */     this.gammas[j] = this.gamma;
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
/*     */   protected void updateA(int w) {
/* 314 */     for (int j = w + 1; j < this.numCols; j++) {
/* 315 */       this.v[j] = this.u[w] * this.dataQR[w * this.numCols + j];
/*     */     }
/*     */     
/* 318 */     for (int k = w + 1; k < this.numRows; k++) {
/* 319 */       int indexQR = k * this.numCols + w + 1;
/* 320 */       for (int m = w + 1; m < this.numCols; m++)
/*     */       {
/* 322 */         this.v[m] = this.v[m] + this.u[k] * this.dataQR[indexQR++];
/*     */       }
/*     */     } 
/*     */     int i;
/* 326 */     for (i = w + 1; i < this.numCols; i++) {
/* 327 */       this.v[i] = this.v[i] * this.gamma;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 332 */     for (i = w; i < this.numRows; i++) {
/* 333 */       double valU = this.u[i];
/*     */       
/* 335 */       int indexQR = i * this.numCols + w + 1;
/* 336 */       for (int m = w + 1; m < this.numCols; m++)
/*     */       {
/* 338 */         this.dataQR[indexQR++] = this.dataQR[indexQR++] - valU * this.v[m];
/*     */       }
/*     */     } 
/*     */     
/* 342 */     if (w < this.numCols) {
/* 343 */       this.dataQR[w + w * this.numCols] = -this.tau;
/*     */     }
/*     */ 
/*     */     
/* 347 */     for (i = w + 1; i < this.numRows; i++) {
/* 348 */       this.dataQR[w + i * this.numCols] = this.u[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void commonSetup(DenseMatrix64F A) {
/* 358 */     setExpectedMaxSize(A.numRows, A.numCols);
/*     */     
/* 360 */     this.QR.set((D1Matrix64F)A);
/*     */   }
/*     */   
/*     */   public double[] getGammas() {
/* 364 */     return this.gammas;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QRDecompositionHouseholder_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */