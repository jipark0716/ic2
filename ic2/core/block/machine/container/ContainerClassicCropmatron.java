/*    */ package ic2.core.block.machine.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityClassicCropmatron;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerClassicCropmatron extends ContainerFullInv<TileEntityClassicCropmatron> {
/*    */   public ContainerClassicCropmatron(EntityPlayer player, TileEntityClassicCropmatron base) {
/* 11 */     super(player, (IInventory)base, 166);
/*    */     int i;
/* 13 */     for (i = 0; i < base.fertilizerSlot.size(); i++) {
/* 14 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base.fertilizerSlot, i, 62, 20 + i * 18));
/*    */     }
/*    */     
/* 17 */     for (i = 0; i < base.hydrationSlot.size(); i++) {
/* 18 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base.hydrationSlot, i, 98, 20 + i * 18));
/*    */     }
/*    */     
/* 21 */     for (i = 0; i < base.weedExSlot.size(); i++)
/* 22 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)base.weedExSlot, i, 134, 20 + i * 18)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerClassicCropmatron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */