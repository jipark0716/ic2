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
/*     */ public class FixedMatrix3_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a1;
/*     */   public double a2;
/*     */   public double a3;
/*     */   
/*     */   public FixedMatrix3_64F() {}
/*     */   
/*     */   public FixedMatrix3_64F(double a1, double a2, double a3) {
/*  36 */     this.a1 = a1;
/*  37 */     this.a2 = a2;
/*  38 */     this.a3 = a3;
/*     */   }
/*     */   
/*     */   public FixedMatrix3_64F(FixedMatrix3_64F o) {
/*  42 */     this.a1 = o.a1;
/*  43 */     this.a2 = o.a2;
/*  44 */     this.a3 = o.a3;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  49 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  54 */     if (row != 0 && col != 0) {
/*  55 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  57 */     int w = Math.max(row, col);
/*     */     
/*  59 */     if (w == 0)
/*  60 */       return this.a1; 
/*  61 */     if (w == 1)
/*  62 */       return this.a2; 
/*  63 */     if (w == 2) {
/*  64 */       return this.a3;
/*     */     }
/*  66 */     throw new IllegalArgumentException("Out of range.  " + w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/*  72 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/*  77 */     if (row != 0 && col != 0) {
/*  78 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  80 */     int w = Math.max(row, col);
/*     */     
/*  82 */     if (w == 0) {
/*  83 */       this.a1 = val;
/*  84 */     } else if (w == 1) {
/*  85 */       this.a2 = val;
/*  86 */     } else if (w == 2) {
/*  87 */       this.a3 = val;
/*     */     } else {
/*  89 */       throw new IllegalArgumentException("Out of range.  " + w);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/*  95 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 100 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 105 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 110 */     return (T)new FixedMatrix3_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 115 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix3_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */