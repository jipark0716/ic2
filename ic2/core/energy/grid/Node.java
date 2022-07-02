/*     */ package ic2.core.energy.grid;
/*     */ import ic2.api.energy.tile.IEnergyConductor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Node {
/*     */   final int uid;
/*     */   final Tile tile;
/*     */   final NodeType nodeType;
/*     */   
/*     */   Node(int uid, Tile tile, NodeType nodeType) {
/*  13 */     if (tile == null) throw new NullPointerException("null tile"); 
/*  14 */     if (nodeType == null) throw new NullPointerException("null node type"); 
/*  15 */     assert nodeType != NodeType.Conductor || tile.getMainTile() instanceof IEnergyConductor;
/*  16 */     assert nodeType != NodeType.Sink || tile.getMainTile() instanceof ic2.api.energy.tile.IEnergySink;
/*  17 */     assert nodeType != NodeType.Source || tile.getMainTile() instanceof ic2.api.energy.tile.IEnergySource;
/*     */     
/*  19 */     this.uid = uid;
/*  20 */     this.tile = tile;
/*  21 */     this.nodeType = nodeType;
/*     */   }
/*     */   
/*     */   public Tile getTile() {
/*  25 */     return this.tile;
/*     */   }
/*     */   
/*     */   public NodeType getType() {
/*  29 */     return this.nodeType;
/*     */   }
/*     */   
/*     */   boolean isExtraNode() {
/*  33 */     return this.isExtraNode;
/*     */   }
/*     */   
/*     */   void setExtraNode(boolean isExtraNode) {
/*  37 */     if (this.nodeType == NodeType.Conductor) throw new IllegalStateException("A conductor can't be an extra node.");
/*     */     
/*  39 */     this.isExtraNode = isExtraNode;
/*     */   }
/*     */   
/*     */   public Grid getGrid() {
/*  43 */     return this.grid;
/*     */   }
/*     */   
/*     */   void setGrid(Grid grid) {
/*  47 */     if (grid == null) throw new NullPointerException("null grid");
/*     */     
/*  49 */     assert this.grid == null;
/*     */     
/*  51 */     this.grid = grid;
/*     */   }
/*     */   
/*     */   void clearGrid() {
/*  55 */     assert this.grid != null;
/*     */     
/*  57 */     this.grid = null;
/*     */   }
/*     */   
/*     */   public Collection<NodeLink> getLinks() {
/*  61 */     return this.links;
/*     */   }
/*     */   
/*     */   public NodeLink getLinkTo(Node node) {
/*  65 */     for (NodeLink link : this.links) {
/*  66 */       if (link.getNeighbor(this) == node) return link;
/*     */     
/*     */     } 
/*  69 */     return null;
/*     */   }
/*     */   
/*     */   double getInnerLoss() {
/*  73 */     switch (this.nodeType) { case Source:
/*  74 */         return 0.002D;
/*  75 */       case Sink: return 0.002D;
/*  76 */       case Conductor: return ((IEnergyConductor)this.tile.getMainTile()).getConductionLoss(); }
/*  77 */      throw new RuntimeException("invalid nodetype: " + this.nodeType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  83 */     String type = null;
/*     */     
/*  85 */     switch (this.nodeType) { case Conductor:
/*  86 */         type = "C"; break;
/*  87 */       case Sink: type = "A"; break;
/*  88 */       case Source: type = "E";
/*     */         break; }
/*     */     
/*  91 */     return this.tile + "|" + type + "|" + this.uid;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isExtraNode = false;
/*     */   
/*     */   private Grid grid;
/*     */   
/* 100 */   List<NodeLink> links = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\Node.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */