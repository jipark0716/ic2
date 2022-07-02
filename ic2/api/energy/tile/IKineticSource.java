/*    */ package ic2.api.energy.tile;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
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
/*    */ public interface IKineticSource
/*    */ {
/*    */   @Deprecated
/*    */   int maxrequestkineticenergyTick(EnumFacing paramEnumFacing);
/*    */   
/*    */   default int getConnectionBandwidth(EnumFacing side) {
/* 24 */     return maxrequestkineticenergyTick(side);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   int requestkineticenergy(EnumFacing paramEnumFacing, int paramInt);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
/* 41 */     return !simulate ? requestkineticenergy(side, request) : maxrequestkineticenergyTick(side);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\tile\IKineticSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */