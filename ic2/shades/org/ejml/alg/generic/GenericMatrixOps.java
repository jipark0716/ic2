/*     */ package ic2.shades.org.ejml.alg.generic;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
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
/*     */ public class GenericMatrixOps
/*     */ {
/*     */   public static boolean isEquivalent(ReshapeMatrix64F a, ReshapeMatrix64F b, double tol) {
/*  40 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/*  41 */       return false;
/*     */     }
/*  43 */     for (int i = 0; i < a.numRows; i++) {
/*  44 */       for (int j = 0; j < a.numCols; j++) {
/*  45 */         double diff = Math.abs(a.get(i, j) - b.get(i, j));
/*     */         
/*  47 */         if (diff > tol) {
/*  48 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*  52 */     return true;
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
/*     */   public static boolean isIdentity(ReshapeMatrix64F a, double tol) {
/*  65 */     for (int i = 0; i < a.numRows; i++) {
/*  66 */       for (int j = 0; j < a.numCols; j++) {
/*  67 */         if (i == j) {
/*  68 */           if (Math.abs(a.get(i, j) - 1.0D) > tol) {
/*  69 */             return false;
/*     */           }
/*  71 */         } else if (Math.abs(a.get(i, j)) > tol) {
/*  72 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isEquivalentTriangle(boolean upper, ReshapeMatrix64F a, ReshapeMatrix64F b, double tol) {
/*  81 */     if (a.numRows != b.numRows || a.numCols != b.numCols) {
/*  82 */       return false;
/*     */     }
/*  84 */     if (upper) {
/*  85 */       for (int i = 0; i < a.numRows; i++) {
/*  86 */         for (int j = i; j < a.numCols; j++) {
/*  87 */           double diff = Math.abs(a.get(i, j) - b.get(i, j));
/*     */           
/*  89 */           if (diff > tol)
/*  90 */             return false; 
/*     */         } 
/*     */       } 
/*     */     } else {
/*  94 */       for (int j = 0; j < a.numCols; j++) {
/*  95 */         for (int i = j; i < a.numRows; i++) {
/*  96 */           double diff = Math.abs(a.get(i, j) - b.get(i, j));
/*     */           
/*  98 */           if (diff > tol) {
/*  99 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void copy(ReshapeMatrix64F from, ReshapeMatrix64F to) {
/* 109 */     int numCols = from.getNumCols();
/* 110 */     int numRows = from.getNumRows();
/*     */     
/* 112 */     for (int i = 0; i < numRows; i++) {
/* 113 */       for (int j = 0; j < numCols; j++) {
/* 114 */         to.set(i, j, from.get(i, j));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setRandom(ReshapeMatrix64F a, double min, double max, Random rand) {
/* 121 */     for (int i = 0; i < a.numRows; i++) {
/* 122 */       for (int j = 0; j < a.numCols; j++) {
/* 123 */         double val = rand.nextDouble() * (max - min) + min;
/* 124 */         a.set(i, j, val);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\generic\GenericMatrixOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */