/*    */ package ic2.api.info;
/*    */ 
/*    */ import net.minecraft.potion.Potion;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.LoaderState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Info
/*    */ {
/*    */   public static IInfoProvider itemInfo;
/*    */   public static Object ic2ModInstance;
/*    */   public static DamageSource DMG_ELECTRIC;
/*    */   public static DamageSource DMG_NUKE_EXPLOSION;
/*    */   public static DamageSource DMG_RADIATION;
/*    */   public static Potion POTION_RADIATION;
/*    */   
/*    */   public static boolean isIc2Available() {
/* 26 */     if (ic2Available != null) return ic2Available.booleanValue();
/*    */     
/* 28 */     boolean loaded = Loader.isModLoaded(MOD_ID);
/*    */     
/* 30 */     if (Loader.instance().hasReachedState(LoaderState.CONSTRUCTING)) {
/* 31 */       ic2Available = Boolean.valueOf(loaded);
/*    */     }
/*    */     
/* 34 */     return loaded;
/*    */   }
/*    */   
/* 37 */   public static String MOD_ID = "ic2";
/* 38 */   private static Boolean ic2Available = null;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\info\Info.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */