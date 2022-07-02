/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerMetalFormer;
/*    */ import ic2.core.block.machine.tileentity.TileEntityMetalFormer;
/*    */ import ic2.core.block.wiring.CableType;
/*    */ import ic2.core.gui.CustomGauge;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.Gauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.IEnableHandler;
/*    */ import ic2.core.gui.RecipeButton;
/*    */ import ic2.core.gui.VanillaButton;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiMetalFormer
/*    */   extends GuiIC2<ContainerMetalFormer>
/*    */ {
/*    */   public GuiMetalFormer(final ContainerMetalFormer container) {
/* 30 */     super((ContainerBase)container);
/*    */     
/* 32 */     addElement((GuiElement)EnergyGauge.asBolt(this, 20, 37, (TileEntityBlock)container.base));
/* 33 */     addElement((GuiElement)CustomGauge.create(this, 52, 39, new CustomGauge.IGaugeRatioProvider()
/*    */           {
/*    */             public double getRatio() {
/* 36 */               return ((TileEntityMetalFormer)container.base).getProgress();
/*    */             }
/*    */           },  Gauge.GaugeStyle.ProgressMetalFormer));
/* 39 */     addElement(((VanillaButton)(new VanillaButton(this, 65, 53, 20, 20, createEventSender(0)))
/* 40 */         .withIcon(new Supplier<ItemStack>()
/*    */           {
/*    */             public ItemStack get() {
/* 43 */               switch (((TileEntityMetalFormer)container.base).getMode()) { case 0:
/* 44 */                   return ItemName.cable.getItemStack((Enum)CableType.copper);
/* 45 */                 case 1: return ItemName.forge_hammer.getItemStack();
/* 46 */                 case 2: return ItemName.cutter.getItemStack(); }
/* 47 */                return null;
/*    */             }
/* 51 */           })).withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 54 */               switch (((TileEntityMetalFormer)container.base).getMode()) { case 0:
/* 55 */                   return Localization.translate("ic2.MetalFormer.gui.switch.Extruding");
/* 56 */                 case 1: return Localization.translate("ic2.MetalFormer.gui.switch.Rolling");
/* 57 */                 case 2: return Localization.translate("ic2.MetalFormer.gui.switch.Cutting"); }
/* 58 */                return null;
/*    */             }
/*    */           }));
/*    */     
/* 62 */     if (RecipeButton.canUse()) {
/* 63 */       for (int i = 0; i < 3; i++) {
/* 64 */         final int mode = i;
/* 65 */         addElement((new RecipeButton(this, 52, 39, 46, 9, new String[] { "metal_former" + mode })).withEnableHandler(new IEnableHandler()
/*    */               {
/*    */                 public boolean isEnabled() {
/* 68 */                   return (((TileEntityMetalFormer)container.base).getMode() == mode);
/*    */                 }
/*    */               }));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 77 */     return new ResourceLocation("ic2", "textures/gui/GUIMetalFormer.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiMetalFormer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */