/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ public enum CropResItemType implements IIdProvider {
/*  7 */   coffee_beans(0),
/*  8 */   coffee_powder(1),
/*  9 */   fertilizer(2),
/* 10 */   grin_powder(3),
/* 11 */   hops(4),
/* 12 */   weed(5),
/*    */   
/* 14 */   milk_wart(6);
/*    */   
/*    */   CropResItemType(int id) {
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


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\CropResItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */