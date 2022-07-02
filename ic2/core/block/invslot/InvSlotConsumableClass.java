/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvSlotConsumableClass
/*    */   extends InvSlotConsumable
/*    */ {
/*    */   private final Class<?> clazz;
/*    */   
/*    */   public InvSlotConsumableClass(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, Class<?> clazz) {
/* 16 */     super(base1, name1, access1, count, preferredSide1);
/* 17 */     this.clazz = clazz;
/*    */   }
/*    */   
/*    */   public InvSlotConsumableClass(IInventorySlotHolder<?> base1, String name1, int count, Class<?> clazz) {
/* 21 */     super(base1, name1, count);
/* 22 */     this.clazz = clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 27 */     if (StackUtil.isEmpty(stack)) return false; 
/* 28 */     if (stack.func_77973_b() instanceof net.minecraft.item.ItemBlock) {
/* 29 */       return this.clazz.isInstance(Block.func_149634_a(stack.func_77973_b()));
/*    */     }
/*    */     
/* 32 */     return this.clazz.isInstance(stack.func_77973_b());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */