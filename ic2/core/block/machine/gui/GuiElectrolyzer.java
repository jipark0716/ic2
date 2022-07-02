/*     */ package ic2.core.block.machine.gui;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.machine.container.ContainerElectrolyzer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*     */ import ic2.core.gui.CustomGauge;
/*     */ import ic2.core.gui.ElectrolyzerTankController;
/*     */ import ic2.core.gui.EnergyGauge;
/*     */ import ic2.core.gui.FluidSlot;
/*     */ import ic2.core.gui.Gauge;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IEnableHandler;
/*     */ import ic2.core.gui.RecipeButton;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTankInfo;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiElectrolyzer
/*     */   extends GuiIC2<ContainerElectrolyzer>
/*     */ {
/*     */   public static class ElectrolyzerFluidTank
/*     */     implements IFluidTank
/*     */   {
/*     */     private Pair<FluidStack, EnumFacing> fluid;
/*     */     
/*     */     public void clear() {
/*  44 */       this.fluid = null;
/*     */     }
/*     */     
/*     */     public void setPair(Pair<FluidStack, EnumFacing> pair) {
/*  48 */       this.fluid = pair;
/*     */     }
/*     */     
/*     */     public EnumFacing getSide() {
/*  52 */       return (this.fluid == null) ? null : (EnumFacing)this.fluid.getRight();
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack getFluid() {
/*  57 */       return (this.fluid == null) ? null : (FluidStack)this.fluid.getLeft();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getFluidAmount() {
/*  62 */       return ((this.fluid == null) ? null : Integer.valueOf(((FluidStack)this.fluid.getLeft()).amount)).intValue();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCapacity() {
/*  67 */       throw new UnsupportedOperationException("Not this");
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidTankInfo getInfo() {
/*  72 */       throw new UnsupportedOperationException("Not this");
/*     */     }
/*     */ 
/*     */     
/*     */     public int fill(FluidStack resource, boolean doFill) {
/*  77 */       throw new UnsupportedOperationException("Not this");
/*     */     }
/*     */ 
/*     */     
/*     */     public FluidStack drain(int maxDrain, boolean doDrain) {
/*  82 */       throw new UnsupportedOperationException("Not this");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public enum ElectrolyzerGauges
/*     */   {
/*  89 */     ONE_TANK((String)(new Gauge.GaugePropertyBuilder(57, 232, 12, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down)).build(), 48),
/*  90 */     TWO_TANK((String)(new Gauge.GaugePropertyBuilder(1, 232, 54, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down)).build(), 24),
/*  91 */     THREE_TANK((String)(new Gauge.GaugePropertyBuilder(41, 159, 54, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down)).build(), 24),
/*  92 */     FOUR_TANK((String)(new Gauge.GaugePropertyBuilder(1, 208, 96, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down)).build(), 3),
/*  93 */     FIVE_TANK((String)(new Gauge.GaugePropertyBuilder(1, 184, 96, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down)).build(), 3);
/*     */     
/*     */     ElectrolyzerGauges(Gauge.GaugeProperties properties, int offset) {
/*  96 */       this.properties = properties;
/*  97 */       this.offset = offset;
/*     */     }
/*     */     
/*     */     public final int offset;
/*     */     public final Gauge.GaugeProperties properties;
/*     */   }
/*     */   
/*     */   public GuiElectrolyzer(ContainerElectrolyzer container) {
/* 105 */     super((ContainerBase)container);
/*     */     
/* 107 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 44, (TileEntityBlock)container.base));
/*     */     
/* 109 */     int controllerX = 36, controllerY = 16;
/* 110 */     addElement((GuiElement)FluidSlot.createFluidSlot(this, 78, 16, (IFluidTank)((TileEntityElectrolyzer)container.base).getInput()));
/*     */     
/* 112 */     ElectrolyzerFluidTank[] tanks = new ElectrolyzerFluidTank[5];
/* 113 */     for (int i = 0; i < 5; i++) {
/* 114 */       final ElectrolyzerFluidTank tank = new ElectrolyzerFluidTank();
/*     */       
/* 116 */       addElement((GuiElement)new FluidSlot(this, 36 + 21 * i, 61, 18, 18, tank)
/*     */           {
/*     */             protected List<String> getToolTip() {
/* 119 */               List<String> ret = new ArrayList<>(3);
/*     */               
/* 121 */               FluidStack fluid = tank.getFluid();
/* 122 */               if (fluid != null) {
/* 123 */                 Fluid liquid = fluid.getFluid();
/*     */                 
/* 125 */                 if (liquid != null) {
/* 126 */                   ret.add(liquid.getLocalizedName(fluid));
/* 127 */                   ret.add("Amount: " + fluid.amount + ' ' + Localization.translate("ic2.generic.text.mb"));
/* 128 */                   ret.add("Output Tank: " + StringUtils.capitalize(tank.getSide().func_176610_l()));
/*     */                 } else {
/* 130 */                   ret.add("Invalid FluidStack instance.");
/*     */                 } 
/*     */               } 
/*     */               
/* 134 */               return ret;
/*     */             }
/*     */           });
/* 137 */       tanks[i] = tank;
/*     */     } 
/*     */     
/* 140 */     ElectrolyzerTankController controller = new ElectrolyzerTankController(this, 36, 16, tanks);
/* 141 */     addElement((GuiElement)controller);
/*     */     
/* 143 */     GuiElement<?> last = null;
/* 144 */     for (ElectrolyzerGauges gauge : ElectrolyzerGauges.values()) {
/* 145 */       addElement(last = (new CustomGauge(this, 36 + gauge.offset, 36, (CustomGauge.IGaugeRatioProvider)container.base, gauge.properties)).withEnableHandler(getEnableHandler(controller, gauge.ordinal() + 1)));
/*     */     }
/*     */     
/* 148 */     if (RecipeButton.canUse()) {
/* 149 */       assert last != null;
/* 150 */       addElement((GuiElement)new RecipeButton(last, new String[] { TeBlock.electrolyzer.getName() }));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IEnableHandler getEnableHandler(final ElectrolyzerTankController controller, final int tank) {
/* 155 */     return new IEnableHandler()
/*     */       {
/*     */         public boolean isEnabled() {
/* 158 */           return (controller.getLastRecipeLength() == tank);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getTexture() {
/* 165 */     return background;
/*     */   }
/*     */   
/* 168 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectrolyzer.png");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiElectrolyzer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */