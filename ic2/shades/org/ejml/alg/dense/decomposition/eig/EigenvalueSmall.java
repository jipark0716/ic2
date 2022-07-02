/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenvalueSmall
/*     */ {
/*  29 */   public Complex64F value0 = new Complex64F();
/*  30 */   public Complex64F value1 = new Complex64F();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void value2x2(double a11, double a12, double a21, double a22) {
/*  39 */     double s = 1.0D / Math.sqrt(2.0D), c = s;
/*     */     
/*  41 */     double aa = a11 - a22;
/*  42 */     double bb = a12 + a21;
/*     */     
/*  44 */     double t_hat = aa / bb;
/*  45 */     double t = t_hat / (1.0D + Math.sqrt(1.0D + t_hat * t_hat));
/*     */     
/*  47 */     c = 1.0D / Math.sqrt(1.0D + t * t);
/*  48 */     s = c * t;
/*     */ 
/*     */     
/*  51 */     double c2 = c * c;
/*  52 */     double s2 = s * s;
/*  53 */     double cs = c * s;
/*     */     
/*  55 */     double b11 = c2 * a11 + s2 * a22 - cs * (a12 + a21);
/*  56 */     double b12 = c2 * a12 - s2 * a21 + cs * (a11 - a22);
/*  57 */     double b21 = c2 * a21 - s2 * a12 + cs * (a11 - a22);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (b12 == 0.0D) {
/*  63 */       c = 0.0D;
/*  64 */       s = 1.0D;
/*     */     } else {
/*  66 */       s = Math.sqrt(b21 / (b12 + b21));
/*  67 */       c = Math.sqrt(b12 / (b12 + b21));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  72 */     cs = c * s;
/*     */     
/*  74 */     a11 = b11 - cs * (b12 + b21);
/*     */ 
/*     */     
/*  77 */     a22 = b11 + cs * (b12 + b21);
/*     */     
/*  79 */     this.value0.real = a11;
/*  80 */     this.value1.real = a22;
/*     */     
/*  82 */     this.value1.imaginary = 0.0D;
/*     */ 
/*     */     
/*  85 */     this.value1.real = b11;
/*  86 */     this.value0.imaginary = Math.sqrt(-b21 * b12);
/*  87 */     this.value1.imaginary = -this.value0.imaginary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void value2x2_fast(double a11, double a12, double a21, double a22) {
/*  97 */     double left = (a11 + a22) / 2.0D;
/*  98 */     double inside = 4.0D * a12 * a21 + (a11 - a22) * (a11 - a22);
/*     */ 
/*     */     
/* 101 */     this.value1.real = left;
/* 102 */     this.value0.imaginary = Math.sqrt(-inside) / 2.0D;
/* 103 */     this.value1.imaginary = -this.value0.imaginary;
/*     */     
/* 105 */     double right = Math.sqrt(inside) / 2.0D;
/* 106 */     this.value0.real = left + right;
/* 107 */     this.value1.real = left - right;
/* 108 */     this.value1.imaginary = 0.0D;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void symm2x2_fast(double a11, double a12, double a22) {
/* 127 */     double left = (a11 + a22) * 0.5D;
/* 128 */     double b = (a11 - a22) * 0.5D;
/* 129 */     double right = Math.sqrt(b * b + a12 * a12);
/* 130 */     this.value0.real = left + right;
/* 131 */     this.value1.real = left - right;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\EigenvalueSmall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */