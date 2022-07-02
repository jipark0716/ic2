/*    */ package ic2.core.gui;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public interface IEnableHandler {
/*    */   boolean isEnabled();
/*    */   
/*    */   public static final class EnableHandlers {
/*    */     public static IEnableHandler and(IEnableHandler... handlers) {
/* 10 */       return () -> Arrays.<IEnableHandler>stream(handlers).allMatch(IEnableHandler::isEnabled);
/*    */     }
/*    */     
/*    */     public static IEnableHandler nand(IEnableHandler... handlers) {
/* 14 */       return () -> !Arrays.<IEnableHandler>stream(handlers).allMatch(IEnableHandler::isEnabled);
/*    */     }
/*    */     
/*    */     public static IEnableHandler or(IEnableHandler... handlers) {
/* 18 */       return () -> Arrays.<IEnableHandler>stream(handlers).anyMatch(IEnableHandler::isEnabled);
/*    */     }
/*    */     
/*    */     public static IEnableHandler nor(IEnableHandler... handlers) {
/* 22 */       return () -> Arrays.<IEnableHandler>stream(handlers).noneMatch(IEnableHandler::isEnabled);
/*    */     }
/*    */     
/*    */     public static IEnableHandler not(IEnableHandler handler) {
/* 26 */       return () -> !handler.isEnabled();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\IEnableHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */