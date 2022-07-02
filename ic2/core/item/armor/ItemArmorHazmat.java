/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.api.item.IHazmatLike;
/*     */ import ic2.api.util.FluidContainerOutputMode;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.slot.ArmorSlot;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingFallEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ public class ItemArmorHazmat
/*     */   extends ItemArmorUtility
/*     */   implements IHazmatLike {
/*     */   public ItemArmorHazmat(ItemName name, EntityEquipmentSlot type) {
/*  35 */     super(name, "hazmat", type);
/*     */     
/*  37 */     func_77656_e(64);
/*     */     
/*  39 */     if (this.field_77881_a == EntityEquipmentSlot.FEET) MinecraftForge.EVENT_BUS.register(this);
/*     */   
/*     */   }
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  44 */     if (this.field_77881_a == EntityEquipmentSlot.HEAD && hazmatAbsorbs(source) && hasCompleteHazmat(player)) {
/*  45 */       if (source == DamageSource.field_76372_a || source == DamageSource.field_76371_c || source == DamageSource.field_190095_e) {
/*  46 */         player.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 60, 1));
/*     */       }
/*  48 */       return new ISpecialArmor.ArmorProperties(10, 1.0D, 2147483647);
/*  49 */     }  if (this.field_77881_a == EntityEquipmentSlot.FEET && source == DamageSource.field_76379_h) {
/*  50 */       return new ISpecialArmor.ArmorProperties(10, (damage < 8.0D) ? 1.0D : 0.875D, (armor.func_77958_k() - armor.func_77952_i() + 2) * 2 * 25);
/*     */     }
/*  52 */     return new ISpecialArmor.ArmorProperties(0, 0.05D, (armor.func_77958_k() - armor.func_77952_i() + 2) / 2 * 25);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
/*  58 */     if (hazmatAbsorbs(source) && hasCompleteHazmat(entity))
/*     */       return; 
/*  60 */     int damageTotal = damage * 2;
/*  61 */     if (this.field_77881_a == EntityEquipmentSlot.FEET && source == DamageSource.field_76379_h) {
/*  62 */       damageTotal = (damage + 1) / 2;
/*     */     }
/*     */     
/*  65 */     stack.func_77972_a(damageTotal, entity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onEntityLivingFallEvent(LivingFallEvent event) {
/*  73 */     if (IC2.platform.isSimulating() && event.getEntity() instanceof EntityPlayer) {
/*  74 */       EntityPlayer player = (EntityPlayer)event.getEntity();
/*  75 */       ItemStack armor = (ItemStack)player.field_71071_by.field_70460_b.get(0);
/*     */       
/*  77 */       if (armor != null && armor.func_77973_b() == this) {
/*  78 */         int fallDamage = (int)event.getDistance() - 3;
/*  79 */         if (fallDamage >= 8)
/*     */           return; 
/*  81 */         int armorDamage = (fallDamage + 1) / 2;
/*     */         
/*  83 */         if (armorDamage <= armor.func_77958_k() - armor.func_77952_i() && armorDamage >= 0) {
/*  84 */           armor.func_77972_a(armorDamage, (EntityLivingBase)player);
/*     */           
/*  86 */           event.setCanceled(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRepairable() {
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/*  99 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/* 104 */     if (!world.field_72995_K && this.field_77881_a == EntityEquipmentSlot.HEAD) {
/* 105 */       if (player.func_70027_ad() && hasCompleteHazmat((EntityLivingBase)player)) {
/* 106 */         if (isInLava(player)) {
/* 107 */           player.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 20, 0, true, true));
/*     */         }
/*     */         
/* 110 */         player.func_70066_B();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 115 */       int maxAir = 300;
/* 116 */       int refillThreshold = 100;
/* 117 */       int airToMbMul = 1000;
/* 118 */       int airToMbDiv = 150;
/* 119 */       int minAmount = 7;
/*     */       
/* 121 */       int air = player.func_70086_ai();
/*     */       
/* 123 */       if (air <= 100) {
/* 124 */         int needed = (300 - air) * 1000 / 150;
/* 125 */         int supplied = 0;
/*     */         
/* 127 */         for (int i = 0; i < player.field_71071_by.field_70462_a.size() && needed > 0; i++) {
/* 128 */           ItemStack cStack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
/* 129 */           if (cStack != null) {
/*     */             
/* 131 */             LiquidUtil.FluidOperationResult result = LiquidUtil.drainContainer(cStack, FluidName.air.getInstance(), needed, FluidContainerOutputMode.InPlacePreferred);
/* 132 */             if (result != null && result.fluidChange.amount >= 7)
/*     */             {
/* 134 */               if (result.extraOutput == null || 
/* 135 */                 StackUtil.storeInventoryItem(result.extraOutput, player, false)) {
/*     */ 
/*     */                 
/* 138 */                 player.field_71071_by.field_70462_a.set(i, result.inPlaceOutput);
/*     */                 
/* 140 */                 int amount = result.fluidChange.amount;
/* 141 */                 supplied += amount;
/* 142 */                 needed -= amount;
/*     */               }  } 
/*     */           } 
/* 145 */         }  player.func_70050_g(air + supplied * 150 / 1000);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInLava(EntityPlayer player) {
/* 158 */     int x = (int)Math.floor(player.field_70165_t);
/* 159 */     int y = (int)Math.floor(player.field_70163_u + 0.02D);
/* 160 */     int z = (int)Math.floor(player.field_70161_v);
/*     */     
/* 162 */     IBlockState state = player.func_130014_f_().func_180495_p(new BlockPos(x, y, z));
/*     */     
/* 164 */     if (state.func_177230_c() instanceof BlockLiquid && (state.func_185904_a() == Material.field_151587_i || state.func_185904_a() == Material.field_151581_o)) {
/* 165 */       float height = (y + 1) - BlockLiquid.func_149801_b(((Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue());
/*     */       
/* 167 */       return (player.field_70163_u < height);
/*     */     } 
/*     */     
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasCompleteHazmat(EntityLivingBase living) {
/* 182 */     for (EntityEquipmentSlot slot : ArmorSlot.getAll()) {
/*     */       
/* 184 */       ItemStack stack = living.func_184582_a(slot);
/*     */       
/* 186 */       if (stack == null || !(stack.func_77973_b() instanceof IHazmatLike)) {
/* 187 */         return false;
/*     */       }
/*     */       
/* 190 */       IHazmatLike hazmat = (IHazmatLike)stack.func_77973_b();
/* 191 */       if (!hazmat.addsProtection(living, slot, stack)) return false; 
/* 192 */       if (hazmat.fullyProtects(living, slot, stack)) return true;
/*     */     
/*     */     } 
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hazmatAbsorbs(DamageSource source) {
/* 202 */     return (source == DamageSource.field_76372_a || source == DamageSource.field_76368_d || source == DamageSource.field_76371_c || source == DamageSource.field_190095_e || source == DamageSource.field_76370_b || source == IC2DamageSource.electricity || source == IC2DamageSource.radiation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
/* 213 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorHazmat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */