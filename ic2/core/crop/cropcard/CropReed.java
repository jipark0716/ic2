/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropReed
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 18 */     return "reed";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 23 */     return "Notch";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 28 */     return new CropProperties(2, 0, 0, 1, 0, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 33 */     return new String[] { "Reed" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 38 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
/* 43 */     return (int)(humidity * 1.2D + nutrients + air * 0.8D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 48 */     return (crop.getCurrentSize() > 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 53 */     return new ItemStack(Items.field_151120_aE, crop.getCurrentSize() - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 63 */     return 200;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */