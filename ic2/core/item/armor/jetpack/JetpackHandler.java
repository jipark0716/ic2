/*     */ package ic2.core.item.armor.jetpack;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IBackupElectricItemManager;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingAttackEvent;
/*     */ import net.minecraftforge.event.entity.player.ItemTooltipEvent;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.LoaderState;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
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
/*     */ public class JetpackHandler
/*     */   implements IBackupElectricItemManager
/*     */ {
/*  50 */   private static Map<EntityPlayer, ItemStack> playerArmorBuffer = new WeakHashMap<>();
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static LayerJetpackOverride render;
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static Field renderLayers;
/*     */ 
/*     */   
/*     */   private boolean internalHandlesCheck = false;
/*     */   
/*  63 */   static final ItemStack jetpack = ItemName.jetpack_electric.getItemStack();
/*     */   
/*     */   public static JetpackHandler instance;
/*     */ 
/*     */   
/*     */   private JetpackHandler() {
/*  69 */     MinecraftForge.EVENT_BUS.register(this);
/*  70 */     ElectricItem.registerBackupManager(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*  77 */     if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) throw new IllegalStateException();
/*     */ 
/*     */     
/*  80 */     instance = new JetpackHandler();
/*     */   }
/*     */   
/*     */   public static void setJetpackAttached(ItemStack stack, boolean value) {
/*  84 */     if (stack == null)
/*  85 */       return;  if (!value) {
/*  86 */       if (!stack.func_77942_o())
/*  87 */         return;  stack.func_77978_p().func_82580_o("hasIC2Jetpack");
/*  88 */       if (stack.func_77978_p().func_82582_d()) {
/*  89 */         stack.func_77982_d(null);
/*     */       }
/*     */     }
/*  92 */     else if (EntityLiving.func_184640_d(stack) == EntityEquipmentSlot.CHEST) {
/*  93 */       StackUtil.getOrCreateNbtData(stack).func_74757_a("hasIC2Jetpack", true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasJetpackAttached(ItemStack stack) {
/* 100 */     return (stack != null && 
/* 101 */       EntityLiving.func_184640_d(stack) == EntityEquipmentSlot.CHEST && stack
/* 102 */       .func_77942_o() && stack.func_77978_p().func_74767_n("hasIC2Jetpack"));
/*     */   }
/*     */   
/*     */   public static boolean hasJetpack(ItemStack stack) {
/* 106 */     return (stack != null && (hasJetpackAttached(stack) || stack.func_77973_b() instanceof IJetpack));
/*     */   }
/*     */   
/*     */   public static IJetpack getJetpack(ItemStack stack) {
/* 110 */     assert hasJetpack(stack);
/* 111 */     if (stack.func_77973_b() instanceof IJetpack) {
/* 112 */       return (IJetpack)stack.func_77973_b();
/*     */     }
/* 114 */     return (IJetpack)jetpack.func_77973_b();
/*     */   }
/*     */   
/*     */   public static double getTransferLimit() {
/* 118 */     return ((IElectricItem)jetpack.func_77973_b()).getTransferLimit(jetpack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/* 126 */     if (getTier(stack) > tier) return 0.0D; 
/* 127 */     if (!ignoreTransferLimit) {
/* 128 */       amount = Math.min(amount, getTransferLimit());
/*     */     }
/* 130 */     double charge = stack.func_77942_o() ? stack.func_77978_p().func_74769_h("charge") : 0.0D;
/* 131 */     amount = Math.min(amount, getMaxCharge(stack) - charge);
/* 132 */     if (!simulate) {
/* 133 */       StackUtil.getOrCreateNbtData(stack).func_74780_a("charge", charge + amount);
/*     */     }
/* 135 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
/* 141 */     if (externally || getTier(stack) > tier || !stack.func_77942_o()) return 0.0D; 
/* 142 */     if (!ignoreTransferLimit) {
/* 143 */       amount = Math.min(amount, getTransferLimit());
/*     */     }
/* 145 */     double charge = stack.func_77978_p().func_74769_h("charge");
/* 146 */     amount = Math.min(amount, charge);
/* 147 */     if (!simulate) {
/* 148 */       charge -= amount;
/* 149 */       if (charge == 0.0D) {
/* 150 */         stack.func_77978_p().func_82580_o("charge");
/* 151 */         if (stack.func_77978_p().func_82582_d()) {
/* 152 */           stack.func_77982_d(null);
/*     */         }
/*     */       } else {
/* 155 */         stack.func_77978_p().func_74780_a("charge", charge);
/*     */       } 
/*     */     } 
/* 158 */     return amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCharge(ItemStack stack) {
/* 163 */     return discharge(stack, Double.MAX_VALUE, 2147483647, true, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 168 */     return ElectricItem.manager.getMaxCharge(jetpack.func_77946_l());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse(ItemStack stack, double amount) {
/* 173 */     return ElectricItem.rawManager.canUse(stack, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
/* 178 */     return ElectricItem.rawManager.use(stack, amount, entity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public String getToolTip(ItemStack stack) {
/* 189 */     return ElectricItem.rawManager.getToolTip(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/* 194 */     return ElectricItem.manager.getTier(jetpack.func_77946_l());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean handles(ItemStack stack) {
/* 200 */     if (this.internalHandlesCheck) return false; 
/* 201 */     this.internalHandlesCheck = true;
/*     */     
/* 203 */     boolean handle = (hasJetpackAttached(stack) && ElectricItem.manager.getMaxCharge(stack) <= 0.0D);
/* 204 */     this.internalHandlesCheck = false;
/* 205 */     return handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void tick(TickEvent.PlayerTickEvent event) {
/* 213 */     if (event.phase != TickEvent.Phase.START)
/*     */       return; 
/* 215 */     ItemStack stack = event.player.func_184582_a(EntityEquipmentSlot.CHEST);
/*     */     
/* 217 */     if (hasJetpack(stack)) {
/* 218 */       JetpackLogic.onArmorTick(event.player.func_130014_f_(), event.player, stack, getJetpack(stack));
/*     */     }
/*     */     
/* 221 */     if (playerArmorBuffer.containsKey(event.player)) {
/* 222 */       ItemStack lastStack = playerArmorBuffer.get(event.player);
/*     */       
/* 224 */       if (StackUtil.isEmpty(lastStack) && hasJetpackAttached(lastStack) && StackUtil.isEmpty(stack)) {
/*     */ 
/*     */ 
/*     */         
/* 228 */         ItemStack newJetpack = jetpack.func_77946_l();
/* 229 */         double oldCharge = ElectricItem.manager.getCharge(lastStack);
/* 230 */         ElectricItem.manager.charge(newJetpack, oldCharge, 2147483647, true, false);
/* 231 */         event.player.func_184201_a(EntityEquipmentSlot.CHEST, newJetpack);
/*     */       } 
/* 233 */       playerArmorBuffer.remove(event.player);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   @SubscribeEvent(priority = EventPriority.HIGH)
/*     */   public void tooltip(ItemTooltipEvent event) {
/* 240 */     if (hasJetpackAttached(event.getItemStack())) {
/* 241 */       event.getToolTip().add(TextFormatting.YELLOW + Localization.translate("ic2.jetpackAttached"));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
/*     */   public void livingAttack(LivingAttackEvent event) {
/* 247 */     if (event.getEntityLiving() instanceof EntityPlayer && event.getSource() != null && !event.getSource().func_76363_c()) {
/*     */ 
/*     */       
/* 250 */       EntityPlayer player = (EntityPlayer)event.getEntityLiving();
/* 251 */       ItemStack currentArmor = player.func_184582_a(EntityEquipmentSlot.CHEST);
/* 252 */       if (hasJetpackAttached(currentArmor))
/*     */       {
/* 254 */         playerArmorBuffer.put(player, currentArmor);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void render(RenderLivingEvent.Pre<EntityLivingBase> event) {
/* 262 */     EntityLivingBase entity = event.getEntity();
/* 263 */     if (hasJetpackAttached(entity.func_184582_a(EntityEquipmentSlot.CHEST))) {
/* 264 */       if (render == null) {
/* 265 */         render = new LayerJetpackOverride(event.getRenderer());
/* 266 */         renderLayers = ReflectionUtil.getField(RenderLivingBase.class, List.class);
/*     */       } 
/* 268 */       event.getRenderer().func_177094_a((LayerRenderer)render);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void renderPost(RenderLivingEvent.Post<EntityLivingBase> event) {
/* 275 */     if (render != null)
/* 276 */       ((List)ReflectionUtil.getFieldValue(renderLayers, event.getRenderer())).remove(render); 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\jetpack\JetpackHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */