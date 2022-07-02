/*    */ package ic2.core.recipe.dynamic;
/*    */ 
/*    */ import ic2.core.util.LiquidUtil;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ 
/*    */ public class RecipeOutputFluidStack extends RecipeOutputIngredient<FluidStack> {
/*    */   public static RecipeOutputFluidStack of(FluidStack ingredient) {
/*  8 */     return new RecipeOutputFluidStack(ingredient);
/*    */   }
/*    */   
/*    */   protected RecipeOutputFluidStack(FluidStack ingredient) {
/* 12 */     super(ingredient);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeOutputIngredient<FluidStack> copy() {
/* 17 */     return of(this.ingredient.copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 22 */     return (this.ingredient.amount <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Object other) {
/* 27 */     if (!(other instanceof FluidStack)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     return this.ingredient.isFluidStackIdentical((FluidStack)other);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesStrict(Object other) {
/* 36 */     return matches(other);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toStringSafe() {
/* 41 */     return LiquidUtil.toStringSafe(this.ingredient);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\dynamic\RecipeOutputFluidStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */