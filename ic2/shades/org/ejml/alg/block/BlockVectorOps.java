/*     */ package ic2.shades.org.ejml.alg.block;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
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
/*     */ public class BlockVectorOps
/*     */ {
/*     */   public static void scale_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, int offset, int end) {
/*  62 */     double[] dataA = A.original.data;
/*  63 */     double[] dataB = B.original.data;
/*     */ 
/*     */     
/*  66 */     int startI = offset - offset % blockLength;
/*  67 */     offset %= blockLength;
/*     */ 
/*     */     
/*  70 */     int rowBlockA = A.row0 + rowA - rowA % blockLength;
/*  71 */     rowA %= blockLength;
/*  72 */     int rowBlockB = B.row0 + rowB - rowB % blockLength;
/*  73 */     rowB %= blockLength;
/*     */     
/*  75 */     int heightA = Math.min(blockLength, A.row1 - rowBlockA);
/*  76 */     int heightB = Math.min(blockLength, B.row1 - rowBlockB);
/*     */     int i;
/*  78 */     for (i = startI; i < end; i += blockLength) {
/*  79 */       int segment = Math.min(blockLength, end - i);
/*     */       
/*  81 */       int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
/*  82 */       int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
/*     */       
/*  84 */       int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
/*  85 */       int indexB = rowBlockB * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
/*     */       
/*  87 */       if (i == startI) {
/*  88 */         indexA += offset;
/*  89 */         indexB += offset;
/*     */         
/*  91 */         for (int j = offset; j < segment; j++) {
/*  92 */           dataB[indexB++] = alpha * dataA[indexA++];
/*     */         }
/*     */       } else {
/*  95 */         for (int j = 0; j < segment; j++) {
/*  96 */           dataB[indexB++] = alpha * dataA[indexA++];
/*     */         }
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
/*     */   public static void div_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, int offset, int end) {
/* 121 */     double[] dataA = A.original.data;
/* 122 */     double[] dataB = B.original.data;
/*     */ 
/*     */     
/* 125 */     int startI = offset - offset % blockLength;
/* 126 */     offset %= blockLength;
/*     */ 
/*     */     
/* 129 */     int rowBlockA = A.row0 + rowA - rowA % blockLength;
/* 130 */     rowA %= blockLength;
/* 131 */     int rowBlockB = B.row0 + rowB - rowB % blockLength;
/* 132 */     rowB %= blockLength;
/*     */     
/* 134 */     int heightA = Math.min(blockLength, A.row1 - rowBlockA);
/* 135 */     int heightB = Math.min(blockLength, B.row1 - rowBlockB);
/*     */     int i;
/* 137 */     for (i = startI; i < end; i += blockLength) {
/* 138 */       int segment = Math.min(blockLength, end - i);
/*     */       
/* 140 */       int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
/* 141 */       int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
/*     */       
/* 143 */       int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
/* 144 */       int indexB = rowBlockB * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
/*     */       
/* 146 */       if (i == startI) {
/* 147 */         indexA += offset;
/* 148 */         indexB += offset;
/*     */         
/* 150 */         for (int j = offset; j < segment; j++) {
/* 151 */           dataB[indexB++] = dataA[indexA++] / alpha;
/*     */         }
/*     */       } else {
/* 154 */         for (int j = 0; j < segment; j++) {
/* 155 */           dataB[indexB++] = dataA[indexA++] / alpha;
/*     */         }
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
/*     */   public static void add_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, double beta, D1Submatrix64F C, int rowC, int offset, int end) {
/* 185 */     int heightA = Math.min(blockLength, A.row1 - A.row0);
/* 186 */     int heightB = Math.min(blockLength, B.row1 - B.row0);
/* 187 */     int heightC = Math.min(blockLength, C.row1 - C.row0);
/*     */ 
/*     */     
/* 190 */     int startI = offset - offset % blockLength;
/* 191 */     offset %= blockLength;
/*     */     
/* 193 */     double[] dataA = A.original.data;
/* 194 */     double[] dataB = B.original.data;
/* 195 */     double[] dataC = C.original.data;
/*     */     int i;
/* 197 */     for (i = startI; i < end; i += blockLength) {
/* 198 */       int segment = Math.min(blockLength, end - i);
/*     */       
/* 200 */       int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
/* 201 */       int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
/* 202 */       int widthC = Math.min(blockLength, C.col1 - C.col0 - i);
/*     */       
/* 204 */       int indexA = A.row0 * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
/* 205 */       int indexB = B.row0 * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
/* 206 */       int indexC = C.row0 * C.original.numCols + (C.col0 + i) * heightC + rowC * widthC;
/*     */       
/* 208 */       if (i == startI) {
/* 209 */         indexA += offset;
/* 210 */         indexB += offset;
/* 211 */         indexC += offset;
/*     */         
/* 213 */         for (int j = offset; j < segment; j++) {
/* 214 */           dataC[indexC++] = alpha * dataA[indexA++] + beta * dataB[indexB++];
/*     */         }
/*     */       } else {
/* 217 */         for (int j = 0; j < segment; j++) {
/* 218 */           dataC[indexC++] = alpha * dataA[indexA++] + beta * dataB[indexB++];
/*     */         }
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
/*     */   public static double dot_row(int blockLength, D1Submatrix64F A, int rowA, D1Submatrix64F B, int rowB, int offset, int end) {
/* 246 */     int startI = offset - offset % blockLength;
/* 247 */     offset %= blockLength;
/*     */     
/* 249 */     double[] dataA = A.original.data;
/* 250 */     double[] dataB = B.original.data;
/*     */     
/* 252 */     double total = 0.0D;
/*     */ 
/*     */     
/* 255 */     int rowBlockA = A.row0 + rowA - rowA % blockLength;
/* 256 */     rowA %= blockLength;
/* 257 */     int rowBlockB = B.row0 + rowB - rowB % blockLength;
/* 258 */     rowB %= blockLength;
/*     */     
/* 260 */     int heightA = Math.min(blockLength, A.row1 - rowBlockA);
/* 261 */     int heightB = Math.min(blockLength, B.row1 - rowBlockB);
/*     */     
/* 263 */     if (A.col1 - A.col0 != B.col1 - B.col0)
/* 264 */       throw new RuntimeException(); 
/*     */     int i;
/* 266 */     for (i = startI; i < end; i += blockLength) {
/* 267 */       int segment = Math.min(blockLength, end - i);
/*     */       
/* 269 */       int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
/* 270 */       int widthB = Math.min(blockLength, B.col1 - B.col0 - i);
/*     */       
/* 272 */       int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
/* 273 */       int indexB = rowBlockB * B.original.numCols + (B.col0 + i) * heightB + rowB * widthB;
/*     */       
/* 275 */       if (i == startI) {
/* 276 */         indexA += offset;
/* 277 */         indexB += offset;
/*     */         
/* 279 */         for (int j = offset; j < segment; j++) {
/* 280 */           total += dataB[indexB++] * dataA[indexA++];
/*     */         }
/*     */       } else {
/* 283 */         for (int j = 0; j < segment; j++) {
/* 284 */           total += dataB[indexB++] * dataA[indexA++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 289 */     return total;
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
/*     */   public static double dot_row_col(int blockLength, D1Submatrix64F A, int rowA, D1Submatrix64F B, int colB, int offset, int end) {
/* 314 */     int startI = offset - offset % blockLength;
/* 315 */     offset %= blockLength;
/*     */     
/* 317 */     double[] dataA = A.original.data;
/* 318 */     double[] dataB = B.original.data;
/*     */     
/* 320 */     double total = 0.0D;
/*     */ 
/*     */     
/* 323 */     int rowBlockA = A.row0 + rowA - rowA % blockLength;
/* 324 */     rowA %= blockLength;
/* 325 */     int colBlockB = B.col0 + colB - colB % blockLength;
/* 326 */     colB %= blockLength;
/*     */     
/* 328 */     int heightA = Math.min(blockLength, A.row1 - rowBlockA);
/* 329 */     int widthB = Math.min(blockLength, B.col1 - colBlockB);
/*     */     
/* 331 */     if (A.col1 - A.col0 != B.col1 - B.col0)
/* 332 */       throw new RuntimeException(); 
/*     */     int i;
/* 334 */     for (i = startI; i < end; i += blockLength) {
/* 335 */       int segment = Math.min(blockLength, end - i);
/*     */       
/* 337 */       int widthA = Math.min(blockLength, A.col1 - A.col0 - i);
/* 338 */       int heightB = Math.min(blockLength, B.row1 - B.row0 - i);
/*     */       
/* 340 */       int indexA = rowBlockA * A.original.numCols + (A.col0 + i) * heightA + rowA * widthA;
/* 341 */       int indexB = (B.row0 + i) * B.original.numCols + colBlockB * heightB + colB;
/*     */       
/* 343 */       if (i == startI) {
/* 344 */         indexA += offset;
/* 345 */         indexB += offset * widthB;
/*     */         
/* 347 */         for (int j = offset; j < segment; j++, indexB += widthB) {
/* 348 */           total += dataB[indexB] * dataA[indexA++];
/*     */         }
/*     */       } else {
/* 351 */         for (int j = 0; j < segment; j++, indexB += widthB) {
/* 352 */           total += dataB[indexB] * dataA[indexA++];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 357 */     return total;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockVectorOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */