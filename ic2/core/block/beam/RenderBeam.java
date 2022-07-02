/*    */ package ic2.core.block.beam;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderBeam
/*    */   extends Render<EntityParticle> {
/*    */   private final ResourceLocation texture;
/*    */   
/*    */   public RenderBeam(RenderManager manager) {
/* 24 */     super(manager);
/*    */     
/* 26 */     this.texture = new ResourceLocation("ic2", "textures/models/beam.png");
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRender(EntityParticle entity, double x, double y, double z, float yaw, float partialTickTime) {
/* 31 */     EntityParticle particle = entity;
/* 32 */     EntityPlayerSP entityPlayerSP = (Minecraft.func_71410_x()).field_71439_g;
/*    */     
/* 34 */     double playerX = ((EntityPlayer)entityPlayerSP).field_70169_q + (((EntityPlayer)entityPlayerSP).field_70165_t - ((EntityPlayer)entityPlayerSP).field_70169_q) * partialTickTime;
/* 35 */     double playerY = ((EntityPlayer)entityPlayerSP).field_70167_r + (((EntityPlayer)entityPlayerSP).field_70163_u - ((EntityPlayer)entityPlayerSP).field_70167_r) * partialTickTime;
/* 36 */     double playerZ = ((EntityPlayer)entityPlayerSP).field_70166_s + (((EntityPlayer)entityPlayerSP).field_70161_v - ((EntityPlayer)entityPlayerSP).field_70166_s) * partialTickTime;
/*    */     
/* 38 */     double particleX = particle.field_70169_q + (particle.field_70165_t - particle.field_70169_q) * partialTickTime - playerX;
/* 39 */     double particleY = particle.field_70167_r + (particle.field_70163_u - particle.field_70167_r) * partialTickTime - playerY;
/* 40 */     double particleZ = particle.field_70166_s + (particle.field_70161_v - particle.field_70166_s) * partialTickTime - playerZ;
/*    */     
/* 42 */     double u1 = 0.0D;
/* 43 */     double u2 = 1.0D;
/* 44 */     double v1 = 0.0D;
/* 45 */     double v2 = 1.0D;
/*    */     
/* 47 */     double scale = 0.1D;
/*    */     
/* 49 */     func_110776_a(getEntityTexture(entity));
/*    */     
/* 51 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 52 */     BufferBuilder worldrenderer = tessellator.func_178180_c();
/*    */     
/* 54 */     GlStateManager.func_179132_a(false);
/* 55 */     GlStateManager.func_179147_l();
/*    */     
/* 57 */     worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/*    */ 
/*    */     
/* 60 */     worldrenderer.func_181662_b(particleX - (ActiveRenderInfo.func_178808_b() + ActiveRenderInfo.func_178805_e()) * scale, particleY - ActiveRenderInfo.func_178809_c() * scale, particleZ - (ActiveRenderInfo.func_178803_d() + ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u2, v2).func_181675_d();
/* 61 */     worldrenderer.func_181662_b(particleX - (ActiveRenderInfo.func_178808_b() - ActiveRenderInfo.func_178805_e()) * scale, particleY + ActiveRenderInfo.func_178809_c() * scale, particleZ - (ActiveRenderInfo.func_178803_d() - ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u2, v1).func_181675_d();
/* 62 */     worldrenderer.func_181662_b(particleX + (ActiveRenderInfo.func_178808_b() + ActiveRenderInfo.func_178805_e()) * scale, particleY + ActiveRenderInfo.func_178809_c() * scale, particleZ + (ActiveRenderInfo.func_178803_d() + ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u1, v1).func_181675_d();
/* 63 */     worldrenderer.func_181662_b(particleX + (ActiveRenderInfo.func_178808_b() - ActiveRenderInfo.func_178805_e()) * scale, particleY - ActiveRenderInfo.func_178809_c() * scale, particleZ + (ActiveRenderInfo.func_178803_d() - ActiveRenderInfo.func_178807_f()) * scale).func_187315_a(u1, v2).func_181675_d();
/*    */     
/* 65 */     tessellator.func_78381_a();
/*    */     
/* 67 */     GlStateManager.func_179084_k();
/* 68 */     GlStateManager.func_179132_a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityParticle entity) {
/* 73 */     return this.texture;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\beam\RenderBeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */