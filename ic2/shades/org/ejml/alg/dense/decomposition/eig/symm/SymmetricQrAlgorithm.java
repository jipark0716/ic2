/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig.symm;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
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
/*     */ public class SymmetricQrAlgorithm
/*     */ {
/*     */   private SymmetricQREigenHelper helper;
/*     */   private DenseMatrix64F Q;
/*     */   private double[] eigenvalues;
/*  46 */   private int exceptionalThresh = 15;
/*  47 */   private int maxIterations = this.exceptionalThresh * 15;
/*     */ 
/*     */   
/*     */   private boolean fastEigenvalues;
/*     */ 
/*     */   
/*     */   private boolean followingScript;
/*     */ 
/*     */   
/*     */   public SymmetricQrAlgorithm(SymmetricQREigenHelper helper) {
/*  57 */     this.helper = helper;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymmetricQrAlgorithm() {
/*  64 */     this.helper = new SymmetricQREigenHelper();
/*     */   }
/*     */   
/*     */   public void setMaxIterations(int maxIterations) {
/*  68 */     this.maxIterations = maxIterations;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getQ() {
/*  72 */     return this.Q;
/*     */   }
/*     */   
/*     */   public void setQ(DenseMatrix64F q) {
/*  76 */     this.Q = q;
/*     */   }
/*     */   
/*     */   public void setFastEigenvalues(boolean fastEigenvalues) {
/*  80 */     this.fastEigenvalues = fastEigenvalues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEigenvalue(int index) {
/*  90 */     return this.helper.diag[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/*  99 */     return this.helper.N;
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
/*     */   public boolean process(int sideLength, double[] diag, double[] off, double[] eigenvalues) {
/* 115 */     if (diag != null)
/* 116 */       this.helper.init(diag, off, sideLength); 
/* 117 */     if (this.Q == null)
/* 118 */       this.Q = CommonOps.identity(this.helper.N); 
/* 119 */     this.helper.setQ(this.Q);
/*     */     
/* 121 */     this.followingScript = true;
/* 122 */     this.eigenvalues = eigenvalues;
/* 123 */     this.fastEigenvalues = false;
/*     */     
/* 125 */     return _process();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean process(int sideLength, double[] diag, double[] off) {
/* 131 */     if (diag != null) {
/* 132 */       this.helper.init(diag, off, sideLength);
/*     */     }
/* 134 */     this.followingScript = false;
/* 135 */     this.eigenvalues = null;
/*     */     
/* 137 */     return _process();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean _process() {
/* 142 */     while (this.helper.x2 >= 0) {
/*     */       
/* 144 */       if (this.helper.steps > this.maxIterations) {
/* 145 */         return false;
/*     */       }
/*     */       
/* 148 */       if (this.helper.x1 == this.helper.x2) {
/*     */ 
/*     */         
/* 151 */         this.helper.resetSteps();
/* 152 */         if (!this.helper.nextSplit())
/*     */           break; 
/* 154 */       } else if (this.fastEigenvalues && this.helper.x2 - this.helper.x1 == 1) {
/*     */ 
/*     */         
/* 157 */         this.helper.resetSteps();
/* 158 */         this.helper.eigenvalue2by2(this.helper.x1);
/* 159 */         this.helper.setSubmatrix(this.helper.x2, this.helper.x2);
/* 160 */       } else if (this.helper.steps - this.helper.lastExceptional > this.exceptionalThresh) {
/*     */         
/* 162 */         this.helper.exceptionalShift();
/*     */       } else {
/* 164 */         performStep();
/*     */       } 
/* 166 */       this.helper.incrementSteps();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performStep() {
/*     */     double lambda;
/* 179 */     for (int i = this.helper.x2 - 1; i >= this.helper.x1; i--) {
/* 180 */       if (this.helper.isZero(i)) {
/* 181 */         this.helper.splits[this.helper.numSplits++] = i;
/* 182 */         this.helper.x1 = i + 1;
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     if (this.followingScript) {
/* 190 */       if (this.helper.steps > 10) {
/* 191 */         this.followingScript = false;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 196 */       lambda = this.eigenvalues[this.helper.x2];
/*     */     }
/*     */     else {
/*     */       
/* 200 */       lambda = this.helper.computeShift();
/*     */     } 
/*     */ 
/*     */     
/* 204 */     this.helper.performImplicitSingleStep(lambda, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\symm\SymmetricQrAlgorithm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */