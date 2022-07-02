/*     */ package ic2.core.block.machine;
/*     */ 
/*     */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipe;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.RecipeOutput;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CannerBottleRecipeManager
/*     */   implements ICannerBottleRecipeManager
/*     */ {
/*     */   public boolean addRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output, boolean replace) {
/*  24 */     return addRecipe(new ICannerBottleRecipeManager.Input(container, fill), output, (NBTTagCompound)null, replace);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void addRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output) {
/*  30 */     if (!addRecipe(container, fill, output, false)) {
/*  31 */       throw new IllegalStateException("ambiguous canner bottle recipe: " + container + " + " + fill + " -> " + output);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addRecipe(ICannerBottleRecipeManager.Input input, ItemStack output, NBTTagCompound metadata, boolean replace) {
/*  37 */     for (Iterator<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> it = this.recipes.iterator(); it.hasNext(); ) {
/*  38 */       MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe = it.next();
/*     */       
/*  40 */       for (ItemStack containerStack : input.container.getInputs()) {
/*  41 */         for (ItemStack fillStack : input.fill.getInputs()) {
/*  42 */           if (((ICannerBottleRecipeManager.Input)recipe.getInput()).matches(containerStack, fillStack)) {
/*  43 */             if (replace) {
/*  44 */               it.remove(); continue;
/*     */             } 
/*  46 */             IC2.log.warn(LogCategory.Recipe, "ambiguous recipe: [" + input.container.getInputs() + "+" + input.fill.getInputs() + " -> " + output + "], conflicts with [" + ((ICannerBottleRecipeManager.Input)recipe
/*  47 */                 .getInput()).container.getInputs() + "+" + ((ICannerBottleRecipeManager.Input)recipe.getInput()).fill.getInputs() + " -> " + recipe.getOutput() + "]");
/*  48 */             return false;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     this.recipes.add(new MachineRecipe(input, output));
/*     */     
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeOutput getOutputFor(ItemStack container, ItemStack fill, boolean adjustInput, boolean acceptTest) {
/*  64 */     if (acceptTest)
/*  65 */     { if (StackUtil.isEmpty(container) && StackUtil.isEmpty(fill)) return null;
/*     */        }
/*  67 */     else if (StackUtil.isEmpty(container) || StackUtil.isEmpty(fill)) { return null; }
/*     */ 
/*     */     
/*  70 */     for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe : this.recipes) {
/*  71 */       ICannerBottleRecipeManager.Input recipeInput = (ICannerBottleRecipeManager.Input)recipe.getInput();
/*     */       
/*  73 */       if (acceptTest && StackUtil.isEmpty(container)) {
/*  74 */         if (recipeInput.fill.matches(fill))
/*  75 */           return new RecipeOutput(null, new ItemStack[] { (ItemStack)recipe.getOutput() });  continue;
/*     */       } 
/*  77 */       if (acceptTest && StackUtil.isEmpty(fill)) {
/*  78 */         if (recipeInput.container.matches(container))
/*  79 */           return new RecipeOutput(null, new ItemStack[] { (ItemStack)recipe.getOutput() });  continue;
/*     */       } 
/*  81 */       if (recipeInput.matches(container, fill)) {
/*  82 */         if (acceptTest || (!StackUtil.isEmpty(container) && StackUtil.getSize(container) >= recipeInput.container.getAmount() && StackUtil.getSize(fill) >= recipeInput.fill.getAmount())) {
/*  83 */           if (adjustInput) {
/*  84 */             if (!StackUtil.isEmpty(container)) container.func_190918_g(recipeInput.container.getAmount()); 
/*  85 */             fill.func_190918_g(recipeInput.fill.getAmount());
/*     */           } 
/*     */           
/*  88 */           new RecipeOutput(null, new ItemStack[] { (ItemStack)recipe.getOutput() });
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public MachineRecipeResult<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> apply(ICannerBottleRecipeManager.RawInput input, boolean acceptTest) {
/* 100 */     boolean emptyContainer = StackUtil.isEmpty(input.container);
/* 101 */     boolean emptyFill = StackUtil.isEmpty(input.fill);
/*     */     
/* 103 */     if (!acceptTest && (emptyContainer || emptyFill)) return null; 
/* 104 */     if (acceptTest && emptyContainer && emptyFill) return null;
/*     */     
/* 106 */     for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe : this.recipes) {
/* 107 */       if ((emptyContainer || (((ICannerBottleRecipeManager.Input)recipe.getInput()).container.matches(input.container) && ((ICannerBottleRecipeManager.Input)recipe.getInput()).container.getAmount() <= StackUtil.getSize(input.container))) && (emptyFill || (((ICannerBottleRecipeManager.Input)recipe
/* 108 */         .getInput()).fill.matches(input.fill) && ((ICannerBottleRecipeManager.Input)recipe.getInput()).fill.getAmount() <= StackUtil.getSize(input.fill)))) {
/* 109 */         return recipe.getResult(new ICannerBottleRecipeManager.RawInput(emptyContainer ? StackUtil.emptyStack : StackUtil.copyShrunk(input.container, ((ICannerBottleRecipeManager.Input)recipe.getInput()).container.getAmount()), emptyFill ? StackUtil.emptyStack : 
/* 110 */               StackUtil.copyShrunk(input.fill, ((ICannerBottleRecipeManager.Input)recipe.getInput()).fill.getAmount())));
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<? extends MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> getRecipes() {
/* 119 */     return this.recipes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIterable() {
/* 124 */     return true;
/*     */   }
/*     */   
/* 127 */   private final List<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> recipes = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\CannerBottleRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */