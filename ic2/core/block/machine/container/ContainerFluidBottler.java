/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFluidBottler;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerFluidBottler extends ContainerStandardMachine<TileEntityFluidBottler> {
/*    */   public ContainerFluidBottler(EntityPlayer player, TileEntityFluidBottler tileEntity1) {
/* 12 */     super(player, tileEntity1, 184, 8, 53, 0, 0, 117, 53, 152, 26);
/* 13 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.drainInputSlot, 0, 44, 35));
/* 14 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.fillInputSlot, 0, 44, 72));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 19 */     List<String> ret = super.getNetworkedFields();
/* 20 */     ret.add("fluidTank");
/* 21 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerFluidBottler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */