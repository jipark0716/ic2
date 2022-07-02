/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import ic2.core.ref.BlockName;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Desertification
/*    */   extends TerraformerBase
/*    */ {
/*    */   boolean terraform(World world, BlockPos pos) {
/* 21 */     pos = TileEntityTerra.getFirstBlockFrom(world, pos, 10);
/* 22 */     if (pos == null) return false;
/*    */     
/* 24 */     IBlockState sand = Blocks.field_150354_m.func_176223_P();
/*    */     
/* 26 */     if (TileEntityTerra.switchGround(world, pos, Blocks.field_150346_d, sand, false) || 
/* 27 */       TileEntityTerra.switchGround(world, pos, (Block)Blocks.field_150349_c, sand, false) || 
/* 28 */       TileEntityTerra.switchGround(world, pos, Blocks.field_150458_ak, sand, false)) {
/* 29 */       TileEntityTerra.switchGround(world, pos, Blocks.field_150346_d, sand, false);
/*    */       
/* 31 */       return true;
/*    */     } 
/*    */     
/* 34 */     Block block = world.func_180495_p(pos).func_177230_c();
/*    */     
/* 36 */     if (block == Blocks.field_150355_j || block == Blocks.field_150358_i || block == Blocks.field_150431_aC || block == Blocks.field_150362_t || block == Blocks.field_150361_u || block == BlockName.leaves
/* 37 */       .getInstance() || isPlant(block)) {
/* 38 */       world.func_175698_g(pos);
/* 39 */       if (isPlant(world.func_180495_p(pos.func_177984_a()).func_177230_c()))
/*    */       {
/* 41 */         world.func_175698_g(pos.func_177984_a());
/*    */       }
/*    */       
/* 44 */       return true;
/* 45 */     }  if (block == Blocks.field_150432_aD || block == Blocks.field_150433_aE) {
/* 46 */       world.func_175656_a(pos, Blocks.field_150358_i.func_176223_P());
/*    */       
/* 48 */       return true;
/* 49 */     }  if ((block == Blocks.field_150344_f || block == Blocks.field_150364_r || block == BlockName.rubber_wood.getInstance()) && world.field_73012_v
/* 50 */       .nextInt(15) == 0) {
/* 51 */       world.func_175656_a(pos, Blocks.field_150480_ab.func_176223_P());
/*    */       
/* 53 */       return true;
/*    */     } 
/*    */     
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isPlant(Block block) {
/* 64 */     for (IBlockState state : Cultivation.plants) {
/* 65 */       if (state.func_177230_c() == block) {
/* 66 */         return true;
/*    */       }
/*    */     } 
/* 69 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Desertification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */