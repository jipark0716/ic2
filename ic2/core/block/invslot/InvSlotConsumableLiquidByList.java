/*    */ package ic2.core.block.invslot;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ 
/*    */ public class InvSlotConsumableLiquidByList extends InvSlotConsumableLiquid {
/*    */   private final Set<Fluid> acceptedFluids;
/*    */   
/*    */   public InvSlotConsumableLiquidByList(IInventorySlotHolder<?> base1, String name1, int count, Fluid... fluidlist) {
/* 13 */     super(base1, name1, count);
/*    */     
/* 15 */     this.acceptedFluids = new HashSet<>(Arrays.asList(fluidlist));
/*    */   }
/*    */   
/*    */   public InvSlotConsumableLiquidByList(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, InvSlotConsumableLiquid.OpType opType, Fluid... fluidlist) {
/* 19 */     super(base1, name1, access1, count, preferredSide1, opType);
/*    */     
/* 21 */     this.acceptedFluids = new HashSet<>(Arrays.asList(fluidlist));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean acceptsLiquid(Fluid fluid) {
/* 26 */     return this.acceptedFluids.contains(fluid);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<Fluid> getPossibleFluids() {
/* 31 */     return this.acceptedFluids;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableLiquidByList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */