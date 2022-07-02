/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IElectrolyzerRecipeManager;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElectrolyzerRecipeManager
/*    */   implements IElectrolyzerRecipeManager
/*    */ {
/*    */   public void addRecipe(String input, int inputAmount, int EUaTick, IElectrolyzerRecipeManager.ElectrolyzerOutput... outputs) {
/* 19 */     addRecipe(input, inputAmount, EUaTick, 200, outputs);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(@Nonnull String input, int inputAmount, int EUaTick, int ticksNeeded, @Nonnull IElectrolyzerRecipeManager.ElectrolyzerOutput... outputs) {
/* 24 */     if (this.fluidMap.containsKey(input)) {
/* 25 */       throw new RuntimeException("The fluid " + input + " already has an output assigned.");
/*    */     }
/* 27 */     this.fluidMap.put(input, new IElectrolyzerRecipeManager.ElectrolyzerRecipe(inputAmount, EUaTick, ticksNeeded, outputs));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IElectrolyzerRecipeManager.ElectrolyzerRecipe getElectrolysisInformation(Fluid fluid) {
/* 33 */     return (fluid == null) ? null : this.fluidMap.get(fluid.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public IElectrolyzerRecipeManager.ElectrolyzerOutput[] getOutput(Fluid input) {
/* 38 */     IElectrolyzerRecipeManager.ElectrolyzerRecipe er = getElectrolysisInformation(input);
/* 39 */     return (er == null) ? null : er.outputs;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 44 */     return (fluid != null && this.fluidMap.containsKey(fluid.getName()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Fluid> getAcceptedFluids() {
/* 49 */     Set<Fluid> ret = new HashSet<>(this.fluidMap.size() * 2, 0.5F);
/*    */     
/* 51 */     for (String fluidName : this.fluidMap.keySet()) {
/* 52 */       Fluid fluid = FluidRegistry.getFluid(fluidName);
/* 53 */       if (fluid != null) ret.add(fluid);
/*    */     
/*    */     } 
/* 56 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, IElectrolyzerRecipeManager.ElectrolyzerRecipe> getRecipeMap() {
/* 61 */     return Collections.unmodifiableMap(this.fluidMap);
/*    */   }
/*    */   
/* 64 */   private final Map<String, IElectrolyzerRecipeManager.ElectrolyzerRecipe> fluidMap = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\ElectrolyzerRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */