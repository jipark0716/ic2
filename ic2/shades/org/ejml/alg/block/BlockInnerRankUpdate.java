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
/*     */ public class BlockInnerRankUpdate
/*     */ {
/*     */   public static void rankNUpdate(int blockLength, double alpha, D1Submatrix64F A, D1Submatrix64F B) {
/*  49 */     int heightB = B.row1 - B.row0;
/*  50 */     if (heightB > blockLength) {
/*  51 */       throw new IllegalArgumentException("Height of B cannot be greater than the block length");
/*     */     }
/*  53 */     int N = B.col1 - B.col0;
/*     */     
/*  55 */     if (A.col1 - A.col0 != N)
/*  56 */       throw new IllegalArgumentException("A does not have the expected number of columns based on B's width"); 
/*  57 */     if (A.row1 - A.row0 != N)
/*  58 */       throw new IllegalArgumentException("A does not have the expected number of rows based on B's width"); 
/*     */     int i;
/*  60 */     for (i = B.col0; i < B.col1; i += blockLength) {
/*     */       
/*  62 */       int indexB_i = B.row0 * B.original.numCols + i * heightB;
/*  63 */       int widthB_i = Math.min(blockLength, B.col1 - i);
/*     */       
/*  65 */       int rowA = i - B.col0 + A.row0;
/*  66 */       int heightA = Math.min(blockLength, A.row1 - rowA);
/*     */       int j;
/*  68 */       for (j = B.col0; j < B.col1; j += blockLength) {
/*     */         
/*  70 */         int widthB_j = Math.min(blockLength, B.col1 - j);
/*     */         
/*  72 */         int indexA = rowA * A.original.numCols + (j - B.col0 + A.col0) * heightA;
/*  73 */         int indexB_j = B.row0 * B.original.numCols + j * heightB;
/*     */         
/*  75 */         BlockInnerMultiplication.blockMultPlusTransA(alpha, B.original.data, B.original.data, A.original.data, indexB_i, indexB_j, indexA, heightB, widthB_i, widthB_j);
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
/*     */   public static void symmRankNMinus_U(int blockLength, D1Submatrix64F A, D1Submatrix64F B) {
/*  94 */     int heightB = B.row1 - B.row0;
/*  95 */     if (heightB > blockLength) {
/*  96 */       throw new IllegalArgumentException("Height of B cannot be greater than the block length");
/*     */     }
/*  98 */     int N = B.col1 - B.col0;
/*     */     
/* 100 */     if (A.col1 - A.col0 != N)
/* 101 */       throw new IllegalArgumentException("A does not have the expected number of columns based on B's width"); 
/* 102 */     if (A.row1 - A.row0 != N) {
/* 103 */       throw new IllegalArgumentException("A does not have the expected number of rows based on B's width");
/*     */     }
/*     */     int i;
/* 106 */     for (i = B.col0; i < B.col1; i += blockLength) {
/*     */       
/* 108 */       int indexB_i = B.row0 * B.original.numCols + i * heightB;
/* 109 */       int widthB_i = Math.min(blockLength, B.col1 - i);
/*     */       
/* 111 */       int rowA = i - B.col0 + A.row0;
/* 112 */       int heightA = Math.min(blockLength, A.row1 - rowA);
/*     */       int j;
/* 114 */       for (j = i; j < B.col1; j += blockLength) {
/*     */         
/* 116 */         int widthB_j = Math.min(blockLength, B.col1 - j);
/*     */         
/* 118 */         int indexA = rowA * A.original.numCols + (j - B.col0 + A.col0) * heightA;
/* 119 */         int indexB_j = B.row0 * B.original.numCols + j * heightB;
/*     */         
/* 121 */         if (i == j) {
/*     */           
/* 123 */           multTransABlockMinus_U(B.original.data, A.original.data, indexB_i, indexB_j, indexA, heightB, widthB_i, widthB_j);
/*     */         } else {
/*     */           
/* 126 */           multTransABlockMinus(B.original.data, A.original.data, indexB_i, indexB_j, indexA, heightB, widthB_i, widthB_j);
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
/*     */   public static void symmRankNMinus_L(int blockLength, D1Submatrix64F A, D1Submatrix64F B) {
/* 144 */     int widthB = B.col1 - B.col0;
/* 145 */     if (widthB > blockLength) {
/* 146 */       throw new IllegalArgumentException("Width of B cannot be greater than the block length");
/*     */     }
/* 148 */     int N = B.row1 - B.row0;
/*     */     
/* 150 */     if (A.col1 - A.col0 != N)
/* 151 */       throw new IllegalArgumentException("A does not have the expected number of columns based on B's height"); 
/* 152 */     if (A.row1 - A.row0 != N)
/* 153 */       throw new IllegalArgumentException("A does not have the expected number of rows based on B's height"); 
/*     */     int i;
/* 155 */     for (i = B.row0; i < B.row1; i += blockLength) {
/*     */ 
/*     */       
/* 158 */       int heightB_i = Math.min(blockLength, B.row1 - i);
/* 159 */       int indexB_i = i * B.original.numCols + heightB_i * B.col0;
/*     */       
/* 161 */       int rowA = i - B.row0 + A.row0;
/* 162 */       int heightA = Math.min(blockLength, A.row1 - rowA);
/*     */       int j;
/* 164 */       for (j = B.row0; j <= i; j += blockLength) {
/*     */         
/* 166 */         int widthB_j = Math.min(blockLength, B.row1 - j);
/*     */         
/* 168 */         int indexA = rowA * A.original.numCols + (j - B.row0 + A.col0) * heightA;
/* 169 */         int indexB_j = j * B.original.numCols + widthB_j * B.col0;
/*     */         
/* 171 */         if (i == j) {
/* 172 */           multTransBBlockMinus_L(B.original.data, A.original.data, indexB_i, indexB_j, indexA, widthB, heightB_i, widthB_j);
/*     */         } else {
/*     */           
/* 175 */           multTransBBlockMinus(B.original.data, A.original.data, indexB_i, indexB_j, indexA, widthB, heightB_i, widthB_j);
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
/*     */   protected static void multTransABlockMinus(double[] dataA, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 202 */     int rowB = indexB;
/* 203 */     int endLoopK = rowB + heightA * widthC;
/* 204 */     int startA = indexA;
/*     */ 
/*     */     
/* 207 */     for (; rowB != endLoopK; rowB += widthC, startA += widthA) {
/* 208 */       int a = startA;
/* 209 */       int c = indexC;
/*     */       
/* 211 */       int endA = a + widthA;
/* 212 */       int endB = rowB + widthC;
/*     */       
/* 214 */       while (a != endA) {
/* 215 */         double valA = dataA[a++];
/*     */         
/* 217 */         int b = rowB;
/* 218 */         while (b != endB) {
/* 219 */           dataC[c++] = dataC[c++] - valA * dataA[b++];
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
/*     */   protected static void multTransABlockMinus_U(double[] dataA, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 245 */     for (int i = 0; i < widthA; i++) {
/* 246 */       for (int k = 0; k < heightA; k++) {
/*     */         
/* 248 */         double valA = dataA[k * widthA + i + indexA];
/* 249 */         int b = k * widthC + indexB + i;
/* 250 */         int c = i * widthC + indexC + i;
/*     */         
/* 252 */         int endC = c - i + widthC;
/*     */         
/* 254 */         while (c != endC)
/*     */         {
/* 256 */           dataC[c++] = dataC[c++] - valA * dataA[b++];
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
/*     */   protected static void multTransBBlockMinus(double[] dataA, double[] dataC, int indexA, int indexB, int indexC, int widthA, int heightA, int widthC) {
/* 282 */     int rowA = indexA;
/* 283 */     int c = indexC;
/* 284 */     for (int i = 0; i < heightA; i++, rowA += widthA) {
/* 285 */       int endA = rowA + widthA;
/* 286 */       int rowB = indexB;
/* 287 */       int endLoopJ = c + widthC;
/*     */ 
/*     */       
/* 290 */       while (c != endLoopJ) {
/* 291 */         int a = rowA;
/* 292 */         int b = rowB;
/*     */         
/* 294 */         double sum = 0.0D;
/* 295 */         while (a != endA) {
/* 296 */           sum += dataA[a++] * dataA[b++];
/*     */         }
/* 298 */         dataC[c++] = dataC[c++] - sum;
/* 299 */         rowB += widthA;
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
/*     */   protected static void multTransBBlockMinus_L(double[] dataA, double[] dataC, int indexA, int indexB, int indexC, int widthA, int heightA, int widthC) {
/* 324 */     for (int i = 0; i < heightA; i++) {
/* 325 */       int rowA = i * widthA + indexA;
/* 326 */       int endA = rowA + widthA;
/* 327 */       int rowB = indexB;
/* 328 */       int rowC = i * widthC + indexC;
/* 329 */       for (int j = 0; j <= i; j++, rowB += widthA) {
/* 330 */         double sum = 0.0D;
/*     */         
/* 332 */         int a = rowA;
/* 333 */         int b = rowB;
/*     */         
/* 335 */         while (a != endA) {
/* 336 */           sum += dataA[a++] * dataA[b++];
/*     */         }
/* 338 */         dataC[rowC + j] = dataC[rowC + j] - sum;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockInnerRankUpdate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */