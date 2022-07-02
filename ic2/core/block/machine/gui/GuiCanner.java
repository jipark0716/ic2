/*     */ package ic2.core.block.machine.gui;
/*     */ import com.google.common.base.Supplier;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.machine.container.ContainerCanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*     */ import ic2.core.gui.CustomButton;
/*     */ import ic2.core.gui.CycleHandler;
/*     */ import ic2.core.gui.EnergyGauge;
/*     */ import ic2.core.gui.GuiElement;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.IEnableHandler;
/*     */ import ic2.core.gui.INumericValueHandler;
/*     */ import ic2.core.gui.RecipeButton;
/*     */ import ic2.core.gui.TankGauge;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiCanner extends GuiIC2<ContainerCanner> {
/*     */   public GuiCanner(ContainerCanner container) {
/*  27 */     super((ContainerBase)container, 184);
/*     */     
/*  29 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 62, (TileEntityBlock)container.base));
/*  30 */     CycleHandler cycleHandler = new CycleHandler(176, 18, 226, 32, 14, true, 4, new INumericValueHandler()
/*     */         {
/*     */           public int getValue() {
/*  33 */             return ((TileEntityCanner)((ContainerCanner)GuiCanner.this.container).base).getMode().ordinal();
/*     */           }
/*     */ 
/*     */           
/*     */           public void onChange(int value) {
/*  38 */             ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)((ContainerCanner)GuiCanner.this.container).base, 0 + value);
/*     */           }
/*     */         });
/*  41 */     addElement((new CustomButton(this, 63, 81, 50, 14, (IOverlaySupplier)cycleHandler, texture, (IClickHandler)cycleHandler))
/*  42 */         .withTooltip(new Supplier<String>()
/*     */           {
/*     */             public String get() {
/*  45 */               switch (((TileEntityCanner)((ContainerCanner)GuiCanner.this.container).base).getMode()) { case BottleSolid:
/*  46 */                   return "ic2.Canner.gui.switch.BottleSolid";
/*  47 */                 case EmptyLiquid: return "ic2.Canner.gui.switch.EmptyLiquid";
/*  48 */                 case BottleLiquid: return "ic2.Canner.gui.switch.BottleLiquid";
/*  49 */                 case EnrichLiquid: return "ic2.Canner.gui.switch.EnrichLiquid"; }
/*  50 */                return null;
/*     */             }
/*     */           }));
/*     */     
/*  54 */     addElement((new CustomButton(this, 77, 64, 22, 13, createEventSender(TileEntityCanner.eventSwapTanks)))
/*  55 */         .withTooltip("ic2.Canner.gui.switchTanks"));
/*  56 */     addElement((GuiElement)TankGauge.createNormal(this, 39, 42, (IFluidTank)((TileEntityCanner)container.base).getInputTank()));
/*  57 */     addElement((GuiElement)TankGauge.createNormal(this, 117, 42, (IFluidTank)((TileEntityCanner)container.base).getOutputTank()));
/*     */     
/*  59 */     if (RecipeButton.canUse()) {
/*  60 */       for (TileEntityCanner.Mode mode : TileEntityCanner.Mode.values) {
/*  61 */         addElement((new RecipeButton(this, 74, 22, 23, 14, new String[] { "canner_" + mode })).withEnableHandler(new IEnableHandler()
/*     */               {
/*     */                 public boolean isEnabled() {
/*  64 */                   return (((TileEntityCanner)((ContainerCanner)GuiCanner.this.container).base).getMode() == mode);
/*     */                 }
/*     */               }));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/*  73 */     super.func_146976_a(f, x, y);
/*     */     
/*  75 */     bindTexture();
/*     */     
/*  77 */     switch (((TileEntityCanner)((ContainerCanner)this.container).base).getMode()) {
/*     */       case BottleSolid:
/*  79 */         drawTexturedRect(59.0D, 53.0D, 9.0D, 18.0D, 3.0D, 4.0D);
/*  80 */         drawTexturedRect(99.0D, 53.0D, 18.0D, 23.0D, 3.0D, 4.0D);
/*     */         break;
/*     */       case EmptyLiquid:
/*  83 */         drawTexturedRect(71.0D, 43.0D, 26.0D, 18.0D, 196.0D, 0.0D);
/*  84 */         drawTexturedRect(59.0D, 53.0D, 9.0D, 18.0D, 3.0D, 4.0D);
/*     */         break;
/*     */       
/*     */       case BottleLiquid:
/*  88 */         drawTexturedRect(99.0D, 53.0D, 18.0D, 23.0D, 3.0D, 4.0D);
/*  89 */         drawTexturedRect(71.0D, 43.0D, 26.0D, 18.0D, 196.0D, 0.0D);
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     int progressSize = Math.round(((TileEntityCanner)((ContainerCanner)this.container).base).getProgress() * 23.0F);
/*     */     
/*  98 */     if (progressSize > 0) {
/*  99 */       drawTexturedRect(74.0D, 22.0D, progressSize, 14.0D, 233.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getTexture() {
/* 105 */     return texture;
/*     */   }
/*     */   
/* 108 */   public static final ResourceLocation texture = new ResourceLocation("ic2", "textures/gui/GUICanner.png");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */