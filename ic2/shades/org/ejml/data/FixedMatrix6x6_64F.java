/*     */ package ic2.shades.org.ejml.data;
/*     */ 
/*     */ import ic2.shades.org.ejml.ops.MatrixIO;
/*     */ 
/*     */ public class FixedMatrix6x6_64F
/*     */   implements FixedMatrix64F
/*     */ {
/*     */   public double a11;
/*     */   public double a12;
/*     */   public double a13;
/*     */   public double a14;
/*     */   public double a15;
/*     */   public double a16;
/*     */   public double a21;
/*     */   public double a22;
/*     */   public double a23;
/*     */   public double a24;
/*     */   public double a25;
/*     */   public double a26;
/*     */   public double a31;
/*     */   public double a32;
/*     */   public double a33;
/*     */   public double a34;
/*     */   public double a35;
/*     */   public double a36;
/*     */   public double a41;
/*     */   public double a42;
/*     */   public double a43;
/*     */   public double a44;
/*     */   public double a45;
/*     */   public double a46;
/*     */   public double a51;
/*     */   public double a52;
/*     */   public double a53;
/*     */   public double a54;
/*     */   public double a55;
/*     */   public double a56;
/*     */   public double a61;
/*     */   public double a62;
/*     */   public double a63;
/*     */   public double a64;
/*     */   public double a65;
/*     */   public double a66;
/*     */   
/*     */   public FixedMatrix6x6_64F() {}
/*     */   
/*     */   public FixedMatrix6x6_64F(double a11, double a12, double a13, double a14, double a15, double a16, double a21, double a22, double a23, double a24, double a25, double a26, double a31, double a32, double a33, double a34, double a35, double a36, double a41, double a42, double a43, double a44, double a45, double a46, double a51, double a52, double a53, double a54, double a55, double a56, double a61, double a62, double a63, double a64, double a65, double a66) {
/*  48 */     this.a11 = a11;
/*  49 */     this.a12 = a12;
/*  50 */     this.a13 = a13;
/*  51 */     this.a14 = a14;
/*  52 */     this.a15 = a15;
/*  53 */     this.a16 = a16;
/*  54 */     this.a21 = a21;
/*  55 */     this.a22 = a22;
/*  56 */     this.a23 = a23;
/*  57 */     this.a24 = a24;
/*  58 */     this.a25 = a25;
/*  59 */     this.a26 = a26;
/*  60 */     this.a31 = a31;
/*  61 */     this.a32 = a32;
/*  62 */     this.a33 = a33;
/*  63 */     this.a34 = a34;
/*  64 */     this.a35 = a35;
/*  65 */     this.a36 = a36;
/*  66 */     this.a41 = a41;
/*  67 */     this.a42 = a42;
/*  68 */     this.a43 = a43;
/*  69 */     this.a44 = a44;
/*  70 */     this.a45 = a45;
/*  71 */     this.a46 = a46;
/*  72 */     this.a51 = a51;
/*  73 */     this.a52 = a52;
/*  74 */     this.a53 = a53;
/*  75 */     this.a54 = a54;
/*  76 */     this.a55 = a55;
/*  77 */     this.a56 = a56;
/*  78 */     this.a61 = a61;
/*  79 */     this.a62 = a62;
/*  80 */     this.a63 = a63;
/*  81 */     this.a64 = a64;
/*  82 */     this.a65 = a65;
/*  83 */     this.a66 = a66;
/*     */   }
/*     */   
/*     */   public FixedMatrix6x6_64F(FixedMatrix6x6_64F o) {
/*  87 */     this.a11 = o.a11;
/*  88 */     this.a12 = o.a12;
/*  89 */     this.a13 = o.a13;
/*  90 */     this.a14 = o.a14;
/*  91 */     this.a15 = o.a15;
/*  92 */     this.a16 = o.a16;
/*  93 */     this.a21 = o.a21;
/*  94 */     this.a22 = o.a22;
/*  95 */     this.a23 = o.a23;
/*  96 */     this.a24 = o.a24;
/*  97 */     this.a25 = o.a25;
/*  98 */     this.a26 = o.a26;
/*  99 */     this.a31 = o.a31;
/* 100 */     this.a32 = o.a32;
/* 101 */     this.a33 = o.a33;
/* 102 */     this.a34 = o.a34;
/* 103 */     this.a35 = o.a35;
/* 104 */     this.a36 = o.a36;
/* 105 */     this.a41 = o.a41;
/* 106 */     this.a42 = o.a42;
/* 107 */     this.a43 = o.a43;
/* 108 */     this.a44 = o.a44;
/* 109 */     this.a45 = o.a45;
/* 110 */     this.a46 = o.a46;
/* 111 */     this.a51 = o.a51;
/* 112 */     this.a52 = o.a52;
/* 113 */     this.a53 = o.a53;
/* 114 */     this.a54 = o.a54;
/* 115 */     this.a55 = o.a55;
/* 116 */     this.a56 = o.a56;
/* 117 */     this.a61 = o.a61;
/* 118 */     this.a62 = o.a62;
/* 119 */     this.a63 = o.a63;
/* 120 */     this.a64 = o.a64;
/* 121 */     this.a65 = o.a65;
/* 122 */     this.a66 = o.a66;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int row, int col) {
/* 127 */     return unsafe_get(row, col);
/*     */   }
/*     */ 
/*     */   
/*     */   public double unsafe_get(int row, int col) {
/* 132 */     if (row == 0) {
/* 133 */       if (col == 0)
/* 134 */         return this.a11; 
/* 135 */       if (col == 1)
/* 136 */         return this.a12; 
/* 137 */       if (col == 2)
/* 138 */         return this.a13; 
/* 139 */       if (col == 3)
/* 140 */         return this.a14; 
/* 141 */       if (col == 4)
/* 142 */         return this.a15; 
/* 143 */       if (col == 5) {
/* 144 */         return this.a16;
/*     */       }
/* 146 */     } else if (row == 1) {
/* 147 */       if (col == 0)
/* 148 */         return this.a21; 
/* 149 */       if (col == 1)
/* 150 */         return this.a22; 
/* 151 */       if (col == 2)
/* 152 */         return this.a23; 
/* 153 */       if (col == 3)
/* 154 */         return this.a24; 
/* 155 */       if (col == 4)
/* 156 */         return this.a25; 
/* 157 */       if (col == 5) {
/* 158 */         return this.a26;
/*     */       }
/* 160 */     } else if (row == 2) {
/* 161 */       if (col == 0)
/* 162 */         return this.a31; 
/* 163 */       if (col == 1)
/* 164 */         return this.a32; 
/* 165 */       if (col == 2)
/* 166 */         return this.a33; 
/* 167 */       if (col == 3)
/* 168 */         return this.a34; 
/* 169 */       if (col == 4)
/* 170 */         return this.a35; 
/* 171 */       if (col == 5) {
/* 172 */         return this.a36;
/*     */       }
/* 174 */     } else if (row == 3) {
/* 175 */       if (col == 0)
/* 176 */         return this.a41; 
/* 177 */       if (col == 1)
/* 178 */         return this.a42; 
/* 179 */       if (col == 2)
/* 180 */         return this.a43; 
/* 181 */       if (col == 3)
/* 182 */         return this.a44; 
/* 183 */       if (col == 4)
/* 184 */         return this.a45; 
/* 185 */       if (col == 5) {
/* 186 */         return this.a46;
/*     */       }
/* 188 */     } else if (row == 4) {
/* 189 */       if (col == 0)
/* 190 */         return this.a51; 
/* 191 */       if (col == 1)
/* 192 */         return this.a52; 
/* 193 */       if (col == 2)
/* 194 */         return this.a53; 
/* 195 */       if (col == 3)
/* 196 */         return this.a54; 
/* 197 */       if (col == 4)
/* 198 */         return this.a55; 
/* 199 */       if (col == 5) {
/* 200 */         return this.a56;
/*     */       }
/* 202 */     } else if (row == 5) {
/* 203 */       if (col == 0)
/* 204 */         return this.a61; 
/* 205 */       if (col == 1)
/* 206 */         return this.a62; 
/* 207 */       if (col == 2)
/* 208 */         return this.a63; 
/* 209 */       if (col == 3)
/* 210 */         return this.a64; 
/* 211 */       if (col == 4)
/* 212 */         return this.a65; 
/* 213 */       if (col == 5) {
/* 214 */         return this.a66;
/*     */       }
/*     */     } 
/* 217 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int row, int col, double val) {
/* 222 */     unsafe_set(row, col, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsafe_set(int row, int col, double val) {
/* 227 */     if (row == 0) {
/* 228 */       if (col == 0) {
/* 229 */         this.a11 = val; return;
/* 230 */       }  if (col == 1) {
/* 231 */         this.a12 = val; return;
/* 232 */       }  if (col == 2) {
/* 233 */         this.a13 = val; return;
/* 234 */       }  if (col == 3) {
/* 235 */         this.a14 = val; return;
/* 236 */       }  if (col == 4) {
/* 237 */         this.a15 = val; return;
/* 238 */       }  if (col == 5) {
/* 239 */         this.a16 = val; return;
/*     */       } 
/* 241 */     } else if (row == 1) {
/* 242 */       if (col == 0) {
/* 243 */         this.a21 = val; return;
/* 244 */       }  if (col == 1) {
/* 245 */         this.a22 = val; return;
/* 246 */       }  if (col == 2) {
/* 247 */         this.a23 = val; return;
/* 248 */       }  if (col == 3) {
/* 249 */         this.a24 = val; return;
/* 250 */       }  if (col == 4) {
/* 251 */         this.a25 = val; return;
/* 252 */       }  if (col == 5) {
/* 253 */         this.a26 = val; return;
/*     */       } 
/* 255 */     } else if (row == 2) {
/* 256 */       if (col == 0) {
/* 257 */         this.a31 = val; return;
/* 258 */       }  if (col == 1) {
/* 259 */         this.a32 = val; return;
/* 260 */       }  if (col == 2) {
/* 261 */         this.a33 = val; return;
/* 262 */       }  if (col == 3) {
/* 263 */         this.a34 = val; return;
/* 264 */       }  if (col == 4) {
/* 265 */         this.a35 = val; return;
/* 266 */       }  if (col == 5) {
/* 267 */         this.a36 = val; return;
/*     */       } 
/* 269 */     } else if (row == 3) {
/* 270 */       if (col == 0) {
/* 271 */         this.a41 = val; return;
/* 272 */       }  if (col == 1) {
/* 273 */         this.a42 = val; return;
/* 274 */       }  if (col == 2) {
/* 275 */         this.a43 = val; return;
/* 276 */       }  if (col == 3) {
/* 277 */         this.a44 = val; return;
/* 278 */       }  if (col == 4) {
/* 279 */         this.a45 = val; return;
/* 280 */       }  if (col == 5) {
/* 281 */         this.a46 = val; return;
/*     */       } 
/* 283 */     } else if (row == 4) {
/* 284 */       if (col == 0) {
/* 285 */         this.a51 = val; return;
/* 286 */       }  if (col == 1) {
/* 287 */         this.a52 = val; return;
/* 288 */       }  if (col == 2) {
/* 289 */         this.a53 = val; return;
/* 290 */       }  if (col == 3) {
/* 291 */         this.a54 = val; return;
/* 292 */       }  if (col == 4) {
/* 293 */         this.a55 = val; return;
/* 294 */       }  if (col == 5) {
/* 295 */         this.a56 = val; return;
/*     */       } 
/* 297 */     } else if (row == 5) {
/* 298 */       if (col == 0) {
/* 299 */         this.a61 = val; return;
/* 300 */       }  if (col == 1) {
/* 301 */         this.a62 = val; return;
/* 302 */       }  if (col == 2) {
/* 303 */         this.a63 = val; return;
/* 304 */       }  if (col == 3) {
/* 305 */         this.a64 = val; return;
/* 306 */       }  if (col == 4) {
/* 307 */         this.a65 = val; return;
/* 308 */       }  if (col == 5) {
/* 309 */         this.a66 = val; return;
/*     */       } 
/*     */     } 
/* 312 */     throw new IllegalArgumentException("Row and/or column out of range. " + row + " " + col);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/* 317 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/* 322 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/* 327 */     return 36;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Matrix64F> T copy() {
/* 332 */     return (T)new FixedMatrix6x6_64F(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void print() {
/* 337 */     MatrixIO.print(System.out, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\FixedMatrix6x6_64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */