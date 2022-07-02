/*    */ package ic2.core.gui;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemStackImage extends GuiElement<ItemStackImage> {
/*    */   private final Supplier<ItemStack> itemSupplier;
/*    */   
/*    */   public ItemStackImage(GuiIC2<?> gui, int x, int y, Supplier<ItemStack> itemSupplier) {
/* 13 */     super(gui, x, y, 16, 16);
/*    */     
/* 15 */     this.itemSupplier = itemSupplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 20 */     super.drawBackground(mouseX, mouseY);
/*    */     
/* 22 */     ItemStack stack = (ItemStack)this.itemSupplier.get();
/*    */     
/* 24 */     if (!StackUtil.isEmpty(stack)) {
/* 25 */       RenderHelper.func_74520_c();
/* 26 */       this.gui.drawItemStack(this.x, this.y, stack);
/* 27 */       RenderHelper.func_74518_a();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawForeground(int mouseX, int mouseY) {
/* 33 */     if (contains(mouseX, mouseY)) {
/* 34 */       ItemStack stack = (ItemStack)this.itemSupplier.get();
/*    */       
/* 36 */       if (!StackUtil.isEmpty(stack))
/* 37 */         this.gui.drawTooltip(mouseX, mouseY, stack); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\ItemStackImage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */