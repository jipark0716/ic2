/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NormOps
/*     */ {
/*     */   public static void normalizeF(DenseMatrix64F A) {
/*  76 */     double val = normF((D1Matrix64F)A);
/*     */     
/*  78 */     if (val == 0.0D) {
/*     */       return;
/*     */     }
/*  81 */     int size = A.getNumElements();
/*     */     
/*  83 */     for (int i = 0; i < size; i++) {
/*  84 */       A.div(i, val);
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
/*     */   public static double conditionP(DenseMatrix64F A, double p) {
/* 104 */     if (p == 2.0D)
/* 105 */       return conditionP2(A); 
/* 106 */     if (A.numRows == A.numCols) {
/*     */ 
/*     */       
/* 109 */       DenseMatrix64F A_inv = new DenseMatrix64F(A.numRows, A.numCols);
/*     */       
/* 111 */       if (!CommonOps.invert(A, A_inv)) {
/* 112 */         throw new IllegalArgumentException("A can't be inverted.");
/*     */       }
/* 114 */       return normP(A, p) * normP(A_inv, p);
/*     */     } 
/* 116 */     DenseMatrix64F pinv = new DenseMatrix64F(A.numCols, A.numRows);
/* 117 */     CommonOps.pinv(A, pinv);
/*     */     
/* 119 */     return normP(A, p) * normP(pinv, p);
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
/*     */   public static double conditionP2(DenseMatrix64F A) {
/* 139 */     SingularValueDecomposition<DenseMatrix64F> svd = DecompositionFactory.svd(A.numRows, A.numCols, false, false, true);
/*     */     
/* 141 */     svd.decompose((Matrix64F)A);
/*     */     
/* 143 */     double[] singularValues = svd.getSingularValues();
/*     */     
/* 145 */     int n = SingularOps.rank(svd, 1.0E-12D);
/*     */     
/* 147 */     if (n == 0) return 0.0D;
/*     */     
/* 149 */     double smallest = Double.MAX_VALUE;
/* 150 */     double largest = Double.MIN_VALUE;
/*     */     
/* 152 */     for (double s : singularValues) {
/* 153 */       if (s < smallest)
/* 154 */         smallest = s; 
/* 155 */       if (s > largest) {
/* 156 */         largest = s;
/*     */       }
/*     */     } 
/* 159 */     return largest / smallest;
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
/*     */   public static double fastNormF(D1Matrix64F a) {
/* 172 */     double total = 0.0D;
/*     */     
/* 174 */     int size = a.getNumElements();
/*     */     
/* 176 */     for (int i = 0; i < size; i++) {
/* 177 */       double val = a.get(i);
/* 178 */       total += val * val;
/*     */     } 
/*     */     
/* 181 */     return Math.sqrt(total);
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
/*     */   public static double normF(D1Matrix64F a) {
/* 199 */     double total = 0.0D;
/*     */     
/* 201 */     double scale = CommonOps.elementMaxAbs(a);
/*     */     
/* 203 */     if (scale == 0.0D) {
/* 204 */       return 0.0D;
/*     */     }
/* 206 */     int size = a.getNumElements();
/*     */     
/* 208 */     for (int i = 0; i < size; i++) {
/* 209 */       double val = a.get(i) / scale;
/* 210 */       total += val * val;
/*     */     } 
/*     */     
/* 213 */     return scale * Math.sqrt(total);
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
/*     */   public static double elementP(RowD1Matrix64F A, double p) {
/* 232 */     if (p == 1.0D)
/* 233 */       return CommonOps.elementSumAbs((D1Matrix64F)A); 
/* 234 */     if (p == 2.0D) {
/* 235 */       return normF((D1Matrix64F)A);
/*     */     }
/* 237 */     double max = CommonOps.elementMaxAbs((D1Matrix64F)A);
/*     */     
/* 239 */     if (max == 0.0D) {
/* 240 */       return 0.0D;
/*     */     }
/* 242 */     double total = 0.0D;
/*     */     
/* 244 */     int size = A.getNumElements();
/*     */     
/* 246 */     for (int i = 0; i < size; i++) {
/* 247 */       double a = A.get(i) / max;
/*     */       
/* 249 */       total += Math.pow(Math.abs(a), p);
/*     */     } 
/*     */     
/* 252 */     return max * Math.pow(total, 1.0D / p);
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
/*     */   public static double fastElementP(D1Matrix64F A, double p) {
/* 264 */     if (p == 2.0D) {
/* 265 */       return fastNormF(A);
/*     */     }
/* 267 */     double total = 0.0D;
/*     */     
/* 269 */     int size = A.getNumElements();
/*     */     
/* 271 */     for (int i = 0; i < size; i++) {
/* 272 */       double a = A.get(i);
/*     */       
/* 274 */       total += Math.pow(Math.abs(a), p);
/*     */     } 
/*     */     
/* 277 */     return Math.pow(total, 1.0D / p);
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
/*     */   public static double normP(DenseMatrix64F A, double p) {
/* 290 */     if (p == 1.0D)
/* 291 */       return normP1(A); 
/* 292 */     if (p == 2.0D)
/* 293 */       return normP2(A); 
/* 294 */     if (Double.isInfinite(p)) {
/* 295 */       return normPInf(A);
/*     */     }
/* 297 */     if (MatrixFeatures.isVector((D1Matrix64F)A)) {
/* 298 */       return elementP((RowD1Matrix64F)A, p);
/*     */     }
/* 300 */     throw new IllegalArgumentException("Doesn't support induced norms yet.");
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
/*     */   public static double fastNormP(DenseMatrix64F A, double p) {
/* 313 */     if (p == 1.0D)
/* 314 */       return normP1(A); 
/* 315 */     if (p == 2.0D)
/* 316 */       return fastNormP2(A); 
/* 317 */     if (Double.isInfinite(p)) {
/* 318 */       return normPInf(A);
/*     */     }
/* 320 */     if (MatrixFeatures.isVector((D1Matrix64F)A)) {
/* 321 */       return fastElementP((D1Matrix64F)A, p);
/*     */     }
/* 323 */     throw new IllegalArgumentException("Doesn't support induced norms yet.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double normP1(DenseMatrix64F A) {
/* 334 */     if (MatrixFeatures.isVector((D1Matrix64F)A)) {
/* 335 */       return CommonOps.elementSumAbs((D1Matrix64F)A);
/*     */     }
/* 337 */     return inducedP1(A);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double normP2(DenseMatrix64F A) {
/* 348 */     if (MatrixFeatures.isVector((D1Matrix64F)A)) {
/* 349 */       return normF((D1Matrix64F)A);
/*     */     }
/* 351 */     return inducedP2(A);
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
/*     */   public static double fastNormP2(DenseMatrix64F A) {
/* 363 */     if (MatrixFeatures.isVector((D1Matrix64F)A)) {
/* 364 */       return fastNormF((D1Matrix64F)A);
/*     */     }
/* 366 */     return inducedP2(A);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double normPInf(DenseMatrix64F A) {
/* 377 */     if (MatrixFeatures.isVector((D1Matrix64F)A)) {
/* 378 */       return CommonOps.elementMaxAbs((D1Matrix64F)A);
/*     */     }
/* 380 */     return inducedPInf(A);
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
/*     */   public static double inducedP1(DenseMatrix64F A) {
/* 395 */     double max = 0.0D;
/*     */     
/* 397 */     int m = A.numRows;
/* 398 */     int n = A.numCols;
/*     */     
/* 400 */     for (int j = 0; j < n; j++) {
/* 401 */       double total = 0.0D;
/* 402 */       for (int i = 0; i < m; i++) {
/* 403 */         total += Math.abs(A.get(i, j));
/*     */       }
/* 405 */       if (total > max) {
/* 406 */         max = total;
/*     */       }
/*     */     } 
/*     */     
/* 410 */     return max;
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
/*     */   public static double inducedP2(DenseMatrix64F A) {
/* 422 */     SingularValueDecomposition<DenseMatrix64F> svd = DecompositionFactory.svd(A.numRows, A.numCols, false, false, true);
/*     */     
/* 424 */     if (!svd.decompose((Matrix64F)A)) {
/* 425 */       throw new RuntimeException("Decomposition failed");
/*     */     }
/* 427 */     double[] singularValues = svd.getSingularValues();
/*     */ 
/*     */     
/* 430 */     return UtilEjml.max(singularValues, 0, singularValues.length);
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
/*     */   public static double inducedPInf(DenseMatrix64F A) {
/* 444 */     double max = 0.0D;
/*     */     
/* 446 */     int m = A.numRows;
/* 447 */     int n = A.numCols;
/*     */     
/* 449 */     for (int i = 0; i < m; i++) {
/* 450 */       double total = 0.0D;
/* 451 */       for (int j = 0; j < n; j++) {
/* 452 */         total += Math.abs(A.get(i, j));
/*     */       }
/* 454 */       if (total > max) {
/* 455 */         max = total;
/*     */       }
/*     */     } 
/*     */     
/* 459 */     return max;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\NormOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */