/*     */ package ic2.core.block.invslot;
/*     */ 
/*     */ import ic2.api.util.FluidContainerOutputMode;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ public class InvSlotConsumableLiquid
/*     */   extends InvSlotConsumable
/*     */ {
/*     */   private OpType opType;
/*     */   
/*     */   public InvSlotConsumableLiquid(IInventorySlotHolder<?> base1, String name1, int count) {
/*  22 */     this(base1, name1, InvSlot.Access.I, count, InvSlot.InvSide.TOP, OpType.Drain);
/*     */   }
/*     */   
/*     */   public InvSlotConsumableLiquid(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, OpType opType1) {
/*  26 */     super(base1, name1, access1, count, preferredSide1);
/*     */     
/*  28 */     this.opType = opType1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean accepts(ItemStack stack) {
/*  33 */     if (StackUtil.isEmpty(stack)) return false; 
/*  34 */     if (!LiquidUtil.isFluidContainer(stack)) return false;
/*     */     
/*  36 */     if (this.opType == OpType.Drain || this.opType == OpType.Both) {
/*  37 */       FluidStack containerFluid = null;
/*     */       
/*  39 */       if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
/*  40 */         ItemStack singleStack = StackUtil.copyWithSize(stack, 1);
/*  41 */         IFluidHandlerItem handler = (IFluidHandlerItem)singleStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
/*     */         
/*  43 */         if (handler != null) {
/*  44 */           containerFluid = handler.drain(2147483647, false);
/*     */         }
/*     */       } 
/*     */       
/*  48 */       if (containerFluid != null && containerFluid.amount > 0 && 
/*     */         
/*  50 */         acceptsLiquid(containerFluid.getFluid())) {
/*  51 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  55 */     if ((this.opType == OpType.Fill || this.opType == OpType.Both) && 
/*  56 */       LiquidUtil.isFillableFluidContainer(stack, getPossibleFluids())) return true;
/*     */ 
/*     */     
/*  59 */     return false;
/*     */   }
/*     */   
/*     */   public FluidStack drain(Fluid fluid, int maxAmount, MutableObject<ItemStack> output, boolean simulate) {
/*  63 */     output.setValue(null);
/*     */     
/*  65 */     if (fluid != null && !acceptsLiquid(fluid)) return null; 
/*  66 */     if (this.opType != OpType.Drain && this.opType != OpType.Both) return null;
/*     */     
/*  68 */     ItemStack stack = get();
/*  69 */     if (StackUtil.isEmpty(stack)) return null;
/*     */ 
/*     */     
/*  72 */     LiquidUtil.FluidOperationResult result = LiquidUtil.drainContainer(stack, fluid, maxAmount, FluidContainerOutputMode.EmptyFullToOutput);
/*  73 */     if (result == null) return null;
/*     */     
/*  75 */     if (fluid == null && 
/*  76 */       !acceptsLiquid(result.fluidChange.getFluid())) {
/*  77 */       return null;
/*     */     }
/*     */     
/*  80 */     output.setValue(result.extraOutput);
/*     */     
/*  82 */     if (!simulate) put(result.inPlaceOutput);
/*     */     
/*  84 */     return result.fluidChange;
/*     */   }
/*     */   
/*     */   public int fill(FluidStack fs, MutableObject<ItemStack> output, boolean simulate) {
/*  88 */     output.setValue(null);
/*     */     
/*  90 */     if (fs == null || fs.amount <= 0) return 0; 
/*  91 */     if (this.opType != OpType.Fill && this.opType != OpType.Both) return 0;
/*     */     
/*  93 */     ItemStack stack = get();
/*  94 */     if (StackUtil.isEmpty(stack)) return 0;
/*     */     
/*  96 */     LiquidUtil.FluidOperationResult result = LiquidUtil.fillContainer(stack, fs, FluidContainerOutputMode.EmptyFullToOutput);
/*  97 */     if (result == null) return 0;
/*     */     
/*  99 */     output.setValue(result.extraOutput);
/*     */     
/* 101 */     if (!simulate) put(result.inPlaceOutput);
/*     */     
/* 103 */     return result.fluidChange.amount;
/*     */   }
/*     */   
/*     */   public boolean transferToTank(IFluidTank tank, MutableObject<ItemStack> output, boolean simulate) {
/* 107 */     int space = tank.getCapacity();
/* 108 */     Fluid fluidRequired = null;
/*     */     
/* 110 */     FluidStack tankFluid = tank.getFluid();
/*     */     
/* 112 */     if (tankFluid != null) {
/* 113 */       space -= tankFluid.amount;
/* 114 */       fluidRequired = tankFluid.getFluid();
/*     */     } 
/*     */     
/* 117 */     FluidStack fluid = drain(fluidRequired, space, output, true);
/* 118 */     if (fluid == null) return false;
/*     */     
/* 120 */     int amount = tank.fill(fluid, !simulate);
/* 121 */     if (amount <= 0) return false;
/*     */     
/* 123 */     if (!simulate) drain(fluidRequired, amount, output, false);
/*     */     
/* 125 */     return true;
/*     */   }
/*     */   
/*     */   public boolean transferFromTank(IFluidTank tank, MutableObject<ItemStack> output, boolean simulate) {
/* 129 */     FluidStack tankFluid = tank.drain(tank.getFluidAmount(), false);
/* 130 */     if (tankFluid == null || tankFluid.amount <= 0) return false;
/*     */     
/* 132 */     int amount = fill(tankFluid, output, simulate);
/* 133 */     if (amount <= 0) return false;
/*     */     
/* 135 */     if (!simulate) tank.drain(amount, true);
/*     */     
/* 137 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processIntoTank(IFluidTank tank, InvSlotOutput outputSlot) {
/* 149 */     if (isEmpty()) return false;
/*     */     
/* 151 */     MutableObject<ItemStack> output = new MutableObject();
/* 152 */     boolean wasChange = false;
/*     */     
/* 154 */     if (transferToTank(tank, output, true) && (StackUtil.isEmpty((ItemStack)output.getValue()) || outputSlot.canAdd((ItemStack)output.getValue()))) {
/* 155 */       wasChange = transferToTank(tank, output, false);
/*     */       
/* 157 */       if (!StackUtil.isEmpty((ItemStack)output.getValue())) {
/* 158 */         outputSlot.add((ItemStack)output.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return wasChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processFromTank(IFluidTank tank, InvSlotOutput outputSlot) {
/* 174 */     if (isEmpty() || tank.getFluidAmount() <= 0) return false;
/*     */     
/* 176 */     MutableObject<ItemStack> output = new MutableObject();
/* 177 */     boolean wasChange = false;
/*     */     
/* 179 */     if (transferFromTank(tank, output, true) && (StackUtil.isEmpty((ItemStack)output.getValue()) || outputSlot.canAdd((ItemStack)output.getValue()))) {
/* 180 */       wasChange = transferFromTank(tank, output, false);
/*     */       
/* 182 */       if (!StackUtil.isEmpty((ItemStack)output.getValue())) {
/* 183 */         outputSlot.add((ItemStack)output.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 187 */     return wasChange;
/*     */   }
/*     */   
/*     */   public void setOpType(OpType opType1) {
/* 191 */     this.opType = opType1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean acceptsLiquid(Fluid fluid) {
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Iterable<Fluid> getPossibleFluids() {
/* 210 */     return null;
/*     */   }
/*     */   
/*     */   public enum OpType
/*     */   {
/* 215 */     Drain,
/* 216 */     Fill,
/* 217 */     Both,
/* 218 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotConsumableLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */