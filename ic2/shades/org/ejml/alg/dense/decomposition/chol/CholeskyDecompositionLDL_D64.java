/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.CholeskyLDLDecomposition;
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
/*     */ 
/*     */ public class CholeskyDecompositionLDL_D64
/*     */   implements CholeskyLDLDecomposition<DenseMatrix64F>
/*     */ {
/*     */   private int maxWidth;
/*     */   private int n;
/*     */   private DenseMatrix64F L;
/*     */   private double[] el;
/*     */   private double[] d;
/*     */   double[] vv;
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  63 */     if (numRows != numCols) {
/*  64 */       throw new IllegalArgumentException("Can only decompose square matrices");
/*     */     }
/*     */     
/*  67 */     this.maxWidth = numRows;
/*     */     
/*  69 */     this.L = new DenseMatrix64F(this.maxWidth, this.maxWidth);
/*  70 */     this.el = this.L.data;
/*     */     
/*  72 */     this.vv = new double[this.maxWidth];
/*  73 */     this.d = new double[this.maxWidth];
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
/*     */   public boolean decompose(DenseMatrix64F mat) {
/*  90 */     if (mat.numRows > this.maxWidth) {
/*  91 */       setExpectedMaxSize(mat.numRows, mat.numCols);
/*  92 */     } else if (mat.numRows != mat.numCols) {
/*  93 */       throw new RuntimeException("Can only decompose square matrices");
/*     */     } 
/*  95 */     this.n = mat.numRows;
/*     */     
/*  97 */     this.L.setReshape(mat);
/*     */     
/*  99 */     double d_inv = 0.0D; int i;
/* 100 */     for (i = 0; i < this.n; i++) {
/* 101 */       for (int j = i; j < this.n; j++) {
/* 102 */         double sum = this.el[i * this.n + j];
/*     */         
/* 104 */         for (int k = 0; k < i; k++) {
/* 105 */           sum -= this.el[i * this.n + k] * this.el[j * this.n + k] * this.d[k];
/*     */         }
/*     */         
/* 108 */         if (i == j) {
/*     */           
/* 110 */           if (sum <= 0.0D) {
/* 111 */             return false;
/*     */           }
/* 113 */           this.d[i] = sum;
/* 114 */           d_inv = 1.0D / sum;
/* 115 */           this.el[i * this.n + i] = 1.0D;
/*     */         } else {
/* 117 */           this.el[j * this.n + i] = sum * d_inv;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     for (i = 0; i < this.n; i++) {
/* 123 */       for (int j = i + 1; j < this.n; j++) {
/* 124 */         this.el[i * this.n + j] = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDiagonal() {
/* 143 */     return this.d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getL() {
/* 153 */     return this.L;
/*     */   }
/*     */   
/*     */   public double[] _getVV() {
/* 157 */     return this.vv;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getL(DenseMatrix64F L) {
/* 162 */     if (L == null) {
/* 163 */       L = this.L.copy();
/*     */     } else {
/* 165 */       L.set((D1Matrix64F)this.L);
/*     */     } 
/*     */     
/* 168 */     return L;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getD(DenseMatrix64F D) {
/* 173 */     return CommonOps.diag(D, this.L.numCols, this.d);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\chol\CholeskyDecompositionLDL_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */