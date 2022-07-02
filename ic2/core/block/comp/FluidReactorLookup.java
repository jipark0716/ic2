/*    */ package ic2.core.block.comp;
/*    */ 
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
/*    */ import ic2.core.util.WorldSearchUtil;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class FluidReactorLookup extends TileEntityComponent {
/*    */   private TileEntityNuclearReactorElectric reactor;
/*    */   
/*    */   public FluidReactorLookup(TileEntityBlock parent) {
/* 14 */     super(parent);
/*    */   }
/*    */   private long lastReactorUpdate;
/*    */   public TileEntityNuclearReactorElectric getReactor() {
/* 18 */     long time = this.parent.func_145831_w().func_82737_E();
/*    */     
/* 20 */     if (time != this.lastReactorUpdate) {
/* 21 */       updateReactor();
/* 22 */       this.lastReactorUpdate = time;
/* 23 */     } else if (this.reactor != null && (this.reactor.func_145837_r() || !this.reactor.isFluidCooled())) {
/* 24 */       this.reactor = null;
/*    */     } 
/*    */     
/* 27 */     return this.reactor;
/*    */   }
/*    */   
/*    */   private void updateReactor() {
/* 31 */     int dist = 2;
/* 32 */     World world = this.parent.func_145831_w();
/* 33 */     BlockPos pos = this.parent.func_174877_v();
/*    */ 
/*    */     
/* 36 */     if (!world.func_175697_a(pos, 2)) {
/* 37 */       this.reactor = null;
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 42 */     if (this.reactor != null && 
/* 43 */       !this.reactor.func_145837_r() && this.reactor
/* 44 */       .isFluidCooled() && this.reactor
/* 45 */       .func_145831_w() == world && world
/* 46 */       .func_175625_s(this.reactor.func_174877_v()) == this.reactor) {
/* 47 */       BlockPos reactorPos = this.reactor.func_174877_v();
/*    */       
/* 49 */       int dx = Math.abs(pos.func_177958_n() - reactorPos.func_177958_n());
/* 50 */       int dy = Math.abs(pos.func_177956_o() - reactorPos.func_177956_o());
/* 51 */       int dz = Math.abs(pos.func_177952_p() - reactorPos.func_177952_p());
/*    */       
/* 53 */       if (dx <= 2 && dy <= 2 && dz <= 2 && (dx == 2 || dy == 2 || dz == 2)) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 60 */     this.reactor = null;
/*    */ 
/*    */     
/* 63 */     WorldSearchUtil.findTileEntities(world, pos, 2, new WorldSearchUtil.ITileEntityResultHandler()
/*    */         {
/*    */           public boolean onMatch(TileEntity te) {
/* 66 */             if (te instanceof TileEntityNuclearReactorElectric) {
/* 67 */               TileEntityNuclearReactorElectric cReactor = (TileEntityNuclearReactorElectric)te;
/*    */               
/* 69 */               if (cReactor.isFluidCooled()) {
/* 70 */                 FluidReactorLookup.this.reactor = cReactor;
/*    */                 
/* 72 */                 return true;
/*    */               } 
/*    */             } 
/*    */             
/* 76 */             return false;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\comp\FluidReactorLookup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */