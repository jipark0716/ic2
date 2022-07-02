/*     */ package ic2.core.energy;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ class NodeLink {
/*     */   Node nodeA;
/*     */   Node nodeB;
/*     */   
/*     */   NodeLink(Node nodeA1, Node nodeB1, double loss1) {
/*  15 */     this(nodeA1, nodeB1, loss1, null, null);
/*     */     
/*  17 */     calculateDirections();
/*     */   }
/*     */   EnumFacing dirFromA; EnumFacing dirFromB; double loss;
/*     */   NodeLink(NodeLink link) {
/*  21 */     this(link.nodeA, link.nodeB, link.loss, link.dirFromA, link.dirFromB);
/*     */     
/*  23 */     this.skippedNodes.addAll(link.skippedNodes);
/*     */   }
/*     */   
/*     */   private NodeLink(Node nodeA1, Node nodeB1, double loss1, EnumFacing dirFromA, EnumFacing dirFromB) {
/*  27 */     assert nodeA1 != nodeB1;
/*     */     
/*  29 */     this.nodeA = nodeA1;
/*  30 */     this.nodeB = nodeB1;
/*  31 */     this.loss = loss1;
/*  32 */     this.dirFromA = dirFromA;
/*  33 */     this.dirFromB = dirFromB;
/*     */   }
/*     */   
/*     */   Node getNeighbor(Node node) {
/*  37 */     if (this.nodeA == node) {
/*  38 */       return this.nodeB;
/*     */     }
/*     */     
/*  41 */     return this.nodeA;
/*     */   }
/*     */   
/*     */   Node getNeighbor(int uid) {
/*  45 */     if (this.nodeA.uid == uid) {
/*  46 */       return this.nodeB;
/*     */     }
/*     */     
/*  49 */     return this.nodeA;
/*     */   }
/*     */   
/*     */   void replaceNode(Node oldNode, Node newNode) {
/*  53 */     if (this.nodeA == oldNode) {
/*  54 */       this.nodeA = newNode;
/*  55 */     } else if (this.nodeB == oldNode) {
/*  56 */       this.nodeB = newNode;
/*     */     } else {
/*  58 */       throw new IllegalArgumentException("Node " + oldNode + " isn't in " + this + ".");
/*     */     } 
/*     */   }
/*     */   
/*     */   EnumFacing getDirFrom(Node node) {
/*  63 */     if (this.nodeA == node)
/*  64 */       return this.dirFromA; 
/*  65 */     if (this.nodeB == node) {
/*  66 */       return this.dirFromB;
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateCurrent() {
/*  73 */     assert !Double.isNaN(this.nodeA.getVoltage());
/*  74 */     assert !Double.isNaN(this.nodeB.getVoltage());
/*     */     
/*  76 */     double currentAB = (this.nodeA.getVoltage() - this.nodeB.getVoltage()) / this.loss;
/*     */     
/*  78 */     this.nodeA.addCurrent(-currentAB);
/*  79 */     this.nodeB.addCurrent(currentAB);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  84 */     return "NodeLink:" + this.nodeA + "@" + this.dirFromA + "->" + this.nodeB + "@" + this.dirFromB;
/*     */   }
/*     */   
/*     */   private void calculateDirections() {
/*  88 */     for (IEnergyTile posA : this.nodeA.tile.subTiles) {
/*  89 */       for (IEnergyTile posB : this.nodeB.tile.subTiles) {
/*  90 */         BlockPos delta = EnergyNet.instance.getPos(posA).func_177973_b((Vec3i)EnergyNet.instance.getPos(posB));
/*     */         
/*  92 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  93 */           if (dir.func_82601_c() == delta.func_177958_n() && dir
/*  94 */             .func_96559_d() == delta.func_177956_o() && dir
/*  95 */             .func_82599_e() == delta.func_177952_p()) {
/*  96 */             this.dirFromA = dir;
/*  97 */             this.dirFromB = dir.func_176734_d();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     assert false;
/* 106 */     this.dirFromA = null;
/* 107 */     this.dirFromB = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   List<Node> skippedNodes = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\NodeLink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */