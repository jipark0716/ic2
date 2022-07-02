/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.core.recipe.AdvRecipe;
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdvRecipeHandler
/*    */   implements IRecipeHandler<AdvRecipe>
/*    */ {
/*    */   @Nonnull
/*    */   public Class<AdvRecipe> getRecipeClass() {
/* 18 */     return AdvRecipe.class;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String getRecipeCategoryUid(AdvRecipe recipe) {
/* 24 */     return "minecraft.crafting";
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IRecipeWrapper getRecipeWrapper(@Nonnull AdvRecipe recipe) {
/* 30 */     return (IRecipeWrapper)new AdvRecipeWrapper(recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(@Nonnull AdvRecipe recipe) {
/* 35 */     if (!recipe.canShow()) return false;
/*    */     
/* 37 */     for (IRecipeInput input : recipe.input) {
/* 38 */       if (input.getInputs().isEmpty()) return false;
/*    */     
/*    */     } 
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\AdvRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */