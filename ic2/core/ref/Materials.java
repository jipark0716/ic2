/*    */ package ic2.core.ref;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ 
/*    */ public enum Materials
/*    */   implements IIdProvider {
/*  7 */   brass(16757760, true),
/*  8 */   bronze(16744448, true),
/*  9 */   copper(16737280, false),
/* 10 */   gold(16776990, true),
/* 11 */   iridium(15658734, false),
/* 12 */   iron(13158600, false),
/* 13 */   lead(9200780, false),
/* 14 */   silver(14474495, true),
/* 15 */   steel(8421504, false),
/* 16 */   tin(14474460, false),
/* 17 */   zinc(16445680, true);
/*    */   private final int color;
/*    */   
/*    */   Materials(int color, boolean shiny) {
/* 21 */     this.color = color;
/* 22 */     this.shiny = shiny;
/*    */   }
/*    */   private final boolean shiny;
/*    */   
/*    */   public String getName() {
/* 27 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 32 */     return ordinal();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getColor() {
/* 37 */     return this.color;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModelName() {
/* 42 */     return this.shiny ? "shiny" : "normal";
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ref\Materials.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */