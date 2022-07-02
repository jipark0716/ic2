/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.gui.GuiElement;
/*    */ import ic2.core.gui.ItemImage;
/*    */ import ic2.core.gui.MouseButton;
/*    */ import ic2.core.gui.ScrollableList;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class GuiTradingTerminal
/*    */   extends GuiIC2<ContainerTradingTerminal> {
/*    */   public GuiTradingTerminal(ContainerTradingTerminal container) {
/* 18 */     super((ContainerBase)container, 176, 227);
/*    */     
/* 20 */     addElement((GuiElement)(this.list = new ScrollableList(this, 4, 20, 168, 99)));
/* 21 */     addElement((new ItemImage(this, 156, 4, () -> ItemName.wrench.getItemStack()) {
/* 22 */           private int count = 1;
/*    */ 
/*    */           
/*    */           protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
/* 26 */             switch (button) {
/*    */               case left:
/* 28 */                 GuiTradingTerminal.this.list.addItem(new ScrollableList.IListItem() {
/* 29 */                       private final int item = GuiTradingTerminal.null.this.count++;
/*    */ 
/*    */                       
/*    */                       public void onClick(MouseButton button) {
/* 33 */                         System.out.println(this.item + " clicked with " + button);
/*    */                       }
/*    */ 
/*    */                       
/*    */                       public String func_176610_l() {
/* 38 */                         return "Trader " + this.item;
/*    */                       }
/*    */                     });
/*    */                 break;
/*    */               
/*    */               case right:
/* 44 */                 if (this.count > 1) {
/* 45 */                   GuiTradingTerminal.this.list.removeItem(this.count-- - 2);
/*    */                 }
/*    */                 break;
/*    */             } 
/* 49 */             return true;
/*    */           }
/* 51 */         }).withTooltip("Settings"));
/*    */   }
/*    */   private final ScrollableList list;
/*    */   
/*    */   protected void drawBackgroundAndTitle(float partialTicks, int mouseX, int mouseY) {
/* 56 */     bindTexture();
/* 57 */     func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*    */     
/* 59 */     String name = Localization.translate(((TileEntityTradingTerminal)((ContainerTradingTerminal)this.container).base).func_70005_c_());
/* 60 */     drawXCenteredString(this.field_146999_f / 2, 8, name, 4210752, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTexture() {
/* 65 */     return TEXTURE;
/*    */   }
/*    */   
/* 68 */   private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUI_Trading_Terminal.png");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\GuiTradingTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */