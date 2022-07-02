/*    */ package ic2.shades.org.ejml.alg.dense.linsol;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class LinearSolverAbstract
/*    */   implements LinearSolver<DenseMatrix64F>
/*    */ {
/*    */   protected DenseMatrix64F A;
/*    */   protected int numRows;
/*    */   protected int numCols;
/*    */   
/*    */   public DenseMatrix64F getA() {
/* 45 */     return this.A;
/*    */   }
/*    */   
/*    */   protected void _setA(DenseMatrix64F A) {
/* 49 */     this.A = A;
/* 50 */     this.numRows = A.numRows;
/* 51 */     this.numCols = A.numCols;
/*    */   }
/*    */ 
/*    */   
/*    */   public void invert(DenseMatrix64F A_inv) {
/* 56 */     InvertUsingSolve.invert(this, (RowD1Matrix64F)this.A, A_inv);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\LinearSolverAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */