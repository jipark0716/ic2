/*    */ package ic2.core.energy;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ public class GridCalculation implements Callable<Iterable<Node>> {
/*    */   public GridCalculation(Grid grid1) {
/*  7 */     this.grid = grid1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<Node> call() throws Exception {
/* 12 */     return this.grid.calculate();
/*    */   }
/*    */   
/*    */   private final Grid grid;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\GridCalculation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */