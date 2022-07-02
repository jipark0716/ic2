/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ @NotClassic
/*    */ public enum UpdateKitType implements IIdProvider {
/*  8 */   mfsu(0);
/*    */   
/*    */   UpdateKitType(int id) {
/* 11 */     this.id = id;
/*    */   }
/*    */   private final int id;
/*    */   
/*    */   public String getName() {
/* 16 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 21 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\UpdateKitType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */