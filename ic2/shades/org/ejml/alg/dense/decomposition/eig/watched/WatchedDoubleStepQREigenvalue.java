/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig.watched;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.EigenvalueExtractor;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
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
/*     */ public class WatchedDoubleStepQREigenvalue
/*     */   implements EigenvalueExtractor
/*     */ {
/*  40 */   WatchedDoubleStepQREigen implicitQR = new WatchedDoubleStepQREigen(); int[] splits;
/*     */   int numSplits;
/*     */   
/*     */   public void setup(DenseMatrix64F A) {
/*  44 */     this.implicitQR.setup(A);
/*  45 */     this.implicitQR.setQ(null);
/*     */     
/*  47 */     this.splits = new int[A.numRows];
/*  48 */     this.numSplits = 0;
/*     */   }
/*     */   int x1; int x2;
/*     */   
/*     */   public boolean process(DenseMatrix64F origA) {
/*  53 */     setup(origA);
/*     */     
/*  55 */     this.x1 = 0;
/*  56 */     this.x2 = origA.numRows - 1;
/*     */     
/*  58 */     while (this.implicitQR.numEigen < origA.numRows) {
/*  59 */       if (this.implicitQR.steps > this.implicitQR.maxIterations) {
/*  60 */         return false;
/*     */       }
/*  62 */       this.implicitQR.incrementSteps();
/*     */       
/*  64 */       if (this.x2 < this.x1) {
/*  65 */         moveToNextSplit(); continue;
/*  66 */       }  if (this.x2 - this.x1 == 0) {
/*     */         
/*  68 */         this.implicitQR.addEigenAt(this.x1);
/*  69 */         this.x2--; continue;
/*  70 */       }  if (this.x2 - this.x1 == 1) {
/*     */         
/*  72 */         this.implicitQR.addComputedEigen2x2(this.x1, this.x2);
/*  73 */         this.x2 -= 2; continue;
/*  74 */       }  if (this.implicitQR.steps - this.implicitQR.lastExceptional > this.implicitQR.exceptionalThreshold) {
/*     */         
/*  76 */         if (Double.isNaN(this.implicitQR.A.get(this.x2, this.x2))) {
/*  77 */           return false;
/*     */         }
/*     */         
/*  80 */         this.implicitQR.exceptionalShift(this.x1, this.x2); continue;
/*  81 */       }  if (this.implicitQR.isZero(this.x2, this.x2 - 1)) {
/*     */         
/*  83 */         this.implicitQR.addEigenAt(this.x2);
/*  84 */         this.x2--; continue;
/*     */       } 
/*  86 */       performIteration();
/*     */     } 
/*     */ 
/*     */     
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   private void moveToNextSplit() {
/*  94 */     if (this.numSplits <= 0) {
/*  95 */       throw new RuntimeException("bad");
/*     */     }
/*  97 */     this.x2 = this.splits[--this.numSplits];
/*     */     
/*  99 */     if (this.numSplits > 0) {
/* 100 */       this.x1 = this.splits[this.numSplits - 1] + 1;
/*     */     } else {
/* 102 */       this.x1 = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void performIteration() {
/* 107 */     boolean changed = false;
/*     */ 
/*     */     
/* 110 */     for (int i = this.x2; i > this.x1; i--) {
/* 111 */       if (this.implicitQR.isZero(i, i - 1)) {
/* 112 */         this.x1 = i;
/* 113 */         this.splits[this.numSplits++] = i - 1;
/* 114 */         changed = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     if (!changed) {
/* 121 */       this.implicitQR.implicitDoubleStep(this.x1, this.x2);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/* 126 */     return this.implicitQR.getNumberOfEigenvalues();
/*     */   }
/*     */ 
/*     */   
/*     */   public Complex64F[] getEigenvalues() {
/* 131 */     return this.implicitQR.getEigenvalues();
/*     */   }
/*     */   
/*     */   public WatchedDoubleStepQREigen getImplicitQR() {
/* 135 */     return this.implicitQR;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\watched\WatchedDoubleStepQREigenvalue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */