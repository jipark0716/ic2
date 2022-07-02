/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.lu;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.LUDecomposition;
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
/*     */ public abstract class LUDecompositionBase_D64
/*     */   implements LUDecomposition<DenseMatrix64F>
/*     */ {
/*     */   protected DenseMatrix64F LU;
/*  41 */   protected int maxWidth = -1;
/*     */   
/*     */   protected int m;
/*     */   
/*     */   protected int n;
/*     */   
/*     */   protected double[] dataLU;
/*     */   
/*     */   protected double[] vv;
/*     */   
/*     */   protected int[] indx;
/*     */   
/*     */   protected int[] pivot;
/*     */   
/*     */   protected double pivsign;
/*     */ 
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  59 */     this.LU = new DenseMatrix64F(numRows, numCols);
/*     */     
/*  61 */     this.dataLU = this.LU.data;
/*  62 */     this.maxWidth = Math.max(numRows, numCols);
/*     */     
/*  64 */     this.vv = new double[this.maxWidth];
/*  65 */     this.indx = new int[this.maxWidth];
/*  66 */     this.pivot = new int[this.maxWidth];
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getLU() {
/*  70 */     return this.LU;
/*     */   }
/*     */   
/*     */   public int[] getIndx() {
/*  74 */     return this.indx;
/*     */   }
/*     */   
/*     */   public int[] getPivot() {
/*  78 */     return this.pivot;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getLower(DenseMatrix64F lower) {
/*  94 */     int numRows = this.LU.numRows;
/*  95 */     int numCols = (this.LU.numRows < this.LU.numCols) ? this.LU.numRows : this.LU.numCols;
/*     */     
/*  97 */     if (lower == null) {
/*  98 */       lower = new DenseMatrix64F(numRows, numCols);
/*     */     } else {
/* 100 */       if (lower.numCols != numCols || lower.numRows != numRows)
/* 101 */         throw new IllegalArgumentException("Unexpected matrix dimension"); 
/* 102 */       CommonOps.fill((D1Matrix64F)lower, 0.0D);
/*     */     } 
/*     */     int i;
/* 105 */     for (i = 0; i < numCols; i++) {
/* 106 */       lower.set(i, i, 1.0D);
/*     */       
/* 108 */       for (int j = 0; j < i; j++) {
/* 109 */         lower.set(i, j, this.LU.get(i, j));
/*     */       }
/*     */     } 
/*     */     
/* 113 */     if (numRows > numCols) {
/* 114 */       for (i = numCols; i < numRows; i++) {
/* 115 */         for (int j = 0; j < numCols; j++) {
/* 116 */           lower.set(i, j, this.LU.get(i, j));
/*     */         }
/*     */       } 
/*     */     }
/* 120 */     return lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getUpper(DenseMatrix64F upper) {
/* 131 */     int numRows = (this.LU.numRows < this.LU.numCols) ? this.LU.numRows : this.LU.numCols;
/* 132 */     int numCols = this.LU.numCols;
/*     */     
/* 134 */     if (upper == null) {
/* 135 */       upper = new DenseMatrix64F(numRows, numCols);
/*     */     } else {
/* 137 */       if (upper.numCols != numCols || upper.numRows != numRows)
/* 138 */         throw new IllegalArgumentException("Unexpected matrix dimension"); 
/* 139 */       CommonOps.fill((D1Matrix64F)upper, 0.0D);
/*     */     } 
/*     */     
/* 142 */     for (int i = 0; i < numRows; i++) {
/* 143 */       for (int j = i; j < numCols; j++) {
/* 144 */         upper.set(i, j, this.LU.get(i, j));
/*     */       }
/*     */     } 
/*     */     
/* 148 */     return upper;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getPivot(DenseMatrix64F pivot) {
/* 152 */     return SpecializedOps.pivotMatrix(pivot, this.pivot, this.LU.numRows, false);
/*     */   }
/*     */   
/*     */   protected void decomposeCommonInit(DenseMatrix64F a) {
/* 156 */     if (a.numRows > this.maxWidth || a.numCols > this.maxWidth) {
/* 157 */       setExpectedMaxSize(a.numRows, a.numCols);
/*     */     }
/*     */     
/* 160 */     this.m = a.numRows;
/* 161 */     this.n = a.numCols;
/*     */     
/* 163 */     this.LU.setReshape(a);
/* 164 */     for (int i = 0; i < this.m; i++) {
/* 165 */       this.pivot[i] = i;
/*     */     }
/* 167 */     this.pivsign = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSingular() {
/* 178 */     for (int i = 0; i < this.m; i++) {
/* 179 */       if (Math.abs(this.dataLU[i * this.n + i]) < UtilEjml.EPS)
/* 180 */         return true; 
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double computeDeterminant() {
/* 192 */     if (this.m != this.n) {
/* 193 */       throw new IllegalArgumentException("Must be a square matrix.");
/*     */     }
/* 195 */     double ret = this.pivsign;
/*     */     
/* 197 */     int total = this.m * this.n; int i;
/* 198 */     for (i = 0; i < total; i += this.n + 1) {
/* 199 */       ret *= this.dataLU[i];
/*     */     }
/*     */     
/* 202 */     return ret;
/*     */   }
/*     */   
/*     */   public double quality() {
/* 206 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.LU);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _solveVectorInternal(double[] vv) {
/* 215 */     int ii = 0;
/*     */     
/* 217 */     for (int i = 0; i < this.n; i++) {
/* 218 */       int ip = this.indx[i];
/* 219 */       double sum = vv[ip];
/* 220 */       vv[ip] = vv[i];
/* 221 */       if (ii != 0) {
/*     */ 
/*     */         
/* 224 */         int index = i * this.n + ii - 1;
/* 225 */         for (int j = ii - 1; j < i; j++)
/* 226 */           sum -= this.dataLU[index++] * vv[j]; 
/* 227 */       } else if (sum != 0.0D) {
/* 228 */         ii = i + 1;
/*     */       } 
/* 230 */       vv[i] = sum;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     TriangularSolver.solveU(this.dataLU, vv, this.n);
/*     */   }
/*     */   
/*     */   public double[] _getVV() {
/* 238 */     return this.vv;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\lu\LUDecompositionBase_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */