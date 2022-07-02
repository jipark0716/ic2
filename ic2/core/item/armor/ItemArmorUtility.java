/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemArmor;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.DamageSource;
/*    */ import net.minecraftforge.common.ISpecialArmor;
/*    */ 
/*    */ public class ItemArmorUtility
/*    */   extends ItemArmorIC2
/*    */   implements ISpecialArmor {
/*    */   public ItemArmorUtility(ItemName name, String armorName, EntityEquipmentSlot type) {
/* 16 */     super(name, ItemArmor.ArmorMaterial.DIAMOND, armorName, type, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_77619_b() {
/* 27 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean func_82789_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
/* 40 */     return new ISpecialArmor.ArmorProperties(0, 0.0D, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
/* 45 */     return 0;
/*    */   }
/*    */   
/*    */   public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {}
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorUtility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */