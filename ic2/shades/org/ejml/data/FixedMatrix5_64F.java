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
/*     */ public class FixedMatrix5_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a1;
/*     */   public double a2;
/*     */   public double a3;
/*     */   public double a4;
/*     */   public double a5;
/*     */   
/*     */   public FixedMatrix5_64F() {}
/*     */   
/*     */   public FixedMatrix5_64F(double a1, double a2, double a3, double a4, double a5) {
/*  36 */     this.a1 = a1;
/*  37 */     this.a2 = a2;
/*  38 */     this.a3 = a3;
/*  39 */     this.a4 = a4;
/*  40 */     this.a5 = a5;
/*     */   }
/*     */   
/*     */   public FixedMatrix5_64F(FixedMatrix5_64F o) {
/*  44 */     this.a1 = o.a1;
/*  45 */     this.a2 = o.a2;
/*  46 */     this.a3 = o.a3;
/*  47 */     this.a4 = o.a4;
/*  48 */     this.a5 = o.a5;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  53 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  58 */     if (row != 0 && col != 0) {
/*  59 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  61 */     int w = Math.max(row, col);
/*     */     
/*  63 */     if (w == 0)
/*  64 */       return this.a1; 
/*  65 */     if (w == 1)
/*  66 */       return this.a2; 
/*  67 */     if (w == 2)
/*  68 */       return this.a3; 
/*  69 */     if (w == 3)
/*  70 */       return this.a4; 
/*  71 */     if (w == 4) {
/*  72 */       return this.a5;
/*     */     }
/*  74 */     throw new IllegalArgumentException("Out of range.  " + w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/*  80 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/*  85 */     if (row != 0 && col != 0) {
/*  86 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  88 */     int w = Math.max(row, col);
/*     */     
/*  90 */     if (w == 0) {
/*  91 */       this.a1 = val;
/*  92 */     } else if (w == 1) {
/*  93 */       this.a2 = val;
/*  94 */     } else if (w == 2) {
/*  95 */       this.a3 = val;
/*  96 */     } else if (w == 3) {
/*  97 */       this.a4 = val;
/*  98 */     } else if (w == 4) {
/*  99 */       this.a5 = val;
/*     */     } else {
/* 101 */       throw new IllegalArgumentException("Out of range.  " + w);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 107 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 112 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 117 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 122 */     return (T)new FixedMatrix5_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 127 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix5_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */