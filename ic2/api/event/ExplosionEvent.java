/*    */ package ic2.api.event;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class ExplosionEvent
/*    */   extends WorldEvent {
/*    */   public final Entity entity;
/*    */   public final Vec3d pos;
/*    */   public final double power;
/*    */   
/*    */   public ExplosionEvent(World world, Entity entity, Vec3d pos, double power, EntityLivingBase igniter, int radiationRange, double rangeLimit) {
/* 18 */     super(world);
/*    */     
/* 20 */     this.entity = entity;
/* 21 */     this.pos = pos;
/* 22 */     this.power = power;
/* 23 */     this.igniter = igniter;
/* 24 */     this.radiationRange = radiationRange;
/* 25 */     this.rangeLimit = rangeLimit;
/*    */   }
/*    */   
/*    */   public final EntityLivingBase igniter;
/*    */   public final int radiationRange;
/*    */   public final double rangeLimit;
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\event\ExplosionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */