/*     */ package ic2.shades.org.ejml.alg.fixed;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.FixedMatrix5_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix5x5_64F;
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
/*     */ public class FixedOps5
/*     */ {
/*     */   public static void add(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/*  46 */     a.a11 += b.a11;
/*  47 */     a.a12 += b.a12;
/*  48 */     a.a13 += b.a13;
/*  49 */     a.a14 += b.a14;
/*  50 */     a.a15 += b.a15;
/*  51 */     a.a21 += b.a21;
/*  52 */     a.a22 += b.a22;
/*  53 */     a.a23 += b.a23;
/*  54 */     a.a24 += b.a24;
/*  55 */     a.a25 += b.a25;
/*  56 */     a.a31 += b.a31;
/*  57 */     a.a32 += b.a32;
/*  58 */     a.a33 += b.a33;
/*  59 */     a.a34 += b.a34;
/*  60 */     a.a35 += b.a35;
/*  61 */     a.a41 += b.a41;
/*  62 */     a.a42 += b.a42;
/*  63 */     a.a43 += b.a43;
/*  64 */     a.a44 += b.a44;
/*  65 */     a.a45 += b.a45;
/*  66 */     a.a51 += b.a51;
/*  67 */     a.a52 += b.a52;
/*  68 */     a.a53 += b.a53;
/*  69 */     a.a54 += b.a54;
/*  70 */     a.a55 += b.a55;
/*     */   }
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
/*     */   public static void addEquals(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b) {
/*  84 */     a.a11 += b.a11;
/*  85 */     a.a12 += b.a12;
/*  86 */     a.a13 += b.a13;
/*  87 */     a.a14 += b.a14;
/*  88 */     a.a15 += b.a15;
/*  89 */     a.a21 += b.a21;
/*  90 */     a.a22 += b.a22;
/*  91 */     a.a23 += b.a23;
/*  92 */     a.a24 += b.a24;
/*  93 */     a.a25 += b.a25;
/*  94 */     a.a31 += b.a31;
/*  95 */     a.a32 += b.a32;
/*  96 */     a.a33 += b.a33;
/*  97 */     a.a34 += b.a34;
/*  98 */     a.a35 += b.a35;
/*  99 */     a.a41 += b.a41;
/* 100 */     a.a42 += b.a42;
/* 101 */     a.a43 += b.a43;
/* 102 */     a.a44 += b.a44;
/* 103 */     a.a45 += b.a45;
/* 104 */     a.a51 += b.a51;
/* 105 */     a.a52 += b.a52;
/* 106 */     a.a53 += b.a53;
/* 107 */     a.a54 += b.a54;
/* 108 */     a.a55 += b.a55;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void transpose(FixedMatrix5x5_64F m) {
/* 119 */     double tmp = m.a12; m.a12 = m.a21; m.a21 = tmp;
/* 120 */     tmp = m.a13; m.a13 = m.a31; m.a31 = tmp;
/* 121 */     tmp = m.a14; m.a14 = m.a41; m.a41 = tmp;
/* 122 */     tmp = m.a15; m.a15 = m.a51; m.a51 = tmp;
/* 123 */     tmp = m.a23; m.a23 = m.a32; m.a32 = tmp;
/* 124 */     tmp = m.a24; m.a24 = m.a42; m.a42 = tmp;
/* 125 */     tmp = m.a25; m.a25 = m.a52; m.a52 = tmp;
/* 126 */     tmp = m.a34; m.a34 = m.a43; m.a43 = tmp;
/* 127 */     tmp = m.a35; m.a35 = m.a53; m.a53 = tmp;
/* 128 */     tmp = m.a45; m.a45 = m.a54; m.a54 = tmp;
/*     */   }
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
/*     */   public static FixedMatrix5x5_64F transpose(FixedMatrix5x5_64F input, FixedMatrix5x5_64F output) {
/* 144 */     if (input == null) {
/* 145 */       input = new FixedMatrix5x5_64F();
/*     */     }
/* 147 */     output.a11 = input.a11;
/* 148 */     output.a12 = input.a21;
/* 149 */     output.a13 = input.a31;
/* 150 */     output.a14 = input.a41;
/* 151 */     output.a15 = input.a51;
/* 152 */     output.a21 = input.a12;
/* 153 */     output.a22 = input.a22;
/* 154 */     output.a23 = input.a32;
/* 155 */     output.a24 = input.a42;
/* 156 */     output.a25 = input.a52;
/* 157 */     output.a31 = input.a13;
/* 158 */     output.a32 = input.a23;
/* 159 */     output.a33 = input.a33;
/* 160 */     output.a34 = input.a43;
/* 161 */     output.a35 = input.a53;
/* 162 */     output.a41 = input.a14;
/* 163 */     output.a42 = input.a24;
/* 164 */     output.a43 = input.a34;
/* 165 */     output.a44 = input.a44;
/* 166 */     output.a45 = input.a54;
/* 167 */     output.a51 = input.a15;
/* 168 */     output.a52 = input.a25;
/* 169 */     output.a53 = input.a35;
/* 170 */     output.a54 = input.a45;
/* 171 */     output.a55 = input.a55;
/*     */     
/* 173 */     return output;
/*     */   }
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
/*     */   public static void mult(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/* 189 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31 + a.a14 * b.a41 + a.a15 * b.a51;
/* 190 */     c.a12 = a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32 + a.a14 * b.a42 + a.a15 * b.a52;
/* 191 */     c.a13 = a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33 + a.a14 * b.a43 + a.a15 * b.a53;
/* 192 */     c.a14 = a.a11 * b.a14 + a.a12 * b.a24 + a.a13 * b.a34 + a.a14 * b.a44 + a.a15 * b.a54;
/* 193 */     c.a15 = a.a11 * b.a15 + a.a12 * b.a25 + a.a13 * b.a35 + a.a14 * b.a45 + a.a15 * b.a55;
/* 194 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31 + a.a24 * b.a41 + a.a25 * b.a51;
/* 195 */     c.a22 = a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32 + a.a24 * b.a42 + a.a25 * b.a52;
/* 196 */     c.a23 = a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33 + a.a24 * b.a43 + a.a25 * b.a53;
/* 197 */     c.a24 = a.a21 * b.a14 + a.a22 * b.a24 + a.a23 * b.a34 + a.a24 * b.a44 + a.a25 * b.a54;
/* 198 */     c.a25 = a.a21 * b.a15 + a.a22 * b.a25 + a.a23 * b.a35 + a.a24 * b.a45 + a.a25 * b.a55;
/* 199 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31 + a.a34 * b.a41 + a.a35 * b.a51;
/* 200 */     c.a32 = a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32 + a.a34 * b.a42 + a.a35 * b.a52;
/* 201 */     c.a33 = a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33 + a.a34 * b.a43 + a.a35 * b.a53;
/* 202 */     c.a34 = a.a31 * b.a14 + a.a32 * b.a24 + a.a33 * b.a34 + a.a34 * b.a44 + a.a35 * b.a54;
/* 203 */     c.a35 = a.a31 * b.a15 + a.a32 * b.a25 + a.a33 * b.a35 + a.a34 * b.a45 + a.a35 * b.a55;
/* 204 */     c.a41 = a.a41 * b.a11 + a.a42 * b.a21 + a.a43 * b.a31 + a.a44 * b.a41 + a.a45 * b.a51;
/* 205 */     c.a42 = a.a41 * b.a12 + a.a42 * b.a22 + a.a43 * b.a32 + a.a44 * b.a42 + a.a45 * b.a52;
/* 206 */     c.a43 = a.a41 * b.a13 + a.a42 * b.a23 + a.a43 * b.a33 + a.a44 * b.a43 + a.a45 * b.a53;
/* 207 */     c.a44 = a.a41 * b.a14 + a.a42 * b.a24 + a.a43 * b.a34 + a.a44 * b.a44 + a.a45 * b.a54;
/* 208 */     c.a45 = a.a41 * b.a15 + a.a42 * b.a25 + a.a43 * b.a35 + a.a44 * b.a45 + a.a45 * b.a55;
/* 209 */     c.a51 = a.a51 * b.a11 + a.a52 * b.a21 + a.a53 * b.a31 + a.a54 * b.a41 + a.a55 * b.a51;
/* 210 */     c.a52 = a.a51 * b.a12 + a.a52 * b.a22 + a.a53 * b.a32 + a.a54 * b.a42 + a.a55 * b.a52;
/* 211 */     c.a53 = a.a51 * b.a13 + a.a52 * b.a23 + a.a53 * b.a33 + a.a54 * b.a43 + a.a55 * b.a53;
/* 212 */     c.a54 = a.a51 * b.a14 + a.a52 * b.a24 + a.a53 * b.a34 + a.a54 * b.a44 + a.a55 * b.a54;
/* 213 */     c.a55 = a.a51 * b.a15 + a.a52 * b.a25 + a.a53 * b.a35 + a.a54 * b.a45 + a.a55 * b.a55;
/*     */   }
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
/*     */   public static void multTransA(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/* 229 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31 + a.a41 * b.a41 + a.a51 * b.a51;
/* 230 */     c.a12 = a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32 + a.a41 * b.a42 + a.a51 * b.a52;
/* 231 */     c.a13 = a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33 + a.a41 * b.a43 + a.a51 * b.a53;
/* 232 */     c.a14 = a.a11 * b.a14 + a.a21 * b.a24 + a.a31 * b.a34 + a.a41 * b.a44 + a.a51 * b.a54;
/* 233 */     c.a15 = a.a11 * b.a15 + a.a21 * b.a25 + a.a31 * b.a35 + a.a41 * b.a45 + a.a51 * b.a55;
/* 234 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31 + a.a42 * b.a41 + a.a52 * b.a51;
/* 235 */     c.a22 = a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32 + a.a42 * b.a42 + a.a52 * b.a52;
/* 236 */     c.a23 = a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33 + a.a42 * b.a43 + a.a52 * b.a53;
/* 237 */     c.a24 = a.a12 * b.a14 + a.a22 * b.a24 + a.a32 * b.a34 + a.a42 * b.a44 + a.a52 * b.a54;
/* 238 */     c.a25 = a.a12 * b.a15 + a.a22 * b.a25 + a.a32 * b.a35 + a.a42 * b.a45 + a.a52 * b.a55;
/* 239 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31 + a.a43 * b.a41 + a.a53 * b.a51;
/* 240 */     c.a32 = a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32 + a.a43 * b.a42 + a.a53 * b.a52;
/* 241 */     c.a33 = a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33 + a.a43 * b.a43 + a.a53 * b.a53;
/* 242 */     c.a34 = a.a13 * b.a14 + a.a23 * b.a24 + a.a33 * b.a34 + a.a43 * b.a44 + a.a53 * b.a54;
/* 243 */     c.a35 = a.a13 * b.a15 + a.a23 * b.a25 + a.a33 * b.a35 + a.a43 * b.a45 + a.a53 * b.a55;
/* 244 */     c.a41 = a.a14 * b.a11 + a.a24 * b.a21 + a.a34 * b.a31 + a.a44 * b.a41 + a.a54 * b.a51;
/* 245 */     c.a42 = a.a14 * b.a12 + a.a24 * b.a22 + a.a34 * b.a32 + a.a44 * b.a42 + a.a54 * b.a52;
/* 246 */     c.a43 = a.a14 * b.a13 + a.a24 * b.a23 + a.a34 * b.a33 + a.a44 * b.a43 + a.a54 * b.a53;
/* 247 */     c.a44 = a.a14 * b.a14 + a.a24 * b.a24 + a.a34 * b.a34 + a.a44 * b.a44 + a.a54 * b.a54;
/* 248 */     c.a45 = a.a14 * b.a15 + a.a24 * b.a25 + a.a34 * b.a35 + a.a44 * b.a45 + a.a54 * b.a55;
/* 249 */     c.a51 = a.a15 * b.a11 + a.a25 * b.a21 + a.a35 * b.a31 + a.a45 * b.a41 + a.a55 * b.a51;
/* 250 */     c.a52 = a.a15 * b.a12 + a.a25 * b.a22 + a.a35 * b.a32 + a.a45 * b.a42 + a.a55 * b.a52;
/* 251 */     c.a53 = a.a15 * b.a13 + a.a25 * b.a23 + a.a35 * b.a33 + a.a45 * b.a43 + a.a55 * b.a53;
/* 252 */     c.a54 = a.a15 * b.a14 + a.a25 * b.a24 + a.a35 * b.a34 + a.a45 * b.a44 + a.a55 * b.a54;
/* 253 */     c.a55 = a.a15 * b.a15 + a.a25 * b.a25 + a.a35 * b.a35 + a.a45 * b.a45 + a.a55 * b.a55;
/*     */   }
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
/*     */   public static void multTransAB(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/* 269 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13 + a.a41 * b.a14 + a.a51 * b.a15;
/* 270 */     c.a12 = a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23 + a.a41 * b.a24 + a.a51 * b.a25;
/* 271 */     c.a13 = a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33 + a.a41 * b.a34 + a.a51 * b.a35;
/* 272 */     c.a14 = a.a11 * b.a41 + a.a21 * b.a42 + a.a31 * b.a43 + a.a41 * b.a44 + a.a51 * b.a45;
/* 273 */     c.a15 = a.a11 * b.a51 + a.a21 * b.a52 + a.a31 * b.a53 + a.a41 * b.a54 + a.a51 * b.a55;
/* 274 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13 + a.a42 * b.a14 + a.a52 * b.a15;
/* 275 */     c.a22 = a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23 + a.a42 * b.a24 + a.a52 * b.a25;
/* 276 */     c.a23 = a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33 + a.a42 * b.a34 + a.a52 * b.a35;
/* 277 */     c.a24 = a.a12 * b.a41 + a.a22 * b.a42 + a.a32 * b.a43 + a.a42 * b.a44 + a.a52 * b.a45;
/* 278 */     c.a25 = a.a12 * b.a51 + a.a22 * b.a52 + a.a32 * b.a53 + a.a42 * b.a54 + a.a52 * b.a55;
/* 279 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13 + a.a43 * b.a14 + a.a53 * b.a15;
/* 280 */     c.a32 = a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23 + a.a43 * b.a24 + a.a53 * b.a25;
/* 281 */     c.a33 = a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33 + a.a43 * b.a34 + a.a53 * b.a35;
/* 282 */     c.a34 = a.a13 * b.a41 + a.a23 * b.a42 + a.a33 * b.a43 + a.a43 * b.a44 + a.a53 * b.a45;
/* 283 */     c.a35 = a.a13 * b.a51 + a.a23 * b.a52 + a.a33 * b.a53 + a.a43 * b.a54 + a.a53 * b.a55;
/* 284 */     c.a41 = a.a14 * b.a11 + a.a24 * b.a12 + a.a34 * b.a13 + a.a44 * b.a14 + a.a54 * b.a15;
/* 285 */     c.a42 = a.a14 * b.a21 + a.a24 * b.a22 + a.a34 * b.a23 + a.a44 * b.a24 + a.a54 * b.a25;
/* 286 */     c.a43 = a.a14 * b.a31 + a.a24 * b.a32 + a.a34 * b.a33 + a.a44 * b.a34 + a.a54 * b.a35;
/* 287 */     c.a44 = a.a14 * b.a41 + a.a24 * b.a42 + a.a34 * b.a43 + a.a44 * b.a44 + a.a54 * b.a45;
/* 288 */     c.a45 = a.a14 * b.a51 + a.a24 * b.a52 + a.a34 * b.a53 + a.a44 * b.a54 + a.a54 * b.a55;
/* 289 */     c.a51 = a.a15 * b.a11 + a.a25 * b.a12 + a.a35 * b.a13 + a.a45 * b.a14 + a.a55 * b.a15;
/* 290 */     c.a52 = a.a15 * b.a21 + a.a25 * b.a22 + a.a35 * b.a23 + a.a45 * b.a24 + a.a55 * b.a25;
/* 291 */     c.a53 = a.a15 * b.a31 + a.a25 * b.a32 + a.a35 * b.a33 + a.a45 * b.a34 + a.a55 * b.a35;
/* 292 */     c.a54 = a.a15 * b.a41 + a.a25 * b.a42 + a.a35 * b.a43 + a.a45 * b.a44 + a.a55 * b.a45;
/* 293 */     c.a55 = a.a15 * b.a51 + a.a25 * b.a52 + a.a35 * b.a53 + a.a45 * b.a54 + a.a55 * b.a55;
/*     */   }
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
/*     */   public static void multTransB(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/* 309 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13 + a.a14 * b.a14 + a.a15 * b.a15;
/* 310 */     c.a12 = a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23 + a.a14 * b.a24 + a.a15 * b.a25;
/* 311 */     c.a13 = a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33 + a.a14 * b.a34 + a.a15 * b.a35;
/* 312 */     c.a14 = a.a11 * b.a41 + a.a12 * b.a42 + a.a13 * b.a43 + a.a14 * b.a44 + a.a15 * b.a45;
/* 313 */     c.a15 = a.a11 * b.a51 + a.a12 * b.a52 + a.a13 * b.a53 + a.a14 * b.a54 + a.a15 * b.a55;
/* 314 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13 + a.a24 * b.a14 + a.a25 * b.a15;
/* 315 */     c.a22 = a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23 + a.a24 * b.a24 + a.a25 * b.a25;
/* 316 */     c.a23 = a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33 + a.a24 * b.a34 + a.a25 * b.a35;
/* 317 */     c.a24 = a.a21 * b.a41 + a.a22 * b.a42 + a.a23 * b.a43 + a.a24 * b.a44 + a.a25 * b.a45;
/* 318 */     c.a25 = a.a21 * b.a51 + a.a22 * b.a52 + a.a23 * b.a53 + a.a24 * b.a54 + a.a25 * b.a55;
/* 319 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13 + a.a34 * b.a14 + a.a35 * b.a15;
/* 320 */     c.a32 = a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23 + a.a34 * b.a24 + a.a35 * b.a25;
/* 321 */     c.a33 = a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33 + a.a34 * b.a34 + a.a35 * b.a35;
/* 322 */     c.a34 = a.a31 * b.a41 + a.a32 * b.a42 + a.a33 * b.a43 + a.a34 * b.a44 + a.a35 * b.a45;
/* 323 */     c.a35 = a.a31 * b.a51 + a.a32 * b.a52 + a.a33 * b.a53 + a.a34 * b.a54 + a.a35 * b.a55;
/* 324 */     c.a41 = a.a41 * b.a11 + a.a42 * b.a12 + a.a43 * b.a13 + a.a44 * b.a14 + a.a45 * b.a15;
/* 325 */     c.a42 = a.a41 * b.a21 + a.a42 * b.a22 + a.a43 * b.a23 + a.a44 * b.a24 + a.a45 * b.a25;
/* 326 */     c.a43 = a.a41 * b.a31 + a.a42 * b.a32 + a.a43 * b.a33 + a.a44 * b.a34 + a.a45 * b.a35;
/* 327 */     c.a44 = a.a41 * b.a41 + a.a42 * b.a42 + a.a43 * b.a43 + a.a44 * b.a44 + a.a45 * b.a45;
/* 328 */     c.a45 = a.a41 * b.a51 + a.a42 * b.a52 + a.a43 * b.a53 + a.a44 * b.a54 + a.a45 * b.a55;
/* 329 */     c.a51 = a.a51 * b.a11 + a.a52 * b.a12 + a.a53 * b.a13 + a.a54 * b.a14 + a.a55 * b.a15;
/* 330 */     c.a52 = a.a51 * b.a21 + a.a52 * b.a22 + a.a53 * b.a23 + a.a54 * b.a24 + a.a55 * b.a25;
/* 331 */     c.a53 = a.a51 * b.a31 + a.a52 * b.a32 + a.a53 * b.a33 + a.a54 * b.a34 + a.a55 * b.a35;
/* 332 */     c.a54 = a.a51 * b.a41 + a.a52 * b.a42 + a.a53 * b.a43 + a.a54 * b.a44 + a.a55 * b.a45;
/* 333 */     c.a55 = a.a51 * b.a51 + a.a52 * b.a52 + a.a53 * b.a53 + a.a54 * b.a54 + a.a55 * b.a55;
/*     */   }
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
/*     */   public static void mult(FixedMatrix5x5_64F a, FixedMatrix5_64F b, FixedMatrix5_64F c) {
/* 349 */     c.a1 = a.a11 * b.a1 + a.a12 * b.a2 + a.a13 * b.a3 + a.a14 * b.a4 + a.a15 * b.a5;
/* 350 */     c.a2 = a.a21 * b.a1 + a.a22 * b.a2 + a.a23 * b.a3 + a.a24 * b.a4 + a.a25 * b.a5;
/* 351 */     c.a3 = a.a31 * b.a1 + a.a32 * b.a2 + a.a33 * b.a3 + a.a34 * b.a4 + a.a35 * b.a5;
/* 352 */     c.a4 = a.a41 * b.a1 + a.a42 * b.a2 + a.a43 * b.a3 + a.a44 * b.a4 + a.a45 * b.a5;
/* 353 */     c.a5 = a.a51 * b.a1 + a.a52 * b.a2 + a.a53 * b.a3 + a.a54 * b.a4 + a.a55 * b.a5;
/*     */   }
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
/*     */   public static void mult(FixedMatrix5_64F a, FixedMatrix5x5_64F b, FixedMatrix5_64F c) {
/* 369 */     c.a1 = a.a1 * b.a11 + a.a2 * b.a21 + a.a3 * b.a31 + a.a4 * b.a41 + a.a5 * b.a51;
/* 370 */     c.a2 = a.a1 * b.a12 + a.a2 * b.a22 + a.a3 * b.a32 + a.a4 * b.a42 + a.a5 * b.a52;
/* 371 */     c.a3 = a.a1 * b.a13 + a.a2 * b.a23 + a.a3 * b.a33 + a.a4 * b.a43 + a.a5 * b.a53;
/* 372 */     c.a4 = a.a1 * b.a14 + a.a2 * b.a24 + a.a3 * b.a34 + a.a4 * b.a44 + a.a5 * b.a54;
/* 373 */     c.a5 = a.a1 * b.a15 + a.a2 * b.a25 + a.a3 * b.a35 + a.a4 * b.a45 + a.a5 * b.a55;
/*     */   }
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
/*     */   public static double dot(FixedMatrix5_64F a, FixedMatrix5_64F b) {
/* 389 */     return a.a1 * b.a1 + a.a2 * b.a2 + a.a3 * b.a3 + a.a4 * b.a4 + a.a5 * b.a5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity(FixedMatrix5x5_64F a) {
/* 399 */     a.a11 = 1.0D; a.a21 = 0.0D; a.a31 = 0.0D; a.a41 = 0.0D; a.a51 = 0.0D;
/* 400 */     a.a12 = 0.0D; a.a22 = 1.0D; a.a32 = 0.0D; a.a42 = 0.0D; a.a52 = 0.0D;
/* 401 */     a.a13 = 0.0D; a.a23 = 0.0D; a.a33 = 1.0D; a.a43 = 0.0D; a.a53 = 0.0D;
/* 402 */     a.a14 = 0.0D; a.a24 = 0.0D; a.a34 = 0.0D; a.a44 = 1.0D; a.a54 = 0.0D;
/* 403 */     a.a15 = 0.0D; a.a25 = 0.0D; a.a35 = 0.0D; a.a45 = 0.0D; a.a55 = 1.0D;
/*     */   }
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
/*     */   public static boolean invert(FixedMatrix5x5_64F a, FixedMatrix5x5_64F inv) {
/* 418 */     double scale = 1.0D / elementMaxAbs(a);
/*     */     
/* 420 */     double a11 = a.a11 * scale;
/* 421 */     double a12 = a.a12 * scale;
/* 422 */     double a13 = a.a13 * scale;
/* 423 */     double a14 = a.a14 * scale;
/* 424 */     double a15 = a.a15 * scale;
/* 425 */     double a21 = a.a21 * scale;
/* 426 */     double a22 = a.a22 * scale;
/* 427 */     double a23 = a.a23 * scale;
/* 428 */     double a24 = a.a24 * scale;
/* 429 */     double a25 = a.a25 * scale;
/* 430 */     double a31 = a.a31 * scale;
/* 431 */     double a32 = a.a32 * scale;
/* 432 */     double a33 = a.a33 * scale;
/* 433 */     double a34 = a.a34 * scale;
/* 434 */     double a35 = a.a35 * scale;
/* 435 */     double a41 = a.a41 * scale;
/* 436 */     double a42 = a.a42 * scale;
/* 437 */     double a43 = a.a43 * scale;
/* 438 */     double a44 = a.a44 * scale;
/* 439 */     double a45 = a.a45 * scale;
/* 440 */     double a51 = a.a51 * scale;
/* 441 */     double a52 = a.a52 * scale;
/* 442 */     double a53 = a.a53 * scale;
/* 443 */     double a54 = a.a54 * scale;
/* 444 */     double a55 = a.a55 * scale;
/*     */     
/* 446 */     double m11 = a22 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a24 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a25 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52));
/* 447 */     double m12 = -(a21 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a23 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a25 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)));
/* 448 */     double m13 = a21 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a22 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a24 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51));
/* 449 */     double m14 = -(a21 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a25 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51)));
/* 450 */     double m15 = a21 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a22 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a23 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a24 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51));
/* 451 */     double m21 = -(a12 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a13 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) + a14 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a15 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)));
/* 452 */     double m22 = a11 * (a33 * (a44 * a55 - a45 * a54) - a34 * (a43 * a55 - a45 * a53) + a35 * (a43 * a54 - a44 * a53)) - a13 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a14 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) - a15 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51));
/* 453 */     double m23 = -(a11 * (a32 * (a44 * a55 - a45 * a54) - a34 * (a42 * a55 - a45 * a52) + a35 * (a42 * a54 - a44 * a52)) - a12 * (a31 * (a44 * a55 - a45 * a54) - a34 * (a41 * a55 - a45 * a51) + a35 * (a41 * a54 - a44 * a51)) + a14 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a15 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)));
/* 454 */     double m24 = a11 * (a32 * (a43 * a55 - a45 * a53) - a33 * (a42 * a55 - a45 * a52) + a35 * (a42 * a53 - a43 * a52)) - a12 * (a31 * (a43 * a55 - a45 * a53) - a33 * (a41 * a55 - a45 * a51) + a35 * (a41 * a53 - a43 * a51)) + a13 * (a31 * (a42 * a55 - a45 * a52) - a32 * (a41 * a55 - a45 * a51) + a35 * (a41 * a52 - a42 * a51)) - a15 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51));
/* 455 */     double m25 = -(a11 * (a32 * (a43 * a54 - a44 * a53) - a33 * (a42 * a54 - a44 * a52) + a34 * (a42 * a53 - a43 * a52)) - a12 * (a31 * (a43 * a54 - a44 * a53) - a33 * (a41 * a54 - a44 * a51) + a34 * (a41 * a53 - a43 * a51)) + a13 * (a31 * (a42 * a54 - a44 * a52) - a32 * (a41 * a54 - a44 * a51) + a34 * (a41 * a52 - a42 * a51)) - a14 * (a31 * (a42 * a53 - a43 * a52) - a32 * (a41 * a53 - a43 * a51) + a33 * (a41 * a52 - a42 * a51)));
/* 456 */     double m31 = a12 * (a23 * (a44 * a55 - a45 * a54) - a24 * (a43 * a55 - a45 * a53) + a25 * (a43 * a54 - a44 * a53)) - a13 * (a22 * (a44 * a55 - a45 * a54) - a24 * (a42 * a55 - a45 * a52) + a25 * (a42 * a54 - a44 * a52)) + a14 * (a22 * (a43 * a55 - a45 * a53) - a23 * (a42 * a55 - a45 * a52) + a25 * (a42 * a53 - a43 * a52)) - a15 * (a22 * (a43 * a54 - a44 * a53) - a23 * (a42 * a54 - a44 * a52) + a24 * (a42 * a53 - a43 * a52));
/* 457 */     double m32 = -(a11 * (a23 * (a44 * a55 - a45 * a54) - a24 * (a43 * a55 - a45 * a53) + a25 * (a43 * a54 - a44 * a53)) - a13 * (a21 * (a44 * a55 - a45 * a54) - a24 * (a41 * a55 - a45 * a51) + a25 * (a41 * a54 - a44 * a51)) + a14 * (a21 * (a43 * a55 - a45 * a53) - a23 * (a41 * a55 - a45 * a51) + a25 * (a41 * a53 - a43 * a51)) - a15 * (a21 * (a43 * a54 - a44 * a53) - a23 * (a41 * a54 - a44 * a51) + a24 * (a41 * a53 - a43 * a51)));
/* 458 */     double m33 = a11 * (a22 * (a44 * a55 - a45 * a54) - a24 * (a42 * a55 - a45 * a52) + a25 * (a42 * a54 - a44 * a52)) - a12 * (a21 * (a44 * a55 - a45 * a54) - a24 * (a41 * a55 - a45 * a51) + a25 * (a41 * a54 - a44 * a51)) + a14 * (a21 * (a42 * a55 - a45 * a52) - a22 * (a41 * a55 - a45 * a51) + a25 * (a41 * a52 - a42 * a51)) - a15 * (a21 * (a42 * a54 - a44 * a52) - a22 * (a41 * a54 - a44 * a51) + a24 * (a41 * a52 - a42 * a51));
/* 459 */     double m34 = -(a11 * (a22 * (a43 * a55 - a45 * a53) - a23 * (a42 * a55 - a45 * a52) + a25 * (a42 * a53 - a43 * a52)) - a12 * (a21 * (a43 * a55 - a45 * a53) - a23 * (a41 * a55 - a45 * a51) + a25 * (a41 * a53 - a43 * a51)) + a13 * (a21 * (a42 * a55 - a45 * a52) - a22 * (a41 * a55 - a45 * a51) + a25 * (a41 * a52 - a42 * a51)) - a15 * (a21 * (a42 * a53 - a43 * a52) - a22 * (a41 * a53 - a43 * a51) + a23 * (a41 * a52 - a42 * a51)));
/* 460 */     double m35 = a11 * (a22 * (a43 * a54 - a44 * a53) - a23 * (a42 * a54 - a44 * a52) + a24 * (a42 * a53 - a43 * a52)) - a12 * (a21 * (a43 * a54 - a44 * a53) - a23 * (a41 * a54 - a44 * a51) + a24 * (a41 * a53 - a43 * a51)) + a13 * (a21 * (a42 * a54 - a44 * a52) - a22 * (a41 * a54 - a44 * a51) + a24 * (a41 * a52 - a42 * a51)) - a14 * (a21 * (a42 * a53 - a43 * a52) - a22 * (a41 * a53 - a43 * a51) + a23 * (a41 * a52 - a42 * a51));
/* 461 */     double m41 = -(a12 * (a23 * (a34 * a55 - a35 * a54) - a24 * (a33 * a55 - a35 * a53) + a25 * (a33 * a54 - a34 * a53)) - a13 * (a22 * (a34 * a55 - a35 * a54) - a24 * (a32 * a55 - a35 * a52) + a25 * (a32 * a54 - a34 * a52)) + a14 * (a22 * (a33 * a55 - a35 * a53) - a23 * (a32 * a55 - a35 * a52) + a25 * (a32 * a53 - a33 * a52)) - a15 * (a22 * (a33 * a54 - a34 * a53) - a23 * (a32 * a54 - a34 * a52) + a24 * (a32 * a53 - a33 * a52)));
/* 462 */     double m42 = a11 * (a23 * (a34 * a55 - a35 * a54) - a24 * (a33 * a55 - a35 * a53) + a25 * (a33 * a54 - a34 * a53)) - a13 * (a21 * (a34 * a55 - a35 * a54) - a24 * (a31 * a55 - a35 * a51) + a25 * (a31 * a54 - a34 * a51)) + a14 * (a21 * (a33 * a55 - a35 * a53) - a23 * (a31 * a55 - a35 * a51) + a25 * (a31 * a53 - a33 * a51)) - a15 * (a21 * (a33 * a54 - a34 * a53) - a23 * (a31 * a54 - a34 * a51) + a24 * (a31 * a53 - a33 * a51));
/* 463 */     double m43 = -(a11 * (a22 * (a34 * a55 - a35 * a54) - a24 * (a32 * a55 - a35 * a52) + a25 * (a32 * a54 - a34 * a52)) - a12 * (a21 * (a34 * a55 - a35 * a54) - a24 * (a31 * a55 - a35 * a51) + a25 * (a31 * a54 - a34 * a51)) + a14 * (a21 * (a32 * a55 - a35 * a52) - a22 * (a31 * a55 - a35 * a51) + a25 * (a31 * a52 - a32 * a51)) - a15 * (a21 * (a32 * a54 - a34 * a52) - a22 * (a31 * a54 - a34 * a51) + a24 * (a31 * a52 - a32 * a51)));
/* 464 */     double m44 = a11 * (a22 * (a33 * a55 - a35 * a53) - a23 * (a32 * a55 - a35 * a52) + a25 * (a32 * a53 - a33 * a52)) - a12 * (a21 * (a33 * a55 - a35 * a53) - a23 * (a31 * a55 - a35 * a51) + a25 * (a31 * a53 - a33 * a51)) + a13 * (a21 * (a32 * a55 - a35 * a52) - a22 * (a31 * a55 - a35 * a51) + a25 * (a31 * a52 - a32 * a51)) - a15 * (a21 * (a32 * a53 - a33 * a52) - a22 * (a31 * a53 - a33 * a51) + a23 * (a31 * a52 - a32 * a51));
/* 465 */     double m45 = -(a11 * (a22 * (a33 * a54 - a34 * a53) - a23 * (a32 * a54 - a34 * a52) + a24 * (a32 * a53 - a33 * a52)) - a12 * (a21 * (a33 * a54 - a34 * a53) - a23 * (a31 * a54 - a34 * a51) + a24 * (a31 * a53 - a33 * a51)) + a13 * (a21 * (a32 * a54 - a34 * a52) - a22 * (a31 * a54 - a34 * a51) + a24 * (a31 * a52 - a32 * a51)) - a14 * (a21 * (a32 * a53 - a33 * a52) - a22 * (a31 * a53 - a33 * a51) + a23 * (a31 * a52 - a32 * a51)));
/* 466 */     double m51 = a12 * (a23 * (a34 * a45 - a35 * a44) - a24 * (a33 * a45 - a35 * a43) + a25 * (a33 * a44 - a34 * a43)) - a13 * (a22 * (a34 * a45 - a35 * a44) - a24 * (a32 * a45 - a35 * a42) + a25 * (a32 * a44 - a34 * a42)) + a14 * (a22 * (a33 * a45 - a35 * a43) - a23 * (a32 * a45 - a35 * a42) + a25 * (a32 * a43 - a33 * a42)) - a15 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42));
/* 467 */     double m52 = -(a11 * (a23 * (a34 * a45 - a35 * a44) - a24 * (a33 * a45 - a35 * a43) + a25 * (a33 * a44 - a34 * a43)) - a13 * (a21 * (a34 * a45 - a35 * a44) - a24 * (a31 * a45 - a35 * a41) + a25 * (a31 * a44 - a34 * a41)) + a14 * (a21 * (a33 * a45 - a35 * a43) - a23 * (a31 * a45 - a35 * a41) + a25 * (a31 * a43 - a33 * a41)) - a15 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)));
/* 468 */     double m53 = a11 * (a22 * (a34 * a45 - a35 * a44) - a24 * (a32 * a45 - a35 * a42) + a25 * (a32 * a44 - a34 * a42)) - a12 * (a21 * (a34 * a45 - a35 * a44) - a24 * (a31 * a45 - a35 * a41) + a25 * (a31 * a44 - a34 * a41)) + a14 * (a21 * (a32 * a45 - a35 * a42) - a22 * (a31 * a45 - a35 * a41) + a25 * (a31 * a42 - a32 * a41)) - a15 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41));
/* 469 */     double m54 = -(a11 * (a22 * (a33 * a45 - a35 * a43) - a23 * (a32 * a45 - a35 * a42) + a25 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a45 - a35 * a43) - a23 * (a31 * a45 - a35 * a41) + a25 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a45 - a35 * a42) - a22 * (a31 * a45 - a35 * a41) + a25 * (a31 * a42 - a32 * a41)) - a15 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
/* 470 */     double m55 = a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41));
/*     */     
/* 472 */     double det = (a11 * m11 + a12 * m12 + a13 * m13 + a14 * m14 + a15 * m15) / scale;
/*     */     
/* 474 */     inv.a11 = m11 / det;
/* 475 */     inv.a12 = m21 / det;
/* 476 */     inv.a13 = m31 / det;
/* 477 */     inv.a14 = m41 / det;
/* 478 */     inv.a15 = m51 / det;
/* 479 */     inv.a21 = m12 / det;
/* 480 */     inv.a22 = m22 / det;
/* 481 */     inv.a23 = m32 / det;
/* 482 */     inv.a24 = m42 / det;
/* 483 */     inv.a25 = m52 / det;
/* 484 */     inv.a31 = m13 / det;
/* 485 */     inv.a32 = m23 / det;
/* 486 */     inv.a33 = m33 / det;
/* 487 */     inv.a34 = m43 / det;
/* 488 */     inv.a35 = m53 / det;
/* 489 */     inv.a41 = m14 / det;
/* 490 */     inv.a42 = m24 / det;
/* 491 */     inv.a43 = m34 / det;
/* 492 */     inv.a44 = m44 / det;
/* 493 */     inv.a45 = m54 / det;
/* 494 */     inv.a51 = m15 / det;
/* 495 */     inv.a52 = m25 / det;
/* 496 */     inv.a53 = m35 / det;
/* 497 */     inv.a54 = m45 / det;
/* 498 */     inv.a55 = m55 / det;
/*     */     
/* 500 */     return (!Double.isNaN(det) && !Double.isInfinite(det));
/*     */   }
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
/*     */   public static double det(FixedMatrix5x5_64F mat) {
/* 513 */     double a11 = mat.a22;
/* 514 */     double a12 = mat.a23;
/* 515 */     double a13 = mat.a24;
/* 516 */     double a14 = mat.a25;
/* 517 */     double a21 = mat.a32;
/* 518 */     double a22 = mat.a33;
/* 519 */     double a23 = mat.a34;
/* 520 */     double a24 = mat.a35;
/* 521 */     double a31 = mat.a42;
/* 522 */     double a32 = mat.a43;
/* 523 */     double a33 = mat.a44;
/* 524 */     double a34 = mat.a45;
/* 525 */     double a41 = mat.a52;
/* 526 */     double a42 = mat.a53;
/* 527 */     double a43 = mat.a54;
/* 528 */     double a44 = mat.a55;
/*     */     
/* 530 */     double ret = 0.0D;
/* 531 */     ret += mat.a11 * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
/* 532 */     a11 = mat.a21;
/* 533 */     a21 = mat.a31;
/* 534 */     a31 = mat.a41;
/* 535 */     a41 = mat.a51;
/* 536 */     ret -= mat.a12 * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
/* 537 */     a12 = mat.a22;
/* 538 */     a22 = mat.a32;
/* 539 */     a32 = mat.a42;
/* 540 */     a42 = mat.a52;
/* 541 */     ret += mat.a13 * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
/* 542 */     a13 = mat.a23;
/* 543 */     a23 = mat.a33;
/* 544 */     a33 = mat.a43;
/* 545 */     a43 = mat.a53;
/* 546 */     ret -= mat.a14 * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
/* 547 */     a14 = mat.a24;
/* 548 */     a24 = mat.a34;
/* 549 */     a34 = mat.a44;
/* 550 */     a44 = mat.a54;
/* 551 */     ret += mat.a15 * (a11 * (a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42)) - a12 * (a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41)) + a13 * (a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41)) - a14 * (a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41)));
/*     */     
/* 553 */     return ret;
/*     */   }
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
/*     */   public static double trace(FixedMatrix5x5_64F a) {
/* 569 */     return a.a11 + a.a21 + a.a31 + a.a41 + a.a51;
/*     */   }
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
/*     */   public static void diag(FixedMatrix5x5_64F input, FixedMatrix5_64F out) {
/* 583 */     out.a1 = input.a11;
/* 584 */     out.a2 = input.a22;
/* 585 */     out.a3 = input.a33;
/* 586 */     out.a4 = input.a44;
/* 587 */     out.a5 = input.a55;
/*     */   }
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
/*     */   public static double elementMax(FixedMatrix5x5_64F a) {
/* 601 */     double max = a.a11;
/* 602 */     max = Math.max(max, a.a12);
/* 603 */     max = Math.max(max, a.a13);
/* 604 */     max = Math.max(max, a.a14);
/* 605 */     max = Math.max(max, a.a15);
/* 606 */     max = Math.max(max, a.a21);
/* 607 */     max = Math.max(max, a.a22);
/* 608 */     max = Math.max(max, a.a23);
/* 609 */     max = Math.max(max, a.a24);
/* 610 */     max = Math.max(max, a.a25);
/* 611 */     max = Math.max(max, a.a31);
/* 612 */     max = Math.max(max, a.a32);
/* 613 */     max = Math.max(max, a.a33);
/* 614 */     max = Math.max(max, a.a34);
/* 615 */     max = Math.max(max, a.a35);
/* 616 */     max = Math.max(max, a.a41);
/* 617 */     max = Math.max(max, a.a42);
/* 618 */     max = Math.max(max, a.a43);
/* 619 */     max = Math.max(max, a.a44);
/* 620 */     max = Math.max(max, a.a45);
/* 621 */     max = Math.max(max, a.a51);
/* 622 */     max = Math.max(max, a.a52);
/* 623 */     max = Math.max(max, a.a53);
/* 624 */     max = Math.max(max, a.a54);
/* 625 */     max = Math.max(max, a.a55);
/*     */     
/* 627 */     return max;
/*     */   }
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
/*     */   public static double elementMaxAbs(FixedMatrix5x5_64F a) {
/* 641 */     double max = a.a11;
/* 642 */     max = Math.max(max, Math.abs(a.a12));
/* 643 */     max = Math.max(max, Math.abs(a.a13));
/* 644 */     max = Math.max(max, Math.abs(a.a14));
/* 645 */     max = Math.max(max, Math.abs(a.a15));
/* 646 */     max = Math.max(max, Math.abs(a.a21));
/* 647 */     max = Math.max(max, Math.abs(a.a22));
/* 648 */     max = Math.max(max, Math.abs(a.a23));
/* 649 */     max = Math.max(max, Math.abs(a.a24));
/* 650 */     max = Math.max(max, Math.abs(a.a25));
/* 651 */     max = Math.max(max, Math.abs(a.a31));
/* 652 */     max = Math.max(max, Math.abs(a.a32));
/* 653 */     max = Math.max(max, Math.abs(a.a33));
/* 654 */     max = Math.max(max, Math.abs(a.a34));
/* 655 */     max = Math.max(max, Math.abs(a.a35));
/* 656 */     max = Math.max(max, Math.abs(a.a41));
/* 657 */     max = Math.max(max, Math.abs(a.a42));
/* 658 */     max = Math.max(max, Math.abs(a.a43));
/* 659 */     max = Math.max(max, Math.abs(a.a44));
/* 660 */     max = Math.max(max, Math.abs(a.a45));
/* 661 */     max = Math.max(max, Math.abs(a.a51));
/* 662 */     max = Math.max(max, Math.abs(a.a52));
/* 663 */     max = Math.max(max, Math.abs(a.a53));
/* 664 */     max = Math.max(max, Math.abs(a.a54));
/* 665 */     max = Math.max(max, Math.abs(a.a55));
/*     */     
/* 667 */     return max;
/*     */   }
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
/*     */   public static double elementMin(FixedMatrix5x5_64F a) {
/* 681 */     double min = a.a11;
/* 682 */     min = Math.min(min, a.a12);
/* 683 */     min = Math.min(min, a.a13);
/* 684 */     min = Math.min(min, a.a14);
/* 685 */     min = Math.min(min, a.a15);
/* 686 */     min = Math.min(min, a.a21);
/* 687 */     min = Math.min(min, a.a22);
/* 688 */     min = Math.min(min, a.a23);
/* 689 */     min = Math.min(min, a.a24);
/* 690 */     min = Math.min(min, a.a25);
/* 691 */     min = Math.min(min, a.a31);
/* 692 */     min = Math.min(min, a.a32);
/* 693 */     min = Math.min(min, a.a33);
/* 694 */     min = Math.min(min, a.a34);
/* 695 */     min = Math.min(min, a.a35);
/* 696 */     min = Math.min(min, a.a41);
/* 697 */     min = Math.min(min, a.a42);
/* 698 */     min = Math.min(min, a.a43);
/* 699 */     min = Math.min(min, a.a44);
/* 700 */     min = Math.min(min, a.a45);
/* 701 */     min = Math.min(min, a.a51);
/* 702 */     min = Math.min(min, a.a52);
/* 703 */     min = Math.min(min, a.a53);
/* 704 */     min = Math.min(min, a.a54);
/* 705 */     min = Math.min(min, a.a55);
/*     */     
/* 707 */     return min;
/*     */   }
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
/*     */   public static double elementMinAbs(FixedMatrix5x5_64F a) {
/* 721 */     double min = a.a11;
/* 722 */     min = Math.min(min, Math.abs(a.a12));
/* 723 */     min = Math.min(min, Math.abs(a.a13));
/* 724 */     min = Math.min(min, Math.abs(a.a14));
/* 725 */     min = Math.min(min, Math.abs(a.a15));
/* 726 */     min = Math.min(min, Math.abs(a.a21));
/* 727 */     min = Math.min(min, Math.abs(a.a22));
/* 728 */     min = Math.min(min, Math.abs(a.a23));
/* 729 */     min = Math.min(min, Math.abs(a.a24));
/* 730 */     min = Math.min(min, Math.abs(a.a25));
/* 731 */     min = Math.min(min, Math.abs(a.a31));
/* 732 */     min = Math.min(min, Math.abs(a.a32));
/* 733 */     min = Math.min(min, Math.abs(a.a33));
/* 734 */     min = Math.min(min, Math.abs(a.a34));
/* 735 */     min = Math.min(min, Math.abs(a.a35));
/* 736 */     min = Math.min(min, Math.abs(a.a41));
/* 737 */     min = Math.min(min, Math.abs(a.a42));
/* 738 */     min = Math.min(min, Math.abs(a.a43));
/* 739 */     min = Math.min(min, Math.abs(a.a44));
/* 740 */     min = Math.min(min, Math.abs(a.a45));
/* 741 */     min = Math.min(min, Math.abs(a.a51));
/* 742 */     min = Math.min(min, Math.abs(a.a52));
/* 743 */     min = Math.min(min, Math.abs(a.a53));
/* 744 */     min = Math.min(min, Math.abs(a.a54));
/* 745 */     min = Math.min(min, Math.abs(a.a55));
/*     */     
/* 747 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void elementMult(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b) {
/* 759 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13; a.a14 *= b.a14; a.a15 *= b.a15;
/* 760 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23; a.a24 *= b.a24; a.a25 *= b.a25;
/* 761 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33; a.a34 *= b.a34; a.a35 *= b.a35;
/* 762 */     a.a41 *= b.a41; a.a42 *= b.a42; a.a43 *= b.a43; a.a44 *= b.a44; a.a45 *= b.a45;
/* 763 */     a.a51 *= b.a51; a.a52 *= b.a52; a.a53 *= b.a53; a.a54 *= b.a54; a.a55 *= b.a55;
/*     */   }
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
/*     */   public static void elementMult(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/* 776 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13; a.a14 *= b.a14; a.a15 *= b.a15;
/* 777 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23; a.a24 *= b.a24; a.a25 *= b.a25;
/* 778 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33; a.a34 *= b.a34; a.a35 *= b.a35;
/* 779 */     a.a41 *= b.a41; a.a42 *= b.a42; a.a43 *= b.a43; a.a44 *= b.a44; a.a45 *= b.a45;
/* 780 */     a.a51 *= b.a51; a.a52 *= b.a52; a.a53 *= b.a53; a.a54 *= b.a54; a.a55 *= b.a55;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void elementDiv(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b) {
/* 792 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13; a.a14 /= b.a14; a.a15 /= b.a15;
/* 793 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23; a.a24 /= b.a24; a.a25 /= b.a25;
/* 794 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33; a.a34 /= b.a34; a.a35 /= b.a35;
/* 795 */     a.a41 /= b.a41; a.a42 /= b.a42; a.a43 /= b.a43; a.a44 /= b.a44; a.a45 /= b.a45;
/* 796 */     a.a51 /= b.a51; a.a52 /= b.a52; a.a53 /= b.a53; a.a54 /= b.a54; a.a55 /= b.a55;
/*     */   }
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
/*     */   public static void elementDiv(FixedMatrix5x5_64F a, FixedMatrix5x5_64F b, FixedMatrix5x5_64F c) {
/* 809 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13; a.a14 /= b.a14; a.a15 /= b.a15;
/* 810 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23; a.a24 /= b.a24; a.a25 /= b.a25;
/* 811 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33; a.a34 /= b.a34; a.a35 /= b.a35;
/* 812 */     a.a41 /= b.a41; a.a42 /= b.a42; a.a43 /= b.a43; a.a44 /= b.a44; a.a45 /= b.a45;
/* 813 */     a.a51 /= b.a51; a.a52 /= b.a52; a.a53 /= b.a53; a.a54 /= b.a54; a.a55 /= b.a55;
/*     */   }
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
/*     */   public static void scale(double alpha, FixedMatrix5x5_64F a) {
/* 827 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha; a.a14 *= alpha; a.a15 *= alpha;
/* 828 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha; a.a24 *= alpha; a.a25 *= alpha;
/* 829 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha; a.a34 *= alpha; a.a35 *= alpha;
/* 830 */     a.a41 *= alpha; a.a42 *= alpha; a.a43 *= alpha; a.a44 *= alpha; a.a45 *= alpha;
/* 831 */     a.a51 *= alpha; a.a52 *= alpha; a.a53 *= alpha; a.a54 *= alpha; a.a55 *= alpha;
/*     */   }
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
/*     */   public static void scale(double alpha, FixedMatrix5x5_64F a, FixedMatrix5x5_64F b) {
/* 846 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha; a.a14 *= alpha; a.a15 *= alpha;
/* 847 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha; a.a24 *= alpha; a.a25 *= alpha;
/* 848 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha; a.a34 *= alpha; a.a35 *= alpha;
/* 849 */     a.a41 *= alpha; a.a42 *= alpha; a.a43 *= alpha; a.a44 *= alpha; a.a45 *= alpha;
/* 850 */     a.a51 *= alpha; a.a52 *= alpha; a.a53 *= alpha; a.a54 *= alpha; a.a55 *= alpha;
/*     */   }
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
/*     */   public static void divide(FixedMatrix5x5_64F a, double alpha) {
/* 864 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha; a.a14 /= alpha; a.a15 /= alpha;
/* 865 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha; a.a24 /= alpha; a.a25 /= alpha;
/* 866 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha; a.a34 /= alpha; a.a35 /= alpha;
/* 867 */     a.a41 /= alpha; a.a42 /= alpha; a.a43 /= alpha; a.a44 /= alpha; a.a45 /= alpha;
/* 868 */     a.a51 /= alpha; a.a52 /= alpha; a.a53 /= alpha; a.a54 /= alpha; a.a55 /= alpha;
/*     */   }
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
/*     */   public static void divide(FixedMatrix5x5_64F a, double alpha, FixedMatrix5x5_64F b) {
/* 883 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha; a.a14 /= alpha; a.a15 /= alpha;
/* 884 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha; a.a24 /= alpha; a.a25 /= alpha;
/* 885 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha; a.a34 /= alpha; a.a35 /= alpha;
/* 886 */     a.a41 /= alpha; a.a42 /= alpha; a.a43 /= alpha; a.a44 /= alpha; a.a45 /= alpha;
/* 887 */     a.a51 /= alpha; a.a52 /= alpha; a.a53 /= alpha; a.a54 /= alpha; a.a55 /= alpha;
/*     */   }
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
/*     */   public static void changeSign(FixedMatrix5x5_64F a) {
/* 901 */     a.a11 = -a.a11; a.a12 = -a.a12; a.a13 = -a.a13; a.a14 = -a.a14; a.a15 = -a.a15;
/* 902 */     a.a21 = -a.a21; a.a22 = -a.a22; a.a23 = -a.a23; a.a24 = -a.a24; a.a25 = -a.a25;
/* 903 */     a.a31 = -a.a31; a.a32 = -a.a32; a.a33 = -a.a33; a.a34 = -a.a34; a.a35 = -a.a35;
/* 904 */     a.a41 = -a.a41; a.a42 = -a.a42; a.a43 = -a.a43; a.a44 = -a.a44; a.a45 = -a.a45;
/* 905 */     a.a51 = -a.a51; a.a52 = -a.a52; a.a53 = -a.a53; a.a54 = -a.a54; a.a55 = -a.a55;
/*     */   }
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
/*     */   public static void fill(FixedMatrix5x5_64F a, double v) {
/* 919 */     a.a11 = v; a.a12 = v; a.a13 = v; a.a14 = v; a.a15 = v;
/* 920 */     a.a21 = v; a.a22 = v; a.a23 = v; a.a24 = v; a.a25 = v;
/* 921 */     a.a31 = v; a.a32 = v; a.a33 = v; a.a34 = v; a.a35 = v;
/* 922 */     a.a41 = v; a.a42 = v; a.a43 = v; a.a44 = v; a.a45 = v;
/* 923 */     a.a51 = v; a.a52 = v; a.a53 = v; a.a54 = v; a.a55 = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\fixed\FixedOps5.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */