/*      */ package ic2.core.block.reactor.tileentity;
/*      */ 
/*      */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*      */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*      */ import ic2.api.energy.tile.IEnergyAcceptor;
/*      */ import ic2.api.energy.tile.IEnergySource;
/*      */ import ic2.api.energy.tile.IEnergyTile;
/*      */ import ic2.api.energy.tile.IMetaDelegate;
/*      */ import ic2.api.reactor.IBaseReactorComponent;
/*      */ import ic2.api.reactor.IReactor;
/*      */ import ic2.api.reactor.IReactorChamber;
/*      */ import ic2.api.reactor.IReactorComponent;
/*      */ import ic2.api.recipe.ILiquidAcceptManager;
/*      */ import ic2.api.recipe.ILiquidHeatExchangerManager;
/*      */ import ic2.api.recipe.Recipes;
/*      */ import ic2.core.ContainerBase;
/*      */ import ic2.core.ExplosionIC2;
/*      */ import ic2.core.IC2;
/*      */ import ic2.core.IC2DamageSource;
/*      */ import ic2.core.IHasGui;
/*      */ import ic2.core.audio.AudioSource;
/*      */ import ic2.core.audio.PositionSpec;
/*      */ import ic2.core.block.IInventorySlotHolder;
/*      */ import ic2.core.block.TileEntityBlock;
/*      */ import ic2.core.block.TileEntityInventory;
/*      */ import ic2.core.block.comp.Fluids;
/*      */ import ic2.core.block.comp.Redstone;
/*      */ import ic2.core.block.comp.TileEntityComponent;
/*      */ import ic2.core.block.invslot.InvSlot;
/*      */ import ic2.core.block.invslot.InvSlotConsumableLiquid;
/*      */ import ic2.core.block.invslot.InvSlotConsumableLiquidByManager;
/*      */ import ic2.core.block.invslot.InvSlotConsumableLiquidByTank;
/*      */ import ic2.core.block.invslot.InvSlotOutput;
/*      */ import ic2.core.block.invslot.InvSlotReactor;
/*      */ import ic2.core.block.reactor.container.ContainerNuclearReactor;
/*      */ import ic2.core.block.reactor.gui.GuiNuclearReactor;
/*      */ import ic2.core.block.state.IIdProvider;
/*      */ import ic2.core.block.type.ResourceBlock;
/*      */ import ic2.core.gui.dynamic.IGuiValueProvider;
/*      */ import ic2.core.init.MainConfig;
/*      */ import ic2.core.item.reactor.ItemReactorHeatStorage;
/*      */ import ic2.core.network.NetworkManager;
/*      */ import ic2.core.ref.BlockName;
/*      */ import ic2.core.ref.TeBlock;
/*      */ import ic2.core.util.ConfigUtil;
/*      */ import ic2.core.util.LogCategory;
/*      */ import ic2.core.util.StackUtil;
/*      */ import ic2.core.util.Util;
/*      */ import ic2.core.util.WorldSearchUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.world.ChunkCache;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraftforge.common.MinecraftForge;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.fluids.FluidTank;
/*      */ import net.minecraftforge.fluids.IFluidTank;
/*      */ import net.minecraftforge.fml.common.eventhandler.Event;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*      */ import org.apache.logging.log4j.Level;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TileEntityNuclearReactorElectric
/*      */   extends TileEntityInventory
/*      */   implements IHasGui, IReactor, IEnergySource, IMetaDelegate, IGuiValueProvider
/*      */ {
/*   91 */   public int updateTicker = IC2.random.nextInt(getTickRate());
/*      */   
/*   93 */   protected final Fluids fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/*      */   
/*   95 */   public final Fluids.InternalFluidTank inputTank = this.fluids.addTank("inputTank", 10000, InvSlot.Access.NONE, InvSlot.InvSide.ANY, Fluids.fluidPredicate((ILiquidAcceptManager)Recipes.liquidHeatupManager));
/*   96 */   public final Fluids.InternalFluidTank outputTank = this.fluids.addTank("outputTank", 10000, InvSlot.Access.NONE);
/*      */   
/*   98 */   public final InvSlotReactor reactorSlot = new InvSlotReactor(this, "reactor", 54);
/*      */   
/*  100 */   public final InvSlotConsumableLiquidByManager coolantinputSlot = new InvSlotConsumableLiquidByManager((IInventorySlotHolder)this, "coolantinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Drain, (ILiquidAcceptManager)Recipes.liquidHeatupManager);
/*  101 */   public final InvSlotConsumableLiquidByTank hotcoolinputSlot = new InvSlotConsumableLiquidByTank((IInventorySlotHolder)this, "hotcoolinputSlot", InvSlot.Access.I, 1, InvSlot.InvSide.ANY, InvSlotConsumableLiquid.OpType.Fill, (IFluidTank)this.outputTank);
/*      */   
/*  103 */   public final InvSlotOutput coolantoutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "coolantoutputSlot", 1);
/*  104 */   public final InvSlotOutput hotcoolantoutputSlot = new InvSlotOutput((IInventorySlotHolder)this, "hotcoolantoutputSlot", 1);
/*      */   
/*  106 */   public final Redstone redstone = (Redstone)addComponent((TileEntityComponent)new Redstone((TileEntityBlock)this));
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onLoaded() {
/*  111 */     super.onLoaded();
/*      */     
/*  113 */     if (!(func_145831_w()).field_72995_K && !isFluidCooled()) {
/*  114 */       refreshChambers();
/*  115 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  116 */       this.addedToEnergyNet = true;
/*      */     } 
/*      */     
/*  119 */     createChamberRedstoneLinks();
/*      */     
/*  121 */     if (isFluidCooled()) {
/*  122 */       createCasingRedstoneLinks();
/*  123 */       openTanks();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onUnloaded() {
/*  129 */     if (IC2.platform.isRendering()) {
/*  130 */       IC2.audioManager.removeSources(this);
/*      */       
/*  132 */       this.audioSourceMain = null;
/*  133 */       this.audioSourceGeiger = null;
/*      */     } 
/*      */     
/*  136 */     if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
/*  137 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*      */       
/*  139 */       this.addedToEnergyNet = false;
/*      */     } 
/*      */     
/*  142 */     super.onUnloaded();
/*      */   }
/*      */   
/*      */   public int gaugeHeatScaled(int i) {
/*  146 */     return i * this.heat / this.maxHeat / 100 * 85;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_145839_a(NBTTagCompound nbt) {
/*  151 */     super.func_145839_a(nbt);
/*      */     
/*  153 */     this.heat = nbt.func_74762_e("heat");
/*  154 */     this.output = nbt.func_74765_d("output");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  162 */     nbt = super.func_189515_b(nbt);
/*      */     
/*  164 */     nbt.func_74768_a("heat", this.heat);
/*  165 */     nbt.func_74777_a("output", (short)(int)getReactorEnergyOutput());
/*      */     
/*  167 */     return nbt;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/*  172 */     super.onNeighborChange(neighbor, neighborPos);
/*      */     
/*  174 */     if (this.addedToEnergyNet) refreshChambers();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawEnergy(double amount) {}
/*      */ 
/*      */   
/*      */   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing direction) {
/*  184 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getOfferedEnergy() {
/*  189 */     return (getReactorEnergyOutput() * 5.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear"));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSourceTier() {
/*  194 */     return 5;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getReactorEUEnergyOutput() {
/*  199 */     return getOfferedEnergy();
/*      */   }
/*      */ 
/*      */   
/*      */   public List<IEnergyTile> getSubTiles() {
/*  204 */     return Collections.unmodifiableList(new ArrayList<>(this.subTiles));
/*      */   }
/*      */   
/*      */   private void processfluidsSlots() {
/*  208 */     this.coolantinputSlot.processIntoTank((IFluidTank)this.inputTank, this.coolantoutputSlot);
/*  209 */     this.hotcoolinputSlot.processFromTank((IFluidTank)this.outputTank, this.hotcoolantoutputSlot);
/*      */   }
/*      */   
/*      */   public void refreshChambers() {
/*  213 */     World world = func_145831_w();
/*  214 */     List<IEnergyTile> newSubTiles = new ArrayList<>();
/*      */     
/*  216 */     newSubTiles.add(this);
/*      */     
/*  218 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  219 */       TileEntity te = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*      */       
/*  221 */       if (te instanceof TileEntityReactorChamberElectric && !te.func_145837_r()) {
/*  222 */         newSubTiles.add(te);
/*      */       }
/*      */     } 
/*      */     
/*  226 */     if (!newSubTiles.equals(this.subTiles)) {
/*  227 */       if (this.addedToEnergyNet) MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*      */       
/*  229 */       this.subTiles.clear();
/*  230 */       this.subTiles.addAll(newSubTiles);
/*      */       
/*  232 */       if (this.addedToEnergyNet) MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void updateEntityServer() {
/*  238 */     super.updateEntityServer();
/*      */     
/*  240 */     if (this.updateTicker++ % getTickRate() != 0)
/*      */       return; 
/*  242 */     if (!func_145831_w().func_175697_a(this.field_174879_c, 8)) {
/*  243 */       this.output = 0.0F;
/*      */     } else {
/*  245 */       boolean toFluidCooled = isFluidReactor();
/*      */       
/*  247 */       if (this.fluidCooled != toFluidCooled) {
/*  248 */         if (toFluidCooled) {
/*  249 */           enableFluidMode();
/*      */         } else {
/*  251 */           disableFluidMode();
/*      */         } 
/*      */         
/*  254 */         this.fluidCooled = toFluidCooled;
/*      */       } 
/*      */       
/*  257 */       dropAllUnfittingStuff();
/*      */       
/*  259 */       this.output = 0.0F;
/*  260 */       this.maxHeat = 10000;
/*  261 */       this.hem = 1.0F;
/*      */       
/*  263 */       processChambers();
/*      */ 
/*      */       
/*  266 */       if (this.fluidCooled) {
/*  267 */         processfluidsSlots();
/*  268 */         FluidStack inputFluid = this.inputTank.getFluid();
/*      */         
/*  270 */         assert inputFluid == null || Recipes.liquidHeatupManager.acceptsFluid(this.inputTank.getFluid().getFluid());
/*      */         
/*  272 */         int huOtput = (int)(huOutputModifier * this.EmitHeatbuffer);
/*  273 */         int outputroom = this.outputTank.getCapacity() - this.outputTank.getFluidAmount();
/*  274 */         this.EmitHeatbuffer = 0;
/*      */ 
/*      */         
/*  277 */         if (outputroom > 0 && inputFluid != null) {
/*      */           
/*  279 */           ILiquidHeatExchangerManager.HeatExchangeProperty prop = Recipes.liquidHeatupManager.getHeatExchangeProperty(inputFluid.getFluid());
/*      */           
/*  281 */           int fluidOutput = huOtput / prop.huPerMB;
/*  282 */           FluidStack add = new FluidStack(prop.outputFluid, fluidOutput);
/*  283 */           if (this.outputTank.canFillFluidType(add)) {
/*  284 */             FluidStack draincoolant; if (fluidOutput < outputroom) {
/*  285 */               this.EmitHeatbuffer = (int)((huOtput % prop.huPerMB) / huOutputModifier);
/*  286 */               this.EmitHeat = (int)(huOtput / huOutputModifier);
/*  287 */               draincoolant = this.inputTank.drainInternal(fluidOutput, false);
/*      */             } else {
/*  289 */               this.EmitHeat = outputroom * prop.huPerMB;
/*  290 */               draincoolant = this.inputTank.drainInternal(outputroom, false);
/*      */             } 
/*      */             
/*  293 */             if (draincoolant != null) {
/*  294 */               this.EmitHeat = draincoolant.amount * prop.huPerMB;
/*      */               
/*  296 */               huOtput -= (this.inputTank.drainInternal(draincoolant.amount, true)).amount * prop.huPerMB;
/*  297 */               this.outputTank.fillInternal(new FluidStack(prop.outputFluid, draincoolant.amount), true);
/*      */             } else {
/*  299 */               this.EmitHeat = 0;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  303 */           this.EmitHeat = 0;
/*      */         } 
/*  305 */         addHeat((int)(huOtput / huOutputModifier));
/*      */       } 
/*      */ 
/*      */       
/*  309 */       if (calculateHeatEffects()) {
/*      */         return;
/*      */       }
/*  312 */       setActive((this.heat >= 1000 || this.output > 0.0F));
/*      */       
/*  314 */       func_70296_d();
/*      */     } 
/*      */ 
/*      */     
/*  318 */     ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "output");
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   protected void updateEntityClient() {
/*  324 */     super.updateEntityClient();
/*      */     
/*  326 */     showHeatEffects(func_145831_w(), this.field_174879_c, this.heat);
/*      */   }
/*      */   
/*      */   public static void showHeatEffects(World world, BlockPos pos, int heat) {
/*  330 */     Random rnd = world.field_73012_v;
/*  331 */     if (rnd.nextInt(8) != 0)
/*      */       return; 
/*  333 */     int puffs = heat / 1000;
/*      */     
/*  335 */     if (puffs > 0) {
/*  336 */       puffs = rnd.nextInt(puffs);
/*      */       int n;
/*  338 */       for (n = 0; n < puffs; n++) {
/*  339 */         world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, (pos.func_177958_n() + rnd.nextFloat()), (pos.func_177956_o() + 0.95F), (pos.func_177952_p() + rnd.nextFloat()), 0.0D, 0.0D, 0.0D, new int[0]);
/*      */       }
/*      */       
/*  342 */       puffs -= rnd.nextInt(4) + 3;
/*      */       
/*  344 */       for (n = 0; n < puffs; n++) {
/*  345 */         world.func_175688_a(EnumParticleTypes.FLAME, (pos.func_177958_n() + rnd.nextFloat()), (pos.func_177956_o() + 1), (pos.func_177952_p() + rnd.nextFloat()), 0.0D, 0.0D, 0.0D, new int[0]);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dropAllUnfittingStuff() {
/*      */     int i;
/*  355 */     for (i = 0; i < this.reactorSlot.size(); i++) {
/*  356 */       ItemStack stack = this.reactorSlot.get(i);
/*      */       
/*  358 */       if (stack != null && !isUsefulItem(stack, false)) {
/*  359 */         this.reactorSlot.put(i, null);
/*  360 */         eject(stack);
/*      */       } 
/*      */     } 
/*      */     
/*  364 */     for (i = this.reactorSlot.size(); i < this.reactorSlot.rawSize(); i++) {
/*  365 */       ItemStack stack = this.reactorSlot.get(i);
/*      */       
/*  367 */       this.reactorSlot.put(i, null);
/*  368 */       eject(stack);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsefulItem(ItemStack stack, boolean forInsertion) {
/*  376 */     Item item = stack.func_77973_b();
/*  377 */     if (item == null) return false;
/*      */     
/*  379 */     if (forInsertion && this.fluidCooled && 
/*  380 */       item.getClass() == ItemReactorHeatStorage.class && (
/*  381 */       (ItemReactorHeatStorage)item).getCustomDamage(stack) > 0) return false;
/*      */ 
/*      */ 
/*      */     
/*  385 */     return (item instanceof IBaseReactorComponent && (!forInsertion || ((IBaseReactorComponent)item).canBePlacedIn(stack, this)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void eject(ItemStack drop) {
/*  394 */     if (!IC2.platform.isSimulating() || drop == null) {
/*      */       return;
/*      */     }
/*      */     
/*  398 */     StackUtil.dropAsEntity(func_145831_w(), this.field_174879_c, drop);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean calculateHeatEffects() {
/*  408 */     if (this.heat < 4000 || !IC2.platform.isSimulating() || ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit") <= 0.0F) {
/*  409 */       return false;
/*      */     }
/*      */     
/*  412 */     float power = this.heat / this.maxHeat;
/*      */     
/*  414 */     if (power >= 1.0F) {
/*  415 */       explode();
/*  416 */       return true;
/*      */     } 
/*      */     
/*  419 */     World world = func_145831_w();
/*      */     
/*  421 */     if (power >= 0.85F && world.field_73012_v.nextFloat() <= 0.2F * this.hem) {
/*  422 */       BlockPos coord = getRandCoord(2);
/*      */ 
/*      */       
/*  425 */       IBlockState state = world.func_180495_p(coord);
/*  426 */       Block block = state.func_177230_c();
/*      */       
/*  428 */       if (block.isAir(state, (IBlockAccess)world, coord)) {
/*  429 */         world.func_175656_a(coord, Blocks.field_150480_ab.func_176223_P());
/*  430 */       } else if (state.func_185887_b(world, coord) >= 0.0F && world
/*  431 */         .func_175625_s(coord) == null) {
/*  432 */         Material mat = state.func_185904_a();
/*      */         
/*  434 */         if (mat == Material.field_151576_e || mat == Material.field_151573_f || mat == Material.field_151587_i || mat == Material.field_151578_c || mat == Material.field_151571_B) {
/*      */           
/*  436 */           world.func_175656_a(coord, Blocks.field_150356_k.func_176223_P());
/*      */         } else {
/*  438 */           world.func_175656_a(coord, Blocks.field_150480_ab.func_176223_P());
/*      */         } 
/*      */       } 
/*      */     } 
/*  442 */     if (power >= 0.7F) {
/*  443 */       List<EntityLivingBase> nearByEntities = world.func_72872_a(EntityLivingBase.class, new AxisAlignedBB((this.field_174879_c.func_177958_n() - 3), (this.field_174879_c.func_177956_o() - 3), (this.field_174879_c.func_177952_p() - 3), (this.field_174879_c
/*  444 */             .func_177958_n() + 4), (this.field_174879_c.func_177956_o() + 4), (this.field_174879_c.func_177952_p() + 4)));
/*      */       
/*  446 */       for (EntityLivingBase entity : nearByEntities) {
/*  447 */         entity.func_70097_a((DamageSource)IC2DamageSource.radiation, (int)(world.field_73012_v.nextInt(4) * this.hem));
/*      */       }
/*      */     } 
/*  450 */     if (power >= 0.5F && world.field_73012_v.nextFloat() <= this.hem) {
/*  451 */       BlockPos coord = getRandCoord(2);
/*      */ 
/*      */       
/*  454 */       IBlockState state = world.func_180495_p(coord);
/*      */       
/*  456 */       if (state.func_185904_a() == Material.field_151586_h) {
/*  457 */         world.func_175698_g(coord);
/*      */       }
/*      */     } 
/*      */     
/*  461 */     if (power >= 0.4F && world.field_73012_v.nextFloat() <= this.hem) {
/*  462 */       BlockPos coord = getRandCoord(2);
/*      */       
/*  464 */       if (world.func_175625_s(coord) == null) {
/*      */         
/*  466 */         IBlockState state = world.func_180495_p(coord);
/*  467 */         Material mat = state.func_185904_a();
/*      */         
/*  469 */         if (mat == Material.field_151575_d || mat == Material.field_151584_j || mat == Material.field_151580_n) {
/*  470 */           world.func_175656_a(coord, Blocks.field_150480_ab.func_176223_P());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  475 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getRandCoord(int radius) {
/*      */     BlockPos ret;
/*  483 */     if (radius <= 0) return null;
/*      */     
/*  485 */     World world = func_145831_w();
/*      */ 
/*      */     
/*      */     do {
/*  489 */       ret = this.field_174879_c.func_177982_a(world.field_73012_v.nextInt(2 * radius + 1) - radius, world.field_73012_v
/*  490 */           .nextInt(2 * radius + 1) - radius, world.field_73012_v
/*  491 */           .nextInt(2 * radius + 1) - radius);
/*  492 */     } while (ret.equals(this.field_174879_c));
/*      */     
/*  494 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processChambers() {
/*  502 */     int size = getReactorSize();
/*      */     
/*  504 */     for (int pass = 0; pass < 2; pass++) {
/*  505 */       for (int y = 0; y < 6; y++) {
/*  506 */         for (int x = 0; x < size; x++) {
/*  507 */           ItemStack stack = this.reactorSlot.get(x, y);
/*      */           
/*  509 */           if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*  510 */             IReactorComponent comp = (IReactorComponent)stack.func_77973_b();
/*  511 */             comp.processChamber(stack, this, x, y, (pass == 0));
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean produceEnergy() {
/*  524 */     return (this.redstone.hasRedstoneInput() && ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear") > 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReactorSize() {
/*  533 */     World world = func_145831_w();
/*      */     
/*  535 */     if (world == null) return 9;
/*      */     
/*  537 */     int cols = 3;
/*      */     
/*  539 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  540 */       TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*      */       
/*  542 */       if (target instanceof TileEntityReactorChamberElectric) {
/*  543 */         cols++;
/*      */       }
/*      */     } 
/*      */     
/*  547 */     return cols;
/*      */   }
/*      */   
/*      */   private boolean isFullSize() {
/*  551 */     return (getReactorSize() == 9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickRate() {
/*  560 */     return 20;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  565 */     if (StackUtil.checkItemEquality(StackUtil.get(player, hand), BlockName.te.getItemStack((Enum)TeBlock.reactor_chamber))) {
/*  566 */       return false;
/*      */     }
/*      */     
/*  569 */     return super.onActivated(player, hand, side, hitX, hitY, hitZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public ContainerBase<TileEntityNuclearReactorElectric> getGuiContainer(EntityPlayer player) {
/*  574 */     return (ContainerBase<TileEntityNuclearReactorElectric>)new ContainerNuclearReactor(player, this);
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*  580 */     return (GuiScreen)new GuiNuclearReactor(new ContainerNuclearReactor(player, this));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onGuiClosed(EntityPlayer player) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNetworkUpdate(String field) {
/*  590 */     if (field.equals("output")) {
/*  591 */       if (this.output > 0.0F) {
/*  592 */         if (this.lastOutput <= 0.0F) {
/*  593 */           if (this.audioSourceMain == null) this.audioSourceMain = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/NuclearReactorLoop.ogg", true, false, IC2.audioManager.getDefaultVolume()); 
/*  594 */           if (this.audioSourceMain != null) this.audioSourceMain.play();
/*      */         
/*      */         } 
/*  597 */         if (this.output < 40.0F) {
/*  598 */           if (this.lastOutput <= 0.0F || this.lastOutput >= 40.0F) {
/*  599 */             if (this.audioSourceGeiger != null) this.audioSourceGeiger.remove(); 
/*  600 */             this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerLowEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
/*  601 */             if (this.audioSourceGeiger != null) this.audioSourceGeiger.play(); 
/*      */           } 
/*  603 */         } else if (this.output < 80.0F) {
/*  604 */           if (this.lastOutput < 40.0F || this.lastOutput >= 80.0F) {
/*  605 */             if (this.audioSourceGeiger != null) this.audioSourceGeiger.remove(); 
/*  606 */             this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerMedEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
/*  607 */             if (this.audioSourceGeiger != null) this.audioSourceGeiger.play(); 
/*      */           } 
/*  609 */         } else if (this.output >= 80.0F && 
/*  610 */           this.lastOutput < 80.0F) {
/*  611 */           if (this.audioSourceGeiger != null) this.audioSourceGeiger.remove(); 
/*  612 */           this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerHighEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
/*  613 */           if (this.audioSourceGeiger != null) this.audioSourceGeiger.play();
/*      */         
/*      */         } 
/*  616 */       } else if (this.lastOutput > 0.0F) {
/*  617 */         if (this.audioSourceMain != null) this.audioSourceMain.stop(); 
/*  618 */         if (this.audioSourceGeiger != null) this.audioSourceGeiger.stop();
/*      */       
/*      */       } 
/*  621 */       this.lastOutput = this.output;
/*      */     } 
/*      */     
/*  624 */     super.onNetworkUpdate(field);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  630 */   private float lastOutput = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TileEntity getCoreTe() {
/*  636 */     return (TileEntity)this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/*  642 */     return this.field_174879_c;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorldObj() {
/*  647 */     return func_145831_w();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHeat() {
/*  652 */     return this.heat;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHeat(int heat) {
/*  657 */     this.heat = heat;
/*      */   }
/*      */ 
/*      */   
/*      */   public int addHeat(int amount) {
/*  662 */     this.heat += amount;
/*  663 */     return this.heat;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemAt(int x, int y) {
/*  668 */     if (x < 0 || x >= getReactorSize() || y < 0 || y >= 6) return null;
/*      */     
/*  670 */     return this.reactorSlot.get(x, y);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemAt(int x, int y, ItemStack item) {
/*  675 */     if (x < 0 || x >= getReactorSize() || y < 0 || y >= 6)
/*      */       return; 
/*  677 */     this.reactorSlot.put(x, y, item);
/*      */   }
/*      */ 
/*      */   
/*      */   public void explode() {
/*  682 */     float boomPower = 10.0F;
/*  683 */     float boomMod = 1.0F;
/*      */     
/*  685 */     for (int i = 0; i < this.reactorSlot.size(); i++) {
/*  686 */       ItemStack stack = this.reactorSlot.get(i);
/*      */       
/*  688 */       if (stack != null && stack.func_77973_b() instanceof IReactorComponent) {
/*  689 */         float f = ((IReactorComponent)stack.func_77973_b()).influenceExplosion(stack, this);
/*      */         
/*  691 */         if (f > 0.0F && f < 1.0F) {
/*  692 */           boomMod *= f;
/*      */         } else {
/*  694 */           boomPower += f;
/*      */         } 
/*      */       } 
/*      */       
/*  698 */       this.reactorSlot.put(i, null);
/*      */     } 
/*      */     
/*  701 */     boomPower *= this.hem * boomMod;
/*      */     
/*  703 */     IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Nuclear Reactor at %s melted (raw explosion power %f)", new Object[] { Util.formatPosition((TileEntity)this), Float.valueOf(boomPower) });
/*      */     
/*  705 */     boomPower = Math.min(boomPower, ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit"));
/*  706 */     World world = func_145831_w();
/*      */     
/*  708 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  709 */       TileEntity target = world.func_175625_s(this.field_174879_c.func_177972_a(dir));
/*      */       
/*  711 */       if (target instanceof TileEntityReactorChamberElectric) {
/*  712 */         world.func_175698_g(target.func_174877_v());
/*      */       }
/*      */     } 
/*      */     
/*  716 */     world.func_175698_g(this.field_174879_c);
/*      */     
/*  718 */     ExplosionIC2 explosion = new ExplosionIC2(world, null, this.field_174879_c, boomPower, 0.01F, ExplosionIC2.Type.Nuclear);
/*  719 */     explosion.doExplosion();
/*      */   }
/*      */ 
/*      */   
/*      */   public void addEmitHeat(int heat) {
/*  724 */     this.EmitHeatbuffer += heat;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxHeat() {
/*  729 */     return this.maxHeat;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMaxHeat(int newMaxHeat) {
/*  734 */     this.maxHeat = newMaxHeat;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHeatEffectModifier() {
/*  739 */     return this.hem;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHeatEffectModifier(float newHEM) {
/*  744 */     this.hem = newHEM;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getReactorEnergyOutput() {
/*  749 */     return this.output;
/*      */   }
/*      */ 
/*      */   
/*      */   public float addOutput(float energy) {
/*  754 */     return this.output += energy;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFluidCooled() {
/*  760 */     return this.fluidCooled;
/*      */   }
/*      */   
/*      */   private void createChamberRedstoneLinks() {
/*  764 */     World world = func_145831_w();
/*      */     
/*  766 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/*  767 */       BlockPos cPos = this.field_174879_c.func_177972_a(facing);
/*  768 */       TileEntity te = world.func_175625_s(cPos);
/*      */       
/*  770 */       if (te instanceof TileEntityReactorChamberElectric) {
/*  771 */         TileEntityReactorChamberElectric chamber = (TileEntityReactorChamberElectric)te;
/*      */         
/*  773 */         if (chamber.redstone.isLinked() && chamber.redstone.getLinkReceiver() != this.redstone) {
/*  774 */           chamber.destoryChamber(true);
/*      */         } else {
/*  776 */           chamber.redstone.linkTo(this.redstone);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void createCasingRedstoneLinks() {
/*  783 */     WorldSearchUtil.findTileEntities(func_145831_w(), this.field_174879_c, 2, new WorldSearchUtil.ITileEntityResultHandler()
/*      */         {
/*      */           public boolean onMatch(TileEntity te) {
/*  786 */             if (te instanceof TileEntityReactorRedstonePort) {
/*  787 */               ((TileEntityReactorRedstonePort)te).redstone.linkTo(TileEntityNuclearReactorElectric.this.redstone);
/*      */             }
/*      */             
/*  790 */             return false;
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private void removeCasingRedstoneLinks() {
/*  796 */     for (Redstone rs : this.redstone.getLinkedOrigins()) {
/*  797 */       if (rs.getParent() instanceof TileEntityReactorRedstonePort) {
/*  798 */         rs.unlinkOutbound();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void enableFluidMode() {
/*  804 */     if (this.addedToEnergyNet) {
/*  805 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*  806 */       this.addedToEnergyNet = false;
/*      */     } 
/*      */     
/*  809 */     createCasingRedstoneLinks();
/*  810 */     openTanks();
/*      */   }
/*      */   
/*      */   private void disableFluidMode() {
/*  814 */     if (!this.addedToEnergyNet) {
/*  815 */       refreshChambers();
/*  816 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*  817 */       this.addedToEnergyNet = true;
/*      */     } 
/*      */     
/*  820 */     removeCasingRedstoneLinks();
/*  821 */     closeTanks();
/*      */   }
/*      */   
/*      */   private void openTanks() {
/*  825 */     this.fluids.changeConnectivity(this.inputTank, InvSlot.Access.I, InvSlot.InvSide.ANY);
/*  826 */     this.fluids.changeConnectivity(this.outputTank, InvSlot.Access.O, InvSlot.InvSide.ANY);
/*      */   }
/*      */   
/*      */   private void closeTanks() {
/*  830 */     this.fluids.changeConnectivity(this.inputTank, InvSlot.Access.NONE, InvSlot.InvSide.ANY);
/*  831 */     this.fluids.changeConnectivity(this.outputTank, InvSlot.Access.NONE, InvSlot.InvSide.ANY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isFluidReactor() {
/*  884 */     if (!isFullSize()) return false; 
/*  885 */     if (!hasFluidChamber()) return false;
/*      */ 
/*      */     
/*  888 */     int range = 2;
/*  889 */     final MutableBoolean foundConflict = new MutableBoolean();
/*      */     
/*  891 */     WorldSearchUtil.findTileEntities(func_145831_w(), this.field_174879_c, 4, new WorldSearchUtil.ITileEntityResultHandler()
/*      */         {
/*      */           public boolean onMatch(TileEntity te) {
/*  894 */             if (!(te instanceof TileEntityNuclearReactorElectric)) return false; 
/*  895 */             if (te == TileEntityNuclearReactorElectric.this) return false;
/*      */             
/*  897 */             TileEntityNuclearReactorElectric reactor = (TileEntityNuclearReactorElectric)te;
/*      */             
/*  899 */             if (reactor.isFullSize() && reactor.hasFluidChamber()) {
/*  900 */               foundConflict.setTrue();
/*  901 */               return true;
/*      */             } 
/*  903 */             return false;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  908 */     return !foundConflict.getValue().booleanValue();
/*      */   }
/*      */   
/*      */   private boolean hasFluidChamber() {
/*  912 */     int range = 2;
/*  913 */     ChunkCache cache = new ChunkCache(func_145831_w(), this.field_174879_c.func_177982_a(-2, -2, -2), this.field_174879_c.func_177982_a(2, 2, 2), 0);
/*  914 */     BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
/*      */     
/*      */     int i;
/*  917 */     for (i = 0; i < 2; i++) {
/*  918 */       int y = this.field_174879_c.func_177956_o() + 2 * (i * 2 - 1);
/*      */       
/*  920 */       for (int z = this.field_174879_c.func_177952_p() - 2; z <= this.field_174879_c.func_177952_p() + 2; z++) {
/*  921 */         for (int x = this.field_174879_c.func_177958_n() - 2; x <= this.field_174879_c.func_177958_n() + 2; x++) {
/*  922 */           cPos.func_181079_c(x, y, z);
/*      */           
/*  924 */           if (!isFluidChamberBlock((IBlockAccess)cache, (BlockPos)cPos)) return false;
/*      */         
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  930 */     for (i = 0; i < 2; i++) {
/*  931 */       int z = this.field_174879_c.func_177952_p() + 2 * (i * 2 - 1);
/*      */       
/*  933 */       for (int y = this.field_174879_c.func_177956_o() - 2 + 1; y <= this.field_174879_c.func_177956_o() + 2 - 1; y++) {
/*  934 */         for (int x = this.field_174879_c.func_177958_n() - 2; x <= this.field_174879_c.func_177958_n() + 2; x++) {
/*  935 */           cPos.func_181079_c(x, y, z);
/*      */           
/*  937 */           if (!isFluidChamberBlock((IBlockAccess)cache, (BlockPos)cPos)) return false;
/*      */         
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  943 */     for (i = 0; i < 2; i++) {
/*  944 */       int x = this.field_174879_c.func_177958_n() + 2 * (i * 2 - 1);
/*      */       
/*  946 */       for (int y = this.field_174879_c.func_177956_o() - 2 + 1; y <= this.field_174879_c.func_177956_o() + 2 - 1; y++) {
/*  947 */         for (int z = this.field_174879_c.func_177952_p() - 2 + 1; z <= this.field_174879_c.func_177952_p() + 2 - 1; z++) {
/*  948 */           cPos.func_181079_c(x, y, z);
/*      */           
/*  950 */           if (!isFluidChamberBlock((IBlockAccess)cache, (BlockPos)cPos)) return false;
/*      */         
/*      */         } 
/*      */       } 
/*      */     } 
/*  955 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean isFluidChamberBlock(IBlockAccess world, BlockPos pos) {
/*  959 */     IBlockState state = world.func_180495_p(pos);
/*  960 */     if (state == BlockName.resource.getBlockState((IIdProvider)ResourceBlock.reactor_vessel)) return true;
/*      */     
/*  962 */     TileEntity te = world.func_175625_s(pos);
/*  963 */     if (te == null) return false;
/*      */     
/*  965 */     return (te instanceof IReactorChamber && ((IReactorChamber)te).isWall());
/*      */   }
/*      */ 
/*      */   
/*      */   public double getGuiValue(String name) {
/*  970 */     if ("heat".equals(name)) {
/*  971 */       return (this.maxHeat == 0) ? 0.0D : (this.heat / this.maxHeat);
/*      */     }
/*  973 */     throw new IllegalArgumentException("Invalid value: " + name);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gaugeLiquidScaled(int i, int tank) {
/*  978 */     switch (tank) {
/*      */       case 0:
/*  980 */         if (this.inputTank.getFluidAmount() <= 0) return 0; 
/*  981 */         return this.inputTank.getFluidAmount() * i / this.inputTank.getCapacity();
/*      */       case 1:
/*  983 */         if (this.outputTank.getFluidAmount() <= 0) return 0; 
/*  984 */         return this.outputTank.getFluidAmount() * i / this.outputTank.getCapacity();
/*      */     } 
/*  986 */     return 0;
/*      */   }
/*      */   
/*      */   public FluidTank getinputtank() {
/*  990 */     return (FluidTank)this.inputTank;
/*      */   }
/*      */   
/*      */   public FluidTank getoutputtank() {
/*  994 */     return (FluidTank)this.outputTank;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_70297_j_() {
/*  999 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1005 */   private final List<IEnergyTile> subTiles = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1020 */   public float output = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1030 */   public int heat = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1035 */   public int maxHeat = 10000;
/*      */ 
/*      */ 
/*      */   
/* 1039 */   public float hem = 1.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1044 */   private int EmitHeatbuffer = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1049 */   public int EmitHeat = 0;
/*      */ 
/*      */   
/*      */   private boolean fluidCooled = false;
/*      */ 
/*      */   
/*      */   public boolean addedToEnergyNet = false;
/*      */ 
/*      */   
/* 1058 */   private static final float huOutputModifier = 40.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/FluidReactor/outputModifier");
/*      */   public AudioSource audioSourceMain;
/*      */   public AudioSource audioSourceGeiger;
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\tileentity\TileEntityNuclearReactorElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */