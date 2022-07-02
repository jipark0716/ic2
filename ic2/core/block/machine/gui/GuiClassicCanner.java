/*    */ package ic2.core.block.machine.gui;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerClassicCanner;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.Gauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.LinkedGauge;
/*    */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiClassicCanner extends GuiIC2<ContainerClassicCanner> {
/*    */   public GuiClassicCanner(ContainerClassicCanner container) {
/* 18 */     super((ContainerBase)container);
/*    */     
/* 20 */     addElement((GuiElement)new LinkedGauge(this, 74, 36, (IGuiValueProvider)container.base, "progress", (Gauge.IGaugeStyle)Gauge.GaugeStyle.ProgressLongArrow));
/* 21 */     addElement((GuiElement)EnergyGauge.asBolt(this, 34, 28, (TileEntityBlock)container.base));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 26 */     return background;
/*    */   }
/*    */   
/* 29 */   public static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUI_Canner_Classic.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiClassicCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */