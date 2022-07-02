/*    */ package ic2.core.network;
/*    */ 
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.util.LogCategory;
/*    */ 
/*    */ public enum SubPacketType
/*    */ {
/*  8 */   Rpc(true, true),
/*  9 */   TileEntityEvent(true, true),
/* 10 */   ItemEvent(true, true),
/* 11 */   PlayerItemData(true, true),
/* 12 */   ContainerData(true, true),
/* 13 */   ContainerEvent(true, true),
/* 14 */   HandHeldInvData(true, true),
/*    */   
/* 16 */   LargePacket(true, false),
/* 17 */   GuiDisplay(true, false),
/* 18 */   ExplosionEffect(true, false),
/* 19 */   TileEntityBlockComponent(true, false),
/* 20 */   TileEntityBlockLandEffect(true, false),
/* 21 */   TileEntityBlockRunEffect(true, false),
/*    */   
/* 23 */   KeyUpdate(false, true),
/* 24 */   TileEntityData(false, true),
/* 25 */   RequestGUI(false, true); private boolean serverToClient;
/*    */   
/*    */   SubPacketType(boolean serverToClient, boolean clientToServer) {
/* 28 */     this.serverToClient = serverToClient;
/* 29 */     this.clientToServer = clientToServer;
/*    */   }
/*    */   private boolean clientToServer; private static final SubPacketType[] values;
/*    */   public void writeTo(GrowingBuffer out) {
/* 33 */     out.writeByte(getId());
/*    */   }
/*    */   
/*    */   public int getId() {
/* 37 */     return ordinal() + 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SubPacketType read(GrowingBuffer in, boolean simulating) {
/* 44 */     int id = in.readUnsignedByte() - 1;
/*    */     
/* 46 */     if (id < 0 || id >= values.length) {
/* 47 */       IC2.log.warn(LogCategory.Network, "Invalid sub packet type: %d", new Object[] { Integer.valueOf(id) });
/* 48 */       return null;
/*    */     } 
/*    */     
/* 51 */     SubPacketType ret = values[id];
/*    */     
/* 53 */     if ((simulating && !ret.clientToServer) || (!simulating && !ret.serverToClient)) {
/* 54 */       IC2.log.warn(LogCategory.Network, "Invalid sub packet type %s for side %s", new Object[] { ret.name(), simulating ? "server" : "client" });
/* 55 */       return null;
/*    */     } 
/*    */     
/* 58 */     return ret;
/*    */   }
/*    */   static {
/* 61 */     values = values();
/*    */ 
/*    */     
/* 64 */     if (values.length > 255) throw new RuntimeException("too many sub packet types"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\network\SubPacketType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */