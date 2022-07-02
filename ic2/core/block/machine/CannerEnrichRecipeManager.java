/*     */ package ic2.core.block.machine;
/*     */ 
/*     */ import ic2.api.recipe.ICannerEnrichRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CannerEnrichRecipeManager
/*     */   implements ICannerEnrichRecipeManager
/*     */ {
/*     */   public boolean addRecipe(ICannerEnrichRecipeManager.Input input, FluidStack output, NBTTagCompound metadata, boolean replace) {
/*  24 */     if (input.fluid == null) throw new NullPointerException("The fluid recipe input is null."); 
/*  25 */     if (input.additive == null) throw new NullPointerException("The additive recipe input is null."); 
/*  26 */     if (output == null) throw new NullPointerException("The recipe output is null."); 
/*  27 */     if (!LiquidUtil.check(input.fluid)) throw new IllegalArgumentException("The fluid recipe input is invalid."); 
/*  28 */     if (!LiquidUtil.check(output)) throw new IllegalArgumentException("The fluid recipe output is invalid.");
/*     */     
/*  30 */     for (ItemStack stack : input.additive.getInputs()) {
/*  31 */       MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack> recipe = getRecipe(input.fluid, stack, true);
/*     */       
/*  33 */       if (recipe != null) {
/*  34 */         if (!replace) {
/*  35 */           return false;
/*     */         }
/*  37 */         this.recipes.remove(recipe);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  42 */     this.recipes.add(new MachineRecipe(input, output));
/*     */     
/*  44 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addRecipe(FluidStack fluid, IRecipeInput additive, FluidStack output) {
/*  49 */     if (!addRecipe(new ICannerEnrichRecipeManager.Input(fluid, additive), output, (NBTTagCompound)null, false)) {
/*  50 */       throw new RuntimeException("ambiguous recipe: [" + fluid + "+" + additive.getInputs() + " -> " + output + "]");
/*     */     }
/*     */   }
/*     */   
/*     */   public MachineRecipeResult<ICannerEnrichRecipeManager.Input, FluidStack, ICannerEnrichRecipeManager.RawInput> apply(ICannerEnrichRecipeManager.RawInput input, boolean acceptTest) {
/*     */     FluidStack remainingFluid;
/*  56 */     MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack> recipe = getRecipe(input.fluid, input.additive, acceptTest);
/*  57 */     if (recipe == null) return null;
/*     */ 
/*     */ 
/*     */     
/*  61 */     if (input.fluid == null) {
/*  62 */       remainingFluid = null;
/*     */     } else {
/*  64 */       remainingFluid = input.fluid.copy();
/*  65 */       remainingFluid.amount -= ((ICannerEnrichRecipeManager.Input)recipe.getInput()).fluid.amount;
/*  66 */       if (remainingFluid.amount <= 0) remainingFluid = null;
/*     */     
/*     */     } 
/*  69 */     return recipe.getResult(new ICannerEnrichRecipeManager.RawInput(remainingFluid, StackUtil.copyShrunk(input.additive, ((ICannerEnrichRecipeManager.Input)recipe.getInput()).additive.getAmount())));
/*     */   }
/*     */   
/*     */   private MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack> getRecipe(FluidStack fluid, ItemStack additive, boolean acceptTest) {
/*  73 */     if (!acceptTest && (fluid == null || StackUtil.isEmpty(additive))) return null;
/*     */     
/*  75 */     for (MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack> recipe : this.recipes) {
/*  76 */       if ((fluid == null || (fluid.isFluidEqual(((ICannerEnrichRecipeManager.Input)recipe.getInput()).fluid) && (acceptTest || ((ICannerEnrichRecipeManager.Input)recipe.getInput()).fluid.amount <= fluid.amount))) && (additive == null || (((ICannerEnrichRecipeManager.Input)recipe
/*  77 */         .getInput()).additive.matches(additive) && (acceptTest || ((ICannerEnrichRecipeManager.Input)recipe.getInput()).additive.getAmount() <= StackUtil.getSize(additive))))) {
/*  78 */         return recipe;
/*     */       }
/*     */     } 
/*     */     
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutputFor(FluidStack fluid, ItemStack additive, boolean adjustInput, boolean acceptTest) {
/*  87 */     MachineRecipeResult<ICannerEnrichRecipeManager.Input, FluidStack, ICannerEnrichRecipeManager.RawInput> result = apply(new ICannerEnrichRecipeManager.RawInput(fluid, additive), acceptTest);
/*  88 */     if (result == null) return null;
/*     */     
/*  90 */     if (adjustInput) {
/*  91 */       fluid.amount = (((ICannerEnrichRecipeManager.RawInput)result.getAdjustedInput()).fluid == null) ? 0 : ((ICannerEnrichRecipeManager.RawInput)result.getAdjustedInput()).fluid.amount;
/*  92 */       additive.func_190920_e(StackUtil.isEmpty(((ICannerEnrichRecipeManager.RawInput)result.getAdjustedInput()).additive) ? 0 : StackUtil.getSize(((ICannerEnrichRecipeManager.RawInput)result.getAdjustedInput()).additive));
/*     */     } 
/*     */     
/*  95 */     NBTTagCompound output = new NBTTagCompound();
/*  96 */     ((FluidStack)result.getOutput()).writeToNBT(output);
/*     */     
/*  98 */     return new RecipeOutput(output, new ItemStack[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<? extends MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack>> getRecipes() {
/* 103 */     return this.recipes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIterable() {
/* 108 */     return true;
/*     */   }
/*     */   
/* 111 */   private final List<MachineRecipe<ICannerEnrichRecipeManager.Input, FluidStack>> recipes = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\CannerEnrichRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */