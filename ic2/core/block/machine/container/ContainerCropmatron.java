/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCropmatron;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCropmatron extends ContainerElectricMachine<TileEntityCropmatron> {
/*    */   public ContainerCropmatron(EntityPlayer player, TileEntityCropmatron base) {
/* 12 */     super(player, base, 192, 134, 80);
/*    */     int i;
/* 14 */     for (i = 0; i < base.fertilizerSlot.size(); i++) {
/* 15 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base.fertilizerSlot, i, 8 + i * 18, 80));
/*    */     }
/*    */     
/* 18 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base.exInputSlot, 0, 49, 27));
/* 19 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base.exOutputSlot, 0, 67, 27));
/* 20 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base.wasserinputSlot, 0, 57, 56));
/* 21 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base.wasseroutputSlot, 0, 75, 56));
/*    */     
/* 23 */     for (i = 0; i < 4; i++) {
/* 24 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base.upgradeSlot, i, 152, 26 + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 30 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 32 */     ret.add("waterTank");
/* 33 */     ret.add("exTank");
/*    */     
/* 35 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerCropmatron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */