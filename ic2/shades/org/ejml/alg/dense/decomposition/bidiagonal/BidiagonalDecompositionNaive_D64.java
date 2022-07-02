/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.bidiagonal;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.ops.SpecializedOps;
/*     */ import ic2.shades.org.ejml.simple.SimpleBase;
/*     */ import ic2.shades.org.ejml.simple.SimpleMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BidiagonalDecompositionNaive_D64
/*     */ {
/*     */   private SimpleMatrix U;
/*     */   private SimpleMatrix B;
/*     */   private SimpleMatrix V;
/*     */   private int m;
/*     */   private int n;
/*     */   private int min;
/*     */   DenseMatrix64F u;
/*     */   
/*     */   public SimpleMatrix getU() {
/*  49 */     return this.U;
/*     */   }
/*     */   
/*     */   public SimpleMatrix getB() {
/*  53 */     return this.B;
/*     */   }
/*     */   
/*     */   public SimpleMatrix getV() {
/*  57 */     return this.V;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F A) {
/*  68 */     init(A);
/*  69 */     return _decompose();
/*     */   }
/*     */   
/*     */   protected void init(DenseMatrix64F A) {
/*  73 */     this.m = A.numRows;
/*  74 */     this.n = A.numCols;
/*     */     
/*  76 */     this.min = Math.min(this.m, this.n);
/*     */     
/*  78 */     this.U = SimpleMatrix.identity(this.m);
/*  79 */     this.B = new SimpleMatrix(A);
/*  80 */     this.V = SimpleMatrix.identity(this.n);
/*     */     
/*  82 */     int max = Math.max(this.m, this.n);
/*  83 */     this.u = new DenseMatrix64F(max, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean _decompose() {
/*  92 */     for (int k = 0; k < this.min; k++) {
/*  93 */       computeU(k);
/*  94 */       computeV(k);
/*     */     } 
/*     */     
/*  97 */     return true;
/*     */   }
/*     */   
/*     */   protected void computeU(int k) {
/* 101 */     this.u.reshape(this.m, 1, false);
/* 102 */     double[] u = this.u.data;
/*     */ 
/*     */ 
/*     */     
/* 106 */     double max = 0.0D;
/*     */     
/* 108 */     for (int i = k; i < this.m; i++) {
/*     */ 
/*     */       
/* 111 */       double val = u[i] = this.B.get(i, k);
/* 112 */       val = Math.abs(val);
/* 113 */       if (val > max) {
/* 114 */         max = val;
/*     */       }
/*     */     } 
/* 117 */     if (max > 0.0D) {
/*     */ 
/*     */       
/* 120 */       double tau = 0.0D;
/*     */ 
/*     */       
/* 123 */       for (int j = k; j < this.m; j++) {
/* 124 */         double val = u[j] = u[j] / max;
/* 125 */         tau += val * val;
/*     */       } 
/*     */       
/* 128 */       tau = Math.sqrt(tau);
/*     */       
/* 130 */       if (u[k] < 0.0D) {
/* 131 */         tau = -tau;
/*     */       }
/*     */       
/* 134 */       double nu = u[k] + tau;
/* 135 */       u[k] = 1.0D;
/*     */       
/* 137 */       for (int m = k + 1; m < this.m; m++) {
/* 138 */         u[m] = u[m] / nu;
/*     */       }
/*     */       
/* 141 */       SimpleMatrix Q_k = SimpleMatrix.wrap(SpecializedOps.createReflector(this.u, nu / tau));
/* 142 */       this.U = (SimpleMatrix)this.U.mult((SimpleBase)Q_k);
/* 143 */       this.B = (SimpleMatrix)Q_k.mult((SimpleBase)this.B);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void computeV(int k) {
/* 148 */     this.u.reshape(this.n, 1, false);
/* 149 */     this.u.zero();
/* 150 */     double[] u = this.u.data;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     double max = 0.0D;
/*     */     
/* 157 */     for (int i = k + 1; i < this.n; i++) {
/*     */ 
/*     */       
/* 160 */       double val = u[i] = this.B.get(k, i);
/* 161 */       val = Math.abs(val);
/* 162 */       if (val > max) {
/* 163 */         max = val;
/*     */       }
/*     */     } 
/* 166 */     if (max > 0.0D) {
/*     */ 
/*     */       
/* 169 */       double tau = 0.0D;
/*     */ 
/*     */       
/* 172 */       for (int j = k + 1; j < this.n; j++) {
/* 173 */         double val = u[j] = u[j] / max;
/* 174 */         tau += val * val;
/*     */       } 
/*     */       
/* 177 */       tau = Math.sqrt(tau);
/*     */       
/* 179 */       if (u[k + 1] < 0.0D) {
/* 180 */         tau = -tau;
/*     */       }
/*     */       
/* 183 */       double nu = u[k + 1] + tau;
/* 184 */       u[k + 1] = 1.0D;
/*     */       
/* 186 */       for (int m = k + 2; m < this.n; m++) {
/* 187 */         u[m] = u[m] / nu;
/*     */       }
/*     */ 
/*     */       
/* 191 */       SimpleMatrix Q_k = SimpleMatrix.wrap(SpecializedOps.createReflector(this.u, nu / tau));
/*     */       
/* 193 */       this.V = (SimpleMatrix)this.V.mult((SimpleBase)Q_k);
/* 194 */       this.B = (SimpleMatrix)this.B.mult((SimpleBase)Q_k);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\bidiagonal\BidiagonalDecompositionNaive_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */