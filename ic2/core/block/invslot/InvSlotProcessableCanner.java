/*     */ package ic2.core.block.invslot;
/*     */ 
/*     */ import ic2.api.recipe.ICannerBottleRecipeManager;
/*     */ import ic2.api.recipe.ICannerEnrichRecipeManager;
/*     */ import ic2.api.recipe.IFillFluidContainerRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.util.FluidContainerOutputMode;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ 
/*     */ public class InvSlotProcessableCanner
/*     */   extends InvSlotProcessable<Object, Object, Object> {
/*     */   public InvSlotProcessableCanner(TileEntityCanner base1, String name1, int count) {
/*  20 */     super((IInventorySlotHolder<?>)base1, name1, count, (IMachineRecipeManager<Object, Object, Object>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean accepts(ItemStack stack) {
/*  25 */     switch (((TileEntityCanner)this.base).getMode()) {
/*     */       case BottleSolid:
/*     */       case EnrichLiquid:
/*  28 */         return super.accepts(stack);
/*     */       case BottleLiquid:
/*     */       case EmptyLiquid:
/*  31 */         return false;
/*     */     } 
/*     */     assert false;
/*  34 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void consume(MachineRecipeResult<Object, Object, Object> result) {
/*  40 */     super.consume(result);
/*     */     
/*  42 */     ItemStack containerStack = ((TileEntityCanner)this.base).canInputSlot.get();
/*     */     
/*  44 */     if (StackUtil.isEmpty(containerStack)) {
/*  45 */       ((TileEntityCanner)this.base).canInputSlot.clear();
/*     */     }
/*     */     
/*  48 */     FluidStack fluid = ((TileEntityCanner)this.base).inputTank.getFluid();
/*     */     
/*  50 */     if (fluid != null && fluid.amount <= 0) {
/*  51 */       ((TileEntityCanner)this.base).inputTank.setFluid(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Object getInput(ItemStack fill) {
/*  57 */     ItemStack container = ((TileEntityCanner)this.base).canInputSlot.get();
/*     */     
/*  59 */     switch (((TileEntityCanner)this.base).getMode()) { case BottleSolid:
/*  60 */         return new ICannerBottleRecipeManager.RawInput(container, fill);
/*  61 */       case BottleLiquid: return new IFillFluidContainerRecipeManager.Input(container, getTankFluid());
/*  62 */       case EmptyLiquid: return container;
/*  63 */       case EnrichLiquid: return new ICannerEnrichRecipeManager.RawInput(getTankFluid(), fill); }
/*  64 */      assert false; return null;
/*     */   }
/*     */   protected void setInput(Object rawInput) {
/*     */     ICannerBottleRecipeManager.RawInput rawInput1;
/*     */     IFillFluidContainerRecipeManager.Input input1;
/*     */     ICannerEnrichRecipeManager.RawInput input;
/*  70 */     InvSlotConsumableCanner canInputSlot = ((TileEntityCanner)this.base).canInputSlot;
/*  71 */     FluidTank tank = ((TileEntityCanner)this.base).inputTank;
/*     */     
/*  73 */     switch (((TileEntityCanner)this.base).getMode()) {
/*     */       case BottleSolid:
/*  75 */         rawInput1 = (ICannerBottleRecipeManager.RawInput)rawInput;
/*  76 */         canInputSlot.put(rawInput1.container);
/*  77 */         put(rawInput1.fill);
/*     */         return;
/*     */       
/*     */       case BottleLiquid:
/*  81 */         input1 = (IFillFluidContainerRecipeManager.Input)rawInput;
/*  82 */         canInputSlot.put(input1.container);
/*  83 */         tank.drain((input1.fluid == null) ? tank.getFluidAmount() : (tank.getFluidAmount() - input1.fluid.amount), true);
/*     */         return;
/*     */       
/*     */       case EmptyLiquid:
/*  87 */         canInputSlot.put((ItemStack)rawInput);
/*     */         return;
/*     */       case EnrichLiquid:
/*  90 */         input = (ICannerEnrichRecipeManager.RawInput)rawInput;
/*  91 */         put(input.additive);
/*  92 */         tank.drain((input.fluid == null) ? tank.getFluidAmount() : (tank.getFluidAmount() - input.fluid.amount), true);
/*     */         return;
/*     */     } 
/*     */     assert false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean allowEmptyInput() {
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected MachineRecipeResult<Object, Object, Object> getOutputFor(Object input, boolean forAccept) {
/* 106 */     return getOutput(input, forAccept);
/*     */   }
/*     */ 
/*     */   
/*     */   protected MachineRecipeResult<Object, Object, Object> getOutput(Object input, boolean forAccept) {
/* 111 */     switch (((TileEntityCanner)this.base).getMode()) { case BottleSolid:
/* 112 */         return Recipes.cannerBottle.apply(input, forAccept);
/* 113 */       case BottleLiquid: return Recipes.fillFluidContainer.apply((IFillFluidContainerRecipeManager.Input)input, FluidContainerOutputMode.EmptyFullToOutput, forAccept);
/* 114 */       case EmptyLiquid: return Recipes.emptyFluidContainer.apply((ItemStack)input, (getTankFluid() == null) ? null : getTankFluid().getFluid(), FluidContainerOutputMode.EmptyFullToOutput, forAccept);
/* 115 */       case EnrichLiquid: return Recipes.cannerEnrich.apply(input, forAccept); }
/* 116 */      assert false; return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private FluidStack getTankFluid() {
/* 121 */     return ((TileEntityCanner)this.base).inputTank.getFluid();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\invslot\InvSlotProcessableCanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */