/*     */ package ic2.shades.org.ejml.alg.block.decomposition.hessenberg;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockInnerMultiplication;
/*     */ import ic2.shades.org.ejml.alg.block.BlockMultiplication;
/*     */ import ic2.shades.org.ejml.alg.block.decomposition.qr.QRDecompositionHouseholder_B64;
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;
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
/*     */ public class TridiagonalDecompositionHouseholder_B64
/*     */   implements TridiagonalSimilarDecomposition<BlockMatrix64F>
/*     */ {
/*     */   protected BlockMatrix64F A;
/*  56 */   protected BlockMatrix64F V = new BlockMatrix64F(1, 1);
/*     */   
/*  58 */   protected BlockMatrix64F tmp = new BlockMatrix64F(1, 1);
/*  59 */   protected double[] gammas = new double[1];
/*     */ 
/*     */   
/*  62 */   protected DenseMatrix64F zerosM = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*     */   public BlockMatrix64F getT(BlockMatrix64F T) {
/*  66 */     if (T == null) {
/*  67 */       T = new BlockMatrix64F(this.A.numRows, this.A.numCols, this.A.blockLength);
/*     */     } else {
/*  69 */       if (T.numRows != this.A.numRows || T.numCols != this.A.numCols) {
/*  70 */         throw new IllegalArgumentException("T must have the same dimensions as the input matrix");
/*     */       }
/*  72 */       CommonOps.fill((D1Matrix64F)T, 0.0D);
/*     */     } 
/*     */     
/*  75 */     T.set(0, 0, this.A.data[0]);
/*  76 */     for (int i = 1; i < this.A.numRows; i++) {
/*  77 */       double d = this.A.get(i - 1, i);
/*  78 */       T.set(i, i, this.A.get(i, i));
/*  79 */       T.set(i - 1, i, d);
/*  80 */       T.set(i, i - 1, d);
/*     */     } 
/*     */     
/*  83 */     return T;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockMatrix64F getQ(BlockMatrix64F Q, boolean transposed) {
/*  88 */     Q = QRDecompositionHouseholder_B64.initializeQ(Q, this.A.numRows, this.A.numCols, this.A.blockLength, false);
/*     */     
/*  90 */     int height = Math.min(this.A.blockLength, this.A.numRows);
/*  91 */     this.V.reshape(height, this.A.numCols, false);
/*  92 */     this.tmp.reshape(height, this.A.numCols, false);
/*     */     
/*  94 */     D1Submatrix64F subQ = new D1Submatrix64F((D1Matrix64F)Q);
/*  95 */     D1Submatrix64F subU = new D1Submatrix64F((D1Matrix64F)this.A);
/*  96 */     D1Submatrix64F subW = new D1Submatrix64F((D1Matrix64F)this.V);
/*  97 */     D1Submatrix64F tmp = new D1Submatrix64F((D1Matrix64F)this.tmp);
/*     */ 
/*     */     
/* 100 */     int N = this.A.numRows;
/*     */     
/* 102 */     int start = N - N % this.A.blockLength;
/* 103 */     if (start == N)
/* 104 */       start -= this.A.blockLength; 
/* 105 */     if (start < 0) {
/* 106 */       start = 0;
/*     */     }
/*     */     int i;
/* 109 */     for (i = start; i >= 0; i -= this.A.blockLength) {
/* 110 */       int blockSize = Math.min(this.A.blockLength, N - i);
/*     */       
/* 112 */       subW.col0 = i;
/* 113 */       subW.row1 = blockSize;
/* 114 */       subW.original.reshape(subW.row1, subW.col1, false);
/*     */       
/* 116 */       if (transposed) {
/* 117 */         tmp.row0 = i;
/* 118 */         tmp.row1 = this.A.numCols;
/* 119 */         tmp.col0 = 0;
/* 120 */         tmp.col1 = blockSize;
/*     */       } else {
/* 122 */         tmp.col0 = i;
/* 123 */         tmp.row1 = blockSize;
/*     */       } 
/* 125 */       tmp.original.reshape(tmp.row1, tmp.col1, false);
/*     */       
/* 127 */       subU.col0 = i;
/* 128 */       subU.row0 = i;
/* 129 */       subU.row1 = subU.row0 + blockSize;
/*     */ 
/*     */       
/* 132 */       copyZeros(subU);
/*     */ 
/*     */       
/* 135 */       TridiagonalHelper_B64.computeW_row(this.A.blockLength, subU, subW, this.gammas, i);
/*     */       
/* 137 */       subQ.col0 = i;
/* 138 */       subQ.row0 = i;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       if (transposed) {
/* 149 */         BlockMultiplication.multTransB(this.A.blockLength, subQ, subU, tmp);
/*     */       } else {
/* 151 */         BlockMultiplication.mult(this.A.blockLength, subU, subQ, tmp);
/*     */       } 
/* 153 */       if (transposed) {
/* 154 */         BlockMultiplication.multPlus(this.A.blockLength, tmp, subW, subQ);
/*     */       } else {
/* 156 */         BlockMultiplication.multPlusTransA(this.A.blockLength, subW, tmp, subQ);
/*     */       } 
/* 158 */       replaceZeros(subU);
/*     */     } 
/*     */     
/* 161 */     return Q;
/*     */   }
/*     */   
/*     */   private void copyZeros(D1Submatrix64F subU) {
/* 165 */     int N = Math.min(this.A.blockLength, subU.col1 - subU.col0);
/* 166 */     for (int i = 0; i < N; i++) {
/*     */       
/* 168 */       for (int j = 0; j <= i; j++) {
/* 169 */         this.zerosM.unsafe_set(i, j, subU.get(i, j));
/* 170 */         subU.set(i, j, 0.0D);
/*     */       } 
/*     */       
/* 173 */       if (subU.col0 + i + 1 < subU.original.numCols) {
/* 174 */         this.zerosM.unsafe_set(i, i + 1, subU.get(i, i + 1));
/* 175 */         subU.set(i, i + 1, 1.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void replaceZeros(D1Submatrix64F subU) {
/* 181 */     int N = Math.min(this.A.blockLength, subU.col1 - subU.col0);
/* 182 */     for (int i = 0; i < N; i++) {
/*     */       
/* 184 */       for (int j = 0; j <= i; j++) {
/* 185 */         subU.set(i, j, this.zerosM.get(i, j));
/*     */       }
/*     */       
/* 188 */       if (subU.col0 + i + 1 < subU.original.numCols) {
/* 189 */         subU.set(i, i + 1, this.zerosM.get(i, i + 1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void getDiagonal(double[] diag, double[] off) {
/* 196 */     diag[0] = this.A.data[0];
/* 197 */     for (int i = 1; i < this.A.numRows; i++) {
/* 198 */       diag[i] = this.A.get(i, i);
/* 199 */       off[i - 1] = this.A.get(i - 1, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean decompose(BlockMatrix64F orig) {
/* 205 */     if (orig.numCols != orig.numRows) {
/* 206 */       throw new IllegalArgumentException("Input matrix must be square.");
/*     */     }
/* 208 */     init(orig);
/*     */     
/* 210 */     D1Submatrix64F subA = new D1Submatrix64F((D1Matrix64F)this.A);
/* 211 */     D1Submatrix64F subV = new D1Submatrix64F((D1Matrix64F)this.V);
/* 212 */     D1Submatrix64F subU = new D1Submatrix64F((D1Matrix64F)this.A);
/*     */     
/* 214 */     int N = orig.numCols;
/*     */     int i;
/* 216 */     for (i = 0; i < N; i += this.A.blockLength) {
/*     */       
/* 218 */       int height = Math.min(this.A.blockLength, this.A.numRows - i);
/*     */       
/* 220 */       subU.col0 = i;
/* 221 */       subU.row0 = i;
/*     */       
/* 223 */       subU.row1 = subU.row0 + height;
/*     */       
/* 225 */       subV.col0 = i;
/* 226 */       subV.row1 = height;
/* 227 */       subV.original.reshape(subV.row1, subV.col1, false);
/*     */ 
/*     */       
/* 230 */       TridiagonalHelper_B64.tridiagUpperRow(this.A.blockLength, subA, this.gammas, subV);
/*     */ 
/*     */ 
/*     */       
/* 234 */       if (subU.row1 < orig.numCols) {
/*     */         
/* 236 */         double before = subU.get(this.A.blockLength - 1, this.A.blockLength);
/* 237 */         subU.set(this.A.blockLength - 1, this.A.blockLength, 1.0D);
/*     */ 
/*     */         
/* 240 */         multPlusTransA(this.A.blockLength, subU, subV, subA);
/* 241 */         multPlusTransA(this.A.blockLength, subV, subU, subA);
/*     */         
/* 243 */         subU.set(this.A.blockLength - 1, this.A.blockLength, before);
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return true;
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
/*     */   public static void multPlusTransA(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/* 262 */     int heightA = Math.min(blockLength, A.row1 - A.row0);
/*     */     int i;
/* 264 */     for (i = C.row0 + blockLength; i < C.row1; i += blockLength) {
/* 265 */       int heightC = Math.min(blockLength, C.row1 - i);
/*     */       
/* 267 */       int indexA = A.row0 * A.original.numCols + (i - C.row0 + A.col0) * heightA;
/*     */       int j;
/* 269 */       for (j = i; j < C.col1; j += blockLength) {
/* 270 */         int widthC = Math.min(blockLength, C.col1 - j);
/*     */         
/* 272 */         int indexC = i * C.original.numCols + j * heightC;
/* 273 */         int indexB = B.row0 * B.original.numCols + (j - C.col0 + B.col0) * heightA;
/*     */         
/* 275 */         BlockInnerMultiplication.blockMultPlusTransA(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, heightC, widthC);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(BlockMatrix64F orig) {
/* 282 */     this.A = orig;
/*     */     
/* 284 */     int height = Math.min(this.A.blockLength, this.A.numRows);
/* 285 */     this.V.reshape(height, this.A.numCols, this.A.blockLength, false);
/* 286 */     this.tmp.reshape(height, this.A.numCols, this.A.blockLength, false);
/*     */     
/* 288 */     if (this.gammas.length < this.A.numCols) {
/* 289 */       this.gammas = new double[this.A.numCols];
/*     */     }
/* 291 */     this.zerosM.reshape(this.A.blockLength, this.A.blockLength + 1, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 296 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\hessenberg\TridiagonalDecompositionHouseholder_B64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */