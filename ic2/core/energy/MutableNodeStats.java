/*    */ package ic2.core.energy;
/*    */ 
/*    */ import ic2.api.energy.NodeStats;
/*    */ 
/*    */ class MutableNodeStats extends NodeStats {
/*    */   protected MutableNodeStats() {
/*  7 */     super(0.0D, 0.0D, 0.0D);
/*    */   }
/*    */   
/*    */   protected void set(double energyIn, double energyOut, double voltage) {
/* 11 */     this.energyIn = energyIn;
/* 12 */     this.energyOut = energyOut;
/* 13 */     this.voltage = voltage;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\MutableNodeStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */