/*     */ package ic2.shades.org.ejml.alg.dense.linsol.lu;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionBase_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverAbstract;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class LinearSolverLuBase
/*     */   extends LinearSolverAbstract
/*     */ {
/*     */   protected LUDecompositionBase_D64 decomp;
/*     */   
/*     */   public LinearSolverLuBase(LUDecompositionBase_D64 decomp) {
/*  34 */     this.decomp = decomp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  40 */     _setA(A);
/*     */     
/*  42 */     return this.decomp.decompose((Matrix64F)A);
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/*  47 */     return this.decomp.quality();
/*     */   }
/*     */ 
/*     */   
/*     */   public void invert(DenseMatrix64F A_inv) {
/*  52 */     double[] vv = this.decomp._getVV();
/*  53 */     DenseMatrix64F LU = this.decomp.getLU();
/*     */     
/*  55 */     if (A_inv.numCols != LU.numCols || A_inv.numRows != LU.numRows) {
/*  56 */       throw new IllegalArgumentException("Unexpected matrix dimension");
/*     */     }
/*  58 */     int n = this.A.numCols;
/*     */     
/*  60 */     double[] dataInv = A_inv.data;
/*     */     
/*  62 */     for (int j = 0; j < n; j++) {
/*     */       
/*  64 */       for (int i = 0; i < n; ) { vv[i] = (i == j) ? 1.0D : 0.0D; i++; }
/*  65 */        this.decomp._solveVectorInternal(vv);
/*     */       
/*  67 */       int index = j;
/*  68 */       for (int k = 0; k < n; ) { dataInv[index] = vv[k]; k++; index += n; }
/*     */     
/*     */     } 
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
/*     */   public void improveSol(DenseMatrix64F b, DenseMatrix64F x) {
/*  82 */     if (b.numCols != x.numCols) {
/*  83 */       throw new IllegalArgumentException("bad shapes");
/*     */     }
/*     */     
/*  86 */     double[] dataA = this.A.data;
/*  87 */     double[] dataB = b.data;
/*  88 */     double[] dataX = x.data;
/*     */     
/*  90 */     int nc = b.numCols;
/*  91 */     int n = b.numCols;
/*     */     
/*  93 */     double[] vv = this.decomp._getVV();
/*  94 */     DenseMatrix64F LU = this.decomp.getLU();
/*     */ 
/*     */     
/*  97 */     for (int k = 0; k < nc; k++) {
/*  98 */       int i; for (i = 0; i < n; i++) {
/*     */         
/* 100 */         double sdp = -dataB[i * nc + k];
/*     */         
/* 102 */         for (int j = 0; j < n; j++) {
/* 103 */           sdp += dataA[i * n + j] * dataX[j * nc + k];
/*     */         }
/*     */         
/* 106 */         vv[i] = sdp;
/*     */       } 
/*     */       
/* 109 */       this.decomp._solveVectorInternal(vv);
/* 110 */       for (i = 0; i < n; i++) {
/* 111 */         dataX[i * nc + k] = dataX[i * nc + k] - vv[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 123 */     return false;
/*     */   }
/*     */   
/*     */   public LUDecompositionBase_D64 getDecomposer() {
/* 127 */     return this.decomp;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\lu\LinearSolverLuBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */