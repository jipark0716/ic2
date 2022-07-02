/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerWeightedFluidDistributor extends ContainerFullInv<TileEntityWeightedFluidDistributor> {
/*    */   public static final short HEIGHT = 211;
/*    */   
/*    */   public ContainerWeightedFluidDistributor(EntityPlayer player, TileEntityWeightedFluidDistributor te) {
/* 16 */     super(player, (IInventory)te, 211);
/*    */     
/* 18 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.inputSlot, 0, 8, 108));
/* 19 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.OutputSlot, 0, 152, 108));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 24 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 26 */     ret.add("fluidTank");
/*    */ 
/*    */     
/* 29 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerWeightedFluidDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */