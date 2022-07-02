/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerStandardMachine<T extends TileEntityStandardMachine<?, ?, ?>> extends ContainerElectricMachine<T> {
/*    */   public ContainerStandardMachine(EntityPlayer player, T tileEntity1) {
/* 12 */     this(player, tileEntity1, 166, 56, 53, 56, 17, 116, 35, 152, 8);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerStandardMachine(EntityPlayer player, T tileEntity1, int height, int dischargeX, int dischargeY, int inputX, int inputY, int outputX, int outputY, int upgradeX, int upgradeY) {
/* 19 */     super(player, tileEntity1, height, dischargeX, dischargeY);
/*    */     
/* 21 */     if (((TileEntityStandardMachine)tileEntity1).inputSlot != null) {
/* 22 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)((TileEntityStandardMachine)tileEntity1).inputSlot, 0, inputX, inputY));
/*    */     }
/*    */     
/* 25 */     if (((TileEntityStandardMachine)tileEntity1).outputSlot != null) {
/* 26 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)((TileEntityStandardMachine)tileEntity1).outputSlot, 0, outputX, outputY));
/*    */     }
/*    */     
/* 29 */     for (int i = 0; i < 4; i++) {
/* 30 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)((TileEntityStandardMachine)tileEntity1).upgradeSlot, i, upgradeX, upgradeY + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 36 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 38 */     ret.add("guiProgress");
/*    */     
/* 40 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerStandardMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */