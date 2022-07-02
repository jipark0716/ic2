/*     */ package ic2.core.block.personal;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.network.INetworkClientTileEntityEventListener;
/*     */ import ic2.api.upgrade.IUpgradableBlock;
/*     */ import ic2.api.upgrade.UpgradableProperty;
/*     */ import ic2.core.ContainerBase;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IHasGui;
/*     */ import ic2.core.block.IInventorySlotHolder;
/*     */ import ic2.core.block.TileEntityInventory;
/*     */ import ic2.core.block.invslot.InvSlot;
/*     */ import ic2.core.block.invslot.InvSlotCharge;
/*     */ import ic2.core.block.invslot.InvSlotConsumableLinked;
/*     */ import ic2.core.block.invslot.InvSlotUpgrade;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class TileEntityEnergyOMat
/*     */   extends TileEntityInventory
/*     */   implements IPersonalBlock, IHasGui, IEnergySink, IEnergySource, INetworkClientTileEntityEventListener, IUpgradableBlock {
/*  46 */   public int euOffer = 1000;
/*  47 */   private GameProfile owner = null;
/*     */   private boolean addedToEnergyNet = false;
/*     */   public int paidFor;
/*     */   public double euBuffer;
/*  51 */   private int euBufferMax = 10000;
/*  52 */   private int tier = 1;
/*     */   public final InvSlot demandSlot;
/*     */   public final InvSlotConsumableLinked inputSlot;
/*     */   
/*     */   public TileEntityEnergyOMat() {
/*  57 */     this.demandSlot = new InvSlot((IInventorySlotHolder)this, "demand", InvSlot.Access.NONE, 1);
/*  58 */     this.inputSlot = new InvSlotConsumableLinked((IInventorySlotHolder)this, "input", 1, this.demandSlot);
/*  59 */     this.chargeSlot = new InvSlotCharge((IInventorySlotHolder)this, 1);
/*  60 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public final InvSlotCharge chargeSlot;
/*     */   
/*     */   public final InvSlotUpgrade upgradeSlot;
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbttagcompound) {
/*  69 */     super.func_145839_a(nbttagcompound);
/*     */     
/*  71 */     if (nbttagcompound.func_74764_b("ownerGameProfile")) {
/*  72 */       this.owner = NBTUtil.func_152459_a(nbttagcompound.func_74775_l("ownerGameProfile"));
/*     */     }
/*     */     
/*  75 */     this.euOffer = nbttagcompound.func_74762_e("euOffer");
/*  76 */     this.paidFor = nbttagcompound.func_74762_e("paidFor");
/*     */     
/*     */     try {
/*  79 */       this.euBuffer = nbttagcompound.func_74769_h("euBuffer");
/*  80 */     } catch (Exception e) {
/*  81 */       this.euBuffer = nbttagcompound.func_74762_e("euBuffer");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  91 */     super.func_189515_b(nbt);
/*     */     
/*  93 */     if (this.owner != null) {
/*  94 */       NBTTagCompound ownerNbt = new NBTTagCompound();
/*  95 */       NBTUtil.func_180708_a(ownerNbt, this.owner);
/*  96 */       nbt.func_74782_a("ownerGameProfile", (NBTBase)ownerNbt);
/*     */     } 
/*     */     
/*  99 */     nbt.func_74768_a("euOffer", this.euOffer);
/* 100 */     nbt.func_74768_a("paidFor", this.paidFor);
/* 101 */     nbt.func_74780_a("euBuffer", this.euBuffer);
/*     */     
/* 103 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer player) {
/* 108 */     return permitsAccess(player.func_146103_bH());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/* 113 */     super.onLoaded();
/*     */     
/* 115 */     if (!(func_145831_w()).field_72995_K) {
/* 116 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent((IEnergyTile)this));
/*     */       
/* 118 */       this.addedToEnergyNet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onUnloaded() {
/* 127 */     if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
/* 128 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent((IEnergyTile)this));
/*     */       
/* 130 */       this.addedToEnergyNet = false;
/*     */     } 
/*     */     
/* 133 */     super.onUnloaded();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/* 138 */     super.updateEntityServer();
/*     */     
/* 140 */     boolean invChanged = false;
/*     */ 
/*     */     
/* 143 */     this.euBufferMax = 10000;
/* 144 */     this.tier = 1;
/* 145 */     this.chargeSlot.setTier(1);
/*     */     
/* 147 */     if (!this.upgradeSlot.isEmpty()) {
/* 148 */       this.euBufferMax = this.upgradeSlot.getEnergyStorage(10000, 0, 0);
/* 149 */       this.tier = 1 + this.upgradeSlot.extraTier;
/* 150 */       this.chargeSlot.setTier(this.tier);
/*     */     } 
/*     */     
/* 153 */     ItemStack tradedIn = this.inputSlot.consumeLinked(true);
/*     */     
/* 155 */     if (tradedIn != null) {
/* 156 */       int transferred = StackUtil.distribute((TileEntity)this, tradedIn, true);
/*     */       
/* 158 */       if (transferred == StackUtil.getSize(tradedIn)) {
/* 159 */         StackUtil.distribute((TileEntity)this, this.inputSlot.consumeLinked(false), false);
/*     */         
/* 161 */         this.paidFor += this.euOffer;
/* 162 */         invChanged = true;
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     if (this.euBuffer >= 1.0D) {
/* 167 */       double sent = this.chargeSlot.charge(this.euBuffer);
/*     */       
/* 169 */       if (sent > 0.0D) {
/* 170 */         this.euBuffer -= sent;
/* 171 */         invChanged = true;
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     if (invChanged) func_70296_d();
/*     */   
/*     */   }
/*     */   
/*     */   public boolean permitsAccess(GameProfile profile) {
/* 180 */     return TileEntityPersonalChest.checkAccess(this, profile);
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getPrivilegedInventory(GameProfile accessor) {
/* 185 */     return (IInventory)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getNetworkedFields() {
/* 190 */     List<String> ret = new ArrayList<>();
/*     */     
/* 192 */     ret.add("owner");
/* 193 */     ret.addAll(super.getNetworkedFields());
/*     */     
/* 195 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile getOwner() {
/* 200 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(GameProfile owner) {
/* 205 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canEntityDestroy(Entity entity) {
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSetFacingWrench(EnumFacing facing, EntityPlayer player) {
/* 215 */     if (player == null || !permitsAccess(player.func_146103_bH())) return false;
/*     */     
/* 217 */     return super.canSetFacingWrench(facing, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
/* 222 */     return !facingMatchesDirection(direction);
/*     */   }
/*     */   
/*     */   public boolean facingMatchesDirection(EnumFacing direction) {
/* 226 */     return (direction == getFacing());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing direction) {
/* 231 */     return facingMatchesDirection(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 236 */     return this.euBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 241 */     this.euBuffer -= amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 246 */     return Math.min(this.paidFor, this.euBufferMax - this.euBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
/* 251 */     double toAdd = Math.min(Math.min(amount, this.paidFor), this.euBufferMax - this.euBuffer);
/* 252 */     this.paidFor = (int)(this.paidFor - toAdd);
/* 253 */     this.euBuffer += toAdd;
/* 254 */     return amount - toAdd;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 259 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 264 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerBase<TileEntityEnergyOMat> getGuiContainer(EntityPlayer player) {
/* 269 */     if (permitsAccess(player.func_146103_bH())) {
/* 270 */       return (ContainerBase<TileEntityEnergyOMat>)new ContainerEnergyOMatOpen(player, this);
/*     */     }
/* 272 */     return (ContainerBase<TileEntityEnergyOMat>)new ContainerEnergyOMatClosed(player, this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 278 */     if (isAdmin || permitsAccess(player.func_146103_bH())) {
/* 279 */       return (GuiScreen)new GuiEnergyOMatOpen(new ContainerEnergyOMatOpen(player, this));
/*     */     }
/*     */     
/* 282 */     return (GuiScreen)new GuiEnergyOMatClosed(new ContainerEnergyOMatClosed(player, this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNetworkEvent(EntityPlayer player, int event) {
/* 292 */     if (!permitsAccess(player.func_146103_bH()))
/* 293 */       return;  switch (event) { case 0:
/* 294 */         attemptSet(-100000); break;
/* 295 */       case 1: attemptSet(-10000); break;
/* 296 */       case 2: attemptSet(-1000); break;
/* 297 */       case 3: attemptSet(-100); break;
/* 298 */       case 4: attemptSet(100000); break;
/* 299 */       case 5: attemptSet(10000); break;
/* 300 */       case 6: attemptSet(1000); break;
/* 301 */       case 7: attemptSet(100);
/*     */         break; }
/*     */   
/*     */   }
/*     */   private void attemptSet(int amount) {
/* 306 */     this.euOffer += amount;
/*     */     
/* 308 */     if (this.euOffer < 100) this.euOffer = 100;
/*     */   
/*     */   }
/*     */   
/*     */   public double getEnergy() {
/* 313 */     return this.euBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 318 */     if (amount <= this.euBuffer) {
/* 319 */       amount -= this.euBuffer;
/* 320 */       return true;
/*     */     } 
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 327 */     return EnumSet.of(UpgradableProperty.EnergyStorage, UpgradableProperty.Transformer);
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\TileEntityEnergyOMat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */