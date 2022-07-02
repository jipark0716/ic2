/*    */ package ic2.shades.org.ejml.data;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class Complex64F
/*    */   implements Serializable
/*    */ {
/*    */   public double real;
/*    */   public double imaginary;
/*    */   
/*    */   public Complex64F(double real, double imaginary) {
/* 34 */     this.real = real;
/* 35 */     this.imaginary = imaginary;
/*    */   }
/*    */ 
/*    */   
/*    */   public Complex64F() {}
/*    */   
/*    */   public double getReal() {
/* 42 */     return this.real;
/*    */   }
/*    */   
/*    */   public double getMagnitude() {
/* 46 */     return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
/*    */   }
/*    */   
/*    */   public double getMagnitude2() {
/* 50 */     return this.real * this.real + this.imaginary * this.imaginary;
/*    */   }
/*    */   
/*    */   public void setReal(double real) {
/* 54 */     this.real = real;
/*    */   }
/*    */   
/*    */   public double getImaginary() {
/* 58 */     return this.imaginary;
/*    */   }
/*    */   
/*    */   public void setImaginary(double imaginary) {
/* 62 */     this.imaginary = imaginary;
/*    */   }
/*    */   
/*    */   public void set(double real, double imaginary) {
/* 66 */     this.real = real;
/* 67 */     this.imaginary = imaginary;
/*    */   }
/*    */   
/*    */   public boolean isReal() {
/* 71 */     return (this.imaginary == 0.0D);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 75 */     if (this.imaginary == 0.0D) {
/* 76 */       return "" + this.real;
/*    */     }
/* 78 */     return this.real + " " + this.imaginary + "i";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\data\Complex64F.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */