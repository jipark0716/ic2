/*    */ package ic2.core.item.upgrade;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public enum ComparisonType {
/*    */   public static final ComparisonType[] VALUES;
/*  6 */   IGNORED, DIRECT, COMPARISON, RANGE; final String name;
/*    */   ComparisonType() {
/*  8 */     this.name = "ic2.upgrade.advancedGUI." + name().toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */   public boolean enabled() {
/* 11 */     return (this != IGNORED);
/*    */   }
/*    */   
/*    */   public boolean ignoreFilters() {
/* 15 */     return (this == IGNORED || this == DIRECT);
/*    */   }
/*    */   
/*    */   public byte getForNBT() {
/* 19 */     return (byte)ordinal();
/*    */   }
/*    */   
/*    */   public static ComparisonType getFromNBT(byte type) {
/* 23 */     return VALUES[type];
/*    */   }
/*    */   static {
/* 26 */     VALUES = values();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\ComparisonType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */