/*     */ package ic2.core.gui;
/*     */ 
/*     */ import ic2.core.GuiIC2;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class VanillaButton
/*     */   extends Button<VanillaButton> {
/*     */   public VanillaButton(GuiIC2<?> gui, int x, int y, int width, int height, IClickHandler handler) {
/*   9 */     super(gui, x, y, width, height, handler);
/*     */   }
/*     */   protected IEnableHandler disableHandler;
/*     */   public VanillaButton withDisableHandler(IEnableHandler handler) {
/*  13 */     this.disableHandler = handler;
/*     */     
/*  15 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/*  19 */     return (this.disableHandler != null && !this.disableHandler.isEnabled());
/*     */   }
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/*     */     int u, v;
/*  24 */     bindTexture(texture);
/*     */ 
/*     */ 
/*     */     
/*  28 */     if (isDisabled()) {
/*  29 */       u = 0;
/*  30 */       v = 46;
/*  31 */     } else if (!isActive(mouseX, mouseY)) {
/*  32 */       u = 0;
/*  33 */       v = 66;
/*     */     } else {
/*  35 */       u = 0;
/*  36 */       v = 86;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     int minLeft = 2;
/*  53 */     int minRight = 2;
/*     */     
/*  55 */     while (this.width < minLeft + minRight) {
/*  56 */       if (minLeft > minRight) {
/*  57 */         minLeft--; continue;
/*     */       } 
/*  59 */       minRight--;
/*     */     } 
/*     */ 
/*     */     
/*  63 */     int cx = this.x;
/*     */     
/*  65 */     int remainingWidth = this.width;
/*  66 */     int cWidth = Math.min(remainingWidth, 200) - minRight;
/*     */ 
/*     */     
/*  69 */     drawVerticalPiece(this.gui, cx, this.y, cWidth, this.height, u, v);
/*  70 */     cx += cWidth;
/*  71 */     remainingWidth -= cWidth;
/*     */ 
/*     */     
/*  74 */     while (remainingWidth > 200 - minLeft) {
/*  75 */       cWidth = Math.min(remainingWidth, 200 - minLeft) - minRight;
/*     */       
/*  77 */       drawVerticalPiece(this.gui, cx, this.y, cWidth, this.height, u + minLeft, v);
/*  78 */       cx += cWidth;
/*  79 */       remainingWidth -= cWidth;
/*     */     } 
/*     */ 
/*     */     
/*  83 */     drawVerticalPiece(this.gui, cx, this.y, remainingWidth, this.height, u + 200 - remainingWidth, v);
/*     */     
/*  85 */     super.drawBackground(mouseX, mouseY);
/*     */   }
/*     */   
/*     */   private static void drawVerticalPiece(GuiIC2<?> gui, int x, int y, int width, int height, int u, int v) {
/*  89 */     int minTop = 2;
/*  90 */     int minBottom = 3;
/*     */     
/*  92 */     while (height < minTop + minBottom) {
/*  93 */       if (minTop > minBottom) {
/*  94 */         minTop--; continue;
/*     */       } 
/*  96 */       minBottom--;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 101 */     int cHeight = Math.min(height, 20) - minBottom;
/*     */     
/* 103 */     gui.drawTexturedRect(x, y, width, cHeight, u, v);
/* 104 */     y += cHeight;
/* 105 */     height -= cHeight;
/*     */ 
/*     */     
/* 108 */     while (height > 20 - minTop) {
/* 109 */       cHeight = Math.min(height, 20 - minTop) - minBottom;
/*     */       
/* 111 */       gui.drawTexturedRect(x, y, width, cHeight, u, (v + minTop));
/* 112 */       y += cHeight;
/* 113 */       height -= cHeight;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     gui.drawTexturedRect(x, y, width, height, u, (v + 20 - height));
/*     */   }
/*     */   
/*     */   protected boolean isActive(int mouseX, int mouseY) {
/* 121 */     return contains(mouseX, mouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getTextColor(int mouseX, int mouseY) {
/* 126 */     return isDisabled() ? 10526880 : (isActive(mouseX, mouseY) ? 16777120 : 14737632);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
/* 131 */     return isDisabled() ? false : super.onMouseClick(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static final ResourceLocation texture = new ResourceLocation("textures/gui/widgets.png");
/*     */   private static final int uNormal = 0;
/*     */   private static final int vNormal = 66;
/*     */   private static final int uHover = 0;
/*     */   private static final int vHover = 86;
/*     */   private static final int uDisabled = 0;
/*     */   private static final int vDisabled = 46;
/*     */   private static final int rawWidth = 200;
/*     */   private static final int rawHeight = 20;
/*     */   private static final int minLeft = 2;
/*     */   private static final int minRight = 2;
/*     */   private static final int minTop = 2;
/*     */   private static final int minBottom = 3;
/*     */   private static final int colorNormal = 14737632;
/*     */   private static final int colorHover = 16777120;
/*     */   private static final int colorDisabled = 10526880;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\VanillaButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */