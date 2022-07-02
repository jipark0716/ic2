/*   */ package ic2.core.recipe.dynamic;
/*   */ 
/*   */ public abstract class RecipeOutputIngredient<T> extends RecipeIngredient<T> {
/*   */   protected RecipeOutputIngredient(T ingredient) {
/* 5 */     super(ingredient);
/*   */   }
/*   */   
/*   */   public abstract RecipeOutputIngredient<T> copy();
/*   */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeOutputIngredient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */