/*    */ package ic2.shades.org.ejml.alg.dense.decomposition.qr;
/*    */ 
/*    */ import ic2.shades.org.ejml.EjmlParameters;
/*    */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*    */ import ic2.shades.org.ejml.alg.block.decomposition.qr.QRDecompositionHouseholder_B64;
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.BaseDecomposition_B64_to_D64;
/*    */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;
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
/*    */ public class QRDecomposition_B64_to_D64
/*    */   extends BaseDecomposition_B64_to_D64
/*    */   implements QRDecomposition<DenseMatrix64F>
/*    */ {
/*    */   public QRDecomposition_B64_to_D64() {
/* 41 */     super((DecompositionInterface)new QRDecompositionHouseholder_B64(), EjmlParameters.BLOCK_WIDTH);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getQ(DenseMatrix64F Q, boolean compact) {
/* 47 */     int minLength = Math.min(this.Ablock.numRows, this.Ablock.numCols);
/* 48 */     if (Q == null) {
/* 49 */       if (compact) {
/* 50 */         Q = new DenseMatrix64F(this.Ablock.numRows, minLength);
/* 51 */         CommonOps.setIdentity((RowD1Matrix64F)Q);
/*    */       } else {
/* 53 */         Q = new DenseMatrix64F(this.Ablock.numRows, this.Ablock.numRows);
/* 54 */         CommonOps.setIdentity((RowD1Matrix64F)Q);
/*    */       } 
/*    */     }
/*    */     
/* 58 */     BlockMatrix64F Qblock = new BlockMatrix64F();
/* 59 */     Qblock.numRows = Q.numRows;
/* 60 */     Qblock.numCols = Q.numCols;
/* 61 */     Qblock.blockLength = this.blockLength;
/* 62 */     Qblock.data = Q.data;
/*    */     
/* 64 */     ((QRDecompositionHouseholder_B64)this.alg).getQ(Qblock, compact);
/*    */     
/* 66 */     convertBlockToRow(Q.numRows, Q.numCols, this.Ablock.blockLength, Q.data);
/*    */     
/* 68 */     return Q;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getR(DenseMatrix64F R, boolean compact) {
/* 75 */     BlockMatrix64F Rblock = ((QRDecompositionHouseholder_B64)this.alg).getR(null, compact);
/*    */     
/* 77 */     if (R == null) {
/* 78 */       R = new DenseMatrix64F(Rblock.numRows, Rblock.numCols);
/*    */     }
/* 80 */     BlockMatrixOps.convert(Rblock, R);
/*    */     
/* 82 */     return R;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QRDecomposition_B64_to_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */