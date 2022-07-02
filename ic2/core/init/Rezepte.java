/*     */ package ic2.core.init;
/*     */ 
/*     */ import ic2.api.recipe.ICraftingRecipeManager;
/*     */ import ic2.api.recipe.IEmptyFluidContainerRecipeManager;
/*     */ import ic2.api.recipe.IFillFluidContainerRecipeManager;
/*     */ import ic2.api.recipe.IMachineRecipeManager;
/*     */ import ic2.api.recipe.IRecipeInput;
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.EmptyFluidContainerRecipeManager;
/*     */ import ic2.core.block.machine.FillFluidContainerRecipeManager;
/*     */ import ic2.core.block.machine.tileentity.TileEntityAssemblyBench;
/*     */ import ic2.core.item.armor.ItemArmorQuantumSuit;
/*     */ import ic2.core.item.armor.jetpack.JetpackAttachmentRecipe;
/*     */ import ic2.core.item.type.CraftingItemType;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.profile.ProfileManager;
/*     */ import ic2.core.recipe.AdvCraftingRecipeManager;
/*     */ import ic2.core.recipe.AdvRecipe;
/*     */ import ic2.core.recipe.AdvShapelessRecipe;
/*     */ import ic2.core.recipe.ArmorDyeingRecipe;
/*     */ import ic2.core.recipe.ColourCarryingRecipe;
/*     */ import ic2.core.recipe.GradualRecipe;
/*     */ import ic2.core.recipe.RecipeInputOreDict;
/*     */ import ic2.core.recipe.SmeltingRecipeManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.Config;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.common.registry.ForgeRegistries;
/*     */ import net.minecraftforge.oredict.RecipeSorter;
/*     */ import net.minecraftforge.registries.IForgeRegistryEntry;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Rezepte
/*     */ {
/*     */   private static int recipeID;
/*     */   private static List<IRecipeInput> disabledRecipeOutputs;
/*     */   
/*     */   public static void registerWithSorter() {
/*  62 */     RecipeSorter.Category shaped = RecipeSorter.Category.SHAPED;
/*  63 */     RecipeSorter.Category shapeless = RecipeSorter.Category.SHAPELESS;
/*     */     
/*  65 */     RecipeSorter.register("ic2:shaped", AdvRecipe.class, shaped, "after:minecraft:shapeless");
/*  66 */     RecipeSorter.register("ic2:shapeless", AdvShapelessRecipe.class, shapeless, "after:ic2:shaped");
/*  67 */     RecipeSorter.register("ic2:gradual", GradualRecipe.class, shapeless, "after:ic2:shapeless");
/*  68 */     RecipeSorter.register("ic2:armorDyeing", ArmorDyeingRecipe.class, shapeless, "after:ic2:shapeless");
/*  69 */     RecipeSorter.register("ic2:colourCarrying", ColourCarryingRecipe.class, shaped, "after:ic2:shaped");
/*  70 */     RecipeSorter.register("ic2:jetpackAttachement", JetpackAttachmentRecipe.class, shapeless, "before:minecraft:shapeless");
/*     */   }
/*     */   
/*     */   static void loadRecipes() {
/*  74 */     Recipes.advRecipes = (ICraftingRecipeManager)new AdvCraftingRecipeManager();
/*  75 */     Recipes.furnace = (IMachineRecipeManager)new SmeltingRecipeManager();
/*  76 */     Recipes.emptyFluidContainer = (IEmptyFluidContainerRecipeManager)new EmptyFluidContainerRecipeManager();
/*  77 */     Recipes.fillFluidContainer = (IFillFluidContainerRecipeManager)new FillFluidContainerRecipeManager();
/*     */     
/*  79 */     Config shapedRecipes = new Config("shaped recipes");
/*  80 */     Config shapelessRecipes = new Config("shapeless recipes");
/*  81 */     Config uuRecipes = new Config("uu recipes");
/*  82 */     Config furnaceRecipes = new Config("furnace recipes");
/*  83 */     Config blastfurnace = new Config("blast furnace recipes");
/*  84 */     Config blockCutter = new Config("block cutter recipes");
/*  85 */     Config compressor = new Config("compressor recipes");
/*  86 */     Config extractor = new Config("extractor recipes");
/*  87 */     Config macerator = new Config("macerator recipes");
/*  88 */     Config mfcutting = new Config("metal former cutting recipes");
/*  89 */     Config mfextruding = new Config("metal former extruding recipes");
/*  90 */     Config mfrolling = new Config("metal former rolling recipes");
/*  91 */     Config oreWashing = new Config("ore washing recipes");
/*  92 */     Config centrifuge = new Config("thermal centrifuge recipes");
/*     */     
/*     */     try {
/*  95 */       shapedRecipes.load(getConfigFile("shaped_recipes"));
/*  96 */       shapelessRecipes.load(getConfigFile("shapeless_recipes"));
/*  97 */       uuRecipes.load(getConfigFile("uu_recipes"));
/*  98 */       furnaceRecipes.load(getConfigFile("furnace"));
/*  99 */       blastfurnace.load(getConfigFile("blast_furnace"));
/* 100 */       blockCutter.load(getConfigFile("block_cutter"));
/* 101 */       compressor.load(getConfigFile("compressor"));
/* 102 */       extractor.load(getConfigFile("extractor"));
/* 103 */       macerator.load(getConfigFile("macerator"));
/* 104 */       mfcutting.load(getConfigFile("metal_former_cutting"));
/* 105 */       mfextruding.load(getConfigFile("metal_former_extruding"));
/* 106 */       mfrolling.load(getConfigFile("metal_former_rolling"));
/* 107 */       oreWashing.load(getConfigFile("ore_washer"));
/* 108 */       centrifuge.load(getConfigFile("thermal_centrifuge"));
/* 109 */     } catch (Exception e) {
/* 110 */       IC2.log.warn(LogCategory.Recipe, e, "Recipe loading failed.");
/*     */     } 
/*     */     
/* 113 */     disabledRecipeOutputs = ConfigUtil.asRecipeInputList(MainConfig.get(), "recipes/disable");
/* 114 */     if (!MainConfig.get().get("recipes/allowCoinCrafting").getBool()) {
/* 115 */       disabledRecipeOutputs.add(Recipes.inputFactory.forStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.coin)));
/*     */     }
/*     */     
/* 118 */     loadCraftingRecipes(shapedRecipes, true);
/* 119 */     loadCraftingRecipes(shapelessRecipes, false);
/* 120 */     loadUuRecipes(uuRecipes);
/* 121 */     loadMachineRecipes(furnaceRecipes, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)SmeltingRecipeManager.SmeltingBridge.INSTANCE, MachineType.Furnace);
/* 122 */     loadMachineRecipes(blastfurnace, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.blastfurnace, MachineType.BlastFurnace);
/* 123 */     loadMachineRecipes(blockCutter, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.blockcutter, MachineType.BlockCutter);
/* 124 */     loadMachineRecipes(compressor, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.compressor, MachineType.Normal);
/* 125 */     loadMachineRecipes(extractor, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.extractor, MachineType.Normal);
/* 126 */     loadMachineRecipes(macerator, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.macerator, MachineType.Normal);
/* 127 */     loadMachineRecipes(mfcutting, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.metalformerCutting, MachineType.Normal);
/* 128 */     loadMachineRecipes(mfextruding, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.metalformerExtruding, MachineType.Normal);
/* 129 */     loadMachineRecipes(mfrolling, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.metalformerRolling, MachineType.Normal);
/* 130 */     loadMachineRecipes(oreWashing, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.oreWashing, MachineType.OreWashingPlant);
/* 131 */     loadMachineRecipes(centrifuge, (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.centrifuge, MachineType.ThermalCentrifuge);
/*     */ 
/*     */     
/* 134 */     IC2.log.debug(LogCategory.Recipe, "%d recipes failed to load in the first pass.", new Object[] { Integer.valueOf(pendingRecipes.size()) });
/*     */   }
/*     */   
/*     */   public static void loadFailedRecipes() {
/* 138 */     int amount = pendingRecipes.size();
/* 139 */     int successful = 0;
/*     */     
/*     */     BooleanSupplier recipe;
/* 142 */     while ((recipe = pendingRecipes.poll()) != null) {
/* 143 */       if (recipe.getAsBoolean()) successful++;
/*     */     
/*     */     } 
/*     */     try {
/* 147 */       Config blockCutter = new Config("block cutter late recipes");
/* 148 */       blockCutter.load(getConfigFile("block_cutter_late"));
/*     */       
/* 150 */       for (Iterator<Config.Value> it = blockCutter.valueIterator(); it.hasNext(); amount++) {
/* 151 */         if (loadMachineRecipe(it.next(), (IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?>)Recipes.blockcutter, MachineType.BlockCutter, true)) {
/* 152 */           successful++;
/*     */         }
/*     */       } 
/* 155 */     } catch (Exception e) {
/* 156 */       IC2.log.warn(LogCategory.Recipe, e, "Late recipe loading failed.");
/*     */     } 
/*     */     
/* 159 */     IC2.log.debug(LogCategory.Recipe, "Successfully loaded %d out of %d recipes in the second pass.", new Object[] { Integer.valueOf(successful), Integer.valueOf(amount) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadCraftingRecipes(Config config, boolean shaped) throws Config.ParseException {
/* 170 */     int amount = 0;
/* 171 */     int successful = 0;
/* 172 */     for (Iterator<Config.Value> it = config.valueIterator(); it.hasNext(); amount++) {
/* 173 */       Config.Value value = it.next();
/* 174 */       if (loadCraftingRecipe(value, shaped, false)) {
/* 175 */         successful++;
/*     */       }
/*     */     } 
/*     */     
/* 179 */     IC2.log.info(LogCategory.Recipe, "Successfully loaded " + successful + " out of " + amount + " recipes for " + config.name);
/*     */   }
/*     */   
/*     */   private static boolean loadCraftingRecipe(Config.Value value, boolean shaped, boolean lastAttempt) {
/*     */     ItemStack output;
/* 184 */     String outputString = value.getString();
/* 185 */     boolean hidden = outputString.contains("@hidden");
/* 186 */     boolean consuming = outputString.contains("@consuming");
/* 187 */     boolean filler = outputString.contains("@filler*");
/* 188 */     boolean fixedSize = outputString.contains("@fixed");
/* 189 */     int fillAmount = -1;
/*     */     
/*     */     try {
/* 192 */       if (hidden) {
/* 193 */         outputString = outputString.replace("@hidden", "").trim();
/*     */       }
/* 195 */       if (consuming) {
/* 196 */         outputString = outputString.replace("@consuming", "").trim();
/*     */       }
/* 198 */       if (filler) {
/* 199 */         int fillerLoc = outputString.indexOf("@filler*");
/* 200 */         int end = outputString.indexOf(' ', fillerLoc);
/* 201 */         String fillerString = outputString.substring(fillerLoc, (end == -1) ? outputString.length() : end);
/* 202 */         fillAmount = Integer.parseInt(fillerString.substring(8));
/* 203 */         outputString = outputString.replace(fillerString, "").trim();
/*     */       } 
/* 205 */       if (fixedSize) {
/* 206 */         outputString = outputString.replace("@fixed", "").trim();
/*     */       }
/* 208 */       output = ConfigUtil.asStackWithAmount(outputString);
/* 209 */     } catch (ParseException e) {
/* 210 */       throw new Config.ParseException("invalid key", value, e);
/* 211 */     } catch (NumberFormatException e) {
/* 212 */       throw new Config.ParseException("Invalid filler amount", value, e);
/*     */     } 
/*     */     
/* 215 */     if (output == null) {
/* 216 */       if (lastAttempt) {
/* 217 */         IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("invalid output specified: " + value.getString(), value), "Skipping recipe for %s due to unresolvable output.", new Object[] { value.name });
/*     */       } else {
/* 219 */         pendingRecipes.add(() -> loadCraftingRecipe(value, shaped, true));
/*     */       } 
/*     */       
/* 222 */       return false;
/*     */     } 
/*     */     
/* 225 */     for (IRecipeInput disable : disabledRecipeOutputs) {
/* 226 */       if (disable.matches(output)) {
/* 227 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 231 */     boolean requireIc2Circuits = ConfigUtil.getBool(MainConfig.get(), "recipes/requireIc2Circuits");
/*     */     
/*     */     try {
/* 234 */       boolean isShapeSpec = shaped;
/* 235 */       List<Object> inputs = new ArrayList();
/*     */       
/* 237 */       for (String part : splitWhitespace(value.name)) {
/* 238 */         if (part.startsWith("@")) {
/* 239 */           if (part.equals("@hidden")) {
/*     */             
/* 241 */             hidden = true; continue;
/* 242 */           }  if (part.startsWith("@filler*")) {
/*     */             try {
/* 244 */               fillAmount = Integer.parseInt(part.substring("@filler*".length()));
/* 245 */               filler = true;
/* 246 */             } catch (NumberFormatException e) {
/* 247 */               throw new Config.ParseException("Invalid filler amount", value, e);
/*     */             }  continue;
/*     */           } 
/* 250 */           throw new Config.ParseException("invalid attribute: " + part, value);
/*     */         } 
/* 252 */         if (isShapeSpec) {
/* 253 */           if (filler) {
/* 254 */             throw new Config.ParseException("Filler recipes can only be shapeless", value);
/*     */           }
/* 256 */           isShapeSpec = false;
/*     */           
/* 258 */           if (part.startsWith("\"")) {
/* 259 */             if (!part.endsWith("\"")) {
/* 260 */               throw new Config.ParseException("missing end quote: " + part, value);
/*     */             }
/*     */             
/* 263 */             part = part.substring(1, part.length() - 1);
/*     */           } 
/*     */           
/* 266 */           String[] rows = part.split("\\|");
/* 267 */           Integer width = null;
/*     */           
/* 269 */           for (String row : rows) {
/* 270 */             if (width != null && width.intValue() != row.length()) {
/* 271 */               throw new Config.ParseException("inconsistent recipe row width", value);
/*     */             }
/*     */             
/* 274 */             width = Integer.valueOf(row.length());
/*     */           } 
/*     */           
/* 277 */           inputs.addAll(Arrays.asList((Object[])rows)); continue;
/*     */         } 
/* 279 */         List<IRecipeInput> input = new ArrayList<>();
/* 280 */         boolean isPatternIndex = shaped;
/*     */         
/* 282 */         for (String subPart : part.split("\\s*\\|\\s*")) {
/* 283 */           String ingredient = subPart;
/*     */           
/* 285 */           if (isPatternIndex) {
/* 286 */             isPatternIndex = false;
/* 287 */             int pos = ingredient.indexOf(":");
/* 288 */             if (pos != 1) {
/* 289 */               throw new Config.ParseException("no valid pattern index character found: " + part, value);
/*     */             }
/*     */             
/* 292 */             inputs.add(Character.valueOf(ingredient.charAt(0)));
/* 293 */             ingredient = ingredient.substring(2);
/*     */           } 
/*     */           
/* 296 */           IRecipeInput cInput = ConfigUtil.asRecipeInput(ingredient);
/*     */           
/* 298 */           if (cInput == null) {
/* 299 */             if (lastAttempt) {
/* 300 */               IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("invalid ingredient specified: " + ingredient, value), "Skipping recipe for %s due to unresolvable input.", new Object[] { value.name }); break;
/*     */             } 
/* 302 */             pendingRecipes.add(() -> loadCraftingRecipe(value, shaped, true));
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */ 
/*     */           
/* 309 */           if (cInput instanceof RecipeInputOreDict) {
/* 310 */             RecipeInputOreDict odInput = (RecipeInputOreDict)cInput;
/*     */             
/* 312 */             if (odInput.input.equals("circuitBasic") && requireIc2Circuits) {
/* 313 */               cInput = Recipes.inputFactory.forStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.circuit));
/* 314 */             } else if (odInput.input.equals("circuitAdvanced") && requireIc2Circuits) {
/* 315 */               cInput = Recipes.inputFactory.forStack(ItemName.crafting.getItemStack((Enum)CraftingItemType.advanced_circuit));
/*     */             } 
/*     */           } 
/*     */           
/* 319 */           input.add(cInput);
/*     */         } 
/*     */         
/* 322 */         if (input.size() == 1) {
/* 323 */           inputs.add(input.get(0)); continue;
/*     */         } 
/* 325 */         inputs.add(input);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 332 */       if (hidden || consuming || fixedSize) {
/* 333 */         inputs.add(new ICraftingRecipeManager.AttributeContainer(hidden, consuming, fixedSize));
/*     */       }
/*     */       
/* 336 */       if (filler) {
/* 337 */         GradualRecipe.addAndRegister(output, fillAmount, inputs.toArray());
/* 338 */       } else if (shaped) {
/* 339 */         AdvRecipe.addAndRegister(output, inputs.toArray());
/*     */       } else {
/* 341 */         AdvShapelessRecipe.addAndRegister(output, inputs.toArray());
/*     */       } 
/* 343 */       return true;
/* 344 */     } catch (ic2.core.util.Config.ParseException e) {
/* 345 */       throw e;
/* 346 */     } catch (Exception e) {
/* 347 */       throw new Config.ParseException("generic parse error", value, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadUuRecipes(Config config) {
/* 352 */     int amount = 0;
/* 353 */     int successful = 0;
/* 354 */     for (Iterator<Config.Value> it = config.valueIterator(); it.hasNext(); amount++) {
/* 355 */       Config.Value value = it.next();
/* 356 */       if (loadUuRecipe(value, false)) {
/* 357 */         successful++;
/*     */       }
/*     */     } 
/*     */     
/* 361 */     IC2.log.info(LogCategory.Recipe, "Successfully loaded " + successful + " out of " + amount + " recipes for solid uu recipes");
/*     */   }
/*     */   
/*     */   private static boolean loadUuRecipe(Config.Value value, boolean lastAttempt) {
/*     */     ItemStack output;
/* 366 */     String outputString = value.getString();
/*     */     
/*     */     try {
/* 369 */       output = ConfigUtil.asStackWithAmount(outputString);
/* 370 */     } catch (ParseException e) {
/* 371 */       throw new Config.ParseException("Invalid output", value, e);
/*     */     } 
/*     */     
/* 374 */     if (output == null) {
/* 375 */       if (lastAttempt) {
/* 376 */         IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("invalid output specified: " + value.getString(), value), "Skipping recipe for %s due to unresolvable output.", new Object[] { value.name });
/*     */       } else {
/* 378 */         pendingRecipes.add(() -> loadUuRecipe(value, true));
/*     */       } 
/*     */       
/* 381 */       return false;
/*     */     } 
/*     */     
/* 384 */     for (IRecipeInput disable : disabledRecipeOutputs) {
/* 385 */       if (disable.matches(output)) {
/* 386 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 391 */       boolean isShapeSpec = true;
/* 392 */       List<Object> inputs = new ArrayList();
/*     */       
/* 394 */       for (String part : splitWhitespace(value.name)) {
/* 395 */         if (isShapeSpec) {
/* 396 */           isShapeSpec = false;
/*     */           
/* 398 */           if (part.startsWith("\"")) {
/* 399 */             if (!part.endsWith("\"")) {
/* 400 */               throw new Config.ParseException("missing end quote: " + part, value);
/*     */             }
/*     */             
/* 403 */             part = part.substring(1, part.length() - 1);
/*     */           } 
/*     */           
/* 406 */           String[] rows = part.split("\\|");
/* 407 */           Integer width = null;
/*     */           
/* 409 */           for (String row : rows) {
/* 410 */             if (width != null && width.intValue() != row.length()) {
/* 411 */               throw new Config.ParseException("inconsistent recipe row width", value);
/*     */             }
/*     */             
/* 414 */             width = Integer.valueOf(row.length());
/*     */           } 
/*     */           
/* 417 */           inputs.addAll(Arrays.asList((Object[])rows)); continue;
/*     */         } 
/* 419 */         List<IRecipeInput> input = new ArrayList<>();
/* 420 */         boolean isPatternIndex = true;
/*     */         
/* 422 */         for (String subPart : part.split("\\s*\\|\\s*")) {
/* 423 */           String ingredient = subPart;
/*     */           
/* 425 */           if (isPatternIndex) {
/* 426 */             isPatternIndex = false;
/* 427 */             int pos = ingredient.indexOf(":");
/* 428 */             if (pos != 1) {
/* 429 */               throw new Config.ParseException("no valid pattern index character found: " + part, value);
/*     */             }
/*     */             
/* 432 */             inputs.add(Character.valueOf(ingredient.charAt(0)));
/* 433 */             ingredient = ingredient.substring(2);
/*     */           } 
/*     */           
/* 436 */           IRecipeInput cInput = ConfigUtil.asRecipeInput(ingredient);
/*     */           
/* 438 */           if (cInput == null) {
/* 439 */             if (lastAttempt) {
/* 440 */               IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("invalid ingredient specified: " + ingredient, value), "Skipping recipe for %s due to unresolvable input.", new Object[] { value.name }); break;
/*     */             } 
/* 442 */             pendingRecipes.add(() -> loadUuRecipe(value, true));
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 448 */           input.add(cInput);
/*     */         } 
/*     */         
/* 451 */         if (input.size() == 1) {
/* 452 */           inputs.add(input.get(0)); continue;
/*     */         } 
/* 454 */         inputs.add(input);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 459 */       boolean foundUU = false;
/* 460 */       boolean foundOther = false;
/* 461 */       for (Object input : inputs) {
/* 462 */         if (input instanceof ic2.core.recipe.RecipeInputItemStack && input.equals(Recipes.inputFactory.forStack(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.matter)))) {
/* 463 */           foundUU = true; continue;
/* 464 */         }  if (input instanceof IRecipeInput || input instanceof List) {
/* 465 */           foundOther = true;
/*     */         }
/*     */       } 
/*     */       
/* 469 */       if (!foundUU) {
/* 470 */         IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("Missing UU from UU recipe", value), "Skipping UU recipe for %s due to missing UU.", new Object[] { value.name });
/*     */       }
/*     */       
/* 473 */       if (foundOther) {
/* 474 */         TileEntityAssemblyBench.RECIPES.add(new AdvRecipe(output, inputs.toArray()));
/*     */       } else {
/* 476 */         TileEntityAssemblyBench.RECIPES.add(TileEntityAssemblyBench.UuRecipe.create(output, inputs.toArray()));
/*     */       } 
/* 478 */       return true;
/* 479 */     } catch (ic2.core.util.Config.ParseException e) {
/* 480 */       throw e;
/* 481 */     } catch (Exception e) {
/* 482 */       throw new Config.ParseException("Generic parse error", value, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadMachineRecipes(Config config, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?> machine, MachineType type) {
/* 488 */     int amount = 0;
/* 489 */     int successful = 0;
/* 490 */     for (Iterator<Config.Value> it = config.valueIterator(); it.hasNext(); amount++) {
/* 491 */       Config.Value value = it.next();
/*     */       
/* 493 */       if (loadMachineRecipe(value, machine, type, false)) {
/* 494 */         successful++;
/*     */       }
/*     */     } 
/*     */     
/* 498 */     IC2.log.info(LogCategory.Recipe, "Successfully loaded " + successful + " out of " + amount + " recipes for " + config.name);
/*     */   }
/*     */   
/*     */   private static boolean loadMachineRecipe(Config.Value value, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?> machine, MachineType type, boolean lastAttempt) {
/*     */     IRecipeInput input;
/* 503 */     boolean exact = value.name.contains("@exact");
/* 504 */     List<ItemStack> outputs = new ArrayList<>();
/* 505 */     NBTTagCompound metadata = new NBTTagCompound();
/*     */     
/*     */     try {
/* 508 */       if (exact) {
/* 509 */         input = Recipes.inputFactory.forExactStack(ConfigUtil.asStackWithAmount(value.name.replace("@exact", "").trim()));
/*     */       } else {
/* 511 */         input = ConfigUtil.asRecipeInputWithAmount(value.name);
/*     */       } 
/* 513 */     } catch (ParseException e) {
/* 514 */       throw new Config.ParseException("invalid key", value, e);
/*     */     } 
/*     */     
/* 517 */     if (input == null) {
/* 518 */       if (lastAttempt) {
/* 519 */         IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("invalid input specified: " + value.name, value), "Skipping recipe due to unresolvable input %s.", new Object[] { value.name });
/*     */       } else {
/* 521 */         pendingRecipes.add(() -> loadMachineRecipe(value, machine, type, true));
/*     */       } 
/*     */       
/* 524 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 528 */       for (String part : splitWhitespace(value.getString())) {
/* 529 */         if (part.startsWith("@")) {
/* 530 */           if (part.startsWith("@ignoreSameInputOutput")) {
/* 531 */             metadata.func_74757_a("ignoreSameInputOutput", true); continue;
/* 532 */           }  if (part.startsWith("@xp:") && type == MachineType.Furnace) {
/* 533 */             metadata.func_74776_a("experience", Float.parseFloat(part.substring(4))); continue;
/* 534 */           }  if (part.startsWith("@hardness:") && type == MachineType.BlockCutter) {
/* 535 */             metadata.func_74768_a("hardness", Integer.parseInt(part.substring(10))); continue;
/* 536 */           }  if (part.startsWith("@heat:") && type == MachineType.ThermalCentrifuge) {
/* 537 */             metadata.func_74768_a("minHeat", Integer.parseInt(part.substring(6))); continue;
/* 538 */           }  if (part.startsWith("@fluid:") && type == MachineType.OreWashingPlant) {
/* 539 */             metadata.func_74768_a("amount", Integer.parseInt(part.substring(7))); continue;
/* 540 */           }  if (part.startsWith("@fluid:") && type == MachineType.BlastFurnace) {
/* 541 */             metadata.func_74768_a("fluid", Integer.parseInt(part.substring(7))); continue;
/* 542 */           }  if (part.startsWith("@duration:") && type == MachineType.BlastFurnace) {
/* 543 */             metadata.func_74768_a("duration", Integer.parseInt(part.substring(10))); continue;
/*     */           } 
/* 545 */           throw new Config.ParseException("invalid attribute: " + part, value);
/*     */         } 
/*     */         
/* 548 */         ItemStack cOutput = ConfigUtil.asStackWithAmount(part);
/*     */         
/* 550 */         if (cOutput == null) {
/* 551 */           if (lastAttempt) {
/* 552 */             IC2.log.warn(LogCategory.Recipe, (Throwable)new Config.ParseException("invalid output specified: " + part, value), "Skipping recipe using %s due to unresolvable output %s.", new Object[] { value.name, part });
/*     */           } else {
/* 554 */             pendingRecipes.add(() -> loadMachineRecipe(value, machine, type, true));
/*     */           } 
/*     */           
/* 557 */           return false;
/*     */         } 
/*     */         
/* 560 */         for (IRecipeInput disable : disabledRecipeOutputs) {
/* 561 */           if (disable.matches(cOutput)) {
/* 562 */             return true;
/*     */           }
/*     */         } 
/*     */         
/* 566 */         outputs.add(cOutput);
/*     */       } 
/*     */       
/* 569 */       if (!type.tagsRequired.isEmpty() && (metadata.func_82582_d() || !type.hasRequiredTags(metadata))) {
/*     */         
/* 571 */         IC2.log.warn(LogCategory.Recipe, "Could not add machine recipe: " + value.name + " missing tag.");
/* 572 */         return false;
/*     */       } 
/*     */       
/* 575 */       if (metadata.func_82582_d())
/*     */       {
/* 577 */         metadata = null;
/*     */       }
/*     */       
/* 580 */       if (machine.addRecipe(input, outputs, metadata, false)) {
/* 581 */         return true;
/*     */       }
/* 583 */       throw new Config.ParseException("Conflicting recipe", value);
/*     */     }
/* 585 */     catch (ic2.core.util.Config.ParseException e) {
/* 586 */       throw e;
/* 587 */     } catch (Exception e) {
/* 588 */       throw new Config.ParseException("generic parse error", value, e);
/*     */     } 
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
/*     */   private static List<String> splitWhitespace(String str) {
/* 679 */     String dummy = str.replaceAll("\\\\.", "xx");
/*     */     
/* 681 */     List<String> ret = new ArrayList<>();
/* 682 */     StringBuilder current = new StringBuilder();
/* 683 */     boolean quoted = false;
/*     */     
/* 685 */     for (int i = 0; i < str.length(); i++) {
/* 686 */       char c = dummy.charAt(i);
/*     */       
/* 688 */       if (c == '"') {
/* 689 */         quoted = !quoted;
/*     */       }
/*     */       
/* 692 */       boolean split = false;
/*     */       
/* 694 */       if (!quoted && 
/* 695 */         Character.isWhitespace(c)) {
/* 696 */         split = true;
/*     */       }
/*     */ 
/*     */       
/* 700 */       if (split) {
/* 701 */         if (current.length() > 0) {
/* 702 */           ret.add(current.toString());
/* 703 */           current.setLength(0);
/*     */         } 
/*     */       } else {
/* 706 */         current.append(str.charAt(i));
/*     */       } 
/*     */     } 
/*     */     
/* 710 */     if (current.length() > 0) {
/* 711 */       ret.add(current.toString());
/*     */     }
/*     */     
/* 714 */     return ret;
/*     */   }
/*     */   
/*     */   public static InputStream getConfigFile(String name) throws FileNotFoundException {
/* 718 */     File file = new File(IC2.platform.getMinecraftDir(), "config/ic2/" + name + ".ini");
/* 719 */     if (file.canRead() && file.isFile()) {
/* 720 */       return new FileInputStream(file);
/*     */     }
/*     */ 
/*     */     
/* 724 */     return ProfileManager.getRecipeConfig(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static InputStream getDefaultConfigFile(String name) {
/* 729 */     return Rezepte.class.getResourceAsStream("/assets/ic2/config/" + name + ".ini");
/*     */   }
/*     */   
/*     */   private enum MachineType {
/* 733 */     Normal((String)new String[0]),
/* 734 */     Furnace((String)new String[0]),
/* 735 */     BlockCutter((String)new String[] { "hardness" }),
/* 736 */     ThermalCentrifuge((String)new String[] { "minHeat" }),
/* 737 */     OreWashingPlant((String)new String[] { "amount" }),
/* 738 */     BlastFurnace((String)new String[] { "fluid", "duration" });
/*     */     
/*     */     final Set<String> tagsRequired;
/*     */     
/*     */     MachineType(String... tagsRequired) {
/* 743 */       this.tagsRequired = new HashSet<>(Arrays.asList(ArrayUtils.nullToEmpty(tagsRequired)));
/*     */     }
/*     */     
/*     */     boolean hasRequiredTags(NBTTagCompound metadata) {
/* 747 */       for (String key : this.tagsRequired) {
/* 748 */         if (!metadata.func_74764_b(key)) {
/* 749 */           return false;
/*     */         }
/*     */       } 
/* 752 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerRecipe(ResourceLocation rl, IRecipe recipe) {
/* 758 */     recipe.setRegistryName(rl);
/* 759 */     ForgeRegistries.RECIPES.register((IForgeRegistryEntry)recipe);
/*     */   }
/*     */   
/*     */   public static void registerRecipe(IRecipe recipe) {
/* 763 */     registerRecipe(new ResourceLocation("ic2", "" + recipeID++), recipe);
/*     */   }
/*     */   
/*     */   public static void registerRecipes() {
/* 767 */     loadRecipes();
/*     */     
/* 769 */     if (!IC2.version.isClassic()) {
/* 770 */       registerRecipe((IRecipe)new ArmorDyeingRecipe(ItemArmorQuantumSuit.class));
/* 771 */       registerRecipe((IRecipe)new JetpackAttachmentRecipe());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 778 */   private static Queue<BooleanSupplier> pendingRecipes = new ArrayDeque<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\init\Rezepte.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */