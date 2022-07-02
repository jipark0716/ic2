/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerSolarDestiller extends ContainerFullInv<TileEntitySolarDestiller> {
/*    */   public ContainerSolarDestiller(EntityPlayer player, TileEntitySolarDestiller tileEntite) {
/* 14 */     super(player, (IInventory)tileEntite, 184);
/*    */ 
/*    */     
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.waterinputSlot, 0, 17, 27));
/* 18 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.destiwaterinputSlot, 0, 136, 64));
/* 19 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.wateroutputSlot, 0, 17, 45));
/* 20 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.destiwateroutputSlott, 0, 136, 82));
/*    */     
/* 22 */     for (int i = 0; i < 2; i++) {
/* 23 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.upgradeSlot, i, 152, 8 + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 30 */     List<String> ret = super.getNetworkedFields();
/* 31 */     ret.add("skyLight");
/* 32 */     ret.add("inputTank");
/* 33 */     ret.add("outputTank");
/* 34 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerSolarDestiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */