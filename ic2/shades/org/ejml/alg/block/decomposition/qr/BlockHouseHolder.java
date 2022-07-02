/*      */ package ic2.shades.org.ejml.alg.block.decomposition.qr;
/*      */ 
/*      */ import ic2.shades.org.ejml.alg.block.BlockInnerMultiplication;
/*      */ import ic2.shades.org.ejml.alg.block.BlockVectorOps;
/*      */ import ic2.shades.org.ejml.data.D1Submatrix64F;
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
/*      */ public class BlockHouseHolder
/*      */ {
/*      */   public static boolean decomposeQR_block_col(int blockLength, D1Submatrix64F Y, double[] gamma) {
/*   53 */     int width = Y.col1 - Y.col0;
/*   54 */     int height = Y.row1 - Y.row0;
/*   55 */     int min = Math.min(width, height);
/*   56 */     for (int i = 0; i < min; i++) {
/*      */       
/*   58 */       if (!computeHouseHolderCol(blockLength, Y, gamma, i)) {
/*   59 */         return false;
/*      */       }
/*      */       
/*   62 */       rank1UpdateMultR_Col(blockLength, Y, i, gamma[Y.col0 + i]);
/*      */     } 
/*      */     
/*   65 */     return true;
/*      */   }
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
/*      */   public static boolean computeHouseHolderCol(int blockLength, D1Submatrix64F Y, double[] gamma, int i) {
/*   87 */     double max = findMaxCol(blockLength, Y, i);
/*      */     
/*   89 */     if (max == 0.0D) {
/*   90 */       return false;
/*      */     }
/*      */     
/*   93 */     double tau = computeTauAndDivideCol(blockLength, Y, i, max);
/*      */ 
/*      */     
/*   96 */     double u_0 = Y.get(i, i) + tau;
/*   97 */     divideElementsCol(blockLength, Y, i, u_0);
/*      */     
/*   99 */     gamma[Y.col0 + i] = u_0 / tau;
/*  100 */     tau *= max;
/*      */ 
/*      */     
/*  103 */     Y.set(i, i, -tau);
/*      */     
/*  105 */     return true;
/*      */   }
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
/*      */   public static boolean computeHouseHolderRow(int blockLength, D1Submatrix64F Y, double[] gamma, int i) {
/*  126 */     double max = findMaxRow(blockLength, Y, i, i + 1);
/*      */     
/*  128 */     if (max == 0.0D) {
/*  129 */       return false;
/*      */     }
/*      */     
/*  132 */     double tau = computeTauAndDivideRow(blockLength, Y, i, i + 1, max);
/*      */ 
/*      */     
/*  135 */     double u_0 = Y.get(i, i + 1) + tau;
/*  136 */     BlockVectorOps.div_row(blockLength, Y, i, u_0, Y, i, i + 1, Y.col1 - Y.col0);
/*      */     
/*  138 */     gamma[Y.row0 + i] = u_0 / tau;
/*      */ 
/*      */     
/*  141 */     Y.set(i, i + 1, -tau * max);
/*      */     
/*  143 */     return true;
/*      */   }
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
/*      */   public static void rank1UpdateMultR_Col(int blockLength, D1Submatrix64F A, int col, double gamma) {
/*  161 */     int width = Math.min(blockLength, A.col1 - A.col0);
/*      */     
/*  163 */     double[] dataA = A.original.data;
/*      */     
/*  165 */     for (int j = col + 1; j < width; j++) {
/*      */ 
/*      */       
/*  168 */       double total = innerProdCol(blockLength, A, col, width, j, width);
/*      */       
/*  170 */       total *= gamma;
/*      */       
/*      */       int i;
/*  173 */       for (i = A.row0; i < A.row1; i += blockLength) {
/*  174 */         int height = Math.min(blockLength, A.row1 - i);
/*      */         
/*  176 */         int indexU = i * A.original.numCols + height * A.col0 + col;
/*  177 */         int indexA = i * A.original.numCols + height * A.col0 + j;
/*      */         
/*  179 */         if (i == A.row0) {
/*  180 */           indexU += width * (col + 1);
/*  181 */           indexA += width * col;
/*      */           
/*  183 */           dataA[indexA] = dataA[indexA] - total;
/*      */           
/*  185 */           indexA += width;
/*      */           
/*  187 */           for (int k = col + 1; k < height; k++, indexU += width, indexA += width) {
/*  188 */             dataA[indexA] = dataA[indexA] - total * dataA[indexU];
/*      */           }
/*      */         } else {
/*  191 */           int endU = indexU + width * height;
/*      */           
/*  193 */           for (; indexU != endU; indexU += width, indexA += width) {
/*  194 */             dataA[indexA] = dataA[indexA] - total * dataA[indexU];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void rank1UpdateMultR_TopRow(int blockLength, D1Submatrix64F A, int col, double gamma) {
/*  216 */     double[] dataA = A.original.data;
/*      */     
/*  218 */     int widthCol = Math.min(blockLength, A.col1 - col);
/*      */     
/*      */     int colStartJ;
/*  221 */     for (colStartJ = A.col0 + blockLength; colStartJ < A.col1; colStartJ += blockLength) {
/*  222 */       int widthJ = Math.min(blockLength, A.col1 - colStartJ);
/*      */       
/*  224 */       for (int j = 0; j < widthJ; j++) {
/*      */         
/*  226 */         double total = innerProdCol(blockLength, A, col, widthCol, colStartJ - A.col0 + j, widthJ) * gamma;
/*      */ 
/*      */ 
/*      */         
/*  230 */         int i = A.row0;
/*  231 */         int height = Math.min(blockLength, A.row1 - i);
/*      */         
/*  233 */         int indexU = i * A.original.numCols + height * A.col0 + col;
/*  234 */         int indexA = i * A.original.numCols + height * colStartJ + j;
/*      */ 
/*      */         
/*  237 */         indexU += widthCol * (col + 1);
/*  238 */         indexA += widthJ * col;
/*      */         
/*  240 */         dataA[indexA] = dataA[indexA] - total;
/*      */         
/*  242 */         indexA += widthJ;
/*      */         
/*  244 */         for (int k = col + 1; k < height; k++, indexU += widthCol, indexA += widthJ) {
/*  245 */           dataA[indexA] = dataA[indexA] - total * dataA[indexU];
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void rank1UpdateMultL_Row(int blockLength, D1Submatrix64F A, int row, int colStart, double gamma) {
/*  268 */     int height = Math.min(blockLength, A.row1 - A.row0);
/*      */     
/*  270 */     double[] dataA = A.original.data;
/*      */     
/*  272 */     int zeroOffset = colStart - row;
/*      */     
/*  274 */     for (int i = row + 1; i < height; i++) {
/*      */       
/*  276 */       double total = innerProdRow(blockLength, A, row, A, i, zeroOffset);
/*      */       
/*  278 */       total *= gamma;
/*      */       
/*      */       int j;
/*  281 */       for (j = A.col0; j < A.col1; j += blockLength) {
/*  282 */         int width = Math.min(blockLength, A.col1 - j);
/*      */         
/*  284 */         int indexU = A.row0 * A.original.numCols + height * j + row * width;
/*  285 */         int indexA = A.row0 * A.original.numCols + height * j + i * width;
/*      */         
/*  287 */         if (j == A.col0) {
/*  288 */           indexU += colStart + 1;
/*  289 */           indexA += colStart;
/*      */           
/*  291 */           dataA[indexA++] = dataA[indexA++] - total;
/*      */           
/*  293 */           for (int k = colStart + 1; k < width; k++) {
/*  294 */             dataA[indexA++] = dataA[indexA++] - total * dataA[indexU++];
/*      */           }
/*      */         } else {
/*  297 */           for (int k = 0; k < width; k++) {
/*  298 */             dataA[indexA++] = dataA[indexA++] - total * dataA[indexU++];
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void rank1UpdateMultL_LeftCol(int blockLength, D1Submatrix64F A, int row, double gamma, int zeroOffset) {
/*  322 */     int heightU = Math.min(blockLength, A.row1 - A.row0);
/*  323 */     int width = Math.min(blockLength, A.col1 - A.col0);
/*      */     
/*  325 */     double[] data = A.original.data;
/*      */     int blockStart;
/*  327 */     for (blockStart = A.row0 + blockLength; blockStart < A.row1; blockStart += blockLength) {
/*  328 */       int heightA = Math.min(blockLength, A.row1 - blockStart);
/*      */       
/*  330 */       for (int i = 0; i < heightA; i++) {
/*      */ 
/*      */         
/*  333 */         double total = innerProdRow(blockLength, A, row, A, i + blockStart - A.row0, zeroOffset);
/*      */         
/*  335 */         total *= gamma;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  340 */         int indexU = A.row0 * A.original.numCols + heightU * A.col0 + row * width;
/*  341 */         int indexA = blockStart * A.original.numCols + heightA * A.col0 + i * width;
/*      */ 
/*      */         
/*  344 */         indexU += zeroOffset + 1;
/*  345 */         indexA += zeroOffset;
/*      */         
/*  347 */         data[indexA++] = data[indexA++] - total;
/*      */         
/*  349 */         for (int k = zeroOffset + 1; k < width; k++) {
/*  350 */           data[indexA++] = data[indexA++] - total * data[indexU++];
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static double innerProdCol(int blockLength, D1Submatrix64F A, int colA, int widthA, int colB, int widthB) {
/*  379 */     double total = 0.0D;
/*      */     
/*  381 */     double[] data = A.original.data;
/*      */     
/*  383 */     int colBlockA = A.col0 + colA - colA % blockLength;
/*  384 */     int colBlockB = A.col0 + colB - colB % blockLength;
/*  385 */     colA %= blockLength;
/*  386 */     colB %= blockLength;
/*      */     
/*      */     int i;
/*  389 */     for (i = A.row0; i < A.row1; i += blockLength) {
/*      */       
/*  391 */       int height = Math.min(blockLength, A.row1 - i);
/*      */       
/*  393 */       int indexA = i * A.original.numCols + height * colBlockA + colA;
/*  394 */       int indexB = i * A.original.numCols + height * colBlockB + colB;
/*      */       
/*  396 */       if (i == A.row0) {
/*      */         
/*  398 */         indexA += widthA * (colA + 1);
/*  399 */         indexB += widthB * colA;
/*      */ 
/*      */         
/*  402 */         total = data[indexB];
/*      */         
/*  404 */         indexB += widthB;
/*      */ 
/*      */         
/*  407 */         int endA = indexA + (height - colA - 1) * widthA;
/*  408 */         for (; indexA != endA; indexA += widthA, indexB += widthB)
/*      */         {
/*  410 */           total += data[indexA] * data[indexB];
/*      */         }
/*      */       } else {
/*      */         
/*  414 */         int endA = indexA + widthA * height;
/*      */         
/*  416 */         for (; indexA != endA; indexA += widthA, indexB += widthB) {
/*  417 */           total += data[indexA] * data[indexB];
/*      */         }
/*      */       } 
/*      */     } 
/*  421 */     return total;
/*      */   }
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
/*      */   public static double innerProdRow(int blockLength, D1Submatrix64F A, int rowA, D1Submatrix64F B, int rowB, int zeroOffset) {
/*  446 */     int offset = rowA + zeroOffset;
/*  447 */     if (offset + B.col0 >= B.col1) {
/*  448 */       return 0.0D;
/*      */     }
/*      */     
/*  451 */     double total = B.get(rowB, offset);
/*      */     
/*  453 */     total += BlockVectorOps.dot_row(blockLength, A, rowA, B, rowB, offset + 1, A.col1 - A.col0);
/*      */     
/*  455 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add_row(int blockLength, D1Submatrix64F A, int rowA, double alpha, D1Submatrix64F B, int rowB, double beta, D1Submatrix64F C, int rowC, int zeroOffset, int end) {
/*  463 */     int offset = rowA + zeroOffset;
/*      */     
/*  465 */     if (C.col0 + offset >= C.col1) {
/*      */       return;
/*      */     }
/*  468 */     C.set(rowC, offset, alpha + B.get(rowB, offset) * beta);
/*      */     
/*  470 */     BlockVectorOps.add_row(blockLength, A, rowA, alpha, B, rowB, beta, C, rowC, offset + 1, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void divideElementsCol(int blockLength, D1Submatrix64F Y, int col, double val) {
/*  479 */     int width = Math.min(blockLength, Y.col1 - Y.col0);
/*      */     
/*  481 */     double[] dataY = Y.original.data;
/*      */     int i;
/*  483 */     for (i = Y.row0; i < Y.row1; i += blockLength) {
/*  484 */       int height = Math.min(blockLength, Y.row1 - i);
/*      */       
/*  486 */       int index = i * Y.original.numCols + height * Y.col0 + col;
/*      */       
/*  488 */       if (i == Y.row0) {
/*  489 */         index += width * (col + 1);
/*      */         
/*  491 */         for (int k = col + 1; k < height; k++, index += width) {
/*  492 */           dataY[index] = dataY[index] / val;
/*      */         }
/*      */       } else {
/*  495 */         int endIndex = index + width * height;
/*      */         
/*  497 */         for (; index != endIndex; index += width) {
/*  498 */           dataY[index] = dataY[index] / val;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void scale_row(int blockLength, D1Submatrix64F Y, D1Submatrix64F W, int row, int zeroOffset, double val) {
/*  521 */     int offset = row + zeroOffset;
/*      */     
/*  523 */     if (offset >= W.col1 - W.col0) {
/*      */       return;
/*      */     }
/*      */     
/*  527 */     W.set(row, offset, val);
/*      */ 
/*      */     
/*  530 */     BlockVectorOps.scale_row(blockLength, Y, row, val, W, row, offset + 1, Y.col1 - Y.col0);
/*      */   }
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
/*      */   public static double computeTauAndDivideCol(int blockLength, D1Submatrix64F Y, int col, double max) {
/*  553 */     int width = Math.min(blockLength, Y.col1 - Y.col0);
/*      */     
/*  555 */     double[] dataY = Y.original.data;
/*      */     
/*  557 */     double top = 0.0D;
/*  558 */     double norm2 = 0.0D;
/*      */     int i;
/*  560 */     for (i = Y.row0; i < Y.row1; i += blockLength) {
/*  561 */       int height = Math.min(blockLength, Y.row1 - i);
/*      */       
/*  563 */       int index = i * Y.original.numCols + height * Y.col0 + col;
/*      */       
/*  565 */       if (i == Y.row0) {
/*  566 */         index += width * col;
/*      */         
/*  568 */         top = dataY[index] = dataY[index] / max;
/*  569 */         norm2 += top * top;
/*  570 */         index += width;
/*      */         
/*  572 */         for (int k = col + 1; k < height; k++, index += width) {
/*  573 */           double val = dataY[index] = dataY[index] / max;
/*  574 */           norm2 += val * val;
/*      */         } 
/*      */       } else {
/*  577 */         for (int k = 0; k < height; k++, index += width) {
/*  578 */           double val = dataY[index] = dataY[index] / max;
/*  579 */           norm2 += val * val;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  584 */     norm2 = Math.sqrt(norm2);
/*      */     
/*  586 */     if (top < 0.0D) {
/*  587 */       norm2 = -norm2;
/*      */     }
/*  589 */     return norm2;
/*      */   }
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
/*      */   public static double computeTauAndDivideRow(int blockLength, D1Submatrix64F Y, int row, int colStart, double max) {
/*  616 */     int height = Math.min(blockLength, Y.row1 - Y.row0);
/*      */     
/*  618 */     double[] dataY = Y.original.data;
/*      */     
/*  620 */     double top = 0.0D;
/*  621 */     double norm2 = 0.0D;
/*      */     
/*  623 */     int startJ = Y.col0 + colStart - colStart % blockLength;
/*  624 */     colStart %= blockLength;
/*      */     int j;
/*  626 */     for (j = startJ; j < Y.col1; j += blockLength) {
/*  627 */       int width = Math.min(blockLength, Y.col1 - j);
/*      */       
/*  629 */       int index = Y.row0 * Y.original.numCols + height * j + row * width;
/*      */       
/*  631 */       if (j == startJ) {
/*  632 */         index += colStart;
/*      */         
/*  634 */         top = dataY[index] = dataY[index] / max;
/*  635 */         norm2 += top * top;
/*  636 */         index++;
/*      */         
/*  638 */         for (int k = colStart + 1; k < width; k++) {
/*  639 */           double val = dataY[index++] = dataY[index++] / max;
/*  640 */           norm2 += val * val;
/*      */         } 
/*      */       } else {
/*  643 */         for (int k = 0; k < width; k++) {
/*  644 */           double val = dataY[index++] = dataY[index++] / max;
/*  645 */           norm2 += val * val;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  650 */     norm2 = Math.sqrt(norm2);
/*      */     
/*  652 */     if (top < 0.0D) {
/*  653 */       norm2 = -norm2;
/*      */     }
/*  655 */     return norm2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double findMaxCol(int blockLength, D1Submatrix64F Y, int col) {
/*  664 */     int width = Math.min(blockLength, Y.col1 - Y.col0);
/*      */     
/*  666 */     double[] dataY = Y.original.data;
/*      */     
/*  668 */     double max = 0.0D;
/*      */     int i;
/*  670 */     for (i = Y.row0; i < Y.row1; i += blockLength) {
/*  671 */       int height = Math.min(blockLength, Y.row1 - i);
/*      */       
/*  673 */       int index = i * Y.original.numCols + height * Y.col0 + col;
/*      */       
/*  675 */       if (i == Y.row0) {
/*  676 */         index += width * col;
/*  677 */         for (int k = col; k < height; k++, index += width) {
/*  678 */           double v = Math.abs(dataY[index]);
/*  679 */           if (v > max) {
/*  680 */             max = v;
/*      */           }
/*      */         } 
/*      */       } else {
/*  684 */         for (int k = 0; k < height; k++, index += width) {
/*  685 */           double v = Math.abs(dataY[index]);
/*  686 */           if (v > max) {
/*  687 */             max = v;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  693 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double findMaxRow(int blockLength, D1Submatrix64F Y, int row, int colStart) {
/*  703 */     int height = Math.min(blockLength, Y.row1 - Y.row0);
/*      */     
/*  705 */     double[] dataY = Y.original.data;
/*      */     
/*  707 */     double max = 0.0D;
/*      */     int j;
/*  709 */     for (j = Y.col0; j < Y.col1; j += blockLength) {
/*  710 */       int width = Math.min(blockLength, Y.col1 - j);
/*      */       
/*  712 */       int index = Y.row0 * Y.original.numCols + height * j + row * width;
/*      */       
/*  714 */       if (j == Y.col0) {
/*  715 */         index += colStart;
/*      */         
/*  717 */         for (int k = colStart; k < width; k++) {
/*  718 */           double v = Math.abs(dataY[index++]);
/*  719 */           if (v > max) {
/*  720 */             max = v;
/*      */           }
/*      */         } 
/*      */       } else {
/*  724 */         for (int k = 0; k < width; k++) {
/*  725 */           double v = Math.abs(dataY[index++]);
/*  726 */           if (v > max) {
/*  727 */             max = v;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  733 */     return max;
/*      */   }
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
/*      */   public static void computeW_Column(int blockLength, D1Submatrix64F Y, D1Submatrix64F W, double[] temp, double[] beta, int betaIndex) {
/*  769 */     int widthB = W.col1 - W.col0;
/*      */ 
/*      */     
/*  772 */     initializeW(blockLength, W, Y, widthB, beta[betaIndex++]);
/*      */     
/*  774 */     int min = Math.min(widthB, W.row1 - W.row0);
/*      */ 
/*      */     
/*  777 */     for (int j = 1; j < min; j++) {
/*      */       
/*  779 */       computeY_t_V(blockLength, Y, j, temp);
/*  780 */       computeZ(blockLength, Y, W, j, temp, beta[betaIndex++]);
/*      */     } 
/*      */   }
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
/*      */   public static void initializeW(int blockLength, D1Submatrix64F W, D1Submatrix64F Y, int widthB, double b) {
/*  803 */     double[] dataW = W.original.data;
/*  804 */     double[] dataY = Y.original.data;
/*      */     int i;
/*  806 */     for (i = W.row0; i < W.row1; i += blockLength) {
/*  807 */       int heightW = Math.min(blockLength, W.row1 - i);
/*      */       
/*  809 */       int indexW = i * W.original.numCols + heightW * W.col0;
/*  810 */       int indexY = i * Y.original.numCols + heightW * Y.col0;
/*      */ 
/*      */       
/*  813 */       if (i == W.row0) {
/*  814 */         dataW[indexW] = -b;
/*  815 */         indexW += widthB;
/*  816 */         indexY += widthB;
/*  817 */         for (int k = 1; k < heightW; k++, indexW += widthB, indexY += widthB) {
/*  818 */           dataW[indexW] = -b * dataY[indexY];
/*      */         }
/*      */       } else {
/*  821 */         for (int k = 0; k < heightW; k++, indexW += widthB, indexY += widthB) {
/*  822 */           dataW[indexW] = -b * dataY[indexY];
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void computeZ(int blockLength, D1Submatrix64F Y, D1Submatrix64F W, int col, double[] temp, double beta) {
/*  840 */     int width = Y.col1 - Y.col0;
/*      */     
/*  842 */     double[] dataW = W.original.data;
/*  843 */     double[] dataY = Y.original.data;
/*      */     
/*  845 */     int colsW = W.original.numCols;
/*      */     
/*  847 */     double beta_neg = -beta;
/*      */     int i;
/*  849 */     for (i = Y.row0; i < Y.row1; i += blockLength) {
/*  850 */       int heightW = Math.min(blockLength, Y.row1 - i);
/*      */       
/*  852 */       int indexW = i * colsW + heightW * W.col0;
/*  853 */       int indexZ = i * colsW + heightW * W.col0 + col;
/*  854 */       int indexV = i * Y.original.numCols + heightW * Y.col0 + col;
/*      */       
/*  856 */       if (i == Y.row0) {
/*      */         
/*  858 */         for (int k = 0; k < heightW; k++, indexZ += width, indexW += width, indexV += width) {
/*      */           
/*  860 */           double total = 0.0D;
/*      */           
/*  862 */           for (int j = 0; j < col; j++) {
/*  863 */             total += dataW[indexW + j] * temp[j];
/*      */           }
/*      */ 
/*      */           
/*  867 */           if (k < col) {
/*  868 */             dataW[indexZ] = -beta * total;
/*  869 */           } else if (k == col) {
/*  870 */             dataW[indexZ] = beta_neg * (1.0D + total);
/*      */           } else {
/*  872 */             dataW[indexZ] = beta_neg * (dataY[indexV] + total);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  876 */         int endZ = indexZ + width * heightW;
/*      */         
/*  878 */         while (indexZ != endZ) {
/*      */           
/*  880 */           double total = 0.0D;
/*      */           
/*  882 */           for (int j = 0; j < col; j++) {
/*  883 */             total += dataW[indexW + j] * temp[j];
/*      */           }
/*      */ 
/*      */           
/*  887 */           dataW[indexZ] = beta_neg * (dataY[indexV] + total);
/*      */           
/*  889 */           indexZ += width; indexW += width; indexV += width;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void computeY_t_V(int blockLength, D1Submatrix64F Y, int col, double[] temp) {
/*  906 */     int widthB = Y.col1 - Y.col0;
/*      */     
/*  908 */     for (int j = 0; j < col; j++) {
/*  909 */       temp[j] = innerProdCol(blockLength, Y, col, widthB, j, widthB);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void multAdd_zeros(int blockLength, D1Submatrix64F Y, D1Submatrix64F B, D1Submatrix64F C) {
/*  922 */     int widthY = Y.col1 - Y.col0;
/*      */     int i;
/*  924 */     for (i = Y.row0; i < Y.row1; i += blockLength) {
/*  925 */       int heightY = Math.min(blockLength, Y.row1 - i);
/*      */       int j;
/*  927 */       for (j = B.col0; j < B.col1; j += blockLength) {
/*  928 */         int widthB = Math.min(blockLength, B.col1 - j);
/*      */         
/*  930 */         int indexC = (i - Y.row0 + C.row0) * C.original.numCols + (j - B.col0 + C.col0) * heightY;
/*      */         int k;
/*  932 */         for (k = Y.col0; k < Y.col1; k += blockLength) {
/*  933 */           int indexY = i * Y.original.numCols + k * heightY;
/*  934 */           int indexB = (k - Y.col0 + B.row0) * B.original.numCols + j * widthY;
/*      */           
/*  936 */           if (i == Y.row0) {
/*  937 */             multBlockAdd_zerosone(Y.original.data, B.original.data, C.original.data, indexY, indexB, indexC, heightY, widthY, widthB);
/*      */           } else {
/*      */             
/*  940 */             BlockInnerMultiplication.blockMultPlus(Y.original.data, B.original.data, C.original.data, indexY, indexB, indexC, heightY, widthY, widthB);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void multBlockAdd_zerosone(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/*  961 */     for (int i = 0; i < heightA; i++) {
/*  962 */       for (int j = 0; j < widthC; j++) {
/*  963 */         double val = (i < widthA) ? dataB[i * widthC + j + indexB] : 0.0D;
/*      */         
/*  965 */         int end = Math.min(i, widthA);
/*      */         
/*  967 */         for (int k = 0; k < end; k++) {
/*  968 */           val += dataA[i * widthA + k + indexA] * dataB[k * widthC + j + indexB];
/*      */         }
/*      */         
/*  971 */         dataC[i * widthC + j + indexC] = dataC[i * widthC + j + indexC] + val;
/*      */       } 
/*      */     } 
/*      */   }
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
/*      */   public static void multTransA_vecCol(int blockLength, D1Submatrix64F A, D1Submatrix64F B, D1Submatrix64F C) {
/*  988 */     int widthA = A.col1 - A.col0;
/*  989 */     if (widthA > blockLength)
/*  990 */       throw new IllegalArgumentException("A is expected to be at most one block wide."); 
/*      */     int j;
/*  992 */     for (j = B.col0; j < B.col1; j += blockLength) {
/*  993 */       int widthB = Math.min(blockLength, B.col1 - j);
/*      */       
/*  995 */       int indexC = C.row0 * C.original.numCols + (j - B.col0 + C.col0) * widthA;
/*      */       int k;
/*  997 */       for (k = A.row0; k < A.row1; k += blockLength) {
/*  998 */         int heightA = Math.min(blockLength, A.row1 - k);
/*      */         
/* 1000 */         int indexA = k * A.original.numCols + A.col0 * heightA;
/* 1001 */         int indexB = (k - A.row0 + B.row0) * B.original.numCols + j * heightA;
/*      */         
/* 1003 */         if (k == A.row0) {
/* 1004 */           multTransABlockSet_lowerTriag(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*      */         } else {
/*      */           
/* 1007 */           BlockInnerMultiplication.blockMultPlusTransA(A.original.data, B.original.data, C.original.data, indexA, indexB, indexC, heightA, widthA, widthB);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void multTransABlockSet_lowerTriag(double[] dataA, double[] dataB, double[] dataC, int indexA, int indexB, int indexC, int heightA, int widthA, int widthC) {
/* 1022 */     for (int i = 0; i < widthA; i++) {
/* 1023 */       for (int j = 0; j < widthC; j++) {
/* 1024 */         double val = (i < heightA) ? dataB[i * widthC + j + indexB] : 0.0D;
/*      */         
/* 1026 */         for (int k = i + 1; k < heightA; k++) {
/* 1027 */           val += dataA[k * widthA + i + indexA] * dataB[k * widthC + j + indexB];
/*      */         }
/*      */         
/* 1030 */         dataC[i * widthC + j + indexC] = val;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\block\decomposition\qr\BlockHouseHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */