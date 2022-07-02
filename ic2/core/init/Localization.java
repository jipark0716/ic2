/*     */ package ic2.core.init;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.client.resources.Locale;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.util.text.translation.LanguageMap;
/*     */ import net.minecraftforge.fml.common.FMLCommonHandler;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class Localization
/*     */ {
/*     */   private static final String defaultLang = "en_us";
/*     */   private static final String ic2LangKey = "ic2.";
/*     */   
/*     */   public static void preInit(File modSourceFile) {
/*  39 */     if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
/*  40 */       Map<String, String> map = getLanguageMapMap();
/*  41 */       loadServerLangFile(modSourceFile, map);
/*     */     } else {
/*     */       
/*  44 */       registerResourceReloadHook();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadServerLangFile(File modSourceFile, Map<String, String> out) {
/*  49 */     String path = "/assets/ic2/" + getLangPath("en_us");
/*     */     
/*  51 */     InputStream is = Localization.class.getResourceAsStream(path);
/*     */     try {
/*  53 */       loadLocalization(is, out);
/*  54 */       IC2.log.trace(LogCategory.Resource, "Successfully loaded server localization.");
/*  55 */     } catch (IOException e) {
/*  56 */       IC2.log.warn(LogCategory.Resource, "Failed to load server localization.");
/*  57 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getLangPath(String language) {
/*  62 */     return "lang_ic2/" + language + ".properties";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static void registerResourceReloadHook() {
/*  70 */     IResourceManager resManager = Minecraft.func_71410_x().func_110442_L();
/*     */     
/*  72 */     if (resManager instanceof IReloadableResourceManager) {
/*  73 */       ((IReloadableResourceManager)resManager).func_110542_a(new IResourceManagerReloadListener()
/*     */           {
/*     */             public void func_110549_a(IResourceManager manager) {
/*  76 */               Map<String, String> tmpMap = new HashMap<>();
/*  77 */               Map<String, String> lmMap = Localization.getLanguageMapMap();
/*  78 */               Map<String, String> localeMap = Localization.getLocaleMap();
/*     */               
/*  80 */               Set<String> languages = new LinkedHashSet<>();
/*  81 */               languages.add("en_us");
/*  82 */               languages.add((Minecraft.func_71410_x()).field_71474_y.field_74363_ab);
/*     */               
/*  84 */               for (String lang : languages) {
/*     */                 try {
/*  86 */                   for (IResource res : manager.func_135056_b(new ResourceLocation("ic2", Localization.getLangPath(lang)))) {
/*     */                     try {
/*  88 */                       tmpMap.clear();
/*  89 */                       Localization.loadLocalization(res.func_110527_b(), tmpMap);
/*  90 */                       lmMap.putAll(tmpMap);
/*  91 */                       localeMap.putAll(tmpMap);
/*     */                       
/*  93 */                       IC2.log.debug(LogCategory.Resource, "Loaded translation keys from %s.", new Object[] { res.func_177241_a() });
/*     */                     } finally {
/*     */                       try {
/*  96 */                         res.close();
/*  97 */                       } catch (IOException iOException) {}
/*     */                     } 
/*     */                   } 
/* 100 */                 } catch (FileNotFoundException e) {
/* 101 */                   IC2.log.debug(LogCategory.Resource, "No translation file for language %s.", new Object[] { lang });
/* 102 */                 } catch (IOException e) {
/* 103 */                   throw new RuntimeException(e);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadLocalization(InputStream inputStream, Map<String, String> out) throws IOException {
/* 112 */     Properties properties = new Properties();
/*     */     
/* 114 */     properties.load(new InputStreamReader(inputStream, Charsets.UTF_8));
/*     */     
/* 116 */     for (Map.Entry<Object, Object> entries : properties.entrySet()) {
/* 117 */       Object key = entries.getKey();
/* 118 */       Object value = entries.getValue();
/*     */       
/* 120 */       if (key instanceof String && value instanceof String) {
/* 121 */         String newKey = (String)key;
/*     */         
/* 123 */         if (!newKey.startsWith("achievement.") && 
/* 124 */           !newKey.startsWith("itemGroup.") && 
/* 125 */           !newKey.startsWith("death.")) {
/* 126 */           newKey = "ic2." + newKey;
/*     */         }
/*     */         
/* 129 */         out.put(newKey, (String)value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Map<String, String> getLanguageMapMap() {
/* 139 */     for (Method method : LanguageMap.class.getDeclaredMethods()) {
/* 140 */       if (method.getReturnType() == LanguageMap.class) {
/* 141 */         method.setAccessible(true);
/*     */         
/* 143 */         Field mapField = ReflectionUtil.getField(LanguageMap.class, Map.class);
/*     */         
/*     */         try {
/* 146 */           return (Map<String, String>)mapField.get(method.invoke((Object)null, new Object[0]));
/* 147 */         } catch (Exception e) {
/* 148 */           throw new RuntimeException(e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Map<String, String> getLocaleMap() {
/* 161 */     Field localeField = ReflectionUtil.getField(I18n.class, Locale.class);
/* 162 */     Field mapField = ReflectionUtil.getField(Locale.class, Map.class);
/*     */     
/*     */     try {
/* 165 */       return (Map<String, String>)mapField.get(localeField.get(null));
/* 166 */     } catch (Exception e) {
/* 167 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String translate(String key) {
/* 173 */     return I18n.func_74838_a(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String translate(String key, Object... args) {
/* 178 */     return I18n.func_74837_a(key, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\init\Localization.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */