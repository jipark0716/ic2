/*    */ package ic2.core.recipe.dynamic;
/*    */ 
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipeInputItemStack extends RecipeInputIngredient<ItemStack> {
/*    */   public static RecipeInputItemStack of(ItemStack ingredient) {
/*  8 */     return new RecipeInputItemStack(ingredient);
/*    */   }
/*    */   
/*    */   public static RecipeInputItemStack of(ItemStack ingredient, boolean consumable) {
/* 12 */     return new RecipeInputItemStack(ingredient, consumable);
/*    */   }
/*    */   
/*    */   protected RecipeInputItemStack(ItemStack ingredient) {
/* 16 */     super(ingredient);
/*    */   }
/*    */   
/*    */   protected RecipeInputItemStack(ItemStack ingredient, boolean consumable) {
/* 20 */     super(ingredient, consumable);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getUnspecific() {
/* 25 */     return this.ingredient.func_77973_b();
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeInputIngredient<ItemStack> copy() {
/* 30 */     return of(this.ingredient.func_77946_l());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 35 */     return StackUtil.isEmpty(this.ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 40 */     return StackUtil.getSize(this.ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public void shrink(int amount) {
/* 45 */     this.ingredient.func_190918_g(amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Object other) {
/* 50 */     if (!(other instanceof ItemStack)) {
/* 51 */       return false;
/*    */     }
/*    */     
/* 54 */     return StackUtil.checkItemEqualityStrict(this.ingredient, (ItemStack)other);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesStrict(Object other) {
/* 59 */     return matches(other);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toStringSafe() {
/* 64 */     return StackUtil.toStringSafe(this.ingredient);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeInputItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */