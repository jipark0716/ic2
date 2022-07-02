/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropBaseMetalUncommon
/*    */   extends CropBaseMetalCommon
/*    */ {
/*    */   public CropBaseMetalUncommon(String cropName, String[] cropAttributes, Block[] cropRootsRequirement, ItemStack cropDrop) {
/* 15 */     super(cropName, cropAttributes, cropRootsRequirement, cropDrop);
/*    */   }
/*    */   
/*    */   public CropBaseMetalUncommon(String cropName, String[] cropAttributes, String[] cropRootsRequirement, ItemStack cropDrop) {
/* 19 */     super(cropName, cropAttributes, cropRootsRequirement, cropDrop);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 24 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 29 */     if (crop.getCurrentSize() < 4) {
/* 30 */       return true;
/*    */     }
/*    */     
/* 33 */     if (crop.getCurrentSize() == 4) {
/* 34 */       if (this.cropRootsRequirement == null || this.cropRootsRequirement.length == 0) {
/* 35 */         return true;
/*    */       }
/*    */       
/* 38 */       for (Object aux : this.cropRootsRequirement) {
/* 39 */         if (aux instanceof String && crop.isBlockBelow((String)aux)) {
/* 40 */           return true;
/*    */         }
/* 42 */         if (aux instanceof Block && crop.isBlockBelow((Block)aux)) {
/* 43 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 53 */     return new CropProperties(6, 2, 0, 0, 2, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 58 */     return (crop.getCurrentSize() == 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 63 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public double dropGainChance() {
/* 68 */     return Math.pow(0.95D, getProperties().getTier());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 73 */     if (crop.getCurrentSize() == 4) {
/* 74 */       return 2200;
/*    */     }
/* 76 */     return 750;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropBaseMetalUncommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */