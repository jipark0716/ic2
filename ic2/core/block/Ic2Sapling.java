/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.BlocksItems;
/*     */ import ic2.core.item.block.ItemBlockIC2;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.IBlockModelProvider;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockBush;
/*     */ import net.minecraft.block.IGrowable;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.common.EnumPlantType;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ic2Sapling
/*     */   extends BlockBush
/*     */   implements IBlockModelProvider, IGrowable
/*     */ {
/*     */   public Ic2Sapling() {
/*  32 */     func_149711_c(0.0F);
/*  33 */     func_149672_a(SoundType.field_185850_c);
/*  34 */     func_149663_c(BlockName.sapling.name());
/*  35 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */     
/*  37 */     ResourceLocation name = IC2.getIdentifier(BlockName.sapling.name());
/*  38 */     BlocksItems.registerBlock((Block)this, name);
/*  39 */     BlocksItems.registerItem((Item)new ItemBlockIC2((Block)this), name);
/*  40 */     BlockName.sapling.setInstance((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(BlockName name) {
/*  46 */     BlockBase.registerDefaultItemModel((Block)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_149739_a() {
/*  51 */     return "ic2." + super.func_149739_a().substring(5) + ".rubber";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  56 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_180650_b(World world, BlockPos pos, IBlockState state, Random random) {
/*  64 */     if (world.field_72995_K)
/*     */       return; 
/*  66 */     if (!func_180671_f(world, pos, state)) {
/*  67 */       func_176226_b(world, pos, state, 0);
/*  68 */       world.func_175698_g(pos);
/*     */       
/*     */       return;
/*     */     } 
/*  72 */     if (world.func_175671_l(pos.func_177984_a()) >= 9 && random.nextInt(30) == 0) {
/*  73 */       func_176474_b(world, random, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_176474_b(World world, Random rand, BlockPos pos, IBlockState state) {
/*  82 */     (new WorldGenRubTree(true)).grow(world, pos, rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_180651_a(IBlockState state) {
/*  90 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_176473_a(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/*  95 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_180670_a(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
/* 108 */     return EnumPlantType.Plains;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\Ic2Sapling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */