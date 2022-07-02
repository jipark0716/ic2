/*    */ package ic2.jeiIntegration;
/*    */ 
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.init.Localization;
/*    */ import mezz.jei.api.gui.IDrawable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ public class TextDrawable
/*    */   implements IDrawable
/*    */ {
/*    */   private final String text;
/*    */   private final Text.TextAlignment alignment;
/*    */   private final int color;
/*    */   
/*    */   public TextDrawable(String text, Text.TextAlignment alignment, int color) {
/* 17 */     this.text = text;
/* 18 */     this.alignment = alignment;
/* 19 */     this.color = color;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(Minecraft arg0) {
/*    */     int x;
/* 28 */     switch (this.alignment) { case Start:
/* 29 */         x = 0; break;
/* 30 */       case Center: x = arg0.field_71462_r.field_146294_l / 2; break;
/* 31 */       case End: x = arg0.field_71462_r.field_146294_l - getWidth(); break;
/* 32 */       default: throw new IllegalArgumentException("invalid alignment: " + this.alignment); }
/*    */ 
/*    */     
/* 35 */     arg0.field_71466_p.func_78276_b(Localization.translate(this.text), x, 0, this.color);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(Minecraft arg0, int arg1, int arg2) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHeight() {
/* 48 */     return 12;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWidth() {
/* 53 */     return (Minecraft.func_71410_x()).field_71466_p.func_78256_a(Localization.translate(this.text));
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\jeiIntegration\TextDrawable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */