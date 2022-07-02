/*     */ package ic2.shades.org.ejml.alg.dense.linsol;
/*     */ 
/*     */ import ic2.shades.org.ejml.data.Matrix64F;
/*     */ import ic2.shades.org.ejml.data.ReshapeMatrix64F;
/*     */ import ic2.shades.org.ejml.interfaces.linsol.LinearSolver;
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
/*     */ public class LinearSolverSafe<T extends ReshapeMatrix64F>
/*     */   implements LinearSolver<T>
/*     */ {
/*     */   private LinearSolver<T> alg;
/*     */   private T A;
/*     */   private T B;
/*     */   
/*     */   public LinearSolverSafe(LinearSolver<T> alg) {
/*  46 */     this.alg = alg;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setA(T A) {
/*  52 */     if (this.alg.modifiesA()) {
/*  53 */       if (this.A == null) {
/*  54 */         this.A = (T)A.copy();
/*     */       } else {
/*  56 */         if (((ReshapeMatrix64F)this.A).numRows != ((ReshapeMatrix64F)A).numRows || ((ReshapeMatrix64F)this.A).numCols != ((ReshapeMatrix64F)A).numCols) {
/*  57 */           this.A.reshape(((ReshapeMatrix64F)A).numRows, ((ReshapeMatrix64F)A).numCols, false);
/*     */         }
/*  59 */         this.A.set((ReshapeMatrix64F)A);
/*     */       } 
/*  61 */       return this.alg.setA((Matrix64F)this.A);
/*     */     } 
/*     */     
/*  64 */     return this.alg.setA((Matrix64F)A);
/*     */   }
/*     */ 
/*     */   
/*     */   public double quality() {
/*  69 */     return this.alg.quality();
/*     */   }
/*     */ 
/*     */   
/*     */   public void solve(T B, T X) {
/*  74 */     if (this.alg.modifiesB()) {
/*  75 */       if (this.B == null) {
/*  76 */         this.B = (T)B.copy();
/*     */       } else {
/*  78 */         if (((ReshapeMatrix64F)this.B).numRows != ((ReshapeMatrix64F)B).numRows || ((ReshapeMatrix64F)this.B).numCols != ((ReshapeMatrix64F)B).numCols) {
/*  79 */           this.B.reshape(((ReshapeMatrix64F)this.A).numRows, ((ReshapeMatrix64F)B).numCols, false);
/*     */         }
/*  81 */         this.B.set((ReshapeMatrix64F)B);
/*     */       } 
/*  83 */       B = this.B;
/*     */     } 
/*     */     
/*  86 */     this.alg.solve((Matrix64F)B, (Matrix64F)X);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invert(T A_inv) {
/*  91 */     this.alg.invert((Matrix64F)A_inv);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesA() {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean modifiesB() {
/* 101 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\shades\org\ejml\alg\dense\linsol\LinearSolverSafe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */