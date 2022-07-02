/*     */ package ic2.core.model;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ public class MergedItemModel implements IBakedModel {
/*     */   private static final int xDataIndex = 0;
/*     */   private static final int yDataIndex = 1;
/*     */   private static final int colorDataIndex = 3;
/*     */   private static final int uDataIndex = 4;
/*     */   private static final int vDataIndex = 5;
/*     */   
/*     */   public MergedItemModel(IBakedModel parent, List<BakedQuad> mergedQuads, int retextureStart, int textureStride) {
/*  22 */     this.parent = parent;
/*  23 */     this.mergedQuads = mergedQuads;
/*  24 */     this.retextureStart = retextureStart;
/*  25 */     this.textureStride = textureStride;
/*     */   }
/*     */   private final IBakedModel parent; private final List<BakedQuad> mergedQuads; private final int retextureStart; private final int textureStride; private float[] currentUvs; private int[] currentColorMultipliers;
/*     */   public MergedItemModel copy() {
/*  29 */     List<BakedQuad> newMergedQuads = new ArrayList<>(this.mergedQuads);
/*     */     
/*  31 */     for (int i = this.retextureStart; i < this.mergedQuads.size(); i++) {
/*  32 */       BakedQuad oldQuad = this.mergedQuads.get(i);
/*  33 */       int[] vertexData = Arrays.copyOf(oldQuad.func_178209_a(), (oldQuad.func_178209_a()).length);
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
/*  44 */       BakedQuad newQuad = new BakedQuad(vertexData, oldQuad.func_178211_c(), oldQuad.func_178210_d(), oldQuad.func_187508_a(), oldQuad.shouldApplyDiffuseLighting(), oldQuad.getFormat());
/*     */ 
/*     */       
/*  47 */       newMergedQuads.set(i, newQuad);
/*     */     } 
/*     */     
/*  50 */     return new MergedItemModel(this.parent, newMergedQuads, this.retextureStart, this.textureStride);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSprite(TextureAtlasSprite sprite, int colorMultiplier, float uSShift, float vSShift, float uEShift, float vEShift) {
/*  58 */     boolean matchingUvs = (this.currentUvs != null && this.currentUvs.length == 4 && this.currentUvs[0] == sprite.func_94209_e() && this.currentUvs[1] == sprite.func_94206_g() && this.currentUvs[2] == sprite.func_94212_f() && this.currentUvs[3] == sprite.func_94210_h());
/*  59 */     boolean matchingColorMul = (this.currentColorMultipliers != null && this.currentColorMultipliers[0] == colorMultiplier);
/*     */     
/*  61 */     if (!matchingUvs || !matchingColorMul) {
/*  62 */       if (!matchingUvs) this.currentUvs = new float[] { sprite.func_94209_e(), sprite.func_94206_g(), sprite.func_94212_f(), sprite.func_94210_h() }; 
/*  63 */       if (!matchingColorMul) this.currentColorMultipliers = new int[] { colorMultiplier };
/*     */       
/*  65 */       setSpriteUnchecked(uSShift, vSShift, uEShift, vEShift);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSprite(float[] uvs, int[] colorMultipliers, float uSShift, float vSShift, float uEShift, float vEShift) {
/*  70 */     boolean matchingUvs = Arrays.equals(uvs, this.currentUvs);
/*  71 */     boolean matchingColorMul = Arrays.equals(colorMultipliers, this.currentColorMultipliers);
/*     */     
/*  73 */     if (!matchingUvs || !matchingColorMul) {
/*  74 */       if (!matchingUvs) this.currentUvs = uvs; 
/*  75 */       if (!matchingColorMul) this.currentColorMultipliers = colorMultipliers;
/*     */       
/*  77 */       setSpriteUnchecked(uSShift, vSShift, uEShift, vEShift);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setSpriteUnchecked(float uSShift, float vSShift, float uEShift, float vEShift) {
/*  82 */     if (this.mergedQuads.size() - this.retextureStart > this.textureStride * this.currentColorMultipliers.length) {
/*  83 */       throw new IllegalStateException(String.format("mismatched size/stride/multipliers: retex-quads=%d, stride=%d, muls=%d", new Object[] {
/*  84 */               Integer.valueOf(this.mergedQuads.size() - this.retextureStart), 
/*  85 */               Integer.valueOf(this.textureStride), 
/*  86 */               Integer.valueOf(this.currentColorMultipliers.length)
/*     */             }));
/*     */     }
/*  89 */     if (this.currentUvs.length != this.currentColorMultipliers.length * 4)
/*  90 */       throw new IllegalStateException(String.format("mismatched uvs/multipliers: uvs=%d, muls=%d", new Object[] {
/*  91 */               Integer.valueOf(this.currentUvs.length), 
/*  92 */               Integer.valueOf(this.currentColorMultipliers.length)
/*     */             })); 
/*     */     int baseIdx;
/*  95 */     for (int texture = 0; baseIdx < this.mergedQuads.size(); texture++, baseIdx += this.textureStride) {
/*  96 */       float uS = this.currentUvs[texture * 4];
/*  97 */       float vS = this.currentUvs[texture * 4 + 1];
/*  98 */       float uE = this.currentUvs[texture * 4 + 2];
/*  99 */       float vE = this.currentUvs[texture * 4 + 3];
/*     */       
/* 101 */       float du = uE - uS;
/* 102 */       float dv = vE - vS;
/*     */ 
/*     */       
/* 105 */       du /= uEShift - uSShift;
/* 106 */       uS -= du * uSShift;
/* 107 */       dv /= vEShift - vSShift;
/* 108 */       vS -= dv * (1.0F - vEShift);
/*     */       
/* 110 */       int colorMultiplier = mapColor(this.currentColorMultipliers[texture]);
/*     */       
/* 112 */       for (int i = 0; i < this.textureStride; i++) {
/* 113 */         int[] vertexData = ((BakedQuad)this.mergedQuads.get(baseIdx + i)).func_178209_a();
/*     */         
/* 115 */         for (int j = 0; j < 4; j++) {
/* 116 */           int offset = j * VdUtil.dataStride;
/*     */           
/* 118 */           vertexData[offset + 3] = colorMultiplier;
/*     */           
/* 120 */           float x = Float.intBitsToFloat(vertexData[offset + 0]);
/* 121 */           float y = Float.intBitsToFloat(vertexData[offset + 1]);
/*     */           
/* 123 */           vertexData[offset + 4] = Float.floatToRawIntBits(uS + x * du);
/* 124 */           vertexData[offset + 5] = Float.floatToRawIntBits(vS + y * dv);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int mapColor(int color) {
/* 134 */     int a = color >>> 24;
/*     */     
/* 136 */     if (a > 0) {
/* 137 */       return color & 0xFF00FF00 | (color & 0xFF) << 16 | (color & 0xFF0000) >> 16;
/*     */     }
/* 139 */     return 0xFF000000 | color & 0xFF00 | (color & 0xFF) << 16 | (color & 0xFF0000) >> 16;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BakedQuad> func_188616_a(IBlockState state, EnumFacing side, long rand) {
/* 145 */     if (side != null) {
/* 146 */       return this.parent.func_188616_a(state, side, rand);
/*     */     }
/* 148 */     return this.mergedQuads;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_177555_b() {
/* 154 */     return this.parent.func_177555_b();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_177556_c() {
/* 159 */     return this.parent.func_177556_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_188618_c() {
/* 164 */     return this.parent.func_188618_c();
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite func_177554_e() {
/* 169 */     return this.parent.func_177554_e();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ItemCameraTransforms func_177552_f() {
/* 175 */     return this.parent.func_177552_f();
/*     */   }
/*     */ 
/*     */   
/*     */   public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
/* 180 */     return Pair.of(this, this.parent.handlePerspective(cameraTransformType).getRight());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemOverrideList func_188617_f() {
/* 185 */     return this.parent.func_188617_f();
/*     */   }
/*     */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\MergedItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */