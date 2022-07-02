/*     */ package ic2.core.gui;
/*     */ import ic2.core.GuiIC2;
/*     */ import java.util.List;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ 
/*     */ public class TankGauge extends GuiElement<TankGauge> {
/*     */   public static final int filledBackgroundU = 6;
/*     */   public static final int filledScaleU = 38;
/*     */   public static final int emptyU = 70;
/*     */   public static final int v = 100;
/*     */   public static final int normalWidth = 20;
/*     */   public static final int normalHeight = 55;
/*     */   
/*     */   public static TankGauge createNormal(GuiIC2<?> gui, int x, int y, IFluidTank tank) {
/*  17 */     return new TankGauge(gui, x, y, 20, 55, tank, TankGuiStyle.Normal);
/*     */   }
/*     */   public static final int fluidOffsetX = 4; public static final int fluidOffsetY = 4; public static final int fluidNetWidth = 12; public static final int fluidNetHeight = 47; private final IFluidTank tank; private final TankGuiStyle style;
/*     */   public static TankGauge createPlain(GuiIC2<?> gui, int x, int y, int width, int height, IFluidTank tank) {
/*  21 */     return new TankGauge(gui, x, y, width, height, tank, TankGuiStyle.Plain);
/*     */   }
/*     */   
/*     */   public static TankGauge createBorderless(GuiIC2<?> gui, int x, int y, IFluidTank tank, boolean mirrored) {
/*  25 */     return new TankGauge(gui, x, y, 12, 47, tank, mirrored ? TankGuiStyle.BorderlessMirrored : TankGuiStyle.Borderless);
/*     */   }
/*     */   
/*     */   private TankGauge(GuiIC2<?> gui, int x, int y, int width, int height, IFluidTank tank, TankGuiStyle style) {
/*  29 */     super(gui, x, y, width, height);
/*     */     
/*  31 */     if (tank == null) throw new NullPointerException("null tank");
/*     */     
/*  33 */     this.tank = tank;
/*  34 */     this.style = style;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/*  39 */     bindCommonTexture();
/*     */     
/*  41 */     FluidStack fs = this.tank.getFluid();
/*     */     
/*  43 */     if (fs == null || fs.amount <= 0) {
/*  44 */       if (this.style.withBorder) {
/*  45 */         this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, 70.0D, 100.0D, this.style.mirrorGauge);
/*  46 */       } else if (this.style.withGauge) {
/*  47 */         this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, 74.0D, 104.0D, this.style.mirrorGauge);
/*     */       } 
/*     */     } else {
/*  50 */       if (this.style.withBorder)
/*     */       {
/*  52 */         this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, 6.0D, 100.0D);
/*     */       }
/*     */ 
/*     */       
/*  56 */       int fluidX = this.x;
/*  57 */       int fluidY = this.y;
/*  58 */       int fluidWidth = this.width;
/*  59 */       int fluidHeight = this.height;
/*     */       
/*  61 */       if (this.style.withBorder) {
/*  62 */         fluidX += 4;
/*  63 */         fluidY += 4;
/*  64 */         fluidWidth = 12;
/*  65 */         fluidHeight = 47;
/*     */       } 
/*     */       
/*  68 */       Fluid fluid = fs.getFluid();
/*  69 */       TextureAtlasSprite sprite = (fluid != null) ? getBlockTextureMap().func_110572_b(fluid.getStill(fs).toString()) : null;
/*  70 */       int color = (fluid != null) ? fluid.getColor(fs) : -1;
/*  71 */       double renderHeight = fluidHeight * Util.limit(fs.amount / this.tank.getCapacity(), 0.0D, 1.0D);
/*     */       
/*  73 */       bindBlockTexture();
/*  74 */       this.gui.drawSprite(fluidX, (fluidY + fluidHeight) - renderHeight, fluidWidth, renderHeight, sprite, color, 1.0D, false, true);
/*     */       
/*  76 */       if (this.style.withGauge) {
/*     */         
/*  78 */         bindCommonTexture();
/*     */         
/*  80 */         int gaugeX = this.x;
/*  81 */         int gaugeY = this.y;
/*     */         
/*  83 */         if (!this.style.withBorder) {
/*  84 */           gaugeX -= 4;
/*  85 */           gaugeY -= 4;
/*     */         } 
/*     */         
/*  88 */         this.gui.drawTexturedRect(gaugeX, gaugeY, 20.0D, 55.0D, 38.0D, 100.0D, this.style.mirrorGauge);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> getToolTip() {
/*  95 */     List<String> ret = super.getToolTip();
/*     */     
/*  97 */     FluidStack fs = this.tank.getFluid();
/*     */     
/*  99 */     if (fs == null || fs.amount <= 0) {
/* 100 */       ret.add(Localization.translate("ic2.generic.text.empty"));
/*     */     } else {
/* 102 */       Fluid fluid = fs.getFluid();
/*     */       
/* 104 */       if (fluid != null) {
/* 105 */         ret.add(fluid.getLocalizedName(fs) + ": " + fs.amount + " " + Localization.translate("ic2.generic.text.mb"));
/*     */       } else {
/* 107 */         ret.add("invalid fluid stack");
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return ret;
/*     */   }
/*     */   
/*     */   private enum TankGuiStyle {
/* 115 */     Normal(true, true, false),
/* 116 */     Borderless(false, true, false),
/* 117 */     BorderlessMirrored(false, true, true),
/* 118 */     Plain(false, false, false); public final boolean withBorder; public final boolean withGauge; public final boolean mirrorGauge;
/*     */     
/*     */     TankGuiStyle(boolean withBorder, boolean withGauge, boolean mirrorGauge) {
/* 121 */       this.withBorder = withBorder;
/* 122 */       this.withGauge = withGauge;
/* 123 */       this.mirrorGauge = mirrorGauge;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\TankGauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */