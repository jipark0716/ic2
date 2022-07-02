/*     */ package ic2.shades.org.ejml.alg.fixed;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.FixedMatrix3_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix3x3_64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FixedOps3
/*     */ {
/*     */   public static void add(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/*  46 */     a.a11 += b.a11;
/*  47 */     a.a12 += b.a12;
/*  48 */     a.a13 += b.a13;
/*  49 */     a.a21 += b.a21;
/*  50 */     a.a22 += b.a22;
/*  51 */     a.a23 += b.a23;
/*  52 */     a.a31 += b.a31;
/*  53 */     a.a32 += b.a32;
/*  54 */     a.a33 += b.a33;
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
/*     */   public static void addEquals(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b) {
/*  68 */     a.a11 += b.a11;
/*  69 */     a.a12 += b.a12;
/*  70 */     a.a13 += b.a13;
/*  71 */     a.a21 += b.a21;
/*  72 */     a.a22 += b.a22;
/*  73 */     a.a23 += b.a23;
/*  74 */     a.a31 += b.a31;
/*  75 */     a.a32 += b.a32;
/*  76 */     a.a33 += b.a33;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void transpose(FixedMatrix3x3_64F m) {
/*  87 */     double tmp = m.a12; m.a12 = m.a21; m.a21 = tmp;
/*  88 */     tmp = m.a13; m.a13 = m.a31; m.a31 = tmp;
/*  89 */     tmp = m.a23; m.a23 = m.a32; m.a32 = tmp;
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
/*     */   public static FixedMatrix3x3_64F transpose(FixedMatrix3x3_64F input, FixedMatrix3x3_64F output) {
/* 105 */     if (input == null) {
/* 106 */       input = new FixedMatrix3x3_64F();
/*     */     }
/* 108 */     output.a11 = input.a11;
/* 109 */     output.a12 = input.a21;
/* 110 */     output.a13 = input.a31;
/* 111 */     output.a21 = input.a12;
/* 112 */     output.a22 = input.a22;
/* 113 */     output.a23 = input.a32;
/* 114 */     output.a31 = input.a13;
/* 115 */     output.a32 = input.a23;
/* 116 */     output.a33 = input.a33;
/*     */     
/* 118 */     return output;
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
/*     */   public static void mult(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/* 134 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a21 + a.a13 * b.a31;
/* 135 */     c.a12 = a.a11 * b.a12 + a.a12 * b.a22 + a.a13 * b.a32;
/* 136 */     c.a13 = a.a11 * b.a13 + a.a12 * b.a23 + a.a13 * b.a33;
/* 137 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a21 + a.a23 * b.a31;
/* 138 */     c.a22 = a.a21 * b.a12 + a.a22 * b.a22 + a.a23 * b.a32;
/* 139 */     c.a23 = a.a21 * b.a13 + a.a22 * b.a23 + a.a23 * b.a33;
/* 140 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a21 + a.a33 * b.a31;
/* 141 */     c.a32 = a.a31 * b.a12 + a.a32 * b.a22 + a.a33 * b.a32;
/* 142 */     c.a33 = a.a31 * b.a13 + a.a32 * b.a23 + a.a33 * b.a33;
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
/*     */   public static void multTransA(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/* 158 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a21 + a.a31 * b.a31;
/* 159 */     c.a12 = a.a11 * b.a12 + a.a21 * b.a22 + a.a31 * b.a32;
/* 160 */     c.a13 = a.a11 * b.a13 + a.a21 * b.a23 + a.a31 * b.a33;
/* 161 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a21 + a.a32 * b.a31;
/* 162 */     c.a22 = a.a12 * b.a12 + a.a22 * b.a22 + a.a32 * b.a32;
/* 163 */     c.a23 = a.a12 * b.a13 + a.a22 * b.a23 + a.a32 * b.a33;
/* 164 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a21 + a.a33 * b.a31;
/* 165 */     c.a32 = a.a13 * b.a12 + a.a23 * b.a22 + a.a33 * b.a32;
/* 166 */     c.a33 = a.a13 * b.a13 + a.a23 * b.a23 + a.a33 * b.a33;
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
/*     */   public static void multTransAB(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/* 182 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a12 + a.a31 * b.a13;
/* 183 */     c.a12 = a.a11 * b.a21 + a.a21 * b.a22 + a.a31 * b.a23;
/* 184 */     c.a13 = a.a11 * b.a31 + a.a21 * b.a32 + a.a31 * b.a33;
/* 185 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a12 + a.a32 * b.a13;
/* 186 */     c.a22 = a.a12 * b.a21 + a.a22 * b.a22 + a.a32 * b.a23;
/* 187 */     c.a23 = a.a12 * b.a31 + a.a22 * b.a32 + a.a32 * b.a33;
/* 188 */     c.a31 = a.a13 * b.a11 + a.a23 * b.a12 + a.a33 * b.a13;
/* 189 */     c.a32 = a.a13 * b.a21 + a.a23 * b.a22 + a.a33 * b.a23;
/* 190 */     c.a33 = a.a13 * b.a31 + a.a23 * b.a32 + a.a33 * b.a33;
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
/*     */   public static void multTransB(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/* 206 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a12 + a.a13 * b.a13;
/* 207 */     c.a12 = a.a11 * b.a21 + a.a12 * b.a22 + a.a13 * b.a23;
/* 208 */     c.a13 = a.a11 * b.a31 + a.a12 * b.a32 + a.a13 * b.a33;
/* 209 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a12 + a.a23 * b.a13;
/* 210 */     c.a22 = a.a21 * b.a21 + a.a22 * b.a22 + a.a23 * b.a23;
/* 211 */     c.a23 = a.a21 * b.a31 + a.a22 * b.a32 + a.a23 * b.a33;
/* 212 */     c.a31 = a.a31 * b.a11 + a.a32 * b.a12 + a.a33 * b.a13;
/* 213 */     c.a32 = a.a31 * b.a21 + a.a32 * b.a22 + a.a33 * b.a23;
/* 214 */     c.a33 = a.a31 * b.a31 + a.a32 * b.a32 + a.a33 * b.a33;
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
/*     */   public static void mult(FixedMatrix3x3_64F a, FixedMatrix3_64F b, FixedMatrix3_64F c) {
/* 230 */     c.a1 = a.a11 * b.a1 + a.a12 * b.a2 + a.a13 * b.a3;
/* 231 */     c.a2 = a.a21 * b.a1 + a.a22 * b.a2 + a.a23 * b.a3;
/* 232 */     c.a3 = a.a31 * b.a1 + a.a32 * b.a2 + a.a33 * b.a3;
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
/*     */   public static void mult(FixedMatrix3_64F a, FixedMatrix3x3_64F b, FixedMatrix3_64F c) {
/* 248 */     c.a1 = a.a1 * b.a11 + a.a2 * b.a21 + a.a3 * b.a31;
/* 249 */     c.a2 = a.a1 * b.a12 + a.a2 * b.a22 + a.a3 * b.a32;
/* 250 */     c.a3 = a.a1 * b.a13 + a.a2 * b.a23 + a.a3 * b.a33;
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
/*     */   public static double dot(FixedMatrix3_64F a, FixedMatrix3_64F b) {
/* 266 */     return a.a1 * b.a1 + a.a2 * b.a2 + a.a3 * b.a3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity(FixedMatrix3x3_64F a) {
/* 276 */     a.a11 = 1.0D; a.a21 = 0.0D; a.a31 = 0.0D;
/* 277 */     a.a12 = 0.0D; a.a22 = 1.0D; a.a32 = 0.0D;
/* 278 */     a.a13 = 0.0D; a.a23 = 0.0D; a.a33 = 1.0D;
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
/*     */   public static boolean invert(FixedMatrix3x3_64F a, FixedMatrix3x3_64F inv) {
/* 293 */     double scale = 1.0D / elementMaxAbs(a);
/*     */     
/* 295 */     double a11 = a.a11 * scale;
/* 296 */     double a12 = a.a12 * scale;
/* 297 */     double a13 = a.a13 * scale;
/* 298 */     double a21 = a.a21 * scale;
/* 299 */     double a22 = a.a22 * scale;
/* 300 */     double a23 = a.a23 * scale;
/* 301 */     double a31 = a.a31 * scale;
/* 302 */     double a32 = a.a32 * scale;
/* 303 */     double a33 = a.a33 * scale;
/*     */     
/* 305 */     double m11 = a22 * a33 - a23 * a32;
/* 306 */     double m12 = -(a21 * a33 - a23 * a31);
/* 307 */     double m13 = a21 * a32 - a22 * a31;
/* 308 */     double m21 = -(a12 * a33 - a13 * a32);
/* 309 */     double m22 = a11 * a33 - a13 * a31;
/* 310 */     double m23 = -(a11 * a32 - a12 * a31);
/* 311 */     double m31 = a12 * a23 - a13 * a22;
/* 312 */     double m32 = -(a11 * a23 - a13 * a21);
/* 313 */     double m33 = a11 * a22 - a12 * a21;
/*     */     
/* 315 */     double det = (a11 * m11 + a12 * m12 + a13 * m13) / scale;
/*     */     
/* 317 */     inv.a11 = m11 / det;
/* 318 */     inv.a12 = m21 / det;
/* 319 */     inv.a13 = m31 / det;
/* 320 */     inv.a21 = m12 / det;
/* 321 */     inv.a22 = m22 / det;
/* 322 */     inv.a23 = m32 / det;
/* 323 */     inv.a31 = m13 / det;
/* 324 */     inv.a32 = m23 / det;
/* 325 */     inv.a33 = m33 / det;
/*     */     
/* 327 */     return (!Double.isNaN(det) && !Double.isInfinite(det));
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
/*     */   public static double det(FixedMatrix3x3_64F mat) {
/* 340 */     double a = mat.a11 * (mat.a22 * mat.a33 - mat.a23 * mat.a32);
/* 341 */     double b = mat.a12 * (mat.a21 * mat.a33 - mat.a23 * mat.a31);
/* 342 */     double c = mat.a13 * (mat.a21 * mat.a32 - mat.a31 * mat.a22);
/*     */     
/* 344 */     return a - b + c;
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
/*     */   public static double trace(FixedMatrix3x3_64F a) {
/* 360 */     return a.a11 + a.a21 + a.a31;
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
/*     */   public static void diag(FixedMatrix3x3_64F input, FixedMatrix3_64F out) {
/* 374 */     out.a1 = input.a11;
/* 375 */     out.a2 = input.a22;
/* 376 */     out.a3 = input.a33;
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
/*     */   public static double elementMax(FixedMatrix3x3_64F a) {
/* 390 */     double max = a.a11;
/* 391 */     max = Math.max(max, a.a12);
/* 392 */     max = Math.max(max, a.a13);
/* 393 */     max = Math.max(max, a.a21);
/* 394 */     max = Math.max(max, a.a22);
/* 395 */     max = Math.max(max, a.a23);
/* 396 */     max = Math.max(max, a.a31);
/* 397 */     max = Math.max(max, a.a32);
/* 398 */     max = Math.max(max, a.a33);
/*     */     
/* 400 */     return max;
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
/*     */   public static double elementMaxAbs(FixedMatrix3x3_64F a) {
/* 414 */     double max = a.a11;
/* 415 */     max = Math.max(max, Math.abs(a.a12));
/* 416 */     max = Math.max(max, Math.abs(a.a13));
/* 417 */     max = Math.max(max, Math.abs(a.a21));
/* 418 */     max = Math.max(max, Math.abs(a.a22));
/* 419 */     max = Math.max(max, Math.abs(a.a23));
/* 420 */     max = Math.max(max, Math.abs(a.a31));
/* 421 */     max = Math.max(max, Math.abs(a.a32));
/* 422 */     max = Math.max(max, Math.abs(a.a33));
/*     */     
/* 424 */     return max;
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
/*     */   public static double elementMin(FixedMatrix3x3_64F a) {
/* 438 */     double min = a.a11;
/* 439 */     min = Math.min(min, a.a12);
/* 440 */     min = Math.min(min, a.a13);
/* 441 */     min = Math.min(min, a.a21);
/* 442 */     min = Math.min(min, a.a22);
/* 443 */     min = Math.min(min, a.a23);
/* 444 */     min = Math.min(min, a.a31);
/* 445 */     min = Math.min(min, a.a32);
/* 446 */     min = Math.min(min, a.a33);
/*     */     
/* 448 */     return min;
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
/*     */   public static double elementMinAbs(FixedMatrix3x3_64F a) {
/* 462 */     double min = a.a11;
/* 463 */     min = Math.min(min, Math.abs(a.a12));
/* 464 */     min = Math.min(min, Math.abs(a.a13));
/* 465 */     min = Math.min(min, Math.abs(a.a21));
/* 466 */     min = Math.min(min, Math.abs(a.a22));
/* 467 */     min = Math.min(min, Math.abs(a.a23));
/* 468 */     min = Math.min(min, Math.abs(a.a31));
/* 469 */     min = Math.min(min, Math.abs(a.a32));
/* 470 */     min = Math.min(min, Math.abs(a.a33));
/*     */     
/* 472 */     return min;
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
/*     */   public static void elementMult(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b) {
/* 484 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13;
/* 485 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23;
/* 486 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33;
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
/*     */   public static void elementMult(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/* 499 */     a.a11 *= b.a11; a.a12 *= b.a12; a.a13 *= b.a13;
/* 500 */     a.a21 *= b.a21; a.a22 *= b.a22; a.a23 *= b.a23;
/* 501 */     a.a31 *= b.a31; a.a32 *= b.a32; a.a33 *= b.a33;
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
/*     */   public static void elementDiv(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b) {
/* 513 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13;
/* 514 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23;
/* 515 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33;
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
/*     */   public static void elementDiv(FixedMatrix3x3_64F a, FixedMatrix3x3_64F b, FixedMatrix3x3_64F c) {
/* 528 */     a.a11 /= b.a11; a.a12 /= b.a12; a.a13 /= b.a13;
/* 529 */     a.a21 /= b.a21; a.a22 /= b.a22; a.a23 /= b.a23;
/* 530 */     a.a31 /= b.a31; a.a32 /= b.a32; a.a33 /= b.a33;
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
/*     */   public static void scale(double alpha, FixedMatrix3x3_64F a) {
/* 544 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha;
/* 545 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha;
/* 546 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha;
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
/*     */   public static void scale(double alpha, FixedMatrix3x3_64F a, FixedMatrix3x3_64F b) {
/* 561 */     a.a11 *= alpha; a.a12 *= alpha; a.a13 *= alpha;
/* 562 */     a.a21 *= alpha; a.a22 *= alpha; a.a23 *= alpha;
/* 563 */     a.a31 *= alpha; a.a32 *= alpha; a.a33 *= alpha;
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
/*     */   public static void divide(FixedMatrix3x3_64F a, double alpha) {
/* 577 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha;
/* 578 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha;
/* 579 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha;
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
/*     */   public static void divide(FixedMatrix3x3_64F a, double alpha, FixedMatrix3x3_64F b) {
/* 594 */     a.a11 /= alpha; a.a12 /= alpha; a.a13 /= alpha;
/* 595 */     a.a21 /= alpha; a.a22 /= alpha; a.a23 /= alpha;
/* 596 */     a.a31 /= alpha; a.a32 /= alpha; a.a33 /= alpha;
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
/*     */   public static void changeSign(FixedMatrix3x3_64F a) {
/* 610 */     a.a11 = -a.a11; a.a12 = -a.a12; a.a13 = -a.a13;
/* 611 */     a.a21 = -a.a21; a.a22 = -a.a22; a.a23 = -a.a23;
/* 612 */     a.a31 = -a.a31; a.a32 = -a.a32; a.a33 = -a.a33;
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
/*     */   public static void fill(FixedMatrix3x3_64F a, double v) {
/* 626 */     a.a11 = v; a.a12 = v; a.a13 = v;
/* 627 */     a.a21 = v; a.a22 = v; a.a23 = v;
/* 628 */     a.a31 = v; a.a32 = v; a.a33 = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\fixed\FixedOps3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */