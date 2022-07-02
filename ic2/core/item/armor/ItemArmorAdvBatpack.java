/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.profile.NotClassic;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ @NotClassic
/*    */ public class ItemArmorAdvBatpack
/*    */   extends ItemArmorElectric {
/*    */   public ItemArmorAdvBatpack() {
/* 12 */     super(ItemName.advanced_batpack, "advbatpack", EntityEquipmentSlot.CHEST, 600000.0D, 1000.0D, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack stack) {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 22 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 27 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorAdvBatpack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */