/*    */ package ic2.core.util;
/*    */ 
/*    */ import net.minecraft.client.particle.Particle;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityIC2FX extends Particle {
/*    */   public EntityIC2FX(World world, double x, double y, double z, int maxAge, double[] velocity, float[] colour) {
/*  8 */     super(world, x, y, z, velocity[0], velocity[1], velocity[2]);
/*    */     
/* 10 */     this.field_70552_h = colour[0];
/* 11 */     this.field_70553_i = colour[1];
/* 12 */     this.field_70551_j = colour[2];
/* 13 */     this.field_70545_g = 0.0F;
/* 14 */     this.field_82339_as = 0.6F;
/* 15 */     this.field_70547_e = maxAge;
/* 16 */     func_187115_a(0.02F, 0.02F);
/* 17 */     this.field_70544_f *= this.field_187136_p.nextFloat() * 0.6F + 0.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_189213_a() {
/* 22 */     this.field_187123_c = this.field_187126_f;
/* 23 */     this.field_187124_d = this.field_187127_g;
/* 24 */     this.field_187125_e = this.field_187128_h;
/*    */     
/* 26 */     func_187110_a(0.0D, 0.019999999552965164D, 0.0D);
/*    */     
/* 28 */     if (this.field_70546_d++ >= this.field_70547_e)
/* 29 */       func_187112_i(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\cor\\util\EntityIC2FX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */