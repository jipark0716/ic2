/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.svd;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.bidiagonal.BidiagonalDecompositionRow_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.bidiagonal.BidiagonalDecompositionTall_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.svd.implicitqr.SvdImplicitQrAlgorithm;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.BidiagonalDecomposition;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.SingularValueDecomposition;
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
/*     */ public class SvdImplicitQrDecompose_D64
/*     */   implements SingularValueDecomposition<DenseMatrix64F>
/*     */ {
/*     */   private int numRows;
/*     */   private int numCols;
/*     */   private int numRowsT;
/*     */   private int numColsT;
/*     */   private boolean canUseTallBidiagonal;
/*     */   private BidiagonalDecomposition<DenseMatrix64F> bidiag;
/*  61 */   private SvdImplicitQrAlgorithm qralg = new SvdImplicitQrAlgorithm();
/*     */   
/*     */   double[] diag;
/*     */   
/*     */   double[] off;
/*     */   
/*     */   private DenseMatrix64F Ut;
/*     */   
/*     */   private DenseMatrix64F Vt;
/*     */   
/*     */   private double[] singularValues;
/*     */   
/*     */   private int numSingular;
/*     */   
/*     */   private boolean compact;
/*     */   
/*     */   private boolean computeU;
/*     */   
/*     */   private boolean computeV;
/*     */   
/*     */   private boolean prefComputeU;
/*     */   
/*     */   private boolean prefComputeV;
/*     */   
/*     */   private boolean transposed;
/*     */   
/*  87 */   private DenseMatrix64F A_mod = new DenseMatrix64F(1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SvdImplicitQrDecompose_D64(boolean compact, boolean computeU, boolean computeV, boolean canUseTallBidiagonal) {
/* 100 */     this.compact = compact;
/* 101 */     this.prefComputeU = computeU;
/* 102 */     this.prefComputeV = computeV;
/* 103 */     this.canUseTallBidiagonal = canUseTallBidiagonal;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getSingularValues() {
/* 108 */     return this.singularValues;
/*     */   }
/*     */ 
/*     */   
/*     */   public int numberOfSingularValues() {
/* 113 */     return this.numSingular;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompact() {
/* 118 */     return this.compact;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getU(DenseMatrix64F U, boolean transpose) {
/* 123 */     if (!this.prefComputeU)
/* 124 */       throw new IllegalArgumentException("As requested U was not computed."); 
/* 125 */     if (transpose) {
/* 126 */       if (U == null)
/* 127 */         return this.Ut; 
/* 128 */       if (U.numRows != this.Ut.numRows || U.numCols != this.Ut.numCols) {
/* 129 */         throw new IllegalArgumentException("Unexpected shape of U");
/*     */       }
/* 131 */       U.set((D1Matrix64F)this.Ut);
/*     */     } else {
/* 133 */       if (U == null) {
/* 134 */         U = new DenseMatrix64F(this.Ut.numCols, this.Ut.numRows);
/* 135 */       } else if (U.numRows != this.Ut.numCols || U.numCols != this.Ut.numRows) {
/* 136 */         throw new IllegalArgumentException("Unexpected shape of U");
/*     */       } 
/* 138 */       CommonOps.transpose(this.Ut, U);
/*     */     } 
/*     */     
/* 141 */     return U;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getV(DenseMatrix64F V, boolean transpose) {
/* 146 */     if (!this.prefComputeV)
/* 147 */       throw new IllegalArgumentException("As requested V was not computed."); 
/* 148 */     if (transpose) {
/* 149 */       if (V == null)
/* 150 */         return this.Vt; 
/* 151 */       if (V.numRows != this.Vt.numRows || V.numCols != this.Vt.numCols) {
/* 152 */         throw new IllegalArgumentException("Unexpected shape of V");
/*     */       }
/* 154 */       V.set((D1Matrix64F)this.Vt);
/*     */     } else {
/* 156 */       if (V == null) {
/* 157 */         V = new DenseMatrix64F(this.Vt.numCols, this.Vt.numRows);
/* 158 */       } else if (V.numRows != this.Vt.numCols || V.numCols != this.Vt.numRows) {
/* 159 */         throw new IllegalArgumentException("Unexpected shape of V");
/* 160 */       }  CommonOps.transpose(this.Vt, V);
/*     */     } 
/*     */     
/* 163 */     return V;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getW(DenseMatrix64F W) {
/* 168 */     int m = this.compact ? this.numSingular : this.numRows;
/* 169 */     int n = this.compact ? this.numSingular : this.numCols;
/*     */     
/* 171 */     if (W == null) {
/* 172 */       W = new DenseMatrix64F(m, n);
/*     */     } else {
/* 174 */       W.reshape(m, n, false);
/* 175 */       W.zero();
/*     */     } 
/*     */     
/* 178 */     for (int i = 0; i < this.numSingular; i++) {
/* 179 */       W.unsafe_set(i, i, this.singularValues[i]);
/*     */     }
/*     */     
/* 182 */     return W;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F orig) {
/* 187 */     if (!setup(orig)) {
/* 188 */       return false;
/*     */     }
/* 190 */     if (bidiagonalization(orig)) {
/* 191 */       return false;
/*     */     }
/* 193 */     if (computeUWV()) {
/* 194 */       return false;
/*     */     }
/*     */     
/* 197 */     makeSingularPositive();
/*     */ 
/*     */     
/* 200 */     undoTranspose();
/*     */     
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean bidiagonalization(DenseMatrix64F orig) {
/* 212 */     if (this.transposed) {
/* 213 */       this.A_mod.reshape(orig.numCols, orig.numRows, false);
/* 214 */       CommonOps.transpose(orig, this.A_mod);
/*     */     } else {
/* 216 */       this.A_mod.reshape(orig.numRows, orig.numCols, false);
/* 217 */       this.A_mod.set((D1Matrix64F)orig);
/*     */     } 
/* 219 */     return !this.bidiag.decompose((Matrix64F)this.A_mod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void undoTranspose() {
/* 226 */     if (this.transposed) {
/* 227 */       DenseMatrix64F temp = this.Vt;
/* 228 */       this.Vt = this.Ut;
/* 229 */       this.Ut = temp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean computeUWV() {
/* 237 */     this.bidiag.getDiagonal(this.diag, this.off);
/* 238 */     this.qralg.setMatrix(this.numRowsT, this.numColsT, this.diag, this.off);
/*     */ 
/*     */ 
/*     */     
/* 242 */     if (this.computeU)
/* 243 */       this.Ut = (DenseMatrix64F)this.bidiag.getU((ReshapeMatrix64F)this.Ut, true, this.compact); 
/* 244 */     if (this.computeV) {
/* 245 */       this.Vt = (DenseMatrix64F)this.bidiag.getV((ReshapeMatrix64F)this.Vt, true, this.compact);
/*     */     }
/* 247 */     this.qralg.setFastValues(false);
/* 248 */     if (this.computeU) {
/* 249 */       this.qralg.setUt(this.Ut);
/*     */     } else {
/* 251 */       this.qralg.setUt(null);
/* 252 */     }  if (this.computeV) {
/* 253 */       this.qralg.setVt(this.Vt);
/*     */     } else {
/* 255 */       this.qralg.setVt(null);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     boolean ret = !this.qralg.process();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 264 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean setup(DenseMatrix64F orig) {
/* 268 */     this.transposed = (orig.numCols > orig.numRows);
/*     */ 
/*     */     
/* 271 */     if (this.transposed) {
/* 272 */       this.computeU = this.prefComputeV;
/* 273 */       this.computeV = this.prefComputeU;
/* 274 */       this.numRowsT = orig.numCols;
/* 275 */       this.numColsT = orig.numRows;
/*     */     } else {
/* 277 */       this.computeU = this.prefComputeU;
/* 278 */       this.computeV = this.prefComputeV;
/* 279 */       this.numRowsT = orig.numRows;
/* 280 */       this.numColsT = orig.numCols;
/*     */     } 
/*     */     
/* 283 */     this.numRows = orig.numRows;
/* 284 */     this.numCols = orig.numCols;
/*     */     
/* 286 */     if (this.numRows == 0 || this.numCols == 0) {
/* 287 */       return false;
/*     */     }
/* 289 */     if (this.diag == null || this.diag.length < this.numColsT) {
/* 290 */       this.diag = new double[this.numColsT];
/* 291 */       this.off = new double[this.numColsT - 1];
/*     */     } 
/*     */ 
/*     */     
/* 295 */     if (this.canUseTallBidiagonal && this.numRows > this.numCols * 2 && !this.computeU) {
/* 296 */       if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionTall_D64)) {
/* 297 */         this.bidiag = (BidiagonalDecomposition<DenseMatrix64F>)new BidiagonalDecompositionTall_D64();
/*     */       }
/* 299 */     } else if (this.bidiag == null || !(this.bidiag instanceof BidiagonalDecompositionRow_D64)) {
/* 300 */       this.bidiag = (BidiagonalDecomposition<DenseMatrix64F>)new BidiagonalDecompositionRow_D64();
/*     */     } 
/*     */     
/* 303 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeSingularPositive() {
/* 311 */     this.numSingular = this.qralg.getNumberOfSingularValues();
/* 312 */     this.singularValues = this.qralg.getSingularValues();
/*     */     
/* 314 */     for (int i = 0; i < this.numSingular; i++) {
/* 315 */       double val = this.qralg.getSingularValue(i);
/*     */       
/* 317 */       if (val < 0.0D) {
/* 318 */         this.singularValues[i] = 0.0D - val;
/*     */         
/* 320 */         if (this.computeU) {
/*     */ 
/*     */           
/* 323 */           int start = i * this.Ut.numCols;
/* 324 */           int stop = start + this.Ut.numCols;
/*     */           
/* 326 */           for (int j = start; j < stop; j++) {
/* 327 */             this.Ut.set(j, 0.0D - this.Ut.get(j));
/*     */           }
/*     */         } 
/*     */       } else {
/* 331 */         this.singularValues[i] = val;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int numRows() {
/* 338 */     return this.numRows;
/*     */   }
/*     */ 
/*     */   
/*     */   public int numCols() {
/* 343 */     return this.numCols;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\svd\SvdImplicitQrDecompose_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */