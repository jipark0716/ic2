/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.item.ElectricItemManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemArmorNightvisionGoggles extends ItemArmorUtility implements IElectricItem, IItemHudInfo {
/*     */   public ItemArmorNightvisionGoggles() {
/*  28 */     super(ItemName.nightvision_goggles, "nightvision", EntityEquipmentSlot.HEAD);
/*     */     
/*  30 */     func_77656_e(27);
/*  31 */     setNoRepair();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack stack) {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  41 */     return 200000.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/*  46 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/*  51 */     return 200.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/*  57 */     List<String> info = new LinkedList<>();
/*  58 */     info.add(ElectricItem.manager.getToolTip(stack));
/*  59 */     return info;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/*  64 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
/*  65 */     boolean active = nbtData.func_74767_n("active");
/*  66 */     byte toggleTimer = nbtData.func_74771_c("toggleTimer");
/*     */     
/*  68 */     if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
/*  69 */       toggleTimer = 10;
/*  70 */       active = !active;
/*     */       
/*  72 */       if (IC2.platform.isSimulating()) {
/*  73 */         nbtData.func_74757_a("active", active);
/*     */         
/*  75 */         if (active) {
/*  76 */           IC2.platform.messagePlayer(player, "Nightvision enabled.", new Object[0]);
/*     */         } else {
/*  78 */           IC2.platform.messagePlayer(player, "Nightvision disabled.", new Object[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     if (IC2.platform.isSimulating() && toggleTimer > 0) {
/*  84 */       toggleTimer = (byte)(toggleTimer - 1);
/*     */       
/*  86 */       nbtData.func_74774_a("toggleTimer", toggleTimer);
/*     */     } 
/*     */     
/*  89 */     boolean ret = false;
/*  90 */     if (active && IC2.platform.isSimulating() && 
/*  91 */       ElectricItem.manager.use(stack, 1.0D, (EntityLivingBase)player)) {
/*  92 */       int skylight = player.func_130014_f_().func_175671_l(new BlockPos((Entity)player));
/*     */       
/*  94 */       if (skylight > 8) {
/*  95 */         IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76439_r);
/*  96 */         player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 100, 0, true, true));
/*     */       } else {
/*  98 */         IC2.platform.removePotion((EntityLivingBase)player, MobEffects.field_76440_q);
/*  99 */         player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 300, 0, true, true));
/*     */       } 
/*     */       
/* 102 */       ret = true;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (ret) player.field_71069_bz.func_75142_b();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 114 */     if (!func_194125_a(tab)) {
/*     */       return;
/*     */     }
/* 117 */     ElectricItemManager.addChargeVariants((Item)this, (List)subItems);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 122 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorNightvisionGoggles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */