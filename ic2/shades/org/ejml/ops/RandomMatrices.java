/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.mult.SubmatrixOps;
/*     */ import ic2.shades.org.ejml.alg.dense.mult.VectorVectorMult;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomMatrices
/*     */ {
/*     */   public static DenseMatrix64F[] createSpan(int dimen, int numVectors, Random rand) {
/*  58 */     if (dimen < numVectors) {
/*  59 */       throw new IllegalArgumentException("The number of vectors must be less than or equal to the dimension");
/*     */     }
/*  61 */     DenseMatrix64F[] u = new DenseMatrix64F[numVectors];
/*     */     
/*  63 */     u[0] = createRandom(dimen, 1, -1.0D, 1.0D, rand);
/*  64 */     NormOps.normalizeF(u[0]);
/*     */     
/*  66 */     for (int i = 1; i < numVectors; i++) {
/*     */       
/*  68 */       DenseMatrix64F a = new DenseMatrix64F(dimen, 1);
/*  69 */       DenseMatrix64F r = null;
/*     */       
/*  71 */       for (int j = 0; j < i; j++) {
/*     */         
/*  73 */         if (j == 0) {
/*  74 */           r = createRandom(dimen, 1, -1.0D, 1.0D, rand);
/*     */         }
/*     */ 
/*     */         
/*  78 */         a.set((D1Matrix64F)r);
/*  79 */         VectorVectorMult.householder(-2.0D, (D1Matrix64F)u[j], (D1Matrix64F)r, (D1Matrix64F)a);
/*  80 */         CommonOps.add((D1Matrix64F)r, (D1Matrix64F)a, (D1Matrix64F)a);
/*  81 */         CommonOps.scale(0.5D, (D1Matrix64F)a);
/*     */ 
/*     */ 
/*     */         
/*  85 */         DenseMatrix64F t = a;
/*  86 */         a = r;
/*  87 */         r = t;
/*     */ 
/*     */         
/*  90 */         double val = NormOps.normF((D1Matrix64F)r);
/*  91 */         if (val == 0.0D || Double.isNaN(val) || Double.isInfinite(val))
/*  92 */           throw new RuntimeException("Failed sanity check"); 
/*  93 */         CommonOps.divide((D1Matrix64F)r, val);
/*     */       } 
/*     */       
/*  96 */       u[i] = r;
/*     */     } 
/*     */     
/*  99 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F createInSpan(DenseMatrix64F[] span, double min, double max, Random rand) {
/* 110 */     DenseMatrix64F A = new DenseMatrix64F(span.length, 1);
/*     */     
/* 112 */     DenseMatrix64F B = new DenseMatrix64F(span[0].getNumElements(), 1);
/*     */     
/* 114 */     for (int i = 0; i < span.length; i++) {
/* 115 */       B.set((D1Matrix64F)span[i]);
/* 116 */       double val = rand.nextDouble() * (max - min) + min;
/* 117 */       CommonOps.scale(val, (D1Matrix64F)B);
/*     */       
/* 119 */       CommonOps.add((D1Matrix64F)A, (D1Matrix64F)B, (D1Matrix64F)A);
/*     */     } 
/*     */ 
/*     */     
/* 123 */     return A;
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
/*     */   public static DenseMatrix64F createOrthogonal(int numRows, int numCols, Random rand) {
/* 138 */     if (numRows < numCols) {
/* 139 */       throw new IllegalArgumentException("The number of rows must be more than or equal to the number of columns");
/*     */     }
/*     */     
/* 142 */     DenseMatrix64F[] u = createSpan(numRows, numCols, rand);
/*     */     
/* 144 */     DenseMatrix64F ret = new DenseMatrix64F(numRows, numCols);
/* 145 */     for (int i = 0; i < numCols; i++) {
/* 146 */       SubmatrixOps.setSubMatrix((RowD1Matrix64F)u[i], (RowD1Matrix64F)ret, 0, 0, 0, i, numRows, 1);
/*     */     }
/*     */     
/* 149 */     return ret;
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
/*     */   public static DenseMatrix64F createDiagonal(int N, double min, double max, Random rand) {
/* 163 */     return createDiagonal(N, N, min, max, rand);
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
/*     */   public static DenseMatrix64F createDiagonal(int numRows, int numCols, double min, double max, Random rand) {
/* 178 */     if (max < min) {
/* 179 */       throw new IllegalArgumentException("The max must be >= the min");
/*     */     }
/* 181 */     DenseMatrix64F ret = new DenseMatrix64F(numRows, numCols);
/*     */     
/* 183 */     int N = Math.min(numRows, numCols);
/*     */     
/* 185 */     double r = max - min;
/*     */     
/* 187 */     for (int i = 0; i < N; i++) {
/* 188 */       ret.set(i, i, rand.nextDouble() * r + min);
/*     */     }
/*     */     
/* 191 */     return ret;
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
/*     */   public static DenseMatrix64F createSingularValues(int numRows, int numCols, Random rand, double... sv) {
/* 209 */     DenseMatrix64F U = createOrthogonal(numRows, numRows, rand);
/* 210 */     DenseMatrix64F V = createOrthogonal(numCols, numCols, rand);
/*     */     
/* 212 */     DenseMatrix64F S = new DenseMatrix64F(numRows, numCols);
/*     */     
/* 214 */     int min = Math.min(numRows, numCols);
/* 215 */     min = Math.min(min, sv.length);
/*     */     
/* 217 */     for (int i = 0; i < min; i++) {
/* 218 */       S.set(i, i, sv[i]);
/*     */     }
/*     */     
/* 221 */     DenseMatrix64F tmp = new DenseMatrix64F(numRows, numCols);
/* 222 */     CommonOps.mult((RowD1Matrix64F)U, (RowD1Matrix64F)S, (RowD1Matrix64F)tmp);
/* 223 */     CommonOps.multTransB((RowD1Matrix64F)tmp, (RowD1Matrix64F)V, (RowD1Matrix64F)S);
/*     */     
/* 225 */     return S;
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
/*     */   public static DenseMatrix64F createEigenvaluesSymm(int num, Random rand, double... eigenvalues) {
/* 237 */     DenseMatrix64F V = createOrthogonal(num, num, rand);
/* 238 */     DenseMatrix64F D = CommonOps.diag(eigenvalues);
/*     */     
/* 240 */     DenseMatrix64F temp = new DenseMatrix64F(num, num);
/*     */     
/* 242 */     CommonOps.mult((RowD1Matrix64F)V, (RowD1Matrix64F)D, (RowD1Matrix64F)temp);
/* 243 */     CommonOps.multTransB((RowD1Matrix64F)temp, (RowD1Matrix64F)V, (RowD1Matrix64F)D);
/*     */     
/* 245 */     return D;
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
/*     */   public static DenseMatrix64F createRandom(int numRow, int numCol, Random rand) {
/* 258 */     DenseMatrix64F mat = new DenseMatrix64F(numRow, numCol);
/*     */     
/* 260 */     setRandom((D1Matrix64F)mat, 0.0D, 1.0D, rand);
/*     */     
/* 262 */     return mat;
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
/*     */   public static void addRandom(DenseMatrix64F A, double min, double max, Random rand) {
/* 278 */     double[] d = A.getData();
/* 279 */     int size = A.getNumElements();
/*     */     
/* 281 */     double r = max - min;
/*     */     
/* 283 */     for (int i = 0; i < size; i++) {
/* 284 */       d[i] = d[i] + r * rand.nextDouble() + min;
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
/*     */   public static DenseMatrix64F createRandom(int numRow, int numCol, double min, double max, Random rand) {
/* 302 */     DenseMatrix64F mat = new DenseMatrix64F(numRow, numCol);
/*     */     
/* 304 */     setRandom((D1Matrix64F)mat, min, max, rand);
/*     */     
/* 306 */     return mat;
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
/*     */   public static void setRandom(DenseMatrix64F mat, Random rand) {
/* 319 */     setRandom((D1Matrix64F)mat, 0.0D, 1.0D, rand);
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
/*     */   public static void setRandom(D1Matrix64F mat, double min, double max, Random rand) {
/* 334 */     double[] d = mat.getData();
/* 335 */     int size = mat.getNumElements();
/*     */     
/* 337 */     double r = max - min;
/*     */     
/* 339 */     for (int i = 0; i < size; i++) {
/* 340 */       d[i] = r * rand.nextDouble() + min;
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
/*     */   public static DenseMatrix64F createSymmPosDef(int width, Random rand) {
/* 353 */     DenseMatrix64F a = new DenseMatrix64F(width, 1);
/* 354 */     DenseMatrix64F b = new DenseMatrix64F(width, width);
/*     */     int i;
/* 356 */     for (i = 0; i < width; i++) {
/* 357 */       a.set(i, 0, rand.nextDouble());
/*     */     }
/*     */     
/* 360 */     CommonOps.multTransB((RowD1Matrix64F)a, (RowD1Matrix64F)a, (RowD1Matrix64F)b);
/*     */     
/* 362 */     for (i = 0; i < width; i++) {
/* 363 */       b.add(i, i, 1.0D);
/*     */     }
/*     */     
/* 366 */     return b;
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
/*     */   public static DenseMatrix64F createSymmetric(int length, double min, double max, Random rand) {
/* 380 */     DenseMatrix64F A = new DenseMatrix64F(length, length);
/*     */     
/* 382 */     createSymmetric(A, min, max, rand);
/*     */     
/* 384 */     return A;
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
/*     */   public static void createSymmetric(DenseMatrix64F A, double min, double max, Random rand) {
/* 397 */     if (A.numRows != A.numCols) {
/* 398 */       throw new IllegalArgumentException("A must be a square matrix");
/*     */     }
/* 400 */     double range = max - min;
/*     */     
/* 402 */     int length = A.numRows;
/*     */     
/* 404 */     for (int i = 0; i < length; i++) {
/* 405 */       for (int j = i; j < length; j++) {
/* 406 */         double val = rand.nextDouble() * range + min;
/* 407 */         A.set(i, j, val);
/* 408 */         A.set(j, i, val);
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
/*     */   public static DenseMatrix64F createUpperTriangle(int dimen, int hessenberg, double min, double max, Random rand) {
/* 426 */     if (hessenberg < 0) {
/* 427 */       throw new RuntimeException("hessenberg must be more than or equal to 0");
/*     */     }
/* 429 */     double range = max - min;
/*     */     
/* 431 */     DenseMatrix64F A = new DenseMatrix64F(dimen, dimen);
/*     */     
/* 433 */     for (int i = 0; i < dimen; i++) {
/* 434 */       int start = (i <= hessenberg) ? 0 : (i - hessenberg);
/*     */       
/* 436 */       for (int j = start; j < dimen; j++) {
/* 437 */         A.set(i, j, rand.nextDouble() * range + min);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 442 */     return A;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\RandomMatrices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */