/*    */ package ic2.core.gui;
/*    */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*    */ 
/*    */ public class LinkedGauge extends Gauge<LinkedGauge> {
/*    */   private final IGuiValueProvider provider;
/*    */   
/*    */   public LinkedGauge(GuiIC2<?> gui, int x, int y, IGuiValueProvider provider, String name, Gauge.IGaugeStyle style) {
/*  8 */     super(gui, x, y, style.getProperties());
/*    */     
/* 10 */     this.provider = provider;
/* 11 */     this.name = name;
/*    */   }
/*    */   protected final String name;
/*    */   
/*    */   protected double getRatio() {
/* 16 */     return this.provider.getGuiValue(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\LinkedGauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */