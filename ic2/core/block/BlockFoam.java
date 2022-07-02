/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.ResourceBlock;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.util.StackUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
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
/*     */ public class BlockFoam
/*     */   extends BlockMultiID<BlockFoam.FoamType>
/*     */ {
/*     */   public static BlockFoam create() {
/*  39 */     return BlockMultiID.<FoamType, BlockFoam>create(BlockFoam.class, FoamType.class, new Object[0]);
/*     */   }
/*     */   
/*     */   private BlockFoam() {
/*  43 */     super(BlockName.foam, Material.field_151580_n);
/*     */     
/*  45 */     func_149675_a(true);
/*  46 */     func_149711_c(0.01F);
/*  47 */     func_149752_b(10.0F);
/*  48 */     func_149672_a(SoundType.field_185854_g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  65 */     return true;
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
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB func_180646_a(IBlockState blockState, IBlockAccess world, BlockPos pos) {
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180645_a(World world, BlockPos pos, IBlockState state, Random random) {
/*  94 */     int tickSpeed = world.func_82736_K().func_180263_c("randomTickSpeed");
/*  95 */     if (tickSpeed <= 0) throw new IllegalStateException("Foam was randomly ticked when world " + world + " isn't ticking?");
/*     */     
/*  97 */     FoamType type = (FoamType)state.func_177229_b((IProperty)this.typeProperty);
/*  98 */     float chance = getHardenChance(world, pos, state, type) * 4096.0F / tickSpeed;
/*     */     
/* 100 */     if (random.nextFloat() < chance) {
/* 101 */       world.func_175656_a(pos, ((FoamType)state.func_177229_b((IProperty)this.typeProperty)).getResult());
/*     */     }
/*     */   }
/*     */   
/*     */   public static float getHardenChance(World world, BlockPos pos, IBlockState state, FoamType type) {
/* 106 */     int light = world.func_175671_l(pos);
/*     */     
/* 108 */     if (!state.func_185916_f() && state
/* 109 */       .func_177230_c().getLightOpacity(state, (IBlockAccess)world, pos) == 0) {
/* 110 */       for (EnumFacing side : EnumFacing.field_82609_l) {
/* 111 */         light = Math.max(light, world.func_175721_c(pos.func_177972_a(side), false));
/*     */       }
/*     */     }
/*     */     
/* 115 */     int avgTime = type.hardenTime * (16 - light);
/*     */     
/* 117 */     return 1.0F / (avgTime * 20);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/* 127 */     if (StackUtil.consume(player, hand, StackUtil.sameItem((Block)Blocks.field_150354_m), 1)) {
/* 128 */       world.func_175656_a(pos, ((FoamType)state.func_177229_b((IProperty)this.typeProperty)).getResult());
/*     */ 
/*     */       
/* 131 */       return true;
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/* 139 */     return ((FoamType)state.func_177229_b((IProperty)this.typeProperty)).getDrops();
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
/*     */   public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public enum FoamType implements IIdProvider {
/* 156 */     normal(300),
/* 157 */     reinforced(600);
/*     */     
/*     */     FoamType(int hardenTime) {
/* 160 */       this.hardenTime = hardenTime;
/*     */     }
/*     */     public final int hardenTime;
/*     */     
/*     */     public String getName() {
/* 165 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 170 */       return ordinal();
/*     */     }
/*     */     public List<ItemStack> getDrops() {
/*     */       List<ItemStack> ret;
/* 174 */       switch (this) {
/*     */         case normal:
/* 176 */           return new ArrayList<>();
/*     */         case reinforced:
/* 178 */           ret = new ArrayList<>();
/* 179 */           ret.add(BlockName.scaffold.getItemStack(BlockScaffold.ScaffoldType.iron));
/*     */           
/* 181 */           return ret;
/*     */       } 
/*     */       
/* 184 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getResult() {
/* 189 */       switch (this) {
/*     */         case normal:
/* 191 */           return BlockName.wall.getBlockState((IIdProvider)BlockWall.defaultColor);
/*     */         case reinforced:
/* 193 */           return BlockName.resource.getBlockState((IIdProvider)ResourceBlock.reinforced_stone);
/*     */       } 
/* 195 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockFoam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */