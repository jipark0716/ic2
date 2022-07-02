/*     */ package ic2.core.crop.cropcard;
/*     */ 
/*     */ import ic2.api.crops.CropProperties;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.crop.IC2CropCard;
/*     */ import ic2.core.item.type.CropResItemType;
/*     */ import ic2.core.ref.ItemName;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ 
/*     */ 
/*     */ public class CropVenomilia
/*     */   extends IC2CropCard
/*     */ {
/*     */   public String getId() {
/*  22 */     return "venomilia";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDiscoveredBy() {
/*  27 */     return "raGan";
/*     */   }
/*     */ 
/*     */   
/*     */   public CropProperties getProperties() {
/*  32 */     return new CropProperties(3, 3, 1, 3, 3, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getAttributes() {
/*  37 */     return new String[] { "Purple", "Flower", "Tulip", "Poison" };
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSize() {
/*  42 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  47 */     return ((crop.getCurrentSize() <= 4 && crop.getLightLevel() >= 12) || crop.getCurrentSize() == 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  52 */     return (crop.getCurrentSize() >= 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOptimalHarvestSize(ICropTile crop) {
/*  57 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  62 */     if (crop.getCurrentSize() == 5) {
/*  63 */       return ItemName.crop_res.getItemStack((Enum)CropResItemType.grin_powder);
/*     */     }
/*  65 */     if (crop.getCurrentSize() >= 4) {
/*  66 */       return new ItemStack(Items.field_151100_aR, 1, 5);
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSizeAfterHarvest(ICropTile crop) {
/*  73 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrowthDuration(ICropTile crop) {
/*  78 */     if (crop.getCurrentSize() >= 3) {
/*  79 */       return 600;
/*     */     }
/*  81 */     return 400;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onRightClick(ICropTile crop, EntityPlayer player) {
/*  86 */     if (!player.func_70093_af()) {
/*  87 */       onEntityCollision(crop, (Entity)player);
/*     */     }
/*  89 */     return crop.performManualHarvest();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLeftClick(ICropTile crop, EntityPlayer player) {
/*  94 */     if (!player.func_70093_af()) {
/*  95 */       onEntityCollision(crop, (Entity)player);
/*     */     }
/*  97 */     return crop.pick();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEntityCollision(ICropTile crop, Entity entity) {
/* 102 */     if (crop.getCurrentSize() == 5 && entity instanceof EntityLivingBase) {
/*     */       
/* 104 */       if (entity instanceof EntityPlayer && ((EntityPlayer)entity).func_70093_af() && IC2.random.nextInt(50) != 0) {
/* 105 */         return super.onEntityCollision(crop, entity);
/*     */       }
/* 107 */       ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76436_u, (IC2.random.nextInt(10) + 5) * 20, 0));
/* 108 */       crop.setCurrentSize(4);
/* 109 */       crop.updateState();
/*     */     } 
/* 111 */     return super.onEntityCollision(crop, entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWeed(ICropTile crop) {
/* 116 */     return (crop.getCurrentSize() == 5 && crop.getStatGrowth() >= 8);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropVenomilia.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */