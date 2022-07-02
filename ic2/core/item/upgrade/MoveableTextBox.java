/*    */ package ic2.core.item.upgrade;
/*    */ import ic2.core.gui.IEnableHandler;
/*    */ 
/*    */ class MoveableTextBox extends TextBox {
/*    */   private IEnableHandler moveHandler;
/*    */   protected int normalX;
/*    */   
/*    */   public MoveableTextBox(GuiIC2<?> gui, int normalX, int normalY, int shiftedX, int shiftedY, int width, int height, String text) {
/*  9 */     super(gui, normalX, normalY, width, height, text);
/*    */     
/* 11 */     this.normalX = normalX;
/* 12 */     this.normalY = normalY;
/* 13 */     this.shiftedX = shiftedX;
/* 14 */     this.shiftedY = shiftedY;
/*    */   }
/*    */   protected int normalY; protected int shiftedX; protected int shiftedY;
/*    */   public MoveableTextBox withMoveHandler(IEnableHandler moveHandler) {
/* 18 */     this.moveHandler = moveHandler;
/*    */     
/* 20 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isMoved() {
/* 24 */     return (this.moveHandler != null && this.moveHandler.isEnabled());
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     super.tick();
/*    */     
/* 31 */     if (isMoved()) {
/* 32 */       this.x = this.shiftedX;
/* 33 */       this.y = this.shiftedY;
/*    */     } else {
/* 35 */       this.x = this.normalX;
/* 36 */       this.y = this.normalY;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\MoveableTextBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */