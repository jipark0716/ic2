/*     */ package ic2.core.block.heatgenerator.tileentity;
/*     */ 
/*     */ import ic2.api.recipe.IFluidHeatManager;
/*     */ import ic2.api.recipe.ILiquidAcceptManager;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.FluidHeatManager;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityHeatSourceInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.heatgenerator.container.ContainerFluidHeatGenerator;
/*     */ import ic2.core.block.heatgenerator.gui.GuiFluidHeatGenerator;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityFluidHeatGenerator extends TileEntityHeatSourceInventory implements IHasGui {
/*  36 */   public final InvSlotConsumableLiquid fluidSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByManager((IInventorySlotHolder)this, "fluidSlot", 1, (ILiquidAcceptManager)Recipes.fluidHeatGenerator);
/*  37 */   public final InvSlotOutput outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*     */   
/*  39 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*     */   @GuiSynced
/*  41 */   protected final FluidTank fluidTank = (FluidTank)this.fluids.addTankInsert("fluidTank", 10000, Fluids.fluidPredicate((ILiquidAcceptManager)Recipes.semiFluidGenerator));
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  46 */     super.updateEntityServer();
/*     */     
/*  48 */     boolean needsInvUpdate = false;
/*     */     
/*  50 */     if (needsFluid()) {
/*  51 */       needsInvUpdate = gainFuel();
/*     */     }
/*     */     
/*  54 */     if (needsInvUpdate) {
/*  55 */       func_70296_d();
/*     */     }
/*     */     
/*  58 */     if (getActive() != this.newActive) setActive(this.newActive);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean isConverting() {
/*  63 */     return (getTankAmount() > 0 && this.HeatBuffer < getMaxHeatEmittedPerTick());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  68 */     Recipes.fluidHeatGenerator = (IFluidHeatManager)new FluidHeatManager();
/*     */     
/*  70 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidOil") > 0.0F) {
/*  71 */       addFuel("oil", 10, Math.round(16.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidOil")));
/*     */     }
/*  73 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidFuel") > 0.0F) {
/*  74 */       addFuel("fuel", 5, Math.round(64.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidFuel")));
/*     */     }
/*  76 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiomass") > 0.0F) {
/*  77 */       addFuel("biomass", 20, Math.round(16.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBiomass")));
/*     */     }
/*  79 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBioethanol") > 0.0F) {
/*  80 */       addFuel("bio.ethanol", 10, Math.round(32.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBioethanol")));
/*     */     }
/*  82 */     if (ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/semiFluidBiogas") > 0.0F)
/*  83 */       addFuel("ic2biogas", 10, Math.round(32.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/semiFluidBiogas"))); 
/*     */   }
/*     */   
/*     */   public static void addFuel(String fluidName, int amount, int heat) {
/*  87 */     Recipes.fluidHeatGenerator.addFluid(fluidName, amount, heat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  93 */     super.func_145839_a(nbttagcompound);
/*  94 */     this.fluidTank.readFromNBT(nbttagcompound.func_74775_l("fluidTank"));
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  99 */     super.func_189515_b(nbt);
/*     */     
/* 101 */     NBTTagCompound fluidTankTag = new NBTTagCompound();
/* 102 */     this.fluidTank.writeToNBT(fluidTankTag);
/* 103 */     nbt.func_74782_a("fluidTank", (NBTBase)fluidTankTag);
/*     */     
/* 105 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int fillHeatBuffer(int maxAmount) {
/* 111 */     if (isConverting()) {
/* 112 */       if (this.ticker >= 19) {
/* 113 */         getFluidTank().drain(this.burnAmount, true);
/* 114 */         this.ticker = 0;
/*     */       } else {
/* 116 */         this.ticker = (short)(this.ticker + 1);
/*     */       } 
/* 118 */       this.newActive = true;
/* 119 */       return this.production;
/*     */     } 
/* 121 */     this.newActive = false;
/* 122 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHeatEmittedPerTick() {
/* 128 */     return calcHeatProduction();
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityFluidHeatGenerator> getGuiContainer(EntityPlayer player) {
/* 133 */     return (ContainerBase<TileEntityFluidHeatGenerator>)new ContainerFluidHeatGenerator(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 139 */     return (GuiScreen)new GuiFluidHeatGenerator(new ContainerFluidHeatGenerator(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected int calcHeatProduction() {
/* 149 */     if (this.fluidTank.getFluid() != null && 
/* 150 */       getFluidfromTank() != null) {
/* 151 */       IFluidHeatManager.BurnProperty property = Recipes.fluidHeatGenerator.getBurnProperty(getFluidfromTank());
/*     */       
/* 153 */       if (property != null) {
/* 154 */         return this.production = property.heat;
/*     */       }
/*     */     } 
/*     */     
/* 158 */     return this.production = 0;
/*     */   }
/*     */   
/*     */   protected void calcBurnAmount() {
/* 162 */     if (getFluidfromTank() != null) {
/* 163 */       IFluidHeatManager.BurnProperty property = Recipes.fluidHeatGenerator.getBurnProperty(getFluidfromTank());
/*     */       
/* 165 */       if (property != null) {
/* 166 */         this.burnAmount = property.amount;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 171 */     this.burnAmount = 0;
/*     */   }
/*     */   
/*     */   public FluidTank getFluidTank() {
/* 175 */     return this.fluidTank;
/*     */   }
/*     */   
/*     */   public FluidStack getFluidStackfromTank() {
/* 179 */     return getFluidTank().getFluid();
/*     */   }
/*     */   
/*     */   public Fluid getFluidfromTank() {
/* 183 */     return getFluidStackfromTank().getFluid();
/*     */   }
/*     */   
/*     */   public int getTankAmount() {
/* 187 */     return getFluidTank().getFluidAmount();
/*     */   }
/*     */   
/*     */   public int gaugeLiquidScaled(int i) {
/* 191 */     if (getFluidTank().getFluidAmount() <= 0) return 0;
/*     */     
/* 193 */     return getFluidTank().getFluidAmount() * i / getFluidTank().getCapacity();
/*     */   }
/*     */   
/*     */   public boolean needsFluid() {
/* 197 */     return (getFluidTank().getFluidAmount() <= getFluidTank().getCapacity());
/*     */   }
/*     */   
/*     */   protected boolean gainFuel() {
/* 201 */     if (this.fluidTank.getFluid() != null) {
/* 202 */       calcHeatProduction();
/* 203 */       calcBurnAmount();
/*     */     } 
/*     */     
/* 206 */     return this.fluidSlot.processIntoTank((IFluidTank)this.fluidTank, this.outputSlot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 215 */   private short ticker = 0;
/* 216 */   protected int burnAmount = 0;
/* 217 */   protected int production = 0;
/*     */   boolean newActive = false;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\tileentity\TileEntityFluidHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */