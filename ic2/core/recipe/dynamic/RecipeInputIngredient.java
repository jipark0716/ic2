/*    */ package ic2.core.recipe.dynamic;
/*    */ 
/*    */ public abstract class RecipeInputIngredient<T> extends RecipeIngredient<T> {
/*    */   protected RecipeInputIngredient(T ingredient) {
/*  5 */     this(ingredient, true);
/*    */   }
/*    */   public final boolean consumable;
/*    */   protected RecipeInputIngredient(T ingredient, boolean consumable) {
/*  9 */     super(ingredient);
/*    */     
/* 11 */     this.consumable = consumable;
/*    */   }
/*    */   
/*    */   public abstract Object getUnspecific();
/*    */   
/*    */   public abstract RecipeInputIngredient<T> copy();
/*    */   
/*    */   public abstract int getCount();
/*    */   
/*    */   public abstract void shrink(int paramInt);
/*    */   
/*    */   public boolean isConsumable() {
/* 23 */     return this.consumable;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeInputIngredient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */