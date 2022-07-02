/*     */ package ic2.core.block.machine.tileentity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ChunkLoaderLogic;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.comp.Energy;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotDischarge;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.block.machine.container.ContainerChunkLoader;
/*     */ import ic2.core.block.machine.gui.GuiChunkLoader;
/*     */ import ic2.core.init.MainConfig;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.util.ConfigUtil;
/*     */ import ic2.core.util.LogCategory;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagLong;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.ForgeChunkManager;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NotClassic
/*     */ public class TileEntityChunkloader
/*     */   extends TileEntityInventory
/*     */   implements INetworkClientTileEntityEventListener, IHasGui, IUpgradableBlock
/*     */ {
/*     */   private ForgeChunkManager.Ticket ticket;
/*  65 */   private final Set<ChunkPos> loadedChunks = new HashSet<>();
/*     */   
/*     */   public final InvSlotUpgrade upgradeSlot;
/*     */   
/*     */   public final InvSlotDischarge dischargeSlot;
/*     */   
/*     */   public final Energy energy;
/*     */   
/*     */   private static final int defaultTier = 1;
/*     */   private static final int defaultEnergyStorage = 2500;
/*  75 */   private final double euPerChunk = ConfigUtil.getFloat(MainConfig.get(), "balance/euPerChunk");
/*     */   
/*     */   public TileEntityChunkloader() {
/*  78 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*     */     
/*  80 */     this.dischargeSlot = new InvSlotDischarge((IInventorySlotHolder)this, InvSlot.Access.IO, 1, true, InvSlot.InvSide.ANY);
/*     */     
/*  82 */     this.energy = (Energy)addComponent((TileEntityComponent)Energy.asBasicSink((TileEntityBlock)this, 2500.0D, 1).addManagedSlot((InvSlot)this.dischargeSlot));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntityServer() {
/*  87 */     super.updateEntityServer();
/*     */     
/*  89 */     boolean active = this.energy.useEnergy(getLoadedChunks().size() * this.euPerChunk);
/*     */     
/*  91 */     if (active != getActive()) {
/*  92 */       setActive(active);
/*     */     }
/*     */     
/*  95 */     this.upgradeSlot.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/* 100 */     super.func_145839_a(nbt);
/* 101 */     NBTTagList list = nbt.func_150295_c("loadedChunks", 4);
/* 102 */     this.loadedChunks.clear();
/* 103 */     for (int i = 0; i < list.func_74745_c(); i++) {
/* 104 */       NBTTagLong currentNBT = (NBTTagLong)list.func_179238_g(i);
/* 105 */       long value = currentNBT.func_150291_c();
/* 106 */       this.loadedChunks.add(ChunkLoaderLogic.deserialize(value));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/* 112 */     super.func_189515_b(nbt);
/*     */     
/* 114 */     NBTTagList list = new NBTTagList();
/* 115 */     nbt.func_74782_a("loadedChunks", (NBTBase)list);
/*     */     
/* 117 */     for (ChunkPos chunk : this.loadedChunks) {
/* 118 */       list.func_74742_a((NBTBase)new NBTTagLong(ChunkLoaderLogic.serialize(chunk)));
/*     */     }
/*     */     
/* 121 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 126 */     World world = func_145831_w();
/*     */     
/* 128 */     if (!world.field_72995_K && getActive() != active) {
/* 129 */       if (active) {
/* 130 */         if (this.ticket != null) throw new IllegalStateException("Cannot activate ChunkLoader: " + this.field_174879_c + " " + this.ticket); 
/* 131 */         this.ticket = ChunkLoaderLogic.getInstance().createTicket(world, this.field_174879_c);
/* 132 */         for (ChunkPos coords : this.loadedChunks) {
/* 133 */           ChunkLoaderLogic.getInstance().addChunkToTicket(this.ticket, coords);
/*     */         }
/*     */       } else {
/*     */         
/* 137 */         if (this.ticket == null) throw new IllegalStateException("Cannot deactivate ChunkLoader: " + this.field_174879_c + " " + this.ticket); 
/* 138 */         ChunkLoaderLogic.getInstance().removeTicket(this.ticket);
/* 139 */         this.ticket = null;
/*     */       } 
/*     */     }
/* 142 */     super.setActive(active);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/* 147 */     super.onLoaded();
/*     */     
/* 149 */     World world = func_145831_w();
/*     */     
/* 151 */     if (!world.field_72995_K) {
/* 152 */       this.ticket = ChunkLoaderLogic.getInstance().getTicket(world, this.field_174879_c, false);
/*     */       
/* 154 */       if (this.ticket != null) {
/* 155 */         this.loadedChunks.clear();
/* 156 */         this.loadedChunks.addAll((Collection<? extends ChunkPos>)this.ticket.getChunkList());
/*     */       } 
/*     */       
/* 159 */       super.setActive((this.ticket != null));
/* 160 */       setOverclockRates();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnloaded() {
/* 166 */     super.onUnloaded();
/*     */     
/* 168 */     this.ticket = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
/* 173 */     super.onPlaced(stack, placer, facing);
/* 174 */     this.loadedChunks.add(ChunkLoaderLogic.getChunkCoords(this.field_174879_c));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 179 */     if ((func_145831_w()).field_72995_K) return true;
/*     */     
/* 181 */     return IC2.platform.launchGui(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityChunkloader> getGuiContainer(EntityPlayer player) {
/* 186 */     return (ContainerBase<TileEntityChunkloader>)new ContainerChunkLoader(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 192 */     return (GuiScreen)new GuiChunkLoader(new ContainerChunkLoader(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChunkToLoaded(ChunkPos chunk) {
/* 202 */     if ((func_145831_w()).field_72995_K) {
/* 203 */       (new RuntimeException("Something tried to change the ChunkLoaderState on the client.")).printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/* 207 */     if (!isChunkInRange(chunk)) {
/* 208 */       IC2.log.warn(LogCategory.Block, "Trying to add a Chunk to loaded, however the chunk is too far away. Aborting.");
/*     */       return;
/*     */     } 
/* 211 */     if (getLoadedChunks().size() < ChunkLoaderLogic.getInstance().getMaxChunksPerTicket()) {
/*     */ 
/*     */       
/* 214 */       if (this.ticket != null) {
/* 215 */         ChunkLoaderLogic.getInstance().addChunkToTicket(this.ticket, chunk);
/*     */       }
/* 217 */       this.loadedChunks.add(chunk);
/* 218 */       func_70296_d();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeChunkFromLoaded(ChunkPos chunk) {
/* 223 */     if ((func_145831_w()).field_72995_K) {
/* 224 */       (new RuntimeException("Something tried to change the ChunkLoaderState on the client.")).printStackTrace();
/*     */       
/*     */       return;
/*     */     } 
/* 228 */     if (ChunkLoaderLogic.getChunkCoords(this.field_174879_c).equals(chunk))
/* 229 */       return;  if (this.ticket != null) {
/* 230 */       ChunkLoaderLogic.getInstance().removeChunkFromTicket(this.ticket, chunk);
/*     */     }
/* 232 */     this.loadedChunks.remove(chunk);
/* 233 */     func_70296_d();
/*     */   }
/*     */   
/*     */   public ImmutableSet<ChunkPos> getLoadedChunks() {
/* 237 */     return ImmutableSet.copyOf(this.loadedChunks);
/*     */   }
/*     */   
/*     */   public boolean isChunkInRange(ChunkPos chunk) {
/* 241 */     ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(this.field_174879_c);
/* 242 */     return (Math.abs(chunk.field_77276_a - mainChunk.field_77276_a) <= 4 && Math.abs(chunk.field_77275_b - mainChunk.field_77275_b) <= 4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 248 */     int x = (event & 0xF) - 8;
/* 249 */     int z = (event >> 4 & 0xF) - 8;
/* 250 */     ChunkPos mainChunk = ChunkLoaderLogic.getChunkCoords(this.field_174879_c);
/* 251 */     ChunkPos chunk = new ChunkPos(mainChunk.field_77276_a + x, mainChunk.field_77275_b + z);
/* 252 */     if (isChunkInRange(chunk)) {
/* 253 */       if (getLoadedChunks().contains(chunk)) {
/* 254 */         removeChunkFromLoaded(chunk);
/*     */       } else {
/*     */         
/* 257 */         addChunkToLoaded(chunk);
/*     */       } 
/*     */     } else {
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onBlockBreak() {
/* 268 */     super.onBlockBreak();
/* 269 */     if (this.ticket != null) {
/* 270 */       ChunkLoaderLogic.getInstance().removeTicket(this.ticket);
/* 271 */       this.ticket = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getEnergy() {
/* 277 */     return this.energy.getEnergy();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 282 */     return this.energy.useEnergy(amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 287 */     return EnumSet.of(UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.Transformer);
/*     */   }
/*     */   
/*     */   public void setOverclockRates() {
/* 291 */     this.upgradeSlot.onChanged();
/*     */     
/* 293 */     int tier = this.upgradeSlot.getTier(1);
/* 294 */     this.energy.setSinkTier(tier);
/* 295 */     this.dischargeSlot.setTier(tier);
/* 296 */     this.energy.setCapacity(this.upgradeSlot.getEnergyStorage(2500, 0, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/* 301 */     super.func_70296_d();
/*     */     
/* 303 */     if (IC2.platform.isSimulating())
/* 304 */       setOverclockRates(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityChunkloader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */