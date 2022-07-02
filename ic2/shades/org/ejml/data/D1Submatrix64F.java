/*    */ package ic2.shades.org.ejml.data;
/*    */ 
/*    */ import ic2.shades.org.ejml.ops.MatrixIO;
/*    */ import ic2.shades.org.ejml.simple.SimpleMatrix;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class D1Submatrix64F
/*    */ {
/*    */   public D1Matrix64F original;
/*    */   public int row0;
/*    */   public int col0;
/*    */   public int row1;
/*    */   public int col1;
/*    */   
/*    */   public D1Submatrix64F() {}
/*    */   
/*    */   public D1Submatrix64F(D1Matrix64F original) {
/* 46 */     set(original);
/*    */   }
/*    */ 
/*    */   
/*    */   public D1Submatrix64F(D1Matrix64F original, int row0, int row1, int col0, int col1) {
/* 51 */     set(original, row0, row1, col0, col1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(D1Matrix64F original, int row0, int row1, int col0, int col1) {
/* 56 */     this.original = original;
/* 57 */     this.row0 = row0;
/* 58 */     this.col0 = col0;
/* 59 */     this.row1 = row1;
/* 60 */     this.col1 = col1;
/*    */   }
/*    */   
/*    */   public void set(D1Matrix64F original) {
/* 64 */     this.original = original;
/* 65 */     this.row1 = original.numRows;
/* 66 */     this.col1 = original.numCols;
/*    */   }
/*    */   
/*    */   public int getRows() {
/* 70 */     return this.row1 - this.row0;
/*    */   }
/*    */   
/*    */   public int getCols() {
/* 74 */     return this.col1 - this.col0;
/*    */   }
/*    */   
/*    */   public double get(int row, int col) {
/* 78 */     return this.original.get(row + this.row0, col + this.col0);
/*    */   }
/*    */   
/*    */   public void set(int row, int col, double value) {
/* 82 */     this.original.set(row + this.row0, col + this.col0, value);
/*    */   }
/*    */   
/*    */   public SimpleMatrix extract() {
/* 86 */     SimpleMatrix ret = new SimpleMatrix(this.row1 - this.row0, this.col1 - this.col0);
/*    */     
/* 88 */     for (int i = 0; i < ret.numRows(); i++) {
/* 89 */       for (int j = 0; j < ret.numCols(); j++) {
/* 90 */         ret.set(i, j, get(i, j));
/*    */       }
/*    */     } 
/*    */     
/* 94 */     return ret;
/*    */   }
/*    */   
/*    */   public void print() {
/* 98 */     MatrixIO.print(System.out, this.original, "%6.3f", this.row0, this.row1, this.col0, this.col1);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\D1Submatrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */