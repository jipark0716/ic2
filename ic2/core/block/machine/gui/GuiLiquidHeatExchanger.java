/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.machine.container.ContainerLiquidHeatExchanger;
/*    */ import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.SlotGrid;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiLiquidHeatExchanger extends GuiIC2<ContainerLiquidHeatExchanger> {
/*    */   public GuiLiquidHeatExchanger(ContainerLiquidHeatExchanger container) {
/* 19 */     super((ContainerBase)container, 204);
/*    */     
/* 21 */     addElement((new SlotGrid(this, 46, 50, 5, 1, SlotGrid.SlotStyle.Plain, 1, 1))
/* 22 */         .withTooltip("ic2.LiquidHeatExchanger.gui.tooltipvent"));
/* 23 */     addElement((new SlotGrid(this, 46, 72, 5, 1, SlotGrid.SlotStyle.Plain, 1, 1))
/* 24 */         .withTooltip("ic2.LiquidHeatExchanger.gui.tooltipvent"));
/* 25 */     addElement((GuiElement)TankGauge.createPlain(this, 19, 47, 12, 44, (IFluidTank)((TileEntityLiquidHeatExchanger)container.base).getInputTank()));
/* 26 */     addElement((GuiElement)TankGauge.createPlain(this, 145, 47, 12, 44, (IFluidTank)((TileEntityLiquidHeatExchanger)container.base).getOutputTank()));
/* 27 */     addElement(Text.create(this, 20, 28, 138, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 30 */                 return Localization.translate("ic2.ElectricHeatGenerator.gui.hUmax", new Object[] { Integer.valueOf(((TileEntityLiquidHeatExchanger)((ContainerLiquidHeatExchanger)GuiLiquidHeatExchanger.access$000(this.this$0)).base).gettransmitHeat()), Integer.valueOf(((TileEntityLiquidHeatExchanger)((ContainerLiquidHeatExchanger)GuiLiquidHeatExchanger.access$100(this.this$0)).base).getMaxHeatEmittedPerTick())
/*    */                     });
/*    */               }
/* 33 */             }), 5752026, false, true, true).withTooltip("ic2.LiquidHeatExchanger.gui.tooltipheat"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 38 */     return new ResourceLocation("ic2", "textures/gui/GUIHeatSourceFluid.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiLiquidHeatExchanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */