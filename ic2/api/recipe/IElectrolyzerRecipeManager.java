/*     */ package ic2.api.recipe;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.ParametersAreNonnullByDefault;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IElectrolyzerRecipeManager
/*     */   extends ILiquidAcceptManager
/*     */ {
/*     */   void addRecipe(@Nonnull String paramString, int paramInt1, int paramInt2, @Nonnull ElectrolyzerOutput... paramVarArgs);
/*     */   
/*     */   void addRecipe(@Nonnull String paramString, int paramInt1, int paramInt2, int paramInt3, @Nonnull ElectrolyzerOutput... paramVarArgs);
/*     */   
/*     */   ElectrolyzerRecipe getElectrolysisInformation(Fluid paramFluid);
/*     */   
/*     */   ElectrolyzerOutput[] getOutput(Fluid paramFluid);
/*     */   
/*     */   Map<String, ElectrolyzerRecipe> getRecipeMap();
/*     */   
/*     */   @ParametersAreNonnullByDefault
/*     */   public static final class ElectrolyzerOutput
/*     */   {
/*     */     public final String fluidName;
/*     */     public final int fluidAmount;
/*     */     public final EnumFacing tankDirection;
/*     */     
/*     */     public ElectrolyzerOutput(String fluidName, int fluidAmount, EnumFacing tankDirection) {
/*  76 */       this.fluidName = fluidName;
/*  77 */       this.fluidAmount = fluidAmount;
/*  78 */       this.tankDirection = tankDirection;
/*     */     }
/*     */     
/*     */     public FluidStack getOutput() {
/*  82 */       return (FluidRegistry.getFluid(this.fluidName) == null) ? null : new FluidStack(FluidRegistry.getFluid(this.fluidName), this.fluidAmount);
/*     */     }
/*     */     
/*     */     public Pair<FluidStack, EnumFacing> getFullOutput() {
/*  86 */       return Pair.of(getOutput(), this.tankDirection);
/*     */     } }
/*     */   
/*     */   public static final class ElectrolyzerRecipe {
/*     */     public final int inputAmount;
/*     */     public final int EUaTick;
/*     */     public final int ticksNeeded;
/*     */     public final IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs;
/*     */     
/*     */     public ElectrolyzerRecipe(int inputAmount, int EUaTick, int ticksNeeded, IElectrolyzerRecipeManager.ElectrolyzerOutput... outputs) {
/*  96 */       this.inputAmount = inputAmount;
/*  97 */       this.EUaTick = EUaTick;
/*  98 */       this.ticksNeeded = ticksNeeded;
/*  99 */       this.outputs = validateOutputs(outputs);
/*     */     }
/*     */     
/*     */     private IElectrolyzerRecipeManager.ElectrolyzerOutput[] validateOutputs(IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs) {
/* 103 */       if (outputs.length < 1 || outputs.length > 5) {
/* 104 */         throw new RuntimeException("Cannot have " + outputs.length + " outputs of an Electrolzer recipe, must be between 1 and 5");
/*     */       }
/* 106 */       Set<EnumFacing> directions = new HashSet<>(outputs.length * 2, 0.5F);
/* 107 */       for (IElectrolyzerRecipeManager.ElectrolyzerOutput output : outputs) {
/* 108 */         if (!directions.add(output.tankDirection)) {
/* 109 */           throw new RuntimeException("Duplicate direction in Electrolzer outputs (" + output.tankDirection + ')');
/*     */         }
/*     */       } 
/* 112 */       return outputs;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IElectrolyzerRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */