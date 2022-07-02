/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorComponent;
/*    */ import ic2.core.profile.NotExperimental;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotExperimental
/*    */ public class ItemReactorHeatpack
/*    */   extends AbstractReactorComponent
/*    */ {
/*    */   protected final int maxPer;
/*    */   protected final int heatPer;
/*    */   
/*    */   public ItemReactorHeatpack(int maxPer, int heatPer) {
/* 22 */     super(ItemName.heatpack);
/*    */     
/* 24 */     this.maxPer = maxPer;
/* 25 */     this.heatPer = heatPer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
/* 30 */     if (heatrun) {
/* 31 */       int size = StackUtil.getSize(stack);
/*    */       
/* 33 */       heat(reactor, size, x + 1, y);
/* 34 */       heat(reactor, size, x - 1, y);
/* 35 */       heat(reactor, size, x, y + 1);
/* 36 */       heat(reactor, size, x, y - 1);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void heat(IReactor reactor, int size, int x, int y) {
/* 41 */     int want = this.maxPer * size;
/* 42 */     if (reactor.getHeat() >= want)
/*    */       return; 
/* 44 */     ItemStack stack = reactor.getItemAt(x, y);
/*    */     
/* 46 */     if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IReactorComponent) {
/* 47 */       IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/*    */       
/* 49 */       if (comp.canStoreHeat(stack, reactor, x, y)) {
/* 50 */         int add = this.heatPer * size;
/* 51 */         int curr = comp.getCurrentHeat(stack, reactor, x, y);
/*    */         
/* 53 */         if (add > want - curr) add = want - curr;
/*    */         
/* 55 */         if (add > 0) comp.alterHeat(stack, reactor, x, y, add);
/*    */       
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 62 */     return StackUtil.getSize(stack) / 10.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorHeatpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */