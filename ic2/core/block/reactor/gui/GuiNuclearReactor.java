/*     */ package ic2.core.block.reactor.gui;
/*     */ 
/*     */ import com.google.common.base.Supplier;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.block.reactor.container.ContainerNuclearReactor;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
/*     */ import ic2.core.gui.Area;
/*     */ import ic2.core.gui.Gauge;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IEnableHandler;
/*     */ import ic2.core.gui.LinkedGauge;
/*     */ import ic2.core.gui.TankGauge;
/*     */ import ic2.core.gui.Text;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.gui.dynamic.TextProvider;
/*     */ import ic2.core.init.Localization;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiNuclearReactor
/*     */   extends GuiIC2<ContainerNuclearReactor>
/*     */ {
/*     */   public GuiNuclearReactor(ContainerNuclearReactor container) {
/*  30 */     super((ContainerBase)container, 212, 243);
/*     */     
/*  32 */     IEnableHandler enableHandler = new IEnableHandler()
/*     */       {
/*     */         public boolean isEnabled() {
/*  35 */           return ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)GuiNuclearReactor.this.container).base).isFluidCooled();
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  40 */     addElement(TankGauge.createBorderless(this, 10, 54, (IFluidTank)((TileEntityNuclearReactorElectric)container.base).getinputtank(), true).withEnableHandler(enableHandler));
/*  41 */     addElement(TankGauge.createBorderless(this, 190, 54, (IFluidTank)((TileEntityNuclearReactorElectric)container.base).getoutputtank(), false).withEnableHandler(enableHandler));
/*  42 */     addElement((new LinkedGauge(this, 7, 136, (IGuiValueProvider)container.base, "heat", (Gauge.IGaugeStyle)Gauge.GaugeStyle.HeatNuclearReactor)).withTooltip(new Supplier<String>()
/*     */           {
/*     */             public String get() {
/*  45 */               return Localization.translate("ic2.NuclearReactor.gui.info.temp", new Object[] { Double.valueOf(((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)GuiNuclearReactor.access$100(this.this$0)).base).getGuiValue("heat") * 100.0D) });
/*     */             }
/*     */           }));
/*  48 */     addElement((GuiElement)Text.create(this, 107, 136, 200, 13, TextProvider.of(new Supplier<String>()
/*     */             {
/*     */               public String get() {
/*  51 */                 if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)GuiNuclearReactor.this.container).base).isFluidCooled()) {
/*  52 */                   return Localization.translate("ic2.NuclearReactor.gui.info.HUoutput", new Object[] { Integer.valueOf(((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)GuiNuclearReactor.access$300(this.this$0)).base).EmitHeat) });
/*     */                 }
/*  54 */                 return Localization.translate("ic2.NuclearReactor.gui.info.EUoutput", new Object[] { Long.valueOf(Math.round(((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)GuiNuclearReactor.access$400(this.this$0)).base).getOfferedEnergy())) });
/*     */               }
/*     */             }), 5752026, false, 4, 0, false, true));
/*     */     
/*  58 */     addElement((new Area(this, 5, 160, 18, 18))
/*  59 */         .withTooltip(new Supplier<String>()
/*     */           {
/*     */             public String get() {
/*  62 */               if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)GuiNuclearReactor.this.container).base).isFluidCooled()) {
/*  63 */                 return "ic2.NuclearReactor.gui.mode.fluid";
/*     */               }
/*  65 */               return "ic2.NuclearReactor.gui.mode.electric";
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
/*  73 */     super.func_146976_a(partialTicks, mouseX, mouseY);
/*     */     
/*  75 */     int size = ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).getReactorSize();
/*  76 */     int startX = 26;
/*  77 */     int startY = 25;
/*     */     
/*  79 */     bindTexture();
/*  80 */     for (int y = 0; y < 6; y++) {
/*  81 */       for (int x = size; x < 9; x++) {
/*  82 */         drawTexturedRect((26 + x * 18), (25 + y * 18), 16.0D, 16.0D, 213.0D, 1.0D);
/*     */       }
/*     */     } 
/*     */     
/*  86 */     if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).isFluidCooled()) {
/*  87 */       int heat = ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).gaugeHeatScaled(160);
/*     */       
/*  89 */       drawTexturedRect((186 - heat), 23.0D, 0.0D, 243.0D, heat, 2.0D);
/*  90 */       drawTexturedRect((186 - heat), 41.0D, 0.0D, 243.0D, heat, 2.0D);
/*  91 */       drawTexturedRect((186 - heat), 59.0D, 0.0D, 243.0D, heat, 2.0D);
/*  92 */       drawTexturedRect((186 - heat), 77.0D, 0.0D, 243.0D, heat, 2.0D);
/*  93 */       drawTexturedRect((186 - heat), 95.0D, 0.0D, 243.0D, heat, 2.0D);
/*  94 */       drawTexturedRect((186 - heat), 113.0D, 0.0D, 243.0D, heat, 2.0D);
/*  95 */       drawTexturedRect((186 - heat), 131.0D, 0.0D, 243.0D, heat, 2.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getTexture() {
/* 101 */     if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).isFluidCooled()) {
/* 102 */       return backgroundFluid;
/*     */     }
/* 104 */     return background;
/*     */   }
/*     */ 
/*     */   
/* 108 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUINuclearReactor.png");
/* 109 */   private static final ResourceLocation backgroundFluid = new ResourceLocation("ic2", "textures/gui/GUINuclearReactorFluid.png");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\gui\GuiNuclearReactor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */