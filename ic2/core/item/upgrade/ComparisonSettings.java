/*    */ package ic2.core.item.upgrade;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum ComparisonSettings
/*    */ {
/* 11 */   LESS_OR_EQUAL("<=")
/*    */   {
/*    */     public boolean compare(int value, int comparison) {
/* 14 */       return (value <= comparison);
/*    */     } },
/* 16 */   LESS("<")
/*    */   {
/*    */     public boolean compare(int value, int comparison) {
/* 19 */       return (value < comparison);
/*    */     } },
/* 21 */   GREATER(">")
/*    */   {
/*    */     public boolean compare(int value, int comparison) {
/* 24 */       return (value > comparison);
/*    */     } },
/* 26 */   GREATER_OR_EQUAL(">=")
/*    */   {
/*    */     public boolean compare(int value, int comparison) {
/* 29 */       return (value >= comparison);
/*    */     }
/*    */   };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final String symbol;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final String name;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final ComparisonSettings DEFAULT;
/*    */ 
/*    */ 
/*    */   
/*    */   public static final ComparisonSettings[] VALUES;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ComparisonSettings(String symbol) {
/* 56 */     this.name = "ic2.upgrade.advancedGUI." + name().toLowerCase(Locale.ENGLISH);
/*    */     this.symbol = symbol; } static {
/* 58 */     DEFAULT = LESS;
/* 59 */     VALUES = values();
/*    */   }
/*    */   
/*    */   public byte getForNBT() {
/*    */     return (byte)ordinal();
/*    */   }
/*    */   
/*    */   public static ComparisonSettings getFromNBT(byte type) {
/*    */     return VALUES[type];
/*    */   }
/*    */   
/*    */   public abstract boolean compare(int paramInt1, int paramInt2);
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\ComparisonSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */