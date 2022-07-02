/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
/*     */ import ic2.api.recipe.IFillFluidContainerRecipeManager;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.api.util.FluidContainerOutputMode;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*     */ import ic2.core.block.machine.container.ContainerFluidBottler;
/*     */ import ic2.core.block.machine.gui.GuiFluidBottler;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityFluidBottler
/*     */   extends TileEntityStandardMachine<Void, Object, Object> {
/*     */   public final InvSlotConsumableLiquid drainInputSlot;
/*     */   public final InvSlotConsumableLiquid fillInputSlot;
/*     */   
/*     */   public TileEntityFluidBottler() {
/*  40 */     super(2, 100, 1);
/*     */     
/*  42 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  43 */     this.fluidTank = (FluidTank)this.fluids.addTank("fluidTank", 8000);
/*     */ 
/*     */     
/*  46 */     this.drainInputSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "drainInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.fluidTank);
/*     */     
/*  48 */     this.fillInputSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "fillInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.fluidTank);
/*     */   }
/*     */   
/*     */   @GuiSynced
/*     */   public final FluidTank fluidTank;
/*     */   
/*     */   protected Collection<ItemStack> getOutput(Object output) {
/*  55 */     if (output instanceof IEmptyFluidContainerRecipeManager.Output) {
/*  56 */       return ((IEmptyFluidContainerRecipeManager.Output)output).container;
/*     */     }
/*  58 */     return super.getOutput(output);
/*     */   }
/*     */   
/*     */   protected final Fluids fluids;
/*     */   
/*     */   public void operateOnce(MachineRecipeResult<Void, Object, Object> result, Collection<ItemStack> processResult) {
/*  64 */     if (result.getOutput() instanceof IEmptyFluidContainerRecipeManager.Output) {
/*  65 */       this.drainInputSlot.put((ItemStack)result.getAdjustedInput());
/*  66 */       FluidStack fs = ((IEmptyFluidContainerRecipeManager.Output)result.getOutput()).fluid;
/*  67 */       this.fluidTank.fill(fs, true);
/*     */     } else {
/*  69 */       IFillFluidContainerRecipeManager.Input adjInput = (IFillFluidContainerRecipeManager.Input)result.getAdjustedInput();
/*  70 */       this.fillInputSlot.put(adjInput.container);
/*  71 */       this.fluidTank.drain((adjInput.fluid == null) ? this.fluidTank.getFluidAmount() : (this.fluidTank.getFluidAmount() - adjInput.fluid.amount), true);
/*     */     } 
/*     */     
/*  74 */     this.outputSlot.add(processResult);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MachineRecipeResult<Void, Object, Object> getOutput() {
/*  80 */     MachineRecipeResult<Void, IEmptyFluidContainerRecipeManager.Output, ItemStack> emptyRes = Recipes.emptyFluidContainer.apply(this.drainInputSlot
/*  81 */         .get(), 
/*  82 */         (this.fluidTank.getFluid() == null) ? null : this.fluidTank.getFluid().getFluid(), FluidContainerOutputMode.EmptyFullToOutput, false);
/*     */ 
/*     */ 
/*     */     
/*  86 */     if (emptyRes != null && ((IEmptyFluidContainerRecipeManager.Output)emptyRes.getOutput()).fluid.amount <= this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount() && this.outputSlot.canAdd(((IEmptyFluidContainerRecipeManager.Output)emptyRes.getOutput()).container)) {
/*  87 */       return (MachineRecipeResult)emptyRes;
/*     */     }
/*     */     
/*  90 */     MachineRecipeResult<Void, Collection<ItemStack>, IFillFluidContainerRecipeManager.Input> fillRes = Recipes.fillFluidContainer.apply(new IFillFluidContainerRecipeManager.Input(this.fillInputSlot
/*  91 */           .get(), this.fluidTank.getFluid()), FluidContainerOutputMode.EmptyFullToOutput, false);
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (fillRes != null && this.outputSlot.canAdd((Collection)fillRes.getOutput())) {
/*  96 */       return (MachineRecipeResult)fillRes;
/*     */     }
/*     */     
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityFluidBottler> getGuiContainer(EntityPlayer player) {
/* 104 */     return (ContainerBase<TileEntityFluidBottler>)new ContainerFluidBottler(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 110 */     return (GuiScreen)new GuiFluidBottler(new ContainerFluidBottler(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 120 */     return EnumSet.of(UpgradableProperty.Processing, new UpgradableProperty[] { UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing });
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityFluidBottler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */