/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Irrigation
/*     */   extends TerraformerBase
/*     */ {
/*     */   boolean terraform(World world, BlockPos pos) {
/*  22 */     if (world.field_73012_v.nextInt(48000) == 0) {
/*  23 */       world.func_72912_H().func_76084_b(true);
/*  24 */       return true;
/*     */     } 
/*     */     
/*  27 */     pos = TileEntityTerra.getFirstBlockFrom(world, pos, 10);
/*  28 */     if (pos == null) return false;
/*     */     
/*  30 */     if (TileEntityTerra.switchGround(world, pos, (Block)Blocks.field_150354_m, Blocks.field_150346_d.func_176223_P(), true)) {
/*  31 */       TileEntityTerra.switchGround(world, pos, (Block)Blocks.field_150354_m, Blocks.field_150346_d.func_176223_P(), true);
/*  32 */       return true;
/*     */     } 
/*     */     
/*  35 */     IBlockState state = world.func_180495_p(pos);
/*  36 */     Block block = state.func_177230_c();
/*     */     
/*  38 */     if (block instanceof IGrowable && ((IGrowable)block).func_176473_a(world, pos, state, false)) {
/*  39 */       ((IGrowable)block).func_176474_b(world, world.field_73012_v, pos, state);
/*     */       
/*  41 */       return true;
/*  42 */     }  if (block == Blocks.field_150329_H)
/*  43 */       return (spreadGrass(world, pos.func_177978_c()) || spreadGrass(world, pos.func_177974_f()) || 
/*  44 */         spreadGrass(world, pos.func_177968_d()) || spreadGrass(world, pos.func_177976_e())); 
/*  45 */     if (block == Blocks.field_150364_r || block == Blocks.field_150363_s) {
/*  46 */       BlockPos above = pos.func_177984_a();
/*  47 */       world.func_175656_a(above, state);
/*     */       
/*  49 */       IBlockState leaves = getLeaves(world, pos);
/*     */       
/*  51 */       if (leaves != null) {
/*  52 */         createLeaves(world, above, leaves);
/*     */       }
/*     */       
/*  55 */       return true;
/*  56 */     }  if (block == Blocks.field_150480_ab) {
/*  57 */       world.func_175698_g(pos);
/*     */       
/*  59 */       return true;
/*     */     } 
/*     */     
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   private static IBlockState getLeaves(World world, BlockPos pos) {
/*  66 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/*  67 */       BlockPos cPos = pos.func_177972_a(facing);
/*  68 */       IBlockState state = world.func_180495_p(cPos);
/*     */       
/*  70 */       if (state.func_177230_c().isLeaves(state, (IBlockAccess)world, cPos)) {
/*  71 */         return state;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createLeaves(World world, BlockPos pos, IBlockState state) {
/*  83 */     BlockPos above = pos.func_177984_a();
/*     */     
/*  85 */     if (world.func_175623_d(above)) world.func_175656_a(above, state);
/*     */     
/*  87 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/*  88 */       BlockPos cPos = pos.func_177972_a(facing);
/*     */       
/*  90 */       if (world.func_175623_d(cPos)) world.func_175656_a(cPos, state);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean spreadGrass(World world, BlockPos pos) {
/*  98 */     if (world.field_73012_v.nextBoolean()) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     pos = TileEntityTerra.getFirstBlockFrom(world, pos, 0);
/* 103 */     if (pos == null) return false;
/*     */     
/* 105 */     Block block = world.func_180495_p(pos).func_177230_c();
/*     */     
/* 107 */     if (block == Blocks.field_150346_d) {
/* 108 */       world.func_175656_a(pos, Blocks.field_150349_c.func_176223_P());
/*     */       
/* 110 */       return true;
/* 111 */     }  if (block == Blocks.field_150349_c) {
/* 112 */       world.func_175656_a(pos.func_177984_a(), Blocks.field_150329_H.func_176223_P().func_177226_a((IProperty)BlockTallGrass.field_176497_a, (Comparable)BlockTallGrass.EnumType.GRASS));
/*     */       
/* 114 */       return true;
/*     */     } 
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Irrigation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */