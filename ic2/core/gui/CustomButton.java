/*    */ package ic2.core.gui;
/*    */ import ic2.core.GuiIC2;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CustomButton extends Button<CustomButton> {
/*    */   private final ResourceLocation texture;
/*    */   
/*    */   public CustomButton(GuiIC2<?> gui, int x, int y, int width, int height, IClickHandler handler) {
/*  9 */     this(gui, x, y, width, height, 0, 0, (ResourceLocation)null, handler);
/*    */   }
/*    */   private final IOverlaySupplier overlaySupplier;
/*    */   
/*    */   public CustomButton(GuiIC2<?> gui, int x, int y, int width, int height, int overlayX, int overlayY, ResourceLocation texture, IClickHandler handler) {
/* 14 */     this(gui, x, y, width, height, new OverlaySupplier(overlayX, overlayY, overlayX + width, overlayY + height), texture, handler);
/*    */   }
/*    */ 
/*    */   
/*    */   public CustomButton(GuiIC2<?> gui, int x, int y, int width, int height, IOverlaySupplier overlaySupplier, ResourceLocation texture, IClickHandler handler) {
/* 19 */     super(gui, x, y, width, height, handler);
/*    */     
/* 21 */     this.texture = texture;
/* 22 */     this.overlaySupplier = overlaySupplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 27 */     if (this.texture != null) {
/* 28 */       bindTexture(this.texture);
/*    */       
/* 30 */       double scale = 0.00390625D;
/*    */       
/* 32 */       this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, this.overlaySupplier.getUS() * scale, this.overlaySupplier.getVS() * scale, this.overlaySupplier.getUE() * scale, this.overlaySupplier.getVE() * scale, false);
/*    */     } 
/*    */     
/* 35 */     if (contains(mouseX, mouseY)) {
/* 36 */       this.gui.drawColoredRect(this.x, this.y, this.width, this.height, -2130706433);
/*    */     }
/*    */     
/* 39 */     super.drawBackground(mouseX, mouseY);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\CustomButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */