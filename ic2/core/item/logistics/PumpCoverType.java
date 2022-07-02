/*    */ package ic2.core.item.logistics;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ 
/*    */ public enum PumpCoverType implements IIdProvider {
/*  6 */   pump_lv(640, 65280),
/*  7 */   pump_mv(2560, 16776960);
/*    */   
/*    */   public final int transferRate;
/*    */   public final int color;
/*    */   
/*    */   PumpCoverType(int transferRate, int color) {
/* 13 */     this.transferRate = transferRate;
/* 14 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 19 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 24 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColor() {
/* 29 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModelName() {
/* 34 */     return "pump";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\logistics\PumpCoverType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */