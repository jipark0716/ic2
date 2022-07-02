/*    */ package ic2.core.block.state;
/*    */ 
/*    */ public class UnlistedProperty<T> implements IUnlistedProperty<T> {
/*    */   private final String name;
/*    */   
/*    */   public UnlistedProperty(String name, Class<T> cls) {
/*  7 */     this.name = name;
/*  8 */     this.cls = cls;
/*    */   }
/*    */   private final Class<T> cls;
/*    */   
/*    */   public String getName() {
/* 13 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValid(T value) {
/* 18 */     return (value == null || this.cls.isInstance(value));
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<T> getType() {
/* 23 */     return this.cls;
/*    */   }
/*    */ 
/*    */   
/*    */   public String valueToString(T value) {
/* 28 */     return value.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     return getClass().getSimpleName() + "{name=" + this.name + ", cls=" + this.cls.getName() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\UnlistedProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */