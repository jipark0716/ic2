/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class CannerCanningWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final IRecipeInput input;
/*    */   private final IRecipeInput can;
/*    */   private final ItemStack output;
/*    */   final IORecipeCategory<ICannerBottleRecipeManager> category;
/*    */   
/*    */   CannerCanningWrapper(ICannerBottleRecipeManager.Input input, ItemStack output, IORecipeCategory<ICannerBottleRecipeManager> category) {
/* 21 */     this.input = input.fill;
/* 22 */     this.can = input.container;
/* 23 */     this.output = output;
/* 24 */     this.category = category;
/*    */   }
/*    */   
/*    */   public List<ItemStack> getInput() {
/* 28 */     return this.input.getInputs();
/*    */   }
/*    */   
/*    */   public List<ItemStack> getCan() {
/* 32 */     return this.can.getInputs();
/*    */   }
/*    */   
/*    */   public ItemStack getOutput() {
/* 36 */     return this.output;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 41 */     ingredients.setInputLists(ItemStack.class, Arrays.asList(new List[] { getInput(), getCan() }));
/* 42 */     ingredients.setOutput(ItemStack.class, getOutput());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\CannerCanningWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */