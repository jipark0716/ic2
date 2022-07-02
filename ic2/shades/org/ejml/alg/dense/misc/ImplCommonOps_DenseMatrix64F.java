/*    */ package ic2.shades.org.ejml.alg.dense.misc;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
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
/*    */ public class ImplCommonOps_DenseMatrix64F
/*    */ {
/*    */   public static void extract(DenseMatrix64F src, int srcY0, int srcX0, DenseMatrix64F dst, int dstY0, int dstX0, int numRows, int numCols) {
/* 36 */     for (int y = 0; y < numRows; y++) {
/* 37 */       int indexSrc = src.getIndex(y + srcY0, srcX0);
/* 38 */       int indexDst = dst.getIndex(y + dstY0, dstX0);
/* 39 */       System.arraycopy(src.data, indexSrc, dst.data, indexDst, numCols);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\misc\ImplCommonOps_DenseMatrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */