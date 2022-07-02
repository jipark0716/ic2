/*    */ package ic2.core.model;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.common.model.IModelState;
/*    */ import net.minecraftforge.common.model.TRSRTransformation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractModel
/*    */   implements IReloadableModel, IBakedModel
/*    */ {
/*    */   public Collection<ResourceLocation> getDependencies() {
/* 27 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<ResourceLocation> getTextures() {
/* 32 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public IModelState getDefaultState() {
/* 42 */     return (IModelState)TRSRTransformation.identity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<BakedQuad> func_188616_a(IBlockState state, EnumFacing side, long rand) {
/* 49 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_177555_b() {
/* 54 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_177556_c() {
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_188618_c() {
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite func_177554_e() {
/* 69 */     return Minecraft.func_71410_x().func_147117_R().func_174944_f();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemCameraTransforms func_177552_f() {
/* 74 */     return ItemCameraTransforms.field_178357_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemOverrideList func_188617_f() {
/* 79 */     return ItemOverrideList.field_188022_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\AbstractModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */