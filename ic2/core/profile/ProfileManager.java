/*     */ package ic2.core.profile;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import ic2.api.event.ProfileEvent;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.init.Rezepte;
/*     */ import ic2.core.util.Config;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.TreeMap;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.FallbackResourceManager;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.client.FMLClientHandler;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ import org.apache.commons.io.IOCase;
/*     */ import org.apache.commons.io.filefilter.NameFileFilter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfileManager
/*     */ {
/*     */   public static final String EXPERIMENTAL = "Experimental";
/*     */   public static final String CLASSIC = "Classic";
/*  51 */   public static final Map<String, Profile> PROFILES = addDefaultProfiles();
/*     */   
/*     */   private static Map<String, Profile> addDefaultProfiles() {
/*  54 */     Map<String, Profile> ret = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
/*     */     
/*  56 */     ret.put("Experimental", new Profile("Experimental", Collections.singleton(TextureStyle.EXPERIMENTAL), Version.NEW, new RecipeChange[0]));
/*     */     try {
/*  58 */       ret.put("Classic", ProfileParser.parse(ProfileTarget.fromJar("ic2/profiles/classic")));
/*  59 */     } catch (IOException e) {
/*  60 */       throw new RuntimeException("Error opening profile XML", e);
/*     */     } 
/*     */     
/*  63 */     return ret;
/*     */   }
/*     */   @SideOnly(Side.CLIENT)
/*     */   private static List<IResourcePack> textureChanges;
/*  67 */   public enum ChangeAction { Nothing, ResourceReload, GameReload; }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*  72 */     Config.Value config = MainConfig.get().get("profiles/selected");
/*  73 */     if (config == null)
/*  74 */       return;  String active = config.value;
/*     */     
/*  76 */     File root = new File(IC2.platform.getMinecraftDir(), "ic2_profiles");
/*  77 */     if (root.exists()) {
/*  78 */       for (File file : root.listFiles()) {
/*  79 */         if (file.isDirectory()) {
/*  80 */           for (File file1 : file.listFiles((FilenameFilter)new NameFileFilter("profile.xml"))) {
/*     */             try {
/*  82 */               Profile p = ProfileParser.parse(new ProfileRoot(file1.getParentFile()));
/*     */               
/*  84 */               if (!registerProfile(p)) {
/*  85 */                 IC2.log.warn(LogCategory.General, "Duplicate profile names: %s, skipping profile at %s", new Object[] { p.name, file1 });
/*     */               } else {
/*  87 */                 IC2.log.debug(LogCategory.General, "Registered profile %s at %s", new Object[] { p.name, file1 });
/*     */               } 
/*  89 */             } catch (IOException e) {
/*  90 */               throw new RuntimeException("Error opening " + file1, e);
/*     */             } 
/*     */           } 
/*  93 */         } else if (IOCase.INSENSITIVE.checkEquals(FilenameUtils.getExtension(file.getName()), "zip")) {
/*  94 */           try (ZipFile zip = new ZipFile(file)) {
/*  95 */             ZipEntry entry = zip.getEntry("profile.xml");
/*     */             
/*  97 */             if (entry != null) {
/*  98 */               Profile p = ProfileParser.parse(new ProfileRoot(file));
/*     */               
/* 100 */               if (!registerProfile(p)) {
/* 101 */                 IC2.log.warn(LogCategory.General, "Duplicate profile names: %s, skipping profile in %s", new Object[] { p.name, file });
/*     */               } else {
/* 103 */                 IC2.log.debug(LogCategory.General, "Registered profile %s in %s", new Object[] { p.name, file });
/*     */               } 
/*     */             } 
/* 106 */           } catch (IOException e) {
/* 107 */             IC2.log.warn(LogCategory.General, "Error opening zip at " + file, new Object[] { e });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 113 */     MinecraftForge.EVENT_BUS.post((Event)new ProfileEvent.Load(Collections.unmodifiableSet(PROFILES.keySet()), active));
/*     */     
/* 115 */     Profile profile = get(active);
/* 116 */     if (profile != null) {
/* 117 */       if (selected != profile) {
/* 118 */         IC2.log.info(LogCategory.General, "Switching profiles from %s to %s", new Object[] { selected.name, active });
/*     */       }
/*     */       
/* 121 */       switchProfiles(profile);
/*     */     } else {
/* 123 */       IC2.log.warn(LogCategory.General, "Unknown/Invalid profile selected in the profile: %s, must be one of %s", new Object[] { active, PROFILES });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean registerProfile(Profile profile) {
/* 129 */     return (PROFILES.putIfAbsent(profile.name, profile) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ChangeAction switchProfiles(Profile to) {
/* 134 */     Profile from = selected;
/* 135 */     if (from == to) return ChangeAction.Nothing;
/*     */     
/* 137 */     MinecraftForge.EVENT_BUS.post((Event)new ProfileEvent.Switch(from.name, to.name));
/* 138 */     applySwitch(to);
/*     */     
/* 140 */     if (from.style != to.style)
/* 141 */       return ChangeAction.GameReload; 
/* 142 */     if (!from.recipeConfigs.equals(to.recipeConfigs) || !from.recipeRemovals.equals(to.recipeRemovals))
/*     */     {
/* 144 */       return ChangeAction.GameReload; } 
/* 145 */     if (!from.textures.equals(to.textures)) {
/* 146 */       return ChangeAction.ResourceReload;
/*     */     }
/* 148 */     return ChangeAction.Nothing;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void applySwitch(Profile to) {
/* 154 */     selected = to;
/* 155 */     IC2.version = to.style;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Profile get(String name) {
/* 160 */     return PROFILES.get(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Profile getOrError(String name) {
/* 165 */     Profile ret = PROFILES.get(name);
/* 166 */     if (ret != null) return ret;
/*     */     
/* 168 */     throw new IllegalArgumentException("Cannot find profile " + name + "! Only have " + PROFILES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static InputStream getRecipeConfig(String name) {
/* 173 */     List<RecipeChange> configs = selected.processRecipeConfigs(name);
/* 174 */     if (configs.isEmpty()) return Rezepte.getDefaultConfigFile(name);
/*     */     
/* 176 */     boolean isReplacing = configs.stream().anyMatch(change -> (change.type == RecipeChange.ChangeType.REPLACEMENT));
/*     */     
/* 178 */     if (isReplacing && configs.size() == 1) {
/* 179 */       return ((RecipeChange)Iterables.getOnlyElement(configs)).getStream();
/*     */     }
/* 181 */     List<InputStream> streams = (List<InputStream>)configs.stream().map(RecipeChange::getStream).filter(Objects::nonNull).collect(Collectors.toList());
/* 182 */     if (!isReplacing) streams.add(0, Rezepte.getDefaultConfigFile(name));
/*     */     
/* 184 */     byte[] split = { 10 };
/* 185 */     for (int i = configs.size() - 1; i > 0; i--)
/*     */     {
/* 187 */       streams.add(i, new ByteArrayInputStream(split));
/*     */     }
/*     */     
/* 190 */     return new SequenceInputStream(Collections.enumeration(streams));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void getRecipeRemovals(String name) {
/* 195 */     List<Object> configs = selected.recipeRemovals.get(name);
/* 196 */     if (configs.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void doTextureChanges() {
/* 203 */     if (textureChanges == null) textureChanges = Collections.emptyList(); 
/* 204 */     List<IResourcePack> packs = new ArrayList<>();
/*     */     
/* 206 */     Map<String, FallbackResourceManager> domainManagers = (Map<String, FallbackResourceManager>)ReflectionUtil.getValue(Minecraft.func_71410_x().func_110442_L(), Map.class);
/* 207 */     for (TextureStyle texture : selected.textures) {
/* 208 */       FallbackResourceManager manager = domainManagers.get(texture.mod);
/* 209 */       if (manager == null) {
/*     */         continue;
/*     */       }
/* 212 */       ((List)ReflectionUtil.getValue(manager, List.class)).removeAll(textureChanges);
/*     */       
/* 214 */       IResourcePack pack = texture.applyChanges();
/* 215 */       if (pack != null) {
/* 216 */         manager.func_110538_a(pack);
/* 217 */         packs.add(pack);
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     List<IResourcePack> defaultPacks = (List<IResourcePack>)ReflectionUtil.getValue(FMLClientHandler.instance(), List.class);
/*     */     
/* 223 */     defaultPacks.removeAll(textureChanges);
/* 224 */     assert !defaultPacks.stream().anyMatch(pack -> pack.func_130077_b().startsWith("IC2 Profile Pack for "));
/*     */     
/* 226 */     packs.forEach(defaultPacks::add);
/*     */     
/* 228 */     textureChanges = packs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 233 */   public static Profile selected = getOrError("Experimental");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\ProfileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */