/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig.symm;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.EigenvalueSmall;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricQREigenHelper
/*     */ {
/*  37 */   protected Random rand = new Random(3434270L);
/*     */ 
/*     */   
/*     */   protected int steps;
/*     */ 
/*     */   
/*     */   protected int numExceptional;
/*     */ 
/*     */   
/*     */   protected int lastExceptional;
/*     */   
/*  48 */   protected EigenvalueSmall eigenSmall = new EigenvalueSmall();
/*     */   
/*     */   protected DenseMatrix64F Q;
/*     */   
/*     */   protected int N;
/*     */   
/*     */   protected double[] diag;
/*     */   
/*     */   protected double[] off;
/*     */   
/*     */   protected int x1;
/*     */   
/*     */   protected int x2;
/*     */   
/*     */   protected int[] splits;
/*     */   
/*     */   protected int numSplits;
/*     */   
/*     */   private double bulge;
/*     */   
/*     */   private double c;
/*     */   private double s;
/*     */   private double c2;
/*     */   private double s2;
/*     */   private double cs;
/*     */   
/*     */   public SymmetricQREigenHelper() {
/*  75 */     this.splits = new int[1];
/*     */   }
/*     */   
/*     */   public void printMatrix() {
/*  79 */     System.out.print("Off Diag[ "); int j;
/*  80 */     for (j = 0; j < this.N - 1; j++) {
/*  81 */       System.out.printf("%5.2f ", new Object[] { Double.valueOf(this.off[j]) });
/*     */     } 
/*  83 */     System.out.println();
/*  84 */     System.out.print("    Diag[ ");
/*  85 */     for (j = 0; j < this.N; j++) {
/*  86 */       System.out.printf("%5.2f ", new Object[] { Double.valueOf(this.diag[j]) });
/*     */     } 
/*  88 */     System.out.println();
/*     */   }
/*     */   
/*     */   public void setQ(DenseMatrix64F q) {
/*  92 */     this.Q = q;
/*     */   }
/*     */   
/*     */   public void incrementSteps() {
/*  96 */     this.steps++;
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
/*     */   public void init(double[] diag, double[] off, int numCols) {
/* 109 */     reset(numCols);
/*     */     
/* 111 */     this.diag = diag;
/* 112 */     this.off = off;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] swapDiag(double[] diag) {
/* 119 */     double[] ret = this.diag;
/* 120 */     this.diag = diag;
/*     */     
/* 122 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] swapOff(double[] off) {
/* 129 */     double[] ret = this.off;
/* 130 */     this.off = off;
/*     */     
/* 132 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(int N) {
/* 140 */     this.N = N;
/*     */     
/* 142 */     this.diag = null;
/* 143 */     this.off = null;
/*     */     
/* 145 */     if (this.splits.length < N) {
/* 146 */       this.splits = new int[N];
/*     */     }
/*     */     
/* 149 */     this.numSplits = 0;
/*     */     
/* 151 */     this.x1 = 0;
/* 152 */     this.x2 = N - 1;
/*     */     
/* 154 */     this.steps = this.numExceptional = this.lastExceptional = 0;
/*     */     
/* 156 */     this.Q = null;
/*     */   }
/*     */   
/*     */   public double[] copyDiag(double[] ret) {
/* 160 */     if (ret == null || ret.length < this.N) {
/* 161 */       ret = new double[this.N];
/*     */     }
/*     */     
/* 164 */     System.arraycopy(this.diag, 0, ret, 0, this.N);
/*     */     
/* 166 */     return ret;
/*     */   }
/*     */   
/*     */   public double[] copyOff(double[] ret) {
/* 170 */     if (ret == null || ret.length < this.N - 1) {
/* 171 */       ret = new double[this.N - 1];
/*     */     }
/*     */     
/* 174 */     System.arraycopy(this.off, 0, ret, 0, this.N - 1);
/*     */     
/* 176 */     return ret;
/*     */   }
/*     */   
/*     */   public double[] copyEigenvalues(double[] ret) {
/* 180 */     if (ret == null || ret.length < this.N) {
/* 181 */       ret = new double[this.N];
/*     */     }
/*     */     
/* 184 */     System.arraycopy(this.diag, 0, ret, 0, this.N);
/*     */     
/* 186 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubmatrix(int x1, int x2) {
/* 195 */     this.x1 = x1;
/* 196 */     this.x2 = x2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isZero(int index) {
/* 203 */     double bottom = Math.abs(this.diag[index]) + Math.abs(this.diag[index + 1]);
/*     */     
/* 205 */     return (Math.abs(this.off[index]) <= bottom * UtilEjml.EPS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void performImplicitSingleStep(double lambda, boolean byAngle) {
/* 210 */     if (this.x2 - this.x1 == 1) {
/* 211 */       createBulge2by2(this.x1, lambda, byAngle);
/*     */     } else {
/* 213 */       createBulge(this.x1, lambda, byAngle);
/*     */       
/* 215 */       for (int i = this.x1; i < this.x2 - 2 && this.bulge != 0.0D; i++) {
/* 216 */         removeBulge(i);
/*     */       }
/*     */       
/* 219 */       if (this.bulge != 0.0D) {
/* 220 */         removeBulgeEnd(this.x2 - 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateQ(int m, int n, double c, double s) {
/* 226 */     int rowA = m * this.N;
/* 227 */     int rowB = n * this.N;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     int endA = rowA + this.N;
/* 236 */     while (rowA < endA) {
/* 237 */       double a = this.Q.data[rowA];
/* 238 */       double b = this.Q.data[rowB];
/* 239 */       this.Q.data[rowA++] = c * a + s * b;
/* 240 */       this.Q.data[rowB++] = -s * a + c * b;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBulge(int x1, double p, boolean byAngle) {
/* 248 */     double a11 = this.diag[x1];
/* 249 */     double a22 = this.diag[x1 + 1];
/* 250 */     double a12 = this.off[x1];
/* 251 */     double a23 = this.off[x1 + 1];
/*     */     
/* 253 */     if (byAngle) {
/* 254 */       this.c = Math.cos(p);
/* 255 */       this.s = Math.sin(p);
/*     */       
/* 257 */       this.c2 = this.c * this.c;
/* 258 */       this.s2 = this.s * this.s;
/* 259 */       this.cs = this.c * this.s;
/*     */     } else {
/* 261 */       computeRotation(a11 - p, a12);
/*     */     } 
/*     */ 
/*     */     
/* 265 */     this.diag[x1] = this.c2 * a11 + 2.0D * this.cs * a12 + this.s2 * a22;
/* 266 */     this.diag[x1 + 1] = this.c2 * a22 - 2.0D * this.cs * a12 + this.s2 * a11;
/* 267 */     this.off[x1] = a12 * (this.c2 - this.s2) + this.cs * (a22 - a11);
/* 268 */     this.off[x1 + 1] = this.c * a23;
/* 269 */     this.bulge = this.s * a23;
/*     */     
/* 271 */     if (this.Q != null)
/* 272 */       updateQ(x1, x1 + 1, this.c, this.s); 
/*     */   }
/*     */   
/*     */   protected void createBulge2by2(int x1, double p, boolean byAngle) {
/* 276 */     double a11 = this.diag[x1];
/* 277 */     double a22 = this.diag[x1 + 1];
/* 278 */     double a12 = this.off[x1];
/*     */     
/* 280 */     if (byAngle) {
/* 281 */       this.c = Math.cos(p);
/* 282 */       this.s = Math.sin(p);
/*     */       
/* 284 */       this.c2 = this.c * this.c;
/* 285 */       this.s2 = this.s * this.s;
/* 286 */       this.cs = this.c * this.s;
/*     */     } else {
/* 288 */       computeRotation(a11 - p, a12);
/*     */     } 
/*     */ 
/*     */     
/* 292 */     this.diag[x1] = this.c2 * a11 + 2.0D * this.cs * a12 + this.s2 * a22;
/* 293 */     this.diag[x1 + 1] = this.c2 * a22 - 2.0D * this.cs * a12 + this.s2 * a11;
/* 294 */     this.off[x1] = a12 * (this.c2 - this.s2) + this.cs * (a22 - a11);
/*     */     
/* 296 */     if (this.Q != null) {
/* 297 */       updateQ(x1, x1 + 1, this.c, this.s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeRotation(double run, double rise) {
/* 308 */     if (Math.abs(rise) > Math.abs(run)) {
/* 309 */       double k = run / rise;
/*     */       
/* 311 */       double bottom = 1.0D + k * k;
/* 312 */       double bottom_sq = Math.sqrt(bottom);
/*     */       
/* 314 */       this.s2 = 1.0D / bottom;
/* 315 */       this.c2 = k * k / bottom;
/* 316 */       this.cs = k / bottom;
/* 317 */       this.s = 1.0D / bottom_sq;
/* 318 */       this.c = k / bottom_sq;
/*     */     } else {
/* 320 */       double t = rise / run;
/*     */       
/* 322 */       double bottom = 1.0D + t * t;
/* 323 */       double bottom_sq = Math.sqrt(bottom);
/*     */       
/* 325 */       this.c2 = 1.0D / bottom;
/* 326 */       this.s2 = t * t / bottom;
/* 327 */       this.cs = t / bottom;
/* 328 */       this.c = 1.0D / bottom_sq;
/* 329 */       this.s = t / bottom_sq;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeBulge(int x1) {
/* 334 */     double a22 = this.diag[x1 + 1];
/* 335 */     double a33 = this.diag[x1 + 2];
/* 336 */     double a12 = this.off[x1];
/* 337 */     double a23 = this.off[x1 + 1];
/* 338 */     double a34 = this.off[x1 + 2];
/*     */     
/* 340 */     computeRotation(a12, this.bulge);
/*     */ 
/*     */     
/* 343 */     this.diag[x1 + 1] = this.c2 * a22 + 2.0D * this.cs * a23 + this.s2 * a33;
/* 344 */     this.diag[x1 + 2] = this.c2 * a33 - 2.0D * this.cs * a23 + this.s2 * a22;
/* 345 */     this.off[x1] = this.c * a12 + this.s * this.bulge;
/* 346 */     this.off[x1 + 1] = a23 * (this.c2 - this.s2) + this.cs * (a33 - a22);
/* 347 */     this.off[x1 + 2] = this.c * a34;
/* 348 */     this.bulge = this.s * a34;
/*     */     
/* 350 */     if (this.Q != null) {
/* 351 */       updateQ(x1 + 1, x1 + 2, this.c, this.s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeBulgeEnd(int x1) {
/* 358 */     double a22 = this.diag[x1 + 1];
/* 359 */     double a12 = this.off[x1];
/* 360 */     double a23 = this.off[x1 + 1];
/* 361 */     double a33 = this.diag[x1 + 2];
/*     */     
/* 363 */     computeRotation(a12, this.bulge);
/*     */ 
/*     */     
/* 366 */     this.diag[x1 + 1] = this.c2 * a22 + 2.0D * this.cs * a23 + this.s2 * a33;
/* 367 */     this.diag[x1 + 2] = this.c2 * a33 - 2.0D * this.cs * a23 + this.s2 * a22;
/* 368 */     this.off[x1] = this.c * a12 + this.s * this.bulge;
/* 369 */     this.off[x1 + 1] = a23 * (this.c2 - this.s2) + this.cs * (a33 - a22);
/*     */     
/* 371 */     if (this.Q != null) {
/* 372 */       updateQ(x1 + 1, x1 + 2, this.c, this.s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void eigenvalue2by2(int x1) {
/* 379 */     double a = this.diag[x1];
/* 380 */     double b = this.off[x1];
/* 381 */     double c = this.diag[x1 + 1];
/*     */ 
/*     */     
/* 384 */     double absA = Math.abs(a);
/* 385 */     double absB = Math.abs(b);
/* 386 */     double absC = Math.abs(c);
/*     */     
/* 388 */     double scale = (absA > absB) ? absA : absB;
/* 389 */     if (absC > scale) scale = absC;
/*     */ 
/*     */ 
/*     */     
/* 393 */     if (scale == 0.0D) {
/* 394 */       this.off[x1] = 0.0D;
/* 395 */       this.diag[x1] = 0.0D;
/* 396 */       this.diag[x1 + 1] = 0.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 400 */     a /= scale;
/* 401 */     b /= scale;
/* 402 */     c /= scale;
/*     */     
/* 404 */     this.eigenSmall.symm2x2_fast(a, b, c);
/*     */     
/* 406 */     this.off[x1] = 0.0D;
/* 407 */     this.diag[x1] = scale * this.eigenSmall.value0.real;
/* 408 */     this.diag[x1 + 1] = scale * this.eigenSmall.value1.real;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionalShift() {
/* 418 */     this.numExceptional++;
/* 419 */     double mag = 0.05D * this.numExceptional;
/* 420 */     if (mag > 1.0D) mag = 1.0D;
/*     */     
/* 422 */     double theta = 2.0D * (this.rand.nextDouble() - 0.5D) * mag;
/* 423 */     performImplicitSingleStep(theta, true);
/*     */     
/* 425 */     this.lastExceptional = this.steps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextSplit() {
/* 433 */     if (this.numSplits == 0)
/* 434 */       return false; 
/* 435 */     this.x2 = this.splits[--this.numSplits];
/* 436 */     if (this.numSplits > 0) {
/* 437 */       this.x1 = this.splits[this.numSplits - 1] + 1;
/*     */     } else {
/* 439 */       this.x1 = 0;
/*     */     } 
/* 441 */     return true;
/*     */   }
/*     */   
/*     */   public double computeShift() {
/* 445 */     if (this.x2 - this.x1 >= 1) {
/* 446 */       return computeWilkinsonShift();
/*     */     }
/* 448 */     return this.diag[this.x2];
/*     */   }
/*     */   
/*     */   public double computeWilkinsonShift() {
/* 452 */     double a = this.diag[this.x2 - 1];
/* 453 */     double b = this.off[this.x2 - 1];
/* 454 */     double c = this.diag[this.x2];
/*     */ 
/*     */     
/* 457 */     double absA = Math.abs(a);
/* 458 */     double absB = Math.abs(b);
/* 459 */     double absC = Math.abs(c);
/*     */     
/* 461 */     double scale = (absA > absB) ? absA : absB;
/* 462 */     if (absC > scale) scale = absC;
/*     */     
/* 464 */     if (scale == 0.0D) {
/* 465 */       throw new RuntimeException("this should never happen");
/*     */     }
/*     */     
/* 468 */     a /= scale;
/* 469 */     b /= scale;
/* 470 */     c /= scale;
/*     */ 
/*     */ 
/*     */     
/* 474 */     this.eigenSmall.symm2x2_fast(a, b, c);
/*     */ 
/*     */     
/* 477 */     double diff0 = Math.abs(this.eigenSmall.value0.real - c);
/* 478 */     double diff1 = Math.abs(this.eigenSmall.value1.real - c);
/*     */     
/* 480 */     if (diff0 < diff1) {
/* 481 */       return scale * this.eigenSmall.value0.real;
/*     */     }
/* 483 */     return scale * this.eigenSmall.value1.real;
/*     */   }
/*     */   
/*     */   public int getMatrixSize() {
/* 487 */     return this.N;
/*     */   }
/*     */   
/*     */   public void resetSteps() {
/* 491 */     this.steps = 0;
/* 492 */     this.lastExceptional = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\symm\SymmetricQREigenHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */