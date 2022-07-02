/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerCropHarvester;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiCropHarvester
/*    */   extends GuiIC2<ContainerCropHarvester> {
/*    */   public GuiCropHarvester(ContainerCropHarvester container) {
/* 17 */     super((ContainerBase)container);
/*    */     
/* 19 */     addElement((GuiElement)EnergyGauge.asBolt(this, 19, 37, (TileEntityBlock)container.base));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 24 */     return new ResourceLocation("ic2", "textures/gui/GUICropHarvester.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiCropHarvester.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */