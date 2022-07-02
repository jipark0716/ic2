/*      */ package ic2.shades.org.ejml.simple;
/*      */ 
/*      */ import ic2.shades.org.ejml.UtilEjml;
/*      */ import ic2.shades.org.ejml.alg.dense.mult.VectorVectorMult;
/*      */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*      */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*      */ import ic2.shades.org.ejml.data.Matrix64F;
/*      */ import ic2.shades.org.ejml.data.MatrixIterator;
/*      */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*      */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*      */ import ic2.shades.org.ejml.factory.SingularMatrixException;
/*      */ import ic2.shades.org.ejml.ops.CommonOps;
/*      */ import ic2.shades.org.ejml.ops.MatrixFeatures;
/*      */ import ic2.shades.org.ejml.ops.MatrixIO;
/*      */ import ic2.shades.org.ejml.ops.NormOps;
/*      */ import ic2.shades.org.ejml.ops.SpecializedOps;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class SimpleBase<T extends SimpleBase>
/*      */   implements Serializable
/*      */ {
/*      */   protected DenseMatrix64F mat;
/*      */   
/*      */   public SimpleBase(int numRows, int numCols) {
/*   52 */     this.mat = new DenseMatrix64F(numRows, numCols);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SimpleBase() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract T createMatrix(int paramInt1, int paramInt2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DenseMatrix64F getMatrix() {
/*   78 */     return this.mat;
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
/*      */   public T transpose() {
/*   92 */     T ret = createMatrix(this.mat.numCols, this.mat.numRows);
/*      */     
/*   94 */     CommonOps.transpose(this.mat, ret.getMatrix());
/*      */     
/*   96 */     return ret;
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
/*      */   public T mult(T b) {
/*  115 */     T ret = createMatrix(this.mat.numRows, (b.getMatrix()).numCols);
/*      */     
/*  117 */     CommonOps.mult((RowD1Matrix64F)this.mat, (RowD1Matrix64F)b.getMatrix(), (RowD1Matrix64F)ret.getMatrix());
/*      */     
/*  119 */     return ret;
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
/*      */   public T kron(T B) {
/*  135 */     T ret = createMatrix(this.mat.numRows * B.numRows(), this.mat.numCols * B.numCols());
/*  136 */     CommonOps.kron(this.mat, B.getMatrix(), ret.getMatrix());
/*      */     
/*  138 */     return ret;
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
/*      */   public T plus(T b) {
/*  157 */     T ret = copy();
/*      */     
/*  159 */     CommonOps.addEquals((D1Matrix64F)ret.getMatrix(), (D1Matrix64F)b.getMatrix());
/*      */     
/*  161 */     return ret;
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
/*      */   public T minus(T b) {
/*  180 */     T ret = copy();
/*      */     
/*  182 */     CommonOps.subtract((D1Matrix64F)getMatrix(), (D1Matrix64F)b.getMatrix(), (D1Matrix64F)ret.getMatrix());
/*      */     
/*  184 */     return ret;
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
/*      */   public T minus(double b) {
/*  203 */     T ret = copy();
/*      */     
/*  205 */     CommonOps.subtract((D1Matrix64F)getMatrix(), b, (D1Matrix64F)ret.getMatrix());
/*      */     
/*  207 */     return ret;
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
/*      */   public T plus(double beta) {
/*  226 */     T ret = createMatrix(numRows(), numCols());
/*      */     
/*  228 */     CommonOps.add((D1Matrix64F)getMatrix(), beta, (D1Matrix64F)ret.getMatrix());
/*      */     
/*  230 */     return ret;
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
/*      */   public T plus(double beta, T b) {
/*  249 */     T ret = copy();
/*      */     
/*  251 */     CommonOps.addEquals((D1Matrix64F)ret.getMatrix(), beta, (D1Matrix64F)b.getMatrix());
/*      */     
/*  253 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double dot(T v) {
/*  263 */     if (!isVector())
/*  264 */       throw new IllegalArgumentException("'this' matrix is not a vector."); 
/*  265 */     if (!v.isVector()) {
/*  266 */       throw new IllegalArgumentException("'v' matrix is not a vector.");
/*      */     }
/*      */     
/*  269 */     return VectorVectorMult.innerProd((D1Matrix64F)this.mat, (D1Matrix64F)v.getMatrix());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVector() {
/*  279 */     return (this.mat.numRows == 1 || this.mat.numCols == 1);
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
/*      */   public T scale(double val) {
/*  294 */     T ret = copy();
/*      */     
/*  296 */     CommonOps.scale(val, (D1Matrix64F)ret.getMatrix());
/*      */     
/*  298 */     return ret;
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
/*      */   public T divide(double val) {
/*  313 */     T ret = copy();
/*      */     
/*  315 */     CommonOps.divide((D1Matrix64F)ret.getMatrix(), val);
/*      */     
/*  317 */     return ret;
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
/*      */   public T invert() {
/*  339 */     T ret = createMatrix(this.mat.numRows, this.mat.numCols);
/*  340 */     if (!CommonOps.invert(this.mat, ret.getMatrix())) {
/*  341 */       throw new SingularMatrixException();
/*      */     }
/*  343 */     if (MatrixFeatures.hasUncountable((D1Matrix64F)ret.getMatrix()))
/*  344 */       throw new SingularMatrixException("Solution has uncountable numbers"); 
/*  345 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T pseudoInverse() {
/*  356 */     T ret = createMatrix(this.mat.numCols, this.mat.numRows);
/*  357 */     CommonOps.pinv(this.mat, ret.getMatrix());
/*  358 */     return ret;
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
/*      */   public T solve(T b) {
/*  384 */     T x = createMatrix(this.mat.numCols, (b.getMatrix()).numCols);
/*      */     
/*  386 */     if (!CommonOps.solve(this.mat, b.getMatrix(), x.getMatrix())) {
/*  387 */       throw new SingularMatrixException();
/*      */     }
/*  389 */     if (MatrixFeatures.hasUncountable((D1Matrix64F)x.getMatrix())) {
/*  390 */       throw new SingularMatrixException("Solution contains uncountable numbers");
/*      */     }
/*  392 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(T a) {
/*  403 */     this.mat.set((D1Matrix64F)a.getMatrix());
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
/*      */   public void set(double val) {
/*  419 */     CommonOps.fill((D1Matrix64F)this.mat, val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zero() {
/*  428 */     this.mat.zero();
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
/*      */   public double normF() {
/*  443 */     return NormOps.normF((D1Matrix64F)this.mat);
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
/*      */   public double conditionP2() {
/*  457 */     return NormOps.conditionP2(this.mat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double determinant() {
/*  468 */     double ret = CommonOps.det(this.mat);
/*      */     
/*  470 */     if (UtilEjml.isUncountable(ret))
/*  471 */       return 0.0D; 
/*  472 */     return ret;
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
/*      */   public double trace() {
/*  485 */     return CommonOps.trace((RowD1Matrix64F)this.mat);
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
/*      */   public void reshape(int numRows, int numCols) {
/*  505 */     this.mat.reshape(numRows, numCols, false);
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
/*      */   public void set(int row, int col, double value) {
/*  517 */     this.mat.set(row, col, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set(int index, double value) {
/*  527 */     this.mat.set(index, value);
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
/*      */   public void setRow(int row, int offset, double... values) {
/*  542 */     for (int i = 0; i < values.length; i++) {
/*  543 */       this.mat.set(row, offset + i, values[i]);
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
/*      */   public void setColumn(int column, int offset, double... values) {
/*  559 */     for (int i = 0; i < values.length; i++) {
/*  560 */       this.mat.set(offset + i, column, values[i]);
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
/*      */   public double get(int row, int col) {
/*  573 */     return this.mat.get(row, col);
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
/*      */   public double get(int index) {
/*  585 */     return this.mat.data[index];
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
/*      */   public int getIndex(int row, int col) {
/*  598 */     return row * this.mat.numCols + col;
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
/*      */   public MatrixIterator iterator(boolean rowMajor, int minRow, int minCol, int maxRow, int maxCol) {
/*  615 */     return new MatrixIterator((ReshapeMatrix64F)this.mat, rowMajor, minRow, minCol, maxRow, maxCol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T copy() {
/*  624 */     T ret = createMatrix(this.mat.numRows, this.mat.numCols);
/*  625 */     ret.getMatrix().set((D1Matrix64F)getMatrix());
/*  626 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int numRows() {
/*  635 */     return this.mat.numRows;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int numCols() {
/*  644 */     return this.mat.numCols;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNumElements() {
/*  654 */     return this.mat.getNumElements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void print() {
/*  662 */     MatrixIO.print(System.out, (Matrix64F)this.mat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void print(int numChar, int precision) {
/*  669 */     MatrixIO.print(System.out, (Matrix64F)this.mat, numChar, precision);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void print(String format) {
/*  679 */     MatrixIO.print(System.out, (Matrix64F)this.mat, format);
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
/*      */   public String toString() {
/*  691 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/*  692 */     MatrixIO.print(new PrintStream(stream), (Matrix64F)this.mat);
/*      */     
/*  694 */     return stream.toString();
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
/*      */   public T extractMatrix(int y0, int y1, int x0, int x1) {
/*  720 */     if (y0 == Integer.MAX_VALUE) y0 = this.mat.numRows; 
/*  721 */     if (y1 == Integer.MAX_VALUE) y1 = this.mat.numRows; 
/*  722 */     if (x0 == Integer.MAX_VALUE) x0 = this.mat.numCols; 
/*  723 */     if (x1 == Integer.MAX_VALUE) x1 = this.mat.numCols;
/*      */     
/*  725 */     T ret = createMatrix(y1 - y0, x1 - x0);
/*      */     
/*  727 */     CommonOps.extract((ReshapeMatrix64F)this.mat, y0, y1, x0, x1, (ReshapeMatrix64F)ret.getMatrix(), 0, 0);
/*      */     
/*  729 */     return ret;
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
/*      */   public T extractVector(boolean extractRow, int element) {
/*  744 */     int length = extractRow ? this.mat.numCols : this.mat.numRows;
/*      */     
/*  746 */     T ret = extractRow ? createMatrix(1, length) : createMatrix(length, 1);
/*      */     
/*  748 */     if (extractRow) {
/*  749 */       SpecializedOps.subvector((RowD1Matrix64F)this.mat, element, 0, length, true, 0, (RowD1Matrix64F)ret.getMatrix());
/*      */     } else {
/*  751 */       SpecializedOps.subvector((RowD1Matrix64F)this.mat, 0, element, length, false, 0, (RowD1Matrix64F)ret.getMatrix());
/*      */     } 
/*      */     
/*  754 */     return ret;
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
/*      */   public T extractDiag() {
/*  767 */     int N = Math.min(this.mat.numCols, this.mat.numRows);
/*      */     
/*  769 */     T diag = createMatrix(N, 1);
/*      */     
/*  771 */     CommonOps.extractDiag(this.mat, diag.getMatrix());
/*      */     
/*  773 */     return diag;
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
/*      */   public boolean isIdentical(T a, double tol) {
/*  785 */     return MatrixFeatures.isIdentical((D1Matrix64F)this.mat, (D1Matrix64F)a.getMatrix(), tol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasUncountable() {
/*  794 */     return MatrixFeatures.hasUncountable((D1Matrix64F)this.mat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleSVD svd() {
/*  804 */     return new SimpleSVD<SimpleMatrix>(this.mat, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleSVD svd(boolean compact) {
/*  813 */     return new SimpleSVD<SimpleMatrix>(this.mat, compact);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SimpleEVD eig() {
/*  820 */     return new SimpleEVD<SimpleMatrix>(this.mat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertIntoThis(int insertRow, int insertCol, T B) {
/*  831 */     CommonOps.insert((ReshapeMatrix64F)B.getMatrix(), (ReshapeMatrix64F)this.mat, insertRow, insertCol);
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
/*      */   public T combine(int insertRow, int insertCol, T B) {
/*      */     T ret;
/*  858 */     if (insertRow == Integer.MAX_VALUE) {
/*  859 */       insertRow = this.mat.numRows;
/*      */     }
/*      */     
/*  862 */     if (insertCol == Integer.MAX_VALUE) {
/*  863 */       insertCol = this.mat.numCols;
/*      */     }
/*      */     
/*  866 */     int maxRow = insertRow + B.numRows();
/*  867 */     int maxCol = insertCol + B.numCols();
/*      */ 
/*      */ 
/*      */     
/*  871 */     if (maxRow > this.mat.numRows || maxCol > this.mat.numCols) {
/*  872 */       int M = Math.max(maxRow, this.mat.numRows);
/*  873 */       int N = Math.max(maxCol, this.mat.numCols);
/*      */       
/*  875 */       ret = createMatrix(M, N);
/*  876 */       ret.insertIntoThis(0, 0, this);
/*      */     } else {
/*  878 */       ret = copy();
/*      */     } 
/*      */     
/*  881 */     ret.insertIntoThis(insertRow, insertCol, B);
/*      */     
/*  883 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double elementMaxAbs() {
/*  893 */     return CommonOps.elementMaxAbs((D1Matrix64F)this.mat);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double elementSum() {
/*  902 */     return CommonOps.elementSum((D1Matrix64F)this.mat);
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
/*      */   public T elementMult(T b) {
/*  916 */     T c = createMatrix(this.mat.numRows, this.mat.numCols);
/*      */     
/*  918 */     CommonOps.elementMult((D1Matrix64F)this.mat, (D1Matrix64F)b.getMatrix(), (D1Matrix64F)c.getMatrix());
/*      */     
/*  920 */     return c;
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
/*      */   public T elementDiv(T b) {
/*  934 */     T c = createMatrix(this.mat.numRows, this.mat.numCols);
/*      */     
/*  936 */     CommonOps.elementDiv((D1Matrix64F)this.mat, (D1Matrix64F)b.getMatrix(), (D1Matrix64F)c.getMatrix());
/*      */     
/*  938 */     return c;
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
/*      */   public T elementPower(T b) {
/*  952 */     T c = createMatrix(this.mat.numRows, this.mat.numCols);
/*      */     
/*  954 */     CommonOps.elementPower((D1Matrix64F)this.mat, (D1Matrix64F)b.getMatrix(), (D1Matrix64F)c.getMatrix());
/*      */     
/*  956 */     return c;
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
/*      */   public T elementPower(double b) {
/*  970 */     T c = createMatrix(this.mat.numRows, this.mat.numCols);
/*      */     
/*  972 */     CommonOps.elementPower((D1Matrix64F)this.mat, b, (D1Matrix64F)c.getMatrix());
/*      */     
/*  974 */     return c;
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
/*      */   public T elementExp() {
/*  987 */     T c = createMatrix(this.mat.numRows, this.mat.numCols);
/*      */     
/*  989 */     CommonOps.elementExp((D1Matrix64F)this.mat, (D1Matrix64F)c.getMatrix());
/*      */     
/*  991 */     return c;
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
/*      */   public T elementLog() {
/* 1004 */     T c = createMatrix(this.mat.numRows, this.mat.numCols);
/*      */     
/* 1006 */     CommonOps.elementLog((D1Matrix64F)this.mat, (D1Matrix64F)c.getMatrix());
/*      */     
/* 1008 */     return c;
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
/*      */   public T negative() {
/* 1021 */     T A = copy();
/* 1022 */     CommonOps.changeSign((D1Matrix64F)A.getMatrix());
/* 1023 */     return A;
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
/*      */   public void saveToFileBinary(String fileName) throws IOException {
/* 1039 */     MatrixIO.saveBin((ReshapeMatrix64F)this.mat, fileName);
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
/*      */   public static SimpleMatrix loadBinary(String fileName) throws IOException {
/* 1055 */     ReshapeMatrix64F mat = MatrixIO.loadBin(fileName);
/*      */ 
/*      */     
/* 1058 */     if (mat instanceof DenseMatrix64F) {
/* 1059 */       return SimpleMatrix.wrap((DenseMatrix64F)mat);
/*      */     }
/*      */     
/* 1062 */     return SimpleMatrix.wrap(new DenseMatrix64F(mat));
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
/*      */   public void saveToFileCSV(String fileName) throws IOException {
/* 1079 */     MatrixIO.saveCSV((ReshapeMatrix64F)this.mat, fileName);
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
/*      */   public static SimpleMatrix loadCSV(String fileName) throws IOException {
/* 1095 */     DenseMatrix64F denseMatrix64F = MatrixIO.loadCSV(fileName);
/*      */ 
/*      */     
/* 1098 */     if (denseMatrix64F instanceof DenseMatrix64F) {
/* 1099 */       return SimpleMatrix.wrap(denseMatrix64F);
/*      */     }
/*      */     
/* 1102 */     return SimpleMatrix.wrap(new DenseMatrix64F((ReshapeMatrix64F)denseMatrix64F));
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
/*      */   public boolean isInBounds(int row, int col) {
/* 1114 */     return (row >= 0 && col >= 0 && row < this.mat.numRows && col < this.mat.numCols);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void printDimensions() {
/* 1121 */     System.out.println("[rows = " + numRows() + " , cols = " + numCols() + " ]");
/*      */   }
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\simple\SimpleBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */