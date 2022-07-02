/*      */ package ic2.shades.org.ejml.ops;
/*      */ 
/*      */ import ic2.shades.org.ejml.EjmlParameters;
/*      */ import ic2.shades.org.ejml.UtilEjml;
/*      */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionAlt_D64;
/*      */ import ic2.shades.org.ejml.alg.dense.decomposition.lu.LUDecompositionBase_D64;
/*      */ import ic2.shades.org.ejml.alg.dense.linsol.LinearSolverSafe;
/*      */ import ic2.shades.org.ejml.alg.dense.linsol.lu.LinearSolverLu;
/*      */ import ic2.shades.org.ejml.alg.dense.misc.ImplCommonOps_DenseMatrix64F;
/*      */ import ic2.shades.org.ejml.alg.dense.misc.ImplCommonOps_Matrix64F;
/*      */ import ic2.shades.org.ejml.alg.dense.misc.RrefGaussJordanRowPivot;
/*      */ import ic2.shades.org.ejml.alg.dense.misc.TransposeAlgs;
/*      */ import ic2.shades.org.ejml.alg.dense.misc.UnrolledDeterminantFromMinor;
/*      */ import ic2.shades.org.ejml.alg.dense.misc.UnrolledInverseFromMinor;
/*      */ import ic2.shades.org.ejml.alg.dense.mult.MatrixMatrixMult;
/*      */ import ic2.shades.org.ejml.alg.dense.mult.MatrixMultProduct;
/*      */ import ic2.shades.org.ejml.alg.dense.mult.MatrixVectorMult;
/*      */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*      */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*      */ import ic2.shades.org.ejml.data.Matrix64F;
/*      */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*      */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*      */ import ic2.shades.org.ejml.factory.LinearSolverFactory;
/*      */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CommonOps
/*      */ {
/*      */   public static void mult(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*   73 */     if (b.numCols == 1) {
/*   74 */       MatrixVectorMult.mult(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*   75 */     } else if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*   76 */       MatrixMatrixMult.mult_reorder(a, b, c);
/*      */     } else {
/*   78 */       MatrixMatrixMult.mult_small(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mult(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*   98 */     if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*   99 */       MatrixMatrixMult.mult_reorder(alpha, a, b, c);
/*      */     } else {
/*  101 */       MatrixMatrixMult.mult_small(alpha, a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransA(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  119 */     if (b.numCols == 1) {
/*      */ 
/*      */       
/*  122 */       if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*  123 */         MatrixVectorMult.multTransA_reorder(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } else {
/*  125 */         MatrixVectorMult.multTransA_small(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } 
/*  127 */     } else if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*      */       
/*  129 */       MatrixMatrixMult.multTransA_reorder(a, b, c);
/*      */     } else {
/*  131 */       MatrixMatrixMult.multTransA_small(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransA(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  151 */     if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*      */       
/*  153 */       MatrixMatrixMult.multTransA_reorder(alpha, a, b, c);
/*      */     } else {
/*  155 */       MatrixMatrixMult.multTransA_small(alpha, a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  173 */     if (b.numRows == 1) {
/*  174 */       MatrixVectorMult.mult(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */     } else {
/*  176 */       MatrixMatrixMult.multTransB(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  196 */     MatrixMatrixMult.multTransB(alpha, a, b, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransAB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  213 */     if (b.numRows == 1) {
/*      */       
/*  215 */       if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*  216 */         MatrixVectorMult.multTransA_reorder(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } else {
/*  218 */         MatrixVectorMult.multTransA_small(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } 
/*  220 */     } else if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
/*  221 */       MatrixMatrixMult.multTransAB_aux(a, b, c, null);
/*      */     } else {
/*  223 */       MatrixMatrixMult.multTransAB(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransAB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  243 */     if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
/*  244 */       MatrixMatrixMult.multTransAB_aux(alpha, a, b, c, null);
/*      */     } else {
/*  246 */       MatrixMatrixMult.multTransAB(alpha, a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multInner(RowD1Matrix64F a, RowD1Matrix64F c) {
/*  268 */     if (a.numCols != c.numCols || a.numCols != c.numRows) {
/*  269 */       throw new IllegalArgumentException("Rows and columns of 'c' must be the same as the columns in 'a'");
/*      */     }
/*  271 */     if (a.numCols >= EjmlParameters.MULT_INNER_SWITCH) {
/*  272 */       MatrixMultProduct.inner_small(a, c);
/*      */     } else {
/*  274 */       MatrixMultProduct.inner_reorder(a, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multOuter(RowD1Matrix64F a, RowD1Matrix64F c) {
/*  295 */     if (a.numRows != c.numCols || a.numRows != c.numRows) {
/*  296 */       throw new IllegalArgumentException("Rows and columns of 'c' must be the same as the rows in 'a'");
/*      */     }
/*  298 */     MatrixMultProduct.outer(a, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  315 */     if (b.numCols == 1) {
/*  316 */       MatrixVectorMult.multAdd(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */     }
/*  318 */     else if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*  319 */       MatrixMatrixMult.multAdd_reorder(a, b, c);
/*      */     } else {
/*  321 */       MatrixMatrixMult.multAdd_small(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  342 */     if (b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*  343 */       MatrixMatrixMult.multAdd_reorder(alpha, a, b, c);
/*      */     } else {
/*  345 */       MatrixMatrixMult.multAdd_small(alpha, a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransA(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  363 */     if (b.numCols == 1) {
/*  364 */       if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*  365 */         MatrixVectorMult.multAddTransA_reorder(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } else {
/*  367 */         MatrixVectorMult.multAddTransA_small(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       }
/*      */     
/*  370 */     } else if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*      */       
/*  372 */       MatrixMatrixMult.multAddTransA_reorder(a, b, c);
/*      */     } else {
/*  374 */       MatrixMatrixMult.multAddTransA_small(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransA(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  395 */     if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH || b.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*      */       
/*  397 */       MatrixMatrixMult.multAddTransA_reorder(alpha, a, b, c);
/*      */     } else {
/*  399 */       MatrixMatrixMult.multAddTransA_small(alpha, a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  417 */     MatrixMatrixMult.multAddTransB(a, b, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  436 */     MatrixMatrixMult.multAddTransB(alpha, a, b, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransAB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  453 */     if (b.numRows == 1) {
/*      */       
/*  455 */       if (a.numCols >= EjmlParameters.MULT_COLUMN_SWITCH) {
/*  456 */         MatrixVectorMult.multAddTransA_reorder(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } else {
/*  458 */         MatrixVectorMult.multAddTransA_small(a, (D1Matrix64F)b, (D1Matrix64F)c);
/*      */       } 
/*  460 */     } else if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
/*  461 */       MatrixMatrixMult.multAddTransAB_aux(a, b, c, null);
/*      */     } else {
/*  463 */       MatrixMatrixMult.multAddTransAB(a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransAB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  483 */     if (a.numCols >= EjmlParameters.MULT_TRANAB_COLUMN_SWITCH) {
/*  484 */       MatrixMatrixMult.multAddTransAB_aux(alpha, a, b, c, null);
/*      */     } else {
/*  486 */       MatrixMatrixMult.multAddTransAB(alpha, a, b, c);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean solve(DenseMatrix64F a, DenseMatrix64F b, DenseMatrix64F x) {
/*  520 */     LinearSolver<DenseMatrix64F> solver = LinearSolverFactory.general(a.numRows, a.numCols);
/*      */ 
/*      */     
/*  523 */     LinearSolverSafe linearSolverSafe = new LinearSolverSafe(solver);
/*      */     
/*  525 */     if (!linearSolverSafe.setA((Matrix64F)a)) {
/*  526 */       return false;
/*      */     }
/*  528 */     linearSolverSafe.solve((Matrix64F)b, (Matrix64F)x);
/*  529 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void transpose(DenseMatrix64F mat) {
/*  544 */     if (mat.numCols == mat.numRows) {
/*  545 */       TransposeAlgs.square((RowD1Matrix64F)mat);
/*      */     } else {
/*  547 */       DenseMatrix64F b = new DenseMatrix64F(mat.numCols, mat.numRows);
/*  548 */       transpose(mat, b);
/*  549 */       mat.setReshape(b);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F transpose(DenseMatrix64F A, DenseMatrix64F A_tran) {
/*  567 */     if (A_tran == null) {
/*  568 */       A_tran = new DenseMatrix64F(A.numCols, A.numRows);
/*      */     }
/*  570 */     else if (A.numRows != A_tran.numCols || A.numCols != A_tran.numRows) {
/*  571 */       throw new IllegalArgumentException("Incompatible matrix dimensions");
/*      */     } 
/*      */ 
/*      */     
/*  575 */     if (A.numRows > EjmlParameters.TRANSPOSE_SWITCH && A.numCols > EjmlParameters.TRANSPOSE_SWITCH) {
/*      */       
/*  577 */       TransposeAlgs.block((RowD1Matrix64F)A, (RowD1Matrix64F)A_tran, EjmlParameters.BLOCK_WIDTH);
/*      */     } else {
/*  579 */       TransposeAlgs.standard((RowD1Matrix64F)A, (RowD1Matrix64F)A_tran);
/*      */     } 
/*  581 */     return A_tran;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double trace(RowD1Matrix64F a) {
/*  596 */     int N = Math.min(a.numRows, a.numCols);
/*  597 */     double sum = 0.0D;
/*  598 */     int index = 0;
/*  599 */     for (int i = 0; i < N; i++) {
/*  600 */       sum += a.get(index);
/*  601 */       index += 1 + a.numCols;
/*      */     } 
/*      */     
/*  604 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double det(DenseMatrix64F mat) {
/*  618 */     int numCol = mat.getNumCols();
/*  619 */     int numRow = mat.getNumRows();
/*      */     
/*  621 */     if (numCol != numRow)
/*  622 */       throw new IllegalArgumentException("Must be a square matrix."); 
/*  623 */     if (numCol <= 6) {
/*      */ 
/*      */ 
/*      */       
/*  627 */       if (numCol >= 2) {
/*  628 */         return UnrolledDeterminantFromMinor.det((RowD1Matrix64F)mat);
/*      */       }
/*  630 */       return mat.get(0);
/*      */     } 
/*      */     
/*  633 */     LUDecompositionAlt_D64 alg = new LUDecompositionAlt_D64();
/*      */     
/*  635 */     if (alg.inputModified()) {
/*  636 */       mat = mat.copy();
/*      */     }
/*      */     
/*  639 */     if (!alg.decompose(mat))
/*  640 */       return 0.0D; 
/*  641 */     return alg.computeDeterminant();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean invert(DenseMatrix64F mat) {
/*  663 */     if (mat.numCols <= 5) {
/*  664 */       if (mat.numCols != mat.numRows) {
/*  665 */         throw new IllegalArgumentException("Must be a square matrix.");
/*      */       }
/*      */       
/*  668 */       if (mat.numCols >= 2) {
/*  669 */         UnrolledInverseFromMinor.inv(mat, mat);
/*      */       } else {
/*  671 */         mat.set(0, 1.0D / mat.get(0));
/*      */       } 
/*      */     } else {
/*  674 */       LUDecompositionAlt_D64 alg = new LUDecompositionAlt_D64();
/*  675 */       LinearSolverLu solver = new LinearSolverLu((LUDecompositionBase_D64)alg);
/*  676 */       if (solver.setA(mat)) {
/*  677 */         solver.invert(mat);
/*      */       } else {
/*  679 */         return false;
/*      */       } 
/*      */     } 
/*  682 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean invert(DenseMatrix64F mat, DenseMatrix64F result) {
/*  710 */     if (mat.numCols <= 5) {
/*  711 */       if (mat.numCols != mat.numRows) {
/*  712 */         throw new IllegalArgumentException("Must be a square matrix.");
/*      */       }
/*  714 */       if (result.numCols >= 2) {
/*  715 */         UnrolledInverseFromMinor.inv(mat, result);
/*      */       } else {
/*  717 */         result.set(0, 1.0D / mat.get(0));
/*      */       } 
/*      */     } else {
/*  720 */       LUDecompositionAlt_D64 alg = new LUDecompositionAlt_D64();
/*  721 */       LinearSolverLu solver = new LinearSolverLu((LUDecompositionBase_D64)alg);
/*      */       
/*  723 */       if (solver.modifiesA()) {
/*  724 */         mat = mat.copy();
/*      */       }
/*  726 */       if (!solver.setA(mat))
/*  727 */         return false; 
/*  728 */       solver.invert(result);
/*      */     } 
/*  730 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void pinv(DenseMatrix64F A, DenseMatrix64F invA) {
/*  751 */     LinearSolver<DenseMatrix64F> solver = LinearSolverFactory.pseudoInverse(true);
/*  752 */     if (solver.modifiesA()) {
/*  753 */       A = A.copy();
/*      */     }
/*  755 */     if (!solver.setA((Matrix64F)A)) {
/*  756 */       throw new IllegalArgumentException("Invert failed, maybe a bug?");
/*      */     }
/*  758 */     solver.invert((Matrix64F)invA);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F[] columnsToVector(DenseMatrix64F A, DenseMatrix64F[] v) {
/*      */     DenseMatrix64F[] ret;
/*  771 */     if (v == null || v.length < A.numCols) {
/*  772 */       ret = new DenseMatrix64F[A.numCols];
/*      */     } else {
/*  774 */       ret = v;
/*      */     } 
/*      */     
/*  777 */     for (int i = 0; i < ret.length; i++) {
/*  778 */       if (ret[i] == null) {
/*  779 */         ret[i] = new DenseMatrix64F(A.numRows, 1);
/*      */       } else {
/*  781 */         ret[i].reshape(A.numRows, 1, false);
/*      */       } 
/*      */       
/*  784 */       DenseMatrix64F u = ret[i];
/*      */       
/*  786 */       for (int j = 0; j < A.numRows; j++) {
/*  787 */         u.set(j, 0, A.get(j, i));
/*      */       }
/*      */     } 
/*      */     
/*  791 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F[] rowsToVector(DenseMatrix64F A, DenseMatrix64F[] v) {
/*      */     DenseMatrix64F[] ret;
/*  804 */     if (v == null || v.length < A.numRows) {
/*  805 */       ret = new DenseMatrix64F[A.numRows];
/*      */     } else {
/*  807 */       ret = v;
/*      */     } 
/*      */ 
/*      */     
/*  811 */     for (int i = 0; i < ret.length; i++) {
/*  812 */       if (ret[i] == null) {
/*  813 */         ret[i] = new DenseMatrix64F(A.numCols, 1);
/*      */       } else {
/*  815 */         ret[i].reshape(A.numCols, 1, false);
/*      */       } 
/*      */       
/*  818 */       DenseMatrix64F u = ret[i];
/*      */       
/*  820 */       for (int j = 0; j < A.numCols; j++) {
/*  821 */         u.set(j, 0, A.get(i, j));
/*      */       }
/*      */     } 
/*      */     
/*  825 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setIdentity(RowD1Matrix64F mat) {
/*  838 */     int width = (mat.numRows < mat.numCols) ? mat.numRows : mat.numCols;
/*      */     
/*  840 */     Arrays.fill(mat.data, 0, mat.getNumElements(), 0.0D);
/*      */     
/*  842 */     int index = 0;
/*  843 */     for (int i = 0; i < width; i++, index += mat.numCols + 1) {
/*  844 */       mat.data[index] = 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F identity(int width) {
/*  861 */     DenseMatrix64F ret = new DenseMatrix64F(width, width);
/*      */     
/*  863 */     for (int i = 0; i < width; i++) {
/*  864 */       ret.set(i, i, 1.0D);
/*      */     }
/*      */     
/*  867 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F identity(int numRows, int numCols) {
/*  879 */     DenseMatrix64F ret = new DenseMatrix64F(numRows, numCols);
/*      */     
/*  881 */     int small = (numRows < numCols) ? numRows : numCols;
/*      */     
/*  883 */     for (int i = 0; i < small; i++) {
/*  884 */       ret.set(i, i, 1.0D);
/*      */     }
/*      */     
/*  887 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F diag(double... diagEl) {
/*  906 */     return diag(null, diagEl.length, diagEl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F diag(DenseMatrix64F ret, int width, double... diagEl) {
/*  914 */     if (ret == null) {
/*  915 */       ret = new DenseMatrix64F(width, width);
/*      */     } else {
/*  917 */       if (ret.numRows != width || ret.numCols != width) {
/*  918 */         throw new IllegalArgumentException("Unexpected matrix size");
/*      */       }
/*  920 */       fill((D1Matrix64F)ret, 0.0D);
/*      */     } 
/*      */     
/*  923 */     for (int i = 0; i < width; i++) {
/*  924 */       ret.unsafe_set(i, i, diagEl[i]);
/*      */     }
/*      */     
/*  927 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F diagR(int numRows, int numCols, double... diagEl) {
/*  948 */     DenseMatrix64F ret = new DenseMatrix64F(numRows, numCols);
/*      */     
/*  950 */     int o = Math.min(numRows, numCols);
/*      */     
/*  952 */     for (int i = 0; i < o; i++) {
/*  953 */       ret.set(i, i, diagEl[i]);
/*      */     }
/*      */     
/*  956 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void kron(DenseMatrix64F A, DenseMatrix64F B, DenseMatrix64F C) {
/*  974 */     int numColsC = A.numCols * B.numCols;
/*  975 */     int numRowsC = A.numRows * B.numRows;
/*      */     
/*  977 */     if (C.numCols != numColsC || C.numRows != numRowsC) {
/*  978 */       throw new IllegalArgumentException("C does not have the expected dimensions");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  984 */     for (int i = 0; i < A.numRows; i++) {
/*  985 */       for (int j = 0; j < A.numCols; j++) {
/*  986 */         double a = A.get(i, j);
/*      */         
/*  988 */         for (int rowB = 0; rowB < B.numRows; rowB++) {
/*  989 */           for (int colB = 0; colB < B.numCols; colB++) {
/*  990 */             double val = a * B.get(rowB, colB);
/*  991 */             C.set(i * B.numRows + rowB, j * B.numCols + colB, val);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void extract(ReshapeMatrix64F src, int srcY0, int srcY1, int srcX0, int srcX1, ReshapeMatrix64F dst, int dstY0, int dstX0) {
/* 1024 */     if (srcY1 < srcY0 || srcY0 < 0 || srcY1 > src.numRows)
/* 1025 */       throw new IllegalArgumentException("srcY1 < srcY0 || srcY0 < 0 || srcY1 > src.numRows"); 
/* 1026 */     if (srcX1 < srcX0 || srcX0 < 0 || srcX1 > src.numCols) {
/* 1027 */       throw new IllegalArgumentException("srcX1 < srcX0 || srcX0 < 0 || srcX1 > src.numCols");
/*      */     }
/* 1029 */     int w = srcX1 - srcX0;
/* 1030 */     int h = srcY1 - srcY0;
/*      */     
/* 1032 */     if (dstY0 + h > dst.numRows)
/* 1033 */       throw new IllegalArgumentException("dst is too small in rows"); 
/* 1034 */     if (dstX0 + w > dst.numCols) {
/* 1035 */       throw new IllegalArgumentException("dst is too small in columns");
/*      */     }
/*      */     
/* 1038 */     if (src instanceof DenseMatrix64F && dst instanceof DenseMatrix64F) {
/* 1039 */       ImplCommonOps_DenseMatrix64F.extract((DenseMatrix64F)src, srcY0, srcX0, (DenseMatrix64F)dst, dstY0, dstX0, h, w);
/*      */     } else {
/* 1041 */       ImplCommonOps_Matrix64F.extract((Matrix64F)src, srcY0, srcX0, (Matrix64F)dst, dstY0, dstX0, h, w);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F extract(DenseMatrix64F src, int srcY0, int srcY1, int srcX0, int srcX1) {
/* 1067 */     if (srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows)
/* 1068 */       throw new IllegalArgumentException("srcY1 <= srcY0 || srcY0 < 0 || srcY1 > src.numRows"); 
/* 1069 */     if (srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols) {
/* 1070 */       throw new IllegalArgumentException("srcX1 <= srcX0 || srcX0 < 0 || srcX1 > src.numCols");
/*      */     }
/* 1072 */     int w = srcX1 - srcX0;
/* 1073 */     int h = srcY1 - srcY0;
/*      */     
/* 1075 */     DenseMatrix64F dst = new DenseMatrix64F(h, w);
/*      */     
/* 1077 */     ImplCommonOps_DenseMatrix64F.extract(src, srcY0, srcX0, dst, 0, 0, h, w);
/*      */     
/* 1079 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void extractDiag(DenseMatrix64F src, DenseMatrix64F dst) {
/* 1093 */     int N = Math.min(src.numRows, src.numCols);
/*      */     
/* 1095 */     if (!MatrixFeatures.isVector((D1Matrix64F)dst))
/* 1096 */       throw new IllegalArgumentException("Expected a vector for dst."); 
/* 1097 */     if (dst.getNumElements() != N) {
/* 1098 */       throw new IllegalArgumentException("Expected " + N + " elements in dst.");
/*      */     }
/*      */     
/* 1101 */     for (int i = 0; i < N; i++) {
/* 1102 */       dst.set(i, src.unsafe_get(i, i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void insert(ReshapeMatrix64F src, ReshapeMatrix64F dest, int destY0, int destX0) {
/* 1116 */     extract(src, 0, src.numRows, 0, src.numCols, dest, destY0, destX0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double elementMax(D1Matrix64F a) {
/* 1130 */     int size = a.getNumElements();
/*      */     
/* 1132 */     double max = a.get(0);
/* 1133 */     for (int i = 1; i < size; i++) {
/* 1134 */       double val = a.get(i);
/* 1135 */       if (val >= max) {
/* 1136 */         max = val;
/*      */       }
/*      */     } 
/*      */     
/* 1140 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double elementMaxAbs(D1Matrix64F a) {
/* 1154 */     int size = a.getNumElements();
/*      */     
/* 1156 */     double max = 0.0D;
/* 1157 */     for (int i = 0; i < size; i++) {
/* 1158 */       double val = Math.abs(a.get(i));
/* 1159 */       if (val > max) {
/* 1160 */         max = val;
/*      */       }
/*      */     } 
/*      */     
/* 1164 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double elementMin(D1Matrix64F a) {
/* 1178 */     int size = a.getNumElements();
/*      */     
/* 1180 */     double min = a.get(0);
/* 1181 */     for (int i = 1; i < size; i++) {
/* 1182 */       double val = a.get(i);
/* 1183 */       if (val < min) {
/* 1184 */         min = val;
/*      */       }
/*      */     } 
/*      */     
/* 1188 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double elementMinAbs(D1Matrix64F a) {
/* 1202 */     int size = a.getNumElements();
/*      */     
/* 1204 */     double min = Double.MAX_VALUE;
/* 1205 */     for (int i = 0; i < size; i++) {
/* 1206 */       double val = Math.abs(a.get(i));
/* 1207 */       if (val < min) {
/* 1208 */         min = val;
/*      */       }
/*      */     } 
/*      */     
/* 1212 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementMult(D1Matrix64F a, D1Matrix64F b) {
/* 1225 */     if (a.numCols != b.numCols || a.numRows != b.numRows) {
/* 1226 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1229 */     int length = a.getNumElements();
/*      */     
/* 1231 */     for (int i = 0; i < length; i++) {
/* 1232 */       a.times(i, b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementMult(D1Matrix64F a, D1Matrix64F b, D1Matrix64F c) {
/* 1247 */     if (a.numCols != b.numCols || a.numRows != b.numRows || a.numRows != c.numRows || a.numCols != c.numCols)
/*      */     {
/* 1249 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1252 */     int length = a.getNumElements();
/*      */     
/* 1254 */     for (int i = 0; i < length; i++) {
/* 1255 */       c.set(i, a.get(i) * b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementDiv(D1Matrix64F a, D1Matrix64F b) {
/* 1269 */     if (a.numCols != b.numCols || a.numRows != b.numRows) {
/* 1270 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1273 */     int length = a.getNumElements();
/*      */     
/* 1275 */     for (int i = 0; i < length; i++) {
/* 1276 */       a.div(i, b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementDiv(D1Matrix64F a, D1Matrix64F b, D1Matrix64F c) {
/* 1291 */     if (a.numCols != b.numCols || a.numRows != b.numRows || a.numRows != c.numRows || a.numCols != c.numCols)
/*      */     {
/* 1293 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1296 */     int length = a.getNumElements();
/*      */     
/* 1298 */     for (int i = 0; i < length; i++) {
/* 1299 */       c.set(i, a.get(i) / b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double elementSum(D1Matrix64F mat) {
/* 1314 */     double total = 0.0D;
/*      */     
/* 1316 */     int size = mat.getNumElements();
/*      */     
/* 1318 */     for (int i = 0; i < size; i++) {
/* 1319 */       total += mat.get(i);
/*      */     }
/*      */     
/* 1322 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double elementSumAbs(D1Matrix64F mat) {
/* 1336 */     double total = 0.0D;
/*      */     
/* 1338 */     int size = mat.getNumElements();
/*      */     
/* 1340 */     for (int i = 0; i < size; i++) {
/* 1341 */       total += Math.abs(mat.get(i));
/*      */     }
/*      */     
/* 1344 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementPower(D1Matrix64F A, D1Matrix64F B, D1Matrix64F C) {
/* 1359 */     if (A.numRows != B.numRows || A.numRows != C.numRows || A.numCols != B.numCols || A.numCols != C.numCols)
/*      */     {
/* 1361 */       throw new IllegalArgumentException("All matrices must be the same shape");
/*      */     }
/*      */     
/* 1364 */     int size = A.getNumElements();
/* 1365 */     for (int i = 0; i < size; i++) {
/* 1366 */       C.data[i] = Math.pow(A.data[i], B.data[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementPower(double a, D1Matrix64F B, D1Matrix64F C) {
/* 1382 */     if (B.numRows != C.numRows || B.numCols != C.numCols) {
/* 1383 */       throw new IllegalArgumentException("All matrices must be the same shape");
/*      */     }
/*      */     
/* 1386 */     int size = B.getNumElements();
/* 1387 */     for (int i = 0; i < size; i++) {
/* 1388 */       C.data[i] = Math.pow(a, B.data[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementPower(D1Matrix64F A, double b, D1Matrix64F C) {
/* 1404 */     if (A.numRows != C.numRows || A.numCols != C.numCols) {
/* 1405 */       throw new IllegalArgumentException("All matrices must be the same shape");
/*      */     }
/*      */     
/* 1408 */     int size = A.getNumElements();
/* 1409 */     for (int i = 0; i < size; i++) {
/* 1410 */       C.data[i] = Math.pow(A.data[i], b);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementLog(D1Matrix64F A, D1Matrix64F C) {
/* 1425 */     if (A.numCols != C.numCols || A.numRows != C.numRows) {
/* 1426 */       throw new IllegalArgumentException("All matrices must be the same shape");
/*      */     }
/*      */     
/* 1429 */     int size = A.getNumElements();
/* 1430 */     for (int i = 0; i < size; i++) {
/* 1431 */       C.data[i] = Math.log(A.data[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void elementExp(D1Matrix64F A, D1Matrix64F C) {
/* 1446 */     if (A.numCols != C.numCols || A.numRows != C.numRows) {
/* 1447 */       throw new IllegalArgumentException("All matrices must be the same shape");
/*      */     }
/*      */     
/* 1450 */     int size = A.getNumElements();
/* 1451 */     for (int i = 0; i < size; i++) {
/* 1452 */       C.data[i] = Math.exp(A.data[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F sumRows(DenseMatrix64F input, DenseMatrix64F output) {
/* 1468 */     if (output == null) {
/* 1469 */       output = new DenseMatrix64F(input.numRows, 1);
/* 1470 */     } else if (output.getNumElements() != input.numRows) {
/* 1471 */       throw new IllegalArgumentException("Output does not have enough elements to store the results");
/*      */     } 
/* 1473 */     for (int row = 0; row < input.numRows; row++) {
/* 1474 */       double total = 0.0D;
/*      */       
/* 1476 */       int end = (row + 1) * input.numCols;
/* 1477 */       for (int index = row * input.numCols; index < end; index++) {
/* 1478 */         total += input.data[index];
/*      */       }
/*      */       
/* 1481 */       output.set(row, total);
/*      */     } 
/* 1483 */     return output;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F sumCols(DenseMatrix64F input, DenseMatrix64F output) {
/* 1498 */     if (output == null) {
/* 1499 */       output = new DenseMatrix64F(1, input.numCols);
/* 1500 */     } else if (output.getNumElements() != input.numCols) {
/* 1501 */       throw new IllegalArgumentException("Output does not have enough elements to store the results");
/*      */     } 
/* 1503 */     for (int cols = 0; cols < input.numCols; cols++) {
/* 1504 */       double total = 0.0D;
/*      */       
/* 1506 */       int index = cols;
/* 1507 */       int end = index + input.numCols * input.numRows;
/* 1508 */       for (; index < end; index += input.numCols) {
/* 1509 */         total += input.data[index];
/*      */       }
/*      */       
/* 1512 */       output.set(cols, total);
/*      */     } 
/* 1514 */     return output;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addEquals(D1Matrix64F a, D1Matrix64F b) {
/* 1529 */     if (a.numCols != b.numCols || a.numRows != b.numRows) {
/* 1530 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1533 */     int length = a.getNumElements();
/*      */     
/* 1535 */     for (int i = 0; i < length; i++) {
/* 1536 */       a.plus(i, b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void addEquals(D1Matrix64F a, double beta, D1Matrix64F b) {
/* 1553 */     if (a.numCols != b.numCols || a.numRows != b.numRows) {
/* 1554 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1557 */     int length = a.getNumElements();
/*      */     
/* 1559 */     for (int i = 0; i < length; i++) {
/* 1560 */       a.plus(i, beta * b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(D1Matrix64F a, D1Matrix64F b, D1Matrix64F c) {
/* 1581 */     if (a.numCols != b.numCols || a.numRows != b.numRows || a.numCols != c.numCols || a.numRows != c.numRows)
/*      */     {
/* 1583 */       throw new IllegalArgumentException("The matrices are not all the same dimension.");
/*      */     }
/*      */     
/* 1586 */     int length = a.getNumElements();
/*      */     
/* 1588 */     for (int i = 0; i < length; i++) {
/* 1589 */       c.set(i, a.get(i) + b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(D1Matrix64F a, double beta, D1Matrix64F b, D1Matrix64F c) {
/* 1611 */     if (a.numCols != b.numCols || a.numRows != b.numRows || a.numCols != c.numCols || a.numRows != c.numRows)
/*      */     {
/* 1613 */       throw new IllegalArgumentException("The matrices are not all the same dimension.");
/*      */     }
/*      */     
/* 1616 */     int length = a.getNumElements();
/*      */     
/* 1618 */     for (int i = 0; i < length; i++) {
/* 1619 */       c.set(i, a.get(i) + beta * b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(double alpha, D1Matrix64F a, double beta, D1Matrix64F b, D1Matrix64F c) {
/* 1642 */     if (a.numCols != b.numCols || a.numRows != b.numRows || a.numCols != c.numCols || a.numRows != c.numRows)
/*      */     {
/* 1644 */       throw new IllegalArgumentException("The matrices are not all the same dimension.");
/*      */     }
/*      */     
/* 1647 */     int length = a.getNumElements();
/*      */     
/* 1649 */     for (int i = 0; i < length; i++) {
/* 1650 */       c.set(i, alpha * a.get(i) + beta * b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(double alpha, D1Matrix64F a, D1Matrix64F b, D1Matrix64F c) {
/* 1672 */     if (a.numCols != b.numCols || a.numRows != b.numRows || a.numCols != c.numCols || a.numRows != c.numRows)
/*      */     {
/* 1674 */       throw new IllegalArgumentException("The matrices are not all the same dimension.");
/*      */     }
/*      */     
/* 1677 */     int length = a.getNumElements();
/*      */     
/* 1679 */     for (int i = 0; i < length; i++) {
/* 1680 */       c.set(i, alpha * a.get(i) + b.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(D1Matrix64F a, double val) {
/* 1695 */     int length = a.getNumElements();
/*      */     
/* 1697 */     for (int i = 0; i < length; i++) {
/* 1698 */       a.plus(i, val);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(D1Matrix64F a, double val, D1Matrix64F c) {
/* 1714 */     if (a.numRows != c.numRows || a.numCols != c.numCols) {
/* 1715 */       throw new IllegalArgumentException("Dimensions of a and c do not match.");
/*      */     }
/*      */     
/* 1718 */     int length = a.getNumElements();
/*      */     
/* 1720 */     for (int i = 0; i < length; i++) {
/* 1721 */       c.data[i] = a.data[i] + val;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void subtract(D1Matrix64F a, double val, D1Matrix64F c) {
/* 1737 */     if (a.numRows != c.numRows || a.numCols != c.numCols) {
/* 1738 */       throw new IllegalArgumentException("Dimensions of a and c do not match.");
/*      */     }
/*      */     
/* 1741 */     int length = a.getNumElements();
/*      */     
/* 1743 */     for (int i = 0; i < length; i++) {
/* 1744 */       c.data[i] = a.data[i] - val;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void subtract(double val, D1Matrix64F a, D1Matrix64F c) {
/* 1760 */     if (a.numRows != c.numRows || a.numCols != c.numCols) {
/* 1761 */       throw new IllegalArgumentException("Dimensions of a and c do not match.");
/*      */     }
/*      */     
/* 1764 */     int length = a.getNumElements();
/*      */     
/* 1766 */     for (int i = 0; i < length; i++) {
/* 1767 */       c.data[i] = val - a.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void subtractEquals(D1Matrix64F a, D1Matrix64F b) {
/* 1783 */     if (a.numCols != b.numCols || a.numRows != b.numRows) {
/* 1784 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1787 */     int length = a.getNumElements();
/*      */     
/* 1789 */     for (int i = 0; i < length; i++) {
/* 1790 */       a.data[i] = a.data[i] - b.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void subtract(D1Matrix64F a, D1Matrix64F b, D1Matrix64F c) {
/* 1810 */     if (a.numCols != b.numCols || a.numRows != b.numRows) {
/* 1811 */       throw new IllegalArgumentException("The 'a' and 'b' matrices do not have compatible dimensions");
/*      */     }
/*      */     
/* 1814 */     int length = a.getNumElements();
/*      */     
/* 1816 */     for (int i = 0; i < length; i++) {
/* 1817 */       c.data[i] = a.data[i] - b.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void scale(double alpha, D1Matrix64F a) {
/* 1835 */     int size = a.getNumElements();
/*      */     
/* 1837 */     for (int i = 0; i < size; i++) {
/* 1838 */       a.data[i] = a.data[i] * alpha;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void scale(double alpha, D1Matrix64F a, D1Matrix64F b) {
/* 1855 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 1856 */       throw new IllegalArgumentException("Matrices must have the same shape");
/*      */     }
/* 1858 */     int size = a.getNumElements();
/*      */     
/* 1860 */     for (int i = 0; i < size; i++) {
/* 1861 */       b.data[i] = a.data[i] * alpha;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void divide(double alpha, D1Matrix64F a) {
/* 1877 */     int size = a.getNumElements();
/*      */     
/* 1879 */     for (int i = 0; i < size; i++) {
/* 1880 */       a.data[i] = alpha / a.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void divide(D1Matrix64F a, double alpha) {
/* 1896 */     int size = a.getNumElements();
/*      */     
/* 1898 */     for (int i = 0; i < size; i++) {
/* 1899 */       a.data[i] = a.data[i] / alpha;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void divide(double alpha, D1Matrix64F a, D1Matrix64F b) {
/* 1916 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 1917 */       throw new IllegalArgumentException("Matrices must have the same shape");
/*      */     }
/* 1919 */     int size = a.getNumElements();
/*      */     
/* 1921 */     for (int i = 0; i < size; i++) {
/* 1922 */       b.data[i] = alpha / a.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void divide(D1Matrix64F a, double alpha, D1Matrix64F b) {
/* 1939 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/* 1940 */       throw new IllegalArgumentException("Matrices must have the same shape");
/*      */     }
/* 1942 */     int size = a.getNumElements();
/*      */     
/* 1944 */     for (int i = 0; i < size; i++) {
/* 1945 */       b.data[i] = a.data[i] / alpha;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void changeSign(D1Matrix64F a) {
/* 1960 */     int size = a.getNumElements();
/*      */     
/* 1962 */     for (int i = 0; i < size; i++) {
/* 1963 */       a.data[i] = -a.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void changeSign(D1Matrix64F input, D1Matrix64F output) {
/* 1978 */     if (input.numRows != output.numRows || input.numCols != output.numCols) {
/* 1979 */       throw new IllegalArgumentException("Matrices must have the same shape");
/*      */     }
/* 1981 */     int size = input.getNumElements();
/*      */     
/* 1983 */     for (int i = 0; i < size; i++) {
/* 1984 */       output.data[i] = -input.data[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(D1Matrix64F a, double value) {
/* 2000 */     Arrays.fill(a.data, 0, a.getNumElements(), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DenseMatrix64F rref(DenseMatrix64F A, int numUnknowns, DenseMatrix64F reduced) {
/* 2028 */     if (reduced == null) {
/* 2029 */       reduced = new DenseMatrix64F(A.numRows, A.numCols);
/* 2030 */     } else if (reduced.numCols != A.numCols || reduced.numRows != A.numRows) {
/* 2031 */       throw new IllegalArgumentException("'re' must have the same shape as the original input matrix");
/*      */     } 
/* 2033 */     if (numUnknowns <= 0) {
/* 2034 */       numUnknowns = Math.min(A.numCols, A.numRows);
/*      */     }
/* 2036 */     RrefGaussJordanRowPivot rrefGaussJordanRowPivot = new RrefGaussJordanRowPivot();
/* 2037 */     rrefGaussJordanRowPivot.setTolerance(elementMaxAbs((D1Matrix64F)A) * UtilEjml.EPS * Math.max(A.numRows, A.numCols));
/*      */     
/* 2039 */     reduced.set((D1Matrix64F)A);
/* 2040 */     rrefGaussJordanRowPivot.reduce((Matrix64F)reduced, numUnknowns);
/*      */     
/* 2042 */     return reduced;
/*      */   }
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\CommonOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */