/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.watched.WatchedDoubleStepQREigenvalue;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.watched.WatchedDoubleStepQREigenvector;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.hessenberg.HessenbergSimilarDecomposition_D64;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WatchedDoubleStepQRDecomposition_D64
/*     */   implements EigenDecomposition<DenseMatrix64F>
/*     */ {
/*     */   HessenbergSimilarDecomposition_D64 hessenberg;
/*     */   WatchedDoubleStepQREigenvalue algValue;
/*     */   WatchedDoubleStepQREigenvector algVector;
/*     */   DenseMatrix64F H;
/*     */   boolean computeVectors;
/*     */   
/*     */   public WatchedDoubleStepQRDecomposition_D64(boolean computeVectors) {
/*  57 */     this.hessenberg = new HessenbergSimilarDecomposition_D64(10);
/*  58 */     this.algValue = new WatchedDoubleStepQREigenvalue();
/*  59 */     this.algVector = new WatchedDoubleStepQREigenvector();
/*     */     
/*  61 */     this.computeVectors = computeVectors;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F A) {
/*  67 */     if (!this.hessenberg.decompose(A)) {
/*  68 */       return false;
/*     */     }
/*  70 */     this.H = this.hessenberg.getH(null);
/*     */     
/*  72 */     (this.algValue.getImplicitQR()).createR = false;
/*     */ 
/*     */     
/*  75 */     if (!this.algValue.process(this.H)) {
/*  76 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     (this.algValue.getImplicitQR()).createR = true;
/*     */     
/*  84 */     if (this.computeVectors) {
/*  85 */       return this.algVector.process(this.algValue.getImplicitQR(), this.H, this.hessenberg.getQ(null));
/*     */     }
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/*  92 */     return this.hessenberg.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/*  97 */     return (this.algValue.getEigenvalues()).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public Complex64F getEigenvalue(int index) {
/* 102 */     return this.algValue.getEigenvalues()[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getEigenVector(int index) {
/* 107 */     return this.algVector.getEigenvectors()[index];
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\WatchedDoubleStepQRDecomposition_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */