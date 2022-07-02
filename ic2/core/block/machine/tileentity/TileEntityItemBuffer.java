/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.upgrade.IUpgradableBlock;
/*    */ import ic2.api.upgrade.IUpgradeItem;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityInventory;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.invslot.InvSlotUpgrade;
/*    */ import ic2.core.block.machine.container.ContainerItemBuffer;
/*    */ import ic2.core.block.machine.gui.GuiItemBuffer;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.util.StackUtil;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityItemBuffer
/*    */   extends TileEntityInventory
/*    */   implements IHasGui, IUpgradableBlock
/*    */ {
/*    */   public final InvSlot rightcontentSlot;
/*    */   public final InvSlot leftcontentSlot;
/*    */   public final InvSlotUpgrade upgradeSlot;
/*    */   private boolean tick;
/*    */   
/*    */   public TileEntityItemBuffer() {
/* 99 */     this.tick = true;
/*    */     this.rightcontentSlot = new InvSlot((IInventorySlotHolder)this, "rightcontent", InvSlot.Access.IO, 24, InvSlot.InvSide.SIDE);
/*    */     this.leftcontentSlot = new InvSlot((IInventorySlotHolder)this, "leftcontent", InvSlot.Access.IO, 24, InvSlot.InvSide.NOTSIDE);
/*    */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 2);
/*    */     this.comparator.setUpdate(() -> calcRedstoneFromInvSlots(new InvSlot[] { this.rightcontentSlot, this.leftcontentSlot }));
/*    */   }
/*    */   
/*    */   protected void updateEntityServer() {
/*    */     super.updateEntityServer();
/*    */     ItemStack upgradeleft = this.upgradeSlot.get(0);
/*    */     ItemStack upgraderight = this.upgradeSlot.get(1);
/*    */     if (!StackUtil.isEmpty(upgradeleft) && !StackUtil.isEmpty(upgraderight)) {
/*    */       if (this.tick) {
/*    */         if (((IUpgradeItem)upgradeleft.func_77973_b()).onTick(upgradeleft, this))
/*    */           func_70296_d(); 
/*    */       } else if (((IUpgradeItem)upgraderight.func_77973_b()).onTick(upgraderight, this)) {
/*    */         func_70296_d();
/*    */       } 
/*    */       this.tick = !this.tick;
/*    */     } else {
/*    */       if (!StackUtil.isEmpty(upgradeleft)) {
/*    */         this.tick = true;
/*    */         if (((IUpgradeItem)upgradeleft.func_77973_b()).onTick(upgradeleft, this))
/*    */           func_70296_d(); 
/*    */       } 
/*    */       if (!StackUtil.isEmpty(upgraderight)) {
/*    */         this.tick = false;
/*    */         if (((IUpgradeItem)upgraderight.func_77973_b()).onTick(upgraderight, this))
/*    */           func_70296_d(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public ContainerBase<TileEntityItemBuffer> getGuiContainer(EntityPlayer player) {
/*    */     return (ContainerBase<TileEntityItemBuffer>)new ContainerItemBuffer(player, this);
/*    */   }
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/*    */     return (GuiScreen)new GuiItemBuffer(new ContainerItemBuffer(player, this));
/*    */   }
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/*    */     return EnumSet.of(UpgradableProperty.ItemProducing);
/*    */   }
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */   
/*    */   public double getEnergy() {
/*    */     return 40.0D;
/*    */   }
/*    */   
/*    */   public boolean useEnergy(double amount) {
/*    */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityItemBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */