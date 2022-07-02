/*     */ package ic2.core.energy;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.energy.tile.IMetaDelegate;
/*     */ import ic2.core.energy.grid.NodeType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.math.BlockPos;
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
/*     */ 
/*     */ class Tile
/*     */ {
/*     */   final IEnergyTile mainTile;
/*     */   final List<IEnergyTile> subTiles;
/*     */   final List<Node> nodes;
/*     */   final double maxCurrent;
/*     */   
/*     */   Tile(EnergyNetLocal energyNet, IEnergyTile mainTile) {
/* 106 */     this.nodes = new ArrayList<>();
/*     */     this.mainTile = mainTile;
/*     */     if (mainTile instanceof IMetaDelegate) {
/*     */       this.subTiles = new ArrayList<>(((IMetaDelegate)mainTile).getSubTiles());
/*     */       if (this.subTiles.isEmpty())
/*     */         throw new RuntimeException("Tile " + mainTile + " must return at least 1 sub tile for IMetaDelegate.getSubTiles()."); 
/*     */     } else {
/*     */       this.subTiles = Arrays.asList(new IEnergyTile[] { mainTile });
/*     */     } 
/*     */     if (mainTile instanceof ic2.api.energy.tile.IEnergySource)
/*     */       this.nodes.add(new Node(energyNet, this, NodeType.Source)); 
/*     */     if (mainTile instanceof ic2.api.energy.tile.IEnergySink)
/*     */       this.nodes.add(new Node(energyNet, this, NodeType.Sink)); 
/*     */     if (mainTile instanceof IEnergyConductor) {
/*     */       this.nodes.add(new Node(energyNet, this, NodeType.Conductor));
/*     */       this.maxCurrent = ((IEnergyConductor)mainTile).getConductorBreakdownEnergy();
/*     */     } else {
/*     */       this.maxCurrent = Double.MAX_VALUE;
/*     */     } 
/*     */   }
/*     */   
/*     */   void addExtraNode(Node node) {
/*     */     node.setExtraNode(true);
/*     */     this.nodes.add(node);
/*     */   }
/*     */   
/*     */   boolean removeExtraNode(Node node) {
/*     */     boolean canBeRemoved = false;
/*     */     if (node.isExtraNode()) {
/*     */       canBeRemoved = true;
/*     */     } else {
/*     */       for (Node otherNode : this.nodes) {
/*     */         if (otherNode != node && otherNode.nodeType == node.nodeType && otherNode.isExtraNode()) {
/*     */           otherNode.setExtraNode(false);
/*     */           canBeRemoved = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     if (canBeRemoved) {
/*     */       this.nodes.remove(node);
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   IEnergyTile getSubTileAt(BlockPos pos) {
/*     */     for (IEnergyTile subTile : this.subTiles) {
/*     */       if (EnergyNet.instance.getPos(subTile).equals(pos))
/*     */         return subTile; 
/*     */     } 
/*     */     return null;
/*     */   }
/*     */   
/*     */   Iterable<NodeStats> getStats() {
/*     */     List<NodeStats> ret = new ArrayList<>(this.nodes.size());
/*     */     for (Node node : this.nodes)
/*     */       ret.add(node.getStats()); 
/*     */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\Tile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */