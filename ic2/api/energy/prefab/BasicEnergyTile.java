/*     */ package ic2.api.energy.prefab;
/*     */ 
/*     */ import ic2.api.energy.event.EnergyTileLoadEvent;
/*     */ import ic2.api.energy.event.EnergyTileUnloadEvent;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.info.ILocatable;
/*     */ import ic2.api.info.Info;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class BasicEnergyTile
/*     */   implements ILocatable, IEnergyTile
/*     */ {
/*     */   private final Object locationProvider;
/*     */   protected World world;
/*     */   protected BlockPos pos;
/*     */   protected double capacity;
/*     */   protected double energyStored;
/*     */   protected boolean addedToEnet;
/*     */   
/*     */   protected BasicEnergyTile(TileEntity parent, double capacity) {
/*  34 */     this(parent, capacity);
/*     */   }
/*     */   
/*     */   protected BasicEnergyTile(ILocatable parent, double capacity) {
/*  38 */     this(parent, capacity);
/*     */   }
/*     */   
/*     */   private BasicEnergyTile(Object locationProvider, double capacity) {
/*  42 */     this.locationProvider = locationProvider;
/*  43 */     this.capacity = capacity;
/*     */   }
/*     */   
/*     */   protected BasicEnergyTile(World world, BlockPos pos, double capacity) {
/*  47 */     if (world == null) throw new NullPointerException("null world"); 
/*  48 */     if (pos == null) throw new NullPointerException("null pos");
/*     */     
/*  50 */     this.locationProvider = null;
/*  51 */     this.world = world;
/*  52 */     this.pos = pos;
/*  53 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  63 */     if (!this.addedToEnet) onLoad();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoad() {
/*  71 */     if (!this.addedToEnet && 
/*  72 */       !(getWorldObj()).field_72995_K && 
/*  73 */       Info.isIc2Available()) {
/*  74 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileLoadEvent(this));
/*     */       
/*  76 */       this.addedToEnet = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/*  85 */     onChunkUnload();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChunkUnload() {
/*  93 */     if (this.addedToEnet && 
/*  94 */       !(getWorldObj()).field_72995_K && 
/*  95 */       Info.isIc2Available()) {
/*  96 */       MinecraftForge.EVENT_BUS.post((Event)new EnergyTileUnloadEvent(this));
/*     */       
/*  98 */       this.addedToEnet = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound tag) {
/* 108 */     NBTTagCompound data = tag.func_74775_l(getNbtTagName());
/* 109 */     setEnergyStored(data.func_74769_h("energy"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound tag) {
/* 118 */     NBTTagCompound data = new NBTTagCompound();
/*     */     
/* 120 */     data.func_74780_a("energy", getEnergyStored());
/* 121 */     tag.func_74782_a(getNbtTagName(), (NBTBase)data);
/*     */     
/* 123 */     return tag;
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
/*     */   public double getCapacity() {
/* 135 */     return this.capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCapacity(double capacity) {
/* 144 */     this.capacity = capacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEnergyStored() {
/* 153 */     return this.energyStored;
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
/*     */   public void setEnergyStored(double amount) {
/* 165 */     this.energyStored = amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFreeCapacity() {
/* 174 */     return getCapacity() - getEnergyStored();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double addEnergy(double amount) {
/* 184 */     if ((getWorldObj()).field_72995_K) return 0.0D;
/*     */     
/* 186 */     double energyStored = getEnergyStored();
/* 187 */     double capacity = getCapacity();
/*     */     
/* 189 */     if (amount > capacity - energyStored) amount = capacity - energyStored;
/*     */     
/* 191 */     setEnergyStored(energyStored + amount);
/*     */     
/* 193 */     return amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseEnergy(double amount) {
/* 203 */     return (getEnergyStored() >= amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useEnergy(double amount) {
/* 213 */     if (!canUseEnergy(amount) || (getWorldObj()).field_72995_K) return false;
/*     */     
/* 215 */     setEnergyStored(getEnergyStored() - amount);
/*     */     
/* 217 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean charge(ItemStack stack) {
/* 227 */     if (stack == null || !Info.isIc2Available() || (getWorldObj()).field_72995_K) return false;
/*     */     
/* 229 */     double energyStored = getEnergyStored();
/* 230 */     double amount = ElectricItem.manager.charge(stack, energyStored, Math.max(getSinkTier(), getSourceTier()), false, false);
/*     */     
/* 232 */     setEnergyStored(energyStored - amount);
/*     */     
/* 234 */     return (amount > 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean discharge(ItemStack stack, double limit) {
/* 245 */     if (stack == null || !Info.isIc2Available() || (getWorldObj()).field_72995_K) return false;
/*     */     
/* 247 */     double energyStored = getEnergyStored();
/* 248 */     double amount = getCapacity() - energyStored;
/* 249 */     if (amount <= 0.0D) return false;
/*     */     
/* 251 */     if (limit > 0.0D && limit < amount) amount = limit;
/*     */     
/* 253 */     amount = ElectricItem.manager.discharge(stack, amount, Math.max(getSinkTier(), getSourceTier()), (limit > 0.0D), true, false);
/*     */     
/* 255 */     setEnergyStored(energyStored + amount);
/*     */     
/* 257 */     return (amount > 0.0D);
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
/*     */   
/*     */   public World getWorldObj() {
/* 270 */     if (this.world == null) initLocation();
/*     */     
/* 272 */     return this.world;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 277 */     if (this.pos == null) initLocation();
/*     */     
/* 279 */     return this.pos;
/*     */   }
/*     */   
/*     */   private void initLocation() {
/* 283 */     if (this.locationProvider instanceof ILocatable) {
/* 284 */       ILocatable provider = (ILocatable)this.locationProvider;
/* 285 */       this.world = provider.getWorldObj();
/* 286 */       this.pos = provider.getPosition();
/* 287 */     } else if (this.locationProvider instanceof TileEntity) {
/* 288 */       TileEntity provider = (TileEntity)this.locationProvider;
/* 289 */       this.world = provider.func_145831_w();
/* 290 */       this.pos = provider.func_174877_v();
/*     */     } else {
/* 292 */       throw new IllegalStateException("no/incompatible location provider");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract String getNbtTagName();
/*     */ 
/*     */   
/*     */   protected int getSinkTier() {
/* 301 */     return 0;
/*     */   }
/*     */   
/*     */   protected int getSourceTier() {
/* 305 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\prefab\BasicEnergyTile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */