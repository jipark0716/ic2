/*    */ package ic2.core.item.upgrade;
/*    */ import ic2.core.GuiIC2;
/*    */ import ic2.core.IC2;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.gui.Button;
/*    */ import ic2.core.gui.IClickHandler;
/*    */ import ic2.core.gui.MouseButton;
/*    */ import ic2.core.gui.VanillaButton;
/*    */ import ic2.core.init.Localization;
/*    */ import ic2.core.item.tool.HandHeldInventory;
/*    */ import ic2.core.network.NetworkManager;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ public abstract class HandHeldUpgradeOption extends HandHeldInventory {
/*    */   protected HandHeldUpgradeOption(HandHeldAdvancedUpgrade upgradeGUI, String name) {
/* 17 */     super(upgradeGUI.getPlayer(), upgradeGUI.getContainerStack(), 9);
/*    */     
/* 19 */     this.name = name;
/*    */   }
/*    */   protected final String name;
/*    */   protected NBTTagCompound getNBT() {
/* 23 */     return HandHeldAdvancedUpgrade.getTag(StackUtil.getOrCreateNbtData(this.containerStack), func_70005_c_());
/*    */   }
/*    */   
/*    */   Button<?> getBackButton(GuiIC2<?> gui, int x, int y) {
/* 27 */     return (new VanillaButton(gui, x, y, 50, 15, new IClickHandler()
/*    */         {
/*    */           public void onClick(MouseButton button) {
/* 30 */             ((NetworkManager)IC2.network.get(false)).requestGUI((IHasGui)HandHeldUpgradeOption.this);
/*    */           }
/* 32 */         })).withText(Localization.translate("ic2.upgrade.advancedGUI.back"));
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_70005_c_() {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 42 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\ite\\upgrade\HandHeldUpgradeOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */