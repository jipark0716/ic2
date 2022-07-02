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
/*    */ public interface IHeatSource
/*    */ {
/*    */   @Deprecated
/*    */   int maxrequestHeatTick(EnumFacing paramEnumFacing);
/*    */   
/*    */   default int getConnectionBandwidth(EnumFacing side) {
/* 24 */     return maxrequestHeatTick(side);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   int requestHeat(EnumFacing paramEnumFacing, int paramInt);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int drawHeat(EnumFacing side, int request, boolean simulate) {
/* 41 */     return !simulate ? requestHeat(side, request) : maxrequestHeatTick(side);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\tile\IHeatSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */