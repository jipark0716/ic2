/*    */ package ic2.core.crop.cropcard;
/*    */ 
/*    */ import ic2.api.crops.CropProperties;
/*    */ import ic2.api.crops.ICropTile;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.crop.IC2CropCard;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class CropBaseSapling
/*    */   extends IC2CropCard {
/*    */   protected final String cropName;
/*    */   protected final String saplingName;
/*    */   protected final ItemStack cropDrop;
/*    */   protected final ItemStack cropSapling;
/*    */   
/*    */   public CropBaseSapling(String cropName, String saplingName, ItemStack cropDrop, ItemStack cropSapling) {
/* 20 */     this.cropName = cropName;
/* 21 */     this.saplingName = "ic2.crop." + saplingName;
/* 22 */     this.cropDrop = cropDrop;
/* 23 */     this.cropSapling = cropSapling;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 28 */     return this.cropName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSeedType() {
/* 33 */     return this.saplingName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDiscoveredBy() {
/* 38 */     return "Speiger";
/*    */   }
/*    */ 
/*    */   
/*    */   public CropProperties getProperties() {
/* 43 */     return new CropProperties(3, 1, 0, 4, 4, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAttributes() {
/* 48 */     return new String[] { "Leaves", "Sapling", "Green" };
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxSize() {
/* 53 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canGrow(ICropTile crop) {
/* 58 */     return (crop.getCurrentSize() < getMaxSize() && crop.getLightLevel() >= 9);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeHarvested(ICropTile crop) {
/* 63 */     return (crop.getCurrentSize() == 5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOptimalHarvestSize(ICropTile crop) {
/* 68 */     return 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack[] getGains(ICropTile crop) {
/* 73 */     List<ItemStack> drops = new ArrayList<>();
/*    */     
/* 75 */     drops.add(this.cropDrop.func_77946_l());
/* 76 */     if (IC2.random.nextInt(100) >= 75) drops.add(this.cropSapling.func_77946_l()); 
/* 77 */     if (getId().equalsIgnoreCase("oak_sapling") && 
/* 78 */       IC2.random.nextInt(100) >= 75) drops.add(new ItemStack(Items.field_151034_e));
/*    */ 
/*    */     
/* 81 */     return drops.<ItemStack>toArray(new ItemStack[drops.size()]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrowthDuration(ICropTile crop) {
/* 86 */     return (crop.getCurrentSize() >= 4) ? 150 : 600;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAfterHarvest(ICropTile crop) {
/* 91 */     return 4;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropBaseSapling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */