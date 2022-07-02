/*     */ package ic2.shades.org.ejml.ops;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.DenseMatrix64F;
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatrixIO
/*     */ {
/*     */   public static void saveBin(ReshapeMatrix64F A, String fileName) throws IOException {
/*  45 */     FileOutputStream fileStream = new FileOutputStream(fileName);
/*  46 */     ObjectOutputStream stream = new ObjectOutputStream(fileStream);
/*     */     
/*     */     try {
/*  49 */       stream.writeObject(A);
/*  50 */       stream.flush();
/*     */     } finally {
/*     */       
/*     */       try {
/*  54 */         stream.close();
/*     */       } finally {
/*  56 */         fileStream.close();
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
/*     */   public static <T extends ReshapeMatrix64F> T loadBin(String fileName) throws IOException {
/*     */     ReshapeMatrix64F reshapeMatrix64F;
/*  73 */     FileInputStream fileStream = new FileInputStream(fileName);
/*  74 */     ObjectInputStream stream = new ObjectInputStream(fileStream);
/*     */ 
/*     */     
/*     */     try {
/*  78 */       reshapeMatrix64F = (ReshapeMatrix64F)stream.readObject();
/*  79 */       if (stream.available() != 0) {
/*  80 */         throw new RuntimeException("File not completely read?");
/*     */       }
/*  82 */     } catch (ClassNotFoundException e) {
/*  83 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/*  86 */     stream.close();
/*  87 */     return (T)reshapeMatrix64F;
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
/*     */   public static void saveCSV(ReshapeMatrix64F A, String fileName) throws IOException {
/* 101 */     PrintStream fileStream = new PrintStream(fileName);
/*     */     
/* 103 */     fileStream.print(A.getNumRows() + " ");
/* 104 */     fileStream.println(A.getNumCols());
/* 105 */     for (int i = 0; i < A.numRows; i++) {
/* 106 */       for (int j = 0; j < A.numCols; j++) {
/* 107 */         fileStream.print(A.get(i, j) + " ");
/*     */       }
/* 109 */       fileStream.println();
/*     */     } 
/* 111 */     fileStream.close();
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
/*     */   public static DenseMatrix64F loadCSV(String fileName) throws IOException {
/* 126 */     FileInputStream fileStream = new FileInputStream(fileName);
/* 127 */     ReadMatrixCsv csv = new ReadMatrixCsv(fileStream);
/*     */     
/* 129 */     DenseMatrix64F ret = csv.read();
/*     */     
/* 131 */     fileStream.close();
/*     */     
/* 133 */     return ret;
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
/*     */   public static DenseMatrix64F loadCSV(String fileName, int numRows, int numCols) throws IOException {
/* 149 */     FileInputStream fileStream = new FileInputStream(fileName);
/* 150 */     ReadMatrixCsv csv = new ReadMatrixCsv(fileStream);
/*     */     
/* 152 */     DenseMatrix64F ret = csv.read(numRows, numCols);
/*     */     
/* 154 */     fileStream.close();
/*     */     
/* 156 */     return ret;
/*     */   }
/*     */   
/*     */   public static void print(PrintStream out, Matrix64F mat) {
/* 160 */     print(out, mat, 6, 3);
/*     */   }
/*     */   
/*     */   public static void print(PrintStream out, Matrix64F mat, int numChar, int precision) {
/* 164 */     String format = "%" + numChar + "." + precision + "f ";
/*     */     
/* 166 */     print(out, mat, format);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void print(PrintStream out, Matrix64F mat, String format) {
/* 171 */     String type = ReshapeMatrix64F.class.isAssignableFrom(mat.getClass()) ? "dense" : "dense fixed";
/*     */     
/* 173 */     out.println("Type = " + type + " , numRows = " + mat.getNumRows() + " , numCols = " + mat.getNumCols());
/*     */     
/* 175 */     format = format + " ";
/*     */     
/* 177 */     for (int y = 0; y < mat.getNumRows(); y++) {
/* 178 */       for (int x = 0; x < mat.getNumCols(); x++) {
/* 179 */         out.printf(format, new Object[] { Double.valueOf(mat.get(y, x)) });
/*     */       } 
/* 181 */       out.println();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void print(PrintStream out, ReshapeMatrix64F mat, String format, int row0, int row1, int col0, int col1) {
/* 187 */     out.println("Type = submatrix , rows " + row0 + " to " + row1 + "  columns " + col0 + " to " + col1);
/*     */     
/* 189 */     format = format + " ";
/*     */     
/* 191 */     for (int y = row0; y < row1; y++) {
/* 192 */       for (int x = col0; x < col1; x++) {
/* 193 */         out.printf(format, new Object[] { Double.valueOf(mat.get(y, x)) });
/*     */       } 
/* 195 */       out.println();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\MatrixIO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */