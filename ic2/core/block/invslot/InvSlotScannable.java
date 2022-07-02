/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.network.IRpcProvider;
/*    */ import ic2.core.network.Rpc;
/*    */ import ic2.core.network.RpcHandler;
/*    */ import ic2.core.util.LogCategory;
/*    */ import ic2.core.util.StackUtil;
/*    */ import ic2.core.uu.UuGraph;
/*    */ import ic2.core.uu.UuIndex;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class InvSlotScannable
/*    */   extends InvSlotConsumable
/*    */ {
/*    */   public InvSlotScannable(IInventorySlotHolder<?> base1, String name1, int count) {
/* 19 */     super(base1, name1, count);
/*    */     
/* 21 */     setStackSizeLimit(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 26 */     if (IC2.platform.isSimulating()) {
/* 27 */       return isValidStack(stack);
/*    */     }
/* 29 */     Rpc<Boolean> rpc = RpcHandler.run(ServerScannableCheck.class, new Object[] { stack });
/*    */     
/*    */     try {
/* 32 */       return ((Boolean)rpc.get(1L, TimeUnit.SECONDS)).booleanValue();
/* 33 */     } catch (Exception e) {
/* 34 */       IC2.log.debug(LogCategory.Block, e, "Scannability check failed.");
/*    */       
/* 36 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean isValidStack(ItemStack stack) {
/* 42 */     stack = UuGraph.find(stack);
/* 43 */     return (!StackUtil.isEmpty(stack) && UuIndex.instance.get(stack) < Double.POSITIVE_INFINITY);
/*    */   }
/*    */   
/*    */   public static class ServerScannableCheck
/*    */     implements IRpcProvider<Boolean> {
/*    */     public Boolean executeRpc(Object... args) {
/* 49 */       ItemStack stack = (ItemStack)args[0];
/*    */       
/* 51 */       return Boolean.valueOf(InvSlotScannable.isValidStack(stack));
/*    */     }
/*    */   }
/*    */   
/*    */   static {
/* 56 */     RpcHandler.registerProvider(new ServerScannableCheck());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotScannable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */