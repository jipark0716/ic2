/*    */ package ic2.shades.org.ejml.alg.dense.linsol.chol;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*    */ import ic2.shades.org.ejml.alg.block.linsol.chol.BlockCholeskyOuterSolver;
/*    */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolver_B64_to_D64;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinearSolverCholBlock64
/*    */   extends LinearSolver_B64_to_D64
/*    */ {
/*    */   public LinearSolverCholBlock64() {
/* 36 */     super((LinearSolver)new BlockCholeskyOuterSolver());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/* 48 */     this.blockB.reshape(B.numRows, B.numCols, false);
/* 49 */     BlockMatrixOps.convert(B, this.blockB);
/*    */ 
/*    */     
/* 52 */     this.alg.solve((Matrix64F)this.blockB, null);
/*    */     
/* 54 */     BlockMatrixOps.convert(this.blockB, X);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\chol\LinearSolverCholBlock64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */