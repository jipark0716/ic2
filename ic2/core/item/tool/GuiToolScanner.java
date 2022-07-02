/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.util.Tuple;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiToolScanner
/*    */   extends GuiIC2<ContainerToolScanner>
/*    */ {
/*    */   public GuiToolScanner(ContainerToolScanner container) {
/* 19 */     super((ContainerBase)container, 230);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 24 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 26 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.itemScanner.found"), 10, 20, 2157374);
/*    */     
/* 28 */     if (((ContainerToolScanner)this.container).scanResults != null) {
/* 29 */       int count = 0;
/* 30 */       for (Tuple.T2<ItemStack, Integer> result : ((ContainerToolScanner)this.container).scanResults) {
/* 31 */         String name = ((ItemStack)result.a).func_77973_b().func_77653_i((ItemStack)result.a);
/* 32 */         this.field_146289_q.func_78276_b(result.b + "x " + name, 10, 34 + count * 11, 5752026);
/* 33 */         count++;
/* 34 */         if (count == 10)
/*    */           break; 
/* 36 */       }  RenderHelper.func_74518_a();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_146976_a(float f, int x, int y) {
/* 42 */     super.func_146976_a(f, x, y);
/* 43 */     if (((ContainerToolScanner)this.container).scanResults != null) {
/* 44 */       int count = 0;
/* 45 */       RenderHelper.func_74520_c();
/* 46 */       for (Tuple.T2<ItemStack, Integer> result : ((ContainerToolScanner)this.container).scanResults) {
/*    */         
/* 48 */         int xPos = 135 + (count & 0x1) * 15;
/*    */         
/* 50 */         drawItem(xPos, 11 * count + 28, (ItemStack)result.a);
/* 51 */         count++;
/* 52 */         if (count == 10)
/*    */           break; 
/* 54 */       }  RenderHelper.func_74518_a();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 62 */     return new ResourceLocation("ic2", "textures/gui/GUIToolScanner.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\GuiToolScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */