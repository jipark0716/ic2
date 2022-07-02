/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerFluidDistributor extends ContainerFullInv<TileEntityFluidDistributor> {
/*    */   public ContainerFluidDistributor(EntityPlayer player, TileEntityFluidDistributor tileEntite) {
/* 14 */     super(player, (IInventory)tileEntite, 184);
/*    */     
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.inputSlot, 0, 9, 54));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.OutputSlot, 0, 9, 72));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 23 */     List<String> ret = super.getNetworkedFields();
/* 24 */     ret.add("fluidTank");
/* 25 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerFluidDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */