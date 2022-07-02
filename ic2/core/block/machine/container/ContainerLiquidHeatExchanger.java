/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerLiquidHeatExchanger extends ContainerFullInv<TileEntityLiquidHeatExchanger> {
/*    */   public ContainerLiquidHeatExchanger(EntityPlayer player, TileEntityLiquidHeatExchanger tileEntite) {
/* 14 */     super(player, (IInventory)tileEntite, 204);
/*    */ 
/*    */     
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.hotfluidinputSlot, 0, 8, 103));
/* 18 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.cooloutputSlot, 0, 152, 103));
/* 19 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.coolfluidinputSlot, 0, 134, 103));
/* 20 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.hotoutputSlot, 0, 26, 103));
/*    */     int i;
/* 22 */     for (i = 0; i < 3; i++) {
/* 23 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.upgradeSlot, i, 62 + i * 18, 103));
/*    */     }
/*    */ 
/*    */     
/* 27 */     for (i = 0; i < 5; i++) {
/* 28 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.heatexchangerslots, i, 46 + i * 17, 50));
/*    */     }
/*    */     
/* 31 */     for (i = 5; i < 10; i++) {
/* 32 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntite.heatexchangerslots, i, 46 + (i - 5) * 17, 72));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 38 */     List<String> ret = super.getNetworkedFields();
/* 39 */     ret.add("inputTank");
/* 40 */     ret.add("outputTank");
/* 41 */     ret.add("transmitHeat");
/* 42 */     ret.add("maxHeatEmitpeerTick");
/* 43 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerLiquidHeatExchanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */