/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QrUpdate
/*     */ {
/*     */   private DenseMatrix64F Q;
/*     */   private DenseMatrix64F R;
/*     */   private DenseMatrix64F U_tran;
/*     */   private DenseMatrix64F Qm;
/*     */   private double[] r_row;
/*     */   private int maxCols;
/*     */   private int maxRows;
/*     */   private int m;
/*     */   private int n;
/*     */   private int m_m;
/*     */   private boolean autoGrow;
/*     */   
/*     */   public QrUpdate(int maxRows, int maxCols) {
/*  84 */     this.autoGrow = false;
/*  85 */     declareInternalData(maxRows, maxCols);
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
/*     */   public QrUpdate(int maxRows, int maxCols, boolean autoGrow) {
/*  97 */     this.autoGrow = autoGrow;
/*  98 */     declareInternalData(maxRows, maxCols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QrUpdate() {
/* 105 */     this.autoGrow = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void declareInternalData(int maxRows, int maxCols) {
/* 115 */     this.maxRows = maxRows;
/* 116 */     this.maxCols = maxCols;
/*     */     
/* 118 */     this.U_tran = new DenseMatrix64F(maxRows, maxRows);
/* 119 */     this.Qm = new DenseMatrix64F(maxRows, maxRows);
/*     */     
/* 121 */     this.r_row = new double[maxCols];
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
/*     */   public void addRow(DenseMatrix64F Q, DenseMatrix64F R, double[] row, int rowIndex, boolean resizeR) {
/* 147 */     setQR(Q, R, 1);
/* 148 */     this.m_m = this.m + 1;
/*     */     
/* 150 */     if (Q.data.length < this.m_m * this.m_m) {
/* 151 */       throw new IllegalArgumentException("Q matrix does not have enough data to grow");
/*     */     }
/* 153 */     if (resizeR && R.data.length < this.m_m * this.n) {
/* 154 */       throw new IllegalArgumentException("R matrix does not have enough data to grow");
/*     */     }
/* 156 */     if (resizeR) {
/* 157 */       R.reshape(this.m_m, this.n, false);
/*     */     }
/* 159 */     this.U_tran.reshape(this.m_m, this.m_m, false);
/*     */ 
/*     */     
/* 162 */     applyFirstGivens(row);
/* 163 */     applyLaterGivens();
/*     */     
/* 165 */     updateInsertQ(rowIndex);
/*     */ 
/*     */     
/* 168 */     this.Q = this.R = null;
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
/*     */   public void deleteRow(DenseMatrix64F Q, DenseMatrix64F R, int rowIndex, boolean resizeR) {
/* 188 */     setQR(Q, R, 0);
/* 189 */     if (this.m - 1 < this.n) {
/* 190 */       throw new IllegalArgumentException("Removing any row would make the system under determined.");
/*     */     }
/*     */     
/* 193 */     this.m_m = this.m - 1;
/* 194 */     this.U_tran.reshape(this.m, this.m, false);
/*     */     
/* 196 */     if (resizeR) {
/* 197 */       R.reshape(this.m_m, this.n, false);
/*     */     }
/* 199 */     computeRemoveGivens(rowIndex);
/* 200 */     updateRemoveQ(rowIndex);
/*     */     
/* 202 */     updateRemoveR();
/*     */ 
/*     */     
/* 205 */     this.Q = this.R = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setQR(DenseMatrix64F Q, DenseMatrix64F R, int growRows) {
/* 216 */     if (Q.numRows != Q.numCols) {
/* 217 */       throw new IllegalArgumentException("Q should be square.");
/*     */     }
/*     */     
/* 220 */     this.Q = Q;
/* 221 */     this.R = R;
/*     */     
/* 223 */     this.m = Q.numRows;
/* 224 */     this.n = R.numCols;
/*     */     
/* 226 */     if (this.m + growRows > this.maxRows || this.n > this.maxCols) {
/* 227 */       if (this.autoGrow) {
/* 228 */         declareInternalData(this.m + growRows, this.n);
/*     */       } else {
/* 230 */         throw new IllegalArgumentException("Autogrow has been set to false and the maximum number of rows or columns has been exceeded.");
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
/*     */   private void updateInsertQ(int rowIndex) {
/* 242 */     this.Qm.setReshape(this.Q);
/* 243 */     this.Q.reshape(this.m_m, this.m_m, false);
/*     */     
/* 245 */     for (int k = 0; k < rowIndex; k++) {
/* 246 */       for (int m = 0; m < this.m_m; m++) {
/* 247 */         double sum = 0.0D;
/* 248 */         for (int n = 0; n < this.m; n++) {
/* 249 */           sum += this.Qm.data[k * this.m + n] * this.U_tran.data[m * this.m_m + n + 1];
/*     */         }
/* 251 */         this.Q.data[k * this.m_m + m] = sum;
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     for (int j = 0; j < this.m_m; j++) {
/* 256 */       this.Q.data[rowIndex * this.m_m + j] = this.U_tran.data[j * this.m_m];
/*     */     }
/*     */     
/* 259 */     for (int i = rowIndex + 1; i < this.m_m; i++) {
/* 260 */       for (int m = 0; m < this.m_m; m++) {
/* 261 */         double sum = 0.0D;
/* 262 */         for (int n = 0; n < this.m; n++) {
/* 263 */           sum += this.Qm.data[(i - 1) * this.m + n] * this.U_tran.data[m * this.m_m + n + 1];
/*     */         }
/* 265 */         this.Q.data[i * this.m_m + m] = sum;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateRemoveQ(int rowIndex) {
/* 276 */     this.Qm.setReshape(this.Q);
/* 277 */     this.Q.reshape(this.m_m, this.m_m, false);
/*     */     int i;
/* 279 */     for (i = 0; i < rowIndex; i++) {
/* 280 */       for (int j = 1; j < this.m; j++) {
/* 281 */         double sum = 0.0D;
/* 282 */         for (int k = 0; k < this.m; k++) {
/* 283 */           sum += this.Qm.data[i * this.m + k] * this.U_tran.data[j * this.m + k];
/*     */         }
/* 285 */         this.Q.data[i * this.m_m + j - 1] = sum;
/*     */       } 
/*     */     } 
/*     */     
/* 289 */     for (i = rowIndex + 1; i < this.m; i++) {
/* 290 */       for (int j = 1; j < this.m; j++) {
/* 291 */         double sum = 0.0D;
/* 292 */         for (int k = 0; k < this.m; k++) {
/* 293 */           sum += this.Qm.data[i * this.m + k] * this.U_tran.data[j * this.m + k];
/*     */         }
/* 295 */         this.Q.data[(i - 1) * this.m_m + j - 1] = sum;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateRemoveR() {
/* 304 */     for (int i = 1; i < this.n + 1; i++) {
/* 305 */       for (int j = 0; j < this.n; j++) {
/* 306 */         double sum = 0.0D;
/* 307 */         for (int k = i - 1; k <= j; k++) {
/* 308 */           sum += this.U_tran.data[i * this.m + k] * this.R.data[k * this.n + j];
/*     */         }
/* 310 */         this.R.data[(i - 1) * this.n + j] = sum;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyFirstGivens(double[] row) {
/* 317 */     double c, s, xi = row[0];
/* 318 */     double xj = this.R.data[0];
/*     */     
/* 320 */     double r = xi * xi + xj * xj;
/* 321 */     if (r != 0.0D) {
/* 322 */       r = Math.sqrt(r);
/* 323 */       c = xi / r;
/* 324 */       s = xj / r;
/*     */     } else {
/*     */       
/* 327 */       c = 1.0D;
/* 328 */       s = 0.0D;
/*     */     } 
/*     */     
/* 331 */     this.R.data[0] = r;
/* 332 */     for (int col = 1; col < this.n; col++) {
/* 333 */       double vali = row[col];
/* 334 */       double valj = this.R.data[col];
/*     */       
/* 336 */       this.R.data[col] = c * vali + s * valj;
/* 337 */       this.r_row[col] = c * valj - s * vali;
/*     */     } 
/*     */ 
/*     */     
/* 341 */     CommonOps.setIdentity((RowD1Matrix64F)this.U_tran);
/* 342 */     this.U_tran.data[0] = c;
/* 343 */     this.U_tran.data[1] = s;
/* 344 */     this.U_tran.data[this.m_m] = -s;
/* 345 */     this.U_tran.data[this.m_m + 1] = c;
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyLaterGivens() {
/* 350 */     for (int row = 1; row < this.n; row++) {
/*     */ 
/*     */       
/* 353 */       double c, s, xi = this.r_row[row];
/* 354 */       double xj = this.R.data[this.n * row + row];
/*     */       
/* 356 */       double r = xi * xi + xj * xj;
/* 357 */       if (r != 0.0D) {
/* 358 */         r = Math.sqrt(r);
/* 359 */         c = xi / r;
/* 360 */         s = xj / r;
/*     */       } else {
/*     */         
/* 363 */         c = 1.0D;
/* 364 */         s = 0.0D;
/*     */       } 
/*     */ 
/*     */       
/* 368 */       this.R.data[this.n * row + row] = r; int col;
/* 369 */       for (col = row + 1; col < this.n; col++) {
/* 370 */         double vali = this.r_row[col];
/* 371 */         double valj = this.R.data[this.n * row + col];
/*     */         
/* 373 */         this.R.data[this.n * row + col] = c * vali + s * valj;
/* 374 */         this.r_row[col] = c * valj - s * vali;
/*     */       } 
/*     */ 
/*     */       
/* 378 */       for (col = 0; col <= row + 1; col++) {
/* 379 */         double q1 = this.U_tran.data[row * this.m_m + col];
/* 380 */         double q2 = this.U_tran.data[(row + 1) * this.m_m + col];
/*     */         
/* 382 */         this.U_tran.data[row * this.m_m + col] = c * q1 + s * q2;
/* 383 */         this.U_tran.data[(row + 1) * this.m_m + col] = c * q2 - s * q1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void computeRemoveGivens(int selectedRow) {
/* 390 */     CommonOps.setIdentity((RowD1Matrix64F)this.U_tran);
/*     */     
/* 392 */     double xj = this.Q.data[selectedRow * this.m + this.m - 1];
/*     */     
/* 394 */     for (int j = this.m - 2; j >= 0; j--) {
/*     */ 
/*     */       
/* 397 */       double c, s, xi = this.Q.data[selectedRow * this.m + j];
/*     */       
/* 399 */       double r = xi * xi + xj * xj;
/* 400 */       if (r != 0.0D) {
/* 401 */         r = Math.sqrt(r);
/* 402 */         c = xi / r;
/* 403 */         s = xj / r;
/*     */       } else {
/* 405 */         c = 1.0D;
/* 406 */         s = 0.0D;
/*     */       } 
/*     */ 
/*     */       
/* 410 */       xj = r;
/*     */ 
/*     */       
/* 413 */       for (int col = j; col < this.m; col++) {
/* 414 */         double q1 = this.U_tran.data[j * this.m + col];
/* 415 */         double q2 = this.U_tran.data[(j + 1) * this.m + col];
/*     */         
/* 417 */         this.U_tran.data[j * this.m + col] = c * q1 + s * q2;
/* 418 */         this.U_tran.data[(j + 1) * this.m + col] = c * q2 - s * q1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getU_tran() {
/* 424 */     return this.U_tran;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QrUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */