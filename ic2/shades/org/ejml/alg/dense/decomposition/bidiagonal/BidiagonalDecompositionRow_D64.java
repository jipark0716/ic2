/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.bidiagonal;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.BidiagonalDecomposition;
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
/*     */ public class BidiagonalDecompositionRow_D64
/*     */   implements BidiagonalDecomposition<DenseMatrix64F>
/*     */ {
/*     */   private DenseMatrix64F UBV;
/*     */   private int m;
/*     */   private int n;
/*     */   private int min;
/*     */   private double[] gammasU;
/*     */   private double[] gammasV;
/*     */   private double[] b;
/*     */   private double[] u;
/*     */   
/*     */   public BidiagonalDecompositionRow_D64(int numElements) {
/*  61 */     this.UBV = new DenseMatrix64F(numElements);
/*  62 */     this.gammasU = new double[numElements];
/*  63 */     this.gammasV = new double[numElements];
/*  64 */     this.b = new double[numElements];
/*  65 */     this.u = new double[numElements];
/*     */   }
/*     */   
/*     */   public BidiagonalDecompositionRow_D64() {
/*  69 */     this(1);
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
/*     */   public boolean decompose(DenseMatrix64F A) {
/*  82 */     init(A);
/*  83 */     return _decompose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(DenseMatrix64F A) {
/*  92 */     this.UBV = A;
/*     */     
/*  94 */     this.m = this.UBV.numRows;
/*  95 */     this.n = this.UBV.numCols;
/*     */     
/*  97 */     this.min = Math.min(this.m, this.n);
/*  98 */     int max = Math.max(this.m, this.n);
/*     */     
/* 100 */     if (this.b.length < max + 1) {
/* 101 */       this.b = new double[max + 1];
/* 102 */       this.u = new double[max + 1];
/*     */     } 
/* 104 */     if (this.gammasU.length < this.m) {
/* 105 */       this.gammasU = new double[this.m];
/*     */     }
/* 107 */     if (this.gammasV.length < this.n) {
/* 108 */       this.gammasV = new double[this.n];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getUBV() {
/* 118 */     return this.UBV;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDiagonal(double[] diag, double[] off) {
/* 123 */     diag[0] = this.UBV.get(0);
/* 124 */     for (int i = 1; i < this.n; i++) {
/* 125 */       diag[i] = this.UBV.unsafe_get(i, i);
/* 126 */       off[i - 1] = this.UBV.unsafe_get(i - 1, i);
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
/*     */   public DenseMatrix64F getB(DenseMatrix64F B, boolean compact) {
/* 138 */     B = handleB(B, compact, this.m, this.n, this.min);
/*     */ 
/*     */ 
/*     */     
/* 142 */     B.set(0, 0, this.UBV.get(0, 0));
/* 143 */     for (int i = 1; i < this.min; i++) {
/* 144 */       B.set(i, i, this.UBV.get(i, i));
/* 145 */       B.set(i - 1, i, this.UBV.get(i - 1, i));
/*     */     } 
/* 147 */     if (this.n > this.m) {
/* 148 */       B.set(this.min - 1, this.min, this.UBV.get(this.min - 1, this.min));
/*     */     }
/* 150 */     return B;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F handleB(DenseMatrix64F B, boolean compact, int m, int n, int min) {
/* 155 */     int w = (n > m) ? (min + 1) : min;
/*     */     
/* 157 */     if (compact) {
/* 158 */       if (B == null) {
/* 159 */         B = new DenseMatrix64F(min, w);
/*     */       } else {
/* 161 */         B.reshape(min, w, false);
/* 162 */         B.zero();
/*     */       }
/*     */     
/* 165 */     } else if (B == null) {
/* 166 */       B = new DenseMatrix64F(m, n);
/*     */     } else {
/* 168 */       B.reshape(m, n, false);
/* 169 */       B.zero();
/*     */     } 
/*     */     
/* 172 */     return B;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getU(DenseMatrix64F U, boolean transpose, boolean compact) {
/* 183 */     U = handleU(U, transpose, compact, this.m, this.n, this.min);
/* 184 */     CommonOps.setIdentity((RowD1Matrix64F)U);
/*     */     
/* 186 */     for (int i = 0; i < this.m; ) { this.u[i] = 0.0D; i++; }
/*     */     
/* 188 */     for (int j = this.min - 1; j >= 0; j--) {
/* 189 */       this.u[j] = 1.0D;
/* 190 */       for (int k = j + 1; k < this.m; k++) {
/* 191 */         this.u[k] = this.UBV.get(k, j);
/*     */       }
/* 193 */       if (transpose) {
/* 194 */         QrHelperFunctions.rank1UpdateMultL(U, this.u, this.gammasU[j], j, j, this.m);
/*     */       } else {
/* 196 */         QrHelperFunctions.rank1UpdateMultR(U, this.u, this.gammasU[j], j, j, this.m, this.b);
/*     */       } 
/*     */     } 
/* 199 */     return U;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F handleU(DenseMatrix64F U, boolean transpose, boolean compact, int m, int n, int min) {
/* 205 */     if (compact) {
/* 206 */       if (transpose) {
/* 207 */         if (U == null) {
/* 208 */           U = new DenseMatrix64F(min, m);
/*     */         } else {
/* 210 */           U.reshape(min, m, false);
/*     */         }
/*     */       
/* 213 */       } else if (U == null) {
/* 214 */         U = new DenseMatrix64F(m, min);
/*     */       } else {
/* 216 */         U.reshape(m, min, false);
/*     */       }
/*     */     
/* 219 */     } else if (U == null) {
/* 220 */       U = new DenseMatrix64F(m, m);
/*     */     } else {
/* 222 */       U.reshape(m, m, false);
/*     */     } 
/*     */     
/* 225 */     return U;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getV(DenseMatrix64F V, boolean transpose, boolean compact) {
/* 236 */     V = handleV(V, transpose, compact, this.m, this.n, this.min);
/* 237 */     CommonOps.setIdentity((RowD1Matrix64F)V);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     for (int j = this.min - 1; j >= 0; j--) {
/* 243 */       this.u[j + 1] = 1.0D;
/* 244 */       for (int i = j + 2; i < this.n; i++) {
/* 245 */         this.u[i] = this.UBV.get(j, i);
/*     */       }
/* 247 */       if (transpose) {
/* 248 */         QrHelperFunctions.rank1UpdateMultL(V, this.u, this.gammasV[j], j + 1, j + 1, this.n);
/*     */       } else {
/* 250 */         QrHelperFunctions.rank1UpdateMultR(V, this.u, this.gammasV[j], j + 1, j + 1, this.n, this.b);
/*     */       } 
/*     */     } 
/* 253 */     return V;
/*     */   }
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F handleV(DenseMatrix64F V, boolean transpose, boolean compact, int m, int n, int min) {
/* 258 */     int w = (n > m) ? (min + 1) : min;
/*     */     
/* 260 */     if (compact) {
/* 261 */       if (transpose) {
/* 262 */         if (V == null) {
/* 263 */           V = new DenseMatrix64F(w, n);
/*     */         } else {
/* 265 */           V.reshape(w, n, false);
/*     */         } 
/* 267 */       } else if (V == null) {
/* 268 */         V = new DenseMatrix64F(n, w);
/*     */       } else {
/* 270 */         V.reshape(n, w, false);
/*     */       }
/*     */     
/* 273 */     } else if (V == null) {
/* 274 */       V = new DenseMatrix64F(n, n);
/*     */     } else {
/* 276 */       V.reshape(n, n, false);
/*     */     } 
/*     */     
/* 279 */     return V;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _decompose() {
/* 286 */     for (int k = 0; k < this.min; k++) {
/*     */       
/* 288 */       computeU(k);
/*     */ 
/*     */       
/* 291 */       computeV(k);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 296 */     return true;
/*     */   }
/*     */   
/*     */   protected void computeU(int k) {
/* 300 */     double[] b = this.UBV.data;
/*     */ 
/*     */ 
/*     */     
/* 304 */     double max = 0.0D;
/*     */     
/* 306 */     for (int i = k; i < this.m; i++) {
/*     */ 
/*     */       
/* 309 */       double val = this.u[i] = b[i * this.n + k];
/* 310 */       val = Math.abs(val);
/* 311 */       if (val > max) {
/* 312 */         max = val;
/*     */       }
/*     */     } 
/* 315 */     if (max > 0.0D) {
/*     */       
/* 317 */       double tau = QrHelperFunctions.computeTauAndDivide(k, this.m, this.u, max);
/*     */ 
/*     */ 
/*     */       
/* 321 */       double nu = this.u[k] + tau;
/* 322 */       QrHelperFunctions.divideElements_Bcol(k + 1, this.m, this.n, this.u, b, k, nu);
/* 323 */       this.u[k] = 1.0D;
/*     */       
/* 325 */       double gamma = nu / tau;
/* 326 */       this.gammasU[k] = gamma;
/*     */ 
/*     */       
/* 329 */       QrHelperFunctions.rank1UpdateMultR(this.UBV, this.u, gamma, k + 1, k, this.m, this.b);
/*     */       
/* 331 */       b[k * this.n + k] = -tau * max;
/*     */     } else {
/* 333 */       this.gammasU[k] = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void computeV(int k) {
/* 338 */     double[] b = this.UBV.data;
/*     */     
/* 340 */     int row = k * this.n;
/*     */ 
/*     */ 
/*     */     
/* 344 */     double max = QrHelperFunctions.findMax(b, row + k + 1, this.n - k - 1);
/*     */     
/* 346 */     if (max > 0.0D) {
/*     */ 
/*     */       
/* 349 */       double tau = QrHelperFunctions.computeTauAndDivide(k + 1, this.n, b, row, max);
/*     */ 
/*     */       
/* 352 */       double nu = b[row + k + 1] + tau;
/* 353 */       QrHelperFunctions.divideElements_Brow(k + 2, this.n, this.u, b, row, nu);
/*     */       
/* 355 */       this.u[k + 1] = 1.0D;
/*     */       
/* 357 */       double gamma = nu / tau;
/* 358 */       this.gammasV[k] = gamma;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 363 */       QrHelperFunctions.rank1UpdateMultL(this.UBV, this.u, gamma, k + 1, k + 1, this.n);
/*     */       
/* 365 */       b[row + k + 1] = -tau * max;
/*     */     } else {
/* 367 */       this.gammasV[k] = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getGammasU() {
/* 377 */     return this.gammasU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getGammasV() {
/* 386 */     return this.gammasV;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 391 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\bidiagonal\BidiagonalDecompositionRow_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */