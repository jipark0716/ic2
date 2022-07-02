/*    */ package ic2.api.recipe;public class MachineRecipeResult<RI, RO, I> {
/*    */   private final MachineRecipe<RI, RO> recipe;
/*    */   
/*    */   public MachineRecipeResult(MachineRecipe<RI, RO> recipe, I adjustedInput) {
/*  5 */     this.recipe = recipe;
/*  6 */     this.adjustedInput = adjustedInput;
/*    */   }
/*    */   private final I adjustedInput;
/*    */   public MachineRecipe<RI, RO> getRecipe() {
/* 10 */     return this.recipe;
/*    */   }
/*    */   
/*    */   public RO getOutput() {
/* 14 */     return this.recipe.getOutput();
/*    */   }
/*    */   
/*    */   public I getAdjustedInput() {
/* 18 */     return this.adjustedInput;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\MachineRecipeResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */