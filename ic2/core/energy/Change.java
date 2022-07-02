/*    */ package ic2.core.energy;
/*    */ 
/*    */ class Change {
/*    */   Node node;
/*    */   final EnumFacing dir;
/*    */   
/*    */   Change(Node node, EnumFacing dir, double amount, double voltage) {
/*  8 */     this.node = node;
/*  9 */     this.dir = dir;
/*    */     
/* 11 */     setAmount(amount);
/* 12 */     setVoltage(voltage);
/*    */   }
/*    */   private double amount; private double voltage;
/*    */   
/*    */   public String toString() {
/* 17 */     return this.node + "@" + this.dir + " " + this.amount + " EU / " + this.voltage + " V";
/*    */   }
/*    */   
/*    */   double getAmount() {
/* 21 */     return this.amount;
/*    */   }
/*    */   
/*    */   void setAmount(double amount) {
/* 25 */     double intAmount = Math.rint(amount);
/* 26 */     if (Math.abs(amount - intAmount) < 0.001D) amount = intAmount;
/*    */     
/* 28 */     assert !Double.isInfinite(amount) && !Double.isNaN(amount);
/*    */     
/* 30 */     this.amount = amount;
/*    */   }
/*    */   
/*    */   double getVoltage() {
/* 34 */     return this.voltage;
/*    */   }
/*    */   
/*    */   private void setVoltage(double voltage) {
/* 38 */     double intVoltage = Math.rint(this.amount);
/* 39 */     if (Math.abs(voltage - intVoltage) < 0.001D) voltage = intVoltage;
/*    */     
/* 41 */     assert !Double.isInfinite(voltage) && !Double.isNaN(voltage);
/*    */     
/* 43 */     this.voltage = voltage;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\Change.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */