/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropBaseMushroom
/*    */   extends IC2CropCard
/*    */ {
/*    */   protected final String cropId;
/*    */   protected final String[] cropAttributes;
/*    */   protected final ItemStack cropDrop;
/*    */   
/*    */   public CropBaseMushroom(String cropId, String[] cropAttributes, ItemStack cropDrop) {
/* 19 */     this.cropId = cropId;
/* 20 */     this.cropAttributes = cropAttributes;
/* 21 */     this.cropDrop = cropDrop;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 26 */     return this.cropId;
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 31 */     return new CropProperties(2, 0, 4, 0, 0, 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 36 */     return this.cropAttributes;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 41 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 46 */     return (crop.getCurrentSize() < getMaxSize() && crop.getStorageWater() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 51 */     return this.cropDrop.func_77946_l();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 56 */     return 200;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropBaseMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */