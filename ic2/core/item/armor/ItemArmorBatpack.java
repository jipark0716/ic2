/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemArmorBatpack
/*    */   extends ItemArmorElectric
/*    */ {
/*    */   public ItemArmorBatpack() {
/* 11 */     super(ItemName.batpack, "batpack", EntityEquipmentSlot.CHEST, 60000.0D, 100.0D, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack stack) {
/* 16 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 21 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 26 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorBatpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */