/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.CholeskyDecomposition;
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CholeskyDecompositionCommon_D64
/*     */   implements CholeskyDecomposition<DenseMatrix64F>
/*     */ {
/*  54 */   protected int maxWidth = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int n;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DenseMatrix64F T;
/*     */ 
/*     */   
/*     */   protected double[] t;
/*     */ 
/*     */   
/*     */   protected double[] vv;
/*     */ 
/*     */   
/*     */   protected boolean lower;
/*     */ 
/*     */ 
/*     */   
/*     */   public CholeskyDecompositionCommon_D64(boolean lower) {
/*  76 */     this.lower = lower;
/*     */   }
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  80 */     if (numRows != numCols) {
/*  81 */       throw new IllegalArgumentException("Can only decompose square matrices");
/*     */     }
/*     */     
/*  84 */     this.maxWidth = numCols;
/*     */     
/*  86 */     this.vv = new double[this.maxWidth];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLower() {
/*  97 */     return this.lower;
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
/*     */   public boolean decompose(DenseMatrix64F mat) {
/* 115 */     if (mat.numRows > this.maxWidth) {
/* 116 */       setExpectedMaxSize(mat.numRows, mat.numCols);
/* 117 */     } else if (mat.numRows != mat.numCols) {
/* 118 */       throw new IllegalArgumentException("Must be a square matrix.");
/*     */     } 
/*     */     
/* 121 */     this.n = mat.numRows;
/*     */     
/* 123 */     this.T = mat;
/* 124 */     this.t = this.T.data;
/*     */     
/* 126 */     if (this.lower) {
/* 127 */       return decomposeLower();
/*     */     }
/* 129 */     return decomposeUpper();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 135 */     return true;
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
/*     */   public DenseMatrix64F getT(DenseMatrix64F T) {
/* 155 */     if (T == null) {
/* 156 */       T = new DenseMatrix64F(this.n, this.n);
/*     */     } else {
/* 158 */       if (T.numRows != this.n || T.numCols != this.n) {
/* 159 */         throw new IllegalArgumentException("Unexpected matrix dimension for T.");
/*     */       }
/* 161 */       CommonOps.fill((D1Matrix64F)T, 0.0D);
/*     */     } 
/*     */ 
/*     */     
/* 165 */     if (this.lower) {
/* 166 */       for (int i = 0; i < this.n; i++) {
/* 167 */         for (int j = 0; j <= i; j++) {
/* 168 */           T.unsafe_set(i, j, this.T.unsafe_get(i, j));
/*     */         }
/*     */       } 
/*     */     } else {
/* 172 */       for (int i = 0; i < this.n; i++) {
/* 173 */         for (int j = i; j < this.n; j++) {
/* 174 */           T.unsafe_set(i, j, this.T.unsafe_get(i, j));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 179 */     return T;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getT() {
/* 188 */     return this.T;
/*     */   }
/*     */   
/*     */   public double[] _getVV() {
/* 192 */     return this.vv;
/*     */   }
/*     */   
/*     */   protected abstract boolean decomposeLower();
/*     */   
/*     */   protected abstract boolean decomposeUpper();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\chol\CholeskyDecompositionCommon_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */