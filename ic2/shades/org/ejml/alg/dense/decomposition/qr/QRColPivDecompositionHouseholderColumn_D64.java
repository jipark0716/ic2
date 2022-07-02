/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRPDecomposition;
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
/*     */ public class QRColPivDecompositionHouseholderColumn_D64
/*     */   extends QRDecompositionHouseholderColumn_D64
/*     */   implements QRPDecomposition<DenseMatrix64F>
/*     */ {
/*     */   protected int[] pivots;
/*     */   protected double[] normsCol;
/*     */   protected double maxAbs;
/*  59 */   protected double singularThreshold = UtilEjml.EPS;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int rank;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QRColPivDecompositionHouseholderColumn_D64(double singularThreshold) {
/*  70 */     this.singularThreshold = singularThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSingularThreshold(double threshold) {
/*  78 */     this.singularThreshold = threshold;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  83 */     super.setExpectedMaxSize(numRows, numCols);
/*     */     
/*  85 */     if (this.pivots == null || this.pivots.length < numCols) {
/*  86 */       this.pivots = new int[numCols];
/*  87 */       this.normsCol = new double[numCols];
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
/*     */   public DenseMatrix64F getQ(DenseMatrix64F Q, boolean compact) {
/*  99 */     if (compact) {
/* 100 */       if (Q == null) {
/* 101 */         Q = CommonOps.identity(this.numRows, this.minLength);
/*     */       } else {
/* 103 */         if (Q.numRows != this.numRows || Q.numCols != this.minLength) {
/* 104 */           throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */         }
/* 106 */         CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */       }
/*     */     
/*     */     }
/* 110 */     else if (Q == null) {
/* 111 */       Q = CommonOps.identity(this.numRows);
/*     */     } else {
/* 113 */       if (Q.numRows != this.numRows || Q.numCols != this.numRows) {
/* 114 */         throw new IllegalArgumentException("Unexpected matrix dimension.");
/*     */       }
/* 116 */       CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 121 */     for (int j = this.rank - 1; j >= 0; j--) {
/* 122 */       double[] u = this.dataQR[j];
/*     */       
/* 124 */       double vv = u[j];
/* 125 */       u[j] = 1.0D;
/* 126 */       QrHelperFunctions.rank1UpdateMultR(Q, u, this.gammas[j], j, j, this.numRows, this.v);
/* 127 */       u[j] = vv;
/*     */     } 
/*     */     
/* 130 */     return Q;
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
/* 147 */     setExpectedMaxSize(A.numRows, A.numCols);
/*     */     
/* 149 */     convertToColumnMajor(A);
/*     */     
/* 151 */     this.maxAbs = CommonOps.elementMaxAbs((D1Matrix64F)A);
/*     */     
/* 153 */     setupPivotInfo();
/*     */ 
/*     */     
/* 156 */     for (int j = 0; j < this.minLength; j++) {
/* 157 */       if (j > 0)
/* 158 */         updateNorms(j); 
/* 159 */       swapColumns(j);
/*     */       
/* 161 */       if (!householderPivot(j))
/*     */         break; 
/* 163 */       updateA(j);
/* 164 */       this.rank = j + 1;
/*     */     } 
/*     */     
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupPivotInfo() {
/* 174 */     for (int col = 0; col < this.numCols; col++) {
/* 175 */       this.pivots[col] = col;
/* 176 */       double[] c = this.dataQR[col];
/* 177 */       double norm = 0.0D;
/* 178 */       for (int row = 0; row < this.numRows; row++) {
/* 179 */         double element = c[row];
/* 180 */         norm += element * element;
/*     */       } 
/* 182 */       this.normsCol[col] = norm;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateNorms(int j) {
/* 191 */     boolean foundNegative = false; int col;
/* 192 */     for (col = j; col < this.numCols; col++) {
/* 193 */       double e = this.dataQR[col][j - 1];
/* 194 */       this.normsCol[col] = this.normsCol[col] - e * e;
/*     */       
/* 196 */       if (this.normsCol[col] < 0.0D) {
/* 197 */         foundNegative = true;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     if (foundNegative) {
/* 205 */       for (col = j; col < this.numCols; col++) {
/* 206 */         double[] u = this.dataQR[col];
/* 207 */         double actual = 0.0D;
/* 208 */         for (int i = j; i < this.numRows; i++) {
/* 209 */           double v = u[i];
/* 210 */           actual += v * v;
/*     */         } 
/* 212 */         this.normsCol[col] = actual;
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
/*     */   private void swapColumns(int j) {
/* 225 */     int largestIndex = j;
/* 226 */     double largestNorm = this.normsCol[j];
/* 227 */     for (int col = j + 1; col < this.numCols; col++) {
/* 228 */       double n = this.normsCol[col];
/* 229 */       if (n > largestNorm) {
/* 230 */         largestNorm = n;
/* 231 */         largestIndex = col;
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     double[] tempC = this.dataQR[j];
/* 236 */     this.dataQR[j] = this.dataQR[largestIndex];
/* 237 */     this.dataQR[largestIndex] = tempC;
/* 238 */     double tempN = this.normsCol[j];
/* 239 */     this.normsCol[j] = this.normsCol[largestIndex];
/* 240 */     this.normsCol[largestIndex] = tempN;
/* 241 */     int tempP = this.pivots[j];
/* 242 */     this.pivots[j] = this.pivots[largestIndex];
/* 243 */     this.pivots[largestIndex] = tempP;
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
/*     */   protected boolean householderPivot(int j) {
/* 263 */     double[] u = this.dataQR[j];
/*     */ 
/*     */ 
/*     */     
/* 267 */     double max = QrHelperFunctions.findMax(u, j, this.numRows - j);
/*     */     
/* 269 */     if (max <= 0.0D) {
/* 270 */       return false;
/*     */     }
/*     */     
/* 273 */     this.tau = QrHelperFunctions.computeTauAndDivide(j, this.numRows, u, max);
/*     */ 
/*     */     
/* 276 */     double u_0 = u[j] + this.tau;
/* 277 */     QrHelperFunctions.divideElements(j + 1, this.numRows, u, u_0);
/*     */     
/* 279 */     this.gamma = u_0 / this.tau;
/* 280 */     this.tau *= max;
/*     */     
/* 282 */     u[j] = -this.tau;
/*     */     
/* 284 */     if (Math.abs(this.tau) <= this.singularThreshold) {
/* 285 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 289 */     this.gammas[j] = this.gamma;
/*     */     
/* 291 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRank() {
/* 296 */     return this.rank;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getPivots() {
/* 301 */     return this.pivots;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getPivotMatrix(DenseMatrix64F P) {
/* 306 */     if (P == null)
/* 307 */     { P = new DenseMatrix64F(this.numCols, this.numCols); }
/* 308 */     else { if (P.numRows != this.numCols)
/* 309 */         throw new IllegalArgumentException("Number of rows must be " + this.numCols); 
/* 310 */       if (P.numCols != this.numCols) {
/* 311 */         throw new IllegalArgumentException("Number of columns must be " + this.numCols);
/*     */       }
/* 313 */       P.zero(); }
/*     */ 
/*     */     
/* 316 */     for (int i = 0; i < this.numCols; i++) {
/* 317 */       P.set(this.pivots[i], i, 1.0D);
/*     */     }
/*     */     
/* 320 */     return P;
/*     */   }
/*     */   
/*     */   public QRColPivDecompositionHouseholderColumn_D64() {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QRColPivDecompositionHouseholderColumn_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */