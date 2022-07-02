/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotConsumableId extends InvSlotConsumable {
/*    */   private final Set<Item> items;
/*    */   
/*    */   public InvSlotConsumableId(IInventorySlotHolder<?> base1, String name1, int count, Item... items) {
/* 14 */     this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP, items);
/*    */   }
/*    */   
/*    */   public InvSlotConsumableId(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, Item... items) {
/* 18 */     super(base1, name1, access1, count, preferredSide1);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     this.items = new HashSet<>();
/*    */     this.items.addAll(Arrays.asList(items));
/*    */   }
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/*    */     return this.items.contains(stack.func_77973_b());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */