/*    */ package ic2.core.util;
/*    */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*    */ 
/*    */ public final class SideGateway<T> {
/*    */   private final T clientInstance;
/*    */   
/*    */   public SideGateway(String serverClass, String clientClass) {
/*    */     try {
/*  9 */       if (FMLCommonHandler.instance().getSide().isClient()) {
/* 10 */         this.clientInstance = (T)Class.forName(clientClass).newInstance();
/*    */       } else {
/* 12 */         this.clientInstance = null;
/*    */       } 
/*    */       
/* 15 */       this.serverInstance = (T)Class.forName(serverClass).newInstance();
/* 16 */     } catch (Exception e) {
/* 17 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   private final T serverInstance;
/*    */   public T get(boolean simulating) {
/* 22 */     if (simulating) {
/* 23 */       return this.serverInstance;
/*    */     }
/* 25 */     return this.clientInstance;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\SideGateway.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */