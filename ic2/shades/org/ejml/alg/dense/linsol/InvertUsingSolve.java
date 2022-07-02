/*    */ package ic2.shades.org.ejml.alg.dense.linsol;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*    */ import ic2.shades.org.ejml.ops.CommonOps;
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
/*    */ public class InvertUsingSolve
/*    */ {
/*    */   public static void invert(LinearSolver<DenseMatrix64F> solver, RowD1Matrix64F A, DenseMatrix64F A_inv, DenseMatrix64F storage) {
/* 38 */     if (A.numRows != A_inv.numRows || A.numCols != A_inv.numCols) {
/* 39 */       throw new IllegalArgumentException("A and A_inv must have the same dimensions");
/*    */     }
/*    */     
/* 42 */     CommonOps.setIdentity((RowD1Matrix64F)storage);
/*    */     
/* 44 */     solver.solve((Matrix64F)storage, (Matrix64F)A_inv);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void invert(LinearSolver<DenseMatrix64F> solver, RowD1Matrix64F A, DenseMatrix64F A_inv) {
/* 49 */     if (A.numRows != A_inv.numRows || A.numCols != A_inv.numCols) {
/* 50 */       throw new IllegalArgumentException("A and A_inv must have the same dimensions");
/*    */     }
/*    */     
/* 53 */     CommonOps.setIdentity((RowD1Matrix64F)A_inv);
/*    */     
/* 55 */     solver.solve((Matrix64F)A_inv, (Matrix64F)A_inv);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\InvertUsingSolve.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */