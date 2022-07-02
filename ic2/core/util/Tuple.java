/*    */ package ic2.core.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class Tuple {
/*    */   public static class T2<TA, TB> { public TA a;
/*    */     
/*    */     public T2(TA a, TB b) {
/* 10 */       this.a = a;
/* 11 */       this.b = b;
/*    */     }
/*    */     public TB b; }
/*    */   
/*    */   public static class T3<TA, TB, TC> { public TA a;
/*    */     public TB b;
/*    */     public TC c;
/*    */     
/*    */     public T3(TA a, TB b, TC c) {
/* 20 */       this.a = a;
/* 21 */       this.b = b;
/* 22 */       this.c = c;
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <K, V> List<T2<K, V>> fromMap(Map<K, V> map) {
/* 31 */     List<T2<K, V>> ret = new ArrayList<>(map.size());
/*    */     
/* 33 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/* 34 */       ret.add(new T2<>(entry.getKey(), entry.getValue()));
/*    */     }
/*    */     
/* 37 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Tuple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */