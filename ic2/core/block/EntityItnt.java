/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.block.state.IIdProvider;
/*    */ import ic2.core.ref.BlockName;
/*    */ import ic2.core.ref.TeBlock;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityItnt extends EntityIC2Explosive {
/*    */   public EntityItnt(World world, double x, double y, double z) {
/* 10 */     super(world, x, y, z, 60, 5.5F, 0.9F, 0.3F, BlockName.te.getBlockState((IIdProvider)TeBlock.itnt), 0);
/*    */   }
/*    */   
/*    */   public EntityItnt(World world) {
/* 14 */     this(world, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\EntityItnt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */