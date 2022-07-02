/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerFluidRegulator
/*    */   extends ContainerElectricMachine<TileEntityFluidRegulator> {
/*    */   public ContainerFluidRegulator(EntityPlayer player, TileEntityFluidRegulator tileEntite) {
/* 13 */     super(player, tileEntite, 184, 8, 57);
/*    */ 
/*    */     
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.wasserinputSlot, 0, 58, 53));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.wasseroutputSlot, 0, 58, 71));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 22 */     List<String> ret = super.getNetworkedFields();
/* 23 */     ret.add("fluidTank");
/* 24 */     ret.add("outputmb");
/* 25 */     ret.add("mode");
/* 26 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerFluidRegulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */