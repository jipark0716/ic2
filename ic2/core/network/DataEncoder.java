/*     */ package ic2.core.network;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.api.network.IGrowingBuffer;
/*     */ import ic2.api.network.INetworkCustomEncoder;
/*     */ import ic2.api.recipe.IElectrolyzerRecipeManager;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Tuple;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTSizeTracker;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fluids.FluidStack;
/*     */ import net.minecraftforge.fluids.FluidTank;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataEncoder
/*     */ {
/*     */   public static void encode(GrowingBuffer os, Object o) throws IOException {
/*     */     try {
/*  58 */       encode(os, o, true);
/*  59 */     } catch (IllegalArgumentException e) {
/*  60 */       IC2.platform.displayError(e, "An unknown data type was attempted to be encoded for sending through\nmultiplayer.\nThis could happen due to a bug.", new Object[0]);
/*     */     }  } public static void encode(IGrowingBuffer os, Object o, boolean withType) throws IOException { INetworkCustomEncoder ince; Class<?> componentClass; BlockPos blockPos; ChunkPos pos; int len; NBTTagCompound nbt; CropCard cropCard; IElectrolyzerRecipeManager.ElectrolyzerRecipe recipe; EncodedType componentType; IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs; boolean anyTypeMismatch; FluidStack fs; FluidTank tank; GameProfile gp; InvSlot slot; ItemStack stack; ResourceLocation loc;
/*     */     TileEntity te;
/*     */     Tuple.T2<?, ?> t2;
/*     */     Tuple.T3<?, ?, ?> t;
/*     */     UUID uuid;
/*     */     Vec3d v;
/*     */     ItemStack[] contents;
/*     */     int i;
/*  69 */     EncodedType type = typeFromObject(o);
/*     */     
/*  71 */     if (withType) os.writeByte(idFromType(type));
/*     */     
/*  73 */     switch (type) {
/*     */ 
/*     */ 
/*     */       
/*     */       case Addon:
/*     */       case UnSafeAddon:
/*  79 */         assert o != null;
/*  80 */         ince = classToAddonType.get(o.getClass());
/*  81 */         if (ince == null) {
/*  82 */           throw new IllegalStateException("Cannot encode an object without an encoder! Type was " + o.getClass());
/*     */         }
/*  84 */         os.writeString(o.getClass().getName());
/*  85 */         ince.encode(os, o);
/*     */         return;
/*     */       
/*     */       case Array:
/*  89 */         componentClass = o.getClass().getComponentType();
/*  90 */         len = Array.getLength(o);
/*  91 */         if (componentClass == Object.class && len > 0) {
/*     */ 
/*     */           
/*  94 */           boolean isEnum = false;
/*  95 */           Class<?> target = null; int j;
/*  96 */           label174: for (j = 0; j < len; j++) {
/*  97 */             Object value = Array.get(o, j);
/*     */             
/*  99 */             if (target == null) {
/*     */               
/* 101 */               if (value instanceof Enum) {
/*     */                 
/* 103 */                 target = ((Enum)value).getDeclaringClass();
/* 104 */                 isEnum = true;
/* 105 */               } else if (value != null) {
/* 106 */                 target = value.getClass();
/* 107 */                 assert target != Object.class;
/*     */               } 
/* 109 */             } else if (value != null) {
/* 110 */               Class<?> valueClass = value.getClass();
/*     */ 
/*     */               
/* 113 */               if (valueClass != target && !target.isAssignableFrom(valueClass)) {
/*     */                 
/* 115 */                 if (isEnum || value instanceof Enum)
/*     */                 {
/*     */                   
/* 118 */                   throw new IllegalArgumentException("Array of mixed enum entries");
/*     */                 }
/*     */ 
/*     */                 
/* 122 */                 while ((target = target.getSuperclass()) != Object.class) {
/* 123 */                   if (target.isAssignableFrom(valueClass)) {
/*     */                     continue label174;
/*     */                   }
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 139 */                 for (; ++j < len; j++) {
/* 140 */                   if (Array.get(o, j) instanceof Enum)
/*     */                   {
/* 142 */                     throw new IllegalArgumentException("Array of mixed enum entries");
/*     */                   }
/*     */                 } 
/* 145 */                 target = Object.class;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 150 */               assert isEnum == value instanceof Enum;
/* 151 */             } else if (isEnum) {
/*     */ 
/*     */               
/* 154 */               throw new IllegalArgumentException("Enum array with null entry");
/*     */             } 
/*     */           } 
/*     */           
/* 158 */           componentClass = target;
/*     */         } 
/* 160 */         componentType = typeFromClass(componentClass);
/* 161 */         os.writeByte(idFromType(componentType));
/* 162 */         os.writeBoolean(componentClass.isPrimitive());
/* 163 */         if (componentType == EncodedType.Addon || componentType == EncodedType.UnSafeAddon || componentType == EncodedType.Enum) {
/* 164 */           os.writeString(componentClass.getName());
/*     */         }
/* 166 */         os.writeVarInt(len);
/*     */         
/* 168 */         anyTypeMismatch = false;
/*     */         
/* 170 */         for (i = 0; i < len; i++) {
/* 171 */           Object value = Array.get(o, i);
/*     */           
/* 173 */           if (value == null || typeFromClass(value.getClass()) != componentType) {
/* 174 */             anyTypeMismatch = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 179 */         os.writeBoolean(anyTypeMismatch);
/*     */         
/* 181 */         for (i = 0; i < len; i++) {
/* 182 */           encode(os, Array.get(o, i), anyTypeMismatch);
/*     */         }
/*     */         return;
/*     */ 
/*     */       
/*     */       case Block:
/* 188 */         encode(os, Util.getName((Block)o), false);
/*     */         return;
/*     */       case BlockPos:
/* 191 */         blockPos = (BlockPos)o;
/* 192 */         os.writeInt(blockPos.func_177958_n());
/* 193 */         os.writeInt(blockPos.func_177956_o());
/* 194 */         os.writeInt(blockPos.func_177952_p());
/*     */         return;
/*     */       
/*     */       case Boolean:
/* 198 */         os.writeBoolean(((Boolean)o).booleanValue());
/*     */         return;
/*     */       case Byte:
/* 201 */         os.writeByte(((Byte)o).byteValue());
/*     */         return;
/*     */       case Character:
/* 204 */         os.writeChar(((Character)o).charValue());
/*     */         return;
/*     */       case ChunkPos:
/* 207 */         pos = (ChunkPos)o;
/* 208 */         os.writeInt(pos.field_77276_a);
/* 209 */         os.writeInt(pos.field_77275_b);
/*     */         return;
/*     */       case Collection:
/* 212 */         encode(os, ((Collection)o).toArray(), false);
/*     */         return;
/*     */       case Component:
/* 215 */         nbt = ((TileEntityComponent)o).writeToNbt();
/* 216 */         encode(os, (nbt == null) ? new NBTTagCompound() : nbt, false);
/*     */         return;
/*     */       
/*     */       case CropCard:
/* 220 */         cropCard = (CropCard)o;
/* 221 */         os.writeString(cropCard.getOwner());
/* 222 */         os.writeString(cropCard.getId());
/*     */         return;
/*     */       
/*     */       case Double:
/* 226 */         os.writeDouble(((Double)o).doubleValue());
/*     */         return;
/*     */       case ElectrolyzerRecipe:
/* 229 */         recipe = (IElectrolyzerRecipeManager.ElectrolyzerRecipe)o;
/* 230 */         os.writeInt(recipe.inputAmount);
/* 231 */         os.writeInt(recipe.EUaTick);
/* 232 */         os.writeInt(recipe.ticksNeeded);
/* 233 */         outputs = recipe.outputs;
/* 234 */         os.writeByte(outputs.length);
/* 235 */         for (IElectrolyzerRecipeManager.ElectrolyzerOutput output : outputs) {
/* 236 */           os.writeString(output.fluidName);
/* 237 */           os.writeInt(output.fluidAmount);
/* 238 */           os.writeByte(output.tankDirection.func_176745_a());
/*     */         } 
/*     */         return;
/*     */       case Enchantment:
/* 242 */         encode(os, Enchantment.field_185264_b.func_177774_c(o), false);
/*     */         return;
/*     */       case Enum:
/* 245 */         os.writeVarInt(((Enum)o).ordinal());
/*     */         return;
/*     */       case Float:
/* 248 */         os.writeFloat(((Float)o).floatValue());
/*     */         return;
/*     */       case Fluid:
/* 251 */         os.writeString(((Fluid)o).getName());
/*     */         return;
/*     */       case FluidStack:
/* 254 */         fs = (FluidStack)o;
/* 255 */         encode(os, fs.getFluid(), false);
/* 256 */         os.writeInt(fs.amount);
/* 257 */         encode(os, fs.tag, true);
/*     */         return;
/*     */       
/*     */       case FluidTank:
/* 261 */         tank = (FluidTank)o;
/* 262 */         encode(os, tank.getFluid(), true);
/* 263 */         os.writeInt(tank.getCapacity());
/*     */         return;
/*     */ 
/*     */       
/*     */       case GameProfile:
/* 268 */         gp = (GameProfile)o;
/* 269 */         encode(os, gp.getId(), true);
/* 270 */         os.writeString(gp.getName());
/*     */         return;
/*     */       
/*     */       case Integer:
/* 274 */         os.writeInt(((Integer)o).intValue());
/*     */         return;
/*     */       case InvSlot:
/* 277 */         slot = (InvSlot)o;
/* 278 */         contents = new ItemStack[slot.size()];
/*     */         
/* 280 */         for (i = 0; i < slot.size(); i++) {
/* 281 */           contents[i] = slot.get(i);
/*     */         }
/*     */         
/* 284 */         encode(os, contents, false);
/*     */         return;
/*     */       
/*     */       case Item:
/* 288 */         encode(os, Util.getName((Item)o), false);
/*     */         return;
/*     */       case ItemStack:
/* 291 */         stack = (ItemStack)o;
/*     */         
/* 293 */         if (StackUtil.isEmpty(stack)) {
/* 294 */           os.writeByte(0);
/*     */         } else {
/* 296 */           os.writeByte(StackUtil.getSize(stack));
/* 297 */           encode(os, stack.func_77973_b(), false);
/* 298 */           os.writeShort(stack.func_77952_i());
/* 299 */           encode(os, stack.func_77978_p(), true);
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case Long:
/* 305 */         os.writeLong(((Long)o).longValue());
/*     */         return;
/*     */       case NBTTagCompound:
/* 308 */         CompressedStreamTools.func_74800_a((NBTTagCompound)o, (DataOutput)os);
/*     */         return;
/*     */       case Null:
/* 311 */         if (!withType) throw new IllegalArgumentException("o has to be non-null without types");
/*     */         
/*     */         return;
/*     */       case Object:
/* 315 */         throw new IllegalArgumentException("unhandled class: " + o.getClass());
/*     */       case Potion:
/* 317 */         encode(os, Potion.field_188414_b.func_177774_c(o), false);
/*     */         return;
/*     */       case ResourceLocation:
/* 320 */         loc = (ResourceLocation)o;
/* 321 */         os.writeString(loc.func_110624_b());
/* 322 */         os.writeString(loc.func_110623_a());
/*     */         return;
/*     */       
/*     */       case Short:
/* 326 */         os.writeShort(((Short)o).shortValue());
/*     */         return;
/*     */       case String:
/* 329 */         os.writeString((String)o);
/*     */         return;
/*     */       case TileEntity:
/* 332 */         te = (TileEntity)o;
/* 333 */         encode(os, te.func_145831_w(), false);
/* 334 */         encode(os, te.func_174877_v(), false);
/*     */         return;
/*     */       
/*     */       case TupleT2:
/* 338 */         t2 = (Tuple.T2<?, ?>)o;
/* 339 */         encode(os, t2.a, true);
/* 340 */         encode(os, t2.b, true);
/*     */         return;
/*     */       
/*     */       case TupleT3:
/* 344 */         t = (Tuple.T3<?, ?, ?>)o;
/* 345 */         encode(os, t.a, true);
/* 346 */         encode(os, t.b, true);
/* 347 */         encode(os, t.c, true);
/*     */         return;
/*     */       
/*     */       case UUID:
/* 351 */         uuid = (UUID)o;
/* 352 */         os.writeLong(uuid.getMostSignificantBits());
/* 353 */         os.writeLong(uuid.getLeastSignificantBits());
/*     */         return;
/*     */       
/*     */       case Vec3:
/* 357 */         v = (Vec3d)o;
/* 358 */         os.writeDouble(v.field_72450_a);
/* 359 */         os.writeDouble(v.field_72448_b);
/* 360 */         os.writeDouble(v.field_72449_c);
/*     */         return;
/*     */       
/*     */       case World:
/* 364 */         os.writeInt(((World)o).field_73011_w.getDimension());
/*     */         return;
/*     */     } 
/* 367 */     throw new IllegalArgumentException("unhandled type: " + type); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object decode(IGrowingBuffer is) throws IOException {
/*     */     try {
/* 373 */       return decode(is, typeFromId(is.readUnsignedByte()));
/* 374 */     } catch (IllegalArgumentException e) {
/* 375 */       String msg = "An unknown data type was received over multiplayer to be decoded.\nThis could happen due to corrupted data or a bug.";
/*     */ 
/*     */       
/* 378 */       IC2.platform.displayError(e, msg, new Object[0]);
/* 379 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T decode(IGrowingBuffer is, Class<T> clazz) throws IOException {
/* 385 */     EncodedType type = typeFromClass(clazz);
/*     */     
/* 387 */     if (type.threadSafe) {
/* 388 */       return (T)decode(is, type);
/*     */     }
/* 390 */     throw new IllegalArgumentException("requesting decode for non thread safe type");
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends Enum<T>> T decodeEnum(IGrowingBuffer is, Class<T> clazz) throws IOException {
/* 395 */     int ordinal = ((Integer)decode(is, EncodedType.Enum)).intValue();
/*     */     
/* 397 */     Enum[] arrayOfEnum = (Enum[])clazz.getEnumConstants();
/* 398 */     return (ordinal >= 0 && ordinal < arrayOfEnum.length) ? (T)arrayOfEnum[ordinal] : null;
/*     */   }
/*     */   
/*     */   public static Object decodeDeferred(GrowingBuffer is, Class<?> clazz) throws IOException {
/* 402 */     EncodedType type = typeFromClass(clazz);
/*     */     
/* 404 */     return decode(is, type); } public static Object decode(final IGrowingBuffer is, EncodedType type) throws IOException { String aimTypeName; EncodedType componentType; final Object ret; int inputAmount; FluidStack ret; ItemStack[] contents; int size; final IResolvableValue<World> deferredWorld; final int dimensionId; final INetworkCustomEncoder ince; boolean primitive; int EUaTick; InvSlot invSlot; Item item; final BlockPos pos; boolean isEnum; int ticksNeeded; int i; int meta; final Class<?> componentClass; byte max; NBTTagCompound nbt; Class<?> component; final int len; IElectrolyzerRecipeManager.ElectrolyzerOutput[] outputs; ItemStack itemStack; boolean anyTypeMismatch; byte b1;
/*     */     boolean needsResolving;
/*     */     Object array;
/*     */     final Object tmpArray;
/* 408 */     switch (type) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case Addon:
/*     */       case UnSafeAddon:
/* 420 */         aimTypeName = is.readString();
/* 421 */         ince = classToAddonType.get(getClass(aimTypeName));
/*     */         
/* 423 */         if (ince == null) {
/* 424 */           throw new IllegalStateException("Cannot decode an object without a decoder! Type was " + aimTypeName);
/*     */         }
/*     */         
/* 427 */         if (ince.isThreadSafe()) {
/* 428 */           return ince.decode(is);
/*     */         }
/* 430 */         return new IResolvableValue()
/*     */           {
/*     */             public Object get() {
/*     */               try {
/* 434 */                 return ince.decode(is);
/* 435 */               } catch (IOException e) {
/* 436 */                 throw new RuntimeException("Unexpected error", e);
/*     */               } 
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case Array:
/* 443 */         componentType = typeFromId(is.readUnsignedByte());
/* 444 */         primitive = is.readBoolean();
/* 445 */         isEnum = (componentType == EncodedType.Enum);
/*     */         
/* 447 */         component = primitive ? unbox(componentType.cls) : componentType.cls;
/*     */         
/* 449 */         if (component == null || isEnum) {
/* 450 */           assert componentType == EncodedType.Addon || componentType == EncodedType.UnSafeAddon || isEnum;
/*     */           
/* 452 */           component = getClass(is.readString());
/*     */         } 
/*     */         
/* 455 */         componentClass = component;
/*     */         
/* 457 */         len = is.readVarInt();
/* 458 */         anyTypeMismatch = is.readBoolean();
/* 459 */         needsResolving = !componentType.threadSafe;
/*     */ 
/*     */         
/* 462 */         if (!needsResolving) {
/* 463 */           array = Array.newInstance(componentClass, len);
/*     */         } else {
/* 465 */           array = new Object[len];
/*     */         } 
/*     */         
/* 468 */         if (!anyTypeMismatch) {
/* 469 */           if (isEnum) {
/*     */             
/* 471 */             Object[] constants = componentClass.getEnumConstants();
/* 472 */             assert constants != null;
/*     */             
/* 474 */             for (int j = 0; j < len; j++) {
/* 475 */               Array.set(array, j, constants[((Integer)decode(is, componentType)).intValue()]);
/*     */             }
/*     */           } else {
/* 478 */             for (int j = 0; j < len; j++) {
/* 479 */               Array.set(array, j, decode(is, componentType));
/*     */             }
/*     */           } 
/*     */         } else {
/* 483 */           for (int j = 0; j < len; j++) {
/* 484 */             EncodedType cType = typeFromId(is.readUnsignedByte());
/*     */             
/* 486 */             if (!cType.threadSafe && !needsResolving) {
/* 487 */               needsResolving = true;
/*     */               
/* 489 */               if (componentClass != Object.class) {
/* 490 */                 Object newArray = new Object[len];
/* 491 */                 System.arraycopy(array, 0, newArray, 0, j);
/* 492 */                 array = newArray;
/*     */               } 
/*     */             } 
/*     */             
/* 496 */             Array.set(array, j, decode(is, cType));
/*     */           } 
/*     */         } 
/*     */         
/* 500 */         if (!needsResolving) {
/* 501 */           return array;
/*     */         }
/* 503 */         tmpArray = array;
/*     */         
/* 505 */         return new IResolvableValue()
/*     */           {
/*     */             public Object get() {
/* 508 */               Object ret = Array.newInstance(componentClass, len);
/*     */               
/* 510 */               for (int i = 0; i < len; i++) {
/* 511 */                 Array.set(ret, i, DataEncoder.getValue(Array.get(tmpArray, i)));
/*     */               }
/*     */               
/* 514 */               return ret;
/*     */             }
/*     */           };
/*     */ 
/*     */       
/*     */       case Block:
/* 520 */         return Util.getBlock((ResourceLocation)decode(is, EncodedType.ResourceLocation));
/*     */       case BlockPos:
/* 522 */         return new BlockPos(is.readInt(), is.readInt(), is.readInt());
/*     */       case Boolean:
/* 524 */         return Boolean.valueOf(is.readBoolean());
/*     */       case Byte:
/* 526 */         return Byte.valueOf(is.readByte());
/*     */       case Character:
/* 528 */         return Character.valueOf(is.readChar());
/*     */       case ChunkPos:
/* 530 */         return new ChunkPos(is.readInt(), is.readInt());
/*     */       case Collection:
/* 532 */         object1 = decode(is, EncodedType.Array);
/*     */         
/* 534 */         if (object1 instanceof IResolvableValue) {
/* 535 */           return new IResolvableValue<List<Object>>()
/*     */             {
/*     */               public List<Object> get()
/*     */               {
/* 539 */                 return Arrays.asList(((DataEncoder.IResolvableValue<Object[]>)ret).get());
/*     */               }
/*     */             };
/*     */         }
/* 543 */         return Arrays.asList((Object[])object1);
/*     */ 
/*     */       
/*     */       case Component:
/* 547 */         return decode(is, EncodedType.NBTTagCompound);
/*     */       case CropCard:
/* 549 */         return Crops.instance.getCropCard(is.readString(), is.readString());
/*     */       case Double:
/* 551 */         return Double.valueOf(is.readDouble());
/*     */       case ElectrolyzerRecipe:
/* 553 */         inputAmount = is.readInt();
/* 554 */         EUaTick = is.readInt();
/* 555 */         ticksNeeded = is.readInt();
/* 556 */         max = is.readByte();
/* 557 */         outputs = new IElectrolyzerRecipeManager.ElectrolyzerOutput[max];
/* 558 */         for (b1 = 0; b1 < max; b1 = (byte)(b1 + 1)) {
/* 559 */           outputs[b1] = new IElectrolyzerRecipeManager.ElectrolyzerOutput(is.readString(), is.readInt(), EnumFacing.func_82600_a(is.readByte()));
/*     */         }
/* 561 */         return new IElectrolyzerRecipeManager.ElectrolyzerRecipe(inputAmount, EUaTick, ticksNeeded, outputs);
/*     */       
/*     */       case Enchantment:
/* 564 */         return Enchantment.field_185264_b.func_82594_a(decode(is, EncodedType.ResourceLocation));
/*     */       
/*     */       case Enum:
/* 567 */         return Integer.valueOf(is.readVarInt());
/*     */       case Float:
/* 569 */         return Float.valueOf(is.readFloat());
/*     */       case Fluid:
/* 571 */         return FluidRegistry.getFluid(is.readString());
/*     */       case FluidStack:
/* 573 */         ret = new FluidStack((Fluid)decode(is, EncodedType.Fluid), is.readInt());
/* 574 */         ret.tag = (NBTTagCompound)decode(is);
/*     */         
/* 576 */         return ret;
/*     */       
/*     */       case FluidTank:
/* 579 */         return new FluidTank((FluidStack)decode(is), is.readInt());
/*     */       case GameProfile:
/* 581 */         return new GameProfile((UUID)decode(is), is.readString());
/*     */       case Integer:
/* 583 */         return Integer.valueOf(is.readInt());
/*     */       case InvSlot:
/* 585 */         contents = (ItemStack[])decode(is, EncodedType.Array);
/* 586 */         invSlot = new InvSlot(contents.length);
/*     */         
/* 588 */         for (i = 0; i < contents.length; i++) {
/* 589 */           invSlot.put(i, contents[i]);
/*     */         }
/*     */         
/* 592 */         return invSlot;
/*     */       
/*     */       case Item:
/* 595 */         return Util.getItem((ResourceLocation)decode(is, EncodedType.ResourceLocation));
/*     */       case ItemStack:
/* 597 */         size = is.readByte();
/*     */         
/* 599 */         if (size == 0) {
/* 600 */           return StackUtil.emptyStack;
/*     */         }
/* 602 */         item = decode(is, Item.class);
/* 603 */         meta = is.readShort();
/* 604 */         nbt = (NBTTagCompound)decode(is);
/*     */         
/* 606 */         itemStack = new ItemStack(item, size, meta);
/* 607 */         itemStack.func_77982_d(nbt);
/*     */         
/* 609 */         return itemStack;
/*     */ 
/*     */       
/*     */       case Long:
/* 613 */         return Long.valueOf(is.readLong());
/*     */       case NBTTagCompound:
/* 615 */         return CompressedStreamTools.func_152456_a((DataInput)is, NBTSizeTracker.field_152451_a);
/*     */       case Null:
/* 617 */         return null;
/*     */       case Object:
/* 619 */         return new Object();
/*     */       case Potion:
/* 621 */         return Potion.field_188414_b.func_82594_a(decode(is, EncodedType.ResourceLocation));
/*     */       case ResourceLocation:
/* 623 */         return new ResourceLocation(is.readString(), is.readString());
/*     */       case Short:
/* 625 */         return Short.valueOf(is.readShort());
/*     */       case String:
/* 627 */         return is.readString();
/*     */       
/*     */       case TileEntity:
/* 630 */         deferredWorld = (IResolvableValue<World>)decode(is, EncodedType.World);
/* 631 */         pos = (BlockPos)decode(is, EncodedType.BlockPos);
/*     */         
/* 633 */         return new IResolvableValue<TileEntity>()
/*     */           {
/*     */             public TileEntity get() {
/* 636 */               World world = deferredWorld.get();
/* 637 */               if (world == null) return null;
/*     */               
/* 639 */               return world.func_175625_s(pos);
/*     */             }
/*     */           };
/*     */       
/*     */       case TupleT2:
/* 644 */         return new Tuple.T2(decode(is), decode(is));
/*     */       case TupleT3:
/* 646 */         return new Tuple.T3(decode(is), decode(is), decode(is));
/*     */       case UUID:
/* 648 */         return new UUID(is.readLong(), is.readLong());
/*     */       case Vec3:
/* 650 */         return new Vec3d(is.readDouble(), is.readDouble(), is.readDouble());
/*     */       case World:
/* 652 */         dimensionId = is.readInt();
/*     */         
/* 654 */         return new IResolvableValue<World>()
/*     */           {
/*     */             public World get() {
/* 657 */               return IC2.platform.getWorld(dimensionId);
/*     */             }
/*     */           };
/*     */     } 
/*     */     
/* 662 */     throw new IllegalArgumentException("unhandled type: " + type); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T getValue(Object decoded) {
/* 668 */     if (decoded instanceof IResolvableValue) {
/* 669 */       return ((IResolvableValue<T>)decoded).get();
/*     */     }
/* 671 */     return (T)decoded;
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
/*     */   public static <T> boolean copyValue(T src, T dst) {
/* 688 */     if (src == null || dst == null) return false;
/*     */     
/* 690 */     if (dst instanceof ItemStack) {
/* 691 */       ItemStack srcT = (ItemStack)src;
/* 692 */       ItemStack dstT = (ItemStack)dst;
/*     */       
/* 694 */       if (srcT.func_77973_b() == dstT.func_77973_b()) {
/* 695 */         dstT.func_190920_e(srcT.func_190916_E());
/* 696 */         StackUtil.setRawMeta(dstT, StackUtil.getRawMeta(srcT));
/* 697 */         dstT.func_77982_d(srcT.func_77978_p());
/*     */         
/* 699 */         return true;
/*     */       } 
/* 701 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 710 */     if (dst instanceof FluidTank) {
/* 711 */       FluidTank srcT = (FluidTank)src;
/* 712 */       FluidTank dstT = (FluidTank)dst;
/*     */       
/* 714 */       dstT.setFluid(srcT.getFluid());
/* 715 */       dstT.setCapacity(srcT.getCapacity());
/* 716 */     } else if (dst instanceof InvSlot) {
/* 717 */       InvSlot srcT = (InvSlot)src;
/* 718 */       InvSlot dstT = (InvSlot)dst;
/*     */       
/* 720 */       if (srcT.size() != dstT.size()) throw new RuntimeException("Can't sync InvSlots with mismatched sizes.");
/*     */       
/* 722 */       for (int i = 0; i < srcT.size(); i++) {
/* 723 */         if (!copyValue(srcT.get(i), dstT.get(i))) {
/* 724 */           dstT.put(i, srcT.get(i));
/*     */         }
/*     */       } 
/* 727 */     } else if (dst instanceof TileEntityComponent) {
/* 728 */       NBTTagCompound nbt = (NBTTagCompound)src;
/*     */       
/* 730 */       ((TileEntityComponent)dst).readFromNbt(nbt);
/* 731 */     } else if (dst instanceof Collection) {
/* 732 */       Collection<Object> srcT = (Collection<Object>)src;
/* 733 */       Collection<Object> dstT = (Collection<Object>)dst;
/*     */       
/* 735 */       dstT.clear();
/* 736 */       dstT.addAll(srcT);
/*     */     } else {
/* 738 */       return false;
/*     */     } 
/*     */     
/* 741 */     return true;
/*     */   }
/*     */   
/*     */   private static Class<?> box(Class<?> clazz) {
/* 745 */     if (clazz == byte.class) return Byte.class; 
/* 746 */     if (clazz == short.class) return Short.class; 
/* 747 */     if (clazz == int.class) return Integer.class; 
/* 748 */     if (clazz == long.class) return Long.class; 
/* 749 */     if (clazz == float.class) return Float.class; 
/* 750 */     if (clazz == double.class) return Double.class; 
/* 751 */     if (clazz == boolean.class) return Boolean.class; 
/* 752 */     if (clazz == char.class) return Character.class;
/*     */     
/* 754 */     return clazz;
/*     */   }
/*     */   
/*     */   private static Class<?> unbox(Class<?> clazz) {
/* 758 */     if (clazz == Byte.class) return byte.class; 
/* 759 */     if (clazz == Short.class) return short.class; 
/* 760 */     if (clazz == Integer.class) return int.class; 
/* 761 */     if (clazz == Long.class) return long.class; 
/* 762 */     if (clazz == Float.class) return float.class; 
/* 763 */     if (clazz == Double.class) return double.class; 
/* 764 */     if (clazz == Boolean.class) return boolean.class; 
/* 765 */     if (clazz == Character.class) return char.class;
/*     */     
/* 767 */     return clazz;
/*     */   }
/*     */   
/*     */   private static Class<?> getClass(String type) {
/*     */     try {
/* 772 */       return Class.forName(type);
/* 773 */     } catch (ClassNotFoundException e) {
/* 774 */       throw new RuntimeException("Missing type from the class path expected by network: " + type, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int idFromType(EncodedType type) {
/* 779 */     return type.ordinal();
/*     */   }
/*     */   
/*     */   private static EncodedType typeFromId(int id) {
/* 783 */     if (id < 0 || id >= EncodedType.types.length) throw new IllegalArgumentException("invalid type id: " + id);
/*     */     
/* 785 */     return EncodedType.types[id];
/*     */   }
/*     */   
/*     */   private static EncodedType typeFromObject(Object o) {
/* 789 */     if (o == null) return EncodedType.Null;
/*     */     
/* 791 */     return typeFromClass(o.getClass());
/*     */   }
/*     */   
/*     */   private static EncodedType typeFromClass(Class<?> cls) {
/* 795 */     if (cls == null) return EncodedType.Null; 
/* 796 */     if (cls.isArray()) return EncodedType.Array;
/*     */     
/* 798 */     if (cls.isPrimitive()) cls = box(cls);
/*     */     
/* 800 */     EncodedType ret = EncodedType.classToTypeMap.get(cls);
/* 801 */     if (ret != null) return ret;
/*     */     
/* 803 */     ret = classToTypeCache.get(cls);
/* 804 */     if (ret != null) return ret;
/*     */     
/* 806 */     INetworkCustomEncoder ince = classToAddonType.get(cls);
/* 807 */     if (ince != null) {
/* 808 */       ret = ince.isThreadSafe() ? EncodedType.Addon : EncodedType.UnSafeAddon;
/* 809 */       classToTypeCache.put(cls, ret);
/* 810 */       return ret;
/*     */     } 
/*     */     
/* 813 */     for (EncodedType type : EncodedType.types) {
/* 814 */       if (type.cls != null && type.cls.isAssignableFrom(cls)) {
/* 815 */         classToTypeCache.put(cls, type);
/* 816 */         return type;
/*     */       } 
/*     */     } 
/*     */     
/* 820 */     throw new IllegalStateException("unmatched " + cls);
/*     */   }
/*     */   
/*     */   public enum EncodedType {
/* 824 */     Null(null), Array(null),
/*     */     
/* 826 */     Byte((String)Byte.class), Short((String)Short.class), Integer((String)Integer.class), Long((String)Long.class), Float((String)Float.class), Double((String)Double.class),
/* 827 */     Boolean((String)Boolean.class), Character((String)Character.class),
/* 828 */     String((String)String.class), Enum((String)Enum.class), UUID((String)UUID.class),
/*     */ 
/*     */     
/* 831 */     Block((String)Block.class), Item((String)Item.class), TileEntity((String)TileEntity.class, false), ItemStack((String)ItemStack.class),
/* 832 */     World((String)World.class, false), NBTTagCompound((String)NBTTagCompound.class), ResourceLocation((String)ResourceLocation.class), GameProfile((String)GameProfile.class),
/* 833 */     Potion((String)Potion.class), Enchantment((String)Enchantment.class),
/* 834 */     BlockPos((String)BlockPos.class), ChunkPos((String)ChunkPos.class), Vec3((String)Vec3d.class),
/*     */ 
/*     */     
/* 837 */     Fluid((String)Fluid.class), FluidStack((String)FluidStack.class), FluidTank((String)FluidTank.class),
/*     */ 
/*     */     
/* 840 */     InvSlot((String)InvSlot.class), Component((String)TileEntityComponent.class, false), CropCard((String)CropCard.class),
/* 841 */     ElectrolyzerRecipe((String)IElectrolyzerRecipeManager.ElectrolyzerRecipe.class), TupleT2((String)Tuple.T2.class), TupleT3((String)Tuple.T3.class),
/*     */ 
/*     */     
/* 844 */     Addon(null), UnSafeAddon(null, false),
/*     */ 
/*     */     
/* 847 */     Collection((String)Collection.class), Object((String)Object.class);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final Class<?> cls;
/*     */ 
/*     */ 
/*     */     
/*     */     final boolean threadSafe;
/*     */ 
/*     */ 
/*     */     
/* 860 */     static final EncodedType[] types = values();
/* 861 */     static final Map<Class<?>, EncodedType> classToTypeMap = new IdentityHashMap<>(types.length - 2);
/*     */     EncodedType(Class<?> cls, boolean threadSafe) { this.cls = cls;
/*     */       this.threadSafe = threadSafe; } static {
/* 864 */       for (EncodedType type : types) {
/* 865 */         if (type.cls != null) classToTypeMap.put(type.cls, type);
/*     */       
/*     */       } 
/* 868 */       if (types.length > 255) throw new RuntimeException("too many types"); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void addNetworkEncoder(Class<?> typeBeingEncoded, INetworkCustomEncoder customEncoder) {
/* 873 */     assert typeBeingEncoded != null && customEncoder != null;
/*     */     
/* 875 */     INetworkCustomEncoder previous = classToAddonType.put(typeBeingEncoded, customEncoder);
/*     */     
/* 877 */     if (previous != null) {
/* 878 */       throw new IllegalStateException("Duplicate mapping for class! " + previous.getClass().getName() + " and " + customEncoder.getClass().getName() + " both map for " + typeBeingEncoded.getName() + '.');
/*     */     }
/*     */   }
/*     */   
/* 882 */   private static final Map<Class<?>, EncodedType> classToTypeCache = Collections.synchronizedMap(new IdentityHashMap<>());
/* 883 */   private static final Map<Class<?>, INetworkCustomEncoder> classToAddonType = Collections.synchronizedMap(new IdentityHashMap<>());
/*     */   
/*     */   private static interface IResolvableValue<T> {
/*     */     T get();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\DataEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */