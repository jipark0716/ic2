/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
/*    */ import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiWeightedFluidDistributor extends GuiWeightedDistributor<ContainerWeightedFluidDistributor> {
/*    */   public GuiWeightedFluidDistributor(ContainerWeightedFluidDistributor container) {
/* 15 */     super(container, 211);
/*    */     
/* 17 */     addElement((GuiElement)TankGauge.createPlain(this, 33, 111, 110, 10, (IFluidTank)((TileEntityWeightedFluidDistributor)container.base).fluidTank));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 22 */     return TEXTURE;
/*    */   }
/*    */   
/* 25 */   private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIWeightedFluidDistributor.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiWeightedFluidDistributor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */