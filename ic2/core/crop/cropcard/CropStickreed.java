/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import ic2.core.item.type.MiscResourceType;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropStickreed
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 21 */     return "stickreed";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 26 */     return "raa1337";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 31 */     return new CropProperties(4, 2, 0, 1, 0, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 36 */     return new String[] { "Reed", "Resin" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 41 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 46 */     return (crop.getCurrentSize() < 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
/* 51 */     return (int)(humidity * 1.2D + nutrients + air * 0.8D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 56 */     return (crop.getCurrentSize() > 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 61 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 66 */     if (crop.getCurrentSize() <= 3) {
/* 67 */       return new ItemStack(Items.field_151120_aE, crop.getCurrentSize() - 1);
/*    */     }
/* 69 */     return ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 74 */     if (crop.getCurrentSize() == 4) return (byte)(3 - IC2.random.nextInt(3)); 
/* 75 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 83 */     if (crop.getCurrentSize() == 4) return 400; 
/* 84 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropStickreed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */