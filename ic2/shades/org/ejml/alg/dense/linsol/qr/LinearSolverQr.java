/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
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
/*     */ public class LinearSolverQr
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   private QRDecomposition<DenseMatrix64F> decomposer;
/*  47 */   protected int maxRows = -1;
/*  48 */   protected int maxCols = -1;
/*     */   
/*     */   protected DenseMatrix64F Q;
/*     */   
/*     */   protected DenseMatrix64F R;
/*     */   
/*     */   private DenseMatrix64F Y;
/*     */   
/*     */   private DenseMatrix64F Z;
/*     */ 
/*     */   
/*     */   public LinearSolverQr(QRDecomposition<DenseMatrix64F> decomposer) {
/*  60 */     this.decomposer = decomposer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int maxRows, int maxCols) {
/*  71 */     this.maxRows = maxRows; this.maxCols = maxCols;
/*     */     
/*  73 */     this.Q = new DenseMatrix64F(maxRows, maxRows);
/*  74 */     this.R = new DenseMatrix64F(maxRows, maxCols);
/*     */     
/*  76 */     this.Y = new DenseMatrix64F(maxRows, 1);
/*  77 */     this.Z = new DenseMatrix64F(maxRows, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  87 */     if (A.numRows > this.maxRows || A.numCols > this.maxCols) {
/*  88 */       setMaxSize(A.numRows, A.numCols);
/*     */     }
/*     */     
/*  91 */     _setA(A);
/*  92 */     if (!this.decomposer.decompose((Matrix64F)A)) {
/*  93 */       return false;
/*     */     }
/*  95 */     this.Q.reshape(this.numRows, this.numRows, false);
/*  96 */     this.R.reshape(this.numRows, this.numCols, false);
/*  97 */     this.decomposer.getQ((Matrix64F)this.Q, false);
/*  98 */     this.decomposer.getR((Matrix64F)this.R, false);
/*     */     
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/* 105 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.R);
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
/* 116 */     if (X.numRows != this.numCols)
/* 117 */       throw new IllegalArgumentException("Unexpected dimensions for X"); 
/* 118 */     if (B.numRows != this.numRows || B.numCols != X.numCols) {
/* 119 */       throw new IllegalArgumentException("Unexpected dimensions for B");
/*     */     }
/* 121 */     int BnumCols = B.numCols;
/*     */     
/* 123 */     this.Y.reshape(this.numRows, 1, false);
/* 124 */     this.Z.reshape(this.numRows, 1, false);
/*     */ 
/*     */     
/* 127 */     for (int colB = 0; colB < BnumCols; colB++) {
/*     */       int i;
/*     */       
/* 130 */       for (i = 0; i < this.numRows; i++) {
/* 131 */         this.Y.data[i] = B.get(i, colB);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 136 */       CommonOps.multTransA((RowD1Matrix64F)this.Q, (RowD1Matrix64F)this.Y, (RowD1Matrix64F)this.Z);
/*     */ 
/*     */       
/* 139 */       TriangularSolver.solveU(this.R.data, this.Z.data, this.numCols);
/*     */ 
/*     */       
/* 142 */       for (i = 0; i < this.numCols; i++) {
/* 143 */         X.set(i, colB, this.Z.data[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 150 */     return this.decomposer.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 155 */     return false;
/*     */   }
/*     */   
/*     */   public QRDecomposition<DenseMatrix64F> getDecomposer() {
/* 159 */     return this.decomposer;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getQ() {
/* 163 */     return this.Q;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getR() {
/* 167 */     return this.R;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\LinearSolverQr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */