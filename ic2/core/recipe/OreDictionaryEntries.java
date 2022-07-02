/*     */ package ic2.core.recipe;
/*     */ 
/*     */ import ic2.core.block.type.ResourceBlock;
/*     */ import ic2.core.block.wiring.CableType;
/*     */ import ic2.core.item.block.ItemCable;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.item.type.CropResItemType;
/*     */ import ic2.core.item.type.DustResourceType;
/*     */ import ic2.core.item.type.IngotResourceType;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.item.type.OreResourceType;
/*     */ import ic2.core.item.type.PlateResourceType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.StackUtil;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OreDictionaryEntries
/*     */ {
/*     */   public static void load() {
/*  25 */     add("oreCopper", BlockName.resource.getItemStack((Enum)ResourceBlock.copper_ore));
/*  26 */     add("oreLead", BlockName.resource.getItemStack((Enum)ResourceBlock.lead_ore));
/*  27 */     add("oreTin", BlockName.resource.getItemStack((Enum)ResourceBlock.tin_ore));
/*  28 */     add("oreUranium", BlockName.resource.getItemStack((Enum)ResourceBlock.uranium_ore));
/*     */ 
/*     */     
/*  31 */     add("treeLeaves", StackUtil.copyWithWildCard(BlockName.leaves.getItemStack()));
/*  32 */     add("treeSapling", StackUtil.copyWithWildCard(BlockName.sapling.getItemStack()));
/*  33 */     add("itemRubber", ItemName.crafting.getItemStack((Enum)CraftingItemType.rubber));
/*  34 */     add("materialRubber", ItemName.crafting.getItemStack((Enum)CraftingItemType.rubber));
/*  35 */     add("materialResin", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin));
/*  36 */     add("itemResin", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin));
/*     */ 
/*     */ 
/*     */     
/*  40 */     add("dustStone", ItemName.dust.getItemStack((Enum)DustResourceType.stone));
/*  41 */     add("dustBronze", ItemName.dust.getItemStack((Enum)DustResourceType.bronze));
/*  42 */     add("dustClay", ItemName.dust.getItemStack((Enum)DustResourceType.clay));
/*  43 */     add("dustCoal", ItemName.dust.getItemStack((Enum)DustResourceType.coal));
/*  44 */     add("dustCopper", ItemName.dust.getItemStack((Enum)DustResourceType.copper));
/*  45 */     add("dustGold", ItemName.dust.getItemStack((Enum)DustResourceType.gold));
/*  46 */     add("dustIron", ItemName.dust.getItemStack((Enum)DustResourceType.iron));
/*  47 */     add("dustSilver", ItemName.dust.getItemStack((Enum)DustResourceType.silver));
/*  48 */     add("dustTin", ItemName.dust.getItemStack((Enum)DustResourceType.tin));
/*  49 */     add("dustLead", ItemName.dust.getItemStack((Enum)DustResourceType.lead));
/*  50 */     add("dustObsidian", ItemName.dust.getItemStack((Enum)DustResourceType.obsidian));
/*  51 */     add("dustLapis", ItemName.dust.getItemStack((Enum)DustResourceType.lapis));
/*  52 */     add("dustSulfur", ItemName.dust.getItemStack((Enum)DustResourceType.sulfur));
/*  53 */     add("dustLithium", ItemName.dust.getItemStack((Enum)DustResourceType.lithium));
/*  54 */     add("dustDiamond", ItemName.dust.getItemStack((Enum)DustResourceType.diamond));
/*  55 */     add("dustSiliconDioxide", ItemName.dust.getItemStack((Enum)DustResourceType.silicon_dioxide));
/*  56 */     add("dustHydratedCoal", ItemName.dust.getItemStack((Enum)DustResourceType.coal_fuel));
/*  57 */     add("dustNetherrack", ItemName.dust.getItemStack((Enum)DustResourceType.netherrack));
/*  58 */     add("dustEnderPearl", ItemName.dust.getItemStack((Enum)DustResourceType.ender_pearl));
/*  59 */     add("dustEnderEye", ItemName.dust.getItemStack((Enum)DustResourceType.ender_eye));
/*  60 */     add("dustMilk", ItemName.dust.getItemStack((Enum)DustResourceType.milk));
/*  61 */     add("powderMilk", ItemName.dust.getItemStack((Enum)DustResourceType.milk));
/*  62 */     add("dustAshes", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.ashes));
/*  63 */     add("itemSlag", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.slag));
/*     */ 
/*     */     
/*  66 */     add("dustTinyCopper", ItemName.dust.getItemStack((Enum)DustResourceType.small_copper));
/*  67 */     add("dustTinyGold", ItemName.dust.getItemStack((Enum)DustResourceType.small_gold));
/*  68 */     add("dustTinyIron", ItemName.dust.getItemStack((Enum)DustResourceType.small_iron));
/*  69 */     add("dustTinySilver", ItemName.dust.getItemStack((Enum)DustResourceType.small_silver));
/*  70 */     add("dustTinyTin", ItemName.dust.getItemStack((Enum)DustResourceType.small_tin));
/*  71 */     add("dustTinyLead", ItemName.dust.getItemStack((Enum)DustResourceType.small_lead));
/*  72 */     add("dustTinySulfur", ItemName.dust.getItemStack((Enum)DustResourceType.small_sulfur));
/*  73 */     add("dustTinyLithium", ItemName.dust.getItemStack((Enum)DustResourceType.small_lithium));
/*  74 */     add("dustTinyBronze", ItemName.dust.getItemStack((Enum)DustResourceType.small_bronze));
/*  75 */     add("dustTinyLapis", ItemName.dust.getItemStack((Enum)DustResourceType.small_lapis));
/*  76 */     add("dustTinyObsidian", ItemName.dust.getItemStack((Enum)DustResourceType.small_obsidian));
/*     */ 
/*     */ 
/*     */     
/*  80 */     add("itemRubber", ItemName.crafting.getItemStack((Enum)CraftingItemType.rubber));
/*  81 */     add("ingotBronze", ItemName.ingot.getItemStack((Enum)IngotResourceType.bronze));
/*  82 */     add("ingotCopper", ItemName.ingot.getItemStack((Enum)IngotResourceType.copper));
/*  83 */     add("ingotSteel", ItemName.ingot.getItemStack((Enum)IngotResourceType.steel));
/*  84 */     add("ingotLead", ItemName.ingot.getItemStack((Enum)IngotResourceType.lead));
/*  85 */     add("ingotTin", ItemName.ingot.getItemStack((Enum)IngotResourceType.tin));
/*  86 */     add("ingotSilver", ItemName.ingot.getItemStack((Enum)IngotResourceType.silver));
/*  87 */     add("ingotRefinedIron", ItemName.ingot.getItemStack((Enum)IngotResourceType.refined_iron));
/*  88 */     add("ingotUranium", ItemName.ingot.getItemStack((Enum)IngotResourceType.uranium));
/*     */ 
/*     */     
/*  91 */     add("plateIron", ItemName.plate.getItemStack((Enum)PlateResourceType.iron));
/*  92 */     add("plateGold", ItemName.plate.getItemStack((Enum)PlateResourceType.gold));
/*  93 */     add("plateCopper", ItemName.plate.getItemStack((Enum)PlateResourceType.copper));
/*  94 */     add("plateTin", ItemName.plate.getItemStack((Enum)PlateResourceType.tin));
/*  95 */     add("plateLead", ItemName.plate.getItemStack((Enum)PlateResourceType.lead));
/*  96 */     add("plateLapis", ItemName.plate.getItemStack((Enum)PlateResourceType.lapis));
/*  97 */     add("plateObsidian", ItemName.plate.getItemStack((Enum)PlateResourceType.obsidian));
/*  98 */     add("plateBronze", ItemName.plate.getItemStack((Enum)PlateResourceType.bronze));
/*  99 */     add("plateSteel", ItemName.plate.getItemStack((Enum)PlateResourceType.steel));
/*     */ 
/*     */     
/* 102 */     add("plateDenseSteel", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_steel));
/* 103 */     add("plateDenseIron", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_iron));
/* 104 */     add("plateDenseGold", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_gold));
/* 105 */     add("plateDenseCopper", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_copper));
/* 106 */     add("plateDenseTin", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_tin));
/* 107 */     add("plateDenseLead", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_lead));
/* 108 */     add("plateDenseLapis", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_lapis));
/* 109 */     add("plateDenseObsidian", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_obsidian));
/* 110 */     add("plateDenseBronze", ItemName.plate.getItemStack((Enum)PlateResourceType.dense_bronze));
/*     */ 
/*     */     
/* 113 */     add("crushedIron", ItemName.crushed.getItemStack((Enum)OreResourceType.iron));
/* 114 */     add("crushedGold", ItemName.crushed.getItemStack((Enum)OreResourceType.gold));
/* 115 */     add("crushedSilver", ItemName.crushed.getItemStack((Enum)OreResourceType.silver));
/* 116 */     add("crushedLead", ItemName.crushed.getItemStack((Enum)OreResourceType.lead));
/* 117 */     add("crushedCopper", ItemName.crushed.getItemStack((Enum)OreResourceType.copper));
/* 118 */     add("crushedTin", ItemName.crushed.getItemStack((Enum)OreResourceType.tin));
/* 119 */     add("crushedUranium", ItemName.crushed.getItemStack((Enum)OreResourceType.uranium));
/*     */ 
/*     */     
/* 122 */     add("crushedPurifiedIron", ItemName.purified.getItemStack((Enum)OreResourceType.iron));
/* 123 */     add("crushedPurifiedGold", ItemName.purified.getItemStack((Enum)OreResourceType.gold));
/* 124 */     add("crushedPurifiedSilver", ItemName.purified.getItemStack((Enum)OreResourceType.silver));
/* 125 */     add("crushedPurifiedLead", ItemName.purified.getItemStack((Enum)OreResourceType.lead));
/* 126 */     add("crushedPurifiedCopper", ItemName.purified.getItemStack((Enum)OreResourceType.copper));
/* 127 */     add("crushedPurifiedTin", ItemName.purified.getItemStack((Enum)OreResourceType.tin));
/* 128 */     add("crushedPurifiedUranium", ItemName.purified.getItemStack((Enum)OreResourceType.uranium));
/*     */ 
/*     */     
/* 131 */     add("blockBronze", BlockName.resource.getItemStack((Enum)ResourceBlock.bronze_block));
/* 132 */     add("blockCopper", BlockName.resource.getItemStack((Enum)ResourceBlock.copper_block));
/* 133 */     add("blockTin", BlockName.resource.getItemStack((Enum)ResourceBlock.tin_block));
/* 134 */     add("blockUranium", BlockName.resource.getItemStack((Enum)ResourceBlock.uranium_block));
/* 135 */     add("blockLead", BlockName.resource.getItemStack((Enum)ResourceBlock.lead_block));
/* 136 */     add("blockSilver", BlockName.resource.getItemStack((Enum)ResourceBlock.silver_block));
/* 137 */     add("blockSteel", BlockName.resource.getItemStack((Enum)ResourceBlock.steel_block));
/*     */     
/* 139 */     add("circuitBasic", ItemName.crafting.getItemStack((Enum)CraftingItemType.circuit));
/* 140 */     add("circuitAdvanced", ItemName.crafting.getItemStack((Enum)CraftingItemType.advanced_circuit));
/*     */     
/* 142 */     add("gemDiamond", ItemName.crafting.getItemStack((Enum)CraftingItemType.industrial_diamond));
/*     */ 
/*     */     
/* 145 */     add("craftingToolForgeHammer", StackUtil.copyWithWildCard(ItemName.forge_hammer.getItemStack()));
/* 146 */     add("craftingToolWireCutter", StackUtil.copyWithWildCard(ItemName.cutter.getItemStack()));
/*     */     
/* 148 */     add("fuelCoke", ItemName.coke.getItemStack());
/*     */ 
/*     */     
/* 151 */     add("itemUUMatter", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.matter));
/* 152 */     add("materialUUMatter", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.matter));
/* 153 */     add("nuggetIridium", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.iridium_shard));
/* 154 */     add("gemIridium", ItemName.misc_resource.getItemStack((Enum)MiscResourceType.iridium_ore));
/*     */ 
/*     */     
/* 157 */     add("itemFertilizer", ItemName.crop_res.getItemStack((Enum)CropResItemType.fertilizer));
/*     */ 
/*     */     
/* 160 */     add("plateCarbon", ItemName.crafting.getItemStack((Enum)CraftingItemType.carbon_plate));
/* 161 */     add("plateAdvancedAlloy", ItemName.crafting.getItemStack((Enum)CraftingItemType.alloy));
/* 162 */     add("plateadvancedAlloy", ItemName.crafting.getItemStack((Enum)CraftingItemType.alloy));
/* 163 */     add("itemScrap", ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap));
/* 164 */     add("materialScrap", ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap));
/* 165 */     add("itemScrapBox", ItemName.crafting.getItemStack((Enum)CraftingItemType.scrap_box));
/* 166 */     add("itemCarbonFibre", ItemName.crafting.getItemStack((Enum)CraftingItemType.carbon_fibre));
/* 167 */     add("itemCarbonFiber", ItemName.crafting.getItemStack((Enum)CraftingItemType.carbon_fibre));
/* 168 */     add("itemCarbonMesh", ItemName.crafting.getItemStack((Enum)CraftingItemType.carbon_mesh));
/* 169 */     add("itemRawCarbonMesh", ItemName.crafting.getItemStack((Enum)CraftingItemType.carbon_mesh));
/*     */ 
/*     */     
/* 172 */     add("machineBlock", BlockName.resource.getItemStack((Enum)ResourceBlock.machine));
/* 173 */     add("machineBlockCasing", BlockName.resource.getItemStack((Enum)ResourceBlock.machine));
/* 174 */     add("machineBlockAdvanced", BlockName.resource.getItemStack((Enum)ResourceBlock.advanced_machine));
/* 175 */     add("machineBlockAdvancedCasing", BlockName.resource.getItemStack((Enum)ResourceBlock.advanced_machine));
/*     */ 
/*     */     
/* 178 */     add("reBattery", StackUtil.copyWithWildCard(ItemName.re_battery.getItemStack()));
/* 179 */     add("energyCrystal", StackUtil.copyWithWildCard(ItemName.energy_crystal.getItemStack()));
/* 180 */     add("lapotronCrystal", StackUtil.copyWithWildCard(ItemName.lapotron_crystal.getItemStack()));
/*     */ 
/*     */     
/* 183 */     add("ic2Generator", BlockName.te.getItemStack((Enum)TeBlock.generator));
/* 184 */     add("ic2SolarPanel", BlockName.te.getItemStack((Enum)TeBlock.solar_generator));
/* 185 */     add("ic2Macerator", BlockName.te.getItemStack((Enum)TeBlock.macerator));
/* 186 */     add("ic2Extractor", BlockName.te.getItemStack((Enum)TeBlock.extractor));
/* 187 */     add("ic2Windmill", BlockName.te.getItemStack((Enum)TeBlock.wind_generator));
/* 188 */     add("ic2Watermill", BlockName.te.getItemStack((Enum)TeBlock.water_generator));
/*     */ 
/*     */     
/* 191 */     add("itemTinCable", ItemCable.getCable(CableType.tin, 0));
/* 192 */     add("itemInsulatedTinCable", ItemCable.getCable(CableType.tin, 1));
/* 193 */     add("itemCopperCable", ItemCable.getCable(CableType.copper, 0));
/* 194 */     add("itemInsulatedCopperCable", ItemCable.getCable(CableType.copper, 1));
/* 195 */     add("itemGoldCable", ItemCable.getCable(CableType.gold, 0));
/* 196 */     add("itemInsulatedGoldCable", ItemCable.getCable(CableType.gold, 2));
/* 197 */     add("itemIronCable", ItemCable.getCable(CableType.iron, 0));
/* 198 */     add("itemInsulatedIronCable", ItemCable.getCable(CableType.iron, 3));
/* 199 */     add("itemInsulatedGlassCable", ItemCable.getCable(CableType.glass, 0));
/* 200 */     add("itemDetectorCable", ItemCable.getCable(CableType.detector, 0));
/* 201 */     add("itemDetectorSplitter", ItemCable.getCable(CableType.splitter, 0));
/*     */   }
/*     */   
/*     */   private static void add(String name, ItemStack stack) {
/* 205 */     if (name == null) throw new NullPointerException("null name for stack " + StackUtil.toStringSafe(stack)); 
/* 206 */     if (StackUtil.isEmpty(stack)) throw new IllegalArgumentException("invalid stack for " + name + ": " + StackUtil.toStringSafe(stack));
/*     */     
/* 208 */     OreDictionary.registerOre(name, stack);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\recipe\OreDictionaryEntries.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */