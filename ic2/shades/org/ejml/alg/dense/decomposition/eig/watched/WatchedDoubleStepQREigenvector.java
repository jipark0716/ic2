/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig.watched;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.LinearSolverFactory;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ import ic2.shades.org.ejml.ops.NormOps;
/*     */ import ic2.shades.org.ejml.ops.SpecializedOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WatchedDoubleStepQREigenvector
/*     */ {
/*     */   WatchedDoubleStepQREigen implicit;
/*     */   DenseMatrix64F Q;
/*     */   DenseMatrix64F[] eigenvectors;
/*     */   DenseMatrix64F eigenvectorTemp;
/*     */   LinearSolver solver;
/*     */   Complex64F[] origEigenvalues;
/*     */   int N;
/*     */   int[] splits;
/*     */   int numSplits;
/*     */   int x1;
/*     */   int x2;
/*     */   int indexVal;
/*     */   boolean onscript;
/*     */   
/*     */   public boolean process(WatchedDoubleStepQREigen implicit, DenseMatrix64F A, DenseMatrix64F Q_h) {
/*  62 */     this.implicit = implicit;
/*     */     
/*  64 */     if (this.N != A.numRows) {
/*  65 */       this.N = A.numRows;
/*  66 */       this.Q = new DenseMatrix64F(this.N, this.N);
/*  67 */       this.splits = new int[this.N];
/*  68 */       this.origEigenvalues = new Complex64F[this.N];
/*  69 */       this.eigenvectors = new DenseMatrix64F[this.N];
/*  70 */       this.eigenvectorTemp = new DenseMatrix64F(this.N, 1);
/*     */       
/*  72 */       this.solver = LinearSolverFactory.linear(0);
/*     */     } else {
/*     */       
/*  75 */       this.eigenvectors = new DenseMatrix64F[this.N];
/*     */     } 
/*  77 */     System.arraycopy(implicit.eigenvalues, 0, this.origEigenvalues, 0, this.N);
/*     */     
/*  79 */     implicit.setup(A);
/*  80 */     implicit.setQ(this.Q);
/*  81 */     this.numSplits = 0;
/*  82 */     this.onscript = true;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (!findQandR()) {
/*  88 */       return false;
/*     */     }
/*  90 */     return extractVectors(Q_h);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean extractVectors(DenseMatrix64F Q_h) {
/*  95 */     UtilEjml.memset(this.eigenvectorTemp.data, 0.0D);
/*     */ 
/*     */     
/*  98 */     boolean triangular = true;
/*  99 */     for (int i = 0; i < this.N; i++) {
/*     */       
/* 101 */       Complex64F c = this.implicit.eigenvalues[this.N - i - 1];
/*     */       
/* 103 */       if (triangular && !c.isReal()) {
/* 104 */         triangular = false;
/*     */       }
/* 106 */       if (c.isReal() && this.eigenvectors[this.N - i - 1] == null) {
/* 107 */         solveEigenvectorDuplicateEigenvalue(c.real, i, triangular);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 112 */     if (Q_h != null) {
/* 113 */       DenseMatrix64F temp = new DenseMatrix64F(this.N, 1);
/* 114 */       for (int j = 0; j < this.N; j++) {
/* 115 */         DenseMatrix64F v = this.eigenvectors[j];
/*     */         
/* 117 */         if (v != null) {
/* 118 */           CommonOps.mult((RowD1Matrix64F)Q_h, (RowD1Matrix64F)v, (RowD1Matrix64F)temp);
/* 119 */           this.eigenvectors[j] = temp;
/* 120 */           temp = v;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void solveEigenvectorDuplicateEigenvalue(double real, int first, boolean isTriangle) {
/* 130 */     double scale = Math.abs(real);
/* 131 */     if (scale == 0.0D) scale = 1.0D;
/*     */     
/* 133 */     this.eigenvectorTemp.reshape(this.N, 1, false);
/* 134 */     this.eigenvectorTemp.zero();
/*     */     
/* 136 */     if (first > 0) {
/* 137 */       if (isTriangle) {
/* 138 */         solveUsingTriangle(real, first, this.eigenvectorTemp);
/*     */       } else {
/* 140 */         solveWithLU(real, first, this.eigenvectorTemp);
/*     */       } 
/*     */     }
/*     */     
/* 144 */     this.eigenvectorTemp.reshape(this.N, 1, false);
/*     */     
/* 146 */     for (int i = first; i < this.N; i++) {
/* 147 */       Complex64F c = this.implicit.eigenvalues[this.N - i - 1];
/*     */       
/* 149 */       if (c.isReal() && Math.abs(c.real - real) / scale < 100.0D * UtilEjml.EPS) {
/* 150 */         this.eigenvectorTemp.data[i] = 1.0D;
/*     */         
/* 152 */         DenseMatrix64F v = new DenseMatrix64F(this.N, 1);
/* 153 */         CommonOps.multTransA((RowD1Matrix64F)this.Q, (RowD1Matrix64F)this.eigenvectorTemp, (RowD1Matrix64F)v);
/* 154 */         this.eigenvectors[this.N - i - 1] = v;
/* 155 */         NormOps.normalizeF(v);
/*     */         
/* 157 */         this.eigenvectorTemp.data[i] = 0.0D;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void solveUsingTriangle(double real, int index, DenseMatrix64F r) {
/*     */     int i;
/* 163 */     for (i = 0; i < index; i++) {
/* 164 */       this.implicit.A.add(i, i, -real);
/*     */     }
/*     */     
/* 167 */     SpecializedOps.subvector((RowD1Matrix64F)this.implicit.A, 0, index, index, false, 0, (RowD1Matrix64F)r);
/* 168 */     CommonOps.changeSign((D1Matrix64F)r);
/*     */     
/* 170 */     TriangularSolver.solveU(this.implicit.A.data, r.data, this.implicit.A.numRows, 0, index);
/*     */     
/* 172 */     for (i = 0; i < index; i++) {
/* 173 */       this.implicit.A.add(i, i, real);
/*     */     }
/*     */   }
/*     */   
/*     */   private void solveWithLU(double real, int index, DenseMatrix64F r) {
/* 178 */     DenseMatrix64F A = new DenseMatrix64F(index, index);
/*     */     
/* 180 */     CommonOps.extract((ReshapeMatrix64F)this.implicit.A, 0, index, 0, index, (ReshapeMatrix64F)A, 0, 0);
/*     */     
/* 182 */     for (int i = 0; i < index; i++) {
/* 183 */       A.add(i, i, -real);
/*     */     }
/*     */     
/* 186 */     r.reshape(index, 1, false);
/*     */     
/* 188 */     SpecializedOps.subvector((RowD1Matrix64F)this.implicit.A, 0, index, index, false, 0, (RowD1Matrix64F)r);
/* 189 */     CommonOps.changeSign((D1Matrix64F)r);
/*     */ 
/*     */     
/* 192 */     if (!this.solver.setA((Matrix64F)A))
/* 193 */       throw new RuntimeException("Solve failed"); 
/* 194 */     this.solver.solve((Matrix64F)r, (Matrix64F)r);
/*     */   }
/*     */   
/*     */   public boolean findQandR() {
/* 198 */     CommonOps.setIdentity((RowD1Matrix64F)this.Q);
/*     */     
/* 200 */     this.x1 = 0;
/* 201 */     this.x2 = this.N - 1;
/*     */ 
/*     */     
/* 204 */     this.indexVal = 0;
/* 205 */     while (this.indexVal < this.N) {
/* 206 */       if (!findNextEigenvalue()) {
/* 207 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     return true;
/*     */   }
/*     */   
/*     */   private boolean findNextEigenvalue() {
/* 219 */     boolean foundEigen = false;
/* 220 */     while (!foundEigen && this.implicit.steps < this.implicit.maxIterations) {
/*     */       
/* 222 */       this.implicit.incrementSteps();
/*     */       
/* 224 */       if (this.x2 < this.x1) {
/* 225 */         moveToNextSplit(); continue;
/* 226 */       }  if (this.x2 - this.x1 == 0) {
/* 227 */         this.implicit.addEigenAt(this.x1);
/* 228 */         this.x2--;
/* 229 */         this.indexVal++;
/* 230 */         foundEigen = true; continue;
/* 231 */       }  if (this.x2 - this.x1 == 1 && !this.implicit.isReal2x2(this.x1, this.x2)) {
/* 232 */         this.implicit.addComputedEigen2x2(this.x1, this.x2);
/* 233 */         this.x2 -= 2;
/* 234 */         this.indexVal += 2;
/* 235 */         foundEigen = true; continue;
/* 236 */       }  if (this.implicit.steps - this.implicit.lastExceptional > this.implicit.exceptionalThreshold) {
/*     */ 
/*     */ 
/*     */         
/* 240 */         this.implicit.exceptionalShift(this.x1, this.x2);
/* 241 */         this.implicit.lastExceptional = this.implicit.steps; continue;
/* 242 */       }  if (this.implicit.isZero(this.x2, this.x2 - 1)) {
/*     */         
/* 244 */         this.implicit.addEigenAt(this.x2);
/* 245 */         foundEigen = true;
/* 246 */         this.x2--;
/* 247 */         this.indexVal++; continue;
/*     */       } 
/* 249 */       checkSplitPerformImplicit();
/*     */     } 
/*     */     
/* 252 */     return foundEigen;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkSplitPerformImplicit() {
/* 258 */     for (int i = this.x2; i > this.x1; i--) {
/* 259 */       if (this.implicit.isZero(i, i - 1)) {
/* 260 */         this.x1 = i;
/* 261 */         this.splits[this.numSplits++] = i - 1;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if (this.onscript) {
/* 268 */       if (this.implicit.steps > this.implicit.exceptionalThreshold / 2) {
/* 269 */         this.onscript = false;
/*     */       } else {
/* 271 */         Complex64F a = this.origEigenvalues[this.indexVal];
/*     */ 
/*     */         
/* 274 */         if (a.isReal()) {
/* 275 */           this.implicit.performImplicitSingleStep(this.x1, this.x2, a.getReal());
/* 276 */         } else if (this.x2 < this.N - 2) {
/* 277 */           this.implicit.performImplicitDoubleStep(this.x1, this.x2, a.real, a.imaginary);
/*     */         } else {
/* 279 */           this.onscript = false;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 284 */     } else if (this.x2 - this.x1 >= 1 && this.x2 < this.N - 2) {
/* 285 */       this.implicit.implicitDoubleStep(this.x1, this.x2);
/*     */     } else {
/* 287 */       this.implicit.performImplicitSingleStep(this.x1, this.x2, this.implicit.A.get(this.x2, this.x2));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void moveToNextSplit() {
/* 293 */     if (this.numSplits <= 0) {
/* 294 */       throw new RuntimeException("bad");
/*     */     }
/* 296 */     this.x2 = this.splits[--this.numSplits];
/*     */     
/* 298 */     if (this.numSplits > 0) {
/* 299 */       this.x1 = this.splits[this.numSplits - 1] + 1;
/*     */     } else {
/* 301 */       this.x1 = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getQ() {
/* 306 */     return this.Q;
/*     */   }
/*     */   
/*     */   public WatchedDoubleStepQREigen getImplicit() {
/* 310 */     return this.implicit;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F[] getEigenvectors() {
/* 314 */     return this.eigenvectors;
/*     */   }
/*     */   
/*     */   public Complex64F[] getEigenvalues() {
/* 318 */     return this.implicit.eigenvalues;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\watched\WatchedDoubleStepQREigenvector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */