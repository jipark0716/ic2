/*     */ package ic2.shades.org.ejml.data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ReshapeMatrix64F
/*     */   implements Matrix64F
/*     */ {
/*     */   private static final long serialVersionUID = 423423451942L;
/*     */   public int numRows;
/*     */   public int numCols;
/*     */   
/*     */   public abstract void reshape(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   public void reshape(int numRows, int numCols) {
/*  71 */     reshape(numRows, numCols, false);
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
/*     */   public MatrixIterator iterator(boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
/*  88 */     return new MatrixIterator(this, rowMajor, minRow, minCol, maxRow, maxCol);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/*  96 */     return this.numRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 104 */     return this.numCols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumRows(int numRows) {
/* 113 */     this.numRows = numRows;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumCols(int numCols) {
/* 122 */     this.numCols = numCols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(ReshapeMatrix64F A) {
/* 132 */     if (A.numRows != this.numRows) {
/* 133 */       throw new IllegalArgumentException("Unexpected number of rows.");
/*     */     }
/* 135 */     if (A.numCols != this.numCols) {
/* 136 */       throw new IllegalArgumentException("Unexpected number of columns.");
/*     */     }
/*     */     
/* 139 */     for (int i = 0; i < A.numRows; i++) {
/* 140 */       for (int j = 0; j < A.numCols; j++)
/* 141 */         set(i, j, A.get(i, j)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\ReshapeMatrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */