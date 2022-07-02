/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import net.minecraft.item.ItemStack;
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
/*    */ public interface ICannerEnrichRecipeManager
/*    */   extends IMachineRecipeManager<ICannerEnrichRecipeManager.Input, FluidStack, ICannerEnrichRecipeManager.RawInput>
/*    */ {
/*    */   @Deprecated
/*    */   void addRecipe(FluidStack paramFluidStack1, IRecipeInput paramIRecipeInput, FluidStack paramFluidStack2);
/*    */   
/*    */   @Deprecated
/*    */   RecipeOutput getOutputFor(FluidStack paramFluidStack, ItemStack paramItemStack, boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/*    */   public static class Input
/*    */   {
/*    */     public final FluidStack fluid;
/*    */     public final IRecipeInput additive;
/*    */     
/*    */     public Input(FluidStack fluid, IRecipeInput additive) {
/* 35 */       this.fluid = fluid;
/* 36 */       this.additive = additive;
/*    */     }
/*    */     
/*    */     public boolean matches(FluidStack fluid, ItemStack additive) {
/* 40 */       return (this.fluid.isFluidEqual(fluid) && this.additive.matches(additive));
/*    */     }
/*    */   }
/*    */   
/*    */   public static class RawInput {
/*    */     public final FluidStack fluid;
/*    */     public final ItemStack additive;
/*    */     
/*    */     public RawInput(FluidStack fluid, ItemStack additive) {
/* 49 */       this.fluid = fluid;
/* 50 */       this.additive = additive;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\ICannerEnrichRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */