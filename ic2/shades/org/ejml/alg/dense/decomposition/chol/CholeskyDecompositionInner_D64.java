/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.chol;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CholeskyDecompositionInner_D64
/*     */   extends CholeskyDecompositionCommon_D64
/*     */ {
/*     */   public CholeskyDecompositionInner_D64() {
/*  34 */     super(true);
/*     */   }
/*     */   
/*     */   public CholeskyDecompositionInner_D64(boolean lower) {
/*  38 */     super(lower);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean decomposeLower() {
/*  44 */     double div_el_ii = 0.0D;
/*     */     int i;
/*  46 */     for (i = 0; i < this.n; i++) {
/*  47 */       for (int j = i; j < this.n; j++) {
/*  48 */         double sum = this.t[i * this.n + j];
/*     */         
/*  50 */         int iEl = i * this.n;
/*  51 */         int jEl = j * this.n;
/*  52 */         int end = iEl + i;
/*     */         
/*  54 */         for (; iEl < end; iEl++, jEl++)
/*     */         {
/*  56 */           sum -= this.t[iEl] * this.t[jEl];
/*     */         }
/*     */         
/*  59 */         if (i == j) {
/*     */           
/*  61 */           if (sum <= 0.0D) {
/*  62 */             return false;
/*     */           }
/*  64 */           double el_ii = Math.sqrt(sum);
/*  65 */           this.t[i * this.n + i] = el_ii;
/*  66 */           div_el_ii = 1.0D / el_ii;
/*     */         } else {
/*  68 */           this.t[j * this.n + i] = sum * div_el_ii;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  74 */     for (i = 0; i < this.n; i++) {
/*  75 */       for (int j = i + 1; j < this.n; j++) {
/*  76 */         this.t[i * this.n + j] = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean decomposeUpper() {
/*  86 */     double div_el_ii = 0.0D;
/*     */     int i;
/*  88 */     for (i = 0; i < this.n; i++) {
/*  89 */       for (int j = i; j < this.n; j++) {
/*  90 */         double sum = this.t[i * this.n + j];
/*     */         
/*  92 */         for (int k = 0; k < i; k++) {
/*  93 */           sum -= this.t[k * this.n + i] * this.t[k * this.n + j];
/*     */         }
/*     */         
/*  96 */         if (i == j) {
/*     */           
/*  98 */           if (sum <= 0.0D) {
/*  99 */             return false;
/*     */           }
/*     */           
/* 102 */           double el_ii = Math.sqrt(sum);
/* 103 */           this.t[i * this.n + i] = el_ii;
/* 104 */           div_el_ii = 1.0D / el_ii;
/*     */         } else {
/* 106 */           this.t[i * this.n + j] = sum * div_el_ii;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     for (i = 0; i < this.n; i++) {
/* 112 */       for (int j = 0; j < i; j++) {
/* 113 */         this.t[i * this.n + j] = 0.0D;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\chol\CholeskyDecompositionInner_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */