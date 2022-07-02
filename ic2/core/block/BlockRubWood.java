/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.item.type.MiscResourceType;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRubWood
/*     */   extends BlockBase
/*     */ {
/*     */   public BlockRubWood() {
/*  29 */     super(BlockName.rubber_wood, Material.field_151575_d);
/*     */     
/*  31 */     func_149675_a(true);
/*  32 */     func_149711_c(1.0F);
/*  33 */     func_149672_a(SoundType.field_185848_a);
/*     */     
/*  35 */     func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)stateProperty, RubberWoodState.plain_y));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer func_180661_e() {
/*  40 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)stateProperty });
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState func_176203_a(int meta) {
/*  45 */     if (meta >= 0 && meta < RubberWoodState.values.length) {
/*  46 */       return func_176223_P().func_177226_a((IProperty)stateProperty, RubberWoodState.values[meta]);
/*     */     }
/*  48 */     return func_176223_P();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_176201_c(IBlockState state) {
/*  54 */     return ((RubberWoodState)state.func_177229_b((IProperty)stateProperty)).ordinal();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState func_180642_a(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  60 */     IBlockState state = super.func_180642_a(world, pos, facing, hitX, hitY, hitZ, meta, placer);
/*     */     
/*  62 */     return state.func_177226_a((IProperty)stateProperty, getPlainAxisState(facing.func_176740_k()));
/*     */   }
/*     */   
/*     */   private static RubberWoodState getPlainAxisState(EnumFacing.Axis axis) {
/*  66 */     switch (axis) { case NORTH:
/*  67 */         return RubberWoodState.plain_x;
/*  68 */       case SOUTH: return RubberWoodState.plain_y;
/*  69 */       case WEST: return RubberWoodState.plain_z; }
/*  70 */      throw new IllegalArgumentException("invalid axis: " + axis);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180653_a(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  79 */     if (world.field_72995_K)
/*     */       return; 
/*  81 */     int count = func_149745_a(world.field_73012_v);
/*     */     
/*  83 */     for (int j1 = 0; j1 < count; j1++) {
/*  84 */       if (world.field_73012_v.nextFloat() <= chance) {
/*     */         
/*  86 */         Item item = func_180660_a(state, world.field_73012_v, fortune);
/*     */         
/*  88 */         if (item != null) {
/*  89 */           func_180635_a(world, pos, new ItemStack(item, 1, 0));
/*     */         }
/*     */         
/*  92 */         if (!((RubberWoodState)state.func_177229_b((IProperty)stateProperty)).isPlain() && world.field_73012_v.nextInt(6) == 0) {
/*  93 */           func_180635_a(world, pos, ItemName.misc_resource.getItemStack((Enum)MiscResourceType.resin));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_180663_b(World world, BlockPos pos, IBlockState state) {
/* 100 */     int range = 4;
/* 101 */     BlockPos.MutableBlockPos cPos = new BlockPos.MutableBlockPos();
/*     */     
/* 103 */     for (int y = -range; y <= range; y++) {
/* 104 */       for (int z = -range; z <= range; z++) {
/* 105 */         for (int x = -range; x <= range; x++) {
/* 106 */           cPos.func_181079_c(pos.func_177958_n() + x, pos.func_177956_o() + y, pos.func_177952_p() + z);
/*     */           
/* 108 */           IBlockState cState = world.func_180495_p((BlockPos)cPos);
/* 109 */           Block cBlock = cState.func_177230_c();
/*     */           
/* 111 */           if (cBlock.isLeaves(cState, (IBlockAccess)world, (BlockPos)cPos)) {
/* 112 */             cBlock.beginLeavesDecay(cState, world, new BlockPos((Vec3i)cPos));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
/* 121 */     if (random.nextInt(7) == 0) {
/* 122 */       RubberWoodState rwState = (RubberWoodState)state.func_177229_b((IProperty)stateProperty);
/* 123 */       if (!rwState.canRegenerate())
/*     */         return; 
/* 125 */       world.func_175656_a(pos, state.func_177226_a((IProperty)stateProperty, rwState.getWet()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumPushReaction func_149656_h(IBlockState state) {
/* 135 */     RubberWoodState rstate = (RubberWoodState)state.func_177229_b((IProperty)stateProperty);
/* 136 */     if (rstate == RubberWoodState.plain_x || rstate == RubberWoodState.plain_y || rstate == RubberWoodState.plain_z)
/*     */     {
/*     */       
/* 139 */       return EnumPushReaction.NORMAL;
/*     */     }
/* 141 */     return EnumPushReaction.BLOCK;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWood(IBlockAccess world, BlockPos pos) {
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 156 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 161 */     return 20;
/*     */   }
/*     */   
/*     */   public enum RubberWoodState implements IStringSerializable {
/* 165 */     plain_y((String)EnumFacing.Axis.Y, null, false),
/* 166 */     plain_x((String)EnumFacing.Axis.X, null, false),
/* 167 */     plain_z((String)EnumFacing.Axis.Z, null, false),
/* 168 */     dry_north((String)EnumFacing.Axis.Y, EnumFacing.NORTH, false),
/* 169 */     dry_south((String)EnumFacing.Axis.Y, EnumFacing.SOUTH, false),
/* 170 */     dry_west((String)EnumFacing.Axis.Y, EnumFacing.WEST, false),
/* 171 */     dry_east((String)EnumFacing.Axis.Y, EnumFacing.EAST, false),
/* 172 */     wet_north((String)EnumFacing.Axis.Y, EnumFacing.NORTH, true),
/* 173 */     wet_south((String)EnumFacing.Axis.Y, EnumFacing.SOUTH, true),
/* 174 */     wet_west((String)EnumFacing.Axis.Y, EnumFacing.WEST, true),
/* 175 */     wet_east((String)EnumFacing.Axis.Y, EnumFacing.EAST, true); public final EnumFacing.Axis axis; public final EnumFacing facing; public final boolean wet;
/*     */     RubberWoodState(EnumFacing.Axis axis, EnumFacing facing, boolean wet) {
/*     */       this.axis = axis;
/*     */       this.facing = facing;
/*     */       this.wet = wet;
/*     */     }
/*     */     public String func_176610_l() {
/*     */       return name();
/*     */     }
/*     */     
/*     */     public boolean isPlain() {
/*     */       return (this.facing == null);
/*     */     }
/*     */     
/*     */     public boolean canRegenerate() {
/*     */       return (!isPlain() && !this.wet);
/*     */     }
/*     */     
/*     */     public RubberWoodState getWet() {
/*     */       if (isPlain())
/*     */         return null; 
/*     */       if (this.wet)
/*     */         return this; 
/*     */       return values[ordinal() + 4];
/*     */     }
/*     */     
/*     */     public RubberWoodState getDry() {
/*     */       if (isPlain() || !this.wet)
/*     */         return this; 
/*     */       return values[ordinal() - 4];
/*     */     }
/*     */     
/*     */     public static RubberWoodState getWet(EnumFacing facing) {
/*     */       switch (facing) {
/*     */         case NORTH:
/*     */           return wet_north;
/*     */         case SOUTH:
/*     */           return wet_south;
/*     */         case WEST:
/*     */           return wet_west;
/*     */         case EAST:
/*     */           return wet_east;
/*     */       } 
/*     */       throw new IllegalArgumentException("incompatible facing: " + facing);
/*     */     }
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/* 225 */     private static final RubberWoodState[] values = values();
/*     */   }
/*     */   
/* 228 */   public static final PropertyEnum<RubberWoodState> stateProperty = PropertyEnum.func_177709_a("state", RubberWoodState.class);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockRubWood.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */