/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.api.energy.tile.IHeatSource;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.network.GuiSynced;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ public abstract class TileEntityHeatSourceInventory
/*     */   extends TileEntityInventory implements IHeatSource {
/*     */   @GuiSynced
/*     */   protected int transmitHeat;
/*     */   @GuiSynced
/*     */   protected int maxHeatEmitpeerTick;
/*     */   protected int HeatBuffer;
/*     */   
/*     */   protected void updateEntityServer() {
/*  18 */     super.updateEntityServer();
/*     */     
/*  20 */     int amount = getMaxHeatEmittedPerTick() - this.HeatBuffer;
/*     */     
/*  22 */     if (amount > 0) {
/*  23 */       addtoHeatBuffer(fillHeatBuffer(amount));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxrequestHeatTick(EnumFacing directionFrom) {
/*  30 */     return getConnectionBandwidth(directionFrom);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectionBandwidth(EnumFacing side) {
/*  35 */     if (facingMatchesDirection(side)) {
/*  36 */       return getMaxHeatEmittedPerTick();
/*     */     }
/*  38 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int requestHeat(EnumFacing directionFrom, int requestheat) {
/*  44 */     return drawHeat(directionFrom, requestheat, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public int drawHeat(EnumFacing side, int request, boolean simulate) {
/*  49 */     if (facingMatchesDirection(side)) {
/*  50 */       int heatBuffer = getHeatBuffer();
/*     */       
/*  52 */       if (heatBuffer >= request) {
/*  53 */         if (!simulate) {
/*  54 */           setHeatBuffer(heatBuffer - request);
/*  55 */           this.transmitHeat = request;
/*     */         } 
/*  57 */         return request;
/*     */       } 
/*  59 */       if (!simulate) {
/*  60 */         this.transmitHeat = heatBuffer;
/*  61 */         setHeatBuffer(0);
/*     */       } 
/*  63 */       return heatBuffer;
/*     */     } 
/*     */     
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbtTagCompound) {
/*  72 */     super.func_145839_a(nbtTagCompound);
/*     */     
/*  74 */     this.HeatBuffer = nbtTagCompound.func_74762_e("HeatBuffer");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  79 */     super.func_189515_b(nbt);
/*     */     
/*  81 */     nbt.func_74768_a("HeatBuffer", this.HeatBuffer);
/*     */     
/*  83 */     return nbt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_70296_d() {
/*  88 */     super.func_70296_d();
/*     */     
/*  90 */     if (IC2.platform.isSimulating()) {
/*  91 */       this.maxHeatEmitpeerTick = getMaxHeatEmittedPerTick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLoaded() {
/*  97 */     super.onLoaded();
/*     */     
/*  99 */     if (IC2.platform.isSimulating()) {
/* 100 */       this.maxHeatEmitpeerTick = getMaxHeatEmittedPerTick();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean facingMatchesDirection(EnumFacing direction) {
/* 105 */     return (direction == getFacing());
/*     */   }
/*     */   
/*     */   public int getHeatBuffer() {
/* 109 */     return this.HeatBuffer;
/*     */   }
/*     */   
/*     */   public void setHeatBuffer(int HeatBuffer) {
/* 113 */     this.HeatBuffer = HeatBuffer;
/*     */   }
/*     */   
/*     */   public void addtoHeatBuffer(int heat) {
/* 117 */     setHeatBuffer(getHeatBuffer() + heat);
/*     */   }
/*     */   
/*     */   public int gettransmitHeat() {
/* 121 */     return this.transmitHeat;
/*     */   }
/*     */   
/*     */   public String getOutput() {
/* 125 */     return gettransmitHeat() + " / " + getMaxHeatEmittedPerTick();
/*     */   }
/*     */   
/*     */   protected abstract int fillHeatBuffer(int paramInt);
/*     */   
/*     */   public abstract int getMaxHeatEmittedPerTick();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\TileEntityHeatSourceInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */