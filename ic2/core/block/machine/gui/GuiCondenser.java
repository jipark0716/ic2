/*    */ package ic2.core.block.machine.gui;
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerCondenser;
/*    */ import ic2.core.block.machine.tileentity.TileEntityCondenser;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.Gauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.LinkedGauge;
/*    */ import ic2.core.gui.SlotGrid;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiCondenser extends GuiIC2<ContainerCondenser> {
/*    */   public GuiCondenser(final ContainerCondenser container) {
/* 20 */     super((ContainerBase)container, 184);
/*    */     
/* 22 */     Supplier<String> ventTooltipSupplier = new Supplier<String>()
/*    */       {
/*    */         public String get() {
/* 25 */           ((TileEntityCondenser)container.base).getClass(); return Localization.translate("ic2.Condenser.gui.tooltipvent", new Object[] { Short.valueOf((short)2) });
/*    */         }
/*    */       };
/* 28 */     addElement((new SlotGrid(this, 25, 25, 1, 2, SlotGrid.SlotStyle.Normal)).withTooltip(ventTooltipSupplier));
/* 29 */     addElement((new SlotGrid(this, 133, 25, 1, 2, SlotGrid.SlotStyle.Normal)).withTooltip(ventTooltipSupplier));
/*    */     
/* 31 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 26, (TileEntityBlock)container.base));
/* 32 */     addElement((GuiElement)TankGauge.createPlain(this, 46, 27, 84, 33, (IFluidTank)((TileEntityCondenser)container.base).getInputTank()));
/* 33 */     addElement((GuiElement)TankGauge.createPlain(this, 46, 74, 84, 15, (IFluidTank)((TileEntityCondenser)container.base).getOutputTank()));
/* 34 */     addElement((GuiElement)new LinkedGauge(this, 47, 63, (IGuiValueProvider)container.base, "progress", (Gauge.IGaugeStyle)Gauge.GaugeStyle.ProgressCondenser));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 39 */     return BACKGROUND;
/*    */   }
/*    */   
/* 42 */   private static final ResourceLocation BACKGROUND = new ResourceLocation("ic2", "textures/gui/GUICondenser.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiCondenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */