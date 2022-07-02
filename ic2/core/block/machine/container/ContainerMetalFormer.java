/*    */ package ic2.core.block.machine.container;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityMetalFormer;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class ContainerMetalFormer
/*    */   extends ContainerStandardMachine<TileEntityMetalFormer>
/*    */ {
/*    */   public ContainerMetalFormer(EntityPlayer player, TileEntityMetalFormer tileEntity1) {
/* 11 */     super(player, tileEntity1, 166, 17, 53, 17, 17, 116, 35, 152, 8);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 16 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 18 */     ret.add("mode");
/*    */     
/* 20 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\container\ContainerMetalFormer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */