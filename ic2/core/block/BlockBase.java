/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.init.BlocksItems;
/*     */ import ic2.core.init.Localization;
/*     */ import ic2.core.item.block.ItemBlockIC2;
/*     */ import ic2.core.model.ModelUtil;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.IBlockModelProvider;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumRarity;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraftforge.client.model.ModelLoader;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ public abstract class BlockBase
/*     */   extends Block
/*     */   implements IBlockModelProvider
/*     */ {
/*     */   protected BlockBase(BlockName name, Material material) {
/*  37 */     this(name, material, ItemBlockIC2.supplier);
/*     */   }
/*     */   
/*     */   protected BlockBase(BlockName name, Material material, Class<? extends ItemBlock> itemClass) {
/*  41 */     this(name, material, createItemBlockSupplier(itemClass));
/*     */   }
/*     */   
/*     */   protected BlockBase(BlockName name, Material material, Function<Block, Item> itemSupplier) {
/*  45 */     super(material);
/*     */     
/*  47 */     func_149647_a((CreativeTabs)IC2.tabIC2);
/*     */     
/*  49 */     if (name != null) {
/*  50 */       register(name.name(), IC2.getIdentifier(name.name()), itemSupplier);
/*  51 */       name.setInstance(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void register(String name, ResourceLocation identifier, Function<Block, Item> itemSupplier) {
/*  56 */     func_149663_c(name);
/*  57 */     BlocksItems.registerBlock(this, identifier);
/*     */     
/*  59 */     if (itemSupplier != null) {
/*  60 */       BlocksItems.registerItem(itemSupplier.apply(this), identifier);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static Function<Block, Item> createItemBlockSupplier(final Class<? extends ItemBlock> cls) {
/*  65 */     if (cls == null) throw new NullPointerException("null item class");
/*     */     
/*  67 */     return new Function<Block, Item>()
/*     */       {
/*     */         public Item apply(Block input) {
/*     */           try {
/*  71 */             return cls.getConstructor(new Class[] { Block.class }).newInstance(new Object[] { input });
/*  72 */           } catch (Exception e) {
/*  73 */             throw new RuntimeException(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerModels(BlockName name) {
/*  82 */     registerDefaultItemModel(this);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void registerDefaultItemModel(Block block) {
/*  87 */     registerItemModels(block, Arrays.asList(new IBlockState[] { block.func_176223_P() }));
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void registerItemModels(Block block, Iterable<IBlockState> states) {
/*  92 */     registerItemModels(block, states, (IStateMapper)null);
/*     */   }
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void registerItemModels(Block block, Iterable<IBlockState> states, IStateMapper mapper) {
/*  97 */     Item item = Item.func_150898_a(block);
/*  98 */     if (item == null || item == Items.field_190931_a)
/*     */       return; 
/* 100 */     ResourceLocation loc = Util.getName(item);
/* 101 */     if (loc == null)
/*     */       return; 
/* 103 */     Map<IBlockState, ModelResourceLocation> locations = (mapper != null) ? mapper.func_178130_a(block) : null;
/*     */     
/* 105 */     for (IBlockState state : states) {
/* 106 */       int meta = block.func_176201_c(state);
/* 107 */       ModelResourceLocation location = (locations != null) ? locations.get(state) : ModelUtil.getModelLocation(loc, state);
/* 108 */       if (location == null) throw new RuntimeException("can't map state " + state);
/*     */       
/* 110 */       ModelLoader.setCustomModelResourceLocation(item, meta, location);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SideOnly(Side.CLIENT)
/*     */   public static void registerDefaultVanillaItemModel(Block block, String path) {
/* 122 */     Item item = Item.func_150898_a(block);
/* 123 */     if (item == null || item == Items.field_190931_a)
/*     */       return; 
/* 125 */     ResourceLocation loc = Util.getName(item);
/* 126 */     if (loc == null)
/*     */       return; 
/* 128 */     if (path == null || path.isEmpty()) {
/* 129 */       path = loc.toString();
/*     */     } else {
/* 131 */       path = path + '/' + loc.toString();
/*     */     } 
/*     */     
/* 134 */     ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(path, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_149739_a() {
/* 139 */     return "ic2." + super.func_149739_a().substring(5);
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_149732_F() {
/* 144 */     return Localization.translate(func_149739_a());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
/* 152 */     return false;
/*     */   }
/*     */   
/*     */   public EnumRarity getRarity(ItemStack stack) {
/* 156 */     return EnumRarity.COMMON;
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\BlockBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */