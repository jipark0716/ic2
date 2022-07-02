/*    */ package ic2.core.block.personal;
/*    */ 
/*    */ import ic2.api.upgrade.IUpgradableBlock;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityInventory;
/*    */ import ic2.core.block.invslot.InvSlotUpgrade;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ public class TileEntityTradingTerminal extends TileEntityInventory implements IHasGui, IUpgradableBlock {
/*    */   protected int range;
/*    */   public final InvSlotUpgrade rangeUpgrade;
/*    */   
/*    */   public TileEntityTradingTerminal() {
/* 22 */     this.rangeUpgrade = new InvSlotUpgrade((IInventorySlotHolder)this, "range", 1);
/* 23 */     this.rangeUpgrade.setStackSizeLimit(16);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onLoaded() {
/* 28 */     super.onLoaded();
/*    */     
/* 30 */     this.range = this.rangeUpgrade.getRemoteRange(512);
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_70296_d() {
/* 35 */     super.func_70296_d();
/*    */     
/* 37 */     if (!(func_145831_w()).field_72995_K) {
/* 38 */       this.range = this.rangeUpgrade.getRemoteRange(512);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 44 */     super.updateEntityServer();
/*    */     
/* 46 */     this.rangeUpgrade.tick();
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<?> getGuiContainer(EntityPlayer player) {
/* 51 */     return (ContainerBase<?>)new ContainerTradingTerminal(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 57 */     return (GuiScreen)new GuiTradingTerminal(new ContainerTradingTerminal(player, this));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */ 
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 66 */     return Collections.singleton(UpgradableProperty.RemotelyAccessible);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getEnergy() {
/* 71 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useEnergy(double amount) {
/* 76 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\personal\TileEntityTradingTerminal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */