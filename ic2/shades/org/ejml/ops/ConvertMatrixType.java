/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.alg.block.BlockMatrixOps;
/*     */ import ic2.shades.org.ejml.data.BlockMatrix64F;
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix2_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix2x2_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix3_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix3x3_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix4_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix4x4_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix5_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix5x5_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix6_64F;
/*     */ import ic2.shades.org.ejml.data.FixedMatrix6x6_64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConvertMatrixType
/*     */ {
/*     */   public static void convert(Matrix64F input, Matrix64F output) {
/*  39 */     if (input.getNumRows() != output.getNumRows())
/*  40 */       throw new IllegalArgumentException("Number of rows do not match"); 
/*  41 */     if (input.getNumCols() != output.getNumCols()) {
/*  42 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/*  44 */     for (int i = 0; i < input.getNumRows(); i++) {
/*  45 */       for (int j = 0; j < input.getNumCols(); j++) {
/*  46 */         output.unsafe_set(i, j, input.unsafe_get(i, j));
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
/*     */   public static DenseMatrix64F convert(FixedMatrix2x2_64F input, DenseMatrix64F output) {
/*  59 */     if (output == null) {
/*  60 */       output = new DenseMatrix64F(2, 2);
/*     */     }
/*  62 */     if (input.getNumRows() != output.getNumRows())
/*  63 */       throw new IllegalArgumentException("Number of rows do not match"); 
/*  64 */     if (input.getNumCols() != output.getNumCols()) {
/*  65 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/*  67 */     output.data[0] = input.a11;
/*  68 */     output.data[1] = input.a12;
/*  69 */     output.data[2] = input.a21;
/*  70 */     output.data[3] = input.a22;
/*     */     
/*  72 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix3x3_64F input, DenseMatrix64F output) {
/*  83 */     if (output == null) {
/*  84 */       output = new DenseMatrix64F(3, 3);
/*     */     }
/*  86 */     if (input.getNumRows() != output.getNumRows())
/*  87 */       throw new IllegalArgumentException("Number of rows do not match"); 
/*  88 */     if (input.getNumCols() != output.getNumCols()) {
/*  89 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/*  91 */     output.data[0] = input.a11;
/*  92 */     output.data[1] = input.a12;
/*  93 */     output.data[2] = input.a13;
/*  94 */     output.data[3] = input.a21;
/*  95 */     output.data[4] = input.a22;
/*  96 */     output.data[5] = input.a23;
/*  97 */     output.data[6] = input.a31;
/*  98 */     output.data[7] = input.a32;
/*  99 */     output.data[8] = input.a33;
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
/*     */   public static DenseMatrix64F convert(FixedMatrix4x4_64F input, DenseMatrix64F output) {
/* 112 */     if (output == null) {
/* 113 */       output = new DenseMatrix64F(4, 4);
/*     */     }
/* 115 */     if (input.getNumRows() != output.getNumRows())
/* 116 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 117 */     if (input.getNumCols() != output.getNumCols()) {
/* 118 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 120 */     output.data[0] = input.a11;
/* 121 */     output.data[1] = input.a12;
/* 122 */     output.data[2] = input.a13;
/* 123 */     output.data[3] = input.a14;
/* 124 */     output.data[4] = input.a21;
/* 125 */     output.data[5] = input.a22;
/* 126 */     output.data[6] = input.a23;
/* 127 */     output.data[7] = input.a24;
/* 128 */     output.data[8] = input.a31;
/* 129 */     output.data[9] = input.a32;
/* 130 */     output.data[10] = input.a33;
/* 131 */     output.data[11] = input.a34;
/* 132 */     output.data[12] = input.a41;
/* 133 */     output.data[13] = input.a42;
/* 134 */     output.data[14] = input.a43;
/* 135 */     output.data[15] = input.a44;
/*     */     
/* 137 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix5x5_64F input, DenseMatrix64F output) {
/* 148 */     if (output == null) {
/* 149 */       output = new DenseMatrix64F(5, 5);
/*     */     }
/* 151 */     if (input.getNumRows() != output.getNumRows())
/* 152 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 153 */     if (input.getNumCols() != output.getNumCols()) {
/* 154 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 156 */     output.data[0] = input.a11;
/* 157 */     output.data[1] = input.a12;
/* 158 */     output.data[2] = input.a13;
/* 159 */     output.data[3] = input.a14;
/* 160 */     output.data[4] = input.a15;
/* 161 */     output.data[5] = input.a21;
/* 162 */     output.data[6] = input.a22;
/* 163 */     output.data[7] = input.a23;
/* 164 */     output.data[8] = input.a24;
/* 165 */     output.data[9] = input.a25;
/* 166 */     output.data[10] = input.a31;
/* 167 */     output.data[11] = input.a32;
/* 168 */     output.data[12] = input.a33;
/* 169 */     output.data[13] = input.a34;
/* 170 */     output.data[14] = input.a35;
/* 171 */     output.data[15] = input.a41;
/* 172 */     output.data[16] = input.a42;
/* 173 */     output.data[17] = input.a43;
/* 174 */     output.data[18] = input.a44;
/* 175 */     output.data[19] = input.a45;
/* 176 */     output.data[20] = input.a51;
/* 177 */     output.data[21] = input.a52;
/* 178 */     output.data[22] = input.a53;
/* 179 */     output.data[23] = input.a54;
/* 180 */     output.data[24] = input.a55;
/*     */     
/* 182 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix6x6_64F input, DenseMatrix64F output) {
/* 193 */     if (output == null) {
/* 194 */       output = new DenseMatrix64F(6, 6);
/*     */     }
/* 196 */     if (input.getNumRows() != output.getNumRows())
/* 197 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 198 */     if (input.getNumCols() != output.getNumCols()) {
/* 199 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 201 */     output.data[0] = input.a11;
/* 202 */     output.data[1] = input.a12;
/* 203 */     output.data[2] = input.a13;
/* 204 */     output.data[3] = input.a14;
/* 205 */     output.data[4] = input.a15;
/* 206 */     output.data[5] = input.a16;
/* 207 */     output.data[6] = input.a21;
/* 208 */     output.data[7] = input.a22;
/* 209 */     output.data[8] = input.a23;
/* 210 */     output.data[9] = input.a24;
/* 211 */     output.data[10] = input.a25;
/* 212 */     output.data[11] = input.a26;
/* 213 */     output.data[12] = input.a31;
/* 214 */     output.data[13] = input.a32;
/* 215 */     output.data[14] = input.a33;
/* 216 */     output.data[15] = input.a34;
/* 217 */     output.data[16] = input.a35;
/* 218 */     output.data[17] = input.a36;
/* 219 */     output.data[18] = input.a41;
/* 220 */     output.data[19] = input.a42;
/* 221 */     output.data[20] = input.a43;
/* 222 */     output.data[21] = input.a44;
/* 223 */     output.data[22] = input.a45;
/* 224 */     output.data[23] = input.a46;
/* 225 */     output.data[24] = input.a51;
/* 226 */     output.data[25] = input.a52;
/* 227 */     output.data[26] = input.a53;
/* 228 */     output.data[27] = input.a54;
/* 229 */     output.data[28] = input.a55;
/* 230 */     output.data[29] = input.a56;
/* 231 */     output.data[30] = input.a61;
/* 232 */     output.data[31] = input.a62;
/* 233 */     output.data[32] = input.a63;
/* 234 */     output.data[33] = input.a64;
/* 235 */     output.data[34] = input.a65;
/* 236 */     output.data[35] = input.a66;
/*     */     
/* 238 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix2x2_64F convert(DenseMatrix64F input, FixedMatrix2x2_64F output) {
/* 249 */     if (output == null) {
/* 250 */       output = new FixedMatrix2x2_64F();
/*     */     }
/* 252 */     if (input.getNumRows() != output.getNumRows())
/* 253 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 254 */     if (input.getNumCols() != output.getNumCols()) {
/* 255 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 257 */     output.a11 = input.data[0];
/* 258 */     output.a12 = input.data[1];
/* 259 */     output.a21 = input.data[2];
/* 260 */     output.a22 = input.data[3];
/*     */     
/* 262 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix3x3_64F convert(DenseMatrix64F input, FixedMatrix3x3_64F output) {
/* 273 */     if (output == null) {
/* 274 */       output = new FixedMatrix3x3_64F();
/*     */     }
/* 276 */     if (input.getNumRows() != output.getNumRows())
/* 277 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 278 */     if (input.getNumCols() != output.getNumCols()) {
/* 279 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 281 */     output.a11 = input.data[0];
/* 282 */     output.a12 = input.data[1];
/* 283 */     output.a13 = input.data[2];
/* 284 */     output.a21 = input.data[3];
/* 285 */     output.a22 = input.data[4];
/* 286 */     output.a23 = input.data[5];
/* 287 */     output.a31 = input.data[6];
/* 288 */     output.a32 = input.data[7];
/* 289 */     output.a33 = input.data[8];
/*     */     
/* 291 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix4x4_64F convert(DenseMatrix64F input, FixedMatrix4x4_64F output) {
/* 302 */     if (output == null) {
/* 303 */       output = new FixedMatrix4x4_64F();
/*     */     }
/* 305 */     if (input.getNumRows() != output.getNumRows())
/* 306 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 307 */     if (input.getNumCols() != output.getNumCols()) {
/* 308 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 310 */     output.a11 = input.data[0];
/* 311 */     output.a12 = input.data[1];
/* 312 */     output.a13 = input.data[2];
/* 313 */     output.a14 = input.data[3];
/* 314 */     output.a21 = input.data[4];
/* 315 */     output.a22 = input.data[5];
/* 316 */     output.a23 = input.data[6];
/* 317 */     output.a24 = input.data[7];
/* 318 */     output.a31 = input.data[8];
/* 319 */     output.a32 = input.data[9];
/* 320 */     output.a33 = input.data[10];
/* 321 */     output.a34 = input.data[11];
/* 322 */     output.a41 = input.data[12];
/* 323 */     output.a42 = input.data[13];
/* 324 */     output.a43 = input.data[14];
/* 325 */     output.a44 = input.data[15];
/*     */     
/* 327 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix5x5_64F convert(DenseMatrix64F input, FixedMatrix5x5_64F output) {
/* 338 */     if (output == null) {
/* 339 */       output = new FixedMatrix5x5_64F();
/*     */     }
/* 341 */     if (input.getNumRows() != output.getNumRows())
/* 342 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 343 */     if (input.getNumCols() != output.getNumCols()) {
/* 344 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 346 */     output.a11 = input.data[0];
/* 347 */     output.a12 = input.data[1];
/* 348 */     output.a13 = input.data[2];
/* 349 */     output.a14 = input.data[3];
/* 350 */     output.a15 = input.data[4];
/* 351 */     output.a21 = input.data[5];
/* 352 */     output.a22 = input.data[6];
/* 353 */     output.a23 = input.data[7];
/* 354 */     output.a24 = input.data[8];
/* 355 */     output.a25 = input.data[9];
/* 356 */     output.a31 = input.data[10];
/* 357 */     output.a32 = input.data[11];
/* 358 */     output.a33 = input.data[12];
/* 359 */     output.a34 = input.data[13];
/* 360 */     output.a35 = input.data[14];
/* 361 */     output.a41 = input.data[15];
/* 362 */     output.a42 = input.data[16];
/* 363 */     output.a43 = input.data[17];
/* 364 */     output.a44 = input.data[18];
/* 365 */     output.a45 = input.data[19];
/* 366 */     output.a51 = input.data[20];
/* 367 */     output.a52 = input.data[21];
/* 368 */     output.a53 = input.data[22];
/* 369 */     output.a54 = input.data[23];
/* 370 */     output.a55 = input.data[24];
/*     */     
/* 372 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix6x6_64F convert(DenseMatrix64F input, FixedMatrix6x6_64F output) {
/* 383 */     if (output == null) {
/* 384 */       output = new FixedMatrix6x6_64F();
/*     */     }
/* 386 */     if (input.getNumRows() != output.getNumRows())
/* 387 */       throw new IllegalArgumentException("Number of rows do not match"); 
/* 388 */     if (input.getNumCols() != output.getNumCols()) {
/* 389 */       throw new IllegalArgumentException("Number of columns do not match");
/*     */     }
/* 391 */     output.a11 = input.data[0];
/* 392 */     output.a12 = input.data[1];
/* 393 */     output.a13 = input.data[2];
/* 394 */     output.a14 = input.data[3];
/* 395 */     output.a15 = input.data[4];
/* 396 */     output.a16 = input.data[5];
/* 397 */     output.a21 = input.data[6];
/* 398 */     output.a22 = input.data[7];
/* 399 */     output.a23 = input.data[8];
/* 400 */     output.a24 = input.data[9];
/* 401 */     output.a25 = input.data[10];
/* 402 */     output.a26 = input.data[11];
/* 403 */     output.a31 = input.data[12];
/* 404 */     output.a32 = input.data[13];
/* 405 */     output.a33 = input.data[14];
/* 406 */     output.a34 = input.data[15];
/* 407 */     output.a35 = input.data[16];
/* 408 */     output.a36 = input.data[17];
/* 409 */     output.a41 = input.data[18];
/* 410 */     output.a42 = input.data[19];
/* 411 */     output.a43 = input.data[20];
/* 412 */     output.a44 = input.data[21];
/* 413 */     output.a45 = input.data[22];
/* 414 */     output.a46 = input.data[23];
/* 415 */     output.a51 = input.data[24];
/* 416 */     output.a52 = input.data[25];
/* 417 */     output.a53 = input.data[26];
/* 418 */     output.a54 = input.data[27];
/* 419 */     output.a55 = input.data[28];
/* 420 */     output.a56 = input.data[29];
/* 421 */     output.a61 = input.data[30];
/* 422 */     output.a62 = input.data[31];
/* 423 */     output.a63 = input.data[32];
/* 424 */     output.a64 = input.data[33];
/* 425 */     output.a65 = input.data[34];
/* 426 */     output.a66 = input.data[35];
/*     */     
/* 428 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix2_64F input, DenseMatrix64F output) {
/* 439 */     if (output == null) {
/* 440 */       output = new DenseMatrix64F(2, 1);
/*     */     }
/* 442 */     if (output.getNumRows() != 1 && output.getNumCols() != 1)
/* 443 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 444 */     int length = Math.max(output.getNumRows(), output.getNumCols());
/* 445 */     if (length != 2) {
/* 446 */       throw new IllegalArgumentException("Length of input vector is not 2.  It is " + length);
/*     */     }
/* 448 */     output.data[0] = input.a1;
/* 449 */     output.data[1] = input.a2;
/*     */     
/* 451 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix3_64F input, DenseMatrix64F output) {
/* 462 */     if (output == null) {
/* 463 */       output = new DenseMatrix64F(3, 1);
/*     */     }
/* 465 */     if (output.getNumRows() != 1 && output.getNumCols() != 1)
/* 466 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 467 */     int length = Math.max(output.getNumRows(), output.getNumCols());
/* 468 */     if (length != 3) {
/* 469 */       throw new IllegalArgumentException("Length of input vector is not 3.  It is " + length);
/*     */     }
/* 471 */     output.data[0] = input.a1;
/* 472 */     output.data[1] = input.a2;
/* 473 */     output.data[2] = input.a3;
/*     */     
/* 475 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix4_64F input, DenseMatrix64F output) {
/* 486 */     if (output == null) {
/* 487 */       output = new DenseMatrix64F(4, 1);
/*     */     }
/* 489 */     if (output.getNumRows() != 1 && output.getNumCols() != 1)
/* 490 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 491 */     int length = Math.max(output.getNumRows(), output.getNumCols());
/* 492 */     if (length != 4) {
/* 493 */       throw new IllegalArgumentException("Length of input vector is not 4.  It is " + length);
/*     */     }
/* 495 */     output.data[0] = input.a1;
/* 496 */     output.data[1] = input.a2;
/* 497 */     output.data[2] = input.a3;
/* 498 */     output.data[3] = input.a4;
/*     */     
/* 500 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix5_64F input, DenseMatrix64F output) {
/* 511 */     if (output == null) {
/* 512 */       output = new DenseMatrix64F(5, 1);
/*     */     }
/* 514 */     if (output.getNumRows() != 1 && output.getNumCols() != 1)
/* 515 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 516 */     int length = Math.max(output.getNumRows(), output.getNumCols());
/* 517 */     if (length != 5) {
/* 518 */       throw new IllegalArgumentException("Length of input vector is not 5.  It is " + length);
/*     */     }
/* 520 */     output.data[0] = input.a1;
/* 521 */     output.data[1] = input.a2;
/* 522 */     output.data[2] = input.a3;
/* 523 */     output.data[3] = input.a4;
/* 524 */     output.data[4] = input.a5;
/*     */     
/* 526 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(FixedMatrix6_64F input, DenseMatrix64F output) {
/* 537 */     if (output == null) {
/* 538 */       output = new DenseMatrix64F(6, 1);
/*     */     }
/* 540 */     if (output.getNumRows() != 1 && output.getNumCols() != 1)
/* 541 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 542 */     int length = Math.max(output.getNumRows(), output.getNumCols());
/* 543 */     if (length != 6) {
/* 544 */       throw new IllegalArgumentException("Length of input vector is not 6.  It is " + length);
/*     */     }
/* 546 */     output.data[0] = input.a1;
/* 547 */     output.data[1] = input.a2;
/* 548 */     output.data[2] = input.a3;
/* 549 */     output.data[3] = input.a4;
/* 550 */     output.data[4] = input.a5;
/* 551 */     output.data[5] = input.a6;
/*     */     
/* 553 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix2_64F convert(DenseMatrix64F input, FixedMatrix2_64F output) {
/* 564 */     if (output == null) {
/* 565 */       output = new FixedMatrix2_64F();
/*     */     }
/* 567 */     if (input.getNumRows() != 1 && input.getNumCols() != 1)
/* 568 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 569 */     int length = Math.max(input.getNumRows(), input.getNumCols());
/* 570 */     if (length != 2) {
/* 571 */       throw new IllegalArgumentException("Length of input vector is not 2.  It is " + length);
/*     */     }
/* 573 */     output.a1 = input.data[0];
/* 574 */     output.a2 = input.data[1];
/*     */     
/* 576 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix3_64F convert(DenseMatrix64F input, FixedMatrix3_64F output) {
/* 587 */     if (output == null) {
/* 588 */       output = new FixedMatrix3_64F();
/*     */     }
/* 590 */     if (input.getNumRows() != 1 && input.getNumCols() != 1)
/* 591 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 592 */     int length = Math.max(input.getNumRows(), input.getNumCols());
/* 593 */     if (length != 3) {
/* 594 */       throw new IllegalArgumentException("Length of input vector is not 3.  It is " + length);
/*     */     }
/* 596 */     output.a1 = input.data[0];
/* 597 */     output.a2 = input.data[1];
/* 598 */     output.a3 = input.data[2];
/*     */     
/* 600 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix4_64F convert(DenseMatrix64F input, FixedMatrix4_64F output) {
/* 611 */     if (output == null) {
/* 612 */       output = new FixedMatrix4_64F();
/*     */     }
/* 614 */     if (input.getNumRows() != 1 && input.getNumCols() != 1)
/* 615 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 616 */     int length = Math.max(input.getNumRows(), input.getNumCols());
/* 617 */     if (length != 4) {
/* 618 */       throw new IllegalArgumentException("Length of input vector is not 4.  It is " + length);
/*     */     }
/* 620 */     output.a1 = input.data[0];
/* 621 */     output.a2 = input.data[1];
/* 622 */     output.a3 = input.data[2];
/* 623 */     output.a4 = input.data[3];
/*     */     
/* 625 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix5_64F convert(DenseMatrix64F input, FixedMatrix5_64F output) {
/* 636 */     if (output == null) {
/* 637 */       output = new FixedMatrix5_64F();
/*     */     }
/* 639 */     if (input.getNumRows() != 1 && input.getNumCols() != 1)
/* 640 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 641 */     int length = Math.max(input.getNumRows(), input.getNumCols());
/* 642 */     if (length != 5) {
/* 643 */       throw new IllegalArgumentException("Length of input vector is not 5.  It is " + length);
/*     */     }
/* 645 */     output.a1 = input.data[0];
/* 646 */     output.a2 = input.data[1];
/* 647 */     output.a3 = input.data[2];
/* 648 */     output.a4 = input.data[3];
/* 649 */     output.a5 = input.data[4];
/*     */     
/* 651 */     return output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FixedMatrix6_64F convert(DenseMatrix64F input, FixedMatrix6_64F output) {
/* 662 */     if (output == null) {
/* 663 */       output = new FixedMatrix6_64F();
/*     */     }
/* 665 */     if (input.getNumRows() != 1 && input.getNumCols() != 1)
/* 666 */       throw new IllegalArgumentException("One row or column must have a length of 1 for it to be a vector"); 
/* 667 */     int length = Math.max(input.getNumRows(), input.getNumCols());
/* 668 */     if (length != 6) {
/* 669 */       throw new IllegalArgumentException("Length of input vector is not 6.  It is " + length);
/*     */     }
/* 671 */     output.a1 = input.data[0];
/* 672 */     output.a2 = input.data[1];
/* 673 */     output.a3 = input.data[2];
/* 674 */     output.a4 = input.data[3];
/* 675 */     output.a5 = input.data[4];
/* 676 */     output.a6 = input.data[5];
/*     */     
/* 678 */     return output;
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
/*     */   public static void convert(DenseMatrix64F input, BlockMatrix64F output) {
/* 690 */     BlockMatrixOps.convert(input, output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F convert(BlockMatrix64F input, DenseMatrix64F output) {
/* 701 */     return BlockMatrixOps.convert(input, output);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\ConvertMatrixType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */