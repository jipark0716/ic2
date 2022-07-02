/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import ic2.core.crop.IC2Crops;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropTerraWart
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 20 */     return "terra_wart";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 25 */     return new CropProperties(5, 2, 4, 0, 3, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 30 */     return new String[] { "Blue", "Aether", "Consumable", "Snow" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 35 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public double dropGainChance() {
/* 40 */     return 0.8D;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 45 */     return ItemName.terra_wart.getItemStack();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(ICropTile crop) {
/* 50 */     if (crop.isBlockBelow(Blocks.field_150433_aE)) {
/* 51 */       if (canGrow(crop)) {
/* 52 */         crop.setGrowthPoints(crop.getGrowthPoints() + 100);
/*    */       }
/*    */     }
/* 55 */     else if (crop.isBlockBelow(Blocks.field_150425_aM) && (crop.getWorldObj()).field_73012_v.nextInt(300) == 0) {
/* 56 */       crop.setCrop(IC2Crops.cropNetherWart);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRootsLength(ICropTile crop) {
/* 62 */     return 5;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropTerraWart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */