/*     */ package ic2.core.util;
/*     */ 
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.model.ISpecialParticleModel;
/*     */ import ic2.core.model.ModelUtil;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.particle.ParticleBlockDust;
/*     */ import net.minecraft.client.particle.ParticleDigging;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleUtil
/*     */ {
/*     */   public static void showFlames(World world, BlockPos pos, EnumFacing facing) {
/*  27 */     if (world.field_73012_v.nextInt(8) != 0)
/*     */       return; 
/*  29 */     double width = 0.625D;
/*  30 */     double height = 0.625D;
/*  31 */     double depthOffset = 0.02D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     double x = pos.func_177958_n() + (facing.func_82601_c() * 1.04D + 1.0D) / 2.0D;
/*  44 */     double y = pos.func_177956_o() + world.field_73012_v.nextFloat() * 0.625D;
/*  45 */     double z = pos.func_177952_p() + (facing.func_82599_e() * 1.04D + 1.0D) / 2.0D;
/*     */     
/*  47 */     if (facing.func_176740_k() == EnumFacing.Axis.X) {
/*  48 */       z += world.field_73012_v.nextFloat() * 0.625D - 0.3125D;
/*     */     } else {
/*  50 */       x += world.field_73012_v.nextFloat() * 0.625D - 0.3125D;
/*     */     } 
/*     */     
/*  53 */     world.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
/*  54 */     world.func_175688_a(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */   }
/*     */   
/*     */   public static void spawnBlockLandParticles(World world, BlockPos pos, double x, double y, double z, int count, ITeBlock teBlock) {
/*  58 */     Minecraft mc = Minecraft.func_71410_x();
/*  59 */     Random rnd = world.field_73012_v;
/*  60 */     if (mc.field_71441_e != world || mc.field_71439_g == null)
/*     */       return; 
/*  62 */     if (mc.field_71474_y.field_74362_aa > 1 || (mc.field_71474_y.field_74362_aa == 1 && rnd
/*  63 */       .nextInt(3) == 0)) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     if (mc.field_71439_g.func_70092_e(x, y, z) > 1024.0D)
/*     */       return; 
/*  69 */     double speed = 0.15D;
/*  70 */     IBlockState state = TeBlockRegistry.get(teBlock.getIdentifier()).getState(teBlock);
/*     */     
/*  72 */     for (int i = 0; i < count; i++) {
/*  73 */       double mx = rnd.nextGaussian() * 0.15D;
/*  74 */       double my = rnd.nextGaussian() * 0.15D;
/*  75 */       double mz = rnd.nextGaussian() * 0.15D;
/*     */       
/*  77 */       ParticleBlockDust particleBlockDust = newParticleBlockDust(world, x, y, z, mx, my, mz, state);
/*     */       
/*  79 */       ensureTexture(world, pos, (Particle)particleBlockDust.func_174845_l(), state);
/*     */       
/*  81 */       mc.field_71452_i.func_78873_a((Particle)particleBlockDust);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void spawnBlockRunParticles(World world, BlockPos pos, double x, double y, double z, double xSpeed, double zSpeed, ITeBlock block) {
/*  86 */     IBlockState state = TeBlockRegistry.get(block.getIdentifier()).getState(block);
/*  87 */     ParticleDigging particle = newParticleDigging(world, x, y, z, xSpeed, 1.5D, zSpeed, state);
/*     */     
/*  89 */     ensureTexture(world, pos, (Particle)particle.func_174845_l(), state);
/*     */     
/*  91 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)particle);
/*     */   }
/*     */   
/*     */   public static void spawnBlockHitParticles(TileEntityBlock te, EnumFacing side) {
/*  95 */     spawnBlockHitParticles(te, side, false);
/*     */   }
/*     */   
/*     */   public static void spawnBlockHitParticles(TileEntityBlock te, EnumFacing side, boolean checkTexture) {
/*  99 */     World world = te.func_145831_w();
/* 100 */     BlockPos pos = te.func_174877_v();
/*     */ 
/*     */ 
/*     */     
/* 104 */     double offset = 0.1D;
/* 105 */     AxisAlignedBB aabb = te.getVisualBoundingBox();
/* 106 */     double x = pos.func_177958_n() + world.field_73012_v.nextDouble() * (aabb.field_72336_d - aabb.field_72340_a - offset * 2.0D) + offset + aabb.field_72340_a;
/* 107 */     double y = pos.func_177956_o() + world.field_73012_v.nextDouble() * (aabb.field_72337_e - aabb.field_72338_b - offset * 2.0D) + offset + aabb.field_72338_b;
/* 108 */     double z = pos.func_177952_p() + world.field_73012_v.nextDouble() * (aabb.field_72334_f - aabb.field_72339_c - offset * 2.0D) + offset + aabb.field_72339_c;
/*     */     
/* 110 */     switch (side) {
/*     */       case DOWN:
/* 112 */         y = pos.func_177956_o() + aabb.field_72338_b - offset;
/*     */         break;
/*     */       case UP:
/* 115 */         y = pos.func_177956_o() + aabb.field_72337_e + offset;
/*     */         break;
/*     */       case NORTH:
/* 118 */         z = pos.func_177952_p() + aabb.field_72339_c - offset;
/*     */         break;
/*     */       case SOUTH:
/* 121 */         z = pos.func_177952_p() + aabb.field_72334_f + offset;
/*     */         break;
/*     */       case WEST:
/* 124 */         x = pos.func_177958_n() + aabb.field_72340_a - offset;
/*     */         break;
/*     */       case EAST:
/* 127 */         x = pos.func_177958_n() + aabb.field_72336_d + offset;
/*     */         break;
/*     */       default:
/* 130 */         throw new IllegalStateException("invalid facing: " + side);
/*     */     } 
/*     */     
/* 133 */     ParticleDigging particle = newParticleDigging(world, x, y, z, 0.0D, 0.0D, 0.0D, te.getBlockState());
/*     */     
/* 135 */     particle.func_174846_a(pos);
/* 136 */     particle.func_70543_e(0.2F);
/* 137 */     particle.func_70541_f(0.6F);
/* 138 */     if (checkTexture) ensureTexture(world, pos, (Particle)particle, te.getBlockState());
/*     */     
/* 140 */     (Minecraft.func_71410_x()).field_71452_i.func_78873_a((Particle)particle);
/*     */   }
/*     */   
/*     */   public static void spawnBlockBreakParticles(TileEntityBlock te) {
/* 144 */     World world = te.func_145831_w();
/* 145 */     BlockPos pos = te.func_174877_v();
/*     */     
/* 147 */     IBlockState state = te.getBlockState();
/* 148 */     Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */ 
/*     */     
/* 152 */     for (int x = 0; x < 4; x++) {
/* 153 */       for (int y = 0; y < 4; y++) {
/* 154 */         for (int z = 0; z < 4; z++) {
/* 155 */           double xOffset = (x + 0.5D) / 4.0D;
/* 156 */           double yOffset = (y + 0.5D) / 4.0D;
/* 157 */           double zOffset = (z + 0.5D) / 4.0D;
/*     */           
/* 159 */           ParticleDigging particle = newParticleDigging(world, pos.func_177958_n() + xOffset, pos.func_177956_o() + yOffset, pos.func_177952_p() + zOffset, xOffset - 0.5D, yOffset - 0.5D, zOffset - 0.5D, state);
/*     */           
/* 161 */           particle.func_174846_a(pos);
/* 162 */           ensureTexture(world, pos, (Particle)particle, state);
/*     */           
/* 164 */           mc.field_71452_i.func_78873_a((Particle)particle);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ParticleBlockDust newParticleBlockDust(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
/*     */     try {
/* 172 */       return particleBlockDust_ctor.newInstance(new Object[] { world, Double.valueOf(xCoord), Double.valueOf(yCoord), Double.valueOf(zCoord), Double.valueOf(xSpeed), Double.valueOf(ySpeed), Double.valueOf(zSpeed), state });
/* 173 */     } catch (Exception e) {
/* 174 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ParticleDigging newParticleDigging(World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, IBlockState state) {
/*     */     try {
/* 180 */       return particleDigging_ctor.newInstance(new Object[] { world, Double.valueOf(xCoord), Double.valueOf(yCoord), Double.valueOf(zCoord), Double.valueOf(xSpeed), Double.valueOf(ySpeed), Double.valueOf(zSpeed), state });
/* 181 */     } catch (Exception e) {
/* 182 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void ensureTexture(World world, BlockPos pos, Particle particle, IBlockState state) {
/* 187 */     if (pos == null)
/* 188 */       return;  IBakedModel model = ModelUtil.getBlockModel(state);
/*     */     
/* 190 */     if (model instanceof ISpecialParticleModel && ((ISpecialParticleModel)model).needsEnhancing(state)) {
/* 191 */       state = state.func_185899_b((IBlockAccess)world, pos);
/* 192 */       state = state.func_177230_c().getExtendedState(state, (IBlockAccess)world, pos);
/* 193 */       assert state instanceof Ic2BlockState.Ic2BlockStateInstance;
/*     */       
/* 195 */       ((ISpecialParticleModel)model).enhanceParticle(particle, (Ic2BlockState.Ic2BlockStateInstance)state);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<ParticleBlockDust> getParticleBlockDustCtor() {
/*     */     try {
/* 201 */       Constructor<ParticleBlockDust> ret = ParticleBlockDust.class.getDeclaredConstructor(new Class[] { World.class, double.class, double.class, double.class, double.class, double.class, double.class, IBlockState.class });
/* 202 */       ret.setAccessible(true);
/*     */       
/* 204 */       return ret;
/* 205 */     } catch (Exception e) {
/* 206 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Constructor<ParticleDigging> getParticleDiggingCtor() {
/*     */     try {
/* 212 */       Constructor<ParticleDigging> ret = ParticleDigging.class.getDeclaredConstructor(new Class[] { World.class, double.class, double.class, double.class, double.class, double.class, double.class, IBlockState.class });
/* 213 */       ret.setAccessible(true);
/*     */       
/* 215 */       return ret;
/* 216 */     } catch (Exception e) {
/* 217 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/* 221 */   private static final Constructor<ParticleBlockDust> particleBlockDust_ctor = getParticleBlockDustCtor();
/* 222 */   private static final Constructor<ParticleDigging> particleDigging_ctor = getParticleDiggingCtor();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\ParticleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */