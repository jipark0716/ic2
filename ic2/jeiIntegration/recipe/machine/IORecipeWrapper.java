/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IORecipeWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final IRecipeInput input;
/*    */   private final Collection<ItemStack> output;
/*    */   final IORecipeCategory<?> category;
/*    */   
/*    */   IORecipeWrapper(MachineRecipe<IRecipeInput, Collection<ItemStack>> container, IORecipeCategory<?> category) {
/* 25 */     this((IRecipeInput)container.getInput(), (Collection<ItemStack>)container.getOutput(), category);
/*    */   }
/*    */   
/*    */   protected IORecipeWrapper(IRecipeInput input, Collection<ItemStack> output, IORecipeCategory<?> category) {
/* 29 */     this.input = input;
/* 30 */     this.output = output;
/* 31 */     this.category = category;
/*    */   }
/*    */   
/*    */   public List<List<ItemStack>> getInputs() {
/* 35 */     List<ItemStack> inputs = this.input.getInputs();
/*    */     
/* 37 */     if (inputs.isEmpty()) {
/* 38 */       return Collections.emptyList();
/*    */     }
/* 40 */     return Collections.singletonList(inputs);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getOutputs() {
/* 45 */     return new ArrayList<>(this.output);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {}
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 54 */     ingredients.setInputLists(ItemStack.class, getInputs());
/* 55 */     ingredients.setOutputs(ItemStack.class, getOutputs());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\IORecipeWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */