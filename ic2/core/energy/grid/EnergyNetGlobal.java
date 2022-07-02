/*     */ package ic2.core.energy.grid;
/*     */ 
/*     */ import ic2.api.energy.IEnergyNet;
/*     */ import ic2.api.energy.IEnergyNetEventReceiver;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.info.ILocatable;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.WorldData;
/*     */ import ic2.core.energy.leg.EnergyCalculatorLeg;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EnergyNetGlobal
/*     */   implements IEnergyNet
/*     */ {
/*     */   public static EnergyNetGlobal create() {
/*  25 */     if (System.getProperty("IC2ExpEnet") != null);
/*  26 */     calculator = (IEnergyCalculator)new EnergyCalculatorLeg();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     EventHandler.init();
/*     */     
/*  33 */     return new EnergyNetGlobal();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEnergyTile getTile(World world, BlockPos pos) {
/*  40 */     if (world == null) throw new NullPointerException("null world"); 
/*  41 */     if (pos == null) throw new NullPointerException("null pos");
/*     */     
/*  43 */     return getLocal(world).getIoTile(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public IEnergyTile getSubTile(World world, BlockPos pos) {
/*  48 */     if (world == null) throw new NullPointerException("null world"); 
/*  49 */     if (pos == null) throw new NullPointerException("null pos");
/*     */     
/*  51 */     return getLocal(world).getSubTile(pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends TileEntity & IEnergyTile> void addTile(T tile) {
/*  56 */     if (tile == null) throw new NullPointerException("null tile");
/*     */     
/*  58 */     addTile((IEnergyTile)tile, tile.func_145831_w(), tile.func_174877_v());
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends ILocatable & IEnergyTile> void addTile(T tile) {
/*  63 */     if (tile == null) throw new NullPointerException("null tile");
/*     */     
/*  65 */     addTile((IEnergyTile)tile, tile.getWorldObj(), tile.getPosition());
/*     */   }
/*     */   
/*     */   private static void addTile(IEnergyTile tile, World world, BlockPos pos) {
/*  69 */     if (EnergyNetSettings.logEnetApiAccessTraces) {
/*  70 */       IC2.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API addTile %s.", new Object[] { Util.toString(tile, (IBlockAccess)world, pos) });
/*  71 */     } else if (EnergyNetSettings.logEnetApiAccesses) {
/*  72 */       IC2.log.debug(LogCategory.EnergyNet, "API addTile %s.", new Object[] { Util.toString(tile, (IBlockAccess)world, pos) });
/*     */     } 
/*     */     
/*  75 */     getLocal(world).addTile(tile, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTile(IEnergyTile tile) {
/*  80 */     if (tile == null) throw new NullPointerException("null tile");
/*     */     
/*  82 */     World world = getWorld(tile);
/*  83 */     BlockPos pos = getPos(tile);
/*     */     
/*  85 */     if (EnergyNetSettings.logEnetApiAccessTraces) {
/*  86 */       IC2.log.debug(LogCategory.EnergyNet, new Throwable("Called from:"), "API removeTile %s.", new Object[] { Util.toString(tile, (IBlockAccess)world, pos) });
/*  87 */     } else if (EnergyNetSettings.logEnetApiAccesses) {
/*  88 */       IC2.log.debug(LogCategory.EnergyNet, "API removeTile %s.", new Object[] { Util.toString(tile, (IBlockAccess)world, pos) });
/*     */     } 
/*     */     
/*  91 */     getLocal(world).removeTile(tile, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public World getWorld(IEnergyTile tile) {
/*  96 */     if (tile == null) throw new NullPointerException("null tile");
/*     */     
/*  98 */     if (tile instanceof ILocatable)
/*  99 */       return ((ILocatable)tile).getWorldObj(); 
/* 100 */     if (tile instanceof TileEntity) {
/* 101 */       return ((TileEntity)tile).func_145831_w();
/*     */     }
/* 103 */     throw new UnsupportedOperationException("unlocatable tile type: " + tile.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos getPos(IEnergyTile tile) {
/* 109 */     if (tile == null) throw new NullPointerException("null tile");
/*     */     
/* 111 */     if (tile instanceof ILocatable)
/* 112 */       return ((ILocatable)tile).getPosition(); 
/* 113 */     if (tile instanceof TileEntity) {
/* 114 */       return ((TileEntity)tile).func_174877_v();
/*     */     }
/* 116 */     throw new UnsupportedOperationException("unlocatable tile type: " + tile.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeStats getNodeStats(IEnergyTile tile) {
/* 122 */     return getLocal(getWorld(tile)).getNodeStats(tile);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dumpDebugInfo(World world, BlockPos pos, PrintStream console, PrintStream chat) {
/* 127 */     return getLocal(world).dumpDebugInfo(pos, console, chat);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getPowerFromTier(int tier) {
/* 132 */     if (tier < 14)
/* 133 */       return (8 << tier * 2); 
/* 134 */     if (tier < 30) {
/* 135 */       return 8.0D * Math.pow(4.0D, tier);
/*     */     }
/* 137 */     return 9.223372036854776E18D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTierFromPower(double power) {
/* 143 */     if (power <= 0.0D) return 0;
/*     */     
/* 145 */     return (int)Math.ceil(Math.log(power / 8.0D) / Math.log(4.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void registerEventReceiver(IEnergyNetEventReceiver receiver) {
/* 150 */     if (eventReceivers.contains(receiver))
/* 151 */       return;  eventReceivers.add(receiver);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void unregisterEventReceiver(IEnergyNetEventReceiver receiver) {
/* 156 */     eventReceivers.remove(receiver);
/*     */   }
/*     */   
/*     */   static Iterable<IEnergyNetEventReceiver> getEventReceivers() {
/* 160 */     return eventReceivers;
/*     */   }
/*     */   
/*     */   static IEnergyCalculator getCalculator() {
/* 164 */     return calculator;
/*     */   }
/*     */   
/*     */   public static EnergyNetLocal getLocal(World world) {
/* 168 */     if (world.field_72995_K) throw new IllegalStateException("not applicable clientside"); 
/* 169 */     assert world.func_73046_m().func_152345_ab();
/*     */     
/* 171 */     return (WorldData.get(world)).energyNet;
/*     */   }
/*     */   
/* 174 */   private static final List<IEnergyNetEventReceiver> eventReceivers = new CopyOnWriteArrayList<>();
/*     */   private static IEnergyCalculator calculator;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\EnergyNetGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */