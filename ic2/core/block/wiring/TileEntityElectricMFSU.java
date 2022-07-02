/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.ref.TeBlock.Delegated;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Delegated(current = TileEntityElectricMFSU.class, old = TileEntityElectricMFSU.TileEntityElectricClassicMFSU.class)
/*    */ public class TileEntityElectricMFSU
/*    */   extends TileEntityElectricBlock
/*    */ {
/*    */   @Delegated(current = TileEntityElectricMFSU.class, old = TileEntityElectricClassicMFSU.class)
/*    */   public static class TileEntityElectricClassicMFSU
/*    */     extends TileEntityElectricBlock
/*    */   {
/*    */     public TileEntityElectricClassicMFSU() {
/* 18 */       super(3, 512, 10000000);
/*    */ 
/*    */       
/* 21 */       this.chargeSlot.setTier(4);
/* 22 */       this.dischargeSlot.setTier(4);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Class<? extends TileEntityElectricBlock> delegate() {
/* 27 */     return IC2.version.isClassic() ? (Class)TileEntityElectricClassicMFSU.class : (Class)TileEntityElectricMFSU.class;
/*    */   }
/*    */   
/*    */   public TileEntityElectricMFSU() {
/* 31 */     super(4, 2048, 40000000);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityElectricMFSU.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */