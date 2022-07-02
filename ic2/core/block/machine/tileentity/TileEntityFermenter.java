/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.energy.tile.IHeatSource;
/*     */ import ic2.api.recipe.IFermenterRecipeManager;
/*     */ import ic2.api.recipe.ILiquidAcceptManager;
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
/*     */ import ic2.core.block.machine.container.ContainerFermenter;
/*     */ import ic2.core.block.machine.gui.GuiFermenter;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.CropResItemType;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.recipe.FermenterRecipeManager;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityFermenter
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, IGuiValueProvider, IUpgradableBlock
/*     */ {
/*  50 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*  51 */   private final FluidTank outputTank = (FluidTank)this.fluids.addTankExtract("output", 2000);
/*  52 */   private final FluidTank inputTank = (FluidTank)this.fluids.addTankInsert("input", 10000, Fluids.fluidPredicate((ILiquidAcceptManager)Recipes.fermenter));
/*     */   
/*  54 */   public final InvSlotOutput fluidInputCellOutSlot = new InvSlotOutput((IInventorySlotHolder)this, "biomassOutput", 1);
/*  55 */   public final InvSlotOutput fluidOutputCellOutSlot = new InvSlotOutput((IInventorySlotHolder)this, "biogassOutput", 1);
/*  56 */   public final InvSlotOutput fertiliserSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*  57 */   public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 2);
/*     */   
/*  59 */   public final InvSlotConsumableLiquidByTank fluidOutputCellInSlot = new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "biogasInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
/*  60 */   public final InvSlotConsumableLiquidByManager fluidInputCellInSlot = new InvSlotConsumableLiquidByManager((IInventorySlotHolder)this, "biomassInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, (ILiquidAcceptManager)Recipes.fermenter);
/*     */ 
/*     */   
/*     */   public static void init() {
/*  64 */     Recipes.fermenter = (IFermenterRecipeManager)new FermenterRecipeManager();
/*     */     
/*  66 */     Recipes.fermenter.addRecipe(FluidName.biomass.getName(), ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/need_amount_biomass_per_run"), 
/*  67 */         ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/hU_per_run"), FluidName.biogas.getName(), ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/output_amount_biogas_per_run"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  72 */     super.func_145839_a(nbttagcompound);
/*     */     
/*  74 */     this.inputTank.readFromNBT(nbttagcompound.func_74775_l("inputTank"));
/*  75 */     this.outputTank.readFromNBT(nbttagcompound.func_74775_l("outputTank"));
/*     */     
/*  77 */     this.progress = nbttagcompound.func_74762_e("progress");
/*  78 */     this.heatBuffer = nbttagcompound.func_74762_e("heatBuffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  83 */     super.func_189515_b(nbt);
/*     */     
/*  85 */     nbt.func_74782_a("inputTank", (NBTBase)this.inputTank.writeToNBT(new NBTTagCompound()));
/*  86 */     nbt.func_74782_a("outputTank", (NBTBase)this.outputTank.writeToNBT(new NBTTagCompound()));
/*     */     
/*  88 */     nbt.func_74768_a("progress", this.progress);
/*  89 */     nbt.func_74768_a("heatBuffer", this.heatBuffer);
/*     */     
/*  91 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  97 */     super.updateEntityServer();
/*     */     
/*  99 */     this.fluidInputCellInSlot.processIntoTank((IFluidTank)this.inputTank, this.fluidInputCellOutSlot);
/* 100 */     this.fluidOutputCellInSlot.processFromTank((IFluidTank)this.outputTank, this.fluidOutputCellOutSlot);
/*     */     
/* 102 */     this.newActive = work();
/* 103 */     if (getActive() != this.newActive) setActive(this.newActive);
/*     */     
/* 105 */     this.upgradeSlot.tick();
/*     */   }
/*     */   
/*     */   private boolean work() {
/* 109 */     if (this.progress >= this.maxProgress) {
/* 110 */       this.fertiliserSlot.add(ItemName.crop_res.getItemStack((Enum)CropResItemType.fertilizer));
/* 111 */       this.progress = 0;
/*     */     } 
/*     */     
/* 114 */     EnumFacing dir = getFacing();
/* 115 */     TileEntity te = func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */     
/* 117 */     if (te instanceof IHeatSource && this.inputTank.getFluid() != null) {
/* 118 */       IFermenterRecipeManager.FermentationProperty fp = Recipes.fermenter.getFermentationInformation(this.inputTank.getFluid().getFluid());
/* 119 */       if (fp != null && this.inputTank.getFluidAmount() >= fp.inputAmount && fp.outputAmount <= this.outputTank.getCapacity() - this.outputTank.getFluidAmount()) {
/* 120 */         this.heatBuffer += ((IHeatSource)te).drawHeat(dir.func_176734_d(), 100, false);
/*     */         
/* 122 */         if (this.heatBuffer >= fp.heat) {
/* 123 */           this.heatBuffer -= fp.heat;
/* 124 */           this.inputTank.drainInternal(fp.inputAmount, true);
/* 125 */           this.outputTank.fillInternal(fp.getOutput(), true);
/* 126 */           this.progress += fp.inputAmount;
/*     */         } 
/* 128 */         return true;
/*     */       } 
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityFermenter> getGuiContainer(EntityPlayer player) {
/* 137 */     return (ContainerBase<TileEntityFermenter>)new ContainerFermenter(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 143 */     return (GuiScreen)new GuiFermenter(new ContainerFermenter(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 153 */     if ("heat".equals(name)) {
/* 154 */       if (this.heatBuffer == 0) {
/* 155 */         return 0.0D;
/*     */       }
/* 157 */       double maxHeatBuff = ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/hU_per_run");
/* 158 */       if (this.inputTank.getFluid() != null) {
/* 159 */         IFermenterRecipeManager.FermentationProperty fp = Recipes.fermenter.getFermentationInformation(this.inputTank.getFluid().getFluid());
/* 160 */         if (fp != null) maxHeatBuff = fp.heat; 
/*     */       } 
/* 162 */       return this.heatBuffer / maxHeatBuff;
/*     */     } 
/* 164 */     if ("progress".equals(name)) {
/* 165 */       return (this.progress == 0) ? 0.0D : (this.progress / this.maxProgress);
/*     */     }
/* 167 */     throw new IllegalArgumentException("Invalid GUI value: " + name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int gaugeLiquidScaled(int i, int tank) {
/* 172 */     switch (tank) {
/*     */       case 0:
/* 174 */         if (this.inputTank.getFluidAmount() <= 0) return 0; 
/* 175 */         return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
/*     */       case 1:
/* 177 */         if (this.outputTank.getFluidAmount() <= 0) return 0; 
/* 178 */         return this.outputTank.getFluidAmount() * i / this.outputTank.getCapacity();
/*     */     } 
/* 180 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidTank getInputTank() {
/* 185 */     return this.inputTank;
/*     */   }
/*     */   
/*     */   public FluidTank getOutputTank() {
/* 189 */     return this.outputTank;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 194 */     return 40.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 204 */     return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 226 */   private int heatBuffer = 0;
/* 227 */   public int progress = 0;
/* 228 */   private final int maxProgress = ConfigUtil.getInt(MainConfig.get(), "balance/fermenter/biomass_per_fertilizier");
/*     */   private boolean newActive = false;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityFermenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */