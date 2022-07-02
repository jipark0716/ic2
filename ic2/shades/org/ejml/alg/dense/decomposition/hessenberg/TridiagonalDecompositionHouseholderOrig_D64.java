/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.hessenberg;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
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
/*     */ public class TridiagonalDecompositionHouseholderOrig_D64
/*     */ {
/*  54 */   int N = 1;
/*  55 */   DenseMatrix64F QT = new DenseMatrix64F(this.N, this.N);
/*  56 */   double[] w = new double[this.N];
/*  57 */   double[] b = new double[this.N];
/*  58 */   double[] gammas = new double[this.N];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQT() {
/*  66 */     return this.QT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getT(DenseMatrix64F T) {
/*  76 */     if (T == null)
/*  77 */     { T = new DenseMatrix64F(this.N, this.N); }
/*  78 */     else { if (this.N != T.numRows || this.N != T.numCols) {
/*  79 */         throw new IllegalArgumentException("The provided H must have the same dimensions as the decomposed matrix.");
/*     */       }
/*  81 */       T.zero(); }
/*     */ 
/*     */     
/*  84 */     T.data[0] = this.QT.data[0];
/*  85 */     T.data[1] = this.QT.data[1];
/*     */ 
/*     */     
/*  88 */     for (int i = 1; i < this.N - 1; i++) {
/*  89 */       T.set(i, i, this.QT.get(i, i));
/*  90 */       T.set(i, i + 1, this.QT.get(i, i + 1));
/*  91 */       T.set(i, i - 1, this.QT.get(i - 1, i));
/*     */     } 
/*     */     
/*  94 */     T.data[(this.N - 1) * this.N + this.N - 1] = this.QT.data[(this.N - 1) * this.N + this.N - 1];
/*  95 */     T.data[(this.N - 1) * this.N + this.N - 2] = this.QT.data[(this.N - 2) * this.N + this.N - 1];
/*     */     
/*  97 */     return T;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQ(DenseMatrix64F Q) {
/* 107 */     if (Q == null) {
/* 108 */       Q = new DenseMatrix64F(this.N, this.N);
/* 109 */       for (int k = 0; k < this.N; k++)
/* 110 */         Q.data[k * this.N + k] = 1.0D; 
/*     */     } else {
/* 112 */       if (this.N != Q.numRows || this.N != Q.numCols) {
/* 113 */         throw new IllegalArgumentException("The provided H must have the same dimensions as the decomposed matrix.");
/*     */       }
/* 115 */       CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */     } 
/* 117 */     for (int i = 0; i < this.N; ) { this.w[i] = 0.0D; i++; }
/*     */     
/* 119 */     for (int j = this.N - 2; j >= 0; j--) {
/* 120 */       this.w[j + 1] = 1.0D;
/* 121 */       for (int k = j + 2; k < this.N; k++) {
/* 122 */         this.w[k] = this.QT.get(j, k);
/*     */       }
/* 124 */       QrHelperFunctions.rank1UpdateMultR(Q, this.w, this.gammas[j + 1], j + 1, j + 1, this.N, this.b);
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return Q;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decompose(DenseMatrix64F A) {
/* 137 */     init(A);
/*     */     
/* 139 */     for (int k = 1; k < this.N; k++) {
/* 140 */       similarTransform(k);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void similarTransform(int k) {
/* 150 */     double[] t = this.QT.data;
/*     */ 
/*     */ 
/*     */     
/* 154 */     double max = 0.0D;
/*     */     
/* 156 */     int rowU = (k - 1) * this.N;
/*     */     
/* 158 */     for (int i = k; i < this.N; i++) {
/* 159 */       double val = Math.abs(t[rowU + i]);
/* 160 */       if (val > max) {
/* 161 */         max = val;
/*     */       }
/*     */     } 
/* 164 */     if (max > 0.0D) {
/*     */ 
/*     */       
/* 167 */       double tau = 0.0D;
/*     */ 
/*     */       
/* 170 */       for (int j = k; j < this.N; j++) {
/* 171 */         double val = t[rowU + j] = t[rowU + j] / max;
/* 172 */         tau += val * val;
/*     */       } 
/*     */       
/* 175 */       tau = Math.sqrt(tau);
/*     */       
/* 177 */       if (t[rowU + k] < 0.0D) {
/* 178 */         tau = -tau;
/*     */       }
/*     */       
/* 181 */       double nu = t[rowU + k] + tau;
/* 182 */       t[rowU + k] = 1.0D;
/*     */       
/* 184 */       for (int m = k + 1; m < this.N; m++) {
/* 185 */         t[rowU + m] = t[rowU + m] / nu;
/*     */       }
/*     */       
/* 188 */       double gamma = nu / tau;
/* 189 */       this.gammas[k] = gamma;
/*     */ 
/*     */       
/* 192 */       householderSymmetric(k, gamma);
/*     */ 
/*     */ 
/*     */       
/* 196 */       t[rowU + k] = -tau * max;
/*     */     } else {
/* 198 */       this.gammas[k] = 0.0D;
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
/* 210 */     int startU = (row - 1) * this.N;
/*     */ 
/*     */     
/* 213 */     for (int i = row; i < this.N; i++) {
/* 214 */       double total = 0.0D;
/* 215 */       for (int k = row; k < this.N; k++) {
/* 216 */         total += this.QT.data[i * this.N + k] * this.QT.data[startU + k];
/*     */       }
/* 218 */       this.w[i] = -gamma * total;
/*     */     } 
/*     */ 
/*     */     
/* 222 */     double alpha = 0.0D;
/*     */     int j;
/* 224 */     for (j = row; j < this.N; j++) {
/* 225 */       alpha += this.QT.data[startU + j] * this.w[j];
/*     */     }
/* 227 */     alpha *= -0.5D * gamma;
/*     */ 
/*     */     
/* 230 */     for (j = row; j < this.N; j++) {
/* 231 */       this.w[j] = this.w[j] + alpha * this.QT.data[startU + j];
/*     */     }
/*     */ 
/*     */     
/* 235 */     for (j = row; j < this.N; j++) {
/*     */       
/* 237 */       double ww = this.w[j];
/* 238 */       double uu = this.QT.data[startU + j];
/*     */ 
/*     */       
/* 241 */       for (int k = j; k < this.N; k++) {
/* 242 */         this.QT.data[j * this.N + k] = this.QT.data[j * this.N + k] + ww * this.QT.data[startU + k] + this.w[k] * uu; this.QT.data[k * this.N + j] = this.QT.data[j * this.N + k] + ww * this.QT.data[startU + k] + this.w[k] * uu;
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
/* 255 */     if (A.numRows != A.numCols) {
/* 256 */       throw new IllegalArgumentException("Must be square");
/*     */     }
/* 258 */     if (A.numCols != this.N) {
/* 259 */       this.N = A.numCols;
/* 260 */       this.QT.reshape(this.N, this.N, false);
/*     */       
/* 262 */       if (this.w.length < this.N) {
/* 263 */         this.w = new double[this.N];
/* 264 */         this.gammas = new double[this.N];
/* 265 */         this.b = new double[this.N];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 270 */     this.QT.set((D1Matrix64F)A);
/*     */   }
/*     */   
/*     */   public double getGamma(int index) {
/* 274 */     return this.gammas[index];
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\hessenberg\TridiagonalDecompositionHouseholderOrig_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */