/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.api.recipe.ILiquidAcceptManager;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ public class InvSlotConsumableLiquidByManager extends InvSlotConsumableLiquid {
/*    */   private final ILiquidAcceptManager manager;
/*    */   
/*    */   public InvSlotConsumableLiquidByManager(IInventorySlotHolder<?> base1, String name1, int count, ILiquidAcceptManager manager1) {
/* 11 */     super(base1, name1, count);
/* 12 */     this.manager = manager1;
/*    */   }
/*    */   
/*    */   public InvSlotConsumableLiquidByManager(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, InvSlotConsumableLiquid.OpType opType, ILiquidAcceptManager manager1) {
/* 16 */     super(base1, name1, access1, count, preferredSide1, opType);
/* 17 */     this.manager = manager1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean acceptsLiquid(Fluid fluid) {
/* 22 */     return this.manager.acceptsFluid(fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<Fluid> getPossibleFluids() {
/* 27 */     return this.manager.getAcceptedFluids();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableLiquidByManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */