/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCanner
/*    */   extends ContainerStandardMachine<TileEntityCanner> {
/*    */   public ContainerCanner(EntityPlayer player, TileEntityCanner tileEntity1) {
/* 13 */     super(player, tileEntity1, 184, 8, 80, 80, 44, 119, 17, 152, 26);
/* 14 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.canInputSlot, 0, 41, 17));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 19 */     List<String> ret = super.getNetworkedFields();
/* 20 */     ret.add("mode");
/* 21 */     ret.add("inputTank");
/* 22 */     ret.add("outputTank");
/* 23 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */