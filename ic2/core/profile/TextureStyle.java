/*     */ package ic2.core.profile;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.client.resources.FolderResourcePack;
/*     */ import net.minecraft.client.resources.IResourcePack;
/*     */ import net.minecraft.client.resources.ResourcePackFileNotFoundException;
/*     */ import net.minecraft.client.resources.data.MetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureStyle
/*     */ {
/*  28 */   public static final TextureStyle EXPERIMENTAL = new TextureStyle("ic2", ProfileTarget.fromJar("assets/ic2"))
/*     */     {
/*     */       @SideOnly(Side.CLIENT)
/*     */       public IResourcePack applyChanges() {
/*  32 */         return null;
/*     */       }
/*     */     };
/*  35 */   public static final TextureStyle CLASSIC = new TextureStyle("ic2", ProfileTarget.fromJar("ic2/profiles/classic/ic2")); protected final ProfileTarget target;
/*     */   
/*     */   public TextureStyle(String mod, ProfileTarget target) {
/*  38 */     this.mod = mod;
/*  39 */     this.target = target;
/*     */   }
/*     */   public final String mod;
/*     */   @SideOnly(Side.CLIENT)
/*     */   public IResourcePack applyChanges() {
/*  44 */     if (this.target.isFile()) {
/*  45 */       return new IResourcePack()
/*     */         {
/*     */           public String func_130077_b() {
/*  48 */             return "IC2 Profile Pack for " + TextureStyle.this.mod;
/*     */           }
/*     */ 
/*     */           
/*     */           public Set<String> func_110587_b() {
/*  53 */             return Collections.singleton(TextureStyle.this.mod);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean func_110589_b(ResourceLocation location) {
/*  58 */             if (!TextureStyle.this.mod.equals(location.func_110624_b())) return false;
/*     */             
/*  60 */             return (TextureStyle.this.target.asZip().getEntry(location.func_110623_a()) != null);
/*     */           }
/*     */ 
/*     */           
/*     */           public InputStream func_110590_a(ResourceLocation location) throws IOException {
/*  65 */             if (!TextureStyle.this.mod.equals(location.func_110624_b())) return null;
/*     */             
/*  67 */             ZipFile zip = TextureStyle.this.target.asZip();
/*  68 */             ZipEntry entry = zip.getEntry(location.func_110623_a());
/*     */             
/*  70 */             if (entry == null) {
/*  71 */               throw new ResourcePackFileNotFoundException(TextureStyle.this.target.root, location.func_110623_a());
/*     */             }
/*  73 */             return zip.getInputStream(entry);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public <T extends net.minecraft.client.resources.data.IMetadataSection> T func_135058_a(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/*  79 */             return null;
/*     */           }
/*     */ 
/*     */           
/*     */           public BufferedImage func_110586_a() throws IOException {
/*  84 */             throw new IOException();
/*     */           }
/*     */         };
/*     */     }
/*  88 */     return (IResourcePack)new FolderResourcePack(this.target.asFile())
/*     */       {
/*     */         public String func_130077_b() {
/*  91 */           return "IC2 Profile Pack for " + TextureStyle.this.mod;
/*     */         }
/*     */ 
/*     */         
/*     */         public Set<String> func_110587_b() {
/*  96 */           return Collections.singleton(TextureStyle.this.mod);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean func_110589_b(ResourceLocation location) {
/* 101 */           if (!TextureStyle.this.mod.equals(location.func_110624_b())) return false;
/*     */           
/* 103 */           return (findFile(location.func_110623_a()) != null);
/*     */         }
/*     */ 
/*     */         
/*     */         public InputStream func_110590_a(ResourceLocation location) throws IOException {
/* 108 */           if (!TextureStyle.this.mod.equals(location.func_110624_b())) return null;
/*     */           
/* 110 */           File file = findFile(location.func_110623_a());
/* 111 */           if (file == null) {
/* 112 */             throw new ResourcePackFileNotFoundException(this.field_110597_b, location.func_110623_a());
/*     */           }
/* 114 */           return new BufferedInputStream(new FileInputStream(file));
/*     */         }
/*     */ 
/*     */         
/*     */         protected File findFile(String path) {
/*     */           try {
/* 120 */             File file = new File(this.field_110597_b, path);
/*     */             
/* 122 */             if (file.isFile() && func_191384_a(file, path)) return file; 
/* 123 */           } catch (IOException iOException) {}
/*     */ 
/*     */           
/* 126 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public <T extends net.minecraft.client.resources.data.IMetadataSection> T func_135058_a(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
/* 131 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public BufferedImage func_110586_a() throws IOException {
/* 136 */           throw new IOException();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 144 */     if (this == obj) return true; 
/* 145 */     if (!(obj instanceof TextureStyle)) return false;
/*     */     
/* 147 */     TextureStyle other = (TextureStyle)obj;
/* 148 */     return (this.mod.equals(other.mod) && this.target.equals(other.target));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 153 */     return Objects.hash(new Object[] { this.mod, this.target.root, this.target.offset });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     return "TextureStyle<" + this.mod + ": " + this.target + '>';
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\profile\TextureStyle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */