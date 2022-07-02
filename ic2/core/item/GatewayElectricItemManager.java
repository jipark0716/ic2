/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.api.item.IElectricItemManager;
/*     */ import ic2.api.item.ISpecialElectricItem;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GatewayElectricItemManager
/*     */   implements IElectricItemManager
/*     */ {
/*     */   public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/*  18 */     if (StackUtil.isEmpty(stack)) return 0.0D;
/*     */     
/*  20 */     IElectricItemManager manager = getManager(stack);
/*  21 */     if (manager == null) return 0.0D;
/*     */     
/*  23 */     return manager.charge(stack, amount, tier, ignoreTransferLimit, simulate);
/*     */   }
/*     */ 
/*     */   
/*     */   public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
/*  28 */     if (StackUtil.isEmpty(stack)) return 0.0D;
/*     */     
/*  30 */     IElectricItemManager manager = getManager(stack);
/*  31 */     if (manager == null) return 0.0D;
/*     */     
/*  33 */     return manager.discharge(stack, amount, tier, ignoreTransferLimit, externally, simulate);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getCharge(ItemStack stack) {
/*  38 */     if (StackUtil.isEmpty(stack)) return 0.0D;
/*     */     
/*  40 */     IElectricItemManager manager = getManager(stack);
/*  41 */     if (manager == null) return 0.0D;
/*     */     
/*  43 */     return manager.getCharge(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getMaxCharge(ItemStack stack) {
/*  48 */     if (StackUtil.isEmpty(stack)) return 0.0D;
/*     */     
/*  50 */     IElectricItemManager manager = getManager(stack);
/*  51 */     if (manager == null) return 0.0D;
/*     */     
/*  53 */     return manager.getMaxCharge(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse(ItemStack stack, double amount) {
/*  58 */     if (StackUtil.isEmpty(stack)) return false;
/*     */     
/*  60 */     IElectricItemManager manager = getManager(stack);
/*  61 */     if (manager == null) return false;
/*     */     
/*  63 */     return manager.canUse(stack, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
/*  68 */     if (StackUtil.isEmpty(stack)) return false;
/*     */     
/*  70 */     if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d)
/*     */     {
/*  72 */       return canUse(stack, amount);
/*     */     }
/*     */     
/*  75 */     IElectricItemManager manager = getManager(stack);
/*  76 */     if (manager == null) return false;
/*     */     
/*  78 */     return manager.use(stack, amount, entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
/*  83 */     if (StackUtil.isEmpty(stack))
/*  84 */       return;  if (entity == null)
/*     */       return; 
/*  86 */     IElectricItemManager manager = getManager(stack);
/*  87 */     if (manager == null)
/*     */       return; 
/*  89 */     manager.chargeFromArmor(stack, entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getToolTip(ItemStack stack) {
/*  94 */     if (StackUtil.isEmpty(stack)) return null;
/*     */     
/*  96 */     IElectricItemManager manager = getManager(stack);
/*  97 */     if (manager == null) return null;
/*     */     
/*  99 */     return manager.getToolTip(stack);
/*     */   }
/*     */   
/*     */   private IElectricItemManager getManager(ItemStack stack) {
/* 103 */     Item item = stack.func_77973_b();
/* 104 */     if (item == null) return null;
/*     */     
/* 106 */     if (item instanceof ISpecialElectricItem)
/* 107 */       return ((ISpecialElectricItem)item).getManager(stack); 
/* 108 */     if (item instanceof ic2.api.item.IElectricItem) {
/* 109 */       return ElectricItem.rawManager;
/*     */     }
/* 111 */     return (IElectricItemManager)ElectricItem.getBackupManager(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier(ItemStack stack) {
/* 117 */     if (StackUtil.isEmpty(stack)) return 0;
/*     */     
/* 119 */     IElectricItemManager manager = getManager(stack);
/* 120 */     if (manager == null) return 0;
/*     */     
/* 122 */     return manager.getTier(stack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\GatewayElectricItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */