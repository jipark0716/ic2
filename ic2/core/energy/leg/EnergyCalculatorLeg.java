/*     */ package ic2.core.energy.leg;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IExplosionPowerOverride;
/*     */ import ic2.api.energy.tile.IMultiEnergySource;
/*     */ import ic2.api.energy.tile.IOverloadHandler;
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2DamageSource;
/*     */ import ic2.core.energy.grid.EnergyNetLocal;
/*     */ import ic2.core.energy.grid.EnergyNetSettings;
/*     */ import ic2.core.energy.grid.Grid;
/*     */ import ic2.core.energy.grid.IEnergyCalculator;
/*     */ import ic2.core.energy.grid.Node;
/*     */ import ic2.core.energy.grid.NodeLink;
/*     */ import ic2.core.energy.grid.NodeType;
/*     */ import ic2.core.energy.grid.Tile;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.mutable.MutableDouble;
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
/*     */ public class EnergyCalculatorLeg
/*     */   implements IEnergyCalculator
/*     */ {
/*     */   public void handleGridChange(Grid grid) {
/*  66 */     long startTime = 0L;
/*     */     
/*  68 */     GridData data = getData(grid);
/*  69 */     updateCache(grid, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean runSyncStep(EnergyNetLocal enet) {
/*  78 */     boolean foundAny = false;
/*     */     
/*  80 */     for (Tile tile : enet.getSources()) {
/*  81 */       IEnergySource source = (IEnergySource)tile.getMainTile();
/*     */ 
/*     */       
/*  84 */       int packets = 1; IMultiEnergySource multiSource;
/*     */       double amount;
/*  86 */       if (!tile.isDisabled() && (
/*  87 */         amount = source.getOfferedEnergy()) > 0.0D && (!(source instanceof IMultiEnergySource) || 
/*  88 */         !(multiSource = (IMultiEnergySource)source).sendMultipleEnergyPackets() || (packets = multiSource.getMultipleEnergyPacketAmount()) > 0)) {
/*  89 */         int tier = source.getSourceTier();
/*     */         
/*  91 */         if (tier < 0) {
/*  92 */           if (EnergyNetSettings.logGridCalculationIssues) {
/*  93 */             IC2.log.warn(LogCategory.EnergyNet, "Tile %s reported an invalid tier (%d).", new Object[] { Util.toString(source, (IBlockAccess)enet.getWorld(), EnergyNet.instance.getPos((IEnergyTile)source)), Integer.valueOf(tier) });
/*     */           }
/*     */           
/*  96 */           tile.setSourceData(0.0D, 0);
/*     */           
/*     */           continue;
/*     */         } 
/* 100 */         foundAny = true;
/* 101 */         double power = EnergyNet.instance.getPowerFromTier(tier);
/* 102 */         amount = Math.min(amount, power * packets);
/* 103 */         tile.setSourceData(amount, packets); continue;
/*     */       } 
/* 105 */       tile.setSourceData(0.0D, 0);
/*     */     } 
/*     */ 
/*     */     
/* 109 */     return foundAny;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean runSyncStep(Grid grid) {
/* 114 */     long startTime = 0L;
/*     */     
/* 116 */     if (runCalculation(grid, getData(grid)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAsyncStep(Grid grid) {
/* 127 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public NodeStats getNodeStats(Tile tile) {
/* 132 */     double in = 0.0D;
/* 133 */     double out = 0.0D;
/* 134 */     double max = 0.0D;
/*     */     
/* 136 */     for (Node node : tile.getNodes()) {
/* 137 */       GridData data = (GridData)node.getGrid().getData();
/* 138 */       if (data == null || !data.active)
/*     */         continue; 
/* 140 */       int calcId = data.currentCalcId;
/* 141 */       Collection<EnergyPath> paths = getPaths(node, data);
/* 142 */       double sum = 0.0D;
/*     */       
/* 144 */       for (EnergyPath path : paths) {
/* 145 */         if (path.lastCalcId != calcId)
/*     */           continue; 
/* 147 */         sum += path.energySupplied;
/* 148 */         max = Math.max(path.maxPacketConducted, max);
/*     */       } 
/*     */       
/* 151 */       if (node.getType() == NodeType.Source) {
/* 152 */         out += sum; continue;
/* 153 */       }  if (node.getType() == NodeType.Sink) {
/* 154 */         in += sum; continue;
/*     */       } 
/* 156 */       in += sum;
/* 157 */       out += sum;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     return new NodeStats(in, out, EnergyNet.instance.getTierFromPower(max));
/*     */   }
/*     */   
/*     */   private static Collection<EnergyPath> getPaths(Node node, GridData data) {
/* 165 */     if (node.getType() == NodeType.Source) {
/* 166 */       List<EnergyPath> list = data.energySourceToEnergyPathMap.get(node);
/* 167 */       if (list == null) list = Collections.emptyList();
/*     */       
/* 169 */       return list;
/*     */     } 
/* 171 */     List<EnergyPath> ret = data.pathCache.get(node);
/* 172 */     if (ret != null) return ret;
/*     */     
/* 174 */     ret = new ArrayList<>();
/*     */     
/* 176 */     for (List<EnergyPath> paths : data.energySourceToEnergyPathMap.values()) {
/* 177 */       for (EnergyPath path : paths) {
/* 178 */         if (node.getType() == NodeType.Sink) {
/* 179 */           if (path.target == node) ret.add(path);  continue;
/*     */         } 
/* 181 */         if (path.conductors.contains(node)) ret.add(path);
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     data.pathCache.put(node, ret);
/*     */     
/* 188 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpNodeInfo(Node node, String prefix, PrintStream console, PrintStream chat) {
/* 194 */     GridData data = getData(node.getGrid());
/* 195 */     Collection<EnergyPath> paths = getPaths(node, data);
/*     */     
/* 197 */     switch (node.getType()) {
/*     */       case Source:
/* 199 */         chat.printf("%s%d connected sink nodes%n", new Object[] { prefix, Integer.valueOf(paths.size()) });
/*     */         break;
/*     */       case Sink:
/* 202 */         chat.printf("%s%d connected source nodes%n", new Object[] { prefix, Integer.valueOf(paths.size()) });
/*     */         break;
/*     */       case Conductor:
/* 205 */         chat.printf("%s%d paths across this conductor%n", new Object[] { prefix, Integer.valueOf(paths.size()) });
/*     */         break;
/*     */     } 
/*     */     
/* 209 */     double sum = 0.0D;
/* 210 */     double max = 0.0D;
/* 211 */     int calcId = data.currentCalcId;
/* 212 */     int pathsToPrint = 8;
/* 213 */     int n = 0;
/*     */     
/* 215 */     for (EnergyPath path : paths) {
/* 216 */       boolean printPathEnergy = false;
/*     */       
/* 218 */       if (n < 8) {
/* 219 */         switch (node.getType()) {
/*     */           case Source:
/* 221 */             chat.printf("%s %s", new Object[] { prefix, path.target });
/*     */             break;
/*     */           case Sink:
/* 224 */             chat.printf("%s %s", new Object[] { prefix, path.source });
/*     */             break;
/*     */           case Conductor:
/* 227 */             chat.printf("%s %s -> %s", new Object[] { prefix, path.source, path.target });
/*     */             break;
/*     */         } 
/*     */         
/* 231 */         printPathEnergy = true;
/* 232 */       } else if (n == 8) {
/* 233 */         chat.printf("%s ... (%d more)%n", new Object[] { Integer.valueOf(paths.size() - 8) });
/*     */       } 
/*     */       
/* 236 */       n++;
/*     */       
/* 238 */       if (path.lastCalcId != calcId) {
/* 239 */         if (printPathEnergy) chat.println(" (idle)"); 
/*     */         continue;
/*     */       } 
/* 242 */       if (printPathEnergy) chat.printf(" (%.2f EU, max packet %.2f EU)%n", new Object[] { Double.valueOf(path.energySupplied), Double.valueOf(path.maxPacketConducted) });
/*     */ 
/*     */       
/* 245 */       sum += path.energySupplied;
/* 246 */       max = Math.max(path.maxPacketConducted, max);
/*     */     } 
/*     */     
/* 249 */     chat.printf("%slast tick: %.2f EU, max packet %.2f EU%n", new Object[] { prefix, Double.valueOf(sum), Double.valueOf(max) });
/*     */   }
/*     */   
/*     */   private static void updateCache(Grid grid, GridData data) {
/*     */     Queue<Node> queue;
/* 254 */     data.active = false;
/* 255 */     data.energySourceToEnergyPathMap.clear();
/* 256 */     data.activeSources.clear();
/* 257 */     data.activeSinks.clear();
/* 258 */     data.pathCache.clear();
/* 259 */     data.currentCalcId = -1;
/*     */ 
/*     */     
/* 262 */     Collection<Node> nodes = grid.getNodes();
/* 263 */     if (nodes.size() < 2)
/*     */       return; 
/* 265 */     List<Node> sources = new ArrayList<>();
/* 266 */     int sinkCount = 0;
/*     */     
/* 268 */     for (Node node : nodes) {
/* 269 */       if (node.getType() == NodeType.Source) {
/* 270 */         sources.add(node); continue;
/* 271 */       }  if (node.getType() == NodeType.Sink) {
/* 272 */         sinkCount++;
/*     */       }
/*     */     } 
/*     */     
/* 276 */     if (sources.isEmpty() || sinkCount == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     Map<Node, Node> path = new IdentityHashMap<>();
/*     */     
/* 285 */     final Map<Node, Double> lossMap = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */     
/* 289 */     if (sources.size() <= 2048) {
/* 290 */       queue = new PriorityQueue<>(nodes.size(), new Comparator<Node>()
/*     */           {
/*     */             public int compare(Node a, Node b) {
/* 293 */               return ((Double)lossMap.get(a)).compareTo((Double)lossMap.get(b));
/*     */             }
/*     */           });
/*     */     } else {
/* 297 */       queue = new ArrayDeque<>(nodes.size());
/*     */     } 
/*     */ 
/*     */     
/* 301 */     Map<IEnergyTile, EnergyPath> paths = new LinkedHashMap<>();
/*     */     
/* 303 */     for (Node srcNode : sources) {
/* 304 */       lossMap.put(srcNode, Double.valueOf(0.0D));
/* 305 */       queue.add(srcNode);
/*     */       
/*     */       Node node;
/* 308 */       while ((node = queue.poll()) != null) {
/* 309 */         if (node.getType() == NodeType.Sink)
/* 310 */         { double loss = ((Double)lossMap.get(node)).doubleValue();
/* 311 */           IEnergyTile tile = node.getTile().getMainTile();
/* 312 */           EnergyPath prev = paths.get(tile);
/* 313 */           if (prev != null && prev.loss <= loss)
/*     */             continue; 
/* 315 */           if (EnergyNetSettings.roundLossDown) loss = Math.floor(loss);
/*     */           
/* 317 */           paths.put(tile, new EnergyPath(srcNode, node, reconstructPath(srcNode, node, path), loss));
/* 318 */           if (paths.size() == sinkCount)
/* 319 */             break;  continue; }  if (node.getType() == NodeType.Conductor || node == srcNode) {
/* 320 */           double loss = ((Double)lossMap.get(node)).doubleValue();
/*     */           
/* 322 */           for (NodeLink link : node.getLinks()) {
/* 323 */             Node neighbor = link.getNeighbor(node);
/*     */             
/* 325 */             if (neighbor.getType() == NodeType.Source) {
/* 326 */               List<EnergyPath> srcPaths = data.energySourceToEnergyPathMap.get(neighbor);
/*     */               
/* 328 */               if (srcPaths != null) {
/* 329 */                 if (!srcPaths.isEmpty()) {
/* 330 */                   loss -= link.getLoss();
/* 331 */                   List<Node> pathToHere = null;
/*     */                   
/* 333 */                   for (EnergyPath cPath : srcPaths) {
/* 334 */                     double cLoss = loss + cPath.loss;
/* 335 */                     IEnergyTile tile = cPath.target.getTile().getMainTile();
/* 336 */                     EnergyPath prev = paths.get(tile);
/* 337 */                     if (prev != null && prev.loss <= cLoss)
/*     */                       continue; 
/* 339 */                     if (EnergyNetSettings.roundLossDown) cLoss = Math.floor(cLoss);
/*     */                     
/* 341 */                     if (pathToHere == null) pathToHere = reconstructPath(srcNode, node, path);
/*     */                     
/* 343 */                     List<Node> conductors = new ArrayList<>(pathToHere.size() + cPath.conductors.size());
/* 344 */                     conductors.addAll(pathToHere);
/* 345 */                     conductors.addAll(cPath.conductors);
/*     */                     
/* 347 */                     paths.put(tile, new EnergyPath(srcNode, cPath.target, conductors, cLoss));
/*     */                   } 
/*     */                 } 
/*     */                 break;
/*     */               } 
/*     */               continue;
/*     */             } 
/* 354 */             double newLoss = loss + link.getLoss();
/* 355 */             Double prevLoss = lossMap.get(neighbor);
/*     */             
/* 357 */             if (prevLoss == null || prevLoss.doubleValue() > newLoss) {
/* 358 */               if (prevLoss != null) queue.remove(neighbor);
/*     */               
/* 360 */               lossMap.put(neighbor, Double.valueOf(newLoss));
/* 361 */               path.put(neighbor, node);
/*     */               
/* 363 */               queue.add(neighbor);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 370 */       if (!paths.isEmpty()) {
/* 371 */         data.energySourceToEnergyPathMap.put(srcNode, new ArrayList<>(paths.values()));
/*     */       }
/*     */       
/* 374 */       lossMap.clear();
/* 375 */       path.clear();
/* 376 */       paths.clear();
/* 377 */       queue.clear();
/*     */     } 
/*     */     
/* 380 */     if (!data.energySourceToEnergyPathMap.isEmpty()) {
/* 381 */       data.active = true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static List<Node> reconstructPath(Node srcNode, Node dstNode, Map<Node, Node> path) {
/* 386 */     List<Node> ret = new ArrayList<>();
/* 387 */     Node node = dstNode;
/*     */     
/* 389 */     while ((node = path.get(node)) != srcNode) {
/* 390 */       assert node != null;
/* 391 */       ret.add(node);
/*     */     } 
/*     */     
/* 394 */     Collections.reverse(ret);
/*     */     
/* 396 */     return ret;
/*     */   }
/*     */   private static boolean runCalculation(Grid grid, GridData data) {
/*     */     int sourcesOffset;
/* 400 */     if (!data.active) return false;
/*     */     
/* 402 */     List<Node> activeSources = data.activeSources;
/* 403 */     Map<Node, MutableDouble> activeSinks = data.activeSinks;
/*     */     
/* 405 */     activeSources.clear();
/* 406 */     activeSinks.clear();
/* 407 */     int calcId = ++data.currentCalcId;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     for (Node node : grid.getNodes()) {
/* 413 */       Tile tile = node.getTile();
/* 414 */       if (tile.isDisabled()) {
/*     */         continue;
/*     */       }
/*     */       
/* 418 */       if (node.getType() == NodeType.Source && data.energySourceToEnergyPathMap
/* 419 */         .containsKey(node) && tile
/* 420 */         .getAmount() > 0.0D) {
/* 421 */         activeSources.add(node); continue;
/*     */       }  double amount;
/* 423 */       if (node.getType() == NodeType.Sink && (
/* 424 */         amount = ((IEnergySink)tile.getMainTile()).getDemandedEnergy()) > 0.0D) {
/* 425 */         activeSinks.put(node, new MutableDouble(amount));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 430 */     if (activeSources.isEmpty() || activeSinks.isEmpty()) return false;
/*     */     
/* 432 */     World world = grid.getEnergyNet().getWorld();
/* 433 */     Random rand = world.field_73012_v;
/* 434 */     boolean shufflePaths = ((world.func_82737_E() & 0x3L) != 0L);
/*     */ 
/*     */     
/* 437 */     if (activeSources.size() > 1) {
/* 438 */       sourcesOffset = rand.nextInt(activeSources.size());
/*     */     } else {
/* 440 */       sourcesOffset = 0;
/*     */     } 
/*     */     int i;
/* 443 */     for (i = sourcesOffset; i < activeSources.size() && !activeSinks.isEmpty(); i++) {
/* 444 */       distribute(activeSources.get(i), data, shufflePaths, calcId, rand);
/*     */     }
/*     */     
/* 447 */     for (i = 0; i < sourcesOffset && !activeSinks.isEmpty(); i++) {
/* 448 */       distribute(activeSources.get(i), data, shufflePaths, calcId, rand);
/*     */     }
/*     */     
/* 451 */     if (!data.eventPaths.isEmpty()) {
/* 452 */       applyCableEffects(data.eventPaths, grid.getEnergyNet().getWorld());
/* 453 */       data.eventPaths.clear();
/*     */     } 
/*     */     
/* 456 */     return true;
/*     */   } private static void distribute(Node srcNode, GridData data, boolean shufflePaths, int calcId, Random rand) {
/*     */     int pathOffset;
/*     */     double offer;
/* 460 */     Tile tile = srcNode.getTile();
/* 461 */     int packetCount = tile.getPacketCount();
/* 462 */     assert packetCount > 0;
/*     */     
/* 464 */     List<EnergyPath> paths = data.energySourceToEnergyPathMap.get(srcNode);
/*     */ 
/*     */     
/* 467 */     if (paths.size() > 1 && shufflePaths) {
/* 468 */       pathOffset = rand.nextInt(paths.size());
/*     */     } else {
/* 470 */       pathOffset = 0;
/*     */     } 
/*     */     
/* 473 */     double totalOffer = tile.getAmount();
/* 474 */     assert totalOffer > 0.0D;
/*     */ 
/*     */     
/* 477 */     if (packetCount == 1) {
/* 478 */       offer = distributeSingle(totalOffer, tile, paths, pathOffset, data, calcId);
/*     */     } else {
/* 480 */       offer = distributeMultiple(totalOffer, tile, paths, pathOffset, data, calcId, packetCount);
/*     */     } 
/*     */     
/* 483 */     double used = totalOffer - Math.max(0.0D, offer);
/*     */     
/* 485 */     if (used > 0.0D) {
/* 486 */       tile.setAmount(offer);
/* 487 */       ((IEnergySource)tile.getMainTile()).drawEnergy(used);
/*     */     } 
/*     */   }
/*     */   private static double distributeSingle(double offer, Tile tile, List<EnergyPath> paths, int pathOffset, GridData data, int calcId) {
/*     */     int i;
/* 492 */     for (i = pathOffset; i < paths.size(); i++) {
/* 493 */       offer -= emit(paths.get(i), offer, data, calcId);
/* 494 */       if (offer <= 0.0D)
/*     */         break; 
/*     */     } 
/* 497 */     for (i = 0; i < pathOffset && offer > 0.0D; i++) {
/* 498 */       offer -= emit(paths.get(i), offer, data, calcId);
/*     */     }
/*     */     
/* 501 */     return offer;
/*     */   }
/*     */   
/*     */   private static double distributeMultiple(double offer, Tile tile, List<EnergyPath> paths, int pathOffset, GridData data, int calcId, int packetCount) {
/* 505 */     IEnergySource source = (IEnergySource)tile.getMainTile();
/* 506 */     double power = EnergyNet.instance.getPowerFromTier(source.getSourceTier());
/*     */     
/*     */     do {
/* 509 */       double cOffer = Math.min(offer, power);
/* 510 */       double used = cOffer - distributeSingle(cOffer, tile, paths, pathOffset, data, calcId);
/* 511 */       if (used <= 0.0D)
/*     */         break; 
/* 513 */       offer -= used;
/* 514 */     } while (--packetCount > 0 && offer > 0.0D);
/*     */     
/* 516 */     return offer;
/*     */   }
/*     */   
/*     */   private static double emit(EnergyPath path, double offer, GridData data, int calcId) {
/* 520 */     Tile targetTile = path.target.getTile();
/* 521 */     if (targetTile.isDisabled()) return 0.0D;
/*     */     
/* 523 */     double injectAmount = offer - path.loss;
/* 524 */     if (injectAmount <= 0.0D) return 0.0D;
/*     */     
/* 526 */     MutableDouble sinkDemand = data.activeSinks.get(path.target);
/* 527 */     if (sinkDemand == null) return 0.0D;
/*     */     
/* 529 */     IEnergySink sink = (IEnergySink)targetTile.getMainTile();
/* 530 */     double amount = Math.min(injectAmount, sinkDemand.doubleValue());
/*     */     
/* 532 */     double rejected = sink.injectEnergy(path.targetDirection, amount, EnergyNet.instance.getTierFromPower(amount));
/* 533 */     if (rejected >= amount) return 0.0D;
/*     */     
/* 535 */     double effectiveAmount = Math.max(0.0D, amount - rejected + path.loss);
/*     */     
/* 537 */     if (path.lastCalcId != calcId) {
/* 538 */       path.lastCalcId = calcId;
/* 539 */       path.energySupplied = 0.0D;
/* 540 */       path.maxPacketConducted = 0.0D;
/*     */     } 
/*     */     
/* 543 */     path.energySupplied += amount - rejected;
/* 544 */     path.maxPacketConducted = Math.max(effectiveAmount, path.maxPacketConducted);
/*     */     
/* 546 */     if (effectiveAmount > path.minEffectEnergy || amount > EnergyNet.instance.getPowerFromTier(sink.getSinkTier())) {
/* 547 */       data.eventPaths.add(path);
/*     */     }
/*     */     
/* 550 */     if (amount >= sinkDemand.doubleValue() || rejected > 0.0D) {
/* 551 */       data.activeSinks.remove(path.target);
/*     */     }
/*     */     
/* 554 */     return effectiveAmount;
/*     */   }
/*     */   
/*     */   private static void applyCableEffects(Collection<EnergyPath> eventPaths, World world) {
/* 558 */     if (!MainConfig.get().get("misc/enableEnetCableMeltdown").getBool()) {
/*     */       return;
/*     */     }
/* 561 */     Set<Tile> cablesToRemove = Collections.newSetFromMap(new IdentityHashMap<>());
/* 562 */     Set<Tile> cablesToStrip = Collections.newSetFromMap(new IdentityHashMap<>());
/* 563 */     Map<Tile, MutableDouble> sinksToExplode = new IdentityHashMap<>();
/* 564 */     Map<EntityLivingBase, MutableDouble> shockEnergyMap = new IdentityHashMap<>();
/*     */     
/* 566 */     for (EnergyPath path : eventPaths) {
/* 567 */       double amount = path.maxPacketConducted;
/*     */ 
/*     */       
/* 570 */       boolean conductorOverload = false;
/*     */       
/* 572 */       if (amount > path.minConductorBreakdownEnergy || amount > path.minInsulationBreakdownEnergy) {
/* 573 */         conductorOverload = true;
/* 574 */         for (Node node : path.conductors) {
/* 575 */           Tile tile = node.getTile();
/* 576 */           IEnergyConductor conductor = (IEnergyConductor)tile.getMainTile();
/*     */           
/* 578 */           if (amount > conductor.getConductorBreakdownEnergy()) {
/* 579 */             cablesToRemove.add(tile); continue;
/* 580 */           }  if (amount > conductor.getInsulationBreakdownEnergy()) {
/* 581 */             cablesToStrip.add(tile);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 587 */       if (amount > path.minInsulationEnergyAbsorption) {
/*     */         
/* 589 */         List<EntityLivingBase> nearbyEntities = world.func_72872_a(EntityLivingBase.class, new AxisAlignedBB((path.minX - 1), (path.minY - 1), (path.minZ - 1), (path.maxX + 2), (path.maxY + 2), (path.maxZ + 2)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 597 */         if (!nearbyEntities.isEmpty()) {
/* 598 */           Map<EntityLivingBase, MutableDouble> localShockEnergyMap = new IdentityHashMap<>();
/*     */           
/* 600 */           for (Node node : path.conductors) {
/* 601 */             Tile tile = node.getTile();
/* 602 */             IEnergyConductor conductor = (IEnergyConductor)tile.getMainTile();
/* 603 */             if (amount <= conductor.getInsulationEnergyAbsorption())
/*     */               continue; 
/* 605 */             int shockEnergy = (int)(amount - conductor.getInsulationEnergyAbsorption());
/*     */             
/* 607 */             for (IEnergyTile subTile : tile.getSubTiles()) {
/* 608 */               BlockPos pos = EnergyNet.instance.getPos(subTile);
/*     */               
/* 610 */               for (EntityLivingBase entity : nearbyEntities) {
/* 611 */                 MutableDouble prev = localShockEnergyMap.get(entity);
/* 612 */                 if (prev != null && prev.doubleValue() >= shockEnergy)
/*     */                   continue; 
/* 614 */                 if (!entity.func_174813_aQ().func_72326_a(new AxisAlignedBB((pos
/* 615 */                       .func_177958_n() - 1), (pos
/* 616 */                       .func_177956_o() - 1), (pos
/* 617 */                       .func_177952_p() - 1), (pos
/* 618 */                       .func_177958_n() + 2), (pos
/* 619 */                       .func_177956_o() + 2), (pos
/* 620 */                       .func_177952_p() + 2)))) {
/*     */                   continue;
/*     */                 }
/*     */                 
/* 624 */                 if (prev == null) {
/* 625 */                   localShockEnergyMap.put(entity, new MutableDouble(shockEnergy)); continue;
/*     */                 } 
/* 627 */                 prev.setValue(shockEnergy);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 633 */           for (Map.Entry<EntityLivingBase, MutableDouble> entry : localShockEnergyMap.entrySet()) {
/* 634 */             MutableDouble prev = shockEnergyMap.get(entry.getKey());
/*     */             
/* 636 */             if (prev == null) {
/* 637 */               shockEnergyMap.put(entry.getKey(), entry.getValue()); continue;
/*     */             } 
/* 639 */             prev.add(((MutableDouble)entry.getValue()).doubleValue());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 646 */       Tile sinkTile = path.target.getTile();
/* 647 */       IEnergySink sink = (IEnergySink)sinkTile.getMainTile();
/*     */       
/* 649 */       if (!conductorOverload && amount > EnergyNet.instance.getPowerFromTier(sink.getSinkTier())) {
/* 650 */         MutableDouble prev = sinksToExplode.get(sinkTile);
/*     */         
/* 652 */         if (prev == null) {
/* 653 */           sinksToExplode.put(sinkTile, new MutableDouble(amount)); continue;
/* 654 */         }  if (prev.doubleValue() < amount) {
/* 655 */           prev.setValue(amount);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 660 */     cablesToStrip.removeAll(cablesToRemove);
/*     */     
/* 662 */     for (Tile tile : cablesToRemove) {
/* 663 */       ((IEnergyConductor)tile.getMainTile()).removeConductor();
/*     */     }
/*     */     
/* 666 */     for (Tile tile : cablesToStrip) {
/* 667 */       ((IEnergyConductor)tile.getMainTile()).removeInsulation();
/*     */     }
/*     */     
/* 670 */     for (Map.Entry<Tile, MutableDouble> entry : sinksToExplode.entrySet()) {
/* 671 */       explodeTile(world, entry.getKey(), ((MutableDouble)entry.getValue()).doubleValue());
/*     */     }
/*     */     
/* 674 */     for (Map.Entry<EntityLivingBase, MutableDouble> entry : shockEnergyMap.entrySet()) {
/* 675 */       EntityLivingBase target = entry.getKey();
/* 676 */       int damage = (int)Math.ceil(((MutableDouble)entry.getValue()).doubleValue() / 64.0D);
/*     */       
/* 678 */       if (target.func_70089_S() && damage > 0) target.func_70097_a((DamageSource)IC2DamageSource.electricity, damage); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static GridData getData(Grid grid) {
/* 683 */     GridData ret = (GridData)grid.getData();
/*     */     
/* 685 */     if (ret == null) {
/* 686 */       ret = new GridData();
/* 687 */       grid.setData(ret);
/*     */     } 
/*     */     
/* 690 */     return ret;
/*     */   }
/*     */   private static class GridData { boolean active;
/*     */     private GridData() {}
/*     */     
/* 695 */     final Map<Node, List<EnergyPath>> energySourceToEnergyPathMap = new IdentityHashMap<>();
/*     */     
/* 697 */     final List<Node> activeSources = new ArrayList<>();
/* 698 */     final Map<Node, MutableDouble> activeSinks = new IdentityHashMap<>();
/* 699 */     final Set<EnergyPath> eventPaths = Collections.newSetFromMap(new IdentityHashMap<>());
/*     */     
/* 701 */     final Map<Node, List<EnergyPath>> pathCache = new IdentityHashMap<>();
/* 702 */     int currentCalcId = -1; }
/*     */ 
/*     */   
/*     */   private static void explodeTile(World world, Tile tile, double maxPower) {
/* 706 */     if (!MainConfig.get().get("misc/enableEnetExplosions").getBool()) {
/*     */       return;
/*     */     }
/* 709 */     int tier = EnergyNet.instance.getTierFromPower(maxPower);
/*     */     
/* 711 */     for (IEnergyTile subTile : tile.getSubTiles()) {
/* 712 */       IEnergySink mainTile = (IEnergySink)tile.getMainTile();
/* 713 */       BlockPos pos = EnergyNet.instance.getPos(subTile);
/* 714 */       TileEntity realTe = world.func_175625_s(pos);
/*     */       
/* 716 */       if ((mainTile instanceof IOverloadHandler && ((IOverloadHandler)mainTile).onOverload(tier)) || (realTe instanceof IOverloadHandler && ((IOverloadHandler)realTe)
/* 717 */         .onOverload(tier))) {
/*     */         continue;
/*     */       }
/* 720 */       float power = 2.5F;
/*     */       
/* 722 */       if (mainTile instanceof IExplosionPowerOverride) {
/* 723 */         IExplosionPowerOverride override = (IExplosionPowerOverride)mainTile;
/* 724 */         if (!override.shouldExplode())
/*     */           continue; 
/* 726 */         power = override.getExplosionPower(tier, power);
/* 727 */       } else if (realTe instanceof IExplosionPowerOverride) {
/* 728 */         IExplosionPowerOverride override = (IExplosionPowerOverride)realTe;
/* 729 */         if (!override.shouldExplode())
/*     */           continue; 
/* 731 */         power = override.getExplosionPower(tier, power);
/*     */       } 
/*     */       
/* 734 */       EntityPlayer closestPlayer = world.func_184137_a(pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D, 20.0D, false);
/*     */       
/* 736 */       if (closestPlayer != null) {
/* 737 */         IC2.achievements.issueAchievement(closestPlayer, "explodeMachine");
/*     */       }
/*     */       
/* 740 */       world.func_175698_g(pos);
/*     */       
/* 742 */       ExplosionIC2 explosion = new ExplosionIC2(world, null, pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, pos.func_177952_p() + 0.5D, power, 0.75F, ExplosionIC2.Type.Electrical);
/* 743 */       explosion.doExplosion();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\leg\EnergyCalculatorLeg.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */