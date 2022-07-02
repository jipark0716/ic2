/*    */ package ic2.core.uu;
/*    */ 
/*    */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.LogCategory;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class CannerBottleSolidResolver
/*    */   implements IRecipeResolver
/*    */ {
/*    */   private static final double transformCost = 0.0D;
/*    */   
/*    */   public List<RecipeTransformation> getTransformations() {
/* 19 */     List<RecipeTransformation> ret = new ArrayList<>();
/*    */     
/* 21 */     for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe : (Iterable<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>>)Recipes.cannerBottle.getRecipes()) {
/*    */       try {
/* 23 */         List<LeanItemStack> container = RecipeUtil.convertOutputs(((ICannerBottleRecipeManager.Input)recipe.getInput()).container.getInputs());
/* 24 */         List<LeanItemStack> fill = RecipeUtil.convertOutputs(((ICannerBottleRecipeManager.Input)recipe.getInput()).fill.getInputs());
/* 25 */         if (container.isEmpty() || fill.isEmpty())
/*    */           continue; 
/* 27 */         List<List<LeanItemStack>> inputs = new ArrayList<>(2);
/* 28 */         inputs.add(container);
/* 29 */         inputs.add(fill);
/*    */         
/* 31 */         ret.add(new RecipeTransformation(0.0D, inputs, RecipeUtil.convertOutputs(Collections.singletonList(recipe.getOutput()))));
/* 32 */       } catch (IllegalArgumentException e) {
/* 33 */         IC2.log.warn(LogCategory.Uu, e, "invalid recipe");
/*    */       } 
/*    */     } 
/*    */     
/* 37 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\uu\CannerBottleSolidResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */