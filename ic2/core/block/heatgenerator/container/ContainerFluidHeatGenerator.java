/*    */ package ic2.core.block.heatgenerator.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerFluidHeatGenerator extends ContainerFullInv<TileEntityFluidHeatGenerator> {
/*    */   public ContainerFluidHeatGenerator(EntityPlayer player, TileEntityFluidHeatGenerator tileEntity1) {
/* 13 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.fluidSlot, 0, 27, 21));
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.outputSlot, 0, 27, 54));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 21 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 23 */     ret.add("fluidTank");
/* 24 */     ret.add("transmitHeat");
/* 25 */     ret.add("maxHeatEmitpeerTick");
/*    */     
/* 27 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\container\ContainerFluidHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */