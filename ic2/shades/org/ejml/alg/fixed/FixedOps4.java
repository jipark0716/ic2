/*     */ package ic2.shades.org.ejml.alg.fixed;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.FixedMatrix4_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix4x4_64F;
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
/*     */ public class FixedOps4
/*     */ {
/*     */   public static void add(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/*  46 */     a.a11 += b.a11;
/*  47 */     a.a12 += b.a12;
/*  48 */     a.a13 += b.a13;
/*  49 */     a.a14 += b.a14;
/*  50 */     a.a21 += b.a21;
/*  51 */     a.a22 += b.a22;
/*  52 */     a.a23 += b.a23;
/*  53 */     a.a24 += b.a24;
/*  54 */     a.a31 += b.a31;
/*  55 */     a.a32 += b.a32;
/*  56 */     a.a33 += b.a33;
/*  57 */     a.a34 += b.a34;
/*  58 */     a.a41 += b.a41;
/*  59 */     a.a42 += b.a42;
/*  60 */     a.a43 += b.a43;
/*  61 */     a.a44 += b.a44;
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
/*     */   public static void addEquals(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b) {
/*  75 */     a.a11 += b.a11;
/*  76 */     a.a12 += b.a12;
/*  77 */     a.a13 += b.a13;
/*  78 */     a.a14 += b.a14;
/*  79 */     a.a21 += b.a21;
/*  80 */     a.a22 += b.a22;
/*  81 */     a.a23 += b.a23;
/*  82 */     a.a24 += b.a24;
/*  83 */     a.a31 += b.a31;
/*  84 */     a.a32 += b.a32;
/*  85 */     a.a33 += b.a33;
/*  86 */     a.a34 += b.a34;
/*  87 */     a.a41 += b.a41;
/*  88 */     a.a42 += b.a42;
/*  89 */     a.a43 += b.a43;
/*  90 */     a.a44 += b.a44;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void transpose(FixedMatrix4x4_64F m) {
/* 101 */     double tmp = m.a12; m.a12 = m.a21; m.a21 = tmp;
/* 102 */     tmp = m.a13; m.a13 = m.a31; m.a31 = tmp;
/* 103 */     tmp = m.a14; m.a14 = m.a41; m.a41 = tmp;
/* 104 */     tmp = m.a23; m.a23 = m.a32; m.a32 = tmp;
/* 105 */     tmp = m.a24; m.a24 = m.a42; m.a42 = tmp;
/* 106 */     tmp = m.a34; m.a34 = m.a43; m.a43 = tmp;
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
/*     */   public static FixedMatrix4x4_64F transpose(FixedMatrix4x4_64F input, FixedMatrix4x4_64F output) {
/* 122 */     if (input == null) {
/* 123 */       input = new FixedMatrix4x4_64F();
/*     */     }
/* 125 */     output.a11 = input.a11;
/* 126 */     output.a12 = input.a21;
/* 127 */     output.a13 = input.a31;
/* 128 */     output.a14 = input.a41;
/* 129 */     output.a21 = input.a12;
/* 130 */     output.a22 = input.a22;
/* 131 */     output.a23 = input.a32;
/* 132 */     output.a24 = input.a42;
/* 133 */     output.a31 = input.a13;
/* 134 */     output.a32 = input.a23;
/* 135 */     output.a33 = input.a33;
/* 136 */     output.a34 = input.a43;
/* 137 */     output.a41 = input.a14;
/* 138 */     output.a42 = input.a24;
/* 139 */     output.a43 = input.a34;
/* 140 */     output.a44 = input.a44;
/*     */     
/* 142 */     return output;
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
/*     */   public static void mult(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/* 158 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31 + a.a14 * b.a41;
/* 159 */     c.a12 = a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32 + a.a14 * b.a42;
/* 160 */     c.a13 = a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33 + a.a14 * b.a43;
/* 161 */     c.a14 = a.a11 * b.a14 + a.a12 * b.a24 + a.a13 * b.a34 + a.a14 * b.a44;
/* 162 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31 + a.a24 * b.a41;
/* 163 */     c.a22 = a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32 + a.a24 * b.a42;
/* 164 */     c.a23 = a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33 + a.a24 * b.a43;
/* 165 */     c.a24 = a.a21 * b.a14 + a.a22 * b.a24 + a.a23 * b.a34 + a.a24 * b.a44;
/* 166 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31 + a.a34 * b.a41;
/* 167 */     c.a32 = a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32 + a.a34 * b.a42;
/* 168 */     c.a33 = a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33 + a.a34 * b.a43;
/* 169 */     c.a34 = a.a31 * b.a14 + a.a32 * b.a24 + a.a33 * b.a34 + a.a34 * b.a44;
/* 170 */     c.a41 = a.a41 * b.a11 + a.a42 * b.a21 + a.a43 * b.a31 + a.a44 * b.a41;
/* 171 */     c.a42 = a.a41 * b.a12 + a.a42 * b.a22 + a.a43 * b.a32 + a.a44 * b.a42;
/* 172 */     c.a43 = a.a41 * b.a13 + a.a42 * b.a23 + a.a43 * b.a33 + a.a44 * b.a43;
/* 173 */     c.a44 = a.a41 * b.a14 + a.a42 * b.a24 + a.a43 * b.a34 + a.a44 * b.a44;
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
/*     */   public static void multTransA(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/* 189 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31 + a.a41 * b.a41;
/* 190 */     c.a12 = a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32 + a.a41 * b.a42;
/* 191 */     c.a13 = a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33 + a.a41 * b.a43;
/* 192 */     c.a14 = a.a11 * b.a14 + a.a21 * b.a24 + a.a31 * b.a34 + a.a41 * b.a44;
/* 193 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31 + a.a42 * b.a41;
/* 194 */     c.a22 = a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32 + a.a42 * b.a42;
/* 195 */     c.a23 = a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33 + a.a42 * b.a43;
/* 196 */     c.a24 = a.a12 * b.a14 + a.a22 * b.a24 + a.a32 * b.a34 + a.a42 * b.a44;
/* 197 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31 + a.a43 * b.a41;
/* 198 */     c.a32 = a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32 + a.a43 * b.a42;
/* 199 */     c.a33 = a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33 + a.a43 * b.a43;
/* 200 */     c.a34 = a.a13 * b.a14 + a.a23 * b.a24 + a.a33 * b.a34 + a.a43 * b.a44;
/* 201 */     c.a41 = a.a14 * b.a11 + a.a24 * b.a21 + a.a34 * b.a31 + a.a44 * b.a41;
/* 202 */     c.a42 = a.a14 * b.a12 + a.a24 * b.a22 + a.a34 * b.a32 + a.a44 * b.a42;
/* 203 */     c.a43 = a.a14 * b.a13 + a.a24 * b.a23 + a.a34 * b.a33 + a.a44 * b.a43;
/* 204 */     c.a44 = a.a14 * b.a14 + a.a24 * b.a24 + a.a34 * b.a34 + a.a44 * b.a44;
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
/*     */   public static void multTransAB(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/* 220 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13 + a.a41 * b.a14;
/* 221 */     c.a12 = a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23 + a.a41 * b.a24;
/* 222 */     c.a13 = a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33 + a.a41 * b.a34;
/* 223 */     c.a14 = a.a11 * b.a41 + a.a21 * b.a42 + a.a31 * b.a43 + a.a41 * b.a44;
/* 224 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13 + a.a42 * b.a14;
/* 225 */     c.a22 = a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23 + a.a42 * b.a24;
/* 226 */     c.a23 = a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33 + a.a42 * b.a34;
/* 227 */     c.a24 = a.a12 * b.a41 + a.a22 * b.a42 + a.a32 * b.a43 + a.a42 * b.a44;
/* 228 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13 + a.a43 * b.a14;
/* 229 */     c.a32 = a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23 + a.a43 * b.a24;
/* 230 */     c.a33 = a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33 + a.a43 * b.a34;
/* 231 */     c.a34 = a.a13 * b.a41 + a.a23 * b.a42 + a.a33 * b.a43 + a.a43 * b.a44;
/* 232 */     c.a41 = a.a14 * b.a11 + a.a24 * b.a12 + a.a34 * b.a13 + a.a44 * b.a14;
/* 233 */     c.a42 = a.a14 * b.a21 + a.a24 * b.a22 + a.a34 * b.a23 + a.a44 * b.a24;
/* 234 */     c.a43 = a.a14 * b.a31 + a.a24 * b.a32 + a.a34 * b.a33 + a.a44 * b.a34;
/* 235 */     c.a44 = a.a14 * b.a41 + a.a24 * b.a42 + a.a34 * b.a43 + a.a44 * b.a44;
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
/*     */   public static void multTransB(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/* 251 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13 + a.a14 * b.a14;
/* 252 */     c.a12 = a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23 + a.a14 * b.a24;
/* 253 */     c.a13 = a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33 + a.a14 * b.a34;
/* 254 */     c.a14 = a.a11 * b.a41 + a.a12 * b.a42 + a.a13 * b.a43 + a.a14 * b.a44;
/* 255 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13 + a.a24 * b.a14;
/* 256 */     c.a22 = a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23 + a.a24 * b.a24;
/* 257 */     c.a23 = a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33 + a.a24 * b.a34;
/* 258 */     c.a24 = a.a21 * b.a41 + a.a22 * b.a42 + a.a23 * b.a43 + a.a24 * b.a44;
/* 259 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13 + a.a34 * b.a14;
/* 260 */     c.a32 = a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23 + a.a34 * b.a24;
/* 261 */     c.a33 = a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33 + a.a34 * b.a34;
/* 262 */     c.a34 = a.a31 * b.a41 + a.a32 * b.a42 + a.a33 * b.a43 + a.a34 * b.a44;
/* 263 */     c.a41 = a.a41 * b.a11 + a.a42 * b.a12 + a.a43 * b.a13 + a.a44 * b.a14;
/* 264 */     c.a42 = a.a41 * b.a21 + a.a42 * b.a22 + a.a43 * b.a23 + a.a44 * b.a24;
/* 265 */     c.a43 = a.a41 * b.a31 + a.a42 * b.a32 + a.a43 * b.a33 + a.a44 * b.a34;
/* 266 */     c.a44 = a.a41 * b.a41 + a.a42 * b.a42 + a.a43 * b.a43 + a.a44 * b.a44;
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
/*     */   public static void mult(FixedMatrix4x4_64F a, FixedMatrix4_64F b, FixedMatrix4_64F c) {
/* 282 */     c.a1 = a.a11 * b.a1 + a.a12 * b.a2 + a.a13 * b.a3 + a.a14 * b.a4;
/* 283 */     c.a2 = a.a21 * b.a1 + a.a22 * b.a2 + a.a23 * b.a3 + a.a24 * b.a4;
/* 284 */     c.a3 = a.a31 * b.a1 + a.a32 * b.a2 + a.a33 * b.a3 + a.a34 * b.a4;
/* 285 */     c.a4 = a.a41 * b.a1 + a.a42 * b.a2 + a.a43 * b.a3 + a.a44 * b.a4;
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
/*     */   public static void mult(FixedMatrix4_64F a, FixedMatrix4x4_64F b, FixedMatrix4_64F c) {
/* 301 */     c.a1 = a.a1 * b.a11 + a.a2 * b.a21 + a.a3 * b.a31 + a.a4 * b.a41;
/* 302 */     c.a2 = a.a1 * b.a12 + a.a2 * b.a22 + a.a3 * b.a32 + a.a4 * b.a42;
/* 303 */     c.a3 = a.a1 * b.a13 + a.a2 * b.a23 + a.a3 * b.a33 + a.a4 * b.a43;
/* 304 */     c.a4 = a.a1 * b.a14 + a.a2 * b.a24 + a.a3 * b.a34 + a.a4 * b.a44;
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
/*     */   public static double dot(FixedMatrix4_64F a, FixedMatrix4_64F b) {
/* 320 */     return a.a1 * b.a1 + a.a2 * b.a2 + a.a3 * b.a3 + a.a4 * b.a4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity(FixedMatrix4x4_64F a) {
/* 330 */     a.a11 = 1.0D; a.a21 = 0.0D; a.a31 = 0.0D; a.a41 = 0.0D;
/* 331 */     a.a12 = 0.0D; a.a22 = 1.0D; a.a32 = 0.0D; a.a42 = 0.0D;
/* 332 */     a.a13 = 0.0D; a.a23 = 0.0D; a.a33 = 1.0D; a.a43 = 0.0D;
/* 333 */     a.a14 = 0.0D; a.a24 = 0.0D; a.a34 = 0.0D; a.a44 = 1.0D;
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
/*     */   public static boolean invert(FixedMatrix4x4_64F a, FixedMatrix4x4_64F inv) {
/* 348 */     double scale = 1.0D / elementMaxAbs(a);
/*     */     
/* 350 */     double a11 = a.a11 * scale;
/* 351 */     double a12 = a.a12 * scale;
/* 352 */     double a13 = a.a13 * scale;
/* 353 */     double a14 = a.a14 * scale;
/* 354 */     double a21 = a.a21 * scale;
/* 355 */     double a22 = a.a22 * scale;
/* 356 */     double a23 = a.a23 * scale;
/* 357 */     double a24 = a.a24 * scale;
/* 358 */     double a31 = a.a31 * scale;
/* 359 */     double a32 = a.a32 * scale;
/* 360 */     double a33 = a.a33 * scale;
/* 361 */     double a34 = a.a34 * scale;
/* 362 */     double a41 = a.a41 * scale;
/* 363 */     double a42 = a.a42 * scale;
/* 364 */     double a43 = a.a43 * scale;
/* 365 */     double a44 = a.a44 * scale;
/*     */     
/* 367 */     double m11 = a22 * (a33 * a44 - a34 * a43) - a23 * (a32 * a44 - a34 * a42) + a24 * (a32 * a43 - a33 * a42);
/* 368 */     double m12 = -(a21 * (a33 * a44 - a34 * a43) - a23 * (a31 * a44 - a34 * a41) + a24 * (a31 * a43 - a33 * a41));
/* 369 */     double m13 = a21 * (a32 * a44 - a34 * a42) - a22 * (a31 * a44 - a34 * a41) + a24 * (a31 * a42 - a32 * a41);
/* 370 */     double m14 = -(a21 * (a32 * a43 - a33 * a42) - a22 * (a31 * a43 - a33 * a41) + a23 * (a31 * a42 - a32 * a41));
/* 371 */     double m21 = -(a12 * (a33 * a44 - a34 * a43) - a13 * (a32 * a44 - a34 * a42) + a14 * (a32 * a43 - a33 * a42));
/* 372 */     double m22 = a11 * (a33 * a44 - a34 * a43) - a13 * (a31 * a44 - a34 * a41) + a14 * (a31 * a43 - a33 * a41);
/* 373 */     double m23 = -(a11 * (a32 * a44 - a34 * a42) - a12 * (a31 * a44 - a34 * a41) + a14 * (a31 * a42 - a32 * a41));
/* 374 */     double m24 = a11 * (a32 * a43 - a33 * a42) - a12 * (a31 * a43 - a33 * a41) + a13 * (a31 * a42 - a32 * a41);
/* 375 */     double m31 = a12 * (a23 * a44 - a24 * a43) - a13 * (a22 * a44 - a24 * a42) + a14 * (a22 * a43 - a23 * a42);
/* 376 */     double m32 = -(a11 * (a23 * a44 - a24 * a43) - a13 * (a21 * a44 - a24 * a41) + a14 * (a21 * a43 - a23 * a41));
/* 377 */     double m33 = a11 * (a22 * a44 - a24 * a42) - a12 * (a21 * a44 - a24 * a41) + a14 * (a21 * a42 - a22 * a41);
/* 378 */     double m34 = -(a11 * (a22 * a43 - a23 * a42) - a12 * (a21 * a43 - a23 * a41) + a13 * (a21 * a42 - a22 * a41));
/* 379 */     double m41 = -(a12 * (a23 * a34 - a24 * a33) - a13 * (a22 * a34 - a24 * a32) + a14 * (a22 * a33 - a23 * a32));
/* 380 */     double m42 = a11 * (a23 * a34 - a24 * a33) - a13 * (a21 * a34 - a24 * a31) + a14 * (a21 * a33 - a23 * a31);
/* 381 */     double m43 = -(a11 * (a22 * a34 - a24 * a32) - a12 * (a21 * a34 - a24 * a31) + a14 * (a21 * a32 - a22 * a31));
/* 382 */     double m44 = a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31);
/*     */     
/* 384 */     double det = (a11 * m11 + a12 * m12 + a13 * m13 + a14 * m14) / scale;
/*     */     
/* 386 */     inv.a11 = m11 / det;
/* 387 */     inv.a12 = m21 / det;
/* 388 */     inv.a13 = m31 / det;
/* 389 */     inv.a14 = m41 / det;
/* 390 */     inv.a21 = m12 / det;
/* 391 */     inv.a22 = m22 / det;
/* 392 */     inv.a23 = m32 / det;
/* 393 */     inv.a24 = m42 / det;
/* 394 */     inv.a31 = m13 / det;
/* 395 */     inv.a32 = m23 / det;
/* 396 */     inv.a33 = m33 / det;
/* 397 */     inv.a34 = m43 / det;
/* 398 */     inv.a41 = m14 / det;
/* 399 */     inv.a42 = m24 / det;
/* 400 */     inv.a43 = m34 / det;
/* 401 */     inv.a44 = m44 / det;
/*     */     
/* 403 */     return (!Double.isNaN(det) && !Double.isInfinite(det));
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
/*     */   public static double det(FixedMatrix4x4_64F mat) {
/* 416 */     double a11 = mat.a22;
/* 417 */     double a12 = mat.a23;
/* 418 */     double a13 = mat.a24;
/* 419 */     double a21 = mat.a32;
/* 420 */     double a22 = mat.a33;
/* 421 */     double a23 = mat.a34;
/* 422 */     double a31 = mat.a42;
/* 423 */     double a32 = mat.a43;
/* 424 */     double a33 = mat.a44;
/*     */     
/* 426 */     double ret = 0.0D;
/* 427 */     ret += mat.a11 * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
/* 428 */     a11 = mat.a21;
/* 429 */     a21 = mat.a31;
/* 430 */     a31 = mat.a41;
/* 431 */     ret -= mat.a12 * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
/* 432 */     a12 = mat.a22;
/* 433 */     a22 = mat.a32;
/* 434 */     a32 = mat.a42;
/* 435 */     ret += mat.a13 * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
/* 436 */     a13 = mat.a23;
/* 437 */     a23 = mat.a33;
/* 438 */     a33 = mat.a43;
/* 439 */     ret -= mat.a14 * (a11 * (a22 * a33 - a23 * a32) - a12 * (a21 * a33 - a23 * a31) + a13 * (a21 * a32 - a22 * a31));
/*     */     
/* 441 */     return ret;
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
/*     */   public static double trace(FixedMatrix4x4_64F a) {
/* 457 */     return a.a11 + a.a21 + a.a31 + a.a41;
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
/*     */   public static void diag(FixedMatrix4x4_64F input, FixedMatrix4_64F out) {
/* 471 */     out.a1 = input.a11;
/* 472 */     out.a2 = input.a22;
/* 473 */     out.a3 = input.a33;
/* 474 */     out.a4 = input.a44;
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
/*     */   public static double elementMax(FixedMatrix4x4_64F a) {
/* 488 */     double max = a.a11;
/* 489 */     max = Math.max(max, a.a12);
/* 490 */     max = Math.max(max, a.a13);
/* 491 */     max = Math.max(max, a.a14);
/* 492 */     max = Math.max(max, a.a21);
/* 493 */     max = Math.max(max, a.a22);
/* 494 */     max = Math.max(max, a.a23);
/* 495 */     max = Math.max(max, a.a24);
/* 496 */     max = Math.max(max, a.a31);
/* 497 */     max = Math.max(max, a.a32);
/* 498 */     max = Math.max(max, a.a33);
/* 499 */     max = Math.max(max, a.a34);
/* 500 */     max = Math.max(max, a.a41);
/* 501 */     max = Math.max(max, a.a42);
/* 502 */     max = Math.max(max, a.a43);
/* 503 */     max = Math.max(max, a.a44);
/*     */     
/* 505 */     return max;
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
/*     */   public static double elementMaxAbs(FixedMatrix4x4_64F a) {
/* 519 */     double max = a.a11;
/* 520 */     max = Math.max(max, Math.abs(a.a12));
/* 521 */     max = Math.max(max, Math.abs(a.a13));
/* 522 */     max = Math.max(max, Math.abs(a.a14));
/* 523 */     max = Math.max(max, Math.abs(a.a21));
/* 524 */     max = Math.max(max, Math.abs(a.a22));
/* 525 */     max = Math.max(max, Math.abs(a.a23));
/* 526 */     max = Math.max(max, Math.abs(a.a24));
/* 527 */     max = Math.max(max, Math.abs(a.a31));
/* 528 */     max = Math.max(max, Math.abs(a.a32));
/* 529 */     max = Math.max(max, Math.abs(a.a33));
/* 530 */     max = Math.max(max, Math.abs(a.a34));
/* 531 */     max = Math.max(max, Math.abs(a.a41));
/* 532 */     max = Math.max(max, Math.abs(a.a42));
/* 533 */     max = Math.max(max, Math.abs(a.a43));
/* 534 */     max = Math.max(max, Math.abs(a.a44));
/*     */     
/* 536 */     return max;
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
/*     */   public static double elementMin(FixedMatrix4x4_64F a) {
/* 550 */     double min = a.a11;
/* 551 */     min = Math.min(min, a.a12);
/* 552 */     min = Math.min(min, a.a13);
/* 553 */     min = Math.min(min, a.a14);
/* 554 */     min = Math.min(min, a.a21);
/* 555 */     min = Math.min(min, a.a22);
/* 556 */     min = Math.min(min, a.a23);
/* 557 */     min = Math.min(min, a.a24);
/* 558 */     min = Math.min(min, a.a31);
/* 559 */     min = Math.min(min, a.a32);
/* 560 */     min = Math.min(min, a.a33);
/* 561 */     min = Math.min(min, a.a34);
/* 562 */     min = Math.min(min, a.a41);
/* 563 */     min = Math.min(min, a.a42);
/* 564 */     min = Math.min(min, a.a43);
/* 565 */     min = Math.min(min, a.a44);
/*     */     
/* 567 */     return min;
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
/*     */   public static double elementMinAbs(FixedMatrix4x4_64F a) {
/* 581 */     double min = a.a11;
/* 582 */     min = Math.min(min, Math.abs(a.a12));
/* 583 */     min = Math.min(min, Math.abs(a.a13));
/* 584 */     min = Math.min(min, Math.abs(a.a14));
/* 585 */     min = Math.min(min, Math.abs(a.a21));
/* 586 */     min = Math.min(min, Math.abs(a.a22));
/* 587 */     min = Math.min(min, Math.abs(a.a23));
/* 588 */     min = Math.min(min, Math.abs(a.a24));
/* 589 */     min = Math.min(min, Math.abs(a.a31));
/* 590 */     min = Math.min(min, Math.abs(a.a32));
/* 591 */     min = Math.min(min, Math.abs(a.a33));
/* 592 */     min = Math.min(min, Math.abs(a.a34));
/* 593 */     min = Math.min(min, Math.abs(a.a41));
/* 594 */     min = Math.min(min, Math.abs(a.a42));
/* 595 */     min = Math.min(min, Math.abs(a.a43));
/* 596 */     min = Math.min(min, Math.abs(a.a44));
/*     */     
/* 598 */     return min;
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
/*     */   public static void elementMult(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b) {
/* 610 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13; a.a14 *= b.a14;
/* 611 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23; a.a24 *= b.a24;
/* 612 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33; a.a34 *= b.a34;
/* 613 */     a.a41 *= b.a41; a.a42 *= b.a42; a.a43 *= b.a43; a.a44 *= b.a44;
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
/*     */   public static void elementMult(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/* 626 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13; a.a14 *= b.a14;
/* 627 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23; a.a24 *= b.a24;
/* 628 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33; a.a34 *= b.a34;
/* 629 */     a.a41 *= b.a41; a.a42 *= b.a42; a.a43 *= b.a43; a.a44 *= b.a44;
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
/*     */   public static void elementDiv(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b) {
/* 641 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13; a.a14 /= b.a14;
/* 642 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23; a.a24 /= b.a24;
/* 643 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33; a.a34 /= b.a34;
/* 644 */     a.a41 /= b.a41; a.a42 /= b.a42; a.a43 /= b.a43; a.a44 /= b.a44;
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
/*     */   public static void elementDiv(FixedMatrix4x4_64F a, FixedMatrix4x4_64F b, FixedMatrix4x4_64F c) {
/* 657 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13; a.a14 /= b.a14;
/* 658 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23; a.a24 /= b.a24;
/* 659 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33; a.a34 /= b.a34;
/* 660 */     a.a41 /= b.a41; a.a42 /= b.a42; a.a43 /= b.a43; a.a44 /= b.a44;
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
/*     */   public static void scale(double alpha, FixedMatrix4x4_64F a) {
/* 674 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha; a.a14 *= alpha;
/* 675 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha; a.a24 *= alpha;
/* 676 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha; a.a34 *= alpha;
/* 677 */     a.a41 *= alpha; a.a42 *= alpha; a.a43 *= alpha; a.a44 *= alpha;
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
/*     */   public static void scale(double alpha, FixedMatrix4x4_64F a, FixedMatrix4x4_64F b) {
/* 692 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha; a.a14 *= alpha;
/* 693 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha; a.a24 *= alpha;
/* 694 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha; a.a34 *= alpha;
/* 695 */     a.a41 *= alpha; a.a42 *= alpha; a.a43 *= alpha; a.a44 *= alpha;
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
/*     */   public static void divide(FixedMatrix4x4_64F a, double alpha) {
/* 709 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha; a.a14 /= alpha;
/* 710 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha; a.a24 /= alpha;
/* 711 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha; a.a34 /= alpha;
/* 712 */     a.a41 /= alpha; a.a42 /= alpha; a.a43 /= alpha; a.a44 /= alpha;
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
/*     */   public static void divide(FixedMatrix4x4_64F a, double alpha, FixedMatrix4x4_64F b) {
/* 727 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha; a.a14 /= alpha;
/* 728 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha; a.a24 /= alpha;
/* 729 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha; a.a34 /= alpha;
/* 730 */     a.a41 /= alpha; a.a42 /= alpha; a.a43 /= alpha; a.a44 /= alpha;
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
/*     */   public static void changeSign(FixedMatrix4x4_64F a) {
/* 744 */     a.a11 = -a.a11; a.a12 = -a.a12; a.a13 = -a.a13; a.a14 = -a.a14;
/* 745 */     a.a21 = -a.a21; a.a22 = -a.a22; a.a23 = -a.a23; a.a24 = -a.a24;
/* 746 */     a.a31 = -a.a31; a.a32 = -a.a32; a.a33 = -a.a33; a.a34 = -a.a34;
/* 747 */     a.a41 = -a.a41; a.a42 = -a.a42; a.a43 = -a.a43; a.a44 = -a.a44;
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
/*     */   public static void fill(FixedMatrix4x4_64F a, double v) {
/* 761 */     a.a11 = v; a.a12 = v; a.a13 = v; a.a14 = v;
/* 762 */     a.a21 = v; a.a22 = v; a.a23 = v; a.a24 = v;
/* 763 */     a.a31 = v; a.a32 = v; a.a33 = v; a.a34 = v;
/* 764 */     a.a41 = v; a.a42 = v; a.a43 = v; a.a44 = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\fixed\FixedOps4.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */