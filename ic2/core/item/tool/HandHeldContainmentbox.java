/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HandHeldContainmentbox
/*    */   extends HandHeldInventory
/*    */ {
/*    */   public HandHeldContainmentbox(EntityPlayer player, ItemStack stack1, int inventorySize) {
/* 17 */     super(player, stack1, inventorySize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<HandHeldContainmentbox> getGuiContainer(EntityPlayer player) {
/* 22 */     return (ContainerBase<HandHeldContainmentbox>)new ContainerContainmentbox(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 28 */     return (GuiScreen)new GuiContainmentbox(new ContainerContainmentbox(player, this));
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_70005_c_() {
/* 33 */     return "ic2.containment_box";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int index, ItemStack stack) {
/* 43 */     if (stack == null) return false;
/*    */     
/* 45 */     return (stack.func_77973_b() == ItemName.nuclear.getInstance() || stack
/* 46 */       .func_77973_b() instanceof ic2.core.item.reactor.ItemReactorMOX || stack
/* 47 */       .func_77973_b() instanceof ic2.core.item.reactor.ItemReactorUranium);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HandHeldContainmentbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */