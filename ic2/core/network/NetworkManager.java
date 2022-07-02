/*     */ package ic2.core.network;
/*     */ 
/*     */ import ic2.api.network.ClientModifiable;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.network.INetworkDataProvider;
/*     */ import ic2.api.network.INetworkItemEventListener;
/*     */ import ic2.api.network.INetworkManager;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.WorldData;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.item.IHandHeldInventory;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IContainerListener;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.NetHandlerPlayServer;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerChunkMap;
/*     */ import net.minecraft.server.management.PlayerChunkMapEntry;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLEventChannel;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.minecraftforge.fml.common.network.NetworkRegistry;
/*     */ import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkManager
/*     */   implements INetworkManager
/*     */ {
/*     */   public NetworkManager() {
/*  57 */     if (channel == null) {
/*  58 */       channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("ic2");
/*     */     }
/*     */     
/*  61 */     channel.register(this);
/*     */   }
/*     */   
/*     */   protected boolean isClient() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTickEnd(WorldData worldData) {
/*     */     try {
/*  72 */       TeUpdate.send(worldData, this);
/*  73 */     } catch (IOException e) {
/*  74 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void sendPlayerItemData(EntityPlayer player, int slot, Object... data) {
/*  79 */     GrowingBuffer buffer = new GrowingBuffer(256);
/*     */     
/*     */     try {
/*  82 */       SubPacketType.PlayerItemData.writeTo(buffer);
/*  83 */       buffer.writeByte(slot);
/*  84 */       DataEncoder.encode(buffer, ((ItemStack)player.field_71071_by.field_70462_a.get(slot)).func_77973_b(), false);
/*  85 */       buffer.writeVarInt(data.length);
/*     */       
/*  87 */       for (Object o : data) {
/*  88 */         DataEncoder.encode(buffer, o);
/*     */       }
/*  90 */     } catch (IOException e) {
/*  91 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  94 */     buffer.flip();
/*     */     
/*  96 */     if (!isClient()) {
/*  97 */       sendPacket(buffer, true, (EntityPlayerMP)player);
/*     */     } else {
/*  99 */       sendPacket(buffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void updateTileEntityField(TileEntity te, String field) {
/* 105 */     if (!isClient()) {
/* 106 */       getTeUpdateData(te).addGlobalField(field);
/* 107 */     } else if (getClientModifiableField(te.getClass(), field) == null) {
/* 108 */       IC2.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[] { te });
/*     */     } else {
/* 110 */       GrowingBuffer buffer = new GrowingBuffer(64);
/*     */       
/*     */       try {
/* 113 */         SubPacketType.TileEntityData.writeTo(buffer);
/* 114 */         DataEncoder.encode(buffer, te, false);
/* 115 */         writeFieldData(te, field, buffer);
/* 116 */       } catch (IOException e) {
/* 117 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 120 */       buffer.flip();
/* 121 */       sendPacket(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Field getClientModifiableField(Class<?> cls, String fieldName) {
/* 126 */     Field field = ReflectionUtil.getFieldRecursive(cls, fieldName);
/*     */     
/* 128 */     if (field == null) {
/* 129 */       IC2.log.warn(LogCategory.Network, "Can't find field %s in %s.", new Object[] { fieldName, cls.getName() });
/* 130 */       return null;
/*     */     } 
/*     */     
/* 133 */     if (field.getAnnotation(ClientModifiable.class) == null) {
/* 134 */       IC2.log.warn(LogCategory.Network, "The field %s in %s is not modifiable.", new Object[] { fieldName, cls.getName() });
/* 135 */       return null;
/*     */     } 
/*     */     
/* 138 */     return field;
/*     */   }
/*     */   
/*     */   private static TeUpdateDataServer getTeUpdateData(TileEntity te) {
/* 142 */     assert IC2.platform.isSimulating();
/* 143 */     if (te == null) throw new NullPointerException();
/*     */     
/* 145 */     WorldData worldData = WorldData.get(te.func_145831_w());
/*     */     
/* 147 */     TeUpdateDataServer ret = (TeUpdateDataServer)worldData.tesToUpdate.get(te);
/*     */     
/* 149 */     if (ret == null) {
/* 150 */       ret = new TeUpdateDataServer();
/* 151 */       worldData.tesToUpdate.put(te, ret);
/*     */     } 
/*     */     
/* 154 */     return ret;
/*     */   }
/*     */   
/*     */   public final void updateTileEntityFieldTo(TileEntity te, String field, EntityPlayerMP player) {
/* 158 */     assert !isClient();
/*     */     
/* 160 */     getTeUpdateData(te).addPlayerField(field, player);
/*     */   }
/*     */   
/*     */   public final void sendComponentUpdate(TileEntityBlock te, String componentName, EntityPlayerMP player, GrowingBuffer data) {
/* 164 */     assert !isClient();
/*     */     
/* 166 */     if (player.func_130014_f_() != te.func_145831_w()) throw new IllegalArgumentException("mismatched world (te " + te.func_145831_w() + ", player " + player.func_130014_f_() + ")");
/*     */     
/* 168 */     GrowingBuffer buffer = new GrowingBuffer(64);
/*     */     
/*     */     try {
/* 171 */       SubPacketType.TileEntityBlockComponent.writeTo(buffer);
/* 172 */       DataEncoder.encode(buffer, te, false);
/* 173 */       buffer.writeString(componentName);
/* 174 */       buffer.writeVarInt(data.available());
/* 175 */       data.writeTo(buffer);
/* 176 */     } catch (IOException e) {
/* 177 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 180 */     buffer.flip();
/* 181 */     sendPacket(buffer, true, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void initiateTileEntityEvent(TileEntity te, int event, boolean limitRange) {
/* 186 */     assert !isClient();
/*     */     
/* 188 */     if ((te.func_145831_w()).field_73010_i.isEmpty())
/*     */       return; 
/* 190 */     GrowingBuffer buffer = new GrowingBuffer(32);
/*     */     
/*     */     try {
/* 193 */       SubPacketType.TileEntityEvent.writeTo(buffer);
/* 194 */       DataEncoder.encode(buffer, te, false);
/* 195 */       buffer.writeInt(event);
/* 196 */     } catch (IOException e) {
/* 197 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 200 */     buffer.flip();
/*     */     
/* 202 */     for (EntityPlayerMP target : getPlayersInRange(te.func_145831_w(), te.func_174877_v(), new ArrayList())) {
/* 203 */       if (limitRange) {
/* 204 */         int dX = (int)(te.func_174877_v().func_177958_n() + 0.5D - target.field_70165_t);
/* 205 */         int dZ = (int)(te.func_174877_v().func_177952_p() + 0.5D - target.field_70161_v);
/*     */         
/* 207 */         if (dX * dX + dZ * dZ > 400)
/*     */           continue; 
/*     */       } 
/* 210 */       sendPacket(buffer, false, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void initiateItemEvent(EntityPlayer player, ItemStack stack, int event, boolean limitRange) {
/* 216 */     if (StackUtil.isEmpty(stack)) throw new NullPointerException("invalid stack: " + StackUtil.toStringSafe(stack));
/*     */     
/* 218 */     assert !isClient();
/*     */     
/* 220 */     GrowingBuffer buffer = new GrowingBuffer(256);
/*     */     
/*     */     try {
/* 223 */       SubPacketType.ItemEvent.writeTo(buffer);
/* 224 */       DataEncoder.encode(buffer, player.func_146103_bH(), false);
/* 225 */       DataEncoder.encode(buffer, stack, false);
/* 226 */       buffer.writeInt(event);
/* 227 */     } catch (Exception e) {
/* 228 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 231 */     buffer.flip();
/*     */     
/* 233 */     for (EntityPlayerMP target : getPlayersInRange(player.func_130014_f_(), player.func_180425_c(), new ArrayList())) {
/* 234 */       if (limitRange) {
/* 235 */         int dX = (int)(player.field_70165_t - target.field_70165_t);
/* 236 */         int dZ = (int)(player.field_70161_v - target.field_70161_v);
/*     */         
/* 238 */         if (dX * dX + dZ * dZ > 400)
/*     */           continue; 
/*     */       } 
/* 241 */       sendPacket(buffer, false, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateClientItemEvent(ItemStack stack, int event) {
/*     */     assert false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateClientTileEntityEvent(TileEntity te, int event) {
/*     */     assert false;
/*     */   }
/*     */   
/*     */   public void initiateRpc(int id, Class<? extends IRpcProvider<?>> provider, Object[] args) {
/*     */     assert false;
/*     */   }
/*     */   
/*     */   public void requestGUI(IHasGui inventory) {
/*     */     assert false;
/*     */   }
/*     */   
/*     */   public final void initiateGuiDisplay(EntityPlayerMP player, IHasGui inventory, int windowId) {
/* 264 */     initiateGuiDisplay(player, inventory, windowId, null);
/*     */   }
/*     */   
/*     */   public final void initiateGuiDisplay(EntityPlayerMP player, IHasGui inventory, int windowId, Integer ID) {
/* 268 */     assert !isClient();
/*     */     
/*     */     try {
/* 271 */       GrowingBuffer buffer = new GrowingBuffer(32);
/*     */       
/* 273 */       SubPacketType.GuiDisplay.writeTo(buffer);
/*     */       
/* 275 */       MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
/* 276 */       boolean isAdmin = server.func_184103_al().func_152596_g(player.func_146103_bH());
/*     */       
/* 278 */       buffer.writeBoolean(isAdmin);
/*     */       
/* 280 */       if (inventory instanceof TileEntity) {
/* 281 */         TileEntity te = (TileEntity)inventory;
/*     */         
/* 283 */         buffer.writeByte(0);
/* 284 */         DataEncoder.encode(buffer, te, false);
/* 285 */       } else if (player.field_71071_by.func_70448_g() != null && player.field_71071_by.func_70448_g().func_77973_b() instanceof IHandHeldInventory) {
/* 286 */         buffer.writeByte(1);
/* 287 */         buffer.writeInt(player.field_71071_by.field_70461_c);
/* 288 */         handleSubData(buffer, player.field_71071_by.func_70448_g(), ID);
/* 289 */       } else if (player.func_184592_cb() != null && player.func_184592_cb().func_77973_b() instanceof IHandHeldInventory) {
/* 290 */         buffer.writeByte(1);
/* 291 */         buffer.writeInt(-1);
/* 292 */         handleSubData(buffer, player.func_184592_cb(), ID);
/*     */       } else {
/* 294 */         IC2.platform.displayError("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: " + inventory + ")", new Object[0]);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 302 */       buffer.writeInt(windowId);
/*     */       
/* 304 */       buffer.flip();
/* 305 */       sendPacket(buffer, true, player);
/* 306 */     } catch (IOException e) {
/* 307 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void handleSubData(GrowingBuffer buffer, ItemStack stack, Integer ID) {
/* 312 */     boolean subInv = (ID != null && stack.func_77973_b() instanceof ic2.core.item.IHandHeldSubInventory);
/*     */     
/* 314 */     buffer.writeBoolean(subInv);
/* 315 */     if (subInv) {
/* 316 */       buffer.writeShort(ID.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public final void sendInitialData(TileEntity te, EntityPlayerMP player) {
/* 321 */     assert !isClient();
/*     */     
/* 323 */     if (te instanceof INetworkDataProvider) {
/* 324 */       TeUpdateDataServer updateData = getTeUpdateData(te);
/*     */       
/* 326 */       for (String field : ((INetworkDataProvider)te).getNetworkedFields()) {
/* 327 */         updateData.addPlayerField(field, player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void sendInitialData(TileEntity te) {
/* 334 */     assert !isClient();
/*     */     
/* 336 */     if (te instanceof INetworkDataProvider) {
/* 337 */       TeUpdateDataServer updateData = getTeUpdateData(te);
/* 338 */       List<String> fields = ((INetworkDataProvider)te).getNetworkedFields();
/*     */       
/* 340 */       for (String field : fields) {
/* 341 */         updateData.addGlobalField(field);
/*     */       }
/*     */       
/* 344 */       if (TeUpdate.debug) {
/* 345 */         IC2.log.info(LogCategory.Network, "Sending initial TE data for %s (%s).", new Object[] { Util.formatPosition(te), fields });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void sendChat(EntityPlayerMP player, String message) {
/* 351 */     assert !isClient();
/*     */     
/* 353 */     GrowingBuffer buffer = new GrowingBuffer(message.length() * 2);
/* 354 */     buffer.writeString(message);
/*     */     
/* 356 */     buffer.flip();
/* 357 */     sendLargePacket(player, 1, buffer);
/*     */   }
/*     */   
/*     */   public final void sendConsole(EntityPlayerMP player, String message) {
/* 361 */     assert !isClient();
/*     */     
/* 363 */     GrowingBuffer buffer = new GrowingBuffer(message.length() * 2);
/* 364 */     buffer.writeString(message);
/*     */     
/* 366 */     buffer.flip();
/* 367 */     sendLargePacket(player, 2, buffer);
/*     */   }
/*     */   
/*     */   public final void sendContainerFields(ContainerBase<?> container, String... fieldNames) {
/* 371 */     for (String fieldName : fieldNames) {
/* 372 */       sendContainerField(container, fieldName);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void sendContainerField(ContainerBase<?> container, String fieldName) {
/* 377 */     if (isClient() && 
/* 378 */       getClientModifiableField(container.getClass(), fieldName) == null) {
/* 379 */       IC2.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[] { container });
/*     */       
/*     */       return;
/*     */     } 
/* 383 */     GrowingBuffer buffer = new GrowingBuffer(256);
/*     */     
/*     */     try {
/* 386 */       SubPacketType.ContainerData.writeTo(buffer);
/* 387 */       buffer.writeInt(container.field_75152_c);
/* 388 */       writeFieldData(container, fieldName, buffer);
/* 389 */     } catch (IOException e) {
/* 390 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 393 */     buffer.flip();
/*     */     
/* 395 */     if (!isClient()) {
/* 396 */       for (IContainerListener listener : container.getListeners()) {
/* 397 */         if (listener instanceof EntityPlayerMP) sendPacket(buffer, false, (EntityPlayerMP)listener); 
/*     */       } 
/*     */     } else {
/* 400 */       sendPacket(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void sendContainerEvent(ContainerBase<?> container, String event) {
/* 405 */     GrowingBuffer buffer = new GrowingBuffer(64);
/*     */     
/* 407 */     SubPacketType.ContainerEvent.writeTo(buffer);
/* 408 */     buffer.writeInt(container.field_75152_c);
/* 409 */     buffer.writeString(event);
/*     */     
/* 411 */     buffer.flip();
/*     */     
/* 413 */     if (!isClient()) {
/* 414 */       for (IContainerListener listener : container.getListeners()) {
/* 415 */         if (listener instanceof EntityPlayerMP) sendPacket(buffer, false, (EntityPlayerMP)listener); 
/*     */       } 
/*     */     } else {
/* 418 */       sendPacket(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void sendHandHeldInvField(ContainerBase<?> container, String fieldName) {
/* 423 */     if (!(container.base instanceof ic2.core.item.tool.HandHeldInventory)) {
/* 424 */       IC2.log.warn(LogCategory.Network, "Invalid container (%s) sent for field update.", new Object[] { container });
/*     */       
/*     */       return;
/*     */     } 
/* 428 */     if (isClient() && getClientModifiableField(container.base.getClass(), fieldName) == null) {
/* 429 */       IC2.log.warn(LogCategory.Network, "Field update for %s failed.", new Object[] { container });
/*     */       
/*     */       return;
/*     */     } 
/* 433 */     GrowingBuffer buffer = new GrowingBuffer(256);
/*     */     
/*     */     try {
/* 436 */       SubPacketType.HandHeldInvData.writeTo(buffer);
/* 437 */       buffer.writeInt(container.field_75152_c);
/* 438 */       writeFieldData(container.base, fieldName, buffer);
/* 439 */     } catch (IOException e) {
/* 440 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 443 */     buffer.flip();
/*     */     
/* 445 */     if (!isClient()) {
/* 446 */       for (IContainerListener listener : container.getListeners()) {
/* 447 */         if (listener instanceof EntityPlayerMP) sendPacket(buffer, false, (EntityPlayerMP)listener); 
/*     */       } 
/*     */     } else {
/* 450 */       sendPacket(buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void initiateTeblockLandEffect(World world, double x, double y, double z, int count, ITeBlock teBlock) {
/* 455 */     initiateTeblockLandEffect(world, null, x, y, z, count, teBlock);
/*     */   }
/*     */   
/*     */   public final void initiateTeblockLandEffect(World world, BlockPos pos, double x, double y, double z, int count, ITeBlock teBlock) {
/* 459 */     assert !isClient();
/*     */     
/* 461 */     GrowingBuffer buffer = new GrowingBuffer(64);
/*     */     
/*     */     try {
/* 464 */       SubPacketType.TileEntityBlockLandEffect.writeTo(buffer);
/* 465 */       DataEncoder.encode(buffer, world, false);
/* 466 */       if (pos != null) {
/* 467 */         buffer.writeBoolean(true);
/* 468 */         DataEncoder.encode(buffer, pos, false);
/*     */       } else {
/* 470 */         buffer.writeBoolean(false);
/*     */       } 
/* 472 */       buffer.writeDouble(x);
/* 473 */       buffer.writeDouble(y);
/* 474 */       buffer.writeDouble(z);
/* 475 */       buffer.writeInt(count);
/* 476 */       buffer.writeString(teBlock.getName());
/* 477 */     } catch (IOException e) {
/* 478 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 481 */     buffer.flip();
/*     */     
/* 483 */     for (EntityPlayer player : world.field_73010_i) {
/* 484 */       if (!(player instanceof EntityPlayerMP))
/*     */         continue; 
/* 486 */       double distance = player.func_70092_e(x, y, z);
/*     */       
/* 488 */       if (distance <= 1024.0D) {
/* 489 */         sendPacket(buffer, false, (EntityPlayerMP)player);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void initiateTeblockRunEffect(World world, Entity entity, ITeBlock teBlock) {
/* 495 */     initiateTeblockRunEffect(world, null, entity, teBlock);
/*     */   }
/*     */   
/*     */   public final void initiateTeblockRunEffect(World world, BlockPos pos, Entity entity, ITeBlock teBlock) {
/* 499 */     assert !isClient();
/*     */     
/* 501 */     GrowingBuffer buffer = new GrowingBuffer(64);
/*     */     
/*     */     try {
/* 504 */       SubPacketType.TileEntityBlockRunEffect.writeTo(buffer);
/* 505 */       DataEncoder.encode(buffer, world, false);
/* 506 */       if (pos != null) {
/* 507 */         buffer.writeBoolean(true);
/* 508 */         DataEncoder.encode(buffer, pos, false);
/*     */       } else {
/* 510 */         buffer.writeBoolean(false);
/*     */       } 
/* 512 */       buffer.writeDouble(entity.field_70165_t + (IC2.random.nextFloat() - 0.5D) * entity.field_70130_N);
/* 513 */       buffer.writeDouble((entity.func_174813_aQ()).field_72338_b + 0.1D);
/* 514 */       buffer.writeDouble(entity.field_70161_v + (IC2.random.nextFloat() - 0.5D) * entity.field_70130_N);
/* 515 */       buffer.writeDouble(-entity.field_70159_w * 4.0D);
/*     */       
/* 517 */       buffer.writeDouble(-entity.field_70179_y * 4.0D);
/* 518 */       buffer.writeString(teBlock.getName());
/* 519 */     } catch (IOException e) {
/* 520 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 523 */     buffer.flip();
/*     */     
/* 525 */     for (EntityPlayer player : world.field_73010_i) {
/* 526 */       if (!(player instanceof EntityPlayerMP))
/*     */         continue; 
/* 528 */       double distance = player.func_70092_e(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */       
/* 530 */       if (distance <= 1024.0D)
/* 531 */         sendPacket(buffer, false, (EntityPlayerMP)player); 
/*     */     } 
/*     */   }
/*     */   
/*     */   final void sendLargePacket(EntityPlayerMP player, int id, GrowingBuffer data) {
/*     */     boolean lastPacket;
/* 537 */     GrowingBuffer buffer = new GrowingBuffer(16384);
/* 538 */     buffer.writeShort(0);
/*     */     
/*     */     try {
/* 541 */       DeflaterOutputStream deflate = new DeflaterOutputStream(buffer);
/* 542 */       data.writeTo(deflate);
/* 543 */       deflate.close();
/* 544 */     } catch (IOException e) {
/* 545 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 548 */     buffer.flip();
/*     */     
/* 550 */     boolean firstPacket = true;
/*     */ 
/*     */     
/*     */     do {
/* 554 */       lastPacket = (buffer.available() <= 32766);
/*     */       
/* 556 */       if (!firstPacket) buffer.skipBytes(-2);
/*     */       
/* 558 */       SubPacketType.LargePacket.writeTo(buffer);
/*     */       
/* 560 */       int state = 0;
/*     */       
/* 562 */       if (firstPacket) state |= 0x1; 
/* 563 */       if (lastPacket) state |= 0x2;
/*     */       
/* 565 */       state |= id << 2;
/*     */       
/* 567 */       buffer.write(state);
/* 568 */       buffer.skipBytes(-2);
/*     */       
/* 570 */       if (lastPacket) {
/* 571 */         sendPacket(buffer, true, player);
/* 572 */         assert !buffer.hasAvailable();
/*     */       } else {
/* 574 */         sendPacket(buffer.copy(32766), true, player);
/*     */       } 
/*     */       
/* 577 */       firstPacket = false;
/* 578 */     } while (!lastPacket);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
/* 583 */     if (getClass() == NetworkManager.class) {
/*     */       try {
/* 585 */         onPacketData(GrowingBuffer.wrap(event.getPacket().payload()), 
/* 586 */             (EntityPlayer)((NetHandlerPlayServer)event.getHandler()).field_147369_b);
/* 587 */       } catch (Throwable t) {
/* 588 */         IC2.log.warn(LogCategory.Network, t, "Network read failed");
/* 589 */         throw new RuntimeException(t);
/*     */       } 
/*     */       
/* 592 */       event.getPacket().payload().release();
/*     */     }  } private void onPacketData(GrowingBuffer is, final EntityPlayer player) throws IOException { final ItemStack stack; final int keyState; final Object teDeferred;
/*     */     final boolean hand;
/*     */     final int event;
/*     */     final Object teDeferred;
/* 597 */     if (!is.hasAvailable())
/*     */       return; 
/* 599 */     SubPacketType packetType = SubPacketType.read(is, true);
/* 600 */     if (packetType == null)
/*     */       return; 
/* 602 */     switch (packetType) {
/*     */       case ItemEvent:
/* 604 */         stack = DataEncoder.<ItemStack>decode(is, ItemStack.class);
/* 605 */         event = is.readInt();
/*     */         
/* 607 */         if (stack.func_77973_b() instanceof INetworkItemEventListener) {
/* 608 */           IC2.platform.requestTick(true, new Runnable()
/*     */               {
/*     */                 public void run() {
/* 611 */                   ((INetworkItemEventListener)stack.func_77973_b()).onNetworkEvent(stack, player, event);
/*     */                 }
/*     */               });
/*     */         }
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case KeyUpdate:
/* 620 */         keyState = is.readInt();
/*     */         
/* 622 */         IC2.platform.requestTick(true, new Runnable()
/*     */             {
/*     */               public void run() {
/* 625 */                 IC2.keyboard.processKeyUpdate(player, keyState);
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case TileEntityEvent:
/* 633 */         teDeferred = DataEncoder.decodeDeferred(is, TileEntity.class);
/* 634 */         event = is.readInt();
/*     */         
/* 636 */         IC2.platform.requestTick(true, new Runnable()
/*     */             {
/*     */               public void run() {
/* 639 */                 TileEntity te = DataEncoder.<TileEntity>getValue(teDeferred);
/*     */                 
/* 641 */                 if (te instanceof INetworkClientTileEntityEventListener) {
/* 642 */                   ((INetworkClientTileEntityEventListener)te).onNetworkEvent(player, event);
/*     */                 }
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case RequestGUI:
/* 651 */         hand = is.readBoolean();
/* 652 */         object1 = hand ? null : DataEncoder.decodeDeferred(is, TileEntity.class);
/*     */         
/* 654 */         IC2.platform.requestTick(true, new Runnable() {
/*     */               private IHasGui tryFindGUI(ItemStack stack) {
/* 656 */                 if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IHandHeldInventory) {
/* 657 */                   return ((IHandHeldInventory)stack.func_77973_b()).getInventory(player, stack);
/*     */                 }
/* 659 */                 return null;
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public void run() {
/* 665 */                 if (hand) {
/* 666 */                   for (ItemStack stack : player.func_184214_aD()) {
/* 667 */                     IHasGui gui = tryFindGUI(stack);
/*     */                     
/* 669 */                     if (gui != null) {
/* 670 */                       IC2.platform.launchGui(player, gui);
/*     */                       break;
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/* 675 */                   TileEntity te = DataEncoder.<TileEntity>getValue(teDeferred);
/*     */                   
/* 677 */                   if (te instanceof IHasGui) {
/* 678 */                     IC2.platform.launchGui(player, (IHasGui)te);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case Rpc:
/* 688 */         RpcHandler.processRpcRequest(is, (EntityPlayerMP)player);
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 693 */     onCommonPacketData(packetType, true, is, player); } protected void onCommonPacketData(SubPacketType packetType, boolean simulating, GrowingBuffer is, final EntityPlayer player) throws IOException { final int slot, windowId; final Object teDeferred; final Item item;
/*     */     final String fieldName, event, fieldName;
/*     */     int dataCount;
/*     */     final Object value, subData[];
/*     */     int i;
/* 698 */     switch (packetType) {
/*     */       case PlayerItemData:
/* 700 */         slot = is.readByte();
/* 701 */         item = DataEncoder.<Item>decode(is, Item.class);
/* 702 */         dataCount = is.readVarInt();
/*     */         
/* 704 */         subData = new Object[dataCount];
/*     */         
/* 706 */         for (i = 0; i < dataCount; i++) {
/* 707 */           subData[i] = DataEncoder.decode(is);
/*     */         }
/*     */         
/* 710 */         if (slot >= 0 && slot < 9) {
/* 711 */           IC2.platform.requestTick(simulating, new Runnable()
/*     */               {
/*     */                 public void run() {
/* 714 */                   for (int i = 0; i < subData.length; i++) {
/* 715 */                     subData[i] = DataEncoder.getValue(subData[i]);
/*     */                   }
/*     */                   
/* 718 */                   ItemStack stack = (ItemStack)player.field_71071_by.field_70462_a.get(slot);
/*     */                   
/* 720 */                   if (!StackUtil.isEmpty(stack) && stack.func_77973_b() == item && 
/* 721 */                     item instanceof IPlayerItemDataListener) {
/* 722 */                     ((IPlayerItemDataListener)item).onPlayerItemNetworkData(player, slot, subData);
/*     */                   }
/*     */                 }
/*     */               });
/*     */         }
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case ContainerData:
/* 733 */         windowId = is.readInt();
/* 734 */         str1 = is.readString();
/* 735 */         value = DataEncoder.decode(is);
/*     */         
/* 737 */         IC2.platform.requestTick(simulating, new Runnable()
/*     */             {
/*     */               public void run() {
/* 740 */                 if (player.field_71070_bA instanceof ContainerBase && player.field_71070_bA.field_75152_c == windowId && (NetworkManager.this
/*     */                   
/* 742 */                   .isClient() || NetworkManager.this.getClientModifiableField(player.field_71070_bA.getClass(), fieldName) != null)) {
/* 743 */                   ReflectionUtil.setValueRecursive(player.field_71070_bA, fieldName, DataEncoder.getValue(value));
/*     */                 }
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case ContainerEvent:
/* 753 */         windowId = is.readInt();
/* 754 */         event = is.readString();
/*     */         
/* 756 */         IC2.platform.requestTick(simulating, new Runnable()
/*     */             {
/*     */               public void run() {
/* 759 */                 if (player.field_71070_bA instanceof ContainerBase && player.field_71070_bA.field_75152_c == windowId)
/*     */                 {
/* 761 */                   ((ContainerBase)player.field_71070_bA).onContainerEvent(event);
/*     */                 }
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case HandHeldInvData:
/* 770 */         windowId = is.readInt();
/* 771 */         fieldName = is.readString();
/* 772 */         value = DataEncoder.decode(is);
/*     */         
/* 774 */         IC2.platform.requestTick(simulating, new Runnable()
/*     */             {
/*     */               public void run() {
/* 777 */                 if (player.field_71070_bA instanceof ContainerBase && player.field_71070_bA.field_75152_c == windowId) {
/*     */                   
/* 779 */                   ContainerBase<?> container = (ContainerBase)player.field_71070_bA;
/*     */                   
/* 781 */                   if (container.base instanceof ic2.core.item.tool.HandHeldInventory && (NetworkManager.this
/* 782 */                     .isClient() || NetworkManager.this.getClientModifiableField(container.base.getClass(), fieldName) != null)) {
/* 783 */                     ReflectionUtil.setValueRecursive(container.base, fieldName, DataEncoder.getValue(value));
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case TileEntityData:
/* 793 */         teDeferred = DataEncoder.decodeDeferred(is, TileEntity.class);
/* 794 */         fieldName = is.readString();
/* 795 */         value = DataEncoder.decode(is);
/*     */         
/* 797 */         IC2.platform.requestTick(simulating, new Runnable()
/*     */             {
/*     */               public void run() {
/* 800 */                 TileEntity te = DataEncoder.<TileEntity>getValue(teDeferred);
/*     */                 
/* 802 */                 if (te != null && (NetworkManager.this
/* 803 */                   .isClient() || NetworkManager.this.getClientModifiableField(te.getClass(), fieldName) != null)) {
/* 804 */                   ReflectionUtil.setValueRecursive(te, fieldName, DataEncoder.getValue(value));
/*     */                 }
/*     */               }
/*     */             });
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 813 */     IC2.log.warn(LogCategory.Network, "Unhandled packet type: %s", new Object[] { packetType.name() }); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateKeyUpdate(int keyState) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendLoginData() {}
/*     */ 
/*     */   
/*     */   public final void initiateExplosionEffect(World world, Vec3d pos, ExplosionIC2.Type type) {
/* 826 */     assert !isClient();
/*     */     
/*     */     try {
/* 829 */       GrowingBuffer buffer = new GrowingBuffer(32);
/*     */       
/* 831 */       SubPacketType.ExplosionEffect.writeTo(buffer);
/*     */       
/* 833 */       DataEncoder.encode(buffer, world, false);
/* 834 */       DataEncoder.encode(buffer, pos, false);
/* 835 */       DataEncoder.encode(buffer, type, false);
/* 836 */       buffer.flip();
/*     */       
/* 838 */       for (Object obj : world.field_73010_i) {
/* 839 */         if (!(obj instanceof EntityPlayerMP))
/*     */           continue; 
/* 841 */         EntityPlayerMP player = (EntityPlayerMP)obj;
/*     */         
/* 843 */         if (player.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) < 128.0D) {
/* 844 */           sendPacket(buffer, false, player);
/*     */         }
/*     */       } 
/* 847 */     } catch (IOException e) {
/* 848 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void sendPacket(GrowingBuffer buffer) {
/* 853 */     if (!isClient()) {
/* 854 */       channel.sendToAll(makePacket(buffer, true));
/*     */     } else {
/* 856 */       channel.sendToServer(makePacket(buffer, true));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void sendPacket(GrowingBuffer buffer, boolean advancePos, EntityPlayerMP player) {
/* 861 */     assert !isClient();
/* 862 */     channel.sendTo(makePacket(buffer, advancePos), player);
/*     */   }
/*     */   
/*     */   static <T extends Collection<EntityPlayerMP>> T getPlayersInRange(World world, BlockPos pos, T result) {
/* 866 */     if (!(world instanceof WorldServer)) return result;
/*     */     
/* 868 */     PlayerChunkMap playerManager = ((WorldServer)world).func_184164_w();
/* 869 */     PlayerChunkMapEntry instance = playerManager.func_187301_b(pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
/* 870 */     if (instance == null) return result;
/*     */     
/* 872 */     result.addAll((Collection)ReflectionUtil.getFieldValue(playerInstancePlayers, instance));
/*     */     
/* 874 */     return result;
/*     */   }
/*     */   
/* 877 */   private static Field playerInstancePlayers = ReflectionUtil.getField(PlayerChunkMapEntry.class, List.class); private static FMLEventChannel channel; private static final int maxPacketDataLength = 32766; public static final String channelName = "ic2";
/*     */   
/*     */   static void writeFieldData(Object object, String fieldName, GrowingBuffer out) throws IOException {
/* 880 */     int pos = fieldName.indexOf('=');
/*     */     
/* 882 */     if (pos != -1) {
/* 883 */       out.writeString(fieldName.substring(0, pos));
/* 884 */       DataEncoder.encode(out, fieldName.substring(pos + 1));
/*     */     } else {
/* 886 */       out.writeString(fieldName);
/*     */       
/*     */       try {
/* 889 */         DataEncoder.encode(out, ReflectionUtil.getValueRecursive(object, fieldName));
/* 890 */       } catch (NoSuchFieldException e) {
/* 891 */         throw new RuntimeException("Can't find field " + fieldName + " in " + object.getClass().getName(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static FMLProxyPacket makePacket(GrowingBuffer buffer, boolean advancePos) {
/* 897 */     return new FMLProxyPacket(new PacketBuffer(buffer.toByteBuf(advancePos)), "ic2");
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\NetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */