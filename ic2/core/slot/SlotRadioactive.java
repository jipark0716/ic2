/*    */ package ic2.core.slot;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotRadioactive
/*    */   extends Slot
/*    */ {
/*    */   public SlotRadioactive(IInventory inventory, int index, int x, int y) {
/* 13 */     super(inventory, index, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 18 */     return this.field_75224_c.func_94041_b(this.field_75222_d, stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotRadioactive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */