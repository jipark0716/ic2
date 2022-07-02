/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import com.google.common.collect.Iterables;
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.api.recipe.MachineRecipeResult;
/*    */ import ic2.api.recipe.Recipes;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.item.crafting.FurnaceRecipes;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SmeltingRecipeManager
/*    */   implements IMachineRecipeManager<ItemStack, ItemStack, ItemStack>
/*    */ {
/*    */   public boolean addRecipe(ItemStack input, ItemStack output, NBTTagCompound metadata, boolean replace) {
/* 23 */     FurnaceRecipes recipes = FurnaceRecipes.func_77602_a();
/*    */     
/* 25 */     if (!StackUtil.isEmpty(recipes.func_151395_a(input)) && !replace) {
/* 26 */       return false;
/*    */     }
/*    */     
/* 29 */     float experience = (metadata != null && metadata.func_74764_b("experience")) ? metadata.func_74760_g("experience") : 0.0F;
/* 30 */     if (experience < 0.0F) throw new IllegalArgumentException("Negative xp for " + StackUtil.toStringSafe(input) + " -> " + StackUtil.toStringSafe(output));
/*    */     
/* 32 */     recipes.func_151394_a(input, output, experience);
/*    */     
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<ItemStack, ItemStack, ItemStack> apply(ItemStack input, boolean acceptTest) {
/* 39 */     FurnaceRecipes recipes = FurnaceRecipes.func_77602_a();
/* 40 */     ItemStack output = recipes.func_151395_a(input);
/* 41 */     if (StackUtil.isEmpty(output)) return null;
/*    */     
/* 43 */     NBTTagCompound nbt = new NBTTagCompound();
/* 44 */     nbt.func_74776_a("experience", recipes.func_151398_b(output) * StackUtil.getSize(output));
/*    */     
/* 46 */     return (new MachineRecipe(input, output, nbt)).getResult(StackUtil.copyShrunk(input, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<? extends MachineRecipe<ItemStack, ItemStack>> getRecipes() {
/* 51 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIterable() {
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum SmeltingBridge
/*    */     implements IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack>
/*    */   {
/* 65 */     INSTANCE;
/*    */ 
/*    */     
/*    */     public boolean addRecipe(IRecipeInput input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
/* 69 */       ItemStack realOutput = (ItemStack)Iterables.getOnlyElement(output);
/* 70 */       boolean ret = false;
/*    */       
/* 72 */       for (ItemStack stack : input.getInputs()) {
/* 73 */         ret |= Recipes.furnace.addRecipe(stack, realOutput, metadata, replace);
/*    */       }
/*    */       
/* 76 */       return ret;
/*    */     }
/*    */ 
/*    */     
/*    */     public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> apply(ItemStack input, boolean acceptTest) {
/* 81 */       MachineRecipeResult<ItemStack, ItemStack, ItemStack> normal = Recipes.furnace.apply(input, acceptTest);
/* 82 */       if (normal == null) return null;
/*    */       
/* 84 */       MachineRecipe<ItemStack, ItemStack> result = normal.getRecipe();
/* 85 */       IRecipeInput resultIn = Recipes.inputFactory.forStack((ItemStack)result.getInput());
/* 86 */       Collection<ItemStack> resultOut = Collections.singletonList(result.getOutput());
/* 87 */       NBTTagCompound resultNBT = result.getMetaData();
/*    */       
/* 89 */       return (new MachineRecipe(resultIn, resultOut, resultNBT)).getResult(normal.getAdjustedInput());
/*    */     }
/*    */ 
/*    */     
/*    */     public Iterable<? extends MachineRecipe<IRecipeInput, Collection<ItemStack>>> getRecipes() {
/* 94 */       throw new UnsupportedOperationException();
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isIterable() {
/* 99 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\SmeltingRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */