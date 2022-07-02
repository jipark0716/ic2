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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReactorReflector
/*    */   extends AbstractDamageableReactorComponent
/*    */ {
/*    */   public ItemReactorReflector(ItemName name, int maxDamage) {
/* 19 */     super(name, maxDamage);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 27 */     if (!heatrun) {
/* 28 */       IReactorComponent source = (IReactorComponent)pulsingStack.func_77973_b();
/*    */       
/* 30 */       source.acceptUraniumPulse(pulsingStack, reactor, stack, pulseX, pulseY, youX, youY, heatrun);
/*    */     }
/* 32 */     else if (getCustomDamage(stack) + 1 >= getMaxCustomDamage(stack)) {
/* 33 */       reactor.setItemAt(youX, youY, null);
/*    */     } else {
/* 35 */       setCustomDamage(stack, getCustomDamage(stack) + 1);
/*    */     } 
/*    */     
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 43 */     return -1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorReflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */