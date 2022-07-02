/*    */ package ic2.core.block.generator.tileentity;
/*    */ 
/*    */ import ic2.core.block.IInventorySlotHolder;
/*    */ import ic2.core.block.invslot.InvSlotConsumable;
/*    */ import ic2.core.block.invslot.InvSlotConsumableItemStack;
/*    */ import ic2.core.init.MainConfig;
/*    */ import ic2.core.item.type.NuclearResourceType;
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.ConfigUtil;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ @NotClassic
/*    */ public class TileEntityRTGenerator
/*    */   extends TileEntityBaseGenerator
/*    */ {
/*    */   public final InvSlotConsumable fuelSlot;
/*    */   
/*    */   public TileEntityRTGenerator() {
/* 21 */     super(Math.round(16.0F * efficiency), 1, 20000);
/*    */     
/* 23 */     this.fuelSlot = (InvSlotConsumable)new InvSlotConsumableItemStack((IInventorySlotHolder)this, "fuel", 6, new ItemStack[] { ItemName.nuclear.getItemStack((Enum)NuclearResourceType.rtg_pellet) });
/* 24 */     this.fuelSlot.setStackSizeLimit(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean gainEnergy() {
/* 29 */     int counter = 0;
/*    */     
/* 31 */     for (int i = 0; i < this.fuelSlot.size(); i++) {
/* 32 */       if (!this.fuelSlot.isEmpty(i)) counter++;
/*    */     
/*    */     } 
/* 35 */     if (counter == 0) return false;
/*    */     
/* 37 */     this.energy.addEnergy(Math.pow(2.0D, (counter - 1)) * efficiency);
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean gainFuel() {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean needsFuel() {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean delayActiveUpdate() {
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/* 59 */   private static final float efficiency = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/radioisotope");
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\block\generator\tileentity\TileEntityRTGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */