/*    */ package ic2.core;
/*    */ 
/*    */ import ic2.api.event.ExplosionEvent;
/*    */ import ic2.core.util.Util;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.Explosion;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class PointExplosion
/*    */   extends Explosion
/*    */ {
/*    */   private final World world;
/*    */   private final Entity entity;
/*    */   private final float dropRate;
/*    */   private final int entityDamage;
/*    */   private float explosionSize;
/*    */   
/*    */   public PointExplosion(World world1, Entity entity, EntityLivingBase exploder, double x, double y, double z, float power, float dropRate1, int entityDamage1) {
/* 27 */     super(world1, (Entity)exploder, x, y, z, power, true, true);
/*    */     
/* 29 */     this.world = world1;
/* 30 */     this.entity = entity;
/* 31 */     this.dropRate = dropRate1;
/* 32 */     this.entityDamage = entityDamage1;
/* 33 */     this.explosionSize = power;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void func_77278_a() {
/* 41 */     double explosionX = (getPosition()).field_72450_a;
/* 42 */     double explosionY = (getPosition()).field_72448_b;
/* 43 */     double explosionZ = (getPosition()).field_72449_c;
/* 44 */     ExplosionEvent event = new ExplosionEvent(this.world, this.entity, getPosition(), this.explosionSize, func_94613_c(), 0, 1.0D);
/*    */     
/* 46 */     if (MinecraftForge.EVENT_BUS.post((Event)event)) {
/*    */       return;
/*    */     }
/*    */     
/* 50 */     for (int x = Util.roundToNegInf(explosionX) - 1; x <= Util.roundToNegInf(explosionX) + 1; x++) {
/* 51 */       for (int y = Util.roundToNegInf(explosionY) - 1; y <= Util.roundToNegInf(explosionY) + 1; y++) {
/* 52 */         for (int z = Util.roundToNegInf(explosionZ) - 1; z <= Util.roundToNegInf(explosionZ) + 1; z++) {
/* 53 */           BlockPos pos = new BlockPos(x, y, z);
/* 54 */           IBlockState block = this.world.func_180495_p(pos);
/*    */           
/* 56 */           if (block.func_177230_c().getExplosionResistance(this.world, pos, (Entity)func_94613_c(), this) < this.explosionSize * 10.0F) {
/* 57 */             func_180343_e().add(pos);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     List<Entity> entitiesInRange = this.world.func_72839_b((Entity)func_94613_c(), new AxisAlignedBB(explosionX - 2.0D, explosionY - 2.0D, explosionZ - 2.0D, explosionX + 2.0D, explosionY + 2.0D, explosionZ + 2.0D));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 71 */     for (Entity entity : entitiesInRange) {
/* 72 */       entity.func_70097_a(DamageSource.func_94539_a(this), this.entityDamage);
/*    */     }
/*    */     
/* 75 */     this.explosionSize = 1.0F / this.dropRate;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\PointExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */