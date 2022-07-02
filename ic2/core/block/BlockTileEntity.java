/*      */ package ic2.core.block;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import ic2.api.item.ITeBlockSpecialItem;
/*      */ import ic2.api.tile.IWrenchable;
/*      */ import ic2.core.IC2;
/*      */ import ic2.core.block.comp.BasicRedstoneComponent;
/*      */ import ic2.core.block.comp.ComparatorEmitter;
/*      */ import ic2.core.block.comp.Redstone;
/*      */ import ic2.core.block.comp.RedstoneEmitter;
/*      */ import ic2.core.block.state.IIdProvider;
/*      */ import ic2.core.block.state.Ic2BlockState;
/*      */ import ic2.core.block.state.MaterialProperty;
/*      */ import ic2.core.block.state.SkippedBooleanProperty;
/*      */ import ic2.core.init.MainConfig;
/*      */ import ic2.core.item.block.ItemBlockTileEntity;
/*      */ import ic2.core.model.ModelUtil;
/*      */ import ic2.core.network.NetworkManager;
/*      */ import ic2.core.ref.BlockName;
/*      */ import ic2.core.ref.IMultiBlock;
/*      */ import ic2.core.ref.MetaTeBlock;
/*      */ import ic2.core.ref.MetaTeBlockProperty;
/*      */ import ic2.core.ref.TeBlock;
/*      */ import ic2.core.util.ConfigUtil;
/*      */ import ic2.core.util.LogCategory;
/*      */ import ic2.core.util.ParticleUtil;
/*      */ import ic2.core.util.StackUtil;
/*      */ import ic2.core.util.Util;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.function.Function;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.SoundType;
/*      */ import net.minecraft.block.material.EnumPushReaction;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.properties.PropertyDirection;
/*      */ import net.minecraft.block.state.BlockFaceShape;
/*      */ import net.minecraft.block.state.BlockStateContainer;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.particle.ParticleManager;
/*      */ import net.minecraft.client.renderer.ItemMeshDefinition;
/*      */ import net.minecraft.client.renderer.block.model.ModelBakery;
/*      */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*      */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraftforge.client.model.ModelLoader;
/*      */ import net.minecraftforge.common.EnumPlantType;
/*      */ import net.minecraftforge.common.IPlantable;
/*      */ import net.minecraftforge.fml.common.Loader;
/*      */ import net.minecraftforge.fml.common.ModContainer;
/*      */ import net.minecraftforge.fml.relauncher.Side;
/*      */ import net.minecraftforge.fml.relauncher.SideOnly;
/*      */ 
/*      */ 
/*      */ public final class BlockTileEntity
/*      */   extends BlockBase
/*      */   implements IMultiBlock<ITeBlock>, IWrenchable, IPlantable
/*      */ {
/*      */   public final IProperty<MetaTeBlock> typeProperty;
/*      */   public final MaterialProperty materialProperty;
/*      */   
/*      */   static BlockTileEntity create(BlockName name, Collection<Material> materials) {
/*   92 */     BlockTileEntity ret = create(name.name(), TeBlock.invalid.getIdentifier(), materials);
/*      */     
/*   94 */     name.setInstance(ret);
/*      */     
/*   96 */     return ret;
/*      */   }
/*      */   
/*      */   static BlockTileEntity create(String name, ResourceLocation identifier, Collection<Material> materials) {
/*  100 */     currentTypeProperty.set(new MetaTeBlockProperty(identifier));
/*  101 */     currentMaterialProperty.set(new MaterialProperty(materials));
/*  102 */     BlockTileEntity ret = new BlockTileEntity(name, identifier);
/*  103 */     currentMaterialProperty.remove();
/*  104 */     currentTypeProperty.remove();
/*      */     
/*  106 */     return ret;
/*      */   }
/*      */   
/*      */   private BlockTileEntity(String name, final ResourceLocation identifier) {
/*  110 */     super((BlockName)null, TeBlockRegistry.getInfo(identifier).getDefaultMaterial());
/*      */     
/*  112 */     this.typeProperty = getTypeProperty();
/*  113 */     this.materialProperty = getMaterialProperty();
/*      */     
/*  115 */     ModContainer ic2 = Loader.instance().activeModContainer();
/*  116 */     Loader.instance().getActiveModList().stream().filter(mod -> identifier.func_110624_b().equals(mod.getModId())).findFirst().ifPresent(Loader.instance()::setActiveModContainer);
/*      */     
/*  118 */     register(name, identifier, (Function<Block, Item>)new Function<Block, Item>()
/*      */         {
/*      */           public Item apply(Block input) {
/*  121 */             return (Item)new ItemBlockTileEntity(input, identifier);
/*      */           }
/*      */         });
/*  124 */     Loader.instance().setActiveModContainer(ic2);
/*      */     
/*  126 */     func_180632_j(this.field_176227_L.func_177621_b()
/*  127 */         .func_177226_a((IProperty)this.materialProperty, (Comparable)MaterialProperty.WrappedMaterial.get(this.field_149764_J))
/*  128 */         .func_177226_a(this.typeProperty, (Comparable)MetaTeBlockProperty.invalid)
/*  129 */         .func_177226_a(facingProperty, (Comparable)EnumFacing.DOWN)
/*  130 */         .func_177226_a(transparentProperty, Boolean.FALSE));
/*      */     
/*  132 */     this.item = (ItemBlockTileEntity)Item.func_150898_a(this);
/*  133 */     IC2.log.debug(LogCategory.Block, "Successfully built BlockTileEntity for identity " + identifier + '.');
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public void registerModels(BlockName name) {
/*  139 */     final ModelResourceLocation invalidLocation = ModelUtil.getTEBlockModelLocation(Util.getName(BlockName.te.getInstance()), this.field_176227_L.func_177621_b()
/*  140 */         .func_177226_a((IProperty)this.materialProperty, (Comparable)MaterialProperty.WrappedMaterial.get(this.field_149764_J))
/*  141 */         .func_177226_a(this.typeProperty, (Comparable)MetaTeBlockProperty.invalid)
/*  142 */         .func_177226_a(facingProperty, (Comparable)EnumFacing.NORTH)
/*  143 */         .func_177226_a(transparentProperty, Boolean.FALSE));
/*      */     
/*  145 */     IC2.log.debug(LogCategory.Block, "Preparing to set models for " + this.item.identifier + '.');
/*  146 */     IC2.log.debug(LogCategory.Block, "Mapping " + func_176194_O().func_177619_a().size() + " states.");
/*      */ 
/*      */     
/*  149 */     ModelLoader.setCustomStateMapper(this, new IStateMapper()
/*      */         {
/*      */           public Map<IBlockState, ModelResourceLocation> func_178130_a(Block block) {
/*  152 */             Map<IBlockState, ModelResourceLocation> ret = new IdentityHashMap<>();
/*      */             
/*  154 */             for (UnmodifiableIterator<IBlockState> unmodifiableIterator = block.func_176194_O().func_177619_a().iterator(); unmodifiableIterator.hasNext(); ) { IBlockState state = unmodifiableIterator.next();
/*  155 */               MetaTeBlock metaTeBlock = (MetaTeBlock)state.func_177229_b(BlockTileEntity.this.typeProperty);
/*  156 */               EnumFacing facing = (EnumFacing)state.func_177229_b(BlockTileEntity.facingProperty);
/*      */ 
/*      */               
/*  159 */               if (metaTeBlock.teBlock.getSupportedFacings().contains(facing) || (facing == EnumFacing.DOWN && metaTeBlock.teBlock
/*  160 */                 .getSupportedFacings().isEmpty())) {
/*  161 */                 ret.put(state, ModelUtil.getTEBlockModelLocation(metaTeBlock.teBlock.getIdentifier(), state)); continue;
/*      */               } 
/*  163 */               ret.put(state, invalidLocation); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  170 */             return ret;
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  175 */     ModelLoader.setCustomMeshDefinition((Item)this.item, new ItemMeshDefinition()
/*      */         {
/*      */           public ModelResourceLocation func_178113_a(ItemStack stack) {
/*  178 */             ITeBlock teBlock = TeBlockRegistry.get(BlockTileEntity.this.item.identifier, stack.func_77952_i());
/*  179 */             if (teBlock == null) return invalidLocation;
/*      */             
/*  181 */             if (teBlock instanceof ITeBlockSpecialItem && ((ITeBlockSpecialItem)teBlock).doesOverrideDefault(stack)) {
/*  182 */               ModelResourceLocation location = ((ITeBlockSpecialItem)teBlock).getModelLocation(stack);
/*  183 */               return (location == null) ? invalidLocation : location;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  188 */             IBlockState state = BlockTileEntity.this.func_176223_P().func_177226_a(BlockTileEntity.this.typeProperty, (Comparable)MetaTeBlockProperty.getState(teBlock)).func_177226_a(BlockTileEntity.facingProperty, (Comparable)BlockTileEntity.getItemFacing(teBlock));
/*      */             
/*  190 */             return ModelUtil.getTEBlockModelLocation(teBlock.getIdentifier(), state);
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  196 */     boolean checkSpecialModels = TeBlockRegistry.getInfo(this.item.identifier).hasSpecialModels();
/*  197 */     for (MetaTeBlockProperty.MetaTePair block : MetaTeBlockProperty.getAllStates(this.item.identifier)) {
/*  198 */       if (block.hasItem()) {
/*  199 */         ModelResourceLocation model = checkSpecialModels ? getSpecialModel(block) : null;
/*      */         
/*  201 */         if (model == null) {
/*      */ 
/*      */           
/*  204 */           IBlockState state = this.field_176227_L.func_177621_b().func_177226_a(this.typeProperty, (Comparable)block.inactive).func_177226_a(facingProperty, (Comparable)getItemFacing(block.getBlock()));
/*  205 */           model = ModelUtil.getTEBlockModelLocation(block.getIdentifier(), state);
/*      */         } 
/*      */         
/*  208 */         assert model != null;
/*      */         
/*  210 */         ModelBakery.registerItemVariants((Item)this.item, new ResourceLocation[] { (ResourceLocation)model });
/*      */       } 
/*  212 */       IC2.log.debug(LogCategory.Block, "Done item for " + this.item.identifier + ':' + block.getName() + '.');
/*      */     } 
/*      */   }
/*      */   
/*      */   private static EnumFacing getItemFacing(ITeBlock teBlock) {
/*  217 */     Set<EnumFacing> supported = teBlock.getSupportedFacings();
/*      */     
/*  219 */     if (supported.contains(EnumFacing.NORTH))
/*  220 */       return EnumFacing.NORTH; 
/*  221 */     if (!supported.isEmpty()) {
/*  222 */       return supported.iterator().next();
/*      */     }
/*  224 */     return EnumFacing.DOWN;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   private ModelResourceLocation getSpecialModel(MetaTeBlockProperty.MetaTePair blockTextures) {
/*  230 */     assert blockTextures.getBlock() instanceof ITeBlockSpecialItem;
/*  231 */     ITeBlockSpecialItem block = (ITeBlockSpecialItem)blockTextures.getBlock();
/*      */     
/*  233 */     ItemStack stack = new ItemStack((Item)this.item, 1, blockTextures.getBlock().getId());
/*  234 */     return block.doesOverrideDefault(stack) ? block.getModelLocation(stack) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
/*  239 */     return ((Boolean)state.func_177229_b(transparentProperty)).booleanValue() ? ((layer == BlockRenderLayer.CUTOUT)) : ((layer == BlockRenderLayer.SOLID));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149716_u() {
/*  244 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasTileEntity(IBlockState state) {
/*  249 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BlockStateContainer func_180661_e() {
/*  254 */     return (BlockStateContainer)new Ic2BlockState(this, new IProperty[] { getTypeProperty(), (IProperty)getMaterialProperty(), facingProperty, transparentProperty });
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_176201_c(IBlockState state) {
/*  259 */     int ret = this.materialProperty.getId((MaterialProperty.WrappedMaterial)state.func_177229_b((IProperty)this.materialProperty));
/*  260 */     if (ret < 0 || ret >= 8) throw new IllegalStateException("invalid material id: " + ret);
/*      */     
/*  262 */     ret |= ((Boolean)state.func_177229_b(transparentProperty)).booleanValue() ? 8 : 0;
/*      */     
/*  264 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState func_176203_a(int meta) {
/*  269 */     boolean isTransparent = ((meta & 0x8) != 0);
/*  270 */     int materialId = meta & 0x7;
/*      */     
/*  272 */     return func_176223_P().func_177226_a((IProperty)this.materialProperty, (Comparable)this.materialProperty.getMaterial(materialId)).func_177226_a(transparentProperty, Boolean.valueOf(isTransparent));
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState func_176221_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  277 */     TileEntityBlock te = getTe(world, pos);
/*  278 */     if (te == null) return state;
/*      */     
/*  280 */     return te.getBlockState();
/*      */   }
/*      */ 
/*      */   
/*      */   public String func_149739_a() {
/*  285 */     if (!isIC2()) {
/*  286 */       return this.item.identifier.func_110624_b() + '.' + this.item.identifier.func_110623_a();
/*      */     }
/*  288 */     return super.func_149739_a();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_149666_a(CreativeTabs tabs, NonNullList<ItemStack> list) {
/*  294 */     TeBlockRegistry.TeBlockInfo<?> info = TeBlockRegistry.getInfo(this.item.identifier);
/*      */     
/*  296 */     if (info.hasCreativeRegisterer()) {
/*  297 */       info.getCreativeRegisterer().addSubBlocks(list, this, this.item, tabs);
/*      */     }
/*  299 */     else if (tabs == IC2.tabIC2 || tabs == CreativeTabs.field_78027_g) {
/*  300 */       for (ITeBlock type : info.getTeBlocks()) {
/*  301 */         if (type.hasItem()) list.add(getItemStack(type));
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<ITeBlock> getAllTypes() {
/*  310 */     return Collections.unmodifiableSet(TeBlockRegistry.getAll(this.item.identifier));
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack func_185473_a(World world, BlockPos pos, IBlockState state) {
/*  315 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  316 */     if (te == null) return StackUtil.emptyStack;
/*      */     
/*  318 */     return te.getPickBlock((EntityPlayer)null, (RayTraceResult)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
/*  323 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  324 */     if (te == null) return StackUtil.emptyStack;
/*      */     
/*  326 */     return te.getPickBlock(player, target);
/*      */   }
/*      */   
/*      */   public IBlockState getState(ITeBlock variant) {
/*      */     EnumFacing facing;
/*  331 */     if (variant == null) throw new IllegalArgumentException("invalid type: " + variant);
/*      */     
/*  333 */     Set<EnumFacing> supportedFacings = variant.getSupportedFacings();
/*      */ 
/*      */     
/*  336 */     if (supportedFacings.isEmpty()) {
/*  337 */       facing = EnumFacing.DOWN;
/*  338 */     } else if (supportedFacings.contains(EnumFacing.NORTH)) {
/*  339 */       facing = EnumFacing.NORTH;
/*      */     } else {
/*  341 */       facing = supportedFacings.iterator().next();
/*      */     } 
/*      */     
/*  344 */     return func_176223_P()
/*  345 */       .func_177226_a((IProperty)this.materialProperty, (Comparable)MaterialProperty.WrappedMaterial.get(variant.getMaterial()))
/*  346 */       .func_177226_a(this.typeProperty, (Comparable)MetaTeBlockProperty.getState(variant))
/*  347 */       .func_177226_a(facingProperty, (Comparable)facing)
/*  348 */       .func_177226_a(transparentProperty, Boolean.valueOf(variant.isTransparent()));
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getState(String variant) {
/*  353 */     return getState(TeBlockRegistry.get(variant));
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemStack(ITeBlock type) {
/*  358 */     if (type == null) throw new IllegalArgumentException("invalid type: null"); 
/*  359 */     int id = type.getId();
/*      */     
/*  361 */     if (id != -1) {
/*  362 */       return new ItemStack((Item)this.item, 1, id);
/*      */     }
/*  364 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack getItemStack(String variant) {
/*  370 */     if (variant == null) throw new IllegalArgumentException("Invalid ITeBlock type: null"); 
/*  371 */     ITeBlock type = TeBlockRegistry.get(variant);
/*  372 */     if (type == null) throw new IllegalArgumentException("Invalid ITeBlock type: " + variant);
/*      */     
/*  374 */     return getItemStack(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVariant(ItemStack stack) {
/*  379 */     if (stack == null) throw new NullPointerException("null stack"); 
/*  380 */     if (stack.func_77973_b() != this.item) throw new IllegalArgumentException("The stack " + stack + " doesn't match " + this.item + " (" + this + ")");
/*      */     
/*  382 */     ITeBlock type = TeBlockRegistry.get(this.item.identifier, stack.func_77960_j());
/*  383 */     if (type == null) throw new IllegalArgumentException("The stack " + stack + " doesn't reference any valid subtype");
/*      */     
/*  385 */     return type.getName();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149686_d(IBlockState state) {
/*  390 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149662_c(IBlockState state) {
/*  395 */     return !((Boolean)state.func_177229_b(transparentProperty)).booleanValue();
/*      */   }
/*      */   
/*      */   public boolean canReplace(World world, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  399 */     if (StackUtil.isEmpty(stack)) return true; 
/*  400 */     if (stack.func_77973_b() != this.item) return false;
/*      */     
/*  402 */     ITeBlock type = TeBlockRegistry.get(this.item.identifier, stack.func_77960_j());
/*  403 */     if (type == null) return false;
/*      */     
/*  405 */     TeBlock.ITePlaceHandler handler = type.getPlaceHandler();
/*      */     
/*  407 */     return (handler == null || handler.canReplace(world, pos, side, stack));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos pos, IBlockState state2, EntityLivingBase entity, int numberOfParticles) {
/*  412 */     if (world.field_72995_K) throw new IllegalStateException();
/*      */     
/*  414 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  415 */     if (te == null) return super.addLandingEffects(state, world, pos, state2, entity, numberOfParticles);
/*      */     
/*  417 */     if (te.clientNeedsExtraModelInfo()) {
/*  418 */       ((NetworkManager)IC2.network.get(true)).initiateTeblockLandEffect((World)world, pos, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, numberOfParticles, te.teBlock);
/*      */     } else {
/*  420 */       ((NetworkManager)IC2.network.get(true)).initiateTeblockLandEffect((World)world, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, numberOfParticles, te.teBlock);
/*      */     } 
/*      */     
/*  423 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
/*  428 */     if (world.field_72995_K) return true;
/*      */     
/*  430 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  431 */     if (te == null) return super.addRunningEffects(state, world, pos, entity);
/*      */     
/*  433 */     if (te.clientNeedsExtraModelInfo()) {
/*  434 */       ((NetworkManager)IC2.network.get(true)).initiateTeblockRunEffect(world, pos, entity, te.teBlock);
/*      */     } else {
/*  436 */       ((NetworkManager)IC2.network.get(true)).initiateTeblockRunEffect(world, entity, te.teBlock);
/*      */     } 
/*      */     
/*  439 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
/*  445 */     BlockPos pos = target.func_178782_a();
/*  446 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  447 */     if (te == null) return super.addHitEffects(state, world, target, manager);
/*      */     
/*  449 */     ParticleUtil.spawnBlockHitParticles(te, target.field_178784_b, te.clientNeedsExtraModelInfo());
/*      */     
/*  451 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
/*  457 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*      */     
/*  459 */     if (te != null && te.clientNeedsExtraModelInfo()) {
/*  460 */       ParticleUtil.spawnBlockBreakParticles(te);
/*      */       
/*  462 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  466 */     return super.addDestroyEffects(world, pos, manager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Material func_149688_o(IBlockState state) {
/*  473 */     return ((MaterialProperty.WrappedMaterial)state.func_177229_b((IProperty)this.materialProperty)).getMaterial();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_176214_u(IBlockState state) {
/*  478 */     return (func_149688_o(state).func_76230_c() && func_176223_P().func_185917_h());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_176205_b(IBlockAccess world, BlockPos pos) {
/*  483 */     return !func_149688_o(world.func_180495_p(pos)).func_76230_c();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_181623_g() {
/*  488 */     return super.func_181623_g();
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumPushReaction func_149656_h(IBlockState state) {
/*  493 */     return func_149688_o(state).func_186274_m();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_149751_l(IBlockState state) {
/*  498 */     return !func_149688_o(state).func_76228_b();
/*      */   }
/*      */ 
/*      */   
/*      */   public MapColor func_180659_g(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  503 */     return func_149688_o(state).func_151565_r();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  510 */     TileEntityBlock te = getTe(world, pos);
/*  511 */     if (te == null) return state;
/*      */     
/*  513 */     return (IBlockState)te.getExtendedState((Ic2BlockState.Ic2BlockStateInstance)state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_180633_a(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/*  527 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  528 */     if (te == null)
/*      */       return; 
/*  530 */     te.onPlaced(stack, placer, EnumFacing.UP);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public RayTraceResult func_180636_a(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
/*  536 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  537 */     if (te == null) return super.func_180636_a(state, world, pos, start, end);
/*      */     
/*  539 */     return te.collisionRayTrace(start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  545 */     TileEntityBlock te = getTe(world, pos);
/*  546 */     if (te == null) return super.func_185496_a(state, world, pos);
/*      */     
/*  548 */     return te.getVisualBoundingBox();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB func_180640_a(IBlockState state, World world, BlockPos pos) {
/*  554 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  555 */     if (te == null) return super.func_180640_a(state, world, pos);
/*      */     
/*  557 */     return te.getOutlineBoundingBox().func_186670_a(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB func_180646_a(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  563 */     TileEntityBlock te = getTe(world, pos);
/*  564 */     if (te == null) return super.func_180646_a(state, world, pos);
/*      */     
/*  566 */     return te.getPhysicsBoundingBox();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_185477_a(IBlockState state, World world, BlockPos pos, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity, boolean isActualState) {
/*  572 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*      */     
/*  574 */     if (te == null) {
/*  575 */       super.func_185477_a(state, world, pos, mask, list, collidingEntity, isActualState);
/*      */     } else {
/*  577 */       te.addCollisionBoxesToList(mask, list, collidingEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_180634_a(World world, BlockPos pos, IBlockState state, Entity entity) {
/*  583 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  584 */     if (te == null)
/*      */       return; 
/*  586 */     te.onEntityCollision(entity);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @SideOnly(Side.CLIENT)
/*      */   public boolean func_176225_a(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/*  593 */     TileEntityBlock te = getTe(world, pos);
/*  594 */     if (te == null) return super.func_176225_a(state, world, pos, side);
/*      */     
/*  596 */     return te.shouldSideBeRendered(side, pos.func_177972_a(side));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
/*  601 */     TileEntityBlock te = getTe(world, pos);
/*  602 */     if (te == null) return false;
/*      */     
/*  604 */     return te.doesSideBlockRendering(face);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  609 */     TileEntityBlock te = getTe(world, pos);
/*  610 */     if (te == null) return false;
/*      */     
/*  612 */     return te.isNormalCube();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/*  617 */     TileEntityBlock te = getTe(world, pos);
/*  618 */     if (te == null) return false;
/*      */     
/*  620 */     return te.isSideSolid(side);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockFaceShape func_193383_a(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
/*  626 */     TileEntityBlock te = getTe(world, pos);
/*  627 */     if (te == null) return super.func_193383_a(world, state, pos, face);
/*      */     
/*  629 */     return te.getFaceShape(face);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  635 */     TileEntityBlock te = getTe(world, pos);
/*  636 */     if (te == null) return func_149717_k(state);
/*      */     
/*  638 */     return te.getLightOpacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
/*  643 */     TileEntityBlock te = getTe(world, pos);
/*  644 */     if (te == null) return 0;
/*      */     
/*  646 */     return te.getLightValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_180639_a(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  653 */     if (player.func_70093_af()) return false;
/*      */     
/*  655 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  656 */     if (te == null) return false;
/*      */     
/*  658 */     return te.onActivated(player, hand, side, hitX, hitY, hitZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_180649_a(World world, BlockPos pos, EntityPlayer player) {
/*  663 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  664 */     if (te == null)
/*      */       return; 
/*  666 */     te.onClicked(player);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_189540_a(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
/*  671 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  672 */     if (te == null)
/*      */       return; 
/*  674 */     te.onNeighborChange(neighborBlock, neighborPos);
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_180656_a(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/*  679 */     TileEntityBlock te = getTe(world, pos);
/*  680 */     if (te == null) return 0;
/*      */     
/*  682 */     BasicRedstoneComponent component = (BasicRedstoneComponent)te.getComponent(RedstoneEmitter.class);
/*      */     
/*  684 */     return (component == null) ? 0 : component.getLevel();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
/*  689 */     TileEntityBlock te = getTe(world, pos);
/*  690 */     if (te == null) return false;
/*      */     
/*  692 */     return (te.hasComponent((Class)RedstoneEmitter.class) || te.hasComponent((Class)Redstone.class));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean func_149740_M(IBlockState state) {
/*  702 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_180641_l(IBlockState state, World world, BlockPos pos) {
/*  707 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  708 */     if (te == null) return 0;
/*      */     
/*  710 */     BasicRedstoneComponent component = (BasicRedstoneComponent)te.getComponent(ComparatorEmitter.class);
/*      */     
/*  712 */     return (component == null) ? 0 : component.getLevel();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
/*  717 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  718 */     if (te == null) return false;
/*      */     
/*  720 */     return te.recolor(side, color);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
/*  725 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*      */     
/*  727 */     if (te != null) {
/*  728 */       te.onExploded(explosion);
/*      */     }
/*      */ 
/*      */     
/*  732 */     super.onBlockExploded(world, pos, explosion);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_180663_b(World world, BlockPos pos, IBlockState state) {
/*  737 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*      */     
/*  739 */     if (te != null) {
/*  740 */       te.onBlockBreak();
/*      */     }
/*      */     
/*  743 */     super.func_180663_b(world, pos, state);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
/*  748 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*      */     
/*  750 */     if (te != null) {
/*  751 */       if (!te.onRemovedByPlayer(player, willHarvest)) return false;
/*      */       
/*  753 */       if (willHarvest && !world.field_72995_K) {
/*      */         
/*  755 */         removedTes[nextRemovedTeIndex] = new WeakReference<>(te);
/*  756 */         nextRemovedTeIndex = (nextRemovedTeIndex + 1) % 4;
/*      */       } 
/*      */     } 
/*      */     
/*  760 */     return super.removedByPlayer(state, world, pos, player, willHarvest);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float func_180647_a(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
/*  766 */     float ret = super.func_180647_a(state, player, world, pos);
/*      */     
/*  768 */     if (!player.func_184823_b(state)) {
/*  769 */       TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*      */       
/*  771 */       if (te != null && te.teBlock.getHarvestTool() == TeBlock.HarvestTool.None) {
/*  772 */         ret *= 3.3333333F;
/*      */       }
/*      */     } 
/*      */     
/*  776 */     return ret;
/*      */   }
/*      */   
/*      */   public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
/*      */     ItemStack stack;
/*  781 */     boolean ret = super.canHarvestBlock(world, pos, player);
/*  782 */     if (ret) return ret;
/*      */     
/*  784 */     TileEntityBlock te = getTe(world, pos);
/*  785 */     if (te == null) return false;
/*      */     
/*  787 */     switch (te.teBlock.getHarvestTool()) {
/*      */       case None:
/*  789 */         return true;
/*      */       
/*      */       case Wrench:
/*  792 */         stack = player.func_184614_ca();
/*      */         
/*  794 */         if (!stack.func_190926_b()) {
/*  795 */           String tool = TeBlock.HarvestTool.Pickaxe.toolClass;
/*  796 */           return (stack.func_77973_b().getHarvestLevel(stack, tool, player, world.func_180495_p(pos)) >= TeBlock.HarvestTool.Pickaxe.level);
/*      */         } 
/*      */         break;
/*      */     } 
/*  800 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getHarvestTool(IBlockState state) {
/*  806 */     if (state.func_177230_c() != this) return null;
/*      */     
/*  808 */     return (((MetaTeBlock)state.func_177229_b(this.typeProperty)).teBlock.getHarvestTool()).toolClass;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHarvestLevel(IBlockState state) {
/*  813 */     if (state.func_177230_c() != this) return 0;
/*      */     
/*  815 */     return (((MetaTeBlock)state.func_177229_b(this.typeProperty)).teBlock.getHarvestTool()).level;
/*      */   }
/*      */ 
/*      */   
/*      */   public void getDrops(NonNullList<ItemStack> list, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/*  820 */     list.addAll(getDrops(world, pos, state, fortune));
/*      */   }
/*      */   
/*      */   public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
/*      */     int i;
/*  825 */     TileEntityBlock te = getTe(world, pos);
/*      */     
/*  827 */     if (te == null) {
/*      */       
/*  829 */       World realWorld = Util.getWorld(world);
/*      */       
/*  831 */       if ((realWorld != null && realWorld.field_72995_K) || (realWorld == null && 
/*  832 */         !IC2.platform.isSimulating())) {
/*  833 */         return new ArrayList<>();
/*      */       }
/*      */       
/*  836 */       i = nextRemovedTeIndex;
/*      */       
/*      */       do {
/*  839 */         int checkIdx = (i + 4 - 1) % 4;
/*  840 */         WeakReference<TileEntityBlock> ref = removedTes[checkIdx];
/*      */         
/*      */         TileEntityBlock cTe;
/*  843 */         if (ref != null && (
/*  844 */           cTe = ref.get()) != null && (realWorld == null || cTe
/*  845 */           .func_145831_w() == realWorld) && cTe
/*  846 */           .func_174877_v().equals(pos)) {
/*  847 */           te = cTe;
/*  848 */           removedTes[checkIdx] = null;
/*      */           
/*      */           break;
/*      */         } 
/*  852 */         i = checkIdx;
/*  853 */       } while (i != nextRemovedTeIndex);
/*      */       
/*  855 */       if (te == null) return new ArrayList<>();
/*      */     
/*      */     } 
/*  858 */     List<ItemStack> ret = new ArrayList<>();
/*      */     
/*  860 */     boolean wasWrench = ConfigUtil.getBool(MainConfig.get(), "balance/ignoreWrenchRequirement");
/*  861 */     if (!wasWrench) {
/*  862 */       EntityPlayer player = this.harvesters.get();
/*      */       
/*  864 */       if (player != null) {
/*  865 */         ItemStack stack = player.func_184614_ca();
/*      */         
/*  867 */         if (!stack.func_190926_b()) {
/*  868 */           String tool = TeBlock.HarvestTool.Wrench.toolClass;
/*  869 */           i = wasWrench | ((stack.func_77973_b().getHarvestLevel(stack, tool, player, state) >= TeBlock.HarvestTool.Wrench.level) ? 1 : 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  874 */     ret.addAll(te.getSelfDrops(fortune, i));
/*  875 */     ret.addAll(te.getAuxDrops(fortune));
/*      */     
/*  877 */     return ret;
/*      */   }
/*      */ 
/*      */   
/*      */   public float func_176195_g(IBlockState state, World world, BlockPos pos) {
/*  882 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  883 */     if (te == null) return 5.0F;
/*      */     
/*  885 */     return te.getHardness();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
/*  890 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  891 */     if (te == null) return 10.0F;
/*      */     
/*  893 */     return te.getExplosionResistance(exploder, explosion);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
/*  898 */     TileEntityBlock te = getTe(world, pos);
/*  899 */     if (te == null) return true;
/*      */     
/*  901 */     return te.canEntityDestroy(entity);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumFacing getFacing(World world, BlockPos pos) {
/*  906 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  907 */     if (te == null) return EnumFacing.DOWN;
/*      */     
/*  909 */     return te.getFacing();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSetFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
/*  914 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  915 */     if (te == null) return false;
/*      */     
/*  917 */     return te.canSetFacingWrench(newDirection, player);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setFacing(World world, BlockPos pos, EnumFacing newDirection, EntityPlayer player) {
/*  922 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  923 */     if (te == null) return false;
/*      */     
/*  925 */     return te.setFacingWrench(newDirection, player);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean wrenchCanRemove(World world, BlockPos pos, EntityPlayer player) {
/*  930 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  931 */     if (te == null) return false;
/*      */     
/*  933 */     return te.wrenchCanRemove(player);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<ItemStack> getWrenchDrops(World world, BlockPos pos, IBlockState state, TileEntity te, EntityPlayer player, int fortune) {
/*  938 */     if (!(te instanceof TileEntityBlock)) return Collections.emptyList();
/*      */     
/*  940 */     return ((TileEntityBlock)te).getWrenchDrops(player, fortune);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
/*  945 */     TileEntityBlock te = getTe(world, pos);
/*  946 */     if (te == null) return TileEntityBlock.noCrop;
/*      */     
/*  948 */     return te.getPlantType();
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundType getSoundType(IBlockState state, World world, BlockPos pos, Entity entity) {
/*  953 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  954 */     if (te == null) return super.getSoundType(state, world, pos, entity);
/*      */     
/*  956 */     return te.getBlockSound(entity);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TileEntityBlock getTe(IBlockAccess world, BlockPos pos) {
/*  962 */     TileEntity te = world.func_175625_s(pos);
/*      */     
/*  964 */     if (te instanceof TileEntityBlock) {
/*  965 */       return (TileEntityBlock)te;
/*      */     }
/*  967 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
/*  973 */     return world.func_180495_p(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
/*  978 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  979 */     if (te != null) {
/*  980 */       EnumFacing target = te.getFacing().func_176732_a(axis.func_176740_k());
/*  981 */       if (te.getSupportedFacings().contains(target) && te.getFacing() != target) {
/*  982 */         te.setFacing(target);
/*  983 */         return true;
/*      */       } 
/*      */     } 
/*  986 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumFacing[] getValidRotations(World world, BlockPos pos) {
/*  991 */     TileEntityBlock te = getTe((IBlockAccess)world, pos);
/*  992 */     if (te == null) return null;
/*      */     
/*  994 */     Set<EnumFacing> facings = te.getSupportedFacings();
/*  995 */     return !facings.isEmpty() ? facings.<EnumFacing>toArray(new EnumFacing[facings.size()]) : null;
/*      */   }
/*      */   
/*      */   public boolean isIC2() {
/*  999 */     return (this.item.identifier == TeBlock.invalid.getIdentifier());
/*      */   }
/*      */   
/*      */   public ItemBlockTileEntity getItem() {
/* 1003 */     return this.item;
/*      */   }
/*      */   
/*      */   public final IProperty<MetaTeBlock> getTypeProperty() {
/* 1007 */     IProperty<MetaTeBlock> ret = (this.typeProperty != null) ? this.typeProperty : currentTypeProperty.get();
/*      */     
/* 1009 */     assert ret != null : "The type property can't be obtained.";
/*      */ 
/*      */     
/* 1012 */     return ret;
/*      */   }
/*      */   
/*      */   public final MaterialProperty getMaterialProperty() {
/* 1016 */     MaterialProperty ret = (this.materialProperty != null) ? this.materialProperty : currentMaterialProperty.get();
/*      */     
/* 1018 */     assert ret != null : "The matieral property can't be obtained.";
/*      */ 
/*      */     
/* 1021 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1026 */   public static final IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>)PropertyDirection.func_177714_a("facing");
/* 1027 */   private static final ThreadLocal<IProperty<MetaTeBlock>> currentTypeProperty = new UnstartingThreadLocal<>();
/* 1028 */   private static final ThreadLocal<MaterialProperty> currentMaterialProperty = new UnstartingThreadLocal<>();
/* 1029 */   public static final IProperty<Boolean> transparentProperty = (IProperty<Boolean>)new SkippedBooleanProperty("transparent");
/*      */   
/*      */   private final ItemBlockTileEntity item;
/*      */   
/*      */   private static final int removedTesToKeep = 4;
/*      */   
/* 1035 */   private static final WeakReference<TileEntityBlock>[] removedTes = (WeakReference<TileEntityBlock>[])new WeakReference[4];
/*      */   private static int nextRemovedTeIndex;
/*      */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */