/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.Both;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ @NotClassic
/*    */ public enum PlateResourceType implements IIdProvider {
/*  9 */   bronze(0),
/* 10 */   copper(1),
/* 11 */   gold(2),
/* 12 */   iron(3),
/* 13 */   lapis(4),
/* 14 */   lead(5),
/* 15 */   obsidian(6),
/* 16 */   steel(7),
/* 17 */   tin(8),
/* 18 */   dense_bronze(9),
/* 19 */   dense_copper(10),
/*    */   
/* 21 */   dense_gold(11),
/* 22 */   dense_iron(12),
/* 23 */   dense_lapis(13),
/* 24 */   dense_lead(14),
/* 25 */   dense_obsidian(15),
/* 26 */   dense_steel(16),
/* 27 */   dense_tin(17);
/*    */   
/*    */   PlateResourceType(int id) {
/* 30 */     this.id = id;
/*    */   }
/*    */   private final int id;
/*    */   
/*    */   public String getName() {
/* 35 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\PlateResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */