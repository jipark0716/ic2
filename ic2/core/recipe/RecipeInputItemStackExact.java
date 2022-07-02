/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class RecipeInputItemStackExact
/*    */   extends RecipeInputBase {
/*    */   public final ItemStack input;
/*    */   public final int amount;
/*    */   
/*    */   RecipeInputItemStackExact(ItemStack input) {
/* 14 */     this(input, StackUtil.getSize(input));
/*    */   }
/*    */   
/*    */   RecipeInputItemStackExact(ItemStack input, int amount) {
/* 18 */     if (StackUtil.isEmpty(input) || input.func_77960_j() == 32767) {
/* 19 */       throw new IllegalArgumentException("invalid input stack");
/*    */     }
/* 21 */     this.input = StackUtil.copy(input);
/* 22 */     this.amount = amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 27 */     return (subject.func_77973_b() == this.input.func_77973_b() && subject
/* 28 */       .func_77960_j() == this.input.func_77960_j() && 
/* 29 */       StackUtil.checkNbtEqualityStrict(subject, this.input));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 34 */     return this.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 39 */     return Collections.singletonList(StackUtil.setImmutableSize(this.input, getAmount()));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return "RInputItemStackExact<" + StackUtil.setImmutableSize(this.input, this.amount) + '>';
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/*    */     RecipeInputItemStackExact other;
/* 50 */     return (obj != null && getClass() == obj.getClass() && 
/* 51 */       StackUtil.checkItemEqualityStrict((other = (RecipeInputItemStackExact)obj).input, this.input) && other.amount == this.amount);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputItemStackExact.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */