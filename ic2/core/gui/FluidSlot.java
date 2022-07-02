/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.init.Localization;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraftforge.fluids.Fluid;
/*    */ import net.minecraftforge.fluids.FluidStack;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ 
/*    */ public class FluidSlot
/*    */   extends GuiElement<FluidSlot> {
/*    */   public static final int posU = 8;
/*    */   public static final int posV = 160;
/*    */   public static final int normalWidth = 18;
/*    */   public static final int normalHeight = 18;
/*    */   
/*    */   public static FluidSlot createFluidSlot(GuiIC2<?> gui, int x, int y, IFluidTank tank) {
/* 19 */     return new FluidSlot(gui, x, y, 18, 18, tank);
/*    */   }
/*    */   public static final int fluidOffsetX = 1; public static final int fluidOffsetY = 1; public static final int fluidNetWidth = 16; public static final int fluidNetHeight = 16; private final IFluidTank tank;
/*    */   protected FluidSlot(GuiIC2<?> gui, int x, int y, int width, int height, IFluidTank tank) {
/* 23 */     super(gui, x, y, width, height);
/*    */     
/* 25 */     if (tank == null) throw new NullPointerException("Null FluidTank instance.");
/*    */     
/* 27 */     this.tank = tank;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 32 */     bindCommonTexture();
/*    */     
/* 34 */     FluidStack fs = this.tank.getFluid();
/*    */     
/* 36 */     this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, 8.0D, 160.0D);
/*    */     
/* 38 */     if (fs != null && fs.amount > 0) {
/*    */ 
/*    */       
/* 41 */       int fluidX = this.x + 1;
/* 42 */       int fluidY = this.y + 1;
/* 43 */       int fluidWidth = 16;
/* 44 */       int fluidHeight = 16;
/*    */       
/* 46 */       Fluid fluid = fs.getFluid();
/* 47 */       TextureAtlasSprite sprite = (fluid != null) ? getBlockTextureMap().func_110572_b(fluid.getStill(fs).toString()) : null;
/* 48 */       int color = (fluid != null) ? fluid.getColor(fs) : -1;
/*    */       
/* 50 */       bindBlockTexture();
/* 51 */       this.gui.drawSprite(fluidX, fluidY, fluidWidth, fluidHeight, sprite, color, 1.0D, false, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<String> getToolTip() {
/* 57 */     List<String> ret = super.getToolTip();
/*    */     
/* 59 */     FluidStack fs = this.tank.getFluid();
/*    */     
/* 61 */     if (fs == null || fs.amount <= 0) {
/* 62 */       ret.add("No Fluid");
/* 63 */       ret.add("Amount: 0 " + Localization.translate("ic2.generic.text.mb"));
/* 64 */       ret.add("Type: Not Available");
/*    */     } else {
/*    */       
/* 67 */       Fluid fluid = fs.getFluid();
/*    */       
/* 69 */       if (fluid != null) {
/* 70 */         ret.add(fluid.getLocalizedName(fs));
/* 71 */         ret.add("Amount: " + fs.amount + " " + Localization.translate("ic2.generic.text.mb"));
/* 72 */         String state = fs.getFluid().isGaseous() ? "Gas" : "Liquid";
/* 73 */         ret.add("Type: " + state);
/*    */       } else {
/*    */         
/* 76 */         ret.add("Invalid FluidStack instance.");
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\FluidSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */