/*    */ package ic2.core.block.machine.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerMatter extends ContainerFullInv<TileEntityMatter> {
/*    */   public ContainerMatter(EntityPlayer player, TileEntityMatter tileEntity1) {
/* 13 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.amplifierSlot, 0, 72, 40));
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.outputSlot, 0, 125, 59));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.containerslot, 0, 125, 23));
/*    */     
/* 19 */     for (int i = 0; i < 4; i++) {
/* 20 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.upgradeSlot, i, 152, 8 + i * 18));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 26 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 28 */     ret.add("energy");
/* 29 */     ret.add("scrap");
/* 30 */     ret.add("fluidTank");
/*    */     
/* 32 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerMatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */