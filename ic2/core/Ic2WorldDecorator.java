/*     */ package ic2.core;
/*     */ 
/*     */ import gnu.trove.set.TIntSet;
/*     */ import gnu.trove.set.hash.TIntHashSet;
/*     */ import ic2.core.block.WorldGenRubTree;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.ResourceBlock;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.util.BiomeUtil;
/*     */ import ic2.core.util.Config;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.Ic2BlockPos;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.gen.IChunkGenerator;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraftforge.common.BiomeDictionary;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.world.ChunkDataEvent;
/*     */ import net.minecraftforge.event.world.ChunkEvent;
/*     */ import net.minecraftforge.fml.common.IWorldGenerator;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ic2WorldDecorator
/*     */   implements IWorldGenerator
/*     */ {
/*     */   private static final boolean DEBUG = false;
/*     */   private static final String chunkDataTag = "ic2WorldGen";
/*     */   private static final String keyRubberTree = "rubberTree";
/*     */   private static final String keyCopperOre = "copperOre";
/*     */   private static final String keyLeadOre = "leadOre";
/*     */   private static final String keyTinOre = "tinOre";
/*     */   private static final String keyUraniumOre = "uraniumOre";
/*     */   public static final int chunkSize = 16;
/*     */   public static final int chunkOffset = 8;
/*     */   private static final int referenceHeight = 64;
/*     */   
/*     */   public Ic2WorldDecorator() {
/*  77 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onChunkLoad(ChunkDataEvent.Load event) {
/*  84 */     assert !(event.getWorld()).field_72995_K;
/*     */     
/*  86 */     Chunk chunk = event.getChunk();
/*  87 */     WorldData worldData = WorldData.get(event.getWorld());
/*     */     
/*  89 */     if (!worldData.pendingUnloadChunks.remove(chunk)) {
/*     */ 
/*     */       
/*  92 */       NBTTagCompound nbt = event.getData().func_74775_l("ic2WorldGen");
/*     */ 
/*     */ 
/*     */       
/*  96 */       worldData.worldGenData.put(chunk, nbt);
/*     */ 
/*     */ 
/*     */       
/* 100 */       checkRetroGen(chunk, nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkRetroGen(Chunk chunk, NBTTagCompound nbt) {
/* 107 */     if (!chunk.func_177419_t())
/*     */       return; 
/* 109 */     Config config = MainConfig.get().getSub("worldgen");
/* 110 */     if (getCheckLimit(config) <= 0 || getUpdateLimit(config) <= 0)
/*     */       return; 
/* 112 */     float epsilon = 1.0E-5F;
/* 113 */     float treeScale = getTreeScale(config) - epsilon;
/* 114 */     float oreScale = getOreScale(config, getBaseHeight(config, chunk.func_177412_p())) - epsilon;
/*     */     
/* 116 */     if (treeScale <= 0.0F && oreScale <= 0.0F) {
/*     */       return;
/*     */     }
/* 119 */     if ((rubberTreeGenEnabled(config, chunk.func_177412_p()) && nbt.func_74760_g("rubberTree") < treeScale) || (
/* 120 */       ConfigUtil.getBool(config, "copper/enabled") && nbt.func_74760_g("copperOre") < oreScale) || (
/* 121 */       ConfigUtil.getBool(config, "lead/enabled") && nbt.func_74760_g("leadOre") < oreScale) || (
/* 122 */       ConfigUtil.getBool(config, "tin/enabled") && nbt.func_74760_g("tinOre") < oreScale) || (
/* 123 */       ConfigUtil.getBool(config, "uranium/enabled") && nbt.func_74760_g("uraniumOre") < oreScale))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       (WorldData.get(chunk.func_177412_p())).chunksToDecorate.add(chunk);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onChunkSave(ChunkDataEvent.Save event) {
/* 139 */     assert !(event.getWorld()).field_72995_K;
/*     */     
/* 141 */     Chunk chunk = event.getChunk();
/* 142 */     NBTTagCompound nbt = (WorldData.get(event.getWorld())).worldGenData.get(chunk);
/*     */     
/* 144 */     if (nbt != null && !nbt.func_82582_d()) {
/* 145 */       nbt = nbt.func_74737_b();
/* 146 */       event.getData().func_74782_a("ic2WorldGen", (NBTBase)nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOWEST)
/*     */   public void onChunkUnload(ChunkEvent.Unload event) {
/* 155 */     if ((event.getWorld()).field_72995_K)
/*     */       return; 
/* 157 */     Chunk chunk = event.getChunk();
/* 158 */     WorldData worldData = WorldData.get(event.getWorld(), false);
/*     */ 
/*     */     
/* 161 */     if (worldData == null) {
/*     */       return;
/*     */     }
/* 164 */     worldData.pendingUnloadChunks.add(chunk);
/*     */   }
/*     */   
/*     */   private static void applyPendingUnloads(WorldData worldData) {
/* 168 */     Collection<Chunk> chunks = worldData.pendingUnloadChunks;
/* 169 */     if (chunks.isEmpty()) {
/*     */       return;
/*     */     }
/* 172 */     for (Chunk chunk : chunks) {
/* 173 */       worldData.worldGenData.remove(chunk);
/*     */       
/* 175 */       if (worldData.chunksToDecorate.remove(chunk));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     chunks.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onTick(World world, WorldData worldData) {
/* 191 */     applyPendingUnloads(worldData);
/*     */     
/* 193 */     if (worldData.chunksToDecorate.isEmpty())
/*     */       return; 
/* 195 */     Config config = MainConfig.get().getSub("worldgen");
/*     */     
/* 197 */     int chunksToCheck = getCheckLimit(config);
/* 198 */     int chunksToDecorate = getUpdateLimit(config);
/*     */ 
/*     */     
/* 201 */     long worldSeed = world.func_72905_C();
/* 202 */     Random rnd = new Random(worldSeed);
/* 203 */     long xSeed = rnd.nextLong() >> 3L;
/* 204 */     long zSeed = rnd.nextLong() >> 3L;
/*     */ 
/*     */     
/* 207 */     int baseHeight = getBaseHeight(config, world);
/* 208 */     int worldHeight = world.func_72800_K();
/* 209 */     float treeScale = getTreeScale(config);
/* 210 */     float oreScale = getOreScale(config, baseHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     int skip = worldData.chunksToDecorate.size() - chunksToCheck;
/*     */     
/* 220 */     if (skip > 0) {
/* 221 */       skip = IC2.random.nextInt(skip + 1);
/*     */     }
/*     */     
/* 224 */     Iterator<Chunk> it = worldData.chunksToDecorate.iterator();
/*     */     
/* 226 */     while (skip > 0) {
/* 227 */       skip--;
/* 228 */       it.next();
/*     */     } 
/*     */     
/* 231 */     while (it.hasNext()) {
/* 232 */       Chunk chunk = it.next();
/*     */       
/* 234 */       if (hasNeighborChunks(chunk)) {
/*     */ 
/*     */         
/* 237 */         NBTTagCompound nbt = worldData.worldGenData.get(chunk);
/* 238 */         if (nbt == null) nbt = new NBTTagCompound();
/*     */         
/* 240 */         long chunkSeed = xSeed * chunk.field_76635_g + zSeed * chunk.field_76647_h ^ worldSeed;
/* 241 */         rnd.setSeed(chunkSeed);
/*     */ 
/*     */ 
/*     */         
/* 245 */         long rubberTreeSeed = rnd.nextLong();
/* 246 */         long copperOreSeed = rnd.nextLong();
/* 247 */         long tinOreSeed = rnd.nextLong();
/* 248 */         long uraniumOreSeed = rnd.nextLong();
/* 249 */         long leadOreSeed = rnd.nextLong();
/*     */         
/*     */         float extra;
/* 252 */         if (rubberTreeGenEnabled(config, world) && (extra = treeScale - nbt.func_74760_g("rubberTree")) > 0.0F) {
/* 253 */           genRubberTree(rnd, rubberTreeSeed, chunk, extra);
/*     */         }
/*     */         
/* 256 */         if ((extra = oreScale - nbt.func_74760_g("copperOre")) > 0.0F) {
/* 257 */           genOre(rnd, copperOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.copper_ore), "copperOre", config.getSub("copper"), baseHeight, worldHeight, extra);
/*     */         }
/*     */         
/* 260 */         if ((extra = oreScale - nbt.func_74760_g("leadOre")) > 0.0F) {
/* 261 */           genOre(rnd, leadOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.lead_ore), "leadOre", config.getSub("lead"), baseHeight, worldHeight, extra);
/*     */         }
/*     */         
/* 264 */         if ((extra = oreScale - nbt.func_74760_g("tinOre")) > 0.0F) {
/* 265 */           genOre(rnd, tinOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.tin_ore), "tinOre", config.getSub("tin"), baseHeight, worldHeight, extra);
/*     */         }
/*     */         
/* 268 */         if ((extra = oreScale - nbt.func_74760_g("uraniumOre")) > 0.0F) {
/* 269 */           genOre(rnd, uraniumOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.uranium_ore), "uraniumOre", config.getSub("uranium"), baseHeight, worldHeight, extra);
/*     */         }
/*     */         
/* 272 */         it.remove();
/*     */         
/* 274 */         if (--chunksToDecorate == 0)
/*     */           break; 
/*     */       } 
/* 277 */       if (--chunksToCheck == 0)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */   private static boolean hasNeighborChunks(Chunk chunk) {
/* 282 */     World world = chunk.func_177412_p();
/* 283 */     Ic2BlockPos pos = new Ic2BlockPos();
/*     */ 
/*     */     
/* 286 */     for (int dx = 0; dx <= 1; dx++) {
/* 287 */       for (int dz = 0; dz <= 1; dz++) {
/* 288 */         if (dx != 0 || dz != 0) {
/*     */           
/* 290 */           pos.set((chunk.field_76635_g + dx) * 16, 0, (chunk.field_76647_h + dz) * 16);
/* 291 */           if (!world.func_175668_a((BlockPos)pos, false)) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 295 */     return true;
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
/*     */   public void generate(Random rnd, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
/* 307 */     Chunk chunk = chunkProvider.func_186025_d(chunkX, chunkZ);
/* 308 */     assert hasNeighborChunks(chunk);
/*     */ 
/*     */ 
/*     */     
/* 312 */     long rubberTreeSeed = rnd.nextLong();
/* 313 */     long copperOreSeed = rnd.nextLong();
/* 314 */     long tinOreSeed = rnd.nextLong();
/* 315 */     long uraniumOreSeed = rnd.nextLong();
/* 316 */     long leadOreSeed = rnd.nextLong();
/*     */     
/* 318 */     Config config = MainConfig.get().getSub("worldgen");
/*     */ 
/*     */     
/* 321 */     int baseHeight = getBaseHeight(config, world);
/* 322 */     float treeScale = getTreeScale(config);
/* 323 */     float oreScale = getOreScale(config, baseHeight);
/*     */     
/* 325 */     if (rubberTreeGenEnabled(config, world) && treeScale > 0.0F) {
/* 326 */       genRubberTree(rnd, rubberTreeSeed, chunk, treeScale);
/*     */     }
/*     */     
/* 329 */     if (oreScale > 0.0F) {
/* 330 */       int worldHeight = world.func_72800_K();
/*     */       
/* 332 */       genOre(rnd, copperOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.copper_ore), "copperOre", config.getSub("copper"), baseHeight, worldHeight, oreScale);
/* 333 */       genOre(rnd, leadOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.lead_ore), "leadOre", config.getSub("lead"), baseHeight, worldHeight, oreScale);
/* 334 */       genOre(rnd, tinOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.tin_ore), "tinOre", config.getSub("tin"), baseHeight, worldHeight, oreScale);
/* 335 */       genOre(rnd, uraniumOreSeed, chunk, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.uranium_ore), "uraniumOre", config.getSub("uranium"), baseHeight, worldHeight, oreScale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void genRubberTree(Random rnd, long seed, Chunk chunk, float baseScale) {
/* 343 */     rnd.setSeed(seed);
/* 344 */     Biome[] biomes = new Biome[4];
/*     */     
/* 346 */     for (int i = 0; i < 4; i++) {
/* 347 */       int x = chunk.field_76635_g * 16 + 8 + (i & 0x1) * 15;
/* 348 */       int z = chunk.field_76647_h * 16 + 8 + ((i & 0x2) >>> 1) * 15;
/*     */       
/* 350 */       BlockPos pos = new BlockPos(x, chunk.func_177412_p().func_181545_F(), z);
/* 351 */       biomes[i] = BiomeUtil.getOriginalBiome(chunk.func_177412_p(), pos);
/*     */     } 
/*     */     
/* 354 */     int rubberTrees = 0;
/*     */     
/* 356 */     for (Biome biome : biomes) {
/* 357 */       if (biome != null) {
/*     */         
/* 359 */         if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
/* 360 */           rubberTrees += rnd.nextInt(10) + 5;
/*     */         }
/*     */         
/* 363 */         if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
/* 364 */           rubberTrees += rnd.nextInt(5) + 1;
/*     */         }
/*     */       } 
/*     */     } 
/* 368 */     rubberTrees = Math.round(rubberTrees * baseScale);
/* 369 */     rubberTrees /= 2;
/*     */     
/* 371 */     if (rubberTrees > 0 && rnd.nextInt(100) < rubberTrees) {
/* 372 */       WorldGenRubTree gen = new WorldGenRubTree(false);
/*     */       
/* 374 */       for (int j = 0; j < rubberTrees; j++) {
/* 375 */         if (!gen.func_180709_b(chunk.func_177412_p(), rnd, new BlockPos(
/* 376 */               randomX(chunk, rnd), chunk
/* 377 */               .func_177412_p().func_181545_F(), 
/* 378 */               randomZ(chunk, rnd)))) {
/* 379 */           rubberTrees -= 3;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 384 */     updateScale(chunk, "rubberTree", baseScale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void genOre(Random rnd, long seed, Chunk chunk, IBlockState ore, String oreScaleKey, Config config, int baseHeight, int worldHeight, float baseScale) {
/* 391 */     if (!ConfigUtil.getBool(config, "enabled"))
/*     */       return; 
/* 393 */     rnd.setSeed(seed);
/*     */     
/* 395 */     int count = ConfigUtil.getInt(config, "count");
/* 396 */     int size = ConfigUtil.getInt(config, "size");
/* 397 */     int minHeight = ConfigUtil.getInt(config, "minHeight");
/* 398 */     int maxHeight = ConfigUtil.getInt(config, "maxHeight");
/* 399 */     OreDistribution distribution = OreDistribution.of(ConfigUtil.getString(config, "distribution"));
/*     */     
/* 401 */     float baseCount = count * baseScale / 64.0F;
/* 402 */     count = (int)Math.round(rnd.nextGaussian() * Math.sqrt(baseCount) + baseCount);
/*     */     
/* 404 */     minHeight = Util.limit(minHeight * baseHeight / 64, 0, worldHeight - 1);
/* 405 */     maxHeight = Util.limit(maxHeight * baseHeight / 64, minHeight + 1, worldHeight - 1);
/* 406 */     int heightSpan = maxHeight - minHeight;
/* 407 */     if (heightSpan == 0)
/*     */       return; 
/* 409 */     WorldGenMinable worldGenMinable = new WorldGenMinable(ore, size);
/* 410 */     BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/*     */     
/* 412 */     for (int n = 0; n < count; n++) {
/* 413 */       int halfHeightSpan, maxA, maxB, maxC, x = randomX(chunk, rnd);
/* 414 */       int z = randomZ(chunk, rnd);
/* 415 */       int y = minHeight;
/*     */       
/* 417 */       switch (distribution) {
/*     */         case UNIFORM:
/* 419 */           y += rnd.nextInt(heightSpan);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case TRIANGLE:
/* 425 */           halfHeightSpan = heightSpan >>> 1;
/* 426 */           y += rnd.nextInt(halfHeightSpan + 1) + rnd.nextInt(heightSpan - halfHeightSpan);
/*     */           break;
/*     */         
/*     */         case RAMP:
/* 430 */           y += heightSpan - 1 - (int)Math.sqrt(rnd.nextInt(heightSpan * heightSpan));
/*     */           break;
/*     */         
/*     */         case REVRAMP:
/* 434 */           y += (int)Math.sqrt(rnd.nextInt(heightSpan * heightSpan));
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case SMOOTH:
/* 441 */           maxA = (heightSpan * 4 + 6) / 7;
/* 442 */           y += rnd.nextInt(maxA);
/* 443 */           maxB = ((heightSpan - maxA + 1) * 2 + 2) / 3;
/* 444 */           y += rnd.nextInt(maxB);
/* 445 */           maxC = heightSpan - maxA - maxB + 2;
/* 446 */           y += rnd.nextInt(maxC);
/*     */           break;
/*     */         
/*     */         default:
/* 450 */           throw new IllegalStateException();
/*     */       } 
/*     */       
/* 453 */       pos.func_181079_c(x, y, z);
/* 454 */       worldGenMinable.func_180709_b(chunk.func_177412_p(), rnd, (BlockPos)pos);
/*     */     } 
/*     */     
/* 457 */     updateScale(chunk, oreScaleKey, baseScale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getBaseHeight(Config config, World world) {
/* 463 */     if (ConfigUtil.getBool(config, "normalizeHeight")) {
/* 464 */       return world.func_181545_F() + 1;
/*     */     }
/* 466 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean rubberTreeGenEnabled(Config config, World world) {
/* 471 */     return (ConfigUtil.getBool(config, "rubberTree") && !rubberTreeBlacklist.contains(world.field_73011_w.getDimension()));
/*     */   }
/*     */   
/*     */   private static float getTreeScale(Config config) {
/* 475 */     return ConfigUtil.getFloat(config, "treeDensityFactor");
/*     */   }
/*     */   
/*     */   private static float getOreScale(Config config, int baseHeight) {
/* 479 */     return ConfigUtil.getFloat(config, "oreDensityFactor") * baseHeight;
/*     */   }
/*     */   
/*     */   private static int getCheckLimit(Config config) {
/* 483 */     return ConfigUtil.getInt(config, "retrogenCheckLimit");
/*     */   }
/*     */   
/*     */   private static int getUpdateLimit(Config config) {
/* 487 */     return ConfigUtil.getInt(config, "retrogenUpdateLimit");
/*     */   }
/*     */   
/*     */   private static void updateScale(Chunk chunk, String key, float scale) {
/* 491 */     WorldData worldData = WorldData.get(chunk.func_177412_p());
/* 492 */     NBTTagCompound nbt = worldData.worldGenData.get(chunk);
/*     */     
/* 494 */     if (nbt == null) {
/* 495 */       nbt = new NBTTagCompound();
/* 496 */       worldData.worldGenData.put(chunk, nbt);
/*     */     } 
/*     */     
/* 499 */     nbt.func_74776_a(key, nbt.func_74760_g(key) + scale);
/* 500 */     chunk.func_177427_f(true);
/*     */   }
/*     */   
/*     */   private static int zeroRnd(Random rnd, int limit) {
/* 504 */     if (limit < 0) throw new IllegalArgumentException("The limit must not be negative: " + limit); 
/* 505 */     if (limit == 0) return 0;
/*     */     
/* 507 */     return rnd.nextInt(limit);
/*     */   }
/*     */   
/*     */   private static int randomX(Chunk chunk, Random rnd) {
/* 511 */     return chunk.field_76635_g * 16 + rnd.nextInt(16);
/*     */   }
/*     */   
/*     */   private static int randomZ(Chunk chunk, Random rnd) {
/* 515 */     return chunk.field_76647_h * 16 + rnd.nextInt(16);
/*     */   }
/*     */   
/*     */   private enum OreDistribution {
/* 519 */     UNIFORM("uniform"),
/* 520 */     TRIANGLE("triangle"),
/* 521 */     RAMP("ramp"),
/* 522 */     REVRAMP("revramp"),
/* 523 */     SMOOTH("smooth");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 537 */     private static final OreDistribution[] values = values();
/*     */     final String name;
/*     */     
/*     */     OreDistribution(String name) {
/*     */       this.name = name;
/*     */     }
/*     */     
/*     */     public static OreDistribution of(String name) {
/*     */       for (OreDistribution value : values) {
/*     */         if (value.name.equalsIgnoreCase(name))
/*     */           return value; 
/*     */       } 
/*     */       throw new RuntimeException("Invalid/unknown worldgen distribution configured: " + name);
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     } }
/* 555 */   private static final TIntSet rubberTreeBlacklist = (TIntSet)new TIntHashSet(ConfigUtil.asIntArray(MainConfig.get(), "worldgen/rubberTreeBlacklist"));
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\Ic2WorldDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */