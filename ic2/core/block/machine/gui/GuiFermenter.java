/*    */ package ic2.core.block.machine.gui;
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.machine.container.ContainerFermenter;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFermenter;
/*    */ import ic2.core.gui.Gauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.LinkedGauge;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiFermenter extends GuiIC2<ContainerFermenter> {
/*    */   public GuiFermenter(final ContainerFermenter container) {
/* 17 */     super((ContainerBase)container, 184);
/*    */     
/* 19 */     addElement((GuiElement)TankGauge.createPlain(this, 38, 49, 48, 30, (IFluidTank)((TileEntityFermenter)container.base).getInputTank()));
/* 20 */     addElement((GuiElement)TankGauge.createNormal(this, 125, 22, (IFluidTank)((TileEntityFermenter)container.base).getOutputTank()));
/* 21 */     addElement((new LinkedGauge(this, 42, 41, (IGuiValueProvider)container.base, "heat", (Gauge.IGaugeStyle)Gauge.GaugeStyle.HeatFermenter))
/* 22 */         .withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 25 */               return Localization.translate("ic2.Fermenter.gui.info.conversion") + " " + (int)(((TileEntityFermenter)container.base).getGuiValue("heat") * 100.0D) + "%";
/*    */             }
/*    */           }));
/* 28 */     addElement((new LinkedGauge(this, 38, 88, (IGuiValueProvider)container.base, "progress", (Gauge.IGaugeStyle)Gauge.GaugeStyle.ProgressFermenter))
/* 29 */         .withTooltip("ic2.Fermenter.gui.info.waste"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 34 */     return TEXTURE;
/*    */   }
/*    */   
/* 37 */   private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIFermenter.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiFermenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */