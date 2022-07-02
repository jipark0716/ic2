/*    */ package ic2.core.block.kineticgenerator.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.kineticgenerator.container.ContainerElectricKineticGenerator;
/*    */ import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
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
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiElectricKineticGenertor
/*    */   extends GuiIC2<ContainerElectricKineticGenerator> {
/*    */   public GuiElectricKineticGenertor(ContainerElectricKineticGenerator container) {
/* 23 */     super((ContainerBase)container);
/*    */     
/* 25 */     addElement((new SlotGrid(this, 43, 26, 5, 2, SlotGrid.SlotStyle.Normal))
/* 26 */         .withTooltip("ic2.ElectricKineticGenerator.gui.motors"));
/* 27 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 44, (TileEntityBlock)container.base));
/* 28 */     addElement(Text.create(this, 29, 66, 119, 13, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 31 */                 return Localization.translate("ic2.ElectricKineticGenerator.gui.kUmax", new Object[] {
/* 32 */                       Integer.valueOf(((TileEntityElectricKineticGenerator)((ContainerElectricKineticGenerator)GuiElectricKineticGenertor.access$000(this.this$0)).base).getMaxKU()), 
/* 33 */                       Integer.valueOf(((TileEntityElectricKineticGenerator)((ContainerElectricKineticGenerator)GuiElectricKineticGenertor.access$100(this.this$0)).base).getMaxKUForGUI())
/*    */                     });
/*    */               }
/* 36 */             }), 5752026, false, true, true).withTooltip("ic2.ElectricKineticGenerator.gui.tooltipkin"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 41 */     return background;
/*    */   }
/*    */   
/* 44 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectricKineticGenerator.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\kineticgenerator\gui\GuiElectricKineticGenertor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */