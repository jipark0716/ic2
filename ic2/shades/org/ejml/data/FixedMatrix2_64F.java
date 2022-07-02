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
/*     */ public class FixedMatrix2_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a1;
/*     */   public double a2;
/*     */   
/*     */   public FixedMatrix2_64F() {}
/*     */   
/*     */   public FixedMatrix2_64F(double a1, double a2) {
/*  36 */     this.a1 = a1;
/*  37 */     this.a2 = a2;
/*     */   }
/*     */   
/*     */   public FixedMatrix2_64F(FixedMatrix2_64F o) {
/*  41 */     this.a1 = o.a1;
/*  42 */     this.a2 = o.a2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  47 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  52 */     if (row != 0 && col != 0) {
/*  53 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  55 */     int w = Math.max(row, col);
/*     */     
/*  57 */     if (w == 0)
/*  58 */       return this.a1; 
/*  59 */     if (w == 1) {
/*  60 */       return this.a2;
/*     */     }
/*  62 */     throw new IllegalArgumentException("Out of range.  " + w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/*  68 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/*  73 */     if (row != 0 && col != 0) {
/*  74 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  76 */     int w = Math.max(row, col);
/*     */     
/*  78 */     if (w == 0) {
/*  79 */       this.a1 = val;
/*  80 */     } else if (w == 1) {
/*  81 */       this.a2 = val;
/*     */     } else {
/*  83 */       throw new IllegalArgumentException("Out of range.  " + w);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/*  89 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/*  94 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/*  99 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 104 */     return (T)new FixedMatrix2_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 109 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix2_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */