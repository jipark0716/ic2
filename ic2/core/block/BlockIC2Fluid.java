/*     */ package ic2.core.block;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.type.ResourceBlock;
/*     */ import ic2.core.init.BlocksItems;
/*     */ import ic2.core.item.block.ItemBlockIC2;
/*     */ import ic2.core.profile.Version;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.IBlockModelProvider;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.BlockFluidClassic;
/*     */ import net.minecraftforge.fluids.Fluid;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class BlockIC2Fluid extends BlockFluidClassic implements IBlockModelProvider {
/*     */   protected final Fluid fluid;
/*     */   
/*     */   public BlockIC2Fluid(FluidName name, Fluid fluid, Material material, int color) {
/*  43 */     super(fluid, material);
/*     */     
/*  45 */     func_149663_c(name.name());
/*  46 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */     
/*  48 */     this.fluid = fluid;
/*  49 */     this.color = color;
/*     */     
/*  51 */     if (this.density <= FluidRegistry.WATER.getDensity()) {
/*  52 */       this.displacements.put(Blocks.field_150355_j, Boolean.valueOf(false));
/*  53 */       this.displacements.put(Blocks.field_150358_i, Boolean.valueOf(false));
/*     */     } 
/*     */     
/*  56 */     if (this.density <= FluidRegistry.LAVA.getDensity()) {
/*  57 */       this.displacements.put(Blocks.field_150353_l, Boolean.valueOf(false));
/*  58 */       this.displacements.put(Blocks.field_150356_k, Boolean.valueOf(false));
/*     */     } 
/*     */     
/*  61 */     ResourceLocation regName = IC2.getIdentifier(name.name());
/*  62 */     BlocksItems.registerBlock((Block)this, regName);
/*  63 */     BlocksItems.registerItem((Item)new ItemBlockIC2((Block)this), regName);
/*     */   }
/*     */   private final int color;
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(BlockName name) {
/*  69 */     BlockBase.registerDefaultItemModel((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149666_a(CreativeTabs tab, NonNullList<ItemStack> items) {
/*  74 */     boolean defaultState = Version.shouldEnable(FluidName.class);
/*     */     
/*     */     try {
/*  77 */       if (Version.shouldEnable(FluidName.class.getField(super.func_149739_a().substring(5)), defaultState)) {
/*  78 */         items.add(new ItemStack((Block)this));
/*     */       }
/*  80 */     } catch (NoSuchFieldException e) {
/*  81 */       throw new RuntimeException("Impossible missing enum field!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180650_b(World world, BlockPos pos, IBlockState state, Random random) {
/*  87 */     super.func_180650_b(world, pos, state, random);
/*     */     
/*  89 */     if (!world.field_72995_K) {
/*  90 */       if (this.fluid == FluidName.pahoehoe_lava.getInstance()) {
/*  91 */         if (isSourceBlock((IBlockAccess)world, pos) && world
/*  92 */           .func_175671_l(pos) >= world.field_73012_v.nextInt(120)) {
/*  93 */           world.func_175656_a(pos, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.basalt));
/*  94 */         } else if (!hardenFromNeighbors(world, pos)) {
/*  95 */           world.func_175684_a(pos, (Block)this, func_149738_a(world));
/*     */         } 
/*  97 */       } else if (this.fluid == FluidName.hot_water.getInstance()) {
/*  98 */         if (isSourceBlock((IBlockAccess)world, pos) && !isLavaBlock(world.func_180495_p(pos.func_177979_c(2)).func_177230_c()) && world.func_180495_p(pos.func_177977_b()).func_177230_c() != this && world.field_73012_v.nextInt(60) == 0) {
/*  99 */           world.func_175656_a(pos, Blocks.field_150358_i.func_176223_P());
/*     */         } else {
/* 101 */           world.func_175684_a(pos, (Block)this, func_149738_a(world));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isLavaBlock(Block block) {
/* 108 */     return (block == Blocks.field_150353_l || block == Blocks.field_150356_k);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_189540_a(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos) {
/* 113 */     super.func_189540_a(state, world, pos, block, neighborPos);
/*     */     
/* 115 */     hardenFromNeighbors(world, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_176213_c(World world, BlockPos pos, IBlockState state) {
/* 120 */     super.func_176213_c(world, pos, state);
/*     */     
/* 122 */     hardenFromNeighbors(world, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 127 */     if (world.field_72995_K)
/*     */       return; 
/* 129 */     if (this.fluid == FluidName.biogas.getInstance() || this.fluid == FluidName.air.getInstance()) {
/* 130 */       world.func_175698_g(pos);
/* 131 */       world.func_184133_a(null, pos, SoundEvents.field_187616_bj, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_180634_a(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 137 */     func_176199_a(worldIn, pos, entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_176199_a(World world, BlockPos pos, Entity entity) {
/* 142 */     if (world.field_72995_K)
/*     */       return; 
/* 144 */     if (this.fluid == FluidName.pahoehoe_lava.getInstance()) {
/* 145 */       entity.func_70015_d(10);
/* 146 */     } else if (this.fluid == FluidName.hot_coolant.getInstance()) {
/* 147 */       entity.func_70015_d(30);
/*     */     } 
/*     */     
/* 150 */     if (entity instanceof EntityLivingBase) {
/* 151 */       EntityLivingBase living = (EntityLivingBase)entity;
/*     */       
/* 153 */       if (this.fluid == FluidName.construction_foam.getInstance()) {
/* 154 */         addPotion(living, MobEffects.field_76421_d, 300, 2);
/* 155 */       } else if (this.fluid == FluidName.uu_matter.getInstance()) {
/* 156 */         addPotion(living, MobEffects.field_76428_l, 100, 1);
/* 157 */       } else if (this.fluid == FluidName.steam.getInstance() || this.fluid == FluidName.superheated_steam.getInstance()) {
/* 158 */         addPotion(living, MobEffects.field_76440_q, 300, 0);
/* 159 */       } else if (this.fluid == FluidName.hot_water.getInstance()) {
/*     */         Potion potion;
/*     */         
/* 162 */         if (((EntityLivingBase)entity).func_70662_br()) {
/* 163 */           potion = MobEffects.field_82731_v;
/*     */         } else {
/* 165 */           potion = MobEffects.field_76428_l;
/*     */         } 
/*     */         
/* 168 */         addPotion(living, potion, 100, IC2.random.nextInt(2));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addPotion(EntityLivingBase entity, Potion potion, int duration, int amplifier) {
/* 174 */     if (entity.func_70644_a(potion))
/*     */       return; 
/* 176 */     entity.func_70690_d(new PotionEffect(potion, duration, amplifier, true, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_149739_a() {
/* 181 */     return "ic2." + super.func_149739_a().substring(5);
/*     */   }
/*     */   
/*     */   public int getColor() {
/* 185 */     return this.color;
/*     */   }
/*     */   
/*     */   private boolean hardenFromNeighbors(World world, BlockPos pos) {
/* 189 */     if (world.field_72995_K) return false;
/*     */     
/* 191 */     if (this.fluid == FluidName.pahoehoe_lava.getInstance()) {
/* 192 */       for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 193 */         LiquidUtil.LiquidData data = LiquidUtil.getLiquid(world, pos.func_177972_a(dir));
/*     */         
/* 195 */         if (data != null && data.liquid
/* 196 */           .getTemperature() <= this.fluid.getTemperature() / 4) {
/* 197 */           if (isSourceBlock((IBlockAccess)world, pos)) {
/* 198 */             world.func_175656_a(pos, BlockName.resource.getBlockState((IIdProvider)ResourceBlock.basalt));
/*     */           } else {
/* 200 */             world.func_175698_g(pos);
/*     */           } 
/*     */           
/* 203 */           return true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 208 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockIC2Fluid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */