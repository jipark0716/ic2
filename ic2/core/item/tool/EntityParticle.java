/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.core.ExplosionIC2;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.util.Quaternion;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import ic2.core.util.Vector3;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.common.registry.IThrowableEntity;
/*     */ 
/*     */ public class EntityParticle extends Entity implements IThrowableEntity {
/*     */   private double coreSize;
/*     */   private double influenceSize;
/*     */   
/*     */   public EntityParticle(World world) {
/*  31 */     super(world);
/*     */     
/*  33 */     this.field_70145_X = true;
/*     */     
/*  35 */     this.lifeTime = 6000;
/*     */   }
/*     */   private int lifeTime; private Entity owner; private Vector3[] radialTestVectors;
/*     */   public EntityParticle(World world, EntityLivingBase owner1, float speed, double coreSize1, double influenceSize1) {
/*  39 */     this(world);
/*     */     
/*  41 */     this.coreSize = coreSize1;
/*  42 */     this.influenceSize = influenceSize1;
/*  43 */     this.owner = (Entity)owner1;
/*     */     
/*  45 */     Vector3 eyePos = Util.getEyePosition(this.owner);
/*  46 */     func_70107_b(eyePos.x, eyePos.y, eyePos.z);
/*     */     
/*  48 */     Vector3 motion = new Vector3(owner1.func_70040_Z());
/*     */     
/*  50 */     Vector3 ortho = motion.copy().cross(Vector3.UP).scaleTo(influenceSize1);
/*     */     
/*  52 */     double stepAngle = Math.atan(0.5D / influenceSize1) * 2.0D;
/*  53 */     int steps = (int)Math.ceil(6.283185307179586D / stepAngle);
/*  54 */     Quaternion q = (new Quaternion()).setFromAxisAngle(motion, stepAngle);
/*  55 */     this.radialTestVectors = new Vector3[steps];
/*     */     
/*  57 */     this.radialTestVectors[0] = ortho.copy();
/*     */     
/*  59 */     for (int i = 1; i < steps; i++) {
/*  60 */       q.rotate(ortho);
/*     */       
/*  62 */       this.radialTestVectors[i] = ortho.copy();
/*     */     } 
/*     */     
/*  65 */     motion.scale(speed);
/*     */     
/*  67 */     this.field_70159_w = motion.x;
/*  68 */     this.field_70181_x = motion.y;
/*  69 */     this.field_70179_y = motion.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70088_a() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70037_a(NBTTagCompound nbttagcompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_70014_b(NBTTagCompound nbttagcompound) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Entity getThrower() {
/*  89 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThrower(Entity entity) {
/*  94 */     this.owner = entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70071_h_() {
/*  99 */     this.field_70169_q = this.field_70165_t;
/* 100 */     this.field_70167_r = this.field_70163_u;
/* 101 */     this.field_70166_s = this.field_70161_v;
/*     */     
/* 103 */     this.field_70165_t += this.field_70159_w;
/* 104 */     this.field_70163_u += this.field_70181_x;
/* 105 */     this.field_70161_v += this.field_70179_y;
/*     */     
/* 107 */     Vector3 start = new Vector3(this.field_70169_q, this.field_70167_r, this.field_70166_s);
/* 108 */     Vector3 end = new Vector3(this.field_70165_t, this.field_70163_u, this.field_70161_v);
/*     */ 
/*     */ 
/*     */     
/* 112 */     World world = func_130014_f_();
/* 113 */     RayTraceResult hit = world.func_72901_a(start.toVec3(), end.toVec3(), true);
/*     */     
/* 115 */     if (hit != null) {
/* 116 */       end.set(hit.field_72307_f);
/* 117 */       this.field_70165_t = hit.field_72307_f.field_72450_a;
/* 118 */       this.field_70163_u = hit.field_72307_f.field_72448_b;
/* 119 */       this.field_70161_v = hit.field_72307_f.field_72449_c;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 124 */     List<Entity> entitiesToCheck = world.func_72839_b(this, (new AxisAlignedBB(this.field_70169_q, this.field_70167_r, this.field_70166_s, this.field_70165_t, this.field_70163_u, this.field_70161_v)).func_186662_g(this.influenceSize));
/* 125 */     List<RayTraceResult> entitiesInfluences = new ArrayList<>();
/* 126 */     double minDistanceSq = start.distanceSquared(end);
/*     */     
/* 128 */     for (Entity entity : entitiesToCheck) {
/* 129 */       if (entity != this.owner && entity.func_70067_L()) {
/* 130 */         RayTraceResult entityInfluence = entity.func_174813_aQ().func_186662_g(this.influenceSize).func_72327_a(start.toVec3(), end.toVec3());
/*     */         
/* 132 */         if (entityInfluence != null) {
/* 133 */           entitiesInfluences.add(entityInfluence);
/*     */           
/* 135 */           RayTraceResult entityHit = entity.func_174813_aQ().func_186662_g(this.coreSize).func_72327_a(start.toVec3(), end.toVec3());
/*     */           
/* 137 */           if (entityHit != null) {
/* 138 */             double distanceSq = start.distanceSquared(entityHit.field_72307_f);
/*     */             
/* 140 */             if (distanceSq < minDistanceSq) {
/* 141 */               hit = entityHit;
/* 142 */               minDistanceSq = distanceSq;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     double maxInfluenceDistance = Math.sqrt(minDistanceSq) + this.influenceSize;
/*     */     
/* 151 */     for (RayTraceResult entityInfluence : entitiesInfluences) {
/* 152 */       if (start.distance(entityInfluence.field_72307_f) <= maxInfluenceDistance) {
/* 153 */         onInfluence(entityInfluence);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (this.radialTestVectors != null) {
/*     */       
/* 161 */       Vector3 vForward = end.copy().sub(start);
/* 162 */       double len = vForward.length();
/* 163 */       vForward.scale(1.0D / len);
/*     */       
/* 165 */       Vector3 origin = new Vector3(start);
/* 166 */       Vector3 tmp = new Vector3();
/*     */ 
/*     */ 
/*     */       
/* 170 */       for (int d = 0; d < len; d++) {
/* 171 */         for (Vector3 radialTestVector : this.radialTestVectors) {
/* 172 */           origin.copy(tmp).add(radialTestVector);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 179 */           RayTraceResult influence = world.func_72901_a(origin.toVec3(), tmp.toVec3(), true);
/*     */           
/* 181 */           if (influence != null) {
/* 182 */             onInfluence(influence);
/*     */           }
/*     */         } 
/*     */         
/* 186 */         origin.add(vForward);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     if (hit != null) {
/* 191 */       onImpact(hit);
/* 192 */       func_70106_y();
/*     */     } else {
/* 194 */       this.lifeTime--;
/*     */       
/* 196 */       if (this.lifeTime <= 0) func_70106_y(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onImpact(RayTraceResult hit) {
/* 201 */     if (!IC2.platform.isSimulating())
/*     */       return; 
/* 203 */     System.out.println("hit " + hit.field_72313_a + " " + hit.field_72307_f + " sim=" + IC2.platform.isSimulating());
/* 204 */     if (hit.field_72313_a != RayTraceResult.Type.BLOCK || IC2.platform.isSimulating());
/*     */ 
/*     */ 
/*     */     
/* 208 */     ExplosionIC2 explosion = new ExplosionIC2(func_130014_f_(), this.owner, hit.field_72307_f.field_72450_a, hit.field_72307_f.field_72448_b, hit.field_72307_f.field_72449_c, 18.0F, 0.95F, ExplosionIC2.Type.Heat);
/* 209 */     explosion.doExplosion();
/*     */   }
/*     */   
/*     */   protected void onInfluence(RayTraceResult hit) {
/* 213 */     if (!IC2.platform.isSimulating())
/*     */       return; 
/* 215 */     System.out.println("influenced " + hit.field_72313_a + " " + hit.field_72307_f + " sim=" + IC2.platform.isSimulating());
/* 216 */     if (hit.field_72313_a == RayTraceResult.Type.BLOCK && IC2.platform.isSimulating()) {
/* 217 */       World world = func_130014_f_();
/* 218 */       IBlockState state = world.func_180495_p(hit.func_178782_a());
/* 219 */       Block block = state.func_177230_c();
/*     */       
/* 221 */       if (block == Blocks.field_150355_j || block == Blocks.field_150358_i) {
/* 222 */         world.func_175698_g(hit.func_178782_a());
/*     */       } else {
/* 224 */         List<ItemStack> drops = StackUtil.getDrops((IBlockAccess)world, hit.func_178782_a(), state, null, 0, true);
/*     */         
/* 226 */         if (drops.size() == 1 && StackUtil.getSize(drops.get(0)) == 1) {
/* 227 */           ItemStack existing = drops.get(0);
/* 228 */           ItemStack smelted = FurnaceRecipes.func_77602_a().func_151395_a(existing);
/*     */           
/* 230 */           if (smelted != null && smelted.func_77973_b() instanceof ItemBlock) {
/*     */             
/* 232 */             world.func_175656_a(hit.func_178782_a(), ((ItemBlock)smelted.func_77973_b()).func_179223_d().func_176223_P());
/* 233 */           } else if (block.isFlammable((IBlockAccess)world, hit.func_178782_a(), hit.field_178784_b)) {
/* 234 */             world.func_175656_a(hit.func_178782_a().func_177972_a(hit.field_178784_b.func_176734_d()), Blocks.field_150480_ab.func_176223_P());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\EntityParticle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */