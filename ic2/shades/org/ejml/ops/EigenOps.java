/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.EigenPowerMethod;
/*     */ import ic2.shades.org.ejml.alg.dense.mult.VectorVectorMult;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Eigenpair;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.LinearSolverFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.EigenDecomposition;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenOps
/*     */ {
/*     */   public static double computeEigenValue(DenseMatrix64F A, DenseMatrix64F eigenVector) {
/*  53 */     double bottom = VectorVectorMult.innerProd((D1Matrix64F)eigenVector, (D1Matrix64F)eigenVector);
/*  54 */     double top = VectorVectorMult.innerProdA((D1Matrix64F)eigenVector, (D1Matrix64F)A, (D1Matrix64F)eigenVector);
/*     */     
/*  56 */     return top / bottom;
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
/*     */   public static Eigenpair computeEigenVector(DenseMatrix64F A, double eigenvalue) {
/*  80 */     if (A.numRows != A.numCols) {
/*  81 */       throw new IllegalArgumentException("Must be a square matrix.");
/*     */     }
/*  83 */     DenseMatrix64F M = new DenseMatrix64F(A.numRows, A.numCols);
/*     */     
/*  85 */     DenseMatrix64F x = new DenseMatrix64F(A.numRows, 1);
/*  86 */     DenseMatrix64F b = new DenseMatrix64F(A.numRows, 1);
/*     */     
/*  88 */     CommonOps.fill((D1Matrix64F)b, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     double origEigenvalue = eigenvalue;
/*     */     
/*  95 */     SpecializedOps.addIdentity((RowD1Matrix64F)A, (RowD1Matrix64F)M, -eigenvalue);
/*     */     
/*  97 */     double threshold = NormOps.normPInf(A) * UtilEjml.EPS;
/*     */     
/*  99 */     double prevError = Double.MAX_VALUE;
/* 100 */     boolean hasWorked = false;
/*     */     
/* 102 */     LinearSolver<DenseMatrix64F> solver = LinearSolverFactory.linear(M.numRows);
/*     */     
/* 104 */     double perp = 1.0E-4D;
/*     */     
/* 106 */     for (int i = 0; i < 200; i++) {
/* 107 */       boolean failed = false;
/*     */ 
/*     */       
/* 110 */       if (!solver.setA((Matrix64F)M)) {
/* 111 */         failed = true;
/*     */       } else {
/* 113 */         solver.solve((Matrix64F)b, (Matrix64F)x);
/*     */       } 
/*     */ 
/*     */       
/* 117 */       if (MatrixFeatures.hasUncountable((D1Matrix64F)x)) {
/* 118 */         failed = true;
/*     */       }
/*     */       
/* 121 */       if (failed) {
/* 122 */         if (!hasWorked) {
/*     */           
/* 124 */           double val = (i % 2 == 0) ? (1.0D - perp) : (1.0D + perp);
/*     */ 
/*     */ 
/*     */           
/* 128 */           eigenvalue = origEigenvalue * Math.pow(val, (i / 2 + 1));
/* 129 */           SpecializedOps.addIdentity((RowD1Matrix64F)A, (RowD1Matrix64F)M, -eigenvalue);
/*     */         }
/*     */         else {
/*     */           
/* 133 */           return new Eigenpair(eigenvalue, b);
/*     */         } 
/*     */       } else {
/* 136 */         hasWorked = true;
/*     */         
/* 138 */         b.set((D1Matrix64F)x);
/* 139 */         NormOps.normalizeF(b);
/*     */ 
/*     */         
/* 142 */         CommonOps.mult((RowD1Matrix64F)M, (RowD1Matrix64F)b, (RowD1Matrix64F)x);
/* 143 */         double error = NormOps.normPInf(x);
/*     */         
/* 145 */         if (error - prevError > UtilEjml.EPS * 10.0D) {
/*     */ 
/*     */ 
/*     */           
/* 149 */           prevError = Double.MAX_VALUE;
/* 150 */           hasWorked = false;
/* 151 */           double val = (i % 2 == 0) ? (1.0D - perp) : (1.0D + perp);
/* 152 */           eigenvalue = origEigenvalue * Math.pow(val, 1.0D);
/*     */         } else {
/*     */           
/* 155 */           if (error <= threshold || Math.abs(prevError - error) <= UtilEjml.EPS) {
/* 156 */             return new Eigenpair(eigenvalue, b);
/*     */           }
/*     */           
/* 159 */           prevError = error;
/* 160 */           eigenvalue = VectorVectorMult.innerProdA((D1Matrix64F)b, (D1Matrix64F)A, (D1Matrix64F)b);
/*     */         } 
/*     */         
/* 163 */         SpecializedOps.addIdentity((RowD1Matrix64F)A, (RowD1Matrix64F)M, -eigenvalue);
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     return null;
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
/*     */   public static Eigenpair dominantEigenpair(DenseMatrix64F A) {
/* 187 */     EigenPowerMethod power = new EigenPowerMethod(A.numRows);
/*     */ 
/*     */     
/* 190 */     if (!power.computeShiftInvert(A, 0.1D)) {
/* 191 */       return null;
/*     */     }
/* 193 */     return null;
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
/*     */   public static double[] boundLargestEigenValue(DenseMatrix64F A, double[] bound) {
/* 211 */     if (A.numRows != A.numCols) {
/* 212 */       throw new IllegalArgumentException("A must be a square matrix.");
/*     */     }
/* 214 */     double min = Double.MAX_VALUE;
/* 215 */     double max = 0.0D;
/*     */     
/* 217 */     int n = A.numRows;
/*     */     
/* 219 */     for (int i = 0; i < n; i++) {
/* 220 */       double total = 0.0D;
/* 221 */       for (int j = 0; j < n; j++) {
/* 222 */         double v = A.get(i, j);
/* 223 */         if (v < 0.0D) throw new IllegalArgumentException("Matrix must be positive");
/*     */         
/* 225 */         total += v;
/*     */       } 
/*     */       
/* 228 */       if (total < min) {
/* 229 */         min = total;
/*     */       }
/*     */       
/* 232 */       if (total > max) {
/* 233 */         max = total;
/*     */       }
/*     */     } 
/*     */     
/* 237 */     if (bound == null) {
/* 238 */       bound = new double[2];
/*     */     }
/* 240 */     bound[0] = min;
/* 241 */     bound[1] = max;
/*     */     
/* 243 */     return bound;
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
/*     */   public static DenseMatrix64F createMatrixD(EigenDecomposition eig) {
/* 257 */     int N = eig.getNumberOfEigenvalues();
/*     */     
/* 259 */     DenseMatrix64F D = new DenseMatrix64F(N, N);
/*     */     
/* 261 */     for (int i = 0; i < N; i++) {
/* 262 */       Complex64F c = eig.getEigenvalue(i);
/*     */       
/* 264 */       if (c.isReal()) {
/* 265 */         D.set(i, i, c.real);
/*     */       }
/*     */     } 
/*     */     
/* 269 */     return D;
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
/*     */   public static DenseMatrix64F createMatrixV(EigenDecomposition<DenseMatrix64F> eig) {
/* 283 */     int N = eig.getNumberOfEigenvalues();
/*     */     
/* 285 */     DenseMatrix64F V = new DenseMatrix64F(N, N);
/*     */     
/* 287 */     for (int i = 0; i < N; i++) {
/* 288 */       Complex64F c = eig.getEigenvalue(i);
/*     */       
/* 290 */       if (c.isReal()) {
/* 291 */         DenseMatrix64F v = (DenseMatrix64F)eig.getEigenVector(i);
/*     */         
/* 293 */         if (v != null) {
/* 294 */           for (int j = 0; j < N; j++) {
/* 295 */             V.set(j, i, v.get(j, 0));
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     return V;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\EigenOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */