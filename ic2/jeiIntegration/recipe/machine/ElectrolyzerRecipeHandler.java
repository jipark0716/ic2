/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ public class ElectrolyzerRecipeHandler
/*    */   implements IRecipeHandler<ElectrolyzerWrapper> {
/*    */   public Class<ElectrolyzerWrapper> getRecipeClass() {
/*  9 */     return ElectrolyzerWrapper.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(ElectrolyzerWrapper recipe) {
/* 14 */     return recipe.category.getUid();
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeWrapper getRecipeWrapper(ElectrolyzerWrapper recipe) {
/* 19 */     return (IRecipeWrapper)recipe;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(ElectrolyzerWrapper recipe) {
/* 24 */     return (recipe.getFluidInput() != null && !recipe.getFluidOutputs().isEmpty());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\ElectrolyzerRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */