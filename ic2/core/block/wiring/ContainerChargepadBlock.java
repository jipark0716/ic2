/*    */ package ic2.core.block.wiring;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerChargepadBlock extends ContainerFullInv<TileEntityChargepadBlock> {
/*    */   public ContainerChargepadBlock(EntityPlayer player, TileEntityChargepadBlock tileEntity1) {
/* 12 */     super(player, (IInventory)tileEntity1, 161);
/*    */ 
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.chargeSlot, 0, 56, 17));
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.dischargeSlot, 0, 56, 53));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 21 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 23 */     ret.add("redstoneMode");
/*    */     
/* 25 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\ContainerChargepadBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */