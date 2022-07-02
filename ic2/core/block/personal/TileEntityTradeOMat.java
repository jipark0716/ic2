/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.network.INetworkTileEntityEventListener;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.WorldData;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLinked;
/*     */ import ic2.core.block.invslot.InvSlotOutput;
/*     */ import ic2.core.item.upgrade.ItemUpgradeModule;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
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
/*     */ public class TileEntityTradeOMat
/*     */   extends TileEntityInventory
/*     */   implements IPersonalBlock, IHasGui, INetworkTileEntityEventListener, INetworkClientTileEntityEventListener
/*     */ {
/*  48 */   private int ticker = IC2.random.nextInt(64);
/*     */   
/*  50 */   public final InvSlot demandSlot = new InvSlot((IInventorySlotHolder)this, "demand", InvSlot.Access.NONE, 1);
/*  51 */   public final InvSlot offerSlot = new InvSlot((IInventorySlotHolder)this, "offer", InvSlot.Access.NONE, 1);
/*     */   
/*  53 */   public final InvSlotConsumableLinked inputSlot = new InvSlotConsumableLinked((IInventorySlotHolder)this, "input", 1, this.demandSlot);
/*  54 */   public final InvSlotOutput outputSlot = new InvSlotOutput((IInventorySlotHolder)this, "output", 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  63 */     super.func_145839_a(nbt);
/*     */     
/*  65 */     if (nbt.func_74764_b("ownerGameProfile")) {
/*  66 */       this.owner = NBTUtil.func_152459_a(nbt.func_74775_l("ownerGameProfile"));
/*     */     }
/*     */     
/*  69 */     this.totalTradeCount = nbt.func_74762_e("totalTradeCount");
/*     */     
/*  71 */     if (nbt.func_74764_b("infinite")) this.infinite = nbt.func_74767_n("infinite");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  80 */     super.func_189515_b(nbt);
/*     */     
/*  82 */     if (this.owner != null) {
/*  83 */       NBTTagCompound ownerNbt = new NBTTagCompound();
/*  84 */       NBTUtil.func_180708_a(ownerNbt, this.owner);
/*  85 */       nbt.func_74782_a("ownerGameProfile", (NBTBase)ownerNbt);
/*     */     } 
/*     */     
/*  88 */     nbt.func_74768_a("totalTradeCount", this.totalTradeCount);
/*     */     
/*  90 */     if (this.infinite) nbt.func_74757_a("infinite", this.infinite);
/*     */     
/*  92 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/*  97 */     List<String> ret = super.getNetworkedFields();
/*     */     
/*  99 */     ret.add("owner");
/*     */     
/* 101 */     return ret;
/*     */   }
/*     */   
/*     */   public final boolean isWireless() {
/* 105 */     return getActive();
/*     */   }
/*     */   
/*     */   public final boolean setWireless(boolean wireless) {
/* 109 */     if (isWireless() == wireless) return false;
/*     */     
/* 111 */     if (wireless) {
/* 112 */       setActive(true);
/* 113 */       (WorldData.get(this.field_145850_b)).tradeMarket.registerTradeOMat(this);
/*     */     } else {
/* 115 */       setActive(false);
/* 116 */       (WorldData.get(this.field_145850_b)).tradeMarket.unregisterTradeOMat(this);
/*     */     } 
/*     */     
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 127 */     super.updateEntityServer();
/*     */     
/* 129 */     trade();
/*     */     
/* 131 */     if (this.infinite) {
/* 132 */       this.stock = -1;
/* 133 */     } else if (++this.ticker % 64 == 0) {
/* 134 */       updateStock();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void trade() {
/* 139 */     ItemStack tradedIn = this.inputSlot.consumeLinked(true);
/* 140 */     if (StackUtil.isEmpty(tradedIn))
/*     */       return; 
/* 142 */     ItemStack offer = this.offerSlot.get();
/* 143 */     if (StackUtil.isEmpty(offer))
/*     */       return; 
/* 145 */     if (!this.outputSlot.canAdd(offer))
/*     */       return; 
/* 147 */     if (this.infinite) {
/* 148 */       this.inputSlot.consumeLinked(false);
/* 149 */       this.outputSlot.add(offer);
/*     */     } else {
/* 151 */       int amount = StackUtil.fetch((TileEntity)this, offer, true);
/* 152 */       if (amount != StackUtil.getSize(offer))
/*     */         return; 
/* 154 */       int transferredOut = StackUtil.distribute((TileEntity)this, tradedIn, true);
/* 155 */       if (transferredOut != StackUtil.getSize(tradedIn))
/*     */         return; 
/* 157 */       amount = StackUtil.fetch((TileEntity)this, offer, false);
/* 158 */       if (amount == 0)
/* 159 */         return;  if (amount != StackUtil.getSize(offer)) {
/* 160 */         IC2.log.warn(LogCategory.Block, "The Trade-O-Mat at %s received an inconsistent result from an adjacent trade supply inventory, the %s items will be lost.", new Object[] {
/* 161 */               Util.formatPosition((TileEntity)this), Integer.valueOf(amount)
/*     */             });
/*     */         return;
/*     */       } 
/* 165 */       StackUtil.distribute((TileEntity)this, this.inputSlot.consumeLinked(false), false);
/* 166 */       this.outputSlot.add(offer);
/* 167 */       this.stock--;
/*     */     } 
/*     */     
/* 170 */     this.totalTradeCount++;
/* 171 */     ((NetworkManager)IC2.network.get(true)).initiateTileEntityEvent((TileEntity)this, 0, true);
/* 172 */     func_70296_d();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/* 177 */     super.onLoaded();
/*     */     
/* 179 */     if (IC2.platform.isSimulating()) {
/* 180 */       updateStock();
/* 181 */       if (isWireless()) (WorldData.get(this.field_145850_b)).tradeMarket.registerTradeOMat(this);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 187 */     if (!isWireless() && StackUtil.consume(player, hand, StackUtil.sameStack(ItemName.upgrade.getItemStack((Enum)ItemUpgradeModule.UpgradeType.remote_interface)), 1)) {
/* 188 */       if (!(func_145831_w()).field_72995_K) setWireless(true);
/*     */       
/* 190 */       return true;
/*     */     } 
/*     */     
/* 193 */     return super.onActivated(player, hand, side, hitX, hitY, hitZ);
/*     */   }
/*     */   
/*     */   public void updateStock() {
/* 197 */     ItemStack offer = this.offerSlot.get();
/*     */     
/* 199 */     if (StackUtil.isEmpty(offer)) {
/* 200 */       this.stock = 0;
/*     */     } else {
/* 202 */       this.stock = StackUtil.fetch((TileEntity)this, StackUtil.copyWithSize(offer, 2147483647), true) / StackUtil.getSize(offer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onUnloaded() {
/* 208 */     super.onUnloaded();
/*     */     
/* 210 */     if (!(func_145831_w()).field_72995_K && isWireless()) (WorldData.get(this.field_145850_b)).tradeMarket.unregisterTradeOMat(this);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer player) {
/* 215 */     return permitsAccess(player.func_146103_bH());
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<ItemStack> getAuxDrops(int fortune) {
/* 220 */     List<ItemStack> drops = super.getAuxDrops(fortune);
/* 221 */     if (isWireless()) drops.add(ItemName.upgrade.getItemStack((Enum)ItemUpgradeModule.UpgradeType.remote_interface)); 
/* 222 */     return drops;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean permitsAccess(GameProfile profile) {
/* 227 */     return TileEntityPersonalChest.checkAccess(this, profile);
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getPrivilegedInventory(GameProfile accessor) {
/* 232 */     return (IInventory)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile getOwner() {
/* 237 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(GameProfile owner) {
/* 242 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canEntityDestroy(Entity entity) {
/* 247 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityTradeOMat> getGuiContainer(EntityPlayer player) {
/* 252 */     if (permitsAccess(player.func_146103_bH())) {
/* 253 */       return (ContainerBase<TileEntityTradeOMat>)new ContainerTradeOMatOpen(player, this);
/*     */     }
/* 255 */     return (ContainerBase<TileEntityTradeOMat>)new ContainerTradeOMatClosed(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 261 */     if (isAdmin || permitsAccess(player.func_146103_bH())) {
/* 262 */       return (GuiScreen)new GuiTradeOMatOpen(new ContainerTradeOMatOpen(player, this), isAdmin);
/*     */     }
/*     */     
/* 265 */     return (GuiScreen)new GuiTradeOMatClosed(new ContainerTradeOMatClosed(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(int event) {
/* 275 */     switch (event) {
/*     */       case 0:
/* 277 */         IC2.audioManager.playOnce(this, "Machines/o-mat.ogg");
/*     */         return;
/*     */     } 
/* 280 */     IC2.platform.displayError("An unknown event type was received over multiplayer.\nThis could happen due to corrupted data or a bug.\n\n(Technical information: event ID " + event + ", tile entity below)\nT: " + this + " (" + this.field_174879_c + ")", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 292 */     if (event == 0 && 
/* 293 */       func_145831_w().func_73046_m().func_184103_al().func_152596_g(player.func_146103_bH())) {
/* 294 */       this.infinite = !this.infinite;
/*     */       
/* 296 */       if (!this.infinite) updateStock();
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 303 */   private GameProfile owner = null;
/* 304 */   public int totalTradeCount = 0;
/* 305 */   public int stock = 0;
/*     */   public boolean infinite = false;
/*     */   private static final int stockUpdateRate = 64;
/*     */   private static final int EventTrade = 0;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\TileEntityTradeOMat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */