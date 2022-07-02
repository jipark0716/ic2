/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicMachineRecipeManager
/*     */   extends MachineRecipeHelper<IRecipeInput, Collection<ItemStack>>
/*     */   implements IBasicMachineRecipeManager
/*     */ {
/*     */   protected IRecipeInput getForInput(IRecipeInput input) {
/*  26 */     return input;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean consumeContainer(ItemStack input, ItemStack inContainer, MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe) {
/*  31 */     for (ItemStack output : recipe.getOutput()) {
/*  32 */       if (StackUtil.checkItemEqualityStrict(inContainer, output)) {
/*  33 */         return true;
/*     */       }
/*     */ 
/*     */       
/*  37 */       if (output.func_77973_b().hasContainerItem(output) && StackUtil.checkItemEqualityStrict(input, output.func_77973_b().getContainerItem(output))) {
/*  38 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean replace, ItemStack... outputs) {
/*  47 */     return addRecipe(input, Arrays.asList(outputs), metadata, replace);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addRecipe(IRecipeInput input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
/*  52 */     if (input == null) throw new NullPointerException("null recipe input"); 
/*  53 */     if (output == null) throw new NullPointerException("null recipe output"); 
/*  54 */     if (output.isEmpty()) throw new IllegalArgumentException("no outputs");
/*     */     
/*  56 */     List<ItemStack> items = new ArrayList<>(output.size());
/*     */     
/*  58 */     for (ItemStack stack : output) {
/*  59 */       if (StackUtil.isEmpty(stack)) {
/*  60 */         displayError("The output ItemStack " + StackUtil.toStringSafe(stack) + " is invalid.");
/*  61 */         return false;
/*     */       } 
/*     */       
/*  64 */       if (input.matches(stack))
/*     */       {
/*  66 */         if (metadata == null || !metadata.func_74764_b("ignoreSameInputOutput")) {
/*  67 */           displayError("The output ItemStack " + stack.toString() + " is the same as the recipe input " + input + ".");
/*  68 */           return false;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*  73 */       items.add(stack.func_77946_l());
/*     */     } 
/*     */     
/*  76 */     label31: for (ItemStack is : input.getInputs()) {
/*  77 */       MachineRecipe<IRecipeInput, Collection<ItemStack>> machineRecipe = getRecipe(is);
/*     */       
/*  79 */       if (machineRecipe != null) {
/*  80 */         if (replace)
/*     */           while (true) {
/*  82 */             this.recipes.remove(input);
/*  83 */             removeCachedRecipes(input);
/*  84 */             machineRecipe = getRecipe(is);
/*  85 */             if (machineRecipe == null)
/*     */               continue label31; 
/*  87 */           }   IC2.log.debug(LogCategory.Recipe, "Skipping %s => %s due to duplicate recipe for %s (%s => %s)", new Object[] { input, output, is, machineRecipe.getInput(), machineRecipe.getOutput() });
/*  88 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  93 */     MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe = new MachineRecipe(input, items, metadata);
/*     */     
/*  95 */     this.recipes.put(input, recipe);
/*  96 */     addToCache(recipe);
/*     */     
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
/* 103 */     MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe = getRecipe(input);
/* 104 */     if (recipe == null) return null;
/*     */     
/* 106 */     if (StackUtil.getSize(input) >= ((IRecipeInput)recipe.getInput()).getAmount() && (
/* 107 */       !input.func_77973_b().hasContainerItem(input) || StackUtil.getSize(input) == ((IRecipeInput)recipe.getInput()).getAmount())) {
/* 108 */       if (adjustInput) {
/* 109 */         if (input.func_77973_b().hasContainerItem(input)) {
/* 110 */           throw new UnsupportedOperationException("can't adjust input item, use apply() instead");
/*     */         }
/* 112 */         input.func_190918_g(((IRecipeInput)recipe.getInput()).getAmount());
/*     */       } 
/*     */ 
/*     */       
/* 116 */       return new RecipeOutput(recipe.getMetaData(), new ArrayList((Collection)recipe.getOutput()));
/*     */     } 
/*     */     
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeRecipe(ItemStack input, Collection<ItemStack> output) {
/* 129 */     MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe = getRecipe(input);
/*     */     
/* 131 */     if (recipe != null && checkListEquality((Collection<ItemStack>)recipe.getOutput(), output)) {
/* 132 */       this.recipes.remove(recipe.getInput());
/* 133 */       removeCachedRecipes((IRecipeInput)recipe.getInput());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean checkListEquality(Collection<ItemStack> a, Collection<ItemStack> b) {
/* 138 */     if (a.size() != b.size()) return false;
/*     */     
/* 140 */     ListIterator<ItemStack> itB = (new ArrayList<>(b)).listIterator();
/* 141 */     for (ItemStack stack : a) {
/* 142 */       while (itB.hasNext()) {
/* 143 */         if (StackUtil.checkItemEqualityStrict(stack, itB.next())) {
/* 144 */           itB.remove();
/*     */ 
/*     */           
/* 147 */           for (; itB.hasPrevious(); itB.previous());
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 153 */       return false;
/*     */     } 
/*     */     
/* 156 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayError(String msg) {
/* 161 */     if (MainConfig.ignoreInvalidRecipes) {
/* 162 */       IC2.log.warn(LogCategory.Recipe, msg);
/*     */     } else {
/* 164 */       throw new RuntimeException(msg);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\BasicMachineRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */