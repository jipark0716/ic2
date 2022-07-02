/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.BlocksItems;
/*     */ import ic2.core.item.block.ItemIc2Leaves;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.IBlockModelProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMap;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ic2Leaves
/*     */   extends BlockLeaves
/*     */   implements IBlockModelProvider
/*     */ {
/*     */   public Ic2Leaves() {
/*  46 */     func_149663_c(BlockName.leaves.name());
/*  47 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */     
/*  49 */     ResourceLocation name = IC2.getIdentifier(BlockName.leaves.name());
/*  50 */     BlocksItems.registerBlock((Block)this, name);
/*  51 */     BlocksItems.registerItem((Item)new ItemIc2Leaves((Block)this), name);
/*  52 */     BlockName.leaves.setInstance((Block)this);
/*     */     
/*  54 */     func_180632_j(this.field_176227_L.func_177621_b()
/*  55 */         .func_177226_a((IProperty)field_176236_b, Boolean.valueOf(true))
/*  56 */         .func_177226_a((IProperty)field_176237_a, Boolean.valueOf(true))
/*  57 */         .func_177226_a((IProperty)typeProperty, LeavesType.rubber));
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(BlockName name) {
/*  63 */     StateMap stateMap = (new StateMap.Builder()).func_178442_a(new IProperty[] { (IProperty)field_176236_b, (IProperty)field_176237_a }).func_178441_a();
/*  64 */     ModelLoader.setCustomStateMapper((Block)this, (IStateMapper)stateMap);
/*     */     
/*  66 */     List<IBlockState> states = new ArrayList<>(typeProperty.func_177700_c().size());
/*     */     
/*  68 */     for (LeavesType type : LeavesType.values) {
/*  69 */       states.add(getDropState(func_176223_P().func_177226_a((IProperty)typeProperty, type)));
/*     */     }
/*     */     
/*  72 */     BlockBase.registerItemModels((Block)this, states, (IStateMapper)stateMap);
/*     */   }
/*     */   
/*     */   private static IBlockState getDropState(IBlockState state) {
/*  76 */     return state.func_177226_a((IProperty)field_176236_b, Boolean.valueOf(false))
/*  77 */       .func_177226_a((IProperty)field_176237_a, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer func_180661_e() {
/*  82 */     return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)field_176236_b, (IProperty)field_176237_a, (IProperty)typeProperty });
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState func_176203_a(int meta) {
/*  87 */     boolean checkDecay = ((meta & 0x8) != 0);
/*  88 */     boolean decayable = ((meta & 0x4) != 0);
/*     */     
/*  90 */     meta &= 0x3;
/*     */ 
/*     */ 
/*     */     
/*  94 */     IBlockState ret = func_176223_P().func_177226_a((IProperty)field_176236_b, Boolean.valueOf(checkDecay)).func_177226_a((IProperty)field_176237_a, Boolean.valueOf(decayable));
/*     */     
/*  96 */     if (meta < LeavesType.values.length) {
/*  97 */       ret = ret.func_177226_a((IProperty)typeProperty, LeavesType.values[meta]);
/*     */     }
/*     */     
/* 100 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_176201_c(IBlockState state) {
/* 105 */     int ret = 0;
/*     */     
/* 107 */     if (((Boolean)state.func_177229_b((IProperty)field_176236_b)).booleanValue()) ret |= 0x8; 
/* 108 */     if (((Boolean)state.func_177229_b((IProperty)field_176237_a)).booleanValue()) ret |= 0x4;
/*     */     
/* 110 */     ret |= ((LeavesType)state.func_177229_b((IProperty)typeProperty)).ordinal();
/*     */     
/* 112 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_149662_c(IBlockState state) {
/* 117 */     return Blocks.field_150362_t.func_149662_c(state);
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public BlockRenderLayer func_180664_k() {
/* 123 */     return Blocks.field_150362_t.func_180664_k();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_176225_a(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/* 128 */     BlockPos nPos = pos.func_177972_a(side);
/*     */     
/* 130 */     return ((!func_149662_c(state) || world.func_180495_p(nPos) != state) && 
/* 131 */       !world.func_180495_p(nPos).doesSideBlockRendering(world, nPos, side.func_176734_d()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Item func_180660_a(IBlockState state, Random rand, int fortune) {
/* 136 */     return ((LeavesType)state.func_177229_b((IProperty)typeProperty)).getSapling().func_77973_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_180651_a(IBlockState state) {
/* 141 */     return ((LeavesType)state.func_177229_b((IProperty)typeProperty)).getSapling().func_77960_j();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int func_176232_d(IBlockState state) {
/* 146 */     return ((LeavesType)state.func_177229_b((IProperty)typeProperty)).saplingDropChance;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
/* 151 */     IBlockState state = getDropState(world.func_180495_p(pos));
/*     */     
/* 153 */     return Arrays.asList(new ItemStack[] { new ItemStack((Block)this, 1, func_176201_c(state)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_149666_a(CreativeTabs tab, NonNullList<ItemStack> list) {
/* 158 */     IBlockState state = getDropState(func_176223_P());
/*     */     
/* 160 */     for (LeavesType type : LeavesType.values) {
/* 161 */       list.add(new ItemStack((Block)this, 1, func_176201_c(state.func_177226_a((IProperty)typeProperty, type))));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPlanks.EnumType func_176233_b(int meta) {
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 187 */     return 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
/* 192 */     return 20;
/*     */   }
/*     */   
/*     */   public enum LeavesType implements IStringSerializable {
/* 196 */     rubber(35);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int saplingDropChance;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     LeavesType(int saplingDropChance) {
/*     */       this.saplingDropChance = saplingDropChance;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     private static final LeavesType[] values = values();
/*     */     public String func_176610_l() { return name(); }
/*     */     public ItemStack getSapling() { return new ItemStack(BlockName.sapling.getInstance()); } static {  }
/* 216 */   } public static final PropertyEnum<LeavesType> typeProperty = PropertyEnum.func_177709_a("type", LeavesType.class);
/*     */   private static final int checkDecayFlag = 8;
/*     */   private static final int decayableFlag = 4;
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\Ic2Leaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */