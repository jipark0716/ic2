/*    */ package ic2.shades.org.ejml.alg.block.decomposition.bidiagonal;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.block.decomposition.qr.BlockHouseHolder;
/*    */ import ic2.shades.org.ejml.data.D1Submatrix64F;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BidiagonalHelper
/*    */ {
/*    */   public static boolean bidiagOuterBlocks(int blockLength, D1Submatrix64F A, double[] gammasU, double[] gammasV) {
/* 47 */     int width = Math.min(blockLength, A.col1 - A.col0);
/* 48 */     int height = Math.min(blockLength, A.row1 - A.row0);
/*    */     
/* 50 */     int min = Math.min(width, height);
/*    */     
/* 52 */     for (int i = 0; i < min; i++) {
/*    */ 
/*    */ 
/*    */       
/* 56 */       if (!BlockHouseHolder.computeHouseHolderCol(blockLength, A, gammasU, i)) {
/* 57 */         return false;
/*    */       }
/*    */       
/* 60 */       BlockHouseHolder.rank1UpdateMultR_Col(blockLength, A, i, gammasU[A.col0 + i]);
/*    */ 
/*    */       
/* 63 */       BlockHouseHolder.rank1UpdateMultR_TopRow(blockLength, A, i, gammasU[A.col0 + i]);
/*    */       
/* 65 */       System.out.println("After column stuff");
/* 66 */       A.original.print();
/*    */ 
/*    */       
/* 69 */       if (!BlockHouseHolder.computeHouseHolderRow(blockLength, A, gammasV, i)) {
/* 70 */         return false;
/*    */       }
/*    */       
/* 73 */       BlockHouseHolder.rank1UpdateMultL_Row(blockLength, A, i, i + 1, gammasV[A.row0 + i]);
/*    */       
/* 75 */       System.out.println("After update row");
/* 76 */       A.original.print();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 83 */       System.out.println("After row stuff");
/* 84 */       A.original.print();
/*    */     } 
/*    */     
/* 87 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\bidiagonal\BidiagonalHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */