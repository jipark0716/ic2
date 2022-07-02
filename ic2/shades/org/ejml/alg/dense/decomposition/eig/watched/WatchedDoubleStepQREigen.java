/*     */ package ic2.shades.org.ejml.alg.dense.decomposition.eig.watched;
/*     */ 
/*     */ import ic2.shades.org.ejml.UtilEjml;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.eig.EigenvalueSmall;
/*     */ import ic2.shades.org.ejml.alg.dense.decomposition.qr.QrHelperFunctions;
/*     */ import ic2.shades.org.ejml.data.Complex64F;
/*     */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.ops.MatrixFeatures;
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
/*     */ public class WatchedDoubleStepQREigen
/*     */ {
/*  46 */   private Random rand = new Random(9026L);
/*     */   
/*     */   private int N;
/*     */   
/*     */   DenseMatrix64F A;
/*     */   
/*     */   private DenseMatrix64F u;
/*     */   
/*     */   private double gamma;
/*     */   
/*     */   private DenseMatrix64F _temp;
/*     */   
/*     */   int[] numStepsFind;
/*     */   
/*     */   int steps;
/*     */   Complex64F[] eigenvalues;
/*     */   int numEigen;
/*  63 */   private EigenvalueSmall valueSmall = new EigenvalueSmall();
/*     */   
/*  65 */   private double[] temp = new double[9];
/*     */   
/*     */   private boolean printHumps = false;
/*     */   
/*     */   boolean checkHessenberg = false;
/*     */   
/*     */   private boolean checkOrthogonal = false;
/*     */   
/*     */   private boolean checkUncountable = false;
/*     */   private boolean useStandardEq = false;
/*     */   private boolean useCareful2x2 = true;
/*     */   private boolean normalize = true;
/*     */   int lastExceptional;
/*     */   int numExceptional;
/*  79 */   int exceptionalThreshold = 20;
/*  80 */   int maxIterations = this.exceptionalThreshold * 20;
/*     */   
/*     */   public boolean createR = true;
/*     */   
/*     */   public DenseMatrix64F Q;
/*     */   
/*     */   public void incrementSteps() {
/*  87 */     this.steps++;
/*     */   }
/*     */   
/*     */   public void setQ(DenseMatrix64F Q) {
/*  91 */     this.Q = Q;
/*     */   }
/*     */   
/*     */   private void addEigenvalue(double v) {
/*  95 */     this.numStepsFind[this.numEigen] = this.steps;
/*  96 */     this.eigenvalues[this.numEigen].set(v, 0.0D);
/*  97 */     this.numEigen++;
/*  98 */     this.steps = 0;
/*  99 */     this.lastExceptional = 0;
/*     */   }
/*     */   
/*     */   private void addEigenvalue(double v, double i) {
/* 103 */     this.numStepsFind[this.numEigen] = this.steps;
/* 104 */     this.eigenvalues[this.numEigen].set(v, i);
/* 105 */     this.numEigen++;
/* 106 */     this.steps = 0;
/* 107 */     this.lastExceptional = 0;
/*     */   }
/*     */   
/*     */   public void setChecks(boolean hessenberg, boolean orthogonal, boolean uncountable) {
/* 111 */     this.checkHessenberg = hessenberg;
/* 112 */     this.checkOrthogonal = orthogonal;
/* 113 */     this.checkUncountable = uncountable;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isZero(int x1, int x2) {
/* 119 */     double target = Math.abs(this.A.get(x1, x2));
/*     */     
/* 121 */     double above = Math.abs(this.A.get(x1 - 1, x2));
/*     */ 
/*     */     
/* 124 */     double right = Math.abs(this.A.get(x1, x2 + 1));
/* 125 */     return (target <= 0.5D * UtilEjml.EPS * (above + right));
/*     */   }
/*     */   
/*     */   public void setup(DenseMatrix64F A) {
/* 129 */     if (A.numRows != A.numCols) {
/* 130 */       throw new RuntimeException("Must be square");
/*     */     }
/* 132 */     if (this.N != A.numRows) {
/* 133 */       this.N = A.numRows;
/*     */       
/* 135 */       this.A = A.copy();
/* 136 */       this.u = new DenseMatrix64F(A.numRows, 1);
/*     */       
/* 138 */       this._temp = new DenseMatrix64F(A.numRows, 1);
/* 139 */       this.numStepsFind = new int[A.numRows];
/*     */     } else {
/* 141 */       this.A.set((D1Matrix64F)A);
/* 142 */       UtilEjml.memset(this.numStepsFind, 0, this.numStepsFind.length);
/*     */     } 
/*     */     
/*     */     int i;
/* 146 */     for (i = 2; i < this.N; i++) {
/* 147 */       for (int j = 0; j < i - 1; j++) {
/* 148 */         this.A.set(i, j, 0.0D);
/*     */       }
/*     */     } 
/*     */     
/* 152 */     this.eigenvalues = new Complex64F[A.numRows];
/* 153 */     for (i = 0; i < this.eigenvalues.length; i++) {
/* 154 */       this.eigenvalues[i] = new Complex64F();
/*     */     }
/*     */     
/* 157 */     this.numEigen = 0;
/* 158 */     this.lastExceptional = 0;
/* 159 */     this.numExceptional = 0;
/* 160 */     this.steps = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exceptionalShift(int x1, int x2) {
/* 167 */     if (this.printHumps) {
/* 168 */       System.out.println("Performing exceptional implicit double step");
/*     */     }
/*     */     
/* 171 */     double val = Math.abs(this.A.get(x2, x2));
/*     */     
/* 173 */     if (val == 0.0D) {
/* 174 */       val = 1.0D;
/*     */     }
/* 176 */     this.numExceptional++;
/*     */     
/* 178 */     double p = 1.0D - Math.pow(0.1D, this.numExceptional);
/* 179 */     val *= p + 2.0D * (1.0D - p) * (this.rand.nextDouble() - 0.5D);
/*     */     
/* 181 */     if (this.rand.nextBoolean()) {
/* 182 */       val = -val;
/*     */     }
/* 184 */     performImplicitSingleStep(x1, x2, val);
/*     */     
/* 186 */     this.lastExceptional = this.steps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void implicitDoubleStep(int x1, int x2) {
/*     */     double b11, b21, b31;
/* 196 */     if (this.printHumps) {
/* 197 */       System.out.println("Performing implicit double step");
/*     */     }
/*     */     
/* 200 */     double z11 = this.A.get(x2 - 1, x2 - 1);
/* 201 */     double z12 = this.A.get(x2 - 1, x2);
/* 202 */     double z21 = this.A.get(x2, x2 - 1);
/* 203 */     double z22 = this.A.get(x2, x2);
/*     */     
/* 205 */     double a11 = this.A.get(x1, x1);
/* 206 */     double a21 = this.A.get(x1 + 1, x1);
/* 207 */     double a12 = this.A.get(x1, x1 + 1);
/* 208 */     double a22 = this.A.get(x1 + 1, x1 + 1);
/* 209 */     double a32 = this.A.get(x1 + 2, x1 + 1);
/*     */     
/* 211 */     if (this.normalize) {
/* 212 */       this.temp[0] = a11; this.temp[1] = a21; this.temp[2] = a12; this.temp[3] = a22; this.temp[4] = a32;
/* 213 */       this.temp[5] = z11; this.temp[6] = z22; this.temp[7] = z12; this.temp[8] = z21;
/*     */       
/* 215 */       double max = Math.abs(this.temp[0]);
/* 216 */       for (int j = 1; j < this.temp.length; j++) {
/* 217 */         if (Math.abs(this.temp[j]) > max)
/* 218 */           max = Math.abs(this.temp[j]); 
/*     */       } 
/* 220 */       a11 /= max; a21 /= max; a12 /= max; a22 /= max; a32 /= max;
/* 221 */       z11 /= max; z22 /= max; z12 /= max; z21 /= max;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     if (this.useStandardEq) {
/* 228 */       b11 = ((a11 - z11) * (a11 - z22) - z21 * z12) / a21 + a12;
/* 229 */       b21 = a11 + a22 - z11 - z22;
/* 230 */       b31 = a32;
/*     */     }
/*     */     else {
/*     */       
/* 234 */       b11 = (a11 - z11) * (a11 - z22) - z21 * z12 + a12 * a21;
/* 235 */       b21 = (a11 + a22 - z11 - z22) * a21;
/* 236 */       b31 = a32 * a21;
/*     */     } 
/*     */     
/* 239 */     performImplicitDoubleStep(x1, x2, b11, b21, b31);
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
/*     */   public void performImplicitDoubleStep(int x1, int x2, double real, double img) {
/* 253 */     double b11, b21, b31, a11 = this.A.get(x1, x1);
/* 254 */     double a21 = this.A.get(x1 + 1, x1);
/* 255 */     double a12 = this.A.get(x1, x1 + 1);
/* 256 */     double a22 = this.A.get(x1 + 1, x1 + 1);
/* 257 */     double a32 = this.A.get(x1 + 2, x1 + 1);
/*     */     
/* 259 */     double p_plus_t = 2.0D * real;
/* 260 */     double p_times_t = real * real + img * img;
/*     */ 
/*     */     
/* 263 */     if (this.useStandardEq) {
/* 264 */       b11 = (a11 * a11 - p_plus_t * a11 + p_times_t) / a21 + a12;
/* 265 */       b21 = a11 + a22 - p_plus_t;
/* 266 */       b31 = a32;
/*     */     }
/*     */     else {
/*     */       
/* 270 */       b11 = a11 * a11 - p_plus_t * a11 + p_times_t + a12 * a21;
/* 271 */       b21 = (a11 + a22 - p_plus_t) * a21;
/* 272 */       b31 = a32 * a21;
/*     */     } 
/*     */     
/* 275 */     performImplicitDoubleStep(x1, x2, b11, b21, b31);
/*     */   }
/*     */ 
/*     */   
/*     */   private void performImplicitDoubleStep(int x1, int x2, double b11, double b21, double b31) {
/* 280 */     if (!bulgeDoubleStepQn(x1, b11, b21, b31, 0.0D, false)) {
/*     */       return;
/*     */     }
/*     */     
/* 284 */     if (this.Q != null) {
/* 285 */       QrHelperFunctions.rank1UpdateMultR(this.Q, this.u.data, this.gamma, 0, x1, x1 + 3, this._temp.data);
/* 286 */       if (this.checkOrthogonal && !MatrixFeatures.isOrthogonal(this.Q, 1.0E-8D)) {
/* 287 */         this.u.print();
/*     */         
/* 289 */         this.Q.print();
/* 290 */         throw new RuntimeException("Bad");
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     if (this.printHumps) {
/* 295 */       System.out.println("Applied first Q matrix, it should be humped now. A = ");
/* 296 */       this.A.print("%12.3e");
/* 297 */       System.out.println("Pushing the hump off the matrix.");
/*     */     } 
/*     */ 
/*     */     
/* 301 */     for (int i = x1; i < x2 - 2; i++) {
/* 302 */       if (bulgeDoubleStepQn(i) && this.Q != null) {
/* 303 */         QrHelperFunctions.rank1UpdateMultR(this.Q, this.u.data, this.gamma, 0, i + 1, i + 4, this._temp.data);
/* 304 */         if (this.checkOrthogonal && !MatrixFeatures.isOrthogonal(this.Q, 1.0E-8D)) {
/* 305 */           throw new RuntimeException("Bad");
/*     */         }
/*     */       } 
/* 308 */       if (this.printHumps) {
/* 309 */         System.out.println("i = " + i + " A = ");
/* 310 */         this.A.print("%12.3e");
/*     */       } 
/*     */     } 
/* 313 */     if (this.printHumps) {
/* 314 */       System.out.println("removing last bump");
/*     */     }
/* 316 */     if (x2 - 2 >= 0 && bulgeSingleStepQn(x2 - 2) && this.Q != null) {
/* 317 */       QrHelperFunctions.rank1UpdateMultR(this.Q, this.u.data, this.gamma, 0, x2 - 1, x2 + 1, this._temp.data);
/* 318 */       if (this.checkOrthogonal && !MatrixFeatures.isOrthogonal(this.Q, 1.0E-8D)) {
/* 319 */         throw new RuntimeException("Bad");
/*     */       }
/*     */     } 
/* 322 */     if (this.printHumps) {
/* 323 */       System.out.println(" A = ");
/* 324 */       this.A.print("%12.3e");
/*     */     } 
/*     */ 
/*     */     
/* 328 */     if (this.checkHessenberg && !MatrixFeatures.isUpperTriangle(this.A, 1, 1.0E-12D)) {
/* 329 */       this.A.print("%12.3e");
/* 330 */       throw new RuntimeException("Bad matrix");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void performImplicitSingleStep(int x1, int x2, double eigenvalue) {
/* 336 */     if (!createBulgeSingleStep(x1, eigenvalue)) {
/*     */       return;
/*     */     }
/*     */     
/* 340 */     if (this.Q != null) {
/* 341 */       QrHelperFunctions.rank1UpdateMultR(this.Q, this.u.data, this.gamma, 0, x1, x1 + 2, this._temp.data);
/* 342 */       if (this.checkOrthogonal && !MatrixFeatures.isOrthogonal(this.Q, 1.0E-8D)) {
/* 343 */         throw new RuntimeException("Bad");
/*     */       }
/*     */     } 
/* 346 */     if (this.printHumps) {
/* 347 */       System.out.println("Applied first Q matrix, it should be humped now. A = ");
/* 348 */       this.A.print("%12.3e");
/* 349 */       System.out.println("Pushing the hump off the matrix.");
/*     */     } 
/*     */ 
/*     */     
/* 353 */     for (int i = x1; i < x2 - 1; i++) {
/* 354 */       if (bulgeSingleStepQn(i) && this.Q != null) {
/* 355 */         QrHelperFunctions.rank1UpdateMultR(this.Q, this.u.data, this.gamma, 0, i + 1, i + 3, this._temp.data);
/* 356 */         if (this.checkOrthogonal && !MatrixFeatures.isOrthogonal(this.Q, 1.0E-8D)) {
/* 357 */           throw new RuntimeException("Bad");
/*     */         }
/*     */       } 
/* 360 */       if (this.printHumps) {
/* 361 */         System.out.println("i = " + i + " A = ");
/* 362 */         this.A.print("%12.3e");
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     if (this.checkHessenberg && !MatrixFeatures.isUpperTriangle(this.A, 1, 1.0E-12D)) {
/* 367 */       this.A.print("%12.3e");
/* 368 */       throw new RuntimeException("Bad matrix");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean createBulgeSingleStep(int x1, double eigenvalue) {
/* 374 */     double b11 = this.A.get(x1, x1) - eigenvalue;
/* 375 */     double b21 = this.A.get(x1 + 1, x1);
/*     */     
/* 377 */     double threshold = Math.abs(this.A.get(x1, x1)) * UtilEjml.EPS;
/*     */     
/* 379 */     return bulgeSingleStepQn(x1, b11, b21, threshold, false);
/*     */   }
/*     */   
/*     */   public boolean bulgeDoubleStepQn(int i) {
/* 383 */     double a11 = this.A.get(i + 1, i);
/* 384 */     double a21 = this.A.get(i + 2, i);
/* 385 */     double a31 = this.A.get(i + 3, i);
/*     */     
/* 387 */     double threshold = Math.abs(this.A.get(i, i)) * UtilEjml.EPS;
/*     */     
/* 389 */     return bulgeDoubleStepQn(i + 1, a11, a21, a31, threshold, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean bulgeDoubleStepQn(int i, double a11, double a21, double a31, double threshold, boolean set) {
/*     */     double max;
/* 397 */     if (this.normalize) {
/* 398 */       double absA11 = Math.abs(a11);
/* 399 */       double absA21 = Math.abs(a21);
/* 400 */       double absA31 = Math.abs(a31);
/*     */       
/* 402 */       max = (absA11 > absA21) ? absA11 : absA21;
/* 403 */       if (absA31 > max) max = absA31;
/*     */ 
/*     */       
/* 406 */       if (max <= threshold) {
/* 407 */         if (set) {
/* 408 */           this.A.set(i, i - 1, 0.0D);
/* 409 */           this.A.set(i + 1, i - 1, 0.0D);
/* 410 */           this.A.set(i + 2, i - 1, 0.0D);
/*     */         } 
/* 412 */         return false;
/*     */       } 
/*     */       
/* 415 */       a11 /= max;
/* 416 */       a21 /= max;
/* 417 */       a31 /= max;
/*     */     } else {
/* 419 */       max = 1.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 424 */     double tau = Math.sqrt(a11 * a11 + a21 * a21 + a31 * a31);
/* 425 */     if (a11 < 0.0D) tau = -tau;
/*     */     
/* 427 */     double div = a11 + tau;
/*     */     
/* 429 */     this.u.set(i, 0, 1.0D);
/* 430 */     this.u.set(i + 1, 0, a21 / div);
/* 431 */     this.u.set(i + 2, 0, a31 / div);
/*     */     
/* 433 */     this.gamma = div / tau;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     QrHelperFunctions.rank1UpdateMultR(this.A, this.u.data, this.gamma, 0, i, i + 3, this._temp.data);
/*     */     
/* 440 */     if (set) {
/* 441 */       this.A.set(i, i - 1, -max * tau);
/* 442 */       this.A.set(i + 1, i - 1, 0.0D);
/* 443 */       this.A.set(i + 2, i - 1, 0.0D);
/*     */     } 
/*     */     
/* 446 */     if (this.printHumps) {
/* 447 */       System.out.println("  After Q.   A =");
/* 448 */       this.A.print();
/*     */     } 
/*     */ 
/*     */     
/* 452 */     QrHelperFunctions.rank1UpdateMultL(this.A, this.u.data, this.gamma, 0, i, i + 3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     if (this.checkUncountable && MatrixFeatures.hasUncountable((D1Matrix64F)this.A)) {
/* 458 */       throw new RuntimeException("bad matrix");
/*     */     }
/*     */     
/* 461 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bulgeSingleStepQn(int i) {
/* 466 */     double a11 = this.A.get(i + 1, i);
/* 467 */     double a21 = this.A.get(i + 2, i);
/*     */     
/* 469 */     double threshold = Math.abs(this.A.get(i, i)) * UtilEjml.EPS;
/*     */     
/* 471 */     return bulgeSingleStepQn(i + 1, a11, a21, threshold, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean bulgeSingleStepQn(int i, double a11, double a21, double threshold, boolean set) {
/*     */     double max;
/* 480 */     if (this.normalize) {
/* 481 */       max = Math.abs(a11);
/* 482 */       if (max < Math.abs(a21)) max = Math.abs(a21);
/*     */ 
/*     */       
/* 485 */       if (max <= threshold) {
/*     */ 
/*     */         
/* 488 */         if (set) {
/* 489 */           this.A.set(i, i - 1, 0.0D);
/* 490 */           this.A.set(i + 1, i - 1, 0.0D);
/*     */         } 
/* 492 */         return false;
/*     */       } 
/*     */       
/* 495 */       a11 /= max;
/* 496 */       a21 /= max;
/*     */     } else {
/* 498 */       max = 1.0D;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 503 */     double tau = Math.sqrt(a11 * a11 + a21 * a21);
/* 504 */     if (a11 < 0.0D) tau = -tau;
/*     */     
/* 506 */     double div = a11 + tau;
/*     */     
/* 508 */     this.u.set(i, 0, 1.0D);
/* 509 */     this.u.set(i + 1, 0, a21 / div);
/*     */     
/* 511 */     this.gamma = div / tau;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 516 */     QrHelperFunctions.rank1UpdateMultR(this.A, this.u.data, this.gamma, 0, i, i + 2, this._temp.data);
/*     */     
/* 518 */     if (set) {
/* 519 */       this.A.set(i, i - 1, -max * tau);
/* 520 */       this.A.set(i + 1, i - 1, 0.0D);
/*     */     } 
/*     */ 
/*     */     
/* 524 */     QrHelperFunctions.rank1UpdateMultL(this.A, this.u.data, this.gamma, 0, i, i + 2);
/*     */     
/* 526 */     if (this.checkUncountable && MatrixFeatures.hasUncountable((D1Matrix64F)this.A)) {
/* 527 */       throw new RuntimeException("bad matrix");
/*     */     }
/*     */     
/* 530 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void eigen2by2_scale(double a11, double a12, double a21, double a22) {
/* 535 */     double abs11 = Math.abs(a11);
/* 536 */     double abs22 = Math.abs(a22);
/* 537 */     double abs12 = Math.abs(a12);
/* 538 */     double abs21 = Math.abs(a21);
/*     */     
/* 540 */     double max = (abs11 > abs22) ? abs11 : abs22;
/* 541 */     if (max < abs12) max = abs12; 
/* 542 */     if (max < abs21) max = abs21;
/*     */     
/* 544 */     if (max == 0.0D) {
/* 545 */       this.valueSmall.value0.real = 0.0D;
/* 546 */       this.valueSmall.value0.imaginary = 0.0D;
/* 547 */       this.valueSmall.value1.real = 0.0D;
/* 548 */       this.valueSmall.value1.imaginary = 0.0D;
/*     */     } else {
/* 550 */       a12 /= max; a21 /= max; a11 /= max; a22 /= max;
/*     */       
/* 552 */       if (this.useCareful2x2) {
/* 553 */         this.valueSmall.value2x2(a11, a12, a21, a22);
/*     */       } else {
/* 555 */         this.valueSmall.value2x2_fast(a11, a12, a21, a22);
/*     */       } 
/* 557 */       this.valueSmall.value0.real *= max;
/* 558 */       this.valueSmall.value0.imaginary *= max;
/* 559 */       this.valueSmall.value1.real *= max;
/* 560 */       this.valueSmall.value1.imaginary *= max;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumberOfEigenvalues() {
/* 568 */     return this.numEigen;
/*     */   }
/*     */   
/*     */   public Complex64F[] getEigenvalues() {
/* 572 */     return this.eigenvalues;
/*     */   }
/*     */   
/*     */   public void addComputedEigen2x2(int x1, int x2) {
/* 576 */     eigen2by2_scale(this.A.get(x1, x1), this.A.get(x1, x2), this.A.get(x2, x1), this.A.get(x2, x2));
/*     */     
/* 578 */     if (this.checkUncountable && (Double.isNaN(this.valueSmall.value0.real) || Double.isNaN(this.valueSmall.value1.real)))
/*     */     {
/* 580 */       throw new RuntimeException("Uncountable");
/*     */     }
/*     */     
/* 583 */     addEigenvalue(this.valueSmall.value0.real, this.valueSmall.value0.imaginary);
/* 584 */     addEigenvalue(this.valueSmall.value1.real, this.valueSmall.value1.imaginary);
/*     */   }
/*     */   
/*     */   public boolean isReal2x2(int x1, int x2) {
/* 588 */     eigen2by2_scale(this.A.get(x1, x1), this.A.get(x1, x2), this.A.get(x2, x1), this.A.get(x2, x2));
/*     */     
/* 590 */     return this.valueSmall.value0.isReal();
/*     */   }
/*     */   
/*     */   public void addEigenAt(int x1) {
/* 594 */     addEigenvalue(this.A.get(x1, x1));
/*     */   }
/*     */   
/*     */   public void printSteps() {
/* 598 */     for (int i = 0; i < this.N; i++)
/* 599 */       System.out.println("Step[" + i + "] = " + this.numStepsFind[i]); 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\decomposition\eig\watched\WatchedDoubleStepQREigen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */