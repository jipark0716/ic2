/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.bidiagonal;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ import ic2.shades.org.ejml.factory.DecompositionFactory;
/*     */ import ic2.shades.org.ejml.interfaces.decomposition.BidiagonalDecomposition;
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
/*     */ public class BidiagonalDecompositionTall_D64
/*     */   implements BidiagonalDecomposition<DenseMatrix64F>
/*     */ {
/*  61 */   QRPDecomposition<DenseMatrix64F> decompQRP = DecompositionFactory.qrp(500, 100);
/*  62 */   BidiagonalDecomposition<DenseMatrix64F> decompBi = new BidiagonalDecompositionRow_D64();
/*     */   
/*  64 */   DenseMatrix64F B = new DenseMatrix64F(1, 1);
/*     */ 
/*     */   
/*     */   int m;
/*     */   
/*     */   int n;
/*     */   
/*     */   int min;
/*     */ 
/*     */   
/*     */   public void getDiagonal(double[] diag, double[] off) {
/*  75 */     diag[0] = this.B.get(0);
/*  76 */     for (int i = 1; i < this.n; i++) {
/*  77 */       diag[i] = this.B.unsafe_get(i, i);
/*  78 */       off[i - 1] = this.B.unsafe_get(i - 1, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getB(DenseMatrix64F B, boolean compact) {
/*  84 */     B = BidiagonalDecompositionRow_D64.handleB(B, compact, this.m, this.n, this.min);
/*     */     
/*  86 */     B.set(0, 0, this.B.get(0, 0));
/*  87 */     for (int i = 1; i < this.min; i++) {
/*  88 */       B.set(i, i, this.B.get(i, i));
/*  89 */       B.set(i - 1, i, this.B.get(i - 1, i));
/*     */     } 
/*  91 */     if (this.n > this.m) {
/*  92 */       B.set(this.min - 1, this.min, this.B.get(this.min - 1, this.min));
/*     */     }
/*  94 */     return B;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getU(DenseMatrix64F U, boolean transpose, boolean compact) {
/*  99 */     U = BidiagonalDecompositionRow_D64.handleU(U, false, compact, this.m, this.n, this.min);
/*     */     
/* 101 */     if (compact) {
/*     */       
/* 103 */       DenseMatrix64F Q1 = (DenseMatrix64F)this.decompQRP.getQ(null, true);
/* 104 */       DenseMatrix64F U1 = (DenseMatrix64F)this.decompBi.getU(null, false, true);
/* 105 */       CommonOps.mult((RowD1Matrix64F)Q1, (RowD1Matrix64F)U1, (RowD1Matrix64F)U);
/*     */     } else {
/*     */       
/* 108 */       DenseMatrix64F Q = (DenseMatrix64F)this.decompQRP.getQ((Matrix64F)U, false);
/* 109 */       DenseMatrix64F U1 = (DenseMatrix64F)this.decompBi.getU(null, false, true);
/* 110 */       DenseMatrix64F Q1 = CommonOps.extract(Q, 0, Q.numRows, 0, this.min);
/* 111 */       DenseMatrix64F tmp = new DenseMatrix64F(Q1.numRows, U1.numCols);
/* 112 */       CommonOps.mult((RowD1Matrix64F)Q1, (RowD1Matrix64F)U1, (RowD1Matrix64F)tmp);
/* 113 */       CommonOps.insert((ReshapeMatrix64F)tmp, (ReshapeMatrix64F)Q, 0, 0);
/*     */     } 
/*     */     
/* 116 */     if (transpose) {
/* 117 */       CommonOps.transpose(U);
/*     */     }
/* 119 */     return U;
/*     */   }
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getV(DenseMatrix64F V, boolean transpose, boolean compact) {
/* 124 */     return (DenseMatrix64F)this.decompBi.getV((ReshapeMatrix64F)V, transpose, compact);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decompose(DenseMatrix64F orig) {
/* 130 */     if (!this.decompQRP.decompose((Matrix64F)orig)) {
/* 131 */       return false;
/*     */     }
/*     */     
/* 134 */     this.m = orig.numRows;
/* 135 */     this.n = orig.numCols;
/* 136 */     this.min = Math.min(this.m, this.n);
/* 137 */     this.B.reshape(this.min, this.n, false);
/*     */     
/* 139 */     this.decompQRP.getR((Matrix64F)this.B, true);
/*     */ 
/*     */ 
/*     */     
/* 143 */     DenseMatrix64F result = new DenseMatrix64F(this.min, this.n);
/* 144 */     DenseMatrix64F P = this.decompQRP.getPivotMatrix(null);
/* 145 */     CommonOps.multTransB((RowD1Matrix64F)this.B, (RowD1Matrix64F)P, (RowD1Matrix64F)result);
/* 146 */     this.B.set((D1Matrix64F)result);
/*     */     
/* 148 */     return this.decompBi.decompose((Matrix64F)this.B);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inputModified() {
/* 153 */     return this.decompQRP.inputModified();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\bidiagonal\BidiagonalDecompositionTall_D64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */