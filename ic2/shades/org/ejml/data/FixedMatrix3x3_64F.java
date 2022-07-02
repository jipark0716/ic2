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
/*     */ public class FixedMatrix3x3_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a11;
/*     */   public double a12;
/*     */   public double a13;
/*     */   public double a21;
/*     */   public double a22;
/*     */   public double a23;
/*     */   public double a31;
/*     */   public double a32;
/*     */   public double a33;
/*     */   
/*     */   public FixedMatrix3x3_64F() {}
/*     */   
/*     */   public FixedMatrix3x3_64F(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33) {
/*  42 */     this.a11 = a11;
/*  43 */     this.a12 = a12;
/*  44 */     this.a13 = a13;
/*  45 */     this.a21 = a21;
/*  46 */     this.a22 = a22;
/*  47 */     this.a23 = a23;
/*  48 */     this.a31 = a31;
/*  49 */     this.a32 = a32;
/*  50 */     this.a33 = a33;
/*     */   }
/*     */   
/*     */   public FixedMatrix3x3_64F(FixedMatrix3x3_64F o) {
/*  54 */     this.a11 = o.a11;
/*  55 */     this.a12 = o.a12;
/*  56 */     this.a13 = o.a13;
/*  57 */     this.a21 = o.a21;
/*  58 */     this.a22 = o.a22;
/*  59 */     this.a23 = o.a23;
/*  60 */     this.a31 = o.a31;
/*  61 */     this.a32 = o.a32;
/*  62 */     this.a33 = o.a33;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  67 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  72 */     if (row == 0) {
/*  73 */       if (col == 0)
/*  74 */         return this.a11; 
/*  75 */       if (col == 1)
/*  76 */         return this.a12; 
/*  77 */       if (col == 2) {
/*  78 */         return this.a13;
/*     */       }
/*  80 */     } else if (row == 1) {
/*  81 */       if (col == 0)
/*  82 */         return this.a21; 
/*  83 */       if (col == 1)
/*  84 */         return this.a22; 
/*  85 */       if (col == 2) {
/*  86 */         return this.a23;
/*     */       }
/*  88 */     } else if (row == 2) {
/*  89 */       if (col == 0)
/*  90 */         return this.a31; 
/*  91 */       if (col == 1)
/*  92 */         return this.a32; 
/*  93 */       if (col == 2) {
/*  94 */         return this.a33;
/*     */       }
/*     */     } 
/*  97 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/* 102 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/* 107 */     if (row == 0) {
/* 108 */       if (col == 0) {
/* 109 */         this.a11 = val; return;
/* 110 */       }  if (col == 1) {
/* 111 */         this.a12 = val; return;
/* 112 */       }  if (col == 2) {
/* 113 */         this.a13 = val; return;
/*     */       } 
/* 115 */     } else if (row == 1) {
/* 116 */       if (col == 0) {
/* 117 */         this.a21 = val; return;
/* 118 */       }  if (col == 1) {
/* 119 */         this.a22 = val; return;
/* 120 */       }  if (col == 2) {
/* 121 */         this.a23 = val; return;
/*     */       } 
/* 123 */     } else if (row == 2) {
/* 124 */       if (col == 0) {
/* 125 */         this.a31 = val; return;
/* 126 */       }  if (col == 1) {
/* 127 */         this.a32 = val; return;
/* 128 */       }  if (col == 2) {
/* 129 */         this.a33 = val; return;
/*     */       } 
/*     */     } 
/* 132 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 137 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 142 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 147 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 152 */     return (T)new FixedMatrix3x3_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 157 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix3x3_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */