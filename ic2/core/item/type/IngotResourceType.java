/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.profile.NotExperimental;
/*    */ 
/*    */ public enum IngotResourceType implements IIdProvider {
/*  8 */   alloy(0),
/*  9 */   bronze(1),
/* 10 */   copper(2),
/* 11 */   lead(3),
/*    */   
/* 13 */   silver(4),
/*    */   
/* 15 */   steel(5),
/*    */   
/* 17 */   tin(6),
/* 18 */   refined_iron(7),
/*    */   
/* 20 */   uranium(8);
/*    */   private final int id;
/*    */   
/*    */   IngotResourceType(int id) {
/* 24 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 34 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\IngotResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */