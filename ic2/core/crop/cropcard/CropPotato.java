/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropPotato
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 18 */     return "potato";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 23 */     return new CropProperties(2, 0, 4, 0, 0, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 28 */     return new String[] { "Yellow", "Food", "Potato" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 33 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 38 */     return (crop.getCurrentSize() < 4 && crop.getLightLevel() >= 9);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 43 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 48 */     return (crop.getCurrentSize() >= 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 53 */     if (crop.getCurrentSize() >= 4 && IC2.random.nextInt(20) <= 0) {
/* 54 */       return new ItemStack(Items.field_151170_bI);
/*    */     }
/* 56 */     if (crop.getCurrentSize() >= 3) {
/* 57 */       return new ItemStack(Items.field_151174_bG);
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 65 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropPotato.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */