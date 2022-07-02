/*    */ package ic2.shades.org.ejml.alg.dense.linsol.lu;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionBase_D64;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.ops.SpecializedOps;
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
/*    */ public class LinearSolverLuKJI
/*    */   extends LinearSolverLuBase
/*    */ {
/*    */   private double[] dataLU;
/*    */   private int[] pivot;
/*    */   
/*    */   public LinearSolverLuKJI(LUDecompositionBase_D64 decomp) {
/* 39 */     super(decomp);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean setA(DenseMatrix64F A) {
/* 45 */     boolean ret = super.setA(A);
/*    */     
/* 47 */     this.pivot = this.decomp.getPivot();
/* 48 */     this.dataLU = (this.decomp.getLU()).data;
/*    */     
/* 50 */     return ret;
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
/*    */   
/*    */   public void solve(DenseMatrix64F b, DenseMatrix64F x) {
/* 63 */     if (b.numCols != x.numCols || b.numRows != this.numRows || x.numRows != this.numCols) {
/* 64 */       throw new IllegalArgumentException("Unexpected matrix size");
/*    */     }
/*    */     
/* 67 */     if (b != x) {
/* 68 */       SpecializedOps.copyChangeRow(this.pivot, b, x);
/*    */     } else {
/* 70 */       throw new IllegalArgumentException("Current doesn't support using the same matrix instance");
/*    */     } 
/*    */ 
/*    */     
/* 74 */     int nx = b.numCols;
/* 75 */     double[] dataX = x.data;
/*    */     
/*    */     int k;
/* 78 */     for (k = 0; k < this.numCols; k++) {
/* 79 */       for (int i = k + 1; i < this.numCols; i++) {
/* 80 */         for (int j = 0; j < nx; j++) {
/* 81 */           dataX[i * nx + j] = dataX[i * nx + j] - dataX[k * nx + j] * this.dataLU[i * this.numCols + k];
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     for (k = this.numCols - 1; k >= 0; k--) {
/* 87 */       for (int j = 0; j < nx; j++) {
/* 88 */         dataX[k * nx + j] = dataX[k * nx + j] / this.dataLU[k * this.numCols + k];
/*    */       }
/* 90 */       for (int i = 0; i < k; i++) {
/* 91 */         for (int m = 0; m < nx; m++)
/* 92 */           dataX[i * nx + m] = dataX[i * nx + m] - dataX[k * nx + m] * this.dataLU[i * this.numCols + k]; 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\lu\LinearSolverLuKJI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */