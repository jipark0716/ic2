/*     */ package ic2.core.crop.cropcard;
/*     */ 
/*     */ import ic2.api.crops.CropProperties;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.crop.IC2CropCard;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class CropBaseMetalCommon
/*     */   extends IC2CropCard {
/*     */   protected final String cropName;
/*     */   protected final String[] cropAttributes;
/*     */   protected final Object[] cropRootsRequirement;
/*     */   protected final ItemStack cropDrop;
/*     */   
/*     */   public CropBaseMetalCommon(String cropName, String[] cropAttributes, Block[] cropRootsRequirement, ItemStack cropDrop) {
/*  17 */     this.cropName = cropName;
/*  18 */     this.cropAttributes = cropAttributes;
/*  19 */     this.cropRootsRequirement = (Object[])cropRootsRequirement;
/*  20 */     this.cropDrop = cropDrop;
/*     */   }
/*     */   
/*     */   public CropBaseMetalCommon(String cropName, String[] cropAttributes, String[] cropRootsRequirement, ItemStack cropDrop) {
/*  24 */     this.cropName = cropName;
/*  25 */     this.cropAttributes = cropAttributes;
/*  26 */     this.cropRootsRequirement = (Object[])cropRootsRequirement;
/*  27 */     this.cropDrop = cropDrop;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  32 */     return this.cropName;
/*     */   }
/*     */ 
/*     */   
/*     */   public CropProperties getProperties() {
/*  37 */     return new CropProperties(6, 2, 0, 0, 1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getAttributes() {
/*  42 */     return this.cropAttributes;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSize() {
/*  47 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  52 */     if (crop.getCurrentSize() < 3) {
/*  53 */       return true;
/*     */     }
/*     */     
/*  56 */     if (crop.getCurrentSize() == 3) {
/*  57 */       if (this.cropRootsRequirement == null || this.cropRootsRequirement.length == 0) {
/*  58 */         return true;
/*     */       }
/*     */       
/*  61 */       for (Object aux : this.cropRootsRequirement) {
/*  62 */         if (aux instanceof String && crop.isBlockBelow((String)aux)) {
/*  63 */           return true;
/*     */         }
/*  65 */         if (aux instanceof Block && crop.isBlockBelow((Block)aux)) {
/*  66 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRootsLength(ICropTile crop) {
/*  76 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  81 */     return (crop.getCurrentSize() == 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOptimalHarvestSize(ICropTile crop) {
/*  86 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  91 */     return this.cropDrop.func_77946_l();
/*     */   }
/*     */ 
/*     */   
/*     */   public double dropGainChance() {
/*  96 */     return super.dropGainChance() / 2.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrowthDuration(ICropTile crop) {
/* 101 */     return (crop.getCurrentSize() == 3) ? 2000 : 800;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSizeAfterHarvest(ICropTile crop) {
/* 106 */     return 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropBaseMetalCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */