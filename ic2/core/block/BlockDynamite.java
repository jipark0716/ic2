/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.ref.BlockName;
/*     */ import java.util.Random;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockTorch;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockDynamite
/*     */   extends BlockBase
/*     */ {
/*     */   public BlockDynamite() {
/*  44 */     super(BlockName.dynamite, MaterialIC2TNT.instance, (Function<Block, Item>)null);
/*     */     
/*  46 */     func_149675_a(true);
/*  47 */     func_149711_c(0.0F);
/*  48 */     func_149672_a(SoundType.field_185850_c);
/*  49 */     func_149647_a(null);
/*  50 */     func_180632_j(func_176223_P().func_177226_a(linked, Boolean.valueOf(false)).func_177226_a((IProperty)BlockTorch.field_176596_a, (Comparable)EnumFacing.UP));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer func_180661_e() {
/*  55 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)BlockTorch.field_176596_a, linked });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  61 */     return Blocks.field_150478_aa.func_176223_P().func_177226_a((IProperty)BlockTorch.field_176596_a, state.func_177229_b((IProperty)BlockTorch.field_176596_a)).func_185900_c(source, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149686_d(IBlockState state) {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public BlockRenderLayer func_180664_k() {
/*  77 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_176196_c(World world, BlockPos pos) {
/*  84 */     for (EnumFacing dir : BlockTorch.field_176596_a.func_177700_c()) {
/*  85 */       if (world.func_175677_d(pos.func_177972_a(dir.func_176734_d()), false)) return true;
/*     */     
/*     */     } 
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState func_180642_a(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  94 */     if (facing == EnumFacing.DOWN || !world.func_175677_d(pos.func_177972_a(facing.func_176734_d()), false)) {
/*  95 */       for (EnumFacing facing2 : BlockTorch.field_176596_a.func_177700_c()) {
/*  96 */         if (world.func_175677_d(pos.func_177972_a(facing2.func_176734_d()), false)) {
/*  97 */           facing = facing2;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 103 */     return func_176223_P().func_177226_a((IProperty)BlockTorch.field_176596_a, (Comparable)facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_176201_c(IBlockState state) {
/* 108 */     return ((EnumFacing)state.func_177229_b((IProperty)BlockTorch.field_176596_a)).ordinal() << 1 | (((Boolean)state.func_177229_b(linked)).booleanValue() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState func_176203_a(int meta) {
/* 113 */     return func_176223_P().func_177226_a(linked, Boolean.valueOf(((meta & 0x1) != 0))).func_177226_a((IProperty)BlockTorch.field_176596_a, (Comparable)EnumFacing.field_82609_l[meta >> 1]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 119 */     checkPlacement(world, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
/* 125 */     checkPlacement(world, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
/* 131 */     checkPlacement(world, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149745_a(Random random) {
/* 139 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_180651_a(IBlockState state) {
/* 144 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180652_a(World world, BlockPos pos, Explosion explosion) {
/* 152 */     explode(world, pos, (explosion != null) ? explosion.func_94613_c() : null, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
/* 160 */     if (!world.field_72995_K) explode(world, pos, (EntityLivingBase)player, false);
/*     */     
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   private void checkPlacement(World world, BlockPos pos, IBlockState state) {
/* 166 */     if (world.field_72995_K)
/*     */       return; 
/* 168 */     if (world.func_175640_z(pos)) {
/* 169 */       explode(world, pos, (EntityLivingBase)null, false);
/* 170 */     } else if (!world.func_175677_d(pos.func_177972_a(((EnumFacing)state.func_177229_b((IProperty)BlockTorch.field_176596_a)).func_176734_d()), false)) {
/* 171 */       world.func_175698_g(pos);
/* 172 */       func_176226_b(world, pos, state, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void explode(World world, BlockPos pos, EntityLivingBase player, boolean byExplosion) {
/* 177 */     world.func_175698_g(pos);
/*     */     
/* 179 */     EntityDynamite entity = new EntityStickyDynamite(world, pos.func_177958_n() + 0.5D, pos.func_177956_o() + 0.5D, (pos.func_177952_p() + 0.5F));
/* 180 */     entity.owner = player;
/* 181 */     entity.fuse = byExplosion ? 5 : 40;
/*     */     
/* 183 */     world.func_72838_d(entity);
/* 184 */     world.func_184133_a(null, pos, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RayTraceResult func_180636_a(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
/* 193 */     return Blocks.field_150478_aa.func_180636_a(state, world, pos, start, end);
/*     */   }
/*     */   
/* 196 */   public static final IProperty<Boolean> linked = (IProperty<Boolean>)PropertyBool.func_177716_a("linked");
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockDynamite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */