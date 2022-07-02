/*     */ package ic2.core.block;public class EntityIC2Explosive extends Entity { public DamageSource damageSource; public EntityLivingBase igniter; public int fuse;
/*     */   public float explosivePower;
/*     */   public int radiationRange;
/*     */   public float dropRate;
/*     */   public float damageVsEntitys;
/*     */   public IBlockState renderBlockState;
/*     */   
/*     */   public EntityIC2Explosive(World world, double x, double y, double z, int fuse, float power, float dropRate, float damage, IBlockState renderBlockState, int radiationRange) {
/*     */     this(world);
/*     */     func_70107_b(x, y, z);
/*     */     float f = (float)(Math.random() * 3.1415927410125732D * 2.0D);
/*     */     this.field_70159_w = (-MathHelper.func_76126_a(f * 3.141593F / 180.0F) * 0.02F);
/*     */     this.field_70181_x = 0.20000000298023224D;
/*     */     this.field_70179_y = (-MathHelper.func_76134_b(f * 3.141593F / 180.0F) * 0.02F);
/*     */     this.field_70169_q = x;
/*     */     this.field_70167_r = y;
/*     */     this.field_70166_s = z;
/*     */     this.fuse = fuse;
/*     */     this.explosivePower = power;
/*     */     this.radiationRange = radiationRange;
/*     */     this.dropRate = dropRate;
/*     */     this.damageVsEntitys = damage;
/*     */     this.renderBlockState = renderBlockState;
/*     */   }
/*     */   
/*     */   public EntityIC2Explosive(World world) {
/*  27 */     super(world);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     this.fuse = 80;
/*  70 */     this.explosivePower = 4.0F;
/*  71 */     this.radiationRange = 0;
/*  72 */     this.dropRate = 0.3F;
/*  73 */     this.damageVsEntitys = 1.0F;
/*     */ 
/*     */ 
/*     */     
/*  77 */     this.renderBlockState = Blocks.field_150346_d.func_176223_P();
/*     */     this.field_70156_m = true;
/*     */     func_70105_a(0.98F, 0.98F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_70041_e_() {
/*  86 */     return false;
/*     */   }
/*     */   protected void func_70088_a() {}
/*     */   
/*     */   public boolean func_70067_L() {
/*  91 */     return !this.field_70128_L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  96 */     this.field_70169_q = this.field_70165_t;
/*  97 */     this.field_70167_r = this.field_70163_u;
/*  98 */     this.field_70166_s = this.field_70161_v;
/*  99 */     this.field_70181_x -= 0.04D;
/* 100 */     func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 101 */     this.field_70159_w *= 0.98D;
/* 102 */     this.field_70181_x *= 0.98D;
/* 103 */     this.field_70179_y *= 0.98D;
/*     */     
/* 105 */     if (this.field_70122_E) {
/* 106 */       this.field_70159_w *= 0.7D;
/* 107 */       this.field_70179_y *= 0.7D;
/* 108 */       this.field_70181_x *= -0.5D;
/*     */     } 
/*     */     
/* 111 */     if (this.fuse-- <= 0) {
/* 112 */       func_70106_y();
/*     */       
/* 114 */       if (IC2.platform.isSimulating()) explode(); 
/*     */     } else {
/* 116 */       func_130014_f_().func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void explode() {
/* 121 */     ExplosionIC2 explosion = new ExplosionIC2(func_130014_f_(), this, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.explosivePower, this.dropRate, (this.radiationRange > 0) ? ExplosionIC2.Type.Nuclear : ExplosionIC2.Type.Normal, this.igniter, this.radiationRange);
/*     */     
/* 123 */     explosion.doExplosion();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbttagcompound) {
/* 128 */     nbttagcompound.func_74774_a("Fuse", (byte)this.fuse);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbttagcompound) {
/* 133 */     this.fuse = nbttagcompound.func_74771_c("Fuse");
/*     */   }
/*     */   
/*     */   public EntityIC2Explosive setIgniter(EntityLivingBase igniter1) {
/* 137 */     this.igniter = igniter1;
/* 138 */     return this;
/*     */   } }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\EntityIC2Explosive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */