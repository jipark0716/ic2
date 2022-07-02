/*   */ package ic2.core.recipe.dynamic;
/*   */ 
/*   */ public abstract class RecipeIngredient<T> {
/*   */   public RecipeIngredient(T ingredient) {
/* 5 */     this.ingredient = ingredient;
/*   */   }
/*   */   
/*   */   public final T ingredient;
/*   */   
/*   */   public abstract boolean isEmpty();
/*   */   
/*   */   public abstract boolean matches(Object paramObject);
/*   */   
/*   */   public abstract boolean matchesStrict(Object paramObject);
/*   */   
/*   */   public abstract String toStringSafe();
/*   */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeIngredient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */