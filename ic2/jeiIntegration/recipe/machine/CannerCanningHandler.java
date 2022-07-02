/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.core.util.StackUtil;
/*    */ import mezz.jei.api.recipe.IRecipeHandler;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ 
/*    */ public class CannerCanningHandler
/*    */   implements IRecipeHandler<CannerCanningWrapper>
/*    */ {
/*    */   public Class<CannerCanningWrapper> getRecipeClass() {
/* 11 */     return CannerCanningWrapper.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getRecipeCategoryUid(CannerCanningWrapper recipe) {
/* 16 */     return recipe.category.getUid();
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeWrapper getRecipeWrapper(CannerCanningWrapper recipe) {
/* 21 */     return (IRecipeWrapper)recipe;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipeValid(CannerCanningWrapper recipe) {
/* 26 */     return (!recipe.getInput().isEmpty() && !recipe.getCan().isEmpty() && !StackUtil.isEmpty(recipe.getOutput()));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\CannerCanningHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */