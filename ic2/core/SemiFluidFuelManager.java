/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.recipe.ISemiFluidFuelManager;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SemiFluidFuelManager
/*    */   implements ISemiFluidFuelManager
/*    */ {
/*    */   public void addFluid(String fluidName, int amount, double power) {
/* 18 */     if (this.burnProperties.containsKey(fluidName)) {
/* 19 */       throw new RuntimeException("The fluid " + fluidName + " does already have a burn property assigned.");
/*    */     }
/*    */     
/* 22 */     this.burnProperties.put(fluidName, new ISemiFluidFuelManager.BurnProperty(amount, power));
/*    */   }
/*    */ 
/*    */   
/*    */   public ISemiFluidFuelManager.BurnProperty getBurnProperty(Fluid fluid) {
/* 27 */     if (fluid == null) return null;
/*    */     
/* 29 */     return this.burnProperties.get(fluid.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsFluid(Fluid fluid) {
/* 34 */     return (fluid != null && this.burnProperties.containsKey(fluid.getName()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Fluid> getAcceptedFluids() {
/* 39 */     Set<Fluid> ret = new HashSet<>(this.burnProperties.size() * 2, 0.5F);
/*    */     
/* 41 */     for (String fluidName : this.burnProperties.keySet()) {
/* 42 */       Fluid fluid = FluidRegistry.getFluid(fluidName);
/*    */       
/* 44 */       if (fluid != null) ret.add(fluid);
/*    */     
/*    */     } 
/* 47 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, ISemiFluidFuelManager.BurnProperty> getBurnProperties() {
/* 52 */     return Collections.unmodifiableMap(this.burnProperties);
/*    */   }
/*    */   
/* 55 */   private final Map<String, ISemiFluidFuelManager.BurnProperty> burnProperties = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\SemiFluidFuelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */