/*    */ package ic2.core.block.machine.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.machine.container.ContainerAdvMiner;
/*    */ import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
/*    */ import ic2.core.gui.BasicButton;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiAdvMiner
/*    */   extends GuiIC2<ContainerAdvMiner>
/*    */ {
/*    */   public GuiAdvMiner(final ContainerAdvMiner container) {
/* 23 */     super((ContainerBase)container, 203);
/*    */     
/* 25 */     addElement((GuiElement)EnergyGauge.asBolt(this, 12, 55, (TileEntityBlock)container.base));
/* 26 */     addElement(BasicButton.create(this, 133, 101, createEventSender(0), BasicButton.ButtonStyle.AdvMinerReset)
/* 27 */         .withTooltip("ic2.AdvMiner.gui.switch.reset"));
/* 28 */     addElement(BasicButton.create(this, 123, 27, createEventSender(1), BasicButton.ButtonStyle.AdvMinerMode)
/* 29 */         .withTooltip("ic2.AdvMiner.gui.switch.mode"));
/* 30 */     addElement(BasicButton.create(this, 129, 45, createEventSender(2), BasicButton.ButtonStyle.AdvMinerSilkTouch)
/* 31 */         .withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 34 */               return Localization.translate("ic2.AdvMiner.gui.switch.silktouch", new Object[] { Boolean.valueOf(((TileEntityAdvMiner)this.val$container.base).silkTouch) });
/*    */             }
/*    */           }));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 41 */     BlockPos target = ((TileEntityAdvMiner)((ContainerAdvMiner)this.container).base).getMineTarget();
/*    */     
/* 43 */     if (target != null) {
/* 44 */       BlockPos pos = ((TileEntityAdvMiner)((ContainerAdvMiner)this.container).base).func_174877_v();
/* 45 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.AdvMiner.gui.info.minelevel", new Object[] { Integer.valueOf(target.func_177958_n() - pos.func_177958_n()), Integer.valueOf(target.func_177952_p() - pos.func_177952_p()), Integer.valueOf(target.func_177956_o() - pos.func_177956_o()) }), 28, 105, 2157374);
/*    */     } 
/*    */     
/* 48 */     if (((TileEntityAdvMiner)((ContainerAdvMiner)this.container).base).blacklist) {
/* 49 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.AdvMiner.gui.mode.blacklist"), 40, 31, 2157374);
/*    */     } else {
/* 51 */       this.field_146289_q.func_78276_b(Localization.translate("ic2.AdvMiner.gui.mode.whitelist"), 40, 31, 2157374);
/*    */     } 
/*    */     
/* 54 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTexture() {
/* 59 */     return new ResourceLocation("ic2", "textures/gui/GUIAdvMiner.png");
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\gui\GuiAdvMiner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */