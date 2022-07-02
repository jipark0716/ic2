/*    */ package ic2.jeiIntegration.recipe.machine;
/*    */ 
/*    */ import ic2.core.block.ITeBlock;
/*    */ import ic2.core.block.TeBlockRegistry;
/*    */ import ic2.jeiIntegration.SlotPosition;
/*    */ import java.util.List;
/*    */ import mezz.jei.api.gui.IDrawable;
/*    */ import mezz.jei.api.gui.IGuiItemStackGroup;
/*    */ import mezz.jei.api.gui.IRecipeLayout;
/*    */ import mezz.jei.api.ingredients.IIngredients;
/*    */ import mezz.jei.api.recipe.IRecipeCategory;
/*    */ import mezz.jei.api.recipe.IRecipeWrapper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IORecipeCategory<T>
/*    */   implements IRecipeCategory<IRecipeWrapper>
/*    */ {
/*    */   protected final ITeBlock block;
/*    */   final T recipeManager;
/*    */   
/*    */   public IORecipeCategory(ITeBlock block, T recipeManager) {
/* 25 */     this.block = block;
/* 26 */     this.recipeManager = recipeManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUid() {
/* 31 */     return this.block.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTitle() {
/* 36 */     return getBlockStack().func_82833_r();
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawExtras(Minecraft minecraft) {}
/*    */   
/*    */   protected abstract List<SlotPosition> getInputSlotPos();
/*    */   
/*    */   protected abstract List<SlotPosition> getOutputSlotPos();
/*    */   
/*    */   protected List<List<ItemStack>> getInputStacks(IIngredients ingredients) {
/* 47 */     return ingredients.getInputs(ItemStack.class);
/*    */   }
/*    */   
/*    */   protected List<List<ItemStack>> getOutputStacks(IIngredients ingredients) {
/* 51 */     return ingredients.getOutputs(ItemStack.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
/* 56 */     IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
/* 57 */     List<SlotPosition> inputSlots = getInputSlotPos();
/* 58 */     List<List<ItemStack>> inputStacks = getInputStacks(ingredients);
/* 59 */     int idx = 0;
/* 60 */     for (; idx < inputSlots.size(); idx++) {
/* 61 */       SlotPosition pos = inputSlots.get(idx);
/* 62 */       itemStacks.init(idx, true, pos.getX(), pos.getY());
/* 63 */       if (idx < inputStacks.size())
/* 64 */         itemStacks.set(idx, inputStacks.get(idx)); 
/*    */     } 
/* 66 */     List<SlotPosition> outputSlots = getOutputSlotPos();
/* 67 */     List<List<ItemStack>> outputStacks = getOutputStacks(ingredients);
/* 68 */     for (int i = 0; i < outputSlots.size(); i++, idx++) {
/* 69 */       SlotPosition pos = outputSlots.get(i);
/* 70 */       itemStacks.init(idx, false, pos.getX(), pos.getY());
/* 71 */       if (i < outputStacks.size())
/* 72 */         itemStacks.set(idx, outputStacks.get(i)); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public ItemStack getBlockStack() {
/* 77 */     return TeBlockRegistry.get(this.block.getIdentifier()).getItemStack(this.block);
/*    */   }
/*    */ 
/*    */   
/*    */   public IDrawable getIcon() {
/* 82 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModName() {
/* 87 */     return "ic2";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\recipe\machine\IORecipeCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */