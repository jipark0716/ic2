/*     */ package ic2.shades.org.ejml.alg.block;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ import ic2.shades.org.ejml.ops.MatrixFeatures;
/*     */ import ic2.shades.org.ejml.ops.RandomMatrices;
/*     */ import ic2.shades.org.ejml.simple.SimpleMatrix;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockMatrixOps
/*     */ {
/*     */   public static void convert(DenseMatrix64F src, BlockMatrix64F dst) {
/*  47 */     if (src.numRows != dst.numRows || src.numCols != dst.numCols) {
/*  48 */       throw new IllegalArgumentException("Must be the same size.");
/*     */     }
/*  50 */     for (int i = 0; i < dst.numRows; i += dst.blockLength) {
/*  51 */       int blockHeight = Math.min(dst.blockLength, dst.numRows - i);
/*     */       int j;
/*  53 */       for (j = 0; j < dst.numCols; j += dst.blockLength) {
/*  54 */         int blockWidth = Math.min(dst.blockLength, dst.numCols - j);
/*     */         
/*  56 */         int indexDst = i * dst.numCols + blockHeight * j;
/*  57 */         int indexSrcRow = i * dst.numCols + j;
/*     */         
/*  59 */         for (int k = 0; k < blockHeight; k++) {
/*  60 */           System.arraycopy(src.data, indexSrcRow, dst.data, indexDst, blockWidth);
/*  61 */           indexDst += blockWidth;
/*  62 */           indexSrcRow += dst.numCols;
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
/*     */   public static void convertRowToBlock(int numRows, int numCols, int blockLength, double[] data, double[] tmp) {
/*  82 */     int minLength = Math.min(blockLength, numRows) * numCols;
/*  83 */     if (tmp.length < minLength) {
/*  84 */       throw new IllegalArgumentException("tmp must be at least " + minLength + " long ");
/*     */     }
/*     */     int i;
/*  87 */     for (i = 0; i < numRows; i += blockLength) {
/*  88 */       int blockHeight = Math.min(blockLength, numRows - i);
/*     */       
/*  90 */       System.arraycopy(data, i * numCols, tmp, 0, blockHeight * numCols);
/*     */       
/*     */       int j;
/*  93 */       for (j = 0; j < numCols; j += blockLength) {
/*  94 */         int blockWidth = Math.min(blockLength, numCols - j);
/*     */         
/*  96 */         int indexDst = i * numCols + blockHeight * j;
/*  97 */         int indexSrcRow = j;
/*     */         
/*  99 */         for (int k = 0; k < blockHeight; k++) {
/* 100 */           System.arraycopy(tmp, indexSrcRow, data, indexDst, blockWidth);
/* 101 */           indexDst += blockWidth;
/* 102 */           indexSrcRow += numCols;
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
/*     */   public static DenseMatrix64F convert(BlockMatrix64F src, DenseMatrix64F dst) {
/* 116 */     if (dst != null) {
/* 117 */       if (dst.numRows != src.numRows || dst.numCols != src.numCols)
/* 118 */         throw new IllegalArgumentException("Must be the same size."); 
/*     */     } else {
/* 120 */       dst = new DenseMatrix64F(src.numRows, src.numCols);
/*     */     } 
/*     */     
/* 123 */     for (int i = 0; i < src.numRows; i += src.blockLength) {
/* 124 */       int blockHeight = Math.min(src.blockLength, src.numRows - i);
/*     */       int j;
/* 126 */       for (j = 0; j < src.numCols; j += src.blockLength) {
/* 127 */         int blockWidth = Math.min(src.blockLength, src.numCols - j);
/*     */         
/* 129 */         int indexSrc = i * src.numCols + blockHeight * j;
/* 130 */         int indexDstRow = i * dst.numCols + j;
/*     */         
/* 132 */         for (int k = 0; k < blockHeight; k++) {
/* 133 */           System.arraycopy(src.data, indexSrc, dst.data, indexDstRow, blockWidth);
/* 134 */           indexSrc += blockWidth;
/* 135 */           indexDstRow += dst.numCols;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     return dst;
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
/*     */   public static void convertBlockToRow(int numRows, int numCols, int blockLength, double[] data, double[] tmp) {
/* 157 */     int minLength = Math.min(blockLength, numRows) * numCols;
/* 158 */     if (tmp.length < minLength) {
/* 159 */       throw new IllegalArgumentException("tmp must be at least " + minLength + " long and not " + tmp.length);
/*     */     }
/*     */     int i;
/* 162 */     for (i = 0; i < numRows; i += blockLength) {
/* 163 */       int blockHeight = Math.min(blockLength, numRows - i);
/*     */       
/* 165 */       System.arraycopy(data, i * numCols, tmp, 0, blockHeight * numCols);
/*     */       int j;
/* 167 */       for (j = 0; j < numCols; j += blockLength) {
/* 168 */         int blockWidth = Math.min(blockLength, numCols - j);
/*     */         
/* 170 */         int indexSrc = blockHeight * j;
/* 171 */         int indexDstRow = i * numCols + j;
/*     */         
/* 173 */         for (int k = 0; k < blockHeight; k++) {
/* 174 */           System.arraycopy(tmp, indexSrc, data, indexDstRow, blockWidth);
/* 175 */           indexSrc += blockWidth;
/* 176 */           indexDstRow += numCols;
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
/*     */   public static void convertTranSrc(DenseMatrix64F src, BlockMatrix64F dst) {
/* 190 */     if (src.numRows != dst.numCols || src.numCols != dst.numRows) {
/* 191 */       throw new IllegalArgumentException("Incompatible matrix shapes.");
/*     */     }
/* 193 */     for (int i = 0; i < dst.numRows; i += dst.blockLength) {
/* 194 */       int blockHeight = Math.min(dst.blockLength, dst.numRows - i);
/*     */       int j;
/* 196 */       for (j = 0; j < dst.numCols; j += dst.blockLength) {
/* 197 */         int blockWidth = Math.min(dst.blockLength, dst.numCols - j);
/*     */         
/* 199 */         int indexDst = i * dst.numCols + blockHeight * j;
/* 200 */         int indexSrc = j * src.numCols + i;
/*     */         
/* 202 */         for (int l = 0; l < blockWidth; l++) {
/* 203 */           int rowSrc = indexSrc + l * src.numCols;
/* 204 */           int rowDst = indexDst + l;
/* 205 */           for (int k = 0; k < blockHeight; k++, rowDst += blockWidth) {
/* 206 */             dst.data[rowDst] = src.data[rowSrc++];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mult(BlockMatrix64F A, BlockMatrix64F B, BlockMatrix64F C) {
/* 217 */     if (A.numCols != B.numRows)
/* 218 */       throw new IllegalArgumentException("Columns in A are incompatible with rows in B"); 
/* 219 */     if (A.numRows != C.numRows)
/* 220 */       throw new IllegalArgumentException("Rows in A are incompatible with rows in C"); 
/* 221 */     if (B.numCols != C.numCols)
/* 222 */       throw new IllegalArgumentException("Columns in B are incompatible with columns in C"); 
/* 223 */     if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
/* 224 */       throw new IllegalArgumentException("Block lengths are not all the same.");
/*     */     }
/* 226 */     int blockLength = A.blockLength;
/*     */     
/* 228 */     D1Submatrix64F Asub = new D1Submatrix64F((D1Matrix64F)A, 0, A.numRows, 0, A.numCols);
/* 229 */     D1Submatrix64F Bsub = new D1Submatrix64F((D1Matrix64F)B, 0, B.numRows, 0, B.numCols);
/* 230 */     D1Submatrix64F Csub = new D1Submatrix64F((D1Matrix64F)C, 0, C.numRows, 0, C.numCols);
/*     */     
/* 232 */     BlockMultiplication.mult(blockLength, Asub, Bsub, Csub);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void multTransA(BlockMatrix64F A, BlockMatrix64F B, BlockMatrix64F C) {
/* 237 */     if (A.numRows != B.numRows)
/* 238 */       throw new IllegalArgumentException("Rows in A are incompatible with rows in B"); 
/* 239 */     if (A.numCols != C.numRows)
/* 240 */       throw new IllegalArgumentException("Columns in A are incompatible with rows in C"); 
/* 241 */     if (B.numCols != C.numCols)
/* 242 */       throw new IllegalArgumentException("Columns in B are incompatible with columns in C"); 
/* 243 */     if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
/* 244 */       throw new IllegalArgumentException("Block lengths are not all the same.");
/*     */     }
/* 246 */     int blockLength = A.blockLength;
/*     */     
/* 248 */     D1Submatrix64F Asub = new D1Submatrix64F((D1Matrix64F)A, 0, A.numRows, 0, A.numCols);
/* 249 */     D1Submatrix64F Bsub = new D1Submatrix64F((D1Matrix64F)B, 0, B.numRows, 0, B.numCols);
/* 250 */     D1Submatrix64F Csub = new D1Submatrix64F((D1Matrix64F)C, 0, C.numRows, 0, C.numCols);
/*     */     
/* 252 */     BlockMultiplication.multTransA(blockLength, Asub, Bsub, Csub);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void multTransB(BlockMatrix64F A, BlockMatrix64F B, BlockMatrix64F C) {
/* 257 */     if (A.numCols != B.numCols)
/* 258 */       throw new IllegalArgumentException("Columns in A are incompatible with columns in B"); 
/* 259 */     if (A.numRows != C.numRows)
/* 260 */       throw new IllegalArgumentException("Rows in A are incompatible with rows in C"); 
/* 261 */     if (B.numRows != C.numCols)
/* 262 */       throw new IllegalArgumentException("Rows in B are incompatible with columns in C"); 
/* 263 */     if (A.blockLength != B.blockLength || A.blockLength != C.blockLength) {
/* 264 */       throw new IllegalArgumentException("Block lengths are not all the same.");
/*     */     }
/* 266 */     int blockLength = A.blockLength;
/*     */     
/* 268 */     D1Submatrix64F Asub = new D1Submatrix64F((D1Matrix64F)A, 0, A.numRows, 0, A.numCols);
/* 269 */     D1Submatrix64F Bsub = new D1Submatrix64F((D1Matrix64F)B, 0, B.numRows, 0, B.numCols);
/* 270 */     D1Submatrix64F Csub = new D1Submatrix64F((D1Matrix64F)C, 0, C.numRows, 0, C.numCols);
/*     */     
/* 272 */     BlockMultiplication.multTransB(blockLength, Asub, Bsub, Csub);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockMatrix64F transpose(BlockMatrix64F A, BlockMatrix64F A_tran) {
/* 283 */     if (A_tran != null) {
/* 284 */       if (A.numRows != A_tran.numCols || A.numCols != A_tran.numRows)
/* 285 */         throw new IllegalArgumentException("Incompatible dimensions."); 
/* 286 */       if (A.blockLength != A_tran.blockLength)
/* 287 */         throw new IllegalArgumentException("Incompatible block size."); 
/*     */     } else {
/* 289 */       A_tran = new BlockMatrix64F(A.numCols, A.numRows, A.blockLength);
/*     */     } 
/*     */ 
/*     */     
/* 293 */     for (int i = 0; i < A.numRows; i += A.blockLength) {
/* 294 */       int blockHeight = Math.min(A.blockLength, A.numRows - i);
/*     */       int j;
/* 296 */       for (j = 0; j < A.numCols; j += A.blockLength) {
/* 297 */         int blockWidth = Math.min(A.blockLength, A.numCols - j);
/*     */         
/* 299 */         int indexA = i * A.numCols + blockHeight * j;
/* 300 */         int indexC = j * A_tran.numCols + blockWidth * i;
/*     */         
/* 302 */         transposeBlock(A, A_tran, indexA, indexC, blockWidth, blockHeight);
/*     */       } 
/*     */     } 
/*     */     
/* 306 */     return A_tran;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void transposeBlock(BlockMatrix64F A, BlockMatrix64F A_tran, int indexA, int indexC, int width, int height) {
/* 316 */     for (int i = 0; i < height; i++) {
/* 317 */       int rowIndexC = indexC + i;
/* 318 */       int rowIndexA = indexA + width * i;
/* 319 */       int end = rowIndexA + width;
/* 320 */       for (; rowIndexA < end; rowIndexC += height, rowIndexA++) {
/* 321 */         A_tran.data[rowIndexC] = A.data[rowIndexA];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockMatrix64F createRandom(int numRows, int numCols, double min, double max, Random rand) {
/* 329 */     BlockMatrix64F ret = new BlockMatrix64F(numRows, numCols);
/*     */     
/* 331 */     RandomMatrices.setRandom((D1Matrix64F)ret, min, max, rand);
/*     */     
/* 333 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockMatrix64F createRandom(int numRows, int numCols, double min, double max, Random rand, int blockLength) {
/* 340 */     BlockMatrix64F ret = new BlockMatrix64F(numRows, numCols, blockLength);
/*     */     
/* 342 */     RandomMatrices.setRandom((D1Matrix64F)ret, min, max, rand);
/*     */     
/* 344 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockMatrix64F convert(DenseMatrix64F A, int blockLength) {
/* 349 */     BlockMatrix64F ret = new BlockMatrix64F(A.numRows, A.numCols, blockLength);
/* 350 */     convert(A, ret);
/* 351 */     return ret;
/*     */   }
/*     */   
/*     */   public static BlockMatrix64F convert(DenseMatrix64F A) {
/* 355 */     BlockMatrix64F ret = new BlockMatrix64F(A.numRows, A.numCols);
/* 356 */     convert(A, ret);
/* 357 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEquals(BlockMatrix64F A, BlockMatrix64F B) {
/* 362 */     if (A.blockLength != B.blockLength) {
/* 363 */       return false;
/*     */     }
/* 365 */     return MatrixFeatures.isEquals((D1Matrix64F)A, (D1Matrix64F)B);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEquals(BlockMatrix64F A, BlockMatrix64F B, double tol) {
/* 370 */     if (A.blockLength != B.blockLength) {
/* 371 */       return false;
/*     */     }
/* 373 */     return MatrixFeatures.isEquals((D1Matrix64F)A, (D1Matrix64F)B, tol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void zeroTriangle(boolean upper, BlockMatrix64F A) {
/* 381 */     int blockLength = A.blockLength;
/*     */     
/* 383 */     if (upper) {
/* 384 */       for (int i = 0; i < A.numRows; i += blockLength) {
/* 385 */         int h = Math.min(blockLength, A.numRows - i);
/*     */         int j;
/* 387 */         for (j = i; j < A.numCols; j += blockLength) {
/* 388 */           int w = Math.min(blockLength, A.numCols - j);
/*     */           
/* 390 */           int index = i * A.numCols + h * j;
/*     */           
/* 392 */           if (j == i) {
/* 393 */             for (int k = 0; k < h; k++) {
/* 394 */               for (int l = k + 1; l < w; l++) {
/* 395 */                 A.data[index + w * k + l] = 0.0D;
/*     */               }
/*     */             } 
/*     */           } else {
/* 399 */             for (int k = 0; k < h; k++) {
/* 400 */               for (int l = 0; l < w; l++) {
/* 401 */                 A.data[index + w * k + l] = 0.0D;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 408 */       for (int i = 0; i < A.numRows; i += blockLength) {
/* 409 */         int h = Math.min(blockLength, A.numRows - i);
/*     */         int j;
/* 411 */         for (j = 0; j <= i; j += blockLength) {
/* 412 */           int w = Math.min(blockLength, A.numCols - j);
/*     */           
/* 414 */           int index = i * A.numCols + h * j;
/*     */           
/* 416 */           if (j == i) {
/* 417 */             for (int k = 0; k < h; k++) {
/* 418 */               int z = Math.min(k, w);
/* 419 */               for (int l = 0; l < z; l++) {
/* 420 */                 A.data[index + w * k + l] = 0.0D;
/*     */               }
/*     */             } 
/*     */           } else {
/* 424 */             for (int k = 0; k < h; k++) {
/* 425 */               for (int l = 0; l < w; l++) {
/* 426 */                 A.data[index + w * k + l] = 0.0D;
/*     */               }
/*     */             } 
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
/*     */   public static void copyTriangle(boolean upper, BlockMatrix64F src, BlockMatrix64F dst) {
/* 445 */     if (src.blockLength != dst.blockLength)
/* 446 */       throw new IllegalArgumentException("Block size is different"); 
/* 447 */     if (src.numRows < dst.numRows)
/* 448 */       throw new IllegalArgumentException("The src has fewer rows than dst"); 
/* 449 */     if (src.numCols < dst.numCols) {
/* 450 */       throw new IllegalArgumentException("The src has fewer columns than dst");
/*     */     }
/* 452 */     int blockLength = src.blockLength;
/*     */     
/* 454 */     int numRows = Math.min(src.numRows, dst.numRows);
/* 455 */     int numCols = Math.min(src.numCols, dst.numCols);
/*     */     
/* 457 */     if (upper) {
/* 458 */       int i; for (i = 0; i < numRows; i += blockLength) {
/* 459 */         int heightSrc = Math.min(blockLength, src.numRows - i);
/* 460 */         int heightDst = Math.min(blockLength, dst.numRows - i);
/*     */         int j;
/* 462 */         for (j = i; j < numCols; j += blockLength) {
/* 463 */           int widthSrc = Math.min(blockLength, src.numCols - j);
/* 464 */           int widthDst = Math.min(blockLength, dst.numCols - j);
/*     */           
/* 466 */           int indexSrc = i * src.numCols + heightSrc * j;
/* 467 */           int indexDst = i * dst.numCols + heightDst * j;
/*     */           
/* 469 */           if (j == i) {
/* 470 */             for (int k = 0; k < heightDst; k++) {
/* 471 */               for (int l = k; l < widthDst; l++) {
/* 472 */                 dst.data[indexDst + widthDst * k + l] = src.data[indexSrc + widthSrc * k + l];
/*     */               }
/*     */             } 
/*     */           } else {
/* 476 */             for (int k = 0; k < heightDst; k++)
/* 477 */               System.arraycopy(src.data, indexSrc + widthSrc * k, dst.data, indexDst + widthDst * k, widthDst); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       int i;
/* 483 */       for (i = 0; i < numRows; i += blockLength) {
/* 484 */         int heightSrc = Math.min(blockLength, src.numRows - i);
/* 485 */         int heightDst = Math.min(blockLength, dst.numRows - i);
/*     */         int j;
/* 487 */         for (j = 0; j <= i; j += blockLength) {
/* 488 */           int widthSrc = Math.min(blockLength, src.numCols - j);
/* 489 */           int widthDst = Math.min(blockLength, dst.numCols - j);
/*     */           
/* 491 */           int indexSrc = i * src.numCols + heightSrc * j;
/* 492 */           int indexDst = i * dst.numCols + heightDst * j;
/*     */           
/* 494 */           if (j == i) {
/* 495 */             for (int k = 0; k < heightDst; k++) {
/* 496 */               int z = Math.min(k + 1, widthDst);
/* 497 */               for (int l = 0; l < z; l++) {
/* 498 */                 dst.data[indexDst + widthDst * k + l] = src.data[indexSrc + widthSrc * k + l];
/*     */               }
/*     */             } 
/*     */           } else {
/* 502 */             for (int k = 0; k < heightDst; k++) {
/* 503 */               System.arraycopy(src.data, indexSrc + widthSrc * k, dst.data, indexDst + widthDst * k, widthDst);
/*     */             }
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
/*     */   public static void set(BlockMatrix64F A, double value) {
/* 522 */     CommonOps.fill((D1Matrix64F)A, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity(BlockMatrix64F A) {
/* 532 */     int minLength = Math.min(A.numRows, A.numCols);
/*     */     
/* 534 */     CommonOps.fill((D1Matrix64F)A, 0.0D);
/*     */     
/* 536 */     int blockLength = A.blockLength;
/*     */     
/* 538 */     for (int i = 0; i < minLength; i += blockLength) {
/* 539 */       int h = Math.min(blockLength, A.numRows - i);
/* 540 */       int w = Math.min(blockLength, A.numCols - i);
/*     */       
/* 542 */       int index = i * A.numCols + h * i;
/*     */       
/* 544 */       int m = Math.min(h, w);
/* 545 */       for (int k = 0; k < m; k++) {
/* 546 */         A.data[index + k * w + k] = 1.0D;
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
/*     */   public static SimpleMatrix convertSimple(BlockMatrix64F A) {
/* 558 */     DenseMatrix64F B = convert(A, (DenseMatrix64F)null);
/*     */     
/* 560 */     return SimpleMatrix.wrap(B);
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
/*     */   public static BlockMatrix64F identity(int numRows, int numCols, int blockLength) {
/* 574 */     BlockMatrix64F A = new BlockMatrix64F(numRows, numCols, blockLength);
/*     */     
/* 576 */     int minLength = Math.min(numRows, numCols);
/*     */     int i;
/* 578 */     for (i = 0; i < minLength; i += blockLength) {
/* 579 */       int h = Math.min(blockLength, A.numRows - i);
/* 580 */       int w = Math.min(blockLength, A.numCols - i);
/*     */       
/* 582 */       int index = i * A.numCols + h * i;
/*     */       
/* 584 */       int m = Math.min(h, w);
/* 585 */       for (int k = 0; k < m; k++) {
/* 586 */         A.data[index + k * w + k] = 1.0D;
/*     */       }
/*     */     } 
/*     */     
/* 590 */     return A;
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
/*     */   public static void checkIdenticalShape(BlockMatrix64F A, BlockMatrix64F B) {
/* 602 */     if (A.blockLength != B.blockLength)
/* 603 */       throw new IllegalArgumentException("Block size is different"); 
/* 604 */     if (A.numRows != B.numRows)
/* 605 */       throw new IllegalArgumentException("Number of rows is different"); 
/* 606 */     if (A.numCols != B.numCols) {
/* 607 */       throw new IllegalArgumentException("NUmber of columns is different");
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
/*     */   public static void extractAligned(BlockMatrix64F src, BlockMatrix64F dst) {
/* 621 */     if (src.blockLength != dst.blockLength)
/* 622 */       throw new IllegalArgumentException("Block size is different"); 
/* 623 */     if (src.numRows < dst.numRows)
/* 624 */       throw new IllegalArgumentException("The src has fewer rows than dst"); 
/* 625 */     if (src.numCols < dst.numCols) {
/* 626 */       throw new IllegalArgumentException("The src has fewer columns than dst");
/*     */     }
/* 628 */     int blockLength = src.blockLength;
/*     */     
/* 630 */     int numRows = Math.min(src.numRows, dst.numRows);
/* 631 */     int numCols = Math.min(src.numCols, dst.numCols);
/*     */     int i;
/* 633 */     for (i = 0; i < numRows; i += blockLength) {
/* 634 */       int heightSrc = Math.min(blockLength, src.numRows - i);
/* 635 */       int heightDst = Math.min(blockLength, dst.numRows - i);
/*     */       int j;
/* 637 */       for (j = 0; j < numCols; j += blockLength) {
/* 638 */         int widthSrc = Math.min(blockLength, src.numCols - j);
/* 639 */         int widthDst = Math.min(blockLength, dst.numCols - j);
/*     */         
/* 641 */         int indexSrc = i * src.numCols + heightSrc * j;
/* 642 */         int indexDst = i * dst.numCols + heightDst * j;
/*     */         
/* 644 */         for (int k = 0; k < heightDst; k++) {
/* 645 */           System.arraycopy(src.data, indexSrc + widthSrc * k, dst.data, indexDst + widthDst * k, widthDst);
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
/*     */   public static boolean blockAligned(int blockLength, D1Submatrix64F A) {
/* 659 */     if (A.col0 % blockLength != 0)
/* 660 */       return false; 
/* 661 */     if (A.row0 % blockLength != 0) {
/* 662 */       return false;
/*     */     }
/* 664 */     if (A.col1 % blockLength != 0 && A.col1 != A.original.numCols) {
/* 665 */       return false;
/*     */     }
/*     */     
/* 668 */     if (A.row1 % blockLength != 0 && A.row1 != A.original.numRows) {
/* 669 */       return false;
/*     */     }
/*     */     
/* 672 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockMatrixOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */