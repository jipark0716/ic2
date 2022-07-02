/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.machine.container.ContainerMatter;
/*    */ import ic2.core.block.machine.tileentity.TileEntityMatter;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiMatter extends GuiIC2<ContainerMatter> {
/*    */   public String progressLabel;
/*    */   public String amplifierLabel;
/*    */   
/*    */   public GuiMatter(ContainerMatter container) {
/* 21 */     super((ContainerBase)container);
/*    */     
/* 23 */     addElement((GuiElement)TankGauge.createNormal(this, 96, 22, (IFluidTank)((TileEntityMatter)container.base).fluidTank));
/*    */     
/* 25 */     this.progressLabel = Localization.translate("ic2.Matter.gui.info.progress");
/* 26 */     this.amplifierLabel = Localization.translate("ic2.Matter.gui.info.amplifier");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 32 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 34 */     this.field_146289_q.func_78276_b(this.progressLabel, 8, 22, 4210752);
/* 35 */     this.field_146289_q.func_78276_b(((TileEntityMatter)((ContainerMatter)this.container).base).getProgressAsString(), 18, 31, 4210752);
/*    */     
/* 37 */     if (((TileEntityMatter)((ContainerMatter)this.container).base).scrap > 0) {
/* 38 */       this.field_146289_q.func_78276_b(this.amplifierLabel, 8, 46, 4210752);
/* 39 */       this.field_146289_q.func_78276_b("" + ((TileEntityMatter)((ContainerMatter)this.container).base).scrap, 8, 58, 4210752);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 45 */     return new ResourceLocation("ic2", "textures/gui/GUIMatter.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiMatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */