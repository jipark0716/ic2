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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CholeskyBlockHelper_D64
/*     */ {
/*     */   private DenseMatrix64F L;
/*     */   private double[] el;
/*     */   
/*     */   public CholeskyBlockHelper_D64(int widthMax) {
/*  46 */     this.L = new DenseMatrix64F(widthMax, widthMax);
/*  47 */     this.el = this.L.data;
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
/*     */   public boolean decompose(DenseMatrix64F mat, int indexStart, int n) {
/*  60 */     double[] m = mat.data;
/*     */ 
/*     */     
/*  63 */     double div_el_ii = 0.0D;
/*     */     
/*  65 */     for (int i = 0; i < n; i++) {
/*  66 */       for (int j = i; j < n; j++) {
/*  67 */         double sum = m[indexStart + i * mat.numCols + j];
/*     */         
/*  69 */         int iEl = i * n;
/*  70 */         int jEl = j * n;
/*  71 */         int end = iEl + i;
/*     */         
/*  73 */         for (; iEl < end; iEl++, jEl++)
/*     */         {
/*  75 */           sum -= this.el[iEl] * this.el[jEl];
/*     */         }
/*     */         
/*  78 */         if (i == j) {
/*     */           
/*  80 */           if (sum <= 0.0D) {
/*  81 */             return false;
/*     */           }
/*  83 */           double el_ii = Math.sqrt(sum);
/*  84 */           this.el[i * n + i] = el_ii;
/*  85 */           m[indexStart + i * mat.numCols + i] = el_ii;
/*  86 */           div_el_ii = 1.0D / el_ii;
/*     */         } else {
/*  88 */           double v = sum * div_el_ii;
/*  89 */           this.el[j * n + i] = v;
/*  90 */           m[indexStart + j * mat.numCols + i] = v;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getL() {
/* 105 */     return this.L;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\chol\CholeskyBlockHelper_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */