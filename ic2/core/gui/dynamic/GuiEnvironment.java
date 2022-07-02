/*    */ package ic2.core.gui.dynamic;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum GuiEnvironment {
/*  8 */   GAME,
/*  9 */   JEI; private static final Map<String, GuiEnvironment> map;
/*    */   
/*    */   GuiEnvironment() {
/* 12 */     this.name = name().toLowerCase(Locale.ENGLISH);
/*    */   }
/*    */   public final String name;
/*    */   public static GuiEnvironment get(String name) {
/* 16 */     return map.get(name);
/*    */   }
/*    */   
/*    */   private static Map<String, GuiEnvironment> getMap() {
/* 20 */     GuiEnvironment[] values = values();
/* 21 */     Map<String, GuiEnvironment> ret = new HashMap<>(values.length);
/*    */     
/* 23 */     for (GuiEnvironment value : values) {
/* 24 */       ret.put(value.name, value);
/*    */     }
/*    */     
/* 27 */     return ret;
/*    */   }
/*    */   static {
/* 30 */     map = getMap();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\dynamic\GuiEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */