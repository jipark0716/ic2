/*    */ package ic2.core.block;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class OverlayTesr
/*    */   extends TileEntitySpecialRenderer<TileEntityBlock>
/*    */ {
/*    */   public void render(TileEntityBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
/* 21 */     IBlockState state = te.getBlockType().func_176223_P();
/*    */     
/* 23 */     GL11.glPushAttrib(64);
/* 24 */     GL11.glPushMatrix();
/*    */     
/* 26 */     RenderHelper.func_74518_a();
/* 27 */     GlStateManager.func_179112_b(770, 771);
/* 28 */     GlStateManager.func_179147_l();
/* 29 */     GlStateManager.func_179129_p();
/*    */     
/* 31 */     if (Minecraft.func_71379_u()) {
/* 32 */       GlStateManager.func_179103_j(7425);
/*    */     } else {
/* 34 */       GlStateManager.func_179103_j(7424);
/*    */     } 
/*    */     
/* 37 */     func_147499_a(TextureMap.field_110575_b);
/*    */ 
/*    */     
/* 40 */     float zScale = 1.001F;
/* 41 */     GlStateManager.func_179109_b((float)(x + 0.5D), (float)(y + 0.5D), (float)(z + 0.5D));
/* 42 */     GlStateManager.func_179152_a(zScale, zScale, zScale);
/* 43 */     GlStateManager.func_179109_b((float)-(x + 0.5D), (float)-(y + 0.5D), (float)-(z + 0.5D));
/*    */ 
/*    */     
/* 46 */     BlockRendererDispatcher renderer = Minecraft.func_71410_x().func_175602_ab();
/* 47 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 48 */     BufferBuilder wr = tessellator.func_178180_c();
/*    */     
/* 50 */     wr.func_181668_a(7, DefaultVertexFormats.field_176600_a);
/* 51 */     wr.func_178969_c(x - te.func_174877_v().func_177958_n(), y - te.func_174877_v().func_177956_o(), z - te.func_174877_v().func_177952_p());
/*    */     
/* 53 */     renderer.func_175019_b().func_178267_a((IBlockAccess)te.func_145831_w(), renderer
/* 54 */         .func_184389_a(state), state, te
/* 55 */         .func_174877_v(), wr, true);
/*    */     
/* 57 */     wr.func_178969_c(0.0D, 0.0D, 0.0D);
/* 58 */     tessellator.func_78381_a();
/*    */ 
/*    */     
/* 61 */     GL11.glPopMatrix();
/* 62 */     GL11.glPopAttrib();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\OverlayTesr.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */