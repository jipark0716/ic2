/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IFluidHeatManager
/*    */   extends ILiquidAcceptManager
/*    */ {
/*    */   void addFluid(String paramString, int paramInt1, int paramInt2);
/*    */   
/*    */   BurnProperty getBurnProperty(Fluid paramFluid);
/*    */   
/*    */   Map<String, BurnProperty> getBurnProperties();
/*    */   
/*    */   public static class BurnProperty
/*    */   {
/*    */     public final int amount;
/*    */     public final int heat;
/*    */     
/*    */     public BurnProperty(int amount1, int heat1) {
/* 25 */       this.amount = amount1;
/* 26 */       this.heat = heat1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\IFluidHeatManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */