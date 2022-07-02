/*    */ package ic2.core.energy.grid;
/*    */ 
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ 
/*    */ public class EnergyNetSettings {
/*  7 */   public static final boolean logEnetApiAccesses = ConfigUtil.getBool(MainConfig.get(), "debug/logEnetApiAccesses");
/*  8 */   public static final boolean logEnetApiAccessTraces = ConfigUtil.getBool(MainConfig.get(), "debug/logEnetApiAccessTraces");
/*  9 */   public static boolean logGridUpdateIssues = ConfigUtil.getBool(MainConfig.get(), "debug/logGridUpdateIssues");
/* 10 */   public static boolean logGridUpdatesVerbose = ConfigUtil.getBool(MainConfig.get(), "debug/logGridUpdatesVerbose");
/* 11 */   public static boolean logGridCalculationIssues = ConfigUtil.getBool(MainConfig.get(), "debug/logGridCalculationIssues");
/*    */   public static final boolean logGridUpdatePerformance = false;
/*    */   public static final boolean logGridCalculationPerformance = false;
/* 14 */   public static final boolean roundLossDown = ConfigUtil.getBool(MainConfig.get(), "misc/roundEnetLoss");
/*    */   public static final int changesQueueDelay = 1;
/*    */   public static final double nonConductorResistance = 0.001D;
/*    */   public static final int bfsThreshold = 2048;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\EnergyNetSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */