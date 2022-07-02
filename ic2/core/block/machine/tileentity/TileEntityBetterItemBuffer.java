/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.upgrade.IUpgradableBlock;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityInventory;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.invslot.InvSlotUpgrade;
/*    */ import ic2.core.gui.dynamic.DynamicContainer;
/*    */ import ic2.core.gui.dynamic.DynamicGui;
/*    */ import ic2.core.gui.dynamic.GuiParser;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityBetterItemBuffer
/*    */   extends TileEntityInventory
/*    */   implements IHasGui, IUpgradableBlock
/*    */ {
/* 33 */   public final InvSlot bufferSlot = new InvSlot((IInventorySlotHolder)this, "buffer", InvSlot.Access.IO, 9, InvSlot.InvSide.ANY);
/* 34 */   public final InvSlotUpgrade upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 39 */     super.updateEntityServer();
/*    */     
/* 41 */     this.upgradeSlot.tick();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 47 */     return EnumSet.of(UpgradableProperty.ItemProducing, UpgradableProperty.ItemConsuming);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getEnergy() {
/* 52 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useEnergy(double amount) {
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerBase<TileEntityBetterItemBuffer> getGuiContainer(EntityPlayer player) {
/* 64 */     return (ContainerBase<TileEntityBetterItemBuffer>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 70 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityBetterItemBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */