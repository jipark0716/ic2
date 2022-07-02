/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import com.google.common.collect.Iterables;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.IRecipeInputFactory;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.Ingredient;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeInputFactory
/*    */   implements IRecipeInputFactory
/*    */ {
/*    */   public IRecipeInput forStack(ItemStack stack) {
/* 19 */     return new RecipeInputItemStack(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forStack(ItemStack stack, int amount) {
/* 24 */     return new RecipeInputItemStack(stack, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forExactStack(ItemStack stack) {
/* 29 */     if (stack.func_77960_j() == 32767) {
/* 30 */       return forStack(stack);
/*    */     }
/* 32 */     return new RecipeInputItemStackExact(stack);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IRecipeInput forExactStack(ItemStack stack, int amount) {
/* 38 */     if (stack.func_77960_j() == 32767) {
/* 39 */       return forStack(stack, amount);
/*    */     }
/* 41 */     return new RecipeInputItemStackExact(stack, amount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IRecipeInput forOreDict(String name) {
/* 47 */     return new RecipeInputOreDict(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forOreDict(String name, int amount) {
/* 52 */     return new RecipeInputOreDict(name, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forOreDict(String name, int amount, int metaOverride) {
/* 57 */     return new RecipeInputOreDict(name, amount, Integer.valueOf(metaOverride));
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forFluidContainer(Fluid fluid) {
/* 62 */     return new RecipeInputFluidContainer(fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forFluidContainer(Fluid fluid, int amount) {
/* 67 */     return new RecipeInputFluidContainer(fluid, amount);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forAny(IRecipeInput... options) {
/* 72 */     return new RecipeInputMultiple(options);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forAny(Iterable<IRecipeInput> options) {
/* 77 */     if (options instanceof Collection) {
/* 78 */       return new RecipeInputMultiple((IRecipeInput[])((Collection)options).toArray((Object[])new IRecipeInput[0]));
/*    */     }
/* 80 */     return new RecipeInputMultiple((IRecipeInput[])Iterables.toArray(options, IRecipeInput.class));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Ingredient getIngredient(IRecipeInput input) {
/* 86 */     return new IngredientRecipeInput(input);
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipeInput forIngredient(Ingredient ingredient) {
/* 91 */     return new RecipeInputIngredient(ingredient);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */