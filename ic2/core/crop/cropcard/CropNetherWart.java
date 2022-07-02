/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import ic2.core.crop.IC2Crops;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropNetherWart
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 20 */     return "nether_wart";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 25 */     return "Notch";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 30 */     return new CropProperties(5, 4, 2, 0, 2, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 35 */     return new String[] { "Red", "Nether", "Ingredient", "Soulsand" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 40 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double dropGainChance() {
/* 45 */     return 2.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 50 */     return new ItemStack(Items.field_151075_bm, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(ICropTile crop) {
/* 55 */     if (crop.isBlockBelow(Blocks.field_150425_aM)) {
/* 56 */       if (canGrow(crop)) {
/* 57 */         crop.setGrowthPoints(crop.getGrowthPoints() + 100);
/*    */       }
/*    */     }
/* 60 */     else if (crop.isBlockBelow(Blocks.field_150433_aE) && (crop.getWorldObj()).field_73012_v.nextInt(300) == 0) {
/* 61 */       crop.setCrop(IC2Crops.cropTerraWart);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRootsLength(ICropTile crop) {
/* 67 */     return 5;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropNetherWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */