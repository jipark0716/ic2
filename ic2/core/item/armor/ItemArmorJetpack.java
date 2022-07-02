/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.item.armor.jetpack.IJetpack;
/*    */ import ic2.core.ref.FluidName;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.NonNullList;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.FluidUtil;
/*    */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*    */ 
/*    */ public class ItemArmorJetpack
/*    */   extends ItemArmorFluidTank
/*    */   implements IJetpack {
/*    */   public ItemArmorJetpack() {
/* 19 */     super(ItemName.jetpack, "jetpack", FluidName.biogas.getInstance(), 30000);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> subItems) {
/* 27 */     if (!func_194125_a(tab)) {
/*    */       return;
/*    */     }
/* 30 */     ItemStack stack = new ItemStack((Item)this, 1);
/* 31 */     filltank(stack);
/* 32 */     stack.func_77964_b(1);
/* 33 */     subItems.add(stack);
/*    */     
/* 35 */     stack = new ItemStack((Item)this, 1);
/* 36 */     stack.func_77964_b(getMaxDamage(stack));
/* 37 */     subItems.add(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean drainEnergy(ItemStack pack, int amount) {
/* 42 */     if (isEmpty(pack)) return false; 
/* 43 */     IFluidHandlerItem handler = FluidUtil.getFluidHandler(pack);
/* 44 */     assert handler != null;
/* 45 */     FluidStack drained = handler.drain(amount, false);
/* 46 */     if (drained == null || drained.amount < amount) return false;
/*    */     
/* 48 */     handler.drain(amount, true);
/* 49 */     Updatedamage(pack);
/*    */     
/* 51 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPower(ItemStack stack) {
/* 56 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDropPercentage(ItemStack stack) {
/* 61 */     return 0.2F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isJetpackActive(ItemStack stack) {
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getChargeLevel(ItemStack stack) {
/* 71 */     return getCharge(stack) / getMaxCharge(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getHoverMultiplier(ItemStack stack, boolean upwards) {
/* 76 */     return 0.2F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getWorldHeightDivisor(ItemStack stack) {
/* 81 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBarPercent(ItemStack stack) {
/* 86 */     return (int)Util.map(getCharge(stack), getMaxCharge(stack), 100.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorJetpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */