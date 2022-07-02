/*     */ package ic2.core;
/*     */ 
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.item.tool.ItemNanoSaber;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TickHandler
/*     */ {
/*     */   public TickHandler() {
/*  23 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onWorldTick(TickEvent.WorldTickEvent event) {
/*  28 */     World world = event.world;
/*  29 */     WorldData worldData = WorldData.get(world, false);
/*  30 */     if (worldData == null)
/*     */       return; 
/*  32 */     if (event.phase == TickEvent.Phase.START) {
/*  33 */       IC2.platform.profilerStartSection("updates");
/*     */       
/*  35 */       processUpdates(world, worldData);
/*     */       
/*  37 */       if (!world.field_72995_K) {
/*  38 */         IC2.platform.profilerEndStartSection("retrogen");
/*     */         
/*  40 */         Ic2WorldDecorator.onTick(world, worldData);
/*     */         
/*  42 */         IC2.platform.profilerEndStartSection("Wind");
/*     */         
/*  44 */         worldData.windSim.updateWind();
/*     */         
/*  46 */         if (ConfigUtil.getBool(MainConfig.get(), "balance/disableEnderChest")) {
/*  47 */           IC2.platform.profilerEndStartSection("EnderChestCheck");
/*     */           
/*  49 */           for (int i = 0; i < world.field_175730_i.size(); i++) {
/*  50 */             TileEntity te = world.field_175730_i.get(i);
/*     */             
/*  52 */             if (te instanceof net.minecraft.tileentity.TileEntityEnderChest && !te.func_145837_r() && !world.func_175623_d(te.func_174877_v())) {
/*  53 */               world.func_175698_g(te.func_174877_v());
/*  54 */               IC2.log.info(LogCategory.General, "Removed vanilla ender chest at %s.", new Object[] { Util.formatPosition(te) });
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  60 */       IC2.platform.profilerEndSection();
/*     */     } else {
/*  62 */       IC2.platform.profilerStartSection("Networking");
/*     */       
/*  64 */       ((NetworkManager)IC2.network.get(!world.field_72995_K)).onTickEnd(worldData);
/*     */       
/*  66 */       IC2.platform.profilerEndSection();
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onServerTick(TickEvent.ServerTickEvent event) {
/*  72 */     if (event.phase == TickEvent.Phase.START)
/*     */     {
/*  74 */       ItemNanoSaber.ticker++;
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onClientTick(TickEvent.ClientTickEvent event) {
/*  80 */     if (event.phase == TickEvent.Phase.START) {
/*  81 */       IC2.platform.profilerStartSection("Keyboard");
/*  82 */       IC2.keyboard.sendKeyUpdate();
/*     */       
/*  84 */       IC2.platform.profilerEndStartSection("AudioManager");
/*  85 */       IC2.audioManager.onTick();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  90 */       IC2.platform.profilerEndStartSection("updates");
/*     */       
/*  92 */       World world = IC2.platform.getPlayerWorld();
/*     */       
/*  94 */       if (world != null) {
/*  95 */         processUpdates(world, WorldData.get(world));
/*     */       }
/*     */       
/*  98 */       IC2.platform.profilerEndSection();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestSingleWorldTick(World world, IWorldTickCallback callback) {
/* 106 */     (WorldData.get(world)).singleUpdates.add(callback);
/*     */     
/* 108 */     if (debugupdate) debugTraces.put(callback, new Throwable()); 
/*     */   }
/*     */   
/*     */   public void requestContinuousWorldTick(World world, IWorldTickCallback update) {
/* 112 */     WorldData worldData = WorldData.get(world);
/*     */     
/* 114 */     if (!worldData.continuousUpdatesInUse) {
/* 115 */       worldData.continuousUpdates.add(update);
/*     */     } else {
/* 117 */       worldData.continuousUpdatesToRemove.remove(update);
/* 118 */       worldData.continuousUpdatesToAdd.add(update);
/*     */     } 
/*     */     
/* 121 */     if (debugupdate) debugTraces.put(update, new Throwable()); 
/*     */   }
/*     */   
/*     */   public void removeContinuousWorldTick(World world, IWorldTickCallback update) {
/* 125 */     WorldData worldData = WorldData.get(world);
/*     */     
/* 127 */     if (!worldData.continuousUpdatesInUse) {
/* 128 */       worldData.continuousUpdates.remove(update);
/*     */     } else {
/* 130 */       worldData.continuousUpdatesToAdd.remove(update);
/* 131 */       worldData.continuousUpdatesToRemove.add(update);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Throwable getLastDebugTrace() {
/* 136 */     return lastDebugTrace;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void processUpdates(World world, WorldData worldData) {
/* 141 */     IC2.platform.profilerStartSection("single-update");
/*     */     
/*     */     IWorldTickCallback callback;
/*     */     
/* 145 */     while ((callback = worldData.singleUpdates.poll()) != null) {
/* 146 */       if (debugupdate) lastDebugTrace = debugTraces.remove(callback);
/*     */       
/* 148 */       callback.onTick(world);
/*     */     } 
/*     */     
/* 151 */     IC2.platform.profilerEndStartSection("cont-update");
/*     */     
/* 153 */     worldData.continuousUpdatesInUse = true;
/*     */     
/* 155 */     for (IWorldTickCallback update : worldData.continuousUpdates) {
/* 156 */       if (debugupdate) lastDebugTrace = debugTraces.remove(update);
/*     */       
/* 158 */       update.onTick(world);
/*     */     } 
/*     */     
/* 161 */     worldData.continuousUpdatesInUse = false;
/*     */     
/* 163 */     if (debugupdate) lastDebugTrace = null;
/*     */     
/* 165 */     worldData.continuousUpdates.addAll(worldData.continuousUpdatesToAdd);
/* 166 */     worldData.continuousUpdatesToAdd.clear();
/*     */     
/* 168 */     worldData.continuousUpdates.removeAll(worldData.continuousUpdatesToRemove);
/* 169 */     worldData.continuousUpdatesToRemove.clear();
/*     */     
/* 171 */     IC2.platform.profilerEndSection();
/*     */   }
/*     */   
/* 174 */   private static final boolean debugupdate = (System.getProperty("ic2.debugupdate") != null);
/* 175 */   private static final Map<IWorldTickCallback, Throwable> debugTraces = debugupdate ? new WeakHashMap<>() : null;
/*     */   private static Throwable lastDebugTrace;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\TickHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */