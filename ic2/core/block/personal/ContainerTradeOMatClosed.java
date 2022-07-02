/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import ic2.core.slot.SlotInvSlotReadOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerTradeOMatClosed
/*    */   extends ContainerFullInv<TileEntityTradeOMat>
/*    */ {
/*    */   public ContainerTradeOMatClosed(EntityPlayer player, TileEntityTradeOMat tileEntity1) {
/* 18 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/* 20 */     func_75146_a((Slot)new SlotInvSlotReadOnly(tileEntity1.demandSlot, 0, 50, 19));
/* 21 */     func_75146_a((Slot)new SlotInvSlotReadOnly(tileEntity1.offerSlot, 0, 50, 38));
/* 22 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.inputSlot, 0, 143, 17));
/* 23 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.outputSlot, 0, 143, 53));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 28 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 30 */     ret.add("stock");
/*    */     
/* 32 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\ContainerTradeOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */