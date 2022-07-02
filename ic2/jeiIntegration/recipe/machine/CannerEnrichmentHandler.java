/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ public class CannerEnrichmentHandler
/*    */   implements IRecipeHandler<CannerEnrichmentWrapper> {
/*    */   public Class<CannerEnrichmentWrapper> getRecipeClass() {
/*  9 */     return CannerEnrichmentWrapper.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(CannerEnrichmentWrapper recipe) {
/* 14 */     return recipe.category.getUid();
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeWrapper getRecipeWrapper(CannerEnrichmentWrapper recipe) {
/* 19 */     return (IRecipeWrapper)recipe;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(CannerEnrichmentWrapper recipe) {
/* 24 */     return (!recipe.getAdditives().isEmpty() && recipe.getInput() != null && recipe.getOutput() != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\CannerEnrichmentHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */