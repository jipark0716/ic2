/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.gui.CustomButton;
/*     */ import ic2.core.gui.IClickHandler;
/*     */ import ic2.core.gui.MouseButton;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class GuiToolMeter
/*     */   extends GuiIC2<ContainerMeter>
/*     */ {
/*     */   public GuiToolMeter(ContainerMeter container) {
/*  23 */     super((ContainerBase)container, 217);
/*     */     
/*  25 */     addElement((new CustomButton(this, 112, 55, 20, 20, createModeSetter(ContainerMeter.Mode.EnergyIn)))
/*  26 */         .withTooltip("ic2.itemToolMEter.mode.switch\nic2.itemToolMEter.mode.EnergyIn"));
/*  27 */     addElement((new CustomButton(this, 132, 55, 20, 20, createModeSetter(ContainerMeter.Mode.EnergyOut)))
/*  28 */         .withTooltip("ic2.itemToolMEter.mode.switch\nic2.itemToolMEter.mode.EnergyOut"));
/*  29 */     addElement((new CustomButton(this, 112, 75, 20, 20, createModeSetter(ContainerMeter.Mode.EnergyGain)))
/*  30 */         .withTooltip("ic2.itemToolMEter.mode.switch\nic2.itemToolMEter.mode.EnergyGain"));
/*  31 */     addElement((new CustomButton(this, 132, 75, 20, 20, createModeSetter(ContainerMeter.Mode.Voltage)))
/*  32 */         .withTooltip("ic2.itemToolMEter.mode.switch\nic2.itemToolMEter.mode.Voltage"));
/*     */   }
/*     */   
/*     */   private IClickHandler createModeSetter(final ContainerMeter.Mode mode) {
/*  36 */     return new IClickHandler()
/*     */       {
/*     */         public void onClick(MouseButton button) {
/*  39 */           ((ContainerMeter)GuiToolMeter.this.container).setMode(mode);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int i, int j, int k) throws IOException {
/*  46 */     super.func_73864_a(i, j, k);
/*     */     
/*  48 */     int xMin = (this.field_146294_l - this.field_146999_f) / 2;
/*  49 */     int yMin = (this.field_146295_m - this.field_147000_g) / 2;
/*     */     
/*  51 */     int x = i - xMin;
/*  52 */     int y = j - yMin;
/*     */     
/*  54 */     if (x >= 26 && y >= 111 && x <= 83 && y <= 123) {
/*  55 */       ((ContainerMeter)this.container).reset();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/*  61 */     super.drawForegroundLayer(mouseX, mouseY);
/*     */     
/*  63 */     String unit = (((ContainerMeter)this.container).getMode() == ContainerMeter.Mode.Voltage) ? "ic2.generic.text.v" : "ic2.generic.text.EUt";
/*  64 */     unit = Localization.translate(unit);
/*     */     
/*  66 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.mode"), 115, 43, 2157374);
/*     */     
/*  68 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.avg"), 15, 41, 2157374);
/*  69 */     this.field_146289_q.func_78276_b("" + Util.toSiString(((ContainerMeter)this.container).getResultAvg(), 6) + unit, 15, 51, 2157374);
/*     */     
/*  71 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.max/min"), 15, 64, 2157374);
/*  72 */     this.field_146289_q.func_78276_b("" + Util.toSiString(((ContainerMeter)this.container).getResultMax(), 6) + unit, 15, 74, 2157374);
/*  73 */     this.field_146289_q.func_78276_b("" + Util.toSiString(((ContainerMeter)this.container).getResultMin(), 6) + unit, 15, 84, 2157374);
/*     */ 
/*     */     
/*  76 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.cycle", new Object[] { Integer.valueOf(((ContainerMeter)this.container).getResultCount() / 20) }), 15, 100, 2157374);
/*  77 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.mode.reset"), 39, 114, 2157374);
/*     */     
/*  79 */     switch (((ContainerMeter)this.container).getMode()) {
/*     */       case EnergyIn:
/*  81 */         this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.mode.EnergyIn"), 105, 100, 2157374);
/*     */         break;
/*     */       case EnergyOut:
/*  84 */         this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.mode.EnergyOut"), 105, 100, 2157374);
/*     */         break;
/*     */       case EnergyGain:
/*  87 */         this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.mode.EnergyGain"), 105, 100, 2157374);
/*     */         break;
/*     */       case Voltage:
/*  90 */         this.field_146289_q.func_78276_b(Localization.translate("ic2.itemToolMEter.mode.Voltage"), 105, 100, 2157374);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146976_a(float f, int x, int y) {
/*  97 */     super.func_146976_a(f, x, y);
/*     */     
/*  99 */     bindTexture();
/*     */     
/* 101 */     switch (((ContainerMeter)this.container).getMode()) {
/*     */       case EnergyIn:
/* 103 */         drawTexturedRect(112.0D, 55.0D, 40.0D, 40.0D, 176.0D, 0.0D);
/*     */         break;
/*     */       case EnergyOut:
/* 106 */         drawTexturedRect(112.0D, 55.0D, 40.0D, 40.0D, 176.0D, 40.0D);
/*     */         break;
/*     */       case EnergyGain:
/* 109 */         drawTexturedRect(112.0D, 55.0D, 40.0D, 40.0D, 176.0D, 120.0D);
/*     */         break;
/*     */       case Voltage:
/* 112 */         drawTexturedRect(112.0D, 55.0D, 40.0D, 40.0D, 176.0D, 80.0D);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getTexture() {
/* 119 */     return new ResourceLocation("ic2", "textures/gui/GUIToolEUMeter.png");
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\GuiToolMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */