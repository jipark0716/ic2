/*    */ package ic2.core.crop;
/*    */ 
/*    */ import ic2.api.crops.ICropTile;
/*    */ import net.minecraft.block.BlockCrops;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CropVanilla
/*    */   extends IC2CropCard
/*    */ {
/*    */   protected final int maxAge;
/*    */   
/*    */   protected CropVanilla(BlockCrops block) {
/* 18 */     this(block.func_185526_g());
/*    */   }
/*    */   
/*    */   protected CropVanilla(int maxAge) {
/* 22 */     this.maxAge = maxAge;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 27 */     return "Notch";
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 32 */     return this.maxAge;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 37 */     return (crop.getCurrentSize() < getMaxSize() && crop.getLightLevel() >= 9);
/*    */   }
/*    */   
/*    */   protected abstract ItemStack getSeeds();
/*    */   
/*    */   protected abstract ItemStack getProduct();
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 45 */     return getProduct();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getSeeds(ICropTile crop) {
/* 50 */     if (crop.getStatGain() <= 1 && crop.getStatGrowth() <= 1 && crop.getStatResistance() <= 1) {
/* 51 */       return getSeeds();
/*    */     }
/* 53 */     return super.getSeeds(crop);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\CropVanilla.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */