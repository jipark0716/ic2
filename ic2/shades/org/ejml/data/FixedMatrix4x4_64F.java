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
/*     */ public class FixedMatrix4x4_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a11;
/*     */   public double a12;
/*     */   public double a13;
/*     */   public double a14;
/*     */   public double a21;
/*     */   public double a22;
/*     */   public double a23;
/*     */   public double a24;
/*     */   public double a31;
/*     */   public double a32;
/*     */   public double a33;
/*     */   public double a34;
/*     */   public double a41;
/*     */   public double a42;
/*     */   public double a43;
/*     */   public double a44;
/*     */   
/*     */   public FixedMatrix4x4_64F() {}
/*     */   
/*     */   public FixedMatrix4x4_64F(double a11, double a12, double a13, double a14, double a21, double a22, double a23, double a24, double a31, double a32, double a33, double a34, double a41, double a42, double a43, double a44) {
/*  44 */     this.a11 = a11;
/*  45 */     this.a12 = a12;
/*  46 */     this.a13 = a13;
/*  47 */     this.a14 = a14;
/*  48 */     this.a21 = a21;
/*  49 */     this.a22 = a22;
/*  50 */     this.a23 = a23;
/*  51 */     this.a24 = a24;
/*  52 */     this.a31 = a31;
/*  53 */     this.a32 = a32;
/*  54 */     this.a33 = a33;
/*  55 */     this.a34 = a34;
/*  56 */     this.a41 = a41;
/*  57 */     this.a42 = a42;
/*  58 */     this.a43 = a43;
/*  59 */     this.a44 = a44;
/*     */   }
/*     */   
/*     */   public FixedMatrix4x4_64F(FixedMatrix4x4_64F o) {
/*  63 */     this.a11 = o.a11;
/*  64 */     this.a12 = o.a12;
/*  65 */     this.a13 = o.a13;
/*  66 */     this.a14 = o.a14;
/*  67 */     this.a21 = o.a21;
/*  68 */     this.a22 = o.a22;
/*  69 */     this.a23 = o.a23;
/*  70 */     this.a24 = o.a24;
/*  71 */     this.a31 = o.a31;
/*  72 */     this.a32 = o.a32;
/*  73 */     this.a33 = o.a33;
/*  74 */     this.a34 = o.a34;
/*  75 */     this.a41 = o.a41;
/*  76 */     this.a42 = o.a42;
/*  77 */     this.a43 = o.a43;
/*  78 */     this.a44 = o.a44;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/*  83 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/*  88 */     if (row == 0) {
/*  89 */       if (col == 0)
/*  90 */         return this.a11; 
/*  91 */       if (col == 1)
/*  92 */         return this.a12; 
/*  93 */       if (col == 2)
/*  94 */         return this.a13; 
/*  95 */       if (col == 3) {
/*  96 */         return this.a14;
/*     */       }
/*  98 */     } else if (row == 1) {
/*  99 */       if (col == 0)
/* 100 */         return this.a21; 
/* 101 */       if (col == 1)
/* 102 */         return this.a22; 
/* 103 */       if (col == 2)
/* 104 */         return this.a23; 
/* 105 */       if (col == 3) {
/* 106 */         return this.a24;
/*     */       }
/* 108 */     } else if (row == 2) {
/* 109 */       if (col == 0)
/* 110 */         return this.a31; 
/* 111 */       if (col == 1)
/* 112 */         return this.a32; 
/* 113 */       if (col == 2)
/* 114 */         return this.a33; 
/* 115 */       if (col == 3) {
/* 116 */         return this.a34;
/*     */       }
/* 118 */     } else if (row == 3) {
/* 119 */       if (col == 0)
/* 120 */         return this.a41; 
/* 121 */       if (col == 1)
/* 122 */         return this.a42; 
/* 123 */       if (col == 2)
/* 124 */         return this.a43; 
/* 125 */       if (col == 3) {
/* 126 */         return this.a44;
/*     */       }
/*     */     } 
/* 129 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/* 134 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/* 139 */     if (row == 0) {
/* 140 */       if (col == 0) {
/* 141 */         this.a11 = val; return;
/* 142 */       }  if (col == 1) {
/* 143 */         this.a12 = val; return;
/* 144 */       }  if (col == 2) {
/* 145 */         this.a13 = val; return;
/* 146 */       }  if (col == 3) {
/* 147 */         this.a14 = val; return;
/*     */       } 
/* 149 */     } else if (row == 1) {
/* 150 */       if (col == 0) {
/* 151 */         this.a21 = val; return;
/* 152 */       }  if (col == 1) {
/* 153 */         this.a22 = val; return;
/* 154 */       }  if (col == 2) {
/* 155 */         this.a23 = val; return;
/* 156 */       }  if (col == 3) {
/* 157 */         this.a24 = val; return;
/*     */       } 
/* 159 */     } else if (row == 2) {
/* 160 */       if (col == 0) {
/* 161 */         this.a31 = val; return;
/* 162 */       }  if (col == 1) {
/* 163 */         this.a32 = val; return;
/* 164 */       }  if (col == 2) {
/* 165 */         this.a33 = val; return;
/* 166 */       }  if (col == 3) {
/* 167 */         this.a34 = val; return;
/*     */       } 
/* 169 */     } else if (row == 3) {
/* 170 */       if (col == 0) {
/* 171 */         this.a41 = val; return;
/* 172 */       }  if (col == 1) {
/* 173 */         this.a42 = val; return;
/* 174 */       }  if (col == 2) {
/* 175 */         this.a43 = val; return;
/* 176 */       }  if (col == 3) {
/* 177 */         this.a44 = val; return;
/*     */       } 
/*     */     } 
/* 180 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 185 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 190 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 195 */     return 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 200 */     return (T)new FixedMatrix4x4_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 205 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix4x4_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */