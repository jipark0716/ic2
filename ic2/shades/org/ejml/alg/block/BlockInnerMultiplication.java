/*     */ package ic2.shades.org.ejml.alg.block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockInnerMultiplication
/*     */ {
/*     */   public static void blockMultPlus(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/*  52 */     int a = indexA;
/*  53 */     int rowC = indexC;
/*  54 */     for (int i = 0; i < heightA; i++, rowC += widthC) {
/*  55 */       int b = indexB;
/*     */       
/*  57 */       int endC = rowC + widthC;
/*  58 */       int endA = a + widthA;
/*  59 */       while (a != endA) {
/*  60 */         double valA = dataA[a++];
/*     */         
/*  62 */         int c = rowC;
/*     */         
/*  64 */         while (c != endC) {
/*  65 */           dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void blockMultPlusTransA(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/*  90 */     int rowC = indexC;
/*  91 */     for (int i = 0; i < widthA; i++, rowC += widthC) {
/*  92 */       int colA = i + indexA;
/*  93 */       int endA = colA + widthA * heightA;
/*  94 */       int b = indexB;
/*     */ 
/*     */       
/*  97 */       while (colA != endA) {
/*  98 */         double valA = dataA[colA];
/*     */         
/* 100 */         int c = rowC;
/* 101 */         int endB = b + widthC;
/*     */ 
/*     */         
/* 104 */         while (b != endB) {
/* 105 */           dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */         }
/* 107 */         colA += widthA;
/*     */       } 
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
/*     */   public static void blockMultPlusTransB(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 122 */     for (int i = 0; i < heightA; i++) {
/* 123 */       for (int j = 0; j < widthC; j++) {
/* 124 */         double val = 0.0D;
/*     */         
/* 126 */         for (int k = 0; k < widthA; k++) {
/* 127 */           val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
/*     */         }
/*     */         
/* 130 */         dataC[i * widthC + j + indexC] = dataC[i * widthC + j + indexC] + val;
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static void blockMultMinus(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 153 */     int a = indexA;
/* 154 */     int rowC = indexC;
/* 155 */     for (int i = 0; i < heightA; i++, rowC += widthC) {
/* 156 */       int b = indexB;
/*     */       
/* 158 */       int endC = rowC + widthC;
/* 159 */       int endA = a + widthA;
/* 160 */       while (a != endA) {
/* 161 */         double valA = dataA[a++];
/*     */         
/* 163 */         int c = rowC;
/*     */         
/* 165 */         while (c != endC) {
/* 166 */           dataC[c++] = dataC[c++] - valA * dataB[b++];
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void blockMultMinusTransA(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 191 */     int rowC = indexC;
/* 192 */     for (int i = 0; i < widthA; i++, rowC += widthC) {
/* 193 */       int colA = i + indexA;
/* 194 */       int endA = colA + widthA * heightA;
/* 195 */       int b = indexB;
/*     */ 
/*     */       
/* 198 */       while (colA != endA) {
/* 199 */         double valA = dataA[colA];
/*     */         
/* 201 */         int c = rowC;
/* 202 */         int endB = b + widthC;
/*     */ 
/*     */         
/* 205 */         while (b != endB) {
/* 206 */           dataC[c++] = dataC[c++] - valA * dataB[b++];
/*     */         }
/* 208 */         colA += widthA;
/*     */       } 
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
/*     */   public static void blockMultMinusTransB(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 223 */     for (int i = 0; i < heightA; i++) {
/* 224 */       for (int j = 0; j < widthC; j++) {
/* 225 */         double val = 0.0D;
/*     */         
/* 227 */         for (int k = 0; k < widthA; k++) {
/* 228 */           val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
/*     */         }
/*     */         
/* 231 */         dataC[i * widthC + j + indexC] = dataC[i * widthC + j + indexC] - val;
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static void blockMultSet(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 254 */     int a = indexA;
/* 255 */     int rowC = indexC;
/* 256 */     for (int i = 0; i < heightA; i++, rowC += widthC) {
/* 257 */       int b = indexB;
/*     */       
/* 259 */       int endC = rowC + widthC;
/* 260 */       int endA = a + widthA;
/* 261 */       while (a != endA) {
/* 262 */         double valA = dataA[a++];
/*     */         
/* 264 */         int c = rowC;
/*     */         
/* 266 */         if (b == indexB) {
/* 267 */           while (c != endC)
/* 268 */             dataC[c++] = valA * dataB[b++]; 
/*     */           continue;
/*     */         } 
/* 271 */         while (c != endC) {
/* 272 */           dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void blockMultSetTransA(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 298 */     int rowC = indexC;
/* 299 */     for (int i = 0; i < widthA; i++, rowC += widthC) {
/* 300 */       int colA = i + indexA;
/* 301 */       int endA = colA + widthA * heightA;
/* 302 */       int b = indexB;
/*     */ 
/*     */       
/* 305 */       while (colA != endA) {
/* 306 */         double valA = dataA[colA];
/*     */         
/* 308 */         int c = rowC;
/* 309 */         int endB = b + widthC;
/*     */ 
/*     */         
/* 312 */         if (b == indexB) {
/* 313 */           while (b != endB) {
/* 314 */             dataC[c++] = valA * dataB[b++];
/*     */           }
/*     */         } else {
/* 317 */           while (b != endB) {
/* 318 */             dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */           }
/*     */         } 
/* 321 */         colA += widthA;
/*     */       } 
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
/*     */   public static void blockMultSetTransB(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 336 */     for (int i = 0; i < heightA; i++) {
/* 337 */       for (int j = 0; j < widthC; j++) {
/* 338 */         double val = 0.0D;
/*     */         
/* 340 */         for (int k = 0; k < widthA; k++) {
/* 341 */           val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
/*     */         }
/*     */         
/* 344 */         dataC[i * widthC + j + indexC] = val;
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static void blockMultPlus(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 367 */     int a = indexA;
/* 368 */     int rowC = indexC;
/* 369 */     for (int i = 0; i < heightA; i++, rowC += widthC) {
/* 370 */       int b = indexB;
/*     */       
/* 372 */       int endC = rowC + widthC;
/* 373 */       int endA = a + widthA;
/* 374 */       while (a != endA) {
/* 375 */         double valA = alpha * dataA[a++];
/*     */         
/* 377 */         int c = rowC;
/*     */         
/* 379 */         while (c != endC) {
/* 380 */           dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void blockMultPlusTransA(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 405 */     int rowC = indexC;
/* 406 */     for (int i = 0; i < widthA; i++, rowC += widthC) {
/* 407 */       int colA = i + indexA;
/* 408 */       int endA = colA + widthA * heightA;
/* 409 */       int b = indexB;
/*     */ 
/*     */       
/* 412 */       while (colA != endA) {
/* 413 */         double valA = alpha * dataA[colA];
/*     */         
/* 415 */         int c = rowC;
/* 416 */         int endB = b + widthC;
/*     */ 
/*     */         
/* 419 */         while (b != endB) {
/* 420 */           dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */         }
/* 422 */         colA += widthA;
/*     */       } 
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
/*     */   public static void blockMultPlusTransB(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 437 */     for (int i = 0; i < heightA; i++) {
/* 438 */       for (int j = 0; j < widthC; j++) {
/* 439 */         double val = 0.0D;
/*     */         
/* 441 */         for (int k = 0; k < widthA; k++) {
/* 442 */           val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
/*     */         }
/*     */         
/* 445 */         dataC[i * widthC + j + indexC] = dataC[i * widthC + j + indexC] + alpha * val;
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static void blockMultSet(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 468 */     int a = indexA;
/* 469 */     int rowC = indexC;
/* 470 */     for (int i = 0; i < heightA; i++, rowC += widthC) {
/* 471 */       int b = indexB;
/*     */       
/* 473 */       int endC = rowC + widthC;
/* 474 */       int endA = a + widthA;
/* 475 */       while (a != endA) {
/* 476 */         double valA = alpha * dataA[a++];
/*     */         
/* 478 */         int c = rowC;
/*     */         
/* 480 */         if (b == indexB) {
/* 481 */           while (c != endC)
/* 482 */             dataC[c++] = valA * dataB[b++]; 
/*     */           continue;
/*     */         } 
/* 485 */         while (c != endC) {
/* 486 */           dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */         }
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void blockMultSetTransA(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 512 */     int rowC = indexC;
/* 513 */     for (int i = 0; i < widthA; i++, rowC += widthC) {
/* 514 */       int colA = i + indexA;
/* 515 */       int endA = colA + widthA * heightA;
/* 516 */       int b = indexB;
/*     */ 
/*     */       
/* 519 */       while (colA != endA) {
/* 520 */         double valA = alpha * dataA[colA];
/*     */         
/* 522 */         int c = rowC;
/* 523 */         int endB = b + widthC;
/*     */ 
/*     */         
/* 526 */         if (b == indexB) {
/* 527 */           while (b != endB) {
/* 528 */             dataC[c++] = valA * dataB[b++];
/*     */           }
/*     */         } else {
/* 531 */           while (b != endB) {
/* 532 */             dataC[c++] = dataC[c++] + valA * dataB[b++];
/*     */           }
/*     */         } 
/* 535 */         colA += widthA;
/*     */       } 
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
/*     */   public static void blockMultSetTransB(double alpha, double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 550 */     for (int i = 0; i < heightA; i++) {
/* 551 */       for (int j = 0; j < widthC; j++) {
/* 552 */         double val = 0.0D;
/*     */         
/* 554 */         for (int k = 0; k < widthA; k++) {
/* 555 */           val += dataA[i * widthA + k + indexA] * dataB[j * widthA + k + indexB];
/*     */         }
/*     */         
/* 558 */         dataC[i * widthC + j + indexC] = alpha * val;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\BlockInnerMultiplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */