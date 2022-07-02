/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.ref.TeBlock.Delegated;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Delegated(current = TileEntityElectricMFE.class, old = TileEntityElectricMFE.TileEntityElectricClassicMFE.class)
/*    */ public class TileEntityElectricMFE
/*    */   extends TileEntityElectricBlock
/*    */ {
/*    */   @Delegated(current = TileEntityElectricMFE.class, old = TileEntityElectricClassicMFE.class)
/*    */   public static class TileEntityElectricClassicMFE
/*    */     extends TileEntityElectricBlock
/*    */   {
/*    */     public TileEntityElectricClassicMFE() {
/* 18 */       super(2, 128, 600000);
/*    */ 
/*    */       
/* 21 */       this.chargeSlot.setTier(3);
/* 22 */       this.dischargeSlot.setTier(3);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Class<? extends TileEntityElectricBlock> delegate() {
/* 27 */     return IC2.version.isClassic() ? (Class)TileEntityElectricClassicMFE.class : (Class)TileEntityElectricMFE.class;
/*    */   }
/*    */   
/*    */   public TileEntityElectricMFE() {
/* 31 */     super(3, 512, 4000000);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityElectricMFE.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */