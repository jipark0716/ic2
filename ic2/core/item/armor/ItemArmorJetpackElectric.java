/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.item.armor.jetpack.IJetpack;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraftforge.common.ISpecialArmor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemArmorJetpackElectric
/*    */   extends ItemArmorElectric
/*    */   implements IJetpack
/*    */ {
/*    */   public ItemArmorJetpackElectric() {
/* 22 */     super(ItemName.jetpack_electric, "jetpack", EntityEquipmentSlot.CHEST, 30000.0D, 60.0D, 1);
/*    */     
/* 24 */     func_77656_e(27);
/* 25 */     func_77625_d(1);
/* 26 */     setNoRepair();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean drainEnergy(ItemStack pack, int amount) {
/* 33 */     return (ElectricItem.manager.discharge(pack, (amount + 6), 2147483647, true, false, false) > 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPower(ItemStack stack) {
/* 38 */     return 0.7F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDropPercentage(ItemStack stack) {
/* 43 */     return 0.05F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isJetpackActive(ItemStack stack) {
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getChargeLevel(ItemStack stack) {
/* 53 */     return ElectricItem.manager.getCharge(stack) / getMaxCharge(stack);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getHoverMultiplier(ItemStack stack, boolean upwards) {
/* 58 */     return 0.1F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getWorldHeightDivisor(ItemStack stack) {
/* 64 */     return 1.28F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 72 */     return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 77 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 85 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 91 */     return 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorJetpackElectric.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */