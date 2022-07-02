/*    */ package ic2.core;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraft.util.EntityDamageSource;
/*    */ import net.minecraft.world.Explosion;
/*    */ 
/*    */ public class IC2DamageSource extends DamageSource {
/*  8 */   public static IC2DamageSource electricity = new IC2DamageSource("electricity");
/*  9 */   public static IC2DamageSource nuke = (IC2DamageSource)(new IC2DamageSource("nuke")).func_94540_d();
/* 10 */   public static IC2DamageSource radiation = (IC2DamageSource)(new IC2DamageSource("radiation")).func_76348_h();
/*    */   
/*    */   public IC2DamageSource(String s) {
/* 13 */     super(s);
/*    */   }
/*    */   
/*    */   public static DamageSource getNukeSource(Explosion explosion) {
/* 17 */     return (explosion != null && explosion.func_94613_c() != null) ? (new EntityDamageSource("nuke.player", (Entity)explosion.func_94613_c())).func_94540_d() : nuke;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\IC2DamageSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */