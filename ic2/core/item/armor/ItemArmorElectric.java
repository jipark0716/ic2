/*     */ package ic2.core.item.armor;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IItemHudInfo;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.BaseElectricItem;
/*     */ import ic2.core.item.ElectricItemManager;
/*     */ import ic2.core.item.IPseudoDamageItem;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraftforge.common.ISpecialArmor;
/*     */ 
/*     */ public abstract class ItemArmorElectric
/*     */   extends ItemArmorIC2 implements ISpecialArmor, IPseudoDamageItem, IElectricItem, IItemHudInfo {
/*     */   protected final double maxCharge;
/*     */   
/*     */   public ItemArmorElectric(ItemName name, String armorName, EntityEquipmentSlot armorType, double maxCharge, double transferLimit, int tier) {
/*  31 */     super(name, ItemArmor.ArmorMaterial.DIAMOND, armorName, armorType, (Object)null);
/*     */     
/*  33 */     this.maxCharge = maxCharge;
/*  34 */     this.tier = tier;
/*  35 */     this.transferLimit = transferLimit;
/*     */     
/*  37 */     func_77656_e(27);
/*  38 */     func_77625_d(1);
/*  39 */     setNoRepair();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final double transferLimit;
/*     */   
/*     */   protected final int tier;
/*     */ 
/*     */   
/*     */   public int func_77619_b() {
/*  49 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_77616_k(ItemStack stack) {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/*  64 */     List<String> info = new LinkedList<>();
/*  65 */     info.add(ElectricItem.manager.getToolTip(stack));
/*  66 */     info.add(Localization.translate("ic2.item.tooltip.PowerTier", new Object[] { Integer.valueOf(this.tier) }));
/*  67 */     return info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/*  75 */     if (!func_194125_a(tab)) {
/*     */       return;
/*     */     }
/*  78 */     ElectricItemManager.addChargeVariants((Item)this, (List)subItems);
/*     */   }
/*     */ 
/*     */   
/*     */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/*  83 */     if (source.func_76363_c()) return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
/*     */     
/*  85 */     double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
/*  86 */     int energyPerDamage = getEnergyPerDamage();
/*     */     
/*  88 */     int damageLimit = Integer.MAX_VALUE;
/*  89 */     if (energyPerDamage > 0) damageLimit = (int)Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage);
/*     */     
/*  91 */     return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/*  96 */     if (ElectricItem.manager.getCharge(armor) >= getEnergyPerDamage()) {
/*  97 */       return (int)Math.round(20.0D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio());
/*     */     }
/*  99 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
/* 104 */     ElectricItem.manager.discharge(stack, (damage * getEnergyPerDamage()), 2147483647, true, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canProvideEnergy(ItemStack stack) {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 114 */     return this.maxCharge;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/* 119 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTransferLimit(ItemStack stack) {
/* 124 */     return this.transferLimit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(ItemStack stack, int damage) {
/* 137 */     int prev = getDamage(stack);
/*     */     
/* 139 */     if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
/* 140 */       IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid armor damage application (%d):", new Object[] { Integer.valueOf(damage - prev) });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStackDamage(ItemStack stack, int damage) {
/* 146 */     super.setDamage(stack, damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double getDamageAbsorptionRatio();
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getEnergyPerDamage();
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double getBaseAbsorptionRatio() {
/* 161 */     switch (this.field_77881_a) {
/*     */       case HEAD:
/* 163 */         return 0.15D;
/*     */       case CHEST:
/* 165 */         return 0.4D;
/*     */       case LEGS:
/* 167 */         return 0.3D;
/*     */       case FEET:
/* 169 */         return 0.15D;
/*     */     } 
/* 171 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */