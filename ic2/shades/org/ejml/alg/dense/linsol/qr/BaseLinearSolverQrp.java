/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverSafe;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.LinearSolverFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRPDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseLinearSolverQrp
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   QRPDecomposition<DenseMatrix64F> decomposition;
/*     */   protected boolean norm2Solution;
/*  75 */   protected DenseMatrix64F Y = new DenseMatrix64F(1, 1);
/*  76 */   protected DenseMatrix64F R = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*  79 */   protected DenseMatrix64F R11 = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*  82 */   protected DenseMatrix64F I = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*     */   protected int rank;
/*     */   
/*  87 */   protected LinearSolver<DenseMatrix64F> internalSolver = LinearSolverFactory.leastSquares(1, 1);
/*     */ 
/*     */   
/*  90 */   private DenseMatrix64F W = new DenseMatrix64F(1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BaseLinearSolverQrp(QRPDecomposition<DenseMatrix64F> decomposition, boolean norm2Solution) {
/* 101 */     this.decomposition = decomposition;
/* 102 */     this.norm2Solution = norm2Solution;
/*     */     
/* 104 */     if (this.internalSolver.modifiesA()) {
/* 105 */       this.internalSolver = (LinearSolver<DenseMatrix64F>)new LinearSolverSafe(this.internalSolver);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/* 110 */     _setA(A);
/*     */     
/* 112 */     if (!this.decomposition.decompose((Matrix64F)A)) {
/* 113 */       return false;
/*     */     }
/* 115 */     this.rank = this.decomposition.getRank();
/*     */     
/* 117 */     this.R.reshape(this.numRows, this.numCols);
/* 118 */     this.decomposition.getR((Matrix64F)this.R, false);
/*     */ 
/*     */     
/* 121 */     this.R11.reshape(this.rank, this.rank);
/* 122 */     CommonOps.extract((ReshapeMatrix64F)this.R, 0, this.rank, 0, this.rank, (ReshapeMatrix64F)this.R11, 0, 0);
/*     */     
/* 124 */     if (this.norm2Solution && this.rank < this.numCols) {
/*     */       
/* 126 */       this.W.reshape(this.rank, this.numCols - this.rank);
/* 127 */       CommonOps.extract((ReshapeMatrix64F)this.R, 0, this.rank, this.rank, this.numCols, (ReshapeMatrix64F)this.W, 0, 0);
/*     */ 
/*     */       
/* 130 */       TriangularSolver.solveU(this.R11.data, 0, this.R11.numCols, this.R11.numCols, this.W.data, 0, this.W.numCols, this.W.numCols);
/*     */ 
/*     */       
/* 133 */       this.W.reshape(this.numCols, this.W.numCols, true);
/*     */       
/* 135 */       for (int i = 0; i < this.numCols - this.rank; i++) {
/* 136 */         for (int j = 0; j < this.numCols - this.rank; j++) {
/* 137 */           if (i == j) {
/* 138 */             this.W.set(i + this.rank, j, -1.0D);
/*     */           } else {
/* 140 */             this.W.set(i + this.rank, j, 0.0D);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/* 150 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.R);
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
/*     */   protected void upgradeSolution(DenseMatrix64F X) {
/* 169 */     DenseMatrix64F z = this.Y;
/*     */ 
/*     */ 
/*     */     
/* 173 */     if (!this.internalSolver.setA((Matrix64F)this.W))
/* 174 */       throw new RuntimeException("This should never happen.  Is input NaN?"); 
/* 175 */     z.reshape(this.numCols - this.rank, 1);
/* 176 */     this.internalSolver.solve((Matrix64F)X, (Matrix64F)z);
/*     */ 
/*     */     
/* 179 */     CommonOps.multAdd(-1.0D, (RowD1Matrix64F)this.W, (RowD1Matrix64F)z, (RowD1Matrix64F)X);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invert(DenseMatrix64F A_inv) {
/* 184 */     if (A_inv.numCols != this.numRows || A_inv.numRows != this.numCols) {
/* 185 */       throw new IllegalArgumentException("Unexpected dimensions for A_inv");
/*     */     }
/* 187 */     this.I.reshape(this.numRows, this.numRows);
/* 188 */     CommonOps.setIdentity((RowD1Matrix64F)this.I);
/*     */     
/* 190 */     solve((Matrix64F)this.I, (Matrix64F)A_inv);
/*     */   }
/*     */   
/*     */   public QRPDecomposition<DenseMatrix64F> getDecomposition() {
/* 194 */     return this.decomposition;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\BaseLinearSolverQrp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */