/*    */ package ic2.core.block.state;
/*    */ 
/*    */ import net.minecraftforge.common.property.IUnlistedProperty;
/*    */ 
/*    */ public class UnlistedBooleanProperty implements IUnlistedProperty<Boolean> {
/*    */   public UnlistedBooleanProperty(String name) {
/*  7 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 12 */     return this.name;
/*    */   }
/*    */   private final String name;
/*    */   
/*    */   public boolean isValid(Boolean value) {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Boolean> getType() {
/* 22 */     return Boolean.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String valueToString(Boolean value) {
/* 27 */     return value.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return getClass().getSimpleName() + "{name=" + this.name + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\state\UnlistedBooleanProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */