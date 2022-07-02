/*    */ package ic2.core.profile;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import java.lang.reflect.AnnotatedElement;
/*    */ 
/*    */ 
/*    */ public enum Version
/*    */ {
/*  9 */   NEW,
/*    */   
/* 11 */   BOTH,
/*    */   
/* 13 */   OLD;
/*    */   
/*    */   public boolean isExperimental() {
/* 16 */     return (this == NEW);
/*    */   }
/*    */   
/*    */   public boolean isClassic() {
/* 20 */     return (this == OLD);
/*    */   }
/*    */   
/*    */   public static boolean shouldEnable(AnnotatedElement e) {
/* 24 */     return shouldEnable(e, true);
/*    */   }
/*    */   
/*    */   public static boolean shouldEnable(AnnotatedElement e, boolean defaultState) {
/* 28 */     if (e.isAnnotationPresent((Class)NotExperimental.class))
/* 29 */       return !IC2.version.isExperimental(); 
/* 30 */     if (e.isAnnotationPresent((Class)NotClassic.class)) {
/* 31 */       return !IC2.version.isClassic();
/*    */     }
/* 33 */     return (e.isAnnotationPresent((Class)Both.class) || defaultState);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\Version.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */