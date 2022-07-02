/*    */ package ic2.core.block.machine.tileentity;
/*    */ 
/*    */ import ic2.api.upgrade.IUpgradableBlock;
/*    */ import ic2.api.upgrade.UpgradableProperty;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.TileEntityInventory;
/*    */ import ic2.core.block.comp.Fluids;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.block.invslot.InvSlotUpgrade;
/*    */ import ic2.core.gui.dynamic.DynamicContainer;
/*    */ import ic2.core.gui.dynamic.DynamicGui;
/*    */ import ic2.core.gui.dynamic.GuiParser;
/*    */ import ic2.core.network.GuiSynced;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.util.Util;
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraftforge.fluids.FluidTank;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityTank
/*    */   extends TileEntityInventory
/*    */   implements IUpgradableBlock, IHasGui {
/*    */   public final InvSlotUpgrade upgradeSlot;
/*    */   @GuiSynced
/*    */   protected final FluidTank fluidTank;
/*    */   protected final Fluids fluids;
/*    */   
/*    */   public TileEntityTank() {
/* 38 */     this.upgradeSlot = new InvSlotUpgrade((IInventorySlotHolder)this, "upgrade", 4);
/*    */     
/* 40 */     this.fluids = (Fluids)addComponent((TileEntityComponent)new Fluids((TileEntityBlock)this));
/* 41 */     this.fluidTank = (FluidTank)this.fluids.addTank("fluid", 24000);
/*    */     
/* 43 */     this.comparator.setUpdate(() -> (this.fluidTank.getFluidAmount() == 0) ? 0 : (int)Util.lerp(1.0F, 15.0F, this.fluidTank.getFluidAmount() / this.fluidTank.getCapacity()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 50 */     super.updateEntityServer();
/*    */ 
/*    */     
/* 53 */     this.upgradeSlot.tick();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getEnergy() {
/* 60 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useEnergy(double amount) {
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<UpgradableProperty> getUpgradableProperties() {
/* 70 */     return EnumSet.of(UpgradableProperty.FluidConsuming, UpgradableProperty.FluidProducing);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ContainerBase<TileEntityTank> getGuiContainer(EntityPlayer player) {
/* 77 */     return (ContainerBase<TileEntityTank>)DynamicContainer.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 83 */     return (GuiScreen)DynamicGui.create((IInventory)this, player, GuiParser.parse(this.teBlock));
/*    */   }
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\machine\tileentity\TileEntityTank.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */