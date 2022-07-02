/*    */ package ic2.core.item.upgrade;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public enum NbtSettings {
/*    */   public static final NbtSettings[] VALUES;
/*  6 */   IGNORED, FUZZY, EXACT; final String name;
/*    */   NbtSettings() {
/*  8 */     this.name = "ic2.upgrade.advancedGUI." + name().toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */   public boolean enabled() {
/* 11 */     return (this != IGNORED);
/*    */   }
/*    */   
/*    */   public byte getForNBT() {
/* 15 */     return (byte)ordinal();
/*    */   }
/*    */   
/*    */   public static NbtSettings getFromNBT(byte type) {
/* 19 */     return VALUES[type];
/*    */   }
/*    */   static {
/* 22 */     VALUES = values();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\NbtSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */