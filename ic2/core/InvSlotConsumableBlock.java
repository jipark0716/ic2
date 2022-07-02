/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityInventory;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.invslot.InvSlotConsumable;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotConsumableBlock extends InvSlotConsumable {
/*    */   public InvSlotConsumableBlock(TileEntityInventory base1, String name1, int count) {
/* 11 */     this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP);
/*    */   }
/*    */   
/*    */   public InvSlotConsumableBlock(TileEntityInventory base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1) {
/* 15 */     super((IInventorySlotHolder)base1, name1, access1, count, preferredSide1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 20 */     return stack.func_77973_b() instanceof net.minecraft.item.ItemBlock;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\InvSlotConsumableBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */