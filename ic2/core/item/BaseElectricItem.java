/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.api.item.IElectricItem;
/*    */ import ic2.api.item.IItemHudInfo;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import ic2.core.util.LogCategory;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BaseElectricItem
/*    */   extends ItemIC2
/*    */   implements IPseudoDamageItem, IElectricItem, IItemHudInfo
/*    */ {
/*    */   public BaseElectricItem(ItemName name, double maxCharge, double transferLimit, int tier) {
/* 36 */     super(name);
/*    */     
/* 38 */     this.maxCharge = maxCharge;
/* 39 */     this.transferLimit = transferLimit;
/* 40 */     this.tier = tier;
/*    */     
/* 42 */     func_77656_e(27);
/* 43 */     func_77625_d(1);
/* 44 */     setNoRepair();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack stack) {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMaxCharge(ItemStack stack) {
/* 54 */     return this.maxCharge;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier(ItemStack stack) {
/* 59 */     return this.tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getTransferLimit(ItemStack stack) {
/* 64 */     return this.transferLimit;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/* 69 */     List<String> info = new LinkedList<>();
/* 70 */     info.add(ElectricItem.manager.getToolTip(stack));
/* 71 */     return info;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 79 */     if (!func_194125_a(tab)) {
/*    */       return;
/*    */     }
/* 82 */     ElectricItemManager.addChargeVariants(this, (List<ItemStack>)subItems);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDamage(ItemStack stack, int damage) {
/* 87 */     int prev = getDamage(stack);
/*    */     
/* 89 */     if (damage != prev && logIncorrectItemDamaging) {
/* 90 */       IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid armor damage application (%d):", new Object[] { Integer.valueOf(damage - prev) });
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setStackDamage(ItemStack stack, int damage) {
/* 96 */     super.setDamage(stack, damage);
/*    */   }
/*    */   
/* 99 */   public static final boolean logIncorrectItemDamaging = ConfigUtil.getBool(MainConfig.get(), "debug/logIncorrectItemDamaging");
/*    */   protected final double maxCharge;
/*    */   protected final double transferLimit;
/*    */   protected final int tier;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\BaseElectricItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */