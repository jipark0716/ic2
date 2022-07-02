/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiTradeOMatClosed
/*    */   extends GuiIC2<ContainerTradeOMatClosed>
/*    */ {
/*    */   public GuiTradeOMatClosed(ContainerTradeOMatClosed container) {
/* 21 */     super((ContainerBase)container);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 29 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 31 */     this.field_146289_q.func_78276_b(Localization.translate("container.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/* 32 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.want"), 12, 23, 4210752);
/*    */     
/* 34 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.offer"), 12, 42, 4210752);
/*    */     
/* 36 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.stock"), 12, 60, 4210752);
/* 37 */     this.field_146289_q.func_78276_b((((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.container).base).stock < 0) ? "∞" : ("" + ((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.container).base).stock), 50, 60, (((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.container).base).stock != 0) ? 4210752 : 16733525);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 42 */     return background;
/*    */   }
/*    */   
/* 45 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUITradeOMatClosed.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\GuiTradeOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */