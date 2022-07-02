/*     */ package ic2.core.energy.grid;
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class NodeLink {
/*     */   Node nodeA;
/*     */   Node nodeB;
/*     */   
/*     */   NodeLink(Node nodeA, Node nodeB, double loss) {
/*  15 */     this(nodeA, nodeB, loss, null, null);
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
/*     */   public Node getNeighbor(Node node) {
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
/*     */   public double getLoss() {
/*  53 */     return this.loss;
/*     */   }
/*     */   
/*     */   void replaceNode(Node oldNode, Node newNode) {
/*  57 */     if (this.nodeA == oldNode) {
/*  58 */       this.nodeA = newNode;
/*  59 */     } else if (this.nodeB == oldNode) {
/*  60 */       this.nodeB = newNode;
/*     */     } else {
/*  62 */       throw new IllegalArgumentException("Node " + oldNode + " isn't in " + this + ".");
/*     */     } 
/*     */   }
/*     */   
/*     */   public EnumFacing getDirFrom(Node node) {
/*  67 */     if (this.nodeA == node)
/*  68 */       return this.dirFromA; 
/*  69 */     if (this.nodeB == node) {
/*  70 */       return this.dirFromB;
/*     */     }
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return "NodeLink:" + this.nodeA + "@" + this.dirFromA + "->" + this.nodeB + "@" + this.dirFromB;
/*     */   }
/*     */   
/*     */   private void calculateDirections() {
/*  82 */     for (IEnergyTile posA : this.nodeA.tile.subTiles) {
/*  83 */       for (IEnergyTile posB : this.nodeB.tile.subTiles) {
/*  84 */         BlockPos delta = EnergyNet.instance.getPos(posA).func_177973_b((Vec3i)EnergyNet.instance.getPos(posB));
/*     */         
/*  86 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/*  87 */           if (dir.func_82601_c() == delta.func_177958_n() && dir
/*  88 */             .func_96559_d() == delta.func_177956_o() && dir
/*  89 */             .func_82599_e() == delta.func_177952_p()) {
/*  90 */             this.dirFromA = dir;
/*  91 */             this.dirFromB = dir.func_176734_d();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*     */     assert false;
/* 100 */     this.dirFromA = null;
/* 101 */     this.dirFromB = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   List<Node> skippedNodes = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\NodeLink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */