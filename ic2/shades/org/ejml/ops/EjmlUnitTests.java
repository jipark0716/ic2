/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EjmlUnitTests
/*     */ {
/*     */   public static void assertCountable(ReshapeMatrix64F A) {
/*  38 */     for (int i = 0; i < A.numRows; i++) {
/*  39 */       for (int j = 0; j < A.numCols; j++) {
/*  40 */         assertTrue(!Double.isNaN(A.get(i, j)), "NaN found at " + i + " " + j);
/*  41 */         assertTrue(!Double.isInfinite(A.get(i, j)), "Infinite found at " + i + " " + j);
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
/*     */   public static void assertShape(ReshapeMatrix64F A, ReshapeMatrix64F B) {
/*  55 */     assertTrue((A.numRows == B.numRows), "Number of rows do not match");
/*  56 */     assertTrue((A.numCols == B.numCols), "Number of columns do not match");
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
/*     */   public static void assertShape(ReshapeMatrix64F A, int numRows, int numCols) {
/*  69 */     assertTrue((A.numRows == numRows), "Unexpected number of rows.");
/*  70 */     assertTrue((A.numCols == numCols), "Unexpected number of columns.");
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
/*     */   public static void assertEqualsUncountable(ReshapeMatrix64F A, ReshapeMatrix64F B, double tol) {
/*  92 */     assertShape(A, B);
/*     */     
/*  94 */     for (int i = 0; i < A.numRows; i++) {
/*  95 */       for (int j = 0; j < A.numCols; j++) {
/*  96 */         double valA = A.get(i, j);
/*  97 */         double valB = B.get(i, j);
/*     */         
/*  99 */         if (Double.isNaN(valA)) {
/* 100 */           assertTrue(Double.isNaN(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
/* 101 */         } else if (Double.isInfinite(valA)) {
/* 102 */           assertTrue(Double.isInfinite(valB), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
/*     */         } else {
/* 104 */           double diff = Math.abs(valA - valB);
/* 105 */           assertTrue((diff <= tol), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
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
/*     */   public static void assertEquals(ReshapeMatrix64F A, ReshapeMatrix64F B, double tol) {
/* 130 */     assertShape(A, B);
/*     */     
/* 132 */     for (int i = 0; i < A.numRows; i++) {
/* 133 */       for (int j = 0; j < A.numCols; j++) {
/* 134 */         double valA = A.get(i, j);
/* 135 */         double valB = B.get(i, j);
/*     */         
/* 137 */         assertTrue((!Double.isNaN(valA) && !Double.isNaN(valB)), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
/* 138 */         assertTrue((!Double.isInfinite(valA) && !Double.isInfinite(valB)), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
/* 139 */         assertTrue((Math.abs(valA - valB) <= tol), "At (" + i + "," + j + ") A = " + valA + " B = " + valB);
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
/*     */   public static void assertEqualsTrans(ReshapeMatrix64F A, ReshapeMatrix64F B, double tol) {
/* 162 */     assertShape(A, B.numCols, B.numRows);
/*     */     
/* 164 */     for (int i = 0; i < A.numRows; i++) {
/* 165 */       for (int j = 0; j < A.numCols; j++) {
/* 166 */         double valA = A.get(i, j);
/* 167 */         double valB = B.get(j, i);
/*     */         
/* 169 */         assertTrue((!Double.isNaN(valA) && !Double.isNaN(valB)), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
/* 170 */         assertTrue((!Double.isInfinite(valA) && !Double.isInfinite(valB)), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
/* 171 */         assertTrue((Math.abs(valA - valB) <= tol), "A(" + i + "," + j + ") = " + valA + ") B(" + j + "," + i + ") = " + valB);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void assertTrue(boolean result, String message) {
/* 179 */     assert result : message;
/*     */     
/* 181 */     if (!result) throw new TestException(message); 
/*     */   }
/*     */   
/*     */   public static class TestException extends RuntimeException {
/*     */     public TestException(String message) {
/* 186 */       super(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\EjmlUnitTests.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */