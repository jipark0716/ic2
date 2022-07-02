/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.SingularValueDecomposition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SingularOps
/*     */ {
/*     */   public static void descendingOrder(DenseMatrix64F U, boolean tranU, DenseMatrix64F W, DenseMatrix64F V, boolean tranV) {
/*  54 */     int numSingular = Math.min(W.numRows, W.numCols);
/*     */     
/*  56 */     checkSvdMatrixSize(U, tranU, W, V, tranV);
/*     */     
/*  58 */     for (int i = 0; i < numSingular; i++) {
/*  59 */       double bigValue = -1.0D;
/*  60 */       int bigIndex = -1;
/*     */ 
/*     */       
/*  63 */       for (int j = i; j < numSingular; j++) {
/*  64 */         double v = W.get(j, j);
/*     */         
/*  66 */         if (v > bigValue) {
/*  67 */           bigValue = v;
/*  68 */           bigIndex = j;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  73 */       if (bigIndex != i) {
/*     */ 
/*     */         
/*  76 */         if (bigIndex == -1) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/*  81 */         double tmp = W.get(i, i);
/*  82 */         W.set(i, i, bigValue);
/*  83 */         W.set(bigIndex, bigIndex, tmp);
/*     */         
/*  85 */         if (V != null) {
/*  86 */           swapRowOrCol(V, tranV, i, bigIndex);
/*     */         }
/*     */         
/*  89 */         if (U != null) {
/*  90 */           swapRowOrCol(U, tranU, i, bigIndex);
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
/*     */   public static void descendingOrder(DenseMatrix64F U, boolean tranU, double[] singularValues, int numSingularValues, DenseMatrix64F V, boolean tranV) {
/* 115 */     for (int i = 0; i < numSingularValues; i++) {
/* 116 */       double bigValue = -1.0D;
/* 117 */       int bigIndex = -1;
/*     */ 
/*     */       
/* 120 */       for (int j = i; j < numSingularValues; j++) {
/* 121 */         double v = singularValues[j];
/*     */         
/* 123 */         if (v > bigValue) {
/* 124 */           bigValue = v;
/* 125 */           bigIndex = j;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 130 */       if (bigIndex != i) {
/*     */ 
/*     */         
/* 133 */         if (bigIndex == -1) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 138 */         double tmp = singularValues[i];
/* 139 */         singularValues[i] = bigValue;
/* 140 */         singularValues[bigIndex] = tmp;
/*     */         
/* 142 */         if (V != null) {
/* 143 */           swapRowOrCol(V, tranV, i, bigIndex);
/*     */         }
/*     */         
/* 146 */         if (U != null) {
/* 147 */           swapRowOrCol(U, tranU, i, bigIndex);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkSvdMatrixSize(DenseMatrix64F U, boolean tranU, DenseMatrix64F W, DenseMatrix64F V, boolean tranV) {
/* 157 */     int numSingular = Math.min(W.numRows, W.numCols);
/* 158 */     boolean compact = (W.numRows == W.numCols);
/*     */     
/* 160 */     if (compact) {
/* 161 */       if (U != null) {
/* 162 */         if (tranU && U.numRows != numSingular)
/* 163 */           throw new IllegalArgumentException("Unexpected size of matrix U"); 
/* 164 */         if (!tranU && U.numCols != numSingular) {
/* 165 */           throw new IllegalArgumentException("Unexpected size of matrix U");
/*     */         }
/*     */       } 
/* 168 */       if (V != null) {
/* 169 */         if (tranV && V.numRows != numSingular)
/* 170 */           throw new IllegalArgumentException("Unexpected size of matrix V"); 
/* 171 */         if (!tranV && V.numCols != numSingular)
/* 172 */           throw new IllegalArgumentException("Unexpected size of matrix V"); 
/*     */       } 
/*     */     } else {
/* 175 */       if (U != null && U.numRows != U.numCols)
/* 176 */         throw new IllegalArgumentException("Unexpected size of matrix U"); 
/* 177 */       if (V != null && V.numRows != V.numCols)
/* 178 */         throw new IllegalArgumentException("Unexpected size of matrix V"); 
/* 179 */       if (U != null && U.numRows != W.numRows)
/* 180 */         throw new IllegalArgumentException("Unexpected size of W"); 
/* 181 */       if (V != null && V.numRows != W.numCols) {
/* 182 */         throw new IllegalArgumentException("Unexpected size of W");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void swapRowOrCol(DenseMatrix64F M, boolean tran, int i, int bigIndex) {
/* 188 */     if (tran) {
/*     */       
/* 190 */       for (int col = 0; col < M.numCols; col++) {
/* 191 */         double tmp = M.get(i, col);
/* 192 */         M.set(i, col, M.get(bigIndex, col));
/* 193 */         M.set(bigIndex, col, tmp);
/*     */       } 
/*     */     } else {
/*     */       
/* 197 */       for (int row = 0; row < M.numRows; row++) {
/* 198 */         double tmp = M.get(row, i);
/* 199 */         M.set(row, i, M.get(row, bigIndex));
/* 200 */         M.set(row, bigIndex, tmp);
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
/*     */   public static DenseMatrix64F nullSpace(SingularValueDecomposition<DenseMatrix64F> svd, DenseMatrix64F nullSpace, double tol) {
/* 224 */     int N = svd.numberOfSingularValues();
/* 225 */     double[] s = svd.getSingularValues();
/*     */     
/* 227 */     DenseMatrix64F V = (DenseMatrix64F)svd.getV(null, true);
/*     */     
/* 229 */     if (V.numRows != svd.numCols()) {
/* 230 */       throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
/*     */     }
/*     */ 
/*     */     
/* 234 */     int numVectors = svd.numCols() - N;
/*     */     
/* 236 */     for (int i = 0; i < N; i++) {
/* 237 */       if (s[i] <= tol) {
/* 238 */         numVectors++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 243 */     if (nullSpace == null) {
/* 244 */       nullSpace = new DenseMatrix64F(numVectors, svd.numCols());
/*     */     } else {
/* 246 */       nullSpace.reshape(numVectors, svd.numCols());
/*     */     } 
/*     */ 
/*     */     
/* 250 */     int count = 0; int j;
/* 251 */     for (j = 0; j < N; j++) {
/* 252 */       if (s[j] <= tol) {
/* 253 */         CommonOps.extract((ReshapeMatrix64F)V, j, j + 1, 0, V.numCols, (ReshapeMatrix64F)nullSpace, count++, 0);
/*     */       }
/*     */     } 
/* 256 */     for (j = N; j < svd.numCols(); j++) {
/* 257 */       CommonOps.extract((ReshapeMatrix64F)V, j, j + 1, 0, V.numCols, (ReshapeMatrix64F)nullSpace, count++, 0);
/*     */     }
/*     */     
/* 260 */     CommonOps.transpose(nullSpace);
/*     */     
/* 262 */     return nullSpace;
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
/*     */   public static DenseMatrix64F nullVector(SingularValueDecomposition<DenseMatrix64F> svd, boolean isRight, DenseMatrix64F nullVector) {
/* 281 */     int N = svd.numberOfSingularValues();
/* 282 */     double[] s = svd.getSingularValues();
/*     */     
/* 284 */     DenseMatrix64F A = isRight ? (DenseMatrix64F)svd.getV(null, true) : (DenseMatrix64F)svd.getU(null, false);
/*     */     
/* 286 */     if (isRight) {
/* 287 */       if (A.numRows != svd.numCols()) {
/* 288 */         throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
/*     */       }
/*     */       
/* 291 */       if (nullVector == null) {
/* 292 */         nullVector = new DenseMatrix64F(svd.numCols(), 1);
/*     */       }
/*     */     } else {
/* 295 */       if (A.numCols != svd.numRows()) {
/* 296 */         throw new IllegalArgumentException("Can't compute the null space using a compact SVD for a matrix of this size.");
/*     */       }
/*     */       
/* 299 */       if (nullVector == null) {
/* 300 */         nullVector = new DenseMatrix64F(svd.numRows(), 1);
/*     */       }
/*     */     } 
/*     */     
/* 304 */     int smallestIndex = -1;
/*     */     
/* 306 */     if (isRight && svd.numCols() > svd.numRows()) {
/* 307 */       smallestIndex = svd.numCols() - 1;
/* 308 */     } else if (!isRight && svd.numCols() < svd.numRows()) {
/* 309 */       smallestIndex = svd.numRows() - 1;
/*     */     } else {
/*     */       
/* 312 */       double smallestValue = Double.MAX_VALUE;
/*     */       
/* 314 */       for (int i = 0; i < N; i++) {
/* 315 */         if (s[i] < smallestValue) {
/* 316 */           smallestValue = s[i];
/* 317 */           smallestIndex = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 323 */     if (isRight) {
/* 324 */       SpecializedOps.subvector((RowD1Matrix64F)A, smallestIndex, 0, A.numRows, true, 0, (RowD1Matrix64F)nullVector);
/*     */     } else {
/* 326 */       SpecializedOps.subvector((RowD1Matrix64F)A, 0, smallestIndex, A.numRows, false, 0, (RowD1Matrix64F)nullVector);
/*     */     } 
/* 328 */     return nullVector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rank(SingularValueDecomposition svd, double threshold) {
/* 339 */     int numRank = 0;
/*     */     
/* 341 */     double[] w = svd.getSingularValues();
/*     */     
/* 343 */     int N = svd.numberOfSingularValues();
/*     */     
/* 345 */     for (int j = 0; j < N; j++) {
/* 346 */       if (w[j] > threshold) {
/* 347 */         numRank++;
/*     */       }
/*     */     } 
/* 350 */     return numRank;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nullity(SingularValueDecomposition svd, double threshold) {
/* 361 */     int ret = 0;
/*     */     
/* 363 */     double[] w = svd.getSingularValues();
/*     */     
/* 365 */     int N = svd.numberOfSingularValues();
/*     */     
/* 367 */     int numCol = svd.numCols();
/*     */     
/* 369 */     for (int j = 0; j < N; j++) {
/* 370 */       if (w[j] <= threshold) ret++; 
/*     */     } 
/* 372 */     return ret + numCol - N;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\SingularOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */