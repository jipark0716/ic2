/*    */ package ic2.core.slot;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotInvSlot extends Slot {
/*    */   public final InvSlot invSlot;
/*    */   
/*    */   public SlotInvSlot(InvSlot invSlot, int index, int x, int y) {
/* 13 */     super((IInventory)invSlot.base.getParent(), invSlot.base.getBaseIndex(invSlot) + index, x, y);
/*    */     
/* 15 */     this.invSlot = invSlot;
/* 16 */     this.index = index;
/*    */   }
/*    */   public final int index;
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 21 */     return this.invSlot.accepts(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_75211_c() {
/* 26 */     return this.invSlot.get(this.index);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75215_d(ItemStack stack) {
/* 31 */     this.invSlot.put(this.index, stack);
/* 32 */     func_75218_e();
/*    */   }
/*    */   
/*    */   public ItemStack func_75209_a(int amount) {
/*    */     ItemStack ret;
/* 37 */     if (amount <= 0) return StackUtil.emptyStack;
/*    */     
/* 39 */     ItemStack stack = this.invSlot.get(this.index);
/* 40 */     if (StackUtil.isEmpty(stack)) return StackUtil.emptyStack;
/*    */     
/* 42 */     amount = Math.min(amount, StackUtil.getSize(stack));
/*    */ 
/*    */     
/* 45 */     if (StackUtil.getSize(stack) == amount) {
/* 46 */       ret = stack;
/* 47 */       this.invSlot.clear(this.index);
/*    */     } else {
/* 49 */       ret = StackUtil.copyWithSize(stack, amount);
/* 50 */       this.invSlot.put(this.index, StackUtil.decSize(stack, amount));
/*    */     } 
/*    */     
/* 53 */     func_75218_e();
/*    */     
/* 55 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75217_a(IInventory inventory, int index) {
/* 60 */     if (inventory != this.invSlot.base) return false;
/*    */     
/* 62 */     int baseIndex = this.invSlot.base.getBaseIndex(this.invSlot);
/* 63 */     if (baseIndex == -1) return false;
/*    */     
/* 65 */     return (baseIndex + this.index == index);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_75219_a() {
/* 70 */     return this.invSlot.getStackSizeLimit();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_190901_a(EntityPlayer player, ItemStack stack) {
/* 75 */     stack = super.func_190901_a(player, stack);
/*    */     
/* 77 */     this.invSlot.onPickupFromSlot(player, stack);
/*    */     
/* 79 */     return stack;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotInvSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */