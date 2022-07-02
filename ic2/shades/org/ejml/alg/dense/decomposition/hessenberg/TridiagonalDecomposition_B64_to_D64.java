/*    */ package ic2.shades.org.ejml.alg.dense.decomposition.hessenberg;
/*    */ 
/*    */ import ic2.shades.org.ejml.EjmlParameters;
/*    */ import ic2.shades.org.ejml.alg.block.decomposition.hessenberg.TridiagonalDecompositionHouseholder_B64;
/*    */ import ic2.shades.org.ejml.alg.dense.decomposition.BaseDecomposition_B64_to_D64;
/*    */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*    */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.DecompositionInterface;
/*    */ import ic2.shades.org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;
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
/*    */ public class TridiagonalDecomposition_B64_to_D64
/*    */   extends BaseDecomposition_B64_to_D64
/*    */   implements TridiagonalSimilarDecomposition<DenseMatrix64F>
/*    */ {
/*    */   public TridiagonalDecomposition_B64_to_D64() {
/* 41 */     this(EjmlParameters.BLOCK_WIDTH);
/*    */   }
/*    */   
/*    */   public TridiagonalDecomposition_B64_to_D64(int blockSize) {
/* 45 */     super((DecompositionInterface)new TridiagonalDecompositionHouseholder_B64(), blockSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getT(DenseMatrix64F T) {
/* 50 */     int N = this.Ablock.numRows;
/*    */     
/* 52 */     if (T == null) {
/* 53 */       T = new DenseMatrix64F(N, N);
/*    */     } else {
/* 55 */       CommonOps.fill((D1Matrix64F)T, 0.0D);
/*    */     } 
/*    */     
/* 58 */     double[] diag = new double[N];
/* 59 */     double[] off = new double[N];
/*    */     
/* 61 */     ((TridiagonalDecompositionHouseholder_B64)this.alg).getDiagonal(diag, off);
/*    */     
/* 63 */     T.unsafe_set(0, 0, diag[0]);
/* 64 */     for (int i = 1; i < N; i++) {
/* 65 */       T.unsafe_set(i, i, diag[i]);
/* 66 */       T.unsafe_set(i, i - 1, off[i - 1]);
/* 67 */       T.unsafe_set(i - 1, i, off[i - 1]);
/*    */     } 
/*    */     
/* 70 */     return T;
/*    */   }
/*    */ 
/*    */   
/*    */   public DenseMatrix64F getQ(DenseMatrix64F Q, boolean transposed) {
/* 75 */     if (Q == null) {
/* 76 */       Q = new DenseMatrix64F(this.Ablock.numRows, this.Ablock.numCols);
/*    */     }
/*    */     
/* 79 */     BlockMatrix64F Qblock = new BlockMatrix64F();
/* 80 */     Qblock.numRows = Q.numRows;
/* 81 */     Qblock.numCols = Q.numCols;
/* 82 */     Qblock.blockLength = this.blockLength;
/* 83 */     Qblock.data = Q.data;
/*    */     
/* 85 */     ((TridiagonalDecompositionHouseholder_B64)this.alg).getQ(Qblock, transposed);
/*    */     
/* 87 */     convertBlockToRow(Q.numRows, Q.numCols, this.Ablock.blockLength, Q.data);
/*    */     
/* 89 */     return Q;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getDiagonal(double[] diag, double[] off) {
/* 94 */     ((TridiagonalDecompositionHouseholder_B64)this.alg).getDiagonal(diag, off);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\hessenberg\TridiagonalDecomposition_B64_to_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */