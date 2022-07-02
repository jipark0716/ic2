/*    */ package ic2.core.block.kineticgenerator.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerElectricKineticGenerator extends ContainerFullInv<TileEntityElectricKineticGenerator> {
/*    */   public ContainerElectricKineticGenerator(EntityPlayer player, TileEntityElectricKineticGenerator tileEntity1) {
/* 11 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/*    */     int i;
/* 14 */     for (i = 0; i < 5; i++) {
/* 15 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.slotMotor, i, 44 + i * 18, 27));
/*    */     }
/*    */     
/* 18 */     for (i = 5; i < 10; i++) {
/* 19 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.slotMotor, i, 44 + (i - 5) * 18, 45));
/*    */     }
/*    */     
/* 22 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.dischargeSlot, 0, 8, 62));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\container\ContainerElectricKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */