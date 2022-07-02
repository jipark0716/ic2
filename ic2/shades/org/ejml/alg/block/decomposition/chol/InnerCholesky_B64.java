/*     */ package ic2.shades.org.ejml.alg.block.decomposition.chol;
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
/*     */ public class InnerCholesky_B64
/*     */ {
/*     */   public static boolean upper(D1Submatrix64F T) {
/*  34 */     int n = T.row1 - T.row0;
/*  35 */     int indexT = T.row0 * T.original.numCols + T.col0 * n;
/*     */     
/*  37 */     return upper(T.original.data, indexT, n);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean lower(D1Submatrix64F T) {
/*  42 */     int n = T.row1 - T.row0;
/*  43 */     int indexT = T.row0 * T.original.numCols + T.col0 * n;
/*     */     
/*  45 */     return lower(T.original.data, indexT, n);
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
/*     */   public static boolean upper(double[] T, int indexT, int n) {
/*  59 */     double div_el_ii = 0.0D;
/*     */     
/*  61 */     for (int i = 0; i < n; i++) {
/*  62 */       for (int j = i; j < n; j++) {
/*  63 */         double sum = T[indexT + i * n + j];
/*     */ 
/*     */         
/*  66 */         for (int k = 0; k < i; k++) {
/*  67 */           sum -= T[indexT + k * n + i] * T[indexT + k * n + j];
/*     */         }
/*     */         
/*  70 */         if (i == j) {
/*     */           
/*  72 */           if (sum <= 0.0D) {
/*  73 */             return false;
/*     */           }
/*  75 */           double el_ii = Math.sqrt(sum);
/*  76 */           T[indexT + i * n + i] = el_ii;
/*  77 */           div_el_ii = 1.0D / el_ii;
/*     */         } else {
/*  79 */           T[indexT + i * n + j] = sum * div_el_ii;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     return true;
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
/*     */   public static boolean lower(double[] T, int indexT, int n) {
/*  99 */     double div_el_ii = 0.0D;
/*     */     
/* 101 */     for (int i = 0; i < n; i++) {
/* 102 */       for (int j = i; j < n; j++) {
/* 103 */         double sum = T[indexT + j * n + i];
/*     */ 
/*     */         
/* 106 */         for (int k = 0; k < i; k++) {
/* 107 */           sum -= T[indexT + i * n + k] * T[indexT + j * n + k];
/*     */         }
/*     */         
/* 110 */         if (i == j) {
/*     */           
/* 112 */           if (sum <= 0.0D) {
/* 113 */             return false;
/*     */           }
/* 115 */           double el_ii = Math.sqrt(sum);
/* 116 */           T[indexT + i * n + i] = el_ii;
/* 117 */           div_el_ii = 1.0D / el_ii;
/*     */         } else {
/* 119 */           T[indexT + j * n + i] = sum * div_el_ii;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\chol\InnerCholesky_B64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */