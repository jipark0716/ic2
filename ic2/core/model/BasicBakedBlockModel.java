/*    */ package ic2.core.model;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BasicBakedBlockModel extends AbstractBakedModel {
/*    */   private final List<BakedQuad>[] faceQuads;
/*    */   
/*    */   public BasicBakedBlockModel(List<BakedQuad>[] faceQuads, List<BakedQuad> generalQuads, TextureAtlasSprite particleTexture) {
/* 13 */     this.faceQuads = faceQuads;
/* 14 */     this.generalQuads = generalQuads;
/* 15 */     this.particleTexture = particleTexture;
/*    */   }
/*    */   private final List<BakedQuad> generalQuads; private final TextureAtlasSprite particleTexture;
/*    */   
/*    */   public List<BakedQuad> func_188616_a(IBlockState state, EnumFacing side, long rand) {
/* 20 */     if (side == null)
/* 21 */       return this.generalQuads; 
/* 22 */     if (this.faceQuads == null) {
/* 23 */       return Collections.emptyList();
/*    */     }
/* 25 */     return this.faceQuads[side.ordinal()];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite func_177554_e() {
/* 31 */     return this.particleTexture;
/*    */   }
/*    */   
/*    */   public static BakedQuad createQuad(int[] vertexData, EnumFacing side, TextureAtlasSprite sprite) {
/* 35 */     return new BakedQuad(vertexData, -1, side, sprite, true, VdUtil.vertexFormat);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\BasicBakedBlockModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */