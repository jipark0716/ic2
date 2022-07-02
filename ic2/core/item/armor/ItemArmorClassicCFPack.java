/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ItemArmorClassicCFPack
/*    */   extends ItemArmorUtility {
/*    */   public ItemArmorClassicCFPack() {
/* 11 */     super(ItemName.cf_pack, "batpack", EntityEquipmentSlot.CHEST);
/*    */     
/* 13 */     func_77656_e(260);
/*    */   }
/*    */   
/*    */   public boolean getCFPellet(EntityPlayer player, ItemStack pack) {
/* 17 */     if (pack.func_77952_i() > 0) {
/* 18 */       pack.func_77964_b(pack.func_77952_i() - 1);
/*    */       
/* 20 */       return true;
/*    */     } 
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double getDurabilityForDisplay(ItemStack stack) {
/* 28 */     return 1.0D - super.getDurabilityForDisplay(stack);
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorClassicCFPack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */