/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ public enum MiscResourceType implements IIdProvider {
/*  7 */   ashes(0),
/*    */   
/*  9 */   iridium_ore(1),
/* 10 */   iridium_shard(2),
/*    */   
/* 12 */   matter(3),
/* 13 */   resin(4),
/* 14 */   slag(5),
/*    */   
/* 16 */   iodine(6),
/*    */   
/* 18 */   water_sheet(7),
/* 19 */   lava_sheet(8);
/*    */   
/*    */   MiscResourceType(int id) {
/* 22 */     this.id = id;
/*    */   }
/*    */   private final int id;
/*    */   
/*    */   public String getName() {
/* 27 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 32 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\MiscResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */