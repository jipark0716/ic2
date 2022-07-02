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
/*    */ 
/*    */ public class CropHops
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 19 */     return "hops";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 24 */     return new CropProperties(5, 2, 2, 0, 1, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 29 */     return new String[] { "Green", "Ingredient", "Wheat" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 34 */     return 7;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 39 */     return 600;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 44 */     return (crop.getCurrentSize() < 7 && crop.getLightLevel() >= 9);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 49 */     return ItemName.crop_res.getItemStack((Enum)CropResItemType.hops);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 54 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropHops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */