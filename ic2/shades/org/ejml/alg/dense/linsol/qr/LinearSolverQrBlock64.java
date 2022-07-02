/*    */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.block.linsol.qr.BlockQrHouseHolderSolver;
/*    */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolver_B64_to_D64;
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
/*    */ public class LinearSolverQrBlock64
/*    */   extends LinearSolver_B64_to_D64
/*    */ {
/*    */   public LinearSolverQrBlock64() {
/* 34 */     super((LinearSolver)new BlockQrHouseHolderSolver());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\LinearSolverQrBlock64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */