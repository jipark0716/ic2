/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import java.util.Arrays;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class InvSlotConsumableLiquidByTank extends InvSlotConsumableLiquid {
/*    */   public final IFluidTank tank;
/*    */   
/*    */   public InvSlotConsumableLiquidByTank(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, InvSlotConsumableLiquid.OpType opType, IFluidTank tank1) {
/* 13 */     super(base1, name1, access1, count, preferredSide1, opType);
/*    */     
/* 15 */     this.tank = tank1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean acceptsLiquid(Fluid fluid) {
/* 20 */     FluidStack fs = this.tank.getFluid();
/*    */     
/* 22 */     return (fs == null || fs.getFluid() == fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<Fluid> getPossibleFluids() {
/* 27 */     FluidStack fs = this.tank.getFluid();
/*    */     
/* 29 */     if (fs == null) {
/* 30 */       return null;
/*    */     }
/* 32 */     return Arrays.asList(new Fluid[] { fs.getFluid() });
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableLiquidByTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */