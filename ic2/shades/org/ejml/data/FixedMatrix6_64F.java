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
/*     */ public class FixedMatrix6_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a1;
/*     */   public double a2;
/*     */   public double a3;
/*     */   public double a4;
/*     */   public double a5;
/*     */   public double a6;
/*     */   
/*     */   public FixedMatrix6_64F() {}
/*     */   
/*     */   public FixedMatrix6_64F(double a1, double a2, double a3, double a4, double a5, double a6) {
/*  36 */     this.a1 = a1;
/*  37 */     this.a2 = a2;
/*  38 */     this.a3 = a3;
/*  39 */     this.a4 = a4;
/*  40 */     this.a5 = a5;
/*  41 */     this.a6 = a6;
/*     */   }
/*     */   
/*     */   public FixedMatrix6_64F(FixedMatrix6_64F o) {
/*  45 */     this.a1 = o.a1;
/*  46 */     this.a2 = o.a2;
/*  47 */     this.a3 = o.a3;
/*  48 */     this.a4 = o.a4;
/*  49 */     this.a5 = o.a5;
/*  50 */     this.a6 = o.a6;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  55 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  60 */     if (row != 0 && col != 0) {
/*  61 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  63 */     int w = Math.max(row, col);
/*     */     
/*  65 */     if (w == 0)
/*  66 */       return this.a1; 
/*  67 */     if (w == 1)
/*  68 */       return this.a2; 
/*  69 */     if (w == 2)
/*  70 */       return this.a3; 
/*  71 */     if (w == 3)
/*  72 */       return this.a4; 
/*  73 */     if (w == 4)
/*  74 */       return this.a5; 
/*  75 */     if (w == 5) {
/*  76 */       return this.a6;
/*     */     }
/*  78 */     throw new IllegalArgumentException("Out of range.  " + w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/*  84 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/*  89 */     if (row != 0 && col != 0) {
/*  90 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  92 */     int w = Math.max(row, col);
/*     */     
/*  94 */     if (w == 0) {
/*  95 */       this.a1 = val;
/*  96 */     } else if (w == 1) {
/*  97 */       this.a2 = val;
/*  98 */     } else if (w == 2) {
/*  99 */       this.a3 = val;
/* 100 */     } else if (w == 3) {
/* 101 */       this.a4 = val;
/* 102 */     } else if (w == 4) {
/* 103 */       this.a5 = val;
/* 104 */     } else if (w == 5) {
/* 105 */       this.a6 = val;
/*     */     } else {
/* 107 */       throw new IllegalArgumentException("Out of range.  " + w);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 113 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 118 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 123 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 128 */     return (T)new FixedMatrix6_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 133 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix6_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */