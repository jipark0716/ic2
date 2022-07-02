/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CropWeed
/*    */   extends IC2CropCard
/*    */ {
/*    */   public String getId() {
/* 21 */     return "weed";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 26 */     return new CropProperties(0, 0, 0, 1, 0, 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 31 */     return new String[] { "Weed", "Bad" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 36 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 41 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onLeftClick(ICropTile crop, EntityPlayer player) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getGain(ICropTile crop) {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 61 */     return 300;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropWeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */