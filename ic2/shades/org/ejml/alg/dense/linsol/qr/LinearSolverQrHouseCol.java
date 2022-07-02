/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholderColumn_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
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
/*     */ public class LinearSolverQrHouseCol
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   private QRDecompositionHouseholderColumn_D64 decomposer;
/*  51 */   private DenseMatrix64F a = new DenseMatrix64F(1, 1);
/*  52 */   private DenseMatrix64F temp = new DenseMatrix64F(1, 1);
/*     */   
/*  54 */   protected int maxRows = -1;
/*  55 */   protected int maxCols = -1;
/*     */   
/*     */   private double[][] QR;
/*  58 */   private DenseMatrix64F R = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*     */   private double[] gammas;
/*     */ 
/*     */   
/*     */   public LinearSolverQrHouseCol() {
/*  65 */     this.decomposer = new QRDecompositionHouseholderColumn_D64();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxRows, int maxCols) {
/*  70 */     this.maxRows = maxRows; this.maxCols = maxCols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  80 */     if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
/*  81 */       setMaxSize(A.numRows, A.numCols);
/*     */     }
/*  83 */     this.R.reshape(A.numCols, A.numCols);
/*  84 */     this.a.reshape(A.numRows, 1);
/*  85 */     this.temp.reshape(A.numRows, 1);
/*     */     
/*  87 */     _setA(A);
/*  88 */     if (!this.decomposer.decompose(A)) {
/*  89 */       return false;
/*     */     }
/*  91 */     this.gammas = this.decomposer.getGammas();
/*  92 */     this.QR = this.decomposer.getQR();
/*  93 */     this.decomposer.getR(this.R, true);
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/*  99 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.R);
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
/* 110 */     if (X.numRows != this.numCols)
/* 111 */       throw new IllegalArgumentException("Unexpected dimensions for X: X rows = " + X.numRows + " expected = " + this.numCols); 
/* 112 */     if (B.numRows != this.numRows || B.numCols != X.numCols) {
/* 113 */       throw new IllegalArgumentException("Unexpected dimensions for B");
/*     */     }
/* 115 */     int BnumCols = B.numCols;
/*     */ 
/*     */     
/* 118 */     for (int colB = 0; colB < BnumCols; colB++) {
/*     */ 
/*     */       
/* 121 */       for (int j = 0; j < this.numRows; j++) {
/* 122 */         this.a.data[j] = B.data[j * BnumCols + colB];
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 130 */       for (int n = 0; n < this.numCols; n++) {
/* 131 */         double[] u = this.QR[n];
/*     */         
/* 133 */         double vv = u[n];
/* 134 */         u[n] = 1.0D;
/* 135 */         QrHelperFunctions.rank1UpdateMultR(this.a, u, this.gammas[n], 0, n, this.numRows, this.temp.data);
/* 136 */         u[n] = vv;
/*     */       } 
/*     */ 
/*     */       
/* 140 */       TriangularSolver.solveU(this.R.data, this.a.data, this.numCols);
/*     */ 
/*     */       
/* 143 */       for (int i = 0; i < this.numCols; i++) {
/* 144 */         X.data[i * X.numCols + colB] = this.a.data[i];
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


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\LinearSolverQrHouseCol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */