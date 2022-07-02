/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRPDecomposition;
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
/*     */ public class SolvePseudoInverseQrp
/*     */   extends BaseLinearSolverQrp
/*     */ {
/*  37 */   private DenseMatrix64F Q = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*  40 */   private DenseMatrix64F x_basic = new DenseMatrix64F(1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SolvePseudoInverseQrp(QRPDecomposition<DenseMatrix64F> decomposition, boolean norm2Solution) {
/*  50 */     super(decomposition, norm2Solution);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setA(DenseMatrix64F A) {
/*  55 */     if (!super.setA(A)) {
/*  56 */       return false;
/*     */     }
/*  58 */     this.Q.reshape(A.numRows, A.numRows);
/*     */     
/*  60 */     this.decomposition.getQ((Matrix64F)this.Q, false);
/*     */     
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/*  67 */     if (X.numRows != this.numCols)
/*  68 */       throw new IllegalArgumentException("Unexpected dimensions for X"); 
/*  69 */     if (B.numRows != this.numRows || B.numCols != X.numCols) {
/*  70 */       throw new IllegalArgumentException("Unexpected dimensions for B");
/*     */     }
/*  72 */     int BnumCols = B.numCols;
/*     */ 
/*     */     
/*  75 */     int[] pivots = this.decomposition.getPivots();
/*     */ 
/*     */     
/*  78 */     for (int colB = 0; colB < BnumCols; colB++) {
/*  79 */       this.x_basic.reshape(this.numRows, 1);
/*  80 */       this.Y.reshape(this.numRows, 1);
/*     */       
/*     */       int i;
/*  83 */       for (i = 0; i < this.numRows; i++) {
/*  84 */         this.Y.data[i] = B.get(i, colB);
/*     */       }
/*     */ 
/*     */       
/*  88 */       CommonOps.multTransA((RowD1Matrix64F)this.Q, (RowD1Matrix64F)this.Y, (RowD1Matrix64F)this.x_basic);
/*     */ 
/*     */       
/*  91 */       TriangularSolver.solveU(this.R11.data, this.x_basic.data, this.rank);
/*     */ 
/*     */       
/*  94 */       this.x_basic.reshape(this.numCols, 1, true);
/*  95 */       for (i = this.rank; i < this.numCols; i++) {
/*  96 */         this.x_basic.data[i] = 0.0D;
/*     */       }
/*  98 */       if (this.norm2Solution && this.rank < this.numCols) {
/*  99 */         upgradeSolution(this.x_basic);
/*     */       }
/*     */       
/* 102 */       for (i = 0; i < this.numCols; i++) {
/* 103 */         X.set(pivots[i], colB, this.x_basic.data[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 110 */     return this.decomposition.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\SolvePseudoInverseQrp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */