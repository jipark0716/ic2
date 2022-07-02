/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.VanillaButton;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiEnergyOMatOpen
/*    */   extends GuiIC2<ContainerEnergyOMatOpen>
/*    */ {
/*    */   public GuiEnergyOMatOpen(ContainerEnergyOMatOpen container) {
/* 16 */     super((ContainerBase)container);
/*    */     
/* 18 */     addElement((GuiElement)(new VanillaButton(this, 102, 16, 32, 10, createEventSender(0)))
/* 19 */         .withText("-100k"));
/* 20 */     addElement((GuiElement)(new VanillaButton(this, 102, 26, 32, 10, createEventSender(1)))
/* 21 */         .withText("-10k"));
/* 22 */     addElement((GuiElement)(new VanillaButton(this, 102, 36, 32, 10, createEventSender(2)))
/* 23 */         .withText("-1k"));
/* 24 */     addElement((GuiElement)(new VanillaButton(this, 102, 46, 32, 10, createEventSender(3)))
/* 25 */         .withText("-100"));
/* 26 */     addElement((GuiElement)(new VanillaButton(this, 134, 16, 32, 10, createEventSender(4)))
/* 27 */         .withText("+100k"));
/* 28 */     addElement((GuiElement)(new VanillaButton(this, 134, 26, 32, 10, createEventSender(5)))
/* 29 */         .withText("+10k"));
/* 30 */     addElement((GuiElement)(new VanillaButton(this, 134, 36, 32, 10, createEventSender(6)))
/* 31 */         .withText("+1k"));
/* 32 */     addElement((GuiElement)(new VanillaButton(this, 134, 46, 32, 10, createEventSender(7)))
/* 33 */         .withText("+100"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 41 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 43 */     this.field_146289_q.func_78276_b(Localization.translate("container.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/* 44 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.offer"), 100, 60, 4210752);
/* 45 */     this.field_146289_q.func_78276_b(((TileEntityEnergyOMat)((ContainerEnergyOMatOpen)this.container).base).euOffer + " EU", 100, 68, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 50 */     return background;
/*    */   }
/*    */   
/* 53 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIEnergyOMatOpen.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\GuiEnergyOMatOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */