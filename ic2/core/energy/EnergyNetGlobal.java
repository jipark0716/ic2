/*    */ package ic2.core.energy;
/*    */ 
/*    */ import ic2.core.util.Util;
/*    */ 
/*    */ public class EnergyNetGlobal {
/*    */   protected static boolean verifyGrid() {
/*  7 */     return Util.hasAssertions();
/*    */   }
/*    */   
/* 10 */   public static final boolean replaceConflicting = (System.getProperty("ic2.energynet.replaceconflicting") != null);
/* 11 */   public static final boolean debugTileManagement = (System.getProperty("ic2.energynet.debugtilemanagement") != null);
/* 12 */   public static final boolean debugGrid = (System.getProperty("ic2.energynet.debuggrid") != null);
/* 13 */   public static final boolean debugGridVerbose = (debugGrid && System.getProperty("ic2.energynet.debuggrid").equals("verbose"));
/* 14 */   public static final boolean checkApi = (System.getProperty("ic2.energynet.checkapi") != null);
/* 15 */   public static final boolean logAll = (System.getProperty("ic2.energynet.logall") != null);
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\EnergyNetGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */