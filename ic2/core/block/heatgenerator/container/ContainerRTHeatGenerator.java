/*    */ package ic2.core.block.heatgenerator.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerRTHeatGenerator extends ContainerFullInv<TileEntityRTHeatGenerator> {
/*    */   public ContainerRTHeatGenerator(EntityPlayer player, TileEntityRTHeatGenerator tileEntity1) {
/* 14 */     super(player, (IInventory)tileEntity1, 166);
/*    */     int i;
/* 16 */     for (i = 0; i < 3; i++) {
/* 17 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.fuelSlot, i, 62 + i * 18, 27));
/*    */     }
/*    */     
/* 20 */     for (i = 3; i < 6; i++) {
/* 21 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.fuelSlot, i, 62 + (i - 3) * 18, 45));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 27 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 29 */     ret.add("transmitHeat");
/* 30 */     ret.add("maxHeatEmitpeerTick");
/*    */     
/* 32 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\container\ContainerRTHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */