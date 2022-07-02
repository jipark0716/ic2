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
/*     */ public class BlockMultiplication
/*     */ {
/*     */   public static void mult(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*     */     int i;
/*  56 */     for (i = A.row0; i < A.row1; i += blockLength) {
/*  57 */       int heightA = Math.min(blockLength, A.row1 - i);
/*     */       int j;
/*  59 */       for (j = B.col0; j < B.col1; j += blockLength) {
/*  60 */         int widthB = Math.min(blockLength, B.col1 - j);
/*     */         
/*  62 */         int indexC = (i - A.row0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * heightA;
/*     */         int k;
/*  64 */         for (k = A.col0; k < A.col1; k += blockLength) {
/*  65 */           int widthA = Math.min(blockLength, A.col1 - k);
/*     */           
/*  67 */           int indexA = i * A.original.numCols + k * heightA;
/*  68 */           int indexB = (k - A.col0 + B.row0) * B.original.numCols + j * widthA;
/*     */           
/*  70 */           if (k == A.col0) {
/*  71 */             BlockInnerMultiplication.blockMultSet(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*     */           } else {
/*     */             
/*  74 */             BlockInnerMultiplication.blockMultPlus(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*     */           } 
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
/*     */   public static void multPlus(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*     */     int i;
/* 104 */     for (i = A.row0; i < A.row1; i += blockLength) {
/* 105 */       int heightA = Math.min(blockLength, A.row1 - i);
/*     */       int j;
/* 107 */       for (j = B.col0; j < B.col1; j += blockLength) {
/* 108 */         int widthB = Math.min(blockLength, B.col1 - j);
/*     */         
/* 110 */         int indexC = (i - A.row0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * heightA;
/*     */         int k;
/* 112 */         for (k = A.col0; k < A.col1; k += blockLength) {
/* 113 */           int widthA = Math.min(blockLength, A.col1 - k);
/*     */           
/* 115 */           int indexA = i * A.original.numCols + k * heightA;
/* 116 */           int indexB = (k - A.col0 + B.row0) * B.original.numCols + j * widthA;
/*     */           
/* 118 */           BlockInnerMultiplication.blockMultPlus(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
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
/*     */   public static void multMinus(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/* 146 */     checkInput(blockLength, A, B, C);
/*     */     int i;
/* 148 */     for (i = A.row0; i < A.row1; i += blockLength) {
/* 149 */       int heightA = Math.min(blockLength, A.row1 - i);
/*     */       int j;
/* 151 */       for (j = B.col0; j < B.col1; j += blockLength) {
/* 152 */         int widthB = Math.min(blockLength, B.col1 - j);
/*     */         
/* 154 */         int indexC = (i - A.row0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * heightA;
/*     */         int k;
/* 156 */         for (k = A.col0; k < A.col1; k += blockLength) {
/* 157 */           int widthA = Math.min(blockLength, A.col1 - k);
/*     */           
/* 159 */           int indexA = i * A.original.numCols + k * heightA;
/* 160 */           int indexB = (k - A.col0 + B.row0) * B.original.numCols + j * widthA;
/*     */           
/* 162 */           BlockInnerMultiplication.blockMultMinus(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkInput(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/* 173 */     int Arow = A.getRows(), Acol = A.getCols();
/* 174 */     int Brow = B.getRows(), Bcol = B.getCols();
/* 175 */     int Crow = C.getRows(), Ccol = C.getCols();
/*     */     
/* 177 */     if (Arow != Crow)
/* 178 */       throw new RuntimeException("Mismatch A and C rows"); 
/* 179 */     if (Bcol != Ccol)
/* 180 */       throw new RuntimeException("Mismatch B and C columns"); 
/* 181 */     if (Acol != Brow) {
/* 182 */       throw new RuntimeException("Mismatch A columns and B rows");
/*     */     }
/* 184 */     if (!BlockMatrixOps.blockAligned(blockLength, A)) {
/* 185 */       throw new RuntimeException("Sub-Matrix A is not block aligned");
/*     */     }
/* 187 */     if (!BlockMatrixOps.blockAligned(blockLength, B)) {
/* 188 */       throw new RuntimeException("Sub-Matrix B is not block aligned");
/*     */     }
/* 190 */     if (!BlockMatrixOps.blockAligned(blockLength, C)) {
/* 191 */       throw new RuntimeException("Sub-Matrix C is not block aligned");
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
/*     */   public static void multTransA(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*     */     int i;
/* 215 */     for (i = A.col0; i < A.col1; i += blockLength) {
/* 216 */       int widthA = Math.min(blockLength, A.col1 - i);
/*     */       int j;
/* 218 */       for (j = B.col0; j < B.col1; j += blockLength) {
/* 219 */         int widthB = Math.min(blockLength, B.col1 - j);
/*     */         
/* 221 */         int indexC = (i - A.col0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * widthA;
/*     */         int k;
/* 223 */         for (k = A.row0; k < A.row1; k += blockLength) {
/* 224 */           int heightA = Math.min(blockLength, A.row1 - k);
/*     */           
/* 226 */           int indexA = k * A.original.numCols + i * heightA;
/* 227 */           int indexB = (k - A.row0 + B.row0) * B.original.numCols + j * heightA;
/*     */           
/* 229 */           if (k == A.row0) {
/* 230 */             BlockInnerMultiplication.blockMultSetTransA(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*     */           } else {
/*     */             
/* 233 */             BlockInnerMultiplication.blockMultPlusTransA(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void multPlusTransA(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*     */     int i;
/* 244 */     for (i = A.col0; i < A.col1; i += blockLength) {
/* 245 */       int widthA = Math.min(blockLength, A.col1 - i);
/*     */       int j;
/* 247 */       for (j = B.col0; j < B.col1; j += blockLength) {
/* 248 */         int widthB = Math.min(blockLength, B.col1 - j);
/*     */         
/* 250 */         int indexC = (i - A.col0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * widthA;
/*     */         int k;
/* 252 */         for (k = A.row0; k < A.row1; k += blockLength) {
/* 253 */           int heightA = Math.min(blockLength, A.row1 - k);
/*     */           
/* 255 */           int indexA = k * A.original.numCols + i * heightA;
/* 256 */           int indexB = (k - A.row0 + B.row0) * B.original.numCols + j * heightA;
/*     */           
/* 258 */           BlockInnerMultiplication.blockMultPlusTransA(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void multMinusTransA(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*     */     int i;
/* 269 */     for (i = A.col0; i < A.col1; i += blockLength) {
/* 270 */       int widthA = Math.min(blockLength, A.col1 - i);
/*     */       int j;
/* 272 */       for (j = B.col0; j < B.col1; j += blockLength) {
/* 273 */         int widthB = Math.min(blockLength, B.col1 - j);
/*     */         
/* 275 */         int indexC = (i - A.col0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * widthA;
/*     */         int k;
/* 277 */         for (k = A.row0; k < A.row1; k += blockLength) {
/* 278 */           int heightA = Math.min(blockLength, A.row1 - k);
/*     */           
/* 280 */           int indexA = k * A.original.numCols + i * heightA;
/* 281 */           int indexB = (k - A.row0 + B.row0) * B.original.numCols + j * heightA;
/*     */           
/* 283 */           BlockInnerMultiplication.blockMultMinusTransA(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
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
/*     */   public static void multTransB(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*     */     int i;
/* 312 */     for (i = A.row0; i < A.row1; i += blockLength) {
/* 313 */       int heightA = Math.min(blockLength, A.row1 - i);
/*     */       int j;
/* 315 */       for (j = B.row0; j < B.row1; j += blockLength) {
/* 316 */         int widthC = Math.min(blockLength, B.row1 - j);
/*     */         
/* 318 */         int indexC = (i - A.row0 + C.row0) * C.original.numCols + (j - B.row0 + C.col0) * heightA;
/*     */         int k;
/* 320 */         for (k = A.col0; k < A.col1; k += blockLength) {
/* 321 */           int widthA = Math.min(blockLength, A.col1 - k);
/*     */           
/* 323 */           int indexA = i * A.original.numCols + k * heightA;
/* 324 */           int indexB = j * B.original.numCols + (k - A.col0 + B.col0) * widthC;
/*     */           
/* 326 */           if (k == A.col0) {
/* 327 */             BlockInnerMultiplication.blockMultSetTransB(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthC);
/*     */           } else {
/*     */             
/* 330 */             BlockInnerMultiplication.blockMultPlusTransB(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthC);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockMultiplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */