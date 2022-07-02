/*     */ package ic2.core.block;
/*     */ 
/*     */ import ic2.core.block.comp.Obscuration;
/*     */ import ic2.core.block.state.IIdProvider;
/*     */ import ic2.core.block.state.Ic2BlockState;
/*     */ import ic2.core.item.tool.ItemObscurator;
/*     */ import ic2.core.model.AbstractModel;
/*     */ import ic2.core.model.BasicBakedBlockModel;
/*     */ import ic2.core.model.ISpecialParticleModel;
/*     */ import ic2.core.model.MergedBlockModel;
/*     */ import ic2.core.model.ModelUtil;
/*     */ import ic2.core.model.VdUtil;
/*     */ import ic2.core.ref.BlockName;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class RenderBlockWall
/*     */   extends AbstractModel
/*     */   implements ISpecialParticleModel
/*     */ {
/*     */   public List<BakedQuad> func_188616_a(IBlockState rawState, EnumFacing side, long rand) {
/*  30 */     if (!(rawState instanceof Ic2BlockState.Ic2BlockStateInstance)) return ModelUtil.getMissingModel().func_188616_a(rawState, side, rand); 
/*  31 */     Ic2BlockState.Ic2BlockStateInstance state = (Ic2BlockState.Ic2BlockStateInstance)rawState;
/*     */     
/*  33 */     if (!state.hasValue(TileEntityWall.renderStateProperty)) {
/*  34 */       return ModelUtil.getMissingModel().func_188616_a((IBlockState)state, side, rand);
/*     */     }
/*     */     
/*  37 */     TileEntityWall.WallRenderState prop = (TileEntityWall.WallRenderState)state.getValue(TileEntityWall.renderStateProperty);
/*     */     
/*  39 */     float[][] uvs = new float[6][];
/*  40 */     int[][] colorMultipliers = new int[6][];
/*  41 */     TextureAtlasSprite[][] sprites = new TextureAtlasSprite[6][];
/*  42 */     int total = 0;
/*     */     
/*  44 */     for (int i = 0; i < 6; i++) {
/*  45 */       Obscuration.ObscurationData data = prop.obscurations[i];
/*  46 */       if (data != null) {
/*     */         
/*  48 */         ItemObscurator.ObscuredRenderInfo renderInfo = ItemObscurator.getRenderInfo(data.state, data.side);
/*  49 */         if (renderInfo != null)
/*     */         {
/*     */           
/*  52 */           if (renderInfo.uvs.length == 4 * data.colorMultipliers.length) {
/*     */             
/*  54 */             uvs[i] = renderInfo.uvs;
/*  55 */             colorMultipliers[i] = data.colorMultipliers;
/*  56 */             sprites[i] = renderInfo.sprites;
/*     */             
/*  58 */             total += data.colorMultipliers.length;
/*     */           }  } 
/*     */       } 
/*  61 */     }  IBakedModel baseModel = ModelUtil.getBlockModel(BlockName.wall.getBlockState((IIdProvider)prop.color));
/*     */     
/*  63 */     if (total == 0) return baseModel.func_188616_a((IBlockState)state, side, rand);
/*     */     
/*  65 */     MergedBlockModel mergedModel = generateModel(baseModel, (IBlockState)state, colorMultipliers);
/*  66 */     mergedModel.setSprite(uvs, colorMultipliers, sprites);
/*     */     
/*  68 */     return mergedModel.func_188616_a((IBlockState)state, side, rand);
/*     */   }
/*     */   
/*     */   private static MergedBlockModel generateModel(IBakedModel baseModel, IBlockState state, int[][] colorMultipliers) {
/*  72 */     float offset = 0.001F;
/*     */     
/*  74 */     List[] arrayOfList = new List[6];
/*  75 */     int[] retextureStart = new int[6];
/*  76 */     IntBuffer buffer = VdUtil.getQuadBuffer();
/*     */     
/*  78 */     for (EnumFacing side : EnumFacing.field_82609_l) {
/*  79 */       int[] sideColorMultipliers = colorMultipliers[side.ordinal()];
/*  80 */       List<BakedQuad> baseFaceQuads = baseModel.func_188616_a(state, side, 0L);
/*     */       
/*  82 */       if (sideColorMultipliers == null) {
/*  83 */         arrayOfList[side.ordinal()] = baseFaceQuads;
/*     */       } else {
/*  85 */         List<BakedQuad> mergedFaceQuads = new ArrayList<>(baseFaceQuads.size() + sideColorMultipliers.length);
/*  86 */         mergedFaceQuads.addAll(baseFaceQuads);
/*     */         
/*  88 */         for (int sideColorMultiplier : sideColorMultipliers) {
/*  89 */           generateQuad(side, 0.001F, buffer);
/*     */ 
/*     */           
/*  92 */           mergedFaceQuads.add(BasicBakedBlockModel.createQuad(Arrays.copyOf(buffer.array(), buffer.position()), side, null));
/*  93 */           buffer.rewind();
/*     */         } 
/*     */         
/*  96 */         arrayOfList[side.ordinal()] = mergedFaceQuads;
/*     */       } 
/*     */       
/*  99 */       retextureStart[side.ordinal()] = baseFaceQuads.size();
/*     */     } 
/*     */     
/* 102 */     return new MergedBlockModel(baseModel, arrayOfList, retextureStart);
/*     */   }
/*     */   
/*     */   private static void generateQuad(EnumFacing side, float offset, IntBuffer out) {
/* 106 */     float neg = -offset;
/* 107 */     float pos = 1.0F + offset;
/*     */     
/* 109 */     switch (side) {
/*     */       case DOWN:
/* 111 */         VdUtil.generateBlockVertex(neg, neg, neg, 0.0F, 0.0F, side, out);
/* 112 */         VdUtil.generateBlockVertex(pos, neg, neg, 1.0F, 0.0F, side, out);
/* 113 */         VdUtil.generateBlockVertex(pos, neg, pos, 1.0F, 1.0F, side, out);
/* 114 */         VdUtil.generateBlockVertex(neg, neg, pos, 0.0F, 1.0F, side, out);
/*     */         return;
/*     */       case UP:
/* 117 */         VdUtil.generateBlockVertex(neg, pos, neg, 0.0F, 0.0F, side, out);
/* 118 */         VdUtil.generateBlockVertex(neg, pos, pos, 0.0F, 1.0F, side, out);
/* 119 */         VdUtil.generateBlockVertex(pos, pos, pos, 1.0F, 1.0F, side, out);
/* 120 */         VdUtil.generateBlockVertex(pos, pos, neg, 1.0F, 0.0F, side, out);
/*     */         return;
/*     */       case NORTH:
/* 123 */         VdUtil.generateBlockVertex(neg, neg, neg, 0.0F, 0.0F, side, out);
/* 124 */         VdUtil.generateBlockVertex(neg, pos, neg, 0.0F, 1.0F, side, out);
/* 125 */         VdUtil.generateBlockVertex(pos, pos, neg, 1.0F, 1.0F, side, out);
/* 126 */         VdUtil.generateBlockVertex(pos, neg, neg, 1.0F, 0.0F, side, out);
/*     */         return;
/*     */       case SOUTH:
/* 129 */         VdUtil.generateBlockVertex(neg, neg, pos, 0.0F, 0.0F, side, out);
/* 130 */         VdUtil.generateBlockVertex(pos, neg, pos, 1.0F, 0.0F, side, out);
/* 131 */         VdUtil.generateBlockVertex(pos, pos, pos, 1.0F, 1.0F, side, out);
/* 132 */         VdUtil.generateBlockVertex(neg, pos, pos, 0.0F, 1.0F, side, out);
/*     */         return;
/*     */       case WEST:
/* 135 */         VdUtil.generateBlockVertex(neg, neg, neg, 0.0F, 0.0F, side, out);
/* 136 */         VdUtil.generateBlockVertex(neg, neg, pos, 1.0F, 0.0F, side, out);
/* 137 */         VdUtil.generateBlockVertex(neg, pos, pos, 1.0F, 1.0F, side, out);
/* 138 */         VdUtil.generateBlockVertex(neg, pos, neg, 0.0F, 1.0F, side, out);
/*     */         return;
/*     */       case EAST:
/* 141 */         VdUtil.generateBlockVertex(pos, neg, neg, 0.0F, 0.0F, side, out);
/* 142 */         VdUtil.generateBlockVertex(pos, pos, neg, 0.0F, 1.0F, side, out);
/* 143 */         VdUtil.generateBlockVertex(pos, pos, pos, 1.0F, 1.0F, side, out);
/* 144 */         VdUtil.generateBlockVertex(pos, neg, pos, 1.0F, 0.0F, side, out);
/*     */         return;
/*     */     } 
/* 147 */     throw new IllegalArgumentException();
/*     */   }
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
/*     */   public void onReload() {}
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsEnhancing(IBlockState state) {
/* 310 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleTexture(Ic2BlockState.Ic2BlockStateInstance state) {
/* 315 */     if (!state.hasValue(TileEntityWall.renderStateProperty)) {
/* 316 */       return ModelUtil.getMissingModel().func_177554_e();
/*     */     }
/* 318 */     TileEntityWall.WallRenderState prop = (TileEntityWall.WallRenderState)state.getValue(TileEntityWall.renderStateProperty);
/*     */     
/* 320 */     Obscuration.ObscurationData data = prop.obscurations[EnumFacing.UP.ordinal()];
/* 321 */     if (data == null) return ModelUtil.getBlockModel(BlockName.wall.getBlockState((IIdProvider)prop.color)).func_177554_e();
/*     */     
/* 323 */     return ModelUtil.getBlockModel(data.state).func_177554_e();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\RenderBlockWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */