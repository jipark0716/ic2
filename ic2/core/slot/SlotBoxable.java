/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.api.item.ItemWrapper;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotBoxable
/*    */   extends Slot
/*    */ {
/*    */   public SlotBoxable(IInventory iinventory, int i, int j, int k) {
/* 16 */     super(iinventory, i, j, k);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack itemstack) {
/* 21 */     if (itemstack == null) return false; 
/* 22 */     return ItemWrapper.canBeStoredInToolbox(itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotBoxable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */