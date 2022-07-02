/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import ic2.core.util.BiomeUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBush;
/*     */ import net.minecraft.block.BlockMushroom;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Mushroom
/*     */   extends TerraformerBase
/*     */ {
/*     */   boolean terraform(World world, BlockPos pos) {
/*  23 */     pos = TileEntityTerra.getFirstSolidBlockFrom(world, pos, 20);
/*  24 */     if (pos == null) return false;
/*     */ 
/*     */     
/*  27 */     if (growBlockWithDependancy(world, pos, Blocks.field_150420_aW, (Block)Blocks.field_150338_P)) {
/*  28 */       return true;
/*     */     }
/*  30 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean growBlockWithDependancy(World world, BlockPos pos, Block target, Block dependancy) {
/*  34 */     BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
/*     */     
/*  36 */     for (int xm = pos.func_177958_n() - 1; dependancy != null && xm < pos.func_177958_n() + 1; xm++) {
/*  37 */       for (int zm = pos.func_177952_p() - 1; zm < pos.func_177952_p() + 1; zm++) {
/*  38 */         for (int ym = pos.func_177956_o() + 5; ym > pos.func_177956_o() - 2; ym--) {
/*  39 */           cPos.func_181079_c(xm, ym, zm);
/*  40 */           IBlockState state = world.func_180495_p((BlockPos)cPos);
/*  41 */           Block block = state.func_177230_c();
/*     */           
/*  43 */           if (dependancy == Blocks.field_150391_bh) {
/*  44 */             if (block == dependancy || block == Blocks.field_150420_aW || block == Blocks.field_150419_aX)
/*  45 */               break;  if (!block.isAir(state, (IBlockAccess)world, (BlockPos)cPos))
/*     */             {
/*  47 */               if (block == Blocks.field_150346_d || block == Blocks.field_150349_c) {
/*  48 */                 BlockPos dstPos = new BlockPos((Vec3i)cPos);
/*  49 */                 world.func_175656_a(dstPos, dependancy.func_176223_P());
/*  50 */                 BiomeUtil.setBiome(world, dstPos, Biomes.field_76789_p);
/*     */                 
/*  52 */                 return true;
/*     */               } 
/*     */             }
/*  55 */           } else if (dependancy == Blocks.field_150338_P) {
/*  56 */             if (block == Blocks.field_150338_P || block == Blocks.field_150337_Q)
/*  57 */               break;  if (!block.isAir(state, (IBlockAccess)world, (BlockPos)cPos))
/*     */             {
/*  59 */               if (growBlockWithDependancy(world, (BlockPos)cPos, (Block)Blocks.field_150338_P, (Block)Blocks.field_150391_bh)) return true;
/*     */             
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  68 */     if (target == Blocks.field_150338_P) {
/*  69 */       Block base = world.func_180495_p(pos).func_177230_c();
/*     */       
/*  71 */       if (base != Blocks.field_150391_bh) {
/*  72 */         if (base == Blocks.field_150420_aW || base == Blocks.field_150419_aX) {
/*  73 */           world.func_175656_a(pos, Blocks.field_150391_bh.func_176223_P());
/*     */         } else {
/*  75 */           return false;
/*     */         } 
/*     */       }
/*     */       
/*  79 */       BlockPos above = pos.func_177984_a();
/*  80 */       IBlockState state = world.func_180495_p(above);
/*  81 */       Block block = state.func_177230_c();
/*     */       
/*  83 */       if (!block.isAir(state, (IBlockAccess)world, above) && block != Blocks.field_150329_H) return false;
/*     */       
/*  85 */       BlockBush blockBush = world.field_73012_v.nextBoolean() ? Blocks.field_150338_P : Blocks.field_150337_Q;
/*     */       
/*  87 */       world.func_175656_a(above, blockBush.func_176223_P());
/*     */       
/*  89 */       return true;
/*  90 */     }  if (target == Blocks.field_150420_aW) {
/*  91 */       BlockPos above = pos.func_177984_a();
/*  92 */       IBlockState state = world.func_180495_p(above);
/*  93 */       Block base = state.func_177230_c();
/*     */       
/*  95 */       if (base != Blocks.field_150338_P && base != Blocks.field_150337_Q) return false;
/*     */       
/*  97 */       if (((BlockMushroom)base).func_176485_d(world, above, state, world.field_73012_v)) {
/*  98 */         for (int i = pos.func_177958_n() - 1; i < pos.func_177958_n() + 1; i++) {
/*  99 */           for (int zm = pos.func_177952_p() - 1; zm < pos.func_177952_p() + 1; zm++) {
/* 100 */             cPos.func_181079_c(i, above.func_177956_o(), zm);
/* 101 */             Block block = world.func_180495_p((BlockPos)cPos).func_177230_c();
/*     */             
/* 103 */             if (block == Blocks.field_150338_P || block == Blocks.field_150337_Q) {
/* 104 */               world.func_175698_g(new BlockPos((Vec3i)cPos));
/*     */             }
/*     */           } 
/*     */         } 
/* 108 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Mushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */