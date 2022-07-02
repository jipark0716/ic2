/*    */ package ic2.core.energy.grid;
/*    */ 
/*    */ public class GridInfo {
/*    */   public GridInfo(int id, int nodeCount, int complexNodeCount, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/*  5 */     this.id = id;
/*  6 */     this.nodeCount = nodeCount;
/*  7 */     this.complexNodeCount = complexNodeCount;
/*  8 */     this.minX = minX;
/*  9 */     this.minY = minY;
/* 10 */     this.minZ = minZ;
/* 11 */     this.maxX = maxX;
/* 12 */     this.maxY = maxY;
/* 13 */     this.maxZ = maxZ;
/*    */   }
/*    */   
/*    */   public final int id;
/*    */   public final int nodeCount;
/*    */   public final int complexNodeCount;
/*    */   public final int minX;
/*    */   public final int minY;
/*    */   public final int minZ;
/*    */   public final int maxX;
/*    */   public final int maxY;
/*    */   public final int maxZ;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\GridInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */