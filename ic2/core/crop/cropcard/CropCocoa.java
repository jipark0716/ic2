/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropCocoa
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 17 */     return "cocoa";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 22 */     return "Notch";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 27 */     return new CropProperties(3, 1, 3, 0, 4, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 32 */     return new String[] { "Brown", "Food", "Stem" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 37 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 42 */     return (crop.getCurrentSize() <= 3 && crop.getStorageNutrients() >= 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
/* 47 */     return (int)(humidity * 0.8D + nutrients * 1.3D + air * 0.9D);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 52 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 57 */     return (crop.getCurrentSize() == 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 62 */     return new ItemStack(Items.field_151100_aR, 1, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 67 */     if (crop.getCurrentSize() == 3) {
/* 68 */       return 900;
/*    */     }
/* 70 */     return 400;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 75 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropCocoa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */