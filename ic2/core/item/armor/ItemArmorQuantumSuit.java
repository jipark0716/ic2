/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.HudMode;
/*     */ import ic2.api.item.IHazmatLike;
/*     */ import ic2.api.item.IItemHudProvider;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2Potion;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.ItemTinCan;
/*     */ import ic2.core.item.armor.jetpack.IJetpack;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumActionResult;
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
/*     */ public class ItemArmorQuantumSuit
/*     */   extends ItemArmorElectric
/*     */   implements IJetpack, IHazmatLike, IItemHudProvider
/*     */ {
/*     */   private static final int defaultColor = -1;
/*     */   
/*     */   public ItemArmorQuantumSuit(ItemName name, EntityEquipmentSlot armorType) {
/*  51 */     super(name, "quantum", armorType, 1.0E7D, 12000.0D, 4);
/*     */     
/*  53 */     if (armorType == EntityEquipmentSlot.FEET) MinecraftForge.EVENT_BUS.register(this);
/*     */     
/*  55 */     potionRemovalCost.put(MobEffects.field_76436_u, Integer.valueOf(10000));
/*  56 */     potionRemovalCost.put(IC2Potion.radiation, Integer.valueOf(10000));
/*  57 */     potionRemovalCost.put(MobEffects.field_82731_v, Integer.valueOf(25000));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasOverlayTexture() {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82816_b_(ItemStack stack) {
/*  67 */     return (func_82814_b(stack) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_82815_c(ItemStack stack) {
/*  72 */     NBTTagCompound nbt = getDisplayNbt(stack, false);
/*  73 */     if (nbt == null || !nbt.func_150297_b("color", 3))
/*     */       return; 
/*  75 */     nbt.func_82580_o("color");
/*     */     
/*  77 */     if (nbt.func_82582_d()) {
/*  78 */       stack.func_77978_p().func_82580_o("display");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_82814_b(ItemStack stack) {
/*  84 */     NBTTagCompound nbt = getDisplayNbt(stack, false);
/*  85 */     if (nbt == null || !nbt.func_150297_b("color", 3)) return -1;
/*     */     
/*  87 */     return nbt.func_74762_e("color");
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_82813_b(ItemStack stack, int color) {
/*  92 */     NBTTagCompound nbt = getDisplayNbt(stack, true);
/*     */     
/*  94 */     nbt.func_74768_a("color", color);
/*     */   }
/*     */   
/*     */   private NBTTagCompound getDisplayNbt(ItemStack stack, boolean create) {
/*  98 */     NBTTagCompound ret, nbt = stack.func_77978_p();
/*     */     
/* 100 */     if (nbt == null) {
/* 101 */       if (!create) return null;
/*     */       
/* 103 */       nbt = new NBTTagCompound();
/* 104 */       stack.func_77982_d(nbt);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 109 */     if (!nbt.func_150297_b("display", 10)) {
/* 110 */       if (!create) return null;
/*     */       
/* 112 */       ret = new NBTTagCompound();
/* 113 */       nbt.func_74782_a("display", (NBTBase)ret);
/*     */     } else {
/* 115 */       ret = nbt.func_74775_l("display");
/*     */     } 
/*     */     
/* 118 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
/* 123 */     return (ElectricItem.manager.getCharge(stack) > 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase entity, ItemStack armor, DamageSource source, double damage, int slot) {
/* 129 */     int energyPerDamage = getEnergyPerDamage();
/* 130 */     int damageLimit = Integer.MAX_VALUE;
/* 131 */     if (energyPerDamage > 0) damageLimit = (int)Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
/*     */     
/* 133 */     if (source == DamageSource.field_76379_h) {
/*     */       
/* 135 */       if (this.field_77881_a == EntityEquipmentSlot.FEET)
/* 136 */         return new ISpecialArmor.ArmorProperties(10, 1.0D, damageLimit); 
/* 137 */       if (this.field_77881_a == EntityEquipmentSlot.LEGS) {
/* 138 */         return new ISpecialArmor.ArmorProperties(9, 0.8D, damageLimit);
/*     */       }
/*     */     } 
/* 141 */     double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
/* 142 */     return new ISpecialArmor.ArmorProperties(8, absorptionRatio, damageLimit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onEntityLivingFallEvent(LivingFallEvent event) {
/* 150 */     if (IC2.platform.isSimulating() && event.getEntity() instanceof EntityLivingBase) {
/* 151 */       EntityLivingBase entity = (EntityLivingBase)event.getEntity();
/* 152 */       ItemStack armor = entity.func_184582_a(EntityEquipmentSlot.FEET);
/*     */       
/* 154 */       if (armor != null && armor.func_77973_b() == this) {
/* 155 */         int fallDamage = Math.max((int)event.getDistance() - 10, 0);
/* 156 */         double energyCost = (getEnergyPerDamage() * fallDamage);
/* 157 */         if (energyCost <= ElectricItem.manager.getCharge(armor)) {
/* 158 */           ElectricItem.manager.discharge(armor, energyCost, 2147483647, true, false, false);
/* 159 */           event.setCanceled(true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamageAbsorptionRatio() {
/* 167 */     if (this.field_77881_a == EntityEquipmentSlot.CHEST) return 1.2D; 
/* 168 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEnergyPerDamage() {
/* 173 */     return 20000;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/* 179 */     return EnumRarity.RARE; } public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/*     */     int air;
/*     */     boolean Nightvision;
/*     */     short hubmode;
/*     */     boolean enableQuantumSpeedOnSprint;
/* 184 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 186 */     byte toggleTimer = nbtData.func_74771_c("toggleTimer");
/* 187 */     boolean ret = false;
/*     */     
/* 189 */     switch (this.field_77881_a) {
/*     */       case HEAD:
/* 191 */         IC2.platform.profilerStartSection("QuantumHelmet");
/*     */         
/* 193 */         air = player.func_70086_ai();
/*     */         
/* 195 */         if (ElectricItem.manager.canUse(stack, 1000.0D) && air < 100) {
/* 196 */           player.func_70050_g(air + 200);
/*     */           
/* 198 */           ElectricItem.manager.use(stack, 1000.0D, null);
/* 199 */           ret = true;
/* 200 */         } else if (air <= 0) {
/* 201 */           IC2.achievements.issueAchievement(player, "starveWithQHelmet");
/*     */         } 
/*     */         
/* 204 */         if (ElectricItem.manager.canUse(stack, 1000.0D) && player.func_71024_bL().func_75121_c()) {
/* 205 */           int slot = -1;
/* 206 */           for (int i = 0; i < player.field_71071_by.field_70462_a.size(); i++) {
/* 207 */             ItemStack playerStack = (ItemStack)player.field_71071_by.field_70462_a.get(i);
/*     */             
/* 209 */             if (!StackUtil.isEmpty(playerStack) && playerStack.func_77973_b() == ItemName.filled_tin_can.getInstance()) {
/* 210 */               slot = i;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 215 */           if (slot > -1) {
/* 216 */             ItemStack playerStack = (ItemStack)player.field_71071_by.field_70462_a.get(slot);
/* 217 */             ItemTinCan can = (ItemTinCan)playerStack.func_77973_b();
/* 218 */             ActionResult<ItemStack> result = can.onEaten(player, playerStack);
/* 219 */             playerStack = (ItemStack)result.func_188398_b();
/*     */             
/* 221 */             if (StackUtil.isEmpty(playerStack)) player.field_71071_by.field_70462_a.set(slot, StackUtil.emptyStack);
/*     */             
/* 223 */             if (result.func_188397_a() == EnumActionResult.SUCCESS) {
/* 224 */               ElectricItem.manager.use(stack, 1000.0D, null);
/*     */             }
/*     */             
/* 227 */             ret = true;
/*     */           } 
/* 229 */         } else if (player.func_71024_bL().func_75116_a() <= 0) {
/* 230 */           IC2.achievements.issueAchievement(player, "starveWithQHelmet");
/*     */         } 
/*     */         
/* 233 */         for (PotionEffect effect : new LinkedList(player.func_70651_bq())) {
/* 234 */           Potion potion = effect.func_188419_a();
/*     */           
/* 236 */           Integer cost = potionRemovalCost.get(potion);
/*     */           
/* 238 */           if (cost != null) {
/* 239 */             cost = Integer.valueOf(cost.intValue() * (effect.func_76458_c() + 1));
/*     */             
/* 241 */             if (ElectricItem.manager.canUse(stack, cost.intValue())) {
/* 242 */               ElectricItem.manager.use(stack, cost.intValue(), null);
/* 243 */               IC2.platform.removePotion((EntityLivingBase)player, potion);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 249 */         Nightvision = nbtData.func_74767_n("Nightvision");
/* 250 */         hubmode = nbtData.func_74765_d("HudMode");
/*     */         
/* 252 */         if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
/* 253 */           toggleTimer = 10;
/* 254 */           Nightvision = !Nightvision;
/*     */           
/* 256 */           if (IC2.platform.isSimulating()) {
/* 257 */             nbtData.func_74757_a("Nightvision", Nightvision);
/*     */             
/* 259 */             if (Nightvision) {
/* 260 */               IC2.platform.messagePlayer(player, "Nightvision enabled.", new Object[0]);
/*     */             } else {
/* 262 */               IC2.platform.messagePlayer(player, "Nightvision disabled.", new Object[0]);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 267 */         if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
/* 268 */           toggleTimer = 10;
/* 269 */           if (hubmode == HudMode.getMaxMode()) {
/* 270 */             hubmode = 0;
/*     */           } else {
/* 272 */             hubmode = (short)(hubmode + 1);
/*     */           } 
/*     */           
/* 275 */           if (IC2.platform.isSimulating()) {
/* 276 */             nbtData.func_74777_a("HudMode", hubmode);
/*     */             
/* 278 */             IC2.platform.messagePlayer(player, Localization.translate(HudMode.getFromID(hubmode).getTranslationKey()), new Object[0]);
/*     */           } 
/*     */         } 
/*     */         
/* 282 */         if (IC2.platform.isSimulating() && toggleTimer > 0) {
/* 283 */           toggleTimer = (byte)(toggleTimer - 1); nbtData.func_74774_a("toggleTimer", toggleTimer);
/*     */         } 
/*     */         
/* 286 */         if (Nightvision && IC2.platform.isSimulating() && 
/* 287 */           ElectricItem.manager.use(stack, 1.0D, (EntityLivingBase)player)) {
/* 288 */           BlockPos pos = new BlockPos((int)Math.floor(player.field_70165_t), (int)Math.floor(player.field_70163_u), (int)Math.floor(player.field_70161_v));
/*     */           
/* 290 */           int skylight = player.func_130014_f_().func_175671_l(pos);
/*     */           
/* 292 */           if (skylight > 8) {
/* 293 */             IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76439_r);
/* 294 */             player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 100, 0, true, true));
/*     */           } else {
/* 296 */             IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76440_q);
/* 297 */             player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 300, 0, true, true));
/*     */           } 
/*     */           
/* 300 */           ret = true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 305 */         IC2.platform.profilerEndSection();
/*     */         break;
/*     */       
/*     */       case CHEST:
/* 309 */         IC2.platform.profilerStartSection("QuantumBodyarmor");
/* 310 */         player.func_70066_B();
/*     */ 
/*     */         
/* 313 */         IC2.platform.profilerEndSection();
/*     */         break;
/*     */       
/*     */       case LEGS:
/* 317 */         IC2.platform.profilerStartSection("QuantumLeggings");
/*     */ 
/*     */ 
/*     */         
/* 321 */         if (IC2.platform.isRendering()) {
/* 322 */           enableQuantumSpeedOnSprint = ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint");
/*     */         } else {
/* 324 */           enableQuantumSpeedOnSprint = true;
/*     */         } 
/*     */         
/* 327 */         if (ElectricItem.manager.canUse(stack, 1000.0D) && (player.field_70122_E || player
/* 328 */           .func_70090_H()) && IC2.keyboard
/* 329 */           .isForwardKeyDown(player) && ((enableQuantumSpeedOnSprint && player
/* 330 */           .func_70051_ag()) || (!enableQuantumSpeedOnSprint && IC2.keyboard
/* 331 */           .isBoostKeyDown(player)))) {
/* 332 */           byte speedTicker = nbtData.func_74771_c("speedTicker");
/* 333 */           speedTicker = (byte)(speedTicker + 1);
/*     */           
/* 335 */           if (speedTicker >= 10) {
/* 336 */             speedTicker = 0;
/* 337 */             ElectricItem.manager.use(stack, 1000.0D, null);
/* 338 */             ret = true;
/*     */           } 
/*     */           
/* 341 */           nbtData.func_74774_a("speedTicker", speedTicker);
/*     */           
/* 343 */           float speed = 0.22F;
/*     */           
/* 345 */           if (player.func_70090_H()) {
/* 346 */             speed = 0.1F;
/*     */             
/* 348 */             if (IC2.keyboard.isJumpKeyDown(player)) player.field_70181_x += 0.10000000149011612D;
/*     */           
/*     */           } 
/* 351 */           if (speed > 0.0F) player.func_191958_b(0.0F, 0.0F, 1.0F, speed);
/*     */         
/*     */         } 
/* 354 */         IC2.platform.profilerEndSection();
/*     */         break;
/*     */       
/*     */       case FEET:
/* 358 */         IC2.platform.profilerStartSection("QuantumBoots");
/*     */         
/* 360 */         if (IC2.platform.isSimulating()) {
/* 361 */           boolean wasOnGround = nbtData.func_74764_b("wasOnGround") ? nbtData.func_74767_n("wasOnGround") : true;
/*     */           
/* 363 */           if (wasOnGround && !player.field_70122_E && IC2.keyboard
/*     */             
/* 365 */             .isJumpKeyDown(player) && IC2.keyboard
/* 366 */             .isBoostKeyDown(player)) {
/* 367 */             ElectricItem.manager.use(stack, 4000.0D, null);
/* 368 */             ret = true;
/*     */           } 
/*     */           
/* 371 */           if (player.field_70122_E != wasOnGround) nbtData.func_74757_a("wasOnGround", player.field_70122_E); 
/*     */         } else {
/* 373 */           if (ElectricItem.manager.canUse(stack, 4000.0D) && player.field_70122_E) this.jumpCharge = 1.0F;
/*     */           
/* 375 */           if (player.field_70181_x >= 0.0D && this.jumpCharge > 0.0F && !player.func_70090_H()) {
/* 376 */             if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
/* 377 */               if (this.jumpCharge == 1.0F) {
/* 378 */                 player.field_70159_w *= 3.5D;
/* 379 */                 player.field_70179_y *= 3.5D;
/*     */               } 
/*     */               
/* 382 */               player.field_70181_x += (this.jumpCharge * 0.3F);
/* 383 */               this.jumpCharge = (float)(this.jumpCharge * 0.75D);
/*     */             }
/* 385 */             else if (this.jumpCharge < 1.0F) {
/* 386 */               this.jumpCharge = 0.0F;
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 395 */         IC2.platform.profilerEndSection();
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 401 */     if (ret) player.field_71069_bz.func_75142_b();
/*     */   
/*     */   }
/*     */   
/*     */   public int func_77619_b() {
/* 406 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drainEnergy(ItemStack pack, int amount) {
/* 413 */     return (ElectricItem.manager.discharge(pack, (amount + 6), 2147483647, true, false, false) > 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPower(ItemStack stack) {
/* 418 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getDropPercentage(ItemStack stack) {
/* 423 */     return 0.05F;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getChargeLevel(ItemStack stack) {
/* 428 */     return ElectricItem.manager.getCharge(stack) / getMaxCharge(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isJetpackActive(ItemStack stack) {
/* 433 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHoverMultiplier(ItemStack stack, boolean upwards) {
/* 438 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWorldHeightDivisor(ItemStack stack) {
/* 443 */     return 0.9F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesProvideHUD(ItemStack stack) {
/* 449 */     return (this.field_77881_a == EntityEquipmentSlot.HEAD && ElectricItem.manager.getCharge(stack) > 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public HudMode getHudMode(ItemStack stack) {
/* 454 */     return HudMode.getFromID(StackUtil.getOrCreateNbtData(stack).func_74765_d("HudMode"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 459 */   protected static final Map<Potion, Integer> potionRemovalCost = new IdentityHashMap<>();
/*     */   private float jumpCharge;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorQuantumSuit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */