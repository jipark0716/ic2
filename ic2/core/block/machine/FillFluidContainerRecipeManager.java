/*    */ package ic2.core.block.machine;
/*    */ 
/*    */ import ic2.api.recipe.IFillFluidContainerRecipeManager;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.api.recipe.MachineRecipeResult;
/*    */ import ic2.api.util.FluidContainerOutputMode;
/*    */ import ic2.core.util.LiquidUtil;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FillFluidContainerRecipeManager
/*    */   implements IFillFluidContainerRecipeManager
/*    */ {
/*    */   public boolean addRecipe(Void input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> apply(IFillFluidContainerRecipeManager.Input input, boolean acceptTest) {
/* 28 */     return apply(input, FluidContainerOutputMode.AnyToOutput, acceptTest);
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> apply(IFillFluidContainerRecipeManager.Input input, FluidContainerOutputMode outputMode, boolean acceptTest) {
/* 33 */     if (StackUtil.isEmpty(input.container) || input.fluid == null) {
/* 34 */       if (!acceptTest) return null; 
/* 35 */       if (StackUtil.isEmpty(input.container) && input.fluid == null) return null;
/*    */       
/* 37 */       if (StackUtil.isEmpty(input.container) || LiquidUtil.isFillableFluidContainer(input.container)) {
/* 38 */         return (new MachineRecipe(null, Collections.emptyList())).getResult(input);
/*    */       }
/* 40 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 44 */     if (input.fluid.amount <= 0) return null;
/*    */     
/* 46 */     LiquidUtil.FluidOperationResult result = LiquidUtil.fillContainer(input.container, input.fluid, outputMode);
/* 47 */     if (result == null) return null;
/*    */     
/* 49 */     Collection<ItemStack> output = StackUtil.isEmpty(result.extraOutput) ? Collections.<ItemStack>emptyList() : Collections.<ItemStack>singletonList(result.extraOutput);
/* 50 */     FluidStack changedFluid = (result.fluidChange.amount >= input.fluid.amount) ? null : new FluidStack(input.fluid, input.fluid.amount - result.fluidChange.amount);
/*    */     
/* 52 */     return (new MachineRecipe(null, output)).getResult(new IFillFluidContainerRecipeManager.Input(result.inPlaceOutput, changedFluid));
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<? extends MachineRecipe<Void, Collection<ItemStack>>> getRecipes() {
/* 57 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIterable() {
/* 62 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\FillFluidContainerRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */