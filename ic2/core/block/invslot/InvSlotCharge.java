/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.energy.tile.IChargingSlot;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotCharge extends InvSlot implements IChargingSlot {
/*    */   public int tier;
/*    */   
/*    */   public InvSlotCharge(IInventorySlotHolder<?> base1, int tier) {
/* 13 */     super(base1, "charge", InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
/*    */     
/* 15 */     this.tier = tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 20 */     return (ElectricItem.manager.charge(stack, Double.POSITIVE_INFINITY, this.tier, true, true) > 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double charge(double amount) {
/* 25 */     if (amount <= 0.0D) throw new IllegalArgumentException("Amount must be > 0.");
/*    */     
/* 27 */     ItemStack stack = get(0);
/* 28 */     if (StackUtil.isEmpty(stack)) return 0.0D;
/*    */     
/* 30 */     return ElectricItem.manager.charge(stack, amount, this.tier, false, false);
/*    */   }
/*    */   
/*    */   public void setTier(int tier1) {
/* 34 */     this.tier = tier1;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */