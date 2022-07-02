/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.LinearSolverFactory;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ import ic2.shades.org.ejml.ops.NormOps;
/*     */ import ic2.shades.org.ejml.ops.SpecializedOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenPowerMethod
/*     */ {
/*  57 */   private double tol = 1.0E-10D;
/*     */   
/*     */   private DenseMatrix64F q0;
/*     */   private DenseMatrix64F q1;
/*     */   private DenseMatrix64F q2;
/*  62 */   private int maxIterations = 20;
/*     */ 
/*     */   
/*     */   private DenseMatrix64F B;
/*     */ 
/*     */   
/*     */   private DenseMatrix64F seed;
/*     */ 
/*     */   
/*     */   public EigenPowerMethod(int size) {
/*  72 */     this.q0 = new DenseMatrix64F(size, 1);
/*  73 */     this.q1 = new DenseMatrix64F(size, 1);
/*  74 */     this.q2 = new DenseMatrix64F(size, 1);
/*     */     
/*  76 */     this.B = new DenseMatrix64F(size, size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(DenseMatrix64F seed) {
/*  85 */     this.seed = seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptions(int maxIterations, double tolerance) {
/*  94 */     this.maxIterations = maxIterations;
/*  95 */     this.tol = tolerance;
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
/*     */   public boolean computeDirect(DenseMatrix64F A) {
/* 108 */     initPower(A);
/*     */     
/* 110 */     boolean converged = false;
/*     */     
/* 112 */     for (int i = 0; i < this.maxIterations && !converged; i++) {
/*     */ 
/*     */       
/* 115 */       CommonOps.mult((RowD1Matrix64F)A, (RowD1Matrix64F)this.q0, (RowD1Matrix64F)this.q1);
/* 116 */       double s = NormOps.normPInf(this.q1);
/* 117 */       CommonOps.divide((D1Matrix64F)this.q1, s, (D1Matrix64F)this.q2);
/*     */       
/* 119 */       converged = checkConverged(A);
/*     */     } 
/*     */     
/* 122 */     return converged;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initPower(DenseMatrix64F A) {
/* 127 */     if (A.numRows != A.numCols) {
/* 128 */       throw new IllegalArgumentException("A must be a square matrix.");
/*     */     }
/* 130 */     if (this.seed != null) {
/* 131 */       this.q0.set((D1Matrix64F)this.seed);
/*     */     } else {
/* 133 */       for (int i = 0; i < A.numRows; i++) {
/* 134 */         this.q0.data[i] = 1.0D;
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
/*     */   private boolean checkConverged(DenseMatrix64F A) {
/* 147 */     double worst = 0.0D;
/* 148 */     double worst2 = 0.0D;
/* 149 */     for (int j = 0; j < A.numRows; j++) {
/* 150 */       double val = Math.abs(this.q2.data[j] - this.q0.data[j]);
/* 151 */       if (val > worst) worst = val; 
/* 152 */       val = Math.abs(this.q2.data[j] + this.q0.data[j]);
/* 153 */       if (val > worst2) worst2 = val;
/*     */     
/*     */     } 
/*     */     
/* 157 */     DenseMatrix64F temp = this.q0;
/* 158 */     this.q0 = this.q2;
/* 159 */     this.q2 = temp;
/*     */     
/* 161 */     if (worst < this.tol)
/* 162 */       return true; 
/* 163 */     if (worst2 < this.tol) {
/* 164 */       return true;
/*     */     }
/* 166 */     return false;
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
/*     */   public boolean computeShiftDirect(DenseMatrix64F A, double alpha) {
/* 180 */     SpecializedOps.addIdentity((RowD1Matrix64F)A, (RowD1Matrix64F)this.B, -alpha);
/*     */     
/* 182 */     return computeDirect(this.B);
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
/*     */   public boolean computeShiftInvert(DenseMatrix64F A, double alpha) {
/* 195 */     initPower(A);
/*     */     
/* 197 */     LinearSolver solver = LinearSolverFactory.linear(A.numCols);
/*     */     
/* 199 */     SpecializedOps.addIdentity((RowD1Matrix64F)A, (RowD1Matrix64F)this.B, -alpha);
/* 200 */     solver.setA((Matrix64F)this.B);
/*     */     
/* 202 */     boolean converged = false;
/*     */     
/* 204 */     for (int i = 0; i < this.maxIterations && !converged; i++) {
/* 205 */       solver.solve((Matrix64F)this.q0, (Matrix64F)this.q1);
/* 206 */       double s = NormOps.normPInf(this.q1);
/* 207 */       CommonOps.divide((D1Matrix64F)this.q1, s, (D1Matrix64F)this.q2);
/*     */       
/* 209 */       converged = checkConverged(A);
/*     */     } 
/*     */     
/* 212 */     return converged;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getEigenVector() {
/* 216 */     return this.q0;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\EigenPowerMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */