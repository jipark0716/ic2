/*    */ package ic2.shades.org.ejml.alg.dense.decomposition.svd;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.Matrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.SingularValueDecomposition;
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
/*    */ public class SafeSvd
/*    */   implements SingularValueDecomposition<DenseMatrix64F>
/*    */ {
/*    */   SingularValueDecomposition<DenseMatrix64F> alg;
/* 35 */   DenseMatrix64F work = new DenseMatrix64F(1, 1);
/*    */   
/*    */   public SafeSvd(SingularValueDecomposition<DenseMatrix64F> alg) {
/* 38 */     this.alg = alg;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] getSingularValues() {
/* 43 */     return this.alg.getSingularValues();
/*    */   }
/*    */ 
/*    */   
/*    */   public int numberOfSingularValues() {
/* 48 */     return this.alg.numberOfSingularValues();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCompact() {
/* 53 */     return this.alg.isCompact();
/*    */   }
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getU(DenseMatrix64F U, boolean transposed) {
/* 58 */     return (DenseMatrix64F)this.alg.getU((Matrix64F)U, transposed);
/*    */   }
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getV(DenseMatrix64F V, boolean transposed) {
/* 63 */     return (DenseMatrix64F)this.alg.getV((Matrix64F)V, transposed);
/*    */   }
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getW(DenseMatrix64F W) {
/* 68 */     return (DenseMatrix64F)this.alg.getW((Matrix64F)W);
/*    */   }
/*    */ 
/*    */   
/*    */   public int numRows() {
/* 73 */     return this.alg.numRows();
/*    */   }
/*    */ 
/*    */   
/*    */   public int numCols() {
/* 78 */     return this.alg.numCols();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean decompose(DenseMatrix64F orig) {
/* 83 */     if (this.alg.inputModified()) {
/* 84 */       this.work.reshape(orig.numRows, orig.numCols);
/* 85 */       this.work.set((D1Matrix64F)orig);
/* 86 */       return this.alg.decompose((Matrix64F)this.work);
/*    */     } 
/* 88 */     return this.alg.decompose((Matrix64F)orig);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean inputModified() {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\svd\SafeSvd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */