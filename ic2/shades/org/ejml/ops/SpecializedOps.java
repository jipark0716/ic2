/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpecializedOps
/*     */ {
/*     */   public static DenseMatrix64F createReflector(RowD1Matrix64F u) {
/*  50 */     if (!MatrixFeatures.isVector((D1Matrix64F)u)) {
/*  51 */       throw new IllegalArgumentException("u must be a vector");
/*     */     }
/*  53 */     double norm = NormOps.fastNormF((D1Matrix64F)u);
/*  54 */     double gamma = -2.0D / norm * norm;
/*     */     
/*  56 */     DenseMatrix64F Q = CommonOps.identity(u.getNumElements());
/*  57 */     CommonOps.multAddTransB(gamma, u, u, (RowD1Matrix64F)Q);
/*     */     
/*  59 */     return Q;
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
/*     */   public static DenseMatrix64F createReflector(DenseMatrix64F u, double gamma) {
/*  79 */     if (!MatrixFeatures.isVector((D1Matrix64F)u)) {
/*  80 */       throw new IllegalArgumentException("u must be a vector");
/*     */     }
/*  82 */     DenseMatrix64F Q = CommonOps.identity(u.getNumElements());
/*  83 */     CommonOps.multAddTransB(-gamma, (RowD1Matrix64F)u, (RowD1Matrix64F)u, (RowD1Matrix64F)Q);
/*     */     
/*  85 */     return Q;
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
/*     */   public static DenseMatrix64F copyChangeRow(int[] order, DenseMatrix64F src, DenseMatrix64F dst) {
/*  97 */     if (dst == null) {
/*  98 */       dst = new DenseMatrix64F(src.numRows, src.numCols);
/*  99 */     } else if (src.numRows != dst.numRows || src.numCols != dst.numCols) {
/* 100 */       throw new IllegalArgumentException("src and dst must have the same dimensions.");
/*     */     } 
/*     */     
/* 103 */     for (int i = 0; i < src.numRows; i++) {
/* 104 */       int indexDst = i * src.numCols;
/* 105 */       int indexSrc = order[i] * src.numCols;
/*     */       
/* 107 */       System.arraycopy(src.data, indexSrc, dst.data, indexDst, src.numCols);
/*     */     } 
/*     */     
/* 110 */     return dst;
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
/*     */   public static DenseMatrix64F copyTriangle(DenseMatrix64F src, DenseMatrix64F dst, boolean upper) {
/* 122 */     if (dst == null) {
/* 123 */       dst = new DenseMatrix64F(src.numRows, src.numCols);
/* 124 */     } else if (src.numRows != dst.numRows || src.numCols != dst.numCols) {
/* 125 */       throw new IllegalArgumentException("src and dst must have the same dimensions.");
/*     */     } 
/*     */     
/* 128 */     if (upper) {
/* 129 */       int N = Math.min(src.numRows, src.numCols);
/* 130 */       for (int i = 0; i < N; i++) {
/* 131 */         int index = i * src.numCols + i;
/* 132 */         System.arraycopy(src.data, index, dst.data, index, src.numCols - i);
/*     */       } 
/*     */     } else {
/* 135 */       for (int i = 0; i < src.numRows; i++) {
/* 136 */         int length = Math.min(i + 1, src.numCols);
/* 137 */         int index = i * src.numCols;
/* 138 */         System.arraycopy(src.data, index, dst.data, index, length);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return dst;
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
/*     */   public static double diffNormF(D1Matrix64F a, D1Matrix64F b) {
/* 164 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 165 */       throw new IllegalArgumentException("Both matrices must have the same shape.");
/*     */     }
/*     */     
/* 168 */     int size = a.getNumElements();
/*     */     
/* 170 */     DenseMatrix64F diff = new DenseMatrix64F(size, 1);
/*     */     
/* 172 */     for (int i = 0; i < size; i++) {
/* 173 */       diff.set(i, b.get(i) - a.get(i));
/*     */     }
/* 175 */     return NormOps.normF((D1Matrix64F)diff);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double diffNormF_fast(D1Matrix64F a, D1Matrix64F b) {
/* 180 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 181 */       throw new IllegalArgumentException("Both matrices must have the same shape.");
/*     */     }
/*     */     
/* 184 */     int size = a.getNumElements();
/*     */     
/* 186 */     double total = 0.0D;
/* 187 */     for (int i = 0; i < size; i++) {
/* 188 */       double diff = b.get(i) - a.get(i);
/* 189 */       total += diff * diff;
/*     */     } 
/* 191 */     return Math.sqrt(total);
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
/*     */   public static double diffNormP1(D1Matrix64F a, D1Matrix64F b) {
/* 213 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 214 */       throw new IllegalArgumentException("Both matrices must have the same shape.");
/*     */     }
/*     */     
/* 217 */     int size = a.getNumElements();
/*     */     
/* 219 */     double total = 0.0D;
/* 220 */     for (int i = 0; i < size; i++) {
/* 221 */       total += Math.abs(b.get(i) - a.get(i));
/*     */     }
/* 223 */     return total;
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
/*     */   public static void addIdentity(RowD1Matrix64F A, RowD1Matrix64F B, double alpha) {
/* 239 */     if (A.numCols != A.numRows)
/* 240 */       throw new IllegalArgumentException("A must be square"); 
/* 241 */     if (B.numCols != A.numCols || B.numRows != A.numRows) {
/* 242 */       throw new IllegalArgumentException("B must be the same shape as A");
/*     */     }
/* 244 */     int n = A.numCols;
/*     */     
/* 246 */     int index = 0;
/* 247 */     for (int i = 0; i < n; i++) {
/* 248 */       for (int j = 0; j < n; j++, index++) {
/* 249 */         if (i == j) {
/* 250 */           B.set(index, A.get(index) + alpha);
/*     */         } else {
/* 252 */           B.set(index, A.get(index));
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
/*     */   public static void subvector(RowD1Matrix64F A, int rowA, int colA, int length, boolean row, int offsetV, RowD1Matrix64F v) {
/* 274 */     if (row) {
/* 275 */       for (int i = 0; i < length; i++) {
/* 276 */         v.set(offsetV + i, A.get(rowA, colA + i));
/*     */       }
/*     */     } else {
/* 279 */       for (int i = 0; i < length; i++) {
/* 280 */         v.set(offsetV + i, A.get(rowA + i, colA));
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
/*     */   public static DenseMatrix64F[] splitIntoVectors(RowD1Matrix64F A, boolean column) {
/* 294 */     int w = column ? A.numCols : A.numRows;
/*     */     
/* 296 */     int M = column ? A.numRows : 1;
/* 297 */     int N = column ? 1 : A.numCols;
/*     */     
/* 299 */     int o = Math.max(M, N);
/*     */     
/* 301 */     DenseMatrix64F[] ret = new DenseMatrix64F[w];
/*     */     
/* 303 */     for (int i = 0; i < w; i++) {
/* 304 */       DenseMatrix64F a = new DenseMatrix64F(M, N);
/*     */       
/* 306 */       if (column) {
/* 307 */         subvector(A, 0, i, o, false, 0, (RowD1Matrix64F)a);
/*     */       } else {
/* 309 */         subvector(A, i, 0, o, true, 0, (RowD1Matrix64F)a);
/*     */       } 
/* 311 */       ret[i] = a;
/*     */     } 
/*     */     
/* 314 */     return ret;
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
/*     */   public static DenseMatrix64F pivotMatrix(DenseMatrix64F ret, int[] pivots, int numPivots, boolean transposed) {
/* 335 */     if (ret == null) {
/* 336 */       ret = new DenseMatrix64F(numPivots, numPivots);
/*     */     } else {
/* 338 */       if (ret.numCols != numPivots || ret.numRows != numPivots)
/* 339 */         throw new IllegalArgumentException("Unexpected matrix dimension"); 
/* 340 */       CommonOps.fill((D1Matrix64F)ret, 0.0D);
/*     */     } 
/*     */     
/* 343 */     if (transposed) {
/* 344 */       for (int i = 0; i < numPivots; i++) {
/* 345 */         ret.set(pivots[i], i, 1.0D);
/*     */       }
/*     */     } else {
/* 348 */       for (int i = 0; i < numPivots; i++) {
/* 349 */         ret.set(i, pivots[i], 1.0D);
/*     */       }
/*     */     } 
/*     */     
/* 353 */     return ret;
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
/*     */   public static double diagProd(RowD1Matrix64F T) {
/* 365 */     double prod = 1.0D;
/* 366 */     int N = Math.min(T.numRows, T.numCols);
/* 367 */     for (int i = 0; i < N; i++) {
/* 368 */       prod *= T.unsafe_get(i, i);
/*     */     }
/*     */     
/* 371 */     return prod;
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
/*     */   public static double qualityTriangular(boolean upper, D1Matrix64F T) {
/* 387 */     int N = Math.min(T.numRows, T.numCols);
/*     */ 
/*     */     
/* 390 */     double max = CommonOps.elementMaxAbs(T);
/*     */     
/* 392 */     if (max == 0.0D) {
/* 393 */       return 0.0D;
/*     */     }
/* 395 */     double quality = 1.0D;
/* 396 */     for (int i = 0; i < N; i++) {
/* 397 */       quality *= T.unsafe_get(i, i) / max;
/*     */     }
/*     */     
/* 400 */     return Math.abs(quality);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double elementSumSq(D1Matrix64F m) {
/* 411 */     double total = 0.0D;
/*     */     
/* 413 */     int N = m.getNumElements();
/* 414 */     for (int i = 0; i < N; i++) {
/* 415 */       double d = m.data[i];
/* 416 */       total += d * d;
/*     */     } 
/*     */     
/* 419 */     return total;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\SpecializedOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */