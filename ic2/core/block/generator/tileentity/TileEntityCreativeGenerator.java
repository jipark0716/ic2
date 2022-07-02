/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*    */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*    */ import ic2.api.energy.tile.IEnergyAcceptor;
/*    */ import ic2.api.energy.tile.IEnergyTile;
/*    */ import ic2.api.energy.tile.IMultiEnergySource;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityCreativeGenerator
/*    */   extends TileEntityBlock
/*    */   implements IMultiEnergySource
/*    */ {
/*    */   public double getOfferedEnergy() {
/* 28 */     return Double.POSITIVE_INFINITY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawEnergy(double amount) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSourceTier() {
/* 39 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean sendMultipleEnergyPackets() {
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMultipleEnergyPacketAmount() {
/* 54 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onLoaded() {
/* 59 */     super.onLoaded();
/* 60 */     if (!(func_145831_w()).field_72995_K) {
/* 61 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*    */     }
/*    */   }
/*    */   
/*    */   protected void onUnloaded() {
/* 66 */     if (!(func_145831_w()).field_72995_K)
/* 67 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this)); 
/* 68 */     super.onUnloaded();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityCreativeGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */