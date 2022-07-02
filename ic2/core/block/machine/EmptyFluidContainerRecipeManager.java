/*    */ package ic2.core.block.machine;
/*    */ 
/*    */ import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.api.recipe.MachineRecipeResult;
/*    */ import ic2.api.util.FluidContainerOutputMode;
/*    */ import ic2.core.util.LiquidUtil;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyFluidContainerRecipeManager
/*    */   implements IEmptyFluidContainerRecipeManager
/*    */ {
/*    */   public boolean addRecipe(Void input, IEmptyFluidContainerRecipeManager.Output output, NBTTagCompound metadata, boolean replace) {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> apply(ItemStack input, boolean acceptTest) {
/* 28 */     return apply(input, null, FluidContainerOutputMode.AnyToOutput, acceptTest);
/*    */   }
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> apply(ItemStack input, Fluid requiredFluid, FluidContainerOutputMode outputMode, boolean acceptTest) {
/* 33 */     if (StackUtil.isEmpty(input)) return null;
/*    */     
/* 35 */     LiquidUtil.FluidOperationResult result = LiquidUtil.drainContainer(input, requiredFluid, 2147483647, outputMode);
/* 36 */     if (result == null) return null;
/*    */     
/* 38 */     Collection<ItemStack> output = StackUtil.isEmpty(result.extraOutput) ? Collections.<ItemStack>emptyList() : Collections.<ItemStack>singletonList(result.extraOutput);
/*    */     
/* 40 */     return (new MachineRecipe(null, new IEmptyFluidContainerRecipeManager.Output(output, result.fluidChange))).getResult(result.inPlaceOutput);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<? extends MachineRecipe<Void, IEmptyFluidContainerRecipeManager.Output>> getRecipes() {
/* 45 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIterable() {
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\EmptyFluidContainerRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */