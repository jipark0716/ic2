/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.recipe.IMachineRecipeManager;
/*    */ import ic2.api.recipe.MachineRecipeResult;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class InvSlotProcessable<RI, RO, I>
/*    */   extends InvSlotConsumable {
/*    */   protected IMachineRecipeManager<RI, RO, I> recipeManager;
/*    */   
/*    */   public InvSlotProcessable(IInventorySlotHolder<?> base, String name, int count, IMachineRecipeManager<RI, RO, I> recipeManager) {
/* 14 */     super(base, name, count);
/*    */     
/* 16 */     this.recipeManager = recipeManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean accepts(ItemStack stack) {
/* 21 */     if (stack.func_77973_b() instanceof ic2.core.item.upgrade.ItemUpgradeModule) return false;
/*    */     
/* 23 */     ItemStack tmp = StackUtil.copyWithSize(stack, 2147483647);
/*    */     
/* 25 */     return (getOutputFor(getInput(tmp), true) != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MachineRecipeResult<RI, RO, I> process() {
/* 34 */     ItemStack input = get();
/* 35 */     if (StackUtil.isEmpty(input) && !allowEmptyInput()) return null;
/*    */ 
/*    */     
/* 38 */     return getOutputFor(getInput(input), false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void consume(MachineRecipeResult<RI, RO, I> result) {
/* 47 */     if (result == null) throw new NullPointerException("null result");
/*    */     
/* 49 */     ItemStack input = get();
/* 50 */     if (StackUtil.isEmpty(input) && !allowEmptyInput()) throw new IllegalStateException("consume from empty slot");
/*    */ 
/*    */     
/* 53 */     setInput((I)result.getAdjustedInput());
/*    */   }
/*    */   
/*    */   public void setRecipeManager(IMachineRecipeManager<RI, RO, I> recipeManager) {
/* 57 */     this.recipeManager = recipeManager;
/*    */   }
/*    */   
/*    */   protected boolean allowEmptyInput() {
/* 61 */     return false;
/*    */   }
/*    */   
/*    */   protected MachineRecipeResult<RI, RO, I> getOutputFor(I input, boolean forAccept) {
/* 65 */     return this.recipeManager.apply(input, forAccept);
/*    */   }
/*    */   
/*    */   protected abstract I getInput(ItemStack paramItemStack);
/*    */   
/*    */   protected abstract void setInput(I paramI);
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotProcessable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */