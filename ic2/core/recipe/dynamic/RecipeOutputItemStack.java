/*    */ package ic2.core.recipe.dynamic;
/*    */ 
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipeOutputItemStack extends RecipeOutputIngredient<ItemStack> {
/*    */   public static RecipeOutputItemStack of(ItemStack ingredient) {
/*  8 */     return new RecipeOutputItemStack(ingredient);
/*    */   }
/*    */   
/*    */   protected RecipeOutputItemStack(ItemStack ingredient) {
/* 12 */     super(ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeOutputIngredient<ItemStack> copy() {
/* 17 */     return of(this.ingredient.func_77946_l());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 22 */     return StackUtil.isEmpty(this.ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Object other) {
/* 27 */     if (!(other instanceof ItemStack)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     return StackUtil.checkItemEqualityStrict(this.ingredient, (ItemStack)other);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesStrict(Object other) {
/* 36 */     return matches(other);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toStringSafe() {
/* 41 */     return StackUtil.toStringSafe(this.ingredient);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeOutputItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */