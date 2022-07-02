/*    */ package ic2.core.audio;public class FutureSound {
/*    */   private boolean run;
/*    */   
/*    */   public FutureSound(Runnable onFinish) {
/*  5 */     this.onFinish = onFinish;
/*    */   }
/*    */   private boolean cancelled; private final Runnable onFinish;
/*    */   public void cancel() {
/*  9 */     if (this.run) throw new IllegalStateException("Tried to cancel completed sound"); 
/* 10 */     this.cancelled = true;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 14 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   void onFinish() {
/* 18 */     if (this.run) throw new IllegalStateException("Tried to run completed sound"); 
/* 19 */     if (this.cancelled)
/*    */       return; 
/* 21 */     this.run = true;
/* 22 */     this.onFinish.run();
/*    */   }
/*    */   
/*    */   public boolean isComplete() {
/* 26 */     return this.run;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\audio\FutureSound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */