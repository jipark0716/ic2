/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
/*    */ import ic2.core.ref.ItemName;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemArmorSolarHelmet
/*    */   extends ItemArmorUtility
/*    */ {
/*    */   public ItemArmorSolarHelmet() {
/* 15 */     super(ItemName.solar_helmet, "solar", EntityEquipmentSlot.HEAD);
/*    */     
/* 17 */     func_77656_e(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/* 22 */     boolean ret = false;
/*    */     
/* 24 */     if (player.field_71071_by.field_70460_b.get(2) != null) {
/* 25 */       double chargeAmount = TileEntitySolarGenerator.getSkyLight(player.func_130014_f_(), player.func_180425_c());
/* 26 */       if (chargeAmount > 0.0D) {
/* 27 */         ret = (ElectricItem.manager.charge((ItemStack)player.field_71071_by.field_70460_b.get(2), chargeAmount, 2147483647, true, false) > 0.0D);
/*    */       }
/*    */     } 
/*    */     
/* 31 */     if (ret) player.field_71069_bz.func_75142_b();
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int func_77619_b() {
/* 42 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorSolarHelmet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */