/*    */ package ic2.core.item.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.Both;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.profile.NotExperimental;
/*    */ 
/*    */ @NotClassic
/*    */ public enum NuclearResourceType implements IIdProvider, IRadioactiveItemType {
/* 10 */   uranium(0, 60, 100),
/*    */   
/* 12 */   uranium_235(1, 150, 100),
/* 13 */   uranium_238(2, 10, 90),
/* 14 */   plutonium(3, 150, 100),
/* 15 */   mox(4, 300, 100),
/* 16 */   small_uranium_235(5, 150, 100),
/* 17 */   small_uranium_238(6, 10, 90),
/* 18 */   small_plutonium(7, 150, 100),
/* 19 */   uranium_pellet(8, 60, 100),
/* 20 */   mox_pellet(9, 300, 100),
/* 21 */   rtg_pellet(10, 2, 90),
/* 22 */   depleted_uranium(11, 10, 100),
/* 23 */   depleted_dual_uranium(12, 10, 100),
/* 24 */   depleted_quad_uranium(13, 10, 100),
/* 25 */   depleted_mox(14, 10, 100),
/* 26 */   depleted_dual_mox(15, 10, 100),
/* 27 */   depleted_quad_mox(16, 10, 100),
/* 28 */   near_depleted_uranium(17, 15, 100),
/*    */   
/* 30 */   re_enriched_uranium(18, 30, 100);
/*    */   private final int id;
/*    */   
/*    */   NuclearResourceType(int id, int radLen, int radAmplifier) {
/* 34 */     this.id = id;
/* 35 */     this.radLen = radLen;
/* 36 */     this.radAmplifier = radAmplifier;
/*    */   }
/*    */   private final int radLen; private final int radAmplifier;
/*    */   
/*    */   public String getName() {
/* 41 */     return name();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 46 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRadiationDuration() {
/* 51 */     return this.radLen;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRadiationAmplifier() {
/* 56 */     return this.radAmplifier;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\type\NuclearResourceType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */