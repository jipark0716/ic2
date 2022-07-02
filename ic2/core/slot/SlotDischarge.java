/*    */ package ic2.core.slot;
/*    */ 
/*    */ import ic2.api.info.Info;
/*    */ import ic2.api.item.ElectricItem;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotDischarge
/*    */   extends Slot
/*    */ {
/* 16 */   public int tier = Integer.MAX_VALUE;
/*    */   
/*    */   public SlotDischarge(IInventory par1iInventory, int tier1, int par2, int par3, int par4) {
/* 19 */     super(par1iInventory, par2, par3, par4);
/* 20 */     this.tier = tier1;
/*    */   }
/*    */   
/*    */   public SlotDischarge(IInventory par1iInventory, int par2, int par3, int par4) {
/* 24 */     super(par1iInventory, par2, par3, par4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_75214_a(ItemStack stack) {
/* 29 */     if (stack == null) return false; 
/* 30 */     if (Info.itemInfo.getEnergyValue(stack) > 0.0D) return true;
/*    */     
/* 32 */     return (ElectricItem.manager.discharge(stack, Double.POSITIVE_INFINITY, this.tier, true, true, true) > 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\slot\SlotDischarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */