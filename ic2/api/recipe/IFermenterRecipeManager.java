/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidRegistry;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFermenterRecipeManager
/*    */   extends ILiquidAcceptManager
/*    */ {
/*    */   void addRecipe(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3);
/*    */   
/*    */   FermentationProperty getFermentationInformation(Fluid paramFluid);
/*    */   
/*    */   FluidStack getOutput(Fluid paramFluid);
/*    */   
/*    */   Map<String, FermentationProperty> getRecipeMap();
/*    */   
/*    */   public static final class FermentationProperty
/*    */   {
/*    */     public final int inputAmount;
/*    */     public final int heat;
/*    */     public final String output;
/*    */     public final int outputAmount;
/*    */     
/*    */     public FermentationProperty(int inputAmount, int heat, String output, int outputAmount) {
/* 49 */       this.inputAmount = inputAmount;
/* 50 */       this.heat = heat;
/* 51 */       this.output = output;
/* 52 */       this.outputAmount = outputAmount;
/*    */     }
/*    */     
/*    */     public FluidStack getOutput() {
/* 56 */       return (FluidRegistry.getFluid(this.output) == null) ? null : new FluidStack(FluidRegistry.getFluid(this.output), this.outputAmount);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IFermenterRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */