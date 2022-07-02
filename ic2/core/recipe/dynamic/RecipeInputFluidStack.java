/*    */ package ic2.core.recipe.dynamic;
/*    */ 
/*    */ import ic2.core.util.LiquidUtil;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class RecipeInputFluidStack extends RecipeInputIngredient<FluidStack> {
/*    */   public static RecipeInputFluidStack of(FluidStack ingredient) {
/*  8 */     return new RecipeInputFluidStack(ingredient);
/*    */   }
/*    */   
/*    */   protected RecipeInputFluidStack(FluidStack ingredient) {
/* 12 */     super(ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getUnspecific() {
/* 17 */     return this.ingredient.getFluid();
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeInputIngredient<FluidStack> copy() {
/* 22 */     return of(this.ingredient.copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 27 */     return (this.ingredient.amount <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 32 */     return this.ingredient.amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public void shrink(int amount) {
/* 37 */     this.ingredient.amount -= amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Object other) {
/* 42 */     if (!(other instanceof FluidStack)) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     return this.ingredient.isFluidStackIdentical((FluidStack)other);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesStrict(Object other) {
/* 51 */     return matches(other);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toStringSafe() {
/* 56 */     return LiquidUtil.toStringSafe(this.ingredient);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeInputFluidStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */