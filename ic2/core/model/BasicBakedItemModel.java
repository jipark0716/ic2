/*    */ package ic2.core.model;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class BasicBakedItemModel extends AbstractBakedModel {
/*    */   private final List<BakedQuad> quads;
/*    */   
/*    */   public BasicBakedItemModel(List<BakedQuad> quads, TextureAtlasSprite particleTexture) {
/* 14 */     this.quads = quads;
/* 15 */     this.particleTexture = particleTexture;
/*    */   }
/*    */   private final TextureAtlasSprite particleTexture;
/*    */   
/*    */   public List<BakedQuad> func_188616_a(IBlockState state, EnumFacing side, long rand) {
/* 20 */     if (side != null) {
/* 21 */       return Collections.emptyList();
/*    */     }
/* 23 */     return this.quads;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite func_177554_e() {
/* 29 */     return this.particleTexture;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemCameraTransforms func_177552_f() {
/* 35 */     return ItemCameraTransforms.field_178357_a;
/*    */   }
/*    */   
/*    */   public static BakedQuad createQuad(int[] vertexData, EnumFacing side) {
/* 39 */     return new BakedQuad(vertexData, -1, side, null, true, VdUtil.vertexFormat);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\BasicBakedItemModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */