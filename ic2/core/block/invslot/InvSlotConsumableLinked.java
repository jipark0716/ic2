/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotConsumableLinked
/*    */   extends InvSlotConsumable {
/*    */   public InvSlotConsumableLinked(IInventorySlotHolder<?> base1, String name1, int count, InvSlot linkedSlot1) {
/* 10 */     super(base1, name1, count);
/*    */     
/* 12 */     this.linkedSlot = linkedSlot1;
/*    */   }
/*    */   public final InvSlot linkedSlot;
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 17 */     ItemStack required = this.linkedSlot.get();
/* 18 */     if (StackUtil.isEmpty(required)) return false;
/*    */     
/* 20 */     return StackUtil.checkItemEqualityStrict(required, stack);
/*    */   }
/*    */   
/*    */   public ItemStack consumeLinked(boolean simulate) {
/* 24 */     ItemStack required = this.linkedSlot.get();
/* 25 */     if (StackUtil.isEmpty(required)) return null;
/*    */     
/* 27 */     int reqAmount = StackUtil.getSize(required);
/* 28 */     ItemStack available = consume(reqAmount, true, true);
/*    */     
/* 30 */     if (!StackUtil.isEmpty(available) && StackUtil.getSize(available) == reqAmount) {
/* 31 */       return consume(reqAmount, simulate, true);
/*    */     }
/*    */     
/* 34 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableLinked.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */