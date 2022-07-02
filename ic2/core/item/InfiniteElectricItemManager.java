/*    */ package ic2.core.item;
/*    */ 
/*    */ import ic2.api.item.IElectricItemManager;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InfiniteElectricItemManager
/*    */   implements IElectricItemManager
/*    */ {
/*    */   public double charge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
/* 11 */     return amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public double discharge(ItemStack stack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
/* 16 */     return amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getCharge(ItemStack stack) {
/* 21 */     return Double.POSITIVE_INFINITY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getMaxCharge(ItemStack stack) {
/* 26 */     return Double.POSITIVE_INFINITY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse(ItemStack stack, double amount) {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean use(ItemStack stack, double amount, EntityLivingBase entity) {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public String getToolTip(ItemStack stack) {
/* 46 */     return "infinite EU";
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier(ItemStack stack) {
/* 51 */     return Integer.MAX_VALUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\InfiniteElectricItemManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */