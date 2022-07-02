/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReactorPlating
/*    */   extends AbstractReactorComponent
/*    */ {
/*    */   private final int maxHeatAdd;
/*    */   private final float effectModifier;
/*    */   
/*    */   public ItemReactorPlating(ItemName name, int maxheatadd, float effectmodifier) {
/* 17 */     super(name);
/*    */     
/* 19 */     this.maxHeatAdd = maxheatadd;
/* 20 */     this.effectModifier = effectmodifier;
/*    */   }
/*    */ 
/*    */   
/*    */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
/* 25 */     if (heatrun) {
/* 26 */       reactor.setMaxHeat(reactor.getMaxHeat() + this.maxHeatAdd);
/* 27 */       reactor.setHeatEffectModifier(reactor.getHeatEffectModifier() * this.effectModifier);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 33 */     if (this.effectModifier >= 1.0F) return 0.0F;
/*    */     
/* 35 */     return this.effectModifier;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorPlating.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */