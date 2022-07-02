/*    */ package ic2.core.block.machine.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFermenter;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerFermenter extends ContainerFullInv<TileEntityFermenter> {
/*    */   public ContainerFermenter(EntityPlayer player, TileEntityFermenter te) {
/* 13 */     super(player, (IInventory)te, 184);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.fluidInputCellInSlot, 0, 14, 46));
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.fluidInputCellOutSlot, 0, 14, 64));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.fluidOutputCellInSlot, 0, 148, 43));
/* 18 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.fluidOutputCellOutSlot, 0, 148, 61));
/* 19 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.fertiliserSlot, 0, 86, 83));
/*    */     
/* 21 */     for (int i = 0; i < 2; i++) {
/* 22 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)te.upgradeSlot, i, 125 + i * 18, 83));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 28 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 30 */     ret.add("inputTank");
/* 31 */     ret.add("outputTank");
/* 32 */     ret.add("progress");
/* 33 */     ret.add("heatBuffer");
/*    */     
/* 35 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerFermenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */