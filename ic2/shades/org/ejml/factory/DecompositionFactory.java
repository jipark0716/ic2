/*     */ package ic2.shades.org.ejml.factory;
/*     */ 
/*     */ import ic2.shades.org.ejml.EjmlParameters;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionBlock_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionInner_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionLDL_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecomposition_B64_to_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.SwitchingEigenDecomposition;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.SymmetricQRAlgorithmDecomposition_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.WatchedDoubleStepQRDecomposition_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.hessenberg.TridiagonalDecompositionHouseholder_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.hessenberg.TridiagonalDecomposition_B64_to_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionAlt_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRColPivDecompositionHouseholderColumn_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholderColumn_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.svd.SvdImplicitQrDecompose_D64;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.CholeskyDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.CholeskyLDLDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.EigenDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.LUDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRPDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.SingularValueDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;
/*     */ import ic2.shades.org.ejml.ops.EigenOps;
/*     */ import ic2.shades.org.ejml.ops.SpecializedOps;
/*     */ import ic2.shades.org.ejml.simple.SimpleBase;
/*     */ import ic2.shades.org.ejml.simple.SimpleMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DecompositionFactory
/*     */ {
/*     */   public static CholeskyDecomposition<DenseMatrix64F> chol(int matrixSize, boolean lower) {
/*  74 */     if (matrixSize < EjmlParameters.SWITCH_BLOCK64_CHOLESKY)
/*  75 */       return (CholeskyDecomposition<DenseMatrix64F>)new CholeskyDecompositionInner_D64(lower); 
/*  76 */     if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
/*  77 */       return (CholeskyDecomposition<DenseMatrix64F>)new CholeskyDecomposition_B64_to_D64(lower);
/*     */     }
/*  79 */     return (CholeskyDecomposition<DenseMatrix64F>)new CholeskyDecompositionBlock_D64(EjmlParameters.BLOCK_WIDTH_CHOL);
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
/*     */   public static CholeskyLDLDecomposition<DenseMatrix64F> cholLDL(int matrixSize) {
/*  92 */     return (CholeskyLDLDecomposition<DenseMatrix64F>)new CholeskyDecompositionLDL_D64();
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
/*     */   public static LUDecomposition<DenseMatrix64F> lu(int numRows, int numCol) {
/* 104 */     return (LUDecomposition<DenseMatrix64F>)new LUDecompositionAlt_D64();
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
/*     */   public static SingularValueDecomposition<DenseMatrix64F> svd(int numRows, int numCols, boolean needU, boolean needV, boolean compact) {
/* 123 */     return (SingularValueDecomposition<DenseMatrix64F>)new SvdImplicitQrDecompose_D64(compact, needU, needV, false);
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
/*     */   public static QRDecomposition<DenseMatrix64F> qr(int numRows, int numCols) {
/* 136 */     return (QRDecomposition<DenseMatrix64F>)new QRDecompositionHouseholderColumn_D64();
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
/*     */   public static QRPDecomposition<DenseMatrix64F> qrp(int numRows, int numCols) {
/* 149 */     return (QRPDecomposition<DenseMatrix64F>)new QRColPivDecompositionHouseholderColumn_D64();
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
/*     */   public static EigenDecomposition<DenseMatrix64F> eig(int matrixSize, boolean needVectors) {
/* 164 */     return (EigenDecomposition<DenseMatrix64F>)new SwitchingEigenDecomposition(matrixSize, needVectors, 1.0E-8D);
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
/*     */   public static EigenDecomposition<DenseMatrix64F> eig(int matrixSize, boolean computeVectors, boolean isSymmetric) {
/* 180 */     if (isSymmetric) {
/* 181 */       TridiagonalSimilarDecomposition<DenseMatrix64F> decomp = tridiagonal(matrixSize);
/* 182 */       return (EigenDecomposition<DenseMatrix64F>)new SymmetricQRAlgorithmDecomposition_D64(decomp, computeVectors);
/*     */     } 
/* 184 */     return (EigenDecomposition<DenseMatrix64F>)new WatchedDoubleStepQRDecomposition_D64(computeVectors);
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
/*     */ 
/*     */   
/*     */   public static double quality(DenseMatrix64F orig, SingularValueDecomposition<DenseMatrix64F> svd) {
/* 206 */     return quality(orig, (DenseMatrix64F)svd.getU(null, false), (DenseMatrix64F)svd.getW(null), (DenseMatrix64F)svd.getV(null, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public static double quality(DenseMatrix64F orig, DenseMatrix64F U, DenseMatrix64F W, DenseMatrix64F Vt) {
/* 211 */     SimpleMatrix _U = SimpleMatrix.wrap(U);
/* 212 */     SimpleMatrix _W = SimpleMatrix.wrap(W);
/* 213 */     SimpleMatrix _Vt = SimpleMatrix.wrap(Vt);
/*     */     
/* 215 */     SimpleMatrix foundA = (SimpleMatrix)((SimpleMatrix)_U.mult((SimpleBase)_W)).mult((SimpleBase)_Vt);
/*     */     
/* 217 */     return SpecializedOps.diffNormF((D1Matrix64F)orig, (D1Matrix64F)foundA.getMatrix()) / foundA.normF();
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
/*     */   public static double quality(DenseMatrix64F orig, EigenDecomposition<DenseMatrix64F> eig) {
/* 237 */     SimpleMatrix A = SimpleMatrix.wrap(orig);
/* 238 */     SimpleMatrix V = SimpleMatrix.wrap(EigenOps.createMatrixV(eig));
/* 239 */     SimpleMatrix D = SimpleMatrix.wrap(EigenOps.createMatrixD(eig));
/*     */     
/* 241 */     SimpleMatrix L = (SimpleMatrix)A.mult((SimpleBase)V);
/* 242 */     SimpleMatrix R = (SimpleMatrix)V.mult((SimpleBase)D);
/*     */     
/* 244 */     SimpleMatrix diff = (SimpleMatrix)L.minus((SimpleBase)R);
/*     */     
/* 246 */     double top = diff.normF();
/* 247 */     double bottom = L.normF();
/*     */     
/* 249 */     double error = top / bottom;
/*     */     
/* 251 */     return error;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TridiagonalSimilarDecomposition<DenseMatrix64F> tridiagonal(int matrixSize) {
/* 261 */     if (matrixSize >= 1800) {
/* 262 */       return (TridiagonalSimilarDecomposition<DenseMatrix64F>)new TridiagonalDecomposition_B64_to_D64();
/*     */     }
/* 264 */     return (TridiagonalSimilarDecomposition<DenseMatrix64F>)new TridiagonalDecompositionHouseholder_D64();
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
/*     */   public static <T extends ic2.shades.org.ejml.data.ReshapeMatrix64F> boolean decomposeSafe(DecompositionInterface<T> decomp, T M) {
/* 278 */     if (decomp.inputModified()) {
/* 279 */       return decomp.decompose(M.copy());
/*     */     }
/* 281 */     return decomp.decompose((Matrix64F)M);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\factory\DecompositionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */