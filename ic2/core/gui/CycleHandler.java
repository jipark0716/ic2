/*    */ package ic2.core.gui;public class CycleHandler implements IClickHandler, IOverlaySupplier { private final int uS; private final int vS; private final int uE;
/*    */   private final int vE;
/*    */   
/*    */   public CycleHandler(int uS, int vS, int uE, int vE, int overlayStep, boolean vertical, int options, INumericValueHandler handler) {
/*  5 */     this.uS = uS;
/*  6 */     this.vS = vS;
/*  7 */     this.uE = uE;
/*  8 */     this.vE = vE;
/*  9 */     this.overlayStep = overlayStep;
/* 10 */     this.vertical = vertical;
/* 11 */     this.options = options;
/* 12 */     this.handler = handler;
/*    */   }
/*    */   private final int overlayStep; private final boolean vertical; private final int options; private final INumericValueHandler handler;
/*    */   
/*    */   public void onClick(MouseButton button) {
/* 17 */     int value = getValue();
/*    */     
/* 19 */     if (button == MouseButton.left) {
/* 20 */       value = (value + 1) % this.options;
/* 21 */     } else if (button == MouseButton.right) {
/* 22 */       value = (value + this.options - 1) % this.options;
/*    */     } else {
/*    */       return;
/*    */     } 
/*    */     
/* 27 */     this.handler.onChange(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUS() {
/* 32 */     if (this.vertical) {
/* 33 */       return this.uS;
/*    */     }
/* 35 */     return this.uS + this.overlayStep * getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getVS() {
/* 41 */     if (!this.vertical) {
/* 42 */       return this.vS;
/*    */     }
/* 44 */     return this.vS + this.overlayStep * getValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getUE() {
/* 50 */     if (this.vertical) {
/* 51 */       return this.uE;
/*    */     }
/* 53 */     return this.uS + this.overlayStep * (getValue() + 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getVE() {
/* 59 */     if (!this.vertical) {
/* 60 */       return this.vE;
/*    */     }
/* 62 */     return this.vS + this.overlayStep * (getValue() + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getValue() {
/* 67 */     int ret = this.handler.getValue();
/* 68 */     if (ret < 0 || ret >= this.options) throw new RuntimeException("invalid value: " + ret);
/*    */     
/* 70 */     return ret;
/*    */   } }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\CycleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */