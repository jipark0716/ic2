/*    */ package ic2.core.energy.grid;
/*    */ 
/*    */ import ic2.api.energy.EnergyNet;
/*    */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*    */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*    */ import ic2.api.info.ILocatable;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.LogCategory;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*    */ 
/*    */ 
/*    */ public class EventHandler
/*    */ {
/*    */   private static boolean initialized;
/*    */   
/*    */   public static void init() {
/* 22 */     if (initialized) throw new IllegalStateException("already initialized"); 
/* 23 */     initialized = true;
/*    */     
/* 25 */     MinecraftForge.EVENT_BUS.register(new EventHandler());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEnergyTileLoad(EnergyTileLoadEvent event) {
/* 32 */     if ((event.getWorld()).field_72995_K) {
/* 33 */       IC2.log.warn(LogCategory.EnergyNet, "EnergyTileLoadEvent: posted for %s client-side, aborting", new Object[] { Util.toString(event.tile, (IBlockAccess)event.getWorld(), EnergyNet.instance.getPos(event.tile)) });
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     if (event.tile instanceof TileEntity) {
/* 38 */       EnergyNet.instance.addTile((TileEntity)event.tile);
/* 39 */     } else if (event.tile instanceof ILocatable) {
/* 40 */       EnergyNet.instance.addTile((ILocatable)event.tile);
/*    */     } else {
/* 42 */       throw new IllegalArgumentException("invalid tile type: " + event.tile);
/*    */     } 
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onEnergyTileUnload(EnergyTileUnloadEvent event) {
/* 48 */     if ((event.getWorld()).field_72995_K) {
/* 49 */       IC2.log.warn(LogCategory.EnergyNet, "EnergyTileUnloadEvent: posted for %s client-side, aborting", new Object[] { Util.toString(event.tile, (IBlockAccess)event.getWorld(), EnergyNet.instance.getPos(event.tile)) });
/*    */       
/*    */       return;
/*    */     } 
/* 53 */     EnergyNet.instance.removeTile(event.tile);
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onWorldTick(TickEvent.WorldTickEvent event) {
/* 58 */     EnergyNetLocal enet = EnergyNetGlobal.getLocal(event.world);
/*    */     
/* 60 */     if (event.phase == TickEvent.Phase.START) {
/* 61 */       enet.onTickStart();
/*    */     } else {
/* 63 */       enet.onTickEnd();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\energy\grid\EventHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */