/*    */ package ic2.core.block.reactor.tileentity;
/*    */ 
/*    */ import ic2.api.reactor.IReactor;
/*    */ import ic2.api.reactor.IReactorChamber;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.comp.FluidReactorLookup;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ 
/*    */ public class TileEntityReactorVessel
/*    */   extends TileEntityBlock
/*    */   implements IReactorChamber {
/* 12 */   protected final FluidReactorLookup lookup = (FluidReactorLookup)addComponent((TileEntityComponent)new FluidReactorLookup(this));
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityNuclearReactorElectric getReactorInstance() {
/* 17 */     return this.lookup.getReactor();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isWall() {
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\reactor\tileentity\TileEntityReactorVessel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */