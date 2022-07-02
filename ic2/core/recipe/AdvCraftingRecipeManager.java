/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.ICraftingRecipeManager;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class AdvCraftingRecipeManager
/*    */   implements ICraftingRecipeManager
/*    */ {
/*    */   public void addRecipe(ItemStack output, Object... input) {
/* 10 */     AdvRecipe.addAndRegister(output, input);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addShapelessRecipe(ItemStack output, Object... input) {
/* 15 */     AdvShapelessRecipe.addAndRegister(output, input);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addGradualRecipe(ItemStack output, int amount, Object... args) {
/* 20 */     GradualRecipe.addAndRegister(output, amount, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\AdvCraftingRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */