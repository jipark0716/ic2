/*     */ package ic2.shades.org.ejml.data;
/*     */ 
/*     */ import ic2.shades.org.ejml.EjmlParameters;
/*     */ import ic2.shades.org.ejml.ops.MatrixIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockMatrix64F
/*     */   extends D1Matrix64F
/*     */ {
/*     */   public int blockLength;
/*     */   
/*     */   public BlockMatrix64F(int numRows, int numCols, int blockLength) {
/*  35 */     this.data = new double[numRows * numCols];
/*  36 */     this.blockLength = blockLength;
/*  37 */     this.numRows = numRows;
/*  38 */     this.numCols = numCols;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockMatrix64F(int numRows, int numCols) {
/*  43 */     this(numRows, numCols, EjmlParameters.BLOCK_WIDTH);
/*     */   }
/*     */   
/*     */   public BlockMatrix64F() {}
/*     */   
/*     */   public void set(BlockMatrix64F A) {
/*  49 */     this.blockLength = A.blockLength;
/*  50 */     this.numRows = A.numRows;
/*  51 */     this.numCols = A.numCols;
/*     */     
/*  53 */     int N = this.numCols * this.numRows;
/*     */     
/*  55 */     if (this.data.length < N) {
/*  56 */       this.data = new double[N];
/*     */     }
/*  58 */     System.arraycopy(A.data, 0, this.data, 0, N);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockMatrix64F wrap(double[] data, int numRows, int numCols, int blockLength) {
/*  63 */     BlockMatrix64F ret = new BlockMatrix64F();
/*  64 */     ret.data = data;
/*  65 */     ret.numRows = numRows;
/*  66 */     ret.numCols = numCols;
/*  67 */     ret.blockLength = blockLength;
/*     */     
/*  69 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getData() {
/*  74 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reshape(int numRows, int numCols, boolean saveValues) {
/*  80 */     if (numRows * numCols <= this.data.length) {
/*  81 */       this.numRows = numRows;
/*  82 */       this.numCols = numCols;
/*     */     } else {
/*  84 */       double[] data = new double[numRows * numCols];
/*     */       
/*  86 */       if (saveValues) {
/*  87 */         System.arraycopy(this.data, 0, data, 0, getNumElements());
/*     */       }
/*     */       
/*  90 */       this.numRows = numRows;
/*  91 */       this.numCols = numCols;
/*  92 */       this.data = data;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reshape(int numRows, int numCols, int blockLength, boolean saveValues) {
/*  97 */     this.blockLength = blockLength;
/*  98 */     reshape(numRows, numCols, saveValues);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex(int row, int col) {
/* 104 */     int blockRow = row / this.blockLength;
/* 105 */     int blockCol = col / this.blockLength;
/*     */     
/* 107 */     int localHeight = Math.min(this.numRows - blockRow * this.blockLength, this.blockLength);
/*     */     
/* 109 */     int index = blockRow * this.blockLength * this.numCols + blockCol * localHeight * this.blockLength;
/*     */     
/* 111 */     int localLength = Math.min(this.numCols - this.blockLength * blockCol, this.blockLength);
/*     */     
/* 113 */     row %= this.blockLength;
/* 114 */     col %= this.blockLength;
/*     */     
/* 116 */     return index + localLength * row + col;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/* 121 */     return this.data[getIndex(row, col)];
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/* 126 */     return this.data[getIndex(row, col)];
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/* 131 */     this.data[getIndex(row, col)] = val;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/* 136 */     this.data[getIndex(row, col)] = val;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 141 */     return this.numRows;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 146 */     return this.numCols;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 151 */     return this.numRows * this.numCols;
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 156 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */   
/*     */   public BlockMatrix64F copy() {
/* 160 */     BlockMatrix64F A = new BlockMatrix64F(this.numRows, this.numCols, this.blockLength);
/* 161 */     A.set(this);
/* 162 */     return A;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\BlockMatrix64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */