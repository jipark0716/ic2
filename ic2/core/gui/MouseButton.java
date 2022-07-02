/*    */ package ic2.core.gui;
/*    */ 
/*    */ public enum MouseButton {
/*  4 */   left(0),
/*  5 */   right(1); public final int id;
/*    */   
/*    */   MouseButton(int id) {
/*  8 */     this.id = id;
/*    */   }
/*    */   private static final MouseButton[] map;
/*    */   public static MouseButton get(int id) {
/* 12 */     if (id < 0 || id >= map.length) return null;
/*    */     
/* 14 */     return map[id];
/*    */   }
/*    */   
/*    */   private static MouseButton[] createMap() {
/* 18 */     MouseButton[] values = values();
/* 19 */     int max = -1;
/*    */     
/* 21 */     for (MouseButton button : values) {
/* 22 */       if (button.id > max) max = button.id;
/*    */     
/*    */     } 
/* 25 */     if (max < 0) return new MouseButton[0];
/*    */     
/* 27 */     MouseButton[] ret = new MouseButton[max + 1];
/*    */     
/* 29 */     for (MouseButton button : values) {
/* 30 */       ret[button.id] = button;
/*    */     }
/*    */     
/* 33 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   static {
/* 38 */     map = createMap();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\MouseButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */