/*     */ package ic2.core.item.tool;
/*     */ 
/*     */ import ic2.api.event.RetextureEvent;
/*     */ import ic2.api.item.ElectricItem;
/*     */ import ic2.core.IC2;
/*     */ import ic2.core.block.state.BlockStateUtil;
/*     */ import ic2.core.item.BaseElectricItem;
/*     */ import ic2.core.model.ModelUtil;
/*     */ import ic2.core.network.IPlayerItemDataListener;
/*     */ import ic2.core.network.NetworkManager;
/*     */ import ic2.core.ref.ItemName;
/*     */ import ic2.core.util.LogCategory;
/*     */ import ic2.core.util.StackUtil;
/*     */ import ic2.core.util.Util;
/*     */ import ic2.core.util.Vector3;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.model.pipeline.IVertexConsumer;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ 
/*     */ public class ItemObscurator extends BaseElectricItem implements IPlayerItemDataListener {
/*  42 */   private final int scanOperationCost = 20000;
/*  43 */   private final int printOperationCost = 5000;
/*     */   
/*     */   public ItemObscurator() {
/*  46 */     super(ItemName.obscurator, 100000.0D, 250.0D, 2);
/*     */     
/*  48 */     func_77656_e(27);
/*  49 */     func_77625_d(1);
/*  50 */     setNoRepair();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getHudInfo(ItemStack stack, boolean advanced) {
/*  55 */     List<String> info = new LinkedList<>();
/*  56 */     info.add(ElectricItem.manager.getToolTip(stack));
/*  57 */     return info;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
/*  62 */     ItemStack stack = StackUtil.get(player, hand);
/*  63 */     if (!player.func_70093_af() && !world.field_72995_K && ElectricItem.manager.canUse(stack, 5000.0D)) {
/*  64 */       NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*     */       
/*     */       IBlockState refState;
/*     */       
/*     */       EnumFacing refSide;
/*     */       int[] colorMultipliers;
/*  70 */       if ((refState = getState(nbt)) == null || (
/*  71 */         refSide = getSide(nbt)) == null || (
/*  72 */         colorMultipliers = getColorMultipliers(nbt)) == null) {
/*  73 */         clear(nbt);
/*  74 */         return EnumActionResult.PASS;
/*     */       } 
/*     */       
/*  77 */       IBlockState state = world.func_180495_p(pos);
/*     */       
/*  79 */       RetextureEvent event = new RetextureEvent(world, pos, state, side, player, refState, getVariant(nbt), refSide, colorMultipliers);
/*  80 */       MinecraftForge.EVENT_BUS.post((Event)event);
/*     */       
/*  82 */       if (event.applied) {
/*  83 */         ElectricItem.manager.use(stack, 5000.0D, (EntityLivingBase)player);
/*     */         
/*  85 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */       
/*  88 */       return EnumActionResult.PASS;
/*  89 */     }  if (player.func_70093_af() && world.field_72995_K && ElectricItem.manager.canUse(stack, 20000.0D)) {
/*  90 */       return scanBlock(stack, player, world, pos, side) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
/*     */     }
/*  92 */     return EnumActionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean scanBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
/*  97 */     assert world.field_72995_K;
/*     */     
/*  99 */     IBlockState state = Util.getBlockState((IBlockAccess)world, pos);
/* 100 */     if (state.func_177230_c().isAir(state, (IBlockAccess)world, pos)) return false;
/*     */     
/* 102 */     ObscuredRenderInfo renderInfo = getRenderInfo(state, side);
/* 103 */     if (renderInfo == null) return false;
/*     */     
/* 105 */     String variant = ModelUtil.getVariant(state);
/* 106 */     int[] colorMultipliers = new int[renderInfo.tints.length];
/*     */     
/* 108 */     for (int i = 0; i < renderInfo.tints.length; i++) {
/* 109 */       colorMultipliers[i] = IC2.platform.getColorMultiplier(state, (IBlockAccess)world, pos, renderInfo.tints[i]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 114 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 116 */     if (getState(nbt) != state || 
/* 117 */       !variant.equals(getVariant(nbt)) || 
/* 118 */       getSide(nbt) != side || 
/* 119 */       !Arrays.equals(getColorMultipliers(nbt), colorMultipliers)) {
/* 120 */       ((NetworkManager)IC2.network.get(false)).sendPlayerItemData(player, player.field_71071_by.field_70461_c, new Object[] { state
/* 121 */             .func_177230_c(), variant, side, colorMultipliers });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       return true;
/*     */     } 
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerItemNetworkData(EntityPlayer player, int slot, Object... data) {
/* 134 */     if (!(data[0] instanceof Block))
/* 135 */       return;  if (!(data[1] instanceof String))
/* 136 */       return;  if (!(data[2] instanceof Integer))
/* 137 */       return;  if (!(data[3] instanceof int[]))
/*     */       return; 
/* 139 */     ItemStack stack = (ItemStack)player.field_71071_by.field_70462_a.get(slot);
/* 140 */     if (!ElectricItem.manager.use(stack, 20000.0D, (EntityLivingBase)player))
/*     */       return; 
/* 142 */     NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
/*     */     
/* 144 */     setState(nbt, (Block)data[0], (String)data[1]);
/* 145 */     setSide(nbt, ((Integer)data[2]).intValue());
/* 146 */     setColorMultipliers(nbt, (int[])data[3]);
/*     */   }
/*     */   
/*     */   public static IBlockState getState(NBTTagCompound nbt) {
/* 150 */     String blockName = nbt.func_74779_i("refBlock");
/* 151 */     if (blockName.isEmpty()) return null;
/*     */     
/* 153 */     Block block = Util.getBlock(blockName);
/* 154 */     if (block == null) return null;
/*     */     
/* 156 */     String variant = getVariant(nbt);
/*     */     
/* 158 */     return BlockStateUtil.getState(block, variant);
/*     */   }
/*     */   
/*     */   public static String getVariant(NBTTagCompound nbt) {
/* 162 */     return nbt.func_74779_i("refVariant");
/*     */   }
/*     */   
/*     */   private static void setState(NBTTagCompound nbt, Block block, String variant) {
/* 166 */     nbt.func_74778_a("refBlock", Util.getName(block).toString());
/* 167 */     nbt.func_74778_a("refVariant", variant);
/*     */   }
/*     */   
/*     */   public static EnumFacing getSide(NBTTagCompound nbt) {
/* 171 */     int ordinal = nbt.func_74771_c("refSide");
/* 172 */     if (ordinal < 0 || ordinal >= EnumFacing.field_82609_l.length) return null;
/*     */     
/* 174 */     return EnumFacing.field_82609_l[ordinal];
/*     */   }
/*     */   
/*     */   private static void setSide(NBTTagCompound nbt, int side) {
/* 178 */     nbt.func_74774_a("refSide", (byte)side);
/*     */   }
/*     */   
/*     */   public static int[] getColorMultipliers(NBTTagCompound nbt) {
/* 182 */     int[] ret = nbt.func_74759_k("refColorMuls");
/*     */     
/* 184 */     return (ret.length == 0) ? null : internColorMultipliers(ret);
/*     */   }
/*     */   
/*     */   public static void setColorMultipliers(NBTTagCompound nbt, int[] colorMultipliers) {
/* 188 */     if (colorMultipliers.length == 0) throw new IllegalArgumentException();
/*     */     
/* 190 */     nbt.func_74783_a("refColorMuls", colorMultipliers);
/*     */   }
/*     */   
/*     */   private static void clear(NBTTagCompound nbt) {
/* 194 */     nbt.func_82580_o("refBlock");
/* 195 */     nbt.func_82580_o("refVariant");
/* 196 */     nbt.func_82580_o("refSide");
/* 197 */     nbt.func_82580_o("refColorMul");
/*     */   }
/*     */   
/*     */   public static ObscuredRenderInfo getRenderInfo(IBlockState state, EnumFacing side) {
/* 201 */     Block block = state.func_177230_c();
/*     */     
/* 203 */     if (block.func_180664_k() == BlockRenderLayer.TRANSLUCENT) return null;
/*     */     
/* 205 */     IBakedModel model = ModelUtil.getBlockModel(state);
/* 206 */     if (model == null) return null;
/*     */     
/* 208 */     List<BakedQuad> faceQuads = model.func_188616_a(state, side, 0L);
/* 209 */     if (faceQuads.isEmpty()) return null;
/*     */     
/* 211 */     float[] uvs = new float[faceQuads.size() * 4];
/* 212 */     int uvsOffset = 0;
/* 213 */     int[] tints = new int[faceQuads.size()];
/* 214 */     TextureAtlasSprite[] sprites = new TextureAtlasSprite[faceQuads.size()];
/* 215 */     ExtractingVertexConsumer testConsumer = testConsumers.get();
/*     */     
/* 217 */     for (BakedQuad faceQuad : faceQuads) {
/* 218 */       testConsumer.setTexture(faceQuad.func_187508_a());
/*     */       
/*     */       try {
/* 221 */         faceQuad.pipe(testConsumer);
/* 222 */       } catch (Throwable t) {
/* 223 */         IC2.log.warn(LogCategory.General, t, "Can't retrieve face data");
/* 224 */         return null;
/*     */       } finally {
/* 226 */         testConsumer.reset();
/*     */       } 
/*     */       
/* 229 */       float[] positions = testConsumer.positions;
/*     */       
/* 231 */       int dx = side.func_82601_c();
/* 232 */       int dy = side.func_96559_d();
/* 233 */       int dz = side.func_82599_e();
/* 234 */       int xS = (dx + 1) / 2;
/* 235 */       int yS = (dy + 1) / 2;
/* 236 */       int zS = (dz + 1) / 2;
/*     */       
/* 238 */       int vertices = 4;
/* 239 */       int positionElements = 3;
/* 240 */       int firstVertex = -1;
/*     */       
/* 242 */       for (int v = 0; v < 4; v++) {
/* 243 */         int vo = v * 3;
/*     */         
/* 245 */         if (Util.isSimilar(positions[vo + 0], xS) && 
/* 246 */           Util.isSimilar(positions[vo + 1], yS) && 
/* 247 */           Util.isSimilar(positions[vo + 2], zS)) {
/* 248 */           firstVertex = v;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 253 */       if (firstVertex == -1) {
/*     */         continue;
/*     */       }
/* 256 */       Vector3 v1 = new Vector3((positions[3] - positions[0]), (positions[4] - positions[1]), (positions[5] - positions[2]));
/* 257 */       if (!Util.isSimilar(v1.lengthSquared(), 1.0D))
/*     */         continue; 
/* 259 */       Vector3 v4 = new Vector3((positions[9] - positions[0]), (positions[10] - positions[1]), (positions[11] - positions[2]));
/* 260 */       if (!Util.isSimilar(v4.lengthSquared(), 1.0D)) {
/*     */         continue;
/*     */       }
/* 263 */       Vector3 v3 = new Vector3((positions[9] - positions[6]), (positions[10] - positions[7]), (positions[11] - positions[8]));
/* 264 */       if (!Util.isSimilar(v3.copy().add(v1).lengthSquared(), 0.0D)) {
/*     */         continue;
/*     */       }
/* 267 */       Vector3 normal = v1.copy().cross(v4);
/* 268 */       if (!Util.isSimilar(normal.copy().sub(dx, dy, dz).lengthSquared(), 0.0D)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 274 */       tints[uvsOffset / 4] = testConsumer.tint;
/* 275 */       sprites[uvsOffset / 4] = testConsumer.sprite;
/*     */       
/* 277 */       uvs[uvsOffset++] = testConsumer.uvs[firstVertex * 2];
/* 278 */       uvs[uvsOffset++] = testConsumer.uvs[firstVertex * 2 + 1];
/* 279 */       uvs[uvsOffset++] = testConsumer.uvs[(firstVertex + 2) % 4 * 2];
/* 280 */       uvs[uvsOffset++] = testConsumer.uvs[(firstVertex + 2) % 4 * 2 + 1];
/*     */     } 
/*     */     
/* 283 */     if (uvsOffset == 0) return null;
/*     */     
/* 285 */     if (uvsOffset < uvs.length) {
/* 286 */       uvs = Arrays.copyOf(uvs, uvsOffset);
/* 287 */       tints = Arrays.copyOf(tints, uvsOffset / 4);
/*     */     } 
/*     */     
/* 290 */     tints = internTints(tints);
/*     */     
/* 292 */     return new ObscuredRenderInfo(uvs, tints, sprites);
/*     */   }
/*     */   
/*     */   public static int[] internTints(int[] tints) {
/* 296 */     if (tints.length == 1) {
/* 297 */       if (tints[0] == noTint[0])
/* 298 */         return noTint; 
/* 299 */       if (tints[0] == zeroTint[0]) {
/* 300 */         return zeroTint;
/*     */       }
/*     */     } 
/*     */     
/* 304 */     return tints;
/*     */   }
/*     */   
/*     */   public static int[] internColorMultipliers(int[] colorMultipliers) {
/* 308 */     if (colorMultipliers.length == 1) {
/* 309 */       if (colorMultipliers[0] == defaultColorMultiplier[0])
/* 310 */         return defaultColorMultiplier; 
/* 311 */       if (colorMultipliers[0] == colorMultiplierOpaqueWhite[0]) {
/* 312 */         return colorMultiplierOpaqueWhite;
/*     */       }
/*     */     } 
/*     */     
/* 316 */     return colorMultipliers;
/*     */   }
/*     */   
/*     */   public static class ObscuredRenderInfo {
/*     */     private ObscuredRenderInfo(float[] uvs, int[] tints, TextureAtlasSprite[] sprites) {
/* 321 */       this.uvs = uvs;
/* 322 */       this.tints = tints;
/* 323 */       this.sprites = sprites;
/*     */     }
/*     */     
/*     */     public final float[] uvs;
/*     */     public final int[] tints;
/*     */     public final TextureAtlasSprite[] sprites;
/*     */   }
/*     */   
/*     */   private static class ExtractingVertexConsumer
/*     */     implements IVertexConsumer {
/*     */     public VertexFormat getVertexFormat() {
/* 334 */       return DefaultVertexFormats.field_181707_g;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setQuadTint(int tint) {
/* 339 */       this.tint = tint;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setQuadOrientation(EnumFacing orientation) {}
/*     */ 
/*     */     
/*     */     public void setApplyDiffuseLighting(boolean diffuse) {}
/*     */ 
/*     */     
/*     */     public void put(int element, float... data) {
/* 350 */       if (element == 0) {
/* 351 */         this.positions[this.posIdx++] = data[0];
/* 352 */         this.positions[this.posIdx++] = data[1];
/* 353 */         this.positions[this.posIdx++] = data[2];
/* 354 */       } else if (element == 1) {
/* 355 */         this.uvs[this.uvIdx++] = data[0];
/* 356 */         this.uvs[this.uvIdx++] = data[1];
/*     */       } else {
/* 358 */         throw new IllegalStateException("invalid element: " + element);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setTexture(TextureAtlasSprite texture) {
/* 364 */       this.sprite = texture;
/*     */     }
/*     */     
/*     */     public void reset() {
/* 368 */       this.posIdx = 0;
/* 369 */       this.uvIdx = 0;
/* 370 */       this.tint = -1;
/* 371 */       this.sprite = null;
/*     */     }
/*     */     
/* 374 */     private final float[] positions = new float[12];
/*     */     private int posIdx;
/* 376 */     private final float[] uvs = new float[8]; private int uvIdx;
/*     */     private TextureAtlasSprite sprite;
/* 378 */     private int tint = -1;
/*     */     
/*     */     private ExtractingVertexConsumer() {} }
/*     */   
/* 382 */   private static ThreadLocal<ExtractingVertexConsumer> testConsumers = new ThreadLocal<ExtractingVertexConsumer>()
/*     */     {
/*     */       protected ItemObscurator.ExtractingVertexConsumer initialValue() {
/* 385 */         return new ItemObscurator.ExtractingVertexConsumer();
/*     */       }
/*     */     };
/*     */   
/* 389 */   private static final int[] noTint = new int[] { -1 };
/* 390 */   private static final int[] zeroTint = new int[] { 0 };
/* 391 */   private static final int[] defaultColorMultiplier = new int[] { 16777215 };
/* 392 */   private static final int[] colorMultiplierOpaqueWhite = new int[] { -1 };
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\ItemObscurator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */