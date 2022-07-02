/*    */ package ic2.core.block.heatgenerator.gui;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.heatgenerator.container.ContainerFluidHeatGenerator;
/*    */ import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiFluidHeatGenerator extends GuiIC2<ContainerFluidHeatGenerator> {
/*    */   public GuiFluidHeatGenerator(ContainerFluidHeatGenerator container) {
/* 13 */     super((ContainerBase)container);
/*    */     
/* 15 */     addElement((GuiElement)TankGauge.createNormal(this, 70, 20, (IFluidTank)((TileEntityFluidHeatGenerator)container.base).getFluidTank()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 20 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 22 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidHeatGenerator.gui.info.Emit") + ((TileEntityFluidHeatGenerator)((ContainerFluidHeatGenerator)this.container).base).gettransmitHeat(), 96, 33, 5752026);
/* 23 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidHeatGenerator.gui.info.MaxEmit") + ((TileEntityFluidHeatGenerator)((ContainerFluidHeatGenerator)this.container).base).getMaxHeatEmittedPerTick(), 96, 52, 5752026);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 28 */     return background;
/*    */   }
/*    */   
/* 31 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIFluidHeatGenerator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\gui\GuiFluidHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */