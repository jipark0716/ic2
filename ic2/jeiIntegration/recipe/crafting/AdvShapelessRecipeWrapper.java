/*    */ package ic2.jeiIntegration.recipe.crafting;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.core.recipe.AdvShapelessRecipe;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.Ic2Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdvShapelessRecipeWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final AdvShapelessRecipe recipe;
/*    */   
/*    */   public AdvShapelessRecipeWrapper(AdvShapelessRecipe recipe) {
/* 25 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public List<List<ItemStack>> getInputs() {
/* 29 */     List<List<ItemStack>> ret = new ArrayList<>(this.recipe.input.length);
/*    */     
/* 31 */     for (IRecipeInput input : this.recipe.input) {
/* 32 */       ret.add(input.getInputs());
/*    */     }
/*    */     
/* 35 */     if (ret.size() == 1 && ((List)ret.get(0)).size() == 1) {
/* 36 */       ItemStack stack = ((List<ItemStack>)ret.get(0)).get(0);
/*    */ 
/*    */       
/* 39 */       if (stack.func_77973_b() == ItemName.painter.getInstance() && stack.func_77960_j() == 32767) {
/* 40 */         ret.set(0, (List<ItemStack>)Arrays.<Ic2Color>stream(Ic2Color.values).map(ItemName.painter::getItemStack).collect(Collectors.toList()));
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 49 */     ingredients.setInputLists(ItemStack.class, getInputs());
/* 50 */     ingredients.setOutput(ItemStack.class, this.recipe.func_77571_b());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\crafting\AdvShapelessRecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */