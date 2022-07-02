/*     */ package ic2.core.network;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.SimpleChannelInboundHandler;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class RpcHandler
/*     */   extends SimpleChannelInboundHandler<Packet<?>>
/*     */ {
/*     */   public static boolean registerProvider(IRpcProvider<?> provider) {
/*  28 */     return (providers.putIfAbsent(provider.getClass().getName(), provider) == null);
/*     */   }
/*     */   
/*  31 */   private static ConcurrentMap<String, IRpcProvider<?>> providers = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Rpc<V> run(Class<? extends IRpcProvider<V>> provider, Object... args) {
/*  41 */     int id = IC2.random.nextInt();
/*  42 */     Rpc<V> rpc = new Rpc<>();
/*     */ 
/*     */     
/*  45 */     Rpc<V> prev = (Rpc<V>)pending.putIfAbsent(Integer.valueOf(id), rpc);
/*  46 */     if (prev != null) return run(provider, args);
/*     */     
/*  48 */     ((NetworkManager)IC2.network.get(false)).initiateRpc(id, provider, args);
/*     */     
/*  50 */     return rpc;
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
/*     */   protected static void processRpcRequest(GrowingBuffer is, EntityPlayerMP player) throws IOException {
/*  62 */     int id = is.readInt();
/*  63 */     String providerClassName = is.readString();
/*  64 */     Object[] args = (Object[])DataEncoder.decode(is);
/*     */     
/*  66 */     IRpcProvider<?> provider = providers.get(providerClassName);
/*     */     
/*  68 */     if (provider == null) {
/*  69 */       IC2.log.warn(LogCategory.Network, "Invalid RPC request from %s.", new Object[] { player.func_70005_c_() });
/*     */       
/*     */       return;
/*     */     } 
/*  73 */     Object result = provider.executeRpc(args);
/*     */     
/*  75 */     GrowingBuffer buffer = new GrowingBuffer(256);
/*     */     
/*  77 */     SubPacketType.Rpc.writeTo(buffer);
/*  78 */     buffer.writeInt(id);
/*  79 */     DataEncoder.encode(buffer, result, true);
/*     */     
/*  81 */     buffer.flip();
/*  82 */     ((NetworkManager)IC2.network.get(true)).sendPacket(buffer, true, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public RpcHandler() {
/*  87 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
/*  92 */     String nettyHandlerName = "ic2_rpc_handler";
/*     */     
/*  94 */     if (event.getManager().channel().pipeline().get("ic2_rpc_handler") == null) {
/*     */       try {
/*  96 */         event.getManager().channel().pipeline().addBefore("packet_handler", "ic2_rpc_handler", (ChannelHandler)this);
/*  97 */       } catch (Exception e) {
/*  98 */         throw new RuntimeException("Can't insert handler in " + event.getManager().channel().pipeline().names() + ".", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/* 105 */     for (Rpc<?> rpc : pending.values()) {
/* 106 */       rpc.cancel(true);
/*     */     }
/*     */     
/* 109 */     pending.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext ctx, Packet<?> oPacket) throws Exception {
/* 114 */     FMLProxyPacket packet = null;
/*     */     
/* 116 */     if (oPacket instanceof FMLProxyPacket) {
/* 117 */       packet = (FMLProxyPacket)oPacket;
/* 118 */     } else if (oPacket instanceof SPacketCustomPayload) {
/* 119 */       packet = new FMLProxyPacket((SPacketCustomPayload)oPacket);
/*     */     } 
/*     */     
/* 122 */     if (packet == null || !packet.channel().equals("ic2")) {
/* 123 */       ctx.fireChannelRead(oPacket);
/*     */       
/*     */       return;
/*     */     } 
/* 127 */     ByteBuf payload = packet.payload();
/*     */     
/* 129 */     if (payload.isReadable() && payload.getByte(0) == SubPacketType.Rpc.getId()) {
/* 130 */       processRpcResponse(GrowingBuffer.wrap(packet.payload()));
/*     */     } else {
/* 132 */       ctx.fireChannelRead(oPacket);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processRpcResponse(GrowingBuffer buffer) {
/*     */     try {
/* 138 */       buffer.readByte();
/*     */       
/* 140 */       int id = buffer.readInt();
/* 141 */       Object result = DataEncoder.decode(buffer);
/*     */       
/* 143 */       Rpc<?> rpc = pending.remove(Integer.valueOf(id));
/*     */       
/* 145 */       if (rpc == null) {
/* 146 */         IC2.log.warn(LogCategory.Network, "RPC %d wasn't found while trying to process its response.", new Object[] { Integer.valueOf(id) });
/*     */       } else {
/* 148 */         rpc.finish(result);
/*     */       } 
/* 150 */     } catch (IOException e) {
/* 151 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/* 155 */   private static ConcurrentMap<Integer, Rpc<?>> pending = new ConcurrentHashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\RpcHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */