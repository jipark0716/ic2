/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityScanner;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerScanner extends ContainerElectricMachine<TileEntityScanner> {
/*    */   public ContainerScanner(EntityPlayer player, TileEntityScanner tileEntity1) {
/* 12 */     super(player, tileEntity1, 166, 8, 43);
/*    */     
/* 14 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.inputSlot, 0, 55, 35));
/* 15 */     func_75146_a((Slot)new SlotInvSlot(tileEntity1.diskSlot, 0, 152, 65));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 20 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 22 */     ret.add("state");
/* 23 */     ret.add("progress");
/* 24 */     ret.add("patternEu");
/* 25 */     ret.add("patternUu");
/*    */     
/* 27 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */