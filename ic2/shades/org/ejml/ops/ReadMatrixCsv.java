/*    */ package ic2.shades.org.ejml.ops;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReadMatrixCsv
/*    */   extends ReadCsv
/*    */ {
/*    */   public ReadMatrixCsv(InputStream in) {
/* 41 */     super(in);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DenseMatrix64F read() throws IOException {
/* 50 */     List<String> words = extractWords();
/* 51 */     if (words.size() != 2) {
/* 52 */       throw new IOException("Unexpected number of words on first line.");
/*    */     }
/* 54 */     int numRows = Integer.parseInt(words.get(0));
/* 55 */     int numCols = Integer.parseInt(words.get(1));
/*    */     
/* 57 */     if (numRows < 0 || numCols < 0) {
/* 58 */       throw new IOException("Invalid number of rows and/or columns: " + numRows + " " + numCols);
/*    */     }
/* 60 */     return read(numRows, numCols);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DenseMatrix64F read(int numRows, int numCols) throws IOException {
/* 73 */     DenseMatrix64F A = new DenseMatrix64F(numRows, numCols);
/*    */     
/* 75 */     for (int i = 0; i < numRows; i++) {
/* 76 */       List<String> words = extractWords();
/* 77 */       if (words == null) {
/* 78 */         throw new IOException("Too few rows found. expected " + numRows + " actual " + i);
/*    */       }
/* 80 */       if (words.size() != numCols)
/* 81 */         throw new IOException("Unexpected number of words in column. Found " + words.size() + " expected " + numCols); 
/* 82 */       for (int j = 0; j < numCols; j++) {
/* 83 */         A.set(i, j, Double.parseDouble(words.get(j)));
/*    */       }
/*    */     } 
/*    */     
/* 87 */     return A;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\ReadMatrixCsv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */