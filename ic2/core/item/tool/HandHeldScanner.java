/*    */ package ic2.core.item.tool;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class HandHeldScanner
/*    */   extends HandHeldInventory
/*    */ {
/*    */   ItemStack itemScanner;
/*    */   EntityPlayer player;
/*    */   
/*    */   public HandHeldScanner(EntityPlayer player, ItemStack itemScanner) {
/* 17 */     super(player, itemScanner, 0);
/*    */     
/* 19 */     this.itemScanner = itemScanner;
/* 20 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<HandHeldScanner> getGuiContainer(EntityPlayer player) {
/* 25 */     return (ContainerBase<HandHeldScanner>)new ContainerToolScanner(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 31 */     return (GuiScreen)new GuiToolScanner(new ContainerToolScanner(player, this));
/*    */   }
/*    */ 
/*    */   
/*    */   public String func_70005_c_() {
/* 36 */     return this.itemScanner.func_77977_a();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_145818_k_() {
/* 41 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\tool\HandHeldScanner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */