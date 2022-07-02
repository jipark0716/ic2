/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReactorVent
/*    */   extends ItemReactorHeatStorage
/*    */ {
/*    */   public final int selfVent;
/*    */   public final int reactorVent;
/*    */   
/*    */   public ItemReactorVent(ItemName name, int heatStorage, int selfvent, int reactorvent) {
/* 17 */     super(name, heatStorage);
/*    */     
/* 19 */     this.selfVent = selfvent;
/* 20 */     this.reactorVent = reactorvent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
/* 25 */     if (heatrun) {
/* 26 */       if (this.reactorVent > 0) {
/* 27 */         int rheat = reactor.getHeat();
/* 28 */         int reactorDrain = rheat;
/* 29 */         if (reactorDrain > this.reactorVent) reactorDrain = this.reactorVent; 
/* 30 */         rheat -= reactorDrain;
/* 31 */         if ((reactorDrain = alterHeat(stack, reactor, x, y, reactorDrain)) > 0)
/*    */           return; 
/* 33 */         reactor.setHeat(rheat);
/*    */       } 
/*    */       
/* 36 */       int self = alterHeat(stack, reactor, x, y, -this.selfVent);
/* 37 */       if (self <= 0) reactor.addEmitHeat(self + this.selfVent); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorVent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */