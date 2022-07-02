/*    */ package ic2.core.gui.dynamic;
/*    */ 
/*    */ import ic2.core.item.tool.HandHeldInventory;
/*    */ import ic2.core.slot.SlotHologramSlot;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class DynamicHandHeldContainer<T extends HandHeldInventory>
/*    */   extends DynamicContainer<T>
/*    */ {
/*    */   public static <T extends HandHeldInventory> DynamicHandHeldContainer<T> create(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
/* 14 */     return new DynamicHandHeldContainer<>(base, player, guiNode);
/*    */   }
/*    */   
/*    */   protected DynamicHandHeldContainer(T base, EntityPlayer player, GuiParser.GuiNode guiNode) {
/* 18 */     super(base, player, guiNode);
/*    */   }
/*    */ 
/*    */   
/*    */   protected SlotHologramSlot.ChangeCallback getCallback() {
/* 23 */     return ((HandHeldInventory)this.base).makeSaveCallback();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onContainerEvent(String event) {
/* 28 */     ((HandHeldInventory)this.base).onEvent(event);
/*    */     
/* 30 */     super.onContainerEvent(event);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack func_184996_a(int slot, int button, ClickType type, EntityPlayer player) {
/* 35 */     boolean thrown = false;
/* 36 */     Slot realSlot = null;
/* 37 */     if (!(player.func_130014_f_()).field_72995_K && slot >= 0 && slot < this.field_75151_b.size()) {
/* 38 */       realSlot = this.field_75151_b.get(slot);
/*    */       
/* 40 */       thrown = ((HandHeldInventory)this.base).isThisContainer(realSlot.func_75211_c());
/*    */     } 
/*    */     
/* 43 */     ItemStack stack = super.func_184996_a(slot, button, type, player);
/*    */     
/* 45 */     if (thrown && !realSlot.func_75216_d()) {
/* 46 */       ((HandHeldInventory)this.base).saveAsThrown(stack);
/* 47 */       player.func_71053_j();
/*    */     } 
/*    */     
/* 50 */     return stack;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_75134_a(EntityPlayer player) {
/* 55 */     ((HandHeldInventory)this.base).onGuiClosed(player);
/*    */     
/* 57 */     super.func_75134_a(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\gui\dynamic\DynamicHandHeldContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */