/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IFermenterRecipeManager;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ public class FermenterRecipeManager
/*    */   implements IFermenterRecipeManager
/*    */ {
/*    */   public void addRecipe(String input, int inputAmount, int heat, String output, int outputAmount) {
/* 18 */     if (this.fluidMap.containsKey(input)) {
/* 19 */       throw new RuntimeException("The fluid " + input + " already has an output assigned.");
/*    */     }
/* 21 */     this.fluidMap.put(input, new IFermenterRecipeManager.FermentationProperty(inputAmount, heat, output, outputAmount));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IFermenterRecipeManager.FermentationProperty getFermentationInformation(Fluid fluid) {
/* 27 */     return (fluid == null) ? null : this.fluidMap.get(fluid.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidStack getOutput(Fluid input) {
/* 32 */     IFermenterRecipeManager.FermentationProperty fp = getFermentationInformation(input);
/* 33 */     if (fp == null) {
/* 34 */       return null;
/*    */     }
/* 36 */     return (FluidRegistry.getFluid(fp.output) == null) ? null : new FluidStack(FluidRegistry.getFluid(fp.output), fp.outputAmount);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 42 */     return (fluid != null && this.fluidMap.containsKey(fluid.getName()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Fluid> getAcceptedFluids() {
/* 47 */     Set<Fluid> ret = new HashSet<>(this.fluidMap.size() * 2, 0.5F);
/*    */     
/* 49 */     for (String fluidName : this.fluidMap.keySet()) {
/* 50 */       Fluid fluid = FluidRegistry.getFluid(fluidName);
/* 51 */       if (fluid != null) ret.add(fluid);
/*    */     
/*    */     } 
/* 54 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, IFermenterRecipeManager.FermentationProperty> getRecipeMap() {
/* 59 */     return Collections.unmodifiableMap(this.fluidMap);
/*    */   }
/*    */   
/* 62 */   private final Map<String, IFermenterRecipeManager.FermentationProperty> fluidMap = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\FermenterRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */