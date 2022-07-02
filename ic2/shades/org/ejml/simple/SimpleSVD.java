/*     */ package ic2.shades.org.ejml.simple;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.SingularValueDecomposition;
/*     */ import ic2.shades.org.ejml.ops.SingularOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleSVD<T extends SimpleMatrix>
/*     */ {
/*     */   private SingularValueDecomposition<DenseMatrix64F> svd;
/*     */   private T U;
/*     */   private T W;
/*     */   private T V;
/*     */   private DenseMatrix64F mat;
/*     */   
/*     */   public SimpleSVD(DenseMatrix64F mat, boolean compact) {
/*  51 */     this.mat = mat;
/*  52 */     this.svd = DecompositionFactory.svd(mat.numRows, mat.numCols, true, true, compact);
/*  53 */     if (!this.svd.decompose((Matrix64F)mat))
/*  54 */       throw new RuntimeException("Decomposition failed"); 
/*  55 */     this.U = (T)SimpleMatrix.wrap((DenseMatrix64F)this.svd.getU(null, false));
/*  56 */     this.W = (T)SimpleMatrix.wrap((DenseMatrix64F)this.svd.getW(null));
/*  57 */     this.V = (T)SimpleMatrix.wrap((DenseMatrix64F)this.svd.getV(null, false));
/*     */ 
/*     */     
/*  60 */     SingularOps.descendingOrder(this.U.getMatrix(), false, this.W.getMatrix(), this.V.getMatrix(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getU() {
/*  71 */     return this.U;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getW() {
/*  81 */     return this.W;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getV() {
/*  92 */     return this.V;
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
/*     */   public double quality() {
/* 109 */     return DecompositionFactory.quality(this.mat, this.U.getMatrix(), this.W.getMatrix(), this.V.transpose().getMatrix());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleMatrix nullSpace() {
/* 118 */     return SimpleMatrix.wrap(SingularOps.nullSpace(this.svd, null, UtilEjml.EPS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSingleValue(int index) {
/* 128 */     return this.W.get(index, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int rank() {
/* 139 */     return SingularOps.rank(this.svd, 10.0D * UtilEjml.EPS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nullity() {
/* 150 */     return SingularOps.nullity(this.svd, 10.0D * UtilEjml.EPS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SingularValueDecomposition getSVD() {
/* 159 */     return this.svd;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\simple\SimpleSVD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */