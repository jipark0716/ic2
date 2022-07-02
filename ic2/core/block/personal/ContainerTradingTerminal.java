/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerTradingTerminal
/*    */   extends ContainerFullInv<TileEntityTradingTerminal> {
/* 13 */   public final Slot rangeSlot = func_75146_a((Slot)new SlotInvSlot((InvSlot)((TileEntityTradingTerminal)this.base).rangeUpgrade, 0, -100, -100));
/*    */   
/*    */   public ContainerTradingTerminal(EntityPlayer player, TileEntityTradingTerminal base) {
/* 16 */     super(player, (IInventory)base, 176, 227);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 21 */     List<String> out = super.getNetworkedFields();
/*    */     
/* 23 */     out.add("range");
/*    */     
/* 25 */     return out;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\ContainerTradingTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */