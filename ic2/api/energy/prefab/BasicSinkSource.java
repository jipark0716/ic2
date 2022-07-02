/*     */ package ic2.api.energy.prefab;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergySink;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.info.ILocatable;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BasicSinkSource
/*     */   extends BasicEnergyTile implements IEnergySink, IEnergySource {
/*     */   protected int sinkTier;
/*     */   protected int sourceTier;
/*     */   
/*     */   public BasicSinkSource(TileEntity parent, double capacity, int sinkTier, int sourceTier) {
/*  20 */     super(parent, capacity);
/*     */     
/*  22 */     if (sinkTier < 0) throw new IllegalArgumentException("invalid sink tier: " + sinkTier); 
/*  23 */     if (sourceTier < 0) throw new IllegalArgumentException("invalid source tier: " + sourceTier);
/*     */     
/*  25 */     this.sinkTier = sinkTier;
/*  26 */     this.sourceTier = sourceTier;
/*  27 */     double power = EnergyNet.instance.getPowerFromTier(sourceTier);
/*     */     
/*  29 */     if (getCapacity() < power) setCapacity(power); 
/*     */   }
/*     */   
/*     */   public BasicSinkSource(ILocatable parent, double capacity, int sinkTier, int sourceTier) {
/*  33 */     super(parent, capacity);
/*     */     
/*  35 */     if (sinkTier < 0) throw new IllegalArgumentException("invalid sink tier: " + sinkTier); 
/*  36 */     if (sourceTier < 0) throw new IllegalArgumentException("invalid source tier: " + sourceTier);
/*     */     
/*  38 */     this.sinkTier = sinkTier;
/*  39 */     this.sourceTier = sourceTier;
/*  40 */     double power = EnergyNet.instance.getPowerFromTier(sourceTier);
/*     */     
/*  42 */     if (getCapacity() < power) setCapacity(power); 
/*     */   }
/*     */   
/*     */   public BasicSinkSource(World world, BlockPos pos, double capacity, int sinkTier, int sourceTier) {
/*  46 */     super(world, pos, capacity);
/*     */     
/*  48 */     if (sinkTier < 0) throw new IllegalArgumentException("invalid sink tier: " + sinkTier); 
/*  49 */     if (sourceTier < 0) throw new IllegalArgumentException("invalid source tier: " + sourceTier);
/*     */     
/*  51 */     this.sinkTier = sinkTier;
/*  52 */     this.sourceTier = sourceTier;
/*  53 */     double power = EnergyNet.instance.getPowerFromTier(sourceTier);
/*     */     
/*  55 */     if (getCapacity() < power) setCapacity(power);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSinkTier(int tier) {
/*  64 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/*  66 */     this.sinkTier = tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceTier(int tier) {
/*  75 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/*  77 */     double power = EnergyNet.instance.getPowerFromTier(tier);
/*     */     
/*  79 */     if (getCapacity() < power) setCapacity(power);
/*     */     
/*  81 */     this.sourceTier = tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/*  88 */     return Math.max(0.0D, getCapacity() - getEnergyStored());
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
/*  93 */     setEnergyStored(getEnergyStored() + amount);
/*     */     
/*  95 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 100 */     return this.sinkTier;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 105 */     return getEnergyStored();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 110 */     setEnergyStored(getEnergyStored() - amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 115 */     return this.sourceTier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getNbtTagName() {
/* 122 */     return "IC2BasicSinkSource";
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\prefab\BasicSinkSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */