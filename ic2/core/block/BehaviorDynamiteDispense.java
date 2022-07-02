/*    */ package ic2.core.block;
/*    */ 
/*    */ import net.minecraft.dispenser.BehaviorProjectileDispense;
/*    */ import net.minecraft.dispenser.IPosition;
/*    */ import net.minecraft.entity.IProjectile;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BehaviorDynamiteDispense
/*    */   extends BehaviorProjectileDispense {
/*    */   public BehaviorDynamiteDispense(boolean sticky) {
/* 12 */     this.sticky = sticky;
/*    */   }
/*    */   private final boolean sticky;
/*    */   
/*    */   protected IProjectile func_82499_a(World world, IPosition pos, ItemStack stack) {
/* 17 */     if (this.sticky) {
/* 18 */       return new EntityStickyDynamite(world, pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c());
/*    */     }
/* 20 */     return new EntityDynamite(world, pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c());
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BehaviorDynamiteDispense.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */