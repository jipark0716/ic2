/*    */ package ic2.core.block.machine.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public abstract class ContainerElectricMachine<T extends TileEntityElectricMachine> extends ContainerFullInv<T> {
/*    */   public ContainerElectricMachine(EntityPlayer player, T base1, int height, int dischargeX, int dischargeY) {
/* 11 */     super(player, (IInventory)base1, height);
/* 12 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)((TileEntityElectricMachine)base1).dischargeSlot, 0, dischargeX, dischargeY));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerElectricMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */