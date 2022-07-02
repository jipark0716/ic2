/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.GuiIC2;
/*    */ 
/*    */ public class CustomGauge extends Gauge<CustomGauge> {
/*    */   public static CustomGauge asFuel(GuiIC2<?> gui, int x, int y, IGaugeRatioProvider provider) {
/*  7 */     return new CustomGauge(gui, x, y, provider, Gauge.GaugeStyle.Fuel.properties);
/*    */   }
/*    */   private final IGaugeRatioProvider provider;
/*    */   public static CustomGauge create(GuiIC2<?> gui, int x, int y, IGaugeRatioProvider provider, Gauge.GaugeStyle style) {
/* 11 */     return new CustomGauge(gui, x, y, provider, style.properties);
/*    */   }
/*    */   
/*    */   public CustomGauge(GuiIC2<?> gui, int x, int y, IGaugeRatioProvider provider, Gauge.GaugeProperties properties) {
/* 15 */     super(gui, x, y, properties);
/*    */     
/* 17 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getRatio() {
/* 22 */     return this.provider.getRatio();
/*    */   }
/*    */   
/*    */   public static interface IGaugeRatioProvider {
/*    */     double getRatio();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\CustomGauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */