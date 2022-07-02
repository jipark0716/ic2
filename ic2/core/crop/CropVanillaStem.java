/*    */ package ic2.core.crop;
/*    */ 
/*    */ import ic2.api.crops.ICropTile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CropVanillaStem
/*    */   extends CropVanilla
/*    */ {
/*    */   protected CropVanillaStem(int maxAge) {
/* 12 */     super(maxAge);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
/* 17 */     return (int)(humidity * 1.1D + nutrients * 0.9D + air);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 22 */     return this.maxAge - 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\CropVanillaStem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */