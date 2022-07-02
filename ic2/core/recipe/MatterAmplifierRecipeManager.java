/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.api.recipe.MachineRecipeResult;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MatterAmplifierRecipeManager
/*    */   implements IMachineRecipeManager<IRecipeInput, Integer, ItemStack>
/*    */ {
/*    */   public boolean addRecipe(IRecipeInput input, Integer output, NBTTagCompound metadata, boolean replace) {
/* 19 */     if (output.intValue() <= 0) throw new IllegalArgumentException("non-positive amplification");
/*    */     
/* 21 */     for (ItemStack stack : input.getInputs()) {
/* 22 */       MachineRecipe<IRecipeInput, Integer> recipe = getRecipe(stack, true);
/*    */       
/* 24 */       if (recipe != null) {
/* 25 */         if (!replace) {
/* 26 */           return false;
/*    */         }
/* 28 */         this.recipes.remove(recipe);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 33 */     this.recipes.add(new MachineRecipe(input, output));
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<IRecipeInput, Integer, ItemStack> apply(ItemStack input, boolean acceptTest) {
/* 40 */     MachineRecipe<IRecipeInput, Integer> recipe = getRecipe(input, acceptTest);
/* 41 */     if (recipe == null) return null;
/*    */     
/* 43 */     return recipe.getResult(StackUtil.copyShrunk(input, ((IRecipeInput)recipe.getInput()).getAmount()));
/*    */   }
/*    */   
/*    */   private MachineRecipe<IRecipeInput, Integer> getRecipe(ItemStack stack, boolean acceptTest) {
/* 47 */     for (MachineRecipe<IRecipeInput, Integer> recipe : this.recipes) {
/* 48 */       if (((IRecipeInput)recipe.getInput()).matches(stack) && (acceptTest || ((IRecipeInput)recipe.getInput()).getAmount() <= StackUtil.getSize(stack))) return recipe;
/*    */     
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<? extends MachineRecipe<IRecipeInput, Integer>> getRecipes() {
/* 56 */     return this.recipes;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIterable() {
/* 61 */     return true;
/*    */   }
/*    */   
/* 64 */   private final List<MachineRecipe<IRecipeInput, Integer>> recipes = new ArrayList<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\MatterAmplifierRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */