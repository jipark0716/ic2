/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public abstract class GuiDefaultBackground<T extends ContainerBase<? extends IInventory>>
/*    */   extends GuiIC2<T> {
/*    */   public GuiDefaultBackground(T container) {
/* 11 */     super((ContainerBase)container);
/*    */   }
/*    */   
/*    */   public GuiDefaultBackground(T container, int ySize) {
/* 15 */     super((ContainerBase)container, ySize);
/*    */   }
/*    */   
/*    */   public GuiDefaultBackground(T container, int xSize, int ySize) {
/* 19 */     super((ContainerBase)container, xSize, ySize);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawBackgroundAndTitle(float partialTicks, int mouseX, int mouseY) {
/* 24 */     GuiElement.bindCommonTexture();
/*    */ 
/*    */     
/* 27 */     drawTexturedRect(-16.0D, -16.0D, 32.0D, 32.0D, 0.0D, 0.0D);
/* 28 */     drawTexturedRect((this.field_146999_f - 16), -16.0D, 32.0D, 32.0D, 64.0D, 0.0D);
/* 29 */     drawTexturedRect(-16.0D, (this.field_147000_g - 16), 32.0D, 32.0D, 0.0D, 64.0D);
/* 30 */     drawTexturedRect((this.field_146999_f - 16), (this.field_147000_g - 16), 32.0D, 32.0D, 64.0D, 64.0D);
/*    */     
/*    */     int side;
/* 33 */     for (side = 0; side < 2; side++) {
/* 34 */       int i = this.field_147000_g * side - 16;
/* 35 */       int v = 64 * side;
/*    */       
/* 37 */       for (int x = 16; x < this.field_146999_f - 16; x += 32) {
/* 38 */         int width = Math.min(32, this.field_146999_f - 16 - x);
/*    */         
/* 40 */         drawTexturedRect(x, i, width, 32.0D, 32.0D, v);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 45 */     for (side = 0; side < 2; side++) {
/* 46 */       int x = this.field_146999_f * side - 16;
/* 47 */       int u = 64 * side;
/*    */       
/* 49 */       for (int i = 16; i < this.field_147000_g - 16; i += 32) {
/* 50 */         int height = Math.min(32, this.field_147000_g - 16 - i);
/*    */         
/* 52 */         drawTexturedRect(x, i, 32.0D, height, u, 32.0D);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 57 */     for (int y = 16; y < this.field_147000_g - 16; y += 32) {
/* 58 */       int height = Math.min(32, this.field_147000_g - 16 - y);
/*    */       
/* 60 */       for (int x = 16; x < this.field_146999_f - 16; x += 32) {
/* 61 */         int width = Math.min(32, this.field_146999_f - 16 - x);
/*    */         
/* 63 */         drawTexturedRect(x, y, width, height, 32.0D, 32.0D);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\GuiDefaultBackground.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */