/*     */ package ic2.shades.org.ejml.alg.dense.linsol.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionCommon_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
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
/*     */ public class LinearSolverChol
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   CholeskyDecompositionCommon_D64 decomp;
/*     */   int n;
/*     */   double[] vv;
/*     */   double[] t;
/*     */   
/*     */   public LinearSolverChol(CholeskyDecompositionCommon_D64 decomp) {
/*  39 */     this.decomp = decomp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  44 */     if (A.numRows != A.numCols) {
/*  45 */       throw new IllegalArgumentException("Matrix must be square");
/*     */     }
/*  47 */     _setA(A);
/*     */     
/*  49 */     if (this.decomp.decompose(A)) {
/*  50 */       this.n = A.numCols;
/*  51 */       this.vv = this.decomp._getVV();
/*  52 */       this.t = (this.decomp.getT()).data;
/*  53 */       return true;
/*     */     } 
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double quality() {
/*  61 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.decomp.getT());
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
/*     */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/*  81 */     if (B.numCols != X.numCols || B.numRows != this.n || X.numRows != this.n) {
/*  82 */       throw new IllegalArgumentException("Unexpected matrix size");
/*     */     }
/*     */     
/*  85 */     int numCols = B.numCols;
/*     */     
/*  87 */     double[] dataB = B.data;
/*  88 */     double[] dataX = X.data;
/*     */     
/*  90 */     if (this.decomp.isLower())
/*  91 */     { for (int j = 0; j < numCols; j++) {
/*  92 */         int i; for (i = 0; i < this.n; ) { this.vv[i] = dataB[i * numCols + j]; i++; }
/*  93 */          solveInternalL();
/*  94 */         for (i = 0; i < this.n; ) { dataX[i * numCols + j] = this.vv[i]; i++; }
/*     */       
/*     */       }  }
/*  97 */     else { throw new RuntimeException("Implement"); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solveInternalL() {
/* 106 */     TriangularSolver.solveL(this.t, this.vv, this.n);
/*     */ 
/*     */     
/* 109 */     TriangularSolver.solveTranL(this.t, this.vv, this.n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invert(DenseMatrix64F inv) {
/* 119 */     if (inv.numRows != this.n || inv.numCols != this.n) {
/* 120 */       throw new RuntimeException("Unexpected matrix dimension");
/*     */     }
/* 122 */     if (inv.data == this.t) {
/* 123 */       throw new IllegalArgumentException("Passing in the same matrix that was decomposed.");
/*     */     }
/*     */     
/* 126 */     double[] a = inv.data;
/*     */     
/* 128 */     if (this.decomp.isLower()) {
/* 129 */       setToInverseL(a);
/*     */     } else {
/* 131 */       throw new RuntimeException("Implement");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToInverseL(double[] a) {
/*     */     int i;
/* 143 */     for (i = 0; i < this.n; i++) {
/* 144 */       double el_ii = this.t[i * this.n + i];
/* 145 */       for (int j = 0; j <= i; j++) {
/* 146 */         double sum = (i == j) ? 1.0D : 0.0D;
/* 147 */         for (int k = i - 1; k >= j; k--) {
/* 148 */           sum -= this.t[i * this.n + k] * a[j * this.n + k];
/*     */         }
/* 150 */         a[j * this.n + i] = sum / el_ii;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 155 */     for (i = this.n - 1; i >= 0; i--) {
/* 156 */       double el_ii = this.t[i * this.n + i];
/*     */       
/* 158 */       for (int j = 0; j <= i; j++) {
/* 159 */         double sum = (i < j) ? 0.0D : a[j * this.n + i];
/* 160 */         for (int k = i + 1; k < this.n; k++) {
/* 161 */           sum -= this.t[k * this.n + i] * a[j * this.n + k];
/*     */         }
/* 163 */         a[j * this.n + i] = sum / el_ii; a[i * this.n + j] = sum / el_ii;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 170 */     return this.decomp.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 175 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\chol\LinearSolverChol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */