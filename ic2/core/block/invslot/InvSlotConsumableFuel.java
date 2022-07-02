/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.info.Info;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotConsumableFuel extends InvSlotConsumable {
/*    */   public final boolean allowLava;
/*    */   
/*    */   public InvSlotConsumableFuel(IInventorySlotHolder<?> base1, String name1, int count, boolean allowLava1) {
/* 11 */     super(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.SIDE);
/*    */     
/* 13 */     this.allowLava = allowLava1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 18 */     return (Info.itemInfo.getFuelValue(stack, this.allowLava) > 0);
/*    */   }
/*    */   
/*    */   public int consumeFuel() {
/* 22 */     ItemStack fuel = consume(1);
/* 23 */     if (fuel == null) return 0;
/*    */     
/* 25 */     return Info.itemInfo.getFuelValue(fuel, this.allowLava);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableFuel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */