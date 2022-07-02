/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorComponent;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemReactorIridiumReflector
/*    */   extends AbstractReactorComponent
/*    */ {
/*    */   public ItemReactorIridiumReflector(ItemName name) {
/* 17 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 25 */     if (!heatrun) {
/* 26 */       IReactorComponent source = (IReactorComponent)pulsingStack.func_77973_b();
/*    */       
/* 28 */       source.acceptUraniumPulse(pulsingStack, reactor, stack, pulseX, pulseY, youX, youY, heatrun);
/*    */     } 
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 35 */     return -1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorIridiumReflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */