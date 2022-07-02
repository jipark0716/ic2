/*    */ package ic2.core.gui;
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.GuiIC2;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public abstract class Button<T extends Button<T>> extends GuiElement<T> {
/*    */   private static final int iconSize = 16;
/*    */   private final IClickHandler handler;
/*    */   
/*    */   protected Button(GuiIC2<?> gui, int x, int y, int width, int height, IClickHandler handler) {
/* 15 */     super(gui, x, y, width, height);
/*    */     
/* 17 */     this.handler = handler;
/*    */   }
/*    */   private Supplier<String> textProvider; private Supplier<ItemStack> iconProvider;
/*    */   public T withText(final String text) {
/* 21 */     return withText(new Supplier<String>()
/*    */         {
/*    */           public String get() {
/* 24 */             return text;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public T withText(Supplier<String> textProvider) {
/* 31 */     this.textProvider = textProvider;
/*    */     
/* 33 */     return (T)this;
/*    */   }
/*    */ 
/*    */   
/*    */   public T withIcon(Supplier<ItemStack> iconProvider) {
/* 38 */     this.iconProvider = iconProvider;
/*    */     
/* 40 */     return (T)this;
/*    */   }
/*    */   
/*    */   protected int getTextColor(int mouseX, int mouseY) {
/* 44 */     return 14540253;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 49 */     if (this.textProvider != null) {
/* 50 */       String text = (String)this.textProvider.get();
/*    */       
/* 52 */       if (text != null && !text.isEmpty()) {
/* 53 */         text = Localization.translate(text);
/*    */         
/* 55 */         this.gui.drawXYCenteredString(this.x + this.width / 2, this.y + this.height / 2, text, getTextColor(mouseX, mouseY), true);
/*    */       } 
/* 57 */     } else if (this.iconProvider != null) {
/* 58 */       ItemStack stack = (ItemStack)this.iconProvider.get();
/*    */       
/* 60 */       if (stack != null && stack.func_77973_b() != null) {
/* 61 */         RenderHelper.func_74520_c();
/* 62 */         this.gui.drawItem(this.x + (this.width - 16) / 2, this.y + (this.height - 16) / 2, stack);
/* 63 */         RenderHelper.func_74518_a();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
/* 70 */     this.gui.field_146297_k.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
/*    */     
/* 72 */     this.handler.onClick(button);
/*    */     
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */