/*    */ package ic2.api.recipe;
/*    */ 
/*    */ public class MachineRecipe<I, O> {
/*    */   private final I input;
/*    */   
/*    */   public MachineRecipe(I input, O output) {
/*  7 */     this(input, output, null);
/*    */   }
/*    */   private final O output; private final NBTTagCompound meta;
/*    */   public MachineRecipe(I input, O output, NBTTagCompound meta) {
/* 11 */     this.input = input;
/* 12 */     this.output = output;
/* 13 */     this.meta = meta;
/*    */   }
/*    */   
/*    */   public I getInput() {
/* 17 */     return this.input;
/*    */   }
/*    */   
/*    */   public O getOutput() {
/* 21 */     return this.output;
/*    */   }
/*    */   
/*    */   public NBTTagCompound getMetaData() {
/* 25 */     return this.meta;
/*    */   }
/*    */   
/*    */   public <AI> MachineRecipeResult<I, O, AI> getResult(AI adjustedInput) {
/* 29 */     return new MachineRecipeResult<>(this, adjustedInput);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\MachineRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */