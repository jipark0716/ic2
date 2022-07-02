/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.IFluidHeatManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FluidHeatManager
/*    */   implements IFluidHeatManager
/*    */ {
/*    */   public void addFluid(String fluidName, int amount, int heat) {
/* 17 */     if (this.burnProperties.containsKey(fluidName)) {
/* 18 */       throw new RuntimeException("The fluid " + fluidName + " does already have a burn property assigned.");
/*    */     }
/*    */     
/* 21 */     this.burnProperties.put(fluidName, new IFluidHeatManager.BurnProperty(amount, heat));
/*    */   }
/*    */ 
/*    */   
/*    */   public IFluidHeatManager.BurnProperty getBurnProperty(Fluid fluid) {
/* 26 */     if (fluid == null) return null;
/*    */     
/* 28 */     return this.burnProperties.get(fluid.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 33 */     return this.burnProperties.containsKey(fluid.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Fluid> getAcceptedFluids() {
/* 38 */     Set<Fluid> ret = new HashSet<>();
/*    */     
/* 40 */     for (String fluidName : this.burnProperties.keySet()) {
/* 41 */       Fluid fluid = FluidRegistry.getFluid(fluidName);
/*    */       
/* 43 */       if (fluid != null) ret.add(fluid);
/*    */     
/*    */     } 
/* 46 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, IFluidHeatManager.BurnProperty> getBurnProperties() {
/* 51 */     return this.burnProperties;
/*    */   }
/*    */ 
/*    */   
/* 55 */   private final Map<String, IFluidHeatManager.BurnProperty> burnProperties = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\FluidHeatManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */