/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.Process;
/*    */ import ic2.core.util.Util;
/*    */ 
/*    */ public class ProgressGauge extends GuiElement<ProgressGauge> {
/*    */   private final Process process;
/*    */   private final ProgressBarType type;
/*    */   
/*    */   public ProgressGauge(GuiIC2<?> gui, int x, int y, TileEntityBlock te, ProgressBarType type) {
/* 13 */     super(gui, x, y, type.w, type.h);
/*    */     
/* 15 */     this.type = type;
/* 16 */     this.process = (Process)te.getComponent(Process.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 21 */     bindCommonTexture();
/*    */     
/* 23 */     this.gui.drawTexturedRect(this.x, this.y, this.type.w, this.type.h, this.type.emptyX, this.type.emptyY);
/*    */     
/* 25 */     int renderWidth = Util.limit((int)Math.round(getProgressRatio() * this.type.w), 0, this.type.w);
/*    */     
/* 27 */     if (renderWidth > 0) {
/* 28 */       this.gui.drawTexturedRect(this.x, this.y, renderWidth, this.type.h, this.type.fullX, this.type.fullY);
/*    */     }
/*    */   }
/*    */   
/*    */   protected double getProgressRatio() {
/* 33 */     return this.process.getProgressRatio();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public enum ProgressBarType
/*    */   {
/* 40 */     type_1(165, 0, 165, 16, 22, 15),
/* 41 */     type_2(165, 35, 165, 52, 21, 11),
/* 42 */     type_3(165, 64, 165, 80, 22, 15),
/* 43 */     type_4(165, 96, 165, 112, 22, 15),
/* 44 */     type_5(133, 64, 133, 80, 18, 15);
/*    */     
/*    */     ProgressBarType(int emptyX, int emptyY, int fullX, int fullY, int w, int h) {
/* 47 */       this.emptyX = emptyX;
/* 48 */       this.emptyY = emptyY;
/* 49 */       this.fullX = fullX;
/* 50 */       this.fullY = fullY;
/* 51 */       this.w = w;
/* 52 */       this.h = h;
/*    */     }
/*    */     
/*    */     private int emptyX;
/*    */     private int emptyY;
/*    */     private int fullX;
/*    */     private int fullY;
/*    */     private int w;
/*    */     private int h;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\ProgressGauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */