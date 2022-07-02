/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicMachineRecipeManagerTest
/*     */   implements IBasicMachineRecipeManager
/*     */ {
/*     */   public boolean addRecipe(IRecipeInput input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
/*  23 */     if (replace) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  32 */       this.recipes.add(0, new MachineRecipe(input, output, metadata));
/*  33 */     } else if (getCollidingRecipe(input) == null) {
/*  34 */       this.recipes.add(new MachineRecipe(input, output, metadata));
/*     */     } else {
/*  36 */       return false;
/*     */     } 
/*     */     
/*  39 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean replace, ItemStack... outputs) {
/*  44 */     return addRecipe(input, Arrays.asList(outputs), metadata, replace);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
/*  49 */     MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe = getRecipe(input, true);
/*  50 */     if (recipe == null) return null; 
/*  51 */     if (adjustInput) {
/*  52 */       if (input.func_77973_b().hasContainerItem(input)) {
/*  53 */         throw new UnsupportedOperationException("can't adjust input item, use apply() instead");
/*     */       }
/*     */       
/*  56 */       input.func_190918_g(((IRecipeInput)recipe.getInput()).getAmount());
/*     */     } 
/*     */     
/*  59 */     return new RecipeOutput(recipe.getMetaData(), new ArrayList((Collection)recipe.getOutput()));
/*     */   }
/*     */ 
/*     */   
/*     */   public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> apply(ItemStack input, boolean acceptTest) {
/*  64 */     if (StackUtil.isEmpty(input)) return null;
/*     */     
/*  66 */     MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe = getRecipe(input, true);
/*  67 */     if (recipe == null) return null;
/*     */ 
/*     */     
/*     */     ItemStack adjustedInput;
/*  71 */     if (input.func_77973_b().hasContainerItem(input) && 
/*  72 */       !StackUtil.isEmpty(adjustedInput = input.func_77973_b().getContainerItem(input))) {
/*  73 */       if (StackUtil.getSize(input) != ((IRecipeInput)recipe.getInput()).getAmount()) return null;
/*     */       
/*  75 */       adjustedInput = StackUtil.copy(input);
/*     */     } else {
/*  77 */       adjustedInput = StackUtil.copyWithSize(input, StackUtil.getSize(input) - ((IRecipeInput)recipe.getInput()).getAmount());
/*     */     } 
/*     */     
/*  80 */     return recipe.getResult(adjustedInput);
/*     */   }
/*     */   
/*     */   private MachineRecipe<IRecipeInput, Collection<ItemStack>> getCollidingRecipe(IRecipeInput input) {
/*  84 */     for (ItemStack itemStackIn : input.getInputs()) {
/*  85 */       MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe = getRecipe(itemStackIn, false);
/*  86 */       if (recipe != null) return recipe; 
/*     */     } 
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   private MachineRecipe<IRecipeInput, Collection<ItemStack>> getRecipe(ItemStack stack, boolean checkAmount) {
/*  92 */     for (MachineRecipe<IRecipeInput, Collection<ItemStack>> container : this.recipes) {
/*  93 */       if (((IRecipeInput)container.getInput()).matches(stack)) {
/*  94 */         if (!checkAmount) return container; 
/*  95 */         if (StackUtil.getSize(stack) >= ((IRecipeInput)container.getInput()).getAmount() && (
/*  96 */           !stack.func_77973_b().hasContainerItem(stack) || StackUtil.getSize(stack) == ((IRecipeInput)container.getInput()).getAmount()))
/*  97 */           return container; 
/*     */       } 
/*     */     } 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<? extends MachineRecipe<IRecipeInput, Collection<ItemStack>>> getRecipes() {
/* 105 */     return this.recipes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIterable() {
/* 110 */     return true;
/*     */   }
/*     */   
/* 113 */   private final List<MachineRecipe<IRecipeInput, Collection<ItemStack>>> recipes = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\BasicMachineRecipeManagerTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */