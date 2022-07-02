/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemReactorLithiumCell
/*    */   extends AbstractDamageableReactorComponent
/*    */ {
/*    */   public ItemReactorLithiumCell() {
/* 19 */     super(ItemName.lithium_fuel_rod, 10000);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 24 */     if (heatrun) {
/* 25 */       int myLevel = getCustomDamage(stack) + reactor.getHeat() / 3000;
/*    */       
/* 27 */       if (myLevel >= getMaxCustomDamage(stack)) {
/* 28 */         reactor.setItemAt(youX, youY, ItemName.tritium_fuel_rod.getItemStack());
/*    */       } else {
/* 30 */         setCustomDamage(stack, myLevel);
/*    */       } 
/*    */     } 
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 39 */     return 1.0D - super.getDurabilityForDisplay(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorLithiumCell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */