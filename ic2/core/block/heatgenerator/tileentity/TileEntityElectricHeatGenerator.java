/*    */ package ic2.core.block.heatgenerator.tileentity;
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityBlock;
/*    */ import ic2.core.block.TileEntityHeatSourceInventory;
/*    */ import ic2.core.block.comp.Energy;
/*    */ import ic2.core.block.comp.TileEntityComponent;
/*    */ import ic2.core.block.heatgenerator.container.ContainerElectricHeatGenerator;
/*    */ import ic2.core.block.heatgenerator.gui.GuiElectricHeatGenerator;
/*    */ import ic2.core.block.invslot.InvSlot;
/*    */ import ic2.core.block.invslot.InvSlotConsumable;
/*    */ import ic2.core.block.invslot.InvSlotDischarge;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.item.type.CraftingItemType;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityElectricHeatGenerator extends TileEntityHeatSourceInventory implements IHasGui {
/*    */   private boolean newActive;
/*    */   public final InvSlotDischarge dischargeSlot;
/*    */   
/*    */   public TileEntityElectricHeatGenerator() {
/* 30 */     this.coilSlot = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "CoilSlot", 10, new ItemStack[] { ItemName.crafting.getItemStack((Enum)CraftingItemType.coil) });
/* 31 */     this.coilSlot.setStackSizeLimit(1);
/* 32 */     this.dischargeSlot = new InvSlotDischarge((IInventorySlotHolder)this, InvSlot.Access.NONE, 4);
/*    */     
/* 34 */     this.energy = (Energy)addComponent((TileEntityComponent)Energy.asBasicSink((TileEntityBlock)this, 10000.0D, 4).addManagedSlot((InvSlot)this.dischargeSlot));
/*    */     
/* 36 */     this.newActive = false;
/*    */   }
/*    */   public final InvSlotConsumable coilSlot; protected final Energy energy;
/*    */   
/*    */   protected void updateEntityServer() {
/* 41 */     super.updateEntityServer();
/*    */     
/* 43 */     if (getActive() != this.newActive) setActive(this.newActive);
/*    */   
/*    */   }
/*    */   
/*    */   public ContainerBase<TileEntityElectricHeatGenerator> getGuiContainer(EntityPlayer player) {
/* 48 */     return (ContainerBase<TileEntityElectricHeatGenerator>)new ContainerElectricHeatGenerator(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 54 */     return (GuiScreen)new GuiElectricHeatGenerator(new ContainerElectricHeatGenerator(player, this));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected int fillHeatBuffer(int maxAmount) {
/* 64 */     int amount = Math.min(maxAmount, (int)(this.energy.getEnergy() / outputMultiplier));
/*    */     
/* 66 */     if (amount > 0) {
/* 67 */       this.energy.useEnergy(amount / outputMultiplier);
/* 68 */       this.newActive = true;
/*    */     } else {
/* 70 */       this.newActive = false;
/*    */     } 
/*    */     
/* 73 */     return amount;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHeatEmittedPerTick() {
/* 78 */     int counter = 0;
/*    */     
/* 80 */     for (int i = 0; i < this.coilSlot.size(); i++) {
/* 81 */       if (!this.coilSlot.isEmpty(i)) counter++;
/*    */     
/*    */     } 
/* 84 */     return counter * 10;
/*    */   }
/*    */   
/*    */   public final float getChargeLevel() {
/* 88 */     return (float)Math.min(1.0D, this.energy.getFillRatio());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 98 */   public static final double outputMultiplier = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/electric");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\tileentity\TileEntityElectricHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */