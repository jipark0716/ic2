/*     */ package ic2.core.gui;
/*     */ 
/*     */ import ic2.core.GuiIC2;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Gauge<T extends Gauge<T>>
/*     */   extends GuiElement<T>
/*     */ {
/*     */   protected final GaugeProperties properties;
/*     */   
/*     */   protected Gauge(GuiIC2<?> gui, int x, int y, GaugeProperties properties) {
/*  18 */     super(gui, x + properties.hoverXOffset, y + properties.hoverYOffset, properties.hoverWidth, properties.hoverHeight);
/*     */ 
/*     */     
/*  21 */     this.properties = properties;
/*     */   }
/*     */   
/*     */   protected abstract double getRatio();
/*     */   
/*     */   protected boolean isActive(double ratio) {
/*  27 */     return (ratio > 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/*  32 */     double ratio = getRatio();
/*     */     
/*  34 */     if (ratio <= 0.0D && this.properties.bgWidth <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*  38 */     bindTexture(this.properties.texture);
/*     */     
/*  40 */     double x = (this.x - this.properties.hoverXOffset);
/*  41 */     double y = (this.y - this.properties.hoverYOffset);
/*     */     
/*  43 */     if (this.properties.bgWidth >= 0) {
/*  44 */       boolean active = isActive(ratio);
/*     */       
/*  46 */       this.gui.drawTexturedRect(x + this.properties.bgXOffset, y + this.properties.bgYOffset, this.properties.bgWidth, this.properties.bgHeight, active ? this.properties.uBgActive : this.properties.uBgInactive, active ? this.properties.vBgActive : this.properties.vBgInactive);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  51 */       if (ratio <= 0.0D) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/*  56 */     ratio = Math.min(ratio, 1.0D);
/*     */     
/*  58 */     double u = this.properties.uInner;
/*  59 */     double v = this.properties.vInner;
/*  60 */     double width = this.properties.innerWidth;
/*  61 */     double height = this.properties.innerHeight;
/*  62 */     double size = this.properties.vertical ? height : width;
/*  63 */     double renderSize = ratio * size;
/*     */     
/*  65 */     if (!this.properties.smooth) renderSize = Math.round(renderSize); 
/*  66 */     if (renderSize <= 0.0D)
/*     */       return; 
/*  68 */     if (this.properties.vertical) {
/*  69 */       if (this.properties.reverse) {
/*  70 */         v += height - renderSize;
/*  71 */         y += height - renderSize;
/*     */       } 
/*     */       
/*  74 */       height = renderSize;
/*     */     } else {
/*  76 */       if (this.properties.reverse) {
/*  77 */         u += width - renderSize;
/*  78 */         x += width - renderSize;
/*     */       } 
/*     */       
/*  81 */       width = renderSize;
/*     */     } 
/*     */     
/*  84 */     this.gui.drawTexturedRect(x, y, width, height, u, v);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class GaugePropertyBuilder
/*     */   {
/*     */     private final short uInner;
/*     */     
/*     */     private final short vInner;
/*     */     
/*     */     private final short innerWidth;
/*     */     
/*     */     private final short innerHeight;
/*     */     
/*     */     private short hoverXOffset;
/*     */     
/*     */     private short hoverYOffset;
/*     */     
/*     */     private short hoverWidth;
/*     */     
/*     */     private short hoverHeight;
/*     */     
/*     */     private short bgXOffset;
/*     */     
/*     */     private short bgYOffset;
/*     */     
/*     */     private short bgWidth;
/*     */     
/*     */     private short bgHeight;
/*     */     
/*     */     private short uBgInactive;
/*     */     
/*     */     private short vBgInactive;
/*     */     
/*     */     private short uBgActive;
/*     */     private short vBgActive;
/*     */     private final boolean vertical;
/*     */     private final boolean reverse;
/*     */     private boolean smooth;
/*     */     private ResourceLocation texture;
/*     */     
/*     */     public GaugePropertyBuilder withHoverBorder(int border) {
/*     */       this.hoverXOffset = toShort(-border);
/*     */       this.hoverYOffset = toShort(-border);
/*     */       this.hoverWidth = toShort(this.innerWidth + 2 * border);
/*     */       this.hoverHeight = toShort(this.innerHeight + 2 * border);
/*     */       return this;
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withHover(int hoverXOffset, int hoverYOffset, int hoverWidth, int hoverHeight) {
/*     */       this.hoverXOffset = toShort(hoverXOffset);
/*     */       this.hoverYOffset = toShort(hoverYOffset);
/*     */       this.hoverWidth = toShort(hoverWidth);
/*     */       this.hoverHeight = toShort(hoverHeight);
/*     */       return this;
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withBackground(int uBg, int vBg) {
/*     */       return withBackground(0, 0, this.innerWidth, this.innerHeight, uBg, vBg);
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withBackground(int bgXOffset, int bgYOffset, int bgWidth, int bgHeight, int uBg, int vBg) {
/*     */       return withBackground(bgXOffset, bgYOffset, bgWidth, bgHeight, uBg, vBg, uBg, vBg);
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withBackground(int uBgInactive, int vBgInactive, int uBgActive, int vBgActive) {
/*     */       return withBackground(0, 0, this.innerWidth, this.innerHeight, uBgInactive, vBgInactive, uBgActive, vBgActive);
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withBackground(int bgXOffset, int bgYOffset, int bgWidth, int bgHeight, int uBgInactive, int vBgInactive, int uBgActive, int vBgActive) {
/*     */       this.bgXOffset = toShort(bgXOffset);
/*     */       this.bgYOffset = toShort(bgYOffset);
/*     */       this.bgWidth = toShort(bgWidth);
/*     */       this.bgHeight = toShort(bgHeight);
/*     */       this.uBgInactive = toShort(uBgInactive);
/*     */       this.vBgInactive = toShort(vBgInactive);
/*     */       this.uBgActive = toShort(uBgActive);
/*     */       this.vBgActive = toShort(vBgActive);
/*     */       return this;
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withSmooth(boolean smooth) {
/*     */       this.smooth = smooth;
/*     */       return this;
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder withTexture(ResourceLocation texture) {
/*     */       this.texture = texture;
/*     */       return this;
/*     */     }
/*     */     
/*     */     public Gauge.GaugeProperties build() {
/*     */       return new Gauge.GaugeProperties(this.uInner, this.vInner, this.innerWidth, this.innerHeight, this.hoverXOffset, this.hoverYOffset, this.hoverWidth, this.hoverHeight, this.bgXOffset, this.bgYOffset, this.bgWidth, this.bgHeight, this.uBgInactive, this.vBgInactive, this.uBgActive, this.vBgActive, this.vertical, this.reverse, this.smooth, this.texture);
/*     */     }
/*     */     
/*     */     private static short toShort(int value) {
/*     */       return (short)value;
/*     */     }
/*     */     
/*     */     public enum GaugeOrientation
/*     */     {
/*     */       Up(true, true),
/*     */       Down(true, false),
/*     */       Left(false, true),
/*     */       Right(false, false);
/*     */       final boolean vertical;
/*     */       final boolean reverse;
/*     */       
/*     */       GaugeOrientation(boolean vertical, boolean reverse) {
/*     */         this.vertical = vertical;
/*     */         this.reverse = reverse;
/*     */       }
/*     */     }
/*     */     
/*     */     public GaugePropertyBuilder(int uInner, int vInner, int innerWidth, int innerHeight, GaugeOrientation dir) {
/* 199 */       this.smooth = true;
/* 200 */       this.texture = GuiElement.commonTexture;
/*     */       this.uInner = toShort(uInner);
/*     */       this.vInner = toShort(vInner);
/*     */       this.innerWidth = this.hoverWidth = toShort(innerWidth);
/*     */       this.innerHeight = this.hoverHeight = toShort(innerHeight);
/*     */       this.vertical = dir.vertical;
/*     */       this.reverse = dir.reverse;
/*     */     } } public static class GaugeProperties { public final short uInner; public final short vInner; public final short innerWidth; public final short innerHeight; public final short hoverXOffset; public final short hoverYOffset; public final short hoverWidth; public final short hoverHeight; public final short bgXOffset; public final short bgYOffset;
/*     */     public GaugeProperties(int uInner, int vInner, int innerWidth, int innerHeight, int hoverXOffset, int hoverYOffset, int hoverWidth, int hoverHeight, int bgXOffset, int bgYOffset, int bgWidth, int bgHeight, int uBgInactive, int vBgInactive, int uBgActive, int vBgActive, boolean vertical, boolean reverse, boolean smooth, ResourceLocation texture) {
/* 209 */       this.uInner = (short)uInner;
/* 210 */       this.vInner = (short)vInner;
/* 211 */       this.innerWidth = (short)innerWidth;
/* 212 */       this.innerHeight = (short)innerHeight;
/*     */       
/* 214 */       this.hoverXOffset = (short)hoverXOffset;
/* 215 */       this.hoverYOffset = (short)hoverYOffset;
/* 216 */       this.hoverWidth = (short)hoverWidth;
/* 217 */       this.hoverHeight = (short)hoverHeight;
/*     */       
/* 219 */       this.bgXOffset = (short)bgXOffset;
/* 220 */       this.bgYOffset = (short)bgYOffset;
/* 221 */       this.bgWidth = (short)bgWidth;
/* 222 */       this.bgHeight = (short)bgHeight;
/*     */       
/* 224 */       this.uBgInactive = (short)uBgInactive;
/* 225 */       this.vBgInactive = (short)vBgInactive;
/* 226 */       this.uBgActive = (short)uBgActive;
/* 227 */       this.vBgActive = (short)vBgActive;
/*     */       
/* 229 */       this.vertical = vertical;
/* 230 */       this.reverse = reverse;
/* 231 */       this.smooth = smooth;
/* 232 */       this.texture = texture;
/*     */     }
/*     */ 
/*     */     
/*     */     public final short bgWidth;
/*     */     
/*     */     public final short bgHeight;
/*     */     
/*     */     public final short uBgInactive;
/*     */     public final short vBgInactive;
/*     */     public final short uBgActive;
/*     */     public final short vBgActive;
/*     */     public final boolean vertical;
/*     */     public final boolean reverse;
/*     */     public final boolean smooth;
/*     */     public final ResourceLocation texture; }
/*     */ 
/*     */   
/*     */   public static interface IGaugeStyle
/*     */   {
/*     */     Gauge.GaugeProperties getProperties();
/*     */   }
/*     */   
/*     */   public enum GaugeStyle
/*     */     implements IGaugeStyle
/*     */   {
/* 258 */     Fuel((String)(new Gauge.GaugePropertyBuilder(112, 80, 13, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withHover(0, 0, 14, 14).withBackground(0, 0, 16, 16, 96, 80).build()),
/* 259 */     Bucket((String)(new Gauge.GaugePropertyBuilder(110, 111, 14, 16, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(96, 111).build()),
/* 260 */     ProgressWind((String)(new Gauge.GaugePropertyBuilder(242, 91, 13, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(242, 63, 242, 77).build()),
/* 261 */     ProgressArrow((String)(new Gauge.GaugePropertyBuilder(165, 16, 22, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 160, 0).build()),
/* 262 */     ProgressArrowModern((String)(new Gauge.GaugePropertyBuilder(86, 234, 16, 10, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(0, 0, 16, 10, 70, 234).build()),
/* 263 */     ProgressArrowModernReversed((String)(new Gauge.GaugePropertyBuilder(70, 244, 16, 10, Gauge.GaugePropertyBuilder.GaugeOrientation.Left)).withBackground(0, 0, 16, 10, 86, 244).build()),
/* 264 */     ProgressCrush((String)(new Gauge.GaugePropertyBuilder(165, 52, 21, 11, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, -3, 32, 16, 160, 32).build()),
/* 265 */     ProgressTriangle((String)(new Gauge.GaugePropertyBuilder(165, 80, 22, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 160, 64).build()),
/* 266 */     ProgressDrop((String)(new Gauge.GaugePropertyBuilder(165, 112, 22, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 160, 96).build()),
/* 267 */     ProgressRecycler((String)(new Gauge.GaugePropertyBuilder(133, 80, 18, 15, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-5, 0, 32, 16, 128, 64).build()),
/* 268 */     ProgressMetalFormer((String)(new Gauge.GaugePropertyBuilder(200, 19, 46, 9, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-8, -3, 64, 16, 192, 0).build()),
/* 269 */     ProgressCentrifuge((String)(new Gauge.GaugePropertyBuilder(252, 33, 3, 28, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withBackground(-1, -1, 5, 30, 246, 32).build()),
/* 270 */     HeatCentrifuge((String)(new Gauge.GaugePropertyBuilder(225, 54, 20, 4, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withBackground(-1, -1, 22, 6, 224, 47).build()),
/* 271 */     HeatNuclearReactor((String)(new Gauge.GaugePropertyBuilder(0, 243, 100, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/GUINuclearReactor.png")).build()),
/* 272 */     HeatSteamGenerator((String)(new Gauge.GaugePropertyBuilder(177, 1, 7, 76, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/GUISteamGenerator.png")).build()),
/* 273 */     CalcificationSteamGenerator((String)(new Gauge.GaugePropertyBuilder(187, 1, 7, 58, Gauge.GaugePropertyBuilder.GaugeOrientation.Up)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/GUISteamGenerator.png")).build()),
/* 274 */     ProgressCondenser((String)(new Gauge.GaugePropertyBuilder(1, 185, 82, 7, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/GUICondenser.png")).build()),
/* 275 */     HeatFermenter((String)(new Gauge.GaugePropertyBuilder(177, 10, 40, 3, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/GUIFermenter.png")).build()),
/* 276 */     ProgressFermenter((String)(new Gauge.GaugePropertyBuilder(177, 1, 40, 7, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withHoverBorder(1).withTexture(new ResourceLocation("ic2", "textures/gui/GUIFermenter.png")).build()),
/* 277 */     ProgressOreWasher((String)(new Gauge.GaugePropertyBuilder(177, 118, 18, 18, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/GUIOreWashingPlant.png")).withBackground(-1, -1, 20, 19, 102, 38).build()),
/* 278 */     ProgressBlockCutter((String)(new Gauge.GaugePropertyBuilder(176, 15, 46, 17, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/GUIBlockCutter.png")).withBackground(55, 33).build()),
/* 279 */     ProgressLongArrow((String)(new Gauge.GaugePropertyBuilder(176, 15, 34, 13, Gauge.GaugePropertyBuilder.GaugeOrientation.Right)).withTexture(new ResourceLocation("ic2", "textures/gui/GUI_Canner_Classic.png")).withBackground(74, 36).build());
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
/*     */     
/* 317 */     private static final Map<String, Gauge.IGaugeStyle> map = getMap();
/*     */     private final String name;
/*     */     public final Gauge.GaugeProperties properties;
/*     */     
/*     */     GaugeStyle(Gauge.GaugeProperties properties) {
/*     */       this.name = name().toLowerCase(Locale.ENGLISH);
/*     */       this.properties = properties;
/*     */     }
/*     */     
/*     */     public Gauge.GaugeProperties getProperties() {
/*     */       return this.properties;
/*     */     }
/*     */     
/*     */     public static void addStyle(String name, Gauge.IGaugeStyle style) {
/*     */       assert name != null : "Cannot add null name";
/*     */       assert style != null : "Cannot add null style";
/*     */       if (map.containsKey(name))
/*     */         throw new RuntimeException("Duplicate style name for " + name + '!'); 
/*     */       map.put(name, style);
/*     */     }
/*     */     
/*     */     public static Gauge.IGaugeStyle get(String name) {
/*     */       return map.get(name);
/*     */     }
/*     */     
/*     */     private static Map<String, Gauge.IGaugeStyle> getMap() {
/*     */       GaugeStyle[] values = values();
/*     */       Map<String, Gauge.IGaugeStyle> ret = new HashMap<>(values.length);
/*     */       for (GaugeStyle style : values)
/*     */         ret.put(style.name, style); 
/*     */       return ret;
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\Gauge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */