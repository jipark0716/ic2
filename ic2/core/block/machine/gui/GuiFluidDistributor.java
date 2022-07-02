/*    */ package ic2.core.block.machine.gui;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.machine.container.ContainerFluidDistributor;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiFluidDistributor extends GuiIC2<ContainerFluidDistributor> {
/*    */   public GuiFluidDistributor(ContainerFluidDistributor container) {
/* 15 */     super((ContainerBase)container, 184);
/*    */     
/* 17 */     addElement((GuiElement)TankGauge.createPlain(this, 29, 38, 55, 47, (IFluidTank)((TileEntityFluidDistributor)container.base).fluidTank));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 22 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 24 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidDistributor.gui.mode.info"), 112, 47, 5752026);
/*    */     
/* 26 */     if (((TileEntityFluidDistributor)((ContainerFluidDistributor)this.container).base).getActive()) {
/* 27 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidDistributor.gui.mode.concentrate"), 95, 71, 5752026);
/*    */     } else {
/* 29 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidDistributor.gui.mode.distribute"), 95, 71, 5752026);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 35 */     super.func_73864_a(mouseX, mouseY, mouseButton);
/*    */     
/* 37 */     mouseX -= this.field_147003_i;
/* 38 */     mouseY -= this.field_147009_r;
/*    */     
/* 40 */     if (mouseX >= 117 && mouseY >= 58 && mouseX <= 135 && mouseY <= 66) {
/* 41 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)((ContainerFluidDistributor)this.container).base, 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 47 */     return new ResourceLocation("ic2", "textures/gui/GUIFluidDistributor.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiFluidDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */