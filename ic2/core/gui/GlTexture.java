/*     */ package ic2.core.gui;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GlTexture
/*     */   implements Closeable
/*     */ {
/*     */   public static void init() {
/*  30 */     IResourceManager manager = Minecraft.func_71410_x().func_110442_L();
/*     */     
/*  32 */     if (manager instanceof IReloadableResourceManager) {
/*  33 */       ((IReloadableResourceManager)manager).func_110542_a(new IResourceManagerReloadListener()
/*     */           {
/*     */             public void func_110549_a(IResourceManager manager) {
/*  36 */               for (GlTexture texture : GlTexture.textures.values()) {
/*  37 */                 if (texture != null) {
/*  38 */                   texture.close();
/*     */                 }
/*     */               } 
/*  41 */               GlTexture.textures.clear();
/*     */             }
/*     */           });
/*     */     } else {
/*  45 */       IC2.log.warn(LogCategory.General, "The resource manager {} is not reloadable.", new Object[] { manager });
/*     */     } 
/*     */   }
/*     */   
/*     */   public static GlTexture get(ResourceLocation identifier) {
/*  50 */     GlTexture ret = textures.get(identifier);
/*  51 */     if (ret != null) return ret;
/*     */     
/*  53 */     return add(identifier, new GlTexture(identifier));
/*     */   }
/*     */   
/*     */   public static GlTexture add(ResourceLocation identifier, GlTexture texture) {
/*     */     try {
/*  58 */       texture.load(Minecraft.func_71410_x().func_110442_L());
/*  59 */     } catch (IOException e) {
/*  60 */       IC2.log.warn(LogCategory.General, "Can't load texture %s", new Object[] { identifier });
/*  61 */       texture.close();
/*  62 */       texture = null;
/*     */     } 
/*     */     
/*  65 */     textures.put(identifier, texture);
/*  66 */     return texture;
/*     */   }
/*     */   
/*     */   public GlTexture(ResourceLocation loc) {
/*  70 */     this.loc = loc;
/*     */   }
/*     */   
/*     */   protected void load(IResourceManager manager) throws IOException {
/*  74 */     IResource resource = manager.func_110536_a(this.loc);
/*     */     
/*  76 */     try (InputStream is = resource.func_110527_b()) {
/*  77 */       load(ImageIO.read(is));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void load(BufferedImage img) {
/*  82 */     this.width = img.getWidth();
/*  83 */     this.height = img.getHeight();
/*     */     
/*  85 */     this.canvasWidth = Integer.highestOneBit((this.width - 1) * 2);
/*  86 */     this.canvasHeight = Integer.highestOneBit((this.height - 1) * 2);
/*     */     
/*  88 */     this.textureId = GlStateManager.func_179146_y();
/*     */     
/*  90 */     IntBuffer buffer = GLAllocation.func_74527_f(this.canvasWidth * this.canvasHeight);
/*  91 */     int[] tmp = new int[this.canvasWidth * this.canvasHeight];
/*     */     
/*  93 */     img.getRGB(0, 0, this.width, this.height, tmp, 0, this.canvasWidth);
/*  94 */     buffer.put(tmp);
/*  95 */     buffer.flip();
/*     */     
/*  97 */     bind();
/*     */     
/*  99 */     GL11.glTexParameteri(3553, 33085, 0);
/* 100 */     GL11.glTexParameterf(3553, 33082, 0.0F);
/* 101 */     GL11.glTexParameterf(3553, 33083, 0.0F);
/* 102 */     GL11.glTexParameteri(3553, 10242, 10496);
/* 103 */     GL11.glTexParameteri(3553, 10243, 10496);
/* 104 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 105 */     GL11.glTexParameteri(3553, 10240, 9728);
/*     */     
/* 107 */     GL11.glTexImage2D(3553, 0, 6408, this.canvasWidth, this.canvasHeight, 0, 32993, 33639, buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 112 */     if (this.textureId == 0)
/*     */       return; 
/* 114 */     GlStateManager.func_179150_h(this.textureId);
/* 115 */     this.textureId = 0;
/*     */   }
/*     */   
/*     */   public void bind() {
/* 119 */     if (this.textureId == 0) throw new IllegalStateException("uninitialized texture");
/*     */     
/* 121 */     GlStateManager.func_179144_i(this.textureId);
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 125 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 129 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getCanvasWidth() {
/* 133 */     return this.canvasWidth;
/*     */   }
/*     */   
/*     */   public int getCanvasHeight() {
/* 137 */     return this.canvasHeight;
/*     */   }
/*     */   
/* 140 */   private static final Map<ResourceLocation, GlTexture> textures = new HashMap<>();
/*     */   private final ResourceLocation loc;
/*     */   protected int textureId;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int canvasWidth;
/*     */   protected int canvasHeight;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\GlTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */