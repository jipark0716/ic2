/*     */ package ic2.shades.org.ejml.simple;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.EigenDecomposition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleEVD<T extends SimpleMatrix>
/*     */ {
/*     */   private EigenDecomposition<DenseMatrix64F> eig;
/*     */   DenseMatrix64F mat;
/*     */   
/*     */   public SimpleEVD(DenseMatrix64F mat) {
/*  41 */     this.mat = mat;
/*  42 */     this.eig = DecompositionFactory.eig(mat.numCols, true);
/*  43 */     if (!this.eig.decompose((Matrix64F)mat)) {
/*  44 */       throw new RuntimeException("Eigenvalue Decomposition failed");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/*  53 */     return this.eig.getNumberOfEigenvalues();
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
/*     */   public Complex64F getEigenvalue(int index) {
/*  72 */     return this.eig.getEigenvalue(index);
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
/*     */   public T getEigenVector(int index) {
/*  85 */     return (T)SimpleMatrix.wrap((DenseMatrix64F)this.eig.getEigenVector(index));
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
/* 102 */     return DecompositionFactory.quality(this.mat, this.eig);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EigenDecomposition getEVD() {
/* 111 */     return this.eig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexMax() {
/* 120 */     int indexMax = 0;
/* 121 */     double max = getEigenvalue(0).getMagnitude2();
/*     */     
/* 123 */     int N = getNumberOfEigenvalues();
/* 124 */     for (int i = 1; i < N; i++) {
/* 125 */       double m = getEigenvalue(i).getMagnitude2();
/* 126 */       if (m > max) {
/* 127 */         max = m;
/* 128 */         indexMax = i;
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     return indexMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndexMin() {
/* 141 */     int indexMin = 0;
/* 142 */     double min = getEigenvalue(0).getMagnitude2();
/*     */     
/* 144 */     int N = getNumberOfEigenvalues();
/* 145 */     for (int i = 1; i < N; i++) {
/* 146 */       double m = getEigenvalue(i).getMagnitude2();
/* 147 */       if (m < min) {
/* 148 */         min = m;
/* 149 */         indexMin = i;
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return indexMin;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\simple\SimpleEVD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */