/*     */ package ic2.core.profile;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraftforge.fml.common.Loader;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfileTarget
/*     */ {
/*     */   public final File root;
/*     */   public final String offset;
/*     */   
/*     */   public static ProfileTarget fromJar(String offset) {
/*  28 */     assert "ic2".equals(Loader.instance().activeModContainer().getModId());
/*  29 */     return new ProfileTarget(Loader.instance().activeModContainer().getSource(), offset);
/*     */   }
/*     */   
/*     */   public ProfileTarget(File root, String offset) {
/*  33 */     this.root = root;
/*  34 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFile() {
/*  41 */     return !this.root.isDirectory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File asFile() {
/*  48 */     return new File(this.root, this.offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZipFile asZip() {
/*  55 */     Map<String, ZipFile> offsetMap = ZIP_HOLDER.computeIfAbsent(this.root, k -> new HashMap<>());
/*  56 */     ZipFile ret = offsetMap.get(this.offset);
/*     */     
/*  58 */     if (ret == null) {
/*     */       try {
/*  60 */         offsetMap.put(this.offset, ret = makeZip());
/*  61 */       } catch (IOException e) {
/*  62 */         throw new RuntimeException("Failed to get zip!", e);
/*     */       } 
/*     */     }
/*     */     
/*  66 */     return ret;
/*     */   }
/*     */   
/*     */   protected ZipFile makeZip() throws IOException {
/*  70 */     return new ZipFile(this.root)
/*     */       {
/*     */         public ZipEntry getEntry(String name) {
/*  73 */           return super.getEntry(StringUtils.isNotBlank(name) ? (ProfileTarget.this.offset + '/' + name) : ProfileTarget.this.offset);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream asStream() throws IOException {
/*  82 */     if (isFile()) {
/*  83 */       ZipFile zip = asZip();
/*     */       
/*  85 */       ZipEntry entry = zip.getEntry(null);
/*  86 */       if (entry == null) return null;
/*     */       
/*  88 */       return zip.getInputStream(entry);
/*     */     } 
/*  90 */     File file = asFile();
/*  91 */     if (!file.canRead() || !file.isFile()) return null;
/*     */     
/*  93 */     return new BufferedInputStream(new FileInputStream(file));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProfileTarget offset(String extra) {
/* 105 */     return new ProfileTarget(this.root, this.offset + '/' + extra);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     return "ProfileTarget<" + this.root + " -> " + this.offset + '>';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 115 */     if (this == obj) return true; 
/* 116 */     if (!(obj instanceof ProfileTarget)) return false;
/*     */     
/* 118 */     ProfileTarget other = (ProfileTarget)obj;
/* 119 */     return (Objects.equals(this.root, other.root) && Objects.equals(this.offset, other.offset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   private static final Map<File, Map<String, ZipFile>> ZIP_HOLDER = new HashMap<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\ProfileTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */