/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCondenser;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCondenser
/*    */   extends ContainerElectricMachine<TileEntityCondenser> {
/*    */   public ContainerCondenser(EntityPlayer player, TileEntityCondenser te) {
/* 13 */     super(player, te, 184, 8, 44);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.waterInputSlot, 0, 26, 73));
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.waterOutputSlot, 0, 134, 73));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.upgradeSlot, 0, 152, 73));
/*    */     
/* 19 */     for (int i = 0; i < 2; i++) {
/* 20 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)te.ventSlots, i, 26 + i * 108, 26));
/* 21 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)te.ventSlots, i + 2, 26 + i * 108, 44));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 27 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 29 */     ret.add("inputTank");
/* 30 */     ret.add("outputTank");
/* 31 */     ret.add("progress");
/*    */     
/* 33 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerCondenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */