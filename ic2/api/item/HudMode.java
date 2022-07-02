/*    */ package ic2.api.item;
/*    */ 
/*    */ public enum HudMode {
/*  4 */   DISABLED("ic2.hud.disabled"), BASIC("ic2.hud.basic"), EXTENDED("ic2.hud.extended"), ADVANCED("ic2.hud.advanced"); private static final HudMode[] VALUES;
/*    */   
/*    */   HudMode(String key) {
/*  7 */     this.translationKey = key;
/*    */   }
/*    */   
/*    */   private final String translationKey;
/*    */   
/*    */   public boolean shouldDisplay() {
/* 13 */     return (this != DISABLED);
/*    */   }
/*    */   
/*    */   public boolean hasTooltip() {
/* 17 */     return (this == EXTENDED || this == ADVANCED);
/*    */   }
/*    */   
/*    */   public String getTranslationKey() {
/* 21 */     return this.translationKey;
/*    */   }
/*    */   
/*    */   public int getID() {
/* 25 */     return ordinal();
/*    */   }
/*    */   
/*    */   public static HudMode getFromID(int ID) {
/* 29 */     return VALUES[ID % VALUES.length];
/*    */   }
/*    */   
/*    */   public static int getMaxMode() {
/* 33 */     return VALUES.length - 1;
/*    */   }
/*    */   static {
/* 36 */     VALUES = values();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\HudMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */