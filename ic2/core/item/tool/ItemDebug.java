/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.item.IBoxable;
/*     */ import ic2.api.item.IDebuggable;
/*     */ import ic2.api.item.IElectricItemManager;
/*     */ import ic2.api.item.ISpecialElectricItem;
/*     */ import ic2.api.reactor.IReactor;
/*     */ import ic2.api.tile.IEnergyStorage;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Energy;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
/*     */ import ic2.core.block.personal.IPersonalBlock;
/*     */ import ic2.core.crop.TileEntityCrop;
/*     */ import ic2.core.item.InfiniteElectricItemManager;
/*     */ import ic2.core.item.ItemIC2;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemDebug
/*     */   extends ItemIC2
/*     */   implements ISpecialElectricItem, IBoxable
/*     */ {
/*     */   public ItemDebug() {
/*  68 */     super(ItemName.debug_item);
/*     */     
/*  70 */     func_77627_a(false);
/*     */     
/*  72 */     if (!Util.inDev()) func_77637_a(null);  } public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*     */     RayTraceResult position;
/*     */     TileEntity tileEntity, te;
/*     */     String plat;
/*     */     int count;
/*  77 */     NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(StackUtil.get(player, hand));
/*     */     
/*  79 */     int modeIdx = nbtData.func_74762_e("mode");
/*  80 */     if (modeIdx < 0 || modeIdx >= Mode.modes.length) modeIdx = 0;
/*     */     
/*  82 */     Mode mode = Mode.modes[modeIdx];
/*     */     
/*  84 */     if (IC2.keyboard.isModeSwitchKeyDown(player)) {
/*  85 */       if (!world.field_72995_K) {
/*  86 */         mode = Mode.modes[(mode.ordinal() + 1) % Mode.modes.length];
/*     */         
/*  88 */         nbtData.func_74768_a("mode", mode.ordinal());
/*  89 */         IC2.platform.messagePlayer(player, "Debug Item Mode: " + mode.getName(), new Object[0]);
/*     */         
/*  91 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*  93 */       return EnumActionResult.PASS;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     TileEntity tileentity = world.func_175625_s(pos);
/*     */     
/*  99 */     if (tileentity instanceof IDebuggable) {
/* 100 */       if (world.field_72995_K) return EnumActionResult.PASS;
/*     */       
/* 102 */       IDebuggable dbg = (IDebuggable)tileentity;
/*     */       
/* 104 */       if (dbg.isDebuggable() && 
/* 105 */         !world.field_72995_K) {
/* 106 */         IC2.platform.messagePlayer(player, dbg.getDebugText(), new Object[0]);
/*     */       }
/*     */ 
/*     */       
/* 110 */       return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
/*     */     } 
/*     */     
/* 113 */     ByteArrayOutputStream consoleBuffer = new ByteArrayOutputStream();
/* 114 */     PrintStream console = new PrintStream(consoleBuffer);
/* 115 */     ByteArrayOutputStream chatBuffer = new ByteArrayOutputStream();
/* 116 */     PrintStream chat = new PrintStream(chatBuffer);
/*     */     
/* 118 */     switch (mode) {
/*     */ 
/*     */       
/*     */       case InterfacesFields:
/*     */       case InterfacesFieldsRetrace:
/* 123 */         if (mode == Mode.InterfacesFields) {
/* 124 */           position = new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(hitX, hitY, hitX), side, pos);
/*     */         } else {
/* 126 */           position = func_77621_a(world, player, true);
/* 127 */           if (position == null) return EnumActionResult.PASS;
/*     */           
/* 129 */           RayTraceResult entityPosition = Util.traceEntities(player, position.field_72307_f, true);
/* 130 */           if (entityPosition != null) position = entityPosition;
/*     */         
/*     */         } 
/*     */ 
/*     */         
/* 135 */         if (FMLCommonHandler.instance().getSide().isClient()) {
/* 136 */           if (!world.field_72995_K) {
/* 137 */             plat = "sp server";
/* 138 */           } else if (player.func_184102_h() == null) {
/* 139 */             plat = "mp client";
/*     */           } else {
/* 141 */             plat = "sp client";
/*     */           } 
/*     */         } else {
/* 144 */           plat = "mp server";
/*     */         } 
/*     */         
/* 147 */         if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 148 */           pos = position.func_178782_a();
/*     */           
/* 150 */           IBlockState state = world.func_180495_p(pos);
/* 151 */           Block block = state.func_177230_c();
/* 152 */           TileEntity tileEntity1 = world.func_175625_s(pos);
/*     */           
/* 154 */           String message = String.format("[%s] block state: %s%nname: %s%ncls: %s%nte: %s", new Object[] { plat, state
/* 155 */                 .func_185899_b((IBlockAccess)world, pos), block.func_149739_a(), block.getClass().getName(), tileEntity1 });
/*     */           
/* 157 */           chat.println(message);
/* 158 */           console.println(message);
/*     */           
/* 160 */           if (tileEntity1 != null) {
/* 161 */             message = "[" + plat + "] interfaces:";
/*     */             
/* 163 */             Class<?> c = tileEntity1.getClass();
/*     */             
/*     */             do {
/* 166 */               for (Class<?> i : c.getInterfaces()) {
/* 167 */                 message = message + " " + i.getName();
/*     */               }
/*     */               
/* 170 */               c = c.getSuperclass();
/* 171 */             } while (c != null);
/*     */             
/* 173 */             chat.println(message);
/* 174 */             console.println(message);
/*     */           } 
/*     */           
/* 177 */           console.println("block fields:");
/* 178 */           dumpObjectFields(console, block);
/*     */           
/* 180 */           if (tileEntity1 != null) {
/* 181 */             console.println();
/* 182 */             console.println("tile entity fields:");
/* 183 */             dumpObjectFields(console, tileEntity1);
/*     */           }  break;
/* 185 */         }  if (position.field_72313_a == RayTraceResult.Type.ENTITY) {
/* 186 */           String message = "[" + plat + "] entity: " + position.field_72308_g;
/* 187 */           chat.println(message);
/* 188 */           console.println(message);
/*     */           
/* 190 */           if (position.field_72308_g instanceof EntityItem) {
/* 191 */             ItemStack entStack = ((EntityItem)position.field_72308_g).func_92059_d();
/* 192 */             String name = Util.getName(entStack.func_77973_b()).toString();
/* 193 */             message = "[" + plat + "] item id: " + name + " meta: " + entStack.func_77952_i() + " size: " + StackUtil.getSize(entStack) + " name: " + entStack.func_77977_a();
/*     */             
/* 195 */             chat.println(message);
/* 196 */             console.println(message);
/* 197 */             console.println("NBT: " + entStack.func_77978_p());
/*     */           }  break;
/*     */         } 
/* 200 */         return EnumActionResult.PASS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case TileData:
/* 207 */         if (world.field_72995_K) return EnumActionResult.PASS;
/*     */         
/* 209 */         tileEntity = world.func_175625_s(pos);
/*     */         
/* 211 */         if (tileEntity instanceof TileEntityBlock) {
/* 212 */           TileEntityBlock tileEntityBlock = (TileEntityBlock)tileEntity;
/* 213 */           chat.println("Block: Active=" + tileEntityBlock.getActive() + " Facing=" + tileEntityBlock.getFacing());
/*     */           
/* 215 */           for (TileEntityComponent comp : tileEntityBlock.getComponents()) {
/* 216 */             if (comp instanceof Energy) {
/* 217 */               Energy energy = (Energy)comp;
/* 218 */               chat.printf("Energy: %.2f / %.2f%n", new Object[] { Double.valueOf(energy.getEnergy()), Double.valueOf(energy.getCapacity()) }); continue;
/* 219 */             }  if (comp instanceof Redstone) {
/* 220 */               Redstone redstone = (Redstone)comp;
/* 221 */               chat.printf("Redstone: %d%n", new Object[] { Integer.valueOf(redstone.getRedstoneInput()) });
/*     */             } 
/*     */           } 
/*     */         } 
/* 225 */         if (tileEntity instanceof TileEntityBaseGenerator) {
/* 226 */           TileEntityBaseGenerator tileEntityBaseGenerator = (TileEntityBaseGenerator)tileEntity;
/* 227 */           chat.println("BaseGen: Fuel=" + tileEntityBaseGenerator.fuel);
/*     */         } 
/* 229 */         if (tileEntity instanceof IEnergyStorage) {
/* 230 */           IEnergyStorage iEnergyStorage = (IEnergyStorage)tileEntity;
/* 231 */           chat.println("EnergyStorage: Stored=" + iEnergyStorage.getStored());
/*     */         } 
/* 233 */         if (tileEntity instanceof IReactor) {
/* 234 */           IReactor iReactor = (IReactor)tileEntity;
/* 235 */           chat.println("Reactor: Heat=" + iReactor.getHeat() + " MaxHeat=" + iReactor.getMaxHeat() + " HEM=" + iReactor.getHeatEffectModifier() + " Output=" + iReactor.getReactorEnergyOutput());
/*     */         } 
/* 237 */         if (tileEntity instanceof IPersonalBlock) {
/* 238 */           IPersonalBlock iPersonalBlock = (IPersonalBlock)tileEntity;
/* 239 */           chat.println("PersonalBlock: CanAccess=" + iPersonalBlock.permitsAccess(player.func_146103_bH()));
/*     */         } 
/* 241 */         if (tileEntity instanceof TileEntityCrop) {
/* 242 */           TileEntityCrop tileEntityCrop = (TileEntityCrop)tileEntity;
/* 243 */           CropCard crop = tileEntityCrop.getCrop();
/* 244 */           String id = (crop != null) ? (crop.getOwner() + ":" + crop.getId()) : "none";
/*     */           
/* 246 */           chat.printf("Crop: Crop=%s Size=%d Growth=%d Gain=%d Resistance=%d Nutrients=%d Water=%d GrowthPoints=%d%n Cross=%b", new Object[] { id, 
/*     */                 
/* 248 */                 Integer.valueOf(tileEntityCrop.getCurrentSize()), 
/* 249 */                 Integer.valueOf(tileEntityCrop.getStatGrowth()), Integer.valueOf(tileEntityCrop.getStatGain()), Integer.valueOf(tileEntityCrop.getStatResistance()), 
/* 250 */                 Integer.valueOf(tileEntityCrop.getStorageNutrients()), Integer.valueOf(tileEntityCrop.getStorageWater()), 
/* 251 */                 Integer.valueOf(tileEntityCrop.getGrowthPoints()), 
/* 252 */                 Boolean.valueOf(tileEntityCrop.isCrossingBase()) });
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EnergyNet:
/* 259 */         if (world.field_72995_K) return EnumActionResult.PASS;
/*     */         
/* 261 */         if (!EnergyNet.instance.dumpDebugInfo(world, pos, console, chat)) {
/* 262 */           return EnumActionResult.PASS;
/*     */         }
/*     */         break;
/*     */       
/*     */       case Accelerate:
/*     */       case AccelerateX100:
/* 268 */         if (world.field_72995_K) return EnumActionResult.PASS;
/*     */         
/* 270 */         te = world.func_175625_s(pos);
/*     */         
/* 272 */         count = (mode == Mode.Accelerate) ? 1000 : 100000;
/*     */         
/* 274 */         if (te == null) {
/* 275 */           IBlockState state = world.func_180495_p(pos);
/* 276 */           if (state.func_177230_c().func_149653_t()) {
/* 277 */             chat.println("Running" + count + " ticks on " + state.func_177230_c() + "(" + pos + ").");
/*     */             int i;
/* 279 */             for (i = 0; i < count && world.func_180495_p(pos) == state; i++) {
/* 280 */               state.func_177230_c().func_180645_a(world, pos, state, field_77697_d);
/*     */             }
/* 282 */             if (i != count)
/* 283 */               chat.println("Ran " + i + " ticks before a state change."); 
/*     */           }  break;
/*     */         } 
/* 286 */         if (te instanceof ITickable) {
/* 287 */           ITickable tickable = (ITickable)te;
/*     */           
/* 289 */           chat.println("Running " + count + " ticks on " + te + ".");
/* 290 */           int changes = 0;
/* 291 */           int interruptCount = -1;
/*     */           
/* 293 */           for (int i = 0; i < count; i++) {
/* 294 */             if (te.func_145837_r()) {
/* 295 */               changes++;
/* 296 */               te = world.func_175625_s(pos);
/*     */               
/* 298 */               if (!(te instanceof ITickable) || te.func_145837_r()) {
/* 299 */                 interruptCount = i;
/*     */                 break;
/*     */               } 
/* 302 */               tickable = (ITickable)te;
/*     */             } 
/*     */ 
/*     */             
/* 306 */             tickable.func_73660_a();
/*     */           } 
/*     */           
/* 309 */           if (changes > 0) {
/* 310 */             if (interruptCount != -1) {
/* 311 */               chat.println("The tile entity changed " + changes + " time(s), interrupted after " + interruptCount + " updates."); break;
/*     */             } 
/* 313 */             chat.println("The tile entity changed " + changes + " time(s).");
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     console.flush();
/* 323 */     chat.flush();
/*     */     
/* 325 */     if (world.field_72995_K) {
/*     */       try {
/* 327 */         consoleBuffer.writeTo(new FileOutputStream(FileDescriptor.out));
/* 328 */       } catch (IOException e) {
/* 329 */         IC2.log.warn(LogCategory.Item, e, "Stdout write failed.");
/*     */       } 
/*     */       
/* 332 */       for (String line : chatBuffer.toString().split("[\\r\\n]+")) {
/* 333 */         IC2.platform.messagePlayer(player, line, new Object[0]);
/*     */       }
/* 335 */     } else if (player instanceof EntityPlayerMP) {
/*     */       try {
/* 337 */         ((NetworkManager)IC2.network.get(true)).sendConsole((EntityPlayerMP)player, consoleBuffer.toString("UTF-8"));
/* 338 */         ((NetworkManager)IC2.network.get(true)).sendChat((EntityPlayerMP)player, chatBuffer.toString("UTF-8"));
/* 339 */       } catch (UnsupportedEncodingException e) {
/* 340 */         IC2.log.warn(LogCategory.Item, e, "String encoding failed.");
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     return world.field_72995_K ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
/*     */   }
/*     */   
/*     */   private static void dumpObjectFields(PrintStream ps, Object o) {
/* 348 */     Class<?> fieldDeclaringClass = o.getClass();
/*     */     
/*     */     do {
/* 351 */       Field[] fields = fieldDeclaringClass.getDeclaredFields();
/*     */       
/* 353 */       for (Field field : fields) {
/* 354 */         if ((field.getModifiers() & 0x8) == 0 || (
/* 355 */           fieldDeclaringClass != Block.class && fieldDeclaringClass != TileEntity.class)) {
/*     */           Object<Object> value;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 361 */           boolean accessible = field.isAccessible();
/* 362 */           field.setAccessible(true);
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 367 */             value = (Object<Object>)field.get(o);
/* 368 */           } catch (IllegalAccessException e) {
/* 369 */             value = (Object<Object>)"<can't access>";
/*     */           } 
/*     */           
/* 372 */           ps.println(field.getName() + " class: " + fieldDeclaringClass.getName() + " type: " + field.getType());
/* 373 */           ps.printf("  identity hash: %x hash: %x modifiers: %x%n", new Object[] { Integer.valueOf(System.identityHashCode(value)), Integer.valueOf((value == null) ? 0 : value.hashCode()), Integer.valueOf(field.getModifiers()) });
/*     */           
/* 375 */           if (value != null && field.getType().isArray()) {
/* 376 */             List<Object> array = new ArrayList();
/*     */             
/* 378 */             for (int i = 0; i < Array.getLength(value); i++) {
/* 379 */               array.add(Array.get(value, i));
/*     */             }
/*     */             
/* 382 */             value = (Object<Object>)array;
/*     */           } 
/*     */           
/* 385 */           if (value instanceof Iterable) {
/* 386 */             ps.println("  values (" + ((value instanceof Collection) ? (String)Integer.valueOf(((Collection)value).size()) : "?") + "):");
/*     */             
/* 388 */             int i = 0;
/*     */             
/* 390 */             for (Object o2 : value) {
/* 391 */               ps.print("    [" + i++ + "] ");
/* 392 */               dumpValueString(o2, field, "      ", ps);
/*     */             } 
/* 394 */           } else if (value instanceof Map) {
/* 395 */             ps.println("  values (" + ((Map)value).size() + "):");
/*     */             
/* 397 */             for (Map.Entry<?, ?> entry : (Iterable<Map.Entry<?, ?>>)((Map)value).entrySet()) {
/* 398 */               ps.print("    " + entry.getKey() + ": ");
/* 399 */               dumpValueString(entry.getValue(), field, "      ", ps);
/*     */             } 
/*     */           } else {
/* 402 */             ps.print("  value: ");
/* 403 */             dumpValueString(value, field, "    ", ps);
/*     */           } 
/*     */           
/* 406 */           field.setAccessible(accessible);
/*     */         } 
/*     */       } 
/* 409 */       fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/* 410 */     } while (fieldDeclaringClass != null);
/*     */   }
/*     */   private static void dumpValueString(Object o, Field parentField, String prefix, PrintStream out) {
/*     */     String ret;
/* 414 */     if (o == null) {
/* 415 */       out.println("<null>");
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 421 */     if (o.getClass().isArray()) {
/* 422 */       ret = "";
/*     */       
/* 424 */       for (int i = 0; i < Array.getLength(o); i++) {
/* 425 */         String valStr; Object val = Array.get(o, i);
/*     */ 
/*     */         
/* 428 */         if (val == null) {
/* 429 */           valStr = "<null>";
/*     */         } else {
/* 431 */           valStr = val.toString();
/*     */           
/* 433 */           if (valStr.length() > 32) {
/* 434 */             valStr = valStr.substring(0, 20) + "... (" + (valStr.length() - 20) + " more)";
/*     */           }
/*     */         } 
/*     */         
/* 438 */         ret = ret + " [" + i + "] " + valStr;
/*     */       } 
/*     */     } else {
/* 441 */       ret = o.toString();
/*     */     } 
/*     */     
/* 444 */     if (ret.length() > 100) {
/* 445 */       ret = ret.substring(0, 90) + "... (" + (ret.length() - 90) + " more)";
/*     */     }
/*     */     
/* 448 */     out.println(ret);
/*     */     
/* 450 */     if (o instanceof net.minecraftforge.fluids.FluidTank && IC2.platform.isSimulating()) {
/* 451 */       System.out.println();
/*     */     }
/*     */     
/* 454 */     if (Modifier.isStatic(parentField.getModifiers()) || parentField
/* 455 */       .isSynthetic() || o
/* 456 */       .getClass().isArray() || o
/* 457 */       .getClass().isEnum() || o
/* 458 */       .getClass().isPrimitive() || o instanceof Iterable || o instanceof Class || o instanceof String) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 463 */     if (o instanceof World) {
/* 464 */       out.println(prefix + " dim: " + ((World)o).field_73011_w.getDimension());
/* 465 */     } else if (!(o instanceof net.minecraft.block.state.BlockStateContainer) && !(o instanceof Block) && !(o instanceof TileEntity) && !(o instanceof net.minecraft.item.Item) && !(o instanceof ItemStack) && !(o instanceof net.minecraft.util.math.Vec3i) && !(o instanceof Vec3d) && !(o instanceof net.minecraft.nbt.NBTBase) && 
/* 466 */       !o.getClass().getName().startsWith("java.")) {
/* 467 */       Class<?> fieldDeclaringClass = o.getClass();
/*     */       
/* 469 */       while (fieldDeclaringClass != null && fieldDeclaringClass != Object.class) {
/* 470 */         for (Field field : fieldDeclaringClass.getDeclaredFields()) {
/* 471 */           if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
/*     */             Object val;
/*     */             
/*     */             String valStr;
/*     */             try {
/* 476 */               field.setAccessible(true);
/*     */               
/* 478 */               val = field.get(o);
/* 479 */             } catch (Exception e) {
/* 480 */               val = "<can't access>";
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 485 */             if (val == o) {
/* 486 */               valStr = "<parent>";
/*     */             } else {
/* 488 */               valStr = toStringLimited(val, 100);
/*     */             } 
/*     */             
/* 491 */             out.println(prefix + field.getName() + ": " + valStr);
/*     */           } 
/*     */         } 
/* 494 */         fieldDeclaringClass = fieldDeclaringClass.getSuperclass();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String toStringLimited(Object o, int limit) {
/* 500 */     if (o == null) return "<null>";
/*     */     
/* 502 */     int extra = 12;
/* 503 */     limit = Math.max(limit, 12);
/*     */     
/* 505 */     String ret = o.toString();
/*     */     
/* 507 */     if (ret.length() > limit) {
/* 508 */       int newLimit = limit - 12;
/* 509 */       return ret.substring(0, newLimit) + "... (" + (ret.length() - newLimit) + " more)";
/*     */     } 
/* 511 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElectricItemManager getManager(ItemStack stack) {
/* 519 */     if (manager == null) manager = (IElectricItemManager)new InfiniteElectricItemManager();
/*     */     
/* 521 */     return manager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeStoredInToolbox(ItemStack itemstack) {
/* 526 */     return true;
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 530 */     InterfacesFields("Interfaces and Fields"),
/* 531 */     InterfacesFieldsRetrace("Interfaces and Fields (liquid/entity)"),
/* 532 */     TileData("Tile Data"),
/* 533 */     EnergyNet("Energy Net"),
/* 534 */     Accelerate("Accelerate"),
/* 535 */     AccelerateX100("Accelerate x100"); private final String name;
/*     */     
/*     */     Mode(String name) {
/*     */       this.name = name;
/*     */     }
/*     */     
/*     */     String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/* 545 */     static final Mode[] modes = values();
/*     */     static {
/*     */     
/*     */     } }
/* 549 */   private static IElectricItemManager manager = null;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */