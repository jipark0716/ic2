/*    */ package ic2.core.block.wiring;
/*    */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*    */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import ic2.core.block.comp.Redstone;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.ref.TeBlock.Delegated;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ @Delegated(current = TileEntityCableSplitter.class, old = TileEntityClassicCableSplitter.class)
/*    */ public class TileEntityClassicCableSplitter extends TileEntityClassicCable {
/*    */   public TileEntityClassicCableSplitter() {
/* 14 */     super(CableType.splitter, 0);
/*    */     
/* 16 */     addComponent((TileEntityComponent)(this.redstone = new Redstone(this)));
/*    */   }
/*    */   public final Redstone redstone;
/*    */   
/*    */   protected void updateEntityServer() {
/* 21 */     super.updateEntityServer();
/*    */ 
/*    */     
/* 24 */     if (this.redstone.hasRedstoneInput() == this.addedToEnergyNet) {
/* 25 */       if (this.addedToEnergyNet) {
/* 26 */         MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/* 27 */         this.addedToEnergyNet = false;
/*    */       } else {
/* 29 */         MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/* 30 */         this.addedToEnergyNet = true;
/*    */       } 
/*    */     }
/*    */     
/* 34 */     setActive(this.addedToEnergyNet);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityClassicCableSplitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */