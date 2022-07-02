/*     */ package ic2.shades.org.ejml.alg.dense.misc;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.ReducedRowEchelonForm;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RrefGaussJordanRowPivot
/*     */   implements ReducedRowEchelonForm<DenseMatrix64F>
/*     */ {
/*     */   double tol;
/*     */   
/*     */   public void setTolerance(double tol) {
/*  36 */     this.tol = tol;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reduce(DenseMatrix64F A, int coefficientColumns) {
/*  41 */     if (A.numCols < coefficientColumns) {
/*  42 */       throw new IllegalArgumentException("The system must be at least as wide as A");
/*     */     }
/*     */     
/*  45 */     int leadIndex = 0;
/*     */     
/*  47 */     for (int i = 0; i < coefficientColumns; i++) {
/*     */ 
/*     */       
/*  50 */       int pivotRow = -1;
/*  51 */       double maxValue = this.tol;
/*     */       int row;
/*  53 */       for (row = leadIndex; row < A.numRows; row++) {
/*  54 */         double v = Math.abs(A.data[row * A.numCols + i]);
/*     */         
/*  56 */         if (v > maxValue) {
/*  57 */           maxValue = v;
/*  58 */           pivotRow = row;
/*     */         } 
/*     */       } 
/*     */       
/*  62 */       if (pivotRow != -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  68 */         if (leadIndex != pivotRow) {
/*  69 */           swapRows(A, leadIndex, pivotRow);
/*     */         }
/*     */         
/*  72 */         for (row = 0; row < A.numRows; row++) {
/*  73 */           if (row != leadIndex) {
/*     */             
/*  75 */             int j = leadIndex * A.numCols + i;
/*  76 */             int indexTarget = row * A.numCols + i;
/*     */             
/*  78 */             double d = A.data[indexTarget] / A.data[j++];
/*  79 */             A.data[indexTarget++] = 0.0D;
/*  80 */             for (int k = i + 1; k < A.numCols; k++) {
/*  81 */               A.data[indexTarget++] = A.data[indexTarget++] - A.data[j++] * d;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/*  86 */         int indexPivot = leadIndex * A.numCols + i;
/*  87 */         double alpha = 1.0D / A.data[indexPivot];
/*  88 */         A.data[indexPivot++] = 1.0D;
/*  89 */         for (int col = i + 1; col < A.numCols; col++) {
/*  90 */           A.data[indexPivot++] = A.data[indexPivot++] * alpha;
/*     */         }
/*  92 */         leadIndex++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected static void swapRows(DenseMatrix64F A, int rowA, int rowB) {
/*  97 */     int indexA = rowA * A.numCols;
/*  98 */     int indexB = rowB * A.numCols;
/*     */     
/* 100 */     for (int i = 0; i < A.numCols; i++, indexA++, indexB++) {
/* 101 */       double temp = A.data[indexA];
/* 102 */       A.data[indexA] = A.data[indexB];
/* 103 */       A.data[indexB] = temp;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\misc\RrefGaussJordanRowPivot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */