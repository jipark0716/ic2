/*     */ package ic2.core.block.machine.tileentity;
/*     */ import ic2.api.energy.tile.IHeatSource;
/*     */ import ic2.api.recipe.IBasicMachineRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.MachineRecipeResult;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotProcessableGeneric;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.gui.dynamic.DynamicContainer;
/*     */ import ic2.core.gui.dynamic.DynamicGui;
/*     */ import ic2.core.gui.dynamic.GuiParser;
/*     */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*     */ import ic2.core.item.type.IngotResourceType;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.recipe.BasicMachineRecipeManager;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityBlastFurnace extends TileEntityInventory implements IUpgradableBlock, IHasGui, IGuiValueProvider {
/*  51 */   public final InvSlotProcessableGeneric inputSlot = new InvSlotProcessableGeneric((IInventorySlotHolder)this, "input", 1, (IMachineRecipeManager)Recipes.blastfurnace);
/*  52 */   public final InvSlotOutput outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 2)
/*     */     {
/*     */       public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
/*  55 */         if (player != null && ItemName.ingot.getItemStack((Enum)IngotResourceType.steel).func_77969_a(stack)) {
/*  56 */           IC2.achievements.issueAchievement(player, "acquireRefinedIron");
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*  61 */   public final InvSlotConsumableLiquidByList tankInputSlot = new InvSlotConsumableLiquidByList((IInventorySlotHolder)this, "cellInput", 1, new Fluid[] { FluidName.air.getInstance() });
/*  62 */   public final InvSlotOutput tankOutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "cellOutput", 1);
/*     */   
/*  64 */   public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 2);
/*     */   
/*  66 */   protected final Redstone redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
/*  67 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this)); @GuiSynced
/*  68 */   public final FluidTank fluidTank = (FluidTank)this.fluids.addTankInsert("fluid", 8000, Fluids.fluidPredicate(new Fluid[] { FluidName.air.getInstance() }));
/*     */ 
/*     */   
/*     */   public static void init() {
/*  72 */     Recipes.blastfurnace = (IBasicMachineRecipeManager)new BasicMachineRecipeManager();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntityServer() {
/*  78 */     super.updateEntityServer();
/*     */     
/*  80 */     boolean needsInvUpdate = false;
/*     */     
/*  82 */     heatup();
/*     */     
/*  84 */     MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result = getOutput();
/*     */ 
/*     */     
/*  87 */     if (result != null && isHot()) {
/*  88 */       setActive(true);
/*     */       
/*  90 */       if (result.getRecipe().getMetaData().func_74762_e("fluid") <= this.fluidTank.getFluidAmount()) {
/*  91 */         this.progress++;
/*  92 */         this.fluidTank.drainInternal(result.getRecipe().getMetaData().func_74762_e("fluid"), true);
/*     */       } 
/*     */       
/*  95 */       this.progressNeeded = result.getRecipe().getMetaData().func_74762_e("duration");
/*  96 */       if (this.progress >= result.getRecipe().getMetaData().func_74762_e("duration")) {
/*  97 */         operateOnce(result, (Collection<ItemStack>)result.getOutput());
/*  98 */         needsInvUpdate = true;
/*  99 */         this.progress = 0;
/*     */       } 
/*     */     } else {
/*     */       
/* 103 */       if (result == null) {
/* 104 */         this.progress = 0;
/*     */       }
/*     */       
/* 107 */       setActive(false);
/*     */     } 
/*     */     
/* 110 */     if (this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity()) {
/* 111 */       gainFluid();
/*     */     }
/*     */     
/* 114 */     needsInvUpdate |= this.upgradeSlot.tickNoMark();
/*     */     
/* 116 */     this.guiProgress = this.progress / this.progressNeeded;
/* 117 */     this.guiHeat = this.heat / maxHeat;
/*     */     
/* 119 */     if (needsInvUpdate) {
/* 120 */       func_70296_d();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void operateOnce(MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> result, Collection<ItemStack> processResult) {
/* 126 */     this.inputSlot.consume(result);
/*     */     
/* 128 */     this.outputSlot.add(processResult);
/*     */   }
/*     */   
/*     */   public MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> getOutput() {
/* 132 */     if (this.inputSlot.isEmpty()) {
/* 133 */       return null;
/*     */     }
/*     */     
/* 136 */     MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output = this.inputSlot.process();
/* 137 */     if (output == null || output.getRecipe().getMetaData() == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     if (this.outputSlot.canAdd((Collection)output.getOutput())) {
/* 141 */       return output;
/*     */     }
/*     */     
/* 144 */     return null;
/*     */   }
/*     */   
/*     */   public boolean gainFluid() {
/* 148 */     return this.tankInputSlot.processIntoTank((IFluidTank)this.fluidTank, this.tankOutputSlot);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 154 */     super.func_145839_a(nbt);
/* 155 */     this.heat = nbt.func_74762_e("heat");
/* 156 */     this.progress = nbt.func_74762_e("progress");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 161 */     super.func_189515_b(nbt);
/*     */     
/* 163 */     nbt.func_74768_a("heat", this.heat);
/* 164 */     nbt.func_74768_a("progress", this.progress);
/*     */     
/* 166 */     return nbt;
/*     */   }
/*     */   
/*     */   private void heatup() {
/* 170 */     int coolingPerTick = 1;
/* 171 */     int heatRequested = 0;
/* 172 */     int gainhU = 0;
/*     */     
/* 174 */     if ((!this.inputSlot.isEmpty() || this.progress >= 1) && this.heat <= maxHeat) {
/* 175 */       heatRequested = maxHeat - this.heat + 100;
/* 176 */     } else if (this.redstone.hasRedstoneInput() && this.heat <= maxHeat) {
/* 177 */       heatRequested = maxHeat - this.heat + 100;
/*     */     } 
/*     */     
/* 180 */     if (heatRequested > 0) {
/* 181 */       EnumFacing dir = getFacing();
/* 182 */       TileEntity te = func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(dir));
/*     */       
/* 184 */       if (te instanceof IHeatSource) {
/* 185 */         gainhU = ((IHeatSource)te).drawHeat(dir.func_176734_d(), heatRequested, false);
/* 186 */         this.heat += gainhU;
/*     */       } 
/*     */       
/* 189 */       if (gainhU == 0) {
/* 190 */         this.heat -= Math.min(this.heat, 1);
/*     */       }
/*     */     } else {
/* 193 */       this.heat -= Math.min(this.heat, 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isHot() {
/* 198 */     return (this.heat >= maxHeat);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityBlastFurnace> getGuiContainer(EntityPlayer player) {
/* 204 */     return (ContainerBase<TileEntityBlastFurnace>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 210 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public double getGuiValue(String name) {
/* 221 */     if (name.equals("progress"))
/* 222 */       return this.guiProgress; 
/* 223 */     if (name.equals("heat")) {
/* 224 */       return this.guiHeat;
/*     */     }
/* 226 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 233 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 238 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 243 */     return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidConsuming);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public int heat = 0;
/* 251 */   public static int maxHeat = 50000;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   protected int progress = 0;
/* 261 */   protected int progressNeeded = 300;
/*     */   @GuiSynced
/*     */   public float guiHeat;
/*     */   @GuiSynced
/*     */   protected float guiProgress;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityBlastFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */