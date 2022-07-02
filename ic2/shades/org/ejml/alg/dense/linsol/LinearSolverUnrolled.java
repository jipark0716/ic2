/*    */ package ic2.shades.org.ejml.alg.dense.linsol;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.dense.misc.UnrolledInverseFromMinor;
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
/*    */ 
/*    */ public class LinearSolverUnrolled
/*    */   implements LinearSolver<DenseMatrix64F>
/*    */ {
/*    */   DenseMatrix64F A;
/*    */   
/*    */   public boolean setA(DenseMatrix64F A) {
/* 37 */     if (A.numRows != A.numCols) {
/* 38 */       return false;
/*    */     }
/* 40 */     this.A = A;
/* 41 */     return (A.numRows <= 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public double quality() {
/* 46 */     throw new IllegalArgumentException("Not supported by this solver.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/* 51 */     throw new RuntimeException("Not supported");
/*    */   }
/*    */ 
/*    */   
/*    */   public void invert(DenseMatrix64F A_inv) {
/* 56 */     if (this.A.numRows == 1)
/* 57 */       A_inv.set(0, 1.0D / this.A.get(0)); 
/* 58 */     UnrolledInverseFromMinor.inv(this.A, A_inv);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifiesA() {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifiesB() {
/* 68 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\LinearSolverUnrolled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */