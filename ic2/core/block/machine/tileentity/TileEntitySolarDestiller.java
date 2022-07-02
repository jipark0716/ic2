/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Fluids;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.machine.container.ContainerSolarDestiller;
/*     */ import ic2.core.block.machine.gui.GuiSolarDestiller;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.util.BiomeUtil;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ import net.minecraftforge.fluids.IFluidTank;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntitySolarDestiller
/*     */   extends TileEntityInventory
/*     */   implements IHasGui, IUpgradableBlock
/*     */ {
/*  47 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*     */   
/*  49 */   public final FluidTank inputTank = (FluidTank)this.fluids.addTankInsert("inputTank", 10000, Fluids.fluidPredicate(new Fluid[] { FluidRegistry.WATER }));
/*  50 */   public final FluidTank outputTank = (FluidTank)this.fluids.addTankExtract("outputTank", 10000);
/*     */   
/*  52 */   public final InvSlotConsumableLiquidByList waterinputSlot = new InvSlotConsumableLiquidByList((IInventorySlotHolder)this, "waterInput", InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain, new Fluid[] { FluidRegistry.WATER });
/*  53 */   public final InvSlotConsumableLiquidByTank destiwaterinputSlot = new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "destilledWaterInput", InvSlot.Access.I, 1, InvSlot.InvSide.BOTTOM, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
/*     */   
/*  55 */   public final InvSlotOutput wateroutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "waterOutput", 1);
/*  56 */   public final InvSlotOutput destiwateroutputSlott = new InvSlotOutput((IInventorySlotHolder)this, "destilledWaterOutput", 1);
/*     */   
/*  58 */   public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 3);
/*     */   private int tickrate;
/*     */   private int updateTicker;
/*     */   private float skyLight;
/*     */   
/*     */   protected void onLoaded() {
/*  64 */     super.onLoaded();
/*     */     
/*  66 */     this.tickrate = getTickRate();
/*  67 */     this.updateTicker = IC2.random.nextInt(this.tickrate);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  72 */     super.updateEntityServer();
/*     */     
/*  74 */     this.waterinputSlot.processIntoTank((IFluidTank)this.inputTank, this.wateroutputSlot);
/*     */ 
/*     */     
/*  77 */     if (++this.updateTicker >= this.tickrate) {
/*  78 */       updateSunVisibility();
/*     */       
/*  80 */       if (canWork()) {
/*  81 */         this.inputTank.drainInternal(1, true);
/*  82 */         this.outputTank.fillInternal(new FluidStack(FluidName.distilled_water.getInstance(), 1), true);
/*     */       } 
/*     */       
/*  85 */       this.updateTicker = 0;
/*     */     } 
/*     */ 
/*     */     
/*  89 */     this.destiwaterinputSlot.processFromTank((IFluidTank)this.outputTank, this.destiwateroutputSlott);
/*     */     
/*  91 */     this.upgradeSlot.tick();
/*     */   }
/*     */   
/*     */   public void updateSunVisibility() {
/*  95 */     this.skyLight = TileEntitySolarGenerator.getSkyLight(func_145831_w(), this.field_174879_c.func_177984_a());
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntitySolarDestiller> getGuiContainer(EntityPlayer player) {
/* 100 */     return (ContainerBase<TileEntitySolarDestiller>)new ContainerSolarDestiller(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 106 */     return (GuiScreen)new GuiSolarDestiller(new ContainerSolarDestiller(player, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */   
/*     */   public int getTickRate() {
/* 113 */     Biome biome = BiomeUtil.getBiome(func_145831_w(), this.field_174879_c);
/*     */     
/* 115 */     if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) == true) return 36; 
/* 116 */     if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD) == true) return 144;
/*     */     
/* 118 */     return 72;
/*     */   }
/*     */   
/*     */   public int gaugeLiquidScaled(int i, int tank) {
/* 122 */     switch (tank) {
/*     */       case 0:
/* 124 */         if (this.inputTank.getFluidAmount() <= 0) return 0; 
/* 125 */         return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
/*     */       case 1:
/* 127 */         if (this.outputTank.getFluidAmount() <= 0) return 0; 
/* 128 */         return this.outputTank.getFluidAmount() * i / this.outputTank.getCapacity();
/*     */     } 
/* 130 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean canWork() {
/* 134 */     return (this.inputTank.getFluidAmount() > 0 && this.outputTank
/* 135 */       .getFluidAmount() < this.outputTank.getCapacity() && this.skyLight > 0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 141 */     return EnumSet.of(UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 147 */     return 40.0D;
/*     */   }
/*     */   public boolean useEnergy(double amount) {
/* 150 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntitySolarDestiller.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */