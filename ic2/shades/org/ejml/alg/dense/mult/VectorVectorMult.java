/*     */ package ic2.shades.org.ejml.alg.dense.mult;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VectorVectorMult
/*     */ {
/*     */   public static void mult(DenseMatrix64F x, DenseMatrix64F y, DenseMatrix64F A) {}
/*     */   
/*     */   public static double innerProd(D1Matrix64F x, D1Matrix64F y) {
/*  67 */     int m = x.getNumElements();
/*     */     
/*  69 */     double total = 0.0D;
/*  70 */     for (int i = 0; i < m; i++) {
/*  71 */       total += x.get(i) * y.get(i);
/*     */     }
/*     */     
/*  74 */     return total;
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
/*     */   public static double innerProdA(D1Matrix64F x, D1Matrix64F A, D1Matrix64F y) {
/*  89 */     int n = A.numRows;
/*  90 */     int m = A.numCols;
/*     */     
/*  92 */     if (x.getNumElements() != n)
/*  93 */       throw new IllegalArgumentException("Unexpected number of elements in x"); 
/*  94 */     if (y.getNumElements() != m) {
/*  95 */       throw new IllegalArgumentException("Unexpected number of elements in y");
/*     */     }
/*  97 */     double result = 0.0D;
/*     */     
/*  99 */     for (int i = 0; i < m; i++) {
/* 100 */       double total = 0.0D;
/*     */       
/* 102 */       for (int j = 0; j < n; j++) {
/* 103 */         total += x.get(j) * A.unsafe_get(j, i);
/*     */       }
/*     */       
/* 106 */       result += total * y.get(i);
/*     */     } 
/*     */     
/* 109 */     return result;
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
/*     */   public static double innerProdTranA(D1Matrix64F x, D1Matrix64F A, D1Matrix64F y) {
/* 126 */     int n = A.numRows;
/*     */     
/* 128 */     if (n != A.numCols) {
/* 129 */       throw new IllegalArgumentException("A must be square");
/*     */     }
/* 131 */     if (x.getNumElements() != n)
/* 132 */       throw new IllegalArgumentException("Unexpected number of elements in x"); 
/* 133 */     if (y.getNumElements() != n) {
/* 134 */       throw new IllegalArgumentException("Unexpected number of elements in y");
/*     */     }
/* 136 */     double result = 0.0D;
/*     */     
/* 138 */     for (int i = 0; i < n; i++) {
/* 139 */       double total = 0.0D;
/*     */       
/* 141 */       for (int j = 0; j < n; j++) {
/* 142 */         total += x.get(j) * A.unsafe_get(i, j);
/*     */       }
/*     */       
/* 145 */       result += total * y.get(i);
/*     */     } 
/*     */     
/* 148 */     return result;
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
/*     */   public static void outerProd(D1Matrix64F x, D1Matrix64F y, RowD1Matrix64F A) {
/* 173 */     int m = A.numRows;
/* 174 */     int n = A.numCols;
/*     */     
/* 176 */     int index = 0;
/* 177 */     for (int i = 0; i < m; i++) {
/* 178 */       double xdat = x.get(i);
/* 179 */       for (int j = 0; j < n; j++) {
/* 180 */         A.set(index++, xdat * y.get(j));
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
/*     */   public static void addOuterProd(double gamma, D1Matrix64F x, D1Matrix64F y, RowD1Matrix64F A) {
/* 208 */     int m = A.numRows;
/* 209 */     int n = A.numCols;
/*     */     
/* 211 */     int index = 0;
/* 212 */     if (gamma == 1.0D) {
/* 213 */       for (int i = 0; i < m; i++) {
/* 214 */         double xdat = x.get(i);
/* 215 */         for (int j = 0; j < n; j++) {
/* 216 */           A.plus(index++, xdat * y.get(j));
/*     */         }
/*     */       } 
/*     */     } else {
/* 220 */       for (int i = 0; i < m; i++) {
/* 221 */         double xdat = x.get(i);
/* 222 */         for (int j = 0; j < n; j++) {
/* 223 */           A.plus(index++, gamma * xdat * y.get(j));
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
/*     */   public static void householder(double gamma, D1Matrix64F u, D1Matrix64F x, D1Matrix64F y) {
/* 247 */     int n = u.getNumElements();
/*     */     
/* 249 */     double sum = 0.0D; int i;
/* 250 */     for (i = 0; i < n; i++) {
/* 251 */       sum += u.get(i) * x.get(i);
/*     */     }
/* 253 */     for (i = 0; i < n; i++) {
/* 254 */       y.set(i, x.get(i) + gamma * u.get(i) * sum);
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
/*     */   public static void rank1Update(double gamma, DenseMatrix64F A, DenseMatrix64F u, DenseMatrix64F w, DenseMatrix64F B) {
/* 280 */     int n = u.getNumElements();
/*     */     
/* 282 */     int matrixIndex = 0;
/* 283 */     for (int i = 0; i < n; i++) {
/* 284 */       double elementU = u.data[i];
/*     */       
/* 286 */       for (int j = 0; j < n; j++, matrixIndex++) {
/* 287 */         B.data[matrixIndex] = A.data[matrixIndex] + gamma * elementU * w.data[j];
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
/*     */   public static void rank1Update(double gamma, DenseMatrix64F A, DenseMatrix64F u, DenseMatrix64F w) {
/* 311 */     int n = u.getNumElements();
/*     */     
/* 313 */     int matrixIndex = 0;
/* 314 */     for (int i = 0; i < n; i++) {
/* 315 */       double elementU = u.data[i];
/*     */       
/* 317 */       for (int j = 0; j < n; j++)
/* 318 */         A.data[matrixIndex++] = A.data[matrixIndex++] + gamma * elementU * w.data[j]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\mult\VectorVectorMult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */