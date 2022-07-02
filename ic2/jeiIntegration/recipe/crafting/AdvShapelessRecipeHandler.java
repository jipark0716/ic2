/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.core.recipe.AdvShapelessRecipe;
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdvShapelessRecipeHandler
/*    */   implements IRecipeHandler<AdvShapelessRecipe>
/*    */ {
/*    */   @Nonnull
/*    */   public Class<AdvShapelessRecipe> getRecipeClass() {
/* 17 */     return AdvShapelessRecipe.class;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getRecipeCategoryUid(AdvShapelessRecipe recipe) {
/* 23 */     return "minecraft.crafting";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IRecipeWrapper getRecipeWrapper(@Nonnull AdvShapelessRecipe recipe) {
/* 29 */     return (IRecipeWrapper)new AdvShapelessRecipeWrapper(recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(@Nonnull AdvShapelessRecipe recipe) {
/* 34 */     if (!recipe.canShow()) return false;
/*    */     
/* 36 */     for (IRecipeInput input : recipe.input) {
/* 37 */       if (input.getInputs().isEmpty()) return false;
/*    */     
/*    */     } 
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\AdvShapelessRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */