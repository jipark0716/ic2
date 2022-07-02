/*    */ package ic2.api.crops;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaseSeed
/*    */ {
/*    */   public final CropCard crop;
/*    */   public int size;
/*    */   public int statGrowth;
/*    */   public int statGain;
/*    */   public int statResistance;
/*    */   
/*    */   public BaseSeed(CropCard crop, int size, int statGrowth, int statGain, int statResistance) {
/* 17 */     this.crop = crop;
/* 18 */     this.size = size;
/* 19 */     this.statGrowth = statGrowth;
/* 20 */     this.statGain = statGain;
/* 21 */     this.statResistance = statResistance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public BaseSeed(CropCard crop, int size, int statGrowth, int statGain, int statResistance, int stackSize) {
/* 29 */     this(crop, size, statGrowth, statGain, statResistance);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\BaseSeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */