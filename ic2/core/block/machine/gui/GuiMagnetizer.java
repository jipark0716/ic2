/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.BlockIC2Fence;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerMagnetizer;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiMagnetizer extends GuiIC2<ContainerMagnetizer> {
/*    */   public GuiMagnetizer(ContainerMagnetizer container) {
/* 18 */     super((ContainerBase)container);
/*    */     
/* 20 */     addElement((GuiElement)EnergyGauge.asBolt(this, 11, 28, (TileEntityBlock)container.base));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 25 */     return new ResourceLocation("ic2", "textures/gui/GUIMagnetizer.png");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 30 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 32 */     if (BlockIC2Fence.hasMetalShoes(((ContainerMagnetizer)this.container).player)) {
/* 33 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.Magnetizer.gui.hasMetalShoes"), 18, 66, 4259648);
/*    */     } else {
/* 35 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.Magnetizer.gui.noMetalShoes"), 18, 66, 16728128);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiMagnetizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */