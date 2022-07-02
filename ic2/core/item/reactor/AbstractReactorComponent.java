/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorComponent;
/*    */ import ic2.core.item.ItemIC2;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class AbstractReactorComponent
/*    */   extends ItemIC2
/*    */   implements IReactorComponent {
/*    */   protected AbstractReactorComponent(ItemName name) {
/* 13 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {}
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
/* 37 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
/* 42 */     return heat;
/*    */   }
/*    */ 
/*    */   
/*    */   public float influenceExplosion(ItemStack stack, IReactor reactor) {
/* 47 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
/* 52 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\AbstractReactorComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */