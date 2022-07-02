/*     */ package ic2.core.item.tfbp;
/*     */ 
/*     */ import ic2.core.block.machine.tileentity.TileEntityTerra;
/*     */ import ic2.core.ref.BlockName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirectional;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
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
/*     */ class Cultivation
/*     */   extends TerraformerBase
/*     */ {
/*     */   void init() {
/*  32 */     plants.add(Blocks.field_150329_H.func_176223_P().func_177226_a((IProperty)BlockTallGrass.field_176497_a, (Comparable)BlockTallGrass.EnumType.GRASS));
/*  33 */     plants.add(Blocks.field_150329_H.func_176223_P().func_177226_a((IProperty)BlockTallGrass.field_176497_a, (Comparable)BlockTallGrass.EnumType.GRASS));
/*  34 */     plants.add(Blocks.field_150329_H.func_176223_P().func_177226_a((IProperty)BlockTallGrass.field_176497_a, (Comparable)BlockTallGrass.EnumType.FERN));
/*  35 */     plants.add(Blocks.field_150328_O.func_176223_P());
/*  36 */     plants.add(Blocks.field_150327_N.func_176223_P());
/*     */     
/*  38 */     plants.add(Blocks.field_150398_cm.func_176223_P().func_177226_a((IProperty)BlockDoublePlant.field_176493_a, (Comparable)BlockDoublePlant.EnumPlantType.GRASS));
/*  39 */     plants.add(Blocks.field_150398_cm.func_176223_P().func_177226_a((IProperty)BlockDoublePlant.field_176493_a, (Comparable)BlockDoublePlant.EnumPlantType.ROSE));
/*  40 */     plants.add(Blocks.field_150398_cm.func_176223_P().func_177226_a((IProperty)BlockDoublePlant.field_176493_a, (Comparable)BlockDoublePlant.EnumPlantType.SUNFLOWER));
/*  41 */     for (BlockPlanks.EnumType type : BlockSapling.field_176480_a.func_177700_c())
/*     */     {
/*  43 */       plants.add(Blocks.field_150345_g.func_176223_P().func_177226_a((IProperty)BlockSapling.field_176480_a, (Comparable)type));
/*     */     }
/*  45 */     plants.add(Blocks.field_150464_aj.func_176223_P());
/*  46 */     plants.add(Blocks.field_150337_Q.func_176223_P());
/*  47 */     plants.add(Blocks.field_150338_P.func_176223_P());
/*  48 */     plants.add(Blocks.field_150423_aK.func_176223_P());
/*  49 */     plants.add(Blocks.field_150440_ba.func_176223_P());
/*     */     
/*  51 */     plants.add(BlockName.sapling.getInstance().func_176223_P());
/*     */   }
/*     */ 
/*     */   
/*     */   boolean terraform(World world, BlockPos pos) {
/*  56 */     pos = TileEntityTerra.getFirstSolidBlockFrom(world, pos, 10);
/*  57 */     if (pos == null) return false;
/*     */     
/*  59 */     if (TileEntityTerra.switchGround(world, pos, (Block)Blocks.field_150354_m, Blocks.field_150346_d.func_176223_P(), true))
/*  60 */       return true; 
/*  61 */     if (TileEntityTerra.switchGround(world, pos, Blocks.field_150377_bs, Blocks.field_150346_d.func_176223_P(), true)) {
/*     */       
/*  63 */       int i = 4;
/*  64 */       while (--i > 0 && TileEntityTerra.switchGround(world, pos, Blocks.field_150377_bs, Blocks.field_150346_d.func_176223_P(), true));
/*     */     } 
/*     */     
/*  67 */     Block block = world.func_180495_p(pos).func_177230_c();
/*     */     
/*  69 */     if (block == Blocks.field_150346_d) {
/*  70 */       world.func_175656_a(pos, Blocks.field_150349_c.func_176223_P());
/*  71 */       return true;
/*     */     } 
/*     */     
/*  74 */     if (block == Blocks.field_150349_c) return growPlantsOn(world, pos);
/*     */     
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean growPlantsOn(World world, BlockPos pos) {
/*  84 */     BlockPos above = pos.func_177984_a();
/*  85 */     IBlockState state = world.func_180495_p(above);
/*  86 */     Block block = state.func_177230_c();
/*     */     
/*  88 */     if (block.isAir(state, (IBlockAccess)world, above) || (block == Blocks.field_150329_H && world.field_73012_v.nextInt(4) == 0)) {
/*     */       
/*  90 */       IBlockState plant = pickRandomPlant(world.field_73012_v);
/*     */       
/*  92 */       if (plant.func_177228_b().containsKey(BlockDirectional.field_176387_N))
/*     */       {
/*  94 */         plant = plant.func_177226_a((IProperty)BlockDirectional.field_176387_N, (Comparable)EnumFacing.field_176754_o[world.field_73012_v.nextInt(EnumFacing.field_176754_o.length)]);
/*     */       }
/*     */       
/*  97 */       if (plant.func_177230_c() instanceof net.minecraft.block.BlockCrops) {
/*  98 */         world.func_175656_a(pos, Blocks.field_150458_ak.func_176223_P());
/*  99 */       } else if (plant.func_177230_c() == Blocks.field_150398_cm) {
/* 100 */         plant = plant.func_177226_a((IProperty)BlockDoublePlant.field_176492_b, (Comparable)BlockDoublePlant.EnumBlockHalf.LOWER);
/* 101 */         world.func_175656_a(above, plant.func_177226_a((IProperty)BlockDoublePlant.field_176492_b, (Comparable)BlockDoublePlant.EnumBlockHalf.LOWER));
/* 102 */         world.func_175656_a(above.func_177984_a(), plant.func_177226_a((IProperty)BlockDoublePlant.field_176492_b, (Comparable)BlockDoublePlant.EnumBlockHalf.UPPER));
/* 103 */         return true;
/*     */       } 
/*     */       
/* 106 */       world.func_175656_a(above, plant);
/*     */       
/* 108 */       return true;
/*     */     } 
/*     */     
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IBlockState pickRandomPlant(Random random) {
/* 118 */     return plants.get(random.nextInt(plants.size()));
/*     */   }
/*     */   
/* 121 */   static ArrayList<IBlockState> plants = new ArrayList<>();
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tfbp\Cultivation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */