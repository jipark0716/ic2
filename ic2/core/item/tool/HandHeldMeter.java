/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class HandHeldMeter
/*    */   extends HandHeldInventory
/*    */ {
/*    */   public HandHeldMeter(EntityPlayer player, ItemStack stack) {
/* 14 */     super(player, stack, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 19 */     return (ContainerBase<?>)new ContainerMeter(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 25 */     return (GuiScreen)new GuiToolMeter(new ContainerMeter(player, this));
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_70005_c_() {
/* 30 */     return "ic2.meter";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   void closeGUI() {
/* 40 */     this.player.func_71053_j();
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HandHeldMeter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */