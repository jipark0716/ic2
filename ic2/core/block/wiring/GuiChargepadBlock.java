/*    */ package ic2.core.block.wiring;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.gui.EnergyGauge;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.Text;
/*    */ import ic2.core.gui.VanillaButton;
/*    */ import ic2.core.gui.dynamic.TextProvider;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class GuiChargepadBlock
/*    */   extends GuiIC2<ContainerChargepadBlock> {
/*    */   public GuiChargepadBlock(final ContainerChargepadBlock container) {
/* 22 */     super((ContainerBase)container, 161);
/*    */     
/* 24 */     addElement((GuiElement)EnergyGauge.asBar(this, 79, 38, (TileEntityBlock)container.base));
/* 25 */     addElement(((VanillaButton)(new VanillaButton(this, 152, 4, 20, 20, createEventSender(0)))
/* 26 */         .withIcon(new Supplier<ItemStack>()
/*    */           {
/*    */             public ItemStack get() {
/* 29 */               return new ItemStack(Items.field_151137_ax);
/*    */             }
/* 32 */           })).withTooltip(new Supplier<String>()
/*    */           {
/*    */             public String get() {
/* 35 */               return ((TileEntityChargepadBlock)container.base).getRedstoneMode();
/*    */             }
/*    */           }));
/* 38 */     addElement((GuiElement)Text.create(this, 79, 25, TextProvider.ofTranslated("ic2.EUStorage.gui.info.level"), 4210752, false));
/* 39 */     addElement((GuiElement)Text.create(this, 110, 35, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 42 */                 return " " + (int)Math.min(((TileEntityChargepadBlock)container.base).energy.getEnergy(), ((TileEntityChargepadBlock)container.base).energy.getCapacity());
/*    */               }
/*    */             }), 4210752, false));
/* 45 */     addElement((GuiElement)Text.create(this, 110, 45, TextProvider.of(new Supplier<String>()
/*    */             {
/*    */               public String get() {
/* 48 */                 return "/" + (int)((TileEntityChargepadBlock)container.base).energy.getCapacity();
/*    */               }
/*    */             },  ), 4210752, false));
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 55 */     return background;
/*    */   }
/*    */   
/* 58 */   private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIChargepadBlock.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\wiring\GuiChargepadBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */