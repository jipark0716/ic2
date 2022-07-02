/*    */ package ic2.shades.org.ejml.alg.dense.decomposition.chol;
/*    */ 
/*    */ import ic2.shades.org.ejml.EjmlParameters;
/*    */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*    */ import ic2.shades.org.ejml.alg.block.decomposition.chol.CholeskyOuterForm_B64;
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.BaseDecomposition_B64_to_D64;
/*    */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.CholeskyDecomposition;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;
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
/*    */ public class CholeskyDecomposition_B64_to_D64
/*    */   extends BaseDecomposition_B64_to_D64
/*    */   implements CholeskyDecomposition<DenseMatrix64F>
/*    */ {
/*    */   public CholeskyDecomposition_B64_to_D64(boolean lower) {
/* 40 */     super((DecompositionInterface)new CholeskyOuterForm_B64(lower), EjmlParameters.BLOCK_WIDTH);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLower() {
/* 45 */     return ((CholeskyOuterForm_B64)this.alg).isLower();
/*    */   }
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getT(DenseMatrix64F T) {
/* 50 */     BlockMatrix64F T_block = ((CholeskyOuterForm_B64)this.alg).getT(null);
/*    */     
/* 52 */     if (T == null) {
/* 53 */       T = new DenseMatrix64F(T_block.numRows, T_block.numCols);
/*    */     }
/*    */     
/* 56 */     BlockMatrixOps.convert(T_block, T);
/*    */     
/* 58 */     return T;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\chol\CholeskyDecomposition_B64_to_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */