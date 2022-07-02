/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.machine.container.ContainerFluidRegulator;
/*     */ import ic2.core.block.machine.gui.GuiFluidRegulator;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityFluidRegulator extends TileEntityElectricMachine implements IHasGui, INetworkClientTileEntityEventListener {
/*     */   private int mode;
/*     */   private int updateTicker;
/*     */   private int outputmb;
/*     */   private boolean newActive;
/*     */   
/*     */   public TileEntityFluidRegulator() {
/*  39 */     super(10000, 4);
/*     */     
/*  41 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  42 */     this.fluidTank = this.fluids.addTank("fluidTank", 10000, InvSlot.Access.NONE);
/*     */     
/*  44 */     this.wasserinputSlot = new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "wasserinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (IFluidTank)this.fluidTank);
/*  45 */     this.wasseroutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "wasseroutputSlot", 1);
/*     */     
/*  47 */     this.newActive = false;
/*  48 */     this.outputmb = 0;
/*  49 */     this.mode = 0;
/*     */     
/*  51 */     this.updateTicker = IC2.random.nextInt(getTickRate());
/*     */   }
/*     */   public final InvSlotOutput wasseroutputSlot; public final InvSlotConsumableLiquidByTank wasserinputSlot; @GuiSynced
/*     */   protected final Fluids.InternalFluidTank fluidTank; protected final Fluids fluids;
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  56 */     super.func_145839_a(nbt);
/*  57 */     this.outputmb = nbt.func_74762_e("outputmb");
/*  58 */     this.mode = nbt.func_74762_e("mode");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  63 */     super.func_189515_b(nbt);
/*  64 */     nbt.func_74768_a("outputmb", this.outputmb);
/*  65 */     nbt.func_74768_a("mode", this.mode);
/*     */     
/*  67 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  72 */     super.onLoaded();
/*  73 */     updateConnectivity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacing(EnumFacing side) {
/*  78 */     super.setFacing(side);
/*  79 */     updateConnectivity();
/*     */   }
/*     */   
/*     */   private void updateConnectivity() {
/*  83 */     this.fluids.changeConnectivity(this.fluidTank, EnumSet.complementOf((EnumSet)EnumSet.of(getFacing())), Collections.emptySet());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  89 */     super.updateEntityServer();
/*     */     
/*  91 */     this.wasserinputSlot.processIntoTank((IFluidTank)this.fluidTank, this.wasseroutputSlot);
/*     */     
/*  93 */     if (this.updateTicker++ % getTickRate() != 0 && this.mode == 0)
/*     */       return; 
/*  95 */     this.newActive = work();
/*  96 */     if (getActive() != this.newActive) setActive(this.newActive);
/*     */   
/*     */   }
/*     */   
/*     */   private boolean work() {
/* 101 */     if (this.outputmb == 0) return false; 
/* 102 */     if (this.energy.getEnergy() < 10.0D) return false; 
/* 103 */     if (this.fluidTank.getFluidAmount() <= 0) return false;
/*     */     
/* 105 */     EnumFacing dir = getFacing();
/* 106 */     TileEntity te = func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(dir));
/* 107 */     EnumFacing side = dir.func_176734_d();
/*     */     
/* 109 */     if (LiquidUtil.isFluidTile(te, side)) {
/* 110 */       int amount = LiquidUtil.fillTile(te, side, this.fluidTank.drainInternal(this.outputmb, false), false);
/*     */       
/* 112 */       if (amount > 0) {
/* 113 */         this.fluidTank.drainInternal(this.outputmb, true);
/* 114 */         this.energy.useEnergy(10.0D);
/* 115 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 125 */     if (event == 1001 || event == 1002) {
/* 126 */       if (event == 1001 && this.mode == 0) this.mode = 1; 
/* 127 */       if (event == 1002 && this.mode == 1) this.mode = 0; 
/*     */       return;
/*     */     } 
/* 130 */     this.outputmb += event;
/* 131 */     if (this.outputmb > 1000) this.outputmb = 1000; 
/* 132 */     if (this.outputmb < 0) this.outputmb = 0; 
/*     */   }
/*     */   
/*     */   public int getTickRate() {
/* 136 */     return 20;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityFluidRegulator> getGuiContainer(EntityPlayer player) {
/* 142 */     return (ContainerBase<TileEntityFluidRegulator>)new ContainerFluidRegulator(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 148 */     return (GuiScreen)new GuiFluidRegulator(new ContainerFluidRegulator(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public int gaugeLiquidScaled(int i, int tank) {
/* 157 */     switch (tank) {
/*     */       case 0:
/* 159 */         if (this.fluidTank.getFluidAmount() <= 0) return 0; 
/* 160 */         return this.fluidTank.getFluidAmount() * i / this.fluidTank.getCapacity();
/*     */     } 
/* 162 */     return 0;
/*     */   }
/*     */   
/*     */   public int getoutputmb() {
/* 166 */     return this.outputmb;
/*     */   }
/*     */   
/*     */   public String getmodegui() {
/* 170 */     switch (this.mode) {
/*     */       case 0:
/* 172 */         return Localization.translate("ic2.generic.text.sec");
/*     */       case 1:
/* 174 */         return Localization.translate("ic2.generic.text.tick");
/*     */     } 
/* 176 */     return "";
/*     */   }
/*     */   
/*     */   public FluidTank getFluidTank() {
/* 180 */     return (FluidTank)this.fluidTank;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityFluidRegulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */