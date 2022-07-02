/*    */ package ic2.jeiIntegration.recipe.misc;
/*    */ 
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ public class ScrapboxRecipeHandler
/*    */   implements IRecipeHandler<ScrapboxRecipeWrapper>
/*    */ {
/*    */   public Class<ScrapboxRecipeWrapper> getRecipeClass() {
/* 10 */     return ScrapboxRecipeWrapper.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(ScrapboxRecipeWrapper recipe) {
/* 15 */     return "ic2.scrapbox";
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeWrapper getRecipeWrapper(ScrapboxRecipeWrapper recipe) {
/* 20 */     return (IRecipeWrapper)recipe;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(ScrapboxRecipeWrapper recipe) {
/* 25 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\misc\ScrapboxRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */