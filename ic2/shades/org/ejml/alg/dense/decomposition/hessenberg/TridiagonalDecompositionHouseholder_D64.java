/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.hessenberg;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;
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
/*     */ public class TridiagonalDecompositionHouseholder_D64
/*     */   implements TridiagonalSimilarDecomposition<DenseMatrix64F>
/*     */ {
/*  67 */   private int N = 1;
/*  68 */   private double[] w = new double[this.N];
/*  69 */   private double[] b = new double[this.N];
/*  70 */   private double[] gammas = new double[this.N];
/*     */ 
/*     */   
/*     */   private DenseMatrix64F QT;
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQT() {
/*  78 */     return this.QT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDiagonal(double[] diag, double[] off) {
/*  83 */     for (int i = 0; i < this.N; i++) {
/*  84 */       diag[i] = this.QT.data[i * this.N + i];
/*     */       
/*  86 */       if (i + 1 < this.N) {
/*  87 */         off[i] = this.QT.data[i * this.N + i + 1];
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
/*     */   public DenseMatrix64F getT(DenseMatrix64F T) {
/* 100 */     if (T == null)
/* 101 */     { T = new DenseMatrix64F(this.N, this.N); }
/* 102 */     else { if (this.N != T.numRows || this.N != T.numCols) {
/* 103 */         throw new IllegalArgumentException("The provided H must have the same dimensions as the decomposed matrix.");
/*     */       }
/* 105 */       T.zero(); }
/*     */ 
/*     */     
/* 108 */     T.data[0] = this.QT.data[0];
/*     */     
/* 110 */     for (int i = 1; i < this.N; i++) {
/* 111 */       T.set(i, i, this.QT.get(i, i));
/* 112 */       double a = this.QT.get(i - 1, i);
/* 113 */       T.set(i - 1, i, a);
/* 114 */       T.set(i, i - 1, a);
/*     */     } 
/*     */     
/* 117 */     if (this.N > 1) {
/* 118 */       T.data[(this.N - 1) * this.N + this.N - 1] = this.QT.data[(this.N - 1) * this.N + this.N - 1];
/* 119 */       T.data[(this.N - 1) * this.N + this.N - 2] = this.QT.data[(this.N - 2) * this.N + this.N - 1];
/*     */     } 
/*     */     
/* 122 */     return T;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQ(DenseMatrix64F Q, boolean transposed) {
/* 133 */     if (Q == null)
/* 134 */     { Q = CommonOps.identity(this.N); }
/* 135 */     else { if (this.N != Q.numRows || this.N != Q.numCols) {
/* 136 */         throw new IllegalArgumentException("The provided H must have the same dimensions as the decomposed matrix.");
/*     */       }
/* 138 */       CommonOps.setIdentity((RowD1Matrix64F)Q); }
/*     */     
/* 140 */     for (int i = 0; i < this.N; ) { this.w[i] = 0.0D; i++; }
/*     */     
/* 142 */     if (transposed) {
/* 143 */       for (int j = this.N - 2; j >= 0; j--) {
/* 144 */         this.w[j + 1] = 1.0D;
/* 145 */         for (int k = j + 2; k < this.N; k++) {
/* 146 */           this.w[k] = this.QT.data[j * this.N + k];
/*     */         }
/* 148 */         QrHelperFunctions.rank1UpdateMultL(Q, this.w, this.gammas[j + 1], j + 1, j + 1, this.N);
/*     */       } 
/*     */     } else {
/* 151 */       for (int j = this.N - 2; j >= 0; j--) {
/* 152 */         this.w[j + 1] = 1.0D;
/* 153 */         for (int k = j + 2; k < this.N; k++) {
/* 154 */           this.w[k] = this.QT.get(j, k);
/*     */         }
/* 156 */         QrHelperFunctions.rank1UpdateMultR(Q, this.w, this.gammas[j + 1], j + 1, j + 1, this.N, this.b);
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return Q;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F A) {
/* 170 */     init(A);
/*     */     
/* 172 */     for (int k = 1; k < this.N; k++) {
/* 173 */       similarTransform(k);
/*     */     }
/*     */     
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void similarTransform(int k) {
/* 183 */     double[] t = this.QT.data;
/*     */ 
/*     */ 
/*     */     
/* 187 */     double max = 0.0D;
/*     */     
/* 189 */     int rowU = (k - 1) * this.N;
/*     */     
/* 191 */     for (int i = k; i < this.N; i++) {
/* 192 */       double val = Math.abs(t[rowU + i]);
/* 193 */       if (val > max) {
/* 194 */         max = val;
/*     */       }
/*     */     } 
/* 197 */     if (max > 0.0D) {
/*     */ 
/*     */       
/* 200 */       double tau = QrHelperFunctions.computeTauAndDivide(k, this.N, t, rowU, max);
/*     */ 
/*     */       
/* 203 */       double nu = t[rowU + k] + tau;
/* 204 */       QrHelperFunctions.divideElements(k + 1, this.N, t, rowU, nu);
/* 205 */       t[rowU + k] = 1.0D;
/*     */       
/* 207 */       double gamma = nu / tau;
/* 208 */       this.gammas[k] = gamma;
/*     */ 
/*     */       
/* 211 */       householderSymmetric(k, gamma);
/*     */ 
/*     */ 
/*     */       
/* 215 */       t[rowU + k] = -tau * max;
/*     */     } else {
/* 217 */       this.gammas[k] = 0.0D;
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
/*     */   public void householderSymmetric(int row, double gamma) {
/* 229 */     int startU = (row - 1) * this.N;
/*     */ 
/*     */     
/* 232 */     for (int i = row; i < this.N; i++) {
/* 233 */       double total = 0.0D;
/*     */       
/*     */       int k;
/*     */       
/* 237 */       for (k = row; k < i; k++) {
/* 238 */         total += this.QT.data[k * this.N + i] * this.QT.data[startU + k];
/*     */       }
/* 240 */       for (k = i; k < this.N; k++) {
/* 241 */         total += this.QT.data[i * this.N + k] * this.QT.data[startU + k];
/*     */       }
/* 243 */       this.w[i] = -gamma * total;
/*     */     } 
/*     */     
/* 246 */     double alpha = 0.0D;
/*     */     int j;
/* 248 */     for (j = row; j < this.N; j++) {
/* 249 */       alpha += this.QT.data[startU + j] * this.w[j];
/*     */     }
/* 251 */     alpha *= -0.5D * gamma;
/*     */ 
/*     */     
/* 254 */     for (j = row; j < this.N; j++) {
/* 255 */       this.w[j] = this.w[j] + alpha * this.QT.data[startU + j];
/*     */     }
/*     */     
/* 258 */     for (j = row; j < this.N; j++) {
/*     */       
/* 260 */       double ww = this.w[j];
/* 261 */       double uu = this.QT.data[startU + j];
/*     */       
/* 263 */       int rowA = j * this.N;
/* 264 */       for (int k = j; k < this.N; k++)
/*     */       {
/*     */         
/* 267 */         this.QT.data[rowA + k] = this.QT.data[rowA + k] + ww * this.QT.data[startU + k] + this.w[k] * uu;
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
/*     */   public void init(DenseMatrix64F A) {
/* 280 */     if (A.numRows != A.numCols) {
/* 281 */       throw new IllegalArgumentException("Must be square");
/*     */     }
/* 283 */     if (A.numCols != this.N) {
/* 284 */       this.N = A.numCols;
/*     */       
/* 286 */       if (this.w.length < this.N) {
/* 287 */         this.w = new double[this.N];
/* 288 */         this.gammas = new double[this.N];
/* 289 */         this.b = new double[this.N];
/*     */       } 
/*     */     } 
/*     */     
/* 293 */     this.QT = A;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 298 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\hessenberg\TridiagonalDecompositionHouseholder_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */