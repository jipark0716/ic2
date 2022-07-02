/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorComponent;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReactorVentSpread
/*    */   extends AbstractReactorComponent
/*    */ {
/*    */   public final int sideVent;
/*    */   
/*    */   public ItemReactorVentSpread(ItemName name, int sidevent) {
/* 18 */     super(name);
/* 19 */     this.sideVent = sidevent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
/* 24 */     if (heatrun) {
/* 25 */       cool(reactor, x - 1, y);
/* 26 */       cool(reactor, x + 1, y);
/* 27 */       cool(reactor, x, y - 1);
/* 28 */       cool(reactor, x, y + 1);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void cool(IReactor reactor, int x, int y) {
/* 33 */     ItemStack stack = reactor.getItemAt(x, y);
/*    */     
/* 35 */     if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/* 36 */       IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/*    */       
/* 38 */       if (comp.canStoreHeat(stack, reactor, x, y)) {
/* 39 */         int self = comp.alterHeat(stack, reactor, x, y, -this.sideVent);
/* 40 */         if (self <= 0) reactor.addEmitHeat(self + this.sideVent); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorVentSpread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */