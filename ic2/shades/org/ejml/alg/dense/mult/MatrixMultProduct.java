/*     */ package ic2.shades.org.ejml.alg.dense.mult;
/*     */ 
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
/*     */ public class MatrixMultProduct
/*     */ {
/*     */   public static void outer(RowD1Matrix64F a, RowD1Matrix64F c) {
/*  38 */     for (int i = 0; i < a.numRows; i++) {
/*  39 */       int indexC1 = i * c.numCols + i;
/*  40 */       int indexC2 = indexC1;
/*  41 */       for (int j = i; j < a.numRows; j++, indexC2 += c.numCols) {
/*  42 */         int indexA = i * a.numCols;
/*  43 */         int indexB = j * a.numCols;
/*  44 */         double sum = 0.0D;
/*  45 */         int end = indexA + a.numCols;
/*  46 */         for (; indexA < end; indexA++, indexB++) {
/*  47 */           sum += a.data[indexA] * a.data[indexB];
/*     */         }
/*  49 */         c.data[indexC1++] = sum; c.data[indexC2] = sum;
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
/*     */   public static void inner_small(RowD1Matrix64F a, RowD1Matrix64F c) {
/*  65 */     for (int i = 0; i < a.numCols; i++) {
/*  66 */       for (int j = i; j < a.numCols; j++) {
/*  67 */         int indexC1 = i * c.numCols + j;
/*  68 */         int indexC2 = j * c.numCols + i;
/*  69 */         int indexA = i;
/*  70 */         int indexB = j;
/*  71 */         double sum = 0.0D;
/*  72 */         int end = indexA + a.numRows * a.numCols;
/*  73 */         for (; indexA < end; indexA += a.numCols, indexB += a.numCols) {
/*  74 */           sum += a.data[indexA] * a.data[indexB];
/*     */         }
/*  76 */         c.data[indexC2] = sum; c.data[indexC1] = sum;
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
/*     */   public static void inner_reorder(RowD1Matrix64F a, RowD1Matrix64F c) {
/*  93 */     for (int i = 0; i < a.numCols; i++) {
/*  94 */       int indexC = i * c.numCols + i;
/*  95 */       double valAi = a.data[i];
/*  96 */       for (int j = i; j < a.numCols; j++) {
/*  97 */         c.data[indexC++] = valAi * a.data[j];
/*     */       }
/*     */       
/* 100 */       for (int k = 1; k < a.numRows; k++) {
/* 101 */         indexC = i * c.numCols + i;
/* 102 */         int indexB = k * a.numCols + i;
/* 103 */         valAi = a.data[indexB];
/* 104 */         for (int n = i; n < a.numCols; n++) {
/* 105 */           c.data[indexC++] = c.data[indexC++] + valAi * a.data[indexB++];
/*     */         }
/*     */       } 
/*     */       
/* 109 */       indexC = i * c.numCols + i;
/* 110 */       int indexC2 = indexC;
/* 111 */       for (int m = i; m < a.numCols; m++, indexC2 += c.numCols) {
/* 112 */         c.data[indexC2] = c.data[indexC++];
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
/*     */   public static void inner_reorder_upper(RowD1Matrix64F a, RowD1Matrix64F c) {
/* 133 */     for (int i = 0; i < a.numCols; i++) {
/* 134 */       int indexC = i * c.numCols + i;
/* 135 */       double valAi = a.data[i];
/* 136 */       for (int j = i; j < a.numCols; j++) {
/* 137 */         c.data[indexC++] = valAi * a.data[j];
/*     */       }
/*     */       
/* 140 */       for (int k = 1; k < a.numRows; k++) {
/* 141 */         indexC = i * c.numCols + i;
/* 142 */         int indexB = k * a.numCols + i;
/* 143 */         valAi = a.data[indexB];
/* 144 */         for (int m = i; m < a.numCols; m++)
/* 145 */           c.data[indexC++] = c.data[indexC++] + valAi * a.data[indexB++]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\mult\MatrixMultProduct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */