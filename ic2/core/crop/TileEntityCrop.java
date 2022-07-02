/*      */ package ic2.core.crop;
/*      */ 
/*      */ import ic2.api.crops.BaseSeed;
/*      */ import ic2.api.crops.CropCard;
/*      */ import ic2.api.crops.Crops;
/*      */ import ic2.api.crops.ICropTile;
/*      */ import ic2.api.network.NetworkHelper;
/*      */ import ic2.core.IC2;
/*      */ import ic2.core.Ic2Player;
/*      */ import ic2.core.block.TileEntityBlock;
/*      */ import ic2.core.block.state.Ic2BlockState;
/*      */ import ic2.core.block.state.UnlistedProperty;
/*      */ import ic2.core.item.ItemCropSeed;
/*      */ import ic2.core.item.type.CropResItemType;
/*      */ import ic2.core.network.NetworkManager;
/*      */ import ic2.core.ref.FluidName;
/*      */ import ic2.core.ref.ItemName;
/*      */ import ic2.core.util.BiomeUtil;
/*      */ import ic2.core.util.LogCategory;
/*      */ import ic2.core.util.StackUtil;
/*      */ import ic2.core.util.Util;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.function.Function;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.Stream;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockFarmland;
/*      */ import net.minecraft.block.SoundType;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.world.EnumSkyBlock;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraftforge.common.EnumPlantType;
/*      */ import net.minecraftforge.common.property.IUnlistedProperty;
/*      */ import net.minecraftforge.fluids.FluidRegistry;
/*      */ import net.minecraftforge.fluids.FluidStack;
/*      */ import net.minecraftforge.fluids.FluidUtil;
/*      */ import net.minecraftforge.fluids.capability.IFluidHandler;
/*      */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*      */ import net.minecraftforge.oredict.OreDictionary;
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
/*      */ public class TileEntityCrop
/*      */   extends TileEntityBlock
/*      */   implements ICropTile
/*      */ {
/*      */   public TileEntityCrop() {
/*   83 */     if (debug) IC2.log.info(LogCategory.Crop, "Debug mode is running");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_145839_a(NBTTagCompound nbt) {
/*   92 */     super.func_145839_a(nbt);
/*      */     
/*   94 */     this.crossingBase = nbt.func_74767_n("crossingBase");
/*      */     
/*   96 */     if (nbt.func_74764_b("cropOwner") && nbt.func_74764_b("cropId")) {
/*   97 */       this.crop = Crops.instance.getCropCard(nbt.func_74779_i("cropOwner"), nbt.func_74779_i("cropId"));
/*   98 */       this.statGrowth = nbt.func_74771_c("statGrowth");
/*   99 */       this.statGain = nbt.func_74771_c("statGain");
/*  100 */       this.statResistance = nbt.func_74771_c("statResistance");
/*  101 */       this.storageNutrients = nbt.func_74765_d("storageNutrients");
/*  102 */       this.storageWater = nbt.func_74765_d("storageWater");
/*  103 */       this.storageWeedEX = nbt.func_74765_d("storageWeedEX");
/*  104 */       this.terrainHumidity = nbt.func_74771_c("terrainHumidity");
/*  105 */       this.terrainNutrients = nbt.func_74771_c("terrainNutrients");
/*  106 */       this.terrainAirQuality = nbt.func_74771_c("terrainAirQuality");
/*  107 */       this.currentSize = nbt.func_74771_c("currentSize");
/*  108 */       this.growthPoints = nbt.func_74765_d("growthPoints");
/*  109 */       this.scanLevel = nbt.func_74771_c("scanLevel");
/*  110 */       this.customData = nbt.func_74775_l("customData");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  119 */     super.func_189515_b(nbt);
/*      */     
/*  121 */     nbt.func_74757_a("crossingBase", this.crossingBase);
/*      */     
/*  123 */     if (this.crop != null) {
/*  124 */       nbt.func_74778_a("cropOwner", this.crop.getOwner());
/*  125 */       nbt.func_74778_a("cropId", this.crop.getId());
/*  126 */       nbt.func_74774_a("statGrowth", this.statGrowth);
/*  127 */       nbt.func_74774_a("statGain", this.statGain);
/*  128 */       nbt.func_74774_a("statResistance", this.statResistance);
/*  129 */       nbt.func_74777_a("storageNutrients", this.storageNutrients);
/*  130 */       nbt.func_74777_a("storageWater", this.storageWater);
/*  131 */       nbt.func_74777_a("storageWeedEX", this.storageWeedEX);
/*  132 */       nbt.func_74774_a("terrainHumidity", this.terrainHumidity);
/*  133 */       nbt.func_74774_a("terrainNutrients", this.terrainNutrients);
/*  134 */       nbt.func_74774_a("terrainAirQuality", this.terrainAirQuality);
/*  135 */       nbt.func_74774_a("currentSize", this.currentSize);
/*  136 */       nbt.func_74777_a("growthPoints", this.growthPoints);
/*  137 */       nbt.func_74774_a("scanLevel", this.scanLevel);
/*  138 */       nbt.func_74782_a("customData", (NBTBase)this.customData.func_74737_b());
/*      */     } 
/*      */     
/*  141 */     return nbt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<String> getNetworkedFields() {
/*  149 */     List<String> ret = new ArrayList<>();
/*      */     
/*  151 */     ret.add("crop");
/*  152 */     ret.add("currentSize");
/*  153 */     ret.add("statGrowth");
/*  154 */     ret.add("statGain");
/*  155 */     ret.add("statResistance");
/*  156 */     ret.add("storageNutrients");
/*  157 */     ret.add("storageWater");
/*  158 */     ret.add("storageWeedEX");
/*  159 */     ret.add("terrainHumidity");
/*  160 */     ret.add("terrainNutrients");
/*  161 */     ret.add("terrainAirQuality");
/*  162 */     ret.add("currentSize");
/*  163 */     ret.add("growthPoints");
/*  164 */     ret.add("scanLevel");
/*  165 */     ret.add("crossingBase");
/*  166 */     ret.add("customData");
/*      */     
/*  168 */     ret.addAll(super.getNetworkedFields());
/*      */     
/*  170 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onNetworkUpdate(String field) {
/*  175 */     updateRenderState();
/*  176 */     rerender();
/*  177 */     super.onNetworkUpdate(field);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected EnumPlantType getPlantType() {
/*  183 */     return EnumPlantType.Crop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onLoaded() {
/*  191 */     super.onLoaded();
/*      */     
/*  193 */     if (!func_145837_r()) this.ticker = IC2.random.nextInt(256);
/*      */     
/*  195 */     updateBiomeHumidityBonus();
/*      */     
/*  197 */     if ((func_145831_w()).field_72995_K) updateRenderState();
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityServer() {
/*  205 */     super.updateEntityServer();
/*      */     
/*  207 */     if (!func_145837_r()) this.ticker++;
/*      */     
/*  209 */     if (this.ticker % tickrate == 0L) performTick();
/*      */     
/*  211 */     if (this.dirty) {
/*  212 */       this.dirty = false;
/*  213 */       IBlockState state = this.field_145850_b.func_180495_p(this.field_174879_c);
/*  214 */       this.field_145850_b.func_184138_a(this.field_174879_c, state, state, 3);
/*  215 */       this.field_145850_b.func_190522_c(this.field_174879_c, (Block)getBlockType());
/*  216 */       this.field_145850_b.func_180500_c(EnumSkyBlock.BLOCK, this.field_174879_c);
/*      */       
/*  218 */       if (!this.field_145850_b.field_72995_K) {
/*  219 */         for (String field : getNetworkedFields()) {
/*  220 */           ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, field);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void performTick() {
/*  227 */     assert !this.field_145850_b.field_72995_K;
/*      */ 
/*      */     
/*  230 */     if (this.ticker % (tickrate * 10 << 2) == 0L) {
/*  231 */       updateBiomeHumidityBonus();
/*      */       
/*  233 */       if (debug) IC2.log.info(LogCategory.Crop, "Crop at %s - biomeHumidityBonus: %s", new Object[] { this.field_174879_c, Byte.valueOf(this.biomeHumidityBonus) });
/*      */     
/*      */     } 
/*  236 */     if (this.ticker % (tickrate << 2) == 0L) {
/*  237 */       updateTerrainHumidity();
/*      */       
/*  239 */       if (debug) IC2.log.info(LogCategory.Crop, "Crop at %s - terrain humidity: %s", new Object[] { this.field_174879_c, Byte.valueOf(this.terrainHumidity) });
/*      */     
/*      */     } 
/*  242 */     if ((this.ticker + tickrate) % (tickrate << 2) == 0L) {
/*  243 */       updateTerrainNutrients();
/*      */       
/*  245 */       if (debug) IC2.log.info(LogCategory.Crop, "Crop at %s - terrain nutrients: %s", new Object[] { this.field_174879_c, Byte.valueOf(this.terrainNutrients) });
/*      */     
/*      */     } 
/*  248 */     if ((this.ticker + (tickrate * 2)) % (tickrate << 2) == 0L) {
/*  249 */       updateTerrainAirQuality();
/*      */       
/*  251 */       if (debug) IC2.log.info(LogCategory.Crop, "Crop at %s - terrain air quality: %s", new Object[] { this.field_174879_c, Byte.valueOf(this.terrainAirQuality) });
/*      */     
/*      */     } 
/*      */     
/*  255 */     if (this.crop == null && (
/*  256 */       !isCrossingBase() || !attemptCrossing()) && (
/*  257 */       !isCrossingBase() || !attemptSpreading())) {
/*  258 */       if (IC2.random.nextInt(100) == 0 && getStorageWeedEX() <= 0) {
/*  259 */         reset();
/*  260 */         this.crop = IC2Crops.weed;
/*  261 */         setCurrentSize(1);
/*      */       } else {
/*  263 */         if (getStorageWeedEX() > 0 && IC2.random.nextInt(10) == 0) this.storageWeedEX = (short)(this.storageWeedEX - 1);
/*      */ 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */     }
/*      */     
/*  271 */     this.crop.tick(this);
/*      */     
/*  273 */     if (debug) System.out.println("Plant: " + getCrop().getUnlocalizedName());
/*      */     
/*  275 */     if (this.crop.canGrow(this)) {
/*  276 */       performGrowthTick();
/*      */       
/*  278 */       if (this.crop == null)
/*      */         return; 
/*  280 */       if (this.growthPoints >= this.crop.getGrowthDuration(this)) {
/*      */         
/*  282 */         this.growthPoints = 0;
/*  283 */         setCurrentSize(getCurrentSize() + 1);
/*  284 */         this.dirty = true;
/*      */       } 
/*      */     } 
/*      */     
/*  288 */     if (this.storageNutrients > 0) this.storageNutrients = (short)(this.storageNutrients - 1);
/*      */     
/*  290 */     if (this.storageWater > 0) this.storageWater = (short)(this.storageWater - 1);
/*      */     
/*  292 */     if (this.crop.isWeed(this) && IC2.random.nextInt(50) - getStatGrowth() <= 2) {
/*  293 */       performWeedWork();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void performGrowthTick() {
/*  300 */     if (this.crop == null)
/*      */       return; 
/*  302 */     if (debugGrowth) IC2.log.info(LogCategory.Crop, "Crop at %s - growth points (before): %s", new Object[] { this.field_174879_c, Short.valueOf(this.growthPoints) });
/*      */ 
/*      */     
/*  305 */     int totalGrowth = 0;
/*      */ 
/*      */     
/*  308 */     int baseGrowth = 3 + IC2.random.nextInt(7) + getStatGrowth();
/*      */ 
/*      */ 
/*      */     
/*  312 */     int minimumQuality = (this.crop.getProperties().getTier() - 1) * 4 + getStatGrowth() + this.statGain + this.statResistance;
/*  313 */     minimumQuality = (minimumQuality < 0) ? 0 : minimumQuality;
/*      */ 
/*      */     
/*  316 */     int providedQuality = this.crop.getWeightInfluences(this, getTerrainHumidity(), getTerrainNutrients(), getTerrainAirQuality()) * 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  322 */     if (providedQuality >= minimumQuality) {
/*  323 */       totalGrowth = baseGrowth * (100 + providedQuality - minimumQuality) / 100;
/*      */     } else {
/*  325 */       int aux = (minimumQuality - providedQuality) * 4;
/*  326 */       if (aux > 100 && IC2.random.nextInt(32) > this.statResistance) {
/*  327 */         reset();
/*  328 */         totalGrowth = 0;
/*      */       } else {
/*  330 */         totalGrowth = baseGrowth * (100 - aux) / 100;
/*  331 */         totalGrowth = (totalGrowth < 0) ? 0 : totalGrowth;
/*      */       } 
/*      */     } 
/*      */     
/*  335 */     this.growthPoints = (short)(this.growthPoints + totalGrowth);
/*      */     
/*  337 */     if (debugGrowth) {
/*  338 */       IC2.log.info(LogCategory.Crop, "Crop at %s - base growth: %s", new Object[] { this.field_174879_c, Integer.valueOf(baseGrowth) });
/*  339 */       IC2.log.info(LogCategory.Crop, "Crop at %s - minimum quality: %s", new Object[] { this.field_174879_c, Integer.valueOf(minimumQuality) });
/*  340 */       IC2.log.info(LogCategory.Crop, "Crop at %s - provided quality: %s", new Object[] { this.field_174879_c, Integer.valueOf(providedQuality) });
/*  341 */       IC2.log.info(LogCategory.Crop, "Crop at %s - total growth: %s", new Object[] { this.field_174879_c, Integer.valueOf(totalGrowth) });
/*  342 */       IC2.log.info(LogCategory.Crop, "Crop at %s - growth points (after): %s", new Object[] { this.field_174879_c, Short.valueOf(this.growthPoints) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performWeedWork() {
/*  353 */     World world = func_145831_w();
/*  354 */     BlockPos dstPos = this.field_174879_c.func_177972_a(EnumFacing.field_176754_o[IC2.random.nextInt(4)]);
/*  355 */     TileEntity dstRaw = world.func_175625_s(dstPos);
/*      */ 
/*      */     
/*  358 */     if (dstRaw instanceof TileEntityCrop) {
/*  359 */       if (debugWeedWork) {
/*  360 */         IC2.log.info(LogCategory.Crop, "Crop at %s - trying to generate weed", new Object[] { dstPos });
/*      */       }
/*  362 */       TileEntityCrop tileEntityCrop = (TileEntityCrop)dstRaw;
/*  363 */       CropCard neighborCrop = tileEntityCrop.getCrop();
/*      */       
/*  365 */       if (neighborCrop == null || (
/*  366 */         !neighborCrop.isWeed(tileEntityCrop) && IC2.random.nextInt(32) >= tileEntityCrop.getStatResistance() && 
/*  367 */         !tileEntityCrop.hasWeedEX())) {
/*      */ 
/*      */ 
/*      */         
/*  371 */         if (debugWeedWork) {
/*  372 */           IC2.log.info(LogCategory.Crop, "Crop at %s - weed generated", new Object[] { dstPos });
/*      */         }
/*      */         
/*  375 */         int newGrowth = Math.max(getStatGrowth(), tileEntityCrop.getStatGrowth());
/*      */         
/*  377 */         if (newGrowth < 31 && IC2.random.nextBoolean()) {
/*  378 */           newGrowth++;
/*      */         }
/*      */         
/*  381 */         tileEntityCrop.reset();
/*  382 */         tileEntityCrop.crop = Crops.weed;
/*  383 */         tileEntityCrop.setCurrentSize(1);
/*  384 */         tileEntityCrop.setStatGrowth(newGrowth);
/*      */       } 
/*  386 */     } else if (world.func_175623_d(dstPos)) {
/*  387 */       if (debugWeedWork) {
/*  388 */         IC2.log.info(LogCategory.Crop, "Block at %s - trying to generate grass", new Object[] { dstPos });
/*      */       }
/*      */       
/*  391 */       BlockPos soilPos = dstPos.func_177977_b();
/*  392 */       Block block = world.func_180495_p(soilPos).func_177230_c();
/*      */       
/*  394 */       if (block == Blocks.field_150346_d || block == Blocks.field_150349_c || block == Blocks.field_150458_ak) {
/*  395 */         world.func_180501_a(soilPos, Blocks.field_150349_c.func_176223_P(), 7);
/*      */         
/*  397 */         world.func_180501_a(dstPos, Blocks.field_150329_H.func_176203_a(1), 7);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasWeedEX() {
/*  407 */     if (this.storageWeedEX > 0) {
/*  408 */       this.storageWeedEX = (short)(this.storageWeedEX - 5);
/*  409 */       return true;
/*      */     } 
/*  411 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  419 */     if ((func_145831_w()).field_72995_K) {
/*  420 */       return true;
/*      */     }
/*      */     
/*  423 */     return rightClick(player, hand);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onClicked(EntityPlayer player) {
/*  429 */     if (this.crop != null) {
/*  430 */       this.crop.onLeftClick(this, player);
/*  431 */     } else if (this.crossingBase && 
/*  432 */       !(func_145831_w()).field_72995_K) {
/*  433 */       this.crossingBase = false;
/*  434 */       this.dirty = true;
/*  435 */       StackUtil.dropAsEntity(func_145831_w(), this.field_174879_c, ItemName.crop_stick.getItemStack());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SoundType getBlockSound(Entity entity) {
/*  443 */     return SoundType.field_185850_c;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onBlockBreak() {
/*  448 */     if (!(func_145831_w()).field_72995_K) {
/*  449 */       pick();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/*  458 */     List<AxisAlignedBB> ret = new ArrayList<>();
/*      */     
/*  460 */     if (forCollision) {
/*  461 */       ret.add(new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
/*      */     } else {
/*  463 */       ret.add(new AxisAlignedBB(0.20000000298023224D, -0.0625D, 0.20000000298023224D, 0.800000011920929D, 0.8500000238418579D, 0.800000011920929D));
/*      */     } 
/*      */     
/*  466 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rightClick(EntityPlayer player, EnumHand hand) {
/*  471 */     ItemStack heldItem = StackUtil.get(player, hand);
/*  472 */     boolean creative = player.field_71075_bZ.field_75098_d;
/*      */     
/*  474 */     if (!StackUtil.isEmpty(heldItem)) {
/*  475 */       if (this.crop == null && !this.crossingBase && heldItem.func_77973_b() == ItemName.crop_stick.getInstance()) {
/*      */         
/*  477 */         if (!creative) StackUtil.consumeOrError(player, hand, 1);
/*      */         
/*  479 */         this.crossingBase = true;
/*  480 */         this.dirty = true;
/*  481 */         return true;
/*      */       } 
/*      */       
/*  484 */       if (this.crop != null && StackUtil.checkItemEquality(heldItem, ItemName.crop_res.getItemStack((Enum)CropResItemType.fertilizer))) {
/*  485 */         if (applyFertilizer(true)) this.dirty = true;
/*      */         
/*  487 */         if (!creative) StackUtil.consumeOrError(player, hand, 1);
/*      */         
/*  489 */         return true;
/*      */       } 
/*      */       
/*  492 */       IFluidHandlerItem iFluidHandlerItem = FluidUtil.getFluidHandler(heldItem);
/*  493 */       if (iFluidHandlerItem != null) {
/*  494 */         if (applyHydration((IFluidHandler)iFluidHandlerItem) || applyWeedEx((IFluidHandler)iFluidHandlerItem, true)) this.dirty = true;
/*      */         
/*  496 */         return true;
/*      */       } 
/*      */ 
/*      */       
/*  500 */       if (this.crop == null && !this.crossingBase && Crops.instance.getBaseSeed(heldItem) != null) {
/*      */         
/*  502 */         reset();
/*  503 */         BaseSeed baseSeed = Crops.instance.getBaseSeed(heldItem);
/*  504 */         setCrop(baseSeed.crop);
/*  505 */         this.currentSize = (byte)baseSeed.size;
/*  506 */         this.statGain = (byte)baseSeed.statGain;
/*  507 */         this.statGrowth = (byte)baseSeed.statGrowth;
/*  508 */         this.statResistance = (byte)baseSeed.statResistance;
/*      */         
/*  510 */         if (!creative) StackUtil.consumeOrError(player, hand, 1);
/*      */         
/*  512 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  516 */     if (this.crop == null) return false;
/*      */     
/*  518 */     return this.crop.onRightClick(this, player);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryPlantIn(CropCard crop, int size, int statGr, int statGa, int statRe, int scan) {
/*  528 */     if (crop == null || crop == IC2Crops.weed || isCrossingBase()) return false;
/*      */     
/*  530 */     if (!crop.canGrow(this)) return false;
/*      */     
/*  532 */     reset();
/*      */     
/*  534 */     setCrop(crop);
/*  535 */     setCurrentSize(size);
/*  536 */     setStatGain(statGa);
/*  537 */     setStatGrowth(statGr);
/*  538 */     setStatResistance(statRe);
/*  539 */     setScanLevel(scan);
/*  540 */     NetworkHelper.sendInitialData((TileEntity)this);
/*  541 */     return true;
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
/*      */   public void onEntityCollision(Entity entity) {
/*  553 */     if (this.crop == null) {
/*      */       return;
/*      */     }
/*  556 */     if (this.crop.onEntityCollision(this, entity)) {
/*  557 */       if (this.field_145850_b.field_72995_K) {
/*      */         return;
/*      */       }
/*  560 */       if (IC2.random.nextInt(100) == 0 && IC2.random.nextInt(40) > this.statResistance) {
/*  561 */         reset();
/*      */         
/*  563 */         this.field_145850_b.func_180501_a(this.field_174879_c.func_177977_b(), Blocks.field_150346_d.func_176223_P(), 7);
/*      */         
/*  565 */         if (debugCollision) IC2.log.info(LogCategory.Crop, "Crop at %s - crop was trampled", new Object[] { this.field_174879_c });
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTerrainAirQuality() {
/*  576 */     int value = 0;
/*      */ 
/*      */     
/*  579 */     int height = (int)Math.floor((this.field_174879_c.func_177956_o() - 40) / 15.0D);
/*  580 */     if (height > 2) height = 2; 
/*  581 */     if (height < 0) height = 0; 
/*  582 */     value += height;
/*      */     
/*  584 */     int fresh = 9;
/*      */ 
/*      */     
/*  587 */     for (int x = this.field_174879_c.func_177958_n() - 1; x < this.field_174879_c.func_177958_n() + 1 && fresh > 0; x++) {
/*  588 */       for (int z = this.field_174879_c.func_177952_p() - 1; z < this.field_174879_c.func_177952_p() + 1 && fresh > 0; z++) {
/*  589 */         if (this.field_145850_b.func_175677_d(new BlockPos(x, this.field_174879_c.func_177956_o(), z), false) || this.field_145850_b
/*  590 */           .func_175625_s(new BlockPos(x, this.field_174879_c.func_177956_o(), z)) instanceof TileEntityCrop) {
/*  591 */           fresh--;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  596 */     value += fresh / 2;
/*      */     
/*  598 */     if (this.field_145850_b.func_175678_i(this.field_174879_c.func_177984_a())) value += 4;
/*      */     
/*  600 */     setTerrainAirQuality(value);
/*      */   }
/*      */   
/*      */   public void updateTerrainHumidity() {
/*  604 */     int humidity = this.biomeHumidityBonus;
/*      */ 
/*      */     
/*  607 */     if (((Integer)this.field_145850_b.func_180495_p(this.field_174879_c.func_177977_b()).func_177229_b((IProperty)BlockFarmland.field_176531_a)).intValue() >= 7) humidity += 2;
/*      */ 
/*      */     
/*  610 */     if (this.storageWater >= 5) humidity += 2;
/*      */ 
/*      */     
/*  613 */     humidity += (this.storageWater + 24) / 25;
/*      */     
/*  615 */     setTerrainHumidity(humidity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTerrainNutrients() {
/*  623 */     int nutrients = Crops.instance.getNutrientBiomeBonus(BiomeUtil.getBiome(this.field_145850_b, this.field_174879_c));
/*      */     
/*  625 */     for (int i = 1; i < 5 && 
/*  626 */       this.field_145850_b.func_180495_p(this.field_174879_c.func_177979_c(i)).func_177230_c() == Blocks.field_150346_d; i++) {
/*  627 */       nutrients++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  634 */     nutrients += (this.storageNutrients + 19) / 20;
/*      */     
/*  636 */     setTerrainNutrients(nutrients);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CropCard getCrop() {
/*  643 */     return this.crop;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCrop(CropCard crop) {
/*  648 */     this.crop = crop;
/*  649 */     updateTerrainHumidity();
/*  650 */     updateTerrainNutrients();
/*  651 */     updateTerrainAirQuality();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCurrentSize() {
/*  656 */     return this.currentSize;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCurrentSize(int size) {
/*  661 */     this.currentSize = (byte)size;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStatGrowth() {
/*  666 */     return this.statGrowth;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatGrowth(int growth) {
/*  671 */     this.statGrowth = (byte)growth;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStatGain() {
/*  676 */     return this.statGain;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatGain(int gain) {
/*  681 */     this.statGain = (byte)gain;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStatResistance() {
/*  686 */     return this.statResistance;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStatResistance(int resistance) {
/*  691 */     this.statResistance = (byte)resistance;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStorageNutrients() {
/*  696 */     return this.storageNutrients;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStorageNutrients(int nutrients) {
/*  701 */     this.storageNutrients = (short)nutrients;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStorageWater() {
/*  706 */     return this.storageWater;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStorageWater(int water) {
/*  711 */     this.storageWater = (short)water;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStorageWeedEX() {
/*  716 */     return this.storageWeedEX;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStorageWeedEX(int weedEX) {
/*  721 */     this.storageWeedEX = (short)weedEX;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTerrainAirQuality() {
/*  726 */     return this.terrainAirQuality;
/*      */   }
/*      */   
/*      */   public void setTerrainAirQuality(int value) {
/*  730 */     this.terrainAirQuality = (byte)value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTerrainHumidity() {
/*  735 */     return this.terrainHumidity;
/*      */   }
/*      */   
/*      */   public void setTerrainHumidity(int humidity) {
/*  739 */     this.terrainHumidity = (byte)humidity;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTerrainNutrients() {
/*  744 */     return this.terrainNutrients;
/*      */   }
/*      */   
/*      */   public void setTerrainNutrients(int nutrients) {
/*  748 */     this.terrainNutrients = (byte)nutrients;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getScanLevel() {
/*  753 */     return this.scanLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setScanLevel(int scanLevel) {
/*  758 */     this.scanLevel = (byte)scanLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getGrowthPoints() {
/*  763 */     return this.growthPoints;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGrowthPoints(int growthPoints) {
/*  768 */     this.growthPoints = (short)growthPoints;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCrossingBase() {
/*  773 */     return this.crossingBase;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCrossingBase(boolean crossingBase) {
/*  778 */     this.crossingBase = crossingBase;
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound getCustomData() {
/*  783 */     return this.customData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/*  790 */     return this.field_174879_c;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorldObj() {
/*  795 */     return this.field_145850_b;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public BlockPos getLocation() {
/*  802 */     return this.field_174879_c;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightLevel() {
/*  808 */     return this.field_145850_b.func_175699_k(this.field_174879_c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightValue() {
/*  814 */     return (this.crop == null) ? 0 : this.crop.getEmittedLight(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean pick() {
/*  820 */     if (this.crop == null) return false;
/*      */     
/*  822 */     boolean bonus = this.crop.canBeHarvested(this);
/*  823 */     float firstchance = this.crop.dropSeedChance(this);
/*      */     
/*  825 */     firstchance = (float)(firstchance * Math.pow(1.1D, this.statResistance));
/*      */ 
/*      */     
/*  828 */     int dropCount = 0;
/*      */     
/*  830 */     if (bonus)
/*  831 */     { if (this.field_145850_b.field_73012_v.nextFloat() <= (firstchance + 1.0F) * 0.8F) dropCount++; 
/*  832 */       float chance = this.crop.dropSeedChance(this) + getStatGrowth() / 100.0F;
/*      */       
/*  834 */       for (int i = 23; i < this.statGain; i++) {
/*  835 */         chance *= 0.95F;
/*      */       }
/*  837 */       if (this.field_145850_b.field_73012_v.nextFloat() <= chance) dropCount++;
/*      */        }
/*  839 */     else if (this.field_145850_b.field_73012_v.nextFloat() <= firstchance * 1.5F) { dropCount++; }
/*      */ 
/*      */     
/*  842 */     ItemStack[] drops = new ItemStack[dropCount];
/*  843 */     for (int index = 0; index < dropCount; ) { drops[index] = this.crop.getSeeds(this); index++; }
/*      */     
/*  845 */     reset();
/*      */     
/*  847 */     if (!this.field_145850_b.field_72995_K && drops.length > 0) {
/*  848 */       for (ItemStack drop : drops) {
/*  849 */         if (drop.func_77973_b() != ItemName.crop_seed_bag.getInstance()) drop.func_77982_d(null); 
/*  850 */         StackUtil.dropAsEntity(this.field_145850_b, this.field_174879_c, drop);
/*      */       } 
/*      */     }
/*      */     
/*  854 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean performManualHarvest() {
/*  859 */     List<ItemStack> dropItems = performHarvest();
/*      */     
/*  861 */     if (dropItems != null && !dropItems.isEmpty()) {
/*  862 */       for (ItemStack stack : dropItems) StackUtil.dropAsEntity(this.field_145850_b, this.field_174879_c, stack);
/*      */       
/*  864 */       return true;
/*      */     } 
/*      */     
/*  867 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ItemStack> performHarvest() {
/*  873 */     if (this.crop == null || !this.crop.canBeHarvested(this)) return null;
/*      */     
/*  875 */     double chance = this.crop.dropGainChance();
/*  876 */     chance *= Math.pow(1.03D, getStatGain());
/*      */     
/*  878 */     if (debug) {
/*  879 */       System.out.println("chance: " + chance);
/*  880 */       int simCount = 200;
/*  881 */       int sum = 0;
/*      */       
/*  883 */       for (int i = 0; i < 200; i++) {
/*  884 */         int j = (int)Math.max(0L, Math.round(IC2.random.nextGaussian() * chance * 0.6827D + chance));
/*  885 */         sum += j;
/*  886 */         System.out.print(j + " ");
/*      */       } 
/*      */       
/*  889 */       System.out.println();
/*  890 */       System.out.println("sum: " + sum + ", avg: " + (sum / 200.0D));
/*      */     } 
/*      */     
/*  893 */     int dropCount = (int)Math.max(0L, Math.round(IC2.random.nextGaussian() * chance * 0.6827D + chance));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  903 */     List<ItemStack> ret = (List<ItemStack>)IntStream.range(0, dropCount).mapToObj(i -> { ItemStack[] drops = this.crop.getGains(this); return Arrays.<ItemStack>stream(drops).map(()); }).flatMap(Function.identity()).collect(Collectors.toList());
/*      */     
/*  905 */     setCurrentSize(this.crop.getSizeAfterHarvest(this));
/*  906 */     this.dirty = true;
/*      */     
/*  908 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  917 */     this.crop = null;
/*  918 */     this.customData = new NBTTagCompound();
/*  919 */     this.statGain = 0;
/*  920 */     this.statResistance = 0;
/*  921 */     this.statGrowth = 0;
/*  922 */     this.terrainAirQuality = -1;
/*  923 */     this.terrainHumidity = -1;
/*  924 */     this.terrainNutrients = -1;
/*  925 */     this.growthPoints = 0;
/*  926 */     this.scanLevel = 0;
/*  927 */     this.currentSize = 1;
/*  928 */     this.dirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateState() {
/*  933 */     this.field_145850_b.func_175704_b(this.field_174879_c, this.field_174879_c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockBelow(Block required) {
/*  944 */     if (this.crop == null) return false;
/*      */     
/*  946 */     for (int index = 1; index < this.crop.getRootsLength(this); index++) {
/*  947 */       BlockPos blockPos = this.field_174879_c.func_177979_c(index);
/*  948 */       IBlockState state = this.field_145850_b.func_180495_p(blockPos);
/*  949 */       Block block = state.func_177230_c();
/*      */       
/*  951 */       if (block.isAir(state, (IBlockAccess)this.field_145850_b, blockPos))
/*  952 */         return false; 
/*  953 */       if (block == required) {
/*  954 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  958 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockBelow(String oreDictionaryEntry) {
/*  969 */     if (this.crop == null) return false;
/*      */     
/*  971 */     for (int index = 1; index < this.crop.getRootsLength(this); index++) {
/*  972 */       BlockPos blockPos = this.field_174879_c.func_177979_c(index);
/*  973 */       IBlockState state = this.field_145850_b.func_180495_p(blockPos);
/*  974 */       Block block = state.func_177230_c();
/*      */       
/*  976 */       if (block.isAir(state, (IBlockAccess)this.field_145850_b, blockPos)) return false;
/*      */       
/*  978 */       ItemStack stackBelow = StackUtil.getPickStack(this.field_145850_b, blockPos, state, Ic2Player.get(this.field_145850_b));
/*  979 */       for (ItemStack stack : OreDictionary.getOres(oreDictionaryEntry)) {
/*  980 */         if (StackUtil.checkItemEquality(stackBelow, stack)) return true; 
/*      */       } 
/*      */     } 
/*  983 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack generateSeeds(CropCard crop, int growth, int gain, int resistance, int scan) {
/*  990 */     return ItemCropSeed.generateItemStackFromValues(crop, growth, gain, resistance, scan);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getLightOpacity() {
/*  996 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Ic2BlockState.Ic2BlockStateInstance getExtendedState(Ic2BlockState.Ic2BlockStateInstance state) {
/* 1001 */     state = super.getExtendedState(state);
/*      */     
/* 1003 */     CropRenderState renderState = this.cropRenderState;
/* 1004 */     if (renderState != null) state = state.withProperties(new Object[] { renderStateProperty, renderState });
/*      */     
/* 1006 */     return state;
/*      */   }
/*      */   
/*      */   private void updateRenderState() {
/* 1010 */     this.cropRenderState = new CropRenderState(this.crop, getCurrentSize(), this.crossingBase);
/*      */   }
/*      */   public static class CropRenderState { public final CropCard crop;
/*      */     
/*      */     public CropRenderState(CropCard crop, int size, boolean crosscrop) {
/* 1015 */       this.crop = crop;
/* 1016 */       this.size = size;
/* 1017 */       this.crosscrop = crosscrop;
/*      */     }
/*      */     public final int size; public final boolean crosscrop;
/*      */     
/*      */     public int hashCode() {
/* 1022 */       int ret = (this.crop != null) ? this.crop.hashCode() : 1;
/* 1023 */       ret = ret * 31 + (this.size + 1) * 5;
/* 1024 */       ret = ret * 31 + (this.crosscrop ? 1 : 0);
/*      */       
/* 1026 */       return ret;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/* 1031 */       if (obj == this) return true;
/*      */       
/* 1033 */       if (!(obj instanceof CropRenderState)) return false;
/*      */       
/* 1035 */       CropRenderState other = (CropRenderState)obj;
/*      */       
/* 1037 */       return (other.crop == this.crop && other.size == this.size && other.crosscrop == this.crosscrop);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1042 */       return "CropState<" + this.crop + ", " + this.size + ", " + this.crosscrop + '>';
/*      */     } }
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
/*      */   public boolean wrenchCanRemove(EntityPlayer player) {
/* 1056 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
/* 1062 */     return (this.crop == null) ? ItemName.crop_stick.getItemStack() : generateSeeds(this.crop, this.statGrowth, this.statGain, this.statResistance, this.scanLevel);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean attemptCrossing() {
/* 1071 */     if (IC2.random.nextInt(3) != 0) return false;
/*      */     
/* 1073 */     List<TileEntityCrop> neighbours = new ArrayList<>(4);
/*      */     
/* 1075 */     checkCrossingAvailability(this.field_174879_c.func_177978_c(), neighbours);
/* 1076 */     checkCrossingAvailability(this.field_174879_c.func_177968_d(), neighbours);
/* 1077 */     checkCrossingAvailability(this.field_174879_c.func_177974_f(), neighbours);
/* 1078 */     checkCrossingAvailability(this.field_174879_c.func_177976_e(), neighbours);
/*      */     
/* 1080 */     if (debug) {
/* 1081 */       System.out.print("Attempted cross with " + neighbours.size() + " plants: ");
/* 1082 */       for (TileEntityCrop neighbour : neighbours) System.out.println(neighbour.getCrop().getUnlocalizedName()); 
/* 1083 */       System.out.println();
/*      */     } 
/*      */ 
/*      */     
/* 1087 */     if (neighbours.size() < 2) return false;
/*      */ 
/*      */     
/* 1090 */     CropCard[] crops = (CropCard[])Crops.instance.getCrops().toArray((Object[])new CropCard[0]);
/* 1091 */     if (crops.length == 0) return false;
/*      */     
/* 1093 */     int[] ratios = new int[crops.length];
/* 1094 */     int total = 0;
/*      */     
/* 1096 */     for (int index = 0; index < ratios.length; index++) {
/* 1097 */       CropCard crop = crops[index];
/*      */ 
/*      */       
/* 1100 */       if (crop.canGrow(this))
/* 1101 */         for (TileEntityCrop neighbour : neighbours) total += calculateRatioFor(crop, neighbour.getCrop());
/*      */          
/* 1103 */       ratios[index] = total;
/*      */     } 
/*      */ 
/*      */     
/* 1107 */     if (debugChance) {
/* 1108 */       int lastChance = 0;
/*      */       
/* 1110 */       for (int i = 0; i < crops.length; i++) {
/* 1111 */         int currentChance = ratios[i];
/* 1112 */         System.out.println(String.format("%s: %.1f%% %d%n", new Object[] { crops[i].getUnlocalizedName(), Double.valueOf((currentChance - lastChance) * 100.0D / total), Integer.valueOf(ratios[i]) }));
/* 1113 */         lastChance = currentChance;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1118 */     int search = IC2.random.nextInt(total);
/* 1119 */     if (debug) System.out.printf("rnd: %d / %d%n", new Object[] { Integer.valueOf(search), Integer.valueOf(total) });
/*      */ 
/*      */ 
/*      */     
/* 1123 */     int min = 0;
/* 1124 */     int max = ratios.length - 1;
/*      */     
/* 1126 */     while (min < max) {
/* 1127 */       int cur = (min + max) / 2;
/* 1128 */       int value = ratios[cur];
/* 1129 */       if (debug) System.out.printf("min: %d, max: %d, cur: %d, value: %d%n", new Object[] { Integer.valueOf(min), Integer.valueOf(max), Integer.valueOf(cur), Integer.valueOf(value) });
/*      */       
/* 1131 */       if (search < value) {
/* 1132 */         max = cur; continue;
/*      */       } 
/* 1134 */       min = cur + 1;
/*      */     } 
/*      */     
/* 1137 */     if (debug) System.out.printf("result: %s (%d %d)%n", new Object[] { crops[min].getUnlocalizedName(), Integer.valueOf(min), Integer.valueOf(max) });
/*      */     
/* 1139 */     assert min == max;
/* 1140 */     assert min >= 0 && min < ratios.length;
/* 1141 */     assert ratios[min] > search;
/* 1142 */     assert min == 0 || ratios[min - 1] <= search;
/*      */ 
/*      */ 
/*      */     
/* 1146 */     setCrossingBase(false);
/* 1147 */     setCrop(crops[min]);
/* 1148 */     this.dirty = true;
/* 1149 */     setCurrentSize(1);
/*      */ 
/*      */     
/* 1152 */     this.statGrowth = 0;
/* 1153 */     this.statResistance = 0;
/* 1154 */     this.statGain = 0;
/*      */     
/* 1156 */     for (TileEntityCrop neighbour : neighbours) {
/* 1157 */       this.statGrowth = (byte)(this.statGrowth + neighbour.statGrowth);
/* 1158 */       this.statResistance = (byte)(this.statResistance + neighbour.statResistance);
/* 1159 */       this.statGain = (byte)(this.statGain + neighbour.statGain);
/*      */     } 
/*      */     
/* 1162 */     int count = neighbours.size();
/*      */     
/* 1164 */     this.statGrowth = (byte)(this.statGrowth / count);
/* 1165 */     this.statResistance = (byte)(this.statResistance / count);
/* 1166 */     this.statGain = (byte)(this.statGain / count);
/*      */     
/* 1168 */     this.statGrowth = (byte)(this.statGrowth + IC2.random.nextInt(1 + 2 * count) - count);
/* 1169 */     this.statGain = (byte)(this.statGain + IC2.random.nextInt(1 + 2 * count) - count);
/* 1170 */     this.statResistance = (byte)(this.statResistance + IC2.random.nextInt(1 + 2 * count) - count);
/*      */     
/* 1172 */     this.statGrowth = (byte)Util.limit(this.statGrowth, 0, 31);
/* 1173 */     this.statGain = (byte)Util.limit(this.statGain, 0, 31);
/* 1174 */     this.statResistance = (byte)Util.limit(this.statResistance, 0, 31);
/*      */     
/* 1176 */     return true;
/*      */   }
/*      */   
/*      */   private boolean attemptSpreading() {
/* 1180 */     List<TileEntityCrop> neighbours = new ArrayList<>(4);
/*      */     
/* 1182 */     for (EnumFacing direction : EnumFacing.field_176754_o) {
/* 1183 */       TileEntity tileEntity = func_145831_w().func_175625_s(this.field_174879_c.func_177972_a(direction));
/*      */       
/* 1185 */       if (tileEntity instanceof TileEntityCrop) {
/*      */         
/* 1187 */         TileEntityCrop tileEntityCrop = (TileEntityCrop)tileEntity;
/*      */         
/* 1189 */         neighbours.add(tileEntityCrop);
/*      */       } 
/*      */     } 
/* 1192 */     if (neighbours.size() != 1) return false;
/*      */     
/* 1194 */     TileEntityCrop sideCrop = neighbours.get(0);
/*      */     
/* 1196 */     CropCard neighborCrop = sideCrop.getCrop();
/* 1197 */     if (neighborCrop == null) return false;
/*      */     
/* 1199 */     if (!neighborCrop.canGrow(this) || !neighborCrop.canCross(sideCrop)) return false;
/*      */     
/* 1201 */     int base = 4;
/*      */     
/* 1203 */     if (sideCrop.statGrowth >= 16) base++; 
/* 1204 */     if (sideCrop.statGrowth >= 30) base++; 
/* 1205 */     if (sideCrop.statResistance >= 28) base += 27 - sideCrop.statResistance;
/*      */ 
/*      */     
/* 1208 */     if (base < IC2.random.nextInt(16)) return false;
/*      */     
/* 1210 */     setCrossingBase(false);
/* 1211 */     setCrop(sideCrop.crop);
/* 1212 */     this.dirty = true;
/* 1213 */     setCurrentSize(1);
/*      */ 
/*      */     
/* 1216 */     this.statGrowth = sideCrop.statGrowth;
/* 1217 */     this.statResistance = sideCrop.statResistance;
/* 1218 */     this.statGain = sideCrop.statGain;
/*      */     
/* 1220 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int calculateRatioFor(CropCard newCrop, CropCard oldCrop) {
/* 1231 */     if (newCrop == oldCrop) return 500;
/*      */     
/* 1233 */     int value = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1238 */     int[] propOld = oldCrop.getProperties().getAllProperties();
/* 1239 */     int[] propNew = newCrop.getProperties().getAllProperties();
/* 1240 */     assert propOld.length == propNew.length;
/*      */     int i;
/* 1242 */     for (i = 0; i < 5; i++) {
/* 1243 */       int delta = Math.abs(propOld[i] - propNew[i]);
/*      */       
/* 1245 */       value += -delta + 2;
/*      */     } 
/*      */     
/*      */     String[] arrayOfString;
/*      */     
/*      */     int j;
/* 1251 */     for (arrayOfString = newCrop.getAttributes(), j = arrayOfString.length, i = 0; i < j; ) { String attributeNew = arrayOfString[i];
/* 1252 */       for (String attributeOld : oldCrop.getAttributes()) {
/* 1253 */         if (attributeNew.equalsIgnoreCase(attributeOld)) {
/* 1254 */           value += 5;
/*      */         }
/*      */       } 
/*      */       
/*      */       i++; }
/*      */ 
/*      */     
/* 1261 */     int diff = newCrop.getProperties().getTier() - oldCrop.getProperties().getTier();
/*      */     
/* 1263 */     if (diff > 1) {
/* 1264 */       value -= 2 * diff;
/*      */     }
/*      */     
/* 1267 */     if (diff < -3) {
/* 1268 */       value -= -diff;
/*      */     }
/*      */     
/* 1271 */     return Math.max(value, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCrossingAvailability(BlockPos pos, List<TileEntityCrop> crops) {
/* 1282 */     TileEntity tile = func_145831_w().func_175625_s(pos);
/*      */     
/* 1284 */     if (!(tile instanceof TileEntityCrop))
/*      */       return; 
/* 1286 */     TileEntityCrop sideCrop = (TileEntityCrop)tile;
/* 1287 */     CropCard neighborCrop = sideCrop.getCrop();
/* 1288 */     if (neighborCrop == null)
/*      */       return; 
/* 1290 */     if (!neighborCrop.canGrow(this) || !neighborCrop.canCross(sideCrop))
/*      */       return; 
/* 1292 */     int base = 4;
/*      */     
/* 1294 */     if (sideCrop.statGrowth >= 16) base++; 
/* 1295 */     if (sideCrop.statGrowth >= 30) base++; 
/* 1296 */     if (sideCrop.statResistance >= 28) base += 27 - sideCrop.statResistance;
/*      */     
/* 1298 */     if (base >= IC2.random.nextInt(16)) {
/* 1299 */       crops.add(sideCrop);
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkSpreadingAvailability(BlockPos pos, TileEntityCrop crop) {
/* 1304 */     TileEntity tile = func_145831_w().func_175625_s(pos);
/*      */     
/* 1306 */     if (!(tile instanceof TileEntityCrop))
/*      */       return; 
/* 1308 */     TileEntityCrop sideCrop = (TileEntityCrop)tile;
/* 1309 */     CropCard neighborCrop = sideCrop.getCrop();
/* 1310 */     if (neighborCrop == null)
/*      */       return; 
/* 1312 */     if (!neighborCrop.canGrow(this) || !neighborCrop.canCross(sideCrop))
/*      */       return; 
/* 1314 */     int base = 4;
/*      */     
/* 1316 */     if (sideCrop.statGrowth >= 16) base++; 
/* 1317 */     if (sideCrop.statGrowth >= 30) base++; 
/* 1318 */     if (sideCrop.statResistance >= 28) base += 27 - sideCrop.statResistance;
/*      */     
/* 1320 */     if (base >= IC2.random.nextInt(16)) {
/* 1321 */       crop = sideCrop;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/* 1327 */     super.onNeighborChange(neighbor, neighborPos);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1335 */     if (this.field_145850_b.func_180495_p(this.field_174879_c.func_177977_b()).func_177230_c() != Blocks.field_150458_ak) {
/*      */       
/* 1337 */       pick();
/* 1338 */       this.field_145850_b.func_175698_g(this.field_174879_c);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean applyHydration(IFluidHandler handler) {
/* 1343 */     int limit = 200;
/* 1344 */     if (this.storageWater >= limit) return false;
/*      */     
/* 1346 */     FluidStack stack = handler.drain(new FluidStack(FluidRegistry.WATER, limit - this.storageWater), true);
/* 1347 */     if (stack == null || stack.amount <= 0) return false;
/*      */     
/* 1349 */     this.storageWater = (short)(this.storageWater + stack.amount);
/*      */     
/* 1351 */     return true;
/*      */   }
/*      */   
/*      */   public boolean applyWeedEx(IFluidHandler handler, boolean manual) {
/* 1355 */     int limit = manual ? 100 : 150;
/* 1356 */     if (this.storageWeedEX >= limit) {
/* 1357 */       return false;
/*      */     }
/* 1359 */     FluidStack stack = handler.drain(new FluidStack(FluidName.weed_ex.getInstance(), limit - this.storageWeedEX), true);
/* 1360 */     if (stack == null || stack.amount <= 0) {
/* 1361 */       return false;
/*      */     }
/* 1363 */     this.storageWeedEX = (short)(this.storageWeedEX + stack.amount);
/* 1364 */     return true;
/*      */   }
/*      */   
/*      */   public boolean applyFertilizer(boolean manual) {
/* 1368 */     if (this.storageNutrients >= 100) return false; 
/* 1369 */     this.storageNutrients = (short)(this.storageNutrients + (manual ? 100 : 90));
/* 1370 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBiomeHumidityBonus() {
/* 1376 */     Biome biome = BiomeUtil.getBiome(this.field_145850_b, this.field_174879_c);
/*      */ 
/*      */     
/* 1379 */     float rainfall = biome.func_76727_i();
/* 1380 */     int rainfallBonus = (int)((25.0F * rainfall) - 12.5D);
/* 1381 */     rainfallBonus = (rainfallBonus > 10) ? 10 : ((rainfallBonus < -10) ? -10 : rainfallBonus);
/*      */ 
/*      */     
/* 1384 */     float temperature = biome.func_180626_a(this.field_174879_c);
/* 1385 */     int coefficientBonus = (int)(Math.abs(rainfallBonus) * (-2.0D * Math.pow(temperature, 2.0D) + (4.0F * temperature) - 1.0D));
/* 1386 */     coefficientBonus = (coefficientBonus > 10) ? 10 : ((coefficientBonus < -10) ? -10 : coefficientBonus);
/*      */     
/* 1388 */     if (debug) IC2.log.info(LogCategory.Crop, "Crop at %s - r bonus %d, t/r coefficient bonus %d", new Object[] { this.field_174879_c, Integer.valueOf(rainfallBonus), Integer.valueOf(coefficientBonus) });
/*      */     
/* 1390 */     this.biomeHumidityBonus = (byte)(rainfallBonus + coefficientBonus);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1395 */   private long ticker = 0L;
/*      */   public boolean dirty = true;
/* 1397 */   public static int tickrate = 256;
/*      */   
/* 1399 */   private CropCard crop = null;
/*      */ 
/*      */   
/* 1402 */   private byte biomeHumidityBonus = 0;
/*      */ 
/*      */   
/*      */   private byte statGrowth;
/*      */ 
/*      */   
/*      */   private byte statGain;
/*      */ 
/*      */   
/*      */   private byte statResistance;
/*      */ 
/*      */   
/*      */   private short storageNutrients;
/*      */   
/*      */   private short storageWater;
/*      */   
/*      */   private short storageWeedEX;
/*      */   
/*      */   private byte terrainAirQuality;
/*      */   
/*      */   private byte terrainHumidity;
/*      */   
/*      */   private byte terrainNutrients;
/*      */   
/*      */   private byte currentSize;
/*      */   
/* 1428 */   private short growthPoints = 0;
/*      */   
/*      */   private byte scanLevel;
/*      */   private boolean crossingBase;
/* 1432 */   private NBTTagCompound customData = new NBTTagCompound();
/*      */ 
/*      */   
/* 1435 */   public static final IUnlistedProperty<CropRenderState> renderStateProperty = (IUnlistedProperty<CropRenderState>)new UnlistedProperty("renderstate", CropRenderState.class);
/*      */ 
/*      */   
/*      */   private volatile CropRenderState cropRenderState;
/*      */   
/* 1440 */   public static final boolean debug = (System.getProperty("ic2.crops.debug") != null);
/* 1441 */   public static final boolean debugChance = (debug && System.getProperty("ic2.crops.debug").contains("chance"));
/* 1442 */   public static final boolean debugGrowth = (debug && System.getProperty("ic2.crops.debug").contains("growth"));
/* 1443 */   public static final boolean debugWeedWork = (debug && System.getProperty("ic2.crops.debug").contains("weedwork"));
/* 1444 */   public static final boolean debugCollision = (debug && System.getProperty("ic2.crops.debug").contains("collision"));
/* 1445 */   public static final boolean debugTerrain = (debug && System.getProperty("ic2.crops.debug").contains("terrain"));
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\TileEntityCrop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */