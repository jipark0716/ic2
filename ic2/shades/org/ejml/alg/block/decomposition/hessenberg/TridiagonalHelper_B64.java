/*     */ package ic2.shades.org.ejml.alg.block.decomposition.hessenberg;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockVectorOps;
/*     */ import ic2.shades.org.ejml.alg.block.decomposition.qr.BlockHouseHolder;
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
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
/*     */ public class TridiagonalHelper_B64
/*     */ {
/*     */   public static void tridiagUpperRow(int blockLength, D1Submatrix64F A, double[] gammas, D1Submatrix64F V) {
/*  57 */     int blockHeight = Math.min(blockLength, A.row1 - A.row0);
/*  58 */     if (blockHeight <= 1)
/*     */       return; 
/*  60 */     int width = A.col1 - A.col0;
/*  61 */     int num = Math.min(width - 1, blockHeight);
/*  62 */     int applyIndex = Math.min(width, blockHeight);
/*     */ 
/*     */     
/*  65 */     for (int i = 0; i < num; i++) {
/*     */       
/*  67 */       BlockHouseHolder.computeHouseHolderRow(blockLength, A, gammas, i);
/*  68 */       double gamma = gammas[A.row0 + i];
/*     */ 
/*     */       
/*  71 */       computeY(blockLength, A, V, i, gamma);
/*     */ 
/*     */       
/*  74 */       computeRowOfV(blockLength, A, V, i, gamma);
/*     */ 
/*     */       
/*  77 */       if (i + 1 < applyIndex) {
/*  78 */         applyReflectorsToRow(blockLength, A, V, i + 1);
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public static void computeW_row(int blockLength, D1Submatrix64F Y, D1Submatrix64F W, double[] beta, int betaIndex) {
/* 110 */     int heightY = Y.row1 - Y.row0;
/* 111 */     CommonOps.fill(W.original, 0.0D);
/*     */ 
/*     */     
/* 114 */     BlockHouseHolder.scale_row(blockLength, Y, W, 0, 1, -beta[betaIndex++]);
/*     */     
/* 116 */     int min = Math.min(heightY, W.col1 - W.col0);
/*     */ 
/*     */     
/* 119 */     for (int i = 1; i < min; i++) {
/*     */       
/* 121 */       double b = -beta[betaIndex++];
/*     */ 
/*     */       
/* 124 */       for (int j = 0; j < i; j++) {
/* 125 */         double yv = BlockHouseHolder.innerProdRow(blockLength, Y, i, Y, j, 1);
/* 126 */         BlockVectorOps.add_row(blockLength, W, i, 1.0D, W, j, b * yv, W, i, 1, Y.col1 - Y.col0);
/*     */       } 
/*     */ 
/*     */       
/* 130 */       BlockHouseHolder.add_row(blockLength, Y, i, b, W, i, 1.0D, W, i, 1, Y.col1 - Y.col0);
/*     */     } 
/*     */   }
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
/*     */   public static void computeV_blockVector(int blockLength, D1Submatrix64F A, double[] gammas, D1Submatrix64F V) {
/* 148 */     int blockHeight = Math.min(blockLength, A.row1 - A.row0);
/* 149 */     if (blockHeight <= 1)
/*     */       return; 
/* 151 */     int width = A.col1 - A.col0;
/* 152 */     int num = Math.min(width - 1, blockHeight);
/*     */     
/* 154 */     for (int i = 0; i < num; i++) {
/* 155 */       double gamma = gammas[A.row0 + i];
/*     */ 
/*     */       
/* 158 */       computeY(blockLength, A, V, i, gamma);
/*     */ 
/*     */       
/* 161 */       computeRowOfV(blockLength, A, V, i, gamma);
/*     */     } 
/*     */   }
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
/*     */   public static void applyReflectorsToRow(int blockLength, D1Submatrix64F A, D1Submatrix64F V, int row) {
/* 182 */     int height = Math.min(blockLength, A.row1 - A.row0);
/*     */     
/* 184 */     double[] dataA = A.original.data;
/* 185 */     double[] dataV = V.original.data;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     for (int i = 0; i < row; i++) {
/* 191 */       int width = Math.min(blockLength, A.col1 - A.col0);
/*     */       
/* 193 */       int indexU = A.original.numCols * A.row0 + height * A.col0 + i * width + row;
/* 194 */       int indexV = V.original.numCols * V.row0 + height * V.col0 + i * width + row;
/*     */       
/* 196 */       double u_row = (i + 1 == row) ? 1.0D : dataA[indexU];
/* 197 */       double v_row = dataV[indexV];
/*     */ 
/*     */       
/* 200 */       double before = A.get(i, i + 1);
/* 201 */       A.set(i, i + 1, 1.0D);
/*     */ 
/*     */       
/* 204 */       BlockVectorOps.add_row(blockLength, A, row, 1.0D, V, i, u_row, A, row, row, A.col1 - A.col0);
/* 205 */       BlockVectorOps.add_row(blockLength, A, row, 1.0D, A, i, v_row, A, row, row, A.col1 - A.col0);
/*     */       
/* 207 */       A.set(i, i + 1, before);
/*     */     } 
/*     */   }
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
/*     */   public static void computeY(int blockLength, D1Submatrix64F A, D1Submatrix64F V, int row, double gamma) {
/* 232 */     multA_u(blockLength, A, V, row);
/*     */     
/* 234 */     for (int i = 0; i < row; i++) {
/*     */ 
/*     */ 
/*     */       
/* 238 */       double dot_v_u = BlockHouseHolder.innerProdRow(blockLength, A, row, V, i, 1);
/*     */ 
/*     */       
/* 241 */       double dot_u_u = BlockHouseHolder.innerProdRow(blockLength, A, row, A, i, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 246 */       BlockVectorOps.add_row(blockLength, V, row, 1.0D, A, i, dot_v_u, V, row, row + 1, A.col1 - A.col0);
/*     */ 
/*     */ 
/*     */       
/* 250 */       BlockVectorOps.add_row(blockLength, V, row, 1.0D, V, i, dot_u_u, V, row, row + 1, A.col1 - A.col0);
/*     */     } 
/*     */ 
/*     */     
/* 254 */     BlockVectorOps.scale_row(blockLength, V, row, -gamma, V, row, row + 1, V.col1 - V.col0);
/*     */   }
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
/*     */   public static void multA_u(int blockLength, D1Submatrix64F A, D1Submatrix64F V, int row) {
/* 275 */     int heightMatA = A.row1 - A.row0;
/*     */     
/* 277 */     for (int i = row + 1; i < heightMatA; i++) {
/*     */       
/* 279 */       double val = innerProdRowSymm(blockLength, A, row, A, i, 1);
/*     */       
/* 281 */       V.set(row, i, val);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double innerProdRowSymm(int blockLength, D1Submatrix64F A, int rowA, D1Submatrix64F B, int rowB, int zeroOffset) {
/* 290 */     int offset = rowA + zeroOffset;
/* 291 */     if (offset + B.col0 >= B.col1) {
/* 292 */       return 0.0D;
/*     */     }
/* 294 */     if (offset < rowB) {
/*     */       
/* 296 */       double d = B.get(offset, rowB);
/*     */       
/* 298 */       d += BlockVectorOps.dot_row_col(blockLength, A, rowA, B, rowB, offset + 1, rowB);
/* 299 */       d += BlockVectorOps.dot_row(blockLength, A, rowA, B, rowB, rowB, A.col1 - A.col0);
/*     */       
/* 301 */       return d;
/*     */     } 
/*     */     
/* 304 */     double total = B.get(rowB, offset);
/*     */     
/* 306 */     total += BlockVectorOps.dot_row(blockLength, A, rowA, B, rowB, offset + 1, A.col1 - A.col0);
/*     */     
/* 308 */     return total;
/*     */   }
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
/*     */   public static void computeRowOfV(int blockLength, D1Submatrix64F A, D1Submatrix64F V, int row, double gamma) {
/* 332 */     double val = BlockHouseHolder.innerProdRow(blockLength, A, row, V, row, 1);
/*     */ 
/*     */     
/* 335 */     double before = A.get(row, row + 1);
/* 336 */     A.set(row, row + 1, 1.0D);
/*     */ 
/*     */     
/* 339 */     BlockVectorOps.add_row(blockLength, V, row, 1.0D, A, row, -0.5D * gamma * val, V, row, row + 1, A.col1 - A.col0);
/*     */     
/* 341 */     A.set(row, row + 1, before);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\hessenberg\TridiagonalHelper_B64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */