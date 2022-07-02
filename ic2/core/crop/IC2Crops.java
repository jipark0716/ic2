/*     */ package ic2.core.crop;
/*     */ 
/*     */ import ic2.api.crops.BaseSeed;
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.CropProperties;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.crop.cropcard.CropBaseMetalCommon;
/*     */ import ic2.core.crop.cropcard.CropBaseMetalUncommon;
/*     */ import ic2.core.crop.cropcard.CropBaseMushroom;
/*     */ import ic2.core.crop.cropcard.CropBaseSapling;
/*     */ import ic2.core.crop.cropcard.CropBeetroot;
/*     */ import ic2.core.crop.cropcard.CropCarrots;
/*     */ import ic2.core.crop.cropcard.CropCocoa;
/*     */ import ic2.core.crop.cropcard.CropCoffee;
/*     */ import ic2.core.crop.cropcard.CropColorFlower;
/*     */ import ic2.core.crop.cropcard.CropEating;
/*     */ import ic2.core.crop.cropcard.CropFlax;
/*     */ import ic2.core.crop.cropcard.CropHops;
/*     */ import ic2.core.crop.cropcard.CropMelon;
/*     */ import ic2.core.crop.cropcard.CropNetherWart;
/*     */ import ic2.core.crop.cropcard.CropPotato;
/*     */ import ic2.core.crop.cropcard.CropPumpkin;
/*     */ import ic2.core.crop.cropcard.CropRedWheat;
/*     */ import ic2.core.crop.cropcard.CropReed;
/*     */ import ic2.core.crop.cropcard.CropStickreed;
/*     */ import ic2.core.crop.cropcard.CropTerraWart;
/*     */ import ic2.core.crop.cropcard.CropVenomilia;
/*     */ import ic2.core.crop.cropcard.CropWeed;
/*     */ import ic2.core.crop.cropcard.CropWheat;
/*     */ import ic2.core.crop.cropcard.GenericCropCard;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.type.CropResItemType;
/*     */ import ic2.core.item.type.DustResourceType;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IC2Crops
/*     */   extends Crops
/*     */ {
/*  70 */   private final Map<BiomeDictionary.Type, Integer> humidityBiomeTypeBonus = new IdentityHashMap<>();
/*  71 */   private final Map<BiomeDictionary.Type, Integer> nutrientBiomeTypeBonus = new IdentityHashMap<>();
/*  72 */   private final Map<ItemStack, BaseSeed> baseSeeds = new HashMap<>();
/*     */   
/*  74 */   public static CropCard cropWheat = (CropCard)new CropWheat();
/*  75 */   public static CropCard cropPumpkin = (CropCard)new CropPumpkin();
/*  76 */   public static CropCard cropMelon = (CropCard)new CropMelon();
/*  77 */   public static CropCard cropYellowFlower = (CropCard)new CropColorFlower("dandelion", new String[] { "Yellow", "Flower" }, 11);
/*  78 */   public static CropCard cropRedFlower = (CropCard)new CropColorFlower("rose", new String[] { "Red", "Flower", "Rose" }, 1);
/*  79 */   public static CropCard cropBlackFlower = (CropCard)new CropColorFlower("blackthorn", new String[] { "Black", "Flower", "Rose" }, 0);
/*  80 */   public static CropCard cropPurpleFlower = (CropCard)new CropColorFlower("tulip", new String[] { "Purple", "Flower", "Tulip" }, 5);
/*  81 */   public static CropCard cropBlueFlower = (CropCard)new CropColorFlower("cyazint", new String[] { "Blue", "Flower" }, 6);
/*  82 */   public static CropCard cropVenomilia = (CropCard)new CropVenomilia();
/*  83 */   public static CropCard cropReed = (CropCard)new CropReed();
/*  84 */   public static CropCard cropStickReed = (CropCard)new CropStickreed();
/*  85 */   public static CropCard cropCocoa = (CropCard)new CropCocoa();
/*  86 */   public static CropCard cropFlax = (CropCard)new CropFlax();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static CropCard cropRedMushroom = (CropCard)new CropBaseMushroom("red_mushroom", new String[] { "Red", "Food", "Mushroom" }, new ItemStack((Block)Blocks.field_150337_Q));
/*     */   
/*  93 */   public static CropCard cropBrownMushroom = (CropCard)new CropBaseMushroom("brown_mushroom", new String[] { "Brown", "Food", "Mushroom" }, new ItemStack((Block)Blocks.field_150338_P));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static CropCard cropNetherWart = (CropCard)new CropNetherWart();
/* 100 */   public static CropCard cropTerraWart = (CropCard)new CropTerraWart();
/*     */ 
/*     */   
/* 103 */   public static CropCard cropOakSapling = (CropCard)new CropBaseSapling("oak_sapling", "acorns", new ItemStack(Blocks.field_150364_r), new ItemStack(Blocks.field_150345_g, 1, 0));
/* 104 */   public static CropCard cropSpruceSapling = (CropCard)new CropBaseSapling("spruce_sapling", "pine_cones", new ItemStack(Blocks.field_150364_r, 1, 1), new ItemStack(Blocks.field_150345_g, 1, 1));
/* 105 */   public static CropCard cropBirchSapling = (CropCard)new CropBaseSapling("birch_sapling", "catkins", new ItemStack(Blocks.field_150364_r, 1, 2), new ItemStack(Blocks.field_150345_g, 1, 2));
/* 106 */   public static CropCard cropJungleSapling = (CropCard)new CropBaseSapling("jungle_sapling", "seedling", new ItemStack(Blocks.field_150364_r, 1, 3), new ItemStack(Blocks.field_150345_g, 1, 3));
/* 107 */   public static CropCard cropAcaciaSapling = (CropCard)new CropBaseSapling("acacia_sapling", "seedling", new ItemStack(Blocks.field_150363_s), new ItemStack(Blocks.field_150345_g, 1, 4));
/* 108 */   public static CropCard cropDarkOakSapling = (CropCard)new CropBaseSapling("dark_oak_sapling", "acorns", new ItemStack(Blocks.field_150363_s, 1, 1), new ItemStack(Blocks.field_150345_g, 1, 5));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 114 */   public static CropCard cropFerru = (CropCard)new CropBaseMetalCommon("ferru", new String[] { "Gray", "Leaves", "Metal" }, new String[] { "oreIron", "blockIron" }, ItemName.dust
/* 115 */       .getItemStack((Enum)DustResourceType.small_iron));
/* 116 */   public static CropCard cropCyprium = (CropCard)new CropBaseMetalCommon("cyprium", new String[] { "Orange", "Leaves", "Metal" }, new String[] { "oreCopper", "blockCopper" }, ItemName.dust
/* 117 */       .getItemStack((Enum)DustResourceType.small_copper));
/* 118 */   public static CropCard cropStagnium = (CropCard)new CropBaseMetalCommon("stagnium", new String[] { "Shiny", "Leaves", "Metal" }, new String[] { "oreTin", "blockTin" }, ItemName.dust
/* 119 */       .getItemStack((Enum)DustResourceType.small_tin));
/* 120 */   public static CropCard cropPlumbiscus = (CropCard)new CropBaseMetalCommon("plumbiscus", new String[] { "Dense", "Leaves", "Metal" }, new String[] { "oreLead", "blockLead" }, ItemName.dust
/* 121 */       .getItemStack((Enum)DustResourceType.small_lead));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static CropCard cropAurelia = (CropCard)new CropBaseMetalUncommon("aurelia", new String[] { "Gold", "Leaves", "Metal" }, new String[] { "oreGold", "blockGold" }, ItemName.dust
/* 127 */       .getItemStack((Enum)DustResourceType.small_gold));
/* 128 */   public static CropCard cropShining = (CropCard)new CropBaseMetalUncommon("shining", new String[] { "Silver", "Leaves", "Metal" }, new String[] { "oreSilver", "blockSilver" }, ItemName.dust
/* 129 */       .getItemStack((Enum)DustResourceType.small_silver));
/*     */ 
/*     */   
/* 132 */   public static CropCard cropRedwheat = (CropCard)new CropRedWheat();
/* 133 */   public static CropCard cropCoffee = (CropCard)new CropCoffee();
/* 134 */   public static CropCard cropHops = (CropCard)new CropHops();
/* 135 */   public static CropCard cropCarrots = (CropCard)new CropCarrots();
/* 136 */   public static CropCard cropPotato = (CropCard)new CropPotato();
/* 137 */   public static CropCard cropEatingPlant = (CropCard)new CropEating();
/* 138 */   public static CropCard cropBeetroot = (CropCard)new CropBeetroot();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/* 144 */     Crops.instance = new IC2Crops();
/* 145 */     Crops.weed = (CropCard)new CropWeed();
/*     */ 
/*     */ 
/*     */     
/* 149 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.WATER, 10);
/* 150 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.WET, 10);
/* 151 */     Crops.instance.addBiomehumidityBonus(BiomeDictionary.Type.DRY, -10);
/*     */     
/* 153 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.JUNGLE, 10);
/* 154 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.SWAMP, 10);
/* 155 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.MUSHROOM, 5);
/* 156 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.FOREST, 5);
/* 157 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.RIVER, 2);
/* 158 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.PLAINS, 0);
/* 159 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.SAVANNA, -2);
/* 160 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.HILLS, -5);
/* 161 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.MOUNTAIN, -5);
/* 162 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.WASTELAND, -8);
/* 163 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.END, -10);
/* 164 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.NETHER, -10);
/* 165 */     Crops.instance.addBiomenutrientsBonus(BiomeDictionary.Type.DEAD, -10);
/*     */     
/* 167 */     registerCrops();
/* 168 */     registerBaseSeeds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerCrops() {
/* 175 */     Crops.instance.registerCrop(weed);
/* 176 */     Crops.instance.registerCrop(cropWheat);
/* 177 */     Crops.instance.registerCrop(cropPumpkin);
/* 178 */     Crops.instance.registerCrop(cropMelon);
/* 179 */     Crops.instance.registerCrop(cropYellowFlower);
/* 180 */     Crops.instance.registerCrop(cropRedFlower);
/* 181 */     Crops.instance.registerCrop(cropBlackFlower);
/* 182 */     Crops.instance.registerCrop(cropPurpleFlower);
/* 183 */     Crops.instance.registerCrop(cropBlueFlower);
/* 184 */     Crops.instance.registerCrop(cropVenomilia);
/* 185 */     Crops.instance.registerCrop(cropReed);
/* 186 */     Crops.instance.registerCrop(cropStickReed);
/* 187 */     Crops.instance.registerCrop(cropCocoa);
/* 188 */     Crops.instance.registerCrop(cropFlax);
/* 189 */     Crops.instance.registerCrop(cropFerru);
/* 190 */     Crops.instance.registerCrop(cropAurelia);
/* 191 */     Crops.instance.registerCrop(cropRedwheat);
/* 192 */     Crops.instance.registerCrop(cropNetherWart);
/* 193 */     Crops.instance.registerCrop(cropTerraWart);
/* 194 */     Crops.instance.registerCrop(cropCoffee);
/* 195 */     Crops.instance.registerCrop(cropHops);
/* 196 */     Crops.instance.registerCrop(cropCarrots);
/* 197 */     Crops.instance.registerCrop(cropPotato);
/* 198 */     Crops.instance.registerCrop(cropRedMushroom);
/* 199 */     Crops.instance.registerCrop(cropBrownMushroom);
/* 200 */     Crops.instance.registerCrop(cropEatingPlant);
/* 201 */     Crops.instance.registerCrop(cropCyprium);
/* 202 */     Crops.instance.registerCrop(cropStagnium);
/* 203 */     Crops.instance.registerCrop(cropPlumbiscus);
/* 204 */     Crops.instance.registerCrop(cropShining);
/* 205 */     Crops.instance.registerCrop(cropBeetroot);
/*     */ 
/*     */     
/* 208 */     Crops.instance.registerCrop(cropOakSapling);
/* 209 */     Crops.instance.registerCrop(cropSpruceSapling);
/* 210 */     Crops.instance.registerCrop(cropBirchSapling);
/* 211 */     Crops.instance.registerCrop(cropJungleSapling);
/* 212 */     Crops.instance.registerCrop(cropAcaciaSapling);
/* 213 */     Crops.instance.registerCrop(cropDarkOakSapling);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     GenericCropCard.create("blazereed")
/* 219 */       .setDiscoveredBy("Mr. Brain")
/* 220 */       .setProperties(new CropProperties(6, 0, 4, 1, 0, 0))
/* 221 */       .setAttributes(new String[] { "Fire", "Blaze", "Reed", "Sulfur"
/* 222 */         }).setMaxSize(4)
/* 223 */       .setDrops(new ItemStack(Items.field_151065_br))
/* 224 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151072_bj), ItemName.dust.getItemStack((Enum)DustResourceType.sulfur)
/* 225 */         }).setAfterHarvestSize(1)
/* 226 */       .register();
/*     */ 
/*     */     
/* 229 */     GenericCropCard.create("corium")
/* 230 */       .setDiscoveredBy("Gregorius Techneticies")
/* 231 */       .setProperties(new CropProperties(6, 0, 2, 3, 1, 0))
/* 232 */       .setAttributes(new String[] { "Cow", "Silk", "Vine"
/* 233 */         }).setMaxSize(4)
/* 234 */       .setDrops(new ItemStack(Items.field_151116_aA))
/* 235 */       .setAfterHarvestSize(1)
/* 236 */       .register();
/*     */ 
/*     */     
/* 239 */     GenericCropCard.create("corpse_plant")
/* 240 */       .setDiscoveredBy("Mr. Kenny")
/* 241 */       .setProperties(new CropProperties(5, 0, 2, 1, 0, 3))
/* 242 */       .setAttributes(new String[] { "Toxic", "Undead", "Vine", "Edible", "Rotten"
/* 243 */         }).setMaxSize(4)
/* 244 */       .setDrops(new ItemStack(Items.field_151078_bh))
/* 245 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151103_aS), new ItemStack(Items.field_151100_aR, 1, 15), new ItemStack(Items.field_151100_aR, 1, 15)
/* 246 */         }).setAfterHarvestSize(1)
/* 247 */       .register();
/*     */ 
/*     */     
/* 250 */     GenericCropCard.create("creeper_weed")
/* 251 */       .setDiscoveredBy("General Spaz")
/* 252 */       .setProperties(new CropProperties(7, 3, 0, 5, 1, 3))
/* 253 */       .setAttributes(new String[] { "Creeper", "Vine", "Explosive", "Fire", "Sulfur", "Saltpeter", "Coal"
/* 254 */         }).setMaxSize(4)
/* 255 */       .setDrops(new ItemStack(Items.field_151016_H))
/* 256 */       .setAfterHarvestSize(1)
/* 257 */       .register();
/*     */ 
/*     */     
/* 260 */     GenericCropCard.create("egg_plant")
/* 261 */       .setDiscoveredBy("Link")
/* 262 */       .setProperties(new CropProperties(6, 0, 4, 1, 0, 0))
/* 263 */       .setAttributes(new String[] { "Chicken", "Egg", "Edible", "Feather", "Flower", "Addictive"
/* 264 */         }).setMaxSize(3)
/* 265 */       .setDrops(new ItemStack(Items.field_151110_aK))
/* 266 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151076_bf), new ItemStack(Items.field_151008_G), new ItemStack(Items.field_151008_G), new ItemStack(Items.field_151008_G)
/* 267 */         }).setGrowthSpeed(900)
/* 268 */       .setAfterHarvestSize(2)
/* 269 */       .register();
/*     */ 
/*     */     
/* 272 */     GenericCropCard.create("ender_blossom")
/* 273 */       .setDiscoveredBy("RichardG")
/* 274 */       .setProperties(new CropProperties(10, 5, 0, 2, 1, 6))
/* 275 */       .setAttributes(new String[] { "Ender", "Flower", "Shiny"
/* 276 */         }).setMaxSize(4)
/* 277 */       .setDrops(ItemName.dust.getItemStack((Enum)DustResourceType.ender_pearl))
/* 278 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151079_bi), new ItemStack(Items.field_151079_bi), new ItemStack(Items.field_151061_bv)
/* 279 */         }).setAfterHarvestSize(1)
/* 280 */       .register();
/*     */ 
/*     */     
/* 283 */     GenericCropCard.create("meat_rose")
/* 284 */       .setDiscoveredBy("VintageBeef")
/* 285 */       .setProperties(new CropProperties(7, 0, 4, 1, 3, 0))
/* 286 */       .setAttributes(new String[] { "Edible", "Flower", "Cow", "Chicken", "Pig", "Sheep"
/* 287 */         }).setMaxSize(4)
/* 288 */       .setDrops(new ItemStack(Items.field_151100_aR, 1, 9))
/* 289 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151082_bd), new ItemStack(Items.field_151147_al), new ItemStack(Items.field_151076_bf), new ItemStack(Items.field_179561_bm)
/* 290 */         }).setGrowthSpeed(1500)
/* 291 */       .setAfterHarvestSize(1)
/* 292 */       .register();
/*     */ 
/*     */     
/* 295 */     GenericCropCard.create("milk_wart")
/* 296 */       .setDiscoveredBy("Mr. Brain")
/* 297 */       .setProperties(new CropProperties(6, 0, 3, 0, 1, 0))
/* 298 */       .setAttributes(new String[] { "Edible", "Milk", "Cow"
/* 299 */         }).setMaxSize(3)
/* 300 */       .setDrops(ItemName.crop_res.getItemStack((Enum)CropResItemType.milk_wart))
/* 301 */       .setGrowthSpeed(900)
/* 302 */       .setAfterHarvestSize(1)
/* 303 */       .addBaseSeed(ItemName.crop_res.getItemStack((Enum)CropResItemType.milk_wart))
/* 304 */       .register();
/*     */ 
/*     */     
/* 307 */     GenericCropCard.create("slime_plant")
/* 308 */       .setDiscoveredBy("Neowulf")
/* 309 */       .setProperties(new CropProperties(6, 3, 0, 0, 0, 2))
/* 310 */       .setAttributes(new String[] { "Slime", "Bouncy", "Sticky", "Bush"
/* 311 */         }).setMaxSize(4)
/* 312 */       .setDrops(new ItemStack(Items.field_151123_aH))
/* 313 */       .setAfterHarvestSize(3)
/* 314 */       .register();
/*     */ 
/*     */     
/* 317 */     GenericCropCard.create("spidernip")
/* 318 */       .setDiscoveredBy("Mr. Kenny")
/* 319 */       .setProperties(new CropProperties(4, 2, 1, 4, 1, 3))
/* 320 */       .setAttributes(new String[] { "Toxic", "Silk", "Spider", "Flower", "Ingredient", "Addictive"
/* 321 */         }).setMaxSize(4)
/* 322 */       .setDrops(new ItemStack(Items.field_151007_F))
/* 323 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151070_bp), new ItemStack(Blocks.field_150321_G)
/* 324 */         }).setGrowthSpeed(600)
/* 325 */       .setAfterHarvestSize(1)
/* 326 */       .register();
/*     */ 
/*     */     
/* 329 */     GenericCropCard.create("tearstalks")
/* 330 */       .setDiscoveredBy("Neowulf")
/* 331 */       .setProperties(new CropProperties(8, 1, 2, 0, 0, 0))
/* 332 */       .setAttributes(new String[] { "Healing", "Nether", "Ingredient", "Reed", "Ghast"
/* 333 */         }).setMaxSize(4)
/* 334 */       .setDrops(new ItemStack(Items.field_151073_bk))
/* 335 */       .setAfterHarvestSize(1)
/* 336 */       .register();
/*     */ 
/*     */     
/* 339 */     GenericCropCard.create("withereed")
/* 340 */       .setDiscoveredBy("CovertJaguar")
/* 341 */       .setProperties(new CropProperties(8, 2, 0, 4, 1, 3))
/* 342 */       .setAttributes(new String[] { "Fire", "Undead", "Reed", "Coal", "Rotten", "Wither"
/* 343 */         }).setMaxSize(4)
/* 344 */       .setDrops(ItemName.dust.getItemStack((Enum)DustResourceType.coal))
/* 345 */       .setSpecialDrops(new ItemStack[] { new ItemStack(Items.field_151044_h), new ItemStack(Items.field_151044_h)
/* 346 */         }).setAfterHarvestSize(1)
/* 347 */       .register();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerBaseSeeds() {
/* 354 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151014_N, 1, 32767), cropWheat, 1, 1, 1, 1);
/* 355 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151080_bb, 1, 32767), cropPumpkin, 1, 1, 1, 1);
/* 356 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151081_bc, 1, 32767), cropMelon, 1, 1, 1, 1);
/* 357 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151075_bm, 1, 32767), cropNetherWart, 1, 1, 1, 1);
/* 358 */     Crops.instance.registerBaseSeed(ItemName.terra_wart.getItemStack(), cropTerraWart, 1, 1, 1, 1);
/* 359 */     Crops.instance.registerBaseSeed(ItemName.crop_res.getItemStack((Enum)CropResItemType.coffee_beans), cropCoffee, 1, 1, 1, 1);
/* 360 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151120_aE, 1, 32767), cropReed, 1, 3, 0, 2);
/* 361 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151100_aR, 1, 3), cropCocoa, 1, 0, 0, 0);
/* 362 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150328_O, 4, 32767), cropRedFlower, 4, 1, 1, 1);
/* 363 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150327_N, 4, 32767), cropYellowFlower, 4, 1, 1, 1);
/* 364 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151172_bF, 1, 32767), cropCarrots, 1, 1, 1, 1);
/* 365 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_151174_bG, 1, 32767), cropPotato, 1, 1, 1, 1);
/* 366 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150338_P, 4, 32767), cropBrownMushroom, 1, 1, 1, 1);
/* 367 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150337_Q, 4, 32767), cropRedMushroom, 1, 1, 1, 1);
/* 368 */     Crops.instance.registerBaseSeed(new ItemStack((Block)Blocks.field_150434_aF, 1, 32767), cropEatingPlant, 1, 1, 1, 1);
/* 369 */     Crops.instance.registerBaseSeed(new ItemStack(Items.field_185163_cU, 1, 32767), cropBeetroot, 1, 1, 1, 1);
/*     */ 
/*     */     
/* 372 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150345_g, 1, 0), cropOakSapling, 1, 1, 1, 1);
/* 373 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150345_g, 1, 1), cropSpruceSapling, 1, 1, 1, 1);
/* 374 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150345_g, 1, 2), cropBirchSapling, 1, 1, 1, 1);
/* 375 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150345_g, 1, 3), cropJungleSapling, 1, 1, 1, 1);
/* 376 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150345_g, 1, 4), cropAcaciaSapling, 1, 1, 1, 1);
/* 377 */     Crops.instance.registerBaseSeed(new ItemStack(Blocks.field_150345_g, 1, 5), cropDarkOakSapling, 1, 1, 1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureInit() {
/* 385 */     if (needsToPost) MinecraftForge.EVENT_BUS.post((Event)new Crops.CropRegisterEvent());
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBiomenutrientsBonus(BiomeDictionary.Type type, int nutrientsBonus) {
/* 391 */     this.nutrientBiomeTypeBonus.put(type, Integer.valueOf(nutrientsBonus));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBiomehumidityBonus(BiomeDictionary.Type type, int humidityBonus) {
/* 396 */     this.humidityBiomeTypeBonus.put(type, Integer.valueOf(humidityBonus));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHumidityBiomeBonus(Biome biome) {
/* 401 */     Integer ret = Integer.valueOf(0);
/*     */     
/* 403 */     for (BiomeDictionary.Type type : BiomeDictionary.getTypes(biome)) {
/* 404 */       Integer val = this.humidityBiomeTypeBonus.get(type);
/*     */       
/* 406 */       if (val != null && val.intValue() > ret.intValue()) ret = val;
/*     */     
/*     */     } 
/* 409 */     return ret.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNutrientBiomeBonus(Biome biome) {
/* 414 */     Integer ret = Integer.valueOf(0);
/*     */     
/* 416 */     for (BiomeDictionary.Type type : BiomeDictionary.getTypes(biome)) {
/* 417 */       Integer val = this.nutrientBiomeTypeBonus.get(type);
/*     */       
/* 419 */       if (val != null && val.intValue() > ret.intValue()) ret = val;
/*     */     
/*     */     } 
/* 422 */     return ret.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public CropCard getCropCard(String owner, String name) {
/* 427 */     Map<String, CropCard> map = this.cropMap.get(owner);
/* 428 */     if (map == null) return null;
/*     */     
/* 430 */     return map.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public CropCard getCropCard(ItemStack stack) {
/* 435 */     if (!stack.func_77942_o()) return null;
/*     */     
/* 437 */     NBTTagCompound nbt = stack.func_77978_p();
/*     */     
/* 439 */     if (nbt.func_74764_b("owner") && nbt.func_74764_b("id")) {
/* 440 */       return getCropCard(nbt.func_74779_i("owner"), nbt.func_74779_i("id"));
/*     */     }
/* 442 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<CropCard> getCrops() {
/* 448 */     return new AbstractCollection<CropCard>()
/*     */       {
/*     */         public Iterator<CropCard> iterator() {
/* 451 */           return new Iterator<CropCard>()
/*     */             {
/*     */               public boolean hasNext() {
/* 454 */                 return (this.iterator != null && this.iterator.hasNext());
/*     */               }
/*     */ 
/*     */               
/*     */               public CropCard next() {
/* 459 */                 if (this.iterator == null) throw new NoSuchElementException("no more elements");
/*     */                 
/* 461 */                 CropCard ret = this.iterator.next();
/*     */                 
/* 463 */                 if (!this.iterator.hasNext()) {
/* 464 */                   this.iterator = getNextIterator();
/*     */                 }
/*     */                 
/* 467 */                 return ret;
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 472 */                 throw new UnsupportedOperationException("This iterator is read-only.");
/*     */               }
/*     */               
/*     */               private Iterator<CropCard> getNextIterator() {
/* 476 */                 Iterator<CropCard> ret = null;
/*     */                 
/* 478 */                 while (this.mapIterator.hasNext() && ret == null) {
/* 479 */                   ret = ((Map)this.mapIterator.next()).values().iterator();
/* 480 */                   if (!ret.hasNext()) ret = null;
/*     */                 
/*     */                 } 
/* 483 */                 return ret;
/*     */               }
/*     */               
/* 486 */               private final Iterator<Map<String, CropCard>> mapIterator = IC2Crops.this.cropMap.values().iterator();
/* 487 */               private Iterator<CropCard> iterator = getNextIterator();
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 493 */           int ret = 0;
/*     */           
/* 495 */           for (Map<String, CropCard> map : (Iterable<Map<String, CropCard>>)IC2Crops.this.cropMap.values()) {
/* 496 */             ret += map.size();
/*     */           }
/*     */           
/* 499 */           return ret;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerCrop(CropCard crop) {
/* 506 */     List<String> disabledCrops = ConfigUtil.asList(ConfigUtil.getString(MainConfig.get(), "agriculture/disabledCrops"));
/*     */     
/* 508 */     String owner = crop.getOwner();
/* 509 */     String id = crop.getId();
/*     */ 
/*     */     
/* 512 */     if (crop != weed && disabledCrops.contains(owner + ":" + id)) {
/* 513 */       IC2.log.info(LogCategory.Crop, "Crop " + owner + ":" + id + " has been disabled");
/*     */       
/*     */       return;
/*     */     } 
/* 517 */     if (!owner.equals(owner.toLowerCase(Locale.ENGLISH))) {
/* 518 */       throw new IllegalArgumentException("The crop owner=" + owner + " id=" + id + " uses a non-lower case owner");
/*     */     }
/* 520 */     Map<String, CropCard> map = this.cropMap.computeIfAbsent(owner, key -> new HashMap<>());
/*     */     
/* 522 */     CropCard prev = map.put(id, crop);
/* 523 */     if (prev != null) {
/* 524 */       throw new IllegalArgumentException("The crop owner=" + owner + " id=" + id + " uses a non-unique owner+id pair");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean registerBaseSeed(ItemStack stack, CropCard crop, int size, int growth, int gain, int resistance) {
/* 529 */     List<String> disabledCrops = ConfigUtil.asList(ConfigUtil.getString(MainConfig.get(), "agriculture/disabledCrops"));
/*     */     
/* 531 */     String owner = crop.getOwner();
/* 532 */     String id = crop.getId();
/*     */ 
/*     */     
/* 535 */     if (crop != weed && disabledCrops.contains(owner + ":" + id)) return false;
/*     */     
/* 537 */     for (ItemStack key : this.baseSeeds.keySet()) {
/* 538 */       if (key.func_77973_b() == stack.func_77973_b() && key.func_77952_i() == stack.func_77952_i()) return false; 
/*     */     } 
/* 540 */     this.baseSeeds.put(stack, new BaseSeed(crop, size, growth, gain, resistance));
/*     */     
/* 542 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerCropTextures(Map<ResourceLocation, TextureAtlasSprite> extraTextures) {
/* 548 */     extraTextures.forEach(CropModel.textures::putIfAbsent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSeed getBaseSeed(ItemStack stack) {
/* 558 */     if (stack == null) return null;
/*     */     
/* 560 */     return this.baseSeeds.keySet()
/* 561 */       .stream()
/* 562 */       .filter(key -> (key.func_77973_b() == stack.func_77973_b() && (key.func_77952_i() == 32767 || key.func_77952_i() == stack.func_77952_i())))
/*     */       
/* 564 */       .findFirst()
/* 565 */       .map(this.baseSeeds::get)
/* 566 */       .orElse(null);
/*     */   }
/*     */   
/*     */   static boolean needsToPost = true;
/* 570 */   private final Map<String, Map<String, CropCard>> cropMap = new HashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\IC2Crops.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */