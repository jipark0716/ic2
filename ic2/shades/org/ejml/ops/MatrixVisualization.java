/*    */ package ic2.shades.org.ejml.ops;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*    */ import javax.swing.JFrame;
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
/*    */ public class MatrixVisualization
/*    */ {
/*    */   public static void show(D1Matrix64F A, String title) {
/* 49 */     JFrame frame = new JFrame(title);
/*    */     
/* 51 */     int width = 300;
/* 52 */     int height = 300;
/*    */     
/* 54 */     if (A.numRows > A.numCols) {
/* 55 */       width = width * A.numCols / A.numRows;
/*    */     } else {
/* 57 */       height = height * A.numRows / A.numCols;
/*    */     } 
/*    */     
/* 60 */     MatrixComponent panel = new MatrixComponent(width, height);
/* 61 */     panel.setMatrix(A);
/*    */     
/* 63 */     frame.add(panel, "Center");
/*    */     
/* 65 */     frame.pack();
/* 66 */     frame.setVisible(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\MatrixVisualization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */