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
/*     */ public class BlockTriangularSolver
/*     */ {
/*     */   public static void invert(int blockLength, boolean upper, D1Submatrix64F T, D1Submatrix64F T_inv, double[] temp) {
/*  56 */     if (upper) {
/*  57 */       throw new IllegalArgumentException("Upper triangular matrices not supported yet");
/*     */     }
/*  59 */     if (temp.length < blockLength * blockLength) {
/*  60 */       throw new IllegalArgumentException("Temp must be at least blockLength*blockLength long.");
/*     */     }
/*  62 */     if (T.row0 != T_inv.row0 || T.row1 != T_inv.row1 || T.col0 != T_inv.col0 || T.col1 != T_inv.col1) {
/*  63 */       throw new IllegalArgumentException("T and T_inv must be at the same elements in the matrix");
/*     */     }
/*  65 */     int M = T.row1 - T.row0;
/*     */     
/*  67 */     double[] dataT = T.original.data;
/*  68 */     double[] dataX = T_inv.original.data;
/*     */     
/*  70 */     int offsetT = T.row0 * T.original.numCols + M * T.col0;
/*     */     int i;
/*  72 */     for (i = 0; i < M; i += blockLength) {
/*  73 */       int heightT = Math.min(T.row1 - i + T.row0, blockLength);
/*     */       
/*  75 */       int indexII = offsetT + T.original.numCols * (i + T.row0) + heightT * (i + T.col0);
/*     */       int j;
/*  77 */       for (j = 0; j < i; j += blockLength) {
/*  78 */         int widthX = Math.min(T.col1 - j + T.col0, blockLength);
/*     */         
/*  80 */         for (int w = 0; w < temp.length; w++) {
/*  81 */           temp[w] = 0.0D;
/*     */         }
/*     */         int k;
/*  84 */         for (k = j; k < i; k += blockLength) {
/*  85 */           int widthT = Math.min(T.col1 - k + T.col0, blockLength);
/*     */           
/*  87 */           int indexL = offsetT + T.original.numCols * (i + T.row0) + heightT * (k + T.col0);
/*  88 */           int m = offsetT + T.original.numCols * (k + T.row0) + widthT * (j + T.col0);
/*     */           
/*  90 */           BlockInnerMultiplication.blockMultMinus(dataT, dataX, temp, indexL, m, 0, heightT, widthT, widthX);
/*     */         } 
/*     */         
/*  93 */         int indexX = offsetT + T.original.numCols * (i + T.row0) + heightT * (j + T.col0);
/*     */         
/*  95 */         BlockInnerTriangularSolver.solveL(dataT, temp, heightT, widthX, heightT, indexII, 0);
/*  96 */         System.arraycopy(temp, 0, dataX, indexX, widthX * heightT);
/*     */       } 
/*  98 */       BlockInnerTriangularSolver.invertLower(dataT, dataX, heightT, indexII, indexII);
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
/*     */   public static void invert(int blockLength, boolean upper, D1Submatrix64F T, double[] temp) {
/* 115 */     if (upper) {
/* 116 */       throw new IllegalArgumentException("Upper triangular matrices not supported yet");
/*     */     }
/* 118 */     if (temp.length < blockLength * blockLength) {
/* 119 */       throw new IllegalArgumentException("Temp must be at least blockLength*blockLength long.");
/*     */     }
/* 121 */     int M = T.row1 - T.row0;
/*     */     
/* 123 */     double[] dataT = T.original.data;
/* 124 */     int offsetT = T.row0 * T.original.numCols + M * T.col0;
/*     */     int i;
/* 126 */     for (i = 0; i < M; i += blockLength) {
/* 127 */       int heightT = Math.min(T.row1 - i + T.row0, blockLength);
/*     */       
/* 129 */       int indexII = offsetT + T.original.numCols * (i + T.row0) + heightT * (i + T.col0);
/*     */       int j;
/* 131 */       for (j = 0; j < i; j += blockLength) {
/* 132 */         int widthX = Math.min(T.col1 - j + T.col0, blockLength);
/*     */         
/* 134 */         for (int w = 0; w < temp.length; w++) {
/* 135 */           temp[w] = 0.0D;
/*     */         }
/*     */         int k;
/* 138 */         for (k = j; k < i; k += blockLength) {
/* 139 */           int widthT = Math.min(T.col1 - k + T.col0, blockLength);
/*     */           
/* 141 */           int indexL = offsetT + T.original.numCols * (i + T.row0) + heightT * (k + T.col0);
/* 142 */           int m = offsetT + T.original.numCols * (k + T.row0) + widthT * (j + T.col0);
/*     */           
/* 144 */           BlockInnerMultiplication.blockMultMinus(dataT, dataT, temp, indexL, m, 0, heightT, widthT, widthX);
/*     */         } 
/*     */         
/* 147 */         int indexX = offsetT + T.original.numCols * (i + T.row0) + heightT * (j + T.col0);
/*     */         
/* 149 */         BlockInnerTriangularSolver.solveL(dataT, temp, heightT, widthX, heightT, indexII, 0);
/* 150 */         System.arraycopy(temp, 0, dataT, indexX, widthX * heightT);
/*     */       } 
/* 152 */       BlockInnerTriangularSolver.invertLower(dataT, heightT, indexII);
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
/*     */   public static void solve(int blockLength, boolean upper, D1Submatrix64F T, D1Submatrix64F B, boolean transT) {
/* 178 */     if (upper) {
/* 179 */       solveR(blockLength, T, B, transT);
/*     */     } else {
/* 181 */       solveL(blockLength, T, B, transT);
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
/*     */   public static void solveBlock(int blockLength, boolean upper, D1Submatrix64F T, D1Submatrix64F B, boolean transT, boolean transB) {
/* 208 */     int Trows = T.row1 - T.row0;
/* 209 */     if (Trows > blockLength) {
/* 210 */       throw new IllegalArgumentException("T can be at most the size of a block");
/*     */     }
/* 212 */     int blockT_rows = Math.min(blockLength, T.original.numRows - T.row0);
/* 213 */     int blockT_cols = Math.min(blockLength, T.original.numCols - T.col0);
/*     */     
/* 215 */     int offsetT = T.row0 * T.original.numCols + blockT_rows * T.col0;
/*     */     
/* 217 */     double[] dataT = T.original.data;
/* 218 */     double[] dataB = B.original.data;
/*     */     
/* 220 */     if (transB) {
/* 221 */       if (upper) {
/* 222 */         if (transT) {
/* 223 */           throw new IllegalArgumentException("Operation not yet supported");
/*     */         }
/* 225 */         throw new IllegalArgumentException("Operation not yet supported");
/*     */       } 
/*     */       
/* 228 */       if (transT)
/* 229 */         throw new IllegalArgumentException("Operation not yet supported"); 
/*     */       int i;
/* 231 */       for (i = B.row0; i < B.row1; i += blockLength) {
/* 232 */         int N = Math.min(B.row1, i + blockLength) - i;
/*     */         
/* 234 */         int offsetB = i * B.original.numCols + N * B.col0;
/*     */         
/* 236 */         BlockInnerTriangularSolver.solveLTransB(dataT, dataB, blockT_rows, N, blockT_rows, offsetT, offsetB);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 241 */       if (Trows != B.row1 - B.row0) {
/* 242 */         throw new IllegalArgumentException("T and B must have the same number of rows.");
/*     */       }
/* 244 */       if (upper) {
/* 245 */         if (transT) {
/* 246 */           int i; for (i = B.col0; i < B.col1; i += blockLength) {
/* 247 */             int offsetB = B.row0 * B.original.numCols + Trows * i;
/*     */             
/* 249 */             int N = Math.min(B.col1, i + blockLength) - i;
/* 250 */             BlockInnerTriangularSolver.solveTransU(dataT, dataB, Trows, N, Trows, offsetT, offsetB);
/*     */           } 
/*     */         } else {
/* 253 */           int i; for (i = B.col0; i < B.col1; i += blockLength) {
/* 254 */             int offsetB = B.row0 * B.original.numCols + Trows * i;
/*     */             
/* 256 */             int N = Math.min(B.col1, i + blockLength) - i;
/* 257 */             BlockInnerTriangularSolver.solveU(dataT, dataB, Trows, N, Trows, offsetT, offsetB);
/*     */           }
/*     */         
/*     */         } 
/* 261 */       } else if (transT) {
/* 262 */         int i; for (i = B.col0; i < B.col1; i += blockLength) {
/* 263 */           int offsetB = B.row0 * B.original.numCols + Trows * i;
/*     */           
/* 265 */           int N = Math.min(B.col1, i + blockLength) - i;
/* 266 */           BlockInnerTriangularSolver.solveTransL(dataT, dataB, Trows, N, blockT_cols, offsetT, offsetB);
/*     */         } 
/*     */       } else {
/* 269 */         int i; for (i = B.col0; i < B.col1; i += blockLength) {
/* 270 */           int offsetB = B.row0 * B.original.numCols + Trows * i;
/*     */           
/* 272 */           int N = Math.min(B.col1, i + blockLength) - i;
/* 273 */           BlockInnerTriangularSolver.solveL(dataT, dataB, Trows, N, blockT_cols, offsetT, offsetB);
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
/*     */   public static void solveL(int blockLength, D1Submatrix64F L, D1Submatrix64F B, boolean transL) {
/*     */     int startI, stepI;
/* 300 */     D1Submatrix64F Y = new D1Submatrix64F(B.original);
/*     */     
/* 302 */     D1Submatrix64F Linner = new D1Submatrix64F(L.original);
/* 303 */     D1Submatrix64F Binner = new D1Submatrix64F(B.original);
/*     */     
/* 305 */     int lengthL = B.row1 - B.row0;
/*     */ 
/*     */ 
/*     */     
/* 309 */     if (transL) {
/* 310 */       startI = lengthL - lengthL % blockLength;
/* 311 */       if (startI == lengthL && lengthL >= blockLength) {
/* 312 */         startI -= blockLength;
/*     */       }
/* 314 */       stepI = -blockLength;
/*     */     } else {
/* 316 */       startI = 0;
/* 317 */       stepI = blockLength;
/*     */     } 
/*     */     
/* 320 */     int i = startI;
/* 321 */     for (; transL ? (
/* 322 */       i < 0) : (
/*     */       
/* 324 */       i >= lengthL); i += stepI) {
/*     */       boolean updateY;
/*     */ 
/*     */       
/* 328 */       int widthT = Math.min(blockLength, lengthL - i);
/*     */       
/* 330 */       L.col0 += i; Linner.col1 = Linner.col0 + widthT;
/* 331 */       L.row0 += i; Linner.row1 = Linner.row0 + widthT;
/*     */       
/* 333 */       Binner.col0 = B.col0; Binner.col1 = B.col1;
/* 334 */       B.row0 += i; Binner.row1 = Binner.row0 + widthT;
/*     */ 
/*     */ 
/*     */       
/* 338 */       solveBlock(blockLength, false, Linner, Binner, transL, false);
/*     */ 
/*     */       
/* 341 */       if (transL) {
/* 342 */         updateY = (Linner.row0 > 0);
/*     */       } else {
/* 344 */         updateY = (Linner.row1 < L.row1);
/*     */       } 
/* 346 */       if (updateY) {
/*     */ 
/*     */ 
/*     */         
/* 350 */         if (transL) {
/* 351 */           Linner.col1 = Linner.col0;
/* 352 */           Linner.col0 = Linner.col1 - blockLength;
/* 353 */           Linner.row1 = L.row1;
/*     */ 
/*     */ 
/*     */           
/* 357 */           Binner.row1 = B.row1;
/*     */           
/* 359 */           Binner.row0 -= blockLength;
/* 360 */           Y.row1 = Binner.row0;
/*     */         } else {
/* 362 */           Linner.row0 = Linner.row1;
/* 363 */           Linner.row1 = Math.min(Linner.row0 + blockLength, L.row1);
/* 364 */           Linner.col0 = L.col0;
/*     */ 
/*     */           
/* 367 */           Binner.row0 = B.row0;
/*     */ 
/*     */           
/* 370 */           Y.row0 = Binner.row1;
/* 371 */           Y.row1 = Math.min(Y.row0 + blockLength, B.row1);
/*     */         } 
/*     */         
/*     */         int k;
/* 375 */         for (k = B.col0; k < B.col1; k += blockLength) {
/*     */           
/* 377 */           Binner.col0 = k;
/* 378 */           Binner.col1 = Math.min(k + blockLength, B.col1);
/*     */           
/* 380 */           Y.col0 = Binner.col0;
/* 381 */           Y.col1 = Binner.col1;
/*     */           
/* 383 */           if (transL) {
/*     */             
/* 385 */             BlockMultiplication.multMinusTransA(blockLength, Linner, Binner, Y);
/*     */           }
/*     */           else {
/*     */             
/* 389 */             BlockMultiplication.multMinus(blockLength, Linner, Binner, Y);
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
/*     */   public static void solveR(int blockLength, D1Submatrix64F R, D1Submatrix64F B, boolean transR) {
/* 418 */     int startI, stepI, lengthR = B.row1 - B.row0;
/* 419 */     if (R.getCols() != lengthR)
/* 420 */       throw new IllegalArgumentException("Number of columns in R must be equal to the number of rows in B"); 
/* 421 */     if (R.getRows() != lengthR) {
/* 422 */       throw new IllegalArgumentException("Number of rows in R must be equal to the number of rows in B");
/*     */     }
/*     */     
/* 425 */     D1Submatrix64F Y = new D1Submatrix64F(B.original);
/*     */     
/* 427 */     D1Submatrix64F Rinner = new D1Submatrix64F(R.original);
/* 428 */     D1Submatrix64F Binner = new D1Submatrix64F(B.original);
/*     */ 
/*     */ 
/*     */     
/* 432 */     if (transR) {
/* 433 */       startI = 0;
/* 434 */       stepI = blockLength;
/*     */     } else {
/* 436 */       startI = lengthR - lengthR % blockLength;
/* 437 */       if (startI == lengthR && lengthR >= blockLength) {
/* 438 */         startI -= blockLength;
/*     */       }
/* 440 */       stepI = -blockLength;
/*     */     } 
/*     */     
/* 443 */     int i = startI;
/* 444 */     for (; transR ? (
/* 445 */       i >= lengthR) : (
/*     */       
/* 447 */       i < 0); i += stepI) {
/*     */       boolean updateY;
/*     */ 
/*     */       
/* 451 */       int widthT = Math.min(blockLength, lengthR - i);
/*     */       
/* 453 */       R.col0 += i; Rinner.col1 = Rinner.col0 + widthT;
/* 454 */       R.row0 += i; Rinner.row1 = Rinner.row0 + widthT;
/*     */       
/* 456 */       Binner.col0 = B.col0; Binner.col1 = B.col1;
/* 457 */       B.row0 += i; Binner.row1 = Binner.row0 + widthT;
/*     */ 
/*     */ 
/*     */       
/* 461 */       solveBlock(blockLength, true, Rinner, Binner, transR, false);
/*     */ 
/*     */       
/* 464 */       if (transR) {
/* 465 */         updateY = (Rinner.row1 < R.row1);
/*     */       } else {
/* 467 */         updateY = (Rinner.row0 > 0);
/*     */       } 
/* 469 */       if (updateY) {
/*     */ 
/*     */ 
/*     */         
/* 473 */         if (transR) {
/* 474 */           Rinner.col0 = Rinner.col1;
/* 475 */           Rinner.col1 = Math.min(Rinner.col0 + blockLength, R.col1);
/* 476 */           Rinner.row0 = R.row0;
/*     */ 
/*     */           
/* 479 */           Binner.row0 = B.row0;
/*     */ 
/*     */           
/* 482 */           Y.row0 = Binner.row1;
/* 483 */           Y.row1 = Math.min(Y.row0 + blockLength, B.row1);
/*     */         } else {
/* 485 */           Rinner.row1 = Rinner.row0;
/* 486 */           Rinner.row0 = Rinner.row1 - blockLength;
/* 487 */           Rinner.col1 = R.col1;
/*     */ 
/*     */           
/* 490 */           Binner.row1 = B.row1;
/*     */           
/* 492 */           Binner.row0 -= blockLength;
/* 493 */           Y.row1 = Binner.row0;
/*     */         } 
/*     */         
/*     */         int k;
/* 497 */         for (k = B.col0; k < B.col1; k += blockLength) {
/*     */           
/* 499 */           Binner.col0 = k;
/* 500 */           Binner.col1 = Math.min(k + blockLength, B.col1);
/*     */           
/* 502 */           Y.col0 = Binner.col0;
/* 503 */           Y.col1 = Binner.col1;
/*     */           
/* 505 */           if (transR) {
/*     */             
/* 507 */             BlockMultiplication.multMinusTransA(blockLength, Rinner, Binner, Y);
/*     */           } else {
/*     */             
/* 510 */             BlockMultiplication.multMinus(blockLength, Rinner, Binner, Y);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockTriangularSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */