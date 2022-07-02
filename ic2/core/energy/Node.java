/*     */ package ic2.core.energy;
/*     */ 
/*     */ import ic2.api.energy.NodeStats;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.energy.grid.NodeType;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ class Node {
/*     */   final int uid;
/*     */   final Tile tile;
/*     */   final NodeType nodeType;
/*     */   private final Node parent;
/*     */   
/*     */   Node(EnergyNetLocal energyNet, Tile tile1, NodeType nodeType1) {
/*  19 */     if (energyNet == null) throw new NullPointerException("The energyNet parameter must not be null."); 
/*  20 */     if (tile1 == null) throw new NullPointerException("The tile parameter must not be null."); 
/*  21 */     assert nodeType1 != NodeType.Conductor || tile1.mainTile instanceof IEnergyConductor;
/*  22 */     assert nodeType1 != NodeType.Sink || tile1.mainTile instanceof ic2.api.energy.tile.IEnergySink;
/*  23 */     assert nodeType1 != NodeType.Source || tile1.mainTile instanceof ic2.api.energy.tile.IEnergySource;
/*     */     
/*  25 */     this.uid = EnergyNetLocal.getNextNodeUid();
/*  26 */     this.tile = tile1;
/*  27 */     this.nodeType = nodeType1;
/*  28 */     this.parent = null;
/*     */   }
/*     */   
/*     */   Node(Node node) {
/*  32 */     this.uid = node.uid;
/*  33 */     this.tile = node.tile;
/*  34 */     this.nodeType = node.nodeType;
/*  35 */     this.parent = node;
/*     */     
/*  37 */     assert this.nodeType != NodeType.Conductor || this.tile.mainTile instanceof IEnergyConductor;
/*  38 */     assert this.nodeType != NodeType.Sink || this.tile.mainTile instanceof ic2.api.energy.tile.IEnergySink;
/*  39 */     assert this.nodeType != NodeType.Source || this.tile.mainTile instanceof ic2.api.energy.tile.IEnergySource;
/*     */     
/*  41 */     for (NodeLink link : node.links) {
/*  42 */       assert (link.getNeighbor(node)).links.contains(link);
/*     */       
/*  44 */       this.links.add(new NodeLink(link));
/*     */     } 
/*     */   }
/*     */   
/*     */   double getInnerLoss() {
/*  49 */     switch (this.nodeType) { case Source:
/*  50 */         return 0.4D;
/*  51 */       case Sink: return 0.4D;
/*  52 */       case Conductor: return ((IEnergyConductor)this.tile.mainTile).getConductionLoss(); }
/*  53 */      throw new RuntimeException("invalid nodetype: " + this.nodeType);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isExtraNode() {
/*  58 */     return (getTop()).isExtraNode;
/*     */   }
/*     */   
/*     */   void setExtraNode(boolean isExtraNode) {
/*  62 */     if (this.nodeType == NodeType.Conductor) throw new IllegalStateException("A conductor can't be an extra node.");
/*     */     
/*  64 */     (getTop()).isExtraNode = isExtraNode;
/*     */   }
/*     */   
/*     */   int getTier() {
/*  68 */     return (getTop()).tier;
/*     */   }
/*     */   
/*     */   void setTier(int tier) {
/*  72 */     if (tier < 0 || Double.isNaN(tier)) {
/*     */       assert false;
/*  74 */       if (EnergyNetGlobal.debugGrid) IC2.log.warn(LogCategory.EnergyNet, "Node %s / te %s is using the invalid tier %d.", new Object[] { this, this.tile.mainTile, Integer.valueOf(tier) }); 
/*  75 */       tier = 0;
/*  76 */     } else if (tier > 20 && (tier != Integer.MAX_VALUE || this.nodeType != NodeType.Sink)) {
/*  77 */       if (Util.inDev()) IC2.log.debug(LogCategory.EnergyNet, "Restricting node %s to tier 20, requested %d.", new Object[] { this, Integer.valueOf(tier) }); 
/*  78 */       tier = 20;
/*     */     } 
/*     */     
/*  81 */     (getTop()).tier = tier;
/*     */   }
/*     */   
/*     */   double getAmount() {
/*  85 */     return (getTop()).amount;
/*     */   }
/*     */   
/*     */   void setAmount(double amount) {
/*  89 */     (getTop()).amount = amount;
/*     */   }
/*     */   
/*     */   double getResistance() {
/*  93 */     return (getTop()).resistance;
/*     */   }
/*     */   
/*     */   void setResistance(double resistance) {
/*  97 */     (getTop()).resistance = resistance;
/*     */   }
/*     */   
/*     */   double getVoltage() {
/* 101 */     return (getTop()).voltage;
/*     */   }
/*     */   
/*     */   void setVoltage(double voltage) {
/* 105 */     (getTop()).voltage = voltage;
/*     */   }
/*     */   
/*     */   double getMaxCurrent() {
/* 109 */     return this.tile.maxCurrent;
/*     */   }
/*     */   
/*     */   void resetCurrents() {
/* 113 */     (getTop()).currentIn = 0.0D;
/* 114 */     (getTop()).currentOut = 0.0D;
/*     */   }
/*     */   
/*     */   void addCurrent(double current) {
/* 118 */     if (current >= 0.0D) {
/* 119 */       (getTop()).currentIn += current;
/*     */     } else {
/* 121 */       (getTop()).currentOut += -current;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 127 */     String type = null;
/*     */     
/* 129 */     switch (this.nodeType) { case Conductor:
/* 130 */         type = "C"; break;
/* 131 */       case Sink: type = "A"; break;
/* 132 */       case Source: type = "E";
/*     */         break; }
/*     */     
/* 135 */     return this.tile.mainTile.getClass().getSimpleName().replace("TileEntity", "") + "|" + type + "|" + this.tier + "|" + this.uid;
/*     */   }
/*     */   
/*     */   Node getTop() {
/* 139 */     if (this.parent != null) {
/* 140 */       return this.parent.getTop();
/*     */     }
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   NodeLink getConnectionTo(Node node) {
/* 147 */     for (NodeLink link : this.links) {
/* 148 */       if (link.getNeighbor(this) == node) return link;
/*     */     
/*     */     } 
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   NodeStats getStats() {
/* 155 */     return this.lastNodeStats;
/*     */   }
/*     */   
/*     */   void updateStats() {
/* 159 */     if (EnergyNetLocal.useLinearTransferModel) {
/* 160 */       this.lastNodeStats.set(this.currentIn * this.voltage, this.currentOut * this.voltage, this.voltage);
/*     */     } else {
/* 162 */       this.lastNodeStats.set(this.currentIn, this.currentOut, this.voltage);
/*     */     } 
/*     */   }
/*     */   
/*     */   Grid getGrid() {
/* 167 */     return (getTop()).grid;
/*     */   }
/*     */   
/*     */   void setGrid(Grid grid) {
/* 171 */     if (grid == null) throw new NullPointerException("null grid");
/*     */     
/* 173 */     assert (getTop()).grid == null;
/*     */     
/* 175 */     (getTop()).grid = grid;
/*     */   }
/*     */   
/*     */   void clearGrid() {
/* 179 */     assert (getTop()).grid != null;
/*     */     
/* 181 */     (getTop()).grid = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isExtraNode = false;
/*     */   
/*     */   private int tier;
/*     */   
/*     */   private double amount;
/*     */   
/*     */   private double resistance;
/*     */   
/*     */   private double voltage;
/*     */   
/*     */   private double currentIn;
/*     */   private double currentOut;
/*     */   private Grid grid;
/* 198 */   List<NodeLink> links = new ArrayList<>();
/*     */   
/* 200 */   private final MutableNodeStats lastNodeStats = new MutableNodeStats();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */