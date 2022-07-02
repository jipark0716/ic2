/*     */ package ic2.shades.org.ejml.alg.dense.linsol.svd;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.SingularValueDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
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
/*     */ public class SolvePseudoInverseSvd
/*     */   implements LinearSolver<DenseMatrix64F>
/*     */ {
/*     */   private SingularValueDecomposition<DenseMatrix64F> svd;
/*  50 */   private DenseMatrix64F pinv = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*  53 */   private double threshold = UtilEjml.EPS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolvePseudoInverseSvd(int maxRows, int maxCols) {
/*  63 */     this.svd = DecompositionFactory.svd(maxRows, maxCols, true, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolvePseudoInverseSvd() {
/*  70 */     this(100, 100);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  75 */     this.pinv.reshape(A.numCols, A.numRows, false);
/*     */     
/*  77 */     if (!this.svd.decompose((Matrix64F)A)) {
/*  78 */       return false;
/*     */     }
/*  80 */     DenseMatrix64F U_t = (DenseMatrix64F)this.svd.getU(null, true);
/*  81 */     DenseMatrix64F V = (DenseMatrix64F)this.svd.getV(null, false);
/*  82 */     double[] S = this.svd.getSingularValues();
/*  83 */     int N = Math.min(A.numRows, A.numCols);
/*     */ 
/*     */     
/*  86 */     double maxSingular = 0.0D;
/*  87 */     for (int i = 0; i < N; i++) {
/*  88 */       if (S[i] > maxSingular) {
/*  89 */         maxSingular = S[i];
/*     */       }
/*     */     } 
/*  92 */     double tau = this.threshold * Math.max(A.numCols, A.numRows) * maxSingular;
/*     */ 
/*     */     
/*  95 */     if (maxSingular != 0.0D) {
/*  96 */       for (int k = 0; k < N; k++) {
/*  97 */         double s = S[k];
/*  98 */         if (s < tau) {
/*  99 */           S[k] = 0.0D;
/*     */         } else {
/* 101 */           S[k] = 1.0D / S[k];
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 106 */     for (int j = 0; j < V.numRows; j++) {
/* 107 */       int index = j * V.numCols;
/* 108 */       for (int k = 0; k < V.numCols; k++) {
/* 109 */         V.data[index++] = V.data[index++] * S[k];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 114 */     CommonOps.mult((RowD1Matrix64F)V, (RowD1Matrix64F)U_t, (RowD1Matrix64F)this.pinv);
/*     */     
/* 116 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/* 121 */     throw new IllegalArgumentException("Not supported by this solver.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void solve(DenseMatrix64F b, DenseMatrix64F x) {
/* 126 */     CommonOps.mult((RowD1Matrix64F)this.pinv, (RowD1Matrix64F)b, (RowD1Matrix64F)x);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invert(DenseMatrix64F A_inv) {
/* 131 */     A_inv.set((D1Matrix64F)this.pinv);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 136 */     return this.svd.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThreshold(double threshold) {
/* 149 */     this.threshold = threshold;
/*     */   }
/*     */   
/*     */   public SingularValueDecomposition<DenseMatrix64F> getDecomposer() {
/* 153 */     return this.svd;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\svd\SolvePseudoInverseSvd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */