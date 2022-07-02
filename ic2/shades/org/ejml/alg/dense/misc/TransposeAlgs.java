/*     */ package ic2.shades.org.ejml.alg.dense.misc;
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
/*     */ 
/*     */ 
/*     */ public class TransposeAlgs
/*     */ {
/*     */   public static void square(RowD1Matrix64F mat) {
/*  40 */     int index = 1;
/*  41 */     int indexEnd = mat.numCols;
/*  42 */     for (int i = 0; i < mat.numRows; 
/*  43 */       index += ++i + 1, indexEnd += mat.numCols) {
/*  44 */       int indexOther = (i + 1) * mat.numCols + i;
/*  45 */       for (; index < indexEnd; index++, indexOther += mat.numCols) {
/*  46 */         double val = mat.data[index];
/*  47 */         mat.data[index] = mat.data[indexOther];
/*  48 */         mat.data[indexOther] = val;
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
/*     */   public static void block(RowD1Matrix64F A, RowD1Matrix64F A_tran, int blockLength) {
/*  68 */     for (int i = 0; i < A.numRows; i += blockLength) {
/*  69 */       int blockHeight = Math.min(blockLength, A.numRows - i);
/*     */       
/*  71 */       int indexSrc = i * A.numCols;
/*  72 */       int indexDst = i;
/*     */       int j;
/*  74 */       for (j = 0; j < A.numCols; j += blockLength) {
/*  75 */         int blockWidth = Math.min(blockLength, A.numCols - j);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         int indexSrcEnd = indexSrc + blockWidth;
/*     */         
/*  82 */         for (; indexSrc < indexSrcEnd; indexSrc++) {
/*  83 */           int rowSrc = indexSrc;
/*  84 */           int rowDst = indexDst;
/*  85 */           int end = rowDst + blockHeight;
/*     */           
/*  87 */           for (; rowDst < end; rowSrc += A.numCols)
/*     */           {
/*  89 */             A_tran.data[rowDst++] = A.data[rowSrc];
/*     */           }
/*  91 */           indexDst += A_tran.numCols;
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
/*     */   public static void standard(RowD1Matrix64F A, RowD1Matrix64F A_tran) {
/* 105 */     int index = 0;
/* 106 */     for (int i = 0; i < A_tran.numRows; i++) {
/* 107 */       int index2 = i;
/*     */       
/* 109 */       int end = index + A_tran.numCols;
/* 110 */       while (index < end) {
/* 111 */         A_tran.data[index++] = A.data[index2];
/* 112 */         index2 += A.numCols;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\misc\TransposeAlgs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */