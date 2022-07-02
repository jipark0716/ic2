/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CholeskyDecompositionBlock_D64
/*     */   extends CholeskyDecompositionCommon_D64
/*     */ {
/*     */   private int blockWidth;
/*     */   private DenseMatrix64F B;
/*     */   private CholeskyBlockHelper_D64 chol;
/*     */   
/*     */   public CholeskyDecompositionBlock_D64(int blockWidth) {
/*  44 */     super(true);
/*     */     
/*  46 */     this.blockWidth = blockWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpectedMaxSize(int numRows, int numCols) {
/*  55 */     super.setExpectedMaxSize(numRows, numCols);
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (numRows < this.blockWidth) {
/*  60 */       this.B = new DenseMatrix64F(0, 0);
/*     */     } else {
/*  62 */       this.B = new DenseMatrix64F(this.blockWidth, this.maxWidth);
/*     */     } 
/*  64 */     this.chol = new CholeskyBlockHelper_D64(this.blockWidth);
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
/*     */   protected boolean decomposeLower() {
/*  82 */     if (this.n < this.blockWidth) {
/*  83 */       this.B.reshape(0, 0, false);
/*     */     } else {
/*  85 */       this.B.reshape(this.blockWidth, this.n - this.blockWidth, false);
/*     */     } 
/*  87 */     int numBlocks = this.n / this.blockWidth;
/*  88 */     int remainder = this.n % this.blockWidth;
/*     */     
/*  90 */     if (remainder > 0) {
/*  91 */       numBlocks++;
/*     */     }
/*     */     
/*  94 */     this.B.numCols = this.n;
/*     */     int i;
/*  96 */     for (i = 0; i < numBlocks; i++) {
/*  97 */       this.B.numCols -= this.blockWidth;
/*     */       
/*  99 */       if (this.B.numCols > 0) {
/*     */         
/* 101 */         if (!this.chol.decompose(this.T, i * this.blockWidth * this.T.numCols + i * this.blockWidth, this.blockWidth)) return false;
/*     */         
/* 103 */         int indexSrc = i * this.blockWidth * this.T.numCols + (i + 1) * this.blockWidth;
/* 104 */         int indexDst = (i + 1) * this.blockWidth * this.T.numCols + i * this.blockWidth;
/*     */ 
/*     */         
/* 107 */         solveL_special((this.chol.getL()).data, this.T, indexSrc, indexDst, this.B);
/*     */         
/* 109 */         int indexL = (i + 1) * this.blockWidth * this.n + (i + 1) * this.blockWidth;
/*     */ 
/*     */         
/* 112 */         symmRankTranA_sub(this.B, this.T, indexL);
/*     */       } else {
/* 114 */         int width = (remainder > 0) ? remainder : this.blockWidth;
/* 115 */         if (!this.chol.decompose(this.T, i * this.blockWidth * this.T.numCols + i * this.blockWidth, width)) return false;
/*     */       
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 121 */     for (i = 0; i < this.n; i++) {
/* 122 */       for (int j = i + 1; j < this.n; j++) {
/* 123 */         this.t[i * this.n + j] = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean decomposeUpper() {
/* 132 */     throw new RuntimeException("Not implemented.  Do a lower decomposition and transpose it...");
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
/*     */   public static void solveL_special(double[] L, DenseMatrix64F b_src, int indexSrc, int indexDst, DenseMatrix64F B) {
/* 152 */     double[] dataSrc = b_src.data;
/*     */     
/* 154 */     double[] b = B.data;
/* 155 */     int m = B.numRows;
/* 156 */     int n = B.numCols;
/* 157 */     int widthL = m;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     for (int j = 0; j < n; j++) {
/* 172 */       int indexb = j;
/* 173 */       int rowL = 0;
/*     */ 
/*     */       
/* 176 */       for (int i = 0; i < widthL; i++, indexb += n, rowL += widthL) {
/* 177 */         double sum = dataSrc[indexSrc + i * b_src.numCols + j];
/*     */         
/* 179 */         int indexL = rowL;
/* 180 */         int endL = indexL + i;
/* 181 */         int indexB = j;
/*     */         
/* 183 */         for (; indexL != endL; indexB += n) {
/* 184 */           sum -= L[indexL++] * b[indexB];
/*     */         }
/* 186 */         double val = sum / L[i * widthL + i];
/* 187 */         dataSrc[indexDst + j * b_src.numCols + i] = val;
/* 188 */         b[indexb] = val;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void symmRankTranA_sub(DenseMatrix64F a, DenseMatrix64F c, int startIndexC) {
/* 211 */     double[] dataA = a.data;
/* 212 */     double[] dataC = c.data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     int strideC = c.numCols + 1;
/* 225 */     for (int i = 0; i < a.numCols; i++) {
/* 226 */       int indexA = i;
/* 227 */       int endR = a.numCols;
/*     */       
/* 229 */       for (int k = 0; k < a.numRows; k++, indexA += a.numCols, endR += a.numCols) {
/* 230 */         int indexC = startIndexC;
/* 231 */         double valA = dataA[indexA];
/* 232 */         int indexR = indexA;
/*     */         
/* 234 */         while (indexR < endR) {
/* 235 */           dataC[indexC++] = dataC[indexC++] - valA * dataA[indexR++];
/*     */         }
/*     */       } 
/* 238 */       startIndexC += strideC;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\chol\CholeskyDecompositionBlock_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */