/*    */ package ic2.core.gui;
/*    */ 
/*    */ public class Image extends GuiElement<Image> {
/*    */   private final ResourceLocation texture;
/*    */   private final int baseWidth;
/*    */   private final int baseHeight;
/*    */   
/*    */   public static Image create(GuiIC2<?> gui, int x, int y, int width, int height, ResourceLocation texture, int baseWidth, int baseHeight, int uS, int vS, int uE, int vE) {
/*  9 */     return create(gui, x, y, width, height, texture, baseWidth, baseHeight, new OverlaySupplier(uS, vS, uE, vE));
/*    */   }
/*    */   private final IOverlaySupplier overlay; private final boolean autoWidth; private final boolean autoHeight;
/*    */   public static Image create(GuiIC2<?> gui, int x, int y, int width, int height, ResourceLocation texture, int baseWidth, int baseHeight, IOverlaySupplier overlay) {
/* 13 */     boolean autoWidth = (width < 0);
/* 14 */     boolean autoHeight = (height < 0);
/*    */     
/* 16 */     if (autoWidth) width = 0; 
/* 17 */     if (autoHeight) height = 0;
/*    */     
/* 19 */     return new Image(gui, x, y, width, height, texture, baseWidth, baseHeight, overlay, autoWidth, autoHeight);
/*    */   }
/*    */   
/*    */   protected Image(GuiIC2<?> gui, int x, int y, int width, int height, ResourceLocation texture, int baseWidth, int baseHeight, IOverlaySupplier overlay, boolean autoWidth, boolean autoHeight) {
/* 23 */     super(gui, x, y, width, height);
/*    */     
/* 25 */     if (texture == null) throw new NullPointerException("null texture"); 
/* 26 */     if (overlay == null) throw new NullPointerException("null overlay");
/*    */     
/* 28 */     this.texture = texture;
/* 29 */     this.baseWidth = baseWidth;
/* 30 */     this.baseHeight = baseHeight;
/* 31 */     this.overlay = overlay;
/* 32 */     this.autoWidth = autoWidth;
/* 33 */     this.autoHeight = autoHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 38 */     super.drawBackground(mouseX, mouseY);
/*    */     
/* 40 */     GlTexture texture = GlTexture.get(this.texture);
/*    */     
/* 42 */     if (texture != null) {
/* 43 */       if (this.autoWidth) this.width = texture.getWidth(); 
/* 44 */       if (this.autoHeight) this.height = texture.getHeight();
/*    */       
/* 46 */       double widthScale = (this.baseWidth > 0) ? (1.0D / this.baseWidth) : (1.0D / texture.getCanvasWidth());
/* 47 */       double heightScale = (this.baseHeight > 0) ? (1.0D / this.baseHeight) : (1.0D / texture.getCanvasHeight());
/*    */       
/* 49 */       double uS = this.overlay.getUS();
/* 50 */       double vS = this.overlay.getVS();
/* 51 */       double uE = this.overlay.getUE();
/* 52 */       if (uE < 0.0D) uE = uS + this.width; 
/* 53 */       double vE = this.overlay.getVE();
/* 54 */       if (vE < 0.0D) vE = vS + this.height;
/*    */       
/* 56 */       texture.bind();
/*    */       
/* 58 */       this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, uS * widthScale, vS * heightScale, uE * widthScale, vE * heightScale, false);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 63 */       if (this.autoWidth) this.width = 0; 
/* 64 */       if (this.autoHeight) this.height = 0; 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\Image.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */