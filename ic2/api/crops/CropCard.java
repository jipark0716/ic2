/*     */ package ic2.api.crops;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CropCard
/*     */ {
/*     */   public abstract String getId();
/*     */   
/*     */   public abstract String getOwner();
/*     */   
/*     */   public String getUnlocalizedName() {
/*  66 */     return getOwner() + ".crop." + getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscoveredBy() {
/*  75 */     return "unknown";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String desc(int i) {
/*  86 */     String[] att = getAttributes();
/*     */     
/*  88 */     if (att == null || att.length == 0) return "";
/*     */     
/*  90 */     if (i == 0) {
/*  91 */       String str = att[0];
/*  92 */       if (att.length >= 2) {
/*  93 */         str = str + ", " + att[1];
/*  94 */         if (att.length >= 3) str = str + ","; 
/*     */       } 
/*  96 */       return str;
/*     */     } 
/*  98 */     if (att.length < 3) return ""; 
/*  99 */     String s = att[2];
/* 100 */     if (att.length >= 4) s = s + ", " + att[3]; 
/* 101 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRootsLength(ICropTile cropTile) {
/* 110 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract CropProperties getProperties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAttributes() {
/* 125 */     return new String[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeedType() {
/* 135 */     return "ic2.crop.seeds";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getMaxSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGrowthDuration(ICropTile cropTile) {
/* 150 */     return getProperties().getTier() * 200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile cropTile) {
/* 168 */     return (cropTile.getCurrentSize() < getMaxSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWeightInfluences(ICropTile crop, int humidity, int nutrients, int air) {
/* 185 */     return humidity + nutrients + air;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCross(ICropTile crop) {
/* 195 */     return (crop.getCurrentSize() >= 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onRightClick(ICropTile cropTile, EntityPlayer player) {
/* 210 */     return cropTile.performManualHarvest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOptimalHarvestSize(ICropTile cropTile) {
/* 221 */     return getMaxSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile cropTile) {
/* 231 */     return (cropTile.getCurrentSize() == getMaxSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double dropGainChance() {
/* 241 */     return Math.pow(0.95D, getProperties().getTier());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemStack getGain(ICropTile crop) {
/* 253 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack[] getGains(ICropTile crop) {
/* 263 */     return new ItemStack[] { getGain(crop) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeAfterHarvest(ICropTile cropTile) {
/* 274 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLeftClick(ICropTile cropTile, EntityPlayer player) {
/* 289 */     return cropTile.pick();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dropSeedChance(ICropTile crop) {
/* 300 */     if (crop.getCurrentSize() == 1) return 0.0F; 
/* 301 */     float base = 0.5F;
/* 302 */     if (crop.getCurrentSize() == 2) base /= 2.0F; 
/* 303 */     for (int i = 0; i < getProperties().getTier(); i++) {
/* 304 */       base = (float)(base * 0.8D);
/*     */     }
/* 306 */     return base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSeeds(ICropTile crop) {
/* 318 */     return crop.generateSeeds(crop.getCrop(), crop.getStatGrowth(), crop.getStatGain(), crop.getStatResistance(), crop.getScanLevel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighbourChange(ICropTile crop) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRedstoneSignalEmitter(ICropTile cropTile) {
/* 336 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEmittedRedstoneSignal(ICropTile cropTile) {
/* 345 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyed(ICropTile crop) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEmittedLight(ICropTile crop) {
/* 364 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 375 */     return (entity instanceof net.minecraft.entity.EntityLivingBase && entity.func_70051_ag());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(ICropTile cropTile) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWeed(ICropTile cropTile) {
/* 397 */     return (cropTile.getCurrentSize() >= 2 && (cropTile.getCrop() == Crops.weed || cropTile.getStatGrowth() >= 24));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld(ICropTile cropTile) {
/* 406 */     return cropTile.getWorldObj();
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public abstract List<ResourceLocation> getTexturesLocation();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\crops\CropCard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */