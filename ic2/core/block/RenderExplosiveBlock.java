/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderExplosiveBlock
/*    */   extends Render<EntityIC2Explosive>
/*    */ {
/*    */   public RenderExplosiveBlock(RenderManager manager) {
/* 31 */     super(manager);
/*    */     
/* 33 */     this.field_76989_e = 0.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRender(EntityIC2Explosive entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 38 */     BlockRendererDispatcher blockRenderer = Minecraft.func_71410_x().func_175602_ab();
/* 39 */     GlStateManager.func_179094_E();
/* 40 */     GlStateManager.func_179109_b((float)x, (float)y + 0.5F, (float)z);
/*    */     
/* 42 */     if (entity.fuse - partialTicks + 1.0F < 10.0F) {
/* 43 */       float scale = 1.0F - (entity.fuse - partialTicks + 1.0F) / 10.0F;
/*    */       
/* 45 */       scale = Util.limit(scale, 0.0F, 1.0F);
/* 46 */       scale = Util.square(Util.square(scale));
/* 47 */       scale = 1.0F + scale * 0.3F;
/*    */       
/* 49 */       GlStateManager.func_179152_a(scale, scale, scale);
/*    */     } 
/*    */     
/* 52 */     float alpha = (1.0F - (entity.fuse - partialTicks + 1.0F) / 100.0F) * 0.8F;
/*    */     
/* 54 */     func_180548_c(entity);
/* 55 */     GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
/* 56 */     GlStateManager.func_179109_b(-0.5F, -0.5F, 0.5F);
/* 57 */     blockRenderer.func_175016_a(entity.renderBlockState, entity.func_70013_c());
/* 58 */     GlStateManager.func_179109_b(0.0F, 0.0F, 1.0F);
/*    */     
/* 60 */     if (entity.fuse / 5 % 2 == 0) {
/* 61 */       GlStateManager.func_179090_x();
/* 62 */       GlStateManager.func_179140_f();
/* 63 */       GlStateManager.func_179147_l();
/* 64 */       GlStateManager.func_179112_b(770, 772);
/* 65 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, alpha);
/* 66 */       GlStateManager.func_179136_a(-3.0F, -3.0F);
/* 67 */       GlStateManager.func_179088_q();
/* 68 */       blockRenderer.func_175016_a(entity.renderBlockState, 1.0F);
/* 69 */       GlStateManager.func_179136_a(0.0F, 0.0F);
/* 70 */       GlStateManager.func_179113_r();
/* 71 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 72 */       GlStateManager.func_179084_k();
/* 73 */       GlStateManager.func_179145_e();
/* 74 */       GlStateManager.func_179098_w();
/*    */     } 
/*    */     
/* 77 */     GlStateManager.func_179121_F();
/* 78 */     super.func_76986_a(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityIC2Explosive entity) {
/* 83 */     return TextureMap.field_110575_b;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\RenderExplosiveBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */