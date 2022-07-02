/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.ICannerEnrichRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import java.util.List;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CannerEnrichmentWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final FluidStack input;
/*    */   private final FluidStack output;
/*    */   private final IRecipeInput additive;
/*    */   final IORecipeCategory<ICannerEnrichRecipeManager> category;
/*    */   
/*    */   CannerEnrichmentWrapper(ICannerEnrichRecipeManager.Input input, FluidStack output, IORecipeCategory<ICannerEnrichRecipeManager> category) {
/* 22 */     this.input = input.fluid;
/* 23 */     this.additive = input.additive;
/* 24 */     this.output = output;
/* 25 */     this.category = category;
/*    */   }
/*    */   
/*    */   public FluidStack getInput() {
/* 29 */     return this.input;
/*    */   }
/*    */   
/*    */   public List<ItemStack> getAdditives() {
/* 33 */     return this.additive.getInputs();
/*    */   }
/*    */   
/*    */   public FluidStack getOutput() {
/* 37 */     return this.output;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 42 */     ingredients.setInput(FluidStack.class, getInput());
/* 43 */     ingredients.setInputs(ItemStack.class, getAdditives());
/* 44 */     ingredients.setOutput(FluidStack.class, getOutput());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\CannerEnrichmentWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */