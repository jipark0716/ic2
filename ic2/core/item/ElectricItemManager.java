/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItem;
/*     */ import ic2.api.item.IElectricItemManager;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.slot.ArmorSlot;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElectricItemManager
/*     */   implements IElectricItemManager
/*     */ {
/*     */   public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/*  24 */     IElectricItem item = (IElectricItem)stack.func_77973_b();
/*     */     
/*  26 */     assert item.getMaxCharge(stack) > 0.0D;
/*     */     
/*  28 */     if (amount < 0.0D || StackUtil.getSize(stack) > 1 || item.getTier(stack) > tier) return 0.0D;
/*     */     
/*  30 */     if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) amount = item.getTransferLimit(stack);
/*     */     
/*  32 */     NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(stack);
/*  33 */     double newCharge = tNBT.func_74769_h("charge");
/*     */     
/*  35 */     amount = Math.min(amount, item.getMaxCharge(stack) - newCharge);
/*     */     
/*  37 */     if (!simulate) {
/*  38 */       newCharge += amount;
/*     */       
/*  40 */       if (newCharge > 0.0D) {
/*  41 */         tNBT.func_74780_a("charge", newCharge);
/*     */       } else {
/*  43 */         tNBT.func_82580_o("charge");
/*  44 */         if (tNBT.func_82582_d()) stack.func_77982_d(null);
/*     */       
/*     */       } 
/*  47 */       if (stack.func_77973_b() instanceof IElectricItem) {
/*  48 */         item = (IElectricItem)stack.func_77973_b();
/*  49 */         int maxDamage = DamageHandler.getMaxDamage(stack);
/*     */         
/*  51 */         DamageHandler.setDamage(stack, mapChargeLevelToDamage(newCharge, item.getMaxCharge(stack), maxDamage), true);
/*     */       } else {
/*  53 */         DamageHandler.setDamage(stack, 0, true);
/*     */       } 
/*     */     } 
/*     */     
/*  57 */     return amount;
/*     */   }
/*     */   
/*     */   private static int mapChargeLevelToDamage(double charge, double maxCharge, int maxDamage) {
/*  61 */     if (maxDamage < 2) return 0;
/*     */     
/*  63 */     maxDamage--;
/*     */     
/*  65 */     return maxDamage - (int)Util.map(charge, maxCharge, maxDamage);
/*     */   }
/*     */ 
/*     */   
/*     */   public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
/*  70 */     IElectricItem item = (IElectricItem)stack.func_77973_b();
/*     */     
/*  72 */     assert item.getMaxCharge(stack) > 0.0D;
/*     */     
/*  74 */     if (amount < 0.0D || StackUtil.getSize(stack) > 1 || item.getTier(stack) > tier) return 0.0D; 
/*  75 */     if (externally && !item.canProvideEnergy(stack)) return 0.0D;
/*     */     
/*  77 */     if (!ignoreTransferLimit && amount > item.getTransferLimit(stack)) amount = item.getTransferLimit(stack);
/*     */     
/*  79 */     NBTTagCompound tNBT = StackUtil.getOrCreateNbtData(stack);
/*  80 */     double newCharge = tNBT.func_74769_h("charge");
/*     */     
/*  82 */     amount = Math.min(amount, newCharge);
/*     */     
/*  84 */     if (!simulate) {
/*  85 */       newCharge -= amount;
/*     */       
/*  87 */       if (newCharge > 0.0D) {
/*  88 */         tNBT.func_74780_a("charge", newCharge);
/*     */       } else {
/*  90 */         tNBT.func_82580_o("charge");
/*  91 */         if (tNBT.func_82582_d()) stack.func_77982_d(null);
/*     */       
/*     */       } 
/*  94 */       if (stack.func_77973_b() instanceof IElectricItem) {
/*  95 */         item = (IElectricItem)stack.func_77973_b();
/*  96 */         int maxDamage = DamageHandler.getMaxDamage(stack);
/*     */         
/*  98 */         DamageHandler.setDamage(stack, mapChargeLevelToDamage(newCharge, item.getMaxCharge(stack), maxDamage), true);
/*     */       } else {
/* 100 */         DamageHandler.setDamage(stack, 0, true);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCharge(ItemStack stack) {
/* 109 */     return ElectricItem.manager.discharge(stack, Double.POSITIVE_INFINITY, 2147483647, true, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/* 114 */     return ElectricItem.manager.getCharge(stack) + ElectricItem.manager
/* 115 */       .charge(stack, Double.POSITIVE_INFINITY, 2147483647, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse(ItemStack stack, double amount) {
/* 120 */     return (ElectricItem.manager.getCharge(stack) >= amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
/* 125 */     if (entity != null) ElectricItem.manager.chargeFromArmor(stack, entity);
/*     */     
/* 127 */     double transfer = ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, true);
/*     */     
/* 129 */     if (Util.isSimilar(transfer, amount)) {
/* 130 */       ElectricItem.manager.discharge(stack, amount, 2147483647, true, false, false);
/* 131 */       if (entity != null) ElectricItem.manager.chargeFromArmor(stack, entity);
/*     */       
/* 133 */       return true;
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void chargeFromArmor(ItemStack target, EntityLivingBase entity) {
/* 141 */     boolean transferred = false;
/*     */     
/* 143 */     for (EntityEquipmentSlot slot : ArmorSlot.getAll()) {
/* 144 */       int tier; ItemStack source = entity.func_184582_a(slot);
/* 145 */       if (source == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 149 */       if (source.func_77973_b() instanceof IElectricItem) {
/* 150 */         tier = ((IElectricItem)source.func_77973_b()).getTier(target);
/*     */       } else {
/* 152 */         tier = Integer.MAX_VALUE;
/*     */       } 
/*     */ 
/*     */       
/* 156 */       double transfer = ElectricItem.manager.discharge(source, Double.POSITIVE_INFINITY, 2147483647, true, true, true);
/* 157 */       if (transfer <= 0.0D) {
/*     */         continue;
/*     */       }
/* 160 */       transfer = ElectricItem.manager.charge(target, transfer, tier, true, false);
/* 161 */       if (transfer <= 0.0D) {
/*     */         continue;
/*     */       }
/* 164 */       ElectricItem.manager.discharge(source, transfer, 2147483647, true, true, false);
/* 165 */       transferred = true;
/*     */     } 
/*     */     
/* 168 */     if (transferred && entity instanceof EntityPlayer && IC2.platform.isSimulating()) {
/* 169 */       ((EntityPlayer)entity).field_71070_bA.func_75142_b();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getToolTip(ItemStack stack) {
/* 175 */     double charge = ElectricItem.manager.getCharge(stack);
/* 176 */     double space = ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, 2147483647, true, true);
/*     */     
/* 178 */     return Util.toSiString(charge, 3) + "/" + Util.toSiString(charge + space, 3) + " EU";
/*     */   }
/*     */   
/*     */   public static ItemStack getCharged(Item item, double charge) {
/* 182 */     if (!(item instanceof IElectricItem)) throw new IllegalArgumentException("no electric item");
/*     */     
/* 184 */     ItemStack ret = new ItemStack(item);
/* 185 */     ElectricItem.manager.charge(ret, charge, 2147483647, true, false);
/*     */     
/* 187 */     return ret;
/*     */   }
/*     */   
/*     */   public static void addChargeVariants(Item item, List<ItemStack> list) {
/* 191 */     list.add(getCharged(item, 0.0D));
/* 192 */     list.add(getCharged(item, Double.POSITIVE_INFINITY));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/* 197 */     if (stack == null || !(stack.func_77973_b() instanceof IElectricItem)) {
/* 198 */       return 0;
/*     */     }
/* 200 */     return ((IElectricItem)stack.func_77973_b()).getTier(stack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ElectricItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */