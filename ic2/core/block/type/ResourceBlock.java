/*    */ package ic2.core.block.type;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import net.minecraft.block.SoundType;
/*    */ 
/*    */ public enum ResourceBlock
/*    */   implements IIdProvider, IExtBlockType, IBlockSound {
/*  9 */   basalt(20.0F, 45.0F, false),
/*    */   
/* 11 */   copper_ore(3.0F, 5.0F, false),
/* 12 */   lead_ore(2.0F, 4.0F, false),
/*    */   
/* 14 */   tin_ore(3.0F, 5.0F, false),
/* 15 */   uranium_ore(4.0F, 6.0F, false),
/* 16 */   bronze_block(5.0F, 10.0F, true),
/* 17 */   copper_block(4.0F, 10.0F, true),
/* 18 */   lead_block(4.0F, 10.0F, true),
/*    */   
/* 20 */   steel_block(8.0F, 10.0F, true),
/*    */   
/* 22 */   tin_block(4.0F, 10.0F, true),
/* 23 */   uranium_block(6.0F, 10.0F, true),
/* 24 */   reinforced_stone(80.0F, 180.0F, false),
/* 25 */   machine(5.0F, 10.0F, true),
/* 26 */   advanced_machine(8.0F, 10.0F, true),
/* 27 */   reactor_vessel(40.0F, 90.0F, false),
/*    */   
/* 29 */   silver_block(4.0F, 10.0F, true);
/*    */   private final float hardness;
/*    */   
/*    */   ResourceBlock(float hardness, float explosionResistance, boolean metal) {
/* 33 */     this.hardness = hardness;
/* 34 */     this.explosionResistance = explosionResistance;
/* 35 */     this.metal = metal;
/*    */   }
/*    */   private final float explosionResistance; private final boolean metal;
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
/*    */   
/*    */   public float getHardness() {
/* 50 */     return this.hardness;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getExplosionResistance() {
/* 55 */     return this.explosionResistance;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundType getSound() {
/* 60 */     return this.metal ? SoundType.field_185852_e : SoundType.field_185851_d;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\type\ResourceBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */