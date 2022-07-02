/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemReactorCondensator
/*    */   extends AbstractDamageableReactorComponent
/*    */ {
/*    */   public ItemReactorCondensator(ItemName name, int maxdmg) {
/* 17 */     super(name, maxdmg);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 22 */     return (getCurrentHeat(stack) < getMaxCustomDamage(stack));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 27 */     return getMaxCustomDamage(stack);
/*    */   }
/*    */   
/*    */   private int getCurrentHeat(ItemStack stack) {
/* 31 */     return getCustomDamage(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
/* 36 */     if (heat < 0) return heat;
/*    */     
/* 38 */     int currentHeat = getCurrentHeat(stack);
/* 39 */     int amount = Math.min(heat, getMaxHeat(stack, reactor, x, y) - currentHeat);
/*    */     
/* 41 */     heat -= amount;
/* 42 */     setCustomDamage(stack, currentHeat + amount);
/*    */     
/* 44 */     return heat;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorCondensator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */