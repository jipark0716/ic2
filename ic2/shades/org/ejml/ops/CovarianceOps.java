/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverSafe;
/*     */ import ic2.shades.org.ejml.alg.dense.misc.UnrolledInverseFromMinor;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.LinearSolverFactory;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
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
/*     */ public class CovarianceOps
/*     */ {
/*  37 */   public static double TOL = 1.0E-9D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidFast(DenseMatrix64F cov) {
/*  47 */     return MatrixFeatures.isDiagonalPositive(cov);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int isValid(DenseMatrix64F cov) {
/*  57 */     if (!MatrixFeatures.isDiagonalPositive(cov)) {
/*  58 */       return 1;
/*     */     }
/*  60 */     if (!MatrixFeatures.isSymmetric(cov, TOL)) {
/*  61 */       return 2;
/*     */     }
/*  63 */     if (!MatrixFeatures.isPositiveSemidefinite(cov)) {
/*  64 */       return 3;
/*     */     }
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean invert(DenseMatrix64F cov) {
/*  77 */     return invert(cov, cov);
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
/*     */   public static boolean invert(DenseMatrix64F cov, DenseMatrix64F cov_inv) {
/*  89 */     if (cov.numCols <= 4) {
/*  90 */       if (cov.numCols != cov.numRows) {
/*  91 */         throw new IllegalArgumentException("Must be a square matrix.");
/*     */       }
/*     */       
/*  94 */       if (cov.numCols >= 2) {
/*  95 */         UnrolledInverseFromMinor.inv(cov, cov_inv);
/*     */       } else {
/*  97 */         cov_inv.data[0] = 1.0D / cov_inv.data[0];
/*     */       } 
/*     */     } else {
/* 100 */       LinearSolver<DenseMatrix64F> solver = LinearSolverFactory.symmPosDef(cov.numRows);
/*     */       
/* 102 */       LinearSolverSafe linearSolverSafe = new LinearSolverSafe(solver);
/* 103 */       if (!linearSolverSafe.setA((Matrix64F)cov))
/* 104 */         return false; 
/* 105 */       linearSolverSafe.invert((Matrix64F)cov_inv);
/*     */     } 
/* 107 */     return true;
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
/*     */   public static void randomVector(DenseMatrix64F cov, DenseMatrix64F vector, Random rand) {
/* 122 */     CovarianceRandomDraw rng = new CovarianceRandomDraw(rand, cov);
/* 123 */     rng.next(vector);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\CovarianceOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */