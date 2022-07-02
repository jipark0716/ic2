/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ public class ContainerSteamGenerator
/*    */   extends ContainerBase<TileEntitySteamGenerator>
/*    */ {
/*    */   public ContainerSteamGenerator(EntityPlayer player, TileEntitySteamGenerator te) {
/* 13 */     super((IInventory)te);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 18 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 20 */     ret.add("waterTank");
/* 21 */     ret.add("heatInput");
/* 22 */     ret.add("inputMB");
/* 23 */     ret.add("outputMB");
/* 24 */     ret.add("pressure");
/* 25 */     ret.add("systemHeat");
/* 26 */     ret.add("outputFluid");
/* 27 */     ret.add("calcification");
/*    */     
/* 29 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerSteamGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */