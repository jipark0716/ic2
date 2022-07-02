/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class TileEntityPersonalChestRenderer
/*    */   extends TileEntitySpecialRenderer<TileEntityPersonalChest>
/*    */ {
/*    */   public void render(TileEntityPersonalChest te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
/* 17 */     func_147499_a(texture);
/*    */     
/* 19 */     float doorHingeX = 0.84375F;
/* 20 */     float doorHingeZ = 0.15625F;
/*    */     
/* 22 */     GlStateManager.func_179094_E();
/* 23 */     GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/* 24 */     GlStateManager.func_179137_b(x, y, z);
/*    */ 
/*    */     
/* 27 */     GlStateManager.func_179109_b(0.5F, 0.5F, 0.5F);
/*    */ 
/*    */ 
/*    */     
/* 31 */     switch (te.getFacing()) { case SOUTH:
/* 32 */         angle = 180.0F; break;
/* 33 */       case WEST: angle = 90.0F; break;
/* 34 */       case EAST: angle = -90.0F; break;
/* 35 */       default: angle = 0.0F;
/*    */         break; }
/*    */     
/* 38 */     GlStateManager.func_179114_b(angle, 0.0F, 1.0F, 0.0F);
/* 39 */     GlStateManager.func_179109_b(-0.5F, -0.5F, -0.5F);
/*    */ 
/*    */     
/* 42 */     float angle = te.getLidAngle(partialTicks);
/*    */     
/* 44 */     angle = 1.0F - angle * angle * angle;
/*    */     
/* 46 */     GlStateManager.func_179109_b(0.84375F, 0.0F, 0.15625F);
/* 47 */     GlStateManager.func_179114_b(angle * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 48 */     GlStateManager.func_179109_b(-0.84375F, 0.0F, -0.15625F);
/*    */ 
/*    */     
/* 51 */     this.model.render();
/*    */     
/* 53 */     GlStateManager.func_179121_F();
/*    */   }
/*    */   
/* 56 */   private static final ResourceLocation texture = new ResourceLocation("ic2", "textures/models/newsafe.png");
/* 57 */   private final ModelPersonalChest model = new ModelPersonalChest();
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\TileEntityPersonalChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */