/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.api.recipe.IElectrolyzerRecipeManager;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.BlankRecipeWrapper;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElectrolyzerWrapper
/*    */   extends BlankRecipeWrapper
/*    */ {
/*    */   private final FluidStack input;
/*    */   private final List<FluidStack> outputs;
/*    */   final IORecipeCategory<IElectrolyzerRecipeManager> category;
/*    */   
/*    */   ElectrolyzerWrapper(FluidStack input, IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs, IORecipeCategory<IElectrolyzerRecipeManager> category) {
/* 21 */     this.input = input;
/*    */     
/* 23 */     List<FluidStack> temp = new ArrayList<>(outputs.length);
/* 24 */     for (IElectrolyzerRecipeManager.ElectrolyzerOutput output : outputs) {
/* 25 */       temp.add(output.getOutput());
/*    */     }
/* 27 */     this.outputs = Collections.unmodifiableList(temp);
/*    */     
/* 29 */     this.category = category;
/*    */   }
/*    */   
/*    */   public FluidStack getFluidInput() {
/* 33 */     return this.input;
/*    */   }
/*    */   
/*    */   public List<FluidStack> getFluidOutputs() {
/* 37 */     return this.outputs;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getIngredients(IIngredients ingredients) {
/* 42 */     ingredients.setInput(FluidStack.class, getFluidInput());
/* 43 */     ingredients.setOutputs(FluidStack.class, getFluidOutputs());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\ElectrolyzerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */