/*     */ package ic2.core.gui;
/*     */ 
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.gui.dynamic.TextProvider;
/*     */ import ic2.core.init.Localization;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiElement<T extends GuiElement<T>>
/*     */ {
/*     */   protected static final int hoverColor = -2130706433;
/*     */   
/*     */   protected GuiElement(GuiIC2<?> gui, int x, int y, int width, int height) {
/*  28 */     if (width < 0) throw new IllegalArgumentException("negative width"); 
/*  29 */     if (height < 0) throw new IllegalArgumentException("negative height");
/*     */     
/*  31 */     this.gui = gui;
/*  32 */     this.x = x;
/*  33 */     this.y = y;
/*  34 */     this.width = width;
/*  35 */     this.height = height;
/*     */   }
/*     */   
/*     */   public final boolean isEnabled() {
/*  39 */     return (this.enableHandler == null || this.enableHandler.isEnabled());
/*     */   }
/*     */   
/*     */   public boolean contains(int x, int y) {
/*  43 */     return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public T withEnableHandler(IEnableHandler enableHandler) {
/*  48 */     this.enableHandler = enableHandler;
/*     */     
/*  50 */     return (T)this;
/*     */   }
/*     */   
/*     */   public T withTooltip(String tooltip) {
/*  54 */     return withTooltip(Suppliers.ofInstance(tooltip));
/*     */   }
/*     */ 
/*     */   
/*     */   public T withTooltip(Supplier<String> tooltipProvider) {
/*  59 */     this.tooltipProvider = tooltipProvider;
/*     */     
/*  61 */     return (T)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {}
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {}
/*     */   
/*     */   public void drawForeground(int mouseX, int mouseY) {
/*  70 */     if (contains(mouseX, mouseY) && !suppressTooltip(mouseX, mouseY)) {
/*  71 */       List<String> lines = getToolTip();
/*     */       
/*  73 */       if (this.tooltipProvider != null) {
/*  74 */         String tooltip = (String)this.tooltipProvider.get();
/*     */         
/*  76 */         if (tooltip != null && !tooltip.isEmpty()) {
/*  77 */           addLines(lines, tooltip);
/*     */         }
/*     */       } 
/*     */       
/*  81 */       if (!lines.isEmpty()) {
/*  82 */         this.gui.drawTooltip(mouseX, mouseY, lines);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addLines(List<String> list, String str) {
/*  88 */     int startPos = 0;
/*     */     
/*     */     int pos;
/*  91 */     while ((pos = str.indexOf('\n', startPos)) != -1) {
/*  92 */       list.add(processText(str.substring(startPos, pos)));
/*  93 */       startPos = pos + 1;
/*     */     } 
/*     */     
/*  96 */     if (startPos == 0) {
/*  97 */       list.add(processText(str));
/*     */     } else {
/*  99 */       list.add(processText(str.substring(startPos)));
/*     */     } 
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
/*     */ 
/*     */   
/*     */   public boolean onMouseClick(int mouseX, int mouseY, MouseButton button, boolean onThis) {
/* 114 */     return (onThis && onMouseClick(mouseX, mouseY, button));
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
/*     */   
/*     */   protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
/* 127 */     return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMouseDrag(int mouseX, int mouseY, MouseButton button, long timeFromLastClick, boolean onThis) {
/* 142 */     return (onThis && onMouseDrag(mouseX, mouseY, button, timeFromLastClick));
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
/*     */ 
/*     */   
/*     */   protected boolean onMouseDrag(int mouseX, int mouseY, MouseButton button, long timeFromLastClick) {
/* 156 */     return false;
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
/*     */ 
/*     */   
/*     */   public boolean onMouseRelease(int mouseX, int mouseY, MouseButton button, boolean onThis) {
/* 170 */     return (onThis && onMouseRelease(mouseX, mouseY, button));
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
/*     */   
/*     */   protected boolean onMouseRelease(int mouseX, int mouseY, MouseButton button) {
/* 183 */     return false;
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
/*     */   public void onMouseScroll(int mouseX, int mouseY, ScrollDirection direction) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKeyTyped(char typedChar, int keyCode) {
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean suppressTooltip(int mouseX, int mouseY) {
/* 209 */     return false;
/*     */   }
/*     */   
/*     */   protected List<String> getToolTip() {
/* 213 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String processText(String text) {
/* 219 */     return Localization.translate(text);
/*     */   }
/*     */   
/*     */   protected final IInventory getBase() {
/* 223 */     return (this.gui.getContainer()).base;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final Map<String, TextProvider.ITextProvider> getTokens() {
/* 228 */     Map<String, TextProvider.ITextProvider> ret = new HashMap<>();
/*     */     
/* 230 */     ret.put("name", TextProvider.ofTranslated(getBase().func_70005_c_()));
/*     */     
/* 232 */     return ret;
/*     */   }
/*     */   
/*     */   protected static void bindTexture(ResourceLocation texture) {
/* 236 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(texture);
/*     */   }
/*     */   
/*     */   public static void bindCommonTexture() {
/* 240 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(commonTexture);
/*     */   }
/*     */   
/*     */   protected static void bindBlockTexture() {
/* 244 */     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(TextureMap.field_110575_b);
/*     */   }
/*     */   
/*     */   protected static TextureMap getBlockTextureMap() {
/* 248 */     return Minecraft.func_71410_x().func_147117_R();
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
/*     */   
/*     */   private static final Method hasMethod(Class<?> cls, String name, Class<?>... params) {
/*     */     try {
/* 262 */       return !cls.getDeclaredMethod(name, params).isAnnotationPresent((Class)SkippedMethod.class) ? Method.PRESENT : Method.SKIPPED;
/* 263 */     } catch (NoSuchMethodException e) {
/* 264 */       return Method.MISSING;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Subscriptions getSubscriptions() {
/* 272 */     Class<?> cls = getClass();
/* 273 */     Subscriptions subscriptions = SUBSCRIPTIONS.get(cls);
/*     */     
/* 275 */     if (subscriptions == null) {
/* 276 */       Method tick = Method.MISSING, background = Method.MISSING, mouseClick = Method.MISSING, mouseDrag = Method.MISSING, mouseRelease = Method.MISSING, mouseScroll = Method.MISSING, key = Method.MISSING;
/*     */       
/* 278 */       while (cls != GuiElement.class && (!tick.hasSeen() || !background.hasSeen() || !mouseClick.hasSeen() || !mouseDrag.hasSeen() || !mouseRelease.hasSeen() || !mouseScroll.hasSeen() || !key.hasSeen())) {
/* 279 */         if (!tick.hasSeen()) tick = hasMethod(cls, "tick", new Class[0]); 
/* 280 */         if (!background.hasSeen()) background = hasMethod(cls, "drawBackground", new Class[] { int.class, int.class }); 
/* 281 */         if (!mouseClick.hasSeen()) mouseClick = hasMethod(cls, "onMouseClick", new Class[] { int.class, int.class, MouseButton.class }); 
/* 282 */         if (!mouseClick.hasSeen()) mouseClick = hasMethod(cls, "onMouseClick", new Class[] { int.class, int.class, MouseButton.class, boolean.class }); 
/* 283 */         if (!mouseDrag.hasSeen()) mouseDrag = hasMethod(cls, "onMouseDrag", new Class[] { int.class, int.class, MouseButton.class, long.class }); 
/* 284 */         if (!mouseDrag.hasSeen()) mouseDrag = hasMethod(cls, "onMouseDrag", new Class[] { int.class, int.class, MouseButton.class, long.class, boolean.class }); 
/* 285 */         if (!mouseRelease.hasSeen()) mouseRelease = hasMethod(cls, "onMouseRelease", new Class[] { int.class, int.class, MouseButton.class }); 
/* 286 */         if (!mouseRelease.hasSeen()) mouseRelease = hasMethod(cls, "onMouseRelease", new Class[] { int.class, int.class, MouseButton.class, boolean.class }); 
/* 287 */         if (!mouseScroll.hasSeen()) mouseScroll = hasMethod(cls, "onMouseScroll", new Class[] { int.class, int.class, ScrollDirection.class }); 
/* 288 */         if (!key.hasSeen()) key = hasMethod(cls, "onKeyTyped", new Class[] { char.class, int.class });
/*     */         
/* 290 */         cls = cls.getSuperclass();
/*     */       } 
/*     */       
/* 293 */       subscriptions = new Subscriptions(tick.isPresent(), background.isPresent(), mouseClick.isPresent(), mouseDrag.isPresent(), mouseRelease.isPresent(), mouseScroll.isPresent(), key.isPresent());
/*     */       
/* 295 */       SUBSCRIPTIONS.put(getClass(), subscriptions);
/*     */     } 
/*     */     
/* 298 */     return subscriptions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.METHOD})
/*     */   protected static @interface SkippedMethod {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private enum Method
/*     */   {
/* 313 */     PRESENT, SKIPPED, MISSING;
/*     */     
/*     */     boolean hasSeen() {
/* 316 */       return (this != MISSING);
/*     */     }
/*     */     
/*     */     boolean isPresent() {
/* 320 */       return (this == PRESENT);
/*     */     } }
/*     */   public static final class Subscriptions { public final boolean tick; public final boolean background;
/*     */     public final boolean mouseClick;
/*     */     
/*     */     Subscriptions(boolean tick, boolean background, boolean mouseClick, boolean mouseDrag, boolean mouseRelease, boolean mouseScroll, boolean key) {
/* 326 */       this.tick = tick;
/* 327 */       this.background = background;
/* 328 */       this.mouseClick = mouseClick;
/* 329 */       this.mouseDrag = mouseDrag;
/* 330 */       this.mouseRelease = mouseRelease;
/* 331 */       this.mouseScroll = mouseScroll;
/* 332 */       this.key = key;
/*     */     }
/*     */     public final boolean mouseDrag; public final boolean mouseRelease; public final boolean mouseScroll; public final boolean key;
/*     */     
/*     */     public String toString() {
/* 337 */       return String.format("tick: %s, background: %s, mouseClick: %s, mouseDrag: %s, mouseRelease: %s, mouseScroll: %s, key: %s", new Object[] {
/* 338 */             Boolean.valueOf(this.tick), Boolean.valueOf(this.background), Boolean.valueOf(this.mouseClick), Boolean.valueOf(this.mouseDrag), Boolean.valueOf(this.mouseRelease), Boolean.valueOf(this.mouseScroll), Boolean.valueOf(this.key)
/*     */           });
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 346 */   public static final ResourceLocation commonTexture = new ResourceLocation("ic2", "textures/gui/common.png");
/* 347 */   private static final Map<Class<?>, Subscriptions> SUBSCRIPTIONS = new HashMap<>();
/*     */   protected final GuiIC2<?> gui;
/*     */   protected int x;
/*     */   protected int y;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private IEnableHandler enableHandler;
/*     */   private Supplier<String> tooltipProvider;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\GuiElement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */