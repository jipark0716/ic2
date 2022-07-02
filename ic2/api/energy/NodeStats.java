/*    */ package ic2.api.energy;public class NodeStats {
/*    */   protected double energyIn;
/*    */   
/*    */   public NodeStats(double energyIn, double energyOut, double voltage) {
/*  5 */     this.energyIn = energyIn;
/*  6 */     this.energyOut = energyOut;
/*  7 */     this.voltage = voltage;
/*    */   }
/*    */   protected double energyOut; protected double voltage;
/*    */   public double getEnergyIn() {
/* 11 */     return this.energyIn;
/*    */   }
/*    */   
/*    */   public double getEnergyOut() {
/* 15 */     return this.energyOut;
/*    */   }
/*    */   
/*    */   public double getVoltage() {
/* 19 */     return this.voltage;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\NodeStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */