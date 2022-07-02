/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.GuiIC2;
/*    */ 
/*    */ public class BasicButton extends Button<BasicButton> {
/*    */   public static BasicButton create(GuiIC2<?> gui, int x, int y, IClickHandler handler, ButtonStyle style) {
/*  7 */     return new BasicButton(gui, x, y, handler, style);
/*    */   }
/*    */   private final ButtonStyle style;
/*    */   protected BasicButton(GuiIC2<?> gui, int x, int y, IClickHandler handler, ButtonStyle style) {
/* 11 */     super(gui, x, y, style.width, style.height, handler);
/*    */     
/* 13 */     this.style = style;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 18 */     bindCommonTexture();
/*    */     
/* 20 */     this.gui.drawTexturedRect(this.x, this.y, this.style.width, this.style.height, this.style.u, this.style.v);
/*    */     
/* 22 */     super.drawBackground(mouseX, mouseY);
/*    */   }
/*    */   
/*    */   public enum ButtonStyle {
/* 26 */     AdvMinerReset(192, 32, 36, 15),
/* 27 */     AdvMinerMode(228, 32, 18, 15),
/* 28 */     AdvMinerSilkTouch(192, 47, 18, 15); final int u; final int v;
/*    */     
/*    */     ButtonStyle(int u, int v, int width, int height) {
/* 31 */       this.u = u;
/* 32 */       this.v = v;
/* 33 */       this.width = width;
/* 34 */       this.height = height;
/*    */     }
/*    */     
/*    */     final int width;
/*    */     final int height;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\BasicButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */