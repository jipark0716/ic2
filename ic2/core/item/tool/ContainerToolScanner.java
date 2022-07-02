/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.item.ContainerHandHeldInventory;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import ic2.core.util.Tuple;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerToolScanner
/*    */   extends ContainerHandHeldInventory<HandHeldScanner> {
/*    */   public ContainerToolScanner(EntityPlayer player, HandHeldScanner scanner) {
/* 15 */     super(scanner);
/*    */     
/* 17 */     addPlayerInventorySlots(player, 231);
/*    */   }
/*    */   public List<Tuple.T2<ItemStack, Integer>> scanResults;
/*    */   public void setResults(List<Tuple.T2<ItemStack, Integer>> results) {
/* 21 */     this.scanResults = results;
/*    */     
/* 23 */     ((NetworkManager)IC2.network.get(true)).sendContainerField((ContainerBase)this, "scanResults");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ContainerToolScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */