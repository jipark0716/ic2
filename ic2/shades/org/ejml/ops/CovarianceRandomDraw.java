/*    */ package ic2.shades.org.ejml.ops;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.chol.CholeskyDecompositionInner_D64;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CovarianceRandomDraw
/*    */ {
/*    */   private DenseMatrix64F A;
/*    */   private Random rand;
/*    */   private DenseMatrix64F r;
/*    */   
/*    */   public CovarianceRandomDraw(Random rand, DenseMatrix64F cov) {
/* 46 */     this.r = new DenseMatrix64F(cov.numRows, 1);
/* 47 */     CholeskyDecompositionInner_D64 cholesky = new CholeskyDecompositionInner_D64(true);
/*    */     
/* 49 */     if (cholesky.inputModified())
/* 50 */       cov = cov.copy(); 
/* 51 */     if (!cholesky.decompose(cov)) {
/* 52 */       throw new RuntimeException("Decomposition failed!");
/*    */     }
/* 54 */     this.A = cholesky.getT();
/* 55 */     this.rand = rand;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void next(DenseMatrix64F x) {
/* 63 */     for (int i = 0; i < this.r.numRows; i++) {
/* 64 */       this.r.set(i, 0, this.rand.nextGaussian());
/*    */     }
/*    */     
/* 67 */     CommonOps.multAdd((RowD1Matrix64F)this.A, (RowD1Matrix64F)this.r, (RowD1Matrix64F)x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double computeLikelihoodP() {
/* 76 */     double ret = 1.0D;
/*    */     
/* 78 */     for (int i = 0; i < this.r.numRows; i++) {
/* 79 */       double a = this.r.get(i, 0);
/*    */       
/* 81 */       ret *= Math.exp(-a * a / 2.0D);
/*    */     } 
/*    */     
/* 84 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\CovarianceRandomDraw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */