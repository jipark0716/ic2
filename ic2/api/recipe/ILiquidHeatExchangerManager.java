/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ILiquidHeatExchangerManager
/*    */   extends ILiquidAcceptManager
/*    */ {
/*    */   void addFluid(String paramString1, String paramString2, int paramInt);
/*    */   
/*    */   HeatExchangeProperty getHeatExchangeProperty(Fluid paramFluid);
/*    */   
/*    */   Map<String, HeatExchangeProperty> getHeatExchangeProperties();
/*    */   
/*    */   ILiquidAcceptManager getSingleDirectionLiquidManager();
/*    */   
/*    */   public static class HeatExchangeProperty
/*    */   {
/*    */     public final Fluid outputFluid;
/*    */     public final int huPerMB;
/*    */     
/*    */     public HeatExchangeProperty(Fluid outputFluid, int huPerMB) {
/* 31 */       this.outputFluid = outputFluid;
/* 32 */       this.huPerMB = huPerMB;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\ILiquidHeatExchangerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */