/*      */ package ic2.shades.org.ejml.alg.dense.mult;
/*      */ 
/*      */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*      */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*      */ import ic2.shades.org.ejml.ops.CommonOps;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MatrixMatrixMult
/*      */ {
/*      */   public static void mult_reorder(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*   65 */     if (a == c || b == c)
/*   66 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*   67 */     if (a.numCols != b.numRows)
/*   68 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*   69 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*   70 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*   73 */     if (a.numCols == 0 || a.numRows == 0) {
/*   74 */       CommonOps.fill((D1Matrix64F)c, 0.0D);
/*      */       
/*      */       return;
/*      */     } 
/*   78 */     int indexCbase = 0;
/*   79 */     int endOfKLoop = b.numRows * b.numCols;
/*      */     
/*   81 */     for (int i = 0; i < a.numRows; i++) {
/*   82 */       int indexA = i * a.numCols;
/*      */ 
/*      */       
/*   85 */       int indexB = 0;
/*   86 */       int indexC = indexCbase;
/*   87 */       int end = indexB + b.numCols;
/*      */       
/*   89 */       double valA = a.get(indexA++);
/*      */       
/*   91 */       while (indexB < end) {
/*   92 */         c.set(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */ 
/*      */       
/*   96 */       while (indexB != endOfKLoop) {
/*   97 */         indexC = indexCbase;
/*   98 */         end = indexB + b.numCols;
/*      */         
/*  100 */         valA = a.get(indexA++);
/*      */         
/*  102 */         while (indexB < end) {
/*  103 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*  106 */       indexCbase += c.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mult_small(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  115 */     if (a == c || b == c)
/*  116 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  117 */     if (a.numCols != b.numRows)
/*  118 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  119 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  120 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  123 */     int aIndexStart = 0;
/*  124 */     int cIndex = 0;
/*      */     
/*  126 */     for (int i = 0; i < a.numRows; i++) {
/*  127 */       for (int j = 0; j < b.numCols; j++) {
/*  128 */         double total = 0.0D;
/*      */         
/*  130 */         int indexA = aIndexStart;
/*  131 */         int indexB = j;
/*  132 */         int end = indexA + b.numRows;
/*  133 */         while (indexA < end) {
/*  134 */           total += a.get(indexA++) * b.get(indexB);
/*  135 */           indexB += b.numCols;
/*      */         } 
/*      */         
/*  138 */         c.set(cIndex++, total);
/*      */       } 
/*  140 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mult_aux(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/*  149 */     if (a == c || b == c)
/*  150 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  151 */     if (a.numCols != b.numRows)
/*  152 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  153 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  154 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  157 */     if (aux == null) aux = new double[b.numRows];
/*      */     
/*  159 */     for (int j = 0; j < b.numCols; j++) {
/*      */       
/*  161 */       for (int k = 0; k < b.numRows; k++) {
/*  162 */         aux[k] = b.unsafe_get(k, j);
/*      */       }
/*      */       
/*  165 */       int indexA = 0;
/*  166 */       for (int i = 0; i < a.numRows; i++) {
/*  167 */         double total = 0.0D;
/*  168 */         for (int m = 0; m < b.numRows;) {
/*  169 */           total += a.get(indexA++) * aux[m++];
/*      */         }
/*  171 */         c.set(i * c.numCols + j, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransA_reorder(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  181 */     if (a == c || b == c)
/*  182 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  183 */     if (a.numRows != b.numRows)
/*  184 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  185 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/*  186 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  189 */     if (a.numCols == 0 || a.numRows == 0) {
/*  190 */       CommonOps.fill((D1Matrix64F)c, 0.0D);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  195 */     for (int i = 0; i < a.numCols; i++) {
/*  196 */       int indexC_start = i * c.numCols;
/*      */ 
/*      */       
/*  199 */       double valA = a.get(i);
/*  200 */       int indexB = 0;
/*  201 */       int end = indexB + b.numCols;
/*  202 */       int indexC = indexC_start;
/*  203 */       while (indexB < end) {
/*  204 */         c.set(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */       
/*  207 */       for (int k = 1; k < a.numRows; k++) {
/*  208 */         valA = a.unsafe_get(k, i);
/*  209 */         end = indexB + b.numCols;
/*  210 */         indexC = indexC_start;
/*      */         
/*  212 */         while (indexB < end) {
/*  213 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransA_small(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  224 */     if (a == c || b == c)
/*  225 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  226 */     if (a.numRows != b.numRows)
/*  227 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  228 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/*  229 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  232 */     int cIndex = 0;
/*      */     
/*  234 */     for (int i = 0; i < a.numCols; i++) {
/*  235 */       for (int j = 0; j < b.numCols; j++) {
/*  236 */         int indexA = i;
/*  237 */         int indexB = j;
/*  238 */         int end = indexB + b.numRows * b.numCols;
/*      */         
/*  240 */         double total = 0.0D;
/*      */ 
/*      */         
/*  243 */         for (; indexB < end; indexB += b.numCols) {
/*  244 */           total += a.get(indexA) * b.get(indexB);
/*  245 */           indexA += a.numCols;
/*      */         } 
/*      */         
/*  248 */         c.set(cIndex++, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransAB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  258 */     if (a == c || b == c)
/*  259 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  260 */     if (a.numRows != b.numCols)
/*  261 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  262 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/*  263 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  266 */     int cIndex = 0;
/*      */     
/*  268 */     for (int i = 0; i < a.numCols; i++) {
/*  269 */       int indexB = 0;
/*  270 */       for (int j = 0; j < b.numRows; j++) {
/*  271 */         int indexA = i;
/*  272 */         int end = indexB + b.numCols;
/*      */         
/*  274 */         double total = 0.0D;
/*      */         
/*  276 */         while (indexB < end) {
/*  277 */           total += a.get(indexA) * b.get(indexB++);
/*  278 */           indexA += a.numCols;
/*      */         } 
/*      */         
/*  281 */         c.set(cIndex++, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransAB_aux(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/*  291 */     if (a == c || b == c)
/*  292 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  293 */     if (a.numRows != b.numCols)
/*  294 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  295 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/*  296 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  299 */     if (aux == null) aux = new double[a.numRows];
/*      */     
/*  301 */     if (a.numCols == 0 || a.numRows == 0) {
/*  302 */       CommonOps.fill((D1Matrix64F)c, 0.0D);
/*      */       return;
/*      */     } 
/*  305 */     int indexC = 0;
/*  306 */     for (int i = 0; i < a.numCols; i++) {
/*  307 */       for (int k = 0; k < b.numCols; k++) {
/*  308 */         aux[k] = a.unsafe_get(k, i);
/*      */       }
/*      */       
/*  311 */       for (int j = 0; j < b.numRows; j++) {
/*  312 */         double total = 0.0D;
/*      */         
/*  314 */         for (int m = 0; m < b.numCols; m++) {
/*  315 */           total += aux[m] * b.unsafe_get(j, m);
/*      */         }
/*  317 */         c.set(indexC++, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  327 */     if (a == c || b == c)
/*  328 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  329 */     if (a.numCols != b.numCols)
/*  330 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  331 */     if (a.numRows != c.numRows || b.numRows != c.numCols) {
/*  332 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  335 */     int cIndex = 0;
/*  336 */     int aIndexStart = 0;
/*      */     
/*  338 */     for (int xA = 0; xA < a.numRows; xA++) {
/*  339 */       int end = aIndexStart + b.numCols;
/*  340 */       int indexB = 0;
/*  341 */       for (int xB = 0; xB < b.numRows; xB++) {
/*  342 */         int indexA = aIndexStart;
/*      */         
/*  344 */         double total = 0.0D;
/*      */         
/*  346 */         while (indexA < end) {
/*  347 */           total += a.get(indexA++) * b.get(indexB++);
/*      */         }
/*      */         
/*  350 */         c.set(cIndex++, total);
/*      */       } 
/*  352 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_reorder(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  361 */     if (a == c || b == c)
/*  362 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  363 */     if (a.numCols != b.numRows)
/*  364 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  365 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  366 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  369 */     if (a.numCols == 0 || a.numRows == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  373 */     int indexCbase = 0;
/*  374 */     int endOfKLoop = b.numRows * b.numCols;
/*      */     
/*  376 */     for (int i = 0; i < a.numRows; i++) {
/*  377 */       int indexA = i * a.numCols;
/*      */ 
/*      */       
/*  380 */       int indexB = 0;
/*  381 */       int indexC = indexCbase;
/*  382 */       int end = indexB + b.numCols;
/*      */       
/*  384 */       double valA = a.get(indexA++);
/*      */       
/*  386 */       while (indexB < end) {
/*  387 */         c.plus(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */ 
/*      */       
/*  391 */       while (indexB != endOfKLoop) {
/*  392 */         indexC = indexCbase;
/*  393 */         end = indexB + b.numCols;
/*      */         
/*  395 */         valA = a.get(indexA++);
/*      */         
/*  397 */         while (indexB < end) {
/*  398 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*  401 */       indexCbase += c.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_small(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  410 */     if (a == c || b == c)
/*  411 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  412 */     if (a.numCols != b.numRows)
/*  413 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  414 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  415 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  418 */     int aIndexStart = 0;
/*  419 */     int cIndex = 0;
/*      */     
/*  421 */     for (int i = 0; i < a.numRows; i++) {
/*  422 */       for (int j = 0; j < b.numCols; j++) {
/*  423 */         double total = 0.0D;
/*      */         
/*  425 */         int indexA = aIndexStart;
/*  426 */         int indexB = j;
/*  427 */         int end = indexA + b.numRows;
/*  428 */         while (indexA < end) {
/*  429 */           total += a.get(indexA++) * b.get(indexB);
/*  430 */           indexB += b.numCols;
/*      */         } 
/*      */         
/*  433 */         c.plus(cIndex++, total);
/*      */       } 
/*  435 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_aux(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/*  444 */     if (a == c || b == c)
/*  445 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  446 */     if (a.numCols != b.numRows)
/*  447 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  448 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  449 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  452 */     if (aux == null) aux = new double[b.numRows];
/*      */     
/*  454 */     for (int j = 0; j < b.numCols; j++) {
/*      */       
/*  456 */       for (int k = 0; k < b.numRows; k++) {
/*  457 */         aux[k] = b.unsafe_get(k, j);
/*      */       }
/*      */       
/*  460 */       int indexA = 0;
/*  461 */       for (int i = 0; i < a.numRows; i++) {
/*  462 */         double total = 0.0D;
/*  463 */         for (int m = 0; m < b.numRows;) {
/*  464 */           total += a.get(indexA++) * aux[m++];
/*      */         }
/*  466 */         c.plus(i * c.numCols + j, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransA_reorder(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  476 */     if (a == c || b == c)
/*  477 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  478 */     if (a.numRows != b.numRows)
/*  479 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  480 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/*  481 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  484 */     if (a.numCols == 0 || a.numRows == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  489 */     for (int i = 0; i < a.numCols; i++) {
/*  490 */       int indexC_start = i * c.numCols;
/*      */ 
/*      */       
/*  493 */       double valA = a.get(i);
/*  494 */       int indexB = 0;
/*  495 */       int end = indexB + b.numCols;
/*  496 */       int indexC = indexC_start;
/*  497 */       while (indexB < end) {
/*  498 */         c.plus(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */       
/*  501 */       for (int k = 1; k < a.numRows; k++) {
/*  502 */         valA = a.unsafe_get(k, i);
/*  503 */         end = indexB + b.numCols;
/*  504 */         indexC = indexC_start;
/*      */         
/*  506 */         while (indexB < end) {
/*  507 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransA_small(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  518 */     if (a == c || b == c)
/*  519 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  520 */     if (a.numRows != b.numRows)
/*  521 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  522 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/*  523 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  526 */     int cIndex = 0;
/*      */     
/*  528 */     for (int i = 0; i < a.numCols; i++) {
/*  529 */       for (int j = 0; j < b.numCols; j++) {
/*  530 */         int indexA = i;
/*  531 */         int indexB = j;
/*  532 */         int end = indexB + b.numRows * b.numCols;
/*      */         
/*  534 */         double total = 0.0D;
/*      */ 
/*      */         
/*  537 */         for (; indexB < end; indexB += b.numCols) {
/*  538 */           total += a.get(indexA) * b.get(indexB);
/*  539 */           indexA += a.numCols;
/*      */         } 
/*      */         
/*  542 */         c.plus(cIndex++, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransAB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  552 */     if (a == c || b == c)
/*  553 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  554 */     if (a.numRows != b.numCols)
/*  555 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  556 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/*  557 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  560 */     int cIndex = 0;
/*      */     
/*  562 */     for (int i = 0; i < a.numCols; i++) {
/*  563 */       int indexB = 0;
/*  564 */       for (int j = 0; j < b.numRows; j++) {
/*  565 */         int indexA = i;
/*  566 */         int end = indexB + b.numCols;
/*      */         
/*  568 */         double total = 0.0D;
/*      */         
/*  570 */         while (indexB < end) {
/*  571 */           total += a.get(indexA) * b.get(indexB++);
/*  572 */           indexA += a.numCols;
/*      */         } 
/*      */         
/*  575 */         c.plus(cIndex++, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransAB_aux(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/*  585 */     if (a == c || b == c)
/*  586 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  587 */     if (a.numRows != b.numCols)
/*  588 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  589 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/*  590 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  593 */     if (aux == null) aux = new double[a.numRows];
/*      */     
/*  595 */     if (a.numCols == 0 || a.numRows == 0) {
/*      */       return;
/*      */     }
/*  598 */     int indexC = 0;
/*  599 */     for (int i = 0; i < a.numCols; i++) {
/*  600 */       for (int k = 0; k < b.numCols; k++) {
/*  601 */         aux[k] = a.unsafe_get(k, i);
/*      */       }
/*      */       
/*  604 */       for (int j = 0; j < b.numRows; j++) {
/*  605 */         double total = 0.0D;
/*      */         
/*  607 */         for (int m = 0; m < b.numCols; m++) {
/*  608 */           total += aux[m] * b.unsafe_get(j, m);
/*      */         }
/*  610 */         c.plus(indexC++, total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransB(RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  620 */     if (a == c || b == c)
/*  621 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  622 */     if (a.numCols != b.numCols)
/*  623 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  624 */     if (a.numRows != c.numRows || b.numRows != c.numCols) {
/*  625 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  628 */     int cIndex = 0;
/*  629 */     int aIndexStart = 0;
/*      */     
/*  631 */     for (int xA = 0; xA < a.numRows; xA++) {
/*  632 */       int end = aIndexStart + b.numCols;
/*  633 */       int indexB = 0;
/*  634 */       for (int xB = 0; xB < b.numRows; xB++) {
/*  635 */         int indexA = aIndexStart;
/*      */         
/*  637 */         double total = 0.0D;
/*      */         
/*  639 */         while (indexA < end) {
/*  640 */           total += a.get(indexA++) * b.get(indexB++);
/*      */         }
/*      */         
/*  643 */         c.plus(cIndex++, total);
/*      */       } 
/*  645 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mult_reorder(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  654 */     if (a == c || b == c)
/*  655 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  656 */     if (a.numCols != b.numRows)
/*  657 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  658 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  659 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  662 */     if (a.numCols == 0 || a.numRows == 0) {
/*  663 */       CommonOps.fill((D1Matrix64F)c, 0.0D);
/*      */       
/*      */       return;
/*      */     } 
/*  667 */     int indexCbase = 0;
/*  668 */     int endOfKLoop = b.numRows * b.numCols;
/*      */     
/*  670 */     for (int i = 0; i < a.numRows; i++) {
/*  671 */       int indexA = i * a.numCols;
/*      */ 
/*      */       
/*  674 */       int indexB = 0;
/*  675 */       int indexC = indexCbase;
/*  676 */       int end = indexB + b.numCols;
/*      */       
/*  678 */       double valA = alpha * a.get(indexA++);
/*      */       
/*  680 */       while (indexB < end) {
/*  681 */         c.set(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */ 
/*      */       
/*  685 */       while (indexB != endOfKLoop) {
/*  686 */         indexC = indexCbase;
/*  687 */         end = indexB + b.numCols;
/*      */         
/*  689 */         valA = alpha * a.get(indexA++);
/*      */         
/*  691 */         while (indexB < end) {
/*  692 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*  695 */       indexCbase += c.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mult_small(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  704 */     if (a == c || b == c)
/*  705 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  706 */     if (a.numCols != b.numRows)
/*  707 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  708 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  709 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  712 */     int aIndexStart = 0;
/*  713 */     int cIndex = 0;
/*      */     
/*  715 */     for (int i = 0; i < a.numRows; i++) {
/*  716 */       for (int j = 0; j < b.numCols; j++) {
/*  717 */         double total = 0.0D;
/*      */         
/*  719 */         int indexA = aIndexStart;
/*  720 */         int indexB = j;
/*  721 */         int end = indexA + b.numRows;
/*  722 */         while (indexA < end) {
/*  723 */           total += a.get(indexA++) * b.get(indexB);
/*  724 */           indexB += b.numCols;
/*      */         } 
/*      */         
/*  727 */         c.set(cIndex++, alpha * total);
/*      */       } 
/*  729 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mult_aux(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/*  738 */     if (a == c || b == c)
/*  739 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  740 */     if (a.numCols != b.numRows)
/*  741 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  742 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  743 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  746 */     if (aux == null) aux = new double[b.numRows];
/*      */     
/*  748 */     for (int j = 0; j < b.numCols; j++) {
/*      */       
/*  750 */       for (int k = 0; k < b.numRows; k++) {
/*  751 */         aux[k] = b.unsafe_get(k, j);
/*      */       }
/*      */       
/*  754 */       int indexA = 0;
/*  755 */       for (int i = 0; i < a.numRows; i++) {
/*  756 */         double total = 0.0D;
/*  757 */         for (int m = 0; m < b.numRows;) {
/*  758 */           total += a.get(indexA++) * aux[m++];
/*      */         }
/*  760 */         c.set(i * c.numCols + j, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransA_reorder(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  770 */     if (a == c || b == c)
/*  771 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  772 */     if (a.numRows != b.numRows)
/*  773 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  774 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/*  775 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  778 */     if (a.numCols == 0 || a.numRows == 0) {
/*  779 */       CommonOps.fill((D1Matrix64F)c, 0.0D);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  784 */     for (int i = 0; i < a.numCols; i++) {
/*  785 */       int indexC_start = i * c.numCols;
/*      */ 
/*      */       
/*  788 */       double valA = alpha * a.get(i);
/*  789 */       int indexB = 0;
/*  790 */       int end = indexB + b.numCols;
/*  791 */       int indexC = indexC_start;
/*  792 */       while (indexB < end) {
/*  793 */         c.set(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */       
/*  796 */       for (int k = 1; k < a.numRows; k++) {
/*  797 */         valA = alpha * a.unsafe_get(k, i);
/*  798 */         end = indexB + b.numCols;
/*  799 */         indexC = indexC_start;
/*      */         
/*  801 */         while (indexB < end) {
/*  802 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransA_small(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  813 */     if (a == c || b == c)
/*  814 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  815 */     if (a.numRows != b.numRows)
/*  816 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  817 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/*  818 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  821 */     int cIndex = 0;
/*      */     
/*  823 */     for (int i = 0; i < a.numCols; i++) {
/*  824 */       for (int j = 0; j < b.numCols; j++) {
/*  825 */         int indexA = i;
/*  826 */         int indexB = j;
/*  827 */         int end = indexB + b.numRows * b.numCols;
/*      */         
/*  829 */         double total = 0.0D;
/*      */ 
/*      */         
/*  832 */         for (; indexB < end; indexB += b.numCols) {
/*  833 */           total += a.get(indexA) * b.get(indexB);
/*  834 */           indexA += a.numCols;
/*      */         } 
/*      */         
/*  837 */         c.set(cIndex++, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransAB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  847 */     if (a == c || b == c)
/*  848 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  849 */     if (a.numRows != b.numCols)
/*  850 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  851 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/*  852 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  855 */     int cIndex = 0;
/*      */     
/*  857 */     for (int i = 0; i < a.numCols; i++) {
/*  858 */       int indexB = 0;
/*  859 */       for (int j = 0; j < b.numRows; j++) {
/*  860 */         int indexA = i;
/*  861 */         int end = indexB + b.numCols;
/*      */         
/*  863 */         double total = 0.0D;
/*      */         
/*  865 */         while (indexB < end) {
/*  866 */           total += a.get(indexA) * b.get(indexB++);
/*  867 */           indexA += a.numCols;
/*      */         } 
/*      */         
/*  870 */         c.set(cIndex++, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransAB_aux(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/*  880 */     if (a == c || b == c)
/*  881 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  882 */     if (a.numRows != b.numCols)
/*  883 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  884 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/*  885 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  888 */     if (aux == null) aux = new double[a.numRows];
/*      */     
/*  890 */     if (a.numCols == 0 || a.numRows == 0) {
/*  891 */       CommonOps.fill((D1Matrix64F)c, 0.0D);
/*      */       return;
/*      */     } 
/*  894 */     int indexC = 0;
/*  895 */     for (int i = 0; i < a.numCols; i++) {
/*  896 */       for (int k = 0; k < b.numCols; k++) {
/*  897 */         aux[k] = a.unsafe_get(k, i);
/*      */       }
/*      */       
/*  900 */       for (int j = 0; j < b.numRows; j++) {
/*  901 */         double total = 0.0D;
/*      */         
/*  903 */         for (int m = 0; m < b.numCols; m++) {
/*  904 */           total += aux[m] * b.unsafe_get(j, m);
/*      */         }
/*  906 */         c.set(indexC++, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multTransB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  916 */     if (a == c || b == c)
/*  917 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  918 */     if (a.numCols != b.numCols)
/*  919 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  920 */     if (a.numRows != c.numRows || b.numRows != c.numCols) {
/*  921 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  924 */     int cIndex = 0;
/*  925 */     int aIndexStart = 0;
/*      */     
/*  927 */     for (int xA = 0; xA < a.numRows; xA++) {
/*  928 */       int end = aIndexStart + b.numCols;
/*  929 */       int indexB = 0;
/*  930 */       for (int xB = 0; xB < b.numRows; xB++) {
/*  931 */         int indexA = aIndexStart;
/*      */         
/*  933 */         double total = 0.0D;
/*      */         
/*  935 */         while (indexA < end) {
/*  936 */           total += a.get(indexA++) * b.get(indexB++);
/*      */         }
/*      */         
/*  939 */         c.set(cIndex++, alpha * total);
/*      */       } 
/*  941 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_reorder(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  950 */     if (a == c || b == c)
/*  951 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/*  952 */     if (a.numCols != b.numRows)
/*  953 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/*  954 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/*  955 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/*  958 */     if (a.numCols == 0 || a.numRows == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  962 */     int indexCbase = 0;
/*  963 */     int endOfKLoop = b.numRows * b.numCols;
/*      */     
/*  965 */     for (int i = 0; i < a.numRows; i++) {
/*  966 */       int indexA = i * a.numCols;
/*      */ 
/*      */       
/*  969 */       int indexB = 0;
/*  970 */       int indexC = indexCbase;
/*  971 */       int end = indexB + b.numCols;
/*      */       
/*  973 */       double valA = alpha * a.get(indexA++);
/*      */       
/*  975 */       while (indexB < end) {
/*  976 */         c.plus(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */ 
/*      */       
/*  980 */       while (indexB != endOfKLoop) {
/*  981 */         indexC = indexCbase;
/*  982 */         end = indexB + b.numCols;
/*      */         
/*  984 */         valA = alpha * a.get(indexA++);
/*      */         
/*  986 */         while (indexB < end) {
/*  987 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*  990 */       indexCbase += c.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_small(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/*  999 */     if (a == c || b == c)
/* 1000 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1001 */     if (a.numCols != b.numRows)
/* 1002 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1003 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/* 1004 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1007 */     int aIndexStart = 0;
/* 1008 */     int cIndex = 0;
/*      */     
/* 1010 */     for (int i = 0; i < a.numRows; i++) {
/* 1011 */       for (int j = 0; j < b.numCols; j++) {
/* 1012 */         double total = 0.0D;
/*      */         
/* 1014 */         int indexA = aIndexStart;
/* 1015 */         int indexB = j;
/* 1016 */         int end = indexA + b.numRows;
/* 1017 */         while (indexA < end) {
/* 1018 */           total += a.get(indexA++) * b.get(indexB);
/* 1019 */           indexB += b.numCols;
/*      */         } 
/*      */         
/* 1022 */         c.plus(cIndex++, alpha * total);
/*      */       } 
/* 1024 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_aux(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/* 1033 */     if (a == c || b == c)
/* 1034 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1035 */     if (a.numCols != b.numRows)
/* 1036 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1037 */     if (a.numRows != c.numRows || b.numCols != c.numCols) {
/* 1038 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1041 */     if (aux == null) aux = new double[b.numRows];
/*      */     
/* 1043 */     for (int j = 0; j < b.numCols; j++) {
/*      */       
/* 1045 */       for (int k = 0; k < b.numRows; k++) {
/* 1046 */         aux[k] = b.unsafe_get(k, j);
/*      */       }
/*      */       
/* 1049 */       int indexA = 0;
/* 1050 */       for (int i = 0; i < a.numRows; i++) {
/* 1051 */         double total = 0.0D;
/* 1052 */         for (int m = 0; m < b.numRows;) {
/* 1053 */           total += a.get(indexA++) * aux[m++];
/*      */         }
/* 1055 */         c.plus(i * c.numCols + j, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransA_reorder(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/* 1065 */     if (a == c || b == c)
/* 1066 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1067 */     if (a.numRows != b.numRows)
/* 1068 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1069 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/* 1070 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1073 */     if (a.numCols == 0 || a.numRows == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1078 */     for (int i = 0; i < a.numCols; i++) {
/* 1079 */       int indexC_start = i * c.numCols;
/*      */ 
/*      */       
/* 1082 */       double valA = alpha * a.get(i);
/* 1083 */       int indexB = 0;
/* 1084 */       int end = indexB + b.numCols;
/* 1085 */       int indexC = indexC_start;
/* 1086 */       while (indexB < end) {
/* 1087 */         c.plus(indexC++, valA * b.get(indexB++));
/*      */       }
/*      */       
/* 1090 */       for (int k = 1; k < a.numRows; k++) {
/* 1091 */         valA = alpha * a.unsafe_get(k, i);
/* 1092 */         end = indexB + b.numCols;
/* 1093 */         indexC = indexC_start;
/*      */         
/* 1095 */         while (indexB < end) {
/* 1096 */           c.plus(indexC++, valA * b.get(indexB++));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransA_small(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/* 1107 */     if (a == c || b == c)
/* 1108 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1109 */     if (a.numRows != b.numRows)
/* 1110 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1111 */     if (a.numCols != c.numRows || b.numCols != c.numCols) {
/* 1112 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1115 */     int cIndex = 0;
/*      */     
/* 1117 */     for (int i = 0; i < a.numCols; i++) {
/* 1118 */       for (int j = 0; j < b.numCols; j++) {
/* 1119 */         int indexA = i;
/* 1120 */         int indexB = j;
/* 1121 */         int end = indexB + b.numRows * b.numCols;
/*      */         
/* 1123 */         double total = 0.0D;
/*      */ 
/*      */         
/* 1126 */         for (; indexB < end; indexB += b.numCols) {
/* 1127 */           total += a.get(indexA) * b.get(indexB);
/* 1128 */           indexA += a.numCols;
/*      */         } 
/*      */         
/* 1131 */         c.plus(cIndex++, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransAB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/* 1141 */     if (a == c || b == c)
/* 1142 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1143 */     if (a.numRows != b.numCols)
/* 1144 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1145 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/* 1146 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1149 */     int cIndex = 0;
/*      */     
/* 1151 */     for (int i = 0; i < a.numCols; i++) {
/* 1152 */       int indexB = 0;
/* 1153 */       for (int j = 0; j < b.numRows; j++) {
/* 1154 */         int indexA = i;
/* 1155 */         int end = indexB + b.numCols;
/*      */         
/* 1157 */         double total = 0.0D;
/*      */         
/* 1159 */         while (indexB < end) {
/* 1160 */           total += a.get(indexA) * b.get(indexB++);
/* 1161 */           indexA += a.numCols;
/*      */         } 
/*      */         
/* 1164 */         c.plus(cIndex++, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransAB_aux(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c, double[] aux) {
/* 1174 */     if (a == c || b == c)
/* 1175 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1176 */     if (a.numRows != b.numCols)
/* 1177 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1178 */     if (a.numCols != c.numRows || b.numRows != c.numCols) {
/* 1179 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1182 */     if (aux == null) aux = new double[a.numRows];
/*      */     
/* 1184 */     if (a.numCols == 0 || a.numRows == 0) {
/*      */       return;
/*      */     }
/* 1187 */     int indexC = 0;
/* 1188 */     for (int i = 0; i < a.numCols; i++) {
/* 1189 */       for (int k = 0; k < b.numCols; k++) {
/* 1190 */         aux[k] = a.unsafe_get(k, i);
/*      */       }
/*      */       
/* 1193 */       for (int j = 0; j < b.numRows; j++) {
/* 1194 */         double total = 0.0D;
/*      */         
/* 1196 */         for (int m = 0; m < b.numCols; m++) {
/* 1197 */           total += aux[m] * b.unsafe_get(j, m);
/*      */         }
/* 1199 */         c.plus(indexC++, alpha * total);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAddTransB(double alpha, RowD1Matrix64F a, RowD1Matrix64F b, RowD1Matrix64F c) {
/* 1209 */     if (a == c || b == c)
/* 1210 */       throw new IllegalArgumentException("Neither 'a' or 'b' can be the same matrix as 'c'"); 
/* 1211 */     if (a.numCols != b.numCols)
/* 1212 */       throw new MatrixDimensionException("The 'a' and 'b' matrices do not have compatible dimensions"); 
/* 1213 */     if (a.numRows != c.numRows || b.numRows != c.numCols) {
/* 1214 */       throw new MatrixDimensionException("The results matrix does not have the desired dimensions");
/*      */     }
/*      */     
/* 1217 */     int cIndex = 0;
/* 1218 */     int aIndexStart = 0;
/*      */     
/* 1220 */     for (int xA = 0; xA < a.numRows; xA++) {
/* 1221 */       int end = aIndexStart + b.numCols;
/* 1222 */       int indexB = 0;
/* 1223 */       for (int xB = 0; xB < b.numRows; xB++) {
/* 1224 */         int indexA = aIndexStart;
/*      */         
/* 1226 */         double total = 0.0D;
/*      */         
/* 1228 */         while (indexA < end) {
/* 1229 */           total += a.get(indexA++) * b.get(indexB++);
/*      */         }
/*      */         
/* 1232 */         c.plus(cIndex++, alpha * total);
/*      */       } 
/* 1234 */       aIndexStart += a.numCols;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\mult\MatrixMatrixMult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */