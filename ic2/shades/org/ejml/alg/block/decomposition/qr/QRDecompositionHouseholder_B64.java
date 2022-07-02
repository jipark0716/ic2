/*     */ package ic2.shades.org.ejml.alg.block.decomposition.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*     */ import ic2.shades.org.ejml.alg.block.BlockMultiplication;
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QRDecompositionHouseholder_B64
/*     */   implements QRDecomposition<BlockMatrix64F>
/*     */ {
/*     */   private BlockMatrix64F dataA;
/*  69 */   private BlockMatrix64F dataW = new BlockMatrix64F(1, 1);
/*     */   
/*  71 */   private BlockMatrix64F dataWTA = new BlockMatrix64F(1, 1);
/*     */ 
/*     */   
/*     */   private int blockLength;
/*     */ 
/*     */   
/*  77 */   private D1Submatrix64F A = new D1Submatrix64F();
/*  78 */   private D1Submatrix64F Y = new D1Submatrix64F();
/*  79 */   private D1Submatrix64F W = new D1Submatrix64F((D1Matrix64F)this.dataW);
/*  80 */   private D1Submatrix64F WTA = new D1Submatrix64F((D1Matrix64F)this.dataWTA);
/*  81 */   private double[] temp = new double[1];
/*     */   
/*  83 */   private double[] gammas = new double[1];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean saveW = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMatrix64F getQR() {
/*  94 */     return this.dataA;
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
/*     */   public void setSaveW(boolean saveW) {
/* 111 */     this.saveW = saveW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMatrix64F getQ(BlockMatrix64F Q, boolean compact) {
/* 119 */     Q = initializeQ(Q, this.dataA.numRows, this.dataA.numCols, this.blockLength, compact);
/*     */     
/* 121 */     applyQ(Q, true);
/*     */     
/* 123 */     return Q;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockMatrix64F initializeQ(BlockMatrix64F Q, int numRows, int numCols, int blockLength, boolean compact) {
/* 132 */     int minLength = Math.min(numRows, numCols);
/* 133 */     if (compact) {
/* 134 */       if (Q == null) {
/* 135 */         Q = new BlockMatrix64F(numRows, minLength, blockLength);
/* 136 */         BlockMatrixOps.setIdentity(Q);
/*     */       } else {
/* 138 */         if (Q.numRows != numRows || Q.numCols != minLength) {
/* 139 */           throw new IllegalArgumentException("Unexpected matrix dimension. Found " + Q.numRows + " " + Q.numCols);
/*     */         }
/* 141 */         BlockMatrixOps.setIdentity(Q);
/*     */       }
/*     */     
/*     */     }
/* 145 */     else if (Q == null) {
/* 146 */       Q = new BlockMatrix64F(numRows, numRows, blockLength);
/* 147 */       BlockMatrixOps.setIdentity(Q);
/*     */     } else {
/* 149 */       if (Q.numRows != numRows || Q.numCols != numRows) {
/* 150 */         throw new IllegalArgumentException("Unexpected matrix dimension. Found " + Q.numRows + " " + Q.numCols);
/*     */       }
/* 152 */       BlockMatrixOps.setIdentity(Q);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return Q;
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
/*     */   public void applyQ(BlockMatrix64F B) {
/* 172 */     applyQ(B, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyQ(BlockMatrix64F B, boolean isIdentity) {
/* 183 */     int minDimen = Math.min(this.dataA.numCols, this.dataA.numRows);
/*     */     
/* 185 */     D1Submatrix64F subB = new D1Submatrix64F((D1Matrix64F)B);
/*     */     
/* 187 */     this.W.col0 = this.W.row0 = 0;
/* 188 */     this.W.row1 = this.dataA.numRows;
/* 189 */     this.WTA.row0 = this.WTA.col0 = 0;
/*     */     
/* 191 */     int start = minDimen - minDimen % this.blockLength;
/* 192 */     if (start == minDimen)
/* 193 */       start -= this.blockLength; 
/* 194 */     if (start < 0) {
/* 195 */       start = 0;
/*     */     }
/*     */     int i;
/* 198 */     for (i = start; i >= 0; i -= this.blockLength) {
/*     */       
/* 200 */       this.Y.col0 = i;
/* 201 */       this.Y.col1 = Math.min(this.Y.col0 + this.blockLength, this.dataA.numCols);
/* 202 */       this.Y.row0 = i;
/* 203 */       if (isIdentity)
/* 204 */         subB.col0 = i; 
/* 205 */       subB.row0 = i;
/*     */       
/* 207 */       setW();
/* 208 */       this.WTA.row1 = this.Y.col1 - this.Y.col0;
/* 209 */       subB.col1 -= subB.col0;
/* 210 */       this.WTA.original.reshape(this.WTA.row1, this.WTA.col1, false);
/*     */ 
/*     */       
/* 213 */       if (!this.saveW) {
/* 214 */         BlockHouseHolder.computeW_Column(this.blockLength, this.Y, this.W, this.temp, this.gammas, this.Y.col0);
/*     */       }
/*     */       
/* 217 */       BlockHouseHolder.multTransA_vecCol(this.blockLength, this.Y, subB, this.WTA);
/* 218 */       BlockMultiplication.multPlus(this.blockLength, this.W, this.WTA, subB);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyQTran(BlockMatrix64F B) {
/* 236 */     int minDimen = Math.min(this.dataA.numCols, this.dataA.numRows);
/*     */     
/* 238 */     D1Submatrix64F subB = new D1Submatrix64F((D1Matrix64F)B);
/*     */     
/* 240 */     this.W.col0 = this.W.row0 = 0;
/* 241 */     this.W.row1 = this.dataA.numRows;
/* 242 */     this.WTA.row0 = this.WTA.col0 = 0;
/*     */     
/*     */     int i;
/* 245 */     for (i = 0; i < minDimen; i += this.blockLength) {
/*     */       
/* 247 */       this.Y.col0 = i;
/* 248 */       this.Y.col1 = Math.min(this.Y.col0 + this.blockLength, this.dataA.numCols);
/* 249 */       this.Y.row0 = i;
/*     */       
/* 251 */       subB.row0 = i;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       setW();
/*     */       
/* 258 */       this.WTA.row0 = 0;
/* 259 */       this.WTA.col0 = 0;
/* 260 */       this.WTA.row1 = this.W.col1 - this.W.col0;
/* 261 */       subB.col1 -= subB.col0;
/* 262 */       this.WTA.original.reshape(this.WTA.row1, this.WTA.col1, false);
/*     */ 
/*     */       
/* 265 */       if (!this.saveW) {
/* 266 */         BlockHouseHolder.computeW_Column(this.blockLength, this.Y, this.W, this.temp, this.gammas, this.Y.col0);
/*     */       }
/*     */       
/* 269 */       BlockMultiplication.multTransA(this.blockLength, this.W, subB, this.WTA);
/* 270 */       BlockHouseHolder.multAdd_zeros(this.blockLength, this.Y, this.WTA, subB);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockMatrix64F getR(BlockMatrix64F R, boolean compact) {
/* 279 */     int min = Math.min(this.dataA.numRows, this.dataA.numCols);
/*     */     
/* 281 */     if (R == null) {
/* 282 */       if (compact) {
/* 283 */         R = new BlockMatrix64F(min, this.dataA.numCols, this.blockLength);
/*     */       } else {
/* 285 */         R = new BlockMatrix64F(this.dataA.numRows, this.dataA.numCols, this.blockLength);
/*     */       }
/*     */     
/* 288 */     } else if (compact) {
/* 289 */       if (R.numCols != this.dataA.numCols || R.numRows != min) {
/* 290 */         throw new IllegalArgumentException("Unexpected dimension.");
/*     */       }
/* 292 */     } else if (R.numCols != this.dataA.numCols || R.numRows != this.dataA.numRows) {
/* 293 */       throw new IllegalArgumentException("Unexpected dimension.");
/*     */     } 
/*     */ 
/*     */     
/* 297 */     BlockMatrixOps.zeroTriangle(false, R);
/* 298 */     BlockMatrixOps.copyTriangle(true, this.dataA, R);
/*     */     
/* 300 */     return R;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(BlockMatrix64F orig) {
/* 308 */     setup(orig);
/*     */     
/* 310 */     int m = Math.min(orig.numCols, orig.numRows);
/*     */ 
/*     */     
/* 313 */     for (int j = 0; j < m; j += this.blockLength) {
/* 314 */       this.Y.col0 = j;
/* 315 */       this.Y.col1 = Math.min(orig.numCols, this.Y.col0 + this.blockLength);
/* 316 */       this.Y.row0 = j;
/*     */ 
/*     */ 
/*     */       
/* 320 */       if (!BlockHouseHolder.decomposeQR_block_col(this.blockLength, this.Y, this.gammas)) {
/* 321 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 325 */       updateA(this.A);
/*     */     } 
/*     */     
/* 328 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setup(BlockMatrix64F orig) {
/* 338 */     this.blockLength = orig.blockLength;
/* 339 */     this.dataW.blockLength = this.blockLength;
/* 340 */     this.dataWTA.blockLength = this.blockLength;
/*     */     
/* 342 */     this.dataA = orig;
/* 343 */     this.A.original = (D1Matrix64F)this.dataA;
/*     */     
/* 345 */     int l = Math.min(this.blockLength, orig.numCols);
/* 346 */     this.dataW.reshape(orig.numRows, l, false);
/* 347 */     this.dataWTA.reshape(l, orig.numRows, false);
/* 348 */     this.Y.original = (D1Matrix64F)orig;
/* 349 */     this.W.row1 = orig.numRows;
/* 350 */     if (this.temp.length < this.blockLength)
/* 351 */       this.temp = new double[this.blockLength]; 
/* 352 */     if (this.gammas.length < orig.numCols) {
/* 353 */       this.gammas = new double[orig.numCols];
/*     */     }
/* 355 */     if (this.saveW) {
/* 356 */       this.dataW.reshape(orig.numRows, orig.numCols, false);
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
/*     */   
/*     */   protected void updateA(D1Submatrix64F A) {
/* 370 */     setW();
/*     */     
/* 372 */     A.row0 = this.Y.row0;
/* 373 */     A.row1 = this.Y.row1;
/* 374 */     A.col0 = this.Y.col1;
/* 375 */     A.col1 = this.Y.original.numCols;
/*     */     
/* 377 */     this.WTA.row0 = 0;
/* 378 */     this.WTA.col0 = 0;
/* 379 */     this.WTA.row1 = this.W.col1 - this.W.col0;
/* 380 */     A.col1 -= A.col0;
/* 381 */     this.WTA.original.reshape(this.WTA.row1, this.WTA.col1, false);
/*     */     
/* 383 */     if (A.col1 > A.col0) {
/* 384 */       BlockHouseHolder.computeW_Column(this.blockLength, this.Y, this.W, this.temp, this.gammas, this.Y.col0);
/*     */       
/* 386 */       BlockMultiplication.multTransA(this.blockLength, this.W, A, this.WTA);
/* 387 */       BlockHouseHolder.multAdd_zeros(this.blockLength, this.Y, this.WTA, A);
/* 388 */     } else if (this.saveW) {
/* 389 */       BlockHouseHolder.computeW_Column(this.blockLength, this.Y, this.W, this.temp, this.gammas, this.Y.col0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setW() {
/* 397 */     if (this.saveW) {
/* 398 */       this.W.col0 = this.Y.col0;
/* 399 */       this.W.col1 = this.Y.col1;
/* 400 */       this.W.row0 = this.Y.row0;
/* 401 */       this.W.row1 = this.Y.row1;
/*     */     } else {
/* 403 */       this.Y.col1 -= this.Y.col0;
/* 404 */       this.W.row0 = this.Y.row0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 415 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\qr\QRDecompositionHouseholder_B64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */