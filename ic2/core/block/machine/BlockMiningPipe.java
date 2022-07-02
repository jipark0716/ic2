/*     */ package ic2.core.block.machine;
/*     */ 
/*     */ import ic2.core.block.BlockMultiID;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.ref.BlockName;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockMiningPipe
/*     */   extends BlockMultiID<BlockMiningPipe.MiningPipeType>
/*     */ {
/*     */   public static BlockMiningPipe create() {
/*  21 */     return (BlockMiningPipe)BlockMultiID.create(BlockMiningPipe.class, MiningPipeType.class, new Object[0]);
/*     */   }
/*     */   
/*     */   public BlockMiningPipe() {
/*  25 */     super(BlockName.mining_pipe, Material.field_151573_f);
/*     */     
/*  27 */     func_149711_c(6.0F);
/*  28 */     func_149752_b(10.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_176196_c(World worldIn, BlockPos pos) {
/*  36 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
/*  41 */     MiningPipeType type = (MiningPipeType)getType(state);
/*  42 */     if (type == null) return true;
/*     */     
/*  44 */     return (type != MiningPipeType.pipe);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  50 */     MiningPipeType type = (MiningPipeType)getType(state);
/*  51 */     if (type == null) return super.func_185496_a(state, world, pos);
/*     */     
/*  53 */     return getAabb(type);
/*     */   }
/*     */   
/*     */   private AxisAlignedBB getAabb(MiningPipeType type) {
/*  57 */     switch (type) {
/*     */       case pipe:
/*  59 */         return pipeAabb;
/*     */     } 
/*     */     
/*  62 */     return field_185505_j;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_149717_k(IBlockState state) {
/*  68 */     return state.func_185917_h() ? 255 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_149686_d(IBlockState state) {
/*  74 */     MiningPipeType type = (MiningPipeType)getType(state);
/*  75 */     if (type == null) return super.func_149686_d(state);
/*     */     
/*  77 */     switch (type) {
/*     */       case pipe:
/*  79 */         return false;
/*     */     } 
/*     */     
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  91 */     MiningPipeType type = (MiningPipeType)getType(state);
/*  92 */     if (type == null) return true;
/*     */     
/*  94 */     switch (type) { case pipe:
/*  95 */         return false;
/*  96 */       case tip: return true; }
/*  97 */      return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack(IBlockState state) {
/* 103 */     MiningPipeType type = (MiningPipeType)getType(state);
/*     */     
/* 105 */     if (type == MiningPipeType.tip) {
/* 106 */       return getItemStack(MiningPipeType.pipe);
/*     */     }
/* 108 */     return super.getItemStack(state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_149666_a(CreativeTabs tabs, NonNullList<ItemStack> itemList) {
/* 114 */     for (MiningPipeType type : this.typeProperty.getShownValues()) {
/* 115 */       if (type == MiningPipeType.tip)
/*     */         continue; 
/* 117 */       itemList.add(getItemStack(type));
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum MiningPipeType implements IIdProvider {
/* 122 */     pipe,
/* 123 */     tip;
/*     */ 
/*     */     
/*     */     public String getName() {
/* 127 */       return name();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getId() {
/* 132 */       return ordinal();
/*     */     }
/*     */   }
/*     */   
/* 136 */   private static final AxisAlignedBB pipeAabb = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\BlockMiningPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */