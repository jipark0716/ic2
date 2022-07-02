/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.api.recipe.ILiquidAcceptManager;
/*     */ import ic2.api.recipe.ILiquidHeatExchangerManager;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.LiquidHeatExchangerManager;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumable;
/*     */ import ic2.core.block.invslot.InvSlotConsumableItemStack;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.machine.container.ContainerLiquidHeatExchanger;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityLiquidHeatExchanger extends TileEntityHeatSourceInventory implements IHasGui, IUpgradableBlock {
/*     */   private boolean newActive;
/*     */   public final FluidTank inputTank;
/*     */   public final FluidTank outputTank;
/*     */   public final InvSlotConsumable heatexchangerslots;
/*     */   public final InvSlotOutput hotoutputSlot;
/*     */   
/*     */   public TileEntityLiquidHeatExchanger() {
/*  50 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*     */     
/*  52 */     this.inputTank = (FluidTank)this.fluids.addTankInsert("inputTank", 2000, Fluids.fluidPredicate((ILiquidAcceptManager)Recipes.liquidCooldownManager));
/*  53 */     this.outputTank = (FluidTank)this.fluids.addTankExtract("outputTank", 2000);
/*     */     
/*  55 */     this.heatexchangerslots = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "heatExchanger", 10, new ItemStack[] { ItemName.crafting.getItemStack((Enum)CraftingItemType.heat_conductor) });
/*  56 */     this.heatexchangerslots.setStackSizeLimit(1);
/*     */     
/*  58 */     this.hotoutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "hotOutputSlot", 1);
/*  59 */     this.cooloutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "outputSlot", 1);
/*     */     
/*  61 */     this.hotfluidinputSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByManager((IInventorySlotHolder)this, "hotFluidInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (ILiquidAcceptManager)Recipes.liquidCooldownManager);
/*  62 */     this.coolfluidinputSlot = (InvSlotConsumableLiquid)new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "coolFluidOutput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
/*     */     
/*  64 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 3);
/*     */     
/*  66 */     this.newActive = false;
/*     */   }
/*     */   public final InvSlotOutput cooloutputSlot; public final InvSlotConsumableLiquid hotfluidinputSlot; public final InvSlotConsumableLiquid coolfluidinputSlot; public final InvSlotUpgrade upgradeSlot; protected final Fluids fluids;
/*     */   public static void init() {
/*  70 */     Recipes.liquidCooldownManager = (ILiquidHeatExchangerManager)new LiquidHeatExchangerManager(false);
/*  71 */     Recipes.liquidHeatupManager = (ILiquidHeatExchangerManager)new LiquidHeatExchangerManager(true);
/*  72 */     addCooldownRecipe("lava", FluidName.pahoehoe_lava.getName(), Math.round(20.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/fluidconversion/heatExchangerLava")));
/*  73 */     addBiDiRecipe(FluidName.hot_coolant.getName(), FluidName.coolant.getName(), Math.round(20.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/fluidconversion/heatExchangerHotCoolant")));
/*  74 */     addHeatupRecipe(FluidName.hot_water.getName(), "water", Math.round(1.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/fluidconversion/heatExchangerWater")));
/*     */   }
/*     */   
/*     */   public static void addBiDiRecipe(String hotFluid, String coldFluid, int huPerMB) {
/*  78 */     addHeatupRecipe(hotFluid, coldFluid, huPerMB);
/*  79 */     addCooldownRecipe(hotFluid, coldFluid, huPerMB);
/*     */   }
/*     */   
/*     */   public static void addHeatupRecipe(String hotFluid, String coldFluid, int huPerMB) {
/*  83 */     Recipes.liquidHeatupManager.addFluid(coldFluid, hotFluid, huPerMB);
/*     */   }
/*     */   
/*     */   public static void addCooldownRecipe(String hotFluid, String coldFluid, int huPerMB) {
/*  87 */     Recipes.liquidCooldownManager.addFluid(hotFluid, coldFluid, huPerMB);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  92 */     super.updateEntityServer();
/*     */     
/*  94 */     this.hotfluidinputSlot.processIntoTank((IFluidTank)this.inputTank, this.hotoutputSlot);
/*  95 */     this.coolfluidinputSlot.processFromTank((IFluidTank)this.outputTank, this.cooloutputSlot);
/*     */     
/*  97 */     this.newActive = (this.HeatBuffer > 0);
/*  98 */     if (getActive() != this.newActive) setActive(this.newActive);
/*     */     
/* 100 */     this.upgradeSlot.tick();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityLiquidHeatExchanger> getGuiContainer(EntityPlayer player) {
/* 106 */     return (ContainerBase<TileEntityLiquidHeatExchanger>)new ContainerLiquidHeatExchanger(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 112 */     return (GuiScreen)new GuiLiquidHeatExchanger(new ContainerLiquidHeatExchanger(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */   
/*     */   public int gaugeLiquidScaled(int i, int tank) {
/* 121 */     switch (tank) {
/*     */       case 0:
/* 123 */         if (this.inputTank.getFluidAmount() <= 0) return 0; 
/* 124 */         return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
/*     */       case 1:
/* 126 */         if (this.outputTank.getFluidAmount() <= 0) return 0; 
/* 127 */         return this.outputTank.getFluidAmount() * i / this.outputTank.getCapacity();
/*     */     } 
/*     */     
/* 130 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeatEmittedPerTick() {
/* 135 */     int count = 0;
/*     */     
/* 137 */     for (int i = 0; i < this.heatexchangerslots.size(); i++) {
/* 138 */       if (!this.heatexchangerslots.isEmpty(i)) count += 10;
/*     */     
/*     */     } 
/* 141 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int fillHeatBuffer(int bufferspace) {
/* 146 */     if (bufferspace > 0) {
/* 147 */       int AmountHotCoolant = this.inputTank.getFluidAmount();
/* 148 */       int OutputTankFreeCap = this.outputTank.getCapacity() - this.outputTank.getFluidAmount();
/* 149 */       FluidStack draincoolant = null;
/*     */       
/* 151 */       if (OutputTankFreeCap == 0 || AmountHotCoolant == 0) return 0;
/*     */       
/* 153 */       Fluid fluidInputTank = this.inputTank.getFluid().getFluid();
/* 154 */       Fluid fluidOutput = null;
/* 155 */       int hUper1mb = 0;
/*     */       
/* 157 */       if (Recipes.liquidCooldownManager.acceptsFluid(fluidInputTank)) {
/* 158 */         ILiquidHeatExchangerManager.HeatExchangeProperty hep = Recipes.liquidCooldownManager.getHeatExchangeProperty(fluidInputTank);
/* 159 */         fluidOutput = hep.outputFluid;
/* 160 */         hUper1mb = hep.huPerMB;
/*     */       } 
/*     */       
/* 163 */       if (fluidOutput == null) return 0;
/*     */       
/* 165 */       if (this.outputTank.getFluidAmount() > 0 && 
/* 166 */         !this.outputTank.getFluid().getFluid().equals(fluidOutput)) return 0;
/*     */ 
/*     */       
/* 169 */       int mbtofillheatbuffer = bufferspace / hUper1mb;
/*     */       
/* 171 */       if (OutputTankFreeCap >= AmountHotCoolant) {
/* 172 */         if (mbtofillheatbuffer <= AmountHotCoolant) {
/* 173 */           draincoolant = this.inputTank.drainInternal(mbtofillheatbuffer, false);
/*     */         } else {
/* 175 */           draincoolant = this.inputTank.drainInternal(AmountHotCoolant, false);
/*     */         }
/*     */       
/* 178 */       } else if (mbtofillheatbuffer <= OutputTankFreeCap) {
/* 179 */         draincoolant = this.inputTank.drainInternal(mbtofillheatbuffer, false);
/*     */       } else {
/* 181 */         draincoolant = this.inputTank.drainInternal(OutputTankFreeCap * 20, false);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 186 */       if (draincoolant != null) {
/* 187 */         this.inputTank.drainInternal(draincoolant.amount, true);
/* 188 */         this.outputTank.fillInternal(new FluidStack(fluidOutput, draincoolant.amount), true);
/*     */         
/* 190 */         return draincoolant.amount * hUper1mb;
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidTank getInputTank() {
/* 200 */     return this.inputTank;
/*     */   }
/*     */   
/*     */   public FluidTank getOutputTank() {
/* 204 */     return this.outputTank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 211 */     return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 218 */     return 40.0D;
/*     */   }
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 222 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityLiquidHeatExchanger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */