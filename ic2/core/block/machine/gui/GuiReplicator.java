/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerReplicator;
/*    */ import ic2.core.block.machine.tileentity.TileEntityReplicator;
/*    */ import ic2.core.gui.CustomButton;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.ItemImage;
/*    */ import ic2.core.gui.TankGauge;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.util.Util;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fluids.IFluidTank;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiReplicator extends GuiIC2<ContainerReplicator> {
/*    */   public GuiReplicator(final ContainerReplicator container) {
/* 27 */     super((ContainerBase)container, 184);
/*    */     
/* 29 */     addElement((GuiElement)EnergyGauge.asBolt(this, 136, 84, (TileEntityBlock)container.base));
/* 30 */     addElement((GuiElement)TankGauge.createNormal(this, 27, 30, (IFluidTank)((TileEntityReplicator)container.base).fluidTank));
/* 31 */     addElement((new ItemImage(this, 91, 17, new Supplier<ItemStack>()
/*    */           {
/*    */             public ItemStack get() {
/* 34 */               return ((TileEntityReplicator)container.base).pattern;
/*    */             }
/* 36 */           })).withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 39 */               TileEntityReplicator te = (TileEntityReplicator)container.base;
/* 40 */               if (te.pattern == null) return null;
/*    */               
/* 42 */               String uuReq = Util.toSiString(te.patternUu, 4) + Localization.translate("ic2.generic.text.bucketUnit");
/* 43 */               String euReq = Util.toSiString(te.patternEu, 4) + Localization.translate("ic2.generic.text.EU");
/*    */               
/* 45 */               return te.pattern.func_82833_r() + " UU: " + uuReq + " EU: " + euReq;
/*    */             }
/*    */           }));
/* 48 */     addElement((new CustomButton(this, 80, 16, 9, 18, createEventSender(0)))
/* 49 */         .withTooltip("ic2.Replicator.gui.info.last"));
/* 50 */     addElement((new CustomButton(this, 109, 16, 9, 18, createEventSender(1)))
/* 51 */         .withTooltip("ic2.Replicator.gui.info.next"));
/* 52 */     addElement((new CustomButton(this, 75, 82, 16, 16, createEventSender(3)))
/* 53 */         .withTooltip("ic2.Replicator.gui.info.Stop"));
/* 54 */     addElement((new CustomButton(this, 92, 82, 16, 16, createEventSender(4)))
/* 55 */         .withTooltip("ic2.Replicator.gui.info.single"));
/* 56 */     addElement((new CustomButton(this, 109, 82, 16, 16, createEventSender(5)))
/* 57 */         .withTooltip("ic2.Replicator.gui.info.repeat"));
/* 58 */     addElement((GuiElement)Text.create(this, 49, 36, 96, 16, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 61 */                 TileEntityReplicator te = (TileEntityReplicator)container.base;
/*    */                 
/* 63 */                 if (te.getMode() == TileEntityReplicator.Mode.STOPPED) {
/* 64 */                   return Localization.translate("ic2.Replicator.gui.info.Waiting");
/*    */                 }
/* 66 */                 int progressUu = 0;
/* 67 */                 int progressEu = 0;
/*    */                 
/* 69 */                 if (te.patternUu != 0.0D) {
/* 70 */                   progressUu = Math.min((int)Math.round(100.0D * te.uuProcessed / te.patternUu), 100);
/*    */                 }
/*    */                 
/* 73 */                 return String.format("UU:%d%%  EU:%d%%  >%s", new Object[] { Integer.valueOf(progressUu), Integer.valueOf(progressEu), (te.getMode() == TileEntityReplicator.Mode.SINGLE) ? "" : ">" });
/*    */               }
/*    */             }), new Supplier<Integer>()
/*    */           {
/*    */             public Integer get()
/*    */             {
/* 79 */               return Integer.valueOf((((TileEntityReplicator)container.base).getMode() == TileEntityReplicator.Mode.STOPPED) ? 15461152 : 2157374);
/*    */             }
/*    */           },  false, 4, 0, false, true));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 86 */     return new ResourceLocation("ic2", "textures/gui/GUIReplicator.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiReplicator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */