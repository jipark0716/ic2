/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import ic2.api.energy.EnergyNet;
/*     */ import ic2.api.energy.tile.IEnergyTile;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IWorldTickCallback;
/*     */ import ic2.core.Ic2Player;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.block.comp.ComparatorEmitter;
/*     */ import ic2.core.block.comp.Energy;
/*     */ import ic2.core.block.comp.Redstone;
/*     */ import ic2.core.block.comp.TileEntityComponent;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityLuminator
/*     */   extends TileEntityBlock {
/*     */   private static final int manualChargeCapacity = 10000;
/*     */   
/*     */   public TileEntityLuminator() {
/*  43 */     this.energy = (Energy)addComponent((TileEntityComponent)Energy.asBasicSink(this, 5.0D));
/*  44 */     this.redstone = (Redstone)addComponent((TileEntityComponent)new Redstone(this));
/*  45 */     this.comparator = (ComparatorEmitter)addComponent((TileEntityComponent)new ComparatorEmitter(this));
/*  46 */     this.comparator.setUpdate(this.energy::getComparatorValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_145839_a(NBTTagCompound nbt) {
/*  51 */     super.func_145839_a(nbt);
/*     */     
/*  53 */     this.invertRedstone = nbt.func_74767_n("invert");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound func_189515_b(NBTTagCompound nbt) {
/*  58 */     super.func_189515_b(nbt);
/*     */     
/*  60 */     nbt.func_74757_a("invert", this.invertRedstone);
/*     */     
/*  62 */     return nbt;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoaded() {
/*  68 */     this.energy.setDirections(Collections.singleton(getFacing().func_176734_d()), Collections.emptySet());
/*  69 */     super.onLoaded();
/*     */     
/*  71 */     IC2.tickHandler.requestSingleWorldTick(func_145831_w(), new IWorldTickCallback()
/*     */         {
/*     */           public void onTick(World world) {
/*  74 */             TileEntityLuminator.this.checkPlacement();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumFacing getPlacementFacing(EntityLivingBase placer, EnumFacing facing) {
/*  81 */     return facing;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateEntityServer() {
/*  86 */     super.updateEntityServer();
/*     */     
/*  88 */     boolean lit = (isLit() && this.energy.useEnergy(0.25D));
/*     */     
/*  90 */     if (getActive() != lit) {
/*  91 */       setActive(lit);
/*  92 */       updateLight();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isLit() {
/*  97 */     return (this.redstone.hasRedstoneInput() != this.invertRedstone);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 102 */     if (!(func_145831_w()).field_72995_K) {
/* 103 */       ItemStack stack = StackUtil.get(player, hand);
/* 104 */       double amount = 10000.0D - this.energy.getEnergy();
/*     */       
/* 106 */       if (stack != null && amount > 0.0D && (
/*     */         
/* 108 */         amount = ElectricItem.manager.discharge(stack, amount, this.energy.getSinkTier(), true, true, false)) > 0.0D) {
/* 109 */         this.energy.forceAddEnergy(amount);
/*     */       } else {
/* 111 */         this.invertRedstone = !this.invertRedstone;
/* 112 */         ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)this, "invertRedstone");
/*     */       } 
/*     */     } 
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onNeighborChange(Block neighbor, BlockPos neighborPos) {
/* 120 */     super.onNeighborChange(neighbor, neighborPos);
/*     */     
/* 122 */     checkPlacement();
/*     */   }
/*     */   
/*     */   private void checkPlacement() {
/* 126 */     World world = func_145831_w();
/*     */     
/* 128 */     if (!isValidPosition(world, this.field_174879_c.func_177972_a(getFacing().func_176734_d()), getFacing())) {
/* 129 */       getBlockType().func_180657_a(world, Ic2Player.get(world), this.field_174879_c, world.func_180495_p(this.field_174879_c), (TileEntity)this, StackUtil.emptyStack);
/* 130 */       world.func_175698_g(this.field_174879_c);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isValidPosition(World world, BlockPos pos, EnumFacing side) {
/* 135 */     if (world.field_72995_K || ignoreBlockStay) return true;
/*     */     
/* 137 */     if (world.func_180495_p(pos).func_193401_d((IBlockAccess)world, pos, side) == BlockFaceShape.SOLID) return true;
/*     */     
/* 139 */     IEnergyTile tile = EnergyNet.instance.getSubTile(world, pos);
/*     */     
/* 141 */     return tile instanceof ic2.api.energy.tile.IEnergyEmitter;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<AxisAlignedBB> getAabbs(boolean forCollision) {
/* 146 */     return aabbMap.get(getFacing());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightValue() {
/* 151 */     return getActive() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onEntityCollision(Entity entity) {
/* 156 */     super.onEntityCollision(entity);
/*     */     
/* 158 */     if (getActive() && entity instanceof net.minecraft.entity.monster.EntityMob) {
/* 159 */       boolean isUndead = (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70668_bt() == EnumCreatureAttribute.UNDEAD);
/*     */       
/* 161 */       entity.func_70015_d(isUndead ? 20 : 10);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSetFacingWrench(EnumFacing facing, EntityPlayer player) {
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean setFacingWrench(EnumFacing facing, EntityPlayer player) {
/* 172 */     this.invertRedstone = !this.invertRedstone;
/*     */     
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wrenchCanRemove(EntityPlayer player) {
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onNetworkUpdate(String field) {
/* 184 */     super.onNetworkUpdate(field);
/*     */     
/* 186 */     if (field.equals("active")) {
/* 187 */       updateLight();
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateLight() {
/* 192 */     func_145831_w().func_180500_c(EnumSkyBlock.BLOCK, this.field_174879_c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<EnumFacing, List<AxisAlignedBB>> getAabbMap() {
/* 198 */     Map<EnumFacing, List<AxisAlignedBB>> ret = new EnumMap<>(EnumFacing.class);
/* 199 */     double height = 0.0625D;
/* 200 */     double remHeight = 0.9375D;
/*     */     
/* 202 */     for (EnumFacing side : EnumFacing.field_82609_l) {
/* 203 */       int dx = side.func_82601_c();
/* 204 */       int dy = side.func_96559_d();
/* 205 */       int dz = side.func_82599_e();
/* 206 */       double xS = ((dx + 1) / 2) * 0.9375D;
/* 207 */       double yS = ((dy + 1) / 2) * 0.9375D;
/* 208 */       double zS = ((dz + 1) / 2) * 0.9375D;
/* 209 */       double xE = 0.0625D + ((dx + 2) / 2) * 0.9375D;
/* 210 */       double yE = 0.0625D + ((dy + 2) / 2) * 0.9375D;
/* 211 */       double zE = 0.0625D + ((dz + 2) / 2) * 0.9375D;
/*     */       
/* 213 */       ret.put(side.func_176734_d(), Arrays.asList(new AxisAlignedBB[] { new AxisAlignedBB(xS, yS, zS, xE, yE, zE) }));
/*     */     } 
/*     */     
/* 216 */     return ret;
/*     */   }
/*     */ 
/*     */   
/* 220 */   private static final Map<EnumFacing, List<AxisAlignedBB>> aabbMap = getAabbMap();
/*     */   private final Energy energy;
/*     */   private final Redstone redstone;
/*     */   private final ComparatorEmitter comparator;
/*     */   private boolean invertRedstone;
/*     */   public static boolean ignoreBlockStay = false;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\TileEntityLuminator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */