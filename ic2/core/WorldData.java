/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.core.block.personal.TradingMarket;
/*    */ import ic2.core.energy.grid.EnergyNetLocal;
/*    */ import ic2.core.network.TeUpdateDataServer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Queue;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldData
/*    */ {
/*    */   private WorldData(World world) {
/* 28 */     if (!world.field_72995_K) {
/* 29 */       this.energyNet = EnergyNetLocal.create(world);
/* 30 */       this.tradeMarket = new TradingMarket(world);
/* 31 */       this.windSim = new WindSim(world);
/*    */     } else {
/* 33 */       this.energyNet = null;
/* 34 */       this.tradeMarket = null;
/* 35 */       this.windSim = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static WorldData get(World world) {
/* 40 */     return get(world, true);
/*    */   }
/*    */   
/*    */   public static WorldData get(World world, boolean load) {
/* 44 */     if (world == null) throw new IllegalArgumentException("world is null");
/*    */     
/* 46 */     ConcurrentMap<Integer, WorldData> index = getIndex(!world.field_72995_K);
/* 47 */     WorldData ret = index.get(Integer.valueOf(world.field_73011_w.getDimension()));
/* 48 */     if (ret != null || !load) return ret;
/*    */     
/* 50 */     ret = new WorldData(world);
/*    */     
/* 52 */     WorldData prev = index.putIfAbsent(Integer.valueOf(world.field_73011_w.getDimension()), ret);
/* 53 */     if (prev != null) ret = prev;
/*    */     
/* 55 */     return ret;
/*    */   }
/*    */   
/*    */   public static void onWorldUnload(World world) {
/* 59 */     getIndex(!world.field_72995_K).remove(Integer.valueOf(world.field_73011_w.getDimension()));
/*    */   }
/*    */   
/*    */   private static ConcurrentMap<Integer, WorldData> getIndex(boolean simulating) {
/* 63 */     return simulating ? idxServer : idxClient;
/*    */   }
/*    */   
/* 66 */   private static ConcurrentMap<Integer, WorldData> idxClient = FMLCommonHandler.instance().getSide().isClient() ? new ConcurrentHashMap<>() : null;
/* 67 */   private static ConcurrentMap<Integer, WorldData> idxServer = new ConcurrentHashMap<>();
/*    */ 
/*    */   
/* 70 */   final Queue<IWorldTickCallback> singleUpdates = new ConcurrentLinkedQueue<>();
/* 71 */   final Set<IWorldTickCallback> continuousUpdates = new HashSet<>();
/*    */   boolean continuousUpdatesInUse = false;
/* 73 */   final List<IWorldTickCallback> continuousUpdatesToAdd = new ArrayList<>();
/* 74 */   final List<IWorldTickCallback> continuousUpdatesToRemove = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public final EnergyNetLocal energyNet;
/*    */ 
/*    */   
/* 80 */   public final Map<TileEntity, TeUpdateDataServer> tesToUpdate = new IdentityHashMap<>();
/*    */ 
/*    */   
/*    */   public final TradingMarket tradeMarket;
/*    */ 
/*    */   
/*    */   public final WindSim windSim;
/*    */ 
/*    */   
/* 89 */   public final Map<Chunk, NBTTagCompound> worldGenData = new IdentityHashMap<>();
/* 90 */   public final Set<Chunk> chunksToDecorate = Collections.newSetFromMap(new IdentityHashMap<>());
/* 91 */   public final Set<Chunk> pendingUnloadChunks = Collections.newSetFromMap(new IdentityHashMap<>());
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\WorldData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */