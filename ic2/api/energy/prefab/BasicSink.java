/*     */ package ic2.api.energy.prefab;
/*     */ 
/*     */ import ic2.api.energy.tile.IEnergyEmitter;
/*     */ import ic2.api.energy.tile.IEnergySink;
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
/*     */ 
/*     */ 
/*     */ public class BasicSink
/*     */   extends BasicEnergyTile
/*     */   implements IEnergySink
/*     */ {
/*     */   protected int tier;
/*     */   
/*     */   public BasicSink(TileEntity parent, double capacity, int tier) {
/*  97 */     super(parent, capacity);
/*     */     
/*  99 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 101 */     this.tier = tier;
/*     */   }
/*     */   
/*     */   public BasicSink(ILocatable parent, double capacity, int tier) {
/* 105 */     super(parent, capacity);
/*     */     
/* 107 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 109 */     this.tier = tier;
/*     */   }
/*     */   
/*     */   public BasicSink(World world, BlockPos pos, double capacity, int tier) {
/* 113 */     super(world, pos, capacity);
/*     */     
/* 115 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 117 */     this.tier = tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSinkTier(int tier) {
/* 126 */     if (tier < 0) throw new IllegalArgumentException("invalid tier: " + tier);
/*     */     
/* 128 */     this.tier = tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDemandedEnergy() {
/* 140 */     return Math.max(0.0D, getCapacity() - getEnergyStored());
/*     */   }
/*     */ 
/*     */   
/*     */   public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
/* 145 */     setEnergyStored(getEnergyStored() + amount);
/*     */     
/* 147 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSinkTier() {
/* 152 */     return this.tier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getNbtTagName() {
/* 159 */     return "IC2BasicSink";
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\energy\prefab\BasicSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */