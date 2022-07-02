/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.Both;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ @NotClassic
/*    */ public enum DustResourceType implements IIdProvider {
/*  9 */   bronze(0),
/*    */   
/* 11 */   clay(1),
/*    */   
/* 13 */   coal(2),
/*    */   
/* 15 */   coal_fuel(3),
/*    */   
/* 17 */   copper(4),
/*    */   
/* 19 */   diamond(5),
/* 20 */   energium(6),
/* 21 */   gold(7),
/*    */   
/* 23 */   iron(8),
/*    */   
/* 25 */   lapis(9),
/* 26 */   lead(10),
/* 27 */   lithium(11),
/* 28 */   obsidian(12),
/* 29 */   silicon_dioxide(13),
/* 30 */   silver(14),
/*    */   
/* 32 */   stone(15),
/* 33 */   sulfur(16),
/* 34 */   tin(17),
/*    */   
/* 36 */   small_bronze(18),
/* 37 */   small_copper(19),
/* 38 */   small_gold(20),
/* 39 */   small_iron(21),
/*    */   
/* 41 */   small_lapis(22),
/* 42 */   small_lead(23),
/* 43 */   small_lithium(24),
/* 44 */   small_obsidian(25),
/* 45 */   small_silver(26),
/* 46 */   small_sulfur(27),
/* 47 */   small_tin(28),
/* 48 */   tin_hydrated(29),
/* 49 */   netherrack(30),
/* 50 */   ender_pearl(31),
/* 51 */   ender_eye(32),
/* 52 */   milk(33);
/*    */   
/*    */   DustResourceType(int id) {
/* 55 */     this.id = id;
/*    */   }
/*    */   private final int id;
/*    */   
/*    */   public String getName() {
/* 60 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 65 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\DustResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */