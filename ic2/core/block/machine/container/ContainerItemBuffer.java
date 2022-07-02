/*    */ package ic2.core.block.machine.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityItemBuffer;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerItemBuffer extends ContainerFullInv<TileEntityItemBuffer> {
/*    */   public ContainerItemBuffer(EntityPlayer player, TileEntityItemBuffer tileEntite) {
/* 11 */     super(player, (IInventory)tileEntite, 232);
/*    */     int y;
/* 13 */     for (y = 0; y < tileEntite.leftcontentSlot.size() / 4; y++) {
/* 14 */       for (int x = 0; x < 4; x++) {
/* 15 */         func_75146_a((Slot)new SlotInvSlot(tileEntite.leftcontentSlot, x + y * 4, 8 + x * 18, 18 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 19 */     for (y = 0; y < tileEntite.rightcontentSlot.size() / 4; y++) {
/* 20 */       for (int x = 0; x < 4; x++) {
/* 21 */         func_75146_a((Slot)new SlotInvSlot(tileEntite.rightcontentSlot, x + y * 4, 98 + x * 18, 18 + y * 18));
/*    */       }
/*    */     } 
/*    */     
/* 25 */     for (int i = 0; i < 2; i++)
/* 26 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.upgradeSlot, i, 35 + i * 90, 128)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerItemBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */