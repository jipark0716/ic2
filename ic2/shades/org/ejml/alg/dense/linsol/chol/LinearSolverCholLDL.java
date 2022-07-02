/*     */ package ic2.shades.org.ejml.alg.dense.linsol.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionLDL_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
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
/*     */ public class LinearSolverCholLDL
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   private CholeskyDecompositionLDL_D64 decomp;
/*     */   private int n;
/*     */   private double[] vv;
/*     */   private double[] el;
/*     */   private double[] d;
/*     */   
/*     */   public LinearSolverCholLDL(CholeskyDecompositionLDL_D64 decomp) {
/*  40 */     this.decomp = decomp;
/*     */   }
/*     */   
/*     */   public LinearSolverCholLDL() {
/*  44 */     this.decomp = new CholeskyDecompositionLDL_D64();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  49 */     _setA(A);
/*     */     
/*  51 */     if (this.decomp.decompose(A)) {
/*  52 */       this.n = A.numCols;
/*  53 */       this.vv = this.decomp._getVV();
/*  54 */       this.el = (this.decomp.getL()).data;
/*  55 */       this.d = this.decomp.getDiagonal();
/*  56 */       return true;
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double quality() {
/*  64 */     return Math.abs(SpecializedOps.diagProd((RowD1Matrix64F)this.decomp.getL()));
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
/*  84 */     if (B.numCols != X.numCols && B.numRows != this.n && X.numRows != this.n) {
/*  85 */       throw new IllegalArgumentException("Unexpected matrix size");
/*     */     }
/*     */     
/*  88 */     int numCols = B.numCols;
/*     */     
/*  90 */     double[] dataB = B.data;
/*  91 */     double[] dataX = X.data;
/*     */     
/*  93 */     for (int j = 0; j < numCols; j++) {
/*  94 */       int i; for (i = 0; i < this.n; ) { this.vv[i] = dataB[i * numCols + j]; i++; }
/*  95 */        solveInternal();
/*  96 */       for (i = 0; i < this.n; ) { dataX[i * numCols + j] = this.vv[i]; i++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void solveInternal() {
/* 105 */     TriangularSolver.solveL(this.el, this.vv, this.n);
/*     */ 
/*     */     
/* 108 */     for (int i = 0; i < this.n; i++) {
/* 109 */       this.vv[i] = this.vv[i] / this.d[i];
/*     */     }
/*     */ 
/*     */     
/* 113 */     TriangularSolver.solveTranL(this.el, this.vv, this.n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invert(DenseMatrix64F inv) {
/* 123 */     if (inv.numRows != this.n || inv.numCols != this.n) {
/* 124 */       throw new RuntimeException("Unexpected matrix dimension");
/*     */     }
/*     */     
/* 127 */     double[] a = inv.data;
/*     */     
/*     */     int i;
/* 130 */     for (i = 0; i < this.n; i++) {
/* 131 */       for (int j = 0; j <= i; j++) {
/* 132 */         double sum = (i == j) ? 1.0D : 0.0D;
/* 133 */         for (int k = i - 1; k >= j; k--) {
/* 134 */           sum -= this.el[i * this.n + k] * a[j * this.n + k];
/*     */         }
/* 136 */         a[j * this.n + i] = sum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 141 */     for (i = 0; i < this.n; i++) {
/* 142 */       double inv_d = 1.0D / this.d[i];
/* 143 */       for (int j = 0; j <= i; j++) {
/* 144 */         a[j * this.n + i] = a[j * this.n + i] * inv_d;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 149 */     for (i = this.n - 1; i >= 0; i--) {
/* 150 */       for (int j = 0; j <= i; j++) {
/* 151 */         double sum = (i < j) ? 0.0D : a[j * this.n + i];
/* 152 */         for (int k = i + 1; k < this.n; k++) {
/* 153 */           sum -= this.el[k * this.n + i] * a[j * this.n + k];
/*     */         }
/* 155 */         a[j * this.n + i] = sum; a[i * this.n + j] = sum;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 162 */     return this.decomp.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 167 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\chol\LinearSolverCholLDL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */