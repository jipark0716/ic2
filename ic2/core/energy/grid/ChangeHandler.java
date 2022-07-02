/*     */ package ic2.core.energy.grid;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.IEnergyNetEventReceiver;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMetaDelegate;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ChangeHandler
/*     */ {
/*     */   static boolean prepareSync(EnergyNetLocal enet, GridChange change) {
/*  33 */     World world = enet.getWorld();
/*  34 */     GridChange.Type type = change.type;
/*  35 */     IEnergyTile ioTile = change.ioTile;
/*  36 */     BlockPos pos = change.pos;
/*     */     
/*  38 */     if (EnergyNet.instance.getWorld(ioTile) != world) {
/*  39 */       if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s had the wrong world in grid update (%s)", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type }); 
/*  40 */       return false;
/*  41 */     }  if (type != GridChange.Type.REMOVAL && !EnergyNet.instance.getPos(ioTile).equals(pos)) {
/*  42 */       if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s has the wrong position in grid update (%s)", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type }); 
/*  43 */       return false;
/*  44 */     }  if (type != GridChange.Type.REMOVAL && !world.func_175667_e(pos)) {
/*  45 */       if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s was unloaded in grid update (%s)", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type }); 
/*  46 */       return false;
/*  47 */     }  if (type != GridChange.Type.REMOVAL && ioTile instanceof TileEntity && ((TileEntity)ioTile).func_145837_r()) {
/*  48 */       if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s was invalidated in grid update (%s)", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type }); 
/*  49 */       return false;
/*     */     } 
/*     */     
/*  52 */     if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Considering tile %s for grid update (%s)", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type });
/*     */     
/*  54 */     if (type == GridChange.Type.ADDITION) {
/*  55 */       if (ioTile instanceof IMetaDelegate) {
/*  56 */         change.subTiles = new ArrayList<>(((IMetaDelegate)ioTile).getSubTiles());
/*  57 */         if (change.subTiles.isEmpty()) throw new RuntimeException(String.format("Tile %s must return at least 1 sub tile for IMetaDelegate.getSubTiles().", new Object[] { Util.toString(ioTile, enet.getWorld(), pos) })); 
/*     */       } else {
/*  59 */         change.subTiles = Arrays.asList(new IEnergyTile[] { ioTile });
/*     */       } 
/*     */     }
/*     */     
/*  63 */     return true;
/*     */   }
/*     */   
/*     */   static void applyAddition(EnergyNetLocal enet, IEnergyTile ioTile, BlockPos pos, List<IEnergyTile> subTiles, Collection<GridChange> pendingChanges) {
/*  67 */     if (enet.registeredIoTiles.containsKey(ioTile)) {
/*  68 */       if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s is already registered", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos) });
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     for (IEnergyTile subTile : subTiles) {
/*  73 */       BlockPos subPos = EnergyNet.instance.getPos(subTile);
/*     */       
/*     */       Tile prev;
/*  76 */       if ((prev = enet.registeredTiles.get(subPos)) != null) {
/*  77 */         IEnergyTile prevIoTile = prev.getMainTile();
/*  78 */         boolean found = false;
/*     */         
/*  80 */         for (Iterator<GridChange> it = pendingChanges.iterator(); it.hasNext(); ) {
/*  81 */           GridChange change = it.next();
/*     */           
/*  83 */           if (change.type == GridChange.Type.REMOVAL && change.ioTile == prevIoTile) {
/*  84 */             if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Expediting pending removal of %s due to addition conflict.", new Object[] { Util.toString(change.ioTile, (IBlockAccess)enet.getWorld(), change.pos) });
/*     */             
/*  86 */             found = true;
/*  87 */             it.remove();
/*  88 */             applyRemoval(enet, change.ioTile, change.pos);
/*  89 */             assert !enet.registeredTiles.containsKey(subPos); break;
/*     */           } 
/*  91 */           if (change.type == GridChange.Type.ADDITION && change.ioTile == prevIoTile) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*  97 */         if (!found) {
/*  98 */           if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s, sub tile %s addition is conflicting with a previous registration at the same location: %s.", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), Util.toString(subTile, (IBlockAccess)enet.getWorld(), subPos), prevIoTile });
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 104 */     if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Adding tile %s.", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos) });
/*     */     
/* 106 */     Tile tile = new Tile(enet, ioTile, subTiles);
/*     */     
/* 108 */     enet.registeredIoTiles.put(ioTile, tile);
/* 109 */     if (ioTile instanceof ic2.api.energy.tile.IEnergySource) enet.sources.add(tile);
/*     */     
/* 111 */     for (IEnergyTile subTile : subTiles) {
/* 112 */       BlockPos subPos = EnergyNet.instance.getPos(subTile);
/*     */       
/* 114 */       enet.registeredTiles.put(subPos, tile);
/* 115 */       enet.addPositionToNotify(subPos);
/*     */     } 
/*     */     
/* 118 */     addTileToGrids(enet, tile);
/*     */     
/* 120 */     for (IEnergyNetEventReceiver receiver : EnergyNetGlobal.getEventReceivers()) {
/* 121 */       receiver.onAdd(ioTile);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addTileToGrids(EnergyNetLocal enet, Tile tile) {
/* 126 */     List<Node> extraNodes = new ArrayList<>();
/* 127 */     IEnergyTile ioTile = tile.getMainTile();
/*     */     
/* 129 */     for (Node node : tile.nodes) {
/* 130 */       Grid grid; List<List<Node>> neighborGroups; Map<Node, Node> neighborReplacements; int i; ListIterator<Node> it; if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Adding node %s.", new Object[] { node });
/*     */ 
/*     */       
/* 133 */       List<Node> neighbors = new ArrayList<>();
/*     */       
/* 135 */       for (IEnergyTile subTile : tile.subTiles) {
/* 136 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 137 */           BlockPos coords = EnergyNet.instance.getPos(subTile).func_177972_a(dir);
/* 138 */           Tile neighborTile = enet.registeredTiles.get(coords);
/* 139 */           if (neighborTile != null && neighborTile != node.tile)
/*     */           {
/* 141 */             for (Node neighbor : neighborTile.nodes) {
/* 142 */               if (neighbor.isExtraNode())
/*     */                 continue; 
/* 144 */               IEnergyTile neighborIoTile = neighbor.tile.getMainTile();
/* 145 */               boolean canEmit = false;
/*     */               
/* 147 */               if ((node.nodeType == NodeType.Source || node.nodeType == NodeType.Conductor) && neighbor.nodeType != NodeType.Source) {
/*     */ 
/*     */                 
/* 150 */                 IEnergyEmitter emitter = (subTile instanceof IEnergyEmitter) ? (IEnergyEmitter)subTile : (IEnergyEmitter)ioTile;
/* 151 */                 IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
/* 152 */                 IEnergyAcceptor acceptor = (neighborSubTe instanceof IEnergyAcceptor) ? (IEnergyAcceptor)neighborSubTe : (IEnergyAcceptor)neighborIoTile;
/*     */ 
/*     */ 
/*     */                 
/* 156 */                 canEmit = (emitter.emitsEnergyTo((IEnergyAcceptor)neighborIoTile, dir) && acceptor.acceptsEnergyFrom((IEnergyEmitter)ioTile, dir.func_176734_d()));
/*     */               } 
/*     */               
/* 159 */               boolean canAccept = false;
/*     */               
/* 161 */               if (!canEmit && (node.nodeType == NodeType.Sink || node.nodeType == NodeType.Conductor) && neighbor.nodeType != NodeType.Sink) {
/*     */ 
/*     */                 
/* 164 */                 IEnergyAcceptor acceptor = (subTile instanceof IEnergyAcceptor) ? (IEnergyAcceptor)subTile : (IEnergyAcceptor)ioTile;
/* 165 */                 IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
/* 166 */                 IEnergyEmitter emitter = (neighborSubTe instanceof IEnergyEmitter) ? (IEnergyEmitter)neighborSubTe : (IEnergyEmitter)neighborIoTile;
/*     */ 
/*     */                 
/* 169 */                 canAccept = (acceptor.acceptsEnergyFrom((IEnergyEmitter)neighborIoTile, dir) && emitter.emitsEnergyTo((IEnergyAcceptor)ioTile, dir.func_176734_d()));
/*     */               } 
/*     */               
/* 172 */               if (canEmit || canAccept) {
/* 173 */                 neighbors.add(neighbor);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/* 179 */       if (neighbors.isEmpty()) {
/* 180 */         if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s.", new Object[] { node }); 
/* 181 */         Grid grid1 = new Grid(enet);
/* 182 */         grid1.add(node, neighbors); continue;
/*     */       } 
/* 184 */       switch (node.nodeType) {
/*     */         case Conductor:
/* 186 */           grid = null;
/*     */           
/* 188 */           for (Node neighbor : neighbors) {
/* 189 */             if (neighbor.nodeType == NodeType.Conductor || neighbor.links.isEmpty()) {
/* 190 */               if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Using %s for %s with neighbors %s.", new Object[] { neighbor.getGrid(), node, neighbors }); 
/* 191 */               grid = neighbor.getGrid();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 196 */           if (grid == null) {
/* 197 */             if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s with neighbors %s.", new Object[] { node, neighbors }); 
/* 198 */             grid = new Grid(enet);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 203 */           neighborReplacements = new HashMap<>();
/*     */ 
/*     */           
/* 206 */           for (it = neighbors.listIterator(); it.hasNext(); ) {
/* 207 */             Node neighbor = it.next();
/*     */             
/* 209 */             if (neighbor.getGrid() == grid)
/*     */               continue; 
/* 211 */             if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
/*     */               
/* 213 */               boolean found = false;
/*     */               
/* 215 */               for (int j = 0; j < it.previousIndex(); j++) {
/* 216 */                 Node neighbor2 = neighbors.get(j);
/*     */                 
/* 218 */                 if (neighbor2.tile == neighbor.tile && neighbor2.nodeType == neighbor.nodeType && neighbor2
/*     */                   
/* 220 */                   .getGrid() == grid) {
/* 221 */                   if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Using neighbor node %s instead of %s.", new Object[] { neighbor2, neighbors }); 
/* 222 */                   found = true;
/* 223 */                   it.set(neighbor2);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/* 228 */               if (!found) {
/* 229 */                 if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", new Object[] { neighbor }); 
/* 230 */                 neighbor = new Node(enet.allocateNodeId(), neighbor.tile, neighbor.nodeType);
/* 231 */                 neighbor.tile.addExtraNode(neighbor);
/* 232 */                 grid.add(neighbor, Collections.emptyList());
/* 233 */                 it.set(neighbor);
/*     */                 
/* 235 */                 assert neighbor.getGrid() != null;
/*     */               }  continue;
/*     */             } 
/* 238 */             grid.merge(neighbor.getGrid(), neighborReplacements);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 243 */           for (it = neighbors.listIterator(); it.hasNext(); ) {
/* 244 */             Node neighbor = it.next();
/* 245 */             Node replacement = neighborReplacements.get(neighbor);
/*     */             
/* 247 */             if (replacement != null) {
/* 248 */               neighbor = replacement;
/* 249 */               it.set(replacement);
/*     */             } 
/*     */             
/* 252 */             assert neighbor.getGrid() == grid;
/*     */           } 
/*     */           
/* 255 */           grid.add(node, neighbors);
/*     */           
/* 257 */           assert node.getGrid() != null;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case Sink:
/*     */         case Source:
/* 264 */           neighborGroups = new ArrayList<>();
/*     */           
/* 266 */           for (Node neighbor : neighbors) {
/* 267 */             boolean found = false;
/*     */             
/* 269 */             if (node.nodeType == NodeType.Conductor) {
/* 270 */               for (List<Node> nodeList : neighborGroups) {
/* 271 */                 Node neighbor2 = nodeList.get(0);
/*     */                 
/* 273 */                 if (neighbor2.nodeType == NodeType.Conductor && neighbor2
/* 274 */                   .getGrid() == neighbor.getGrid()) {
/* 275 */                   nodeList.add(neighbor);
/* 276 */                   found = true;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/* 282 */             if (!found) {
/* 283 */               List<Node> nodeList = new ArrayList<>();
/* 284 */               nodeList.add(neighbor);
/* 285 */               neighborGroups.add(nodeList);
/*     */             } 
/*     */           } 
/*     */           
/* 289 */           if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Neighbor groups detected for %s: %s.", new Object[] { node, neighborGroups }); 
/* 290 */           assert !neighborGroups.isEmpty();
/*     */           
/* 292 */           for (i = 0; i < neighborGroups.size(); i++) {
/* 293 */             Node currentNode; List<Node> nodeList = neighborGroups.get(i);
/* 294 */             Node neighbor = nodeList.get(0);
/*     */ 
/*     */             
/* 297 */             if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
/* 298 */               assert nodeList.size() == 1;
/* 299 */               if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", new Object[] { neighbor });
/*     */               
/* 301 */               neighbor = new Node(enet.allocateNodeId(), neighbor.tile, neighbor.nodeType);
/* 302 */               neighbor.tile.addExtraNode(neighbor);
/* 303 */               (new Grid(enet)).add(neighbor, Collections.emptyList());
/* 304 */               nodeList.set(0, neighbor);
/*     */               
/* 306 */               assert neighbor.getGrid() != null;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 311 */             if (i == 0) {
/* 312 */               currentNode = node;
/*     */             } else {
/* 314 */               if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for %s.", new Object[] { node });
/*     */               
/* 316 */               currentNode = new Node(enet.allocateNodeId(), tile, node.nodeType);
/* 317 */               currentNode.setExtraNode(true);
/* 318 */               extraNodes.add(currentNode);
/*     */             } 
/*     */             
/* 321 */             neighbor.getGrid().add(currentNode, nodeList);
/*     */             
/* 323 */             assert currentNode.getGrid() != null;
/*     */           } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 332 */     for (Node node : extraNodes) {
/* 333 */       tile.addExtraNode(node);
/*     */     }
/*     */   }
/*     */   
/*     */   static void applyRemoval(EnergyNetLocal enet, IEnergyTile ioTile, BlockPos pos) {
/* 338 */     Tile tile = enet.registeredIoTiles.remove(ioTile);
/*     */     
/* 340 */     if (tile == null) {
/* 341 */       if (EnergyNetSettings.logGridUpdateIssues) IC2.log.warn(LogCategory.EnergyNet, "Tile %s removal without registration", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos) });
/*     */       
/*     */       return;
/*     */     } 
/* 345 */     if (EnergyNetSettings.logGridUpdatesVerbose) IC2.log.debug(LogCategory.EnergyNet, "Removing tile %s.", new Object[] { Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos) });
/*     */     
/* 347 */     assert tile.getMainTile() == ioTile;
/* 348 */     if (ioTile instanceof ic2.api.energy.tile.IEnergySource) enet.sources.remove(tile);
/*     */     
/* 350 */     for (IEnergyTile subTile : tile.subTiles) {
/* 351 */       BlockPos subPos = EnergyNet.instance.getPos(subTile);
/*     */       
/* 353 */       enet.registeredTiles.remove(subPos);
/* 354 */       enet.addPositionToNotify(subPos);
/*     */     } 
/*     */     
/* 357 */     removeTileFromGrids(tile);
/*     */     
/* 359 */     for (IEnergyNetEventReceiver receiver : EnergyNetGlobal.getEventReceivers()) {
/* 360 */       receiver.onRemove(ioTile);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void removeTileFromGrids(Tile tile) {
/* 365 */     for (Node node : tile.nodes)
/* 366 */       node.getGrid().remove(node); 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\ChangeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */