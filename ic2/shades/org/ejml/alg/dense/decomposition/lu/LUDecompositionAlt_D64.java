/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.lu;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LUDecompositionAlt_D64
/*     */   extends LUDecompositionBase_D64
/*     */ {
/*     */   public boolean decompose(DenseMatrix64F a) {
/*  44 */     decomposeCommonInit(a);
/*     */     
/*  46 */     double[] LUcolj = this.vv;
/*     */     
/*  48 */     for (int j = 0; j < this.n; j++) {
/*     */       int i;
/*     */       
/*  51 */       for (i = 0; i < this.m; i++) {
/*  52 */         LUcolj[i] = this.dataLU[i * this.n + j];
/*     */       }
/*     */ 
/*     */       
/*  56 */       for (i = 0; i < this.m; i++) {
/*  57 */         int rowIndex = i * this.n;
/*     */ 
/*     */         
/*  60 */         int kmax = (i < j) ? i : j;
/*  61 */         double s = 0.0D;
/*  62 */         for (int m = 0; m < kmax; m++) {
/*  63 */           s += this.dataLU[rowIndex + m] * LUcolj[m];
/*     */         }
/*     */         
/*  66 */         LUcolj[i] = LUcolj[i] - s; this.dataLU[rowIndex + j] = LUcolj[i] - s;
/*     */       } 
/*     */ 
/*     */       
/*  70 */       int p = j;
/*  71 */       double max = Math.abs(LUcolj[p]);
/*  72 */       for (int k = j + 1; k < this.m; k++) {
/*  73 */         double v = Math.abs(LUcolj[k]);
/*  74 */         if (v > max) {
/*  75 */           p = k;
/*  76 */           max = v;
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       if (p != j) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  87 */         int rowP = p * this.n;
/*  88 */         int rowJ = j * this.n;
/*  89 */         int endP = rowP + this.n;
/*  90 */         for (; rowP < endP; rowP++, rowJ++) {
/*  91 */           double t = this.dataLU[rowP];
/*  92 */           this.dataLU[rowP] = this.dataLU[rowJ];
/*  93 */           this.dataLU[rowJ] = t;
/*     */         } 
/*  95 */         int m = this.pivot[p]; this.pivot[p] = this.pivot[j]; this.pivot[j] = m;
/*  96 */         this.pivsign = -this.pivsign;
/*     */       } 
/*  98 */       this.indx[j] = p;
/*     */ 
/*     */       
/* 101 */       if (j < this.m) {
/* 102 */         double lujj = this.dataLU[j * this.n + j];
/* 103 */         if (lujj != 0.0D) {
/* 104 */           for (int m = j + 1; m < this.m; m++) {
/* 105 */             this.dataLU[m * this.n + j] = this.dataLU[m * this.n + j] / lujj;
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\lu\LUDecompositionAlt_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */