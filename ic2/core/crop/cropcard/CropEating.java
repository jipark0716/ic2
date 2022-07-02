/*     */ package ic2.core.crop.cropcard;
/*     */ 
/*     */ import ic2.api.crops.CropProperties;
/*     */ import ic2.api.crops.ICropTile;
/*     */ import ic2.api.item.ItemWrapper;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.crop.IC2CropCard;
/*     */ import ic2.core.util.BiomeUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CropEating
/*     */   extends IC2CropCard
/*     */ {
/*     */   public String getDiscoveredBy() {
/*  37 */     return "Hasudako";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  43 */     return "eatingplant";
/*     */   }
/*     */ 
/*     */   
/*     */   public CropProperties getProperties() {
/*  48 */     return new CropProperties(6, 1, 1, 3, 1, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getAttributes() {
/*  53 */     return new String[] { "Bad", "Food" };
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSize() {
/*  58 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canGrow(ICropTile crop) {
/*  63 */     if (crop.getCurrentSize() < 3) return (crop.getLightLevel() > 10); 
/*  64 */     return (crop.isBlockBelow((Block)Blocks.field_150353_l) && crop.getCurrentSize() < getMaxSize() && crop.getLightLevel() > 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOptimalHarvestSize(ICropTile crop) {
/*  69 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeHarvested(ICropTile crop) {
/*  74 */     return (crop.getCurrentSize() >= 4 && crop.getCurrentSize() < 6);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getGain(ICropTile crop) {
/*  79 */     if (crop.getCurrentSize() >= 4 && crop.getCurrentSize() < 6)
/*  80 */       return new ItemStack((Block)Blocks.field_150434_aF); 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*  85 */   private final double movementMultiplier = 0.5D;
/*  86 */   private final double length = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(ICropTile crop) {
/*  93 */     if (crop.getCurrentSize() == 1)
/*     */       return; 
/*  95 */     BlockPos coords = crop.getPosition();
/*  96 */     double xcentered = coords.func_177958_n() + 0.5D;
/*  97 */     double ycentered = coords.func_177956_o() + 0.5D;
/*  98 */     double zcentered = coords.func_177952_p() + 0.5D;
/*     */     
/* 100 */     if (crop.getCustomData().func_74767_n("eaten")) {
/* 101 */       StackUtil.dropAsEntity(crop.getWorldObj(), coords, new ItemStack(Items.field_151078_bh));
/* 102 */       crop.getCustomData().func_74757_a("eaten", false);
/*     */     } 
/*     */     
/* 105 */     List<EntityLivingBase> list = crop.getWorldObj().func_72872_a(EntityLivingBase.class, new AxisAlignedBB(xcentered - 1.0D, coords
/*     */           
/* 107 */           .func_177956_o(), zcentered - 1.0D, xcentered + 1.0D, coords
/*     */ 
/*     */           
/* 110 */           .func_177956_o() + 1.0D + 1.0D, zcentered + 1.0D));
/*     */     
/* 112 */     if (list.isEmpty()) {
/*     */       return;
/*     */     }
/* 115 */     Collections.shuffle(list);
/* 116 */     for (EntityLivingBase entity : list) {
/* 117 */       if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d)
/*     */         continue; 
/* 119 */       entity.field_70159_w = (xcentered - entity.field_70165_t) * 0.5D;
/* 120 */       entity.field_70179_y = (zcentered - entity.field_70161_v) * 0.5D;
/* 121 */       if (entity.field_70181_x > -0.05D) {
/* 122 */         entity.field_70181_x = -0.05D;
/*     */       }
/* 124 */       entity.func_70097_a((DamageSource)damage, crop.getCurrentSize() * 2.0F);
/* 125 */       if (!hasMetalAromor(entity)) {
/*     */ 
/*     */         
/* 128 */         entity.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 64, 50));
/* 129 */         entity.func_70690_d(new PotionEffect(MobEffects.field_76441_p, 64, 0));
/* 130 */         entity.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 64, 0));
/*     */       } 
/* 132 */       if (canGrow(crop)) {
/* 133 */         crop.setGrowthPoints(crop.getGrowthPoints() + 100);
/*     */       }
/* 135 */       crop.getWorldObj().func_184148_a(null, xcentered, ycentered, zcentered, SoundEvents.field_187537_bA, SoundCategory.BLOCKS, 1.0F, IC2.random.nextFloat() * 0.1F + 0.9F);
/* 136 */       crop.getCustomData().func_74757_a("eaten", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRootsLength(ICropTile crop) {
/* 143 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrowthDuration(ICropTile crop) {
/* 148 */     float multiplier = 1.0F;
/* 149 */     BlockPos coords = crop.getPosition();
/* 150 */     Biome biome = BiomeUtil.getBiome(crop.getWorldObj(), coords);
/* 151 */     if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN))
/* 152 */       multiplier /= 1.5F; 
/* 153 */     multiplier /= 1.0F + crop.getTerrainAirQuality() / 10.0F;
/*     */     
/* 155 */     return (int)(super.getGrowthDuration(crop) * multiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasMetalAromor(EntityLivingBase entity) {
/* 160 */     if (!(entity instanceof EntityPlayer)) return false; 
/* 161 */     EntityPlayer player = (EntityPlayer)entity;
/* 162 */     for (ItemStack stack : player.field_71071_by.field_70460_b) {
/* 163 */       if (stack != null && ItemWrapper.isMetalArmor(stack, player)) {
/* 164 */         return true;
/*     */       }
/*     */     } 
/* 167 */     return false;
/*     */   }
/*     */   
/* 170 */   private static final IC2DamageSource damage = new IC2DamageSource("cropEating");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\cropcard\CropEating.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */