/*    */ package ic2.core.slot;
/*    */ 
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotCustom
/*    */   extends Slot
/*    */ {
/*    */   private final Item item;
/*    */   
/*    */   public SlotCustom(IInventory iinventory, Item item, int i, int j, int k) {
/* 17 */     super(iinventory, i, j, k);
/*    */     
/* 19 */     this.item = item;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack itemstack) {
/* 24 */     if (itemstack == null) return false;
/*    */     
/* 26 */     return (this.item != null && itemstack.func_77973_b() == this.item);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */