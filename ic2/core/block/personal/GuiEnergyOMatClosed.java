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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiEnergyOMatClosed
/*    */   extends GuiIC2<ContainerEnergyOMatClosed>
/*    */ {
/*    */   public GuiEnergyOMatClosed(ContainerEnergyOMatClosed container) {
/* 20 */     super((ContainerBase)container);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 28 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 30 */     this.field_146289_q.func_78276_b(Localization.translate("container.inventory"), 8, this.field_147000_g - 96 + 2, 4210752);
/* 31 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.want"), 12, 21, 4210752);
/* 32 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTrader.offer"), 12, 39, 4210752);
/* 33 */     this.field_146289_q.func_78276_b(((TileEntityEnergyOMat)((ContainerEnergyOMatClosed)this.container).base).euOffer + " EU", 50, 39, 4210752);
/* 34 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.container.personalTraderEnergy.paidFor", new Object[] { Integer.valueOf(((TileEntityEnergyOMat)((ContainerEnergyOMatClosed)this.container).base).paidFor) }), 12, 57, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 39 */     return background;
/*    */   }
/*    */   
/* 42 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIEnergyOMatClosed.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\GuiEnergyOMatClosed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */