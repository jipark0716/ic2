/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.util.ItemComparableItemStack;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotConsumableItemStack extends InvSlotConsumable {
/*    */   private final Set<ItemComparableItemStack> stacks;
/*    */   
/*    */   public InvSlotConsumableItemStack(IInventorySlotHolder<?> base1, String name1, int count, ItemStack... stacks) {
/* 13 */     this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP, stacks);
/*    */   }
/*    */   
/*    */   public InvSlotConsumableItemStack(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, ItemStack... stacks) {
/* 17 */     super(base1, name1, access1, count, preferredSide1);
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
/* 29 */     this.stacks = new HashSet<>();
/*    */     for (ItemStack stack : stacks)
/*    */       this.stacks.add(new ItemComparableItemStack(stack, true)); 
/*    */   }
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/*    */     return this.stacks.contains(new ItemComparableItemStack(stack, false));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */