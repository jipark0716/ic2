/*    */ package ic2.core.block.machine.gui;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerFluidRegulator;
/*    */ import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class GuiFluidRegulator extends GuiIC2<ContainerFluidRegulator> {
/*    */   public GuiFluidRegulator(ContainerFluidRegulator container) {
/* 17 */     super((ContainerBase)container, 184);
/*    */     
/* 19 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 39, (TileEntityBlock)container.base));
/* 20 */     addElement((GuiElement)TankGauge.createNormal(this, 78, 34, (IFluidTank)((TileEntityFluidRegulator)container.base).getFluidTank()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 25 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 27 */     this.field_146289_q.func_78276_b(((TileEntityFluidRegulator)((ContainerFluidRegulator)this.container).base).getoutputmb() + Localization.translate("ic2.generic.text.mb"), 105, 57, 2157374);
/* 28 */     this.field_146289_q.func_78276_b(((TileEntityFluidRegulator)((ContainerFluidRegulator)this.container).base).getmodegui(), 145, 57, 2157374);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void func_73864_a(int i, int j, int k) throws IOException {
/* 34 */     super.func_73864_a(i, j, k);
/*    */     
/* 36 */     int xMin = (this.field_146294_l - this.field_146999_f) / 2;
/* 37 */     int yMin = (this.field_146295_m - this.field_147000_g) / 2;
/*    */     
/* 39 */     int x = i - xMin;
/* 40 */     int y = j - yMin;
/*    */     
/* 42 */     TileEntityFluidRegulator te = (TileEntityFluidRegulator)((ContainerFluidRegulator)this.container).base;
/*    */     
/* 44 */     if (x >= 102 && y >= 68 && x <= 110 && y <= 76) {
/* 45 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, -1000);
/*    */     }
/* 47 */     if (x >= 112 && y >= 68 && x <= 120 && y <= 76) {
/* 48 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, -100);
/*    */     }
/* 50 */     if (x >= 122 && y >= 68 && x <= 130 && y <= 76) {
/* 51 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, -10);
/*    */     }
/* 53 */     if (x >= 132 && y >= 68 && x <= 140 && y <= 76) {
/* 54 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, -1);
/*    */     }
/*    */     
/* 57 */     if (x >= 132 && y >= 44 && x <= 140 && y <= 52) {
/* 58 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, 1);
/*    */     }
/*    */     
/* 61 */     if (x >= 122 && y >= 44 && x <= 130 && y <= 52) {
/* 62 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, 10);
/*    */     }
/*    */     
/* 65 */     if (x >= 112 && y >= 44 && x <= 120 && y <= 52) {
/* 66 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, 100);
/*    */     }
/*    */     
/* 69 */     if (x >= 102 && y >= 44 && x <= 110 && y <= 52) {
/* 70 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, 1000);
/*    */     }
/*    */     
/* 73 */     if (x >= 151 && y >= 44 && x <= 161 && y <= 52) {
/* 74 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, 1001);
/*    */     }
/*    */     
/* 77 */     if (x >= 151 && y >= 68 && x <= 161 && y <= 76) {
/* 78 */       ((NetworkManager)IC2.network.get(false)).initiateClientTileEntityEvent((TileEntity)te, 1002);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 84 */     return background;
/*    */   }
/*    */   
/* 87 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIFluidRegulator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiFluidRegulator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */