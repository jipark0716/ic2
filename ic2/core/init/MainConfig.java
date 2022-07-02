/*    */ package ic2.core.init;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.Config;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import java.io.File;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainConfig
/*    */ {
/*    */   public static void load() {
/* 16 */     config = new Config("ic2 general config");
/* 17 */     defaultConfig = new Config("ic2 default config");
/*    */     
/*    */     try {
/* 20 */       config.load(IC2.class.getResourceAsStream("/assets/ic2/config/general.ini"));
/* 21 */       defaultConfig.load(IC2.class.getResourceAsStream("/assets/ic2/config/general.ini"));
/* 22 */     } catch (Exception e) {
/* 23 */       throw new RuntimeException("Error loading base config", e);
/*    */     } 
/*    */     
/* 26 */     File configFile = getFile();
/*    */     
/*    */     try {
/* 29 */       if (configFile.exists()) config.load(configFile); 
/* 30 */     } catch (Exception e) {
/* 31 */       throw new RuntimeException("Error loading user config", e);
/*    */     } 
/*    */     
/* 34 */     upgradeContents();
/* 35 */     save();
/*    */     
/* 37 */     ignoreInvalidRecipes = ConfigUtil.getBool(get(), "recipes/ignoreInvalidRecipes");
/*    */   }
/*    */   
/*    */   public static void save() {
/*    */     try {
/* 42 */       config.save(getFile());
/* 43 */     } catch (Exception e) {
/* 44 */       throw new RuntimeException("Error saving user config", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Config get() {
/* 49 */     return config;
/*    */   }
/*    */   
/*    */   public static Config.Value getDefault(String config) {
/* 53 */     return defaultConfig.get(config);
/*    */   }
/*    */   
/*    */   public static Iterator<Config.Value> getDefaults(String sub) {
/* 57 */     return defaultConfig.getSub(sub).valueIterator();
/*    */   }
/*    */   
/*    */   private static File getFile() {
/* 61 */     File folder = new File(IC2.platform.getMinecraftDir(), "config");
/* 62 */     folder.mkdirs();
/*    */     
/* 64 */     return new File(folder, "IC2.ini");
/*    */   }
/*    */   
/*    */   private static void upgradeContents() {
/* 68 */     if (config.get("worldgen/copperOre") != null) {
/* 69 */       String[] ores = { "copper", "tin", "uranium", "lead" };
/*    */       
/* 71 */       for (String ore : ores) {
/* 72 */         Config.Value oldValue = config.remove("worldgen/" + ore + "Ore");
/* 73 */         if (oldValue != null)
/*    */         {
/* 75 */           if (!oldValue.getBool()) {
/* 76 */             Config.Value newValue = config.get("worldgen/" + ore + "/enabled");
/* 77 */             newValue.set(Boolean.valueOf(false));
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/* 82 */     List<String> blacklist = ConfigUtil.asList(ConfigUtil.getString(config, "balance/recyclerBlacklist"));
/* 83 */     if (blacklist.contains("IC2:blockScaffold")) {
/* 84 */       blacklist.set(blacklist.indexOf("IC2:blockScaffold"), "IC2:scaffold");
/* 85 */       config.set("balance/recyclerBlacklist", Joiner.on(", ").join(blacklist));
/*    */     } 
/*    */     
/* 88 */     if (config.get("misc/enableIc2Audio") != null) {
/* 89 */       config.get("audio/enabled").set(Boolean.valueOf(config.remove("misc/enableIc2Audio").getBool()));
/*    */     }
/* 91 */     if (config.get("misc/maxAudioSourceCount") != null)
/* 92 */       config.get("audio/maxSourceCount").set(Integer.valueOf(config.remove("misc/maxAudioSourceCount").getInt())); 
/*    */   }
/*    */   
/*    */   public static boolean ignoreInvalidRecipes = false;
/*    */   private static Config config;
/*    */   private static Config defaultConfig;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\init\MainConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */