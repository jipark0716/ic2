/*     */ package ic2.core.crop;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import ic2.api.crops.CropCard;
/*     */ import ic2.api.crops.Crops;
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.model.AbstractModel;
/*     */ import ic2.core.model.BasicBakedBlockModel;
/*     */ import ic2.core.model.VdUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.common.model.IModelState;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class CropModel
/*     */   extends AbstractModel
/*     */ {
/*     */   public Collection<ResourceLocation> getTextures() {
/*  49 */     if (textures.isEmpty()) {
/*  50 */       IC2Crops.needsToPost = false;
/*  51 */       MinecraftForge.EVENT_BUS.post((Event)new Crops.CropRegisterEvent());
/*     */       
/*  53 */       for (CropCard crop : Crops.instance.getCrops()) {
/*  54 */         for (ResourceLocation aux : crop.getTexturesLocation()) textures.put(aux, null); 
/*     */       } 
/*  56 */       textures.put(STICK, null);
/*  57 */       textures.put(UPGRADED_STICK, null);
/*     */     } 
/*     */     
/*  60 */     return textures.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
/*  65 */     for (Map.Entry<ResourceLocation, TextureAtlasSprite> entry : textures.entrySet()) {
/*  66 */       entry.setValue(bakedTextureGetter.apply(entry.getKey()));
/*     */     }
/*  68 */     return (IBakedModel)this;
/*     */   }
/*     */   
/*     */   private static ResourceLocation getTextureLocation(CropCard crop, int currentSize) {
/*  72 */     return crop.getTexturesLocation().get(currentSize - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
/*     */     TileEntityCrop.CropRenderState prop;
/*     */     Ic2BlockState.Ic2BlockStateInstance state;
/*  79 */     if (rawState instanceof Ic2BlockState.Ic2BlockStateInstance && (state = (Ic2BlockState.Ic2BlockStateInstance)rawState).hasValue(TileEntityCrop.renderStateProperty)) {
/*  80 */       prop = (TileEntityCrop.CropRenderState)state.getValue(TileEntityCrop.renderStateProperty);
/*     */     } else {
/*  82 */       prop = new TileEntityCrop.CropRenderState(null, 0, false);
/*     */     } 
/*     */     
/*     */     try {
/*  86 */       return ((IBakedModel)this.modelCache.get(prop)).func_188616_a(rawState, side, rand);
/*  87 */     } catch (Exception error) {
/*  88 */       throw new RuntimeException(error);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   IBakedModel generateModel(TileEntityCrop.CropRenderState prop) {
/*  96 */     List[] arrayOfList = new List[EnumFacing.field_176754_o.length];
/*     */     
/*  98 */     for (int index = 0; index < arrayOfList.length; ) { arrayOfList[index] = new ArrayList(); index++; }
/*     */     
/* 100 */     List<BakedQuad> generalQuads = new ArrayList<>();
/*     */     
/* 102 */     TextureAtlasSprite cropSprite = textures.computeIfAbsent(getTextureLocation(prop.crop, prop.size), MISSING);
/*     */     
/* 104 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 105 */       int offsetX = facing.func_82601_c();
/* 106 */       int offsetZ = facing.func_82599_e();
/* 107 */       float x = Math.abs(offsetX) * (0.5F + offsetX * 0.25F);
/* 108 */       float z = Math.abs(offsetZ) * (0.5F + offsetZ * 0.25F);
/* 109 */       float xS = (offsetX == 0) ? 0.0F : x;
/* 110 */       float xE = (offsetX == 0) ? 1.0F : x;
/* 111 */       float zS = (offsetZ == 0) ? 0.0F : z;
/* 112 */       float zE = (offsetZ == 0) ? 1.0F : z;
/*     */       
/* 114 */       VdUtil.addFlippedCuboidWithYOffset(xS, 0.001F, zS, xE, 1.0F, zE, -1, EnumSet.of(facing), cropSprite, arrayOfList, generalQuads, -0.0625F);
/* 115 */       VdUtil.addFlippedCuboidWithYOffset(xS, 0.001F, zS, xE, 1.0F, zE, -1, EnumSet.of(facing.func_176734_d()), cropSprite, arrayOfList, generalQuads, -0.0625F);
/*     */     } 
/*     */     
/* 118 */     int used = 0;
/* 119 */     for (int i = 0; i < arrayOfList.length; i++) {
/* 120 */       if (arrayOfList[i].isEmpty()) {
/* 121 */         arrayOfList[i] = Collections.emptyList();
/*     */       } else {
/* 123 */         used++;
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     if (used == 0) arrayOfList = null;
/*     */     
/* 129 */     if (generalQuads.isEmpty()) generalQuads = Collections.emptyList();
/*     */     
/* 131 */     return (IBakedModel)new BasicBakedBlockModel(arrayOfList, generalQuads, cropSprite);
/*     */   }
/*     */ 
/*     */   
/*     */   IBakedModel generateStickModel(boolean crosscrop) {
/* 136 */     List[] arrayOfList = new List[EnumFacing.field_176754_o.length];
/*     */     
/* 138 */     for (int index = 0; index < arrayOfList.length; ) { arrayOfList[index] = new ArrayList(); index++; }
/*     */     
/* 140 */     List<BakedQuad> generalQuads = new ArrayList<>();
/*     */     
/* 142 */     TextureAtlasSprite stickSprite = textures.get(STICK);
/* 143 */     TextureAtlasSprite upgradedStickSprite = textures.get(UPGRADED_STICK);
/*     */     
/* 145 */     for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 146 */       int offsetX = facing.func_82601_c();
/* 147 */       int offsetZ = facing.func_82599_e();
/* 148 */       float x = Math.abs(offsetX) * (0.5F + offsetX * 0.25F);
/* 149 */       float z = Math.abs(offsetZ) * (0.5F + offsetZ * 0.25F);
/* 150 */       float xS = (offsetX == 0) ? 0.0F : x;
/* 151 */       float xE = (offsetX == 0) ? 1.0F : x;
/* 152 */       float zS = (offsetZ == 0) ? 0.0F : z;
/* 153 */       float zE = (offsetZ == 0) ? 1.0F : z;
/*     */       
/* 155 */       if (!crosscrop) {
/* 156 */         VdUtil.addFlippedCuboidWithYOffset(xS, 0.001F, zS, xE, 1.0F, zE, -1, EnumSet.of(facing), stickSprite, arrayOfList, generalQuads, -0.0625F);
/* 157 */         VdUtil.addFlippedCuboidWithYOffset(xS, 0.001F, zS, xE, 1.0F, zE, -1, EnumSet.of(facing.func_176734_d()), stickSprite, arrayOfList, generalQuads, -0.0625F);
/*     */       } else {
/* 159 */         VdUtil.addFlippedCuboidWithYOffset(xS, 0.001F, zS, xE, 1.0F, zE, -1, EnumSet.of(facing), upgradedStickSprite, arrayOfList, generalQuads, -0.0625F);
/* 160 */         VdUtil.addFlippedCuboidWithYOffset(xS, 0.001F, zS, xE, 1.0F, zE, -1, EnumSet.of(facing.func_176734_d()), upgradedStickSprite, arrayOfList, generalQuads, -0.0625F);
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     int used = 0;
/* 165 */     for (int i = 0; i < arrayOfList.length; i++) {
/* 166 */       if (arrayOfList[i].isEmpty()) {
/* 167 */         arrayOfList[i] = Collections.emptyList();
/*     */       } else {
/* 169 */         used++;
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if (used == 0) arrayOfList = null;
/*     */     
/* 175 */     if (generalQuads.isEmpty()) generalQuads = Collections.emptyList();
/*     */     
/* 177 */     return (IBakedModel)new BasicBakedBlockModel(arrayOfList, generalQuads, stickSprite);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite func_177554_e() {
/* 182 */     return textures.get(STICK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReload() {
/* 187 */     this.modelCache.invalidateAll();
/*     */   }
/*     */ 
/*     */   
/* 191 */   private static final ResourceLocation STICK = new ResourceLocation("ic2", "blocks/crop/stick");
/* 192 */   private static final ResourceLocation UPGRADED_STICK = new ResourceLocation("ic2", "blocks/crop/stick_upgraded");
/*     */   private static final Function<ResourceLocation, TextureAtlasSprite> MISSING = location -> Minecraft.func_71410_x().func_147117_R().func_174944_f();
/* 194 */   static final Map<ResourceLocation, TextureAtlasSprite> textures = new HashMap<>();
/*     */   
/* 196 */   private final LoadingCache<TileEntityCrop.CropRenderState, IBakedModel> modelCache = CacheBuilder.newBuilder()
/* 197 */     .maximumSize(256L)
/* 198 */     .expireAfterAccess(5L, TimeUnit.MINUTES)
/* 199 */     .build(new CacheLoader<TileEntityCrop.CropRenderState, IBakedModel>()
/*     */       {
/*     */         public IBakedModel load(TileEntityCrop.CropRenderState key) throws Exception {
/* 202 */           if (key.crop == null || key.size <= 0) return CropModel.this.generateStickModel(key.crosscrop);
/*     */           
/* 204 */           return CropModel.this.generateModel(key);
/*     */         }
/*     */       });
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\crop\CropModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */