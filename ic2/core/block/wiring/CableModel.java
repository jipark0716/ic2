/*     */ package ic2.core.block.wiring;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import ic2.core.block.BlockFoam;
/*     */ import ic2.core.block.TileEntityWall;
/*     */ import ic2.core.block.comp.Obscuration;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.model.AbstractModel;
/*     */ import ic2.core.model.BasicBakedBlockModel;
/*     */ import ic2.core.model.ISpecialParticleModel;
/*     */ import ic2.core.model.ModelUtil;
/*     */ import ic2.core.model.VdUtil;
/*     */ import ic2.core.ref.BlockName;
/*     */ import ic2.core.ref.TeBlock;
/*     */ import ic2.core.util.Ic2Color;
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
/*     */ @SideOnly(Side.CLIENT)
/*     */ public class CableModel
/*     */   extends AbstractModel
/*     */   implements ISpecialParticleModel
/*     */ {
/*     */   public Collection<ResourceLocation> getTextures() {
/*  51 */     return this.textures.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
/*  56 */     for (Map.Entry<ResourceLocation, TextureAtlasSprite> entry : this.textures.entrySet()) {
/*  57 */       entry.setValue(bakedTextureGetter.apply(entry.getKey()));
/*     */     }
/*     */     
/*  60 */     return (IBakedModel)this;
/*     */   }
/*     */   
/*     */   private static Map<ResourceLocation, TextureAtlasSprite> generateTextureLocations() {
/*  64 */     Map<ResourceLocation, TextureAtlasSprite> ret = new HashMap<>();
/*  65 */     StringBuilder name = new StringBuilder();
/*  66 */     name.append("blocks/wiring/cable/");
/*  67 */     int reset0 = name.length();
/*     */     
/*  69 */     for (CableType type : CableType.values) {
/*  70 */       name.append(type.name());
/*  71 */       name.append("_cable");
/*  72 */       int reset1 = name.length();
/*     */       
/*  74 */       for (int insulation = 0; insulation <= type.maxInsulation; insulation++) {
/*  75 */         if (type.maxInsulation != 0) {
/*  76 */           name.append('_');
/*  77 */           name.append(insulation);
/*     */         } 
/*     */         
/*  80 */         if (insulation >= type.minColoredInsulation) {
/*  81 */           name.append('_');
/*  82 */           int reset2 = name.length();
/*     */           
/*  84 */           for (Ic2Color color : Ic2Color.values) {
/*  85 */             name.append(color.name());
/*  86 */             ret.put(new ResourceLocation("ic2", name.toString()), null);
/*  87 */             name.setLength(reset2);
/*     */           } 
/*     */         } else {
/*  90 */           ret.put(new ResourceLocation("ic2", name.toString()), null);
/*  91 */           if (type == CableType.splitter || type == CableType.detector) {
/*  92 */             ret.put(new ResourceLocation("ic2", name.toString() + "_active"), null);
/*     */           }
/*     */         } 
/*     */         
/*  96 */         name.setLength(reset1);
/*     */       } 
/*     */       
/*  99 */       name.setLength(reset0);
/*     */     } 
/*     */     
/* 102 */     return ret;
/*     */   }
/*     */   
/*     */   private static ResourceLocation getTextureLocation(CableType type, int insulation, Ic2Color color, boolean active) {
/* 106 */     String loc = "blocks/wiring/cable/" + type.getName(insulation, color);
/* 107 */     if (active) {
/* 108 */       loc = loc + "_active";
/*     */     }
/* 110 */     return new ResourceLocation("ic2", loc);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
/* 115 */     if (!(rawState instanceof Ic2BlockState.Ic2BlockStateInstance)) return ModelUtil.getMissingModel().func_188616_a(rawState, side, rand); 
/* 116 */     Ic2BlockState.Ic2BlockStateInstance state = (Ic2BlockState.Ic2BlockStateInstance)rawState;
/*     */ 
/*     */     
/* 119 */     if (!state.hasValue(TileEntityCable.renderStateProperty)) {
/* 120 */       return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand);
/*     */     }
/*     */     
/* 123 */     TileEntityCable.CableRenderState prop = (TileEntityCable.CableRenderState)state.getValue(TileEntityCable.renderStateProperty);
/*     */     
/* 125 */     if (prop.foam == CableFoam.Soft)
/* 126 */       return ModelUtil.getBlockModel(BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.normal)).func_188616_a((IBlockState)state, side, rand); 
/* 127 */     if (prop.foam == CableFoam.Hardened) {
/* 128 */       TileEntityWall.WallRenderState wallProp = (TileEntityWall.WallRenderState)state.getValue(TileEntityWall.renderStateProperty);
/*     */       
/* 130 */       if (wallProp == null)
/* 131 */         return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand); 
/* 132 */       if (wallProp.obscurations == null) {
/* 133 */         return ModelUtil.getBlockModel(BlockName.wall.getBlockState((IIdProvider)wallProp.color)).func_188616_a((IBlockState)state, side, rand);
/*     */       }
/* 135 */       IBakedModel model = ModelUtil.getBlockModel(BlockName.te.getBlockState((IIdProvider)TeBlock.wall));
/*     */       
/* 137 */       return model.func_188616_a((IBlockState)state, side, rand);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 142 */       return ((IBakedModel)this.modelCache.get(prop)).func_188616_a((IBlockState)state, side, rand);
/* 143 */     } catch (ExecutionException e) {
/* 144 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IBakedModel generateModel(TileEntityCable.CableRenderState prop) {
/* 150 */     float th = prop.type.thickness + (prop.insulation * 2) * 0.0625F;
/* 151 */     float sp = (1.0F - th) / 2.0F;
/*     */ 
/*     */     
/* 154 */     List[] arrayOfList = new List[EnumFacing.field_82609_l.length];
/*     */     
/* 156 */     for (int i = 0; i < arrayOfList.length; i++) {
/* 157 */       arrayOfList[i] = new ArrayList();
/*     */     }
/*     */     
/* 160 */     List<BakedQuad> generalQuads = new ArrayList<>();
/* 161 */     TextureAtlasSprite sprite = this.textures.get(getTextureLocation(prop.type, prop.insulation, prop.color, prop.active));
/*     */     
/* 163 */     for (EnumFacing facing : EnumFacing.field_82609_l) {
/* 164 */       boolean hasConnection = ((prop.connectivity & 1 << facing.ordinal()) != 0);
/*     */ 
/*     */       
/* 167 */       float zS = sp, yS = zS, xS = yS;
/* 168 */       float zE = sp + th, yE = zE, xE = yE;
/*     */       
/* 170 */       if (hasConnection) {
/* 171 */         switch (facing) { case DOWN:
/* 172 */             yS = 0.0F; yE = sp; break;
/* 173 */           case UP: yS = sp + th; yE = 1.0F; break;
/* 174 */           case NORTH: zS = 0.0F; zE = sp; break;
/* 175 */           case SOUTH: zS = sp + th; zE = 1.0F; break;
/* 176 */           case WEST: xS = 0.0F; xE = sp; break;
/* 177 */           case EAST: xS = sp + th; xE = 1.0F; break;
/* 178 */           default: throw new RuntimeException(); }
/*     */ 
/*     */         
/* 181 */         VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, EnumSet.complementOf((EnumSet)EnumSet.of(facing.func_176734_d())), sprite, arrayOfList, generalQuads);
/*     */       } else {
/* 183 */         VdUtil.addCuboid(xS, yS, zS, xE, yE, zE, EnumSet.of(facing), sprite, arrayOfList, generalQuads);
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     int used = 0;
/*     */     
/* 189 */     for (int j = 0; j < arrayOfList.length; j++) {
/* 190 */       if (arrayOfList[j].isEmpty()) {
/* 191 */         arrayOfList[j] = Collections.emptyList();
/*     */       } else {
/* 193 */         used++;
/*     */       } 
/*     */     } 
/*     */     
/* 197 */     if (used == 0) arrayOfList = null; 
/* 198 */     if (generalQuads.isEmpty()) generalQuads = Collections.emptyList();
/*     */     
/* 200 */     return (IBakedModel)new BasicBakedBlockModel(arrayOfList, generalQuads, sprite);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onReload() {
/* 205 */     this.modelCache.invalidateAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needsEnhancing(IBlockState state) {
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture(Ic2BlockState.Ic2BlockStateInstance state) {
/* 215 */     if (!state.hasValue(TileEntityCable.renderStateProperty)) {
/* 216 */       return ModelUtil.getMissingModel().func_177554_e();
/*     */     }
/* 218 */     TileEntityCable.CableRenderState prop = (TileEntityCable.CableRenderState)state.getValue(TileEntityCable.renderStateProperty);
/*     */     
/* 220 */     if (prop.foam == CableFoam.Soft)
/* 221 */       return ModelUtil.getBlockModel(BlockName.foam.getBlockState((IIdProvider)BlockFoam.FoamType.normal)).func_177554_e(); 
/* 222 */     if (prop.foam == CableFoam.Hardened) {
/* 223 */       TileEntityWall.WallRenderState wallProp = (TileEntityWall.WallRenderState)state.getValue(TileEntityWall.renderStateProperty);
/*     */       
/* 225 */       if (wallProp == null)
/* 226 */         return ModelUtil.getMissingModel().func_177554_e(); 
/* 227 */       if (wallProp.obscurations == null) {
/* 228 */         return ModelUtil.getBlockModel(BlockName.wall.getBlockState((IIdProvider)wallProp.color)).func_177554_e();
/*     */       }
/* 230 */       Obscuration.ObscurationData data = wallProp.obscurations[EnumFacing.UP.ordinal()];
/* 231 */       if (data == null) return ModelUtil.getBlockModel(BlockName.wall.getBlockState((IIdProvider)wallProp.color)).func_177554_e();
/*     */       
/* 233 */       return ModelUtil.getBlockModel(data.state).func_177554_e();
/*     */     } 
/*     */     
/* 236 */     return this.textures.get(getTextureLocation(prop.type, prop.insulation, prop.color, prop.active));
/*     */   }
/*     */ 
/*     */   
/* 240 */   private final Map<ResourceLocation, TextureAtlasSprite> textures = generateTextureLocations();
/* 241 */   private final LoadingCache<TileEntityCable.CableRenderState, IBakedModel> modelCache = CacheBuilder.newBuilder()
/* 242 */     .maximumSize(256L)
/* 243 */     .expireAfterAccess(5L, TimeUnit.MINUTES)
/* 244 */     .build(new CacheLoader<TileEntityCable.CableRenderState, IBakedModel>()
/*     */       {
/*     */         public IBakedModel load(TileEntityCable.CableRenderState key) throws Exception {
/* 247 */           return CableModel.this.generateModel(key);
/*     */         }
/*     */       });
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\CableModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */