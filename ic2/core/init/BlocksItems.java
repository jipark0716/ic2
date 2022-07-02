/*     */ package ic2.core.init;
/*     */ import ic2.api.info.Info;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2Potion;
/*     */ import ic2.core.Ic2Fluid;
/*     */ import ic2.core.block.BlockDynamite;
/*     */ import ic2.core.block.BlockFoam;
/*     */ import ic2.core.block.BlockIC2Door;
/*     */ import ic2.core.block.BlockIC2Fence;
/*     */ import ic2.core.block.BlockIC2Fluid;
/*     */ import ic2.core.block.BlockOre;
/*     */ import ic2.core.block.BlockRubWood;
/*     */ import ic2.core.block.BlockScaffold;
/*     */ import ic2.core.block.BlockSheet;
/*     */ import ic2.core.block.BlockTexGlass;
/*     */ import ic2.core.block.BlockWall;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.Ic2Leaves;
/*     */ import ic2.core.block.Ic2Sapling;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.machine.BlockMiningPipe;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.steam.BlockRefractoryBricks;
/*     */ import ic2.core.block.type.ResourceBlock;
/*     */ import ic2.core.crop.ItemCrop;
/*     */ import ic2.core.item.BehaviorScrapboxDispense;
/*     */ import ic2.core.item.ItemBattery;
/*     */ import ic2.core.item.ItemBatteryChargeHotbar;
/*     */ import ic2.core.item.ItemBatterySU;
/*     */ import ic2.core.item.ItemBooze;
/*     */ import ic2.core.item.ItemClassicCell;
/*     */ import ic2.core.item.ItemCoke;
/*     */ import ic2.core.item.ItemContainmentbox;
/*     */ import ic2.core.item.ItemCropSeed;
/*     */ import ic2.core.item.ItemCrystalMemory;
/*     */ import ic2.core.item.ItemFilledFuelCan;
/*     */ import ic2.core.item.ItemFluidCell;
/*     */ import ic2.core.item.ItemHandlers;
/*     */ import ic2.core.item.ItemIC2Boat;
/*     */ import ic2.core.item.ItemIodineTablet;
/*     */ import ic2.core.item.ItemMug;
/*     */ import ic2.core.item.ItemMulti;
/*     */ import ic2.core.item.ItemNuclearResource;
/*     */ import ic2.core.item.ItemTerraWart;
/*     */ import ic2.core.item.ItemTinCan;
/*     */ import ic2.core.item.ItemToolbox;
/*     */ import ic2.core.item.armor.ItemArmorAdvBatpack;
/*     */ import ic2.core.item.armor.ItemArmorBatpack;
/*     */ import ic2.core.item.armor.ItemArmorCFPack;
/*     */ import ic2.core.item.armor.ItemArmorEnergypack;
/*     */ import ic2.core.item.armor.ItemArmorHazmat;
/*     */ import ic2.core.item.armor.ItemArmorIC2;
/*     */ import ic2.core.item.armor.ItemArmorJetpack;
/*     */ import ic2.core.item.armor.ItemArmorJetpackElectric;
/*     */ import ic2.core.item.armor.ItemArmorLappack;
/*     */ import ic2.core.item.armor.ItemArmorNanoSuit;
/*     */ import ic2.core.item.armor.ItemArmorNightvisionGoggles;
/*     */ import ic2.core.item.armor.ItemArmorQuantumSuit;
/*     */ import ic2.core.item.armor.ItemArmorSolarHelmet;
/*     */ import ic2.core.item.armor.ItemArmorStaticBoots;
/*     */ import ic2.core.item.block.BehaviorTeBlockDispense;
/*     */ import ic2.core.item.block.ItemBarrel;
/*     */ import ic2.core.item.block.ItemBlockTileEntity;
/*     */ import ic2.core.item.block.ItemCable;
/*     */ import ic2.core.item.block.ItemDynamite;
/*     */ import ic2.core.item.block.ItemFluidPipe;
/*     */ import ic2.core.item.crafting.BlockCuttingBlade;
/*     */ import ic2.core.item.crafting.UpgradeKit;
/*     */ import ic2.core.item.logistics.ItemPumpCover;
/*     */ import ic2.core.item.reactor.ItemReactorCondensator;
/*     */ import ic2.core.item.reactor.ItemReactorDepletedUranium;
/*     */ import ic2.core.item.reactor.ItemReactorHeatStorage;
/*     */ import ic2.core.item.reactor.ItemReactorHeatSwitch;
/*     */ import ic2.core.item.reactor.ItemReactorHeatpack;
/*     */ import ic2.core.item.reactor.ItemReactorIridiumReflector;
/*     */ import ic2.core.item.reactor.ItemReactorLithiumCell;
/*     */ import ic2.core.item.reactor.ItemReactorMOX;
/*     */ import ic2.core.item.reactor.ItemReactorPlating;
/*     */ import ic2.core.item.reactor.ItemReactorReflector;
/*     */ import ic2.core.item.reactor.ItemReactorUranium;
/*     */ import ic2.core.item.reactor.ItemReactorVent;
/*     */ import ic2.core.item.reactor.ItemReactorVentSpread;
/*     */ import ic2.core.item.resources.ItemWindRotor;
/*     */ import ic2.core.item.tfbp.Tfbp;
/*     */ import ic2.core.item.tool.HarvestLevel;
/*     */ import ic2.core.item.tool.Ic2Axe;
/*     */ import ic2.core.item.tool.Ic2Hoe;
/*     */ import ic2.core.item.tool.Ic2Pickaxe;
/*     */ import ic2.core.item.tool.Ic2Shovel;
/*     */ import ic2.core.item.tool.Ic2Sword;
/*     */ import ic2.core.item.tool.ItemClassicSprayer;
/*     */ import ic2.core.item.tool.ItemCropnalyzer;
/*     */ import ic2.core.item.tool.ItemDebug;
/*     */ import ic2.core.item.tool.ItemDrill;
/*     */ import ic2.core.item.tool.ItemDrillIridium;
/*     */ import ic2.core.item.tool.ItemElectricToolChainsaw;
/*     */ import ic2.core.item.tool.ItemElectricToolHoe;
/*     */ import ic2.core.item.tool.ItemFrequencyTransmitter;
/*     */ import ic2.core.item.tool.ItemNanoSaber;
/*     */ import ic2.core.item.tool.ItemObscurator;
/*     */ import ic2.core.item.tool.ItemRemote;
/*     */ import ic2.core.item.tool.ItemScanner;
/*     */ import ic2.core.item.tool.ItemScannerAdv;
/*     */ import ic2.core.item.tool.ItemSprayer;
/*     */ import ic2.core.item.tool.ItemToolCrowbar;
/*     */ import ic2.core.item.tool.ItemToolCutter;
/*     */ import ic2.core.item.tool.ItemToolHammer;
/*     */ import ic2.core.item.tool.ItemToolMeter;
/*     */ import ic2.core.item.tool.ItemToolMiningLaser;
/*     */ import ic2.core.item.tool.ItemToolPainter;
/*     */ import ic2.core.item.tool.ItemToolWrench;
/*     */ import ic2.core.item.tool.ItemToolWrenchElectric;
/*     */ import ic2.core.item.tool.ItemToolWrenchNew;
/*     */ import ic2.core.item.tool.ItemTreetap;
/*     */ import ic2.core.item.tool.ItemTreetapElectric;
/*     */ import ic2.core.item.tool.ItemWeedingTrowel;
/*     */ import ic2.core.item.tool.ItemWindmeter;
/*     */ import ic2.core.item.type.CasingResourceType;
/*     */ import ic2.core.item.type.CellType;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.item.type.CropResItemType;
/*     */ import ic2.core.item.type.DustResourceType;
/*     */ import ic2.core.item.type.IngotResourceType;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.item.type.OreResourceType;
/*     */ import ic2.core.item.type.PlateResourceType;
/*     */ import ic2.core.item.upgrade.ItemUpgradeModule;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.material.MaterialLiquid;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.util.EnumHelper;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
/*     */ import net.minecraftforge.fluids.capability.IFluidHandlerItem;
/*     */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*     */ 
/*     */ public class BlocksItems {
/*     */   public static void init() {
/* 156 */     initPotions();
/* 157 */     initBlocks();
/* 158 */     initFluids();
/* 159 */     initItems();
/* 160 */     initMigration();
/*     */   }
/*     */   
/*     */   private static void initPotions() {
/* 164 */     Info.POTION_RADIATION = (Potion)(IC2Potion.radiation = new IC2Potion("radiation", true, 5149489, new ItemStack[0]));
/*     */   }
/*     */   
/*     */   private static void initBlocks() {
/* 168 */     TeBlockRegistry.addAll(TeBlock.class, TeBlock.invalid.getIdentifier());
/* 169 */     TeBlockRegistry.addCreativeRegisterer((ITeBlock)TeBlock.invalid);
/* 170 */     TeBlock.reactor_chamber.setPlaceHandler(ItemHandlers.reactorChamberPlace);
/* 171 */     TeBlockRegistry.buildBlocks();
/*     */     
/* 173 */     ItemBlockTileEntity itemTeBlock = TeBlockRegistry.get(TeBlock.itnt.getIdentifier()).getItem();
/* 174 */     BlockDispenser.field_149943_a.func_82595_a(itemTeBlock, new BehaviorTeBlockDispense());
/*     */     
/* 176 */     BlockOre.create();
/* 177 */     BlockName.resource.getInstance().setHarvestLevel("pickaxe", 1, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.copper_ore));
/* 178 */     BlockName.resource.getInstance().setHarvestLevel("pickaxe", 1, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.lead_ore));
/* 179 */     BlockName.resource.getInstance().setHarvestLevel("pickaxe", 1, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.tin_ore));
/* 180 */     BlockName.resource.getInstance().setHarvestLevel("pickaxe", 2, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.uranium_ore));
/* 181 */     BlockName.resource.getInstance().setHarvestLevel("pickaxe", 2, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.reinforced_stone));
/*     */ 
/*     */     
/* 184 */     new Ic2Leaves();
/* 185 */     OreDictionary.registerOre("woodRubber", (Block)new BlockRubWood());
/* 186 */     BlockName.rubber_wood.getInstance().setHarvestLevel("axe", 0);
/* 187 */     new Ic2Sapling();
/*     */     
/* 189 */     BlockScaffold.create();
/* 190 */     BlockIC2Fence.create();
/* 191 */     BlockSheet.create();
/* 192 */     BlockTexGlass.create();
/* 193 */     BlockFoam.create();
/* 194 */     BlockWall.create();
/* 195 */     BlockMiningPipe.create();
/* 196 */     new BlockIC2Door();
/*     */     
/* 198 */     new BlockDynamite();
/*     */     
/* 200 */     new BlockRefractoryBricks();
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
/*     */   private static void initFluids() {
/* 212 */     MaterialLiquid materialLiquid = new MaterialLiquid(MapColor.field_151680_x);
/*     */     
/* 214 */     registerIC2fluid(FluidName.uu_matter, Material.field_151586_h, 3867955, 3000, 3000, 0, 300, false);
/* 215 */     registerIC2fluid(FluidName.construction_foam, Material.field_151586_h, 2105376, 10000, 50000, 0, 300, false);
/* 216 */     registerIC2fluid(FluidName.coolant, Material.field_151586_h, 1333866, 1000, 3000, 0, 300, false);
/* 217 */     registerIC2fluid(FluidName.creosote, Material.field_151586_h, 4012298, 10000, 50000, 0, 300, false);
/* 218 */     registerIC2fluid(FluidName.hot_coolant, Material.field_151586_h, 11872308, 1000, 3000, 0, 1200, false);
/* 219 */     registerIC2fluid(FluidName.pahoehoe_lava, Material.field_151586_h, 8090732, 50000, 250000, 10, 1200, false);
/* 220 */     registerIC2fluid(FluidName.biomass, Material.field_151586_h, 3632933, 1000, 3000, 0, 300, false);
/* 221 */     registerIC2fluid(FluidName.biogas, Material.field_151586_h, 10983500, 1000, 3000, 0, 300, true);
/* 222 */     registerIC2fluid(FluidName.distilled_water, Material.field_151586_h, 4413173, 1000, 1000, 0, 300, false);
/* 223 */     registerIC2fluid(FluidName.superheated_steam, (Material)materialLiquid, 13291985, -3000, 100, 0, 600, true);
/* 224 */     registerIC2fluid(FluidName.steam, (Material)materialLiquid, 12369084, -800, 300, 0, 420, true);
/* 225 */     registerIC2fluid(FluidName.hot_water, Material.field_151586_h, 4644607, 1000, 1000, 0, 350, false);
/* 226 */     registerIC2fluid(FluidName.weed_ex, Material.field_151586_h, 478996, 1000, 1000, 0, 300, false);
/* 227 */     registerIC2fluid(FluidName.air, (Material)materialLiquid, 14474460, 0, 500, 0, 300, true);
/* 228 */     registerIC2fluid(FluidName.hydrogen, (Material)materialLiquid, 14474460, 0, 500, 0, 300, true);
/* 229 */     registerIC2fluid(FluidName.oxygen, (Material)materialLiquid, 14474460, 0, 500, 0, 300, true);
/* 230 */     registerIC2fluid(FluidName.heavy_water, Material.field_151586_h, 4413173, 1000, 1000, 0, 300, false);
/* 231 */     registerIC2fluid(FluidName.milk, Material.field_151586_h, 16579836, 1050, 1000, 0, 300, false);
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
/*     */   private static void initItems() {
/* 247 */     ItemArmor.ArmorMaterial bronzeArmorMaterial = EnumHelper.addArmorMaterial("IC2_BRONZE", "IC2_BRONZE", 15, new int[] { 2, 5, 6, 2 }, 9, null, 0.0F);
/* 248 */     ItemArmor.ArmorMaterial alloyArmorMaterial = EnumHelper.addArmorMaterial("IC2_ALLOY", "IC2_ALLOY", 50, new int[] { 4, 7, 9, 4 }, 12, null, 2.0F);
/*     */ 
/*     */     
/* 251 */     new ItemArmorAdvBatpack();
/* 252 */     new ItemArmorIC2(ItemName.alloy_chestplate, alloyArmorMaterial, "alloy", EntityEquipmentSlot.CHEST, ItemName.crafting.getItemStack((Enum)CraftingItemType.alloy));
/* 253 */     new ItemArmorBatpack();
/* 254 */     new ItemArmorIC2(ItemName.bronze_boots, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.FEET, "ingotBronze");
/* 255 */     new ItemArmorIC2(ItemName.bronze_chestplate, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.CHEST, "ingotBronze");
/* 256 */     new ItemArmorIC2(ItemName.bronze_helmet, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.HEAD, "ingotBronze");
/* 257 */     new ItemArmorIC2(ItemName.bronze_leggings, bronzeArmorMaterial, "bronze", EntityEquipmentSlot.LEGS, "ingotBronze");
/* 258 */     new ItemArmorCFPack();
/* 259 */     new ItemArmorEnergypack();
/* 260 */     new ItemArmorHazmat(ItemName.hazmat_chestplate, EntityEquipmentSlot.CHEST);
/* 261 */     new ItemArmorHazmat(ItemName.hazmat_helmet, EntityEquipmentSlot.HEAD);
/* 262 */     new ItemArmorHazmat(ItemName.hazmat_leggings, EntityEquipmentSlot.LEGS);
/* 263 */     new ItemArmorJetpack();
/* 264 */     new ItemArmorJetpackElectric();
/* 265 */     new ItemArmorLappack();
/* 266 */     new ItemArmorNanoSuit(ItemName.nano_boots, EntityEquipmentSlot.FEET);
/* 267 */     new ItemArmorNanoSuit(ItemName.nano_chestplate, EntityEquipmentSlot.CHEST);
/* 268 */     new ItemArmorNanoSuit(ItemName.nano_helmet, EntityEquipmentSlot.HEAD);
/* 269 */     new ItemArmorNanoSuit(ItemName.nano_leggings, EntityEquipmentSlot.LEGS);
/* 270 */     new ItemArmorNightvisionGoggles();
/* 271 */     new ItemArmorQuantumSuit(ItemName.quantum_boots, EntityEquipmentSlot.FEET);
/* 272 */     new ItemArmorQuantumSuit(ItemName.quantum_chestplate, EntityEquipmentSlot.CHEST);
/* 273 */     new ItemArmorQuantumSuit(ItemName.quantum_helmet, EntityEquipmentSlot.HEAD);
/* 274 */     new ItemArmorQuantumSuit(ItemName.quantum_leggings, EntityEquipmentSlot.LEGS);
/* 275 */     new ItemArmorHazmat(ItemName.rubber_boots, EntityEquipmentSlot.FEET);
/* 276 */     new ItemArmorSolarHelmet();
/* 277 */     new ItemArmorStaticBoots();
/*     */ 
/*     */     
/* 280 */     new ItemIC2Boat();
/*     */ 
/*     */     
/* 283 */     new ItemBarrel();
/* 284 */     new ItemMug();
/* 285 */     new ItemBooze();
/*     */ 
/*     */     
/* 288 */     ItemMulti.create(ItemName.crushed, OreResourceType.class);
/* 289 */     ItemMulti.create(ItemName.purified, OreResourceType.class);
/* 290 */     ItemMulti.create(ItemName.dust, DustResourceType.class);
/* 291 */     ItemMulti.create(ItemName.ingot, IngotResourceType.class);
/*     */     
/* 293 */     ItemMulti.create(ItemName.plate, PlateResourceType.class);
/* 294 */     ItemMulti.create(ItemName.casing, CasingResourceType.class);
/* 295 */     ItemNuclearResource itemNuclearResource = new ItemNuclearResource();
/* 296 */     itemNuclearResource.setUpdateHandler(null, ItemHandlers.radioactiveUpdate);
/* 297 */     ItemMulti<MiscResourceType> miscResource = ItemMulti.create(ItemName.misc_resource, MiscResourceType.class);
/* 298 */     miscResource.setRarity((Enum)MiscResourceType.matter, EnumRarity.RARE);
/* 299 */     miscResource.setRarity((Enum)MiscResourceType.iridium_ore, EnumRarity.RARE);
/* 300 */     miscResource.setRarity((Enum)MiscResourceType.iridium_shard, EnumRarity.UNCOMMON);
/* 301 */     miscResource.setUseHandler((Enum)MiscResourceType.resin, ItemHandlers.resinUse);
/* 302 */     miscResource.setUseHandler((Enum)MiscResourceType.water_sheet, ItemHandlers.getFluidPlacer((Block)Blocks.field_150355_j));
/* 303 */     miscResource.setUseHandler((Enum)MiscResourceType.lava_sheet, ItemHandlers.getFluidPlacer((Block)Blocks.field_150353_l));
/*     */ 
/*     */     
/* 306 */     ItemMulti<CraftingItemType> crafting = ItemMulti.create(ItemName.crafting, CraftingItemType.class);
/* 307 */     crafting.setRarity((Enum)CraftingItemType.advanced_circuit, EnumRarity.UNCOMMON);
/* 308 */     crafting.setRarity((Enum)CraftingItemType.iridium, EnumRarity.RARE);
/* 309 */     crafting.setRightClickHandler((Enum)CraftingItemType.cf_powder, ItemHandlers.cfPowderApply);
/* 310 */     crafting.setRightClickHandler((Enum)CraftingItemType.scrap_box, ItemHandlers.scrapBoxUnpack);
/* 311 */     BlockDispenser.field_149943_a.func_82595_a(crafting, new BehaviorScrapboxDispense());
/*     */     
/* 313 */     new BlockCuttingBlade();
/*     */     
/* 315 */     new UpgradeKit();
/*     */ 
/*     */     
/* 318 */     new ItemCrop();
/*     */ 
/*     */     
/* 321 */     ItemMulti.create(ItemName.crop_res, CropResItemType.class);
/* 322 */     new ItemTerraWart();
/* 323 */     new ItemCropnalyzer();
/*     */ 
/*     */     
/* 326 */     new ItemBattery(ItemName.re_battery, 10000.0D, 100.0D, 1);
/* 327 */     new ItemBattery(ItemName.advanced_re_battery, 100000.0D, 256.0D, 2)
/*     */       {
/*     */         protected boolean isEnabled() {
/* 330 */           return !IC2.version.isClassic();
/*     */         }
/*     */       };
/* 333 */     new ItemBattery(ItemName.energy_crystal, 1000000.0D, 2048.0D, 3);
/* 334 */     (new ItemBattery(ItemName.lapotron_crystal, 1.0E7D, 8092.0D, 4)).setRarity(EnumRarity.UNCOMMON);
/* 335 */     new ItemBatterySU(ItemName.single_use_battery, 1200, 1);
/* 336 */     new ItemBatteryChargeHotbar(ItemName.charging_re_battery, 40000.0D, 128.0D, 1);
/* 337 */     new ItemBatteryChargeHotbar(ItemName.advanced_charging_re_battery, 400000.0D, 1024.0D, 2);
/* 338 */     new ItemBatteryChargeHotbar(ItemName.charging_energy_crystal, 4000000.0D, 8192.0D, 3);
/* 339 */     (new ItemBatteryChargeHotbar(ItemName.charging_lapotron_crystal, 4.0E7D, 32768.0D, 4)).setRarity(EnumRarity.UNCOMMON);
/*     */ 
/*     */     
/* 342 */     new ItemReactorHeatStorage(ItemName.heat_storage, 10000);
/* 343 */     new ItemReactorHeatStorage(ItemName.tri_heat_storage, 30000);
/* 344 */     new ItemReactorHeatStorage(ItemName.hex_heat_storage, 60000);
/* 345 */     new ItemReactorPlating(ItemName.plating, 1000, 0.95F);
/* 346 */     new ItemReactorPlating(ItemName.heat_plating, 2000, 0.99F);
/* 347 */     new ItemReactorPlating(ItemName.containment_plating, 500, 0.9F);
/* 348 */     new ItemReactorHeatSwitch(ItemName.heat_exchanger, 2500, 12, 4);
/* 349 */     new ItemReactorHeatSwitch(ItemName.reactor_heat_exchanger, 5000, 0, 72);
/* 350 */     new ItemReactorHeatSwitch(ItemName.component_heat_exchanger, 5000, 36, 0);
/* 351 */     new ItemReactorHeatSwitch(ItemName.advanced_heat_exchanger, 10000, 24, 8);
/* 352 */     new ItemReactorVent(ItemName.heat_vent, 1000, 6, 0);
/* 353 */     new ItemReactorVent(ItemName.reactor_heat_vent, 1000, 5, 5);
/* 354 */     new ItemReactorVent(ItemName.overclocked_heat_vent, 1000, 20, 36);
/* 355 */     new ItemReactorVentSpread(ItemName.component_heat_vent, 4);
/* 356 */     new ItemReactorVent(ItemName.advanced_heat_vent, 1000, 12, 0);
/* 357 */     new ItemReactorReflector(ItemName.neutron_reflector, 30000);
/* 358 */     new ItemReactorReflector(ItemName.thick_neutron_reflector, 120000);
/* 359 */     new ItemReactorIridiumReflector(ItemName.iridium_reflector);
/* 360 */     new ItemReactorCondensator(ItemName.rsh_condensator, 20000);
/* 361 */     new ItemReactorCondensator(ItemName.lzh_condensator, 100000);
/* 362 */     new ItemReactorHeatpack(1000, 1);
/*     */ 
/*     */     
/* 365 */     new ItemReactorUranium(ItemName.uranium_fuel_rod, 1);
/* 366 */     new ItemReactorUranium(ItemName.dual_uranium_fuel_rod, 2);
/* 367 */     new ItemReactorUranium(ItemName.quad_uranium_fuel_rod, 4);
/* 368 */     new ItemReactorMOX(ItemName.mox_fuel_rod, 1);
/* 369 */     new ItemReactorMOX(ItemName.dual_mox_fuel_rod, 2);
/* 370 */     new ItemReactorMOX(ItemName.quad_mox_fuel_rod, 4);
/* 371 */     new ItemReactorLithiumCell();
/*     */     
/* 373 */     new ItemReactorDepletedUranium();
/*     */ 
/*     */     
/* 376 */     new Tfbp();
/*     */ 
/*     */     
/* 379 */     Item.ToolMaterial bronzeToolMaterial = EnumHelper.addToolMaterial("IC2_BRONZE", 2, 350, 6.0F, 2.0F, 13).setRepairItem(ItemName.ingot.getItemStack((Enum)IngotResourceType.bronze));
/* 380 */     new Ic2Axe(bronzeToolMaterial);
/* 381 */     new Ic2Hoe(bronzeToolMaterial);
/* 382 */     new Ic2Pickaxe(bronzeToolMaterial);
/* 383 */     new Ic2Shovel(bronzeToolMaterial);
/* 384 */     new Ic2Sword(bronzeToolMaterial);
/*     */     
/* 386 */     new ItemToolCutter();
/* 387 */     new ItemDebug();
/* 388 */     if (!IC2.version.isClassic()) { new ItemSprayer(); } else { new ItemClassicSprayer(); }
/* 389 */      new ItemToolHammer();
/* 390 */     new ItemFrequencyTransmitter();
/* 391 */     new ItemToolMeter();
/* 392 */     new ItemToolbox();
/* 393 */     new ItemTreetap();
/* 394 */     new ItemToolWrench();
/* 395 */     new ItemToolWrenchNew();
/* 396 */     new ItemToolCrowbar();
/* 397 */     new ItemContainmentbox();
/*     */ 
/*     */     
/* 400 */     new ItemWeedingTrowel();
/* 401 */     new ItemCropSeed();
/*     */ 
/*     */     
/* 404 */     new ItemScannerAdv();
/* 405 */     new ItemElectricToolChainsaw();
/* 406 */     new ItemDrill(ItemName.diamond_drill, 80, HarvestLevel.Diamond, 30000, 100, 1, 16.0F);
/* 407 */     new ItemDrill(ItemName.drill, 50, HarvestLevel.Iron, 30000, 100, 1, 8.0F);
/* 408 */     new ItemElectricToolHoe();
/* 409 */     new ItemTreetapElectric();
/* 410 */     new ItemToolWrenchElectric();
/* 411 */     new ItemDrillIridium();
/* 412 */     new ItemToolMiningLaser();
/* 413 */     new ItemNanoSaber();
/* 414 */     new ItemObscurator();
/*     */     
/* 416 */     new ItemScanner();
/* 417 */     new ItemWindmeter();
/*     */ 
/*     */     
/* 420 */     new ItemToolPainter();
/*     */ 
/*     */     
/* 423 */     new ItemFluidCell();
/* 424 */     ItemClassicCell itemClassicCell = new ItemClassicCell();
/* 425 */     itemClassicCell.setUseHandler((Enum)CellType.empty, ItemHandlers.emptyCellFill);
/* 426 */     itemClassicCell.addCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, stack -> {
/*     */           CellType type = (CellType)cell.getType(stack);
/*     */           
/*     */           return type.isFluidContainer() ? (IFluidHandlerItem)new CellType.CellFluidHandler(stack, cell::getType) : null;
/*     */         });
/*     */     
/* 432 */     new ItemCable();
/*     */ 
/*     */     
/* 435 */     ItemUpgradeModule itemUpgradeModule = new ItemUpgradeModule();
/* 436 */     itemUpgradeModule.setRightClickHandler((Enum)ItemUpgradeModule.UpgradeType.advanced_ejector, ItemHandlers.openAdvancedUpgradeGUI);
/* 437 */     itemUpgradeModule.setRightClickHandler((Enum)ItemUpgradeModule.UpgradeType.advanced_pulling, ItemHandlers.openAdvancedUpgradeGUI);
/*     */ 
/*     */     
/* 440 */     new ItemTinCan();
/* 441 */     new ItemFilledFuelCan();
/*     */ 
/*     */     
/* 444 */     new ItemIodineTablet();
/*     */ 
/*     */     
/* 447 */     new ItemCrystalMemory();
/*     */ 
/*     */     
/* 450 */     new ItemWindRotor(ItemName.rotor_wood, 5, 10800, 0.25F, 10, 60, new ResourceLocation("ic2", "textures/items/rotor/wood_rotor_model.png"));
/* 451 */     new ItemWindRotor(ItemName.rotor_bronze, 7, 86400, 0.5F, 14, 75, new ResourceLocation("ic2", "textures/items/rotor/bronze_rotor_model.png"));
/* 452 */     new ItemWindRotor(ItemName.rotor_iron, 7, 86400, 0.5F, 14, 75, new ResourceLocation("ic2", "textures/items/rotor/iron_rotor_model.png"));
/* 453 */     new ItemWindRotor(ItemName.rotor_steel, 9, 172800, 0.75F, 17, 90, new ResourceLocation("ic2", "textures/items/rotor/steel_rotor_model.png"));
/* 454 */     new ItemWindRotor(ItemName.rotor_carbon, 11, 604800, 1.0F, 20, 110, new ResourceLocation("ic2", "textures/items/rotor/carbon_rotor_model.png"));
/*     */     
/* 456 */     new ItemDynamite(ItemName.dynamite);
/* 457 */     new ItemDynamite(ItemName.dynamite_sticky);
/* 458 */     new ItemRemote();
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
/* 482 */     new ItemFluidPipe();
/* 483 */     ItemPumpCover itemPumpCover = new ItemPumpCover();
/*     */ 
/*     */     
/* 486 */     new ItemCoke();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initMigration() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerIC2fluid(FluidName name, Material material, int color, int density, int viscosity, int luminosity, int temperature, boolean isGaseous) {
/* 498 */     Fluid fluid = (new Ic2Fluid(name)).setDensity(density).setViscosity(viscosity).setLuminosity(luminosity).setTemperature(temperature).setGaseous(isGaseous);
/*     */     
/* 500 */     if (!FluidRegistry.registerFluid(fluid)) {
/* 501 */       fluid = FluidRegistry.getFluid(name.getName());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 506 */     if (!fluid.canBePlacedInWorld()) {
/* 507 */       BlockIC2Fluid blockIC2Fluid = new BlockIC2Fluid(name, fluid, material, color);
/* 508 */       fluid.setBlock((Block)blockIC2Fluid);
/* 509 */       fluid.setUnlocalizedName(blockIC2Fluid.func_149739_a().substring(4));
/*     */     } else {
/* 511 */       Block block = fluid.getBlock();
/*     */     } 
/*     */     
/* 514 */     name.setInstance(fluid);
/*     */ 
/*     */     
/* 517 */     FluidRegistry.addBucketForFluid(fluid);
/*     */   }
/*     */   
/*     */   public static <T extends Item> T registerItem(T item, ResourceLocation rl) {
/* 521 */     item.setRegistryName(rl);
/* 522 */     return registerItem(item);
/*     */   }
/*     */   
/*     */   public static <T extends Item> T registerItem(T item) {
/* 526 */     ForgeRegistries.ITEMS.register((IForgeRegistryEntry)item);
/* 527 */     return item;
/*     */   }
/*     */   
/*     */   public static <T extends Block> T registerBlock(T item, ResourceLocation rl) {
/* 531 */     item.setRegistryName(rl);
/* 532 */     return registerBlock(item);
/*     */   }
/*     */   
/*     */   public static <T extends Block> T registerBlock(T item) {
/* 536 */     ForgeRegistries.BLOCKS.register((IForgeRegistryEntry)item);
/* 537 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\init\BlocksItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */