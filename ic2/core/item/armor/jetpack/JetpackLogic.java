/*     */ package ic2.core.item.armor.jetpack;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.audio.AudioSource;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.World;
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
/*     */ public class JetpackLogic
/*     */ {
/*     */   private static boolean lastJetpackUsed;
/*     */   private static AudioSource audioSource;
/*     */   
/*     */   public static boolean useJetpack(EntityPlayer player, boolean hoverMode, IJetpack jetpack, ItemStack stack) {
/*  29 */     if (jetpack.getChargeLevel(stack) <= 0.0D) return false; 
/*  30 */     IBoostingJetpack bjetpack = (jetpack instanceof IBoostingJetpack) ? (IBoostingJetpack)jetpack : null;
/*     */     
/*  32 */     float power = jetpack.getPower(stack);
/*  33 */     float dropPercentage = jetpack.getDropPercentage(stack);
/*     */     
/*  35 */     if (jetpack.getChargeLevel(stack) <= dropPercentage) {
/*  36 */       power = (float)(power * jetpack.getChargeLevel(stack) / dropPercentage);
/*     */     }
/*     */     
/*  39 */     if (IC2.keyboard.isForwardKeyDown(player)) {
/*     */       float retruster, boost;
/*     */       
/*  42 */       if (bjetpack != null) {
/*  43 */         retruster = bjetpack.getBaseThrust(stack, hoverMode);
/*  44 */         boost = bjetpack.getBoostThrust(player, stack, hoverMode);
/*     */       } else {
/*  46 */         retruster = hoverMode ? 1.0F : 0.15F;
/*  47 */         boost = 0.0F;
/*     */       } 
/*     */       
/*  50 */       float forwardpower = power * retruster * 2.0F;
/*     */       
/*  52 */       if (forwardpower > 0.0F) {
/*  53 */         player.func_191958_b(0.0F, 0.0F, 0.4F * forwardpower + boost, 0.02F + boost);
/*     */         
/*  55 */         if (boost != 0.0F && !player.field_70122_E) bjetpack.useBoostPower(stack, boost);
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     int worldHeight = IC2.getWorldHeight(player.func_130014_f_());
/*  61 */     int maxFlightHeight = (int)(worldHeight / jetpack.getWorldHeightDivisor(stack));
/*     */     
/*  63 */     double y = player.field_70163_u;
/*     */     
/*  65 */     if (y > (maxFlightHeight - 25)) {
/*  66 */       if (y > maxFlightHeight) y = maxFlightHeight;
/*     */       
/*  68 */       power = (float)(power * (maxFlightHeight - y) / 25.0D);
/*     */     } 
/*     */     
/*  71 */     double prevmotion = player.field_70181_x;
/*  72 */     player.field_70181_x = Math.min(player.field_70181_x + (power * 0.2F), 0.6000000238418579D);
/*     */     
/*  74 */     if (hoverMode) {
/*     */       
/*  76 */       float maxHoverY = 0.0F;
/*     */       
/*  78 */       if (IC2.keyboard.isJumpKeyDown(player)) {
/*  79 */         maxHoverY += jetpack.getHoverMultiplier(stack, true);
/*  80 */         if (bjetpack != null) maxHoverY *= bjetpack.getHoverBoost(player, stack, true);
/*     */       
/*     */       } 
/*  83 */       if (IC2.keyboard.isSneakKeyDown(player)) {
/*  84 */         maxHoverY += -jetpack.getHoverMultiplier(stack, false);
/*  85 */         if (bjetpack != null) maxHoverY *= bjetpack.getHoverBoost(player, stack, false);
/*     */       
/*     */       } 
/*     */       
/*  89 */       if (player.field_70181_x > maxHoverY) {
/*  90 */         player.field_70181_x = maxHoverY;
/*     */         
/*  92 */         if (prevmotion > player.field_70181_x) player.field_70181_x = prevmotion;
/*     */       
/*     */       } 
/*     */     } 
/*  96 */     int consume = 2;
/*     */     
/*  98 */     if (hoverMode) consume = 1;
/*     */     
/* 100 */     if (!player.field_70122_E) {
/* 101 */       jetpack.drainEnergy(stack, consume);
/*     */     }
/*     */     
/* 104 */     player.field_70143_R = 0.0F;
/* 105 */     player.field_70140_Q = 0.0F;
/*     */     
/* 107 */     IC2.platform.resetPlayerInAirTime(player);
/*     */     
/* 109 */     return true;
/*     */   }
/*     */   
/*     */   public static void onArmorTick(World world, EntityPlayer player, ItemStack stack, IJetpack jetpack) {
/* 113 */     if (stack == null || !jetpack.isJetpackActive(stack))
/*     */       return; 
/* 115 */     NBTTagCompound nbtData = getJetpackCompound(stack);
/* 116 */     boolean hoverMode = getHoverMode(nbtData);
/* 117 */     byte toggleTimer = nbtData.func_74771_c("toggleTimer");
/* 118 */     boolean jetpackUsed = false;
/*     */     
/* 120 */     if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
/* 121 */       toggleTimer = 10;
/* 122 */       hoverMode = !hoverMode;
/*     */       
/* 124 */       if (IC2.platform.isSimulating()) {
/* 125 */         nbtData.func_74757_a("hoverMode", hoverMode);
/*     */         
/* 127 */         if (hoverMode) {
/* 128 */           IC2.platform.messagePlayer(player, "Hover Mode enabled.", new Object[0]);
/*     */         } else {
/* 130 */           IC2.platform.messagePlayer(player, "Hover Mode disabled.", new Object[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     if (IC2.keyboard.isJumpKeyDown(player) || hoverMode) {
/* 136 */       jetpackUsed = useJetpack(player, hoverMode, jetpack, stack);
/* 137 */       if (player.field_70122_E && hoverMode && IC2.platform.isSimulating()) {
/*     */         
/* 139 */         setHoverMode(nbtData, false);
/* 140 */         IC2.platform.messagePlayer(player, "Hover Mode disabled.", new Object[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     if (IC2.platform.isSimulating() && toggleTimer > 0) {
/* 145 */       toggleTimer = (byte)(toggleTimer - 1);
/*     */       
/* 147 */       nbtData.func_74774_a("toggleTimer", toggleTimer);
/*     */     } 
/*     */     
/* 150 */     if (IC2.platform.isRendering() && player == IC2.platform.getPlayerInstance()) {
/* 151 */       if (lastJetpackUsed != jetpackUsed) {
/* 152 */         if (jetpackUsed) {
/* 153 */           if (audioSource == null) audioSource = IC2.audioManager.createSource(player, PositionSpec.Backpack, "Tools/Jetpack/JetpackLoop.ogg", true, false, IC2.audioManager.getDefaultVolume()); 
/* 154 */           if (audioSource != null) audioSource.play();
/*     */         
/* 156 */         } else if (audioSource != null) {
/* 157 */           audioSource.remove();
/* 158 */           audioSource = null;
/*     */         } 
/*     */ 
/*     */         
/* 162 */         lastJetpackUsed = jetpackUsed;
/*     */       } 
/*     */       
/* 165 */       if (audioSource != null) audioSource.updatePosition();
/*     */     
/*     */     } 
/* 168 */     if (jetpackUsed) player.field_71069_bz.func_75142_b(); 
/*     */   }
/*     */   
/*     */   private static void setHoverMode(NBTTagCompound nbt, boolean value) {
/* 172 */     nbt.func_74757_a("hoverMode", value);
/*     */   }
/*     */   private static boolean getHoverMode(NBTTagCompound nbt) {
/* 175 */     return nbt.func_74767_n("hoverMode");
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTTagCompound getJetpackCompound(ItemStack stack) {
/* 180 */     return StackUtil.getOrCreateNbtData(stack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\jetpack\JetpackLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */