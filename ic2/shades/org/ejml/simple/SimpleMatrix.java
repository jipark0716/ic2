/*     */ package ic2.shades.org.ejml.simple;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.generic.GenericMatrixOps;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ import ic2.shades.org.ejml.ops.RandomMatrices;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleMatrix
/*     */   extends SimpleBase<SimpleMatrix>
/*     */ {
/*     */   public static final int END = 2147483647;
/*     */   
/*     */   public SimpleMatrix(int numRows, int numCols, boolean rowMajor, double... data) {
/* 120 */     this.mat = new DenseMatrix64F(numRows, numCols, rowMajor, data);
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
/*     */   public SimpleMatrix(double[][] data) {
/* 136 */     this.mat = new DenseMatrix64F(data);
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
/*     */   public SimpleMatrix(int numRows, int numCols) {
/* 148 */     this.mat = new DenseMatrix64F(numRows, numCols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMatrix(SimpleMatrix orig) {
/* 157 */     this.mat = orig.mat.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMatrix(DenseMatrix64F orig) {
/* 166 */     this.mat = orig.copy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMatrix(ReshapeMatrix64F orig) {
/* 175 */     this.mat = new DenseMatrix64F(orig.numRows, orig.numCols);
/*     */     
/* 177 */     GenericMatrixOps.copy(orig, (ReshapeMatrix64F)this.mat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMatrix() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleMatrix wrap(DenseMatrix64F internalMat) {
/* 192 */     SimpleMatrix ret = new SimpleMatrix();
/* 193 */     ret.mat = internalMat;
/* 194 */     return ret;
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
/*     */   public static SimpleMatrix identity(int width) {
/* 206 */     SimpleMatrix ret = new SimpleMatrix(width, width);
/*     */     
/* 208 */     CommonOps.setIdentity((RowD1Matrix64F)ret.mat);
/*     */     
/* 210 */     return ret;
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
/*     */   public static SimpleMatrix diag(double... vals) {
/* 229 */     DenseMatrix64F m = CommonOps.diag(vals);
/* 230 */     SimpleMatrix ret = wrap(m);
/* 231 */     return ret;
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
/*     */   public static SimpleMatrix random(int numRows, int numCols, double minValue, double maxValue, Random rand) {
/* 248 */     SimpleMatrix ret = new SimpleMatrix(numRows, numCols);
/* 249 */     RandomMatrices.setRandom((D1Matrix64F)ret.mat, minValue, maxValue, rand);
/* 250 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleMatrix createMatrix(int numRows, int numCols) {
/* 258 */     return new SimpleMatrix(numRows, numCols);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\simple\SimpleMatrix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */