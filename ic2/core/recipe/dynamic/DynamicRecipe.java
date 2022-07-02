/*     */ package ic2.core.recipe.dynamic;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicRecipe
/*     */ {
/*     */   private final DynamicRecipeManager manager;
/*     */   private List<RecipeInputIngredient> inputIngredients;
/*     */   private List<RecipeOutputIngredient> outputIngredients;
/*     */   private int operationEnergyCost;
/*     */   private int operationDuration;
/*     */   private NBTTagCompound metadata;
/*     */   
/*     */   public DynamicRecipe(DynamicRecipeManager manager) {
/* 197 */     this.inputIngredients = new ArrayList<>();
/* 198 */     this.outputIngredients = new ArrayList<>();
/*     */     this.manager = manager;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withInput(List<RecipeInputIngredient> inputs) {
/*     */     this.inputIngredients.addAll(inputs);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withInput(ItemStack stack) {
/*     */     if (StackUtil.isEmpty(stack))
/*     */       throw new IllegalArgumentException("Input cannot be empty"); 
/*     */     this.inputIngredients.add(RecipeInputItemStack.of(stack));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withInput(String oreDict) {
/*     */     this.inputIngredients.add(RecipeInputOreDictionary.of(oreDict));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withInput(String oreDict, int amount) {
/*     */     if (amount <= 0)
/*     */       throw new IllegalArgumentException("Input cannot be empty"); 
/*     */     this.inputIngredients.add(RecipeInputOreDictionary.of(oreDict, amount));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withInput(String oreDict, int amount, Integer meta) {
/*     */     if (amount <= 0)
/*     */       throw new IllegalArgumentException("Input cannot be empty"); 
/*     */     this.inputIngredients.add(RecipeInputOreDictionary.of(oreDict, amount, meta));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withInput(FluidStack stack) {
/*     */     if (stack.amount <= 0)
/*     */       throw new IllegalArgumentException("Input cannot be empty"); 
/*     */     this.inputIngredients.add(RecipeInputFluidStack.of(stack));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withOutput(List<RecipeOutputIngredient> outputs) {
/*     */     this.outputIngredients.addAll(outputs);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withOutput(ItemStack stack) {
/*     */     if (StackUtil.isEmpty(stack))
/*     */       throw new IllegalArgumentException("Output cannot be empty"); 
/*     */     this.outputIngredients.add(RecipeOutputItemStack.of(stack));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withOutput(FluidStack stack) {
/*     */     if (stack.amount <= 0)
/*     */       throw new IllegalArgumentException("Output cannot be empty"); 
/*     */     this.outputIngredients.add(RecipeOutputFluidStack.of(stack));
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withOperationEnergyCost(int operationEnergyCost) {
/*     */     this.operationEnergyCost = operationEnergyCost;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withOperationDurationTicks(int operationDuration) {
/*     */     this.operationDuration = operationDuration;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withOperationDurationSeconds(int operationDuration) {
/*     */     this.operationDuration = operationDuration * 20;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(NBTTagCompound metadata) {
/*     */     this.metadata = metadata;
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(String key, int value) {
/*     */     if (this.metadata == null)
/*     */       this.metadata = new NBTTagCompound(); 
/*     */     this.metadata.func_74768_a(key, value);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(String key, short value) {
/*     */     if (this.metadata == null)
/*     */       this.metadata = new NBTTagCompound(); 
/*     */     this.metadata.func_74777_a(key, value);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(String key, byte value) {
/*     */     if (this.metadata == null)
/*     */       this.metadata = new NBTTagCompound(); 
/*     */     this.metadata.func_74774_a(key, value);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(String key, float value) {
/*     */     if (this.metadata == null)
/*     */       this.metadata = new NBTTagCompound(); 
/*     */     this.metadata.func_74776_a(key, value);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(String key, double value) {
/*     */     if (this.metadata == null)
/*     */       this.metadata = new NBTTagCompound(); 
/*     */     this.metadata.func_74780_a(key, value);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public DynamicRecipe withMetadata(String key, boolean value) {
/*     */     if (this.metadata == null)
/*     */       this.metadata = new NBTTagCompound(); 
/*     */     this.metadata.func_74757_a(key, value);
/*     */     return this;
/*     */   }
/*     */   
/*     */   public void register() {
/*     */     register(false);
/*     */   }
/*     */   
/*     */   public void register(boolean replace) {
/*     */     boolean success = false;
/*     */     success = this.manager.addRecipe(this, replace);
/*     */     if (!success)
/*     */       this.manager.displayError("Registration failed for recipe " + this); 
/*     */   }
/*     */   
/*     */   public List<RecipeInputIngredient> getInputIngredients() {
/*     */     return this.inputIngredients;
/*     */   }
/*     */   
/*     */   public List<RecipeOutputIngredient> getOutputIngredients() {
/*     */     return this.outputIngredients;
/*     */   }
/*     */   
/*     */   public int getOperationEnergyCost() {
/*     */     return this.operationEnergyCost;
/*     */   }
/*     */   
/*     */   public int getOperationDuration() {
/*     */     return this.operationDuration;
/*     */   }
/*     */   
/*     */   public NBTTagCompound getMetadata() {
/*     */     return this.metadata;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\DynamicRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */