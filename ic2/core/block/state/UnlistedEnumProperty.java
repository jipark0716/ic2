/*    */ package ic2.core.block.state;
/*    */ 
/*    */ public class UnlistedEnumProperty<V extends Enum<V>> extends UnlistedProperty<V> {
/*    */   public UnlistedEnumProperty(String name, Class<V> cls) {
/*  5 */     super(name, cls);
/*    */   }
/*    */ 
/*    */   
/*    */   public String valueToString(V value) {
/* 10 */     return value.name();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\UnlistedEnumProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */