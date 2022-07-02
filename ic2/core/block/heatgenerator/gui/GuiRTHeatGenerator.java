/*    */ package ic2.core.block.heatgenerator.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.heatgenerator.container.ContainerRTHeatGenerator;
/*    */ import ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiRTHeatGenerator
/*    */   extends GuiIC2<ContainerRTHeatGenerator>
/*    */ {
/*    */   public GuiRTHeatGenerator(final ContainerRTHeatGenerator container) {
/* 20 */     super((ContainerBase)container);
/*    */     
/* 22 */     addElement(Text.create(this, 49, 66, 79, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 25 */                 return ((TileEntityRTHeatGenerator)container.base).gettransmitHeat() + " / " + ((TileEntityRTHeatGenerator)container.base).getMaxHeatEmittedPerTick();
/*    */               }
/*    */             }, 
/* 28 */           ), 5752026, false, 0, 0, true, true).withTooltip("ic2.RTHeatGenerator.gui.tooltipheat"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 33 */     return background;
/*    */   }
/*    */   
/* 36 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIRTHeatGenerator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\gui\GuiRTHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */