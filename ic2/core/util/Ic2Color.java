/*    */ package ic2.core.util;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Ic2Color
/*    */   implements IIdProvider
/*    */ {
/* 16 */   black(EnumDyeColor.BLACK, "dyeBlack"),
/* 17 */   blue(EnumDyeColor.BLUE, "dyeBlue"),
/* 18 */   brown(EnumDyeColor.BROWN, "dyeBrown"),
/* 19 */   cyan(EnumDyeColor.CYAN, "dyeCyan"),
/* 20 */   gray(EnumDyeColor.GRAY, "dyeGray"),
/* 21 */   green(EnumDyeColor.GREEN, "dyeGreen"),
/* 22 */   light_blue(EnumDyeColor.LIGHT_BLUE, "dyeLightBlue"),
/* 23 */   light_gray(EnumDyeColor.SILVER, "dyeLightGray"),
/* 24 */   lime(EnumDyeColor.LIME, "dyeLime"),
/* 25 */   magenta(EnumDyeColor.MAGENTA, "dyeMagenta"),
/* 26 */   orange(EnumDyeColor.ORANGE, "dyeOrange"),
/* 27 */   pink(EnumDyeColor.PINK, "dyePink"),
/* 28 */   purple(EnumDyeColor.PURPLE, "dyePurple"),
/* 29 */   red(EnumDyeColor.RED, "dyeRed"),
/* 30 */   white(EnumDyeColor.WHITE, "dyeWhite"),
/* 31 */   yellow(EnumDyeColor.YELLOW, "dyeYellow"); public static final Ic2Color[] values; private static final Map<EnumDyeColor, Ic2Color> mcColorMap;
/*    */   
/*    */   Ic2Color(EnumDyeColor mcColor, String oreDictDyeName) {
/* 34 */     this.mcColor = mcColor;
/* 35 */     this.oreDictDyeName = oreDictDyeName;
/*    */   }
/*    */   public final EnumDyeColor mcColor; public final String oreDictDyeName;
/*    */   
/*    */   public String getName() {
/* 40 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 45 */     return ordinal();
/*    */   }
/*    */   
/*    */   public static Ic2Color get(EnumDyeColor mcColor) {
/* 49 */     return mcColorMap.get(mcColor);
/*    */   }
/*    */   static {
/* 52 */     values = values();
/* 53 */     mcColorMap = new EnumMap<>(EnumDyeColor.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     for (Ic2Color color : values)
/* 60 */       mcColorMap.put(color.mcColor, color); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\Ic2Color.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */