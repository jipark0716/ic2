/*     */ package ic2.core.ref;
/*     */ 
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.BlockTileEntity;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.TileEntityBarrel;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityWall;
/*     */ import ic2.core.block.generator.tileentity.TileEntityCreativeGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityRTGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySemifluidGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityStirlingGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWaterGenerator;
/*     */ import ic2.core.block.generator.tileentity.TileEntityWindGenerator;
/*     */ import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
/*     */ import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
/*     */ import ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator;
/*     */ import ic2.core.block.heatgenerator.tileentity.TileEntitySolidHeatGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityManualKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
/*     */ import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
/*     */ import ic2.core.block.machine.tileentity.ITnt;
/*     */ import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityAssemblyBench;
/*     */ import ic2.core.block.machine.tileentity.TileEntityBatchCrafter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityBetterItemBuffer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityBlastFurnace;
/*     */ import ic2.core.block.machine.tileentity.TileEntityBlockCutter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCentrifuge;
/*     */ import ic2.core.block.machine.tileentity.TileEntityChunkloader;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCompressor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCondenser;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
/*     */ import ic2.core.block.machine.tileentity.TileEntityCropmatron;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectricFurnace;
/*     */ import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityExtractor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityFermenter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityFluidBottler;
/*     */ import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityInduction;
/*     */ import ic2.core.block.machine.tileentity.TileEntityIndustrialWorkbench;
/*     */ import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
/*     */ import ic2.core.block.machine.tileentity.TileEntityItemBuffer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMacerator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMassFabricator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMetalFormer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMiner;
/*     */ import ic2.core.block.machine.tileentity.TileEntityNuke;
/*     */ import ic2.core.block.machine.tileentity.TileEntityOreWashing;
/*     */ import ic2.core.block.machine.tileentity.TileEntityPatternStorage;
/*     */ import ic2.core.block.machine.tileentity.TileEntityPump;
/*     */ import ic2.core.block.machine.tileentity.TileEntityRecycler;
/*     */ import ic2.core.block.machine.tileentity.TileEntityReplicator;
/*     */ import ic2.core.block.machine.tileentity.TileEntityScanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySolidCanner;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySortingMachine;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
/*     */ import ic2.core.block.machine.tileentity.TileEntitySteamRepressurizer;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTank;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTeleporter;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import ic2.core.block.machine.tileentity.TileEntityTesla;
/*     */ import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
/*     */ import ic2.core.block.machine.tileentity.TileEntityWeightedItemDistributor;
/*     */ import ic2.core.block.personal.TileEntityEnergyOMat;
/*     */ import ic2.core.block.personal.TileEntityPersonalChest;
/*     */ import ic2.core.block.personal.TileEntityTradeOMat;
/*     */ import ic2.core.block.personal.TileEntityTradingTerminal;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityRCI_LZH;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityRCI_RSH;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityReactorAccessHatch;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityReactorChamberElectric;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityReactorFluidPort;
/*     */ import ic2.core.block.reactor.tileentity.TileEntityReactorRedstonePort;
/*     */ import ic2.core.block.steam.TileEntityCokeKiln;
/*     */ import ic2.core.block.steam.TileEntityCokeKilnGrate;
/*     */ import ic2.core.block.steam.TileEntityCokeKilnHatch;
/*     */ import ic2.core.block.storage.box.TileEntityBronzeStorageBox;
/*     */ import ic2.core.block.storage.box.TileEntityIridiumStorageBox;
/*     */ import ic2.core.block.storage.box.TileEntityIronStorageBox;
/*     */ import ic2.core.block.storage.box.TileEntitySteelStorageBox;
/*     */ import ic2.core.block.storage.box.TileEntityWoodenStorageBox;
/*     */ import ic2.core.block.storage.tank.TileEntityBronzeTank;
/*     */ import ic2.core.block.storage.tank.TileEntityIridiumTank;
/*     */ import ic2.core.block.storage.tank.TileEntityIronTank;
/*     */ import ic2.core.block.storage.tank.TileEntitySteelTank;
/*     */ import ic2.core.block.transport.TileEntityFluidPipe;
/*     */ import ic2.core.block.wiring.TileEntityCable;
/*     */ import ic2.core.block.wiring.TileEntityCableDetector;
/*     */ import ic2.core.block.wiring.TileEntityCableSplitter;
/*     */ import ic2.core.block.wiring.TileEntityChargepadBatBox;
/*     */ import ic2.core.block.wiring.TileEntityChargepadCESU;
/*     */ import ic2.core.block.wiring.TileEntityChargepadMFE;
/*     */ import ic2.core.block.wiring.TileEntityChargepadMFSU;
/*     */ import ic2.core.block.wiring.TileEntityElectricBatBox;
/*     */ import ic2.core.block.wiring.TileEntityElectricCESU;
/*     */ import ic2.core.block.wiring.TileEntityElectricMFE;
/*     */ import ic2.core.block.wiring.TileEntityElectricMFSU;
/*     */ import ic2.core.block.wiring.TileEntityLuminator;
/*     */ import ic2.core.block.wiring.TileEntityTransformerEV;
/*     */ import ic2.core.block.wiring.TileEntityTransformerHV;
/*     */ import ic2.core.block.wiring.TileEntityTransformerLV;
/*     */ import ic2.core.block.wiring.TileEntityTransformerMV;
/*     */ import ic2.core.crop.TileEntityCrop;
/*     */ import ic2.core.item.block.ItemBlockTileEntity;
/*     */ import ic2.core.profile.Version;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.lang.annotation.Documented;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Inherited;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import net.minecraftforge.fml.common.ModContainer;
/*     */ import net.minecraftforge.fml.common.registry.GameRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TeBlock
/*     */   implements ITeBlock, ITeBlock.ITeBlockCreativeRegisterer
/*     */ {
/* 154 */   invalid(null, 0, false, Util.noFacings, false, HarvestTool.None, DefaultDrop.None, 5.0F, 10.0F, EnumRarity.COMMON),
/*     */ 
/*     */   
/* 157 */   barrel((Class)TileEntityBarrel.class, -1, true, Util.horizontalFacings, false, HarvestTool.Axe, DefaultDrop.None, 2.0F, 6.0F, EnumRarity.COMMON),
/*     */   
/* 159 */   wall((Class)TileEntityWall.class, -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 3.0F, 30.0F, EnumRarity.COMMON),
/*     */   
/* 161 */   itnt((Class)ITnt.class, 1, false, Util.horizontalFacings, false, HarvestTool.None, DefaultDrop.Self, 0.0F, 0.0F, EnumRarity.COMMON),
/* 162 */   nuke(TileEntityNuke.delegate(), 2, false, Util.horizontalFacings, false, HarvestTool.None, DefaultDrop.Self, 0.0F, 0.0F, EnumRarity.UNCOMMON),
/*     */   
/* 164 */   generator((Class)TileEntityGenerator.class, 3, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 165 */   geo_generator((Class)TileEntityGeoGenerator.class, 4, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 166 */   kinetic_generator((Class)TileEntityKineticGenerator.class, 5, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 167 */   rt_generator((Class)TileEntityRTGenerator.class, 6, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 168 */   semifluid_generator((Class)TileEntitySemifluidGenerator.class, 7, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 169 */   solar_generator((Class)TileEntitySolarGenerator.class, 8, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 170 */   stirling_generator((Class)TileEntityStirlingGenerator.class, 9, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 171 */   water_generator((Class)TileEntityWaterGenerator.class, 10, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 172 */   wind_generator((Class)TileEntityWindGenerator.class, 11, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 174 */   electric_heat_generator((Class)TileEntityElectricHeatGenerator.class, 12, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 175 */   fluid_heat_generator((Class)TileEntityFluidHeatGenerator.class, 13, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 176 */   rt_heat_generator((Class)TileEntityRTHeatGenerator.class, 14, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 177 */   solid_heat_generator((Class)TileEntitySolidHeatGenerator.class, 15, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 179 */   electric_kinetic_generator((Class)TileEntityElectricKineticGenerator.class, 16, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 180 */   manual_kinetic_generator((Class)TileEntityManualKineticGenerator.class, 17, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0F, 10.0F, EnumRarity.COMMON),
/* 181 */   steam_kinetic_generator((Class)TileEntitySteamKineticGenerator.class, 18, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 182 */   stirling_kinetic_generator((Class)TileEntityStirlingKineticGenerator.class, 19, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 183 */   water_kinetic_generator((Class)TileEntityWaterKineticGenerator.class, 20, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 184 */   wind_kinetic_generator((Class)TileEntityWindKineticGenerator.class, 21, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 186 */   nuclear_reactor((Class)TileEntityNuclearReactorElectric.class, 22, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Generator, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 187 */   reactor_access_hatch((Class)TileEntityReactorAccessHatch.class, 23, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0F, 90.0F, EnumRarity.UNCOMMON),
/* 188 */   reactor_chamber((Class)TileEntityReactorChamberElectric.class, 24, false, Util.onlyNorth, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 189 */   reactor_fluid_port((Class)TileEntityReactorFluidPort.class, 25, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0F, 90.0F, EnumRarity.UNCOMMON),
/* 190 */   reactor_redstone_port((Class)TileEntityReactorRedstonePort.class, 26, false, Util.onlyNorth, false, HarvestTool.Pickaxe, DefaultDrop.Self, 40.0F, 90.0F, EnumRarity.UNCOMMON),
/*     */   
/* 192 */   condenser((Class)TileEntityCondenser.class, 27, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 193 */   fluid_bottler((Class)TileEntityFluidBottler.class, 28, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 194 */   fluid_distributor((Class)TileEntityFluidDistributor.class, 29, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 195 */   fluid_regulator((Class)TileEntityFluidRegulator.class, 30, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 196 */   liquid_heat_exchanger((Class)TileEntityLiquidHeatExchanger.class, 31, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 197 */   pump((Class)TileEntityPump.class, 32, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 198 */   solar_distiller((Class)TileEntitySolarDestiller.class, 33, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 199 */   steam_generator((Class)TileEntitySteamGenerator.class, 34, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 201 */   item_buffer((Class)TileEntityItemBuffer.class, 35, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 202 */   luminator_flat((Class)TileEntityLuminator.class, 36, true, Util.allFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0F, 10.0F, EnumRarity.COMMON),
/* 203 */   magnetizer((Class)TileEntityMagnetizer.class, 37, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 204 */   sorting_machine((Class)TileEntitySortingMachine.class, 38, false, Util.onlyNorth, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 205 */   teleporter((Class)TileEntityTeleporter.class, 39, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.RARE, IC2Material.MACHINE, false),
/* 206 */   terraformer((Class)TileEntityTerra.class, 40, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 207 */   tesla_coil((Class)TileEntityTesla.class, 41, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 209 */   canner(TileEntityCanner.delegate(), 42, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 210 */   compressor((Class)TileEntityCompressor.class, 43, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 211 */   electric_furnace((Class)TileEntityElectricFurnace.class, 44, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 212 */   extractor((Class)TileEntityExtractor.class, 45, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 213 */   iron_furnace((Class)TileEntityIronFurnace.class, 46, true, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 5.0F, 10.0F, EnumRarity.COMMON),
/* 214 */   macerator((Class)TileEntityMacerator.class, 47, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 215 */   recycler((Class)TileEntityRecycler.class, 48, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 216 */   solid_canner((Class)TileEntitySolidCanner.class, 49, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 218 */   blast_furnace((Class)TileEntityBlastFurnace.class, 50, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 219 */   block_cutter((Class)TileEntityBlockCutter.class, 51, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 220 */   centrifuge((Class)TileEntityCentrifuge.class, 52, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 221 */   fermenter((Class)TileEntityFermenter.class, 53, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 222 */   induction_furnace((Class)TileEntityInduction.class, 54, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 223 */   metal_former((Class)TileEntityMetalFormer.class, 55, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 224 */   ore_washing_plant((Class)TileEntityOreWashing.class, 56, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 226 */   advanced_miner((Class)TileEntityAdvMiner.class, 57, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 227 */   crop_harvester((Class)TileEntityCropHarvester.class, 58, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 228 */   cropmatron(TileEntityCropmatron.delegate(), 59, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 229 */   miner((Class)TileEntityMiner.class, 60, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 231 */   mass_fabricator(TileEntityMassFabricator.delegate(), 92, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.RARE, IC2Material.MACHINE, false),
/* 232 */   uu_assembly_bench((Class)TileEntityAssemblyBench.class, 93, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 233 */   matter_generator((Class)TileEntityMatter.class, 61, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.RARE, IC2Material.MACHINE, false),
/* 234 */   pattern_storage((Class)TileEntityPatternStorage.class, 62, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 235 */   replicator((Class)TileEntityReplicator.class, 63, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 236 */   scanner((Class)TileEntityScanner.class, 64, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/*     */   
/* 238 */   energy_o_mat((Class)TileEntityEnergyOMat.class, 65, false, Util.allFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0F, 3600000.0F, EnumRarity.COMMON),
/* 239 */   personal_chest((Class)TileEntityPersonalChest.class, 66, false, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0F, 3600000.0F, EnumRarity.UNCOMMON),
/* 240 */   trade_o_mat((Class)TileEntityTradeOMat.class, 67, true, Util.horizontalFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, -1.0F, 3600000.0F, EnumRarity.COMMON),
/* 241 */   trading_terminal((Class)TileEntityTradingTerminal.class, 94, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/*     */   
/* 243 */   cable(TileEntityCable.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5F, 5.0F, EnumRarity.COMMON, Material.field_151580_n, true),
/* 244 */   detector_cable(TileEntityCableDetector.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5F, 5.0F, EnumRarity.COMMON, Material.field_151580_n, false),
/* 245 */   splitter_cable(TileEntityCableSplitter.delegate(), -1, false, Util.noFacings, false, HarvestTool.Pickaxe, DefaultDrop.Self, 0.5F, 5.0F, EnumRarity.COMMON, Material.field_151580_n, false),
/*     */   
/* 247 */   chargepad_batbox((Class)TileEntityChargepadBatBox.class, 68, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 248 */   chargepad_cesu((Class)TileEntityChargepadCESU.class, 69, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 249 */   chargepad_mfe((Class)TileEntityChargepadMFE.class, 70, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 250 */   chargepad_mfsu((Class)TileEntityChargepadMFSU.class, 71, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/*     */   
/* 252 */   batbox((Class)TileEntityElectricBatBox.class, 72, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 253 */   cesu((Class)TileEntityElectricCESU.class, 73, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 254 */   mfe(TileEntityElectricMFE.delegate(), 74, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 255 */   mfsu(TileEntityElectricMFSU.delegate(), 75, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 256 */   electrolyzer(TileEntityElectrolyzer.delegate(), 76, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */   
/* 258 */   lv_transformer((Class)TileEntityTransformerLV.class, 77, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 259 */   mv_transformer((Class)TileEntityTransformerMV.class, 78, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 260 */   hv_transformer((Class)TileEntityTransformerHV.class, 79, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 261 */   ev_transformer((Class)TileEntityTransformerEV.class, 80, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/*     */ 
/*     */   
/* 264 */   tank((Class)TileEntityTank.class, 81, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 265 */   chunk_loader((Class)TileEntityChunkloader.class, 82, true, Util.downSideFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 266 */   item_buffer_2((Class)TileEntityBetterItemBuffer.class, 83, false, Util.noFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 267 */   creative_generator((Class)TileEntityCreativeGenerator.class, 86, true, Util.noFacings, false, HarvestTool.None, DefaultDrop.None, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, EnumRarity.COMMON),
/* 268 */   steam_repressurizer((Class)TileEntitySteamRepressurizer.class, 87, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 269 */   weighted_fluid_distributor((Class)TileEntityWeightedFluidDistributor.class, 90, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 270 */   weighted_item_distributor((Class)TileEntityWeightedItemDistributor.class, 91, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Machine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */ 
/*     */   
/* 273 */   rci_rsh((Class)TileEntityRCI_RSH.class, 84, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 274 */   rci_lzh((Class)TileEntityRCI_LZH.class, 85, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/*     */ 
/*     */   
/* 277 */   crop((Class)TileEntityCrop.class, -1, false, Util.noFacings, false, HarvestTool.Axe, DefaultDrop.Self, 0.8F, 0.2F, EnumRarity.COMMON, Material.field_151585_k, true),
/*     */ 
/*     */   
/* 280 */   industrial_workbench((Class)TileEntityIndustrialWorkbench.class, 88, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 281 */   batch_crafter((Class)TileEntityBatchCrafter.class, 89, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.AdvMachine, 2.0F, 10.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/*     */ 
/*     */ 
/*     */   
/* 285 */   fluid_pipe((Class)TileEntityFluidPipe.class, -1, false, Util.allFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0F, 5.0F, EnumRarity.COMMON, IC2Material.PIPE, true),
/*     */   
/* 287 */   coke_kiln((Class)TileEntityCokeKiln.class, 100, true, Util.horizontalFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, Material.field_151576_e, false),
/* 288 */   coke_kiln_hatch((Class)TileEntityCokeKilnHatch.class, 101, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, Material.field_151576_e, false),
/* 289 */   coke_kiln_grate((Class)TileEntityCokeKilnGrate.class, 102, false, Util.allFacings, true, HarvestTool.Pickaxe, DefaultDrop.Self, 2.0F, 10.0F, EnumRarity.COMMON, Material.field_151576_e, false),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 297 */   wooden_storage_box((Class)TileEntityWoodenStorageBox.class, 111, false, Util.noFacings, false, HarvestTool.Axe, DefaultDrop.Self, 1.0F, 10.0F, EnumRarity.COMMON, Material.field_151575_d, false),
/* 298 */   iron_storage_box((Class)TileEntityIronStorageBox.class, 112, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 299 */   bronze_storage_box((Class)TileEntityBronzeStorageBox.class, 113, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 1.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, false),
/* 300 */   steel_storage_box((Class)TileEntitySteelStorageBox.class, 114, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 2.0F, 20.0F, EnumRarity.UNCOMMON, IC2Material.MACHINE, false),
/* 301 */   iridium_storage_box((Class)TileEntityIridiumStorageBox.class, 115, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 100.0F, EnumRarity.EPIC, IC2Material.MACHINE, false),
/*     */ 
/*     */   
/* 304 */   bronze_tank((Class)TileEntityBronzeTank.class, 131, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, true),
/* 305 */   iron_tank((Class)TileEntityIronTank.class, 132, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 3.0F, 15.0F, EnumRarity.COMMON, IC2Material.MACHINE, true),
/* 306 */   steel_tank((Class)TileEntitySteelTank.class, 133, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 4.0F, 20.0F, EnumRarity.COMMON, IC2Material.MACHINE, true),
/* 307 */   iridium_tank((Class)TileEntityIridiumTank.class, 134, false, Util.horizontalFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 5.0F, 100.0F, EnumRarity.COMMON, IC2Material.MACHINE, true);
/*     */   
/*     */   private static final ResourceLocation loc;
/*     */   
/*     */   private final Class<? extends TileEntityBlock> teClass;
/*     */   private final int itemMeta;
/*     */   private final boolean hasActive;
/*     */   private final Set<EnumFacing> supportedFacings;
/*     */   private final boolean allowWrenchRotating;
/*     */   private final HarvestTool harvestTool;
/*     */   private final DefaultDrop defaultDrop;
/*     */   private final float hardness;
/*     */   
/*     */   TeBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity, Material material, boolean transparent) {
/* 321 */     this.teClass = teClass;
/* 322 */     this.itemMeta = itemMeta;
/* 323 */     this.hasActive = hasActive;
/* 324 */     this.supportedFacings = supportedFacings;
/* 325 */     this.allowWrenchRotating = allowWrenchRotating;
/* 326 */     this.harvestTool = harvestTool;
/* 327 */     this.defaultDrop = defaultDrop;
/* 328 */     this.hardness = hardness;
/* 329 */     this.explosionResistance = explosionResistance;
/* 330 */     this.rarity = rarity;
/* 331 */     this.material = material;
/* 332 */     this.transparent = transparent;
/*     */   }
/*     */   private final float explosionResistance; private final EnumRarity rarity; private final Material material; private final boolean transparent; private TileEntityBlock dummyTe; private ITePlaceHandler placeHandler; public static final TeBlock[] values; private static final String teIdPrefix = "ic2:"; private static final String teClassicPrefix = "Old";
/*     */   
/*     */   public boolean hasItem() {
/* 337 */     return (this.teClass != null && this.itemMeta != -1);
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
/*     */   public String getName() {
/* 349 */     return name();
/*     */   }
/*     */   static {
/* 352 */     loc = new ResourceLocation("ic2", "te");
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
/* 568 */     values = values();
/*     */   }
/*     */   
/*     */   public ResourceLocation getIdentifier() {
/*     */     return loc;
/*     */   }
/*     */   
/*     */   public Class<? extends TileEntityBlock> getTeClass() {
/*     */     return this.teClass;
/*     */   }
/*     */   
/*     */   public boolean hasActive() {
/*     */     return this.hasActive;
/*     */   }
/*     */   
/*     */   public int getId() {
/*     */     return this.itemMeta;
/*     */   }
/*     */   
/*     */   public float getHardness() {
/*     */     return this.hardness;
/*     */   }
/*     */   
/*     */   public HarvestTool getHarvestTool() {
/*     */     return this.harvestTool;
/*     */   }
/*     */   
/*     */   public DefaultDrop getDefaultDrop() {
/*     */     return this.defaultDrop;
/*     */   }
/*     */   
/*     */   public float getExplosionResistance() {
/*     */     return this.explosionResistance;
/*     */   }
/*     */   
/*     */   public boolean allowWrenchRotating() {
/*     */     return this.allowWrenchRotating;
/*     */   }
/*     */   
/*     */   public Set<EnumFacing> getSupportedFacings() {
/*     */     return this.supportedFacings;
/*     */   }
/*     */   
/*     */   public EnumRarity getRarity() {
/*     */     return this.rarity;
/*     */   }
/*     */   
/*     */   public Material getMaterial() {
/*     */     return this.material;
/*     */   }
/*     */   
/*     */   public boolean isTransparent() {
/*     */     return this.transparent;
/*     */   }
/*     */   
/*     */   public void addSubBlocks(NonNullList<ItemStack> list, BlockTileEntity block, ItemBlockTileEntity item, CreativeTabs tab) {
/*     */     if (tab == IC2.tabIC2 || tab == CreativeTabs.field_78027_g)
/*     */       for (TeBlock type : values) {
/*     */         if (type.hasItem() && Version.shouldEnable(type.teClass)) {
/*     */           list.add(block.getItemStack(type));
/*     */           if (type.getDummyTe() instanceof IEnergyStorage) {
/*     */             ItemStack filled = block.getItemStack(type);
/*     */             StackUtil.getOrCreateNbtData(filled).func_74780_a("energy", ((IEnergyStorage)type.getDummyTe()).getCapacity());
/*     */             list.add(filled);
/*     */           } 
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   public void setPlaceHandler(ITePlaceHandler handler) {
/*     */     if (this.placeHandler != null)
/*     */       throw new RuntimeException("duplicate place handler"); 
/*     */     this.placeHandler = handler;
/*     */   }
/*     */   
/*     */   public ITePlaceHandler getPlaceHandler() {
/*     */     return this.placeHandler;
/*     */   }
/*     */   
/*     */   public static void buildDummies() {
/*     */     ModContainer mc = Loader.instance().activeModContainer();
/*     */     if (mc == null || !"ic2".equals(mc.getModId()))
/*     */       throw new IllegalAccessError("Don't mess with this please."); 
/*     */     for (TeBlock block : values) {
/*     */       if (block.teClass != null)
/*     */         try {
/*     */           block.dummyTe = block.teClass.newInstance();
/*     */         } catch (Exception e) {
/*     */           if (Util.inDev())
/*     */             e.printStackTrace(); 
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public TileEntityBlock getDummyTe() {
/*     */     return this.dummyTe;
/*     */   }
/*     */   
/*     */   public static void registerTeMappings() {
/*     */     for (TeBlock block : values) {
/*     */       if (block.teClass != null)
/*     */         if (block.teClass.isAnnotationPresent((Class)Delegated.class)) {
/*     */           Delegated delegation = block.teClass.<Delegated>getAnnotation(Delegated.class);
/*     */           assert delegation != null;
/*     */           GameRegistry.registerTileEntity(delegation.current(), "ic2:" + block.getName());
/*     */           GameRegistry.registerTileEntity(delegation.old(), "ic2:Old" + block.getName());
/*     */           TeBlockRegistry.ensureMapping(block, delegation.current());
/*     */           TeBlockRegistry.ensureMapping(block, delegation.old());
/*     */         } else {
/*     */           GameRegistry.registerTileEntity(block.teClass, "ic2:" + block.getName());
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inherited
/*     */   @Documented
/*     */   @Target({ElementType.TYPE})
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   public static @interface Delegated {
/*     */     Class<? extends TileEntityBlock> old();
/*     */     
/*     */     Class<? extends TileEntityBlock> current();
/*     */   }
/*     */   
/*     */   public enum HarvestTool {
/*     */     None(null, -1),
/*     */     Pickaxe("pickaxe", 0),
/*     */     Shovel("shovel", 0),
/*     */     Axe("axe", 0),
/*     */     Wrench("wrench", 0);
/*     */     public final String toolClass;
/*     */     public final int level;
/*     */     
/*     */     HarvestTool(String toolClass, int level) {
/*     */       this.toolClass = toolClass;
/*     */       this.level = level;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum DefaultDrop {
/*     */     Self, None, Generator, Machine, AdvMachine;
/*     */   }
/*     */   
/*     */   public static interface ITePlaceHandler {
/*     */     boolean canReplace(World param1World, BlockPos param1BlockPos, EnumFacing param1EnumFacing, ItemStack param1ItemStack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\TeBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */