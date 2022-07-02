/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerCropHarvester extends ContainerElectricMachine<TileEntityCropHarvester> {
/*    */   public ContainerCropHarvester(EntityPlayer player, TileEntityCropHarvester base) {
/* 12 */     super(player, base, 166, 16, 53);
/*    */     
/* 14 */     for (int y = 0; y < base.contentSlot.size() / 5; y++) {
/* 15 */       for (int x = 0; x < 5; x++) {
/* 16 */         func_75146_a((Slot)new SlotInvSlot(base.contentSlot, x + y * 5, 48 + x * 18, 17 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 20 */     for (int i = 0; i < 4; i++) {
/* 21 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base.upgradeSlot, i, 152, 8 + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 27 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 29 */     ret.add("energy");
/* 30 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerCropHarvester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */