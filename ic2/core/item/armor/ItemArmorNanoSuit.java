/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.HudMode;
/*     */ import ic2.api.item.IItemHudProvider;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingFallEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemArmorNanoSuit
/*     */   extends ItemArmorElectric
/*     */   implements IItemHudProvider
/*     */ {
/*     */   public ItemArmorNanoSuit(ItemName name, EntityEquipmentSlot armorType) {
/*  38 */     super(name, "nano", armorType, 1000000.0D, 1600.0D, 3);
/*     */     
/*  40 */     if (armorType == EntityEquipmentSlot.FEET) MinecraftForge.EVENT_BUS.register(this);
/*     */   
/*     */   }
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  45 */     if (source == DamageSource.field_76379_h && this.field_77881_a == EntityEquipmentSlot.FEET) {
/*  46 */       int energyPerDamage = getEnergyPerDamage();
/*  47 */       int damageLimit = Integer.MAX_VALUE;
/*  48 */       if (energyPerDamage > 0) damageLimit = (int)Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
/*     */       
/*  50 */       return new ISpecialArmor.ArmorProperties(10, (damage < 8.0D) ? 1.0D : 0.875D, damageLimit);
/*     */     } 
/*  52 */     return super.getProperties(player, armor, source, damage, slot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onEntityLivingFallEvent(LivingFallEvent event) {
/*  61 */     if (IC2.platform.isSimulating() && event.getEntity() instanceof EntityLivingBase) {
/*  62 */       EntityLivingBase entity = (EntityLivingBase)event.getEntity();
/*  63 */       ItemStack armor = entity.func_184582_a(EntityEquipmentSlot.FEET);
/*     */       
/*  65 */       if (armor != null && armor.func_77973_b() == this) {
/*  66 */         int fallDamage = (int)event.getDistance() - 3;
/*  67 */         if (fallDamage >= 8)
/*     */           return; 
/*  69 */         double energyCost = (getEnergyPerDamage() * fallDamage);
/*     */         
/*  71 */         if (energyCost <= ElectricItem.manager.getCharge(armor)) {
/*  72 */           ElectricItem.manager.discharge(armor, energyCost, 2147483647, true, false, false);
/*     */           
/*  74 */           event.setCanceled(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/*  83 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*  84 */     byte toggleTimer = nbtData.func_74771_c("toggleTimer");
/*     */ 
/*     */     
/*  87 */     boolean ret = false;
/*     */     
/*  89 */     if (this.field_77881_a == EntityEquipmentSlot.HEAD) {
/*  90 */       IC2.platform.profilerStartSection("NanoHelmet");
/*     */       
/*  92 */       boolean Nightvision = nbtData.func_74767_n("Nightvision");
/*  93 */       short hubmode = nbtData.func_74765_d("HudMode");
/*     */       
/*  95 */       if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
/*  96 */         toggleTimer = 10;
/*  97 */         Nightvision = !Nightvision;
/*     */         
/*  99 */         if (IC2.platform.isSimulating()) {
/* 100 */           nbtData.func_74757_a("Nightvision", Nightvision);
/*     */           
/* 102 */           if (Nightvision) {
/* 103 */             IC2.platform.messagePlayer(player, "Nightvision enabled.", new Object[0]);
/*     */           } else {
/* 105 */             IC2.platform.messagePlayer(player, "Nightvision disabled.", new Object[0]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 110 */       if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
/* 111 */         toggleTimer = 10;
/* 112 */         if (hubmode == HudMode.getMaxMode()) {
/* 113 */           hubmode = 0;
/*     */         } else {
/* 115 */           hubmode = (short)(hubmode + 1);
/*     */         } 
/*     */         
/* 118 */         if (IC2.platform.isSimulating()) {
/* 119 */           nbtData.func_74777_a("HudMode", hubmode);
/*     */           
/* 121 */           IC2.platform.messagePlayer(player, Localization.translate(HudMode.getFromID(hubmode).getTranslationKey()), new Object[0]);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 126 */       if (IC2.platform.isSimulating() && toggleTimer > 0) {
/* 127 */         toggleTimer = (byte)(toggleTimer - 1); nbtData.func_74774_a("toggleTimer", toggleTimer);
/*     */       } 
/*     */       
/* 130 */       if (Nightvision && IC2.platform.isSimulating() && 
/* 131 */         ElectricItem.manager.use(stack, 1.0D, (EntityLivingBase)player)) {
/* 132 */         BlockPos pos = new BlockPos((int)Math.floor(player.field_70165_t), (int)Math.floor(player.field_70163_u), (int)Math.floor(player.field_70161_v));
/*     */         
/* 134 */         int skylight = player.func_130014_f_().func_175671_l(pos);
/*     */         
/* 136 */         if (skylight > 8) {
/* 137 */           IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76439_r);
/* 138 */           player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 100, 0, true, true));
/*     */         } else {
/* 140 */           IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76440_q);
/* 141 */           player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 300, 0, true, true));
/*     */         } 
/*     */         
/* 144 */         ret = true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 149 */       IC2.platform.profilerEndSection();
/*     */     } 
/*     */     
/* 152 */     if (ret) player.field_71069_bz.func_75142_b();
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamageAbsorptionRatio() {
/* 158 */     return 0.9D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerDamage() {
/* 163 */     return 5000;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 169 */     return EnumRarity.UNCOMMON;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesProvideHUD(ItemStack stack) {
/* 174 */     return (this.field_77881_a == EntityEquipmentSlot.HEAD && ElectricItem.manager.getCharge(stack) > 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public HudMode getHudMode(ItemStack stack) {
/* 179 */     return HudMode.getFromID(StackUtil.getOrCreateNbtData(stack).func_74765_d("HudMode"));
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorNanoSuit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */