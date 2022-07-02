/*     */ package ic2.core.item;
/*     */ import ic2.core.util.ReflectionUtil;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.item.EntityBoat;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketSteerBoat;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityIC2Boat extends EntityBoat {
/*     */   private static Method method_tickLerp;
/*     */   private static Field field_paddlePositions;
/*     */   private static Field field_previousStatus;
/*     */   private static Field field_status;
/*     */   private static Field field_outOfControlTicks;
/*     */   private static Field field_momentum;
/*     */   private static Field field_lastYd;
/*     */   
/*     */   public static void init() {
/*  39 */     method_tickLerp = getMethod("tickLerp", "func_184447_s", new Class[0]);
/*     */     
/*  41 */     field_paddlePositions = getField("paddlePositions", "field_184470_f");
/*  42 */     field_previousStatus = getField("previousStatus", "field_184471_aG");
/*  43 */     field_status = getField("status", "field_184469_aF");
/*  44 */     field_outOfControlTicks = getField("outOfControlTicks", "field_184474_h");
/*     */     
/*  46 */     field_momentum = getField("momentum", "field_184472_g");
/*  47 */     field_lastYd = getField("lastYd", "field_184473_aH");
/*     */     
/*  49 */     field_waterLevel = getField("waterLevel", "field_184465_aD");
/*  50 */     field_boatGlide = getField("boatGlide", "field_184467_aE");
/*     */     
/*  52 */     field_deltaRotation = getField("deltaRotation", "field_184475_as");
/*  53 */     field_rightInputDown = getField("rightInputDown", "field_184459_aA");
/*  54 */     field_leftInputDown = getField("leftInputDown", "field_184480_az");
/*  55 */     field_forwardInputDown = getField("forwardInputDown", "field_184461_aB");
/*  56 */     field_backInputDown = getField("backInputDown", "field_184463_aC");
/*     */   }
/*     */   private static Field field_waterLevel; private static Field field_boatGlide; private static Field field_deltaRotation; private static Field field_rightInputDown; private static Field field_leftInputDown; private static Field field_forwardInputDown; private static Field field_backInputDown;
/*     */   private static Field getField(String deobfName, String srgName) {
/*  60 */     return ReflectionUtil.getField(EntityBoat.class, new String[] { srgName, deobfName });
/*     */   }
/*     */   
/*     */   private static Method getMethod(String deobfName, String srgName, Class<?>... parameterTypes) {
/*  64 */     return ReflectionUtil.getMethod(EntityBoat.class, new String[] { srgName, deobfName }, parameterTypes);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityIC2Boat(World world) {
/*  69 */     super(world);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  74 */     World world = func_130014_f_();
/*     */     
/*     */     try {
/*  77 */       field_previousStatus.set(this, field_status.get(this));
/*  78 */       EntityBoat.Status status = getBoatStatus();
/*  79 */       field_status.set(this, status);
/*     */       
/*  81 */       if (status != EntityBoat.Status.UNDER_WATER && status != EntityBoat.Status.UNDER_FLOWING_WATER) {
/*  82 */         field_outOfControlTicks.setFloat(this, 0.0F);
/*     */       } else {
/*  84 */         field_outOfControlTicks.setFloat(this, field_outOfControlTicks.getFloat(this) + 1.0F);
/*     */       } 
/*     */       
/*  87 */       if (!world.field_72995_K && field_outOfControlTicks.getFloat(this) >= 60.0F) {
/*  88 */         func_184226_ay();
/*     */       }
/*     */       
/*  91 */       if (func_70268_h() > 0) {
/*  92 */         func_70265_b(func_70268_h() - 1);
/*     */       }
/*     */       
/*  95 */       if (func_70271_g() > 0.0F) {
/*  96 */         func_70266_a(func_70271_g() - 1.0F);
/*     */       }
/*     */       
/*  99 */       this.field_70169_q = this.field_70165_t;
/* 100 */       this.field_70167_r = this.field_70163_u;
/* 101 */       this.field_70166_s = this.field_70161_v;
/* 102 */       doEntityUpdate(world);
/* 103 */       method_tickLerp.invoke(this, new Object[0]);
/*     */       
/* 105 */       if (func_184186_bw()) {
/* 106 */         if (func_184188_bt().isEmpty() || !(func_184188_bt().get(0) instanceof net.minecraft.entity.player.EntityPlayer)) {
/* 107 */           func_184445_a(false, false);
/*     */         }
/*     */         
/* 110 */         updateMotion();
/*     */         
/* 112 */         if (world.field_72995_K) {
/* 113 */           controlBoat();
/* 114 */           world.func_184135_a((Packet)new CPacketSteerBoat(func_184457_a(0), func_184457_a(1)));
/*     */         } 
/*     */         
/* 117 */         func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */       } else {
/* 119 */         this.field_70159_w = 0.0D;
/* 120 */         this.field_70181_x = 0.0D;
/* 121 */         this.field_70179_y = 0.0D;
/*     */       } 
/*     */       
/* 124 */       for (int i = 0; i <= 1; i++) {
/* 125 */         if (func_184457_a(i)) {
/* 126 */           double paddlePosition = Array.getFloat(field_paddlePositions.get(this), i);
/*     */           
/* 128 */           if (!func_174814_R() && paddlePosition % 6.283185307179586D <= 0.7853981633974483D && (paddlePosition + 0.4D) % 6.283185307179586D >= 0.7853981633974483D) {
/* 129 */             SoundEvent soundevent = func_193047_k();
/*     */             
/* 131 */             if (soundevent != null) {
/* 132 */               Vec3d look = func_70676_i(1.0F);
/*     */               
/* 134 */               world.func_184148_a(null, this.field_70165_t + ((i == 1) ? -look.field_72449_c : look.field_72449_c), this.field_70163_u, this.field_70161_v + ((i == 1) ? look.field_72450_a : -look.field_72450_a), soundevent, 
/* 135 */                   func_184176_by(), 1.0F, 0.8F + 0.4F * this.field_70146_Z.nextFloat());
/*     */             } 
/*     */           } 
/*     */           
/* 139 */           Array.setFloat(field_paddlePositions.get(this), i, (float)(paddlePosition + 0.04D));
/*     */         } else {
/* 141 */           Array.setFloat(field_paddlePositions.get(this), i, 0.0F);
/*     */         } 
/*     */       } 
/* 144 */     } catch (Exception e) {
/* 145 */       throw new RuntimeException("Error reflecting boat in update", e);
/*     */     } 
/*     */     
/* 148 */     func_145775_I();
/* 149 */     List<Entity> list = world.func_175674_a((Entity)this, func_174813_aQ().func_72314_b(0.2D, -0.01D, 0.2D), EntitySelectors.func_188442_a((Entity)this));
/*     */     
/* 151 */     if (!list.isEmpty()) {
/* 152 */       boolean flag = (!world.field_72995_K && !(func_184179_bs() instanceof net.minecraft.entity.player.EntityPlayer));
/*     */       
/* 154 */       for (Entity entity : list) {
/* 155 */         if (!entity.func_184196_w((Entity)this)) {
/* 156 */           if (flag && func_184188_bt().size() < 2 && !entity.func_184218_aH() && entity.field_70130_N < this.field_70130_N && entity instanceof net.minecraft.entity.EntityLivingBase && !(entity instanceof net.minecraft.entity.passive.EntityWaterMob) && !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/*     */             
/* 158 */             entity.func_184220_m((Entity)this); continue;
/*     */           } 
/* 160 */           func_70108_f(entity);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doEntityUpdate(World world) {
/* 168 */     if (!world.field_72995_K) {
/* 169 */       func_70052_a(6, func_184202_aL());
/*     */     }
/*     */     
/* 172 */     func_70030_z();
/*     */   }
/*     */   
/*     */   private void updateMotion() {
/* 176 */     double generalHeightChangingValue = func_189652_ae() ? 0.0D : -0.04D;
/* 177 */     double heightChange = 0.0D;
/* 178 */     float momentum = 0.05F;
/*     */     
/*     */     try {
/* 181 */       EntityBoat.Status status = (EntityBoat.Status)field_status.get(this);
/*     */       
/* 183 */       if (field_previousStatus.get(this) == EntityBoat.Status.IN_AIR && status != EntityBoat.Status.IN_AIR && status != EntityBoat.Status.ON_LAND) {
/* 184 */         field_waterLevel.setDouble(this, (func_174813_aQ()).field_72338_b + this.field_70131_O);
/* 185 */         func_70107_b(this.field_70165_t, (func_184451_k() - this.field_70131_O) + 0.101D, this.field_70161_v);
/* 186 */         this.field_70181_x = 0.0D;
/* 187 */         field_lastYd.setDouble(this, 0.0D);
/* 188 */         field_status.set(this, EntityBoat.Status.IN_WATER);
/*     */       } else {
/* 190 */         switch (status) {
/*     */           case IN_AIR:
/* 192 */             momentum = 0.9F;
/*     */             break;
/*     */           case IN_WATER:
/* 195 */             heightChange = (field_waterLevel.getDouble(this) - (func_174813_aQ()).field_72338_b) / this.field_70131_O;
/* 196 */             momentum = 0.9F;
/*     */             break;
/*     */           case ON_LAND:
/* 199 */             momentum = field_boatGlide.getFloat(this);
/* 200 */             if (func_184179_bs() instanceof net.minecraft.entity.player.EntityPlayer) {
/* 201 */               field_boatGlide.setFloat(this, momentum / 2.0F);
/*     */             }
/*     */             break;
/*     */           case UNDER_FLOWING_WATER:
/* 205 */             generalHeightChangingValue = -7.0E-4D;
/* 206 */             momentum = 0.9F;
/*     */             break;
/*     */           case UNDER_WATER:
/* 209 */             heightChange = 0.01D;
/* 210 */             momentum = 0.45F;
/*     */             break;
/*     */         } 
/*     */         
/* 214 */         this.field_70159_w *= momentum;
/* 215 */         this.field_70179_y *= momentum;
/* 216 */         field_deltaRotation.setFloat(this, field_deltaRotation.getFloat(this) * momentum);
/* 217 */         this.field_70181_x += generalHeightChangingValue;
/*     */         
/* 219 */         if (heightChange > 0.0D) {
/* 220 */           this.field_70181_x += heightChange * 0.061538461538461535D;
/* 221 */           this.field_70181_x *= 0.75D;
/*     */         } 
/*     */       } 
/* 224 */       field_momentum.setFloat(this, momentum);
/* 225 */     } catch (Exception e) {
/* 226 */       throw new RuntimeException("Error reflecting boat in updateMotion", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_184451_k() {
/* 232 */     AxisAlignedBB boundingBox = func_174813_aQ();
/*     */     
/* 234 */     int minX = (int)Math.floor(boundingBox.field_72340_a);
/* 235 */     int maxX = (int)Math.ceil(boundingBox.field_72336_d);
/* 236 */     int minZ = (int)Math.floor(boundingBox.field_72339_c);
/* 237 */     int maxZ = (int)Math.ceil(boundingBox.field_72334_f);
/*     */     
/* 239 */     BlockPos.PooledMutableBlockPos blockPosPool = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */     
/*     */     try {
/* 242 */       World world = func_130014_f_();
/* 243 */       int maxY = (int)Math.ceil(boundingBox.field_72337_e - field_lastYd.getDouble(this));
/*     */       
/* 245 */       for (int y = (int)Math.floor(boundingBox.field_72337_e); y < maxY; y++) {
/* 246 */         float waterHeight = 0.0F;
/* 247 */         int x = minX;
/*     */         
/*     */         label29: while (true) {
/* 250 */           if (x >= maxX) {
/* 251 */             if (waterHeight < 1.0F) {
/* 252 */               return blockPosPool.func_177956_o() + waterHeight;
/*     */             }
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 258 */           for (int z = minZ; z < maxZ; z++) {
/* 259 */             blockPosPool.func_181079_c(x, y, z);
/* 260 */             IBlockState block = world.func_180495_p((BlockPos)blockPosPool);
/*     */ 
/*     */             
/* 263 */             if (isWater(block)) {
/* 264 */               waterHeight = Math.max(waterHeight, getBlockLiquidHeight(block, (IBlockAccess)world, (BlockPos)blockPosPool));
/*     */             }
/*     */ 
/*     */             
/* 268 */             if (waterHeight >= 1.0F) {
/*     */               break label29;
/*     */             }
/*     */           } 
/*     */           
/* 273 */           x++;
/*     */         } 
/*     */       } 
/*     */       
/* 277 */       return (maxY + 1);
/* 278 */     } catch (Exception e) {
/* 279 */       throw new RuntimeException("Error reflecting boat in getWaterLevelAbove", e);
/*     */     } finally {
/* 281 */       blockPosPool.func_185344_t();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityBoat.Status getBoatStatus() {
/* 290 */     EntityBoat.Status isUnderWater = getUnderwaterStatus();
/*     */     
/*     */     try {
/* 293 */       if (isUnderWater != null) {
/* 294 */         field_waterLevel.setDouble(this, (func_174813_aQ()).field_72337_e);
/* 295 */         return isUnderWater;
/* 296 */       }  if (checkInWater()) {
/* 297 */         return EntityBoat.Status.IN_WATER;
/*     */       }
/* 299 */       float glideSpeed = func_184441_l();
/*     */       
/* 301 */       if (glideSpeed > 0.0F) {
/* 302 */         field_boatGlide.setFloat(this, glideSpeed);
/* 303 */         return EntityBoat.Status.ON_LAND;
/*     */       } 
/* 305 */       return EntityBoat.Status.IN_AIR;
/*     */     
/*     */     }
/* 308 */     catch (Exception e) {
/* 309 */       throw new RuntimeException("Error reflecting boat in getBoatStatus", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkInWater() {
/*     */     int i;
/* 318 */     World world = func_130014_f_();
/* 319 */     AxisAlignedBB boundingBox = func_174813_aQ();
/* 320 */     boolean isInWater = false;
/* 321 */     BlockPos.PooledMutableBlockPos blockPosPool = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */     
/*     */     try {
/* 324 */       double waterLevel = Double.MIN_VALUE;
/*     */       
/* 326 */       for (int x = (int)Math.floor(boundingBox.field_72340_a); x < Math.ceil(boundingBox.field_72336_d); x++) {
/* 327 */         for (int y = (int)Math.floor(boundingBox.field_72338_b); y < Math.ceil(boundingBox.field_72338_b + 0.001D); y++) {
/* 328 */           for (int z = (int)Math.floor(boundingBox.field_72339_c); z < Math.ceil(boundingBox.field_72334_f); z++) {
/* 329 */             blockPosPool.func_181079_c(x, y, z);
/* 330 */             IBlockState block = world.func_180495_p((BlockPos)blockPosPool);
/*     */ 
/*     */             
/* 333 */             if (isWater(block)) {
/* 334 */               float waterHeight = getLiquidHeight(block, (IBlockAccess)world, (BlockPos)blockPosPool);
/*     */               
/* 336 */               waterLevel = Math.max(waterHeight, waterLevel);
/* 337 */               i = isInWater | ((boundingBox.field_72338_b < waterHeight) ? 1 : 0);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 343 */       field_waterLevel.setDouble(this, waterLevel);
/* 344 */     } catch (Exception e) {
/* 345 */       throw new RuntimeException("Error reflecting boat in checkInWater", e);
/*     */     } finally {
/* 347 */       blockPosPool.func_185344_t();
/*     */     } 
/*     */     
/* 350 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private EntityBoat.Status getUnderwaterStatus() {
/* 359 */     World world = func_130014_f_();
/* 360 */     AxisAlignedBB boundingBox = func_174813_aQ();
/* 361 */     double boatTop = boundingBox.field_72337_e + 0.001D;
/* 362 */     BlockPos.PooledMutableBlockPos blockPosPool = BlockPos.PooledMutableBlockPos.func_185346_s();
/*     */     
/*     */     try {
/* 365 */       for (int x = (int)Math.floor(boundingBox.field_72340_a); x < Math.ceil(boundingBox.field_72336_d); x++) {
/* 366 */         for (int y = (int)Math.floor(boundingBox.field_72337_e); y < Math.ceil(boatTop); y++) {
/* 367 */           for (int z = (int)Math.floor(boundingBox.field_72339_c); z < Math.ceil(boundingBox.field_72334_f); z++) {
/* 368 */             blockPosPool.func_181079_c(x, y, z);
/* 369 */             IBlockState block = world.func_180495_p((BlockPos)blockPosPool);
/*     */ 
/*     */             
/* 372 */             if (isWater(block) && boatTop < getLiquidHeight(block, (IBlockAccess)world, (BlockPos)blockPosPool)) {
/* 373 */               return (((Integer)block.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue() != 0) ? EntityBoat.Status.UNDER_FLOWING_WATER : EntityBoat.Status.UNDER_WATER;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 380 */       blockPosPool.func_185344_t();
/*     */     } 
/*     */     
/* 383 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getLiquidHeight(IBlockState block, IBlockAccess world, BlockPos pos) {
/* 388 */     return pos.func_177956_o() + getBlockLiquidHeight(block, world, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getBlockLiquidHeight(IBlockState block, IBlockAccess world, BlockPos pos) {
/* 393 */     int liquidHeight = ((Integer)block.func_177229_b((IProperty)BlockLiquid.field_176367_b)).intValue();
/* 394 */     return ((liquidHeight & 0x7) == 0 && world.func_180495_p(pos.func_177984_a()).func_185904_a() == block.func_185904_a()) ? 1.0F : (1.0F - BlockLiquid.func_149801_b(liquidHeight));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void controlBoat() {
/* 401 */     if (func_184207_aI()) {
/* 402 */       float speed = 0.0F;
/*     */       
/*     */       try {
/* 405 */         boolean left = field_leftInputDown.getBoolean(this);
/* 406 */         boolean right = field_rightInputDown.getBoolean(this);
/* 407 */         boolean forward = field_forwardInputDown.getBoolean(this);
/* 408 */         boolean backward = field_backInputDown.getBoolean(this);
/* 409 */         if (left) {
/* 410 */           field_deltaRotation.setFloat(this, field_deltaRotation.getFloat(this) - 1.0F);
/*     */         }
/*     */         
/* 413 */         if (right) {
/* 414 */           field_deltaRotation.setFloat(this, field_deltaRotation.getFloat(this) + 1.0F);
/*     */         }
/*     */         
/* 417 */         if (right != left && !forward && !backward) {
/* 418 */           speed += 0.005F;
/*     */         }
/*     */         
/* 421 */         this.field_70177_z += field_deltaRotation.getFloat(this);
/*     */         
/* 423 */         if (forward) {
/* 424 */           speed += 0.04F;
/*     */         }
/*     */         
/* 427 */         if (backward) {
/* 428 */           speed -= 0.005F;
/*     */         }
/*     */ 
/*     */         
/* 432 */         this.field_70159_w += (MathHelper.func_76126_a(-this.field_70177_z * 3.1415927F / 180.0F) * speed) * getAccelerationFactor();
/* 433 */         this.field_70179_y += (MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F) * speed) * getAccelerationFactor();
/*     */         
/* 435 */         func_184445_a(((right && !left) || forward), ((left && !right) || forward));
/* 436 */       } catch (Exception e) {
/* 437 */         throw new RuntimeException("Error reflecting boat in controlBoat", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_184231_a(double y, boolean onGround, IBlockState state, BlockPos pos) {
/* 444 */     boolean expectDeath = (this.field_70143_R > 3.0F && !this.field_70128_L);
/*     */     
/* 446 */     super.func_184231_a(y, onGround, state, pos);
/*     */     
/* 448 */     if (expectDeath && this.field_70128_L && func_130014_f_().func_82736_K().func_82766_b("doEntityDrops")) {
/* 449 */       super.func_70099_a(getBrokenItem(), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityItem func_70099_a(ItemStack stack, float offsetY) {
/* 455 */     if (stack.func_77973_b() == Items.field_151124_az) {
/* 456 */       return super.func_70099_a(getItem(), offsetY);
/*     */     }
/* 458 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getPickedResult(RayTraceResult target) {
/* 464 */     return getItem();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract ItemStack getItem();
/*     */ 
/*     */   
/*     */   protected ItemStack getBrokenItem() {
/* 472 */     return getItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getTexture();
/*     */ 
/*     */   
/*     */   protected double getAccelerationFactor() {
/* 480 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getTopSpeed() {
/* 485 */     return 0.35D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isWater(IBlockState block) {
/* 490 */     return (block.func_185904_a() == Material.field_151586_h);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\EntityIC2Boat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */