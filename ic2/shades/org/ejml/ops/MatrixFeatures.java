/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionInner_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.mult.VectorVectorMult;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.EigenDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.LUDecomposition;
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
/*     */ public class MatrixFeatures
/*     */ {
/*     */   public static boolean hasNaN(D1Matrix64F m) {
/*  57 */     int length = m.getNumElements();
/*     */     
/*  59 */     for (int i = 0; i < length; i++) {
/*  60 */       if (Double.isNaN(m.get(i)))
/*  61 */         return true; 
/*     */     } 
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasUncountable(D1Matrix64F m) {
/*  74 */     int length = m.getNumElements();
/*     */     
/*  76 */     for (int i = 0; i < length; i++) {
/*  77 */       double a = m.get(i);
/*  78 */       if (Double.isNaN(a) || Double.isInfinite(a))
/*  79 */         return true; 
/*     */     } 
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isZeros(D1Matrix64F m, double tol) {
/*  92 */     int length = m.getNumElements();
/*     */     
/*  94 */     for (int i = 0; i < length; i++) {
/*  95 */       if (Math.abs(m.get(i)) > tol)
/*  96 */         return false; 
/*     */     } 
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVector(D1Matrix64F mat) {
/* 109 */     return (mat.numCols == 1 || mat.numRows == 1);
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
/*     */   public static boolean isPositiveDefinite(DenseMatrix64F A) {
/* 126 */     if (!isSquare((D1Matrix64F)A)) {
/* 127 */       return false;
/*     */     }
/* 129 */     CholeskyDecompositionInner_D64 chol = new CholeskyDecompositionInner_D64(true);
/* 130 */     if (chol.inputModified()) {
/* 131 */       A = A.copy();
/*     */     }
/* 133 */     return chol.decompose(A);
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
/*     */   public static boolean isPositiveSemidefinite(DenseMatrix64F A) {
/* 150 */     if (!isSquare((D1Matrix64F)A)) {
/* 151 */       return false;
/*     */     }
/* 153 */     EigenDecomposition<DenseMatrix64F> eig = DecompositionFactory.eig(A.numCols, false);
/* 154 */     if (eig.inputModified())
/* 155 */       A = A.copy(); 
/* 156 */     eig.decompose((Matrix64F)A);
/*     */     
/* 158 */     for (int i = 0; i < A.numRows; i++) {
/* 159 */       Complex64F v = eig.getEigenvalue(i);
/*     */       
/* 161 */       if (v.getReal() < 0.0D) {
/* 162 */         return false;
/*     */       }
/*     */     } 
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSquare(D1Matrix64F mat) {
/* 176 */     return (mat.numCols == mat.numRows);
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
/*     */   public static boolean isSymmetric(DenseMatrix64F m, double tol) {
/* 194 */     if (m.numCols != m.numRows) {
/* 195 */       return false;
/*     */     }
/* 197 */     double max = CommonOps.elementMaxAbs((D1Matrix64F)m);
/*     */     
/* 199 */     for (int i = 0; i < m.numRows; i++) {
/* 200 */       for (int j = 0; j < i; j++) {
/* 201 */         double a = m.get(i, j) / max;
/* 202 */         double b = m.get(j, i) / max;
/*     */         
/* 204 */         double diff = Math.abs(a - b);
/*     */         
/* 206 */         if (diff > tol) {
/* 207 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 211 */     return true;
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
/*     */   public static boolean isSymmetric(DenseMatrix64F m) {
/* 227 */     return isSymmetric(m, 0.0D);
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
/*     */   public static boolean isSkewSymmetric(DenseMatrix64F A, double tol) {
/* 244 */     if (A.numCols != A.numRows) {
/* 245 */       return false;
/*     */     }
/* 247 */     for (int i = 0; i < A.numRows; i++) {
/* 248 */       for (int j = 0; j < i; j++) {
/* 249 */         double a = A.get(i, j);
/* 250 */         double b = A.get(j, i);
/*     */         
/* 252 */         double diff = Math.abs(a + b);
/*     */         
/* 254 */         if (diff > tol) {
/* 255 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 259 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInverse(DenseMatrix64F a, DenseMatrix64F b, double tol) {
/* 269 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 270 */       return false;
/*     */     }
/*     */     
/* 273 */     int numRows = a.numRows;
/* 274 */     int numCols = a.numCols;
/*     */     
/* 276 */     for (int i = 0; i < numRows; i++) {
/* 277 */       for (int j = 0; j < numCols; j++) {
/* 278 */         double total = 0.0D;
/* 279 */         for (int k = 0; k < numCols; k++) {
/* 280 */           total += a.get(i, k) * b.get(k, j);
/*     */         }
/*     */         
/* 283 */         if (i == j) {
/* 284 */           if (Math.abs(total - 1.0D) > tol)
/* 285 */             return false; 
/* 286 */         } else if (Math.abs(total) > tol) {
/* 287 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 291 */     return true;
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
/*     */   public static boolean isEquals(D1Matrix64F a, D1Matrix64F b, double tol) {
/* 313 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     if (tol == 0.0D) {
/* 318 */       return isEquals(a, b);
/*     */     }
/* 320 */     int length = a.getNumElements();
/*     */     
/* 322 */     for (int i = 0; i < length; i++) {
/* 323 */       if (tol < Math.abs(a.get(i) - b.get(i))) {
/* 324 */         return false;
/*     */       }
/*     */     } 
/* 327 */     return true;
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
/*     */   public static boolean isEqualsTriangle(ReshapeMatrix64F a, ReshapeMatrix64F b, boolean upper, double tol) {
/* 350 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 351 */       return false;
/*     */     }
/*     */     
/* 354 */     if (upper) {
/* 355 */       for (int i = 0; i < a.numRows; i++) {
/* 356 */         for (int j = i; j < a.numCols; j++) {
/* 357 */           if (Math.abs(a.get(i, j) - b.get(i, j)) > tol)
/* 358 */             return false; 
/*     */         } 
/*     */       } 
/*     */     } else {
/* 362 */       for (int i = 0; i < a.numRows; i++) {
/* 363 */         int end = Math.min(i, a.numCols - 1);
/*     */         
/* 365 */         for (int j = 0; j <= end; j++) {
/* 366 */           if (Math.abs(a.get(i, j) - b.get(i, j)) > tol) {
/* 367 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 372 */     return true;
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
/*     */   public static boolean isEquals(D1Matrix64F a, D1Matrix64F b) {
/* 391 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 392 */       return false;
/*     */     }
/*     */     
/* 395 */     int length = a.getNumElements();
/* 396 */     for (int i = 0; i < length; i++) {
/* 397 */       if (a.get(i) != b.get(i)) {
/* 398 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 402 */     return true;
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
/*     */   public static boolean isIdentical(D1Matrix64F a, D1Matrix64F b, double tol) {
/* 425 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 426 */       return false;
/*     */     }
/* 428 */     if (tol < 0.0D) {
/* 429 */       throw new IllegalArgumentException("Tolerance must be greater than or equal to zero.");
/*     */     }
/* 431 */     int length = a.getNumElements();
/* 432 */     for (int i = 0; i < length; ) {
/* 433 */       double valA = a.get(i);
/* 434 */       double valB = b.get(i);
/*     */ 
/*     */ 
/*     */       
/* 438 */       double diff = Math.abs(valA - valB);
/*     */ 
/*     */ 
/*     */       
/* 442 */       if (tol >= diff) {
/*     */         i++; continue;
/*     */       } 
/* 445 */       if (Double.isNaN(valA))
/* 446 */         return Double.isNaN(valB); 
/* 447 */       if (Double.isInfinite(valA)) {
/* 448 */         return (valA == valB);
/*     */       }
/* 450 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 454 */     return true;
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
/*     */   public static boolean isOrthogonal(DenseMatrix64F Q, double tol) {
/* 468 */     if (Q.numRows < Q.numCols) {
/* 469 */       throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
/*     */     }
/*     */     
/* 472 */     DenseMatrix64F[] u = CommonOps.columnsToVector(Q, null);
/*     */     
/* 474 */     for (int i = 0; i < u.length; i++) {
/* 475 */       DenseMatrix64F a = u[i];
/*     */       
/* 477 */       for (int j = i + 1; j < u.length; j++) {
/* 478 */         double val = VectorVectorMult.innerProd((D1Matrix64F)a, (D1Matrix64F)u[j]);
/*     */         
/* 480 */         if (Math.abs(val) > tol) {
/* 481 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 485 */     return true;
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
/*     */   public static boolean isRowsLinearIndependent(DenseMatrix64F A) {
/* 497 */     LUDecomposition<DenseMatrix64F> lu = DecompositionFactory.lu(A.numRows, A.numCols);
/* 498 */     if (lu.inputModified()) {
/* 499 */       A = A.copy();
/*     */     }
/* 501 */     if (!lu.decompose((Matrix64F)A)) {
/* 502 */       throw new RuntimeException("Decompositon failed?");
/*     */     }
/*     */     
/* 505 */     return !lu.isSingular();
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
/*     */   public static boolean isIdentity(DenseMatrix64F mat, double tol) {
/* 518 */     int index = 0;
/* 519 */     for (int i = 0; i < mat.numRows; i++) {
/* 520 */       for (int j = 0; j < mat.numCols; j++) {
/* 521 */         if (i == j) {
/* 522 */           if (Math.abs(mat.get(index++) - 1.0D) > tol) {
/* 523 */             return false;
/*     */           }
/* 525 */         } else if (Math.abs(mat.get(index++)) > tol) {
/* 526 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 531 */     return true;
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
/*     */   public static boolean isConstantVal(DenseMatrix64F mat, double val, double tol) {
/* 545 */     int index = 0;
/* 546 */     for (int i = 0; i < mat.numRows; i++) {
/* 547 */       for (int j = 0; j < mat.numCols; j++) {
/* 548 */         if (Math.abs(mat.get(index++) - val) > tol) {
/* 549 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 554 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDiagonalPositive(DenseMatrix64F a) {
/* 564 */     for (int i = 0; i < a.numRows; i++) {
/* 565 */       if (a.get(i, i) < 0.0D)
/* 566 */         return false; 
/*     */     } 
/* 568 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFullRank(DenseMatrix64F a) {
/* 573 */     throw new RuntimeException("Implement");
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
/*     */   public static boolean isNegative(D1Matrix64F a, D1Matrix64F b, double tol) {
/* 589 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 590 */       throw new IllegalArgumentException("Matrix dimensions must match");
/*     */     }
/* 592 */     int length = a.getNumElements();
/*     */     
/* 594 */     for (int i = 0; i < length; i++) {
/* 595 */       if (Math.abs(a.get(i) + b.get(i)) > tol) {
/* 596 */         return false;
/*     */       }
/*     */     } 
/* 599 */     return true;
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
/*     */   public static boolean isUpperTriangle(DenseMatrix64F A, int hessenberg, double tol) {
/* 617 */     if (A.numRows != A.numCols) {
/* 618 */       return false;
/*     */     }
/* 620 */     for (int i = hessenberg + 1; i < A.numRows; i++) {
/* 621 */       for (int j = 0; j < i - hessenberg; j++) {
/* 622 */         if (Math.abs(A.get(i, j)) > tol) {
/* 623 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 627 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rank(DenseMatrix64F A) {
/* 637 */     return rank(A, UtilEjml.EPS * 100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rank(DenseMatrix64F A, double threshold) {
/* 648 */     SingularValueDecomposition<DenseMatrix64F> svd = DecompositionFactory.svd(A.numRows, A.numCols, false, false, true);
/*     */     
/* 650 */     if (svd.inputModified()) {
/* 651 */       A = A.copy();
/*     */     }
/* 653 */     if (!svd.decompose((Matrix64F)A)) {
/* 654 */       throw new RuntimeException("Decomposition failed");
/*     */     }
/* 656 */     return SingularOps.rank(svd, threshold);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nullity(DenseMatrix64F A) {
/* 666 */     return nullity(A, UtilEjml.EPS * 100.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nullity(DenseMatrix64F A, double threshold) {
/* 677 */     SingularValueDecomposition<DenseMatrix64F> svd = DecompositionFactory.svd(A.numRows, A.numCols, false, false, true);
/*     */     
/* 679 */     if (svd.inputModified()) {
/* 680 */       A = A.copy();
/*     */     }
/* 682 */     if (!svd.decompose((Matrix64F)A)) {
/* 683 */       throw new RuntimeException("Decomposition failed");
/*     */     }
/* 685 */     return SingularOps.nullity(svd, threshold);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\MatrixFeatures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */