/*    */ package ic2.core.block.heatgenerator.tileentity;
/*    */ 
/*    */ import ic2.core.ContainerBase;
/*    */ import ic2.core.IHasGui;
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.TileEntityHeatSourceInventory;
/*    */ import ic2.core.block.heatgenerator.container.ContainerRTHeatGenerator;
/*    */ import ic2.core.block.heatgenerator.gui.GuiRTHeatGenerator;
/*    */ import ic2.core.block.invslot.InvSlotConsumable;
/*    */ import ic2.core.block.invslot.InvSlotConsumableItemStack;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.item.type.NuclearResourceType;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityRTHeatGenerator
/*    */   extends TileEntityHeatSourceInventory
/*    */   implements IHasGui
/*    */ {
/*    */   private boolean newActive;
/*    */   public final InvSlotConsumable fuelSlot;
/*    */   
/*    */   public TileEntityRTHeatGenerator() {
/* 33 */     this.fuelSlot = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "fuelSlot", 6, new ItemStack[] { ItemName.nuclear.getItemStack((Enum)NuclearResourceType.rtg_pellet) });
/* 34 */     this.fuelSlot.setStackSizeLimit(1);
/*    */     
/* 36 */     this.newActive = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void updateEntityServer() {
/* 42 */     super.updateEntityServer();
/*    */     
/* 44 */     if (this.HeatBuffer > 0) { this.newActive = true; } else { this.newActive = false; }
/* 45 */      if (getActive() != this.newActive) setActive(this.newActive);
/*    */   
/*    */   }
/*    */   
/*    */   protected int fillHeatBuffer(int maxAmount) {
/* 50 */     if (maxAmount >= getMaxHeatEmittedPerTick()) {
/* 51 */       return getMaxHeatEmittedPerTick();
/*    */     }
/*    */     
/* 54 */     return maxAmount;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxHeatEmittedPerTick() {
/* 60 */     int counter = 0;
/*    */     
/* 62 */     for (int i = 0; i < this.fuelSlot.size(); i++) {
/* 63 */       if (!this.fuelSlot.isEmpty(i)) counter++;
/*    */     
/*    */     } 
/* 66 */     if (counter == 0) return 0; 
/* 67 */     return (int)(Math.pow(2.0D, (counter - 1)) * outputMultiplier);
/*    */   }
/*    */ 
/*    */   
/*    */   public ContainerBase<TileEntityRTHeatGenerator> getGuiContainer(EntityPlayer player) {
/* 72 */     return (ContainerBase<TileEntityRTHeatGenerator>)new ContainerRTHeatGenerator(player, this);
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
/* 78 */     return (GuiScreen)new GuiRTHeatGenerator(new ContainerRTHeatGenerator(player, this));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onGuiClosed(EntityPlayer player) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 92 */   public static final float outputMultiplier = 2.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/heatgenerator/radioisotope");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\heatgenerator\tileentity\TileEntityRTHeatGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */