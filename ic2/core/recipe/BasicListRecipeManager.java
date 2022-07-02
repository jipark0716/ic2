/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IListRecipeManager;
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.api.recipe.MachineRecipe;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.LogCategory;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ public class BasicListRecipeManager
/*    */   extends MachineRecipeHelper<IRecipeInput, Object>
/*    */   implements IListRecipeManager
/*    */ {
/*    */   public void add(IRecipeInput input) {
/* 21 */     if (input == null) throw new NullPointerException("Input must not be null.");
/*    */     
/* 23 */     addRecipe(input, dummyOutput, (NBTTagCompound)null, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(ItemStack stack) {
/* 28 */     if (StackUtil.isEmpty(stack)) return false;
/*    */     
/* 30 */     return (getRecipe(stack) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 35 */     return this.recipes.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<IRecipeInput> getInputs() {
/* 40 */     return new ArrayList<>(this.recipes.keySet());
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<IRecipeInput> iterator() {
/* 45 */     return this.recipes.keySet().iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean addRecipe(IRecipeInput input, Object output, NBTTagCompound metadata, boolean replace) {
/* 50 */     label14: for (ItemStack is : input.getInputs()) {
/* 51 */       MachineRecipe<IRecipeInput, Object> machineRecipe = getRecipe(is);
/*    */       
/* 53 */       if (machineRecipe != null) {
/* 54 */         if (replace)
/*    */           while (true) {
/* 56 */             this.recipes.remove(input);
/* 57 */             removeCachedRecipes(input);
/* 58 */             machineRecipe = getRecipe(is);
/* 59 */             if (machineRecipe == null)
/*    */               continue label14; 
/* 61 */           }   IC2.log.debug(LogCategory.Recipe, "Skipping %s due to duplicate recipe for %s (%s)", new Object[] { input, is, machineRecipe.getInput() });
/* 62 */         return false;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 67 */     MachineRecipe<IRecipeInput, Object> recipe = new MachineRecipe(input, output, metadata);
/*    */     
/* 69 */     this.recipes.put(input, recipe);
/* 70 */     addToCache(recipe);
/*    */     
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IRecipeInput getForInput(IRecipeInput input) {
/* 77 */     return input;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean consumeContainer(ItemStack input, ItemStack container, MachineRecipe<IRecipeInput, Object> recipe) {
/* 82 */     return true;
/*    */   }
/*    */   
/* 85 */   private static final Object dummyOutput = new Object();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\BasicListRecipeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */