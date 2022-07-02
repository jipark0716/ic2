/*     */ package ic2.shades.org.ejml.alg.block.linsol.qr;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*     */ import ic2.shades.org.ejml.alg.block.BlockTriangularSolver;
/*     */ import ic2.shades.org.ejml.alg.block.decomposition.qr.QRDecompositionHouseholder_B64;
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
/*     */ public class BlockQrHouseHolderSolver
/*     */   implements LinearSolver<BlockMatrix64F>
/*     */ {
/*  51 */   protected QRDecompositionHouseholder_B64 decomp = new QRDecompositionHouseholder_B64();
/*     */ 
/*     */   
/*     */   protected BlockMatrix64F QR;
/*     */ 
/*     */   
/*     */   public BlockQrHouseHolderSolver() {
/*  58 */     this.decomp.setSaveW(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(BlockMatrix64F A) {
/*  69 */     if (A.numRows < A.numCols) {
/*  70 */       throw new IllegalArgumentException("Number of rows must be more than or equal to the number of columns.  Can't solve an underdetermined system.");
/*     */     }
/*     */     
/*  73 */     if (!this.decomp.decompose(A)) {
/*  74 */       return false;
/*     */     }
/*  76 */     this.QR = this.decomp.getQR();
/*     */     
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double quality() {
/*  88 */     return SpecializedOps.qualityTriangular(true, (D1Matrix64F)this.decomp.getQR());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void solve(BlockMatrix64F B, BlockMatrix64F X) {
/*  94 */     if (B.numCols != X.numCols)
/*  95 */       throw new IllegalArgumentException("Columns of B and X do not match"); 
/*  96 */     if (this.QR.numCols != X.numRows)
/*  97 */       throw new IllegalArgumentException("Rows in X do not match the columns in A"); 
/*  98 */     if (this.QR.numRows != B.numRows)
/*  99 */       throw new IllegalArgumentException("Rows in B do not match the rows in A."); 
/* 100 */     if (B.blockLength != this.QR.blockLength || X.blockLength != this.QR.blockLength) {
/* 101 */       throw new IllegalArgumentException("All matrices must have the same block length.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.decomp.applyQTran(B);
/*     */ 
/*     */ 
/*     */     
/* 112 */     BlockMatrixOps.extractAligned(B, X);
/*     */ 
/*     */     
/* 115 */     int M = Math.min(this.QR.numRows, this.QR.numCols);
/*     */     
/* 117 */     BlockTriangularSolver.solve(this.QR.blockLength, true, new D1Submatrix64F((D1Matrix64F)this.QR, 0, M, 0, M), new D1Submatrix64F((D1Matrix64F)X), false);
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
/*     */   public void invert(BlockMatrix64F A_inv) {
/* 129 */     int M = Math.min(this.QR.numRows, this.QR.numCols);
/* 130 */     if (A_inv.numRows != M || A_inv.numCols != M) {
/* 131 */       throw new IllegalArgumentException("A_inv must be square an have dimension " + M);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     BlockMatrixOps.setIdentity(A_inv);
/* 140 */     this.decomp.applyQTran(A_inv);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     BlockTriangularSolver.solve(this.QR.blockLength, true, new D1Submatrix64F((D1Matrix64F)this.QR, 0, M, 0, M), new D1Submatrix64F((D1Matrix64F)A_inv), false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/* 151 */     return this.decomp.inputModified();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 156 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\linsol\qr\BlockQrHouseHolderSolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */