/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.api.item.ItemWrapper;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ public class HandHeldToolbox
/*    */   extends HandHeldInventory
/*    */ {
/*    */   public HandHeldToolbox(EntityPlayer player, ItemStack stack, int inventorySize) {
/* 17 */     super(player, stack, inventorySize);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<HandHeldToolbox> getGuiContainer(EntityPlayer player) {
/* 22 */     return (ContainerBase<HandHeldToolbox>)new ContainerToolbox(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 28 */     return (GuiScreen)new GuiToolbox(new ContainerToolbox(player, this));
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_70005_c_() {
/* 33 */     return "toolbox";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_94041_b(int i, ItemStack itemstack) {
/* 43 */     if (StackUtil.isEmpty(itemstack)) return false; 
/* 44 */     return ItemWrapper.canBeStoredInToolbox(itemstack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HandHeldToolbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */