/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ 
/*    */ public class JetpackRecipeHandler
/*    */   implements IRecipeHandler<JetpackRecipeWrapper>
/*    */ {
/*    */   public Class<JetpackRecipeWrapper> getRecipeClass() {
/* 11 */     return JetpackRecipeWrapper.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(JetpackRecipeWrapper recipe) {
/* 16 */     return "minecraft.crafting";
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeWrapper getRecipeWrapper(JetpackRecipeWrapper wrapper) {
/* 21 */     return (IRecipeWrapper)wrapper;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(JetpackRecipeWrapper recipe) {
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\JetpackRecipeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */