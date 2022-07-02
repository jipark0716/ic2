/*    */ package ic2.core.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ 
/*    */ public abstract class AbstractBakedModel
/*    */   implements IBakedModel {
/*    */   public boolean func_177555_b() {
/* 11 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_177556_c() {
/* 16 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_188618_c() {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite func_177554_e() {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemCameraTransforms func_177552_f() {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemOverrideList func_188617_f() {
/* 36 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\model\AbstractBakedModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */