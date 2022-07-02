/*     */ package ic2.core.energy;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMetaDelegate;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.TickHandler;
/*     */ import ic2.core.energy.grid.EnergyNetLocal;
/*     */ import ic2.core.energy.grid.Grid;
/*     */ import ic2.core.energy.grid.GridInfo;
/*     */ import ic2.core.energy.grid.IEnergyCalculator;
/*     */ import ic2.core.energy.grid.Node;
/*     */ import ic2.core.energy.grid.NodeType;
/*     */ import ic2.core.energy.grid.Tile;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ 
/*     */ public final class EnergyNetLocal implements IEnergyCalculator {
/*  44 */   public static final boolean useLinearTransferModel = ConfigUtil.getBool(MainConfig.get(), "misc/useLinearTransferModel");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final World world = null; public EnergyNetLocal() {
/*  53 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleGridChange(Grid grid) {
/*  59 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean runSyncStep(EnergyNetLocal enet) {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean runSyncStep(Grid grid) {
/*  71 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAsyncStep(Grid grid) {
/*  77 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeStats getNodeStats(Tile tile) {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpNodeInfo(Node node, String prefix, PrintStream console, PrintStream chat) {
/*  89 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTile(IEnergyTile mainTile) {
/*  98 */     addTile(mainTile, 0);
/*     */   }
/*     */   
/*     */   protected void addTile(IEnergyTile mainTile, int retry) {
/* 102 */     if (EnergyNetGlobal.debugTileManagement) {
/* 103 */       IC2.log.debug(LogCategory.EnergyNet, "EnergyNet.addTile(%s, %d), world=%s, chunk=%s, this=%s", new Object[] { mainTile, 
/*     */             
/* 105 */             Integer.valueOf(retry), EnergyNet.instance
/* 106 */             .getWorld(mainTile), EnergyNet.instance
/* 107 */             .getWorld(mainTile).func_175726_f(EnergyNet.instance.getPos(mainTile)), this });
/*     */     }
/*     */     
/* 110 */     if (EnergyNetGlobal.checkApi && !Util.checkInterfaces(mainTile.getClass())) {
/* 111 */       IC2.log.warn(LogCategory.EnergyNet, "EnergyNet.addTile: %s doesn't implement its advertised interfaces completely.", new Object[] { mainTile });
/*     */     }
/*     */     
/* 114 */     if (mainTile instanceof TileEntity && ((TileEntity)mainTile).func_145837_r()) {
/* 115 */       logWarn("EnergyNet.addTile: " + mainTile + " is invalid (TileEntity.isInvalid()), aborting");
/*     */       
/*     */       return;
/*     */     } 
/* 119 */     if (this.world != DimensionManager.getWorld(this.world.field_73011_w.getDimension())) {
/* 120 */       logDebug("EnergyNet.addTile: " + mainTile + " is in an unloaded world, aborting");
/*     */       
/*     */       return;
/*     */     } 
/* 124 */     if (this.locked) {
/* 125 */       logDebug("EnergyNet.addTileEntity: adding " + mainTile + " while locked, postponing.");
/* 126 */       this.pendingAdds.put(mainTile, Integer.valueOf(retry));
/*     */       
/*     */       return;
/*     */     } 
/* 130 */     Tile tile = new Tile(this, mainTile);
/*     */     
/* 132 */     if (EnergyNetGlobal.debugTileManagement) {
/* 133 */       List<String> posStrings = new ArrayList<>(tile.subTiles.size());
/*     */       
/* 135 */       for (IEnergyTile subTile : tile.subTiles) {
/* 136 */         posStrings.add(String.format("%s (%s)", new Object[] { subTile, EnergyNet.instance.getPos(subTile) }));
/*     */       } 
/*     */       
/* 139 */       IC2.log.debug(LogCategory.EnergyNet, "positions: %s", new Object[] { posStrings });
/*     */     } 
/*     */     
/* 142 */     for (ListIterator<IEnergyTile> it = tile.subTiles.listIterator(); it.hasNext(); ) {
/* 143 */       IEnergyTile subTile = it.next();
/* 144 */       BlockPos pos = EnergyNet.instance.getPos(subTile).func_185334_h();
/* 145 */       Tile conflicting = this.registeredTiles.get(pos);
/* 146 */       boolean abort = false;
/*     */       
/* 148 */       if (conflicting != null) {
/* 149 */         if (mainTile == conflicting.mainTile) {
/* 150 */           logDebug("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") is already added using the same position, aborting");
/* 151 */         } else if (retry < 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 158 */           this.pendingAdds.put(mainTile, Integer.valueOf(retry + 1));
/* 159 */         } else if ((conflicting.mainTile instanceof TileEntity && ((TileEntity)mainTile).func_145837_r()) || EnergyNetGlobal.replaceConflicting) {
/*     */           
/* 161 */           logDebug("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") is conflicting with " + conflicting.mainTile + " (invalid=" + ((conflicting.mainTile instanceof TileEntity && ((TileEntity)conflicting.mainTile).func_145837_r()) ? 1 : 0) + ") using the same position, which is abandoned (prev. te not removed), replacing");
/* 162 */           removeTile(conflicting.mainTile);
/* 163 */           conflicting = null;
/*     */         } else {
/* 165 */           logWarn("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") is still conflicting with " + conflicting.mainTile + " using the same position (overlapping), aborting");
/*     */         } 
/*     */         
/* 168 */         if (conflicting != null) abort = true;
/*     */       
/*     */       } 
/* 171 */       if (!abort && !this.world.func_175667_e(pos)) {
/* 172 */         if (retry < 1) {
/*     */           
/* 174 */           logWarn("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") was added too early, postponing");
/*     */           
/* 176 */           this.pendingAdds.put(mainTile, Integer.valueOf(retry + 1));
/*     */         } else {
/* 178 */           logWarn("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") unloaded, aborting");
/*     */         } 
/*     */         
/* 181 */         abort = true;
/*     */       } 
/*     */       
/* 184 */       if (abort) {
/*     */         
/* 186 */         it.previous(); while (it.hasPrevious()) {
/* 187 */           subTile = it.previous();
/*     */           
/* 189 */           this.registeredTiles.remove(EnergyNet.instance.getPos(subTile));
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 195 */       this.registeredTiles.put(pos, tile);
/*     */       
/* 197 */       notifyLoadedNeighbors(pos, tile.subTiles);
/*     */     } 
/*     */     
/* 200 */     addTileToGrids(tile);
/*     */     
/* 202 */     if (EnergyNetGlobal.verifyGrid()) {
/* 203 */       for (Node node : tile.nodes) {
/* 204 */         assert node.getGrid() != null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void notifyLoadedNeighbors(BlockPos pos, List<IEnergyTile> excluded) {
/* 210 */     Set<BlockPos> excludedPositions = new HashSet<>(excluded.size());
/*     */     
/* 212 */     for (IEnergyTile subTile : excluded) {
/* 213 */       excludedPositions.add(EnergyNet.instance.getPos(subTile).func_185334_h());
/*     */     }
/*     */     
/* 216 */     Block block = this.world.func_180495_p(pos).func_177230_c();
/* 217 */     int ocx = pos.func_177958_n() >> 4;
/* 218 */     int ocz = pos.func_177952_p() >> 4;
/*     */     
/* 220 */     for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 221 */       BlockPos cPos = pos.func_177972_a(dir);
/* 222 */       if (!excludedPositions.contains(cPos)) {
/*     */         
/* 224 */         int ccx = cPos.func_177958_n() >> 4;
/* 225 */         int ccz = cPos.func_177952_p() >> 4;
/*     */         
/* 227 */         if (dir.func_176740_k().func_176720_b() || (ccx == ocx && ccz == ocz) || this.world
/*     */           
/* 229 */           .func_175667_e(cPos)) {
/* 230 */           this.world.func_180495_p(cPos).func_189546_a(this.world, cPos, block, pos);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeTile(IEnergyTile mainTile) {
/*     */     List<IEnergyTile> subTiles;
/* 241 */     if (this.locked) throw new IllegalStateException("removeTile isn't allowed from this context");
/*     */     
/* 243 */     if (EnergyNetGlobal.debugTileManagement) {
/* 244 */       IC2.log.debug(LogCategory.EnergyNet, "EnergyNet.removeTile(%s), world=%s, chunk=%s, this=%s", new Object[] { mainTile, EnergyNet.instance
/*     */             
/* 246 */             .getWorld(mainTile), EnergyNet.instance
/* 247 */             .getWorld(mainTile).func_175726_f(EnergyNet.instance.getPos(mainTile)), this });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 252 */     if (mainTile instanceof IMetaDelegate) {
/* 253 */       subTiles = ((IMetaDelegate)mainTile).getSubTiles();
/*     */     } else {
/* 255 */       subTiles = Arrays.asList(new IEnergyTile[] { mainTile });
/*     */     } 
/*     */     
/* 258 */     boolean wasPending = (this.pendingAdds.remove(mainTile) != null);
/*     */     
/* 260 */     if (EnergyNetGlobal.debugTileManagement) {
/* 261 */       List<String> posStrings = new ArrayList<>(subTiles.size());
/*     */       
/* 263 */       for (IEnergyTile subTile : subTiles) {
/* 264 */         posStrings.add(String.format("%s (%s)", new Object[] { subTile, EnergyNet.instance.getPos(subTile) }));
/*     */       } 
/*     */       
/* 267 */       IC2.log.debug(LogCategory.EnergyNet, "positions: %s", new Object[] { posStrings });
/*     */     } 
/*     */     
/* 270 */     boolean removed = false;
/*     */     
/* 272 */     for (IEnergyTile subTile : subTiles) {
/* 273 */       BlockPos pos = EnergyNet.instance.getPos(subTile);
/*     */       
/* 275 */       Tile tile = this.registeredTiles.get(pos);
/*     */       
/* 277 */       if (tile == null) {
/*     */         
/* 279 */         if (!wasPending) logDebug("EnergyNet.removeTileEntity: " + subTile + " (" + mainTile + ") wasn't found (added), skipping");  continue;
/*     */       } 
/* 281 */       if (tile.mainTile != mainTile) {
/* 282 */         logWarn("EnergyNet.removeTileEntity: " + subTile + " (" + mainTile + ") doesn't match the registered tile " + tile.mainTile + ", skipping"); continue;
/*     */       } 
/* 284 */       if (!removed) {
/* 285 */         assert (new HashSet(subTiles)).equals(new HashSet<>(tile.subTiles));
/*     */         
/* 287 */         removeTileFromGrids(tile);
/* 288 */         removed = true;
/* 289 */         this.removedTiles.add(tile);
/*     */       } 
/*     */       
/* 292 */       this.registeredTiles.remove(pos);
/*     */       
/* 294 */       if (this.world.func_175667_e(pos)) {
/* 295 */         notifyLoadedNeighbors(pos, tile.subTiles);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected double getTotalEnergyEmitted(TileEntity tileEntity) {
/* 301 */     BlockPos coords = new BlockPos((Vec3i)tileEntity.func_174877_v());
/* 302 */     Tile tile = this.registeredTiles.get(coords);
/* 303 */     if (tile == null) {
/* 304 */       logWarn("EnergyNet.getTotalEnergyEmitted: " + tileEntity + " is not added to the enet, aborting");
/* 305 */       return 0.0D;
/*     */     } 
/*     */     
/* 308 */     double ret = 0.0D;
/* 309 */     Iterable<NodeStats> stats = tile.getStats();
/*     */     
/* 311 */     for (NodeStats stat : stats) {
/* 312 */       ret += stat.getEnergyOut();
/*     */     }
/*     */     
/* 315 */     return ret;
/*     */   }
/*     */   
/*     */   protected double getTotalEnergySunken(TileEntity tileEntity) {
/* 319 */     BlockPos coords = new BlockPos((Vec3i)tileEntity.func_174877_v());
/* 320 */     Tile tile = this.registeredTiles.get(coords);
/* 321 */     if (tile == null) {
/* 322 */       logWarn("EnergyNet.getTotalEnergySunken: " + tileEntity + " is not added to the enet, aborting");
/* 323 */       return 0.0D;
/*     */     } 
/*     */     
/* 326 */     double ret = 0.0D;
/* 327 */     Iterable<NodeStats> stats = tile.getStats();
/*     */     
/* 329 */     for (NodeStats stat : stats) {
/* 330 */       ret += stat.getEnergyIn();
/*     */     }
/*     */     
/* 333 */     return ret;
/*     */   }
/*     */   
/*     */   protected NodeStats getNodeStats(IEnergyTile energyTile) {
/* 337 */     BlockPos coords = EnergyNet.instance.getPos(energyTile);
/* 338 */     Tile tile = this.registeredTiles.get(coords);
/* 339 */     if (tile == null) {
/* 340 */       logWarn("EnergyNet.getTotalEnergySunken: " + energyTile + " is not added to the enet");
/* 341 */       return new NodeStats(0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/* 344 */     double in = 0.0D;
/* 345 */     double out = 0.0D;
/* 346 */     double voltage = 0.0D;
/* 347 */     Iterable<NodeStats> stats = tile.getStats();
/*     */     
/* 349 */     for (NodeStats stat : stats) {
/* 350 */       in += stat.getEnergyIn();
/* 351 */       out += stat.getEnergyOut();
/* 352 */       voltage = Math.max(voltage, stat.getVoltage());
/*     */     } 
/*     */     
/* 355 */     return new NodeStats(in, out, voltage);
/*     */   }
/*     */   
/*     */   protected Tile getTile(BlockPos pos) {
/* 359 */     return this.registeredTiles.get(pos);
/*     */   }
/*     */   
/*     */   public boolean dumpDebugInfo(PrintStream console, PrintStream chat, BlockPos pos) {
/* 363 */     Tile tile = this.registeredTiles.get(pos);
/* 364 */     if (tile == null) return false;
/*     */     
/* 366 */     chat.println("Tile " + tile + " info:");
/* 367 */     chat.println(" main: " + tile.mainTile);
/* 368 */     chat.println(" sub: " + tile.subTiles);
/* 369 */     chat.println(" nodes: " + tile.nodes.size());
/*     */     
/* 371 */     Set<Grid> processedGrids = new HashSet<>();
/*     */     
/* 373 */     for (Node node : tile.nodes) {
/* 374 */       Grid grid = node.getGrid();
/*     */       
/* 376 */       if (processedGrids.add(grid)) {
/* 377 */         grid.dumpNodeInfo(chat, true, node);
/* 378 */         grid.dumpStats(chat, true);
/* 379 */         grid.dumpMatrix(console, true, true, true);
/* 380 */         console.println("dumping graph for " + grid);
/* 381 */         grid.dumpGraph(true);
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     return true;
/*     */   }
/*     */   
/*     */   public List<GridInfo> getGridInfos() {
/* 389 */     List<GridInfo> ret = new ArrayList<>();
/*     */     
/* 391 */     for (Grid grid : this.grids) {
/* 392 */       ret.add(grid.getInfo());
/*     */     }
/*     */     
/* 395 */     return ret;
/*     */   }
/*     */   
/*     */   protected void onTickEnd() {
/* 399 */     if (!IC2.platform.isSimulating()) {
/*     */       return;
/*     */     }
/* 402 */     this.locked = true;
/* 403 */     for (Grid grid : this.grids) {
/* 404 */       grid.finishCalculation();
/* 405 */       grid.updateStats();
/*     */     } 
/* 407 */     this.locked = false;
/*     */     
/* 409 */     processChanges();
/*     */ 
/*     */     
/* 412 */     if (!this.pendingAdds.isEmpty()) {
/* 413 */       List<Map.Entry<IEnergyTile, Integer>> pending = new ArrayList<>(this.pendingAdds.entrySet());
/* 414 */       this.pendingAdds.clear();
/*     */       
/* 416 */       for (Map.Entry<IEnergyTile, Integer> entry : pending) {
/* 417 */         addTile(entry.getKey(), ((Integer)entry.getValue()).intValue());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 422 */     this.locked = true;
/*     */     
/* 424 */     for (Grid grid : this.grids) {
/* 425 */       grid.prepareCalculation();
/*     */     }
/*     */     
/* 428 */     List<Runnable> tasks = new ArrayList<>();
/*     */     
/* 430 */     for (Grid grid : this.grids) {
/* 431 */       Runnable task = grid.startCalculation();
/* 432 */       if (task != null) tasks.add(task);
/*     */     
/*     */     } 
/* 435 */     (IC2.getInstance()).threadPool.executeAll(tasks);
/*     */     
/* 437 */     this.locked = false;
/*     */   }
/*     */   
/*     */   protected void addChange(Node node, EnumFacing dir, double amount, double voltage) {
/* 441 */     this.changes.add(new Change(node, dir, amount, voltage));
/*     */   }
/*     */   
/*     */   protected static int getNextGridUid() {
/* 445 */     return nextGridUid++;
/*     */   }
/*     */   
/*     */   protected static int getNextNodeUid() {
/* 449 */     return nextNodeUid++;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTileToGrids(Tile tile) {
/* 454 */     List<Node> extraNodes = new ArrayList<>();
/*     */     
/* 456 */     for (Node node : tile.nodes) {
/* 457 */       Grid grid; List<List<Node>> neighborGroups; Map<Node, Node> neighborReplacements; int i; ListIterator<Node> it; if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Adding node %s.", new Object[] { node });
/*     */ 
/*     */       
/* 460 */       List<Node> neighbors = new ArrayList<>();
/*     */       
/* 462 */       for (IEnergyTile subTile : tile.subTiles) {
/* 463 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 464 */           BlockPos coords = EnergyNet.instance.getPos(subTile).func_177972_a(dir);
/* 465 */           Tile neighborTile = this.registeredTiles.get(coords);
/* 466 */           if (neighborTile != null && neighborTile != node.tile)
/*     */           {
/* 468 */             for (Node neighbor : neighborTile.nodes) {
/* 469 */               if (neighbor.isExtraNode())
/*     */                 continue; 
/* 471 */               boolean canEmit = false;
/*     */               
/* 473 */               if ((node.nodeType == NodeType.Source || node.nodeType == NodeType.Conductor) && neighbor.nodeType != NodeType.Source) {
/*     */ 
/*     */                 
/* 476 */                 IEnergyEmitter emitter = (subTile instanceof IEnergyEmitter) ? (IEnergyEmitter)subTile : (IEnergyEmitter)node.tile.mainTile;
/* 477 */                 IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
/* 478 */                 IEnergyAcceptor acceptor = (neighborSubTe instanceof IEnergyAcceptor) ? (IEnergyAcceptor)neighborSubTe : (IEnergyAcceptor)neighbor.tile.mainTile;
/*     */ 
/*     */ 
/*     */                 
/* 482 */                 canEmit = (emitter.emitsEnergyTo((IEnergyAcceptor)neighbor.tile.mainTile, dir) && acceptor.acceptsEnergyFrom((IEnergyEmitter)node.tile.mainTile, dir.func_176734_d()));
/*     */               } 
/*     */               
/* 485 */               boolean canAccept = false;
/*     */               
/* 487 */               if (!canEmit && (node.nodeType == NodeType.Sink || node.nodeType == NodeType.Conductor) && neighbor.nodeType != NodeType.Sink) {
/*     */ 
/*     */                 
/* 490 */                 IEnergyAcceptor acceptor = (subTile instanceof IEnergyAcceptor) ? (IEnergyAcceptor)subTile : (IEnergyAcceptor)node.tile.mainTile;
/* 491 */                 IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
/* 492 */                 IEnergyEmitter emitter = (neighborSubTe instanceof IEnergyEmitter) ? (IEnergyEmitter)neighborSubTe : (IEnergyEmitter)neighbor.tile.mainTile;
/*     */ 
/*     */                 
/* 495 */                 canAccept = (acceptor.acceptsEnergyFrom((IEnergyEmitter)neighbor.tile.mainTile, dir) && emitter.emitsEnergyTo((IEnergyAcceptor)node.tile.mainTile, dir.func_176734_d()));
/*     */               } 
/*     */               
/* 498 */               if (canEmit || canAccept) {
/* 499 */                 neighbors.add(neighbor);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 505 */       if (neighbors.isEmpty()) {
/* 506 */         if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s.", new Object[] { node }); 
/* 507 */         Grid grid1 = new Grid(this);
/* 508 */         grid1.add(node, neighbors); continue;
/*     */       } 
/* 510 */       switch (node.nodeType) {
/*     */         case Conductor:
/* 512 */           grid = null;
/*     */           
/* 514 */           for (Node neighbor : neighbors) {
/* 515 */             if (neighbor.nodeType == NodeType.Conductor || neighbor.links.isEmpty()) {
/* 516 */               if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Using %s for %s with neighbors %s.", new Object[] { neighbor.getGrid(), node, neighbors }); 
/* 517 */               grid = neighbor.getGrid();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 522 */           if (grid == null) {
/* 523 */             if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s with neighbors %s.", new Object[] { node, neighbors }); 
/* 524 */             grid = new Grid(this);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 529 */           neighborReplacements = new HashMap<>();
/*     */ 
/*     */           
/* 532 */           for (it = neighbors.listIterator(); it.hasNext(); ) {
/* 533 */             Node neighbor = it.next();
/*     */             
/* 535 */             if (neighbor.getGrid() == grid)
/*     */               continue; 
/* 537 */             if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
/*     */               
/* 539 */               boolean found = false;
/*     */               
/* 541 */               for (int j = 0; j < it.previousIndex(); j++) {
/* 542 */                 Node neighbor2 = neighbors.get(j);
/*     */                 
/* 544 */                 if (neighbor2.tile == neighbor.tile && neighbor2.nodeType == neighbor.nodeType && neighbor2
/*     */                   
/* 546 */                   .getGrid() == grid) {
/* 547 */                   if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Using neighbor node %s instead of %s.", new Object[] { neighbor2, neighbors }); 
/* 548 */                   found = true;
/* 549 */                   it.set(neighbor2);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 554 */               if (!found) {
/* 555 */                 if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", new Object[] { neighbor }); 
/* 556 */                 neighbor = new Node(this, neighbor.tile, neighbor.nodeType);
/* 557 */                 neighbor.tile.addExtraNode(neighbor);
/* 558 */                 grid.add(neighbor, Collections.emptyList());
/* 559 */                 it.set(neighbor);
/*     */                 
/* 561 */                 assert neighbor.getGrid() != null;
/*     */               }  continue;
/*     */             } 
/* 564 */             grid.merge(neighbor.getGrid(), neighborReplacements);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 569 */           for (it = neighbors.listIterator(); it.hasNext(); ) {
/* 570 */             Node neighbor = it.next();
/* 571 */             Node replacement = neighborReplacements.get(neighbor);
/*     */             
/* 573 */             if (replacement != null) {
/* 574 */               neighbor = replacement;
/* 575 */               it.set(replacement);
/*     */             } 
/*     */             
/* 578 */             assert neighbor.getGrid() == grid;
/*     */           } 
/*     */           
/* 581 */           grid.add(node, neighbors);
/*     */           
/* 583 */           assert node.getGrid() != null;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case Sink:
/*     */         case Source:
/* 590 */           neighborGroups = new ArrayList<>();
/*     */           
/* 592 */           for (Node neighbor : neighbors) {
/* 593 */             boolean found = false;
/*     */             
/* 595 */             if (node.nodeType == NodeType.Conductor) {
/* 596 */               for (List<Node> nodeList : neighborGroups) {
/* 597 */                 Node neighbor2 = nodeList.get(0);
/*     */                 
/* 599 */                 if (neighbor2.nodeType == NodeType.Conductor && neighbor2
/* 600 */                   .getGrid() == neighbor.getGrid()) {
/* 601 */                   nodeList.add(neighbor);
/* 602 */                   found = true;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/* 608 */             if (!found) {
/* 609 */               List<Node> nodeList = new ArrayList<>();
/* 610 */               nodeList.add(neighbor);
/* 611 */               neighborGroups.add(nodeList);
/*     */             } 
/*     */           } 
/*     */           
/* 615 */           if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Neighbor groups detected for %s: %s.", new Object[] { node, neighborGroups }); 
/* 616 */           assert !neighborGroups.isEmpty();
/*     */           
/* 618 */           for (i = 0; i < neighborGroups.size(); i++) {
/* 619 */             Node currentNode; List<Node> nodeList = neighborGroups.get(i);
/* 620 */             Node neighbor = nodeList.get(0);
/*     */ 
/*     */             
/* 623 */             if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
/* 624 */               assert nodeList.size() == 1;
/* 625 */               if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", new Object[] { neighbor });
/*     */               
/* 627 */               neighbor = new Node(this, neighbor.tile, neighbor.nodeType);
/* 628 */               neighbor.tile.addExtraNode(neighbor);
/* 629 */               (new Grid(this)).add(neighbor, Collections.emptyList());
/* 630 */               nodeList.set(0, neighbor);
/*     */               
/* 632 */               assert neighbor.getGrid() != null;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 637 */             if (i == 0) {
/* 638 */               currentNode = node;
/*     */             } else {
/* 640 */               if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for %s.", new Object[] { node });
/*     */               
/* 642 */               currentNode = new Node(this, tile, node.nodeType);
/* 643 */               currentNode.setExtraNode(true);
/* 644 */               extraNodes.add(currentNode);
/*     */             } 
/*     */             
/* 647 */             neighbor.getGrid().add(currentNode, nodeList);
/*     */             
/* 649 */             assert currentNode.getGrid() != null;
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 658 */     for (Node node : extraNodes) {
/* 659 */       tile.addExtraNode(node);
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeTileFromGrids(Tile tile) {
/* 664 */     for (Node node : tile.nodes) {
/* 665 */       node.getGrid().remove(node);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void processChanges() {
/* 671 */     for (Tile tile : this.removedTiles) {
/* 672 */       for (Iterator<Change> it = this.changes.iterator(); it.hasNext(); ) {
/* 673 */         Change change = it.next();
/*     */         
/* 675 */         if (change.node.tile != tile) {
/*     */           continue;
/*     */         }
/* 678 */         Tile replacement = this.registeredTiles.get(EnergyNet.instance.getPos(change.node.tile.mainTile));
/* 679 */         boolean validReplacement = false;
/*     */         
/* 681 */         if (replacement != null) {
/* 682 */           for (Node node : replacement.nodes) {
/* 683 */             if (node.nodeType == change.node.nodeType && node.getGrid() == change.node.getGrid()) {
/* 684 */               if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Redirecting change %s to replacement node %s.", new Object[] { change, node }); 
/* 685 */               change.node = node;
/* 686 */               validReplacement = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/* 693 */         if (validReplacement) {
/*     */           continue;
/*     */         }
/* 696 */         it.remove();
/*     */         
/* 698 */         List<Change> sameGridSourceChanges = new ArrayList<>();
/*     */         
/* 700 */         for (Change change2 : this.changes) {
/* 701 */           if (change2.node.nodeType == NodeType.Source && 
/* 702 */             change.node.getGrid() == change2.node.getGrid()) {
/* 703 */             sameGridSourceChanges.add(change2);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 708 */         if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Redistributing change %s to remaining source nodes %s.", new Object[] { change, sameGridSourceChanges });
/*     */         
/* 710 */         for (Change change2 : sameGridSourceChanges) {
/* 711 */           change2.setAmount(change2.getAmount() - Math.abs(change.getAmount()) / sameGridSourceChanges.size());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 716 */     this.removedTiles.clear();
/*     */ 
/*     */     
/* 719 */     for (Change change : this.changes) {
/* 720 */       if (change.node.nodeType == NodeType.Sink) {
/* 721 */         assert change.getAmount() > 0.0D;
/*     */         
/* 723 */         IEnergySink sink = (IEnergySink)change.node.tile.mainTile;
/* 724 */         double returned = sink.injectEnergy(change.dir, change.getAmount(), change.getVoltage());
/* 725 */         if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Applied change %s, %f EU returned.", new Object[] { change, Double.valueOf(returned) });
/*     */         
/* 727 */         if (returned > 0.0D) {
/* 728 */           List<Change> sameGridSourceChanges = new ArrayList<>();
/*     */           
/* 730 */           for (Change change2 : this.changes) {
/* 731 */             if (change2.node.nodeType == NodeType.Source && 
/* 732 */               change.node.getGrid() == change2.node.getGrid()) {
/* 733 */               sameGridSourceChanges.add(change2);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 738 */           if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Redistributing returned amount to source nodes %s.", new Object[] { sameGridSourceChanges });
/*     */           
/* 740 */           for (Change change2 : sameGridSourceChanges) {
/* 741 */             change2.setAmount(change2.getAmount() - returned / sameGridSourceChanges.size());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 747 */     for (Change change : this.changes) {
/* 748 */       if (change.node.nodeType == NodeType.Source) {
/* 749 */         assert change.getAmount() <= 0.0D;
/*     */         
/* 751 */         if (change.getAmount() >= 0.0D)
/*     */           continue; 
/* 753 */         IEnergySource source = (IEnergySource)change.node.tile.mainTile;
/* 754 */         source.drawEnergy(change.getAmount());
/*     */         
/* 756 */         if (EnergyNetGlobal.debugGrid) IC2.log.debug(LogCategory.EnergyNet, "Applied change %s.", new Object[] { change });
/*     */       
/*     */       } 
/*     */     } 
/* 760 */     this.changes.clear();
/*     */   }
/*     */   
/*     */   private void logDebug(String msg) {
/* 764 */     if (!shouldLog(msg))
/*     */       return; 
/* 766 */     IC2.log.debug(LogCategory.EnergyNet, msg);
/*     */     
/* 768 */     if (EnergyNetGlobal.debugTileManagement) {
/* 769 */       IC2.log.debug(LogCategory.EnergyNet, new Throwable(), "stack trace");
/* 770 */       if (TickHandler.getLastDebugTrace() != null) IC2.log.debug(LogCategory.EnergyNet, TickHandler.getLastDebugTrace(), "parent stack trace"); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void logWarn(String msg) {
/* 775 */     if (!shouldLog(msg))
/*     */       return; 
/* 777 */     IC2.log.warn(LogCategory.EnergyNet, msg);
/*     */     
/* 779 */     if (EnergyNetGlobal.debugTileManagement) {
/* 780 */       IC2.log.debug(LogCategory.EnergyNet, new Throwable(), "stack trace");
/* 781 */       if (TickHandler.getLastDebugTrace() != null) IC2.log.debug(LogCategory.EnergyNet, TickHandler.getLastDebugTrace(), "parent stack trace"); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldLog(String msg) {
/* 786 */     if (EnergyNetGlobal.logAll) return true;
/*     */     
/* 788 */     cleanRecentLogs();
/*     */     
/* 790 */     msg = msg.replaceAll("@[0-9a-f]+", "@x");
/* 791 */     long time = System.nanoTime();
/*     */     
/* 793 */     Long lastLog = this.recentLogs.put(msg, Long.valueOf(time));
/*     */     
/* 795 */     return (lastLog == null || lastLog.longValue() < time - 300000000000L);
/*     */   }
/*     */   
/*     */   private void cleanRecentLogs() {
/* 799 */     if (this.recentLogs.size() < 100)
/*     */       return; 
/* 801 */     long minTime = System.nanoTime() - 300000000000L;
/*     */     
/* 803 */     for (Iterator<Long> it = this.recentLogs.values().iterator(); it.hasNext(); ) {
/* 804 */       long recTime = ((Long)it.next()).longValue();
/*     */       
/* 806 */       if (recTime < minTime) it.remove(); 
/*     */     } 
/*     */   }
/*     */   
/* 810 */   private static int nextGridUid = 0;
/* 811 */   private static int nextNodeUid = 0;
/*     */ 
/*     */ 
/*     */   
/* 815 */   protected final Set<Grid> grids = new HashSet<>();
/* 816 */   protected List<Change> changes = new ArrayList<>();
/*     */   
/* 818 */   private final Map<BlockPos, Tile> registeredTiles = new HashMap<>();
/* 819 */   private final Map<IEnergyTile, Integer> pendingAdds = new WeakHashMap<>();
/* 820 */   private final Set<Tile> removedTiles = new HashSet<>();
/*     */   
/*     */   private boolean locked = false;
/*     */   
/* 824 */   private final Map<String, Long> recentLogs = new HashMap<>();
/*     */   public static final double nonConductorResistance = 0.2D;
/*     */   public static final double sourceResistanceFactor = 0.0625D;
/*     */   public static final double sinkResistanceFactor = 1.0D;
/*     */   public static final double sourceCurrent = 17.0D;
/*     */   public static final boolean enableCache = true;
/*     */   private static final long logSuppressionTimeout = 300000000000L;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\EnergyNetLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */