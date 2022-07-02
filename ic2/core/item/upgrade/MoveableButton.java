/*    */ package ic2.core.item.upgrade;
/*    */ import ic2.core.gui.IClickHandler;
/*    */ import ic2.core.gui.IEnableHandler;
/*    */ 
/*    */ class MoveableButton extends VanillaButton {
/*    */   private IEnableHandler moveHandler;
/*    */   protected int normalX;
/*    */   
/*    */   public MoveableButton(GuiIC2<?> gui, int normalX, int normalY, int shiftedX, int shiftedY, int width, int height, IClickHandler handler) {
/* 10 */     super(gui, normalX, normalY, width, height, handler);
/*    */     
/* 12 */     this.normalX = normalX;
/* 13 */     this.normalY = normalY;
/* 14 */     this.shiftedX = shiftedX;
/* 15 */     this.shiftedY = shiftedY;
/*    */   }
/*    */   protected int normalY; protected int shiftedX; protected int shiftedY;
/*    */   public MoveableButton withMoveHandler(IEnableHandler moveHandler) {
/* 19 */     this.moveHandler = moveHandler;
/*    */     
/* 21 */     return this;
/*    */   }
/*    */   
/*    */   public boolean isMoved() {
/* 25 */     return (this.moveHandler != null && this.moveHandler.isEnabled());
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 30 */     super.tick();
/*    */     
/* 32 */     if (isMoved()) {
/* 33 */       this.x = this.shiftedX;
/* 34 */       this.y = this.shiftedY;
/*    */     } else {
/* 36 */       this.x = this.normalX;
/* 37 */       this.y = this.normalY;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\MoveableButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */