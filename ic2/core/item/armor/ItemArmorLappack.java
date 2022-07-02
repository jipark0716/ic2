/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.profile.NotExperimental;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.EnumRarity;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ import net.minecraftforge.fml.relauncher.SideOnly;
/*    */ 
/*    */ @NotExperimental
/*    */ public class ItemArmorLappack
/*    */   extends ItemArmorElectric
/*    */ {
/*    */   public ItemArmorLappack() {
/* 16 */     super(ItemName.lappack, "lappack", EntityEquipmentSlot.CHEST, 2.0E7D, 2500.0D, 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canProvideEnergy(ItemStack stack) {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDamageAbsorptionRatio() {
/* 26 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnergyPerDamage() {
/* 31 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   @SideOnly(Side.CLIENT)
/*    */   public EnumRarity func_77613_e(ItemStack stack) {
/* 37 */     return EnumRarity.UNCOMMON;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorLappack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */