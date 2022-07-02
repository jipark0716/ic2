/*    */ package ic2.shades.org.ejml.alg.dense.misc;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImplCommonOps_Matrix64F
/*    */ {
/*    */   public static void extract(Matrix64F src, int srcY0, int srcX0, Matrix64F dst, int dstY0, int dstX0, int numRows, int numCols) {
/* 36 */     for (int y = 0; y < numRows; y++) {
/* 37 */       for (int x = 0; x < numCols; x++) {
/* 38 */         double v = src.get(y + srcY0, x + srcX0);
/* 39 */         dst.set(dstY0 + y, dstX0 + x, v);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\misc\ImplCommonOps_Matrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */