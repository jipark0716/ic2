/*    */ package ic2.api.energy.event;
/*    */ 
/*    */ import ic2.api.energy.EnergyNet;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import net.minecraftforge.event.world.WorldEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnergyTileEvent
/*    */   extends WorldEvent
/*    */ {
/*    */   public final IEnergyTile tile;
/*    */   
/*    */   public EnergyTileEvent(IEnergyTile tile) {
/* 15 */     super(EnergyNet.instance.getWorld(tile));
/*    */     
/* 17 */     if (getWorld() == null) throw new NullPointerException("world is null");
/*    */     
/* 19 */     this.tile = tile;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\event\EnergyTileEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */