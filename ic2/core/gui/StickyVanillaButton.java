/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.GuiIC2;
/*    */ 
/*    */ public class StickyVanillaButton extends VanillaButton {
/*    */   public StickyVanillaButton(GuiIC2<?> gui, int x, int y, int width, int height, IClickHandler handler) {
/*  7 */     super(gui, x, y, width, height, handler);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     this.isOn = false;
/*    */   }
/*    */   
/*    */   protected boolean isOn;
/*    */   
/*    */   public void setOn(boolean on) {
/*    */     this.isOn = on;
/*    */   }
/*    */   
/*    */   public boolean isOn() {
/*    */     return this.isOn;
/*    */   }
/*    */   
/*    */   public StickyVanillaButton withDisableHandler(IEnableHandler handler) {
/*    */     super.withDisableHandler(handler);
/*    */     return this;
/*    */   }
/*    */   
/*    */   public StickyVanillaButton withText(String text) {
/*    */     super.withText(text);
/*    */     return this;
/*    */   }
/*    */   
/*    */   public StickyVanillaButton withTooltip(String tooltip) {
/*    */     super.withTooltip(tooltip);
/*    */     return this;
/*    */   }
/*    */   
/*    */   protected boolean isActive(int mouseX, int mouseY) {
/*    */     return (this.isOn || super.isActive(mouseX, mouseY));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\StickyVanillaButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */