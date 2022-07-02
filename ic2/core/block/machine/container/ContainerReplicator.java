/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityReplicator;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerReplicator extends ContainerElectricMachine<TileEntityReplicator> {
/*    */   public ContainerReplicator(EntityPlayer player, TileEntityReplicator tileEntity1) {
/* 12 */     super(player, tileEntity1, 184, 152, 83);
/* 13 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.outputSlot, 0, 90, 59));
/* 14 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.fluidSlot, 0, 8, 27));
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.cellSlot, 0, 8, 72));
/* 16 */     for (int i = 0; i < 4; i++) {
/* 17 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.upgradeSlot, i, 152, 8 + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 23 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 25 */     ret.add("fluidTank");
/* 26 */     ret.add("uuProcessed");
/* 27 */     ret.add("pattern");
/* 28 */     ret.add("mode");
/* 29 */     ret.add("index");
/* 30 */     ret.add("maxIndex");
/* 31 */     ret.add("patternUu");
/* 32 */     ret.add("patternEu");
/*    */     
/* 34 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerReplicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */