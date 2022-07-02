/*    */ package ic2.shades.org.ejml.alg.dense.mult;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
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
/*    */ public class SubmatrixOps
/*    */ {
/*    */   public static void setSubMatrix(RowD1Matrix64F src, RowD1Matrix64F dst, int srcRow, int srcCol, int dstRow, int dstCol, int numSubRows, int numSubCols) {
/* 35 */     for (int i = 0; i < numSubRows; i++) {
/* 36 */       for (int j = 0; j < numSubCols; j++) {
/* 37 */         double val = src.get(i + srcRow, j + srcCol);
/* 38 */         dst.set(i + dstRow, j + dstCol, val);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\mult\SubmatrixOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */