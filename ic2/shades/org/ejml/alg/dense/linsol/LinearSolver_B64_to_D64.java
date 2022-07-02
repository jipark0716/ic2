/*     */ package ic2.shades.org.ejml.alg.dense.linsol;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*     */ import ic2.shades.org.ejml.alg.block.linsol.chol.BlockCholeskyOuterSolver;
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearSolver_B64_to_D64
/*     */   implements LinearSolver<DenseMatrix64F>
/*     */ {
/*  36 */   protected LinearSolver<BlockMatrix64F> alg = (LinearSolver<BlockMatrix64F>)new BlockCholeskyOuterSolver();
/*     */ 
/*     */   
/*  39 */   protected BlockMatrix64F blockA = new BlockMatrix64F(1, 1);
/*     */   
/*  41 */   protected BlockMatrix64F blockB = new BlockMatrix64F(1, 1);
/*     */   
/*  43 */   protected BlockMatrix64F blockX = new BlockMatrix64F(1, 1);
/*     */   
/*     */   public LinearSolver_B64_to_D64(LinearSolver<BlockMatrix64F> alg) {
/*  46 */     this.alg = alg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  57 */     this.blockA.reshape(A.numRows, A.numCols, false);
/*  58 */     BlockMatrixOps.convert(A, this.blockA);
/*     */     
/*  60 */     return this.alg.setA((Matrix64F)this.blockA);
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/*  65 */     return this.alg.quality();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/*  76 */     this.blockB.reshape(B.numRows, B.numCols, false);
/*  77 */     this.blockX.reshape(X.numRows, X.numCols, false);
/*  78 */     BlockMatrixOps.convert(B, this.blockB);
/*     */     
/*  80 */     this.alg.solve((Matrix64F)this.blockB, (Matrix64F)this.blockX);
/*     */     
/*  82 */     BlockMatrixOps.convert(this.blockX, X);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invert(DenseMatrix64F A_inv) {
/*  93 */     this.blockB.reshape(A_inv.numRows, A_inv.numCols, false);
/*     */     
/*  95 */     this.alg.invert((Matrix64F)this.blockB);
/*     */     
/*  97 */     BlockMatrixOps.convert(this.blockB, A_inv);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 107 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\LinearSolver_B64_to_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */