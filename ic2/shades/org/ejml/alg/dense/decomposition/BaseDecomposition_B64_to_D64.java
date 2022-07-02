/*    */ package ic2.shades.org.ejml.alg.dense.decomposition;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*    */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
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
/*    */ 
/*    */ 
/*    */ public class BaseDecomposition_B64_to_D64
/*    */   implements DecompositionInterface<DenseMatrix64F>
/*    */ {
/*    */   protected DecompositionInterface<BlockMatrix64F> alg;
/*    */   protected double[] tmp;
/* 38 */   protected BlockMatrix64F Ablock = new BlockMatrix64F();
/*    */   
/*    */   protected int blockLength;
/*    */   
/*    */   public BaseDecomposition_B64_to_D64(DecompositionInterface<BlockMatrix64F> alg, int blockLength) {
/* 43 */     this.alg = alg;
/* 44 */     this.blockLength = blockLength;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean decompose(DenseMatrix64F A) {
/* 49 */     this.Ablock.numRows = A.numRows;
/* 50 */     this.Ablock.numCols = A.numCols;
/* 51 */     this.Ablock.blockLength = this.blockLength;
/* 52 */     this.Ablock.data = A.data;
/*    */     
/* 54 */     int tmpLength = Math.min(this.Ablock.blockLength, A.numRows) * A.numCols;
/*    */     
/* 56 */     if (this.tmp == null || this.tmp.length < tmpLength) {
/* 57 */       this.tmp = new double[tmpLength];
/*    */     }
/*    */ 
/*    */     
/* 61 */     BlockMatrixOps.convertRowToBlock(A.numRows, A.numCols, this.Ablock.blockLength, A.data, this.tmp);
/*    */     
/* 63 */     boolean ret = this.alg.decompose((Matrix64F)this.Ablock);
/*    */ 
/*    */     
/* 66 */     if (!this.alg.inputModified()) {
/* 67 */       BlockMatrixOps.convertBlockToRow(A.numRows, A.numCols, this.Ablock.blockLength, A.data, this.tmp);
/*    */     }
/*    */     
/* 70 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void convertBlockToRow(int numRows, int numCols, int blockLength, double[] data) {
/* 75 */     int tmpLength = Math.min(blockLength, numRows) * numCols;
/*    */     
/* 77 */     if (this.tmp == null || this.tmp.length < tmpLength) {
/* 78 */       this.tmp = new double[tmpLength];
/*    */     }
/* 80 */     BlockMatrixOps.convertBlockToRow(numRows, numCols, this.Ablock.blockLength, data, this.tmp);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean inputModified() {
/* 85 */     return this.alg.inputModified();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\BaseDecomposition_B64_to_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */