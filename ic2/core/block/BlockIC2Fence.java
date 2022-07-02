/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.api.item.ItemWrapper;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.machine.tileentity.TileEntityMagnetizer;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.IExtBlockType;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.util.Ic2BlockPos;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockIC2Fence
/*     */   extends BlockMultiID<BlockIC2Fence.IC2FenceType>
/*     */ {
/*     */   public static BlockIC2Fence create() {
/*  50 */     return BlockMultiID.<IC2FenceType, BlockIC2Fence>create(BlockIC2Fence.class, IC2FenceType.class, new Object[0]);
/*     */   }
/*     */   
/*     */   private BlockIC2Fence() {
/*  54 */     super(BlockName.fence, Material.field_151573_f);
/*     */ 
/*     */     
/*  57 */     IBlockState defaultState = this.field_176227_L.func_177621_b().func_177226_a((IProperty)this.typeProperty, this.typeProperty.getDefault());
/*     */     
/*  59 */     for (IProperty<Boolean> property : connectProperties.values()) {
/*  60 */       defaultState = defaultState.func_177226_a(property, Boolean.valueOf(false));
/*     */     }
/*     */     
/*  63 */     func_180632_j(defaultState);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(BlockName name) {
/*  69 */     Item item = Item.func_150898_a(this);
/*  70 */     if (item == null || item == Items.field_190931_a)
/*     */       return; 
/*  72 */     ResourceLocation loc = Util.getName(item);
/*  73 */     if (loc == null)
/*     */       return; 
/*  75 */     for (IBlockState state : getTypeStates()) {
/*  76 */       ModelLoader.setCustomModelResourceLocation(item, 
/*  77 */           func_176201_c(state), new ModelResourceLocation(loc
/*  78 */             .toString() + "/" + ((IC2FenceType)state.func_177229_b((IProperty)this.typeProperty)).getName(), null));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d(IBlockState state) {
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer func_180661_e() {
/*  89 */     List<IProperty<?>> properties = new ArrayList<>();
/*  90 */     properties.add(getTypeProperty());
/*  91 */     properties.addAll(connectProperties.values());
/*     */     
/*  93 */     return new BlockStateContainer(this, properties
/*  94 */         .<IProperty>toArray(new IProperty[0]));
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
/*     */   
/*     */   public IBlockState func_176221_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 108 */     boolean isPole = true;
/* 109 */     boolean magnetizerConnected = false;
/* 110 */     IBlockState ret = state;
/*     */     
/* 112 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 113 */       IBlockState neighborState = world.func_180495_p(pos.func_177972_a(facing));
/*     */       
/* 115 */       if (isFence(neighborState)) {
/* 116 */         isPole = false;
/* 117 */         if (magnetizerConnected)
/* 118 */           break;  ret = ret.func_177226_a(connectProperties.get(facing), Boolean.valueOf(true));
/* 119 */       } else if (isPole && getMagnetizer(world, pos.func_177972_a(facing), facing, world.func_180495_p(pos.func_177972_a(facing)), false) != null) {
/* 120 */         magnetizerConnected = true;
/* 121 */         ret = ret.func_177226_a(connectProperties.get(facing), Boolean.valueOf(true));
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     if (!isPole && magnetizerConnected) {
/* 126 */       ret = state;
/*     */       
/* 128 */       for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 129 */         IBlockState neighborState = world.func_180495_p(pos.func_177972_a(facing));
/*     */         
/* 131 */         if (isFence(neighborState)) {
/* 132 */           ret = ret.func_177226_a(connectProperties.get(facing), Boolean.valueOf(true));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149721_r(IBlockState state) {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos blockPos, EnumFacing side) {
/* 161 */     return (side.func_176740_k() == EnumFacing.Axis.Y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
/* 171 */     return (face.func_176740_k() != EnumFacing.Axis.Y) ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180634_a(World world, BlockPos pos, IBlockState state, Entity rawEntity) {
/* 176 */     if (!(rawEntity instanceof EntityPlayer))
/*     */       return; 
/* 178 */     boolean powered = isPowered(world, pos, (IC2FenceType)state.func_177229_b((IProperty)this.typeProperty));
/* 179 */     EntityPlayer player = (EntityPlayer)rawEntity;
/* 180 */     boolean metalShoes = hasMetalShoes(player);
/* 181 */     boolean descending = player.func_70093_af();
/* 182 */     boolean slow = (player.field_70181_x >= -0.25D || player.field_70181_x < 1.6D);
/*     */     
/* 184 */     if (slow) player.field_70143_R = 0.0F;
/*     */     
/* 186 */     if (!powered) {
/* 187 */       if (descending && !slow && metalShoes) player.field_70181_x *= 0.9D;
/*     */     
/* 189 */     } else if (descending) {
/* 190 */       if (!slow) player.field_70181_x *= 0.8D; 
/*     */     } else {
/* 192 */       player.field_70181_x += 0.075D;
/*     */       
/* 194 */       if (player.field_70181_x > 0.0D) {
/* 195 */         player.field_70181_x *= 1.03D;
/*     */       }
/*     */       
/* 198 */       double maxSpeed = IC2.keyboard.isAltKeyDown(player) ? 0.1D : (metalShoes ? 1.5D : 0.5D);
/*     */ 
/*     */       
/* 201 */       player.field_70181_x = Math.min(player.field_70181_x, maxSpeed);
/*     */     } 
/*     */     
/* 204 */     if (!world.field_72995_K) {
/* 205 */       List<TileEntityMagnetizer> magnetizers = getMagnetizers((IBlockAccess)world, pos, false);
/* 206 */       for (TileEntityMagnetizer magnetizer : magnetizers) {
/* 207 */         ((NetworkManager)IC2.network.get(true)).updateTileEntityField((TileEntity)magnetizer, "energy");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_185477_a(IBlockState state, World world, BlockPos pos, AxisAlignedBB mask, List<AxisAlignedBB> result, Entity collidingEntity, boolean isActualState) {
/* 214 */     if (!isActualState) state = func_176221_a(state, (IBlockAccess)world, pos);
/*     */     
/* 216 */     func_185492_a(pos, mask, result, aabbs.get(null));
/*     */     
/* 218 */     for (IProperty<Boolean> property : connectProperties.values()) {
/* 219 */       if (((Boolean)state.func_177229_b(property)).booleanValue()) {
/* 220 */         func_185492_a(pos, mask, result, aabbs.get(property));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 227 */     AxisAlignedBB ret = aabbs.get(null);
/* 228 */     double xS = ret.field_72340_a;
/* 229 */     double yS = 0.0D;
/* 230 */     double zS = ret.field_72339_c;
/* 231 */     double xE = ret.field_72336_d;
/* 232 */     double yE = 1.0D;
/* 233 */     double zE = ret.field_72334_f;
/*     */     
/* 235 */     state = func_176221_a(state, world, pos);
/*     */     
/* 237 */     for (IProperty<Boolean> property : connectProperties.values()) {
/* 238 */       if (((Boolean)state.func_177229_b(property)).booleanValue()) {
/* 239 */         AxisAlignedBB aabb = aabbs.get(property);
/* 240 */         xS = Math.min(xS, aabb.field_72340_a);
/* 241 */         zS = Math.min(zS, aabb.field_72339_c);
/* 242 */         xE = Math.max(xE, aabb.field_72336_d);
/* 243 */         zE = Math.max(zE, aabb.field_72334_f);
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return new AxisAlignedBB(xS, 0.0D, zS, xE, 1.0D, zE);
/*     */   }
/*     */   
/*     */   private static boolean isFence(IBlockState state) {
/* 251 */     return (state.func_177230_c() instanceof BlockIC2Fence || state.func_177230_c() instanceof net.minecraft.block.BlockFence);
/*     */   }
/*     */   
/*     */   private static TileEntityMagnetizer getMagnetizer(IBlockAccess world, BlockPos pos, EnumFacing side, IBlockState state, boolean checkPower) {
/* 255 */     if (state.func_177230_c() != BlockName.te.getInstance()) return null;
/*     */     
/* 257 */     TileEntity te = world.func_175625_s(pos);
/*     */ 
/*     */     
/* 260 */     if (te instanceof TileEntityMagnetizer) {
/* 261 */       TileEntityMagnetizer ret = (TileEntityMagnetizer)te;
/* 262 */       if (side != null && !side.func_176734_d().equals(ret.getFacing()))
/*     */       {
/* 264 */         return null;
/*     */       }
/*     */       
/* 267 */       if (!checkPower || ret.canBoost()) return ret;
/*     */     
/*     */     } 
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean hasMetalShoes(EntityPlayer player) {
/* 274 */     ItemStack shoes = (ItemStack)player.field_71071_by.field_70460_b.get(0);
/*     */     
/* 276 */     if (shoes != null) {
/* 277 */       Item item = shoes.func_77973_b();
/*     */       
/* 279 */       if (item == Items.field_151167_ab || item == Items.field_151151_aj || item == Items.field_151029_X || 
/*     */ 
/*     */         
/* 282 */         ItemWrapper.isMetalArmor(shoes, player)) {
/* 283 */         return true;
/*     */       }
/*     */     } 
/* 286 */     return false;
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
/*     */   private boolean isPowered(World world, BlockPos start, IC2FenceType type) {
/* 298 */     if (!type.canBoost) return false;
/*     */     
/* 300 */     List<TileEntityMagnetizer> magnetizers = getMagnetizers((IBlockAccess)world, start, true);
/* 301 */     if (magnetizers.isEmpty()) return false;
/*     */     
/* 303 */     double multiplier = 1.0D / magnetizers.size();
/*     */     
/* 305 */     for (TileEntityMagnetizer magnetizer : magnetizers) {
/* 306 */       magnetizer.boost(multiplier);
/*     */     }
/*     */     
/* 309 */     return true;
/*     */   }
/*     */   
/*     */   private List<TileEntityMagnetizer> getMagnetizers(IBlockAccess world, BlockPos start, boolean checkPower) {
/* 313 */     int maxRange = 20;
/*     */ 
/*     */     
/* 316 */     List<TileEntityMagnetizer> ret = new ArrayList<>();
/* 317 */     Ic2BlockPos center = new Ic2BlockPos((Vec3i)start);
/* 318 */     Ic2BlockPos tmp = new Ic2BlockPos();
/*     */ 
/*     */ 
/*     */     
/* 322 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 323 */       Ic2BlockPos nPos = tmp.set((Vec3i)center).move(facing);
/* 324 */       IBlockState state = nPos.getBlockState(world);
/*     */ 
/*     */       
/* 327 */       if (isFence(state))
/* 328 */         return Collections.emptyList();  TileEntityMagnetizer te;
/* 329 */       if ((te = getMagnetizer(world, (BlockPos)nPos, facing, state, checkPower)) != null) {
/* 330 */         ret.add(te);
/*     */       }
/*     */     } 
/*     */     
/* 334 */     if (!ret.isEmpty()) return ret;
/*     */ 
/*     */ 
/*     */     
/* 338 */     int minDir = 0;
/* 339 */     int maxDir = 2;
/*     */ 
/*     */     
/* 342 */     for (int dy = 1; dy <= 20; dy++) {
/* 343 */       boolean abort = false;
/*     */       
/* 345 */       for (int dir = minDir; dir < maxDir; dir++) {
/* 346 */         int offset = dir * 2 - 1;
/* 347 */         center.setY(start.func_177956_o() + offset * dy);
/*     */         
/* 349 */         IBlockState centerState = center.getBlockState(world);
/*     */         
/* 351 */         if (!(centerState.func_177230_c() instanceof BlockIC2Fence) || 
/* 352 */           !((IC2FenceType)centerState.func_177229_b((IProperty)this.typeProperty)).canBoost) {
/*     */ 
/*     */           
/* 355 */           if (dir == 0) {
/* 356 */             minDir = 1;
/*     */           } else {
/* 358 */             maxDir = 1;
/*     */           } 
/*     */           
/* 361 */           if (minDir == maxDir) abort = true;
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/* 366 */         int oldSize = ret.size();
/*     */         
/* 368 */         for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 369 */           Ic2BlockPos nPos = tmp.set((Vec3i)center).move(facing);
/* 370 */           IBlockState state = nPos.getBlockState(world);
/*     */ 
/*     */           
/* 373 */           if (isFence(state)) {
/* 374 */             if (dir == 0) {
/* 375 */               minDir = 1;
/*     */             } else {
/* 377 */               maxDir = 1;
/*     */             } 
/*     */             
/* 380 */             if (minDir == maxDir) abort = true;
/*     */             
/* 382 */             for (; ret.size() > oldSize; ret.remove(ret.size() - 1)); break;
/*     */           } 
/*     */           TileEntityMagnetizer te;
/* 385 */           if ((te = getMagnetizer(world, (BlockPos)nPos, facing, state, checkPower)) != null) {
/* 386 */             abort = true;
/* 387 */             ret.add(te);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 392 */       if (abort) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 397 */     return ret;
/*     */   }
/*     */   
/*     */   private static Map<EnumFacing, IProperty<Boolean>> getConnectProperties() {
/* 401 */     Map<EnumFacing, IProperty<Boolean>> ret = new EnumMap<>(EnumFacing.class);
/*     */     
/* 403 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 404 */       ret.put(facing, PropertyBool.func_177716_a(facing.func_176610_l()));
/*     */     }
/*     */     
/* 407 */     return ret;
/*     */   }
/*     */   
/*     */   private static Map<IProperty<Boolean>, AxisAlignedBB> getAabbs() {
/* 411 */     Map<IProperty<Boolean>, AxisAlignedBB> ret = new IdentityHashMap<>(connectProperties.size() + 1);
/* 412 */     double spaceL = 0.375D;
/* 413 */     double spaceR = 0.625D;
/*     */     
/* 415 */     ret.put(null, new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D));
/*     */     
/* 417 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/*     */       double start, end;
/*     */       AxisAlignedBB aabb;
/* 420 */       if (facing.func_176743_c() == EnumFacing.AxisDirection.NEGATIVE) {
/* 421 */         start = 0.0D;
/* 422 */         end = 0.375D;
/*     */       } else {
/* 424 */         start = 0.625D;
/* 425 */         end = 1.0D;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 430 */       if (facing.func_176740_k() == EnumFacing.Axis.X) {
/* 431 */         aabb = new AxisAlignedBB(start, 0.0D, 0.375D, end, 1.5D, 0.625D);
/*     */       } else {
/* 433 */         aabb = new AxisAlignedBB(0.375D, 0.0D, start, 0.625D, 1.5D, end);
/*     */       } 
/*     */       
/* 436 */       ret.put(connectProperties.get(facing), aabb);
/*     */     } 
/*     */     
/* 439 */     return ret;
/*     */   }
/*     */   
/*     */   public enum IC2FenceType implements IIdProvider, IExtBlockType {
/* 443 */     iron(true, 5.0F, 10.0F); private final float explosionResistance;
/*     */     
/*     */     IC2FenceType(boolean canBoost, float hardness, float explosionResistance) {
/* 446 */       this.canBoost = canBoost;
/* 447 */       this.hardness = hardness;
/* 448 */       this.explosionResistance = explosionResistance;
/*     */     }
/*     */     private final float hardness; public final boolean canBoost;
/*     */     
/*     */     public String getName() {
/* 453 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 458 */       return ordinal();
/*     */     }
/*     */ 
/*     */     
/*     */     public float getHardness() {
/* 463 */       return this.hardness;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getExplosionResistance() {
/* 468 */       return this.explosionResistance;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 476 */   public static final Map<EnumFacing, IProperty<Boolean>> connectProperties = getConnectProperties();
/*     */   
/*     */   private static final double halfThickness = 0.125D;
/*     */   private static final double height = 1.5D;
/* 480 */   private static final Map<IProperty<Boolean>, AxisAlignedBB> aabbs = getAabbs();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockIC2Fence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */