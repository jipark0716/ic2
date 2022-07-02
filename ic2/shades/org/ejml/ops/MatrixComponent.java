/*    */ package ic2.shades.org.ejml.ops;
/*    */ 
/*    */ import ic2.shades.org.ejml.data.D1Matrix64F;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import javax.swing.JPanel;
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
/*    */ public class MatrixComponent
/*    */   extends JPanel
/*    */ {
/*    */   BufferedImage image;
/*    */   
/*    */   public MatrixComponent(int width, int height) {
/* 37 */     this.image = new BufferedImage(width, height, 1);
/* 38 */     setPreferredSize(new Dimension(width, height));
/* 39 */     setMinimumSize(new Dimension(width, height));
/*    */   }
/*    */   
/*    */   public synchronized void setMatrix(D1Matrix64F A) {
/* 43 */     double maxValue = CommonOps.elementMaxAbs(A);
/* 44 */     renderMatrix(A, this.image, maxValue);
/* 45 */     repaint();
/*    */   }
/*    */ 
/*    */   
/*    */   public static void renderMatrix(D1Matrix64F M, BufferedImage image, double maxValue) {
/* 50 */     int w = image.getWidth();
/* 51 */     int h = image.getHeight();
/*    */     
/* 53 */     double widthStep = M.numCols / image.getWidth();
/* 54 */     double heightStep = M.numRows / image.getHeight();
/*    */     
/* 56 */     for (int i = 0; i < h; i++) {
/* 57 */       for (int j = 0; j < w; j++) {
/* 58 */         double value = M.get((int)(i * heightStep), (int)(j * widthStep));
/*    */         
/* 60 */         if (value == 0.0D) {
/* 61 */           image.setRGB(j, i, -16777216);
/* 62 */         } else if (value > 0.0D) {
/* 63 */           int p = 255 - (int)(255.0D * value / maxValue);
/* 64 */           int rgb = 0xFFFF0000 | p << 8 | p;
/*    */           
/* 66 */           image.setRGB(j, i, rgb);
/*    */         } else {
/* 68 */           int p = 255 + (int)(255.0D * value / maxValue);
/* 69 */           int rgb = 0xFF000000 | p << 16 | p << 8 | 0xFF;
/*    */           
/* 71 */           image.setRGB(j, i, rgb);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void paint(Graphics g) {
/* 81 */     g.drawImage(this.image, 0, 0, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\ops\MatrixComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */