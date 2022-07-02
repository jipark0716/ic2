/*   */ package ic2.core.block;
/*   */ 
/*   */ class UnstartingThreadLocal<T>
/*   */   extends ThreadLocal<T> {
/*   */   protected T initialValue() {
/* 6 */     throw new UnsupportedOperationException();
/*   */   }
/*   */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\UnstartingThreadLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */