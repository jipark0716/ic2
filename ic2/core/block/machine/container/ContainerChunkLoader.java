/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityChunkloader;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerChunkLoader
/*    */   extends ContainerFullInv<TileEntityChunkloader> {
/*    */   public ContainerChunkLoader(EntityPlayer player, TileEntityChunkloader base1) {
/* 15 */     super(player, (IInventory)base1, 250);
/*    */     
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)base1.dischargeSlot, 0, 8, 143));
/* 18 */     for (int i = 0; i < base1.upgradeSlot.size(); i++) {
/* 19 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base1.upgradeSlot, i, 8, 44 + 18 * i));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 26 */     List<String> ret = super.getNetworkedFields();
/* 27 */     ret.add("loadedChunks");
/* 28 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */