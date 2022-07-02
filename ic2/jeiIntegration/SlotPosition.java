/*    */ package ic2.jeiIntegration;
/*    */ 
/*    */ import ic2.core.gui.SlotGrid;
/*    */ 
/*    */ public class SlotPosition {
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final SlotGrid.SlotStyle style;
/*    */   
/*    */   public SlotPosition(int x, int y) {
/* 11 */     this(x, y, SlotGrid.SlotStyle.Normal);
/*    */   }
/*    */   
/*    */   public SlotPosition(SlotPosition old, int x, int y) {
/* 15 */     this(old.x + x, old.y + y, old.style);
/*    */   }
/*    */   
/*    */   public SlotPosition(int x, int y, SlotGrid.SlotStyle style) {
/* 19 */     this.x = x;
/* 20 */     this.y = y;
/* 21 */     this.style = style;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 25 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 29 */     return this.y;
/*    */   }
/*    */   
/*    */   public SlotGrid.SlotStyle getStyle() {
/* 33 */     return this.style;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\SlotPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */