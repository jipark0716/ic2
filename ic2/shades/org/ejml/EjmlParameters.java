/*    */ package ic2.shades.org.ejml;
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
/*    */ public class EjmlParameters
/*    */ {
/* 34 */   public static MemoryUsage MEMORY = MemoryUsage.FASTER;
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
/* 46 */   public static int BLOCK_WIDTH = 60;
/* 47 */   public static int BLOCK_WIDTH_CHOL = 20;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public static int BLOCK_SIZE = BLOCK_WIDTH * BLOCK_WIDTH;
/*    */   
/* 54 */   public static int TRANSPOSE_SWITCH = 375;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public static int MULT_COLUMN_SWITCH = 15;
/* 60 */   public static int MULT_TRANAB_COLUMN_SWITCH = 40;
/* 61 */   public static int MULT_INNER_SWITCH = 100;
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
/* 73 */   public static int SWITCH_BLOCK64_CHOLESKY = 1000;
/*    */   
/* 75 */   public static int SWITCH_BLOCK64_QR = 1500;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum MemoryUsage
/*    */   {
/* 82 */     LOW_MEMORY,
/*    */ 
/*    */ 
/*    */     
/* 86 */     FASTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\EjmlParameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */