/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropRedWheat
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 19 */     return "redwheat";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 24 */     return "raa1337";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 29 */     return new CropProperties(6, 3, 0, 0, 2, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 34 */     return new String[] { "Red", "Redstone", "Wheat" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 39 */     return 7;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 44 */     return (crop.getCurrentSize() < 7 && crop.getLightLevel() <= 10 && crop.getLightLevel() >= 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 49 */     return (crop.getCurrentSize() == 7);
/*    */   }
/*    */ 
/*    */   
/*    */   public double dropGainChance() {
/* 54 */     return 0.5D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 59 */     return 7;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 64 */     BlockPos coords = crop.getPosition();
/* 65 */     if (crop.getWorldObj().func_175687_A(coords) > 0 || (crop.getWorldObj()).field_73012_v.nextBoolean()) {
/* 66 */       return new ItemStack(Items.field_151137_ax, 1);
/*    */     }
/* 68 */     return new ItemStack(Items.field_151015_O, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRedstoneSignalEmitter(ICropTile crop) {
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEmittedRedstoneSignal(ICropTile crop) {
/* 78 */     return (crop.getCurrentSize() == 7) ? 15 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEmittedLight(ICropTile crop) {
/* 83 */     return (crop.getCurrentSize() == 7) ? 7 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 88 */     return 600;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 93 */     return 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropRedWheat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */