/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipeInputItemStack
/*    */   extends RecipeInputBase
/*    */   implements IRecipeInput {
/*    */   public final ItemStack input;
/*    */   public final int amount;
/*    */   
/*    */   RecipeInputItemStack(ItemStack input) {
/* 16 */     this(input, StackUtil.getSize(input));
/*    */   }
/*    */   
/*    */   RecipeInputItemStack(ItemStack input, int amount) {
/* 20 */     if (StackUtil.isEmpty(input)) throw new IllegalArgumentException("invalid input stack");
/*    */     
/* 22 */     this.input = input.func_77946_l();
/* 23 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 28 */     return (subject.func_77973_b() == this.input.func_77973_b() && (subject
/* 29 */       .func_77960_j() == this.input.func_77960_j() || this.input.func_77960_j() == 32767) && (this.input
/* 30 */       .func_77960_j() == 32767 || StackUtil.matchesNBT(subject.func_77978_p(), this.input.func_77978_p())));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 35 */     return this.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 40 */     return Arrays.asList(new ItemStack[] { StackUtil.setImmutableSize(this.input, getAmount()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "RInputItemStack<" + StackUtil.setImmutableSize(this.input, this.amount) + ">";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     RecipeInputItemStack other;
/* 51 */     return (obj != null && getClass() == obj.getClass() && 
/* 52 */       StackUtil.checkItemEqualityStrict((other = (RecipeInputItemStack)obj).input, this.input) && other.amount == this.amount);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */