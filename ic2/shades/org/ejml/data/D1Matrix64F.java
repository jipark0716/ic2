/*     */ package ic2.shades.org.ejml.data;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.mult.MatrixDimensionException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class D1Matrix64F
/*     */   extends ReshapeMatrix64F
/*     */ {
/*     */   public double[] data;
/*     */   
/*     */   public double[] getData() {
/*  43 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(double[] data) {
/*  50 */     this.data = data;
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
/*     */   public abstract int getIndex(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(D1Matrix64F b) {
/*  73 */     if (this.numRows != b.numRows || this.numCols != b.numCols) {
/*  74 */       throw new MatrixDimensionException("The two matrices do not have compatible shapes.");
/*     */     }
/*     */     
/*  77 */     int dataLength = b.getNumElements();
/*     */     
/*  79 */     System.arraycopy(b.data, 0, this.data, 0, dataLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int index) {
/*  90 */     return this.data[index];
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
/*     */   public double set(int index, double val) {
/* 102 */     this.data[index] = val; return val;
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
/*     */   public double plus(int index, double val) {
/* 122 */     this.data[index] = this.data[index] + val; return this.data[index] + val;
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
/*     */   public double minus(int index, double val) {
/* 142 */     this.data[index] = this.data[index] - val; return this.data[index] - val;
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
/*     */   public double times(int index, double val) {
/* 162 */     this.data[index] = this.data[index] * val; return this.data[index] * val;
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
/*     */   public double div(int index, double val) {
/* 182 */     this.data[index] = this.data[index] / val; return this.data[index] / val;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\D1Matrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */