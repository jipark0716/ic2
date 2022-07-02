/*    */ package ic2.core.gui;
/*    */ 
/*    */ import ic2.api.upgrade.IUpgradableBlock;
/*    */ import ic2.api.upgrade.IUpgradeItem;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.api.upgrade.UpgradeRegistry;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.init.Localization;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class UpgradesWidget extends GuiElement<UpgradesWidget> {
/*    */   private final List<ItemStack> compatibleUpgrades;
/*    */   private static final int xCoord = 96;
/*    */   
/*    */   public UpgradesWidget(GuiIC2<?> gui, int x, int y, IUpgradableBlock te) {
/* 19 */     super(gui, x, y, 10, 10);
/*    */     
/* 21 */     this.compatibleUpgrades = getCompatibleUpgrades(te);
/*    */   }
/*    */   private static final int yCoord = 128; private static final int iWidth = 10; private static final int iHeight = 10;
/*    */   
/*    */   public void drawBackground(int mouseX, int mouseY) {
/* 26 */     bindCommonTexture();
/*    */     
/* 28 */     this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, 96.0D, 128.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<String> getToolTip() {
/* 33 */     List<String> ret = super.getToolTip();
/*    */     
/* 35 */     ret.add(Localization.translate("ic2.generic.text.upgrade"));
/*    */     
/* 37 */     for (ItemStack itemstack : this.compatibleUpgrades) {
/* 38 */       ret.add(itemstack.func_82833_r());
/*    */     }
/*    */     
/* 41 */     return ret;
/*    */   }
/*    */   
/*    */   private static List<ItemStack> getCompatibleUpgrades(IUpgradableBlock block) {
/* 45 */     List<ItemStack> ret = new ArrayList<>();
/* 46 */     Set<UpgradableProperty> properties = block.getUpgradableProperties();
/*    */     
/* 48 */     for (ItemStack stack : UpgradeRegistry.getUpgrades()) {
/* 49 */       IUpgradeItem item = (IUpgradeItem)stack.func_77973_b();
/*    */       
/* 51 */       if (item.isSuitableFor(stack, properties)) ret.add(stack);
/*    */     
/*    */     } 
/* 54 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\UpgradesWidget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */