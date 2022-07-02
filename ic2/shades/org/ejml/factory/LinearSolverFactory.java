/*     */ package ic2.shades.org.ejml.factory;
/*     */ 
/*     */ import ic2.shades.org.ejml.EjmlParameters;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionCommon_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionInner_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionAlt_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionBase_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRColPivDecompositionHouseholderColumn_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.AdjustableLinearSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.chol.LinearSolverChol;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.chol.LinearSolverCholBlock64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.lu.LinearSolverLu;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.qr.AdjLinearSolverQr;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.qr.LinearSolverQrBlock64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.qr.LinearSolverQrHouseCol;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.qr.LinearSolverQrpHouseCol;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.qr.SolvePseudoInverseQrp;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.svd.SolvePseudoInverseSvd;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRPDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearSolverFactory
/*     */ {
/*     */   public static LinearSolver<DenseMatrix64F> general(int numRows, int numCols) {
/*  50 */     if (numRows == numCols) {
/*  51 */       return linear(numRows);
/*     */     }
/*  53 */     return leastSquares(numRows, numCols);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LinearSolver<DenseMatrix64F> linear(int matrixSize) {
/*  62 */     return (LinearSolver<DenseMatrix64F>)new LinearSolverLu((LUDecompositionBase_D64)new LUDecompositionAlt_D64());
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
/*     */   public static LinearSolver<DenseMatrix64F> leastSquares(int numRows, int numCols) {
/*  74 */     if (numCols < EjmlParameters.SWITCH_BLOCK64_QR) {
/*  75 */       return (LinearSolver<DenseMatrix64F>)new LinearSolverQrHouseCol();
/*     */     }
/*  77 */     if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
/*  78 */       return (LinearSolver<DenseMatrix64F>)new LinearSolverQrBlock64();
/*     */     }
/*  80 */     return (LinearSolver<DenseMatrix64F>)new LinearSolverQrHouseCol();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LinearSolver<DenseMatrix64F> symmPosDef(int matrixWidth) {
/*  90 */     if (matrixWidth < EjmlParameters.SWITCH_BLOCK64_CHOLESKY) {
/*  91 */       CholeskyDecompositionInner_D64 choleskyDecompositionInner_D641 = new CholeskyDecompositionInner_D64(true);
/*  92 */       return (LinearSolver<DenseMatrix64F>)new LinearSolverChol((CholeskyDecompositionCommon_D64)choleskyDecompositionInner_D641);
/*     */     } 
/*  94 */     if (EjmlParameters.MEMORY == EjmlParameters.MemoryUsage.FASTER) {
/*  95 */       return (LinearSolver<DenseMatrix64F>)new LinearSolverCholBlock64();
/*     */     }
/*  97 */     CholeskyDecompositionInner_D64 choleskyDecompositionInner_D64 = new CholeskyDecompositionInner_D64(true);
/*  98 */     return (LinearSolver<DenseMatrix64F>)new LinearSolverChol((CholeskyDecompositionCommon_D64)choleskyDecompositionInner_D64);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LinearSolver<DenseMatrix64F> leastSquaresQrPivot(boolean computeNorm2, boolean computeQ) {
/* 129 */     QRColPivDecompositionHouseholderColumn_D64 decomposition = new QRColPivDecompositionHouseholderColumn_D64();
/*     */ 
/*     */     
/* 132 */     if (computeQ) {
/* 133 */       return (LinearSolver<DenseMatrix64F>)new SolvePseudoInverseQrp((QRPDecomposition)decomposition, computeNorm2);
/*     */     }
/* 135 */     return (LinearSolver<DenseMatrix64F>)new LinearSolverQrpHouseCol(decomposition, computeNorm2);
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
/*     */   public static LinearSolver<DenseMatrix64F> pseudoInverse(boolean useSVD) {
/* 155 */     if (useSVD) {
/* 156 */       return (LinearSolver<DenseMatrix64F>)new SolvePseudoInverseSvd();
/*     */     }
/* 158 */     return leastSquaresQrPivot(true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AdjustableLinearSolver adjustable() {
/* 166 */     return (AdjustableLinearSolver)new AdjLinearSolverQr();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\factory\LinearSolverFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */