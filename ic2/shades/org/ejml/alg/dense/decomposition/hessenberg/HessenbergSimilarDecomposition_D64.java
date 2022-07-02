/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.hessenberg;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;
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
/*     */ public class HessenbergSimilarDecomposition_D64
/*     */   implements DecompositionInterface<DenseMatrix64F>
/*     */ {
/*     */   private DenseMatrix64F QH;
/*     */   private int N;
/*     */   private double[] gammas;
/*     */   private double[] b;
/*     */   private double[] u;
/*     */   
/*     */   public HessenbergSimilarDecomposition_D64(int initialSize) {
/*  68 */     this.gammas = new double[initialSize];
/*  69 */     this.b = new double[initialSize];
/*  70 */     this.u = new double[initialSize];
/*     */   }
/*     */   
/*     */   public HessenbergSimilarDecomposition_D64() {
/*  74 */     this(5);
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
/*     */   public boolean decompose(DenseMatrix64F A) {
/*  86 */     if (A.numRows != A.numCols)
/*  87 */       throw new IllegalArgumentException("A must be square."); 
/*  88 */     if (A.numRows <= 0) {
/*  89 */       return false;
/*     */     }
/*  91 */     this.QH = A;
/*     */     
/*  93 */     this.N = A.numCols;
/*     */     
/*  95 */     if (this.b.length < this.N) {
/*  96 */       this.b = new double[this.N];
/*  97 */       this.gammas = new double[this.N];
/*  98 */       this.u = new double[this.N];
/*     */     } 
/* 100 */     return _decompose();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQH() {
/* 114 */     return this.QH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getH(DenseMatrix64F H) {
/* 124 */     if (H == null)
/* 125 */     { H = new DenseMatrix64F(this.N, this.N); }
/* 126 */     else { if (this.N != H.numRows || this.N != H.numCols) {
/* 127 */         throw new IllegalArgumentException("The provided H must have the same dimensions as the decomposed matrix.");
/*     */       }
/* 129 */       H.zero(); }
/*     */ 
/*     */     
/* 132 */     System.arraycopy(this.QH.data, 0, H.data, 0, this.N);
/*     */     
/* 134 */     for (int i = 1; i < this.N; i++) {
/* 135 */       for (int j = i - 1; j < this.N; j++) {
/* 136 */         H.set(i, j, this.QH.get(i, j));
/*     */       }
/*     */     } 
/*     */     
/* 140 */     return H;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getQ(DenseMatrix64F Q) {
/* 150 */     if (Q == null) {
/* 151 */       Q = new DenseMatrix64F(this.N, this.N);
/* 152 */       for (int i = 0; i < this.N; i++)
/* 153 */         Q.data[i * this.N + i] = 1.0D; 
/*     */     } else {
/* 155 */       if (this.N != Q.numRows || this.N != Q.numCols) {
/* 156 */         throw new IllegalArgumentException("The provided H must have the same dimensions as the decomposed matrix.");
/*     */       }
/* 158 */       CommonOps.setIdentity((RowD1Matrix64F)Q);
/*     */     } 
/* 160 */     for (int j = this.N - 2; j >= 0; j--) {
/* 161 */       this.u[j + 1] = 1.0D;
/* 162 */       for (int i = j + 2; i < this.N; i++) {
/* 163 */         this.u[i] = this.QH.get(i, j);
/*     */       }
/* 165 */       QrHelperFunctions.rank1UpdateMultR(Q, this.u, this.gammas[j], j + 1, j + 1, this.N, this.b);
/*     */     } 
/*     */     
/* 168 */     return Q;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _decompose() {
/* 175 */     double[] h = this.QH.data;
/*     */     
/* 177 */     for (int k = 0; k < this.N - 2; k++) {
/*     */ 
/*     */       
/* 180 */       double max = 0.0D;
/*     */       
/* 182 */       for (int i = k + 1; i < this.N; i++) {
/*     */ 
/*     */         
/* 185 */         double val = this.u[i] = h[i * this.N + k];
/* 186 */         val = Math.abs(val);
/* 187 */         if (val > max) {
/* 188 */           max = val;
/*     */         }
/*     */       } 
/* 191 */       if (max > 0.0D) {
/*     */ 
/*     */         
/* 194 */         double tau = 0.0D;
/*     */ 
/*     */         
/* 197 */         for (int j = k + 1; j < this.N; j++) {
/* 198 */           double val = this.u[j] = this.u[j] / max;
/* 199 */           tau += val * val;
/*     */         } 
/*     */         
/* 202 */         tau = Math.sqrt(tau);
/*     */         
/* 204 */         if (this.u[k + 1] < 0.0D) {
/* 205 */           tau = -tau;
/*     */         }
/*     */         
/* 208 */         double nu = this.u[k + 1] + tau;
/* 209 */         this.u[k + 1] = 1.0D;
/*     */         
/* 211 */         for (int m = k + 2; m < this.N; m++) {
/* 212 */           this.u[m] = this.u[m] / nu; h[m * this.N + k] = this.u[m] / nu;
/*     */         } 
/*     */         
/* 215 */         double gamma = nu / tau;
/* 216 */         this.gammas[k] = gamma;
/*     */ 
/*     */         
/* 219 */         QrHelperFunctions.rank1UpdateMultR(this.QH, this.u, gamma, k + 1, k + 1, this.N, this.b);
/*     */ 
/*     */         
/* 222 */         QrHelperFunctions.rank1UpdateMultL(this.QH, this.u, gamma, 0, k + 1, this.N);
/*     */ 
/*     */ 
/*     */         
/* 226 */         h[(k + 1) * this.N + k] = -tau * max;
/*     */       } else {
/*     */         
/* 229 */         this.gammas[k] = 0.0D;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 234 */     return true;
/*     */   }
/*     */   
/*     */   public double[] getGammas() {
/* 238 */     return this.gammas;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\hessenberg\HessenbergSimilarDecomposition_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */