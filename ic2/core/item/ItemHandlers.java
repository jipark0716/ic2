/*     */ package ic2.core.item;
/*     */ 
/*     */ import ic2.api.recipe.Recipes;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.IC2Potion;
/*     */ import ic2.core.block.BlockSheet;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.item.armor.ItemArmorHazmat;
/*     */ import ic2.core.item.type.CellType;
/*     */ import ic2.core.item.type.IRadioactiveItemType;
/*     */ import ic2.core.item.upgrade.ItemUpgradeModule;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.FluidName;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.LiquidUtil;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockPistonBase;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.fluids.FluidRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemHandlers
/*     */ {
/*  49 */   public static ItemMulti.IItemRightClickHandler cfPowderApply = new ItemMulti.IItemRightClickHandler()
/*     */     {
/*     */       public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer player, EnumHand hand) {
/*  52 */         RayTraceResult position = Util.traceBlocks(player, true);
/*  53 */         if (position == null) return new ActionResult(EnumActionResult.PASS, stack);
/*     */         
/*  55 */         if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  56 */           World world = player.func_130014_f_();
/*  57 */           if (!world.canMineBlockBody(player, position.func_178782_a())) return new ActionResult(EnumActionResult.FAIL, stack);
/*     */           
/*  59 */           if (world.func_180495_p(position.func_178782_a()).func_177230_c() == Blocks.field_150355_j) {
/*  60 */             stack = StackUtil.decSize(stack);
/*  61 */             world.func_175656_a(position.func_178782_a(), FluidName.construction_foam.getInstance().getBlock().func_176223_P());
/*  62 */             new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */           } 
/*     */         } 
/*     */         
/*  66 */         return new ActionResult(EnumActionResult.FAIL, stack);
/*     */       }
/*     */     };
/*     */   
/*  70 */   public static ItemMulti.IItemRightClickHandler scrapBoxUnpack = new ItemMulti.IItemRightClickHandler()
/*     */     {
/*     */       public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer player, EnumHand hand) {
/*  73 */         if (!(player.func_130014_f_()).field_72995_K) {
/*  74 */           ItemStack drop = Recipes.scrapboxDrops.getDrop(stack, false);
/*     */           
/*  76 */           if (drop != null && player
/*  77 */             .func_71019_a(drop, false) != null && !player.field_71075_bZ.field_75098_d) {
/*     */             
/*  79 */             stack = StackUtil.decSize(stack);
/*  80 */             return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */           } 
/*     */         } 
/*     */         
/*  84 */         return new ActionResult(EnumActionResult.PASS, stack);
/*     */       }
/*     */     };
/*     */   
/*  88 */   public static ItemMulti.IItemUseHandler resinUse = new ItemMulti.IItemUseHandler()
/*     */     {
/*     */       public EnumActionResult onUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumHand hand, EnumFacing side) {
/*  91 */         World world = player.func_130014_f_();
/*  92 */         IBlockState state = world.func_180495_p(pos);
/*     */         
/*  94 */         if (state.func_177230_c() == Blocks.field_150331_J && state.func_177229_b((IProperty)BlockPistonBase.field_176387_N) == side) {
/*     */ 
/*     */           
/*  97 */           IBlockState newState = Blocks.field_150320_F.func_176223_P().func_177226_a((IProperty)BlockPistonBase.field_176387_N, (Comparable)side).func_177226_a((IProperty)BlockPistonBase.field_176320_b, state.func_177229_b((IProperty)BlockPistonBase.field_176320_b));
/*  98 */           world.func_180501_a(pos, newState, 3);
/*     */           
/* 100 */           if (!player.field_71075_bZ.field_75098_d) StackUtil.consumeOrError(player, hand, 1);
/*     */           
/* 102 */           return EnumActionResult.SUCCESS;
/*     */         } 
/*     */         
/* 105 */         if (side != EnumFacing.UP) return EnumActionResult.PASS;
/*     */         
/* 107 */         pos = pos.func_177984_a();
/*     */         
/* 109 */         if (!state.func_177230_c().isAir(state, (IBlockAccess)world, pos) || 
/* 110 */           !BlockName.sheet.getInstance().func_176198_a(world, pos, side)) {
/* 111 */           return EnumActionResult.PASS;
/*     */         }
/*     */         
/* 114 */         world.func_175656_a(pos, BlockName.sheet.getBlockState((IIdProvider)BlockSheet.SheetType.resin));
/* 115 */         if (!player.field_71075_bZ.field_75098_d) StackUtil.consumeOrError(player, hand, 1);
/*     */         
/* 117 */         return EnumActionResult.PASS;
/*     */       }
/*     */     };
/*     */   
/* 121 */   public static ItemMulti.IItemUpdateHandler radioactiveUpdate = new ItemMulti.IItemUpdateHandler()
/*     */     {
/*     */       public void onUpdate(ItemStack stack, World world, Entity rawEntity, int slotIndex, boolean isCurrentItem) {
/* 124 */         Item item = stack.func_77973_b();
/* 125 */         if (item == null || !(item instanceof ItemMulti))
/*     */           return; 
/* 127 */         Object rawType = ((ItemMulti)item).getType(stack);
/* 128 */         if (!(rawType instanceof IRadioactiveItemType))
/*     */           return; 
/* 130 */         IRadioactiveItemType type = (IRadioactiveItemType)rawType;
/*     */         
/* 132 */         if (!(rawEntity instanceof EntityLivingBase))
/*     */           return; 
/* 134 */         EntityLivingBase entity = (EntityLivingBase)rawEntity;
/* 135 */         if (ItemArmorHazmat.hasCompleteHazmat(entity))
/*     */           return; 
/* 137 */         IC2Potion.radiation.applyTo(entity, type.getRadiationDuration() * 20, type.getRadiationAmplifier());
/*     */       }
/*     */     };
/*     */   
/* 141 */   public static TeBlock.ITePlaceHandler reactorChamberPlace = new TeBlock.ITePlaceHandler()
/*     */     {
/*     */       public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack)
/*     */       {
/* 145 */         int count = 0;
/*     */         
/* 147 */         for (EnumFacing dir : EnumFacing.field_82609_l) {
/* 148 */           TileEntity te = world.func_175625_s(pos.func_177972_a(dir));
/*     */           
/* 150 */           if (te instanceof ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric) {
/* 151 */             count++;
/*     */           }
/*     */         } 
/*     */         
/* 155 */         return (count == 1);
/*     */       }
/*     */     };
/*     */   
/* 159 */   public static ItemMulti.IItemRightClickHandler openAdvancedUpgradeGUI = new ItemMulti.IItemRightClickHandler()
/*     */     {
/*     */       public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer player, EnumHand hand) {
/* 162 */         assert stack.func_77973_b() == ItemName.upgrade.getInstance();
/*     */         
/* 164 */         if (IC2.platform.isSimulating()) {
/* 165 */           IC2.platform.launchGui(player, ((ItemUpgradeModule)stack.func_77973_b()).getInventory(player, stack));
/*     */         }
/*     */         
/* 168 */         return new ActionResult(EnumActionResult.SUCCESS, stack);
/*     */       }
/*     */     };
/*     */   
/*     */   public static ItemMulti.IItemUseHandler getFluidPlacer(final Block type) {
/* 173 */     return new ItemMulti.IItemUseHandler()
/*     */       {
/*     */         public EnumActionResult onUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumHand hand, EnumFacing side) {
/* 176 */           assert stack.func_77973_b() == ItemName.misc_resource.getInstance();
/*     */           
/* 178 */           World world = player.func_130014_f_();
/* 179 */           if (!world.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)world, pos)) {
/* 180 */             pos = pos.func_177972_a(side);
/*     */           }
/*     */           
/* 183 */           if (player.func_175151_a(pos, side, stack) && world.func_190527_a(type, pos, false, side, null)) {
/* 184 */             IBlockState placedState = type.getStateForPlacement(world, pos, side, 0.0F, 0.0F, 0.0F, 0, (EntityLivingBase)player, hand);
/* 185 */             world.func_175656_a(pos, placedState);
/*     */             
/* 187 */             SoundType sound = placedState.func_177230_c().getSoundType(placedState, world, pos, (Entity)player);
/* 188 */             world.func_184133_a(player, pos, sound.func_185841_e(), SoundCategory.BLOCKS, (sound.func_185843_a() + 1.0F) / 2.0F, sound.func_185847_b() * 0.8F);
/*     */             
/* 190 */             StackUtil.consumeOrError(player, hand, 1);
/*     */             
/* 192 */             return EnumActionResult.SUCCESS;
/*     */           } 
/* 194 */           return EnumActionResult.FAIL;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/* 200 */   public static ItemMulti.IItemUseHandler emptyCellFill = new ItemMulti.IItemUseHandler()
/*     */     {
/*     */       public EnumActionResult onUse(ItemStack stack, EntityPlayer player, BlockPos pos, EnumHand hand, EnumFacing side) {
/* 203 */         assert stack.func_77973_b() == ItemName.cell.getInstance();
/* 204 */         World world = player.func_130014_f_();
/*     */         
/* 206 */         RayTraceResult position = Util.traceBlocks(player, true);
/* 207 */         if (position == null) return EnumActionResult.FAIL;
/*     */         
/* 209 */         if (position.field_72313_a == RayTraceResult.Type.BLOCK) {
/* 210 */           pos = position.func_178782_a();
/*     */           
/* 212 */           if (!world.canMineBlockBody(player, pos)) return EnumActionResult.FAIL; 
/* 213 */           if (!player.func_175151_a(pos, position.field_178784_b, player.func_184586_b(hand))) return EnumActionResult.FAIL;
/*     */           
/* 215 */           LiquidUtil.LiquidData data = LiquidUtil.getLiquid(world, pos);
/* 216 */           if (data != null && data.isSource) {
/* 217 */             if (data.liquid == FluidRegistry.WATER && StackUtil.storeInventoryItem(ItemName.cell.getItemStack((Enum)CellType.water), player, true)) {
/* 218 */               world.func_175698_g(pos);
/*     */               
/* 220 */               StackUtil.consumeOrError(player, hand, 1);
/* 221 */               StackUtil.storeInventoryItem(ItemName.cell.getItemStack((Enum)CellType.water), player, false);
/*     */               
/* 223 */               return EnumActionResult.SUCCESS;
/*     */             } 
/*     */             
/* 226 */             if (data.liquid == FluidRegistry.LAVA && StackUtil.storeInventoryItem(ItemName.cell.getItemStack((Enum)CellType.lava), player, true)) {
/* 227 */               world.func_175698_g(pos);
/*     */               
/* 229 */               StackUtil.consumeOrError(player, hand, 1);
/* 230 */               StackUtil.storeInventoryItem(ItemName.cell.getItemStack((Enum)CellType.lava), player, false);
/*     */               
/* 232 */               return EnumActionResult.SUCCESS;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 237 */         return EnumActionResult.PASS;
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\ItemHandlers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */