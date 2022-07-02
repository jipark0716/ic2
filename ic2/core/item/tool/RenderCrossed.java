/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderCrossed
/*    */   extends Render<EntityMiningLaser>
/*    */ {
/*    */   private final ResourceLocation texture;
/*    */   
/*    */   public RenderCrossed(RenderManager manager, ResourceLocation texture) {
/* 27 */     super(manager);
/*    */     
/* 29 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRender(EntityMiningLaser entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 34 */     if (entity.field_70126_B == 0.0F && entity.field_70127_C == 0.0F)
/*    */       return; 
/* 36 */     func_110776_a(getEntityTexture(entity));
/*    */     
/* 38 */     GlStateManager.func_179094_E();
/* 39 */     GlStateManager.func_179109_b((float)x, (float)y, (float)z);
/* 40 */     GlStateManager.func_179114_b(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
/* 41 */     GlStateManager.func_179114_b(entity.field_70127_C + (entity.field_70125_A - entity.field_70127_C) * partialTicks, 0.0F, 0.0F, 1.0F);
/* 42 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 43 */     BufferBuilder worldrenderer = tessellator.func_178180_c();
/* 44 */     float uSideS = 0.0F;
/* 45 */     float uSideE = 0.5F;
/* 46 */     float vSideS = 0.0F;
/* 47 */     float vSideE = 0.15625F;
/* 48 */     float uBackS = 0.0F;
/* 49 */     float uBackE = 0.15625F;
/* 50 */     float vBackS = 0.15625F;
/* 51 */     float vBackE = 0.3125F;
/* 52 */     float scale = 0.05625F;
/* 53 */     GlStateManager.func_179091_B();
/* 54 */     GlStateManager.func_179114_b(45.0F, 1.0F, 0.0F, 0.0F);
/* 55 */     GlStateManager.func_179152_a(scale, scale, scale);
/* 56 */     GlStateManager.func_179109_b(-4.0F, 0.0F, 0.0F);
/*    */ 
/*    */ 
/*    */     
/* 60 */     GL11.glNormal3f(scale, 0.0F, 0.0F);
/* 61 */     worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 62 */     worldrenderer.func_181662_b(-7.0D, -2.0D, -2.0D).func_187315_a(uBackS, vBackS).func_181675_d();
/* 63 */     worldrenderer.func_181662_b(-7.0D, -2.0D, 2.0D).func_187315_a(uBackE, vBackS).func_181675_d();
/* 64 */     worldrenderer.func_181662_b(-7.0D, 2.0D, 2.0D).func_187315_a(uBackE, vBackE).func_181675_d();
/* 65 */     worldrenderer.func_181662_b(-7.0D, 2.0D, -2.0D).func_187315_a(uBackS, vBackE).func_181675_d();
/* 66 */     tessellator.func_78381_a();
/* 67 */     GL11.glNormal3f(-scale, 0.0F, 0.0F);
/* 68 */     worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 69 */     worldrenderer.func_181662_b(-7.0D, 2.0D, -2.0D).func_187315_a(uBackS, vBackS).func_181675_d();
/* 70 */     worldrenderer.func_181662_b(-7.0D, 2.0D, 2.0D).func_187315_a(uBackE, vBackS).func_181675_d();
/* 71 */     worldrenderer.func_181662_b(-7.0D, -2.0D, 2.0D).func_187315_a(uBackE, vBackE).func_181675_d();
/* 72 */     worldrenderer.func_181662_b(-7.0D, -2.0D, -2.0D).func_187315_a(uBackS, vBackE).func_181675_d();
/* 73 */     tessellator.func_78381_a();
/*    */ 
/*    */ 
/*    */     
/* 77 */     for (int j = 0; j < 4; j++) {
/* 78 */       GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
/* 79 */       GL11.glNormal3f(0.0F, 0.0F, scale);
/* 80 */       worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
/* 81 */       worldrenderer.func_181662_b(-8.0D, -2.0D, 0.0D).func_187315_a(uSideS, vSideS).func_181675_d();
/* 82 */       worldrenderer.func_181662_b(8.0D, -2.0D, 0.0D).func_187315_a(uSideE, vSideS).func_181675_d();
/* 83 */       worldrenderer.func_181662_b(8.0D, 2.0D, 0.0D).func_187315_a(uSideE, vSideE).func_181675_d();
/* 84 */       worldrenderer.func_181662_b(-8.0D, 2.0D, 0.0D).func_187315_a(uSideS, vSideE).func_181675_d();
/* 85 */       tessellator.func_78381_a();
/*    */     } 
/*    */     
/* 88 */     GlStateManager.func_179101_C();
/* 89 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityMiningLaser entity) {
/* 94 */     return this.texture;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\RenderCrossed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */