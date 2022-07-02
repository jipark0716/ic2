/*    */ package ic2.core.gui;public class OverlaySupplier implements IOverlaySupplier { private final int uS;
/*    */   private final int vS;
/*    */   
/*    */   public OverlaySupplier(int uS, int vS, int uE, int vE) {
/*  5 */     this.uS = uS;
/*  6 */     this.vS = vS;
/*  7 */     this.uE = uE;
/*  8 */     this.vE = vE;
/*    */   }
/*    */   private final int uE; private final int vE;
/*    */   
/*    */   public int getUS() {
/* 13 */     return this.uS;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVS() {
/* 18 */     return this.vS;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUE() {
/* 23 */     return this.uE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVE() {
/* 28 */     return this.vE;
/*    */   } }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\OverlaySupplier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */