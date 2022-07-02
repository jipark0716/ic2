/*     */ package ic2.core;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeChunkManager;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.world.WorldEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ChunkLoaderLogic
/*     */   implements ForgeChunkManager.LoadingCallback
/*     */ {
/*     */   private static ChunkLoaderLogic instance;
/*  32 */   private final Map<World, List<ForgeChunkManager.Ticket>> tickets = new IdentityHashMap<>();
/*     */   
/*     */   ChunkLoaderLogic() {
/*  35 */     if (instance != null) throw new IllegalStateException(); 
/*  36 */     instance = this;
/*  37 */     MinecraftForge.EVENT_BUS.register(this);
/*  38 */     ForgeChunkManager.setForcedChunkLoadingCallback(IC2.getInstance(), this);
/*     */   }
/*     */   
/*     */   private List<ForgeChunkManager.Ticket> getTicketsForWorld(World world) {
/*  42 */     if (world.field_72995_K) return null; 
/*  43 */     if (!this.tickets.containsKey(world)) {
/*  44 */       this.tickets.put(world, new ArrayList<>());
/*     */     }
/*  46 */     return this.tickets.get(world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void unloadWorld(WorldEvent.Unload event) {
/*  55 */     if ((event.getWorld()).field_72995_K)
/*  56 */       return;  if (this.tickets.containsKey(event.getWorld())) {
/*  57 */       this.tickets.remove(event.getWorld());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
/*  63 */     List<ForgeChunkManager.Ticket> worldTickets = getTicketsForWorld(world);
/*  64 */     for (ForgeChunkManager.Ticket ticket : tickets) {
/*  65 */       worldTickets.add(ticket);
/*  66 */       NBTTagList list = ticket.getModData().func_150295_c("loadedChunks", 4);
/*  67 */       for (int i = 0; i < list.func_74745_c(); i++) {
/*  68 */         NBTTagLong value = (NBTTagLong)list.func_179238_g(i);
/*  69 */         ForgeChunkManager.forceChunk(ticket, deserialize(value.func_150291_c()));
/*     */       } 
/*  71 */       ChunkPos mainChunk = getChunkCoords(getPosFromTicket(ticket));
/*  72 */       if (!ticket.getChunkList().contains(mainChunk)) {
/*  73 */         ForgeChunkManager.forceChunk(ticket, mainChunk);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ForgeChunkManager.Ticket getTicket(World world, BlockPos pos, boolean create) {
/*  79 */     if (world.field_72995_K) return null; 
/*  80 */     List<ForgeChunkManager.Ticket> ticketList = getTicketsForWorld(world);
/*  81 */     if (ticketList == null) throw new IllegalStateException(); 
/*  82 */     for (ForgeChunkManager.Ticket ticket : ticketList) {
/*  83 */       if (pos.equals(getPosFromTicket(ticket))) {
/*  84 */         return ticket;
/*     */       }
/*     */     } 
/*  87 */     if (create) {
/*  88 */       return createTicket(world, pos);
/*     */     }
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ForgeChunkManager.Ticket createTicket(World world, BlockPos pos) {
/*  98 */     assert getTicket(world, pos, false) == null;
/*  99 */     ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket(IC2.getInstance(), world, ForgeChunkManager.Type.NORMAL);
/* 100 */     ticket.getModData().func_74768_a("x", pos.func_177958_n());
/* 101 */     ticket.getModData().func_74768_a("y", pos.func_177956_o());
/* 102 */     ticket.getModData().func_74768_a("z", pos.func_177952_p());
/* 103 */     getTicketsForWorld(world).add(ticket);
/* 104 */     addChunkToTicket(ticket, getChunkCoords(pos));
/* 105 */     return ticket;
/*     */   }
/*     */   
/*     */   public void addChunkToTicket(ForgeChunkManager.Ticket ticket, ChunkPos chunk) {
/* 109 */     if (ticket.getChunkList().contains(chunk))
/*     */       return; 
/* 111 */     ForgeChunkManager.forceChunk(ticket, chunk);
/*     */ 
/*     */     
/* 114 */     ForgeChunkManager.reorderChunk(ticket, getChunkCoords(getPosFromTicket(ticket)));
/* 115 */     NBTTagList list = ticket.getModData().func_150295_c("loadedChunks", 4);
/* 116 */     if (!ticket.getModData().func_150297_b("loadedChunks", 9)) {
/* 117 */       ticket.getModData().func_74782_a("loadedChunks", (NBTBase)list);
/*     */     }
/* 119 */     ticket.getModData().func_74782_a("loadedChunks", (NBTBase)list);
/* 120 */     list.func_74742_a((NBTBase)new NBTTagLong(chunk.field_77276_a & 0xFFFFFFFFL | (chunk.field_77275_b & 0xFFFFFFFFL) << 32L));
/*     */   }
/*     */   
/*     */   public void removeChunkFromTicket(ForgeChunkManager.Ticket ticket, ChunkPos chunk) {
/* 124 */     if (getChunkCoords(getPosFromTicket(ticket)).equals(chunk))
/* 125 */       return;  ForgeChunkManager.unforceChunk(ticket, chunk);
/* 126 */     NBTTagList list = ticket.getModData().func_150295_c("loadedChunks", 4);
/* 127 */     long serializedChunk = serialize(chunk);
/* 128 */     for (int i = 0; i < list.func_74745_c(); i++) {
/* 129 */       NBTTagLong pos = (NBTTagLong)list.func_179238_g(i);
/* 130 */       if (pos.func_150291_c() == serializedChunk) {
/* 131 */         list.func_74744_a(i--);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeTicket(World world, BlockPos pos) {
/* 137 */     removeTicket(getTicket(world, pos, false));
/*     */   }
/*     */   
/*     */   public void removeTicket(ForgeChunkManager.Ticket ticket) {
/* 141 */     ForgeChunkManager.releaseTicket(ticket);
/* 142 */     getTicketsForWorld(ticket.world).remove(ticket);
/*     */   }
/*     */   
/*     */   public int getMaxChunksPerTicket() {
/* 146 */     return ForgeChunkManager.getMaxChunkDepthFor("ic2");
/*     */   }
/*     */   
/*     */   private BlockPos getPosFromTicket(ForgeChunkManager.Ticket ticket) {
/* 150 */     return new BlockPos(ticket.getModData().func_74762_e("x"), ticket.getModData().func_74762_e("y"), ticket.getModData().func_74762_e("z"));
/*     */   }
/*     */   
/*     */   public static ChunkLoaderLogic getInstance() {
/* 154 */     return instance;
/*     */   }
/*     */   
/*     */   public static long serialize(ChunkPos chunk) {
/* 158 */     return chunk.field_77276_a & 0xFFFFFFFFL | (chunk.field_77275_b & 0xFFFFFFFFL) << 32L;
/*     */   }
/*     */   
/*     */   public static ChunkPos deserialize(long value) {
/* 162 */     return new ChunkPos((int)(value & 0xFFFFFFFFFFFFFFFFL), (int)(value >> 32L));
/*     */   }
/*     */   
/*     */   public static ChunkPos getChunkCoords(BlockPos pos) {
/* 166 */     return new ChunkPos(pos.func_177958_n() >> 4, pos.func_177952_p() >> 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ChunkLoaderLogic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */