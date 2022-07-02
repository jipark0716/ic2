/*    */ package ic2.shades.org.ejml.alg.dense.linsol.lu;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionBase_D64;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
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
/*    */ public class LinearSolverLu
/*    */   extends LinearSolverLuBase
/*    */ {
/*    */   boolean doImprove = false;
/*    */   
/*    */   public LinearSolverLu(LUDecompositionBase_D64 decomp) {
/* 36 */     super(decomp);
/*    */   }
/*    */   
/*    */   public LinearSolverLu(LUDecompositionBase_D64 decomp, boolean doImprove) {
/* 40 */     super(decomp);
/* 41 */     this.doImprove = doImprove;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void solve(DenseMatrix64F b, DenseMatrix64F x) {
/* 47 */     if (b.numCols != x.numCols || b.numRows != this.numRows || x.numRows != this.numCols) {
/* 48 */       throw new IllegalArgumentException("Unexpected matrix size");
/*    */     }
/*    */     
/* 51 */     int numCols = b.numCols;
/*    */     
/* 53 */     double[] dataB = b.data;
/* 54 */     double[] dataX = x.data;
/*    */     
/* 56 */     double[] vv = this.decomp._getVV();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     for (int j = 0; j < numCols; j++) {
/* 64 */       int index = j; int i;
/* 65 */       for (i = 0; i < this.numCols; ) { vv[i] = dataB[index]; i++; index += numCols; }
/* 66 */        this.decomp._solveVectorInternal(vv);
/* 67 */       index = j;
/* 68 */       for (i = 0; i < this.numCols; ) { dataX[index] = vv[i]; i++; index += numCols; }
/*    */     
/*    */     } 
/* 71 */     if (this.doImprove)
/* 72 */       improveSol(b, x); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\lu\LinearSolverLu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */