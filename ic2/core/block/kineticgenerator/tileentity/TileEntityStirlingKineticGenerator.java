/*     */ package ic2.core.block.kineticgenerator.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IHeatSource;
/*     */ import ic2.api.energy.tile.IKineticSource;
/*     */ import ic2.api.recipe.ILiquidHeatExchangerManager;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.kineticgenerator.container.ContainerStirlingKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.gui.GuiStirlingKineticGenerator;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityStirlingKineticGenerator
/*     */   extends TileEntityInventory
/*     */   implements IKineticSource, IUpgradableBlock, IHasGui
/*     */ {
/*     */   public FluidTank inputTank;
/*     */   public FluidTank outputTank;
/*     */   public InvSlotOutput hotoutputSlot;
/*     */   public InvSlotOutput cooloutputSlot;
/*     */   public InvSlotConsumableLiquidByTank hotfluidinputSlot;
/*     */   public InvSlotConsumableLiquidByManager coolfluidinputSlot;
/*     */   public InvSlotUpgrade upgradeSlot;
/*  54 */   private int heatbuffer = 0;
/*     */   
/*     */   private final int maxHeatbuffer;
/*     */   
/*     */   private int kUBuffer;
/*     */   
/*     */   private final int maxkUBuffer;
/*     */   
/*     */   private boolean newActive;
/*     */   
/*     */   private int liquidHeatStored;
/*     */   
/*     */   protected final Fluids fluids;
/*     */   private static final int PARTS_KU = 3;
/*     */   private static final int PARTS_LIQUID = 1;
/*     */   private static final int PARTS_TOTAL = 4;
/*     */   
/*     */   public TileEntityStirlingKineticGenerator() {
/*  72 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  73 */     this.inputTank = (FluidTank)this.fluids.addTankInsert("inputTank", 2000, Fluids.fluidPredicate(Recipes.liquidHeatupManager.getSingleDirectionLiquidManager()));
/*  74 */     this.outputTank = (FluidTank)this.fluids.addTankExtract("outputTank", 2000);
/*     */     
/*  76 */     this.hotoutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "hotOutputSlot", 1);
/*  77 */     this.cooloutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "outputSlot", 1);
/*     */     
/*  79 */     this.coolfluidinputSlot = new InvSlotConsumableLiquidByManager((IInventorySlotHolder)this, "coolfluidinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, Recipes.liquidHeatupManager.getSingleDirectionLiquidManager());
/*  80 */     this.hotfluidinputSlot = new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "hotfluidoutputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
/*     */     
/*  82 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 3);
/*  83 */     this.maxHeatbuffer = 1000;
/*  84 */     this.maxkUBuffer = 2000;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  89 */     super.func_145839_a(nbt);
/*     */     
/*  91 */     this.inputTank.readFromNBT(nbt.func_74775_l("inputTank"));
/*  92 */     this.outputTank.readFromNBT(nbt.func_74775_l("outputTank"));
/*  93 */     this.heatbuffer = nbt.func_74762_e("heatbuffer");
/*  94 */     this.kUBuffer = nbt.func_74762_e("kubuffer");
/*  95 */     this.liquidHeatStored = nbt.func_74762_e("liquidHeatStored");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 100 */     super.func_189515_b(nbt);
/*     */     
/* 102 */     NBTTagCompound inputTankTag = new NBTTagCompound();
/* 103 */     this.inputTank.writeToNBT(inputTankTag);
/* 104 */     nbt.func_74782_a("inputTank", (NBTBase)inputTankTag);
/*     */     
/* 106 */     NBTTagCompound outputTankTag = new NBTTagCompound();
/* 107 */     this.outputTank.writeToNBT(outputTankTag);
/* 108 */     nbt.func_74782_a("outputTank", (NBTBase)outputTankTag);
/* 109 */     nbt.func_74768_a("heatbuffer", this.heatbuffer);
/* 110 */     nbt.func_74768_a("kUBuffer", this.kUBuffer);
/* 111 */     nbt.func_74768_a("liquidHeatStored", this.liquidHeatStored);
/*     */     
/* 113 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 118 */     super.updateEntityServer();
/*     */     
/* 120 */     this.coolfluidinputSlot.processIntoTank((IFluidTank)this.inputTank, this.cooloutputSlot);
/* 121 */     this.hotfluidinputSlot.processFromTank((IFluidTank)this.outputTank, this.hotoutputSlot);
/*     */     
/* 123 */     if (this.heatbuffer < this.maxHeatbuffer) {
/* 124 */       this.heatbuffer += drawHu(this.maxHeatbuffer - this.heatbuffer);
/*     */     }
/*     */     
/* 127 */     this.newActive = false;
/*     */     
/* 129 */     if (this.inputTank.getFluidAmount() > 0 && this.outputTank.getFluidAmount() < this.outputTank.getCapacity() && Recipes.liquidHeatupManager.getSingleDirectionLiquidManager().acceptsFluid(this.inputTank.getFluid().getFluid()) && this.kUBuffer < this.maxkUBuffer) {
/* 130 */       ILiquidHeatExchangerManager.HeatExchangeProperty property = Recipes.liquidHeatupManager.getHeatExchangeProperty(this.inputTank.getFluid().getFluid());
/* 131 */       if (this.outputTank.getFluid() == null || (new FluidStack(property.outputFluid, 0)).isFluidEqual(this.outputTank.getFluid())) {
/* 132 */         int heatbufferToUse = this.heatbuffer / 4;
/* 133 */         heatbufferToUse = Math.min(heatbufferToUse, (Math.min(this.outputTank.getCapacity() - this.outputTank.getFluidAmount(), this.inputTank.getFluidAmount()) * property.huPerMB - this.liquidHeatStored) / 1);
/* 134 */         heatbufferToUse = Math.min(heatbufferToUse, (this.maxkUBuffer - this.kUBuffer) / 3);
/*     */         
/* 136 */         if (heatbufferToUse > 0) {
/* 137 */           this.kUBuffer += heatbufferToUse * 3 * 4;
/* 138 */           this.liquidHeatStored += heatbufferToUse * 1;
/* 139 */           this.heatbuffer -= heatbufferToUse * 4;
/* 140 */           this.newActive = true;
/*     */         } 
/* 142 */         if (this.liquidHeatStored >= property.huPerMB) {
/* 143 */           int mbToConvert = this.liquidHeatStored / property.huPerMB;
/* 144 */           mbToConvert = (this.inputTank.drainInternal(mbToConvert, false)).amount;
/* 145 */           mbToConvert = this.outputTank.fillInternal(new FluidStack(property.outputFluid, mbToConvert), false);
/* 146 */           this.liquidHeatStored -= mbToConvert * property.huPerMB;
/* 147 */           this.inputTank.drainInternal(mbToConvert, true);
/* 148 */           this.outputTank.fillInternal(new FluidStack(property.outputFluid, mbToConvert), true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     if (getActive() != this.newActive) setActive(this.newActive);
/*     */     
/* 155 */     this.upgradeSlot.tick();
/*     */   }
/*     */   
/*     */   private int drawHu(int amount) {
/* 159 */     if (amount <= 0) return 0;
/*     */     
/* 161 */     World world = func_145831_w();
/* 162 */     int tmpAmount = amount;
/*     */     
/* 164 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 165 */       if (dir != getFacing()) {
/*     */         
/* 167 */         TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */         
/* 169 */         if (te instanceof IHeatSource) {
/* 170 */           IHeatSource hs = (IHeatSource)te;
/*     */           
/* 172 */           int request = hs.drawHeat(dir.func_176734_d(), tmpAmount, true);
/* 173 */           if (request > 0) {
/* 174 */             tmpAmount -= hs.drawHeat(dir.func_176734_d(), request, false);
/*     */             
/* 176 */             if (tmpAmount <= 0)
/*     */               break; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 181 */     }  return amount - tmpAmount;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxrequestkineticenergyTick(EnumFacing directionFrom) {
/* 186 */     return Math.min(this.kUBuffer, getConnectionBandwidth(directionFrom));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectionBandwidth(EnumFacing side) {
/* 191 */     if (side != getFacing()) return 0;
/*     */     
/* 193 */     return this.maxkUBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int requestkineticenergy(EnumFacing directionFrom, int requestkineticenergy) {
/* 198 */     return drawKineticEnergy(directionFrom, requestkineticenergy, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawKineticEnergy(EnumFacing side, int request, boolean simulate) {
/* 203 */     if (side != getFacing()) return 0; 
/* 204 */     if (request > this.kUBuffer) request = this.kUBuffer;
/*     */     
/* 206 */     if (!simulate) this.kUBuffer -= request;
/*     */     
/* 208 */     return request;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 213 */     return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 220 */     return 40.0D;
/*     */   }
/*     */   public boolean useEnergy(double amount) {
/* 223 */     return true;
/*     */   }
/*     */   public FluidTank getInputTank() {
/* 226 */     return this.inputTank;
/*     */   }
/*     */   
/*     */   public FluidTank getOutputTank() {
/* 230 */     return this.outputTank;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 235 */     return (ContainerBase<?>)new ContainerStirlingKineticGenerator(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 241 */     return (GuiScreen)new GuiStirlingKineticGenerator(new ContainerStirlingKineticGenerator(player, this));
/*     */   }
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\tileentity\TileEntityStirlingKineticGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */