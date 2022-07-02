/*     */ package ic2.shades.org.ejml.data;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatrixIterator
/*     */   implements Iterator<Double>
/*     */ {
/*     */   private ReshapeMatrix64F a;
/*     */   private boolean rowMajor;
/*     */   private int minCol;
/*     */   private int minRow;
/*  43 */   private int index = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int submatrixStride;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int subRow;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int subCol;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MatrixIterator(ReshapeMatrix64F a, boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
/*  69 */     if (maxCol < minCol)
/*  70 */       throw new IllegalArgumentException("maxCol has to be more than or equal to minCol"); 
/*  71 */     if (maxRow < minRow)
/*  72 */       throw new IllegalArgumentException("maxRow has to be more than or equal to minCol"); 
/*  73 */     if (maxCol >= a.numCols)
/*  74 */       throw new IllegalArgumentException("maxCol must be < numCols"); 
/*  75 */     if (maxRow >= a.numRows) {
/*  76 */       throw new IllegalArgumentException("maxRow must be < numCRows");
/*     */     }
/*     */ 
/*     */     
/*  80 */     this.a = a;
/*  81 */     this.rowMajor = rowMajor;
/*  82 */     this.minCol = minCol;
/*  83 */     this.minRow = minRow;
/*     */     
/*  85 */     this.size = (maxCol - minCol + 1) * (maxRow - minRow + 1);
/*     */     
/*  87 */     if (rowMajor) {
/*  88 */       this.submatrixStride = maxCol - minCol + 1;
/*     */     } else {
/*  90 */       this.submatrixStride = maxRow - minRow + 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasNext() {
/*  95 */     return (this.index < this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public Double next() {
/* 100 */     if (this.rowMajor) {
/* 101 */       this.subRow = this.index / this.submatrixStride;
/* 102 */       this.subCol = this.index % this.submatrixStride;
/*     */     } else {
/* 104 */       this.subRow = this.index % this.submatrixStride;
/* 105 */       this.subCol = this.index / this.submatrixStride;
/*     */     } 
/* 107 */     this.index++;
/* 108 */     return Double.valueOf(this.a.get(this.subRow + this.minRow, this.subCol + this.minCol));
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/* 113 */     throw new RuntimeException("Operation not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/* 122 */     return this.index - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRowMajor() {
/* 130 */     return this.rowMajor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double value) {
/* 139 */     this.a.set(this.subRow + this.minRow, this.subCol + this.minCol, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\MatrixIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */