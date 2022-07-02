/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholderTran_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearSolverQrHouseTran
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   private QRDecompositionHouseholderTran_D64 decomposer;
/*     */   private double[] a;
/*  52 */   protected int maxRows = -1;
/*  53 */   protected int maxCols = -1;
/*     */ 
/*     */   
/*     */   private DenseMatrix64F QR;
/*     */   
/*     */   private DenseMatrix64F U;
/*     */ 
/*     */   
/*     */   public LinearSolverQrHouseTran() {
/*  62 */     this.decomposer = new QRDecompositionHouseholderTran_D64();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxRows, int maxCols) {
/*  67 */     this.maxRows = maxRows; this.maxCols = maxCols;
/*     */     
/*  69 */     this.a = new double[maxRows];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  79 */     if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
/*  80 */       setMaxSize(A.numRows, A.numCols);
/*     */     }
/*  82 */     _setA(A);
/*  83 */     if (!this.decomposer.decompose(A)) {
/*  84 */       return false;
/*     */     }
/*  86 */     this.QR = this.decomposer.getQR();
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double quality() {
/*  94 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.QR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/* 105 */     if (X.numRows != this.numCols)
/* 106 */       throw new IllegalArgumentException("Unexpected dimensions for X: X rows = " + X.numRows + " expected = " + this.numCols); 
/* 107 */     if (B.numRows != this.numRows || B.numCols != X.numCols) {
/* 108 */       throw new IllegalArgumentException("Unexpected dimensions for B");
/*     */     }
/* 110 */     this.U = this.decomposer.getR(this.U, true);
/* 111 */     double[] gammas = this.decomposer.getGammas();
/* 112 */     double[] dataQR = this.QR.data;
/*     */     
/* 114 */     int BnumCols = B.numCols;
/*     */ 
/*     */     
/* 117 */     for (int colB = 0; colB < BnumCols; colB++) {
/*     */ 
/*     */       
/* 120 */       for (int j = 0; j < this.numRows; j++) {
/* 121 */         this.a[j] = B.data[j * BnumCols + colB];
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       for (int n = 0; n < this.numCols; n++) {
/* 130 */         int indexU = n * this.numRows + n + 1;
/*     */         
/* 132 */         double ub = this.a[n];
/*     */         int k;
/* 134 */         for (k = n + 1; k < this.numRows; k++, indexU++) {
/* 135 */           ub += dataQR[indexU] * this.a[k];
/*     */         }
/*     */ 
/*     */         
/* 139 */         ub *= gammas[n];
/*     */         
/* 141 */         this.a[n] = this.a[n] - ub;
/* 142 */         indexU = n * this.numRows + n + 1;
/* 143 */         for (k = n + 1; k < this.numRows; k++, indexU++) {
/* 144 */           this.a[k] = this.a[k] - dataQR[indexU] * ub;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 149 */       TriangularSolver.solveU(this.U.data, this.a, this.numCols);
/*     */ 
/*     */       
/* 152 */       for (int i = 0; i < this.numCols; i++) {
/* 153 */         X.data[i * X.numCols + colB] = this.a[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 160 */     return this.decomposer.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 165 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\LinearSolverQrHouseTran.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */