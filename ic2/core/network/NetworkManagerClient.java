/*     */ package ic2.core.network;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import ic2.api.network.INetworkItemEventListener;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.audio.AudioPosition;
/*     */ import ic2.core.audio.PositionSpec;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.Components;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.item.IHandHeldInventory;
/*     */ import ic2.core.item.IHandHeldSubInventory;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ParticleUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.zip.InflaterOutputStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketCloseWindow;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class NetworkManagerClient
/*     */   extends NetworkManager
/*     */ {
/*     */   private GrowingBuffer largePacketBuffer;
/*     */   
/*     */   protected boolean isClient() {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateClientItemEvent(ItemStack stack, int event) {
/*     */     try {
/*  65 */       GrowingBuffer buffer = new GrowingBuffer(256);
/*     */       
/*  67 */       SubPacketType.ItemEvent.writeTo(buffer);
/*  68 */       DataEncoder.encode(buffer, stack, false);
/*  69 */       buffer.writeInt(event);
/*     */       
/*  71 */       buffer.flip();
/*  72 */       sendPacket(buffer);
/*  73 */     } catch (IOException e) {
/*  74 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateKeyUpdate(int keyState) {
/*  80 */     GrowingBuffer buffer = new GrowingBuffer(5);
/*     */     
/*  82 */     SubPacketType.KeyUpdate.writeTo(buffer);
/*  83 */     buffer.writeInt(keyState);
/*     */     
/*  85 */     buffer.flip();
/*  86 */     sendPacket(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateClientTileEntityEvent(TileEntity te, int event) {
/*     */     try {
/*  92 */       GrowingBuffer buffer = new GrowingBuffer(32);
/*     */       
/*  94 */       SubPacketType.TileEntityEvent.writeTo(buffer);
/*  95 */       DataEncoder.encode(buffer, te, false);
/*  96 */       buffer.writeInt(event);
/*     */       
/*  98 */       buffer.flip();
/*  99 */       sendPacket(buffer);
/* 100 */     } catch (IOException e) {
/* 101 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void initiateRpc(int id, Class<? extends IRpcProvider<?>> provider, Object[] args) {
/*     */     try {
/* 108 */       GrowingBuffer buffer = new GrowingBuffer(256);
/*     */       
/* 110 */       SubPacketType.Rpc.writeTo(buffer);
/*     */       
/* 112 */       buffer.writeInt(id);
/* 113 */       buffer.writeString(provider.getName());
/* 114 */       DataEncoder.encode(buffer, args);
/*     */       
/* 116 */       buffer.flip();
/* 117 */       sendPacket(buffer);
/* 118 */     } catch (IOException e) {
/* 119 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestGUI(IHasGui inventory) {
/*     */     try {
/* 126 */       GrowingBuffer buffer = new GrowingBuffer(32);
/*     */       
/* 128 */       SubPacketType.RequestGUI.writeTo(buffer);
/*     */       
/* 130 */       if (inventory instanceof TileEntity) {
/* 131 */         TileEntity te = (TileEntity)inventory;
/*     */         
/* 133 */         buffer.writeBoolean(false);
/* 134 */         DataEncoder.encode(buffer, te, false);
/*     */       } else {
/* 136 */         EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*     */         
/* 138 */         if ((!StackUtil.isEmpty(((EntityPlayer)entityPlayerSP).field_71071_by.func_70448_g()) && ((EntityPlayer)entityPlayerSP).field_71071_by.func_70448_g().func_77973_b() instanceof IHandHeldInventory) || (
/* 139 */           !StackUtil.isEmpty(entityPlayerSP.func_184592_cb()) && entityPlayerSP.func_184592_cb().func_77973_b() instanceof IHandHeldInventory)) {
/* 140 */           buffer.writeBoolean(true);
/*     */         } else {
/* 142 */           IC2.platform.displayError("An unknown GUI type was attempted to be displayed.\nThis could happen due to corrupted data from a player or a bug.\n\n(Technical information: " + inventory + ")", new Object[0]);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 148 */       buffer.flip();
/* 149 */       sendPacket(buffer);
/* 150 */     } catch (IOException e) {
/* 151 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
/* 157 */     assert !getClass().getName().equals(NetworkManager.class.getName());
/*     */     
/*     */     try {
/* 160 */       onPacketData(GrowingBuffer.wrap(event.getPacket().payload()), (EntityPlayer)(Minecraft.func_71410_x()).field_71439_g);
/* 161 */     } catch (Throwable t) {
/* 162 */       IC2.log.warn(LogCategory.Network, t, "Network read failed");
/* 163 */       throw new RuntimeException(t);
/*     */     } 
/*     */     
/* 166 */     event.getPacket().payload().release(); } private void onPacketData(GrowingBuffer is, final EntityPlayer player) throws IOException { int state; final Object teDeferred; final GameProfile profile; final boolean isAdmin; final Object worldDeferred; final int dimensionId; final Object worldDeferred; final int event; final ItemStack stack; final Object teDeferred; final int currentItemPosition; final Vec3d pos; final BlockPos pos; final int event, windowId; final boolean subGUI; final ExplosionIC2.Type type; String componentName; final double x; final short ID; final Class<? extends TileEntityComponent> componentCls; final int windowId, dataLen; final double y; final byte[] data; final double z; final int count; final double xSpeed;
/*     */     final ITeBlock teBlock;
/*     */     final double zSpeed;
/*     */     final ITeBlock teBlock;
/* 170 */     if (!is.hasAvailable())
/*     */       return; 
/* 172 */     SubPacketType packetType = SubPacketType.read(is, false);
/* 173 */     if (packetType == null)
/*     */       return; 
/* 175 */     switch (packetType) {
/*     */       case LargePacket:
/* 177 */         state = is.readUnsignedByte();
/*     */         
/* 179 */         if ((state & 0x2) != 0) {
/*     */           GrowingBuffer input;
/*     */           
/* 182 */           if ((state & 0x1) != 0) {
/* 183 */             input = is;
/*     */           } else {
/* 185 */             input = this.largePacketBuffer;
/*     */             
/* 187 */             if (input == null) throw new IOException("unexpected large packet continuation");
/*     */             
/* 189 */             is.writeTo(input);
/* 190 */             input.flip();
/* 191 */             this.largePacketBuffer = null;
/*     */           } 
/*     */           
/* 194 */           GrowingBuffer decompBuffer = new GrowingBuffer(input.available() * 2);
/* 195 */           InflaterOutputStream inflate = new InflaterOutputStream(decompBuffer);
/*     */           
/* 197 */           input.writeTo(inflate);
/* 198 */           inflate.close();
/* 199 */           decompBuffer.flip();
/*     */           
/* 201 */           switch (state >> 2) { case 0:
/* 202 */               TeUpdate.receive(decompBuffer); break;
/* 203 */             case 1: processChatPacket(decompBuffer); break;
/* 204 */             case 2: processConsolePacket(decompBuffer); break; }
/*     */         
/*     */         } else {
/* 207 */           if ((state & 0x1) != 0) {
/* 208 */             assert this.largePacketBuffer == null;
/* 209 */             this.largePacketBuffer = new GrowingBuffer(32752);
/*     */           } 
/*     */           
/* 212 */           if (this.largePacketBuffer == null) throw new IOException("unexpected large packet continuation");
/*     */           
/* 214 */           is.writeTo(this.largePacketBuffer);
/*     */         } 
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case TileEntityEvent:
/* 221 */         teDeferred = DataEncoder.decodeDeferred(is, TileEntity.class);
/* 222 */         event = is.readInt();
/*     */         
/* 224 */         IC2.platform.requestTick(false, new Runnable()
/*     */             {
/*     */               public void run() {
/* 227 */                 TileEntity te = DataEncoder.<TileEntity>getValue(teDeferred);
/*     */                 
/* 229 */                 if (te instanceof INetworkTileEntityEventListener) {
/* 230 */                   ((INetworkTileEntityEventListener)te).onNetworkEvent(event);
/*     */                 }
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case ItemEvent:
/* 239 */         profile = DataEncoder.<GameProfile>decode(is, GameProfile.class);
/* 240 */         stack = DataEncoder.<ItemStack>decode(is, ItemStack.class);
/* 241 */         i = is.readInt();
/*     */         
/* 243 */         IC2.platform.requestTick(false, new Runnable()
/*     */             {
/*     */               public void run() {
/* 246 */                 WorldClient worldClient = (Minecraft.func_71410_x()).field_71441_e;
/*     */                 
/* 248 */                 for (Object obj : ((World)worldClient).field_73010_i) {
/* 249 */                   EntityPlayer player = (EntityPlayer)obj;
/*     */                   
/* 251 */                   if ((profile.getId() != null && profile.getId().equals(player.func_146103_bH().getId())) || (profile
/* 252 */                     .getId() == null && profile.getName().equals(player.func_146103_bH().getName()))) {
/* 253 */                     if (stack.func_77973_b() instanceof INetworkItemEventListener) {
/* 254 */                       ((INetworkItemEventListener)stack.func_77973_b()).onNetworkEvent(stack, player, event);
/*     */                     }
/*     */                     break;
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case GuiDisplay:
/* 267 */         isAdmin = is.readBoolean();
/*     */         
/* 269 */         switch (is.readByte()) {
/*     */           case 0:
/* 271 */             object2 = DataEncoder.decodeDeferred(is, TileEntity.class);
/* 272 */             windowId = is.readInt();
/*     */             
/* 274 */             IC2.platform.requestTick(false, new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 277 */                     EntityPlayer player = IC2.platform.getPlayerInstance();
/* 278 */                     TileEntity te = DataEncoder.<TileEntity>getValue(teDeferred);
/*     */                     
/* 280 */                     if (te instanceof IHasGui) {
/* 281 */                       IC2.platform.launchGuiClient(player, (IHasGui)te, isAdmin);
/* 282 */                       player.field_71070_bA.field_75152_c = windowId;
/* 283 */                     } else if (player instanceof EntityPlayerSP) {
/* 284 */                       ((EntityPlayerSP)player).field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(windowId));
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */             break;
/*     */ 
/*     */ 
/*     */           
/*     */           case 1:
/* 293 */             currentItemPosition = is.readInt();
/* 294 */             subGUI = is.readBoolean();
/* 295 */             ID = subGUI ? is.readShort() : 0;
/* 296 */             j = is.readInt();
/*     */             
/* 298 */             IC2.platform.requestTick(false, new Runnable() {
/*     */                   public void run() {
/*     */                     ItemStack currentItem;
/* 301 */                     EntityPlayer player = IC2.platform.getPlayerInstance();
/*     */ 
/*     */                     
/* 304 */                     if (currentItemPosition < 0) {
/*     */                       
/* 306 */                       int actualItemPosition = currentItemPosition ^ 0xFFFFFFFF;
/* 307 */                       if (actualItemPosition > player.field_71071_by.field_184439_c.size() - 1)
/*     */                         return; 
/* 309 */                       currentItem = (ItemStack)player.field_71071_by.field_184439_c.get(actualItemPosition);
/*     */                     } else {
/* 311 */                       if (currentItemPosition != player.field_71071_by.field_70461_c)
/*     */                         return; 
/* 313 */                       currentItem = player.field_71071_by.func_70448_g();
/*     */                     } 
/* 315 */                     if (currentItem != null && currentItem.func_77973_b() instanceof IHandHeldInventory) {
/* 316 */                       if (subGUI && currentItem.func_77973_b() instanceof IHandHeldSubInventory) {
/* 317 */                         IC2.platform.launchGuiClient(player, ((IHandHeldSubInventory)currentItem.func_77973_b()).getSubInventory(player, currentItem, ID), isAdmin);
/*     */                       } else {
/* 319 */                         IC2.platform.launchGuiClient(player, ((IHandHeldInventory)currentItem.func_77973_b()).getInventory(player, currentItem), isAdmin);
/*     */                       } 
/* 321 */                     } else if (player instanceof EntityPlayerSP) {
/* 322 */                       ((EntityPlayerSP)player).field_71174_a.func_147297_a((Packet)new CPacketCloseWindow(windowId));
/*     */                     } 
/*     */                     
/* 325 */                     player.field_71070_bA.field_75152_c = windowId;
/*     */                   }
/*     */                 });
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */ 
/*     */       
/*     */       case ExplosionEffect:
/* 337 */         object1 = DataEncoder.decodeDeferred(is, World.class);
/* 338 */         vec3d = DataEncoder.<Vec3d>decode(is, Vec3d.class);
/* 339 */         type = DataEncoder.<ExplosionIC2.Type>decodeEnum(is, ExplosionIC2.Type.class);
/*     */         
/* 341 */         IC2.platform.requestTick(false, new Runnable()
/*     */             {
/*     */               public void run() {
/* 344 */                 World world = DataEncoder.<World>getValue(worldDeferred);
/*     */                 
/* 346 */                 if (world != null) {
/* 347 */                   switch (type) {
/*     */                     case LargePacket:
/* 349 */                       world.func_184133_a(player, new BlockPos(pos), SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/* 350 */                       world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */                       break;
/*     */                     
/*     */                     case TileEntityEvent:
/* 354 */                       IC2.audioManager.playOnce(new AudioPosition(world, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c), PositionSpec.Center, "Machines/MachineOverload.ogg", true, IC2.audioManager.getDefaultVolume());
/* 355 */                       world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */                       break;
/*     */                     
/*     */                     case ItemEvent:
/* 359 */                       world.func_184133_a(player, new BlockPos(pos), SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 4.0F, (1.0F + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
/* 360 */                       world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */                       break;
/*     */                     
/*     */                     case GuiDisplay:
/* 364 */                       IC2.audioManager.playOnce(new AudioPosition(world, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c), PositionSpec.Center, "Tools/NukeExplosion.ogg", true, IC2.audioManager.getDefaultVolume());
/* 365 */                       world.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, pos.field_72450_a, pos.field_72448_b, pos.field_72449_c, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */                       break;
/*     */                   } 
/*     */ 
/*     */                 
/*     */                 }
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */       
/*     */       case Rpc:
/* 377 */         throw new RuntimeException("Received unexpected RPC packet");
/*     */       
/*     */       case TileEntityBlockComponent:
/* 380 */         dimensionId = is.readInt();
/* 381 */         pos = DataEncoder.<BlockPos>decode(is, BlockPos.class);
/*     */         
/* 383 */         componentName = is.readString();
/* 384 */         componentCls = Components.getClass(componentName);
/* 385 */         if (componentCls == null) throw new IOException("invalid component: " + componentName);
/*     */         
/* 387 */         dataLen = is.readVarInt();
/* 388 */         if (dataLen > 65536) throw new IOException("data length limit exceeded"); 
/* 389 */         data = new byte[dataLen];
/* 390 */         is.readFully(data);
/*     */         
/* 392 */         IC2.platform.requestTick(false, new Runnable()
/*     */             {
/*     */               public void run() {
/* 395 */                 WorldClient worldClient = (Minecraft.func_71410_x()).field_71441_e;
/* 396 */                 if (((World)worldClient).field_73011_w.getDimension() != dimensionId)
/*     */                   return; 
/* 398 */                 TileEntity teRaw = worldClient.func_175625_s(pos);
/* 399 */                 if (!(teRaw instanceof TileEntityBlock))
/*     */                   return; 
/* 401 */                 TileEntityComponent component = ((TileEntityBlock)teRaw).getComponent(componentCls);
/* 402 */                 if (component == null)
/*     */                   return; 
/* 404 */                 DataInputStream dataIs = new DataInputStream(new ByteArrayInputStream(data));
/*     */                 
/*     */                 try {
/* 407 */                   component.onNetworkUpdate(dataIs);
/* 408 */                 } catch (IOException e) {
/* 409 */                   throw new RuntimeException(e);
/*     */                 } 
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case TileEntityBlockLandEffect:
/* 418 */         worldDeferred = DataEncoder.decodeDeferred(is, World.class);
/*     */         
/* 420 */         if (is.readBoolean()) {
/* 421 */           pos = (BlockPos)DataEncoder.decode(is, DataEncoder.EncodedType.BlockPos);
/*     */         } else {
/* 423 */           pos = null;
/*     */         } 
/* 425 */         x = is.readDouble();
/* 426 */         y = is.readDouble();
/* 427 */         z = is.readDouble();
/* 428 */         count = is.readInt();
/* 429 */         teBlock = TeBlockRegistry.get(is.readString());
/*     */         
/* 431 */         IC2.platform.requestTick(false, new Runnable()
/*     */             {
/*     */               public void run() {
/* 434 */                 World world = DataEncoder.<World>getValue(worldDeferred);
/* 435 */                 if (world == null)
/*     */                   return; 
/* 437 */                 ParticleUtil.spawnBlockLandParticles(world, pos, x, y, z, count, teBlock);
/*     */               }
/*     */             });
/*     */         return;
/*     */ 
/*     */ 
/*     */       
/*     */       case TileEntityBlockRunEffect:
/* 445 */         worldDeferred = DataEncoder.decodeDeferred(is, World.class);
/*     */         
/* 447 */         if (is.readBoolean()) {
/* 448 */           pos = (BlockPos)DataEncoder.decode(is, DataEncoder.EncodedType.BlockPos);
/*     */         } else {
/* 450 */           pos = null;
/*     */         } 
/* 452 */         x = is.readDouble();
/* 453 */         y = is.readDouble();
/* 454 */         z = is.readDouble();
/* 455 */         xSpeed = is.readDouble();
/* 456 */         zSpeed = is.readDouble();
/* 457 */         iTeBlock1 = TeBlockRegistry.get(is.readString());
/*     */         
/* 459 */         IC2.platform.requestTick(false, new Runnable()
/*     */             {
/*     */               public void run() {
/* 462 */                 World world = DataEncoder.<World>getValue(worldDeferred);
/* 463 */                 if (world == null)
/*     */                   return; 
/* 465 */                 ParticleUtil.spawnBlockRunParticles(world, pos, x, y, z, xSpeed, zSpeed, teBlock);
/*     */               }
/*     */             });
/*     */         return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 473 */     onCommonPacketData(packetType, false, is, player); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processChatPacket(GrowingBuffer buffer) {
/* 479 */     final String messages = buffer.readString();
/*     */     
/* 481 */     IC2.platform.requestTick(false, new Runnable()
/*     */         {
/*     */           public void run() {
/* 484 */             for (String line : messages.split("[\\r\\n]+")) {
/* 485 */               IC2.platform.messagePlayer(null, line, new Object[0]);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static void processConsolePacket(GrowingBuffer buffer) {
/* 492 */     String messages = buffer.readString();
/*     */ 
/*     */     
/* 495 */     PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));
/*     */     
/* 497 */     for (String line : messages.split("[\\r\\n]+")) {
/* 498 */       console.println(line);
/*     */     }
/*     */     
/* 501 */     console.flush();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\NetworkManagerClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */