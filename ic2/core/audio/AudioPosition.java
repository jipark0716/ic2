/*    */ package ic2.core.audio;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class AudioPosition {
/*    */   private final WeakReference<World> worldRef;
/*    */   public final float x;
/*    */   
/*    */   public static AudioPosition getFrom(Object obj, PositionSpec positionSpec) {
/* 12 */     if (obj instanceof AudioPosition)
/* 13 */       return (AudioPosition)obj; 
/* 14 */     if (obj instanceof Entity) {
/* 15 */       Entity e = (Entity)obj;
/*    */       
/* 17 */       return new AudioPosition(e.func_130014_f_(), (float)e.field_70165_t, (float)e.field_70163_u, (float)e.field_70161_v);
/* 18 */     }  if (obj instanceof TileEntity) {
/* 19 */       TileEntity te = (TileEntity)obj;
/*    */       
/* 21 */       return new AudioPosition(te.func_145831_w(), te.func_174877_v().func_177958_n() + 0.5F, te.func_174877_v().func_177956_o() + 0.5F, te.func_174877_v().func_177952_p() + 0.5F);
/*    */     } 
/* 23 */     return null;
/*    */   }
/*    */   public final float y; public final float z;
/*    */   
/*    */   public AudioPosition(World world, float x, float y, float z) {
/* 28 */     this.worldRef = new WeakReference<>(world);
/* 29 */     this.x = x;
/* 30 */     this.y = y;
/* 31 */     this.z = z;
/*    */   }
/*    */   
/*    */   public AudioPosition(World world, BlockPos pos) {
/* 35 */     this(world, pos.func_177958_n() + 0.5F, pos.func_177956_o() + 0.5F, pos.func_177952_p() + 0.5F);
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 39 */     return this.worldRef.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\audio\AudioPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */