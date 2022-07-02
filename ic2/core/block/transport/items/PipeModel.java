/*     */ package ic2.core.block.transport.items;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.block.transport.TileEntityFluidPipe;
/*     */ import ic2.core.block.transport.TileEntityPipe;
/*     */ import ic2.core.model.AbstractModel;
/*     */ import ic2.core.model.BasicBakedBlockModel;
/*     */ import ic2.core.model.ISpecialParticleModel;
/*     */ import ic2.core.model.ModelUtil;
/*     */ import ic2.core.model.VdUtil;
/*     */ import ic2.core.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.particle.Particle;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.common.model.IModelState;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class PipeModel
/*     */   extends AbstractModel
/*     */   implements ISpecialParticleModel
/*     */ {
/*     */   public Collection<ResourceLocation> getTextures() {
/*  49 */     return this.textures.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
/*  54 */     for (Map.Entry<ResourceLocation, TextureAtlasSprite> entry : this.textures.entrySet()) {
/*  55 */       entry.setValue(bakedTextureGetter.apply(entry.getKey()));
/*     */     }
/*     */     
/*  58 */     return (IBakedModel)this;
/*     */   }
/*     */   
/*     */   private static Map<ResourceLocation, TextureAtlasSprite> generateTextureLocations() {
/*  62 */     Map<ResourceLocation, TextureAtlasSprite> ret = new HashMap<>();
/*     */     
/*  64 */     ret.put(new ResourceLocation("ic2", "blocks/transport/pipe_side"), null);
/*     */     
/*  66 */     StringBuilder name = new StringBuilder();
/*  67 */     name.append("blocks/transport/");
/*  68 */     name.append("pipe");
/*  69 */     int reset0 = name.length();
/*     */     
/*  71 */     for (PipeSize size : PipeSize.values()) {
/*  72 */       name.append('_');
/*  73 */       name.append(size.name());
/*     */       
/*  75 */       ret.put(new ResourceLocation("ic2", name.toString()), null);
/*     */       
/*  77 */       name.setLength(reset0);
/*     */     } 
/*     */     
/*  80 */     return ret;
/*     */   }
/*     */   
/*     */   private static ResourceLocation getTextureLocation(PipeSize size) {
/*  84 */     String loc = "blocks/transport/pipe_" + size.getName();
/*  85 */     return new ResourceLocation("ic2", loc);
/*     */   }
/*     */   
/*     */   private static ResourceLocation getSideTextureLocation() {
/*  89 */     String loc = "blocks/transport/pipe_side";
/*  90 */     return new ResourceLocation("ic2", loc);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
/*  95 */     if (!(rawState instanceof Ic2BlockState.Ic2BlockStateInstance)) {
/*  96 */       return ModelUtil.getMissingModel().func_188616_a(rawState, side, rand);
/*     */     }
/*     */     
/*  99 */     Ic2BlockState.Ic2BlockStateInstance state = (Ic2BlockState.Ic2BlockStateInstance)rawState;
/*     */     
/* 101 */     if (!state.hasValue(TileEntityFluidPipe.renderStateProperty)) {
/* 102 */       return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand);
/*     */     }
/*     */     
/* 105 */     TileEntityPipe.PipeRenderState prop = (TileEntityPipe.PipeRenderState)state.getValue(TileEntityFluidPipe.renderStateProperty);
/*     */     
/*     */     try {
/* 108 */       return ((IBakedModel)this.modelCache.get(prop)).func_188616_a((IBlockState)state, side, rand);
/* 109 */     } catch (ExecutionException e) {
/* 110 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IBakedModel generateModel(TileEntityPipe.PipeRenderState prop) {
/* 116 */     PipeType type = prop.type;
/*     */ 
/*     */     
/* 119 */     int color = 0xFF000000 | ((byte)type.blue & 0xFF) << 16 | ((byte)type.green & 0xFF) << 8 | (byte)type.red & 0xFF;
/*     */     
/* 121 */     float th = prop.size.thickness;
/* 122 */     float sp = (1.0F - th) / 2.0F;
/*     */     
/* 124 */     EnumFacing pFacing = EnumFacing.values()[prop.facing];
/*     */ 
/*     */     
/* 127 */     List[] arrayOfList = new List[EnumFacing.field_82609_l.length];
/*     */     
/* 129 */     for (int i = 0; i < arrayOfList.length; i++) {
/* 130 */       arrayOfList[i] = new ArrayList();
/*     */     }
/*     */     
/* 133 */     List<BakedQuad> generalQuads = new ArrayList<>();
/* 134 */     TextureAtlasSprite sideSprite = this.textures.get(getSideTextureLocation());
/* 135 */     TextureAtlasSprite sizeSprite = this.textures.get(getTextureLocation(prop.size));
/*     */     
/* 137 */     int totalConnections = Integer.bitCount(prop.connectivity);
/*     */     
/* 139 */     if (totalConnections == 0) {
/*     */ 
/*     */       
/* 142 */       float zS = sp, yS = zS, xS = yS;
/* 143 */       float zE = sp + th, yE = zE, xE = yE;
/*     */       
/* 145 */       VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, Util.allFacings, sizeSprite, arrayOfList, generalQuads);
/* 146 */     } else if (totalConnections == 1) {
/*     */       
/* 148 */       EnumFacing connected = EnumFacing.field_82609_l[Integer.numberOfTrailingZeros(prop.connectivity)];
/*     */       
/* 150 */       for (EnumFacing facing : EnumFacing.field_82609_l) {
/*     */ 
/*     */         
/* 153 */         float zS = sp, yS = zS, xS = yS;
/* 154 */         float zE = sp + th, yE = zE, xE = yE;
/*     */         
/* 156 */         if (facing == connected) {
/* 157 */           switch (facing) {
/*     */             case DOWN:
/* 159 */               yS = 0.0F;
/* 160 */               yE = sp;
/*     */               break;
/*     */             case UP:
/* 163 */               yS = sp + th;
/* 164 */               yE = 1.0F;
/*     */               break;
/*     */             case NORTH:
/* 167 */               zS = 0.0F;
/* 168 */               zE = sp;
/*     */               break;
/*     */             case SOUTH:
/* 171 */               zS = sp + th;
/* 172 */               zE = 1.0F;
/*     */               break;
/*     */             case WEST:
/* 175 */               xS = 0.0F;
/* 176 */               xE = sp;
/*     */               break;
/*     */             case EAST:
/* 179 */               xS = sp + th;
/* 180 */               xE = 1.0F;
/*     */               break;
/*     */             default:
/* 183 */               throw new RuntimeException();
/*     */           } 
/*     */           
/* 186 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sizeSprite, arrayOfList, generalQuads);
/* 187 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.complementOf((EnumSet)EnumSet.of(facing, facing.func_176734_d())), sideSprite, arrayOfList, generalQuads);
/* 188 */         } else if (facing == connected.func_176734_d()) {
/* 189 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sizeSprite, arrayOfList, generalQuads);
/*     */         } else {
/* 191 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sideSprite, arrayOfList, generalQuads);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 195 */       for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 196 */         boolean hasConnection = ((prop.connectivity & 1 << facing.ordinal()) != 0);
/*     */ 
/*     */         
/* 199 */         float zS = sp, yS = zS, xS = yS;
/* 200 */         float zE = sp + th, yE = zE, xE = yE;
/*     */         
/* 202 */         if (hasConnection) {
/* 203 */           switch (facing) { case DOWN:
/* 204 */               yS = 0.0F; yE = sp; break;
/* 205 */             case UP: yS = sp + th; yE = 1.0F; break;
/* 206 */             case NORTH: zS = 0.0F; zE = sp; break;
/* 207 */             case SOUTH: zS = sp + th; zE = 1.0F; break;
/* 208 */             case WEST: xS = 0.0F; xE = sp; break;
/* 209 */             case EAST: xS = sp + th; xE = 1.0F; break;
/* 210 */             default: throw new RuntimeException(); }
/*     */ 
/*     */           
/* 213 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sizeSprite, arrayOfList, generalQuads);
/* 214 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.complementOf((EnumSet)EnumSet.of(facing, facing.func_176734_d())), sideSprite, arrayOfList, generalQuads);
/*     */         } else {
/* 216 */           VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, EnumSet.of(facing), sideSprite, arrayOfList, generalQuads);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 222 */     float cs = 1.0F;
/* 223 */     float ch = 0.1F;
/* 224 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 225 */       boolean hasCover = ((prop.covers & 1 << facing.ordinal()) != 0);
/*     */ 
/*     */       
/* 228 */       float zS = 0.0F, yS = zS, xS = yS;
/* 229 */       float zE = 1.0F, yE = zE, xE = yE;
/*     */       
/* 231 */       if (hasCover) {
/* 232 */         switch (facing) { case DOWN:
/* 233 */             yS = 0.0F; yE = ch; break;
/* 234 */           case UP: yS = cs - ch; yE = 1.0F; break;
/* 235 */           case NORTH: zS = 0.0F; zE = ch; break;
/* 236 */           case SOUTH: zS = cs - ch; zE = 1.0F; break;
/* 237 */           case WEST: xS = 0.0F; xE = ch; break;
/* 238 */           case EAST: xS = cs - ch; xE = 1.0F; break;
/* 239 */           default: throw new RuntimeException(); }
/*     */ 
/*     */         
/* 242 */         VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, color, Util.allFacings, sideSprite, arrayOfList, generalQuads);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 247 */     int used = 0;
/*     */     
/* 249 */     for (int j = 0; j < arrayOfList.length; j++) {
/* 250 */       if (arrayOfList[j].isEmpty()) {
/* 251 */         arrayOfList[j] = Collections.emptyList();
/*     */       } else {
/* 253 */         used++;
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     if (used == 0) {
/* 258 */       arrayOfList = null;
/*     */     }
/*     */     
/* 261 */     if (generalQuads.isEmpty()) {
/* 262 */       generalQuads = Collections.emptyList();
/*     */     }
/*     */     
/* 265 */     return (IBakedModel)new BasicBakedBlockModel(arrayOfList, generalQuads, sizeSprite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReload() {
/* 270 */     this.modelCache.invalidateAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite func_177554_e() {
/* 275 */     return this.textures.get(getSideTextureLocation());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsEnhancing(IBlockState state) {
/* 280 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enhanceParticle(Particle particle, Ic2BlockState.Ic2BlockStateInstance state) {
/* 285 */     if (state.hasValue(TileEntityPipe.renderStateProperty)) {
/* 286 */       TileEntityPipe.PipeRenderState prop = (TileEntityPipe.PipeRenderState)state.getValue(TileEntityPipe.renderStateProperty);
/*     */       
/* 288 */       particle.func_70538_b(prop.type.red / 255.0F, prop.type.green / 255.0F, prop.type.blue / 255.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 293 */   private final Map<ResourceLocation, TextureAtlasSprite> textures = generateTextureLocations();
/* 294 */   private final LoadingCache<TileEntityPipe.PipeRenderState, IBakedModel> modelCache = CacheBuilder.newBuilder()
/* 295 */     .maximumSize(256L)
/* 296 */     .expireAfterAccess(5L, TimeUnit.MINUTES)
/* 297 */     .build(new CacheLoader<TileEntityPipe.PipeRenderState, IBakedModel>()
/*     */       {
/*     */         public IBakedModel load(TileEntityPipe.PipeRenderState key) throws Exception {
/* 300 */           return PipeModel.this.generateModel(key);
/*     */         }
/*     */       });
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\transport\items\PipeModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */