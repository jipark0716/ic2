/*     */ package ic2.shades.org.ejml.alg.fixed;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.FixedMatrix2_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix2x2_64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FixedOps2
/*     */ {
/*     */   public static void add(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/*  46 */     a.a11 += b.a11;
/*  47 */     a.a12 += b.a12;
/*  48 */     a.a21 += b.a21;
/*  49 */     a.a22 += b.a22;
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
/*     */   public static void addEquals(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b) {
/*  63 */     a.a11 += b.a11;
/*  64 */     a.a12 += b.a12;
/*  65 */     a.a21 += b.a21;
/*  66 */     a.a22 += b.a22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void transpose(FixedMatrix2x2_64F m) {
/*  77 */     double tmp = m.a12; m.a12 = m.a21; m.a21 = tmp;
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
/*     */   public static FixedMatrix2x2_64F transpose(FixedMatrix2x2_64F input, FixedMatrix2x2_64F output) {
/*  93 */     if (input == null) {
/*  94 */       input = new FixedMatrix2x2_64F();
/*     */     }
/*  96 */     output.a11 = input.a11;
/*  97 */     output.a12 = input.a21;
/*  98 */     output.a21 = input.a12;
/*  99 */     output.a22 = input.a22;
/*     */     
/* 101 */     return output;
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
/*     */   public static void mult(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/* 117 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a21;
/* 118 */     c.a12 = a.a11 * b.a12 + a.a12 * b.a22;
/* 119 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a21;
/* 120 */     c.a22 = a.a21 * b.a12 + a.a22 * b.a22;
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
/*     */   public static void multTransA(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/* 136 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a21;
/* 137 */     c.a12 = a.a11 * b.a12 + a.a21 * b.a22;
/* 138 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a21;
/* 139 */     c.a22 = a.a12 * b.a12 + a.a22 * b.a22;
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
/*     */   public static void multTransAB(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/* 155 */     c.a11 = a.a11 * b.a11 + a.a21 * b.a12;
/* 156 */     c.a12 = a.a11 * b.a21 + a.a21 * b.a22;
/* 157 */     c.a21 = a.a12 * b.a11 + a.a22 * b.a12;
/* 158 */     c.a22 = a.a12 * b.a21 + a.a22 * b.a22;
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
/*     */   public static void multTransB(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/* 174 */     c.a11 = a.a11 * b.a11 + a.a12 * b.a12;
/* 175 */     c.a12 = a.a11 * b.a21 + a.a12 * b.a22;
/* 176 */     c.a21 = a.a21 * b.a11 + a.a22 * b.a12;
/* 177 */     c.a22 = a.a21 * b.a21 + a.a22 * b.a22;
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
/*     */   public static void mult(FixedMatrix2x2_64F a, FixedMatrix2_64F b, FixedMatrix2_64F c) {
/* 193 */     c.a1 = a.a11 * b.a1 + a.a12 * b.a2;
/* 194 */     c.a2 = a.a21 * b.a1 + a.a22 * b.a2;
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
/*     */   public static void mult(FixedMatrix2_64F a, FixedMatrix2x2_64F b, FixedMatrix2_64F c) {
/* 210 */     c.a1 = a.a1 * b.a11 + a.a2 * b.a21;
/* 211 */     c.a2 = a.a1 * b.a12 + a.a2 * b.a22;
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
/*     */   public static double dot(FixedMatrix2_64F a, FixedMatrix2_64F b) {
/* 227 */     return a.a1 * b.a1 + a.a2 * b.a2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIdentity(FixedMatrix2x2_64F a) {
/* 237 */     a.a11 = 1.0D; a.a21 = 0.0D;
/* 238 */     a.a12 = 0.0D; a.a22 = 1.0D;
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
/*     */   public static boolean invert(FixedMatrix2x2_64F a, FixedMatrix2x2_64F inv) {
/* 253 */     double scale = 1.0D / elementMaxAbs(a);
/*     */     
/* 255 */     double a11 = a.a11 * scale;
/* 256 */     double a12 = a.a12 * scale;
/* 257 */     double a21 = a.a21 * scale;
/* 258 */     double a22 = a.a22 * scale;
/*     */     
/* 260 */     double m11 = a22;
/* 261 */     double m12 = -a21;
/* 262 */     double m21 = -a12;
/* 263 */     double m22 = a11;
/*     */     
/* 265 */     double det = (a11 * m11 + a12 * m12) / scale;
/*     */     
/* 267 */     inv.a11 = m11 / det;
/* 268 */     inv.a12 = m21 / det;
/* 269 */     inv.a21 = m12 / det;
/* 270 */     inv.a22 = m22 / det;
/*     */     
/* 272 */     return (!Double.isNaN(det) && !Double.isInfinite(det));
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
/*     */   public static double det(FixedMatrix2x2_64F mat) {
/* 285 */     return mat.a11 * mat.a22 - mat.a12 * mat.a21;
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
/*     */   public static double trace(FixedMatrix2x2_64F a) {
/* 301 */     return a.a11 + a.a21;
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
/*     */   public static void diag(FixedMatrix2x2_64F input, FixedMatrix2_64F out) {
/* 315 */     out.a1 = input.a11;
/* 316 */     out.a2 = input.a22;
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
/*     */   public static double elementMax(FixedMatrix2x2_64F a) {
/* 330 */     double max = a.a11;
/* 331 */     max = Math.max(max, a.a12);
/* 332 */     max = Math.max(max, a.a21);
/* 333 */     max = Math.max(max, a.a22);
/*     */     
/* 335 */     return max;
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
/*     */   public static double elementMaxAbs(FixedMatrix2x2_64F a) {
/* 349 */     double max = a.a11;
/* 350 */     max = Math.max(max, Math.abs(a.a12));
/* 351 */     max = Math.max(max, Math.abs(a.a21));
/* 352 */     max = Math.max(max, Math.abs(a.a22));
/*     */     
/* 354 */     return max;
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
/*     */   public static double elementMin(FixedMatrix2x2_64F a) {
/* 368 */     double min = a.a11;
/* 369 */     min = Math.min(min, a.a12);
/* 370 */     min = Math.min(min, a.a21);
/* 371 */     min = Math.min(min, a.a22);
/*     */     
/* 373 */     return min;
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
/*     */   public static double elementMinAbs(FixedMatrix2x2_64F a) {
/* 387 */     double min = a.a11;
/* 388 */     min = Math.min(min, Math.abs(a.a12));
/* 389 */     min = Math.min(min, Math.abs(a.a21));
/* 390 */     min = Math.min(min, Math.abs(a.a22));
/*     */     
/* 392 */     return min;
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
/*     */   public static void elementMult(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b) {
/* 404 */     a.a11 *= b.a11; a.a12 *= b.a12;
/* 405 */     a.a21 *= b.a21; a.a22 *= b.a22;
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
/*     */   public static void elementMult(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/* 418 */     a.a11 *= b.a11; a.a12 *= b.a12;
/* 419 */     a.a21 *= b.a21; a.a22 *= b.a22;
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
/*     */   public static void elementDiv(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b) {
/* 431 */     a.a11 /= b.a11; a.a12 /= b.a12;
/* 432 */     a.a21 /= b.a21; a.a22 /= b.a22;
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
/*     */   public static void elementDiv(FixedMatrix2x2_64F a, FixedMatrix2x2_64F b, FixedMatrix2x2_64F c) {
/* 445 */     a.a11 /= b.a11; a.a12 /= b.a12;
/* 446 */     a.a21 /= b.a21; a.a22 /= b.a22;
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
/*     */   public static void scale(double alpha, FixedMatrix2x2_64F a) {
/* 460 */     a.a11 *= alpha; a.a12 *= alpha;
/* 461 */     a.a21 *= alpha; a.a22 *= alpha;
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
/*     */   public static void scale(double alpha, FixedMatrix2x2_64F a, FixedMatrix2x2_64F b) {
/* 476 */     a.a11 *= alpha; a.a12 *= alpha;
/* 477 */     a.a21 *= alpha; a.a22 *= alpha;
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
/*     */   public static void divide(FixedMatrix2x2_64F a, double alpha) {
/* 491 */     a.a11 /= alpha; a.a12 /= alpha;
/* 492 */     a.a21 /= alpha; a.a22 /= alpha;
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
/*     */   public static void divide(FixedMatrix2x2_64F a, double alpha, FixedMatrix2x2_64F b) {
/* 507 */     a.a11 /= alpha; a.a12 /= alpha;
/* 508 */     a.a21 /= alpha; a.a22 /= alpha;
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
/*     */   public static void changeSign(FixedMatrix2x2_64F a) {
/* 522 */     a.a11 = -a.a11; a.a12 = -a.a12;
/* 523 */     a.a21 = -a.a21; a.a22 = -a.a22;
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
/*     */   public static void fill(FixedMatrix2x2_64F a, double v) {
/* 537 */     a.a11 = v; a.a12 = v;
/* 538 */     a.a21 = v; a.a22 = v;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\fixed\FixedOps2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */