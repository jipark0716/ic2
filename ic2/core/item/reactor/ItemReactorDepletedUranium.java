/*    */ package ic2.core.item.reactor;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.core.item.type.NuclearResourceType;
/*    */ import ic2.core.profile.NotExperimental;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotExperimental
/*    */ public class ItemReactorDepletedUranium
/*    */   extends AbstractDamageableReactorComponent
/*    */ {
/*    */   public ItemReactorDepletedUranium() {
/* 20 */     super(ItemName.depleted_isotope_fuel_rod, 10000);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
/* 25 */     if (heatrun) {
/* 26 */       int myLevel = getCustomDamage(stack) + 1 + reactor.getHeat() / 3000;
/*    */       
/* 28 */       if (myLevel >= getMaxCustomDamage(stack)) {
/* 29 */         reactor.setItemAt(youX, youY, ItemName.nuclear.getItemStack((Enum)NuclearResourceType.re_enriched_uranium));
/*    */       } else {
/* 31 */         setCustomDamage(stack, myLevel);
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 40 */     return 1.0D - super.getDurabilityForDisplay(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\reactor\ItemReactorDepletedUranium.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */