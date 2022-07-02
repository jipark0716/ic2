/*    */ package ic2.core.block;
/*    */ 
/*    */ import ic2.api.tile.IRotorProvider;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KineticGeneratorRenderer<T extends TileEntity>
/*    */   extends TileEntitySpecialRenderer<T>
/*    */ {
/*    */   protected void renderBlockRotor(IRotorProvider windGen, World world, BlockPos pos) {
/* 28 */     int diameter = windGen.getRotorDiameter();
/* 29 */     if (diameter == 0)
/*    */       return; 
/* 31 */     float angle = windGen.getAngle();
/* 32 */     ResourceLocation rotorRL = windGen.getRotorRenderTexture();
/*    */     
/* 34 */     ModelBase model = rotorModels.get(Integer.valueOf(diameter));
/*    */     
/* 36 */     if (model == null) {
/* 37 */       model = new KineticGeneratorRotor(diameter);
/* 38 */       rotorModels.put(Integer.valueOf(diameter), model);
/*    */     } 
/*    */     
/* 41 */     EnumFacing facing = windGen.getFacing();
/* 42 */     pos = pos.func_177972_a(facing);
/*    */     
/* 44 */     int light = world.func_175626_b(pos, 0);
/* 45 */     int blockLight = light % 65536;
/* 46 */     int skyLight = light / 65536;
/* 47 */     OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, blockLight, skyLight);
/*    */     
/* 49 */     GlStateManager.func_179094_E();
/* 50 */     GlStateManager.func_179109_b(0.5F, 0.5F, 0.5F);
/*    */     
/* 52 */     switch (facing) {
/*    */       case NORTH:
/* 54 */         GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
/*    */         break;
/*    */       case EAST:
/* 57 */         GL11.glRotatef(-180.0F, 0.0F, 1.0F, 0.0F);
/*    */         break;
/*    */       case SOUTH:
/* 60 */         GL11.glRotatef(-270.0F, 0.0F, 1.0F, 0.0F);
/*    */         break;
/*    */       case UP:
/* 63 */         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/*    */         break;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 69 */     GlStateManager.func_179114_b(angle, 1.0F, 0.0F, 0.0F);
/* 70 */     GlStateManager.func_179109_b(-0.2F, 0.0F, 0.0F);
/*    */     
/* 72 */     func_147499_a(rotorRL);
/*    */     
/* 74 */     model.func_78088_a(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
/*    */     
/* 76 */     GlStateManager.func_179121_F();
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_192841_a(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
/* 81 */     GL11.glPushMatrix();
/* 82 */     GL11.glTranslatef((float)x, (float)y, (float)z);
/* 83 */     if (te instanceof IRotorProvider) renderBlockRotor((IRotorProvider)te, te.func_145831_w(), te.func_174877_v()); 
/* 84 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/* 87 */   private static final Map<Integer, ModelBase> rotorModels = new HashMap<>();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\KineticGeneratorRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */