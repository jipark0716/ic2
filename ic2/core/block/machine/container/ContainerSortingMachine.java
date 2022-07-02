/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
/*    */ import ic2.core.slot.SlotHologramSlot;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class ContainerSortingMachine extends ContainerElectricMachine<TileEntitySortingMachine> {
/*    */   public ContainerSortingMachine(EntityPlayer player, TileEntitySortingMachine tileEntity) {
/* 15 */     super(player, tileEntity, 243, 188, 219);
/*    */     int i;
/* 17 */     for (i = 0; i < 3; i++) {
/* 18 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity.upgradeSlot, i, 188, 161 + i * 18));
/*    */     }
/*    */     
/* 21 */     for (i = 0; i < 11; i++) {
/* 22 */       func_75146_a((Slot)new SlotInvSlot(tileEntity.buffer, i, 8 + i * 18, 141));
/*    */     }
/*    */     
/* 25 */     for (i = 0; i < EnumFacing.field_82609_l.length; i++) {
/* 26 */       EnumFacing dir = EnumFacing.field_82609_l[i];
/* 27 */       ItemStack[] filterSlots = tileEntity.getFilterSlots(dir);
/*    */       
/* 29 */       for (int j = 0; j < filterSlots.length; j++) {
/* 30 */         func_75146_a((Slot)new SlotHologramSlot(filterSlots, j, 80 + j * 18, 19 + i * 20, 64, null));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 37 */     List<String> ret = super.getNetworkedFields();
/* 38 */     ret.add("defaultRoute");
/* 39 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerSortingMachine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */