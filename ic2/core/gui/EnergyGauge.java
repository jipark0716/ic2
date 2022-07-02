/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.Energy;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.util.Util;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class EnergyGauge
/*    */   extends Gauge<EnergyGauge>
/*    */ {
/*    */   private static final boolean useCleanEnergyValues = false;
/*    */   private final Energy energy;
/*    */   
/*    */   public static EnergyGauge asBar(GuiIC2<?> gui, int x, int y, TileEntityBlock te) {
/* 22 */     return new EnergyGauge(gui, x, y, te, EnergyGaugeStyle.Bar);
/*    */   }
/*    */   
/*    */   public static EnergyGauge asBolt(GuiIC2<?> gui, int x, int y, TileEntityBlock te) {
/* 26 */     return new EnergyGauge(gui, x, y, te, EnergyGaugeStyle.Bolt);
/*    */   }
/*    */   
/*    */   public EnergyGauge(GuiIC2<?> gui, int x, int y, TileEntityBlock te, EnergyGaugeStyle style) {
/* 30 */     super(gui, x, y, style.properties);
/*    */     
/* 32 */     this.energy = (Energy)te.getComponent(Energy.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<String> getToolTip() {
/* 37 */     List<String> ret = super.getToolTip();
/*    */     
/* 39 */     double amount = this.energy.getEnergy();
/* 40 */     double capacity = this.energy.getCapacity();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     ret.add(Util.toSiString(amount, 4) + '/' + 
/* 47 */         Util.toSiString(capacity, 4) + ' ' + 
/* 48 */         Localization.translate("ic2.generic.text.EU"));
/*    */     
/* 50 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getRatio() {
/* 55 */     return this.energy.getFillRatio();
/*    */   }
/*    */   
/*    */   public enum EnergyGaugeStyle {
/* 59 */     Bar((String)(new Gauge.GaugePropertyBuilder(132, 43, 24, 9, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-4, -11, 32, 32, 128, 0).build()),
/* 60 */     Bolt((String)(new Gauge.GaugePropertyBuilder(116, 65, 7, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(-4, -1, 16, 16, 96, 64).build()),
/* 61 */     StirlingBar((String)(new Gauge.GaugePropertyBuilder(176, 15, 58, 14, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/GUIStirlingGenerator.png")).withBackground(59, 33).build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 83 */     private static final Map<String, EnergyGaugeStyle> map = getMap();
/*    */     public final String name;
/*    */     public final Gauge.GaugeProperties properties;
/*    */     
/*    */     EnergyGaugeStyle(Gauge.GaugeProperties properties) {
/*    */       this.name = name().toLowerCase(Locale.ENGLISH);
/*    */       this.properties = properties;
/*    */     }
/*    */     
/*    */     public static EnergyGaugeStyle get(String name) {
/*    */       return map.get(name);
/*    */     }
/*    */     
/*    */     private static Map<String, EnergyGaugeStyle> getMap() {
/*    */       EnergyGaugeStyle[] values = values();
/*    */       Map<String, EnergyGaugeStyle> ret = new HashMap<>(values.length);
/*    */       for (EnergyGaugeStyle style : values)
/*    */         ret.put(style.name, style); 
/*    */       return ret;
/*    */     }
/*    */     
/*    */     static {
/*    */     
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\EnergyGauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */