/*     */ package ic2.shades.org.ejml.alg.block.decomposition.chol;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockInnerRankUpdate;
/*     */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*     */ import ic2.shades.org.ejml.alg.block.BlockTriangularSolver;
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.D1Submatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.CholeskyDecomposition;
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
/*     */ public class CholeskyOuterForm_B64
/*     */   implements CholeskyDecomposition<BlockMatrix64F>
/*     */ {
/*     */   private boolean lower = false;
/*     */   private BlockMatrix64F T;
/*  48 */   private D1Submatrix64F subA = new D1Submatrix64F();
/*  49 */   private D1Submatrix64F subB = new D1Submatrix64F();
/*  50 */   private D1Submatrix64F subC = new D1Submatrix64F();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CholeskyOuterForm_B64(boolean lower) {
/*  58 */     this.lower = lower;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(BlockMatrix64F A) {
/*  69 */     if (A.numCols != A.numRows) {
/*  70 */       throw new IllegalArgumentException("A must be square");
/*     */     }
/*  72 */     this.T = A;
/*     */     
/*  74 */     if (this.lower) {
/*  75 */       return decomposeLower();
/*     */     }
/*  77 */     return decomposeUpper();
/*     */   }
/*     */   
/*     */   private boolean decomposeLower() {
/*  81 */     int blockLength = this.T.blockLength;
/*     */     
/*  83 */     this.subA.set((D1Matrix64F)this.T);
/*  84 */     this.subB.set((D1Matrix64F)this.T);
/*  85 */     this.subC.set((D1Matrix64F)this.T);
/*     */     
/*  87 */     for (int i = 0; i < this.T.numCols; i += blockLength) {
/*  88 */       int widthA = Math.min(blockLength, this.T.numCols - i);
/*     */       
/*  90 */       this.subA.col0 = i; this.subA.col1 = i + widthA;
/*  91 */       this.subA.row0 = this.subA.col0; this.subA.row1 = this.subA.col1;
/*     */       
/*  93 */       this.subB.col0 = i; this.subB.col1 = i + widthA;
/*  94 */       this.subB.row0 = i + widthA; this.subB.row1 = this.T.numRows;
/*     */       
/*  96 */       this.subC.col0 = i + widthA; this.subC.col1 = this.T.numRows;
/*  97 */       this.subC.row0 = i + widthA; this.subC.row1 = this.T.numRows;
/*     */ 
/*     */       
/* 100 */       if (!InnerCholesky_B64.lower(this.subA)) {
/* 101 */         return false;
/*     */       }
/*     */       
/* 104 */       if (widthA == blockLength) {
/*     */         
/* 106 */         BlockTriangularSolver.solveBlock(blockLength, false, this.subA, this.subB, false, true);
/*     */ 
/*     */         
/* 109 */         BlockInnerRankUpdate.symmRankNMinus_L(blockLength, this.subC, this.subB);
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     BlockMatrixOps.zeroTriangle(true, this.T);
/*     */     
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean decomposeUpper() {
/* 120 */     int blockLength = this.T.blockLength;
/*     */     
/* 122 */     this.subA.set((D1Matrix64F)this.T);
/* 123 */     this.subB.set((D1Matrix64F)this.T);
/* 124 */     this.subC.set((D1Matrix64F)this.T);
/*     */     
/* 126 */     for (int i = 0; i < this.T.numCols; i += blockLength) {
/* 127 */       int widthA = Math.min(blockLength, this.T.numCols - i);
/*     */       
/* 129 */       this.subA.col0 = i; this.subA.col1 = i + widthA;
/* 130 */       this.subA.row0 = this.subA.col0; this.subA.row1 = this.subA.col1;
/*     */       
/* 132 */       this.subB.col0 = i + widthA; this.subB.col1 = this.T.numCols;
/* 133 */       this.subB.row0 = i; this.subB.row1 = i + widthA;
/*     */       
/* 135 */       this.subC.col0 = i + widthA; this.subC.col1 = this.T.numCols;
/* 136 */       this.subC.row0 = i + widthA; this.subC.row1 = this.T.numCols;
/*     */ 
/*     */       
/* 139 */       if (!InnerCholesky_B64.upper(this.subA)) {
/* 140 */         return false;
/*     */       }
/*     */       
/* 143 */       if (widthA == blockLength) {
/*     */         
/* 145 */         BlockTriangularSolver.solveBlock(blockLength, true, this.subA, this.subB, true, false);
/*     */ 
/*     */         
/* 148 */         BlockInnerRankUpdate.symmRankNMinus_U(blockLength, this.subC, this.subB);
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     BlockMatrixOps.zeroTriangle(false, this.T);
/*     */     
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLower() {
/* 159 */     return this.lower;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockMatrix64F getT(BlockMatrix64F T) {
/* 164 */     if (T == null)
/* 165 */       return this.T; 
/* 166 */     T.set(this.T);
/*     */     
/* 168 */     return T;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 173 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\chol\CholeskyOuterForm_B64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */