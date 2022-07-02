/*     */ package ic2.shades.org.ejml.alg.dense.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.TriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QRColPivDecompositionHouseholderColumn_D64;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.QRPDecomposition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearSolverQrpHouseCol
/*     */   extends BaseLinearSolverQrp
/*     */ {
/*     */   private QRColPivDecompositionHouseholderColumn_D64 decomposition;
/*  40 */   private DenseMatrix64F x_basic = new DenseMatrix64F(1, 1);
/*     */ 
/*     */ 
/*     */   
/*     */   public LinearSolverQrpHouseCol(QRColPivDecompositionHouseholderColumn_D64 decomposition, boolean norm2Solution) {
/*  45 */     super((QRPDecomposition<DenseMatrix64F>)decomposition, norm2Solution);
/*  46 */     this.decomposition = decomposition;
/*     */   }
/*     */ 
/*     */   
/*     */   public void solve(DenseMatrix64F B, DenseMatrix64F X) {
/*  51 */     if (X.numRows != this.numCols)
/*  52 */       throw new IllegalArgumentException("Unexpected dimensions for X"); 
/*  53 */     if (B.numRows != this.numRows || B.numCols != X.numCols) {
/*  54 */       throw new IllegalArgumentException("Unexpected dimensions for B");
/*     */     }
/*  56 */     int BnumCols = B.numCols;
/*     */ 
/*     */     
/*  59 */     int[] pivots = this.decomposition.getPivots();
/*     */     
/*  61 */     double[][] qr = this.decomposition.getQR();
/*  62 */     double[] gammas = this.decomposition.getGammas();
/*     */ 
/*     */     
/*  65 */     for (int colB = 0; colB < BnumCols; colB++) {
/*  66 */       this.x_basic.reshape(this.numRows, 1);
/*  67 */       this.Y.reshape(this.numRows, 1);
/*     */       
/*     */       int i;
/*  70 */       for (i = 0; i < this.numRows; i++) {
/*  71 */         this.x_basic.data[i] = B.get(i, colB);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  76 */       for (i = 0; i < this.rank; i++) {
/*  77 */         double[] u = qr[i];
/*     */         
/*  79 */         double vv = u[i];
/*  80 */         u[i] = 1.0D;
/*  81 */         QrHelperFunctions.rank1UpdateMultR(this.x_basic, u, gammas[i], 0, i, this.numRows, this.Y.data);
/*  82 */         u[i] = vv;
/*     */       } 
/*     */ 
/*     */       
/*  86 */       TriangularSolver.solveU(this.R11.data, this.x_basic.data, this.rank);
/*     */ 
/*     */       
/*  89 */       this.x_basic.reshape(this.numCols, 1, true);
/*  90 */       for (i = this.rank; i < this.numCols; i++) {
/*  91 */         this.x_basic.data[i] = 0.0D;
/*     */       }
/*  93 */       if (this.norm2Solution && this.rank < this.numCols) {
/*  94 */         upgradeSolution(this.x_basic);
/*     */       }
/*     */       
/*  97 */       for (i = 0; i < this.numCols; i++) {
/*  98 */         X.set(pivots[i], colB, this.x_basic.data[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 105 */     return this.decomposition.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 110 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\qr\LinearSolverQrpHouseCol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */