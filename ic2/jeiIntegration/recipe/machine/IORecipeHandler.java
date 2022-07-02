/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ public class IORecipeHandler
/*    */   implements IRecipeHandler<IORecipeWrapper>
/*    */ {
/*    */   public Class<IORecipeWrapper> getRecipeClass() {
/* 11 */     return IORecipeWrapper.class;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IRecipeWrapper getRecipeWrapper(@Nonnull IORecipeWrapper recipe) {
/* 17 */     return (IRecipeWrapper)recipe;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(@Nonnull IORecipeWrapper recipe) {
/* 22 */     return !recipe.getInputs().isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(IORecipeWrapper recipe) {
/* 27 */     return recipe.category.getUid();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\IORecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */