/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ 
/*    */ public class RecipeInputIngredient implements IRecipeInput {
/*    */   private Ingredient ingredient;
/*    */   
/*    */   RecipeInputIngredient(Ingredient ingredient) {
/* 13 */     this.ingredient = ingredient;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 18 */     return this.ingredient.apply(subject);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 23 */     return Arrays.asList(this.ingredient.func_193365_a());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 28 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Ingredient getIngredient() {
/* 33 */     return this.ingredient;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputIngredient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */