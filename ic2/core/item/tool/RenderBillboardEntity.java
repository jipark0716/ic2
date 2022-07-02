/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderBillboardEntity
/*    */   extends Render<EntityParticle> {
/*    */   public RenderBillboardEntity(RenderManager manager) {
/* 11 */     super(manager);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     this.texture = new ResourceLocation("ic2", "textures/models/beam.png");
/*    */   }
/*    */   
/*    */   private final ResourceLocation texture;
/*    */   
/*    */   public void doRender(EntityParticle entity, double x, double y, double z, float yaw, float partialTickTime) {}
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityParticle entity) {
/*    */     return this.texture;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\RenderBillboardEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */