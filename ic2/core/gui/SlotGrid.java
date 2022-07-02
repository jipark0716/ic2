/*     */ package ic2.core.gui;
/*     */ 
/*     */ import ic2.core.GuiIC2;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class SlotGrid extends GuiElement<SlotGrid> {
/*     */   private final SlotStyle style;
/*     */   
/*     */   public SlotGrid(GuiIC2<?> gui, int x, int y, SlotStyle style) {
/*  15 */     this(gui, x, y, 1, 1, style);
/*     */   }
/*     */   private final int border; private final int spacing;
/*     */   public SlotGrid(GuiIC2<?> gui, int x, int y, int xCount, int yCount, SlotStyle style) {
/*  19 */     this(gui, x, y, xCount, yCount, style, 0, 0);
/*     */   }
/*     */   
/*     */   public SlotGrid(GuiIC2<?> gui, int x, int y, SlotStyle style, int border) {
/*  23 */     this(gui, x, y, 1, 1, style, border, 0);
/*     */   }
/*     */   
/*     */   public SlotGrid(GuiIC2<?> gui, int x, int y, int xCount, int yCount, SlotStyle style, int border, int spacing) {
/*  27 */     super(gui, x - border, y - border, xCount * style.width + 2 * border + (xCount - 1) * spacing, yCount * style.height + 2 * border + (yCount - 1) * spacing);
/*     */ 
/*     */ 
/*     */     
/*  31 */     this.style = style;
/*  32 */     this.border = border;
/*  33 */     this.spacing = spacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBackground(int mouseX, int mouseY) {
/*  38 */     super.drawBackground(mouseX, mouseY);
/*     */     
/*  40 */     if (this.style.background != null) {
/*  41 */       bindTexture(this.style.background);
/*     */       
/*  43 */       int startX = this.x + this.border;
/*  44 */       int startY = this.y + this.border;
/*  45 */       int maxX = this.x + this.width - this.border;
/*  46 */       int maxY = this.y + this.height - this.border;
/*  47 */       int xStep = this.style.width + this.spacing;
/*  48 */       int yStep = this.style.height + this.spacing;
/*     */       int cy;
/*  50 */       for (cy = startY; cy < maxY; cy += yStep) {
/*  51 */         int cx; for (cx = startX; cx < maxX; cx += xStep) {
/*  52 */           this.gui.drawTexturedRect(cx, cy, this.style.width, this.style.height, this.style.u, this.style.v);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean suppressTooltip(int mouseX, int mouseY) {
/*  61 */     if (!StackUtil.isEmpty(this.gui.field_146297_k.field_71439_g.field_71071_by.func_70445_o())) return false;
/*     */     
/*  63 */     Slot slot = this.gui.getSlotUnderMouse();
/*     */     
/*  65 */     return (slot != null && slot.func_75216_d());
/*     */   }
/*     */   
/*     */   public static final class SlotStyle {
/*     */     public SlotStyle(int u, int v, int width, int height) {
/*  70 */       this(u, v, width, height, GuiElement.commonTexture);
/*     */     }
/*     */     
/*     */     public SlotStyle(int width, int height) {
/*  74 */       this(0, 0, width, height, null);
/*     */     }
/*     */     
/*     */     public SlotStyle(int u, int v, int width, int height, ResourceLocation background) {
/*  78 */       this.u = u;
/*  79 */       this.v = v;
/*  80 */       this.width = width;
/*  81 */       this.height = height;
/*  82 */       this.background = background;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static void registerVarient(String name, SlotStyle newSlotStyle) {
/*  92 */       assert name != null && newSlotStyle != null;
/*  93 */       SlotStyle old = map.put(name.toLowerCase(Locale.ENGLISH), newSlotStyle);
/*     */       
/*  95 */       if (old != null) {
/*  96 */         throw new RuntimeException("Duplicate slot instance for name! " + name + " -> " + old + " and " + newSlotStyle);
/*     */       }
/*     */     }
/*     */     
/*     */     public static SlotStyle get(String name) {
/* 101 */       return map.get(name);
/*     */     }
/*     */     
/*     */     private static Map<String, SlotStyle> getMap() {
/* 105 */       Map<String, SlotStyle> ret = new HashMap<>(6, 0.5F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       ret.put("normal", Normal);
/* 114 */       ret.put("large", Large);
/* 115 */       ret.put("plain", Plain);
/*     */       
/* 117 */       return ret;
/*     */     }
/*     */ 
/*     */     
/* 121 */     public static final SlotStyle Normal = new SlotStyle(103, 7, 18, 18);
/* 122 */     public static final SlotStyle Large = new SlotStyle(99, 35, 26, 26);
/* 123 */     public static final SlotStyle Plain = new SlotStyle(16, 16);
/*     */     
/* 125 */     private static final Map<String, SlotStyle> map = getMap();
/*     */     public static final int refSize = 16;
/*     */     public final int u;
/*     */     public final int v;
/*     */     public final int width;
/*     */     public final int height;
/*     */     public final ResourceLocation background;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\SlotGrid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */