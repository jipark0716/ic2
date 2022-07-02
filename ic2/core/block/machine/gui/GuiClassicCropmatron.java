/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerClassicCropmatron;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiClassicCropmatron extends GuiIC2<ContainerClassicCropmatron> {
/*    */   public GuiClassicCropmatron(ContainerClassicCropmatron container) {
/* 16 */     super((ContainerBase)container);
/*    */     
/* 18 */     addElement((GuiElement)EnergyGauge.asBolt(this, 29, 39, (TileEntityBlock)container.base));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 23 */     return background;
/*    */   }
/*    */   
/* 26 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUI_Cropmatron_Classic.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiClassicCropmatron.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */