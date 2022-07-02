/*     */ package ic2.shades.org.ejml;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UtilEjml
/*     */ {
/*  37 */   public static String VERSION = "0.26";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static double TOLERANCE = 1.0E-8D;
/*     */   
/*  44 */   public static double EPS = Math.pow(2.0D, -52.0D);
/*     */   
/*     */   public static boolean isUncountable(double val) {
/*  47 */     return (Double.isNaN(val) || Double.isInfinite(val));
/*     */   }
/*     */   
/*     */   public static void memset(double[] data, double val) {
/*  51 */     for (int i = 0; i < data.length; i++) {
/*  52 */       data[i] = val;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void memset(double[] data, double val, int length) {
/*  57 */     for (int i = 0; i < length; i++) {
/*  58 */       data[i] = val;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void memset(int[] data, int val, int length) {
/*  63 */     for (int i = 0; i < length; i++) {
/*  64 */       data[i] = val;
/*     */     }
/*     */   }
/*     */   
/*     */   public static <T> void setnull(T[] array) {
/*  69 */     for (int i = 0; i < array.length; i++) {
/*  70 */       array[i] = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static double max(double[] array, int start, int length) {
/*  75 */     double max = array[start];
/*  76 */     int end = start + length;
/*     */     
/*  78 */     for (int i = start + 1; i < end; i++) {
/*  79 */       double v = array[i];
/*  80 */       if (v > max) {
/*  81 */         max = v;
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DenseMatrix64F parseMatrix(String s, int numColumns) {
/*  93 */     String[] vals = s.split("(\\s)+");
/*     */ 
/*     */     
/*  96 */     int start = vals[0].isEmpty() ? 1 : 0;
/*     */ 
/*     */     
/*  99 */     int numRows = (vals.length - start) / numColumns;
/*     */     
/* 101 */     DenseMatrix64F ret = new DenseMatrix64F(numRows, numColumns);
/*     */     
/* 103 */     int index = start;
/* 104 */     for (int i = 0; i < numRows; i++) {
/* 105 */       for (int j = 0; j < numColumns; j++) {
/* 106 */         ret.set(i, j, Double.parseDouble(vals[index++]));
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return ret;
/*     */   }
/*     */   
/*     */   public static Integer[] sortByIndex(final double[] data, int size) {
/* 114 */     Integer[] idx = new Integer[size];
/* 115 */     for (int i = 0; i < size; i++) {
/* 116 */       idx[i] = Integer.valueOf(i);
/*     */     }
/*     */     
/* 119 */     Arrays.sort(idx, new Comparator<Integer>() {
/*     */           public int compare(Integer o1, Integer o2) {
/* 121 */             return Double.compare(data[o1.intValue()], data[o2.intValue()]);
/*     */           }
/*     */         });
/*     */     
/* 125 */     return idx;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\UtilEjml.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */