/*    */ package ic2.core.block.heatgenerator.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerElectricHeatGenerator extends ContainerFullInv<TileEntityElectricHeatGenerator> {
/*    */   public ContainerElectricHeatGenerator(EntityPlayer player, TileEntityElectricHeatGenerator tileEntity1) {
/* 14 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/*    */     int i;
/* 17 */     for (i = 0; i < 5; i++) {
/* 18 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.coilSlot, i, 44 + i * 18, 27));
/*    */     }
/*    */     
/* 21 */     for (i = 5; i < 10; i++) {
/* 22 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.coilSlot, i, 44 + (i - 5) * 18, 45));
/*    */     }
/*    */     
/* 25 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.dischargeSlot, 0, 8, 62));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 30 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 32 */     ret.add("transmitHeat");
/* 33 */     ret.add("maxHeatEmitpeerTick");
/*    */     
/* 35 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\container\ContainerElectricHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */