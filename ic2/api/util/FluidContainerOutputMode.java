/*    */ package ic2.api.util;
/*    */ 
/*    */ public enum FluidContainerOutputMode {
/*  4 */   EmptyFullToOutput(true),
/*  5 */   AnyToOutput(true),
/*  6 */   InPlacePreferred(false),
/*  7 */   InPlace(false);
/*    */   
/*    */   FluidContainerOutputMode(boolean outputEmptyFull) {
/* 10 */     this.outputEmptyFull = outputEmptyFull;
/*    */   }
/*    */   private final boolean outputEmptyFull;
/*    */   public boolean isOutputEmptyFull() {
/* 14 */     return this.outputEmptyFull;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\ap\\util\FluidContainerOutputMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */