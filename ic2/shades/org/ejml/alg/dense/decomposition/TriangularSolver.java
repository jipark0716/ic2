/*     */ package ic2.shades.org.ejml.alg.dense.decomposition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TriangularSolver
/*     */ {
/*     */   public static void invertLower(double[] L, int m) {
/*  49 */     for (int i = 0; i < m; i++) {
/*  50 */       double L_ii = L[i * m + i];
/*  51 */       for (int j = 0; j < i; j++) {
/*  52 */         double val = 0.0D;
/*  53 */         for (int k = j; k < i; k++) {
/*  54 */           val += L[i * m + k] * L[k * m + j];
/*     */         }
/*  56 */         L[i * m + j] = -val / L_ii;
/*     */       } 
/*  58 */       L[i * m + i] = 1.0D / L_ii;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void invertLower(double[] L, double[] L_inv, int m) {
/*  63 */     for (int i = 0; i < m; i++) {
/*  64 */       double L_ii = L[i * m + i];
/*  65 */       for (int j = 0; j < i; j++) {
/*  66 */         double val = 0.0D;
/*  67 */         for (int k = j; k < i; k++) {
/*  68 */           val -= L[i * m + k] * L_inv[k * m + j];
/*     */         }
/*  70 */         L_inv[i * m + j] = val / L_ii;
/*     */       } 
/*  72 */       L_inv[i * m + i] = 1.0D / L_ii;
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
/*     */   
/*     */   public static void solveL(double[] L, double[] b, int n) {
/*  98 */     for (int i = 0; i < n; i++) {
/*  99 */       double sum = b[i];
/* 100 */       int indexL = i * n;
/* 101 */       for (int k = 0; k < i; k++) {
/* 102 */         sum -= L[indexL++] * b[k];
/*     */       }
/* 104 */       b[i] = sum / L[indexL];
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
/*     */   public static void solveL(double[] L, double[] b, int m, int n) {
/* 120 */     for (int j = 0; j < n; j++) {
/* 121 */       for (int i = 0; i < m; i++) {
/* 122 */         double sum = b[i * n + j];
/* 123 */         for (int k = 0; k < i; k++) {
/* 124 */           sum -= L[i * m + k] * b[k * n + j];
/*     */         }
/* 126 */         b[i * n + j] = sum / L[i * m + i];
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
/*     */   public static void solveTranL(double[] L, double[] b, int n) {
/* 150 */     for (int i = n - 1; i >= 0; i--) {
/* 151 */       double sum = b[i];
/* 152 */       for (int k = i + 1; k < n; k++) {
/* 153 */         sum -= L[k * n + i] * b[k];
/*     */       }
/* 155 */       b[i] = sum / L[i * n + i];
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
/*     */   
/*     */   public static void solveU(double[] U, double[] b, int n) {
/* 181 */     for (int i = n - 1; i >= 0; i--) {
/* 182 */       double sum = b[i];
/* 183 */       int indexU = i * n + i + 1;
/* 184 */       for (int j = i + 1; j < n; j++) {
/* 185 */         sum -= U[indexU++] * b[j];
/*     */       }
/* 187 */       b[i] = sum / U[i * n + i];
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
/*     */   public static void solveU(double[] U, double[] b, int sideLength, int minRow, int maxRow) {
/* 200 */     for (int i = maxRow - 1; i >= minRow; i--) {
/* 201 */       double sum = b[i];
/* 202 */       int indexU = i * sideLength + i + 1;
/* 203 */       for (int j = i + 1; j < maxRow; j++) {
/* 204 */         sum -= U[indexU++] * b[j];
/*     */       }
/* 206 */       b[i] = sum / U[i * sideLength + i];
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
/*     */   
/*     */   public static void solveU(double[] U, int startU, int strideU, int widthU, double[] b, int startB, int strideB, int widthB) {
/* 232 */     for (int colB = 0; colB < widthB; colB++) {
/* 233 */       for (int i = widthU - 1; i >= 0; i--) {
/* 234 */         double sum = b[startB + i * strideB + colB];
/* 235 */         for (int j = i + 1; j < widthU; j++) {
/* 236 */           sum -= U[startU + i * strideU + j] * b[startB + j * strideB + colB];
/*     */         }
/* 238 */         b[startB + i * strideB + colB] = sum / U[startU + i * strideU + i];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\TriangularSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */