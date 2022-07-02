/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class SlotInvSlotReadOnly
/*    */   extends SlotInvSlot {
/*    */   public SlotInvSlotReadOnly(InvSlot invSlot, int index, int xDisplayPosition, int yDisplayPosition) {
/* 11 */     super(invSlot, index, xDisplayPosition, yDisplayPosition);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_190901_a(EntityPlayer player, ItemStack stack) {
/* 21 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_82869_a(EntityPlayer player) {
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_75209_a(int par1) {
/* 31 */     return StackUtil.emptyStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotInvSlotReadOnly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */