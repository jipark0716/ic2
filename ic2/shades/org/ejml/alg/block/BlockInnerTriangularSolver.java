/*     */ package ic2.shades.org.ejml.alg.block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockInnerTriangularSolver
/*     */ {
/*     */   public static void invertLower(double[] L, double[] L_inv, int m, int offsetL, int offsetL_inv) {
/*  65 */     for (int i = 0; i < m; i++) {
/*  66 */       double L_ii = L[offsetL + i * m + i];
/*  67 */       for (int j = 0; j < i; j++) {
/*  68 */         double val = 0.0D;
/*  69 */         for (int k = j; k < i; k++) {
/*  70 */           val += L[offsetL + i * m + k] * L_inv[offsetL_inv + k * m + j];
/*     */         }
/*  72 */         L_inv[offsetL_inv + i * m + j] = -val / L_ii;
/*     */       } 
/*  74 */       L_inv[offsetL_inv + i * m + i] = 1.0D / L_ii;
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
/*     */   public static void invertLower(double[] L, int m, int offsetL) {
/*  92 */     for (int i = 0; i < m; i++) {
/*  93 */       double L_ii = L[offsetL + i * m + i];
/*  94 */       for (int j = 0; j < i; j++) {
/*  95 */         double val = 0.0D;
/*  96 */         for (int k = j; k < i; k++) {
/*  97 */           val += L[offsetL + i * m + k] * L[offsetL + k * m + j];
/*     */         }
/*  99 */         L[offsetL + i * m + j] = -val / L_ii;
/*     */       } 
/* 101 */       L[offsetL + i * m + i] = 1.0D / L_ii;
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
/*     */   public static void solveL(double[] L, double[] b, int m, int n, int strideL, int offsetL, int offsetB) {
/* 126 */     for (int j = 0; j < n; j++) {
/* 127 */       for (int i = 0; i < m; i++) {
/* 128 */         double sum = b[offsetB + i * n + j];
/* 129 */         for (int k = 0; k < i; k++) {
/* 130 */           sum -= L[offsetL + i * strideL + k] * b[offsetB + k * n + j];
/*     */         }
/* 132 */         b[offsetB + i * n + j] = sum / L[offsetL + i * strideL + i];
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
/*     */   public static void solveTransL(double[] L, double[] b, int m, int n, int strideL, int offsetL, int offsetB) {
/* 158 */     for (int j = 0; j < n; j++) {
/* 159 */       for (int i = m - 1; i >= 0; i--) {
/* 160 */         double sum = b[offsetB + i * n + j];
/* 161 */         for (int k = i + 1; k < m; k++) {
/* 162 */           sum -= L[offsetL + k * strideL + i] * b[offsetB + k * n + j];
/*     */         }
/* 164 */         b[offsetB + i * n + j] = sum / L[offsetL + i * strideL + i];
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void solveLTransB(double[] L, double[] b, int m, int n, int strideL, int offsetL, int offsetB) {
/* 198 */     for (int j = 0; j < n; j++) {
/* 199 */       for (int i = 0; i < m; i++) {
/* 200 */         double sum = b[offsetB + j * m + i];
/* 201 */         int l = offsetL + i * strideL;
/* 202 */         int bb = offsetB + j * m;
/* 203 */         int endL = l + i;
/* 204 */         while (l != endL)
/*     */         {
/* 206 */           sum -= L[l++] * b[bb++];
/*     */         }
/* 208 */         b[offsetB + j * m + i] = sum / L[offsetL + i * strideL + i];
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
/*     */   public static void solveU(double[] U, double[] b, int m, int n, int strideU, int offsetU, int offsetB) {
/* 233 */     for (int j = 0; j < n; j++) {
/* 234 */       for (int i = m - 1; i >= 0; i--) {
/* 235 */         double sum = b[offsetB + i * n + j];
/* 236 */         for (int k = i + 1; k < m; k++) {
/* 237 */           sum -= U[offsetU + i * strideU + k] * b[offsetB + k * n + j];
/*     */         }
/* 239 */         b[offsetB + i * n + j] = sum / U[offsetU + i * strideU + i];
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
/*     */   public static void solveTransU(double[] U, double[] b, int m, int n, int strideU, int offsetU, int offsetB) {
/* 264 */     for (int j = 0; j < n; j++) {
/* 265 */       for (int i = 0; i < m; i++) {
/* 266 */         double sum = b[offsetB + i * n + j];
/* 267 */         for (int k = 0; k < i; k++) {
/* 268 */           sum -= U[offsetU + k * strideU + i] * b[offsetB + k * n + j];
/*     */         }
/* 270 */         b[offsetB + i * n + j] = sum / U[offsetU + i * strideU + i];
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockInnerTriangularSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */