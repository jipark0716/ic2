/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.machine.tileentity.TileEntityWeightedItemDistributor;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerWeightedItemDistributor
/*    */   extends ContainerFullInv<TileEntityWeightedItemDistributor> {
/*    */   public ContainerWeightedItemDistributor(EntityPlayer player, TileEntityWeightedItemDistributor te) {
/* 13 */     super(player, (IInventory)te, 211);
/*    */     
/* 15 */     for (int i = 0; i < te.buffer.size(); i++)
/* 16 */       func_75146_a((Slot)new SlotInvSlot(te.buffer, i, 8 + i * 18, 108)); 
/*    */   }
/*    */   
/*    */   public static final short HEIGHT = 211;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerWeightedItemDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */