/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.VanillaButton;
/*    */ import ic2.core.init.Localization;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiElectricBlock
/*    */   extends GuiIC2<ContainerElectricBlock>
/*    */ {
/*    */   public GuiElectricBlock(final ContainerElectricBlock container) {
/* 25 */     super((ContainerBase)container, 196);
/*    */     
/* 27 */     addElement((GuiElement)EnergyGauge.asBar(this, 79, 38, (TileEntityBlock)container.base));
/* 28 */     addElement(((VanillaButton)(new VanillaButton(this, 152, 4, 20, 20, createEventSender(0)))
/* 29 */         .withIcon(new Supplier<ItemStack>()
/*    */           {
/*    */             public ItemStack get() {
/* 32 */               return new ItemStack(Items.field_151137_ax);
/*    */             }
/* 35 */           })).withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 38 */               return ((TileEntityElectricBlock)container.base).getRedstoneMode();
/*    */             }
/*    */           }));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawForegroundLayer(int mouseX, int mouseY) {
/* 45 */     super.drawForegroundLayer(mouseX, mouseY);
/*    */     
/* 47 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.EUStorage.gui.info.armor"), 8, this.field_147000_g - 126 + 3, 4210752);
/*    */     
/* 49 */     this.field_146289_q.func_78276_b(Localization.translate("ic2.EUStorage.gui.info.level"), 79, 25, 4210752);
/* 50 */     int e = (int)Math.min(((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).energy.getEnergy(), ((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).energy.getCapacity());
/* 51 */     this.field_146289_q.func_78276_b(" " + e, 110, 35, 4210752);
/* 52 */     this.field_146289_q.func_78276_b("/" + (int)((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).energy.getCapacity(), 110, 45, 4210752);
/*    */     
/* 54 */     String output = Localization.translate("ic2.EUStorage.gui.info.output", new Object[] { Double.valueOf(((TileEntityElectricBlock)((ContainerElectricBlock)this.container).base).output) });
/* 55 */     this.field_146289_q.func_78276_b(output, 85, 60, 4210752);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 60 */     return background;
/*    */   }
/*    */   
/* 63 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIElectricBlock.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\GuiElectricBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */