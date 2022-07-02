/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import net.minecraftforge.common.IPlantable;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenRubTree
/*     */   extends WorldGenerator
/*     */ {
/*     */   public static final int maxHeight = 8;
/*     */   
/*     */   private static class TreeManager
/*     */     extends SecurityManager
/*     */   {
/*  33 */     static final TreeManager INSTANCE = new TreeManager();
/*     */     private String caller;
/*     */     
/*     */     void logCaller() {
/*  37 */       this.caller = getClassContext()[2].getName();
/*     */     }
/*     */     
/*     */     String getCallerClass() {
/*  41 */       String ret = this.caller;
/*  42 */       this.caller = null;
/*  43 */       return ret;
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldGenRubTree(boolean notify) {
/*  48 */     super(notify);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_180709_b(World world, Random random, BlockPos pos) {
/*  53 */     BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
/*  54 */     cPos.func_181079_c(pos.func_177958_n() + 8, IC2.getWorldHeight(world) - 1, pos.func_177952_p() + 8);
/*     */     
/*  56 */     while (world.func_175623_d((BlockPos)cPos) && cPos.func_177956_o() > 0) {
/*  57 */       cPos.func_181079_c(cPos.func_177958_n(), cPos.func_177956_o() - 1, cPos.func_177952_p());
/*     */     }
/*     */     
/*  60 */     cPos.func_181079_c(cPos.func_177958_n(), cPos.func_177956_o() + 1, cPos.func_177952_p());
/*     */     
/*  62 */     return grow(world, (BlockPos)cPos, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean grow(World world, BlockPos pos, Random random) {
/*  71 */     if (world == null) {
/*  72 */       IC2.log.warn(LogCategory.General, "RubberTree did not spawn! w=%s.", new Object[] { world });
/*  73 */       return false;
/*     */     } 
/*     */     
/*  76 */     SaplingGrowTreeEvent event = new SaplingGrowTreeEvent(world, random, pos)
/*     */       {
/*     */         public void setResult(Event.Result value) {
/*  79 */           super.setResult(value);
/*     */           
/*  81 */           if (value == Event.Result.DENY) {
/*  82 */             WorldGenRubTree.TreeManager.INSTANCE.logCaller();
/*     */           }
/*     */         }
/*     */       };
/*  86 */     MinecraftForge.TERRAIN_GEN_BUS.post((Event)event);
/*  87 */     if (event.getResult() == Event.Result.DENY) {
/*  88 */       IC2.log.debug(LogCategory.General, "Rubber tree growth cancelled by " + TreeManager.INSTANCE.getCallerClass());
/*  89 */       return false;
/*     */     } 
/*     */     
/*  92 */     Block woodBlock = BlockName.rubber_wood.getInstance();
/*     */     
/*  94 */     IBlockState leaves = BlockName.leaves.getInstance().func_176223_P().func_177226_a((IProperty)Ic2Leaves.typeProperty, Ic2Leaves.LeavesType.rubber);
/*  95 */     int treeholechance = 25;
/*  96 */     int height = getGrowHeight(world, pos);
/*  97 */     if (height < 2) return false;
/*     */ 
/*     */     
/* 100 */     height -= random.nextInt(height / 2 + 1);
/*     */     
/* 102 */     BlockPos.MutableBlockPos tmpPos = new BlockPos.MutableBlockPos();
/*     */     
/* 104 */     for (int cHeight = 0; cHeight < height; cHeight++) {
/* 105 */       BlockPos cPos = pos.func_177981_b(cHeight);
/*     */       
/* 107 */       if (random.nextInt(100) <= treeholechance) {
/* 108 */         treeholechance -= 10;
/*     */         
/* 110 */         func_175903_a(world, cPos, woodBlock.func_176223_P()
/* 111 */             .func_177226_a((IProperty)BlockRubWood.stateProperty, BlockRubWood.RubberWoodState.getWet(EnumFacing.field_176754_o[random.nextInt(4)])));
/*     */       } else {
/* 113 */         func_175903_a(world, cPos, woodBlock.func_176223_P()
/* 114 */             .func_177226_a((IProperty)BlockRubWood.stateProperty, BlockRubWood.RubberWoodState.plain_y));
/*     */       } 
/*     */       
/* 117 */       if (height < 4 || (height < 7 && cHeight > 1) || cHeight > 2)
/*     */       {
/*     */         
/* 120 */         for (int cx = pos.func_177958_n() - 2; cx <= pos.func_177958_n() + 2; cx++) {
/* 121 */           for (int cz = pos.func_177952_p() - 2; cz <= pos.func_177952_p() + 2; cz++) {
/* 122 */             int chance = Math.max(1, cHeight + 4 - height);
/* 123 */             int dx = Math.abs(cx - pos.func_177958_n());
/* 124 */             int dz = Math.abs(cz - pos.func_177952_p());
/*     */ 
/*     */             
/* 127 */             if ((dx <= 1 && dz <= 1) || (dx <= 1 && random
/* 128 */               .nextInt(chance) == 0) || (dz <= 1 && random
/* 129 */               .nextInt(chance) == 0)) {
/* 130 */               tmpPos.func_181079_c(cx, pos.func_177956_o() + cHeight, cz);
/*     */               
/* 132 */               if (world.func_175623_d((BlockPos)tmpPos)) {
/* 133 */                 func_175903_a(world, new BlockPos((Vec3i)tmpPos), leaves);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 141 */     for (int i = 0; i <= height / 4 + random.nextInt(2); i++) {
/* 142 */       tmpPos.func_181079_c(pos.func_177958_n(), pos.func_177956_o() + height + i, pos.func_177952_p());
/*     */ 
/*     */       
/* 145 */       if (world.func_175623_d((BlockPos)tmpPos)) {
/* 146 */         func_175903_a(world, new BlockPos((Vec3i)tmpPos), leaves);
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGrowHeight(World world, BlockPos pos) {
/* 160 */     BlockPos below = pos.func_177977_b();
/* 161 */     IBlockState baseState = world.func_180495_p(below);
/* 162 */     Block baseBlock = baseState.func_177230_c();
/*     */     
/* 164 */     if (baseBlock.isAir(baseState, (IBlockAccess)world, below) || 
/* 165 */       !baseBlock.canSustainPlant(baseState, (IBlockAccess)world, below, EnumFacing.UP, (IPlantable)BlockName.sapling.getInstance()) || (
/* 166 */       !world.func_175623_d(pos.func_177984_a()) && world.func_180495_p(pos.func_177984_a()).func_177230_c() != BlockName.sapling.getInstance()))
/*     */     {
/* 168 */       return 0;
/*     */     }
/*     */     
/* 171 */     int height = 1;
/* 172 */     pos = pos.func_177984_a();
/*     */     
/* 174 */     while (world.func_175623_d(pos) && height < 8) {
/* 175 */       pos = pos.func_177984_a();
/* 176 */       height++;
/*     */     } 
/*     */     
/* 179 */     return height;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\WorldGenRubTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */