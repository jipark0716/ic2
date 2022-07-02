/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityClassicCanner;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerClassicCanner extends ContainerElectricMachine<TileEntityClassicCanner> {
/*    */   public ContainerClassicCanner(EntityPlayer player, TileEntityClassicCanner base) {
/* 12 */     super(player, base, 166, 30, 45);
/*    */     
/* 14 */     func_75146_a((Slot)new SlotInvSlot(base.resInputSlot, 0, 69, 17));
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base.outputSlot, 0, 119, 35));
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base.inputSlot, 0, 69, 53));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 21 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 23 */     ret.add("progress");
/* 24 */     ret.add("mode");
/*    */     
/* 26 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerClassicCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */