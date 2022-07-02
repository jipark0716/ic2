/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.EigenDecomposition;
/*     */ import ic2.shades.org.ejml.ops.MatrixFeatures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwitchingEigenDecomposition
/*     */   implements EigenDecomposition<DenseMatrix64F>
/*     */ {
/*     */   private double tol;
/*     */   EigenDecomposition<DenseMatrix64F> symmetricAlg;
/*     */   EigenDecomposition<DenseMatrix64F> generalAlg;
/*     */   boolean symmetric;
/*     */   boolean computeVectors;
/*  47 */   DenseMatrix64F A = new DenseMatrix64F(1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SwitchingEigenDecomposition(int matrixSize, boolean computeVectors, double tol) {
/*  55 */     this.symmetricAlg = DecompositionFactory.eig(matrixSize, computeVectors, true);
/*  56 */     this.generalAlg = DecompositionFactory.eig(matrixSize, computeVectors, false);
/*  57 */     this.computeVectors = computeVectors;
/*  58 */     this.tol = tol;
/*     */   }
/*     */   
/*     */   public SwitchingEigenDecomposition(int matrixSize) {
/*  62 */     this(matrixSize, true, 1.0E-8D);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/*  67 */     return this.symmetric ? this.symmetricAlg.getNumberOfEigenvalues() : this.generalAlg.getNumberOfEigenvalues();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Complex64F getEigenvalue(int index) {
/*  73 */     return this.symmetric ? this.symmetricAlg.getEigenvalue(index) : this.generalAlg.getEigenvalue(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getEigenVector(int index) {
/*  79 */     if (!this.computeVectors) {
/*  80 */       throw new IllegalArgumentException("Configured to not compute eignevectors");
/*     */     }
/*  82 */     return this.symmetric ? (DenseMatrix64F)this.symmetricAlg.getEigenVector(index) : (DenseMatrix64F)this.generalAlg.getEigenVector(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F orig) {
/*  88 */     this.A.setReshape(orig);
/*     */     
/*  90 */     this.symmetric = MatrixFeatures.isSymmetric(this.A, this.tol);
/*     */     
/*  92 */     return this.symmetric ? this.symmetricAlg.decompose((Matrix64F)this.A) : this.generalAlg.decompose((Matrix64F)this.A);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 102 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\SwitchingEigenDecomposition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */