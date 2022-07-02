/*    */ package ic2.core.block.kineticgenerator.container;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
/*    */ import ic2.core.slot.SlotInvSlot;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ 
/*    */ public class ContainerSteamKineticGenerator extends ContainerFullInv<TileEntitySteamKineticGenerator> {
/*    */   public ContainerSteamKineticGenerator(EntityPlayer player, TileEntitySteamKineticGenerator te) {
/* 14 */     super(player, (IInventory)te, 166);
/*    */     
/* 16 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.upgradeSlot, 0, 152, 26));
/* 17 */     func_75146_a((Slot)new SlotInvSlot((InvSlot)te.turbineSlot, 0, 80, 26));
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 22 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 24 */     ret.add("distilledWaterTank");
/* 25 */     ret.add("kUoutput");
/* 26 */     ret.add("ventingSteam");
/* 27 */     ret.add("throttled");
/* 28 */     ret.add("isTurbineFilledWithWater");
/*    */     
/* 30 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\container\ContainerSteamKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */