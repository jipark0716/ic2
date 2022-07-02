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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropColorFlower
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String name;
/*    */   public String[] attributes;
/*    */   public int color;
/*    */   
/*    */   public CropColorFlower(String n, String[] a, int c) {
/* 27 */     this.name = n;
/* 28 */     this.attributes = a;
/* 29 */     this.color = c;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 34 */     if (this.name.equals("dandelion") || this.name.equals("rose")) return "Notch"; 
/* 35 */     return "Alblaka";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 40 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 45 */     return new CropProperties(2, 1, 1, 0, 5, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 50 */     return this.attributes;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 55 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 60 */     return (crop.getCurrentSize() <= 3 && crop.getLightLevel() >= 12);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 65 */     return (crop.getCurrentSize() == 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 70 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 75 */     return new ItemStack(Items.field_151100_aR, 1, this.color);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 80 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 85 */     if (crop.getCurrentSize() == 3) return 600; 
/* 86 */     return 400;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropColorFlower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */