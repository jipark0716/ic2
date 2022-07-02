/*    */ package ic2.core.item.tfbp;
/*    */ 
/*    */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockSnow;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Chilling
/*    */   extends TerraformerBase
/*    */ {
/*    */   boolean terraform(World world, BlockPos pos) {
/* 21 */     pos = TileEntityTerra.getFirstBlockFrom(world, pos, 10);
/* 22 */     if (pos == null) return false;
/*    */     
/* 24 */     IBlockState state = world.func_180495_p(pos);
/* 25 */     Block block = state.func_177230_c();
/*    */     
/* 27 */     if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
/* 28 */       world.func_175656_a(pos, Blocks.field_150432_aD.func_176223_P());
/*    */       
/* 30 */       return true;
/* 31 */     }  if (block == Blocks.field_150432_aD) {
/* 32 */       BlockPos below = pos.func_177977_b();
/* 33 */       Block blockBelow = world.func_180495_p(below).func_177230_c();
/*    */       
/* 35 */       if (blockBelow == Blocks.field_150355_j || blockBelow == Blocks.field_150358_i) {
/* 36 */         world.func_175656_a(below, Blocks.field_150432_aD.func_176223_P());
/* 37 */         return true;
/*    */       } 
/* 39 */     } else if (block == Blocks.field_150431_aC) {
/* 40 */       if (isSurroundedBySnow(world, pos)) {
/* 41 */         world.func_175656_a(pos, Blocks.field_150433_aE.func_176223_P());
/* 42 */         return true;
/*    */       } 
/*    */       
/* 45 */       int size = ((Integer)state.func_177229_b((IProperty)BlockSnow.field_176315_a)).intValue();
/* 46 */       if (BlockSnow.field_176315_a.func_177700_c().contains(Integer.valueOf(size + 1))) {
/* 47 */         world.func_175656_a(pos, state.func_177226_a((IProperty)BlockSnow.field_176315_a, Integer.valueOf(size + 1)));
/* 48 */         return true;
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 53 */     pos = pos.func_177984_a();
/*    */     
/* 55 */     if (Blocks.field_150431_aC.func_176196_c(world, pos) || block == Blocks.field_150432_aD) {
/* 56 */       world.func_175656_a(pos, Blocks.field_150431_aC.func_176223_P());
/*    */       
/* 58 */       return true;
/*    */     } 
/*    */     
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isSurroundedBySnow(World world, BlockPos pos) {
/* 69 */     for (EnumFacing dir : EnumFacing.field_176754_o) {
/* 70 */       if (!isSnowHere(world, pos.func_177972_a(dir))) return false;
/*    */     
/*    */     } 
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean isSnowHere(World world, BlockPos pos) {
/* 81 */     int prevY = pos.func_177956_o();
/* 82 */     pos = TileEntityTerra.getFirstBlockFrom(world, pos, 16);
/* 83 */     if (pos == null || prevY > pos.func_177956_o()) return false;
/*    */     
/* 85 */     Block block = world.func_180495_p(pos).func_177230_c();
/*    */     
/* 87 */     if (block == Blocks.field_150433_aE || block == Blocks.field_150431_aC) {
/* 88 */       return true;
/*    */     }
/*    */     
/* 91 */     pos = pos.func_177984_a();
/*    */     
/* 93 */     if (Blocks.field_150431_aC.func_176196_c(world, pos) || block == Blocks.field_150432_aD) {
/* 94 */       world.func_175656_a(pos, Blocks.field_150431_aC.func_176223_P());
/*    */     }
/*    */     
/* 97 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Chilling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */