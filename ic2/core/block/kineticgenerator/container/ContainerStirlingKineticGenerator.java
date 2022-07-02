/*    */ package ic2.core.block.kineticgenerator.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerStirlingKineticGenerator
/*    */   extends ContainerFullInv<TileEntityStirlingKineticGenerator> {
/*    */   public ContainerStirlingKineticGenerator(EntityPlayer player, TileEntityStirlingKineticGenerator te) {
/* 15 */     super(player, (IInventory)te, 204);
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.coolfluidinputSlot, 0, 8, 103));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.cooloutputSlot, 0, 26, 103));
/* 18 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.hotfluidinputSlot, 0, 134, 103));
/* 19 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.hotoutputSlot, 0, 152, 103));
/*    */     
/* 21 */     for (int i = 0; i < 3; i++) {
/* 22 */       func_75146_a((Slot)new SlotInvSlot((InvSlot)te.upgradeSlot, i, 62 + i * 18, 103));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 28 */     List<String> ret = super.getNetworkedFields();
/* 29 */     ret.add("inputTank");
/* 30 */     ret.add("outputTank");
/* 31 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\container\ContainerStirlingKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */