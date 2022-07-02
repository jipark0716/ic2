/*    */ package ic2.core.item.armor;
/*    */ 
/*    */ import ic2.api.item.ElectricItem;
/*    */ import ic2.core.ref.ItemName;
/*    */ import ic2.core.util.StackUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ItemArmorStaticBoots
/*    */   extends ItemArmorUtility
/*    */ {
/*    */   public ItemArmorStaticBoots() {
/* 16 */     super(ItemName.static_boots, "rubber", EntityEquipmentSlot.FEET);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
/* 21 */     if (StackUtil.isEmpty((ItemStack)player.field_71071_by.field_70460_b.get(2)))
/*    */       return; 
/* 23 */     boolean ret = false;
/* 24 */     NBTTagCompound compound = StackUtil.getOrCreateNbtData(stack);
/*    */     
/* 26 */     boolean isNotWalking = (player.func_184187_bx() != null || player.func_70090_H());
/* 27 */     if (!compound.func_74764_b("x") || isNotWalking) compound.func_74768_a("x", (int)player.field_70165_t); 
/* 28 */     if (!compound.func_74764_b("z") || isNotWalking) compound.func_74768_a("z", (int)player.field_70161_v);
/*    */     
/* 30 */     double distance = Math.sqrt(((compound.func_74762_e("x") - (int)player.field_70165_t) * (compound.func_74762_e("x") - (int)player.field_70165_t) + (compound.func_74762_e("z") - (int)player.field_70161_v) * (compound.func_74762_e("z") - (int)player.field_70161_v)));
/* 31 */     if (distance >= 5.0D) {
/*    */       
/* 33 */       compound.func_74768_a("x", (int)player.field_70165_t);
/* 34 */       compound.func_74768_a("z", (int)player.field_70161_v);
/*    */       
/* 36 */       ret = (ElectricItem.manager.charge((ItemStack)player.field_71071_by.field_70460_b.get(2), Math.min(3.0D, distance / 5.0D), 2147483647, true, false) > 0.0D);
/*    */     } 
/*    */     
/* 39 */     if (ret) player.field_71069_bz.func_75142_b(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\core\item\armor\ItemArmorStaticBoots.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */