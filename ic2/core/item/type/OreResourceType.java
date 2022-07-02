/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ @NotClassic
/*    */ public enum OreResourceType implements IIdProvider {
/*  8 */   copper(0),
/*  9 */   gold(1),
/* 10 */   iron(2),
/* 11 */   lead(3),
/* 12 */   silver(4),
/* 13 */   tin(5),
/* 14 */   uranium(6);
/*    */   
/*    */   OreResourceType(int id) {
/* 17 */     this.id = id;
/*    */   }
/*    */   private final int id;
/*    */   
/*    */   public String getName() {
/* 22 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 27 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\OreResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */