/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.svd.implicitqr;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.EigenvalueSmall;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.simple.SimpleMatrix;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SvdImplicitQrAlgorithm
/*     */ {
/*  55 */   protected Random rand = new Random(3434270L);
/*     */ 
/*     */   
/*     */   protected DenseMatrix64F Ut;
/*     */ 
/*     */   
/*     */   protected DenseMatrix64F Vt;
/*     */ 
/*     */   
/*     */   protected int totalSteps;
/*     */ 
/*     */   
/*     */   protected double maxValue;
/*     */ 
/*     */   
/*     */   protected int N;
/*     */ 
/*     */   
/*  73 */   protected EigenvalueSmall eigenSmall = new EigenvalueSmall();
/*     */ 
/*     */   
/*     */   protected int numExceptional;
/*     */ 
/*     */   
/*     */   protected int nextExceptional;
/*     */ 
/*     */   
/*     */   protected double[] diag;
/*     */ 
/*     */   
/*     */   protected double[] off;
/*     */   
/*     */   double bulge;
/*     */   
/*     */   protected int x1;
/*     */   
/*     */   protected int x2;
/*     */   
/*     */   int steps;
/*     */   
/*     */   protected int[] splits;
/*     */   
/*     */   protected int numSplits;
/*     */   
/*  99 */   private int exceptionalThresh = 15;
/* 100 */   private int maxIterations = this.exceptionalThresh * 100;
/*     */ 
/*     */   
/*     */   boolean followScript;
/*     */ 
/*     */   
/*     */   private static final int giveUpOnKnown = 10;
/*     */ 
/*     */   
/*     */   private double[] values;
/*     */ 
/*     */   
/*     */   private boolean fastValues = false;
/*     */ 
/*     */   
/*     */   private boolean findingZeros;
/*     */   
/*     */   double c;
/*     */   
/*     */   double s;
/*     */ 
/*     */   
/*     */   public SvdImplicitQrAlgorithm(boolean fastValues) {
/* 123 */     this.fastValues = fastValues;
/*     */   }
/*     */ 
/*     */   
/*     */   public SvdImplicitQrAlgorithm() {}
/*     */ 
/*     */   
/*     */   public DenseMatrix64F getUt() {
/* 131 */     return this.Ut;
/*     */   }
/*     */   
/*     */   public void setUt(DenseMatrix64F ut) {
/* 135 */     this.Ut = ut;
/*     */   }
/*     */   
/*     */   public DenseMatrix64F getVt() {
/* 139 */     return this.Vt;
/*     */   }
/*     */   
/*     */   public void setVt(DenseMatrix64F vt) {
/* 143 */     this.Vt = vt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMatrix(int numRows, int numCols, double[] diag, double[] off) {
/* 150 */     initParam(numRows, numCols);
/* 151 */     this.diag = diag;
/* 152 */     this.off = off;
/*     */     
/* 154 */     this.maxValue = Math.abs(diag[0]);
/* 155 */     for (int i = 1; i < this.N; i++) {
/* 156 */       double a = Math.abs(diag[i]);
/* 157 */       double b = Math.abs(off[i - 1]);
/*     */       
/* 159 */       if (a > this.maxValue) {
/* 160 */         this.maxValue = Math.abs(a);
/*     */       }
/* 162 */       if (b > this.maxValue) {
/* 163 */         this.maxValue = Math.abs(b);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public double[] swapDiag(double[] diag) {
/* 169 */     double[] ret = this.diag;
/* 170 */     this.diag = diag;
/* 171 */     return ret;
/*     */   }
/*     */   
/*     */   public double[] swapOff(double[] off) {
/* 175 */     double[] ret = this.off;
/* 176 */     this.off = off;
/* 177 */     return ret;
/*     */   }
/*     */   
/*     */   public void setMaxValue(double maxValue) {
/* 181 */     this.maxValue = maxValue;
/*     */   }
/*     */   
/*     */   public void initParam(int M, int N) {
/* 185 */     if (N > M) {
/* 186 */       throw new RuntimeException("Must be a square or tall matrix");
/*     */     }
/* 188 */     this.N = N;
/*     */     
/* 190 */     if (this.splits == null || this.splits.length < N) {
/* 191 */       this.splits = new int[N];
/*     */     }
/*     */     
/* 194 */     this.x1 = 0;
/* 195 */     this.x2 = this.N - 1;
/*     */     
/* 197 */     this.steps = 0;
/* 198 */     this.totalSteps = 0;
/* 199 */     this.numSplits = 0;
/* 200 */     this.numExceptional = 0;
/* 201 */     this.nextExceptional = this.exceptionalThresh;
/*     */   }
/*     */   
/*     */   public boolean process() {
/* 205 */     this.followScript = false;
/* 206 */     this.findingZeros = true;
/*     */     
/* 208 */     return _process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean process(double[] values) {
/* 218 */     this.followScript = true;
/* 219 */     this.values = values;
/* 220 */     this.findingZeros = false;
/*     */     
/* 222 */     return _process();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean _process() {
/* 227 */     if (this.maxValue == 0.0D)
/* 228 */       return true; 
/* 229 */     while (this.x2 >= 0) {
/*     */       
/* 231 */       if (this.steps > this.maxIterations) {
/* 232 */         return false;
/*     */       }
/*     */       
/* 235 */       if (this.x1 == this.x2) {
/*     */ 
/*     */ 
/*     */         
/* 239 */         resetSteps();
/* 240 */         if (!nextSplit())
/*     */           break;  continue;
/* 242 */       }  if (this.fastValues && this.x2 - this.x1 == 1) {
/*     */         
/* 244 */         resetSteps();
/* 245 */         eigenBB_2x2(this.x1);
/* 246 */         setSubmatrix(this.x2, this.x2); continue;
/* 247 */       }  if (this.steps >= this.nextExceptional) {
/* 248 */         exceptionShift();
/*     */         continue;
/*     */       } 
/* 251 */       if (!checkForAndHandleZeros()) {
/* 252 */         if (this.followScript) {
/* 253 */           performScriptedStep(); continue;
/*     */         } 
/* 255 */         performDynamicStep();
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performDynamicStep() {
/* 273 */     if (this.findingZeros) {
/* 274 */       if (this.steps > 6) {
/* 275 */         this.findingZeros = false;
/*     */       } else {
/* 277 */         double scale = computeBulgeScale();
/* 278 */         performImplicitSingleStep(scale, 0.0D, false);
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 285 */       double scale = computeBulgeScale();
/*     */       
/* 287 */       double lambda = selectWilkinsonShift(scale);
/*     */       
/* 289 */       performImplicitSingleStep(scale, lambda, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performScriptedStep() {
/* 298 */     double scale = computeBulgeScale();
/* 299 */     if (this.steps > 10) {
/*     */       
/* 301 */       this.followScript = false;
/*     */     } else {
/*     */       
/* 304 */       double s = this.values[this.x2] / scale;
/* 305 */       performImplicitSingleStep(scale, s * s, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void incrementSteps() {
/* 310 */     this.steps++;
/* 311 */     this.totalSteps++;
/*     */   }
/*     */   
/*     */   public boolean isOffZero(int i) {
/* 315 */     double bottom = Math.abs(this.diag[i]) + Math.abs(this.diag[i + 1]);
/*     */     
/* 317 */     return (Math.abs(this.off[i]) <= bottom * UtilEjml.EPS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDiagonalZero(int i) {
/* 323 */     double bottom = Math.abs(this.diag[i + 1]) + Math.abs(this.off[i]);
/*     */     
/* 325 */     return (Math.abs(this.diag[i]) <= bottom * UtilEjml.EPS);
/*     */   }
/*     */   
/*     */   public void resetSteps() {
/* 329 */     this.steps = 0;
/* 330 */     this.nextExceptional = this.exceptionalThresh;
/* 331 */     this.numExceptional = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nextSplit() {
/* 339 */     if (this.numSplits == 0)
/* 340 */       return false; 
/* 341 */     this.x2 = this.splits[--this.numSplits];
/* 342 */     if (this.numSplits > 0) {
/* 343 */       this.x1 = this.splits[this.numSplits - 1] + 1;
/*     */     } else {
/* 345 */       this.x1 = 0;
/*     */     } 
/* 347 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void performImplicitSingleStep(double scale, double lambda, boolean byAngle) {
/* 358 */     createBulge(this.x1, lambda, scale, byAngle);
/*     */     
/* 360 */     for (int i = this.x1; i < this.x2 - 1 && this.bulge != 0.0D; i++) {
/* 361 */       removeBulgeLeft(i, true);
/* 362 */       if (this.bulge == 0.0D)
/*     */         break; 
/* 364 */       removeBulgeRight(i);
/*     */     } 
/*     */     
/* 367 */     if (this.bulge != 0.0D) {
/* 368 */       removeBulgeLeft(this.x2 - 1, false);
/*     */     }
/* 370 */     incrementSteps();
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
/*     */   protected void updateRotator(DenseMatrix64F Q, int m, int n, double c, double s) {
/* 386 */     int rowA = m * Q.numCols;
/* 387 */     int rowB = n * Q.numCols;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 398 */     int endA = rowA + Q.numCols;
/* 399 */     for (; rowA != endA; rowA++, rowB++) {
/* 400 */       double a = Q.get(rowA);
/* 401 */       double b = Q.get(rowB);
/* 402 */       Q.set(rowA, c * a + s * b);
/* 403 */       Q.set(rowB, -s * a + c * b);
/*     */     } 
/*     */   }
/*     */   
/*     */   private double computeBulgeScale() {
/* 408 */     double b11 = this.diag[this.x1];
/* 409 */     double b12 = this.off[this.x1];
/*     */     
/* 411 */     return Math.max(Math.abs(b11), Math.abs(b12));
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
/*     */   protected void createBulge(int x1, double p, double scale, boolean byAngle) {
/* 424 */     double b11 = this.diag[x1];
/* 425 */     double b12 = this.off[x1];
/* 426 */     double b22 = this.diag[x1 + 1];
/*     */     
/* 428 */     if (byAngle) {
/* 429 */       this.c = Math.cos(p);
/* 430 */       this.s = Math.sin(p);
/*     */     } else {
/*     */       
/* 433 */       double u1 = b11 / scale * b11 / scale - p;
/* 434 */       double u2 = b12 / scale * b11 / scale;
/*     */       
/* 436 */       double gamma = Math.sqrt(u1 * u1 + u2 * u2);
/*     */       
/* 438 */       this.c = u1 / gamma;
/* 439 */       this.s = u2 / gamma;
/*     */     } 
/*     */ 
/*     */     
/* 443 */     this.diag[x1] = b11 * this.c + b12 * this.s;
/* 444 */     this.off[x1] = b12 * this.c - b11 * this.s;
/* 445 */     this.diag[x1 + 1] = b22 * this.c;
/* 446 */     this.bulge = b22 * this.s;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 455 */     if (this.Vt != null) {
/* 456 */       updateRotator(this.Vt, x1, x1 + 1, this.c, this.s);
/*     */     }
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
/*     */   protected void computeRotator(double rise, double run) {
/* 476 */     if (Math.abs(rise) < Math.abs(run)) {
/* 477 */       double k = rise / run;
/*     */       
/* 479 */       double bottom = Math.sqrt(1.0D + k * k);
/* 480 */       this.s = 1.0D / bottom;
/* 481 */       this.c = k / bottom;
/*     */     } else {
/* 483 */       double t = run / rise;
/* 484 */       double bottom = Math.sqrt(1.0D + t * t);
/* 485 */       this.c = 1.0D / bottom;
/* 486 */       this.s = t / bottom;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeBulgeLeft(int x1, boolean notLast) {
/* 491 */     double b11 = this.diag[x1];
/* 492 */     double b12 = this.off[x1];
/* 493 */     double b22 = this.diag[x1 + 1];
/*     */     
/* 495 */     computeRotator(b11, this.bulge);
/*     */ 
/*     */     
/* 498 */     this.diag[x1] = this.c * b11 + this.s * this.bulge;
/* 499 */     this.off[x1] = this.c * b12 + this.s * b22;
/* 500 */     this.diag[x1 + 1] = this.c * b22 - this.s * b12;
/*     */     
/* 502 */     if (notLast) {
/* 503 */       double b23 = this.off[x1 + 1];
/* 504 */       this.bulge = this.s * b23;
/* 505 */       this.off[x1 + 1] = this.c * b23;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     if (this.Ut != null) {
/* 516 */       updateRotator(this.Ut, x1, x1 + 1, this.c, this.s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void removeBulgeRight(int x1) {
/* 526 */     double b12 = this.off[x1];
/* 527 */     double b22 = this.diag[x1 + 1];
/* 528 */     double b23 = this.off[x1 + 1];
/*     */     
/* 530 */     computeRotator(b12, this.bulge);
/*     */ 
/*     */     
/* 533 */     this.off[x1] = b12 * this.c + this.bulge * this.s;
/* 534 */     this.diag[x1 + 1] = b22 * this.c + b23 * this.s;
/* 535 */     this.off[x1 + 1] = -b22 * this.s + b23 * this.c;
/*     */     
/* 537 */     double b33 = this.diag[x1 + 2];
/* 538 */     this.diag[x1 + 2] = b33 * this.c;
/* 539 */     this.bulge = b33 * this.s;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 548 */     if (this.Vt != null) {
/* 549 */       updateRotator(this.Vt, x1 + 1, x1 + 2, this.c, this.s);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSubmatrix(int x1, int x2) {
/* 560 */     this.x1 = x1;
/* 561 */     this.x2 = x2;
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
/*     */   public double selectWilkinsonShift(double scale) {
/*     */     double a22;
/* 575 */     if (this.x2 - this.x1 > 1) {
/* 576 */       double d1 = this.diag[this.x2 - 1] / scale;
/* 577 */       double o1 = this.off[this.x2 - 2] / scale;
/* 578 */       double d2 = this.diag[this.x2] / scale;
/* 579 */       double o2 = this.off[this.x2 - 1] / scale;
/*     */       
/* 581 */       double a11 = o1 * o1 + d1 * d1;
/* 582 */       a22 = o2 * o2 + d2 * d2;
/*     */       
/* 584 */       this.eigenSmall.symm2x2_fast(a11, o2 * d1, a22);
/*     */     } else {
/* 586 */       double a = this.diag[this.x2 - 1] / scale;
/* 587 */       double b = this.off[this.x2 - 1] / scale;
/* 588 */       double c = this.diag[this.x2] / scale;
/*     */       
/* 590 */       double a11 = a * a;
/* 591 */       a22 = b * b + c * c;
/*     */       
/* 593 */       this.eigenSmall.symm2x2_fast(a11, a * b, a22);
/*     */     } 
/*     */ 
/*     */     
/* 597 */     double diff0 = Math.abs(this.eigenSmall.value0.real - a22);
/* 598 */     double diff1 = Math.abs(this.eigenSmall.value1.real - a22);
/*     */     
/* 600 */     return (diff0 < diff1) ? this.eigenSmall.value0.real : this.eigenSmall.value1.real;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void eigenBB_2x2(int x1) {
/* 607 */     double b11 = this.diag[x1];
/* 608 */     double b12 = this.off[x1];
/* 609 */     double b22 = this.diag[x1 + 1];
/*     */ 
/*     */     
/* 612 */     double absA = Math.abs(b11);
/* 613 */     double absB = Math.abs(b12);
/* 614 */     double absC = Math.abs(b22);
/*     */     
/* 616 */     double scale = (absA > absB) ? absA : absB;
/* 617 */     if (absC > scale) scale = absC;
/*     */ 
/*     */ 
/*     */     
/* 621 */     if (scale == 0.0D) {
/*     */       return;
/*     */     }
/* 624 */     b11 /= scale;
/* 625 */     b12 /= scale;
/* 626 */     b22 /= scale;
/*     */     
/* 628 */     this.eigenSmall.symm2x2_fast(b11 * b11, b11 * b12, b12 * b12 + b22 * b22);
/*     */     
/* 630 */     this.off[x1] = 0.0D;
/* 631 */     this.diag[x1] = scale * Math.sqrt(this.eigenSmall.value0.real);
/* 632 */     double sgn = Math.signum(this.eigenSmall.value1.real);
/* 633 */     this.diag[x1 + 1] = sgn * scale * Math.sqrt(Math.abs(this.eigenSmall.value1.real));
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
/*     */   protected boolean checkForAndHandleZeros() {
/*     */     int i;
/* 646 */     for (i = this.x2 - 1; i >= this.x1; i--) {
/* 647 */       if (isOffZero(i)) {
/*     */         
/* 649 */         resetSteps();
/* 650 */         this.splits[this.numSplits++] = i;
/* 651 */         this.x1 = i + 1;
/* 652 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 657 */     for (i = this.x2 - 1; i >= this.x1; i--) {
/* 658 */       if (isDiagonalZero(i)) {
/*     */         
/* 660 */         pushRight(i);
/* 661 */         resetSteps();
/* 662 */         this.splits[this.numSplits++] = i;
/* 663 */         this.x1 = i + 1;
/* 664 */         return true;
/*     */       } 
/*     */     } 
/* 667 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pushRight(int row) {
/* 675 */     if (isOffZero(row)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 680 */     rotatorPushRight(row);
/* 681 */     int end = this.N - 2 - row;
/* 682 */     for (int i = 0; i < end && this.bulge != 0.0D; i++) {
/* 683 */       rotatorPushRight2(row, i + 2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotatorPushRight(int m) {
/* 693 */     double b11 = this.off[m];
/* 694 */     double b21 = this.diag[m + 1];
/*     */     
/* 696 */     computeRotator(b21, -b11);
/*     */ 
/*     */     
/* 699 */     this.off[m] = 0.0D;
/* 700 */     this.diag[m + 1] = b21 * this.c - b11 * this.s;
/*     */     
/* 702 */     if (m + 2 < this.N) {
/* 703 */       double b22 = this.off[m + 1];
/* 704 */       this.off[m + 1] = b22 * this.c;
/* 705 */       this.bulge = b22 * this.s;
/*     */     } else {
/* 707 */       this.bulge = 0.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 718 */     if (this.Ut != null) {
/* 719 */       updateRotator(this.Ut, m, m + 1, this.c, this.s);
/*     */     }
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
/*     */   private void rotatorPushRight2(int m, int offset) {
/* 733 */     double b11 = this.bulge;
/* 734 */     double b12 = this.diag[m + offset];
/*     */     
/* 736 */     computeRotator(b12, -b11);
/*     */     
/* 738 */     this.diag[m + offset] = b12 * this.c - b11 * this.s;
/*     */     
/* 740 */     if (m + offset < this.N - 1) {
/* 741 */       double b22 = this.off[m + offset];
/* 742 */       this.off[m + offset] = b22 * this.c;
/* 743 */       this.bulge = b22 * this.s;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 754 */     if (this.Ut != null) {
/* 755 */       updateRotator(this.Ut, m, m + offset, this.c, this.s);
/*     */     }
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
/*     */   public void exceptionShift() {
/* 769 */     this.numExceptional++;
/* 770 */     double mag = 0.05D * this.numExceptional;
/* 771 */     if (mag > 1.0D) mag = 1.0D;
/*     */     
/* 773 */     double angle = 6.283185307179586D * (this.rand.nextDouble() - 0.5D) * mag;
/* 774 */     performImplicitSingleStep(0.0D, angle, true);
/*     */ 
/*     */     
/* 777 */     this.nextExceptional = this.steps + this.exceptionalThresh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SimpleMatrix createQ(int x1, double c, double s, boolean transposed) {
/* 784 */     return createQ(x1, x1 + 1, c, s, transposed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SimpleMatrix createQ(int x1, int x2, double c, double s, boolean transposed) {
/* 791 */     SimpleMatrix Q = SimpleMatrix.identity(this.N);
/* 792 */     Q.set(x1, x1, c);
/* 793 */     if (transposed) {
/* 794 */       Q.set(x1, x2, s);
/* 795 */       Q.set(x2, x1, -s);
/*     */     } else {
/* 797 */       Q.set(x1, x2, -s);
/* 798 */       Q.set(x2, x1, s);
/*     */     } 
/* 800 */     Q.set(x2, x2, c);
/* 801 */     return Q;
/*     */   }
/*     */   
/*     */   private SimpleMatrix createB() {
/* 805 */     SimpleMatrix B = new SimpleMatrix(this.N, this.N);
/*     */     
/* 807 */     for (int i = 0; i < this.N - 1; i++) {
/* 808 */       B.set(i, i, this.diag[i]);
/* 809 */       B.set(i, i + 1, this.off[i]);
/*     */     } 
/* 811 */     B.set(this.N - 1, this.N - 1, this.diag[this.N - 1]);
/*     */     
/* 813 */     return B;
/*     */   }
/*     */   
/*     */   public void printMatrix() {
/* 817 */     System.out.print("Off Diag[ "); int j;
/* 818 */     for (j = 0; j < this.N - 1; j++) {
/* 819 */       System.out.printf("%5.2f ", new Object[] { Double.valueOf(this.off[j]) });
/*     */     } 
/* 821 */     System.out.println();
/* 822 */     System.out.print("    Diag[ ");
/* 823 */     for (j = 0; j < this.N; j++) {
/* 824 */       System.out.printf("%5.2f ", new Object[] { Double.valueOf(this.diag[j]) });
/*     */     } 
/* 826 */     System.out.println();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumberOfSingularValues() {
/* 831 */     return this.N;
/*     */   }
/*     */   
/*     */   public double getSingularValue(int index) {
/* 835 */     return this.diag[index];
/*     */   }
/*     */   
/*     */   public void setFastValues(boolean b) {
/* 839 */     this.fastValues = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getSingularValues() {
/* 844 */     return this.diag;
/*     */   }
/*     */   
/*     */   public double[] getDiag() {
/* 848 */     return this.diag;
/*     */   }
/*     */   
/*     */   public double[] getOff() {
/* 852 */     return this.off;
/*     */   }
/*     */   
/*     */   public double getMaxValue() {
/* 856 */     return this.maxValue;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\svd\implicitqr\SvdImplicitQrAlgorithm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */