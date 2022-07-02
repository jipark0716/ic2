/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.Ic2Player;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.gen.ChunkProviderServer;
/*     */ import net.minecraftforge.oredict.OreDictionary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Util
/*     */ {
/*     */   public static int roundToNegInf(float x) {
/*  45 */     int ret = (int)x;
/*     */     
/*  47 */     if (ret > x) ret--;
/*     */     
/*  49 */     return ret;
/*     */   }
/*     */   
/*     */   public static int roundToNegInf(double x) {
/*  53 */     int ret = (int)x;
/*     */     
/*  55 */     if (ret > x) ret--;
/*     */     
/*  57 */     return ret;
/*     */   }
/*     */   
/*     */   public static int saturatedCast(double x) {
/*  61 */     if (x > 2.147483647E9D)
/*  62 */       return Integer.MAX_VALUE; 
/*  63 */     if (x < -2.147483648E9D) {
/*  64 */       return Integer.MIN_VALUE;
/*     */     }
/*  66 */     return (int)x;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int limit(int value, int min, int max) {
/*  71 */     if (value <= min)
/*  72 */       return min; 
/*  73 */     if (value >= max) {
/*  74 */       return max;
/*     */     }
/*  76 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float limit(float value, float min, float max) {
/*  81 */     if (Float.isNaN(value) || value <= min)
/*  82 */       return min; 
/*  83 */     if (value >= max) {
/*  84 */       return max;
/*     */     }
/*  86 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double limit(double value, double min, double max) {
/*  91 */     if (Double.isNaN(value) || value <= min)
/*  92 */       return min; 
/*  93 */     if (value >= max) {
/*  94 */       return max;
/*     */     }
/*  96 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double map(double value, double srcMax, double dstMax) {
/* 101 */     if (value < 0.0D || Double.isNaN(value)) value = 0.0D; 
/* 102 */     if (value > srcMax) value = srcMax;
/*     */     
/* 104 */     return value / srcMax * dstMax;
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
/*     */   public static double lerp(double start, double end, double fraction) {
/* 116 */     assert fraction >= 0.0D && fraction <= 1.0D;
/*     */     
/* 118 */     return start + (end - start) * fraction;
/*     */   }
/*     */   
/*     */   public static float lerp(float start, float end, float fraction) {
/* 122 */     assert fraction >= 0.0F && fraction <= 1.0F;
/*     */     
/* 124 */     return start + (end - start) * fraction;
/*     */   }
/*     */   
/*     */   public static int square(int x) {
/* 128 */     return x * x;
/*     */   }
/*     */   
/*     */   public static float square(float x) {
/* 132 */     return x * x;
/*     */   }
/*     */   
/*     */   public static double square(double x) {
/* 136 */     return x * x;
/*     */   }
/*     */   
/*     */   public static boolean isSimilar(float a, float b) {
/* 140 */     return (Math.abs(a - b) < 1.0E-5F);
/*     */   }
/*     */   
/*     */   public static boolean isSimilar(double a, double b) {
/* 144 */     return (Math.abs(a - b) < 1.0E-5D);
/*     */   }
/*     */   
/*     */   public static int countInArray(Object[] oa, Class<?>... clsz) {
/* 148 */     int ret = 0;
/* 149 */     for (Object o : oa) {
/* 150 */       for (Class<?> cls : clsz) {
/* 151 */         if (cls.isAssignableFrom(o.getClass())) ret++; 
/*     */       } 
/*     */     } 
/* 154 */     return ret;
/*     */   }
/*     */   
/*     */   public static int countInArray(Object[] oa, Class<?> cls) {
/* 158 */     int ret = 0;
/* 159 */     for (Object o : oa) {
/* 160 */       if (cls.isAssignableFrom(o.getClass())) ret++; 
/*     */     } 
/* 162 */     return ret;
/*     */   }
/*     */   
/*     */   public static boolean inDev() {
/* 166 */     return inDev;
/*     */   }
/*     */   
/*     */   public static boolean hasAssertions() {
/* 170 */     boolean ret = false;
/* 171 */     assert ret = true;
/* 172 */     return ret;
/*     */   }
/*     */   
/*     */   public static boolean matchesOD(ItemStack stack, Object match) {
/* 176 */     if (match instanceof ItemStack)
/* 177 */       return (!StackUtil.isEmpty(stack) && stack.func_77969_a((ItemStack)match)); 
/* 178 */     if (match instanceof String) {
/* 179 */       if (StackUtil.isEmpty(stack)) return false;
/*     */       
/* 181 */       for (int oreId : OreDictionary.getOreIDs(stack)) {
/* 182 */         if (OreDictionary.getOreName(oreId).equals(match)) return true;
/*     */       
/*     */       } 
/* 185 */       return false;
/*     */     } 
/* 187 */     return (stack == match);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toString(TileEntity te) {
/* 192 */     if (te == null) return "null";
/*     */     
/* 194 */     return toString(te, (IBlockAccess)te.func_145831_w(), te.func_174877_v());
/*     */   }
/*     */   
/*     */   public static String toString(Object o, IBlockAccess world, BlockPos pos) {
/* 198 */     return toString(o, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
/*     */   }
/*     */   
/*     */   public static String toString(Object o, IBlockAccess world, int x, int y, int z) {
/* 202 */     StringBuilder ret = new StringBuilder(64);
/*     */     
/* 204 */     if (o == null) {
/* 205 */       ret.append("null");
/*     */     } else {
/* 207 */       ret.append(o.getClass().getName());
/* 208 */       ret.append('@');
/* 209 */       ret.append(Integer.toHexString(System.identityHashCode(o)));
/*     */     } 
/*     */     
/* 212 */     ret.append(" (");
/* 213 */     ret.append(formatPosition(world, x, y, z));
/* 214 */     ret.append(")");
/*     */     
/* 216 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public static String formatPosition(TileEntity te) {
/* 220 */     return formatPosition((IBlockAccess)te.func_145831_w(), te.func_174877_v());
/*     */   }
/*     */   
/*     */   public static String formatPosition(IBlockAccess world, BlockPos pos) {
/* 224 */     return formatPosition(world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatPosition(IBlockAccess world, int x, int y, int z) {
/*     */     int dimId;
/* 230 */     if (world instanceof World && ((World)world).field_73011_w != null) {
/* 231 */       dimId = ((World)world).field_73011_w.getDimension();
/*     */     } else {
/* 233 */       dimId = Integer.MIN_VALUE;
/*     */     } 
/*     */     
/* 236 */     if (!includeWorldHash) {
/* 237 */       return formatPosition(dimId, x, y, z);
/*     */     }
/* 239 */     return String.format("dim %d (@%x): %d/%d/%d", new Object[] { Integer.valueOf(dimId), Integer.valueOf(System.identityHashCode(world)), Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String formatPosition(int dimId, int x, int y, int z) {
/* 244 */     return "dim " + dimId + ": " + x + "/" + y + "/" + z;
/*     */   }
/*     */   
/*     */   public static String formatPosition(BlockPos pos) {
/* 248 */     return formatPosition(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
/*     */   }
/*     */   
/*     */   public static String formatPosition(int x, int y, int z) {
/* 252 */     return x + "/" + y + "/" + z;
/*     */   }
/*     */   public static String toSiString(double value, int digits) {
/*     */     String si;
/* 256 */     if (value == 0.0D) return "0 "; 
/* 257 */     if (Double.isNaN(value)) return "NaN "; 
/* 258 */     String ret = "";
/*     */     
/* 260 */     if (value < 0.0D) {
/* 261 */       ret = "-";
/* 262 */       value = -value;
/*     */     } 
/*     */     
/* 265 */     if (Double.isInfinite(value)) return ret + "∞ ";
/*     */ 
/*     */     
/* 268 */     double log = Math.log10(value);
/*     */ 
/*     */ 
/*     */     
/* 272 */     if (log >= 0.0D) {
/* 273 */       int reduce = (int)Math.floor(log / 3.0D);
/* 274 */       mul = 1.0D / Math.pow(10.0D, (reduce * 3));
/*     */       
/* 276 */       switch (reduce) { case 0:
/* 277 */           si = ""; break;
/* 278 */         case 1: si = "k"; break;
/* 279 */         case 2: si = "M"; break;
/* 280 */         case 3: si = "G"; break;
/* 281 */         case 4: si = "T"; break;
/* 282 */         case 5: si = "P"; break;
/* 283 */         case 6: si = "E"; break;
/* 284 */         case 7: si = "Z"; break;
/* 285 */         case 8: si = "Y"; break;
/* 286 */         default: si = "E" + (reduce * 3); break; }
/*     */     
/*     */     } else {
/* 289 */       int expand = (int)Math.ceil(-log / 3.0D);
/* 290 */       mul = Math.pow(10.0D, (expand * 3));
/*     */       
/* 292 */       switch (expand) { case 0:
/* 293 */           si = ""; break;
/* 294 */         case 1: si = "m"; break;
/* 295 */         case 2: si = "µ"; break;
/* 296 */         case 3: si = "n"; break;
/* 297 */         case 4: si = "p"; break;
/* 298 */         case 5: si = "f"; break;
/* 299 */         case 6: si = "a"; break;
/* 300 */         case 7: si = "z"; break;
/* 301 */         case 8: si = "y"; break;
/* 302 */         default: si = "E-" + (expand * 3);
/*     */           break; }
/*     */ 
/*     */     
/*     */     } 
/* 307 */     value *= mul;
/*     */ 
/*     */     
/* 310 */     int iVal = (int)Math.floor(value);
/* 311 */     value -= iVal;
/*     */ 
/*     */     
/* 314 */     int iDigits = 1;
/* 315 */     if (iVal > 0) iDigits = (int)(iDigits + Math.floor(Math.log10(iVal)));
/*     */ 
/*     */     
/* 318 */     double mul = Math.pow(10.0D, (digits - iDigits));
/* 319 */     int dVal = (int)Math.round(value * mul);
/*     */ 
/*     */     
/* 322 */     if (dVal >= mul) {
/* 323 */       iVal++;
/* 324 */       dVal = (int)(dVal - mul);
/*     */ 
/*     */       
/* 327 */       iDigits = 1;
/* 328 */       if (iVal > 0) iDigits = (int)(iDigits + Math.floor(Math.log10(iVal)));
/*     */     
/*     */     } 
/*     */     
/* 332 */     ret = ret + Integer.toString(iVal);
/*     */ 
/*     */     
/* 335 */     if (digits > iDigits && dVal != 0) {
/* 336 */       ret = ret + String.format(".%0" + (digits - iDigits) + "d", new Object[] { Integer.valueOf(dVal) });
/*     */     }
/*     */ 
/*     */     
/* 340 */     ret = ret.replaceFirst("(\\.\\d*?)0+$", "$1");
/*     */     
/* 342 */     return ret + " " + si;
/*     */   }
/*     */   
/*     */   public static void exit(int status) {
/* 346 */     Method exit = null;
/*     */     
/*     */     try {
/* 349 */       exit = Class.forName("java.lang.Shutdown").getDeclaredMethod("exit", new Class[] { int.class });
/* 350 */       exit.setAccessible(true);
/* 351 */     } catch (Exception e) {
/* 352 */       IC2.log.warn(LogCategory.General, e, "Method lookup failed.");
/*     */       
/*     */       try {
/* 355 */         Field security = System.class.getDeclaredField("security");
/* 356 */         security.setAccessible(true);
/* 357 */         security.set((Object)null, (Object)null);
/*     */         
/* 359 */         exit = System.class.getMethod("exit", new Class[] { int.class });
/* 360 */       } catch (Exception f) {
/* 361 */         throw new Error(f);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 366 */       exit.invoke((Object)null, new Object[] { Integer.valueOf(status) });
/* 367 */     } catch (Exception e) {
/* 368 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Vector3 getEyePosition(Entity entity) {
/* 373 */     return new Vector3(entity.field_70165_t, entity.field_70163_u + entity.func_70047_e(), entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static Vector3 getLook(Entity entity) {
/* 377 */     return new Vector3(entity.func_70040_Z());
/*     */   }
/*     */   
/*     */   public static Vector3 getLookScaled(Entity entity) {
/* 381 */     return getLook(entity).scale(getReachDistance(entity));
/*     */   }
/*     */   
/*     */   public static double getReachDistance(Entity entity) {
/* 385 */     if (entity instanceof EntityPlayerMP) {
/* 386 */       return ((EntityPlayerMP)entity).field_71134_c.getBlockReachDistance();
/*     */     }
/* 388 */     return 5.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RayTraceResult traceBlocks(EntityPlayer player, boolean liquid) {
/* 393 */     return traceBlocks(player, liquid, !liquid, false);
/*     */   }
/*     */   
/*     */   public static RayTraceResult traceBlocks(EntityPlayer player, boolean liquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/* 397 */     Vector3 start = getEyePosition((Entity)player);
/* 398 */     Vector3 end = getLookScaled((Entity)player).add(start);
/*     */     
/* 400 */     return player.func_130014_f_().func_147447_a(start.toVec3(), end.toVec3(), liquid, ignoreBlockWithoutBoundingBox, returnLastUncollidableBlock);
/*     */   }
/*     */   
/*     */   public static RayTraceResult traceEntities(EntityPlayer player, boolean alwaysCollide) {
/* 404 */     Vector3 start = getEyePosition((Entity)player);
/*     */     
/* 406 */     return traceEntities(player.func_130014_f_(), start.toVec3(), getLookScaled((Entity)player).add(start).toVec3(), (Entity)player, alwaysCollide);
/*     */   }
/*     */   
/*     */   public static RayTraceResult traceEntities(EntityPlayer player, Vec3d end, boolean alwaysCollide) {
/* 410 */     return traceEntities(player.func_130014_f_(), getEyePosition((Entity)player).toVec3(), end, (Entity)player, alwaysCollide);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RayTraceResult traceEntities(World world, Vec3d start, Vec3d end, Entity exclude, boolean alwaysCollide) {
/* 419 */     AxisAlignedBB aabb = new AxisAlignedBB(Math.min(start.field_72450_a, end.field_72450_a), Math.min(start.field_72448_b, end.field_72448_b), Math.min(start.field_72449_c, end.field_72449_c), Math.max(start.field_72450_a, end.field_72450_a), Math.max(start.field_72448_b, end.field_72448_b), Math.max(start.field_72449_c, end.field_72449_c));
/*     */     
/* 421 */     List<Entity> entities = world.func_72839_b(exclude, aabb);
/* 422 */     RayTraceResult closest = null;
/* 423 */     double minDist = Double.POSITIVE_INFINITY;
/*     */     
/* 425 */     for (Entity entity : entities) {
/* 426 */       if (!alwaysCollide && !entity.func_70067_L())
/*     */         continue; 
/* 428 */       RayTraceResult pos = entity.func_174813_aQ().func_72327_a(start, end);
/* 429 */       if (pos == null)
/*     */         continue; 
/* 431 */       double distance = start.func_72436_e(pos.field_72307_f);
/*     */       
/* 433 */       if (distance < minDist) {
/* 434 */         pos.field_72308_g = entity;
/* 435 */         pos.field_72313_a = RayTraceResult.Type.ENTITY;
/*     */         
/* 437 */         minDist = distance;
/* 438 */         closest = pos;
/*     */       } 
/*     */     } 
/*     */     
/* 442 */     return closest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFakePlayer(EntityPlayer entity, boolean fuzzy) {
/* 452 */     if (entity == null) return false;
/*     */ 
/*     */     
/* 455 */     if (!(entity instanceof EntityPlayerMP)) return true; 
/* 456 */     if (fuzzy) {
/* 457 */       return entity instanceof net.minecraftforge.common.util.FakePlayer;
/*     */     }
/*     */     
/* 460 */     return (entity.getClass() != EntityPlayerMP.class);
/*     */   }
/*     */   
/*     */   public static World getWorld(IBlockAccess world) {
/*     */     Field field;
/* 465 */     if (world == null) return null; 
/* 466 */     if (world instanceof World) return (World)world;
/*     */     
/* 468 */     Class<? extends IBlockAccess> cls = (Class)world.getClass();
/*     */ 
/*     */     
/* 471 */     synchronized (worldFieldCache) {
/* 472 */       field = worldFieldCache.get(cls);
/*     */       
/* 474 */       if (field == null && !worldFieldCache.containsKey(cls)) {
/* 475 */         field = ReflectionUtil.getFieldRecursive(world.getClass(), World.class, false);
/* 476 */         worldFieldCache.put(cls, field);
/*     */       } 
/*     */     } 
/*     */     
/* 480 */     if (field != null) {
/*     */       try {
/* 482 */         return (World)field.get(world);
/* 483 */       } catch (Exception e) {
/* 484 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/* 487 */     return null;
/*     */   }
/*     */ 
/*     */   
/* 491 */   private static final Map<Class<? extends IBlockAccess>, Field> worldFieldCache = new IdentityHashMap<>();
/*     */   
/*     */   public static Chunk getLoadedChunk(World world, int chunkX, int chunkZ) {
/* 494 */     Chunk chunk = null;
/*     */     
/* 496 */     if (world.func_72863_F() instanceof ChunkProviderServer) {
/* 497 */       ChunkProviderServer cps = (ChunkProviderServer)world.func_72863_F();
/*     */       
/*     */       try {
/* 500 */         chunk = (Chunk)cps.field_73244_f.get(ChunkPos.func_77272_a(chunkX, chunkZ));
/* 501 */       } catch (NoSuchFieldError e) {
/* 502 */         if (cps.func_73149_a(chunkX, chunkZ)) {
/* 503 */           chunk = cps.func_186025_d(chunkX, chunkZ);
/*     */         }
/*     */       } 
/*     */     } else {
/* 507 */       chunk = world.func_72964_e(chunkX, chunkZ);
/*     */     } 
/*     */     
/* 510 */     if (chunk instanceof net.minecraft.world.chunk.EmptyChunk) {
/* 511 */       return null;
/*     */     }
/* 513 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkMcCoordBounds(int x, int y, int z) {
/* 518 */     return (checkMcCoordBounds(x, z) && y >= 0 && y < 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean checkMcCoordBounds(int x, int z) {
/* 523 */     return (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean checkInterfaces(Class<?> cls) {
/* 534 */     Boolean cached = checkedClasses.get(cls);
/* 535 */     if (cached != null) return cached.booleanValue();
/*     */     
/* 537 */     Set<Class<?>> interfaces = Collections.newSetFromMap(new IdentityHashMap<>());
/* 538 */     Class<?> c = cls;
/*     */     
/*     */     do {
/* 541 */       for (Class<?> i : c.getInterfaces()) {
/* 542 */         interfaces.add(i);
/*     */       }
/*     */       
/* 545 */       c = c.getSuperclass();
/* 546 */     } while (c != null);
/*     */     
/* 548 */     boolean result = true;
/*     */     
/* 550 */     for (Class<?> iface : interfaces) {
/* 551 */       for (Method method : iface.getMethods()) {
/* 552 */         boolean found = false;
/* 553 */         c = cls;
/*     */         
/*     */         do {
/*     */           try {
/* 557 */             Method match = c.getDeclaredMethod(method.getName(), method.getParameterTypes());
/*     */             
/* 559 */             if (method.getReturnType().isAssignableFrom(match.getReturnType())) {
/* 560 */               found = true;
/*     */               break;
/*     */             } 
/* 563 */           } catch (NoSuchMethodException noSuchMethodException) {}
/*     */           
/* 565 */           c = c.getSuperclass();
/* 566 */         } while (c != null);
/*     */         
/* 568 */         if (!found) {
/* 569 */           IC2.log.info(LogCategory.General, "Can't find method %s.%s in %s.", new Object[] { method.getDeclaringClass().getName(), method.getName(), cls.getName() });
/* 570 */           result = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 575 */     checkedClasses.put(cls, Boolean.valueOf(result));
/*     */     
/* 577 */     return result;
/*     */   }
/*     */   
/*     */   public static IBlockState getBlockState(IBlockAccess world, BlockPos pos) {
/* 581 */     IBlockState state = world.func_180495_p(pos);
/*     */     
/* 583 */     return state.func_185899_b(world, pos);
/*     */   }
/*     */   
/*     */   public static Block getBlock(String name) {
/* 587 */     if (name == null) throw new NullPointerException("null name");
/*     */     
/* 589 */     return getBlock(new ResourceLocation(name));
/*     */   }
/*     */   
/*     */   public static Block getBlock(ResourceLocation loc) {
/* 593 */     Block ret = (Block)Block.field_149771_c.func_82594_a(loc);
/* 594 */     if (ret != Blocks.field_150350_a) return ret;
/*     */     
/* 596 */     if (loc.func_110624_b().equals("minecraft") && loc.func_110623_a().equals("air")) {
/* 597 */       return ret;
/*     */     }
/* 599 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation getName(Block block) {
/* 604 */     return (ResourceLocation)Block.field_149771_c.func_177774_c(block);
/*     */   }
/*     */   
/*     */   public static Item getItem(String name) {
/* 608 */     if (name == null) throw new NullPointerException("null name");
/*     */     
/* 610 */     return getItem(new ResourceLocation(name));
/*     */   }
/*     */   
/*     */   public static Item getItem(ResourceLocation loc) {
/* 614 */     return (Item)Item.field_150901_e.func_82594_a(loc);
/*     */   }
/*     */   
/*     */   public static ResourceLocation getName(Item item) {
/* 618 */     return (ResourceLocation)Item.field_150901_e.func_177774_c(item);
/*     */   }
/*     */   
/*     */   public static boolean harvestBlock(World world, BlockPos pos) {
/* 622 */     if (world.field_72995_K) return false;
/*     */ 
/*     */     
/* 625 */     IBlockState state = world.func_180495_p(pos);
/* 626 */     Block block = state.func_177230_c();
/* 627 */     TileEntity te = world.func_175625_s(pos);
/* 628 */     EntityPlayer player = Ic2Player.get(world);
/*     */     
/* 630 */     boolean canHarvest = block.canHarvestBlock((IBlockAccess)world, pos, player);
/*     */     
/* 632 */     block.func_176208_a(world, pos, state, player);
/*     */     
/* 634 */     boolean removed = block.removedByPlayer(state, world, pos, player, canHarvest);
/*     */     
/* 636 */     if (canHarvest && removed) {
/* 637 */       block.func_180657_a(world, player, pos, state, te, new ItemStack(Items.field_151046_w));
/*     */     }
/*     */     
/* 640 */     return removed;
/*     */   }
/*     */   
/* 643 */   public static Set<EnumFacing> noFacings = Collections.emptySet();
/* 644 */   public static Set<EnumFacing> onlyNorth = Collections.unmodifiableSet(EnumSet.of(EnumFacing.NORTH));
/* 645 */   public static Set<EnumFacing> horizontalFacings = Collections.unmodifiableSet(EnumSet.copyOf(Arrays.asList(EnumFacing.field_176754_o)));
/* 646 */   public static Set<EnumFacing> verticalFacings = Collections.unmodifiableSet(EnumSet.of(EnumFacing.DOWN, EnumFacing.UP));
/* 647 */   public static Set<EnumFacing> downSideFacings = Collections.unmodifiableSet(EnumSet.complementOf(EnumSet.of(EnumFacing.UP)));
/* 648 */   public static Set<EnumFacing> allFacings = Collections.unmodifiableSet(EnumSet.allOf(EnumFacing.class));
/*     */   
/* 650 */   private static final boolean inDev = (System.getProperty("INDEV") != null);
/* 651 */   private static final boolean includeWorldHash = (System.getProperty("ic2.debug.includeworldhash") != null);
/* 652 */   private static final Map<Class<?>, Boolean> checkedClasses = new IdentityHashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */