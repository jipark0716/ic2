/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerAdvMiner extends ContainerElectricMachine<TileEntityAdvMiner> {
/*    */   public ContainerAdvMiner(EntityPlayer player, TileEntityAdvMiner tileEntity1) {
/* 12 */     super(player, tileEntity1, 203, 8, 80);
/*    */     
/* 14 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.scannerSlot, 0, 8, 26));
/*    */     
/* 16 */     for (int i = 0; i < 4; i++) {
/* 17 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.upgradeSlot, i, 152, 26 + i * 18));
/*    */     }
/*    */     
/* 20 */     for (int row = 0; row < 3; row++) {
/* 21 */       for (int col = 0; col < 5; col++) {
/* 22 */         func_75146_a((Slot)new SlotInvSlot(tileEntity1.filterSlot, col + row * 5, 36 + col * 18, 44 + row * 18));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 31 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 33 */     ret.add("mineTarget");
/* 34 */     ret.add("blacklist");
/* 35 */     ret.add("silkTouch");
/*    */     
/* 37 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerAdvMiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */