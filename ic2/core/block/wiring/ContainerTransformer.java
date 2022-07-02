/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.ContainerFullInv;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ 
/*    */ public class ContainerTransformer
/*    */   extends ContainerFullInv<TileEntityTransformer> {
/*    */   public ContainerTransformer(EntityPlayer player, TileEntityTransformer tileEntity1, int height) {
/* 11 */     super(player, (IInventory)tileEntity1, height);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getNetworkedFields() {
/* 16 */     List<String> ret = super.getNetworkedFields();
/*    */     
/* 18 */     ret.add("configuredMode");
/* 19 */     ret.add("inputFlow");
/* 20 */     ret.add("outputFlow");
/*    */     
/* 22 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\ContainerTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */