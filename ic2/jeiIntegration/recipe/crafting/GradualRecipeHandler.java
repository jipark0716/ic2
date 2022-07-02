/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.core.recipe.GradualRecipe;
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ 
/*    */ public class GradualRecipeHandler
/*    */   implements IRecipeHandler<GradualRecipe>
/*    */ {
/*    */   public Class<GradualRecipe> getRecipeClass() {
/* 12 */     return GradualRecipe.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(GradualRecipe recipe) {
/* 17 */     return "minecraft.crafting";
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeWrapper getRecipeWrapper(GradualRecipe recipe) {
/* 22 */     return (IRecipeWrapper)new GradualRecipeWrapper(recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(GradualRecipe recipe) {
/* 27 */     return (recipe.canShow() && recipe.chargeMaterial != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\GradualRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */