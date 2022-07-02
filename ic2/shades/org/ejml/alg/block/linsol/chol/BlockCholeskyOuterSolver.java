/*     */ package ic2.shades.org.ejml.alg.block.linsol.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*     */ import ic2.shades.org.ejml.alg.block.BlockTriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.block.decomposition.chol.CholeskyOuterForm_B64;
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*     */ import ic2.shades.org.ejml.ops.SpecializedOps;
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
/*     */ 
/*     */ public class BlockCholeskyOuterSolver
/*     */   implements LinearSolver<BlockMatrix64F>
/*     */ {
/*  52 */   private CholeskyOuterForm_B64 chol = new CholeskyOuterForm_B64(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockLength;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] temp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(BlockMatrix64F A) {
/*  69 */     if (!this.chol.decompose(A)) {
/*  70 */       return false;
/*     */     }
/*  72 */     this.blockLength = A.blockLength;
/*     */     
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/*  79 */     return SpecializedOps.qualityTriangular(false, (D1Matrix64F)this.chol.getT(null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void solve(BlockMatrix64F B, BlockMatrix64F X) {
/*  88 */     if (B.blockLength != this.blockLength) {
/*  89 */       throw new IllegalArgumentException("Unexpected blocklength in B.");
/*     */     }
/*  91 */     D1Submatrix64F L = new D1Submatrix64F((D1Matrix64F)this.chol.getT(null));
/*     */     
/*  93 */     if (X != null) {
/*  94 */       if (X.blockLength != this.blockLength)
/*  95 */         throw new IllegalArgumentException("Unexpected blocklength in X."); 
/*  96 */       if (X.numRows != L.col1) throw new IllegalArgumentException("Not enough rows in X");
/*     */     
/*     */     } 
/*  99 */     if (B.numRows != L.col1) throw new IllegalArgumentException("Not enough rows in B");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     BlockTriangularSolver.solve(this.blockLength, false, L, new D1Submatrix64F((D1Matrix64F)B), false);
/*     */ 
/*     */     
/* 107 */     BlockTriangularSolver.solve(this.blockLength, false, L, new D1Submatrix64F((D1Matrix64F)B), true);
/*     */     
/* 109 */     if (X != null)
/*     */     {
/* 111 */       BlockMatrixOps.extractAligned(B, X);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void invert(BlockMatrix64F A_inv) {
/* 118 */     BlockMatrix64F T = this.chol.getT(null);
/* 119 */     if (A_inv.numRows != T.numRows || A_inv.numCols != T.numCols) {
/* 120 */       throw new IllegalArgumentException("Unexpected number or rows and/or columns");
/*     */     }
/*     */     
/* 123 */     if (this.temp == null || this.temp.length < this.blockLength * this.blockLength) {
/* 124 */       this.temp = new double[this.blockLength * this.blockLength];
/*     */     }
/*     */     
/* 127 */     BlockMatrixOps.zeroTriangle(true, A_inv);
/*     */     
/* 129 */     D1Submatrix64F L = new D1Submatrix64F((D1Matrix64F)T);
/* 130 */     D1Submatrix64F B = new D1Submatrix64F((D1Matrix64F)A_inv);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     BlockTriangularSolver.invert(this.blockLength, false, L, B, this.temp);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     BlockTriangularSolver.solveL(this.blockLength, L, B, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 145 */     return this.chol.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 150 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\linsol\chol\BlockCholeskyOuterSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */