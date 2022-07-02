/*    */ package ic2.core.recipe;
/*    */ 
/*    */ import ic2.api.recipe.IRecipeInput;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeInputMultiple
/*    */   extends RecipeInputBase
/*    */   implements IRecipeInput
/*    */ {
/*    */   private final IRecipeInput[] inputs;
/*    */   
/*    */   RecipeInputMultiple(IRecipeInput... inputs) {
/* 22 */     this.inputs = inputs;
/*    */   }
/*    */   
/*    */   RecipeInputMultiple(List<IRecipeInput> inputs) {
/* 26 */     this.inputs = inputs.<IRecipeInput>toArray(new IRecipeInput[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(ItemStack subject) {
/* 31 */     for (IRecipeInput input : this.inputs) {
/* 32 */       if (input.matches(subject)) return true; 
/*    */     } 
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 39 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getInputs() {
/* 44 */     List<ItemStack> list = new ArrayList<>();
/*    */     
/* 46 */     for (IRecipeInput input : this.inputs) {
/* 47 */       list.addAll(input.getInputs());
/*    */     }
/*    */     
/* 50 */     return Collections.unmodifiableList(list);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     if (this.inputs.length <= 0) return "RecipeInputMultiple<Nothing>"; 
/* 56 */     StringBuilder b = new StringBuilder("RecipeInputMultiple<");
/*    */     
/* 58 */     for (int i = 0, end = this.inputs.length - 1;; i++) {
/* 59 */       b.append(this.inputs[i].toString());
/*    */       
/* 61 */       if (i == end) return b.append('>').toString();
/*    */       
/* 63 */       b.append(", ");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 69 */     if (obj != null && getClass() == obj.getClass()) {
/* 70 */       IRecipeInput[] otherInputs = ((RecipeInputMultiple)obj).inputs;
/*    */       
/* 72 */       if (this.inputs.length == otherInputs.length) {
/* 73 */         for (int i = 0; i < this.inputs.length; i++) {
/* 74 */           if (!this.inputs[i].equals(otherInputs[i])) {
/* 75 */             return false;
/*    */           }
/*    */         } 
/*    */         
/* 79 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\RecipeInputMultiple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */