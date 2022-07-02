/*     */ package ic2.shades.org.ejml.data;
/*     */ 
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
/*     */ 
/*     */ public class FixedMatrix2x2_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a11;
/*     */   public double a12;
/*     */   public double a21;
/*     */   public double a22;
/*     */   
/*     */   public FixedMatrix2x2_64F() {}
/*     */   
/*     */   public FixedMatrix2x2_64F(double a11, double a12, double a21, double a22) {
/*  40 */     this.a11 = a11;
/*  41 */     this.a12 = a12;
/*  42 */     this.a21 = a21;
/*  43 */     this.a22 = a22;
/*     */   }
/*     */   
/*     */   public FixedMatrix2x2_64F(FixedMatrix2x2_64F o) {
/*  47 */     this.a11 = o.a11;
/*  48 */     this.a12 = o.a12;
/*  49 */     this.a21 = o.a21;
/*  50 */     this.a22 = o.a22;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  55 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  60 */     if (row == 0) {
/*  61 */       if (col == 0)
/*  62 */         return this.a11; 
/*  63 */       if (col == 1) {
/*  64 */         return this.a12;
/*     */       }
/*  66 */     } else if (row == 1) {
/*  67 */       if (col == 0)
/*  68 */         return this.a21; 
/*  69 */       if (col == 1) {
/*  70 */         return this.a22;
/*     */       }
/*     */     } 
/*  73 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/*  78 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/*  83 */     if (row == 0) {
/*  84 */       if (col == 0) {
/*  85 */         this.a11 = val; return;
/*  86 */       }  if (col == 1) {
/*  87 */         this.a12 = val; return;
/*     */       } 
/*  89 */     } else if (row == 1) {
/*  90 */       if (col == 0) {
/*  91 */         this.a21 = val; return;
/*  92 */       }  if (col == 1) {
/*  93 */         this.a22 = val; return;
/*     */       } 
/*     */     } 
/*  96 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 101 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 106 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 111 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 116 */     return (T)new FixedMatrix2x2_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 121 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix2x2_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */