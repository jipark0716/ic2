/*     */ package ic2.core.gui;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.gui.dynamic.TextProvider;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class Text extends GuiElement<Text> {
/*     */   private final TextProvider.ITextProvider textProvider;
/*     */   private final Supplier<Integer> color;
/*     */   private final boolean shadow;
/*     */   private final boolean fixedHoverWidth;
/*     */   
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, String text, int color, boolean shadow) {
/*  18 */     return create(gui, x, y, TextProvider.of(text), color, shadow);
/*     */   }
/*     */   private final boolean fixedHoverHeight; private final int baseX; private final int baseY; private final boolean centerX; private final boolean centerY;
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, TextProvider.ITextProvider textProvider, int color, boolean shadow) {
/*  22 */     return create(gui, x, y, textProvider, color, shadow, false, false);
/*     */   }
/*     */   
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, String text, int color, boolean shadow, boolean centerX, boolean centerY) {
/*  26 */     return create(gui, x, y, TextProvider.of(text), color, shadow, centerX, centerY);
/*     */   }
/*     */   
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, TextProvider.ITextProvider textProvider, int color, boolean shadow, boolean centerX, boolean centerY) {
/*  30 */     return create(gui, x, y, -1, -1, textProvider, color, shadow, centerX, centerY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, int width, int height, TextProvider.ITextProvider textProvider, int color, boolean shadow, boolean centerX, boolean centerY) {
/*  35 */     return create(gui, x, y, width, height, textProvider, color, shadow, 0, 0, centerX, centerY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, int width, int height, TextProvider.ITextProvider textProvider, int color, boolean shadow, int xOffset, int yOffset, boolean centerX, boolean centerY) {
/*  41 */     return create(gui, x, y, width, height, textProvider, Suppliers.ofInstance(Integer.valueOf(color)), shadow, xOffset, yOffset, centerX, centerY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Text createRightAligned(GuiIC2<?> gui, int x, int y, int width, int height, TextProvider.ITextProvider textProvider, int color, boolean shadow, int xOffset, int yOffset, boolean centerX, boolean centerY) {
/*  47 */     return create(gui, x, y, width, height, textProvider, Suppliers.ofInstance(Integer.valueOf(color)), shadow, xOffset - 
/*  48 */         getWidth(gui, textProvider), yOffset, centerX, centerY);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Text create(GuiIC2<?> gui, int x, int y, int width, int height, TextProvider.ITextProvider textProvider, Supplier<Integer> color, boolean shadow, int xOffset, int yOffset, boolean centerX, boolean centerY) {
/*     */     boolean fixedHoverWidth, fixedHoverHeight;
/*  68 */     if (width < 0) {
/*  69 */       fixedHoverWidth = false;
/*  70 */       width = getWidth(gui, textProvider);
/*     */     } else {
/*  72 */       fixedHoverWidth = true;
/*     */     } 
/*     */     
/*  75 */     if (height < 0) {
/*  76 */       fixedHoverHeight = false;
/*  77 */       height = 8;
/*     */     } else {
/*  79 */       fixedHoverHeight = true;
/*     */     } 
/*     */     
/*  82 */     int baseX = x + xOffset;
/*  83 */     int baseY = y + yOffset;
/*     */     
/*  85 */     if (centerX) {
/*  86 */       if (fixedHoverWidth) {
/*  87 */         baseX += width / 2;
/*     */       } else {
/*  89 */         x -= width / 2;
/*     */       } 
/*     */     }
/*     */     
/*  93 */     if (centerY) {
/*  94 */       if (fixedHoverHeight) {
/*  95 */         baseY += (height + 1) / 2;
/*     */       } else {
/*  97 */         y -= height / 2;
/*     */       } 
/*     */     }
/*     */     
/* 101 */     return new Text(gui, x, y, width, height, textProvider, color, shadow, fixedHoverWidth, fixedHoverHeight, baseX, baseY, centerX, centerY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Text(GuiIC2<?> gui, int x, int y, int width, int height, TextProvider.ITextProvider textProvider, Supplier<Integer> color, boolean shadow, boolean fixedHoverWidth, boolean fixedHoverHeight, int baseX, int baseY, boolean centerX, boolean centerY) {
/* 107 */     super(gui, x, y, width, height);
/*     */     
/* 109 */     this.textProvider = textProvider;
/* 110 */     this.color = color;
/* 111 */     this.shadow = shadow;
/* 112 */     this.fixedHoverWidth = fixedHoverWidth;
/* 113 */     this.fixedHoverHeight = fixedHoverHeight;
/* 114 */     this.baseX = baseX;
/* 115 */     this.baseY = baseY;
/* 116 */     this.centerX = centerX;
/* 117 */     this.centerY = centerY;
/*     */   }
/*     */   
/*     */   private static int getWidth(GuiIC2<?> gui, TextProvider.ITextProvider textProvider) {
/* 121 */     String text = textProvider.get((gui.getContainer()).base, TextProvider.emptyTokens());
/*     */     
/* 123 */     if (text.isEmpty()) {
/* 124 */       return 0;
/*     */     }
/* 126 */     return (Minecraft.func_71410_x()).field_71466_p.func_78256_a(text);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/*     */     int textWidth, textHeight;
/* 132 */     String text = this.textProvider.get(getBase(), getTokens());
/*     */ 
/*     */ 
/*     */     
/* 136 */     if (text.isEmpty()) {
/* 137 */       textWidth = textHeight = 0;
/*     */     } else {
/* 139 */       textWidth = this.gui.getStringWidth(text);
/* 140 */       textHeight = 8;
/*     */     } 
/*     */     
/* 143 */     int textX = this.baseX;
/* 144 */     if (this.centerX) textX -= textWidth / 2;
/*     */     
/* 146 */     int textY = this.baseY;
/* 147 */     if (this.centerY) textY -= textHeight / 2;
/*     */     
/* 149 */     if (!this.fixedHoverWidth) {
/* 150 */       this.x = textX;
/* 151 */       this.width = textWidth;
/*     */     } 
/*     */     
/* 154 */     if (!this.fixedHoverHeight) {
/* 155 */       this.y = textY;
/* 156 */       this.height = textHeight;
/*     */     } 
/*     */     
/* 159 */     super.drawBackground(mouseX, mouseY);
/*     */     
/* 161 */     if (!text.isEmpty())
/* 162 */       this.gui.drawString(textX, textY, text, ((Integer)this.color.get()).intValue(), this.shadow); 
/*     */   }
/*     */   
/*     */   public enum TextAlignment
/*     */   {
/* 167 */     Start, Center, End;
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
/*     */     public final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     private static final Map<String, TextAlignment> map = getMap();
/*     */     
/*     */     TextAlignment() {
/*     */       this.name = name().toLowerCase(Locale.ENGLISH);
/*     */     }
/*     */     
/*     */     public static TextAlignment get(String name) {
/*     */       return map.get(name);
/*     */     }
/*     */     
/*     */     private static Map<String, TextAlignment> getMap() {
/*     */       TextAlignment[] values = values();
/*     */       Map<String, TextAlignment> ret = new HashMap<>(values.length);
/*     */       for (TextAlignment style : values)
/*     */         ret.put(style.name, style); 
/*     */       return ret;
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\Text.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */