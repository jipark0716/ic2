/*    */ package ic2.core.network;
/*    */ 
/*    */ import java.util.concurrent.CancellationException;
/*    */ import java.util.concurrent.CountDownLatch;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
/*    */ 
/*    */ public class Rpc<V>
/*    */   implements Future<V> {
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 13 */     if (isDone()) return false;
/*    */     
/* 15 */     this.cancelled = true;
/* 16 */     this.latch.countDown();
/*    */     
/* 18 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 23 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDone() {
/* 28 */     return (this.latch.getCount() == 0L);
/*    */   }
/*    */ 
/*    */   
/*    */   public V get() throws InterruptedException, ExecutionException {
/*    */     try {
/* 34 */       return get(-1L, TimeUnit.NANOSECONDS);
/* 35 */     } catch (TimeoutException e) {
/* 36 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 42 */     if (timeout < 0L) {
/* 43 */       this.latch.await();
/*    */     } else {
/* 45 */       boolean finished = this.latch.await(timeout, unit);
/* 46 */       if (!finished) throw new TimeoutException();
/*    */     
/*    */     } 
/* 49 */     if (this.cancelled) throw new CancellationException();
/*    */     
/* 51 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void finish(Object result) {
/* 56 */     this.result = (V)result;
/* 57 */     this.latch.countDown();
/*    */   }
/*    */   
/* 60 */   private final CountDownLatch latch = new CountDownLatch(1);
/*    */   private volatile boolean cancelled;
/*    */   private volatile V result;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\Rpc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */