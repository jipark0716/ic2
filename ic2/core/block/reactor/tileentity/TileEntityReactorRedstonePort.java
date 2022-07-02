/*    */ package ic2.core.block.reactor.tileentity;
/*    */ 
/*    */ import ic2.core.block.comp.Redstone;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.profile.NotClassic;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityReactorRedstonePort
/*    */   extends TileEntityReactorVessel
/*    */ {
/* 11 */   public final Redstone redstone = (Redstone)addComponent((TileEntityComponent)new Redstone(this));
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onLoaded() {
/* 16 */     super.onLoaded();
/*    */     
/* 18 */     updateRedstoneLink();
/*    */   }
/*    */   
/*    */   private void updateRedstoneLink() {
/* 22 */     if ((func_145831_w()).field_72995_K)
/*    */       return; 
/* 24 */     TileEntityNuclearReactorElectric reactor = this.lookup.getReactor();
/*    */     
/* 26 */     if (reactor != null)
/* 27 */       this.redstone.linkTo(reactor.redstone); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\tileentity\TileEntityReactorRedstonePort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */