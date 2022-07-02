/*    */ package ic2.core.block.steam;
/*    */ import ic2.core.block.BlockBase;
/*    */ import ic2.core.ref.BlockName;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockRefractoryBricks extends BlockBase {
/*    */   public BlockRefractoryBricks() {
/* 13 */     super(BlockName.refractory_bricks, Material.field_151576_e);
/*    */     
/* 15 */     func_149711_c(2.0F);
/* 16 */     func_149752_b(10.0F);
/* 17 */     setHarvestLevel("pickaxe", 0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateContainer func_180661_e() {
/* 22 */     return new BlockStateContainer((Block)this, new net.minecraft.block.properties.IProperty[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\steam\BlockRefractoryBricks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */