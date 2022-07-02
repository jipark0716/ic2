/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import ic2.core.item.type.CropResItemType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropCoffee
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 18 */     return "coffee";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 23 */     return "Snoochy";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 28 */     return new CropProperties(7, 1, 4, 1, 2, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 33 */     return new String[] { "Leaves", "Ingredient", "Beans" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 38 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 43 */     return (crop.getCurrentSize() < 5 && crop.getLightLevel() >= 9);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
/* 48 */     return (int)(0.4D * humidity + 1.4D * nutrients + 1.2D * air);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 53 */     if (crop.getCurrentSize() == 3) {
/* 54 */       return (int)(super.getGrowthDuration(crop) * 0.5D);
/*    */     }
/* 56 */     if (crop.getCurrentSize() == 4) {
/* 57 */       return (int)(super.getGrowthDuration(crop) * 1.5D);
/*    */     }
/* 59 */     return super.getGrowthDuration(crop);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 64 */     return (crop.getCurrentSize() >= 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 69 */     if (crop.getCurrentSize() == 4) {
/* 70 */       return null;
/*    */     }
/*    */     
/* 73 */     return ItemName.crop_res.getItemStack((Enum)CropResItemType.coffee_beans);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 78 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropCoffee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */