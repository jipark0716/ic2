/*    */ package ic2.core.block.heatgenerator.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.heatgenerator.container.ContainerElectricHeatGenerator;
/*    */ import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.SlotGrid;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiElectricHeatGenerator
/*    */   extends GuiIC2<ContainerElectricHeatGenerator>
/*    */ {
/*    */   public GuiElectricHeatGenerator(ContainerElectricHeatGenerator container) {
/* 28 */     super((ContainerBase)container);
/*    */     
/* 30 */     addElement((new SlotGrid(this, 43, 26, 5, 2, SlotGrid.SlotStyle.Normal))
/* 31 */         .withTooltip("ic2.ElectricHeatGenerator.gui.coils"));
/* 32 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 44, (TileEntityBlock)container.base));
/* 33 */     addElement(Text.create(this, 34, 66, 109, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 36 */                 return Localization.translate("ic2.ElectricHeatGenerator.gui.hUmax", new Object[] {
/* 37 */                       Integer.valueOf(((TileEntityElectricHeatGenerator)((ContainerElectricHeatGenerator)GuiElectricHeatGenerator.access$000(this.this$0)).base).gettransmitHeat()), 
/* 38 */                       Integer.valueOf(((TileEntityElectricHeatGenerator)((ContainerElectricHeatGenerator)GuiElectricHeatGenerator.access$100(this.this$0)).base).getMaxHeatEmittedPerTick())
/*    */                     });
/*    */               }
/* 41 */             }), 5752026, false, true, true).withTooltip("ic2.ElectricHeatGenerator.gui.tooltipheat"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 46 */     return background;
/*    */   }
/*    */   
/* 49 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectricHeatGenerator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\gui\GuiElectricHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */