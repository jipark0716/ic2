/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemScannerAdv
/*    */   extends ItemScanner
/*    */ {
/*    */   public ItemScannerAdv() {
/* 17 */     super(ItemName.advanced_scanner, 1000000.0D, 512.0D, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int startLayerScan(ItemStack stack) {
/* 22 */     return ElectricItem.manager.use(stack, 250.0D, null) ? (getScanRange() / 2) : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getScanRange() {
/* 27 */     return 12;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemScannerAdv.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */