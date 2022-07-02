/*    */ package ic2.api.item;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IHazmatLike
/*    */ {
/*    */   boolean addsProtection(EntityLivingBase paramEntityLivingBase, EntityEquipmentSlot paramEntityEquipmentSlot, ItemStack paramItemStack);
/*    */   
/*    */   default boolean fullyProtects(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean hasCompleteHazmat(EntityLivingBase living) {
/* 63 */     for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
/* 64 */       if (slot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
/* 65 */         ItemStack stack = living.func_184582_a(slot);
/*    */         
/* 67 */         if (stack == null || !(stack.func_77973_b() instanceof IHazmatLike)) {
/* 68 */           return false;
/*    */         }
/*    */         
/* 71 */         IHazmatLike hazmat = (IHazmatLike)stack.func_77973_b();
/* 72 */         if (!hazmat.addsProtection(living, slot, stack)) return false; 
/* 73 */         if (hazmat.fullyProtects(living, slot, stack)) return true; 
/*    */       } 
/*    */     } 
/* 76 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jipar\Desktop\mods\1.12.2\industrialcraft-2-2.8.211-ex112.jar!\ic2\api\item\IHazmatLike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */