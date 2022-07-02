/*    */ package ic2.core.block.kineticgenerator.gui;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.kineticgenerator.container.ContainerStirlingKineticGenerator;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiStirlingKineticGenerator extends GuiIC2<ContainerStirlingKineticGenerator> {
/*    */   public GuiStirlingKineticGenerator(ContainerStirlingKineticGenerator container) {
/* 12 */     super((ContainerBase)container, 204);
/*    */     
/* 14 */     addElement((GuiElement)TankGauge.createPlain(this, 19, 47, 12, 44, (IFluidTank)((TileEntityStirlingKineticGenerator)container.base).getInputTank()));
/* 15 */     addElement((GuiElement)TankGauge.createPlain(this, 145, 47, 12, 44, (IFluidTank)((TileEntityStirlingKineticGenerator)container.base).getOutputTank()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 20 */     return new ResourceLocation("ic2", "textures/gui/GUIStirlingKineticGenerator.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\gui\GuiStirlingKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */