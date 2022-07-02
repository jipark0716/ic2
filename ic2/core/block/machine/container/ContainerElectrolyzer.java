/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerElectrolyzer extends ContainerElectricMachine<TileEntityElectrolyzer> {
/*    */   public ContainerElectrolyzer(EntityPlayer player, TileEntityElectrolyzer tileEntity) {
/* 12 */     super(player, tileEntity, 166, 8, 62);
/*    */     
/* 14 */     for (int i = 0; i < 4; i++) {
/* 15 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.upgradeSlot, i, 152, 8 + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 21 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 23 */     ret.add("progress");
/* 24 */     ret.add("recipe");
/* 25 */     ret.add("input");
/*    */     
/* 27 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerElectrolyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */