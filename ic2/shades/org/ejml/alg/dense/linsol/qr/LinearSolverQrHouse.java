/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholder_D64;
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
/*     */ public class LinearSolverQrHouse
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   private QRDecompositionHouseholder_D64 decomposer;
/*     */   private double[] a;
/*     */   private double[] u;
/*  48 */   private int maxRows = -1;
/*     */ 
/*     */   
/*     */   private DenseMatrix64F QR;
/*     */   
/*     */   private double[] gammas;
/*     */ 
/*     */   
/*     */   public LinearSolverQrHouse() {
/*  57 */     this.decomposer = new QRDecompositionHouseholder_D64();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxRows) {
/*  63 */     this.maxRows = maxRows;
/*     */     
/*  65 */     this.a = new double[maxRows];
/*  66 */     this.u = new double[maxRows];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  76 */     if (A.numRows > this.maxRows) {
/*  77 */       setMaxSize(A.numRows);
/*     */     }
/*     */     
/*  80 */     _setA(A);
/*  81 */     if (!this.decomposer.decompose(A)) {
/*  82 */       return false;
/*     */     }
/*  84 */     this.gammas = this.decomposer.getGammas();
/*  85 */     this.QR = this.decomposer.getQR();
/*     */     
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/*  92 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.QR);
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
/* 103 */     if (X.numRows != this.numCols)
/* 104 */       throw new IllegalArgumentException("Unexpected dimensions for X"); 
/* 105 */     if (B.numRows != this.numRows || B.numCols != X.numCols) {
/* 106 */       throw new IllegalArgumentException("Unexpected dimensions for B");
/*     */     }
/* 108 */     int BnumCols = B.numCols;
/*     */ 
/*     */     
/* 111 */     for (int colB = 0; colB < BnumCols; colB++) {
/*     */ 
/*     */       
/* 114 */       for (int j = 0; j < this.numRows; j++) {
/* 115 */         this.a[j] = B.data[j * BnumCols + colB];
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 123 */       for (int n = 0; n < this.numCols; n++) {
/* 124 */         this.u[n] = 1.0D;
/* 125 */         double ub = this.a[n];
/*     */         int k;
/* 127 */         for (k = n + 1; k < this.numRows; k++) {
/* 128 */           this.u[k] = this.QR.unsafe_get(k, n); ub += this.QR.unsafe_get(k, n) * this.a[k];
/*     */         } 
/*     */ 
/*     */         
/* 132 */         ub *= this.gammas[n];
/*     */         
/* 134 */         for (k = n; k < this.numRows; k++) {
/* 135 */           this.a[k] = this.a[k] - this.u[k] * ub;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 140 */       TriangularSolver.solveU(this.QR.data, this.a, this.numCols);
/*     */ 
/*     */       
/* 143 */       for (int i = 0; i < this.numCols; i++) {
/* 144 */         X.data[i * X.numCols + colB] = this.a[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 151 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 156 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\LinearSolverQrHouse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */