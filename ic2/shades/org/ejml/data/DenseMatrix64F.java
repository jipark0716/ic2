/*     */ package ic2.shades.org.ejml.data;
/*     */ 
/*     */ import ic2.shades.org.ejml.ops.CommonOps;
/*     */ import ic2.shades.org.ejml.ops.MatrixIO;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DenseMatrix64F
/*     */   extends RowD1Matrix64F
/*     */ {
/*     */   public DenseMatrix64F(int numRows, int numCols, boolean rowMajor, double... data) {
/* 110 */     int length = numRows * numCols;
/* 111 */     this.data = new double[length];
/*     */     
/* 113 */     this.numRows = numRows;
/* 114 */     this.numCols = numCols;
/*     */     
/* 116 */     set(numRows, numCols, rowMajor, data);
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
/*     */   
/*     */   public DenseMatrix64F(double[][] data) {
/* 129 */     this.numRows = data.length;
/* 130 */     this.numCols = (data[0]).length;
/*     */     
/* 132 */     this.data = new double[this.numRows * this.numCols];
/*     */     
/* 134 */     int pos = 0;
/* 135 */     for (int i = 0; i < this.numRows; i++) {
/* 136 */       double[] row = data[i];
/*     */       
/* 138 */       if (row.length != this.numCols) {
/* 139 */         throw new IllegalArgumentException("All rows must have the same length");
/*     */       }
/*     */       
/* 142 */       System.arraycopy(row, 0, this.data, pos, this.numCols);
/*     */       
/* 144 */       pos += this.numCols;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F(int numRows, int numCols) {
/* 156 */     this.data = new double[numRows * numCols];
/*     */     
/* 158 */     this.numRows = numRows;
/* 159 */     this.numCols = numCols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F(DenseMatrix64F orig) {
/* 169 */     this(orig.numRows, orig.numCols);
/* 170 */     System.arraycopy(orig.data, 0, this.data, 0, orig.getNumElements());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F(int length) {
/* 180 */     this.data = new double[length];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F(ReshapeMatrix64F mat) {
/* 195 */     this(mat.numRows, mat.numCols);
/* 196 */     for (int i = 0; i < this.numRows; i++) {
/* 197 */       for (int j = 0; j < this.numCols; j++) {
/* 198 */         set(i, j, mat.get(i, j));
/*     */       }
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F wrap(int numRows, int numCols, double[] data) {
/* 214 */     DenseMatrix64F s = new DenseMatrix64F();
/* 215 */     s.data = data;
/* 216 */     s.numRows = numRows;
/* 217 */     s.numCols = numCols;
/*     */     
/* 219 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reshape(int numRows, int numCols, boolean saveValues) {
/* 227 */     if (this.data.length < numRows * numCols) {
/* 228 */       double[] d = new double[numRows * numCols];
/*     */       
/* 230 */       if (saveValues) {
/* 231 */         System.arraycopy(this.data, 0, d, 0, getNumElements());
/*     */       }
/*     */       
/* 234 */       this.data = d;
/*     */     } 
/*     */     
/* 237 */     this.numRows = numRows;
/* 238 */     this.numCols = numCols;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double value) {
/* 255 */     if (col < 0 || col >= this.numCols || row < 0 || row >= this.numRows) {
/* 256 */       throw new IllegalArgumentException("Specified element is out of bounds: (" + row + " , " + col + ")");
/*     */     }
/*     */     
/* 259 */     this.data[row * this.numCols + col] = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double value) {
/* 264 */     this.data[row * this.numCols + col] = value;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int row, int col, double value) {
/* 280 */     if (col < 0 || col >= this.numCols || row < 0 || row >= this.numRows) {
/* 281 */       throw new IllegalArgumentException("Specified element is out of bounds");
/*     */     }
/*     */     
/* 284 */     this.data[row * this.numCols + col] = this.data[row * this.numCols + col] + value;
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
/*     */   
/*     */   public double get(int row, int col) {
/* 297 */     if (col < 0 || col >= this.numCols || row < 0 || row >= this.numRows) {
/* 298 */       throw new IllegalArgumentException("Specified element is out of bounds: " + row + " " + col);
/*     */     }
/*     */     
/* 301 */     return this.data[row * this.numCols + col];
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/* 306 */     return this.data[row * this.numCols + col];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndex(int row, int col) {
/* 311 */     return row * this.numCols + col;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInBounds(int row, int col) {
/* 322 */     return (col >= 0 && col < this.numCols && row >= 0 && row < this.numRows);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 333 */     return this.numRows * this.numCols;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReshape(DenseMatrix64F b) {
/* 354 */     int dataLength = b.getNumElements();
/*     */     
/* 356 */     if (this.data.length < dataLength) {
/* 357 */       this.data = new double[dataLength];
/*     */     }
/*     */     
/* 360 */     this.numRows = b.numRows;
/* 361 */     this.numCols = b.numCols;
/*     */     
/* 363 */     System.arraycopy(b.data, 0, this.data, 0, dataLength);
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
/*     */   
/*     */   public void set(int numRows, int numCols, boolean rowMajor, double... data) {
/* 376 */     int length = numRows * numCols;
/*     */     
/* 378 */     if (numRows != this.numRows || numCols != this.numCols)
/* 379 */       throw new IllegalArgumentException("Unexpected matrix shape."); 
/* 380 */     if (length > this.data.length) {
/* 381 */       throw new IllegalArgumentException("The length of this matrix's data array is too small.");
/*     */     }
/* 383 */     if (rowMajor) {
/* 384 */       System.arraycopy(data, 0, this.data, 0, length);
/*     */     } else {
/* 386 */       int index = 0;
/* 387 */       for (int i = 0; i < numRows; i++) {
/* 388 */         for (int j = 0; j < numCols; j++) {
/* 389 */           this.data[index++] = data[j * numRows + i];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void zero() {
/* 399 */     CommonOps.fill(this, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DenseMatrix64F copy() {
/* 409 */     return new DenseMatrix64F(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print() {
/* 419 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void print(String format) {
/* 430 */     MatrixIO.print(System.out, this, format);
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
/*     */   
/*     */   public String toString() {
/* 443 */     ByteArrayOutputStream stream = new ByteArrayOutputStream();
/* 444 */     MatrixIO.print(new PrintStream(stream), this);
/*     */     
/* 446 */     return stream.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\DenseMatrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */