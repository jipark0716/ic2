/*    */ package ic2.core.block.wiring;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.slot.ArmorSlot;
/*    */ import ic2.core.slot.SlotArmor;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerElectricBlock extends ContainerFullInv<TileEntityElectricBlock> {
/*    */   public ContainerElectricBlock(EntityPlayer player, TileEntityElectricBlock tileEntity1) {
/* 14 */     super(player, (IInventory)tileEntity1, 196);
/*    */     
/* 16 */     for (int col = 0; col < ArmorSlot.getCount(); col++) {
/* 17 */       func_75146_a((Slot)new SlotArmor(player.field_71071_by, 
/* 18 */             ArmorSlot.get(col), 8 + col * 18, 84));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 23 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.chargeSlot, 0, 56, 17));
/* 24 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.dischargeSlot, 0, 56, 53));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 29 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 31 */     ret.add("redstoneMode");
/*    */     
/* 33 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\ContainerElectricBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */