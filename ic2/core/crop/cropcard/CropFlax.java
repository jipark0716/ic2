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
/*    */ public class CropFlax
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 16 */     return "flax";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 21 */     return "Eloraam";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 26 */     return new CropProperties(2, 1, 1, 2, 0, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 31 */     return new String[] { "Silk", "Vine", "Addictive" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 36 */     return 4;
/*    */   }
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 40 */     return (crop.getCurrentSize() < 4 && crop.getLightLevel() >= 9);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 45 */     return new ItemStack(Items.field_151007_F);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 50 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropFlax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */