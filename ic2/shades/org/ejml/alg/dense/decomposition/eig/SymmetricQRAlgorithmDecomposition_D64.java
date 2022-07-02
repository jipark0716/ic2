/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.symm.SymmetricQREigenHelper;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.symm.SymmetricQrAlgorithm;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.EigenDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricQRAlgorithmDecomposition_D64
/*     */   implements EigenDecomposition<DenseMatrix64F>
/*     */ {
/*     */   private TridiagonalSimilarDecomposition<DenseMatrix64F> decomp;
/*     */   private SymmetricQREigenHelper helper;
/*     */   private SymmetricQrAlgorithm vector;
/*     */   private boolean computeVectorsWithValues = false;
/*     */   private double[] values;
/*     */   private double[] diag;
/*     */   private double[] off;
/*     */   private double[] diagSaved;
/*     */   private double[] offSaved;
/*     */   private DenseMatrix64F V;
/*     */   private DenseMatrix64F[] eigenvectors;
/*     */   boolean computeVectors;
/*     */   
/*     */   public SymmetricQRAlgorithmDecomposition_D64(TridiagonalSimilarDecomposition<DenseMatrix64F> decomp, boolean computeVectors) {
/*  81 */     this.decomp = decomp;
/*  82 */     this.computeVectors = computeVectors;
/*     */     
/*  84 */     this.helper = new SymmetricQREigenHelper();
/*     */     
/*  86 */     this.vector = new SymmetricQrAlgorithm(this.helper);
/*     */   }
/*     */ 
/*     */   
/*     */   public SymmetricQRAlgorithmDecomposition_D64(boolean computeVectors) {
/*  91 */     this(DecompositionFactory.tridiagonal(0), computeVectors);
/*     */   }
/*     */   
/*     */   public void setComputeVectorsWithValues(boolean computeVectorsWithValues) {
/*  95 */     if (!this.computeVectors) {
/*  96 */       throw new IllegalArgumentException("Compute eigenvalues has been set to false");
/*     */     }
/*  98 */     this.computeVectorsWithValues = computeVectorsWithValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxIterations(int max) {
/* 108 */     this.vector.setMaxIterations(max);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/* 113 */     return this.helper.getMatrixSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Complex64F getEigenvalue(int index) {
/* 118 */     return new Complex64F(this.values[index], 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getEigenVector(int index) {
/* 123 */     return this.eigenvectors[index];
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
/*     */   public boolean decompose(DenseMatrix64F orig) {
/* 135 */     if (orig.numCols != orig.numRows)
/* 136 */       throw new IllegalArgumentException("Matrix must be square."); 
/* 137 */     if (orig.numCols <= 0) {
/* 138 */       return false;
/*     */     }
/* 140 */     int N = orig.numRows;
/*     */ 
/*     */     
/* 143 */     if (!this.decomp.decompose((Matrix64F)orig)) {
/* 144 */       return false;
/*     */     }
/* 146 */     if (this.diag == null || this.diag.length < N) {
/* 147 */       this.diag = new double[N];
/* 148 */       this.off = new double[N - 1];
/*     */     } 
/* 150 */     this.decomp.getDiagonal(this.diag, this.off);
/*     */ 
/*     */     
/* 153 */     this.helper.init(this.diag, this.off, N);
/*     */     
/* 155 */     if (this.computeVectors) {
/* 156 */       if (this.computeVectorsWithValues) {
/* 157 */         return extractTogether();
/*     */       }
/* 159 */       return extractSeparate(N);
/*     */     } 
/*     */     
/* 162 */     return computeEigenValues();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 168 */     return this.decomp.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean extractTogether() {
/* 173 */     this.V = (DenseMatrix64F)this.decomp.getQ((ReshapeMatrix64F)this.V, true);
/*     */ 
/*     */     
/* 176 */     this.helper.setQ(this.V);
/*     */     
/* 178 */     this.vector.setFastEigenvalues(false);
/*     */ 
/*     */     
/* 181 */     if (!this.vector.process(-1, null, null)) {
/* 182 */       return false;
/*     */     }
/*     */     
/* 185 */     this.eigenvectors = CommonOps.rowsToVector(this.V, this.eigenvectors);
/*     */ 
/*     */     
/* 188 */     this.values = this.helper.copyEigenvalues(this.values);
/*     */     
/* 190 */     return true;
/*     */   }
/*     */   
/*     */   private boolean extractSeparate(int numCols) {
/* 194 */     if (!computeEigenValues()) {
/* 195 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 199 */     this.helper.reset(numCols);
/* 200 */     this.diagSaved = this.helper.swapDiag(this.diagSaved);
/* 201 */     this.offSaved = this.helper.swapOff(this.offSaved);
/*     */ 
/*     */     
/* 204 */     this.V = (DenseMatrix64F)this.decomp.getQ((ReshapeMatrix64F)this.V, true);
/*     */ 
/*     */     
/* 207 */     this.vector.setQ(this.V);
/*     */ 
/*     */     
/* 210 */     if (!this.vector.process(-1, null, null, this.values)) {
/* 211 */       return false;
/*     */     }
/*     */     
/* 214 */     this.values = this.helper.copyEigenvalues(this.values);
/*     */     
/* 216 */     this.eigenvectors = CommonOps.rowsToVector(this.V, this.eigenvectors);
/*     */     
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean computeEigenValues() {
/* 228 */     this.diagSaved = this.helper.copyDiag(this.diagSaved);
/* 229 */     this.offSaved = this.helper.copyOff(this.offSaved);
/*     */     
/* 231 */     this.vector.setQ(null);
/* 232 */     this.vector.setFastEigenvalues(true);
/*     */ 
/*     */     
/* 235 */     if (!this.vector.process(-1, null, null)) {
/* 236 */       return false;
/*     */     }
/*     */     
/* 239 */     this.values = this.helper.copyEigenvalues(this.values);
/* 240 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\SymmetricQRAlgorithmDecomposition_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */