/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.IExtBlockType;
/*     */ import ic2.core.item.block.ItemBlockSheet;
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.profile.NotClassic;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.Ic2BlockPos;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockSheet
/*     */   extends BlockMultiID<BlockSheet.SheetType>
/*     */ {
/*     */   public static BlockSheet create() {
/*  34 */     return BlockMultiID.<SheetType, BlockSheet>create(BlockSheet.class, SheetType.class, new Object[0]);
/*     */   }
/*     */   
/*     */   public BlockSheet() {
/*  38 */     super(BlockName.sheet, Material.field_151594_q, (Class)ItemBlockSheet.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d(IBlockState state) {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  53 */     return aabb;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_185477_a(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
/*  59 */     if (getType(state) == SheetType.wool && 
/*  60 */       entityIn instanceof EntityPlayer && (entityIn.func_70093_af() || entityIn.field_70163_u < pos.func_177956_o() + aabb.field_72337_e - entityIn.field_70138_W)) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     super.func_185477_a(state, worldIn, pos, entityBox, collidingBoxes, entityIn, isActualState);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_180646_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  70 */     AxisAlignedBB aabb = super.func_180646_a(state, world, pos);
/*  71 */     switch (getType(state)) {
/*     */       case resin:
/*  73 */         return null;
/*     */     } 
/*  75 */     return aabb;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  80 */     return isValidPosition(world, pos, func_176203_a(stack.func_77952_i()));
/*     */   }
/*     */   
/*     */   private boolean isValidPosition(World world, BlockPos pos, IBlockState state) {
/*  84 */     switch (getType(state)) {
/*     */       case resin:
/*  86 */         return isNormalCubeBelow(world, pos);
/*     */       case rubber:
/*  88 */         for (EnumFacing facing : EnumFacing.field_176754_o) {
/*  89 */           state = world.func_180495_p(pos.func_177972_a(facing));
/*     */           
/*  91 */           if (state == BlockName.sheet.getBlockState(SheetType.rubber) || state
/*  92 */             .func_177230_c().isNormalCube(state, (IBlockAccess)world, pos)) {
/*  93 */             return true;
/*     */           }
/*     */         } 
/*     */         
/*  97 */         return isNormalCubeBelow(world, pos);
/*     */       case wool:
/*  99 */         return true;
/* 100 */     }  return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isNormalCubeBelow(World world, BlockPos pos) {
/* 105 */     pos = pos.func_177977_b();
/* 106 */     IBlockState state = world.func_180495_p(pos);
/*     */     
/* 108 */     return state.func_177230_c().isNormalCube(state, (IBlockAccess)world, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
/* 113 */     if (!isValidPosition(world, pos, state)) {
/* 114 */       world.func_175698_g(pos);
/* 115 */       func_176226_b(world, pos, state, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180634_a(World world, BlockPos pos, IBlockState state, Entity entity) {
/* 121 */     switch (getType(state)) {
/*     */       case resin:
/* 123 */         entity.field_70143_R = (float)(entity.field_70143_R * 0.75D);
/* 124 */         entity.field_70159_w *= 0.6D;
/* 125 */         entity.field_70181_x *= 0.85D;
/* 126 */         entity.field_70179_y *= 0.6D;
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case rubber:
/* 134 */         if (world.func_175677_d(pos.func_177977_b(), false)) {
/*     */           return;
/*     */         }
/*     */         
/* 138 */         if (entity instanceof net.minecraft.entity.EntityLivingBase && !canSupportWeight(world, pos)) {
/* 139 */           world.func_175698_g(pos);
/*     */           
/*     */           return;
/*     */         } 
/* 143 */         if (entity.field_70181_x <= -0.4D) {
/* 144 */           entity.field_70143_R = 0.0F;
/* 145 */           entity.field_70159_w *= 1.1D;
/* 146 */           entity.field_70179_y *= 1.1D;
/*     */           
/* 148 */           if (entity instanceof net.minecraft.entity.EntityLivingBase) {
/* 149 */             if (entity instanceof EntityPlayer && IC2.keyboard.isJumpKeyDown((EntityPlayer)entity)) {
/* 150 */               entity.field_70181_x *= -1.3D; break;
/* 151 */             }  if (entity instanceof EntityPlayer && ((EntityPlayer)entity).func_70093_af()) {
/* 152 */               entity.field_70181_x *= -0.1D; break;
/*     */             } 
/* 154 */             entity.field_70181_x *= -0.8D;
/*     */             break;
/*     */           } 
/* 157 */           entity.field_70181_x *= -0.8D;
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case wool:
/* 164 */         entity.field_70143_R = (float)(entity.field_70143_R * 0.95D);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean canSupportWeight(World world, BlockPos pos) {
/* 170 */     int maxRange = 16;
/* 171 */     Ic2BlockPos cPos = new Ic2BlockPos();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     for (EnumFacing axis : positiveHorizontalFacings) {
/* 180 */       for (int dir = -1; dir <= 1; dir += 2) {
/* 181 */         cPos.set((Vec3i)pos);
/* 182 */         boolean supported = false;
/*     */         
/* 184 */         for (int i = 0; i < 16; i++) {
/* 185 */           cPos.move(axis, dir);
/* 186 */           IBlockState state = cPos.getBlockState((IBlockAccess)world);
/*     */           
/* 188 */           if (state.func_177230_c().isNormalCube(state, (IBlockAccess)world, (BlockPos)cPos)) {
/* 189 */             supported = true; break;
/*     */           } 
/* 191 */           if (state != BlockName.sheet.getBlockState(SheetType.rubber)) {
/*     */             break;
/*     */           }
/* 194 */           cPos.moveDown();
/*     */           
/* 196 */           IBlockState baseState = cPos.getBlockState((IBlockAccess)world);
/*     */           
/* 198 */           if (baseState.func_177230_c().isNormalCube(baseState, (IBlockAccess)world, (BlockPos)cPos)) {
/* 199 */             supported = true;
/*     */             
/*     */             break;
/*     */           } 
/* 203 */           cPos.moveUp();
/*     */         } 
/*     */ 
/*     */         
/* 207 */         if (!supported)
/*     */           break; 
/* 209 */         if (dir == 1) {
/* 210 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/* 220 */     switch (getType(state)) {
/*     */       case resin:
/* 222 */         if (IC2.random.nextInt(5) != 0) {
/* 223 */           List<ItemStack> ret = new ArrayList<>();
/* 224 */           ret.add(ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin));
/*     */           
/* 226 */           return ret;
/*     */         } 
/* 228 */         return new ArrayList<>();
/*     */     } 
/*     */     
/* 231 */     return super.getDrops(world, pos, state, fortune);
/*     */   }
/*     */   
/*     */   public enum SheetType
/*     */     implements IIdProvider, IExtBlockType {
/* 236 */     resin(1.6F, 0.5F),
/* 237 */     rubber(0.8F, 2.0F),
/* 238 */     wool(0.8F, 0.8F);
/*     */     
/*     */     private final float hardness;
/*     */     
/*     */     private final float explosionResistance;
/*     */ 
/*     */     
/*     */     SheetType(float hardness, float explosionResistance) {
/*     */       this.hardness = hardness;
/*     */       this.explosionResistance = explosionResistance;
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return name();
/*     */     }
/*     */     
/*     */     public int getId() {
/*     */       return ordinal();
/*     */     }
/*     */     
/*     */     public float getHardness() {
/*     */       return this.hardness;
/*     */     }
/*     */     
/*     */     public float getExplosionResistance() {
/*     */       return this.explosionResistance;
/*     */     }
/*     */     
/* 266 */     public static SheetType[] values = values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/* 272 */   private static final AxisAlignedBB aabb = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
/* 273 */   private static final EnumFacing[] positiveHorizontalFacings = new EnumFacing[] { EnumFacing.EAST, EnumFacing.SOUTH };
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockSheet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */