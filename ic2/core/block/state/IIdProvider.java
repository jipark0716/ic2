/*    */ package ic2.core.block.state;
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
/*    */ public interface IIdProvider
/*    */ {
/*    */   String getName();
/*    */   
/*    */   int getId();
/*    */   
/*    */   default int getColor() {
/* 27 */     return 16777215;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default String getModelName() {
/* 36 */     return getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\IIdProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */