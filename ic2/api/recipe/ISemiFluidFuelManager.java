/*    */ package ic2.api.recipe;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ISemiFluidFuelManager
/*    */   extends ILiquidAcceptManager
/*    */ {
/*    */   void addFluid(String paramString, int paramInt, double paramDouble);
/*    */   
/*    */   BurnProperty getBurnProperty(Fluid paramFluid);
/*    */   
/*    */   Map<String, BurnProperty> getBurnProperties();
/*    */   
/*    */   public static final class BurnProperty
/*    */   {
/*    */     public final int amount;
/*    */     public final double power;
/*    */     
/*    */     public BurnProperty(int amount, double power) {
/* 25 */       this.amount = amount;
/* 26 */       this.power = power;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\recipe\ISemiFluidFuelManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */