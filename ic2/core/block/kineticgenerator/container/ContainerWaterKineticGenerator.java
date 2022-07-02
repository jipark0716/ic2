/*    */ package ic2.core.block.kineticgenerator.container;
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerWaterKineticGenerator extends ContainerFullInv<TileEntityWaterKineticGenerator> {
/*    */   public ContainerWaterKineticGenerator(EntityPlayer player, TileEntityWaterKineticGenerator tileEntity1) {
/* 13 */     super(player, (IInventory)tileEntity1, 166);
/*    */     
/* 15 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)tileEntity1.rotorSlot, 0, 80, 26));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 20 */     List<String> ret = super.getNetworkedFields();
/* 21 */     ret.add("waterFlow");
/* 22 */     ret.add("type");
/* 23 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\container\ContainerWaterKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */