/*     */ package ic2.core.item.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.BlockTileEntity;
/*     */ import ic2.core.block.ITeBlock;
/*     */ import ic2.core.block.TeBlockRegistry;
/*     */ import ic2.core.block.TileEntityBlock;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.ITooltipFlag;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public class ItemBlockTileEntity
/*     */   extends ItemBlockIC2 {
/*     */   public ItemBlockTileEntity(Block block, ResourceLocation identifier) {
/*  30 */     super(block);
/*     */     
/*  32 */     func_77627_a(true);
/*  33 */     this.identifier = identifier;
/*     */   }
/*     */   public final ResourceLocation identifier;
/*     */   
/*     */   public String func_77667_c(ItemStack stack) {
/*  38 */     ITeBlock teBlock = getTeBlock(stack);
/*  39 */     String name = (teBlock == null) ? "invalid" : teBlock.getName();
/*     */     
/*  41 */     return func_77658_a() + "." + name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> items) {
/*  46 */     this.field_150939_a.func_149666_a(tab, items);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void func_77624_a(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
/*  53 */     ITeBlock block = getTeBlock(stack);
/*  54 */     if (block != null && block.getDummyTe() != null) {
/*  55 */       block.getDummyTe().addInformation(stack, tooltip, advanced);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
/*  62 */     assert newState.func_177230_c() == this.field_150939_a;
/*     */     
/*  64 */     if (!((BlockTileEntity)this.field_150939_a).canReplace(world, pos, side, stack)) return false;
/*     */     
/*  66 */     ITeBlock teBlock = getTeBlock(stack);
/*  67 */     if (teBlock == null) return false;
/*     */     
/*  69 */     Class<? extends TileEntityBlock> teClass = teBlock.getTeClass();
/*  70 */     if (teClass == null) return false;
/*     */     
/*  72 */     TileEntityBlock te = TileEntityBlock.instantiate(teClass);
/*  73 */     if (!placeTeBlock(stack, (EntityLivingBase)player, world, pos, side, te)) return false;
/*     */     
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean placeTeBlock(ItemStack stack, EntityLivingBase placer, World world, BlockPos pos, EnumFacing side, TileEntityBlock te) {
/*  79 */     IBlockState oldState = world.func_180495_p(pos);
/*  80 */     IBlockState newState = te.getBlockState();
/*  81 */     if (!world.func_180501_a(pos, newState, 0)) return false;
/*     */     
/*  83 */     world.func_175690_a(pos, (TileEntity)te);
/*  84 */     te.onPlaced(stack, placer, side);
/*     */ 
/*     */     
/*  87 */     world.markAndNotifyBlock(pos, world.func_175726_f(pos), oldState, newState, 3);
/*     */     
/*  89 */     if (!world.field_72995_K) ((NetworkManager)IC2.network.get(true)).sendInitialData((TileEntity)te);
/*     */     
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumRarity func_77613_e(ItemStack stack) {
/*  96 */     ITeBlock teblock = getTeBlock(stack);
/*     */     
/*  98 */     return (teblock != null) ? teblock.getRarity() : EnumRarity.COMMON;
/*     */   }
/*     */   
/*     */   private ITeBlock getTeBlock(ItemStack stack) {
/* 102 */     if (stack == null) return null;
/*     */     
/* 104 */     return TeBlockRegistry.get(this.identifier, stack.func_77952_i());
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\block\ItemBlockTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */