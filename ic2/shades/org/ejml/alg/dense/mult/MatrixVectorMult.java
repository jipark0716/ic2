/*     */ package ic2.shades.org.ejml.alg.dense.mult;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatrixVectorMult
/*     */ {
/*     */   public static void mult(RowD1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/*  64 */     if (C.numCols != 1)
/*  65 */       throw new MatrixDimensionException("C is not a column vector"); 
/*  66 */     if (C.numRows != A.numRows) {
/*  67 */       throw new MatrixDimensionException("C is not the expected length");
/*     */     }
/*     */     
/*  70 */     if (B.numRows == 1) {
/*  71 */       if (A.numCols != B.numCols) {
/*  72 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*  74 */     } else if (B.numCols == 1) {
/*  75 */       if (A.numCols != B.numRows) {
/*  76 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*     */     } else {
/*  79 */       throw new MatrixDimensionException("B is not a vector");
/*     */     } 
/*     */     
/*  82 */     if (A.numCols == 0) {
/*  83 */       CommonOps.fill(C, 0.0D);
/*     */       
/*     */       return;
/*     */     } 
/*  87 */     int indexA = 0;
/*  88 */     int cIndex = 0;
/*  89 */     double b0 = B.get(0);
/*  90 */     for (int i = 0; i < A.numRows; i++) {
/*  91 */       double total = A.get(indexA++) * b0;
/*     */       
/*  93 */       for (int j = 1; j < A.numCols; j++) {
/*  94 */         total += A.get(indexA++) * B.get(j);
/*     */       }
/*     */       
/*  97 */       C.set(cIndex++, total);
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
/*     */   public static void multAdd(RowD1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/* 121 */     if (C.numCols != 1)
/* 122 */       throw new MatrixDimensionException("C is not a column vector"); 
/* 123 */     if (C.numRows != A.numRows) {
/* 124 */       throw new MatrixDimensionException("C is not the expected length");
/*     */     }
/* 126 */     if (B.numRows == 1) {
/* 127 */       if (A.numCols != B.numCols) {
/* 128 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/* 130 */     } else if (B.numCols == 1) {
/* 131 */       if (A.numCols != B.numRows) {
/* 132 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*     */     } else {
/* 135 */       throw new MatrixDimensionException("B is not a vector");
/*     */     } 
/*     */     
/* 138 */     if (A.numCols == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 142 */     int indexA = 0;
/* 143 */     int cIndex = 0;
/* 144 */     for (int i = 0; i < A.numRows; i++) {
/* 145 */       double total = A.get(indexA++) * B.get(0);
/*     */       
/* 147 */       for (int j = 1; j < A.numCols; j++) {
/* 148 */         total += A.get(indexA++) * B.get(j);
/*     */       }
/*     */       
/* 151 */       C.plus(cIndex++, total);
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
/*     */ 
/*     */   
/*     */   public static void multTransA_small(RowD1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/* 180 */     if (C.numCols != 1)
/* 181 */       throw new MatrixDimensionException("C is not a column vector"); 
/* 182 */     if (C.numRows != A.numCols) {
/* 183 */       throw new MatrixDimensionException("C is not the expected length");
/*     */     }
/* 185 */     if (B.numRows == 1) {
/* 186 */       if (A.numRows != B.numCols) {
/* 187 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/* 189 */     } else if (B.numCols == 1) {
/* 190 */       if (A.numRows != B.numRows) {
/* 191 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*     */     } else {
/* 194 */       throw new MatrixDimensionException("B is not a vector");
/*     */     } 
/*     */     
/* 197 */     int cIndex = 0;
/* 198 */     for (int i = 0; i < A.numCols; i++) {
/* 199 */       double total = 0.0D;
/*     */       
/* 201 */       int indexA = i;
/* 202 */       for (int j = 0; j < A.numRows; j++) {
/* 203 */         total += A.get(indexA) * B.get(j);
/* 204 */         indexA += A.numCols;
/*     */       } 
/*     */       
/* 207 */       C.set(cIndex++, total);
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
/*     */   public static void multTransA_reorder(RowD1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/* 221 */     if (C.numCols != 1)
/* 222 */       throw new MatrixDimensionException("C is not a column vector"); 
/* 223 */     if (C.numRows != A.numCols) {
/* 224 */       throw new MatrixDimensionException("C is not the expected length");
/*     */     }
/* 226 */     if (B.numRows == 1) {
/* 227 */       if (A.numRows != B.numCols) {
/* 228 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/* 230 */     } else if (B.numCols == 1) {
/* 231 */       if (A.numRows != B.numRows) {
/* 232 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*     */     } else {
/* 235 */       throw new MatrixDimensionException("B is not a vector");
/*     */     } 
/*     */     
/* 238 */     if (A.numRows == 0) {
/* 239 */       CommonOps.fill(C, 0.0D);
/*     */       
/*     */       return;
/*     */     } 
/* 243 */     double B_val = B.get(0);
/* 244 */     for (int i = 0; i < A.numCols; i++) {
/* 245 */       C.set(i, A.get(i) * B_val);
/*     */     }
/*     */     
/* 248 */     int indexA = A.numCols;
/* 249 */     for (int j = 1; j < A.numRows; j++) {
/* 250 */       B_val = B.get(j);
/* 251 */       for (int k = 0; k < A.numCols; k++) {
/* 252 */         C.plus(k, A.get(indexA++) * B_val);
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
/*     */   public static void multAddTransA_small(RowD1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/* 280 */     if (C.numCols != 1)
/* 281 */       throw new MatrixDimensionException("C is not a column vector"); 
/* 282 */     if (C.numRows != A.numCols) {
/* 283 */       throw new MatrixDimensionException("C is not the expected length");
/*     */     }
/* 285 */     if (B.numRows == 1) {
/* 286 */       if (A.numRows != B.numCols) {
/* 287 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/* 289 */     } else if (B.numCols == 1) {
/* 290 */       if (A.numRows != B.numRows) {
/* 291 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*     */     } else {
/* 294 */       throw new MatrixDimensionException("B is not a vector");
/*     */     } 
/*     */     
/* 297 */     int cIndex = 0;
/* 298 */     for (int i = 0; i < A.numCols; i++) {
/* 299 */       double total = 0.0D;
/*     */       
/* 301 */       int indexA = i;
/* 302 */       for (int j = 0; j < A.numRows; j++) {
/* 303 */         total += A.get(indexA) * B.get(j);
/* 304 */         indexA += A.numCols;
/*     */       } 
/*     */       
/* 307 */       C.plus(cIndex++, total);
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
/*     */   public static void multAddTransA_reorder(RowD1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/* 321 */     if (C.numCols != 1)
/* 322 */       throw new MatrixDimensionException("C is not a column vector"); 
/* 323 */     if (C.numRows != A.numCols) {
/* 324 */       throw new MatrixDimensionException("C is not the expected length");
/*     */     }
/* 326 */     if (B.numRows == 1) {
/* 327 */       if (A.numRows != B.numCols) {
/* 328 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/* 330 */     } else if (B.numCols == 1) {
/* 331 */       if (A.numRows != B.numRows) {
/* 332 */         throw new MatrixDimensionException("A and B are not compatible");
/*     */       }
/*     */     } else {
/* 335 */       throw new MatrixDimensionException("B is not a vector");
/*     */     } 
/*     */     
/* 338 */     if (A.numRows == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 342 */     int indexA = 0;
/* 343 */     for (int j = 0; j < A.numRows; j++) {
/* 344 */       double B_val = B.get(j);
/* 345 */       for (int i = 0; i < A.numCols; i++)
/* 346 */         C.plus(i, A.get(indexA++) * B_val); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\mult\MatrixVectorMult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */