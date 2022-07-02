/*    */ package ic2.core.block;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class EntityStickyDynamite
/*    */   extends EntityDynamite
/*    */ {
/*    */   public EntityStickyDynamite(World world) {
/* 11 */     super(world);
/*    */     
/* 13 */     this.sticky = true;
/*    */   }
/*    */   
/*    */   public EntityStickyDynamite(World world, EntityLivingBase entityliving) {
/* 17 */     super(world, entityliving);
/*    */     
/* 19 */     this.sticky = true;
/*    */   }
/*    */   
/*    */   public EntityStickyDynamite(World world, double x, double y, double z) {
/* 23 */     super(world, x, y, z);
/* 24 */     this.sticky = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\EntityStickyDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */