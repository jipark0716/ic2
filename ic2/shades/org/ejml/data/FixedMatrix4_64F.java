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
/*     */ public class FixedMatrix4_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a1;
/*     */   public double a2;
/*     */   public double a3;
/*     */   public double a4;
/*     */   
/*     */   public FixedMatrix4_64F() {}
/*     */   
/*     */   public FixedMatrix4_64F(double a1, double a2, double a3, double a4) {
/*  36 */     this.a1 = a1;
/*  37 */     this.a2 = a2;
/*  38 */     this.a3 = a3;
/*  39 */     this.a4 = a4;
/*     */   }
/*     */   
/*     */   public FixedMatrix4_64F(FixedMatrix4_64F o) {
/*  43 */     this.a1 = o.a1;
/*  44 */     this.a2 = o.a2;
/*  45 */     this.a3 = o.a3;
/*  46 */     this.a4 = o.a4;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  51 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  56 */     if (row != 0 && col != 0) {
/*  57 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  59 */     int w = Math.max(row, col);
/*     */     
/*  61 */     if (w == 0)
/*  62 */       return this.a1; 
/*  63 */     if (w == 1)
/*  64 */       return this.a2; 
/*  65 */     if (w == 2)
/*  66 */       return this.a3; 
/*  67 */     if (w == 3) {
/*  68 */       return this.a4;
/*     */     }
/*  70 */     throw new IllegalArgumentException("Out of range.  " + w);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/*  76 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/*  81 */     if (row != 0 && col != 0) {
/*  82 */       throw new IllegalArgumentException("Row or column must be zero since this is a vector");
/*     */     }
/*  84 */     int w = Math.max(row, col);
/*     */     
/*  86 */     if (w == 0) {
/*  87 */       this.a1 = val;
/*  88 */     } else if (w == 1) {
/*  89 */       this.a2 = val;
/*  90 */     } else if (w == 2) {
/*  91 */       this.a3 = val;
/*  92 */     } else if (w == 3) {
/*  93 */       this.a4 = val;
/*     */     } else {
/*  95 */       throw new IllegalArgumentException("Out of range.  " + w);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 101 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 106 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 111 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 116 */     return (T)new FixedMatrix4_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 121 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix4_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */