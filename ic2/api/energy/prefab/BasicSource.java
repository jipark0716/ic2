/*     */ package ic2.api.energy.prefab;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergyAcceptor;
/*     */ import ic2.api.energy.tile.IEnergySource;
/*     */ import ic2.api.info.ILocatable;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
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
/*     */ 
/*     */ public class BasicSource
/*     */   extends BasicEnergyTile
/*     */   implements IEnergySource
/*     */ {
/*     */   protected int tier;
/*     */   
/*     */   public BasicSource(TileEntity parent, double capacity, int tier) {
/*  96 */     super(parent, capacity);
/*     */     
/*  98 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 100 */     this.tier = tier;
/* 101 */     double power = EnergyNet.instance.getPowerFromTier(tier);
/*     */     
/* 103 */     if (getCapacity() < power) setCapacity(power); 
/*     */   }
/*     */   
/*     */   public BasicSource(ILocatable parent, double capacity, int tier) {
/* 107 */     super(parent, capacity);
/*     */     
/* 109 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 111 */     this.tier = tier;
/* 112 */     double power = EnergyNet.instance.getPowerFromTier(tier);
/*     */     
/* 114 */     if (getCapacity() < power) setCapacity(power); 
/*     */   }
/*     */   
/*     */   public BasicSource(World world, BlockPos pos, double capacity, int tier) {
/* 118 */     super(world, pos, capacity);
/*     */     
/* 120 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 122 */     this.tier = tier;
/* 123 */     double power = EnergyNet.instance.getPowerFromTier(tier);
/*     */     
/* 125 */     if (getCapacity() < power) setCapacity(power);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSourceTier(int tier) {
/* 134 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 136 */     double power = EnergyNet.instance.getPowerFromTier(tier);
/*     */     
/* 138 */     if (getCapacity() < power) setCapacity(power);
/*     */     
/* 140 */     this.tier = tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing direction) {
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOfferedEnergy() {
/* 152 */     return getEnergyStored();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEnergy(double amount) {
/* 157 */     setEnergyStored(getEnergyStored() - amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSourceTier() {
/* 162 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getNbtTagName() {
/* 169 */     return "IC2BasicSource";
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\prefab\BasicSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */