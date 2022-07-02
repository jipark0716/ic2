/*    */ package ic2.core.block.machine.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerPatternStorage extends ContainerFullInv<TileEntityPatternStorage> {
/*    */   public ContainerPatternStorage(EntityPlayer player, TileEntityPatternStorage tileEntity1) {
/* 13 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.diskSlot, 0, 18, 20));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 20 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 22 */     ret.add("index");
/* 23 */     ret.add("maxIndex");
/* 24 */     ret.add("pattern");
/* 25 */     ret.add("patternUu");
/* 26 */     ret.add("patternEu");
/*    */     
/* 28 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerPatternStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */