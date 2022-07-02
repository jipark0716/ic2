/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.qr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QrHelperFunctions
/*     */ {
/*     */   public static double findMax(double[] u, int startU, int length) {
/*  48 */     double max = -1.0D;
/*     */     
/*  50 */     int index = startU;
/*  51 */     int stopIndex = startU + length;
/*  52 */     for (; index < stopIndex; index++) {
/*  53 */       double val = u[index];
/*  54 */       val = (val < 0.0D) ? -val : val;
/*  55 */       if (val > max) {
/*  56 */         max = val;
/*     */       }
/*     */     } 
/*  59 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void divideElements(int j, int numRows, double[] u, double u_0) {
/*  67 */     for (int i = j; i < numRows; i++) {
/*  68 */       u[i] = u[i] / u_0;
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
/*     */   public static void divideElements(int j, int numRows, double[] u, int startU, double u_0) {
/*  81 */     for (int i = j; i < numRows; i++) {
/*  82 */       u[i + startU] = u[i + startU] / u_0;
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
/*     */   public static void divideElements_Brow(int j, int numRows, double[] u, double[] b, int startB, double u_0) {
/*  97 */     for (int i = j; i < numRows; i++) {
/*  98 */       b[i + startB] = b[i + startB] / u_0; u[i] = b[i + startB] / u_0;
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
/*     */   public static void divideElements_Bcol(int j, int numRows, int numCols, double[] u, double[] b, int startB, double u_0) {
/* 114 */     int indexB = j * numCols + startB;
/* 115 */     for (int i = j; i < numRows; i++, indexB += numCols) {
/* 116 */       u[i] = u[i] / u_0; b[indexB] = u[i] / u_0;
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
/*     */   public static double computeTauAndDivide(int j, int numRows, double[] u, int startU, double max) {
/* 129 */     double tau = 0.0D;
/*     */ 
/*     */ 
/*     */     
/* 133 */     for (int i = j; i < numRows; i++) {
/* 134 */       double d = u[startU + i] = u[startU + i] / max;
/* 135 */       tau += d * d;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     tau = Math.sqrt(tau);
/*     */     
/* 146 */     if (u[startU + j] < 0.0D) {
/* 147 */       tau = -tau;
/*     */     }
/* 149 */     return tau;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double computeTauAndDivide(int j, int numRows, double[] u, double max) {
/* 175 */     double tau = 0.0D;
/*     */ 
/*     */     
/* 178 */     for (int i = j; i < numRows; i++) {
/* 179 */       double d = u[i] = u[i] / max;
/* 180 */       tau += d * d;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     tau = Math.sqrt(tau);
/*     */     
/* 190 */     if (u[j] < 0.0D) {
/* 191 */       tau = -tau;
/*     */     }
/* 193 */     return tau;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rank1UpdateMultR(DenseMatrix64F A, double[] u, double gamma, int colA0, int w0, int w1, double[] _temp) {
/* 227 */     for (int j = colA0; j < A.numCols; j++) {
/* 228 */       _temp[j] = u[w0] * A.data[w0 * A.numCols + j];
/*     */     }
/*     */     
/* 231 */     for (int k = w0 + 1; k < w1; k++) {
/* 232 */       int indexA = k * A.numCols + colA0;
/* 233 */       double valU = u[k];
/* 234 */       for (int m = colA0; m < A.numCols; m++)
/* 235 */         _temp[m] = _temp[m] + valU * A.data[indexA++]; 
/*     */     } 
/*     */     int i;
/* 238 */     for (i = colA0; i < A.numCols; i++) {
/* 239 */       _temp[i] = _temp[i] * gamma;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 244 */     for (i = w0; i < w1; i++) {
/* 245 */       double valU = u[i];
/*     */       
/* 247 */       int indexA = i * A.numCols + colA0;
/* 248 */       for (int m = colA0; m < A.numCols; m++) {
/* 249 */         A.data[indexA++] = A.data[indexA++] - valU * _temp[m];
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
/*     */   public static void rank1UpdateMultR(DenseMatrix64F A, double[] u, int offsetU, double gamma, int colA0, int w0, int w1, double[] _temp) {
/* 271 */     for (int j = colA0; j < A.numCols; j++) {
/* 272 */       _temp[j] = u[w0 + offsetU] * A.data[w0 * A.numCols + j];
/*     */     }
/*     */     
/* 275 */     for (int k = w0 + 1; k < w1; k++) {
/* 276 */       int indexA = k * A.numCols + colA0;
/* 277 */       double valU = u[k + offsetU];
/* 278 */       for (int m = colA0; m < A.numCols; m++)
/* 279 */         _temp[m] = _temp[m] + valU * A.data[indexA++]; 
/*     */     } 
/*     */     int i;
/* 282 */     for (i = colA0; i < A.numCols; i++) {
/* 283 */       _temp[i] = _temp[i] * gamma;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 288 */     for (i = w0; i < w1; i++) {
/* 289 */       double valU = u[i + offsetU];
/*     */       
/* 291 */       int indexA = i * A.numCols + colA0;
/* 292 */       for (int m = colA0; m < A.numCols; m++) {
/* 293 */         A.data[indexA++] = A.data[indexA++] - valU * _temp[m];
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rank1UpdateMultL(DenseMatrix64F A, double[] u, double gamma, int colA0, int w0, int w1) {
/* 319 */     for (int i = colA0; i < A.numRows; i++) {
/* 320 */       int startIndex = i * A.numCols + w0;
/* 321 */       double sum = 0.0D;
/* 322 */       int rowIndex = startIndex; int j;
/* 323 */       for (j = w0; j < w1; j++) {
/* 324 */         sum += A.data[rowIndex++] * u[j];
/*     */       }
/* 326 */       sum = -gamma * sum;
/*     */       
/* 328 */       rowIndex = startIndex;
/* 329 */       for (j = w0; j < w1; j++)
/* 330 */         A.data[rowIndex++] = A.data[rowIndex++] + sum * u[j]; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\qr\QrHelperFunctions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */