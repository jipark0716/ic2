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
/*     */ public class FixedMatrix5x5_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a11;
/*     */   public double a12;
/*     */   public double a13;
/*     */   public double a14;
/*     */   public double a15;
/*     */   public double a21;
/*     */   public double a22;
/*     */   public double a23;
/*     */   public double a24;
/*     */   public double a25;
/*     */   public double a31;
/*     */   public double a32;
/*     */   public double a33;
/*     */   public double a34;
/*     */   public double a35;
/*     */   public double a41;
/*     */   public double a42;
/*     */   public double a43;
/*     */   public double a44;
/*     */   public double a45;
/*     */   public double a51;
/*     */   public double a52;
/*     */   public double a53;
/*     */   public double a54;
/*     */   public double a55;
/*     */   
/*     */   public FixedMatrix5x5_64F() {}
/*     */   
/*     */   public FixedMatrix5x5_64F(double a11, double a12, double a13, double a14, double a15, double a21, double a22, double a23, double a24, double a25, double a31, double a32, double a33, double a34, double a35, double a41, double a42, double a43, double a44, double a45, double a51, double a52, double a53, double a54, double a55) {
/*  46 */     this.a11 = a11;
/*  47 */     this.a12 = a12;
/*  48 */     this.a13 = a13;
/*  49 */     this.a14 = a14;
/*  50 */     this.a15 = a15;
/*  51 */     this.a21 = a21;
/*  52 */     this.a22 = a22;
/*  53 */     this.a23 = a23;
/*  54 */     this.a24 = a24;
/*  55 */     this.a25 = a25;
/*  56 */     this.a31 = a31;
/*  57 */     this.a32 = a32;
/*  58 */     this.a33 = a33;
/*  59 */     this.a34 = a34;
/*  60 */     this.a35 = a35;
/*  61 */     this.a41 = a41;
/*  62 */     this.a42 = a42;
/*  63 */     this.a43 = a43;
/*  64 */     this.a44 = a44;
/*  65 */     this.a45 = a45;
/*  66 */     this.a51 = a51;
/*  67 */     this.a52 = a52;
/*  68 */     this.a53 = a53;
/*  69 */     this.a54 = a54;
/*  70 */     this.a55 = a55;
/*     */   }
/*     */   
/*     */   public FixedMatrix5x5_64F(FixedMatrix5x5_64F o) {
/*  74 */     this.a11 = o.a11;
/*  75 */     this.a12 = o.a12;
/*  76 */     this.a13 = o.a13;
/*  77 */     this.a14 = o.a14;
/*  78 */     this.a15 = o.a15;
/*  79 */     this.a21 = o.a21;
/*  80 */     this.a22 = o.a22;
/*  81 */     this.a23 = o.a23;
/*  82 */     this.a24 = o.a24;
/*  83 */     this.a25 = o.a25;
/*  84 */     this.a31 = o.a31;
/*  85 */     this.a32 = o.a32;
/*  86 */     this.a33 = o.a33;
/*  87 */     this.a34 = o.a34;
/*  88 */     this.a35 = o.a35;
/*  89 */     this.a41 = o.a41;
/*  90 */     this.a42 = o.a42;
/*  91 */     this.a43 = o.a43;
/*  92 */     this.a44 = o.a44;
/*  93 */     this.a45 = o.a45;
/*  94 */     this.a51 = o.a51;
/*  95 */     this.a52 = o.a52;
/*  96 */     this.a53 = o.a53;
/*  97 */     this.a54 = o.a54;
/*  98 */     this.a55 = o.a55;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/* 103 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/* 108 */     if (row == 0) {
/* 109 */       if (col == 0)
/* 110 */         return this.a11; 
/* 111 */       if (col == 1)
/* 112 */         return this.a12; 
/* 113 */       if (col == 2)
/* 114 */         return this.a13; 
/* 115 */       if (col == 3)
/* 116 */         return this.a14; 
/* 117 */       if (col == 4) {
/* 118 */         return this.a15;
/*     */       }
/* 120 */     } else if (row == 1) {
/* 121 */       if (col == 0)
/* 122 */         return this.a21; 
/* 123 */       if (col == 1)
/* 124 */         return this.a22; 
/* 125 */       if (col == 2)
/* 126 */         return this.a23; 
/* 127 */       if (col == 3)
/* 128 */         return this.a24; 
/* 129 */       if (col == 4) {
/* 130 */         return this.a25;
/*     */       }
/* 132 */     } else if (row == 2) {
/* 133 */       if (col == 0)
/* 134 */         return this.a31; 
/* 135 */       if (col == 1)
/* 136 */         return this.a32; 
/* 137 */       if (col == 2)
/* 138 */         return this.a33; 
/* 139 */       if (col == 3)
/* 140 */         return this.a34; 
/* 141 */       if (col == 4) {
/* 142 */         return this.a35;
/*     */       }
/* 144 */     } else if (row == 3) {
/* 145 */       if (col == 0)
/* 146 */         return this.a41; 
/* 147 */       if (col == 1)
/* 148 */         return this.a42; 
/* 149 */       if (col == 2)
/* 150 */         return this.a43; 
/* 151 */       if (col == 3)
/* 152 */         return this.a44; 
/* 153 */       if (col == 4) {
/* 154 */         return this.a45;
/*     */       }
/* 156 */     } else if (row == 4) {
/* 157 */       if (col == 0)
/* 158 */         return this.a51; 
/* 159 */       if (col == 1)
/* 160 */         return this.a52; 
/* 161 */       if (col == 2)
/* 162 */         return this.a53; 
/* 163 */       if (col == 3)
/* 164 */         return this.a54; 
/* 165 */       if (col == 4) {
/* 166 */         return this.a55;
/*     */       }
/*     */     } 
/* 169 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/* 174 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/* 179 */     if (row == 0) {
/* 180 */       if (col == 0) {
/* 181 */         this.a11 = val; return;
/* 182 */       }  if (col == 1) {
/* 183 */         this.a12 = val; return;
/* 184 */       }  if (col == 2) {
/* 185 */         this.a13 = val; return;
/* 186 */       }  if (col == 3) {
/* 187 */         this.a14 = val; return;
/* 188 */       }  if (col == 4) {
/* 189 */         this.a15 = val; return;
/*     */       } 
/* 191 */     } else if (row == 1) {
/* 192 */       if (col == 0) {
/* 193 */         this.a21 = val; return;
/* 194 */       }  if (col == 1) {
/* 195 */         this.a22 = val; return;
/* 196 */       }  if (col == 2) {
/* 197 */         this.a23 = val; return;
/* 198 */       }  if (col == 3) {
/* 199 */         this.a24 = val; return;
/* 200 */       }  if (col == 4) {
/* 201 */         this.a25 = val; return;
/*     */       } 
/* 203 */     } else if (row == 2) {
/* 204 */       if (col == 0) {
/* 205 */         this.a31 = val; return;
/* 206 */       }  if (col == 1) {
/* 207 */         this.a32 = val; return;
/* 208 */       }  if (col == 2) {
/* 209 */         this.a33 = val; return;
/* 210 */       }  if (col == 3) {
/* 211 */         this.a34 = val; return;
/* 212 */       }  if (col == 4) {
/* 213 */         this.a35 = val; return;
/*     */       } 
/* 215 */     } else if (row == 3) {
/* 216 */       if (col == 0) {
/* 217 */         this.a41 = val; return;
/* 218 */       }  if (col == 1) {
/* 219 */         this.a42 = val; return;
/* 220 */       }  if (col == 2) {
/* 221 */         this.a43 = val; return;
/* 222 */       }  if (col == 3) {
/* 223 */         this.a44 = val; return;
/* 224 */       }  if (col == 4) {
/* 225 */         this.a45 = val; return;
/*     */       } 
/* 227 */     } else if (row == 4) {
/* 228 */       if (col == 0) {
/* 229 */         this.a51 = val; return;
/* 230 */       }  if (col == 1) {
/* 231 */         this.a52 = val; return;
/* 232 */       }  if (col == 2) {
/* 233 */         this.a53 = val; return;
/* 234 */       }  if (col == 3) {
/* 235 */         this.a54 = val; return;
/* 236 */       }  if (col == 4) {
/* 237 */         this.a55 = val; return;
/*     */       } 
/*     */     } 
/* 240 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 245 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 250 */     return 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 255 */     return 25;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 260 */     return (T)new FixedMatrix5x5_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 265 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix5x5_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */