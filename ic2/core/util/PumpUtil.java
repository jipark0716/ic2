/*     */ package ic2.core.util;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.IFluidBlock;
/*     */ 
/*     */ public class PumpUtil
/*     */ {
/*     */   private static int moveUp(World world, BlockPos.MutableBlockPos pos) {
/*  17 */     pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o() + 1, pos.func_177952_p());
/*     */     
/*  19 */     int newDecay = getFlowDecay(world, (BlockPos)pos);
/*  20 */     if (newDecay >= 0) return newDecay;
/*     */     
/*  22 */     pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p());
/*  23 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  24 */     if (newDecay >= 0) return newDecay;
/*     */     
/*  26 */     pos.func_181079_c(pos.func_177958_n() - 2, pos.func_177956_o(), pos.func_177952_p());
/*  27 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  28 */     if (newDecay >= 0) return newDecay;
/*     */     
/*  30 */     pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
/*  31 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  32 */     if (newDecay >= 0) return newDecay;
/*     */     
/*  34 */     pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 2);
/*  35 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  36 */     if (newDecay >= 0) return newDecay;
/*     */     
/*  38 */     pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o() - 1, pos.func_177952_p() + 1);
/*     */     
/*  40 */     return -1;
/*     */   }
/*     */   
/*     */   private static int moveSideways(World world, BlockPos.MutableBlockPos pos, int decay) {
/*  44 */     pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
/*  45 */     int newDecay = getFlowDecay(world, (BlockPos)pos);
/*  46 */     if (newDecay >= 0 && newDecay < decay) return newDecay;
/*     */     
/*  48 */     pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
/*  49 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  50 */     if (newDecay >= 0 && newDecay < decay) return newDecay;
/*     */     
/*  52 */     pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 2);
/*  53 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  54 */     if (newDecay >= 0 && newDecay < decay) return newDecay;
/*     */     
/*  56 */     pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
/*  57 */     newDecay = getFlowDecay(world, (BlockPos)pos);
/*  58 */     if (newDecay >= 0 && newDecay < decay) return newDecay;
/*     */     
/*  60 */     pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
/*     */     
/*  62 */     return -1;
/*     */   }
/*     */   
/*     */   public static BlockPos searchFluidSource(World world, BlockPos startPos) {
/*  66 */     BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/*  67 */     pos.func_181079_c(startPos.func_177958_n(), startPos.func_177956_o(), startPos.func_177952_p());
/*     */     
/*  69 */     int decay = getFlowDecay(world, (BlockPos)pos);
/*     */     
/*  71 */     for (int i = 0; i < 64; i++) {
/*     */       
/*  73 */       int newDecay = moveUp(world, pos);
/*     */       
/*  75 */       if (newDecay < 0) {
/*     */         
/*  77 */         newDecay = moveSideways(world, pos, decay);
/*     */         
/*  79 */         if (newDecay < 0)
/*     */           break; 
/*     */       } 
/*  82 */       decay = newDecay;
/*     */     } 
/*     */     
/*  85 */     Set<BlockPos> visited = new HashSet<>(64);
/*     */ 
/*     */     
/*  88 */     for (int j = 0; j < 64; j++) {
/*  89 */       visited.add(new BlockPos((Vec3i)pos));
/*     */       
/*  91 */       pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
/*     */       
/*  93 */       if (!visited.contains(pos)) {
/*  94 */         int newDecay = getFlowDecay(world, (BlockPos)pos);
/*     */         
/*  96 */         if (newDecay >= 0) {
/*  97 */           if (newDecay == 0) return (BlockPos)pos;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 102 */       pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
/*     */       
/* 104 */       if (!visited.contains(pos)) {
/* 105 */         int newDecay = getFlowDecay(world, (BlockPos)pos);
/*     */         
/* 107 */         if (newDecay >= 0) {
/* 108 */           if (newDecay == 0) return (BlockPos)pos;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 113 */       pos.func_181079_c(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 2);
/*     */       
/* 115 */       if (!visited.contains(pos)) {
/* 116 */         int newDecay = getFlowDecay(world, (BlockPos)pos);
/*     */         
/* 118 */         if (newDecay >= 0) {
/* 119 */           if (newDecay == 0) return (BlockPos)pos;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 124 */       pos.func_181079_c(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p() + 1);
/*     */       
/* 126 */       if (!visited.contains(pos)) {
/* 127 */         int newDecay = getFlowDecay(world, (BlockPos)pos);
/*     */         
/* 129 */         if (newDecay >= 0) {
/* 130 */           if (newDecay == 0) return (BlockPos)pos;
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/* 135 */       pos.func_181079_c(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p());
/*     */     } 
/*     */ 
/*     */     
/* 139 */     BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
/*     */     
/* 141 */     for (int ix = -2; ix <= 2; ix++) {
/* 142 */       for (int iz = -2; iz <= 2; iz++) {
/* 143 */         cPos.func_181079_c(pos.func_177958_n() + ix, pos.func_177956_o(), pos.func_177952_p() + iz);
/*     */         
/* 145 */         IBlockState state = world.func_180495_p((BlockPos)cPos);
/* 146 */         decay = getFlowDecay(state, world, (BlockPos)cPos);
/*     */         
/* 148 */         if (decay >= 0) {
/*     */           
/* 150 */           if (decay == 0) {
/* 151 */             return (BlockPos)cPos;
/*     */           }
/*     */ 
/*     */           
/* 155 */           if (decay >= 1 && decay < 7 && state.func_177230_c() instanceof BlockLiquid) {
/* 156 */             world.func_175656_a((BlockPos)cPos, state.func_177226_a((IProperty)BlockLiquid.field_176367_b, Integer.valueOf(decay + 1)));
/*     */           } else {
/* 158 */             world.func_175698_g((BlockPos)cPos);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 165 */     return null;
/*     */   }
/*     */   
/*     */   protected static int getFlowDecay(World world, BlockPos pos) {
/* 169 */     IBlockState state = world.func_180495_p(pos);
/*     */     
/* 171 */     return getFlowDecay(state, world, pos);
/*     */   }
/*     */   
/*     */   protected static int getFlowDecay(IBlockState state, World world, BlockPos pos) {
/* 175 */     Block block = state.func_177230_c();
/*     */     
/* 177 */     if (block instanceof IFluidBlock) {
/* 178 */       IFluidBlock fb = (IFluidBlock)block;
/*     */       
/* 180 */       if (fb.canDrain(world, pos)) {
/* 181 */         return 0;
/*     */       }
/* 183 */       float level = Math.abs(fb.getFilledPercentage(world, pos));
/*     */       
/* 185 */       return 7 - Util.limit(Math.round(6.0F * level), 0, 6);
/*     */     } 
/* 187 */     if (block instanceof BlockLiquid) {
/* 188 */       return ((Integer)state.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue();
/*     */     }
/* 190 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isExistInArray(int x, int y, int z, int[][] xyz, int end_i) {
/* 195 */     for (int i = 0; i <= end_i; i++) {
/* 196 */       if (xyz[i][0] == x && xyz[i][1] == y && xyz[i][2] == z) return true; 
/*     */     } 
/* 198 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\PumpUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */