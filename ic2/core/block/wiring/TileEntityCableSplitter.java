/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*    */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.comp.Redstone;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.ref.TeBlock.Delegated;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Delegated(current = TileEntityCableSplitter.class, old = TileEntityClassicCableSplitter.class)
/*    */ public class TileEntityCableSplitter
/*    */   extends TileEntityCable
/*    */ {
/*    */   public final Redstone redstone;
/*    */   
/*    */   public static Class<? extends TileEntityCable> delegate() {
/* 20 */     return IC2.version.isClassic() ? (Class)TileEntityClassicCableSplitter.class : (Class)TileEntityCableSplitter.class;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TileEntityCableSplitter() {
/* 26 */     super(CableType.splitter, 0);
/* 27 */     addComponent((TileEntityComponent)(this.redstone = new Redstone(this)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 32 */     super.updateEntityServer();
/*    */ 
/*    */     
/* 35 */     if (this.redstone.hasRedstoneInput() == this.addedToEnergyNet) {
/* 36 */       if (this.addedToEnergyNet) {
/* 37 */         MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 38 */         this.addedToEnergyNet = false;
/*    */       } else {
/* 40 */         MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 41 */         this.addedToEnergyNet = true;
/*    */       } 
/*    */     }
/*    */     
/* 45 */     setActive(this.addedToEnergyNet);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityCableSplitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */