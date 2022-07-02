/*     */ package ic2.shades.org.ejml.alg.dense.misc;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.RowD1Matrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeterminantFromMinor
/*     */ {
/*     */   private int width;
/*     */   private int minWidth;
/*     */   private int[] levelIndexes;
/*     */   private double[] levelResults;
/*     */   private int[] levelRemoved;
/*     */   private int[] open;
/*     */   private int numOpen;
/*     */   private DenseMatrix64F tempMat;
/*     */   private boolean dirty = false;
/*     */   
/*     */   public DeterminantFromMinor(int width) {
/*  75 */     this(width, 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeterminantFromMinor(int width, int minWidth) {
/*  85 */     if (minWidth > 5 || minWidth < 2) {
/*  86 */       throw new IllegalArgumentException("No direct function for that width");
/*     */     }
/*     */     
/*  89 */     if (width < minWidth) {
/*  90 */       minWidth = width;
/*     */     }
/*  92 */     this.minWidth = minWidth;
/*  93 */     this.width = width;
/*     */     
/*  95 */     int numLevels = width - minWidth - 2;
/*     */     
/*  97 */     this.levelResults = new double[numLevels];
/*  98 */     this.levelRemoved = new int[numLevels];
/*  99 */     this.levelIndexes = new int[numLevels];
/*     */     
/* 101 */     this.open = new int[width];
/*     */     
/* 103 */     this.tempMat = new DenseMatrix64F(minWidth - 1, minWidth - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double compute(RowD1Matrix64F mat) {
/* 114 */     if (this.width != mat.numCols || this.width != mat.numRows) {
/* 115 */       throw new RuntimeException("Unexpected matrix dimension");
/*     */     }
/*     */ 
/*     */     
/* 119 */     initStructures();
/*     */ 
/*     */ 
/*     */     
/* 123 */     int level = 0;
/*     */     while (true) {
/* 125 */       int levelWidth = this.width - level;
/* 126 */       int levelIndex = this.levelIndexes[level];
/*     */       
/* 128 */       if (levelIndex == levelWidth) {
/* 129 */         if (level == 0) {
/* 130 */           return this.levelResults[0];
/*     */         }
/* 132 */         this.levelIndexes[level - 1] = this.levelIndexes[level - 1] + 1; int prevLevelIndex = this.levelIndexes[level - 1];
/*     */         
/* 134 */         double val = mat.get((level - 1) * this.width + this.levelRemoved[level - 1]);
/* 135 */         if (prevLevelIndex % 2 == 0) {
/* 136 */           this.levelResults[level - 1] = this.levelResults[level - 1] + val * this.levelResults[level];
/*     */         } else {
/* 138 */           this.levelResults[level - 1] = this.levelResults[level - 1] - val * this.levelResults[level];
/*     */         } 
/*     */         
/* 141 */         putIntoOpen(level - 1);
/*     */         
/* 143 */         this.levelResults[level] = 0.0D;
/* 144 */         this.levelIndexes[level] = 0;
/* 145 */         level--; continue;
/*     */       } 
/* 147 */       int excluded = openRemove(levelIndex);
/*     */       
/* 149 */       this.levelRemoved[level] = excluded;
/*     */       
/* 151 */       if (levelWidth == this.minWidth) {
/* 152 */         createMinor(mat);
/* 153 */         double subresult = mat.get(level * this.width + this.levelRemoved[level]);
/*     */         
/* 155 */         subresult *= UnrolledDeterminantFromMinor.det((RowD1Matrix64F)this.tempMat);
/*     */         
/* 157 */         if (levelIndex % 2 == 0) {
/* 158 */           this.levelResults[level] = this.levelResults[level] + subresult;
/*     */         } else {
/* 160 */           this.levelResults[level] = this.levelResults[level] - subresult;
/*     */         } 
/*     */ 
/*     */         
/* 164 */         putIntoOpen(level);
/* 165 */         this.levelIndexes[level] = this.levelIndexes[level] + 1; continue;
/*     */       } 
/* 167 */       level++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initStructures() {
/*     */     int i;
/* 174 */     for (i = 0; i < this.width; i++) {
/* 175 */       this.open[i] = i;
/*     */     }
/* 177 */     this.numOpen = this.width;
/*     */     
/* 179 */     if (this.dirty) {
/* 180 */       for (i = 0; i < this.levelIndexes.length; i++) {
/* 181 */         this.levelIndexes[i] = 0;
/* 182 */         this.levelResults[i] = 0.0D;
/* 183 */         this.levelRemoved[i] = 0;
/*     */       } 
/*     */     }
/* 186 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   private int openRemove(int where) {
/* 190 */     int val = this.open[where];
/*     */     
/* 192 */     System.arraycopy(this.open, where + 1, this.open, where, this.numOpen - where - 1);
/* 193 */     this.numOpen--;
/*     */     
/* 195 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   private void openAdd(int where, int val) {
/* 200 */     for (int i = this.numOpen; i > where; i--) {
/* 201 */       this.open[i] = this.open[i - 1];
/*     */     }
/* 203 */     this.numOpen++;
/* 204 */     this.open[where] = val;
/*     */   }
/*     */   
/*     */   private void openAdd(int val) {
/* 208 */     this.open[this.numOpen++] = val;
/*     */   }
/*     */   
/*     */   private void putIntoOpen(int level) {
/* 212 */     boolean added = false;
/* 213 */     for (int i = 0; i < this.numOpen; i++) {
/* 214 */       if (this.open[i] > this.levelRemoved[level]) {
/* 215 */         added = true;
/* 216 */         openAdd(i, this.levelRemoved[level]);
/*     */         break;
/*     */       } 
/*     */     } 
/* 220 */     if (!added) {
/* 221 */       openAdd(this.levelRemoved[level]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void createMinor(RowD1Matrix64F mat) {
/* 227 */     int w = this.minWidth - 1;
/* 228 */     int firstRow = (this.width - w) * this.width;
/*     */     
/* 230 */     for (int i = 0; i < this.numOpen; i++) {
/* 231 */       int col = this.open[i];
/* 232 */       int srcIndex = firstRow + col;
/* 233 */       int dstIndex = i;
/*     */       
/* 235 */       for (int j = 0; j < w; j++) {
/* 236 */         this.tempMat.set(dstIndex, mat.get(srcIndex));
/* 237 */         dstIndex += w;
/* 238 */         srcIndex += this.width;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\misc\DeterminantFromMinor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */