/*    */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*    */ 
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRDecompositionHouseholderColumn_D64;
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrUpdate;
/*    */ import ic2.shades.org.ejml.alg.dense.linsol.AdjustableLinearSolver;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.QRDecomposition;
/*    */ import ic2.shades.org.ejml.ops.CommonOps;
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
/*    */ public class AdjLinearSolverQr
/*    */   extends LinearSolverQr
/*    */   implements AdjustableLinearSolver
/*    */ {
/*    */   private QrUpdate update;
/*    */   private DenseMatrix64F A;
/*    */   
/*    */   public AdjLinearSolverQr() {
/* 41 */     super((QRDecomposition<DenseMatrix64F>)new QRDecompositionHouseholderColumn_D64());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMaxSize(int maxRows, int maxCols) {
/* 47 */     maxRows += 5;
/*    */     
/* 49 */     super.setMaxSize(maxRows, maxCols);
/*    */     
/* 51 */     this.update = new QrUpdate(maxRows, maxCols, true);
/* 52 */     this.A = new DenseMatrix64F(maxRows, maxCols);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getA() {
/* 62 */     if (this.A.data.length < this.numRows * this.numCols) {
/* 63 */       this.A = new DenseMatrix64F(this.numRows, this.numCols);
/*    */     }
/* 65 */     this.A.reshape(this.numRows, this.numCols, false);
/* 66 */     CommonOps.mult((RowD1Matrix64F)this.Q, (RowD1Matrix64F)this.R, (RowD1Matrix64F)this.A);
/*    */     
/* 68 */     return this.A;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean addRowToA(double[] A_row, int rowIndex) {
/* 74 */     if (this.numRows + 1 > this.maxRows) {
/*    */       
/* 76 */       int grow = this.maxRows / 10;
/* 77 */       if (grow < 1) grow = 1; 
/* 78 */       this.maxRows = this.numRows + grow;
/* 79 */       this.Q.reshape(this.maxRows, this.maxRows, true);
/* 80 */       this.R.reshape(this.maxRows, this.maxCols, true);
/*    */     } 
/*    */     
/* 83 */     this.update.addRow(this.Q, this.R, A_row, rowIndex, true);
/* 84 */     this.numRows++;
/*    */     
/* 86 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean removeRowFromA(int index) {
/* 91 */     this.update.deleteRow(this.Q, this.R, index, true);
/* 92 */     this.numRows--;
/* 93 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\AdjLinearSolverQr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */